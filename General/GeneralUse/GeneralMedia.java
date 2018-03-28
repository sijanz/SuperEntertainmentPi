package General.GeneralUse;

import java.util.*;

import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;


/**
 * @author Dustin
 */
public class GeneralMedia {

    private static String pathToMediaDirectory = System.getProperty("user.dir") + "/" + "Media";
    private static String pathToVideoDirectory = pathToMediaDirectory + "/" + "Videos";
    private static String pathToMusicDirectory = pathToMediaDirectory + "/" + "Music";
    private static String pathToPictureDirectory = pathToMediaDirectory + "/" + "Pictures";

    private static List<String> mediaPaths = Arrays.asList(pathToMusicDirectory,
            pathToPictureDirectory, pathToVideoDirectory);


    public enum Music_Format {
        mp3
    }

    public enum Video_Format {
        mp4
    }

    public enum Picture_Format {
        jpeg, gif, png, jpg
    }


    public static List<String> getMediaPaths() {
        return mediaPaths;
    }

    public static String getPathToVideoDirectory() {
        return pathToVideoDirectory;
    }

    public static String getPathToMusicDirectory() {
        return pathToMusicDirectory;
    }

    public static String getPathToPictureDirectory() {
        return pathToPictureDirectory;
    }
	
	
	/*
	 * Method iterates through the expected paths .....
	 * 
	 * Til yet there is no possibility, to change the files, maybe though a config
	 * file?
	 * 
	 * 
	 * If the exception is thrown, the permissions, do not allow the creation of
	 * directories, the list will be empty
	 * 
	 */
    public static void initMediaArchives() {
        try {
            for (String path : mediaPaths) {
                if (!(GeneralDirectory.checkIfPathExists(path))) {
                    GeneralDirectory.createPath(path);
                }
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }


    public static XML_NODES identifyPath(String pathName) {
        XML_NODES name = null;

        if (Objects.equals(pathName, pathToVideoDirectory)) {
            name = XML_NODES.VIDEO;
        } else if (Objects.equals(pathName, pathToPictureDirectory)) {
            name = XML_NODES.PICTURE;
        } else if (Objects.equals(pathName, pathToMusicDirectory)) {
            name = XML_NODES.MUSIC;
        }
        return name;
    }


    public static String determineMediaPathName(String mediaType, String fileName) {
        String mediaTypeInstance = "";

        switch (mediaType) {
            case "VIDEO":
                mediaTypeInstance = getPathToVideoDirectory();
                break;
            case "PICTURE":
                mediaTypeInstance = getPathToPictureDirectory();
                break;
            case "MUSIC":
                mediaTypeInstance = getPathToMusicDirectory();
                break;
        }
        return mediaTypeInstance + "/" + fileName;
    }


    public static XML_SUB_NODES identifyMediaTypeInstance(XML_NODES token) {
        XML_SUB_NODES mediaTypeInstance = null;

        switch (token.toString()) {
            case "VIDEO":
                mediaTypeInstance = XML_SUB_NODES.Clip;
                break;
            case "PICTURE":
                mediaTypeInstance = XML_SUB_NODES.Image;
                break;
            case "MUSIC":
                mediaTypeInstance = XML_SUB_NODES.Track;
                break;
        }
        return mediaTypeInstance;
    }
}
