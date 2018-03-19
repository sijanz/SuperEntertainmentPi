package Client.Client_Super_Entertainment_Pi;

import Client.ClientGUI.RemoteCaller;
import Client.ErrorScreen.Network_Not_Available_Error;
import Client.ErrorScreen.Network_Not_Available_ErrorStarter;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;

import java.io.IOException;
import java.net.UnknownHostException;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


/**
 * Opens a socket, starts the GUI and contains methods for the interaction with the socket.
 *
 * @author Dustin, Simon
 */
public class Client_Manager {

    // the client socket
    private static Client_Socket connectionToServer;


    /**
     * Starts the client.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) throws Exception {
        checkConfiguration();

        // starts the client gui
        Client_Interaction_Starter startGUI = new Client_Interaction_Starter(RemoteCaller.class, args);
        startGUI.start();
    }


    /**
     * Reads and checks connection data from the configuration file.
     */
    private static void checkConfiguration() {
        try {
            Client_Manager.re_build_socket();
            Client_Manager.close_socket();
        } catch (IOException e) {
            new Network_Not_Available_ErrorStarter(Network_Not_Available_Error.class, null);
        }
    }


    /**
     * Getter for the client socket.
     *
     * @return the client socket
     */
    public static Client_Socket get_Client_Socket() {
        return connectionToServer;
    }


    /**
     * Closes the socket.
     */
    public static void close_socket() {

        try {
            connectionToServer.clientSocket.close();
        } catch (Exception e) {

            // manually close socket if method fails
            connectionToServer.clientSocket = null;

            e.printStackTrace();
        }
    }


    /**
     * Rebuilds the socket, called from other classes.
     */
    public static void re_build_socket() throws IOException {
        try {

            build_socket();
        } catch (Exception e) {
            throw new IOException();
        }
    }


    /**
     * Builds the socket.
     */
    private static void build_socket() throws Exception {
        try {
            connectionToServer = new Client_Socket();
        } catch (UnknownHostException e) {
            throw new UnknownHostException();
        } catch (IOException e) {
            throw new IOException();
        }
    }


    /**
     * Return the path of the working directory.
     *
     * @return path of the working directory
     */
    public static String get_working_directory() {

        return System.getProperty("user.dir");
    }


    /**
     * Sends login credentials to the server.
     *
     * @return true if the authentication is a success, false otherwise
     */
    public static boolean sendCredentials() {
        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.requestLoginVerification());
            Client_Service.receiveMessageFromServer(connectionToServer);
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !Client_Service.get_last_Message_from_Server().contains(XML_Manager.XML_NODES.Error.toString());
    }


    public static boolean register() {
        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.requestRegistration());
            Client_Service.receiveMessageFromServer(connectionToServer);
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !Client_Service.get_last_Message_from_Server().contains(XML_Manager.XML_NODES.Error.toString());
    }


    public static void logout() {
        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.logoutUser());
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteUser() {
        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.deleteUser());
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void changePassword(String password) {
        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.changePassword(password));
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the index from the server.
     */
    public static boolean retrieveIndex() throws IOException {

        try {
            build_socket();
            Client_Service.send_message_to_server(connectionToServer, Client_Service.requestIndex());
            Client_Service.receiveMessageFromServer(connectionToServer);
            close_socket();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // client side authentication
        if (Client_Service.get_last_Message_from_Server().equals(XML_Manager.XML_NODES.Error.toString())) {
            return false;
        } else {
            create_index();
            return true;
        }
    }


    /**
     * Creates the index on the client.
     */
    private static void create_index() {
        XML_Shell workFile = new XML_Shell();

        String msg_form_Server = Client_Service.get_last_Message_from_Server();

        try {
            XML_Manager.stringToDom(msg_form_Server, workFile);
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }

    }
}
