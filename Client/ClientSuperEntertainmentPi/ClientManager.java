package Client.ClientSuperEntertainmentPi;

import Client.ClientGUI.RemoteCaller;
import Client.ErrorScreen.NetworkNotAvailableError;
import Client.ErrorScreen.NetworkNotAvailableErrorStarter;
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
public class ClientManager {

    // the client socket
    private static ClientSocket connectionToServer;


    /**
     * Starts the client.
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {
        checkConfiguration();

        // starts the client gui
        ClientInteractionStarter startGUI = new ClientInteractionStarter(RemoteCaller.class, args);
        startGUI.start();
    }


    /**
     * Reads and checks connection data from the configuration file.
     */
    private static void checkConfiguration() {
        try {
            ClientManager.rebuildSocket();
            ClientManager.closeSocket();
        } catch (IOException e) {
            new NetworkNotAvailableErrorStarter(NetworkNotAvailableError.class, null);
        }
    }


    /**
     * Getter for the client socket.
     *
     * @return the client socket
     */
    public static ClientSocket getClientSocket() {
        return connectionToServer;
    }


    /**
     * Closes the socket.
     */
    public static void closeSocket() {
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
    public static void rebuildSocket() throws IOException {
        try {
            buildSocket();
        } catch (Exception e) {
            throw new IOException();
        }
    }


    /**
     * Builds the socket.
     */
    private static void buildSocket() throws Exception {
        try {
            connectionToServer = new ClientSocket();
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
    public static String getWorkingDirectory() {

        return System.getProperty("user.dir");
    }


    /**
     * Sends login credentials to the server.
     *
     * @return true if the authentication is a success, false otherwise
     */
    public static boolean sendCredentials() {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.requestLoginVerification());
            ClientService.receiveMessageFromServer(connectionToServer);
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !ClientService.getLastMessageFromServer().contains(XML_Manager.XML_NODES.Error.toString());
    }


    /**
     * Sends the command to register the user to the server.
     *
     * @return true if success, false otherwise
     */
    public static boolean register() {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.requestRegistration());
            ClientService.receiveMessageFromServer(connectionToServer);
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !ClientService.getLastMessageFromServer().contains(XML_Manager.XML_NODES.Error.toString());
    }


    /**
     * Sends the command to logout the user to the server.
     */
    public static void logout() {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.logoutUser());
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends the command to delete the user to the server.
     */
    public static void deleteUser() {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.deleteUser());
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends the command to change the password to the server.
     */
    public static void changePassword(String password) {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.changePassword(password));
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the index from the server.
     */
    public static boolean retrieveIndex() {
        try {
            buildSocket();
            ClientService.sendMessageToServer(connectionToServer, ClientService.requestIndex());
            ClientService.receiveMessageFromServer(connectionToServer);
            closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // client side authentication
        if (ClientService.getLastMessageFromServer().equals(XML_Manager.XML_NODES.Error.toString())) {
            return false;
        } else {
            createIndex();
            return true;
        }
    }


    /**
     * Creates the index on the client.
     */
    private static void createIndex() {
        XML_Shell workFile = new XML_Shell();
        String messageFromServer = ClientService.getLastMessageFromServer();

        try {
            XML_Manager.stringToDom(messageFromServer, workFile);
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        }

    }
}
