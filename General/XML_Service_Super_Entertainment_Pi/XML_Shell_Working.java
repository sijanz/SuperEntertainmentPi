/*
 * !!! DANGER ZONE !!!
 */


package General.XML_Service_Super_Entertainment_Pi;

import General.GeneralUse.GeneralDirectory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import General.GeneralUse.GeneralFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;

public class XML_Shell_Working {


    private static String path_to_XML_Directory = System.getProperty("user.dir") + "/" + "XML_FILES";
    private static String communication_XML_File = "communication.xml";
    private static String path_to_communication_XML_File = path_to_XML_Directory + "/" + communication_XML_File;


    private static String upload_XML_File = "upload.xml";
    private static String path_to_upload_XML_File = path_to_XML_Directory + "/" + upload_XML_File;


    private static String index_XML_File = "index.xml";
    private static String path_to_index_XML_File = path_to_XML_Directory + "/" + index_XML_File;


	/*
     * The init() method will always freshly create the standard communicaton xml
	 * file
	 *
	 */

    public XML_Shell_Working() {

        init();
    }


    public static String get_path_of_index_XML_File() {

        return path_to_index_XML_File;

    }


    static String get_path_of_communication_XML_File() {


        return path_to_communication_XML_File;

    }

    public static String get_path_of_XML_Directory() {

        return path_to_XML_Directory;

    }


    private void init() {

        try {

            if (!(GeneralDirectory.checkIfPathExists(XML_Shell_Working.get_path_of_XML_Directory()))) {
                GeneralDirectory.createPath(XML_Shell_Working.get_path_of_XML_Directory());
            }

            try {

                if (!(GeneralFile.checkIfFileExists(XML_Shell_Working.get_path_of_communication_XML_File()))) {

                    GeneralFile.createFile(XML_Shell_Working.get_path_of_communication_XML_File());
                    create_XML_to_communicate();
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
            document = documentBuilder.parse(new File(path_to_communication_XML_File));

            Element root = get_root_of_Communication_XML_File(document);

            Element new_Node = create_Node(document, New_Node_Name);

            root.appendChild(new_Node);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(path_to_communication_XML_File);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
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

            System.out.println("------> " + filepath);

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
	 *
	 */


    private void create_XML_to_communicate() {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("Message");


            doc.appendChild(rootElement);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path_to_communication_XML_File));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
