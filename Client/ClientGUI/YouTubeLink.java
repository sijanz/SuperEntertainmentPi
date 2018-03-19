package Client.ClientGUI;

import Client.EmbeddedPlayer.EmbeddedPlayerStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class YouTubeLink extends Controller implements Initializable {

    @FXML
    BorderPane MainPane;

    @FXML
    TextField youtubeLabel;

    @FXML
    Label errorLabel;

    @FXML
    Button backButton;

    @FXML
    private void acceptUrl(ActionEvent event) {
        String url = youtubeLabel.getText();

        if (isValidUrl(url)) {
            EmbeddedPlayerStarter.startYouTubePlayer(url);
            try {
                setNewScene(event, "VideoMenu", "Videos");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Bitte eine gültige URL eingeben!");
        }
    }


    private boolean isValidUrl(String url) {
        return url.contains("https://www.youtube.com/watch");
    }


    @FXML
    private void showVideoMenu(ActionEvent event) throws Exception {
        setNewScene(event, "VideoMenu", "Videos");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        errorLabel.setText("");
        backButton.setTooltip(new Tooltip("Zurück"));
        fadeIn(MainPane, 350);
    }
}
