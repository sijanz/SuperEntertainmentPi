package Server.Work_Directroy_Manager;

import java.io.IOException;

import General.Backlog.BacklogManager;
import General.GeneralUse.GeneralDate;
import General.GeneralUse.GeneralDirectory;


/**
 * @author Dustin
 */
public class Work_Directory_Manager {


    public static void delete_user_directory(String path_to_user_directoy) {

        try {
            GeneralDirectory.deleteAllFilesInPath(path_to_user_directoy, true);
        } catch (IOException e) {

            BacklogManager.writeToBacklogFile("//// Cannot delete Path " + path_to_user_directoy + " at " + GeneralDate.getDateAndTime());
        }
    }
}
