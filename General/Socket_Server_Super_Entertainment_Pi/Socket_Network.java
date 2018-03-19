package General.Socket_Server_Super_Entertainment_Pi;


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

import General.BacklogManagement.BacklogManager;


/**
 * This Class does contain, everything to handle, create 
 * the properties, that the socket needs, to be created, or
 *
 * @author Dustin, Simon
 */
public class Socket_Network extends Socket {

    private int portnumber = 6700;
    private String ip_address = "";
    private String Subnet_in_CIDR = "";

    /*
     * Methods to init a socket, with its standard properties like ip, port number
	 * and CIDR
	 */
    private static int standard_port = 6666;
    private static String standard_CIDR = "24";
    private static String standard_ip_address = "serversuperentertainmentpi.local";

    // hostname of the server
    private static String hostname = "serversuperentertainmentpi.local";


	/*
     * This function returns all combinations of ipv4 addresses with CIDR of every
	 * interface that supports ipv4
	 */
    protected Socket_Network() {

        this.portnumber = standard_port;
        this.ip_address = standard_ip_address;
        this.Subnet_in_CIDR = standard_CIDR;

    }


    // gets the port number of the initialized object
    public int get_portnumber() {
        return this.portnumber;
    }


    // gets the ipv4 Adress with subnet
    protected static HashMap<String, Short> get_ip_with_CIDR() {
        HashMap<String, Short> ip_adress_with_Subnet = new HashMap<>();
        try {
            InetAddress localHost = Inet4Address.getLocalHost();

            // @debug
            System.out.println(localHost.getHostName() + " " + localHost.getHostAddress());

            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {

                // We will just search for ipv4 adresses
                if (address.getAddress() instanceof Inet4Address) {
                    ip_adress_with_Subnet.put(address.getAddress().getHostAddress(),
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
        return ip_adress_with_Subnet;
    }


    // resolves ip adress from hostname
    public static String resolve_ip_address_from_hostname() throws UnknownHostException {
        InetAddress address = InetAddress.getByName(get_hostname());
        return address.getHostAddress();
    }


    // resolves the CIDR-Subnet-Mask
    // gets the hostname, that was initialized in the object
    public static String get_hostname() {
        return Socket_Network.hostname;
    }


    // sets the hostname of the initiated object
    public static void set_hostname(String hostname) {
        Socket_Network.hostname = hostname;
    }

    // gets the ip adress, that was initialized in the object

    public String get_ip_address() {
        return this.ip_address;
    }


    public static InetAddress get_ip_address_in_Inet_format(String ip_address) throws UnknownHostException {
        return InetAddress.getByName(ip_address);
    }


	/*
	 * Method to generate a random Port-Number 
	 * 7000 - 470000
	 * 
	 * */
    public static int determine_port_number(int MAX_PORT_NUMBER, int MIN_PORT_NUMBER, int bound_PORT) {
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
	 * Methods to set socket with standard properties like ip , portnumber and CIDR
	 */
    public static void set_standard_ip_address(String ip_address) {
        standard_ip_address = ip_address;
    }


    public static String get_standard_CIDR() {
        return standard_CIDR;
    }


    public static void set_standard_CIDR(String CIDR) {
        standard_CIDR = CIDR;
    }


    public static int get_standard_port() {
        return standard_port;
    }


    public static void set_standard_port(int port_number) {
        standard_port = port_number;
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
