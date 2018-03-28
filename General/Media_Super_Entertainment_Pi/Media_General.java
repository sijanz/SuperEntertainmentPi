package General.Media_Super_Entertainment_Pi;

import java.util.*;

import General.Backlog.BacklogManager;
import General.GeneralUse.CharacterIterator;
import General.GeneralUse.GeneralDirectory;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;


/**
 * @author Dustin
 */
public class Media_General {

    private static String path_to_Media_Directory = System.getProperty("user.dir") + "/" + "Media";
    private static String path_to_Lock_Files = System.getProperty("user.dir") + "/" + "Control";

    private static String path_to_Video_Directory = path_to_Media_Directory + "/" + "Videos";
    private static String path_to_Music_Directory = path_to_Media_Directory + "/" + "Music";
    private static String path_to_Picture_Directory = path_to_Media_Directory + "/" + "Pictures";


    private static List<String> media_paths = Arrays.asList(path_to_Music_Directory,
            path_to_Picture_Directory, path_to_Video_Directory);

	
	
	/*
     * Method for decoding, and encoding media files
	 * 
	 * */
    public enum Music_Format {

        mp3
    }

    public enum Video_Format {

        mp4
    }

    public enum Picture_Format {

        jpeg, gif, png, jpg
    }

    public enum Media_Type_Sub {

        Image, Clip, Track
    }
	
	
	/*
	 * @params: 
	 * (String) String filename_to_check, 
	 * 
	 * we will try to etermine the format, of the filename......
	 *
	 * */
    static String determine_media_Format(CharacterIterator chars_of_filename_to_check) {
        String format[] = {"kein_format"};
        StringBuilder tmp_format = new StringBuilder();

        try {
            for (int ctr = chars_of_filename_to_check.size() - 1; ctr > -1; ctr = ctr - 1) {
                tmp_format.append(chars_of_filename_to_check.get(ctr));
                if (chars_of_filename_to_check.get(ctr - 1).equals(".")) {
                    String format_to_check = tmp_format.reverse().toString();

                    if (!(check_if_format_exists(format_to_check, format))) {
                        BacklogManager.writeToBacklogFile("////// Format " + format_to_check + " does not exist ");
                    }
                }
            }
        } catch (Exception ex) {
            BacklogManager.writeToBacklogFile("//// Cannot determine media format ");
        }
        return format[0];
    }


    private static boolean check_if_format_exists(String tmp_format, String[] format_that_was_find) {
        return (

                check_if_music_format_type(tmp_format, format_that_was_find) ||
                        check_if_video_format_type(tmp_format, format_that_was_find) ||
                        check_if_picture_format_type(tmp_format, format_that_was_find)

        );
    }

    private static boolean check_if_music_format_type(String tmp_format, String[] result) {
        Boolean format_flag = true;
        try {
            Music_Format test = Music_Format.valueOf(tmp_format);
            result[0] = test.toString();
        } catch (IllegalArgumentException ex) {
            format_flag = false;
        }
        return format_flag;
    }

    private static boolean check_if_picture_format_type(String tmp_format, String[] result) {
        Boolean format_flag = true;
        try {
            Picture_Format test = Picture_Format.valueOf(tmp_format);
            result[0] = test.toString();
        } catch (IllegalArgumentException ex) {
            format_flag = false;
        }
        return format_flag;
    }

    private static boolean check_if_video_format_type(String tmp_format, String[] result) {
        Boolean format_flag = true;
        try {
            Video_Format test = Video_Format.valueOf(tmp_format);
            result[0] = test.toString();
        } catch (IllegalArgumentException ex) {
            format_flag = false;
        }
        return format_flag;
    }
	
	
	/*
	 * Method iterates through the expected paths .....
	 * 
	 * Til yet there is no possiblity, to change the files, mybe though a config
	 * file?
	 * 
	 * 
	 * If the exception is thrown, the permissions, do not allow the creation of
	 * directories, the list will be empty
	 * 
	 */
    public static void init_media_archives() {
        try {
            for (String path : media_paths) {
                if (!(GeneralDirectory.checkIfPathExists(path))) {
                    GeneralDirectory.createPath(path);
                }
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }


    public static XML_NODES identify_path(String path_name) {
        XML_NODES name = null;

        if (Objects.equals(path_name, path_to_Video_Directory)) {
            name = XML_NODES.VIDEO;
        } else if (Objects.equals(path_name, path_to_Picture_Directory)) {
            name = XML_NODES.PICTURE;
        } else if (Objects.equals(path_name, path_to_Music_Directory)) {
            name = XML_NODES.MUSIC;
        }
        return name;
    }


    public static String determine_media_pathname(String media_type, String file_name) {
        String media_type_instance = "";
        switch (media_type) {
            case "VIDEO":
                media_type_instance = get_path_to_Video_Direcotry();
                break;
            case "PICTURE":
                media_type_instance = get_path_to_Picture_Direcotry();
                break;
            case "MUSIC":
                media_type_instance = get_path_to_Music_Direcotry();
                break;
        }
        return media_type_instance + "/" + file_name;
    }


    public static XML_SUB_NODES identify_media_type_instance(XML_NODES token) {
        XML_SUB_NODES media_type_instance = null;

        switch (token.toString()) {
            case "VIDEO":
                media_type_instance = XML_SUB_NODES.Clip;
                break;
            case "PICTURE":
                media_type_instance = XML_SUB_NODES.Image;
                break;
            case "MUSIC":
                media_type_instance = XML_SUB_NODES.Track;
                break;
        }
        return media_type_instance;
    }

    public static List<String> get_media_paths() {
        return media_paths;
    }

    public static String get_path_to_Video_Direcotry() {
        return path_to_Video_Directory;
    }

    public static String get_path_to_Music_Direcotry() {
        return path_to_Music_Directory;
    }

    public static String get_path_to_Picture_Direcotry() {
        return path_to_Picture_Directory;
    }
}
