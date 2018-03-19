package Client.ConfigurationFileManager;


import Client.ClientManagement.ClientManager;
import General.General_Super_Entertainment_Pi.General_File;
import General.General_Super_Entertainment_Pi.General_Purpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;


/**
 * @author Simon, Dustin
 */
public class ConfigurationFileManager {

    private static XML_Shell workFile = new XML_Shell(ClientManager.getWorkingDirectory());


    public static void setConfiguredIpAddress(String ipAddress) {
        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), workFile.get_path_of_Configuration_XML_File())) {
            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), ipAddress, workFile.get_path_of_Configuration_XML_File());
        } else {
            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, workFile.get_path_of_Configuration_XML_File());
            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Ip_Adress, ipAddress, workFile.get_path_of_Configuration_XML_File());
        }
    }


    public static String getServerPort() {
        return General_Purpose.get_Content_of_Tag_as_String(XML_SUB_NODES.Server_Port.toString(), General_File.return_content_of_file(workFile.get_path_of_Configuration_XML_File()));
    }


    public static void setInitialServerPort(String port) {
        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Port.toString(), workFile.get_path_of_Configuration_XML_File())) {
            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Port.toString(), port, workFile.get_path_of_Configuration_XML_File());
        } else {
            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, workFile.get_path_of_Configuration_XML_File());
            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Port, port, workFile.get_path_of_Configuration_XML_File());
        }
    }


    public static void setInitialServerIpAddress(String ipAddress) {
        if (XML_Manager.check_if_node_exist(XML_FILE.Configuration, XML_Manager.XML_SUB_NODES.Server_Ip_Adress.toString(), workFile.get_path_of_Configuration_XML_File())) {
            XML_Manager.sbubstitute_with_new_Message(XML_FILE.Configuration, XML_SUB_NODES.Server_Ip_Adress.toString(), ipAddress, workFile.get_path_of_Configuration_XML_File());
        } else {
            XML_Manager.create_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, workFile.get_path_of_Configuration_XML_File());
            XML_Manager.append_new_Message(XML_FILE.Configuration, XML_NODES.CONFIGURATION, XML_SUB_NODES.Server_Ip_Adress, ipAddress, workFile.get_path_of_Configuration_XML_File());
        }
    }


    public static String getServerIpAddress() {
        return General_Purpose.get_Content_of_Tag_as_String(XML_SUB_NODES.Server_Ip_Adress.toString(), General_File.return_content_of_file(workFile.get_path_of_Configuration_XML_File()));
    }
}
