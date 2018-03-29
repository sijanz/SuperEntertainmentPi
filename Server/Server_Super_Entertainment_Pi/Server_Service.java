package Server.Server_Super_Entertainment_Pi;

import java.net.*;
import java.io.*;
import java.util.List;

import General.GeneralUse.GeneralDirectory;
import General.GeneralUse.GeneralFile;
import General.GeneralUse.GeneralPurpose;
import General.GeneralUse.GeneralMedia;
import General.SocketNetwork.SocketNetwork;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import Server.Database.DatabaseManager;
import Server.ServerGUI.OMXPlayer;
import Server.ServerGUI.PictureDisplayer;
import Server.Work_Directroy_Manager.Work_Directory_Manager;


/**
 * @author Dustin, Simon
 */
public class Server_Service implements Runnable {
    private Socket connection_to_a_client;
    private BufferedReader input_buffer = null;
    private DatabaseManager databaseManager = new DatabaseManager();

    private String received_Message = "";


    Server_Service(Socket connection_to_a_client) {

        // @debug
        System.out.println("Server_Service: starting...");

        this.connection_to_a_client = connection_to_a_client;
    }


    private void read() throws IOException {
        StringBuilder user_Input_Builder = new StringBuilder();
        String line;

		/*
         * Socket does block , ends reading at the defined end </Message>
		 */
        while ((line = input_buffer.readLine()) != null) {
            user_Input_Builder.append(line);
            if (line.contains("</Message>")) {
                break;
            }
        }

        // @debug
        System.out.println("reading final " + user_Input_Builder.toString());

        this.received_Message = user_Input_Builder.toString();
    }


    private static void send_message_to_client(Socket socket, String message_to_the_server) throws IOException {

        // @debug
        System.out.println("reading " + message_to_the_server);

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(message_to_the_server);
        printWriter.flush();
    }


    private void build_index() {


		/*
        We will iterate through
		the media directory
		First we
		will create the main nodes,
		media types, with the different
		name types
		*/
        for (String path_to_iterate_through : GeneralMedia.getMediaPaths()) {

            XML_Manager.create_Message(GeneralMedia.identifyPath(path_to_iterate_through));


			/*
              *
			  * media_type = Music | Video | Picture
			  *
			  * name_of_media_type_instance = Track | |
			  *
			  *
			  *
			  * <media_type>
			  *
			  * 		<name_of_media_type_instance attributes = {....}>
			  * 				Name of the Track
			  * 		</name_of_media_type_instance>
			  *
			  * </media_type>
			  *
			  * */
            List<String> all_files_from_specific_path = GeneralDirectory.getAllFilesFromPath(path_to_iterate_through);

            for (String name_of_File : all_files_from_specific_path) {

				/*
                  * At this point ,
				  * the  method will ad the following to the message.xml
				  *
				  *
			      *    <name_of_media_type_instance attributes = {....}>
				  * 				Name of the Track
				  * 	  </name_of_media_type_instance>
			      *
				  * */
                XML_Manager.append_new_Message(GeneralMedia.identifyPath(path_to_iterate_through),
                        GeneralMedia.identifyMediaTypeInstance(GeneralMedia.identifyPath(path_to_iterate_through)),
                        name_of_File);
            }
        }
    }


    private void determineAction(String message) {

        // @debug
        System.out.println("determining action...");

        // login
        if (message.contains(XML_Manager.XML_NODES.Login.toString())) {
            if (databaseManager.loginUser(extractUsername(message), extractPassword(message))) {
                sendToken(extractUsername(message));
            } else {
                sendError();
            }

            // logout
        } else if (message.contains(XML_Manager.XML_NODES.Logout.toString())) {
            logout(extractUsername(message));

            // register
        } else if (message.contains(XML_Manager.XML_NODES.Register.toString())) {
            if (registerUser(extractUsername(message), extractPassword(message))) {
                sendOk();
            } else {
                sendError();
            }

            // get index
        } else if (message.contains(XML_Manager.XML_NODES.Get_Index.toString())) {
            if (authenticate(extractUsername(message), extractToken(message))) {
                sendIndex();
            } else {
                sendError();
            }

            // delete user
        } else if (message.contains(XML_Manager.XML_NODES.Delete_User.toString())) {
            databaseManager.deleteUser(extractUsername(message));

            // change password
        } else if (message.contains(XML_Manager.XML_NODES.Change_Password.toString())) {
            databaseManager.changePassword(extractUsername(message), extractPassword(message));

            // upload video
        } else if (message.contains(XML_Manager.XML_NODES.UPLOAD_Video.toString())) {

			/*
            * CAUTION, we will update just one video, at time........
			*
			* It is possible to update several videos, but this needs a different structure
			* for the uploading socket
			* */


            List<String> list_of_File_Names = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Clip.toString(), XML_Manager.XML_NODES.UPLOAD_Video.toString(),
                    XML_Manager.XML_SUB_NODES.Upload_File_Size.toString(), message);


            List<String> list_of_File_Size = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Clip.toString(), XML_Manager.XML_SUB_NODES.Upload_File_Size.toString()
                    , XML_Manager.XML_NODES.UPLOAD_Video.toString(), message);


            String new_file_path = GeneralMedia.determineMediaPathName(XML_Manager.XML_NODES.VIDEO.toString(), list_of_File_Names.get(0));

            do_upload(new_file_path, Integer.parseInt(list_of_File_Size.get(0)));

            this.build_index();

            // upload audio
        } else if (message.contains(XML_Manager.XML_NODES.UPLOAD_Audio.toString())) {


			/*
            * CAUTION, we will update just one video, at time........
			*
			* It is possible to update several videos, but this needs a different structure
			* for the uploading socket
			* */
            List<String> list_of_File_Names = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Track.toString(), XML_Manager.XML_NODES.UPLOAD_Audio.toString(),
                    XML_Manager.XML_SUB_NODES.Upload_File_Size.toString(), message);
            List<String> list_of_File_Size = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Track.toString(), XML_Manager.XML_SUB_NODES.Upload_File_Size.toString()
                    , XML_Manager.XML_NODES.UPLOAD_Audio.toString(), message);


            String new_file_path = GeneralMedia.determineMediaPathName(XML_Manager.XML_NODES.MUSIC.toString(), list_of_File_Names.get(0));

            System.out.println("Name " + list_of_File_Names.get(0) + " " + list_of_File_Size.get(0));
            do_upload(new_file_path, Integer.parseInt(list_of_File_Size.get(0)));

            build_index();


            // upload picture
        } else if (message.contains(XML_Manager.XML_NODES.UPLOAD_Picture.toString())) {

        	/*
            * CAUTION, we will update just one video, at time........
			*
			* It is possible to update several videos, but this needs a different structure
			* for the uploading socket
			* */
            List<String> list_of_File_Names = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Image.toString(), XML_Manager.XML_NODES.UPLOAD_Picture.toString(),
                    XML_Manager.XML_SUB_NODES.Upload_File_Size.toString(), message);
            List<String> list_of_File_Size = GeneralPurpose.getContentOfTagAsString(XML_Manager.XML_SUB_NODES.Image.toString(), XML_Manager.XML_SUB_NODES.Upload_File_Size.toString()
                    , XML_Manager.XML_NODES.UPLOAD_Picture.toString(), message);


            String new_file_path = GeneralMedia.determineMediaPathName(XML_Manager.XML_NODES.PICTURE.toString(), list_of_File_Names.get(0));

            System.out.println("Name " + list_of_File_Names.get(0) + " " + list_of_File_Size.get(0));
            do_upload(new_file_path, Integer.parseInt(list_of_File_Size.get(0)));

            build_index();

        } else if (message.contains("</COMMAND>")) {

            if (message.contains("</Delete>")) {

                //<COMMAND><Delete>

                List<String> list_of_File_Names = GeneralPurpose.getContentOfTagAsString("Clip", "Delete", "COMMAND", message);

                System.out.println(message);
                System.out.println(String.join(" , ", list_of_File_Names));


                do_delete(list_of_File_Names);

                build_index();

            } else if (message.contains("</Play")) {

                if (message.contains("</Play_Video")) {
                    new OMXPlayer("Media/Videos/" + getMediaName(message));

                } else if (message.contains("</Play_Music")) {
                    new OMXPlayer("Media/Music/" + getMediaName(message));

                } else if (message.contains("</Play_Picture")) {
                    new Thread(new PictureDisplayer(getMediaName(message))).start();
                }

            } else if (message.contains("</Pause")) {
                OMXPlayer.playPause();

            } else if (message.contains("</Stop")) {

                if (message.contains("</Stop_Video")) {
                    OMXPlayer.stop();

                } else if (message.contains("</Stop_Music")) {
                    OMXPlayer.stop();

                } else if (message.contains("</Stop_Picture")) {
                    createLockFile("StopPicture.lock");
                }

            } else if (message.contains("</Volume_Up")) {
                OMXPlayer.volumeUp();
            } else if (message.contains("</Volume_Down")) {
                OMXPlayer.volumeDown();
            } else if (message.contains("</Fast_Forward")) {
                OMXPlayer.fastForward();
            } else if (message.contains("</Rewind")) {
                OMXPlayer.rewind();
            }
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
    }


    private void do_delete(List<String> list_of_File_Names) {
        for (String current_file : list_of_File_Names) {
            if (!(current_file.equals("<NO_PATH>"))) {
                String path_to_the_file = GeneralPurpose.buildPathToFile(current_file);
                GeneralFile.deleteFile(path_to_the_file);
            }
        }

        // @debug
        System.out.println("deleted");
    }


    private int calc_port_number() {
        Boolean flag = true;
        int possible_port_number = -42;

			/*
			 * We will calculate a port number,
			 * on that an upload socket can connecto to.
			 *
			 *
			 * We have to keep in midn, that the client
			 * receveives the message, on which port, the upload socke listens,
			 * before that port is openen, so we muss be sure, that tha no
			 * other upload socket, can take the port number of another one.
			 *
			 * */
        while (flag) {

            // rondom port numer 7000 - 47000

            int MAX_PORT_NUMBER = 47000;
            int MIN_PORT_NUMBER = 14000;
            int bound_PORT = 7000;

            possible_port_number = SocketNetwork.determinePortNumber(MAX_PORT_NUMBER, MIN_PORT_NUMBER, bound_PORT);


            if (!(Server_Manager.get_List_of_used_upload_socket_ports().indexOf(possible_port_number) > -1)) {
                Server_Manager.get_List_of_used_upload_socket_ports().add(possible_port_number);
                flag = false;
            }
        }
        return possible_port_number;

    }


    /*
     * Filesize n the range of int!!!!
     *
     * cannot upload files, greater than 2GB
     *
     * */
    private void do_upload(String new_file_path, int file_size) {
        try {
            int port_number_for_upload_socket = calc_port_number();
            InetAddress bindAddr = InetAddress.getByName(SocketNetwork.getIpAddress());
            System.out.println("Port is " + port_number_for_upload_socket);
            send_message_to_client(this.connection_to_a_client, " <Message><Upload_Port>" + port_number_for_upload_socket + "</Upload_Port></Message> \r\n");
            ServerSocket upload = new ServerSocket(port_number_for_upload_socket, 50, bindAddr);

            new Thread(new Send_Files(upload, new_file_path, file_size)).start();

        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                send_message_to_client(this.connection_to_a_client, "<Error>No Upload possible</Error> " + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Sends the token value of a given user to the client.
     *
     * @param username the name of the user to get the token value of
     */
    private void sendToken(String username) {
        try {
            send_message_to_client(this.connection_to_a_client, databaseManager.getToken(username) + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Logs out a user.
     *
     * @param username the name of the user to log out
     */
    private void logout(String username) {
        databaseManager.logoutUser(username);
    }

    private static String extractUsername(String receivedMessage) {
        if (receivedMessage.contains("<name>")) {
            int start = receivedMessage.indexOf("<name>") + 6;
            int end = 0;
            for (int i = start; i < receivedMessage.length(); i++) {
                if (receivedMessage.charAt(i) == '<') {
                    end = i;
                    break;
                }
            }
            return receivedMessage.substring(start, end);
        }
        return "";
    }

    private static String extractPassword(String receivedMessage) {
        if (receivedMessage.contains("<password>")) {
            int start = receivedMessage.indexOf("<password>") + 10;
            int end = 0;
            for (int i = start; i < receivedMessage.length(); i++) {
                if (receivedMessage.charAt(i) == '<') {
                    end = i;
                    break;
                }
            }
            return receivedMessage.substring(start, end);
        }
        return "";
    }

    private static String extractToken(String receivedMessage) {
        if (receivedMessage.contains("<token>")) {
            int start = receivedMessage.indexOf("<token>") + 7;
            int end = 0;
            for (int i = start; i < receivedMessage.length(); i++) {
                if (receivedMessage.charAt(i) == '<') {
                    end = i;
                    break;
                }
            }
            return receivedMessage.substring(start, end);
        }
        return "";
    }


    /**
     * Registers a new user.
     *
     * @param username the name of the user to register
     * @param password the password of the user to register
     * @return true if success, false otherwise
     */
    private boolean registerUser(String username, String password) {
        return databaseManager.createUser(username, password);
    }

    private void sendIndex() {

        this.build_index();

        // @debug
        System.out.println("Der Inhalt der verschickt werden soll " + GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));


        // We have to at a ' ', so the first character is not missing afterwards

        try {
            send_message_to_client(this.connection_to_a_client, " " + GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()) + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // TODO: easter egg
    private boolean authenticate(String username, String token) {
        return databaseManager.authenticateUser(username, token);
    }

    private void sendOk() {
        try {
            send_message_to_client(this.connection_to_a_client, "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendError() {
        try {
            send_message_to_client(this.connection_to_a_client, XML_Manager.XML_NODES.Error.toString() + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {

        try {
            InputStream is = this.connection_to_a_client.getInputStream();
            this.input_buffer = new BufferedReader(new InputStreamReader(is));

            this.read();

            // @debug
            System.out.println("Content is read " + this.received_Message + "  length of the message " + this.received_Message.length());

            determineAction(this.received_Message);

            Work_Directory_Manager.delete_user_directory(XML_Shell.get_path_of_communication_XML_File());

            this.connection_to_a_client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createLockFile(String lockName) {
        try {
            if (new File("/home/pi/" + lockName).createNewFile()) {
                System.out.println("Server_Service: " + lockName + " created");
            } else {
                System.out.println("Server_Service: failed to create " + lockName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getMediaName(String message) {
        int start = 0;
        int end = 0;
        int countL = 0;
        int countR = 0;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '<') {
                countL++;
            }
            if (countL == 5) {
                end = i;
                break;
            }
        }
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '>') {
                countR++;
            }
            if (countR == 4) {
                start = i + 1;
                break;
            }
        }
        return message.substring(start, end);
    }
}
