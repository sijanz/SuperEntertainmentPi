package Server.Server_Super_Entertainment_Pi;

import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.Backlog.BacklogManager;
import General.GeneralUse.GeneralDate;
import General.Media_Super_Entertainment_Pi.Media_General;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import Server.Database.DatabaseManager;
import Server.ServerGUI.LockHandler;
import Server.ServerGUI.ServerGUI;


/**
 * @author Dustin, Simon
 */
public class Server_Manager {

    private static List<Integer> list_of_upload_ports = new ArrayList<>(); //Example 1

    private static XML_Shell work_file = new XML_Shell(Server_Manager.get_working_directory());


    static List<Integer> get_List_of_used_upload_socket_ports() {

        return list_of_upload_ports;
    }

    public static void main(String[] args) {


        Configure_Network_Setting();

        Server_Socket.set_Server_Socket();

        Media_General.init_media_archives();

        BacklogManager.initializeBacklog();

        DatabaseManager.initializeDatabase();

        // starting the threads...
        new Thread(new ServerGUI()).start();
        new Thread(new LockHandler()).start();


        while (true) {
            try {
                Server_Socket serv_Socket = new Server_Socket();

                // @debug
                System.out.println("Connected");

                while (true) {

                    // @debug
                    System.out.println("Waiting for a client ");

                    System.out.println(serv_Socket.get_ip_address() + " " + serv_Socket.get_portnumber() + "  " + Server_Socket.get_standard_CIDR());

                    Socket connection_to_a_Client = serv_Socket.listen_on_port();

                    try {
                        BacklogManager.writeToBacklogFile("////Connection details ");
                        BacklogManager.writeToBacklogFile("Connected at " + GeneralDate.getDateAndTime());
                        BacklogManager.writeToBacklogFile(
                                "Connected on " + serv_Socket.get_ip_address() + " " + serv_Socket.get_portnumber());
                        BacklogManager
                                .writeToBacklogFile("Connected with pid  " + ManagementFactory.getRuntimeMXBean().getName());


                        new Thread(new Server_Service(connection_to_a_Client)).start();
                    } catch (Exception ex) {
                        connection_to_a_Client.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void Configure_Network_Setting() {
        if ((!(XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Port.toString(), work_file.get_path_of_Configuration_XML_File())))
                &&
                (!(XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Ip_Adress.toString(), work_file.get_path_of_Configuration_XML_File())))
                ) {
            String standard_Host = Server_Socket.getIpAddress();
            ConfigurationFileManager.setInitialServerIpAddress(standard_Host);
            String standard_Port = "6666";
            ConfigurationFileManager.setInitialServerPort(standard_Port);
        }
    }


    private static String get_working_directory() {
        return System.getProperty("user.dir");
    }
}
