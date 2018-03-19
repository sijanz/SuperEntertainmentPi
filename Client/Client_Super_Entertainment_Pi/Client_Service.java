package Client.Client_Super_Entertainment_Pi;

import java.io.*;

import General.General_Super_Entertainment_Pi.General_File;
import General.General_Super_Entertainment_Pi.General_Purpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;


/**
 * @author Dustin
 */
public class Client_Service extends Thread {

    private static String last_msg_from_Server;

    private static XML_Shell work_file = new XML_Shell(Client_Manager.get_working_directory());

    private static void set_last_Message_from_Server(String msg_from_Server) {
        last_msg_from_Server = msg_from_Server;
    }


    public static String get_last_Message_from_Server() {
        return last_msg_from_Server;
    }

    public static void manage_upload(String last_message, String file_apth) {
        int receivedPortNumber = Integer.parseInt(last_message.replace("<Upload_Port>", "-").replace("</Upload_Port>", "-").split("-")[1]);

        Upload_File upload = new Upload_File(receivedPortNumber, file_apth);
        upload.run();
    }


    public static void receiveMessageFromServer(Client_Socket socket) throws IOException {
        InputStream is = socket.clientSocket.getInputStream();
        BufferedReader input_buffer = new BufferedReader(new InputStreamReader(is));
        String received_message = General_Purpose.normalize_String(input_buffer.readLine().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", ""));
        set_last_Message_from_Server(received_message);

        // @debug
        System.out.println("received message: " + received_message + "\n");
    }


    public static void send_message_to_server(Client_Socket socket, String message) {

        // @debug
        System.out.println("\n\nsent message: " + message.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", ""));

        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.clientSocket.getOutputStream()));
            printWriter.print(message + "\r\n");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String requestIndex() {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Get_Index, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Get_Index, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Get_Index, XML_Manager.XML_SUB_NODES.token, User.getToken());
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }

    static String requestLoginVerification() {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Login, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Login, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Login, XML_Manager.XML_SUB_NODES.password, User.getPassword());
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }

    static String logoutUser() {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Logout, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Logout, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }

    static String requestRegistration() {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Register, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Register, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Register, XML_Manager.XML_SUB_NODES.password, User.getPassword());
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }

    static String deleteUser() {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Delete_User, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Delete_User, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }

    static String changePassword(String password) {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Change_Password, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Change_Password, XML_Manager.XML_SUB_NODES.name, User.getUsername());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Change_Password, XML_Manager.XML_SUB_NODES.password, password);
        return General_File.return_content_of_file(XML_Shell.get_path_of_communication_XML_File());
    }
}
