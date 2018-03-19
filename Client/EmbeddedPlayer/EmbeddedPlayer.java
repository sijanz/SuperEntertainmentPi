package Client.EmbeddedPlayer;

import Client.ClientGUI.ChooseOutputMenu;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;


/**
 * Starts the media player using the VLCj-framework and contains methods to control the media playback.
 *
 * @author Simon
 */
class EmbeddedPlayer {

    // the media player itself
    private static final EmbeddedMediaListPlayerComponent mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();

    // the main JFrame to work on
    private static final JFrame frame = new JFrame();

    // the type of media to be played
    private static XML_Manager.XML_NODES mediaType;


    /**
     * Starts the playback of an audio file or picture in a new frame.
     *
     * @param selectedMedia the media to play
     */
    EmbeddedPlayer(String selectedMedia, boolean isYouTubePlayer) {

        if (!isYouTubePlayer) {

            // creates the playlist
            initializeMediaList(ChooseOutputMenu.getList());

            // @debug
            System.out.println("EmbeddedPlayer: created playlist:");
            for (MediaListItem item : mediaPlayerComponent.getMediaList().items()) {
                System.out.println(item.name());
            }
        }

        // initialize JPanel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        // initialize and add media player
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        // pane where the control buttons are located
        JPanel buttonPane = new JPanel();

        // initialize buttons
        JButton shuffleButton = new JButton();
        JButton pauseButton = new JButton();
        JButton rewindButton = new JButton();
        JButton fastForwardButton = new JButton();
        JButton volumeDownButton = new JButton();
        JButton volumeUpButton = new JButton();
        JButton previousItemButton = new JButton();
        JButton nextItemButton = new JButton();
        JButton exitButton = new JButton();

        // add buttons to array
        JButton[] buttonArray = {shuffleButton, pauseButton, exitButton, rewindButton,
                fastForwardButton, previousItemButton, nextItemButton, volumeDownButton, volumeUpButton};

        // set button preferences
        for (JButton button : buttonArray) {
            button.setPreferredSize(new Dimension(60, 60));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            buttonPane.add(button);
        }

        // add action listeners to JButtons
        shuffleButton.addActionListener(actionEvent -> shuffle());
        pauseButton.addActionListener(actionEvent -> playPause());
        rewindButton.addActionListener(actionEvent -> rewind());
        fastForwardButton.addActionListener(actionEvent -> fastForward());
        volumeDownButton.addActionListener(actionEvent -> volumeDown());
        volumeUpButton.addActionListener(actionEvent -> volumeUp());
        previousItemButton.addActionListener(actionEvent -> playPreviousItem());
        nextItemButton.addActionListener(actionEvent -> playNextItem());
        exitButton.addActionListener(actionEvent -> exit());

        // add icons to JButtons
        addIconToButton(shuffleButton, new ImageIcon("Images/shuffle.png"));
        addIconToButton(pauseButton, new ImageIcon("Images/play.png"));
        addIconToButton(rewindButton, new ImageIcon("Images/rewind_10.png"));
        addIconToButton(fastForwardButton, new ImageIcon("Images/fast_forward_10.png"));
        addIconToButton(volumeDownButton, new ImageIcon("Images/lower.png"));
        addIconToButton(volumeUpButton, new ImageIcon("Images/louder.png"));
        addIconToButton(previousItemButton, new ImageIcon("Images/previous_item.png"));
        addIconToButton(nextItemButton, new ImageIcon("Images/next_item.png"));
        addIconToButton(exitButton, new ImageIcon("Images/stop.png"));

        // add rollover icon to JButtons
        addRolloverIconToButton(shuffleButton, new ImageIcon("Images/shuffle_hover.png"));
        addRolloverIconToButton(pauseButton, new ImageIcon("Images/play_hover.png"));
        addRolloverIconToButton(rewindButton, new ImageIcon("Images/rewind_10_hover.png"));
        addRolloverIconToButton(fastForwardButton, new ImageIcon("Images/fast_forward_10_hover.png"));
        addRolloverIconToButton(volumeDownButton, new ImageIcon("Images/lower_hover.png"));
        addRolloverIconToButton(volumeUpButton, new ImageIcon("Images/louder_hover.png"));
        addRolloverIconToButton(previousItemButton, new ImageIcon("Images/previous_item_hover.png"));
        addRolloverIconToButton(nextItemButton, new ImageIcon("Images/next_item_hover.png"));
        addRolloverIconToButton(exitButton, new ImageIcon("Images/stop_hover.png"));

        if (isYouTubePlayer) {
            buttonPane.remove(shuffleButton);
            buttonPane.remove(previousItemButton);
            buttonPane.remove(nextItemButton);
        }

        buttonPane.setBackground(Color.darkGray);

        contentPane.add(buttonPane, BorderLayout.SOUTH);

        frame.setTitle("Wiedergabe");

        if (isYouTubePlayer) {
            frame.setTitle(selectedMedia);
        }

        frame.setContentPane(contentPane);
        frame.setBounds(new Rectangle(800, 600));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        if (!isYouTubePlayer) {

            // play media
            mediaPlayerComponent.getMediaListPlayer().playItem(getSelectedMediaIndex(selectedMedia));
        } else {
            mediaPlayerComponent.getMediaPlayer().setPlaySubItems(true);
            mediaPlayerComponent.getMediaPlayer().playMedia(selectedMedia);
        }
    }


    /**
     * Helper method for adding icons to JButtons.
     *
     * @param button the button to add the icon to
     * @param icon   the icon to add
     */
    private static void addIconToButton(JButton button, ImageIcon icon) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(newImage));
    }


    /**
     * Helper method for adding icon to JButtons as a rollover effect.
     *
     * @param button the button to add the icon to
     * @param icon   the icon to add
     */
    private static void addRolloverIconToButton(JButton button, ImageIcon icon) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setRolloverIcon(new ImageIcon(newImage));
    }


    /**
     * Setter for the type of media to play.
     *
     * @param newMediaType the new type of the media to play
     */
    static void setMediaType(XML_Manager.XML_NODES newMediaType) {
        mediaType = newMediaType;
    }


    /**
     * Returns the index of a media item in the playlist.
     *
     * @param mediaName the media name to get the index from
     * @return the index to the media item, -1 if not found
     */
    private static int getSelectedMediaIndex(String mediaName) {
        int i = 0;
        for (MediaListItem item : mediaPlayerComponent.getMediaList().items()) {
            if (item.toString().contains(mediaName)) {
                return i;
            }
            i++;
        }
        return -1;
    }


    /**
     * Creates a mediaList-playlist out of a media list defined in the library.
     *
     * @param mediaList the predefined media list
     */
    private static void initializeMediaList(List<String> mediaList) {
        for (String iterator : mediaList) {
            mediaPlayerComponent.getMediaList().addMedia(EmbeddedPlayerStarter.createMrl(mediaType, iterator));
        }
    }


    /**
     * Shuffles the playlist by clearing the old one and creating a new one in random order.
     */
    private static void shuffle() {
        mediaPlayerComponent.getMediaList().clear();

        List<String> shuffledList = ChooseOutputMenu.getList();
        for (int i = 1; i < shuffledList.size(); i++) {
            int random = (int) (Math.random() * 100);
            if (random % 2 == 0) {
                Collections.swap(shuffledList, i, i - 1);
            }
        }

        initializeMediaList(shuffledList);

        // @debug
        System.out.println("EmbeddedPlayer: created shuffled list:");
        for (MediaListItem item : mediaPlayerComponent.getMediaList().items()) {
            System.out.println(item.name());
        }
    }


    /**
     * Plays or pauses a media file.
     */
    private static void playPause() {
        mediaPlayerComponent.getMediaPlayer().pause();
    }


    /**
     * Increases the volume.
     */
    private static void volumeUp() {
        if (mediaPlayerComponent.getMediaPlayer().getVolume() <= 90) {
            mediaPlayerComponent.getMediaPlayer().setVolume(mediaPlayerComponent.getMediaPlayer().getVolume() + 10);
        }
    }


    /**
     * Decreases the volume.
     */
    private static void volumeDown() {
        mediaPlayerComponent.getMediaPlayer().setVolume(mediaPlayerComponent.getMediaPlayer().getVolume() - 10);
    }


    /**
     * Skips the playback plus 10 seconds.
     */
    private static void fastForward() {
        mediaPlayerComponent.getMediaPlayer().skip(10000);
    }


    /**
     * Skips the playback minus 10 seconds.
     */
    private static void rewind() {
        mediaPlayerComponent.getMediaPlayer().skip(-10000);
    }


    /**
     * Plays back the previous item in the playlist.
     */
    private static void playPreviousItem() {
        int size = mediaPlayerComponent.getMediaList().size();
        if (isFirstInList(mediaPlayerComponent.getMediaListPlayer().currentMrl())) {
            mediaPlayerComponent.getMediaListPlayer().playItem(size - 1);
        } else {
            mediaPlayerComponent.getMediaListPlayer().playPrevious();
        }
    }


    /**
     * Plays back the next item in the playlist.
     */
    private static void playNextItem() {

        // @debug
        System.out.println("is last: " + isLastInList(mediaPlayerComponent.getMediaListPlayer().currentMrl()));
        if (isLastInList(mediaPlayerComponent.getMediaListPlayer().currentMrl())) {
            mediaPlayerComponent.getMediaListPlayer().playItem(0);
        } else {
            mediaPlayerComponent.getMediaListPlayer().playNext();
        }
    }


    /**
     * Ends the playback and closes the JFrame.
     */
    private static void exit() {
        mediaPlayerComponent.getMediaPlayer().stop();
        mediaPlayerComponent.getMediaList().clear();
        frame.dispose();
    }


    /**
     * Checks if a given media item is first in the playlist.
     *
     * @param mrl the MRL of the media item
     * @return true if first, false otherwise
     */
    private static boolean isFirstInList(String mrl) {
        return mediaPlayerComponent.getMediaList().items().get(0).toString().contains(mrl);
    }


    /**
     * Checks if a given media item is last in the playlist.
     *
     * @param mrl the MRL of the media item
     * @return true if last, false otherwise
     */
    private static boolean isLastInList(String mrl) {
        List<MediaListItem> currentMediaList = mediaPlayerComponent.getMediaList().items();
        for (int i = 0; i < currentMediaList.size(); i++) {
            if (currentMediaList.get(i).toString().contains(mrl) && i == (currentMediaList.size() - 1)) {
                return true;
            }
        }
        return false;
    }
}
