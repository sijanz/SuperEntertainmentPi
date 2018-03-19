package Server.ServerGUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Starts the omxplayer and contains methods to control the media playback.
 *
 * @author Simon
 */
public class OMXPlayer {

    private static Process process = null;


    /**
     * Opens an omxplayer instance by running xterm and executing a script within it.
     *
     * @param selectedMedia the media to play
     */
    public OMXPlayer(String selectedMedia) {

        // create a script to store commands in
        try {
            PrintWriter writer = new PrintWriter("play.sh", "UTF-8");
            writer.println("omxplayer " + selectedMedia);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // make script executable
        try {
            Runtime.getRuntime().exec("chmod +x play.sh");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // run xterm in a new thread
        ProcessBuilder processBuilder = new ProcessBuilder("xterm", "./play.sh");
        processBuilder.redirectErrorStream(true);
        Thread thread = new Thread(new Controller(process, processBuilder));
        thread.start();

        // DO NOT DELETE! - somehow it is crucial for correct execution
        try {
            Robot robot = new Robot();
            robot.delay(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // remove script
        try {
            Runtime.getRuntime().exec("rm play.sh");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Plays or pauses a video.
     */
    public static void playPause() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_P);
            robot.keyRelease(KeyEvent.VK_P);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Stops the video playback.
     */
    public static void stop() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_Q);
            robot.keyRelease(KeyEvent.VK_Q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Increases the video volume.
     */
    public static void volumeUp() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_PLUS);
            robot.keyRelease(KeyEvent.VK_PLUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Decreases the video volume.
     */
    public static void volumeDown() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Skips the video playback plus 30 seconds.
     */
    public static void fastForward() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Skips the video playback minus 30 seconds.
     */
    public static void rewind() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_LEFT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Class to start a process in a new thread.
     */
    private class Controller implements Runnable {
        Process p;
        ProcessBuilder pb;

        Controller(Process p, ProcessBuilder pb) {
            this.p = p;
            this.pb = pb;
        }

        @Override
        public void run() {
            try {
                p = pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
