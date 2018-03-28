package Server.ServerGUI;

import java.io.File;
import java.util.List;

import static General.GeneralUse.GeneralDirectory.getAllFilesFromPath;


/**
 * Runs in a thread checking for .lock files. If a .lock file is detected, an action is triggered.
 *
 * @author Simon
 */
public class LockHandler implements Runnable {

    // the path where the .lock files are located
    private final String lockPath = "/home/pi/";


    /**
     * Constructor.
     */
    public LockHandler() {

        // @debug
        System.out.println("LockHandler: my path is " + this.lockPath);
    }


    /**
     * Searches for .lock files in the path determines the action if a .lock file is found.
     */
    private void checkLockFiles() {
        List<String> locks = getAllFilesFromPath(this.lockPath);
        for (String currentLock : locks) {
            if (isLock(currentLock)) {

                // @debug
                System.out.println("LockHandler: " + currentLock);

                determineAction(currentLock);
                File f = new File(lockPath + "/" + currentLock);
                if (f.delete()) {

                    // @debug
                    System.out.println("LockHandler: deleted " + currentLock);
                }
            }
        }
    }


    /**
     * Checks if a given file is a .lock file.
     *
     * @param name the given file
     * @return true if .lock file, false otherwise
     */
    private static boolean isLock(String name) {
        return name.contains(".lock");
    }


    /**
     * Determines the action for each .lock file.
     *
     * @param lockFile the given .lock file
     */
    private static void determineAction(String lockFile) {

        // @debug
        System.out.println("LockHandler: determining action...");

        switch (lockFile) {
            case "StopPicture.lock":
                PictureDisplayer.finished = true;
                break;
        }
    }


    /**
     * Checks for .lock files and waits for 1 second.
     */
    @Override
    public void run() {
        while (true) {
            try {
                checkLockFiles();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
