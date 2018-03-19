package Client.ClientGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * called when creating a new playlist
 */
public class PlaylistName extends Controller {

    @FXML
    TextField namefield;

    static String home = "";

    /**
     * cancel operation and change scene back to VideoMenu
     * @param event ActionEvent
     * @throws Exception exception
     */
    @FXML
    private void cancel(ActionEvent event) throws Exception {
        switch (home) {
            case "video":
                setNewScene(event, "VideoMenu", "Videos");
                break;
            case "music":
                setNewScene(event, "AudioMenu", "Musik");
                break;
            case "picture":
                setNewScene(event, "ImageMenu", "Bilder");
                break;
        }
    }

    /**
     * create new playlist with given name
     * @param event ActionEvent
     * @throws Exception exception
     * @author Ramin
     */
    @FXML
    private void playlistnameconfirm(ActionEvent event) throws Exception {
        String playlistname = namefield.getText();
        switch (home) {
            case "video":
                PlaylistControl.create_New_VIDEOplaylist(playlistname);
                setNewScene(event, "VideoMenu", "Videos");
                break;
            case "music":
                PlaylistControl.create_New_MUSICplaylist(playlistname);
                setNewScene(event, "AudioMenu", "Music");
                break;
            case "picture":
                PlaylistControl.create_New_PICTUREplaylist(playlistname);
                setNewScene(event, "ImageMenu", "Bilder");
                break;
            default:
                System.out.println("wrong home - home set to: " + home);
        }


    }
}
