package Client.ClientGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

// TODO: pictures for everyone
public class SubMenu extends Controller implements Initializable {

    @FXML
    private BorderPane MainPane;

    @FXML
    Button homeButton;


    @FXML
    private void showMainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }


    @FXML
    private void testVideo(ActionEvent event) throws Exception {
        ClientInterface.playVideo("videotest.mp4");
        System.out.println("test");
        setNewScene(event, "VideoPlay", "Wiedergabe");
    }


    @FXML
    private void testAudio(ActionEvent event) throws Exception {
        ClientInterface.playMusic("audiotest.mp3");
        setNewScene(event, "VideoPlay", "Wiedergabe");
    }


    @FXML
    private void testPicture(ActionEvent event) throws Exception {
        ClientInterface.playPicture("testpicture.png");
        setNewScene(event, "ImagePlay", "Wiedergabe");
    }


    @FXML
    private void testChooseOutputMenu(ActionEvent event) throws Exception {
        setNewScene(event, "ChooseOutputMenu", "Ausgabeart wählen");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            homeButton.setTooltip(new Tooltip("Hauptmenü"));
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
