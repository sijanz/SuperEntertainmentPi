package General.General_Super_Entertainment_Pi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

import General.Backlog_Super_Entertainment_Pi.Backlog_Manager;


/**
 * @author Dustin
 */
public class General_File {


    public static void copy_File (String source_path_with_file, String destination_path) throws IOException {

        Files.move(Paths.get(source_path_with_file), Paths.get(destination_path));

    }


    /*
     * The Function will overwrite, if the destination file exists,,.....
     * */
    public static void copy_and_overwrite_File (String source_path_with_file, String destination_path) throws IOException {


        Files.move(Paths.get(source_path_with_file), Paths.get(destination_path), REPLACE_EXISTING );
    }



	/*
	 * @params:
	 *
	 * String source_path_with_file : path to the source file, (file included!!!!)
	 * String destination_path : path to the directory
	 *
	 *

    public static void move_File (String source_path_with_file, String destination_path) throws IOException {

	    	Path fileToMovePath = null;

	    	fileToMovePath = Files.createFile(Paths.get(source_path_with_file));
		Path targetPath = Paths.get(destination_path);
	    Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()));
    }

    *
	 * @params:
	 *
	 * String source_path_with_file : path to the source file, (file included!!!!)
	 * String destination_path : path to the directory
	 *
	 *

    public static void overwrite_File (String source_path_with_file, String destination_path) throws IOException {

	    	Path fileToMovePath = null;

	    	fileToMovePath = Files.createFile(Paths.get(source_path_with_file));
		Path targetPath = Paths.get(destination_path);
	    Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()) , REPLACE_EXISTING );
    }

    */
	/*
	 * every operation needs the whole filpath, that includes the filename as itself!!
		filepath = "path to file " + " file name"
	 *
	 *
	 */
    public static Boolean check_if_file_exists(String path) throws SecurityException {

        File f = new File(path);

        if ((f.exists())) {

            return true;
        } else {

            return false;
        }
    }


	/*
	 *
	 * Method that shall read the contents
	 * of a file, save it as a string, so the server can send it back.
	 *
	 * */
    public static String return_content_of_file(String file_path)  {


        FileInputStream fileInputStream;

        try {

            fileInputStream = new FileInputStream(file_path);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            int i;

            while ((i = fileInputStream.read()) != -1) {

                byteArrayOutputStream.write(i);
            }

            String content = byteArrayOutputStream.toString();

            fileInputStream.close();
            return content;

        } catch (IOException e) {
            Backlog_Manager.write_to_backlog_file("///////NO File at "  + file_path + " to read at " + General_Date.get_Date_and_Time());

            return "";
        }

    }


    public static void delete_file(String file_path) {

        File f = new File(file_path);

        f.delete();
    }


    public static void create_file(String file_path) throws IOException {

        File f = new File(file_path);

        f.createNewFile();
    }

}
