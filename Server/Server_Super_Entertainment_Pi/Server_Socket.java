package Server.Server_Super_Entertainment_Pi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.SocketNetwork.SocketNetwork;


/**
 * @author Dustin
 */
class Server_Socket extends SocketNetwork {

    private static String standard_ip_address = ConfigurationFileManager.getServerIpAddress();

    private ServerSocket serverSocket;

    static void set_Server_Socket() {

        System.out.println(ConfigurationFileManager.getServerPort());

        System.out.println(ConfigurationFileManager.getServerIpAddress());
        SocketNetwork.setStandardIpAddress(ConfigurationFileManager.getServerIpAddress());
        SocketNetwork.setStandardPort(Integer.parseInt(ConfigurationFileManager.getServerPort()));
        String standard_CIDR = "24";
        SocketNetwork.setStandardCIDR(standard_CIDR);
    }


    Server_Socket() throws Exception {
        super();

        try {
            if (this.get_ip_address().equals("127.0.0.1")) {
                setHostname("localhost");
            }

            this.serverSocket = new ServerSocket(this.getPortNumber(), 50,
                    InetAddress.getByName(standard_ip_address));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            standard_ip_address = (String) getIpWithCidr().keySet().toArray()[0];
            SocketNetwork.setStandardIpAddress(standard_ip_address);
            ConfigurationFileManager.setConfiguredIpAddress(standard_ip_address);
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

