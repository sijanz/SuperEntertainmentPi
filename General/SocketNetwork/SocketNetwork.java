package General.SocketNetwork;


import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;

import General.Backlog.BacklogManager;


/**
 * This Class does contain, everything to handle, create 
 * the properties, that the socket needs, to be created, or
 *
 * @author Dustin, Simon
 */
public class SocketNetwork extends Socket {

    private int portNumber;
    private String ipAddress;


    /*
     * Methods to init a socket, with its standard properties like ip, port number
	 * and CIDR
	 */
    private static int standardPort = 6666;
    private static String standardCIDR = "24";
    private static String standardIpAddress = "serversuperentertainmentpi.local";

    // hostname of the server
    private static String hostname = "serversuperentertainmentpi.local";


	/*
     * This function returns all combinations of ipv4 addresses with CIDR of every
	 * interface that supports ipv4
	 */
    protected SocketNetwork() {
        this.portNumber = standardPort;
        this.ipAddress = standardIpAddress;
    }


    // gets the port number of the initialized object
    public int getPortNumber() {
        return this.portNumber;
    }


    // gets the ipv4 Adress with subnet
    protected static HashMap<String, Short> getIpWithCidr() {
        HashMap<String, Short> ipAddressWithSubnet = new HashMap<>();

        try {
            InetAddress localHost = Inet4Address.getLocalHost();

            // @debug
            System.out.println(localHost.getHostName() + " " + localHost.getHostAddress());

            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {

                // We will just search for ipv4 addresses
                if (address.getAddress() instanceof Inet4Address) {
                    ipAddressWithSubnet.put(address.getAddress().getHostAddress(),
                            address.getNetworkPrefixLength());
                }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

		/*
		 * result object does contain, all ipv4 adresses with CIDR , for every
		 * interface, that supports ipv4
		 */
        return ipAddressWithSubnet;
    }


    // sets the hostname of the initiated object
    public static void setHostname(String hostname) {
        SocketNetwork.hostname = hostname;
    }

    // gets the ip adress, that was initialized in the object
    public String get_ip_address() {
        return this.ipAddress;
    }


    public static InetAddress getIpAddressInInetFormat(String ip_address) throws UnknownHostException {
        return InetAddress.getByName(ip_address);
    }


	/*
	 * Method to generate a random Port-Number 
	 * 7000 - 470000
	 * 
	 * */
    public static int determinePortNumber(int MAX_PORT_NUMBER, int MIN_PORT_NUMBER, int bound_PORT) {
        Random Test_Port = new Random();
        int port_number;

        do {
            port_number = MAX_PORT_NUMBER - Test_Port.nextInt(MIN_PORT_NUMBER) - bound_PORT;

        }
        while (!(is_port_available(port_number)));

        return port_number;
    }


    private static boolean is_port_available(int port) {
        ServerSocket test_port_availability = null;

        try {
            test_port_availability = new ServerSocket(port);
            test_port_availability.setReuseAddress(true);
            test_port_availability.close();

            return true;
        } catch (IOException e) {
            BacklogManager.writeToBacklogFile("///// Cannot close socket on Port " + port);
        } finally {
            if (test_port_availability != null) {
                try {
                    test_port_availability.close();
                } catch (IOException e) {
                    BacklogManager.writeToBacklogFile("///// Cannot close socket on Port " + port);
                }
            }
        }

        return false;
    }
			
			
	
	/*
	 * Methods to set socket with standard properties like ip , portNumber and CIDR
	 */
    public static void setStandardIpAddress(String ipAddress) {
        standardIpAddress = ipAddress;
    }


    public static String getStandardCIDR() {
        return standardCIDR;
    }


    public static void setStandardCIDR(String CIDR) {
        standardCIDR = CIDR;
    }


    public static void setStandardPort(int portNumber) {
        standardPort = portNumber;
    }


    public static String getIpAddress() {
        String ipAddress = "";
        try {
            String rawIpAddress = InetAddress.getLocalHost().toString();

            if (rawIpAddress.contains("/")) {
                ipAddress = rawIpAddress.substring(rawIpAddress.indexOf("/") + 1, rawIpAddress.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}
