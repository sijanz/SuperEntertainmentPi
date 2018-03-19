package General.Backlog_Super_Entertainment_Pi;

import java.io.BufferedWriter;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import General.General_Super_Entertainment_Pi.General_Date;
import General.General_Super_Entertainment_Pi.General_Directory;
import General.General_Super_Entertainment_Pi.General_File;
import General.General_Super_Entertainment_Pi.General_Purpose;
import General.Media_Super_Entertainment_Pi.Media_General;


/**
 * @author Dustin
 */
public class Backlog_Manager {

    private static String path_to_Backlog_Directory = System.getProperty("user.dir") + "/" + "Backlog";

    private static String Backlog_Filename = "Backlog_from_" + General_Date.get_Date().replace("/", "-") + "_boot_Nr_";

    // Backlog file of current boot
    private static String path_to_current_Backlog_File = "";


    private static int get_latest_boot_number() {

        List<String> files = General_Directory.get_all_Files_from_path(path_to_Backlog_Directory);
        int max = 0;

        for (String t : files) {

            // Backlog files ar named like this Backlog_from_2017-10-27_boot_Nr_1.txt

            String[] files_in_backlog = t.split(Backlog_Filename);

			
			/*
             * We want to create a Backlog, every time the user server starts
			 * 
			 * The Backlog, got the date, and the number of the boot, if it starts n the same 
			 * day. 
			 * 
			 * */


            for (String aFiles_in_backlog : files_in_backlog) {

				/*For Debugging, purposes, we will check, if there are other files,
                 * than, the one we expected... after that we will determine the last boot number
				 * */

                // First we check, if there is a possibility to determine the last boot


                if (General_Purpose.tryParseInt(aFiles_in_backlog.split(".txt")[0])) {

                    String index_to_parse = aFiles_in_backlog.split(".txt")[0];

                    if (Integer.parseInt(index_to_parse) >= max) {

                        // than we will determine the boot number
                        max = Integer.parseInt(index_to_parse);
                    }
                }
            }
        }
        return max + 1;
    }

    public static void init_backlog() {

        System.out.println("//////////////////7A New Backlog file was created");


        if (!(General_Directory.check_if_path_exists(path_to_Backlog_Directory))) {
            try {

                General_Directory.create_path(path_to_Backlog_Directory);
            } catch (SecurityException se) {
                //
            }
        }

        // create new boot file

        path_to_current_Backlog_File = path_to_Backlog_Directory + "/" + Backlog_Filename + get_latest_boot_number() + ".txt";

        try {

            create_backlog_file();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void write_to_backlog_file(String message_to_add) {

        message_to_add = message_to_add + "\r\n";


        try (FileWriter fw = new FileWriter(path_to_current_Backlog_File, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message_to_add);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }


    private static void create_backlog_file() throws IOException {
        General_File.create_file(path_to_current_Backlog_File);

        String message_to_add = "Server starts at " + General_Date.get_Date_and_Time() + "\r\n" +

                "Backlog File is " + Backlog_Filename + get_latest_boot_number() + ".txt" + "\r\n" +

                "Backlog is located at " + path_to_Backlog_Directory + "\r\n" +

                "Medias are  located at : " + "\r\n" +
                Media_General.get_path_to_Music_Direcotry() + "\r\n" +
                Media_General.get_path_to_Picture_Direcotry() + "\r\n" +
                Media_General.get_path_to_Video_Direcotry() + "\r\n";

        System.out.println(message_to_add);

        try (PrintWriter out = new PrintWriter(path_to_current_Backlog_File)) {
            out.println(message_to_add);
        } catch (FileNotFoundException e) {
            try {
                General_File.create_file(path_to_current_Backlog_File);
            } catch (IOException io) {
                e.printStackTrace();
            }
        }
    }
}
