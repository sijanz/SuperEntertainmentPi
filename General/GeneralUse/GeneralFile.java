package General.GeneralUse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import General.Backlog.BacklogManager;


/**
 * @author Dustin
 */
public class GeneralFile {


    public static Boolean checkIfFileExists(String path) throws SecurityException {
        File f = new File(path);
        return f.exists();
    }


    /*
     *
     * Method that shall read the contents
     * of a file, save it as a string, so the server can send it back.
     *
     * */
    public static String returnContentOfFile(String filePath) {
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(filePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int i;
            while ((i = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(i);
            }

            String content = byteArrayOutputStream.toString();
            fileInputStream.close();
            return content;

        } catch (IOException e) {
            BacklogManager.writeToBacklogFile("///////NO File at " + filePath + " to read at " + GeneralDate.getDateAndTime());
            return "";
        }
    }


    public static void deleteFile(String filePath) {
        File f = new File(filePath);
        f.delete();
    }


    public static void createFile(String filePath) throws IOException {
        File f = new File(filePath);
        f.createNewFile();
    }
}
