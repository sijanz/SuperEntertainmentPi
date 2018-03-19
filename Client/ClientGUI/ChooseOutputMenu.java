package Client.ClientGUI;

import Client.ClientSuperEntertainmentPi.ClientManager;
import Client.EmbeddedPlayer.EmbeddedPlayerStarter;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Controller class for the menu that chooses the output.
 *
 * @author Simon
 */
public class ChooseOutputMenu extends Controller implements Initializable {

    private static XML_Manager.XML_NODES mediaType;
    private static String mediaToPlay;
    static List<String> list; // full content of current playlist
    static String home; // "video", "music" or "image"
    static String mode; // "normal" or "random"

    @FXML
    private BorderPane MainPane;

    @FXML
    javafx.scene.control.Button showRemoteButton;

    @FXML
    javafx.scene.control.Button showLocalButton;

    @FXML
    Button backButton;


    public static List<String> getList() {
        return list;
    }


    /**
     * Setter for mediaType.
     *
     * @param newMediaType the new media type to set
     */
    static void setMediaType(XML_Manager.XML_NODES newMediaType) {
        mediaType = newMediaType;
    }


    /**
     * Setter for mediaToPlay.
     *
     * @param newMediaToPlay the new media to play
     */
    static void setMediaToPlay(String newMediaToPlay) {
        mediaToPlay = newMediaToPlay;
    }


    /**
     * Event handler method for the returning to the main menu.
     *
     * @param event generic ActionEvent
     */
    @FXML
    private void showMediaMenu(ActionEvent event) throws Exception {
        if (ClientManager.retrieveIndex()) {
            switch (mediaType) {
                case VIDEO:
                    setNewScene(event, "VideoMenu", "Videos");
                    break;
                case AUDIO:
                    setNewScene(event, "AudioMenu", "Musik");
                    break;
            }
        } else {
            setNewScene(event, "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }


    /**
     * Event handler method for the starting of the EmbeddedPlayer.
     */
    @FXML
    private void startLocalPlayback() {
        EmbeddedPlayerStarter.startEmbeddedPlayer(mediaType, mediaToPlay);
    }


    /**
     * Event handler method for the displaying to the remote play menu.
     *
     * @param event generic ActionEvent
     */
    @FXML
    private void showControlView(ActionEvent event) throws Exception {
        switch (MediaPlay.getMediaType()) {
            case VIDEO:
                ClientInterface.playVideo(mediaToPlay);
                setNewScene(event, "VideoPlay", "Wiedergabe");
                break;
            case AUDIO:
                ClientInterface.playMusic(mediaToPlay);
                setNewScene(event, "AudioPlay", "Wiedergabe");
                break;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            backButton.setTooltip(new Tooltip("Zurück"));
            showRemoteButton.setTooltip(new Tooltip("Raspberry Pi"));
            showLocalButton.setTooltip(new Tooltip("Dieser Monitor"));
            fadeIn(MainPane, 350);

            // @debug
            System.out.println("home is " + home);
            System.out.println("list is " + list);
            System.out.println("mode is " + mode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
