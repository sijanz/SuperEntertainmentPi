package Client.ClientManagement;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.Socket_Server_Super_Entertainment_Pi.*;


/**
 * Contains the socket for the client.
 *
 * @author Simon, Dustin
 */
class ClientSocket extends Socket_Network {

    // the client socket
    Socket clientSocket;


    /**
     * Creates a new socket for the client with the ip address and port number read from the config file.
     */
    ClientSocket() throws Exception {
        super();

        try {

            // @debug
            System.out.println("ClientSocket: read ip: " + ConfigurationFileManager.getServerIpAddress() + " read port: " + ConfigurationFileManager.getServerPort());

            this.clientSocket = new Socket(ConfigurationFileManager.getServerIpAddress(), Integer.parseInt(ConfigurationFileManager.getServerPort()));
        } catch (UnknownHostException e) {
            throw new UnknownHostException();
        } catch (IOException e) {
            throw new IOException();
        }
    }
}