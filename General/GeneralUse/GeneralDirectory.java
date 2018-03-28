package General.GeneralUse;

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
public class GeneralDirectory {


    //deletes the object on the pathToFile variable
    private static void deleteFilepath(String pathToFile) {
        try {
            Files.delete(Paths.get(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAllFilesInPath(String pathToDelete, Boolean deletePathToo) throws IOException {
        List<String> filesToBeDeleted = GeneralDirectory.getAllFilesFromPath(pathToDelete, true);
        for (String itr : filesToBeDeleted) {

            // @debug
            System.out.println("---> " + itr);

            deleteFilepath(itr);
        }
        if (deletePathToo) {
            Files.delete(Paths.get(pathToDelete));
        }
    }


    public static List<String> getAllFilesFromPath(String filePath, Boolean withPath) {
        List<String> mediaPaths = new ArrayList<>();
        try {

            // @debug
            System.out.println("--> " + filePath);

            File folder = new File(filePath);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;

            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {

                    // @ðebug
                    System.out.println("File : " + listOfFile.getName());

                    if (!(new Character(listOfFile.getName().charAt(0))).equals('.')) {
                        if (withPath) {
                            mediaPaths.add(filePath + "/" + listOfFile.getName());
                        } else {
                            mediaPaths.add(listOfFile.getName());
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return mediaPaths;
    }


    public static List<String> getAllFilesFromPath(String filePath) {
        List<String> mediaPaths = new ArrayList<>();
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                mediaPaths.add(listOfFile.getName());
            }
        }
        return mediaPaths;
    }


    public static Boolean checkIfPathExists(String path) throws SecurityException {
        File f = new File(path);
        return (f.exists() && f.isDirectory());
    }


    public static void createPath(String path) throws SecurityException {
        File theDir = new File(path);
        if (theDir.mkdirs()) {
            BacklogManager.writeToBacklogFile("succeeded to create " + path);
        } else {
            BacklogManager.writeToBacklogFile("failed to create " + path);
        }
    }
}
