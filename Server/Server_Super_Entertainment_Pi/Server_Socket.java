package Server.Server_Super_Entertainment_Pi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.Configuration_File_Manager.Configuration_File_Manager;
import General.Socket_Server_Super_Entertainment_Pi.Socket_Network;


/**
 * @author Dustin
 */
class Server_Socket extends Socket_Network {

    private static String standard_ip_address = Configuration_File_Manager.get_Server_Ip_address();

    private ServerSocket serverSocket;

    static void set_Server_Socket() {

        System.out.println(Configuration_File_Manager.get_Server_Port());

        System.out.println(Configuration_File_Manager.get_Server_Ip_address());
        Socket_Network.set_standard_ip_address(Configuration_File_Manager.get_Server_Ip_address());
        Socket_Network.set_standard_port(Integer.parseInt(Configuration_File_Manager.get_Server_Port()));
        String standard_CIDR = "24";
        Socket_Network.set_standard_CIDR(standard_CIDR);
    }


    Server_Socket() throws Exception {
        super();

        try {
            if (this.get_ip_address().equals("127.0.0.1")) {
                set_hostname("localhost");
            }

            this.serverSocket = new ServerSocket(this.get_portnumber(), 50,
                    InetAddress.getByName(standard_ip_address));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            standard_ip_address = (String) get_ip_with_CIDR().keySet().toArray()[0];
            Socket_Network.set_standard_ip_address(standard_ip_address);
            Configuration_File_Manager.set_Configured_Ip_Adress(standard_ip_address);
            e.printStackTrace();
        }
    }


    Socket listen_on_port() {
        Socket connection_to_a_Client_established = null;

        try {
            connection_to_a_Client_established = this.serverSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection_to_a_Client_established;
    }
}

