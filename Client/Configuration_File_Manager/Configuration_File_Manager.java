package Client.Configuration_File_Manager;


import Client.ClientSuperEntertainmentPi.ClientManager;
import General.General_Super_Entertainment_Pi.General_File;
import General.General_Super_Entertainment_Pi.General_Purpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;


/**
 * @author Dustin
 */
public class Configuration_File_Manager {

    private static XML_Shell work_file = null;

    static {

        work_file = new XML_Shell(ClientManager.getWorkingDirectory());
    }


    public static void set_Configured_Ip_Adress(String IP_Adress) {


        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), work_file.get_path_of_Configuration_XML_File())) {
            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), IP_Adress, work_file.get_path_of_Configuration_XML_File());
        } else {

            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, work_file.get_path_of_Configuration_XML_File());
            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Ip_Adress, IP_Adress, work_file.get_path_of_Configuration_XML_File());

        }
    }


    public static String get_Server_Port() {

        return General_Purpose.get_Content_of_Tag_as_String(XML_SUB_NODES.Server_Port.toString(), General_File.return_content_of_file(work_file.get_path_of_Configuration_XML_File()));
    }


    public static void set_initial_Server_Port(String Port) {

        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Port.toString(), work_file.get_path_of_Configuration_XML_File())) {

            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Port.toString(), Port, work_file.get_path_of_Configuration_XML_File());
        } else {

            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, work_file.get_path_of_Configuration_XML_File());

            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Port, Port, work_file.get_path_of_Configuration_XML_File());

        }

    }


    public static void set_initial_Server_Ip_Adress(String IP_Adress) {
        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Ip_Adress.toString(), work_file.get_path_of_Configuration_XML_File())) {

            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), IP_Adress, work_file.get_path_of_Configuration_XML_File());
        } else {

            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, work_file.get_path_of_Configuration_XML_File());

            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Ip_Adress, IP_Adress, work_file.get_path_of_Configuration_XML_File());

        }
    }


    public static String get_Server_Ip_address() {

        return General_Purpose.get_Content_of_Tag_as_String(XML_SUB_NODES.Server_Ip_Adress.toString(), General_File.return_content_of_file(work_file.get_path_of_Configuration_XML_File()));
    }
}
