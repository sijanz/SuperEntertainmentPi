package General.General_Super_Entertainment_Pi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import General.Backlog.BacklogManager;


/**
 * @author Dustin
 */
public class General_Directory {


    //deletes the object on the path_to_file variable
    private static void delete_filepath(String path_to_file)  {

        try {

            Files.delete( Paths.get(path_to_file) );
        }
        catch(IOException ex ) {
            //
        }
    }


    public static void delete_all_files_path(String path_to_delete , Boolean delete_path_too) throws IOException {

        List<String> files_to_be_deleted =   General_Directory.get_all_Files_from_path(path_to_delete, true );

        for(String file_to_be_deleted : files_to_be_deleted )
        {

            System.out.println("---> " + file_to_be_deleted);

            delete_filepath(file_to_be_deleted);


        }
        if(delete_path_too ) {

            Files.delete(Paths.get( path_to_delete));
        }
    }




    public static List<String> get_all_Files_from_path(String file_path, Boolean with_path) {

        List<String> media_paths = new ArrayList<>();

        try {
            System.out.println("--> " + file_path);
            File folder = new File(file_path);
            File[] listOfFiles = folder.listFiles();

            assert listOfFiles != null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {

                    System.out.println("File : " + listOfFile.getName());
                    if (!(new Character(listOfFile.getName().charAt(0))).equals('.')) {

                        if (with_path) {

                            media_paths.add(file_path + "/" + listOfFile.getName());
                        } else {

                            media_paths.add(listOfFile.getName());
                        }
                    }

                }

            }

        }
        catch( NullPointerException nullex) {

            //
        }

        return media_paths;
    }


    public static List<String> get_all_Files_from_path(String file_path) {

        List<String> media_paths = new ArrayList<>();

        File folder = new File(file_path);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {

                media_paths.add(listOfFile.getName());

            }
        }

        return media_paths;
    }


    public static Boolean check_if_path_exists(String path) throws SecurityException {

        File f = new File(path);

        return (f.exists() && f.isDirectory());
    }


    public static void delete_path(String path_to_delete ) throws IOException {

        List<String> files_to_be_deleted =   General_Directory.get_all_Files_from_path(path_to_delete);

        for(String file_to_be_deleted : files_to_be_deleted )
        {
            General_File.delete_file(file_to_be_deleted);
        }

        Files.delete(Paths.get(path_to_delete));
    }


    public static void create_path(String path) throws SecurityException {

        File theDir = new File(path);

        if (theDir.mkdirs()) {

            BacklogManager.writeToBacklogFile("succeeeded to create " + path);

        } else {

            BacklogManager.writeToBacklogFile("failed to create " + path);

        }
    }

}
