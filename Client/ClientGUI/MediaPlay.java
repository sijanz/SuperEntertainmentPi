package Client.ClientGUI;

import Client.ClientSuperEntertainmentPi.ClientManager;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


// TODO: implement slideshow on the Pi
public class MediaPlay extends Controller implements Initializable {

    private static XML_Manager.XML_NODES mediaType;

    private static String mediaToPlay;

    private static List<String> playList;

    @FXML
    private BorderPane MainPane;

    @FXML
    Label mediaNameLabel;


    static void setMediaType(XML_Manager.XML_NODES newMediaType) {
        mediaType = newMediaType;
    }


    static XML_Manager.XML_NODES getMediaType() {
        return mediaType;
    }


    static void setMediaToPlay(String newMediaToPlay) {
        mediaToPlay = newMediaToPlay;
    }


    @FXML
    private void shuffle() {
        for (int i = 1; i < playList.size(); i++) {
            int random = (int) (Math.random() * 100);
            if (random % 2 == 0) {
                Collections.swap(playList, i, i - 1);
            }
        }

        // @debug
        System.out.println("MediaPlay: created shuffled list:");
        for (String item : playList) {
            System.out.println(item);
        }
    }


    @FXML
    private void pause() throws Exception {
        ClientInterface.pause();
    }


    @FXML
    private void rewind() throws Exception {
        ClientInterface.rewind();
    }


    @FXML
    private void fastForward() throws Exception {
        ClientInterface.fastForward();
    }


    @FXML
    private void playPreviousItem() throws Exception {
        if (isFirstInList(mediaToPlay)) {
            mediaToPlay = playList.get(playList.size() - 1);
            ClientInterface.playNewMedia(mediaToPlay, mediaType);
            mediaNameLabel.setText(mediaToPlay);
        } else {
            mediaToPlay = playList.get(getPositionOfSelectedMedia(mediaToPlay) - 1);
            ClientInterface.playNewMedia(mediaToPlay, mediaType);
            mediaNameLabel.setText(mediaToPlay);
        }
    }


    @FXML
    private void playNextItem() throws Exception {
        if (isLastInList(mediaToPlay)) {
            mediaToPlay = playList.get(0);
            ClientInterface.playNewMedia(mediaToPlay, mediaType);
            mediaNameLabel.setText(mediaToPlay);
        } else {
            mediaToPlay = playList.get(getPositionOfSelectedMedia(mediaToPlay) + 1);
            ClientInterface.playNewMedia(mediaToPlay, mediaType);
            mediaNameLabel.setText(mediaToPlay);
        }
    }


    @FXML
    private void volumeDown() throws Exception {
        ClientInterface.volumeDown();
    }


    @FXML
    private void volumeUp() throws Exception {
        ClientInterface.volumeUp();
    }

    private static int getPositionOfSelectedMedia(String mediaToPlay) {
        int i = 0;
        for (String item : playList) {
            if (item.equals(mediaToPlay)) {
                return i;
            }
            i++;
        }
        return -1;
    }


    private static boolean isFirstInList(String mediaToPlay) {
        return playList.get(0).equals(mediaToPlay);
    }


    private static boolean isLastInList(String mediaToPlay) {
        return playList.get(playList.size() - 1).equals(mediaToPlay);
    }


    @FXML
    private void showVideoMenu(ActionEvent event) throws Exception {
        if (ClientManager.retrieveIndex()) {
            switch (mediaType) {
                case VIDEO:
                    ClientInterface.stopVideo();
                    setNewScene(event, "VideoMenu", "Videos");
                    break;
                case AUDIO:
                    ClientInterface.stopMusic();
                    setNewScene(event, "AudioMenu", "Musik");
                    break;
                case PICTURE:
                    ClientInterface.stopPicture();
                    setNewScene(event, "ImageMenu", "Bilder");
                    break;
            }
        } else {
            setNewScene(event, "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playList = ChooseOutputMenu.list;

        if (mediaType != XML_Manager.XML_NODES.PICTURE) {
            initializeMediaButtonPane();
            if (mediaType == XML_Manager.XML_NODES.VIDEO) {
                previousItemButton.setTooltip(new Tooltip("Vorheriges Video"));
                nextItemButton.setTooltip(new Tooltip("Nächstes Video"));
            } else if (mediaType == XML_Manager.XML_NODES.AUDIO) {
                previousItemButton.setTooltip(new Tooltip("Vorheriges Musikstück"));
                nextItemButton.setTooltip(new Tooltip("Nächstes Musikstück"));
            }
        } else {
            shuffleButton.setTooltip(new Tooltip("Shuffle"));
            stopButton.setTooltip(new Tooltip("Stop"));
            previousItemButton.setTooltip(new Tooltip("Vorheriges Bild"));
            nextItemButton.setTooltip(new Tooltip("Nächstes Bild"));
        }
        try {
            mediaNameLabel.setText(mediaToPlay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initializeTopPanel();
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
