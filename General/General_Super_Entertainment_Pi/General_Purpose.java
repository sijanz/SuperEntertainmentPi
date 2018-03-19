package General.General_Super_Entertainment_Pi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import General.Backlog.BacklogManager;
import General.Media_Super_Entertainment_Pi.Media_General;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_FILE;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;


/**
 * @author
 */
public class General_Purpose {


    private static String user_name;

    public enum Error_Types {
        Invalid_Key, User_Name_is_used, Invalid_Salt, User_is_logged_in,
        User_does_not_exist, Password_Hash_incorrect, User_is_logged_out,
        Hash_is_incorrect, Cannot_Create_Register_Key, Cannot_Create_Admin_Register_Key,
        Cannot_Create_Admin_Key, Cannot_Register, Not_Permitted_Action, Invalid_Admin_Key
    }

    public enum Change_Types {

        Update_Elements, Delete_Elements
    }

    public enum Commit_Types {

        UpdateElement, DeleteElement
    }

    public enum Commit_Change_States {

        in_progress, done
    }


	/*
     *
	 * @filename: name of a file, but not its path : -> example.mp4
	 *
	 * */

    public static String build_path_to_file(String filename) {
        List<String> paths_to_serach = Media_General.get_media_paths();
        List<String> files_of_specific_path;

        for (String current_path : paths_to_serach) {
            files_of_specific_path = General_Directory.get_all_Files_from_path(current_path);

            for (String current_file : files_of_specific_path) {
                System.out.println(current_file + " == " + filename);
                if (filename.equals(current_file)) {
                    return current_path + "/" + current_file;
                }
            }
        }
        return "<NO_PATH>";
    }

    public static List<String> create_dataset(String work_directory) {

        return General_Directory.get_all_Files_from_path(work_directory);
    }


    public static void generate_Lock(String lock_file, String lock_path) {
        if (!(General_Directory.check_if_path_exists(lock_path))) {
            General_Directory.create_path(lock_path);
        }

        String path_of_lock = lock_path + "/" + lock_file;

        System.out.println("path to lock " + path_of_lock);

        try {
            General_File.create_file(path_of_lock);

        } catch (IOException e) {
            BacklogManager.writeToBacklogFile("//// Cannot build " + path_of_lock + "  " + General_Date.get_Date_and_Time());
            e.printStackTrace();
        }
    }


    public static Commit_Types determine_commit_type(Change_Types demanded_change) {
        List<Change_Types> change_types_List = new ArrayList<>();
        List<Commit_Types> commit_types_List = new ArrayList<>();

        Collections.addAll(change_types_List, Change_Types.values());
        Collections.addAll(commit_types_List, Commit_Types.values());

        return commit_types_List.get(change_types_List.indexOf(demanded_change));
    }


	/*
     * @params: Tag_to_filter : <Name_of_tag_that_gets_filtered>
	 *
	 * */
    public static String get_Content_of_Tag_as_String(String Tag_to_filter, String message) {

        String temp = message.replace("<" + Tag_to_filter + ">", "§");
        String temp_final = temp.replace("</" + Tag_to_filter + ">", "§");

        String[] parts = temp_final.split("§");
        String result = "";

        try {

            result = parts[1];
        } catch (IndexOutOfBoundsException ex) {

            result = "";
        }


        return result;
    }


	/*
     *
	 * @params: Tag_to_filter : <Name_of_tag_that_gets_filtered>
	 *
	 * */
    public static List<String> get_Content_of_Tag_as_String(String Tag, String Tag_to_filter, String Tag_Not_to_filter, String message) {

        List<String> result = new ArrayList<>();

        String temp = message.replace("<" + Tag_to_filter + ">", "X");
        String temp_final = temp.replace("</" + Tag_to_filter + ">", "X");


        String[] parts = temp_final.split("X");

        int ctr = 1;
        for (int index = 1; index <= (parts.length / 3); index = index + 1) {


            temp = parts[ctr].replace("<" + Tag + ">", "X");
            temp_final = temp.replace("</" + Tag + ">", "X");


            temp = temp_final.replace("<" + Tag_Not_to_filter + ">", "§");
            temp_final = temp.replace("</" + Tag_Not_to_filter + ">", "§");


            String[] tmp_result = temp_final.split("X");


            for (String aTmp_result : tmp_result) {


                if (!(aTmp_result.contains("§")) && (aTmp_result.length() > 0)) {

                    result.add(aTmp_result);

                }
            }
            ctr = ctr + 3;
        }
        return result;
    }


    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();


    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static void do_send_not_authenticated_response_to_client(Socket socket_of_client, String Response_to_client, XML_Shell work_file, String user_name) {


        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }

        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Response, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(XML_FILE.Communication, XML_NODES.Response, XML_SUB_NODES.Response_Text, Response_to_client, XML_Shell.get_path_of_communication_XML_File());
    }


    public static void do_send_response_to_client_with_next_session_credentials(Socket socket_of_client, String User_Name, String Response_to_client, XML_Shell work_file, String user_name) {
        if (General_File.check_if_file_exists(XML_Shell.get_path_of_communication_XML_File())) {
            General_File.delete_file(XML_Shell.get_path_of_communication_XML_File());
        }
        XML_Manager.create_Message(work_file, XML_FILE.Communication, XML_NODES.Response, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(work_file, XML_FILE.Communication, XML_NODES.Response, XML_SUB_NODES.Response_Text, Response_to_client, XML_Shell.get_path_of_communication_XML_File());
    }


    public static String create_Hash_of_File(File file_to_hash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            InputStream fis = new FileInputStream(file_to_hash);
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }


        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return bytesToHex(digest.digest());
    }


	/*
	 * Checks, if you can parse an input as an integer
	 */
    public static String normalize_String(String input_to_normalize) {

			/*
			 *  '&' --> '&amp;'

				'<' --> '&lt;'

				'>' --> '&gt;'

				'\' --> '&#92;'

				' ' ' --> '&#39;'

				'"' --> '&#34';

			 *
			 * */
        return input_to_normalize.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&#92;", "\\").replaceAll("&#39;", "'").replaceAll("&#34", "\"");
    }


    public static String create_md5_hash(String message) {

        byte[] bytesOfMessage;
        byte[] thedigest = null;

        try {
            bytesOfMessage = message.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return bytesToHex(thedigest);
    }


    /*
     * Checks, if you can parse an input as an integer
     */
    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

