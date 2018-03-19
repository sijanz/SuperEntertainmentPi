package Client.ClientGUI;

import Client.ClientManagement.ClientManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenu extends Controller implements Initializable {

    @FXML
    private BorderPane MainPane;

    @FXML
    Button videoButton;

    @FXML
    Button musicButton;

    @FXML
    Button pictureButton;

    @FXML
    Button teamButton;

    @FXML
    Button settingsButton;

    @FXML
    Button exitButton;

    @FXML
    private void showVideoMenu(ActionEvent event) throws Exception {
        if (ClientManager.retrieveIndex()) {
            setNewScene(event, "VideoMenu", "Videos");
        } else {
            setNewScene(event, "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }

    @FXML
    private void showMusicMenu(ActionEvent event) throws Exception {
        if (ClientManager.retrieveIndex()) {
            setNewScene(event, "AudioMenu", "Musik");
        } else {
            setNewScene(event, "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }

    @FXML
    private void showPictureMenu(ActionEvent event) throws Exception {
        if (ClientManager.retrieveIndex()) {
            setNewScene(event, "ImageMenu", "Bilder");
        } else {
            setNewScene(event, "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }

    @FXML
    private void showTeamMenu(ActionEvent event) throws Exception {
        setNewScene(event, "TeamMenu", "Das Team");
    }

    @FXML
    private void showSettingsMenu(ActionEvent event) throws Exception {
        setNewScene(event, "OptionsEntry", "Einstellungen");
    }

    @FXML
    public void exit() throws Exception {
        ClientManager.logout();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            videoButton.setTooltip(new Tooltip("Videobibliothek"));
            musicButton.setTooltip(new Tooltip("Musikbibliothek"));
            pictureButton.setTooltip(new Tooltip("Bildbibliothek"));
            teamButton.setTooltip(new Tooltip("Das Team"));
            settingsButton.setTooltip(new Tooltip("Einstellungen"));
            exitButton.setTooltip(new Tooltip("Beenden"));
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
