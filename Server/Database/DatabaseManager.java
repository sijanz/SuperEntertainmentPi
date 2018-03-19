package Server.Database;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.security.SecureRandom;


/**
 * Manages the database accesses and the database as a .xml-file itself.
 *
 * @author Simon
 */
public class DatabaseManager {


    /**
     * Initializes the database with the root node if the database isn't existing.
     */
    public static void initializeDatabase() {
        if (!databaseExists()) {
            try {

                // create file
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // create root element
                Element rootElement = doc.createElement("users");
                doc.appendChild(rootElement);

                // write changes to database
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/database.xml"));
                transformer.transform(source, result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Helper method to determine if the database exists.
     *
     * @return true if database exists, false otherwise
     */
    private static boolean databaseExists() {
        File f = new File("database.xml");
        return (f.exists() && !f.isDirectory());
    }


    /**
     * Creates a new user in the database.
     *
     * @param username the name of the new user
     * @param password the password of the new user
     */
    public boolean createUser(String username, String password) {
        if (!userExists(username)) {
            try {

                // read in database
                File inputFile = new File("database.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);

                Node users = doc.getFirstChild();

                // create user node
                Element user = doc.createElement("user");
                users.appendChild(user);

                // set username
                Attr attr = doc.createAttribute("name");
                attr.setValue(username);
                user.setAttributeNode(attr);

                // set password
                Element passwordElement = doc.createElement("password");
                passwordElement.appendChild(doc.createTextNode(password));
                user.appendChild(passwordElement);

                // set token value to null
                Element tokenElement = doc.createElement("token");
                tokenElement.appendChild(doc.createTextNode(""));
                user.appendChild(tokenElement);

                // write changes to database
                DOMSource source = new DOMSource(doc);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                StreamResult result = new StreamResult("database.xml");
                transformer.transform(source, result);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * Checks if a given user exists in the database.
     *
     * @param username the user to check
     * @return true if user exists, false otherwise
     */
    private boolean userExists(String username) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Logs in the user by setting a token.
     *
     * @param username the name of the user to log in
     * @param password the password of the user to log in
     * @return true if success, false otherwise
     */
    public boolean loginUser(String username, String password) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // check if the given password is correct
                        if (element.getElementsByTagName("password").item(0).getTextContent().equals(password)) {

                            // set token
                            element.getElementsByTagName("token").item(0).setTextContent(generateToken());

                            // write changes to database
                            DOMSource source = new DOMSource(doc);
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            StreamResult result = new StreamResult("database.xml");
                            transformer.transform(source, result);
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Generates and returns a token.
     *
     * @return the token value
     */
    private static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }


    /**
     * Logs out the user by deleting the token.
     *
     * @param username the name of the user to log out
     */
    public void logoutUser(String username) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // delete token
                        element.getElementsByTagName("token").item(0).setTextContent("");

                        // write changes to database
                        DOMSource source = new DOMSource(doc);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        StreamResult result = new StreamResult("database.xml");
                        transformer.transform(source, result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the token of a given user.
     *
     * @param username the user to get the token of
     * @return the token value
     */
    public String getToken(String username) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // return token
                        return element.getElementsByTagName("token").item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Changes the password of a given user.
     *
     * @param username    the name of the user
     * @param newPassword the new password
     */
    public void changePassword(String username, String newPassword) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // set new password
                        element.getElementsByTagName("password").item(0).setTextContent(newPassword);

                        // write changes to database
                        DOMSource source = new DOMSource(doc);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        StreamResult result = new StreamResult("database.xml");
                        transformer.transform(source, result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Authenticates a user by comparing the token value.
     *
     * @param username the name of the user to authenticate
     * @param token    the token value to compare
     * @return true if token value matches, false otherwise
     */
    public boolean authenticateUser(String username, String token) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // compare token value
                        if (element.getElementsByTagName("token").item(0).getTextContent().equals(token)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Deletes a user from the database.
     *
     * @param username the name of the user to delete
     */
    public void deleteUser(String username) {
        try {

            // read in database
            File inputFile = new File("database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // needed as a reference to delete user-node
            Node users = doc.getFirstChild();

            // search for user
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (element.getAttribute("name").equals(username)) {

                        // remove user
                        users.removeChild(node);

                        // write changes to database
                        DOMSource source = new DOMSource(doc);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        StreamResult result = new StreamResult("database.xml");
                        transformer.transform(source, result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
