/*
 * !!! DANGER ZONE !!!
 */


package General.XML_Service_Super_Entertainment_Pi;

import General.Backlog.BacklogManager;
import General.General_Super_Entertainment_Pi.General_Directory;
import General.General_Super_Entertainment_Pi.General_File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Dustin
 */
public class XML_Shell {

    private String path_to_XML_Directory = "XML_FILES";
    private String communication_XML_File = "communication.xml";
    static String path_to_communication_XML_File = System.getProperty("user.dir") + "/" + "XML_FILES/" + "communication.xml";

    private final String Configuration_XML_File = "configuration.xml";
    private final String index_XML_File = "index.xml";
    final static  String upload_XML_File = "upload.xml";
    private final String Configuration_XML_File_encrypted = "configuration.des";
    private final String session_credentials_XML_File = "session_credentials.xml";
    private final String Configuration_Index_XML_Listener_File = "configuration_index_xml_listener_file.xml";

 
	
	
    static String path_to_upload_XML_File = System.getProperty("user.dir") + "/" + "XML_FILES/" + upload_XML_File;
    
    
    
    private String path_to_session_credentials_XML_File = path_to_XML_Directory + "/" + session_credentials_XML_File;
    private String path_to_index_XML_File = path_to_XML_Directory + "/" + index_XML_File;
    private String path_to_Configuration_XML_File = path_to_XML_Directory + "/" + Configuration_XML_File;
    private String path_to_Index_XML_Listener_File = path_to_XML_Directory + "/" + Configuration_Index_XML_Listener_File;
    private String path_to_Configuration_XML_File_encrypted = path_to_XML_Directory + "/" + Configuration_XML_File_encrypted;

    private final   String path_to_index_xml_copy = path_to_XML_Directory  + "/index_copy.xml";
    private final   String path_to_original_index_xml = path_to_XML_Directory  + "/index.xml";


    public XML_Shell() {

        init_Configuration();
    }


    public  XML_Shell(XML_Manager.XML_FILE file,  String correction_xml) {



        System.out.println("------------------------> häää " + file.toString());



        switch(file.toString()) {

            case "Correction_Credentials":

                init_Correction_Credentials(correction_xml);
                break;

            case "Init_Index_Copy":

                init_index_copy(correction_xml);
                break;

            case "Commitment_File":

                init_Commitment_File(correction_xml);
                break;
        }


    }


    public XML_Shell(String path_to_working_directory) {

        String path_to_working_directory1 = path_to_working_directory;

        this.path_to_upload_XML_File = path_to_working_directory1 + "/" + upload_XML_File;

        this.path_to_session_credentials_XML_File = path_to_XML_Directory + "/" + session_credentials_XML_File;

        this.path_to_index_XML_File = path_to_XML_Directory + "/" + index_XML_File;

        this.path_to_Configuration_XML_File = path_to_XML_Directory + "/" + Configuration_XML_File;

        this.path_to_Configuration_XML_File_encrypted = path_to_XML_Directory + "/" + Configuration_XML_File_encrypted;

        this.path_to_XML_Directory = path_to_working_directory1 + "/" + "XML_FILES";

        this.path_to_communication_XML_File = this.path_to_XML_Directory + "/" + communication_XML_File;

    }


    // Christian
    public static String get_path_to_MusicDirectory() {
        return "XML_FILES/music";
    }

    public static String get_path_to_VideoDirectory() {
        return "XML_FILES/video";
    }

    public static String get_path_to_PictureDirectory() {
        return "XML_FILES/picture";
    }


    public  String get_path_to_index_xml_copy() {
        return  path_to_index_xml_copy;
    }


    public static String get_path_of_commit_index_XML_File() {

        return System.getProperty("user.dir") + "/XML_FILES/commit_index_xml_listener_file.xml";
    }

    public String get_path_of_Configuration_Lock_Directory() {
        return System.getProperty("user.dir") + "/Configuration_Lock_File/";
    }

    public String get_path_of_Configuration_Lock() {
        return get_path_of_Configuration_Lock_Directory() + "config.lock";
    }

    
    public static String get_path_of_upload_XML_File() {
        
        return  path_to_upload_XML_File;
}


    public void set_context(String new_context_path) {
        this.path_to_communication_XML_File = new_context_path;

        this.init_Communication();

    }


    public XML_Shell(XML_Manager.XML_FILE file, String path_to_correction_xml, String correction_xml) {
        switch (file.toString()) {

            case "Correction_Credentials":

                init_Correction_Credentials(path_to_correction_xml, correction_xml);
                break;
        }

    }



		/*
		 * The init() method will always freshly create the standard communicaton xml
		 * file
		 *
		 * /*
		 * Edit we will also handle
		 * the configurtion file
		 *
		 * */

    public XML_Shell(XML_Manager.XML_FILE file) {

        switch (file.toString()) {


            case "Session_Credentials":

                init_Session_Credentials();
                break;


            case "Configuration":

                init_Configuration();
                break;


            case "Communication":

                init_Communication();
                break;

            case "Configuration_Index_XML_Listener":

                init_Index_XML_Listener_Configuration();
                break;

            case "Index":
                System.out.println("Index !!!!!!!");
                //	init_Communication();
                break;
        }


    }


    private void init_Correction_Credentials(String correction_xml)
    {

        try {

            General_File.create_file(correction_xml);
            create_XML_File("Configuration_Index_XML_Update_File", correction_xml);

        }
        catch (IOException ex) {

            ex.printStackTrace();
        }

    }



    private void init_Commitment_File(String correction_xml) {


        try {

            General_File.create_file(correction_xml);
            create_XML_File("Commitment_File", correction_xml);

        }
        catch (IOException ex) {

            ex.printStackTrace();
        }

    }

    private void init_index_copy(String correction_xml)
    {
        try {

            General_File.create_file(correction_xml);
            create_XML_File("Index_XML_Copy_File", correction_xml);

        }
        catch (IOException ex) {

            ex.printStackTrace();
        }
    }



    public String get_path_of_Configuration_XML_File_encrypted() {

        return path_to_Configuration_XML_File_encrypted;
    }


    public String get_path_of_session_Credentials_XML_File() {

        return path_to_session_credentials_XML_File;
    }


    public static String get_path_of_communication_XML_File() {
		
		return path_to_communication_XML_File;
		
	}

    
    
    public String get_path_of_index_XML_File() {

        return path_to_index_XML_File;

    }


 

    public String get_path_of_Configuration_Index_XML_Listener_File() {

        return this.path_to_Index_XML_Listener_File;

    }


    public String get_path_of_XML_Directory() {

        return path_to_XML_Directory;

    }

    public String get_path_of_Configuration_XML_File() {


        return path_to_Configuration_XML_File;

    }

    private void init_Correction_Credentials(String path_to_correction_xml, String correction_xml) {

        try {

            General_Directory.create_path(path_to_correction_xml);

            try {

                General_File.create_file(path_to_correction_xml + "/" + correction_xml);

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
            // go on,,, thorw exceptiono with error message
        }
    }

    private void init_Index_XML_Listener_Configuration() {

        try {

            if (!(General_Directory.check_if_path_exists(this.get_path_of_XML_Directory()))) {
                General_Directory.create_path(this.get_path_of_XML_Directory());
            }

            try {

                if (!(General_File.check_if_file_exists(this.get_path_of_Configuration_Index_XML_Listener_File()))) {

                    General_File.create_file(this.get_path_of_Configuration_Index_XML_Listener_File());
                    create_XML_File("Configuration_Index_XML_Listener_File", this.get_path_of_Configuration_Index_XML_Listener_File());
                }

            } catch (IOException ex) {


                ex.printStackTrace();

                // cannot create file
            }

        } catch (SecurityException ex) {


            ex.printStackTrace();
            // go on,,, thorw exceptiono with error message
        }

    }


    private void init_Configuration() {

        try {

            if (!(General_Directory.check_if_path_exists(this.get_path_of_XML_Directory()))) {
                General_Directory.create_path(this.get_path_of_XML_Directory());
            }

            try {

                if (!(General_File.check_if_file_exists(this.get_path_of_Configuration_XML_File()))) {


                    System.out.println("Create Configuration XML");

                    General_File.create_file(this.get_path_of_Configuration_XML_File());
                    create_XML_File("Configuration_File", this.get_path_of_Configuration_XML_File());
                }

            } catch (IOException ex) {


                ex.printStackTrace();

                // cannot create file
            }

        } catch (SecurityException ex) {


            ex.printStackTrace();
            // go on,,, thorw exceptiono with error message
        }

    }

    private void init_Session_Credentials() {

        try {

            if (!(General_Directory.check_if_path_exists(this.get_path_of_XML_Directory()))) {
                General_Directory.create_path(this.get_path_of_XML_Directory());
            }

            try {
                if (!(General_File.check_if_file_exists(this.get_path_of_session_Credentials_XML_File()))) {


                    System.out.println("Session_Credentials XML");

                    General_File.create_file(this.get_path_of_session_Credentials_XML_File());
                    create_XML_File("Message", this.get_path_of_session_Credentials_XML_File());
                }

            } catch (IOException ex) {


                ex.printStackTrace();

                // cannot create file
            }

        } catch (SecurityException ex) {


            ex.printStackTrace();
            // go on,,, thorw exceptiono with error message
        }
    }

    private void init_Communication() {

        try {

            if (!(General_Directory.check_if_path_exists(this.get_path_of_XML_Directory()))) {
                General_Directory.create_path(this.get_path_of_XML_Directory());
            }

            try {
                if (!(General_File.check_if_file_exists(this.get_path_of_communication_XML_File()))) {
                    General_File.create_file(this.get_path_of_communication_XML_File());
                    create_XML_File("Message", this.get_path_of_communication_XML_File());
                }

            } catch (IOException ex) {


                ex.printStackTrace();

                // cannot create file
            }

        } catch (SecurityException ex) {


            ex.printStackTrace();
            // go on,,, thorw exceptiono with error message
        }

    }


    public Boolean check_if_node_value_exists(String Node_to_got_changed, String file_path) {


        Boolean equality_flag = false;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        Document dom;
        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(file_path);

            Element docEle = dom.getDocumentElement();
            NodeList nl = docEle.getElementsByTagName(Node_to_got_changed);


            if (nl != null) {
                int length = nl.getLength();
                for (int i = 0; i < length; i++) {
                    System.out.println("--->" + nl.item(i).getTextContent());
                    System.out.println(nl.item(i).getNodeName() + " == " + Node_to_got_changed);
                    if (Objects.equals(nl.item(i).getNodeName(), Node_to_got_changed)) {

                        equality_flag = true;
                        i = length;
                    }
                }
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }


        return equality_flag;
    }

    public void change_Node_Value(String message, String Node_to_got_changed, String file_path) {
        System.out.println("hä " + message + " " + Node_to_got_changed);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            Document dom;
            db = dbf.newDocumentBuilder();
            dom = db.parse(file_path);

            Element docEle = dom.getDocumentElement();
            NodeList nl = docEle.getElementsByTagName(Node_to_got_changed);


            if (nl != null) {
                int length = nl.getLength();
                for (int i = 0; i < length; i++) {
                    System.out.println("--->" + nl.item(i).getTextContent());
                    System.out.println(nl.item(i).getNodeName() + " == " + Node_to_got_changed);
                    if (Objects.equals(nl.item(i).getNodeName(), Node_to_got_changed)) {
                        Element el = (Element) nl.item(i);

                        System.out.println(Node_to_got_changed + "  " + message);

                        el.setTextContent(message);

                    }
                }
            }


            DOMSource source = new DOMSource(dom);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file_path);
            transformer.transform(source, result);

            System.out.println("Done");


        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e1) {
            e1.printStackTrace();
        }
    }


    private Element get_root_of_Communication_XML_File(Document document) {
        Element root;

        root = document.getDocumentElement();

        return root;
    }

    private Element create_Node(Document document, String Node_to_create) {
        Element newNode;

        newNode = document.createElement(Node_to_create);

        return newNode;

    }

    private Element create_Node(Document document, String Node_to_create, String message) {
        Element newNode;

        newNode = document.createElement(Node_to_create);
        newNode.setTextContent(message);

        return newNode;
    }


    private String check_if_node_name_exist_and_correct(String message, NodeList nodes) {

        int itr = 0;
        for (int index = 0; index < nodes.getLength(); index = index + 1) {


            if (nodes.item(index).getTextContent().contains(message)) {

                itr = itr + 1;
                // System.out.println("-------> " + nodes.item(index).getTextContent() + " itr " + itr);
            }
            if (nodes.item(index).getTextContent().equals(message)) {

				 /*
				  * Example :
				  *
				  * exmaple.mp4 => [0] example
				  * 			   =>  [1] mp4
				  * */

                String[] parts = message.split(".");

                message = parts[0] + itr + "." + parts[1];
                index = nodes.getLength();
            }

        }

        return message;

    }

	 /*
		 * Adds node to root node
		 *
		 * @param New_Node_Name: Name of the Node, that shall be created
		 *
		 * The New Node will be created with the given contents.
		 *
		 * After this, the new Node will be added at the root node
		 *
		 * @Exceptions: will be handled with an own error class
		 */

    public List<String> get_child_nodes(String Parent_Node_Name, String filepath) {

        List<String> resulted_nodes = new ArrayList<>();


        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(filepath));

            NodeList elements_matched_with_media_type = document.getElementsByTagName(Parent_Node_Name);

            for (int itr = 0; itr < elements_matched_with_media_type.getLength(); itr = itr + 1) {

                resulted_nodes.add(elements_matched_with_media_type.item(itr).getTextContent().toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resulted_nodes;
    }

	/*
	 * Adds node to root node
	 *
	 * @param New_Node_Name: Name of the Node, that shall be created
	 *
	 * The New Node will be created with the given contents.
	 *
	 * After this, the new Node will be added at the root node
	 *
	 * @Exceptions: will be handled with an own error class
	 */

    public void add_New_node(String New_Node_Name, String filepath) {

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(filepath));

            Element root = get_root_of_Communication_XML_File(document);

            Element new_Node = create_Node(document, New_Node_Name);

            root.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filepath);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/*
     * Adds node to an existing one
     *
     * @param New_Node_Name: Name of the Node, that shall be created
     *
     * @param Name_of_the_existing_node: Name of the Node, that shall be added to
     *
     * @param message: textual content of the Node
     *
     * The New Node will be created with the given contents.
     *
     * After this, the new Node will be added at the root node
     *
     * @Exceptions: will be handled with an own error class Shall handle null
     * pointer exception, if it will not find that not, that shall exist
     *
     */

    public void add_New_node_to_an_existing_one(String New_Node_Name, String Name_of_the_exisiting_Node,
                                                String message) {

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(path_to_communication_XML_File));

            Element dataTag = document.getDocumentElement();
            Element existing_Element = (Element) dataTag.getElementsByTagName(Name_of_the_exisiting_Node).item(0);

            Element new_Node = create_Node(document, New_Node_Name, message);

            existing_Element.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();


            StreamResult result = new StreamResult(path_to_communication_XML_File);
            transformer.transform(source, result);
        } catch (Exception ex) {
            // throw own error

        }

    }

    /*
     * Adds node to root node
     *
     * @param New_Node_Name: Name of the Node, that shall be created
     *
     * The New Node will be created with the given contents.
     *
     * After this, the new Node will be added at the root node
     *
     * @Exceptions: will be handled with an own error class
     */

    public void add_New_node(String New_Node_Name) {

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;

            System.out.println("hä ist nicht temp " + this.get_path_of_communication_XML_File() + " " + General_Directory.check_if_path_exists(this.get_path_of_communication_XML_File()));

            document = documentBuilder.parse(new File(this.get_path_of_communication_XML_File()));

            Element root = get_root_of_Communication_XML_File(document);
            System.out.println(General_File.return_content_of_file(this.get_path_of_communication_XML_File()));

            Element new_Node = create_Node(document, New_Node_Name);

            root.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(this.get_path_of_communication_XML_File());
            transformer.transform(source, result);

            System.out.println(General_File.return_content_of_file(this.get_path_of_communication_XML_File()));

        } catch (Exception e) {
            System.out.println("exception " + this.get_path_of_communication_XML_File() + " " + General_Directory.check_if_path_exists(this.get_path_of_communication_XML_File()));
        }

    }


	/*
	 * Adds node to root node
	 *
	 * @param New_Node_Name: Name of the Node, that shall be created
	 *
	 * @param message: textual content of the Node
	 *
	 * The New Node will be created with the given contents.
	 *
	 * After this, the new Node will be added at the root node
	 *
	 * @Exceptions: will be handled with an own error class
	 *
	 *
	 */

    public void add_New_node(String New_Node_Name, String message, String filepath) {

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            System.out.println("filepath " + filepath);
            document = documentBuilder.parse(new File(filepath));

            Element root = get_root_of_Communication_XML_File(document);

            Element new_Node = create_Node(document, New_Node_Name, message);

            root.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filepath);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/*
	 * Adds node to an existing one
	 *
	 * @param New_Node_Name: Name of the Node, that shall be created
	 *
	 * @param Name_of_the_existing_node: Name of the Node, that shall be added to
	 *
	 * @param index: if there are several nodes, this will identify the specific
	 * one, that shall be added to
	 *
	 * @param message: textual content of the Node
	 *
	 * The New Node will be created with the given contents.
	 *
	 * After this, the new Node will be added at the root node
	 *
	 * @Exceptions: will be handled with an own error class Shall handle null
	 * pointer exception, if it will not find that not, that shall exist
	 *
	 */

    public void add_New_node_to_an_existing_one(String New_Node_Name, String Name_of_the_exisiting_Node, int index,
                                                String message, String filepath) {

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(filepath));

            Element dataTag = document.getDocumentElement();
            Element existing_Element = (Element) dataTag.getElementsByTagName(Name_of_the_exisiting_Node).item(index);

            Element new_Node = create_Node(document, New_Node_Name, message);

            existing_Element.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filepath);
            transformer.transform(source, result);
        } catch (Exception ex) {
            // throw own error

        }

    }


    public void subtract_existing_node(String Name_of_the_node_that_will_be_deleted,

                                       String message_that_will_be_deleted, String filepath) {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            dbf.setValidating(false);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new FileInputStream(new File(filepath)));

            NodeList elements = doc.getElementsByTagName(Name_of_the_node_that_will_be_deleted);


            if (elements != null) {
                int length = elements.getLength();
                for (int i = 0; i < length; i++) {

                    if (Objects.equals(elements.item(i).getNodeName(), Name_of_the_node_that_will_be_deleted)) {

                        if (elements.item(i).getTextContent().equals(message_that_will_be_deleted)) {

                            elements.item(i).getParentNode().removeChild(elements.item(i));


                            doc.normalize();
                            return;
                        }


                    }
                }
            }


        } catch (ParserConfigurationException | SAXException | IOException ex) {
            BacklogManager.writeToBacklogFile("///// Cannot delete Node " + Name_of_the_node_that_will_be_deleted + " : " + message_that_will_be_deleted);
        }

    }




	/*
	 * Adds node to an existing one
	 *
	 * @param New_Node_Name: Name of the Node, that shall be created
	 *
	 * @param Name_of_the_existing_node: Name of the Node, that shall be added to
	 *
	 * @param message: textual content of the Node
	 *
	 * The New Node will be created with the given contents.
	 *
	 * After this, the new Node will be added at the root node
	 *
	 * @Exceptions: will be handled with an own error class Shall handle null
	 * pointer exception, if it will not find that not, that shall exist
	 *
	 */

    public void add_New_node_to_an_existing_one(String New_Node_Name, String Name_of_the_exisiting_Node,
                                                String message, String filepath) {

        try {

            System.out.println("#+#+#+#+#+#> " + filepath + " New_Node_Name  " + New_Node_Name + "  Name_of_the_exisiting_Node " + Name_of_the_exisiting_Node + "  message " + message);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(new File(filepath));

            Element dataTag = document.getDocumentElement();
			
			/*
			 * IT is possible that a user chooses a file, that already exist, / got the sam ename, so
			 * we will coreect this 
			 * */


            String message_corrected = check_if_node_name_exist_and_correct(message, dataTag.getElementsByTagName(Name_of_the_exisiting_Node));


            Element existing_Element = (Element) dataTag.getElementsByTagName(Name_of_the_exisiting_Node).item(0);

            Element new_Node = create_Node(document, New_Node_Name, message_corrected);

            existing_Element.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(filepath);
            transformer.transform(source, result);
        } catch (Exception ex) {
            // throw own error

        }

    }

	/*
	 * The communication between the clients and the server shall be in XML Format.
	 * 
	 * The function crates the standrd communications XML-File, will be added with
	 * nodes, that contains the information, that shall be exchanged
	 * 
	 * The standard file looks like:
	 * 
	 * <?xml version = "1.0" encoding = "UTF-8" standalone = "no"?> <Message>
	 * </Message>
	 * 
	 * 
	 * The specific communication, shall be designed with additional nodes, that can
	 * be parsed, by the recevier.
	 * 
	 * @edit: This Function can now create every kind of needed XMl files.......
	 */


    private void create_XML_File(String name_of_root_node, String path_of_file) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement(name_of_root_node);


            doc.appendChild(rootElement);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path_of_file));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}