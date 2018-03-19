package Server.Work_Directroy_Manager;

import java.io.IOException;

import General.Backlog.BacklogManager;
import General.General_Super_Entertainment_Pi.General_Date;
import General.General_Super_Entertainment_Pi.General_Directory;


/**
 * @author Dustin
 */
public class Work_Directory_Manager {


    public static void delete_user_directory(String path_to_user_directoy) {

        try {
            General_Directory.delete_all_files_path(path_to_user_directoy, true);
        } catch (IOException e) {

            BacklogManager.writeToBacklogFile("//// Cannot delete Path " + path_to_user_directoy + " at " + General_Date.get_Date_and_Time());
        }
    }
}
