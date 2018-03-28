package Client.ClientManagement;

import java.io.*;

import General.GeneralUse.GeneralFile;
import General.GeneralUse.GeneralPurpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;


/**
 * @author Simon, Dustin
 */
public class ClientService extends Thread {

    private static String lastMessageFromServer;

    private static XML_Shell workFile = new XML_Shell(ClientManager.getWorkingDirectory());


    /**
     * Setter for lastMessageFromServer.
     *
     * @param messageFromServer the new message from the server
     */
    private static void setLastMessageFromServer(String messageFromServer) {
        lastMessageFromServer = messageFromServer;
    }


    /**
     * Getter for lastMessageFromServer.
     *
     * @return lastMessageFromServer
     */
    public static String getLastMessageFromServer() {
        return lastMessageFromServer;
    }


    /**
     * Uploads a file.
     *
     * @param lastMessage the last message form the server
     * @param filePath    the path of the file to upload
     */
    public static void manageUpload(String lastMessage, String filePath) {
        int receivedPortNumber = Integer.parseInt(lastMessage.replace("<Upload_Port>", "-").replace("</Upload_Port>", "-").split("-")[1]);
        UploadFile upload = new UploadFile(receivedPortNumber, filePath);
        upload.run();
    }


    /**
     * Receives a message from the server and stores it in receivedMessage.
     *
     * @param socket the client socket
     */
    public static void receiveMessageFromServer(ClientSocket socket) throws IOException {
        InputStream is = socket.clientSocket.getInputStream();
        BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(is));
        String receivedMessage = GeneralPurpose.normalizeString(inputBuffer.readLine().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", ""));
        setLastMessageFromServer(receivedMessage);

        // @debug
        System.out.println("received message: " + receivedMessage + "\n");
    }


    /**
     * Sends a message to the server.
     *
     * @param socket  the client socket
     * @param message the message to send
     */
    public static void sendMessageToServer(ClientSocket socket, String message) {

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


    /**
     * Builds a string that contains the command to request the index.
     *
     * @return the built string
     */
    static String requestIndex() {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Get_Index, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Get_Index, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Get_Index, XML_Manager.XML_SUB_NODES.token, Client.ClientManagement.User.getToken());
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }


    /**
     * Builds a string that contains the command to request the login verification.
     *
     * @return the built string
     */
    static String requestLoginVerification() {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Login, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Login, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Login, XML_Manager.XML_SUB_NODES.password, Client.ClientManagement.User.getPassword());
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }


    /**
     * Builds a string that contains the command to logout the user.
     *
     * @return the built string
     */
    static String logoutUser() {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Logout, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Logout, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }


    /**
     * Builds a string that contains the command to request the registration.
     *
     * @return the built string
     */
    static String requestRegistration() {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Register, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Register, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Register, XML_Manager.XML_SUB_NODES.password, Client.ClientManagement.User.getPassword());
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }


    /**
     * Builds a string that contains the command to delete the user.
     *
     * @return the built string
     */
    static String deleteUser() {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Delete_User, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Delete_User, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }


    /**
     * Builds a string that contains the command to change the password.
     *
     * @return the built string
     */
    static String changePassword(String password) {
        if (GeneralFile.checkIfFileExists(XML_Shell.get_path_of_communication_XML_File())) {
            GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(workFile, XML_FILE.Communication, XML_NODES.Change_Password, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Change_Password, XML_Manager.XML_SUB_NODES.name, Client.ClientManagement.User.getUsername());
        XML_Manager.append_new_Message(workFile, XML_FILE.Communication, XML_NODES.Change_Password, XML_Manager.XML_SUB_NODES.password, password);
        return GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File());
    }
}
