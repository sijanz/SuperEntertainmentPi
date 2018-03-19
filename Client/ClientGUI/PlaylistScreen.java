package Client.ClientGUI;

import General.General_Super_Entertainment_Pi.General_File;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * called when adding an item to a playlist
 */

public class PlaylistScreen extends Controller implements Initializable {

    static String selectedItem = "";

    static String home = "";

    @FXML
    ListView<String> playlistselect;

    @FXML
    Label label;

    /**
     * cancel operation and change scene back to VideoMenu
     * @param event ActionEvent
     * @throws Exception exception
     */
    @FXML
    private void cancel(ActionEvent event) throws Exception {
        switch (home) {
            case "videodelete":
                setNewScene(event, "VideoMenu", "Videos");
                break;
            case "musicdelete":
                setNewScene(event, "AudioMenu", "Musik");
                break;
            case "picturedelete":
                setNewScene(event, "ImageMenu", "Bilder");
                break;
            case "videoadd":
                setNewScene(event, "VideoMenu", "Videos");
                break;
            case "musicadd":
                setNewScene(event, "AudioMenu", "Musik");
                break;
            case "pictureadd":
                setNewScene(event, "ImageMenu", "Bilder");
                break;
        }
    }

    /**
     * ok button, add element to playlist or delete playlist
     * @param event ActionEvent
     * @throws Exception exception
     */
    @FXML
    private void ok(ActionEvent event) throws Exception {

        //check if a playlist is selected
        if (playlistselect.getSelectionModel().getSelectedIndex() != -1) {

            //save selected playlist in playlistname and delete brackets []
            String playlistname = playlistselect.getSelectionModel().getSelectedItems().toString();
            playlistname = PlaylistControl.noBrackets(playlistname);

            switch (home) {
                case "videoadd":
                    //get content in playlist-file and save it in ContentInPlaylist
                    String PathVideo = XML_Shell.get_path_to_VideoDirectory() + "/" + playlistname + ".xml";
                    String ContentInPlaylistVideo = General_File.return_content_of_file(PathVideo);

                    //add new Video to file
                    ContentInPlaylistVideo = ContentInPlaylistVideo.replace("</VIDEO>", "<" + VideoMenu.Subnode + ">" + selectedItem + "</" + VideoMenu.Subnode + ">" + "</VIDEO>");

                    //write new content in file
                    PrintWriter printwriterVideo = new PrintWriter(PathVideo);
                    printwriterVideo.write(ContentInPlaylistVideo);
                    printwriterVideo.close();

                    //change scene back to VideoMenu
                    setNewScene(event, "VideoMenu", "Videos");
                    break;
                case "musicadd":
                    //get content in playlist-file and save it in ContentInPlaylist
                    String PathMusic = XML_Shell.get_path_to_MusicDirectory() + "/" + playlistname + ".xml";
                    String ContentInPlaylistMusic = General_File.return_content_of_file(PathMusic);

                    //add new Video to file
                    ContentInPlaylistMusic = ContentInPlaylistMusic.replace("</MUSIC>", "<Track>" + selectedItem + "</Track></MUSIC>");

                    //write new content in file
                    PrintWriter printwriterMusic = new PrintWriter(PathMusic);
                    printwriterMusic.write(ContentInPlaylistMusic);
                    printwriterMusic.close();

                    //change scene back to VideoMenu
                    setNewScene(event, "AudioMenu", "Audio");
                    break;
                case "pictureadd":
                    //get content in playlist-file and save it in ContentInPlaylist
                    String PathPicture = XML_Shell.get_path_to_PictureDirectory() + "/" + playlistname + ".xml";
                    String ContentInPlaylistPicture = General_File.return_content_of_file(PathPicture);
                    System.out.println(ContentInPlaylistPicture);

                    //add new Video to file
                    ContentInPlaylistPicture = ContentInPlaylistPicture.replace("</PICTURE>", "<Image>" + selectedItem + "</Image></PICTURE>");

                    // write new content in file
                    PrintWriter printwriterPicture = new PrintWriter(PathPicture);
                    printwriterPicture.write(ContentInPlaylistPicture);
                    printwriterPicture.close();

                    //change scene back to VideoMenu
                    setNewScene(event, "ImageMenu", "Bilder");

                    break;

                case "videodelete":
                    PlaylistControl.delete_VIDEOplaylist(playlistname);
                    setNewScene(event, "VideoMenu", "Videos");
                    break;
                case "musicdelete":
                    PlaylistControl.delete_MUSICplaylist(playlistname);
                    setNewScene(event, "AudioMenu", "Musik");
                    break;
                case "picturedelete":
                    PlaylistControl.delete_PICTUREplaylist(playlistname);
                    setNewScene(event, "ImageMenu", "Bilder");
                    break;
                default:
                    //error message: home is invalid
                    System.out.println("invalid home - home is " + home);
                    break;
            }
        } else {
            //error message: no playlist selected
            System.out.println("You have to select a Playlist");
        }
    }

    /**
     * show playlists in videodirectory in playselect
     */
    private void getplayselect() {

        List<String> playlist = null;

        boolean flag = true;
        switch (home) {
            case "videoadd":
                playlist = PlaylistControl.getVideoPlaylist();
                break;
            case "musicadd":
                playlist = PlaylistControl.getMusicPlaylist();
                break;
            case "pictureadd":
                playlist = PlaylistControl.getPicturePlaylist();
                break;
            case "videodelete":
                playlist = PlaylistControl.getVideoPlaylist();
                break;
            case "musicdelete":
                playlist = PlaylistControl.getMusicPlaylist();
                break;
            case "picturedelete":
                playlist = PlaylistControl.getPicturePlaylist();
                break;

            default:
                System.out.println("invalid home - home is " + home);
                flag = false;
                break;
        }
        //goes through the provided list and writes all entries in playlistView
        if (flag) {
            for (String i : playlist) {
                i = i.replace(".xml", "");
                if (!i.equals(".directory") && !i.equals("allVideos") && !i.equals("allMusic") && !i.equals("allImages")) {
                    playlistselect.getItems().add(i);
                }
            }
        }
    }

    /**
     * calls getplayselect() when entering scene
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getplayselect();
            switch (home) {
                case "videoadd":
                    label.setText("Wähle eine Playlist aus, zu der du das Video hinzufügen willst:");
                    break;
                case "musicadd":
                    label.setText("Wähle eine Playlist aus, zu der du die Audiodatei hinzufügen willst:");
                    break;
                case "pictureadd":
                    label.setText("Wähle eine Playlist aus, zu der du das Bild hinzufügen willst:");
                    break;
                case "videodelete":
                    label.setText("Wähle eine Playlist zum Löschen aus:");
                    break;
                case "musicdelete":
                    label.setText("Wähle eine Playlist zum Löschen aus:");
                    break;
                case "picturedelete":
                    label.setText("Wähle eine Playlist zum Löschen aus:");
                    break;

                default:
                    System.out.println("invalid home - home is " + home);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
