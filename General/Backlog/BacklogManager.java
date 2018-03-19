package General.Backlog;

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
 * @author Dustin, Simon
 */
public class BacklogManager {


    private static String pathToBacklogDirectory = System.getProperty("user.dir") + "/" + "Backlog";
    private static String backlogFilename = "Backlog_from_" + General_Date.get_Date().replace("/", "-") + "_boot_Nr_";

    // Backlog file of current boot
    private static String pathToCurrentBacklogFile = "";


    private static int getLatestBootNumber() {
        List<String> files = General_Directory.get_all_Files_from_path(pathToBacklogDirectory);
        int max = 0;

        for (String t : files) {

            // Backlog files ar named like this Backlog_from_2017-10-27_boot_Nr_1.txt
            String[] filesInBacklog = t.split(backlogFilename);

			
			/*
             * We want to create a Backlog, every time the user server starts
			 * 
			 * The Backlog, got the date, and the number of the boot, if it starts n the same 
			 * day. 
			 * 
			 */
            for (String iterator : filesInBacklog) {
				/*For Debugging, purposes, we will check, if there are other files,
                 * than, the one we expected... after that we will determine the last boot number
				 *
                 * First we check, if there is a possibility to determine the last boot
                 */
                if (General_Purpose.tryParseInt(iterator.split(".txt")[0])) {
                    String indexToParse = iterator.split(".txt")[0];

                    if (Integer.parseInt(indexToParse) >= max) {

                        // than we will determine the boot number
                        max = Integer.parseInt(indexToParse);
                    }
                }
            }
        }
        return max + 1;
    }


    public static void initializeBacklog() {
        System.out.println("////////////////// A new backlog file was created");

        if (!(General_Directory.check_if_path_exists(pathToBacklogDirectory))) {
            try {
                General_Directory.create_path(pathToBacklogDirectory);
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        // create new boot file
        pathToCurrentBacklogFile = pathToBacklogDirectory + "/" + backlogFilename + getLatestBootNumber() + ".txt";

        try {
            createBacklogFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToBacklogFile(String messageToAdd) {
        messageToAdd = messageToAdd + "\r\n";

        try (FileWriter fw = new FileWriter(pathToCurrentBacklogFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(messageToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void createBacklogFile() throws IOException {
        General_File.create_file(pathToCurrentBacklogFile);

        String messageToAdd = "Server starts at " + General_Date.get_Date_and_Time() + "\r\n" +

                "Backlog File is " + backlogFilename + getLatestBootNumber() + ".txt" + "\r\n" +

                "Backlog is located at " + pathToBacklogDirectory + "\r\n" +

                "Medias are located at : " + "\r\n" +
                Media_General.get_path_to_Music_Direcotry() + "\r\n" +
                Media_General.get_path_to_Picture_Direcotry() + "\r\n" +
                Media_General.get_path_to_Video_Direcotry() + "\r\n";

        // @debug
        System.out.println(messageToAdd);

        try (PrintWriter out = new PrintWriter(pathToCurrentBacklogFile)) {
            out.println(messageToAdd);
        } catch (FileNotFoundException e) {
            try {
                General_File.create_file(pathToCurrentBacklogFile);
            } catch (IOException io) {
                e.printStackTrace();
            }
        }
    }
}
