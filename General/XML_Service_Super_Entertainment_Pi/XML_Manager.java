/*
 * !!! DANGER ZONE !!!
 */


package General.XML_Service_Super_Entertainment_Pi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @author Dustin
 */
public class XML_Manager {

	/*
	 * Main Nodes
	 */

    public static enum XML_FILE {
        Communication, Index , Configuration, Session_Credentials, Correction_Credentials, Configuration_Index_XML_Listener, Init_Index_Copy, Commitment_File
    }


	/*
	 * Main Nodes
	 */

    public static enum XML_NODES {
        Error, Greetings, MUSIC, AUDIO, VIDEO, PICTURE , UPLOAD, UPLOAD_Video, UPLOAD_Audio, UPLOAD_Picture, COMMAND, CONFIGURATION,  Login , Un_Login, Register, Change_Password, Delete_User,
        Response , Authentication , Session_Credentials, 	Update_Media, Delete_Media,Update_VIDEO, Delete_VIDEO,Update_PICTURE, Delete_PICTURE,Update_MUSIC,Delete_MUSIC,
        Track, Clip, Image, NONE, COMMITMENT, database, Get_Index, Logout
   
    }

	/*
	 * Operation Nodes Nodes
	 */

    public static enum XML_Operation_NODES {

        Update_VIDEO, Delete_VIDEO,Update_PICTURE, Delete_PICTURE,Update_MUSIC,Delete_MUSIC

    }

    public static enum XML_NODES_Media_Types{

        MUSIC, VIDEO, PICTURE
    }


    public static enum XML_NODES_Media_Sub_Types{

        Track, Clip, Image
    }


	/*
	 * Sub nodes
	 */

    public static enum XML_SUB_NODES {

    	    Admin_Registration_progress,
        Commit_LATEST, Commit_None, Current_Index_Version, Server_Message, Server_Public_Key, Admin_Register_Key, Admin_Registration,
        Case, Message,  User_Name, Password, User_Password_Hash, Password_Hash,  Salt_of_User, Token, Track, Upload_File_Size, Upload_Port,
        Delete, Upload, Pause_Video, Exit_Video, Play_Video, Stop_Video, Fastest_Video, Register_Key,
        Faster_Video,Slower_Video,Slowest_Video  , Salt, 	Port , Hostname , IP_Adress, User_New_Password_Hash, User_Public_Key, Public_RSA_Signing_Key,
        Private_RSA_Signing_Key,  Private_RSA_Signing_Key_Modulus , Private_RSA_Signing_Key_Exponent,  Public_RSA_Signing_Key_Modulus , Public_RSA_Signing_Key_Exponent,
        Response_Text, Next_TOKEN , Next_SALT , Hash_of_the_File , Signature_of_the_File, Image , Clip, Play_Music, Play_Picture, Stop_Music,
        Stop_Picture, Pause, Volume_Up, Volume_Down, Rewind, Fast_Forward, NONE, User_Index_Hash, Server_Ip_Adress, Server_Port, Configured_Port , Configured_Ip_Adress,
        Update_ALL, Update_Partial, Server_Message_Session_Key, Server_Message_Hash , Server_Message_Signature, Server_Message_Token, Server_Message_Session_Salt,  Index_XML_Listener_Port,
        password, token, user, name

    }


    public static HashMap<  XML_SUB_NODES, XML_NODES> Media_Sub_Node_Main_Relation;


    static {



        Media_Sub_Node_Main_Relation = new HashMap<>();



        Media_Sub_Node_Main_Relation.put( XML_SUB_NODES.Track, XML_NODES.MUSIC);
        Media_Sub_Node_Main_Relation.put( XML_SUB_NODES.Clip, XML_NODES.VIDEO);
        Media_Sub_Node_Main_Relation.put( XML_SUB_NODES.Image, XML_NODES.PICTURE);

    }



    public static HashMap< XML_NODES, XML_SUB_NODES> Media_Main_Sub_Node_Relation;


    static {



        Media_Main_Sub_Node_Relation = new HashMap<>();



        Media_Main_Sub_Node_Relation.put( XML_NODES.MUSIC, XML_SUB_NODES.Track);
        Media_Main_Sub_Node_Relation.put( XML_NODES.VIDEO, XML_SUB_NODES.Clip);
        Media_Main_Sub_Node_Relation.put( XML_NODES.PICTURE, XML_SUB_NODES.Image);

    }

    public static HashMap<XML_Operation_NODES,  XML_NODES> Media_Operation_Main_Node_Relation;
    static {

        Media_Operation_Main_Node_Relation = new HashMap<>();

        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Update_VIDEO,  XML_NODES.VIDEO);
        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Delete_VIDEO,  XML_NODES.VIDEO);
        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Update_PICTURE,  XML_NODES.PICTURE);
        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Delete_PICTURE,  XML_NODES.PICTURE);
        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Update_MUSIC,  XML_NODES.MUSIC);
        Media_Operation_Main_Node_Relation.put(XML_Operation_NODES.Delete_MUSIC,  XML_NODES.MUSIC);
    }

    public static HashMap<XML_Operation_NODES, XML_NODES> Media_Operation_to_type_Relation;
    static {

        Media_Operation_to_type_Relation = new HashMap<>();

        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Update_VIDEO, XML_NODES.Update_Media);
        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Delete_VIDEO,  XML_NODES.Delete_Media);
        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Update_PICTURE,  XML_NODES.Update_Media);
        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Delete_PICTURE,  XML_NODES.Delete_Media);
        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Update_MUSIC,  XML_NODES.Update_Media);
        Media_Operation_to_type_Relation.put(XML_Operation_NODES.Delete_MUSIC,  XML_NODES.Delete_Media);
    }


    public static XML_NODES create_operation_for_media_type(XML_NODES media, XML_NODES Operation) {

        XML_NODES result = null;

        if (media.equals(XML_NODES.Clip)) {

            if (Operation.equals(XML_NODES.Update_Media)) {

                result = XML_NODES.Update_VIDEO;
            } else {

                result = XML_NODES.Delete_VIDEO;
            }
        } else if (media.equals(XML_NODES.Image)) {

            if (Operation.equals(XML_NODES.Update_Media)) {

                result = XML_NODES.Update_PICTURE;
            } else {

                result = XML_NODES.Delete_PICTURE;
            }
        } else if (media.equals(XML_NODES.Track)) {

            if (Operation.equals(XML_NODES.Update_Media)) {

                result = XML_NODES.Update_MUSIC;
            } else {

                result = XML_NODES.Delete_MUSIC;
            }
        }

        return result;
    }

    public static boolean check_if_node_exist(XML_FILE file, String Node_to_got_changed, String file_path) {
        XML_Shell message_XML = new XML_Shell(file);

        return message_XML.check_if_node_value_exists(Node_to_got_changed, file_path);

    }

    public static void sbubstitute_with_new_Message(XML_FILE file, String Node_to_got_changed, String message, String filepath) {

        XML_Shell message_XML = new XML_Shell(file);

        System.out.println("--> message " + message + " node to change " + Node_to_got_changed);
        message_XML.change_Node_Value(message, Node_to_got_changed, filepath);
    }



    public static void create_Message(XML_FILE XML_file, XML_NODES Main_Node) {
        XML_Shell message_XML = new XML_Shell(XML_file);
        message_XML.add_New_node(Main_Node.toString());
    }

    public static void create_Message(XML_Shell work_file, XML_FILE XML_file, XML_NODES Main_Node) {
        XML_Shell message_XML = new XML_Shell(XML_file);



        System.out.println("###########> " + Main_Node.toString());

        message_XML.set_context(work_file.get_path_of_communication_XML_File());


        message_XML.add_New_node(Main_Node.toString());
    }


    public static void create_Message(XML_NODES Main_Node, String filepath) {
        XML_Shell_Working message_XML = new XML_Shell_Working();
        message_XML.add_New_node(Main_Node.toString(), filepath);
    }

    public static void create_Message(XML_FILE XML_file, XML_NODES Main_Node, String filepath) {

        XML_Shell message_XML = new XML_Shell(XML_file, filepath);


        System.out.println("Main Node " + Main_Node.toString() + "  " + filepath);


        message_XML.add_New_node(Main_Node.toString(), filepath);

    }

    
    public static void create_Message(XML_NODES Main_Node) {
        XML_Shell_Working message_XML = new XML_Shell_Working();
        message_XML.add_New_node(Main_Node.toString());
    }
	

    
    public static void create_Message(XML_Shell work_file, XML_FILE XML_file, XML_NODES Main_Node, String filepath) {
        XML_Shell message_XML = new XML_Shell(XML_file);
        message_XML.set_context(work_file.get_path_of_communication_XML_File());
        message_XML.add_New_node(Main_Node.toString(), filepath);
    }


    public static void create_Message(XML_FILE XML_file, XML_NODES Main_Node, String message, String[] attributes, String filepath) {
        XML_Shell message_XML = new XML_Shell(XML_file);
        message_XML.add_New_node(Main_Node.toString(), message, filepath);
    }


    public static List<String> get_Contents_of_specific_Media_Path(XML_NODES media_type, XML_Shell work_file) {

        List<String> result = new ArrayList<>();

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(work_file.get_path_of_index_XML_File()));

            Element dataTag = document.getDocumentElement();
            NodeList matched_elements = dataTag.getElementsByTagName(media_type.toString());


            for (int index = 0; index < matched_elements.getLength(); index = index + 1) {

                result.add(matched_elements.item(index).getTextContent());

            }


        } catch (Exception ex) {
            // throw own error

        }

        return result;
    }

    /**
     * @author Christian
     */
    public static List<String> get_Contents_of_specific_Media_Path(XML_SUB_NODES media_type, String path) {

        List<String> result = new ArrayList<>();

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(path));

            Element dataTag = document.getDocumentElement();
            NodeList matched_elements = dataTag.getElementsByTagName(media_type.toString());


            for (int index = 0; index < matched_elements.getLength(); index = index + 1) {

                result.add(matched_elements.item(index).getTextContent());

            }


        } catch (Exception ex) {
            // throw own error

        }

        return result;
    }


    public static void append_new_Message(XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String filepath, String message) {
        XML_Shell message_XML = new XML_Shell();

        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message, filepath);


    }


    public static void append_new_Message(XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message, String filepath) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message, filepath);
    }


    public static void append_new_Message(XML_Shell work_file, XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message, String filepath) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.set_context(work_file.get_path_of_communication_XML_File());
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message, filepath);
    }


    public static void append_new_Message(XML_Shell work_file, XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.set_context(work_file.get_path_of_communication_XML_File());
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message);
    }

    public static void append_new_Message(XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message);
    }

    public static void append_new_Message(XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message) {
        XML_Shell message_XML = new XML_Shell();
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message);
    }


    public static void delete_Message(XML_FILE file, XML_SUB_NODES Sub_Node, String message, String filepath) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.subtract_existing_node(Sub_Node.toString(), message, filepath);
    }


    public static void stringToDom(String xmlSource, XML_Shell work_file)
            throws SAXException, ParserConfigurationException, IOException, TransformerException {
        // Parse the given input

        //System.out.println("String to translate " + xmlSource + " " + xmlSource.length() + " ---> " + XML_Shell.get_path_of_index_XML_File());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        byte[] original_XML = Charset.forName("UTF-8").encode(xmlSource).array();
//	    byte[] parse_XML_String  = new byte[original_XML.length  - 1];

        List<Byte> parse_XML_String = new ArrayList<>();

        byte[] temp = null;

        for (int index = 0; index < original_XML.length - 1; index = index + 1) {

            if (original_XML[index] != 0) {

                parse_XML_String.add(original_XML[index]);

                temp = new byte[parse_XML_String.size()];

                for (int itr = 0; itr < temp.length; itr = itr + 1) {

                    temp[itr] = parse_XML_String.get(itr);

                }

            } else {

                index = original_XML.length;
            }

        }


        try {
            InputStream is = new ByteArrayInputStream(temp);
            Document doc = builder.parse(is);

            // Write the parsed document to an xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(work_file.get_path_of_index_XML_File()));
            transformer.transform(source, result);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullPointerException();
        }

    }


    public static void append_new_Message(XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String filepath, String message, String[] attributes) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message, filepath);
    }


    public static void create_Message(XML_FILE file, XML_NODES Main_Node, XML_SUB_NODES Sub_Node, String message,

                                      String[] attributes, String filepath) {
        XML_Shell message_XML = new XML_Shell(file);
        message_XML.add_New_node(Main_Node.toString(), filepath);
        message_XML.add_New_node_to_an_existing_one(Sub_Node.toString(), Main_Node.toString(), message, filepath);
    }


}