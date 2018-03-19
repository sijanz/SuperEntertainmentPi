package Server.ServerGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Starts the media player using the VLCj-framework and contains methods to control the media playback.
 *
 * @author Simon
 */
public class PictureDisplayer implements Runnable {

    // a helper flag to close the thread from another thread
    static volatile boolean finished = false;

    // the JFrame to work on
    private static JFrame frame = new JFrame();


    /**
     * Starts the playback of an audio file or picture in a new frame.
     *
     * @param selectedMedia the media to play
     */
    public PictureDisplayer(String selectedMedia) {

        // initialize the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // read in the picture
        try {
            BufferedImage myPicture = ImageIO.read(new File("Media/Pictures/" + selectedMedia));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            contentPane.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // show the whole thing
        contentPane.setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }


    /**
     * Runs while the helper flag is set.
     */
    @Override
    public void run() {
        // @debug
        System.out.println("VLCPlayer: running");

        while (!finished) {
            // show picture
        }
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        finished = false;

        // @debug
        System.out.println("VLCPlayer: stop running");
    }
}
