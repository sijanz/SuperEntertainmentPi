package Client.Client_Super_Entertainment_Pi;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.Configuration_File_Manager.Configuration_File_Manager;
import General.Socket_Server_Super_Entertainment_Pi.*;


/**
 * @author Dustin
 */
class Client_Socket extends Socket_Network {

    // the client socket
    Socket clientSocket;


    Client_Socket() throws Exception {
        super();

        try {

            // @debug
            System.out.println("Client_Socket: read ip: " + Configuration_File_Manager.get_Server_Ip_address() + " read port: " + Configuration_File_Manager.get_Server_Port());

            this.clientSocket = new Socket(Configuration_File_Manager.get_Server_Ip_address(), Integer.parseInt(Configuration_File_Manager.get_Server_Port()));

        } catch (UnknownHostException e) {
            throw new UnknownHostException();
        } catch (IOException e) {
            throw new IOException();
        }
    }
}