package General.GeneralUse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Simon, Dustin
 */
public class GeneralPurpose {


    /*
     *
     * @filename: name of a file, but not its path : -> example.mp4
     *
     * */
    public static String buildPathToFile(String filename) {
        List<String> pathsToSearch = GeneralMedia.getMediaPaths();
        List<String> filesOfSpecificPath;

        for (String currentPath : pathsToSearch) {
            filesOfSpecificPath = GeneralDirectory.getAllFilesFromPath(currentPath);

            for (String currentFile : filesOfSpecificPath) {
                System.out.println(currentFile + " == " + filename);
                if (filename.equals(currentFile)) {
                    return currentPath + "/" + currentFile;
                }
            }
        }
        return "<NO_PATH>";
    }


    /*
     * @params: tagToFilter : <Name_of_tag_that_gets_filtered>
     *
     * */
    public static String getContentOfTagAsString(String tagToFilter, String message) {
        String temp = message.replace("<" + tagToFilter + ">", "§");
        String temp_final = temp.replace("</" + tagToFilter + ">", "§");

        String[] parts = temp_final.split("§");
        String result;

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
    public static List<String> getContentOfTagAsString(String Tag, String Tag_to_filter, String Tag_Not_to_filter, String message) {
        List<String> result = new ArrayList<>();
        String temp = message.replace("<" + Tag_to_filter + ">", "X");
        String tempFinal = temp.replace("</" + Tag_to_filter + ">", "X");

        String[] parts = tempFinal.split("X");

        int ctr = 1;
        for (int index = 1; index <= (parts.length / 3); index++) {

            temp = parts[ctr].replace("<" + Tag + ">", "X");
            tempFinal = temp.replace("</" + Tag + ">", "X");

            temp = tempFinal.replace("<" + Tag_Not_to_filter + ">", "§");
            tempFinal = temp.replace("</" + Tag_Not_to_filter + ">", "§");

            String[] tmpResult = tempFinal.split("X");

            for (String aTmp_result : tmpResult) {

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


    public static String createHashOfFile(File fileToHash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            InputStream fis = new FileInputStream(fileToHash);
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
    public static String normalizeString(String input) {

			/*
			 *  '&' --> '&amp;'

				'<' --> '&lt;'

				'>' --> '&gt;'

				'\' --> '&#92;'

				' ' ' --> '&#39;'

				'"' --> '&#34';

			 *
			 * */
        return input.replace("&amp;", "&").replace("&lt;", "<")
                .replace("&gt;", ">").replace("&#92;", "\\")
                .replaceAll("&#39;", "'").replaceAll("&#34", "\"");
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

