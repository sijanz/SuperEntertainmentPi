package Client.ClientGUI;

import Client.ClientManagement.ClientManager;
import General.GeneralUse.GeneralFile;
import General.Media_Super_Entertainment_Pi.Media_General;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for videoMenu.
 *
 * @author Christian, Ramin, Emmanuel, Simon
 */
public class VideoMenu extends Controller implements Initializable {

    private static String allVideos = "allVideos";
    private static int NumberOfListViewItems = 0;
    static String Subnode = "Clip";
    private static String selectedVideo;

    @FXML
    ListView<String> listView;

    @FXML
    ComboBox<String> playlistView;

    @FXML
    MenuButton deleteButton;

    @FXML
    MenuButton addButton;

    @FXML
    TextField searchField;

    @FXML
    BorderPane MainPane;

    @FXML
    Button homeButton;

    @FXML
    Label currentPlaylist;

    @FXML
    Button playButton;

    @FXML
    Label errorLabel;

    @FXML
    Button youtubePlayerButton;


    /**
     * Redirects to a menu where you can choose the output.
     *
     * @param event ActionEvent
     */
    @FXML
    private void showOutputMenu(ActionEvent event) throws Exception {
        if (selectedVideo != null) {
            ChooseOutputMenu.home = "video";
            ChooseOutputMenu.list = listView.getItems();
            MediaPlay.setMediaType(XML_Manager.XML_NODES.VIDEO);
            ChooseOutputMenu.setMediaToPlay(selectedVideo);
            ChooseOutputMenu.setMediaType(XML_Manager.XML_NODES.VIDEO);
            MediaPlay.setMediaToPlay(selectedVideo);
            setNewScene(event, "ChooseOutputMenu", "Ausgabeart wählen");
        }
    }


    /**
     * shows playlists in videoDirectory in playlistView
     */
    private void showPlaylistView() {
        // VideoPlaylist contains the name of ALL files in XML_Files/video
        List<String> VideoPlaylist = PlaylistControl.getVideoPlaylist();
        //writes entries in playlistView
        for (String i : VideoPlaylist) {
            i = i.replace(".xml", "");
            if (!i.equals(".directory")) {
                playlistView.getItems().add(i);
            }
        }
    }


    /**
     * updates listView with content of selected playlist
     */
    @FXML
    private void showListView() {
        errorLabel.setText("");
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {
            //path to selected videoPlaylist
            String selectedPlaylist = playlistView.getValue();
            System.out.println("value is " + selectedPlaylist);
            String path = XML_Shell.get_path_to_VideoDirectory() + "/" + PlaylistControl.noBrackets(selectedPlaylist) + ".xml";
            //clear content of listView
            listView.getItems().clear();
            //get content of playlist
            List<String> playlistcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Clip);
            //show content of selected playlist in listView and set NumberofListViewItems
            NumberOfListViewItems = 0;
            for (String i : playlistcontent) {
                if (compare(i, searchField.getText())) {
                    listView.getItems().add(i);
                    NumberOfListViewItems++;
                }
            }
            currentPlaylist.setText(playlistView.getValue());
            listView.getSelectionModel().selectFirst();
        }
    }


    /**
     * move selected Item in listview one up
     *
     * @throws Exception e
     * @author Christian
     */
    @FXML
    private void contentup() throws Exception {
        errorLabel.setText("");
        //check if an item is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {
            //check if a playlist is selected
            if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

                //save current playlist
                String playlist = playlistView.getValue();
                playlist = PlaylistControl.noBrackets(playlist);

                //get selected entry and entry above
                String content1 = listView.getSelectionModel().getSelectedItems().toString();
                listView.getSelectionModel().selectPrevious();
                String content2 = listView.getSelectionModel().getSelectedItems().toString();
                content1 = PlaylistControl.noBrackets(content1);
                content2 = PlaylistControl.noBrackets(content2);

                String Path = XML_Shell.get_path_to_VideoDirectory() + "/" + playlist + ".xml";
                //get complete content
                String fullcontent = GeneralFile.returnContentOfFile(Path);

                //change place of content1 and content2
                fullcontent = fullcontent.replace("<" + Subnode + ">" + content2 + "</" + Subnode + "><" + Subnode + ">" + content1 + "</" + Subnode + ">", "<" + Subnode + ">" + content1 + "</" + Subnode + "><" + Subnode + ">" + content2 + "</" + Subnode + ">");

                //save new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(fullcontent);
                printwriter.close();

                //update listview
                showListView();

            } else {
                errorLabel.setText("Kein Video ausgewählt");
            }
        } else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }


    /**
     * move selected Item in listview one down
     *
     * @throws Exception exception
     */
    @FXML
    private void contentdown() throws Exception {
        errorLabel.setText("");
        //check if an item is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {
            //check if a playlist is selected
            if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

                //save current playlist
                String playlist = playlistView.getValue();
                playlist = playlist.replace("[", "");
                playlist = playlist.replace("]", "");

                //get selected entry and entry above
                String content1 = listView.getSelectionModel().getSelectedItems().toString();
                listView.getSelectionModel().selectNext();
                String content2 = listView.getSelectionModel().getSelectedItems().toString();
                content1 = PlaylistControl.noBrackets(content1);
                content2 = PlaylistControl.noBrackets(content2);

                //get complete content
                String Path = XML_Shell.get_path_to_VideoDirectory() + "/" + playlist + ".xml";
                String fullcontent = GeneralFile.returnContentOfFile(Path);

                //change place of content1 and content2
                fullcontent = fullcontent.replace("<" + Subnode + ">" + content1 + "</" + Subnode + "><" + Subnode + ">" + content2 + "</" + Subnode + ">", "<" + Subnode + ">" + content2 + "</" + Subnode + "><" + Subnode + ">" + content1 + "</" + Subnode + ">");

                //save new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(fullcontent);
                printwriter.close();

                //update listview
                showListView();

            } else {
                errorLabel.setText("Kein Video ausgewählt");
            }
        } else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }


    /**
     * add an Item from one playlist to another one
     *
     * @throws Exception e
     */
    @FXML
    private void addItemToPlaylist() throws Exception {
        errorLabel.setText("");

        //check if an item is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {

            //save selected Video in selectedVideo in PlaylistScreen
            PlaylistScreen.selectedItem = (listView.getSelectionModel().getSelectedItem());

            //change stage to PlaylistScreen
            PlaylistScreen.home = "videoadd";
            setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "PlaylistScreen", "Wähle eine Playlist");
        } else {
            errorLabel.setText("Kein Video ausgewählt");
        }
    }

    /**
     * deletes selected Item from selected Playlist
     *
     * @throws Exception e
     */
    @FXML
    private void deleteItemFromPlaylist() throws Exception {
        errorLabel.setText("");
        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {
            //check if a video is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {
                //save Item to delete
                String ItemToDelete = listView.getSelectionModel().getSelectedItem();
                System.out.println(ItemToDelete);
                //get playlist path
                String Path = playlistView.getSelectionModel().getSelectedItem();
                Path = XML_Shell.get_path_to_VideoDirectory() + "/" + Path + ".xml";
                //save content of path in ContentInPlaylist
                String ContentInPlaylist = GeneralFile.returnContentOfFile(Path);
                //delete content
                ContentInPlaylist = ContentInPlaylist.replace("<" + Subnode + ">" + ItemToDelete + "</" + Subnode + ">", "");
                //write new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(ContentInPlaylist);
                printwriter.close();

                //update listview
                showListView();
            } else {
                errorLabel.setText("Kein Video ausgewaehlt");
            }
        } else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }


    /**
     * changes scene - input name for the new playlist
     *
     * @throws Exception e
     * @author Ramin
     */
    @FXML
    private void createPlaylist() throws Exception {
        PlaylistName.home = "video";
        setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "PlaylistName", "Neue Playlist erstellen");
    }


    /**
     * changes scene - input name for playlist to delete
     *
     * @throws Exception e
     * @author Ramin
     */
    @FXML
    private void deletePlaylist() throws Exception {
        PlaylistScreen.home = "videodelete";
        setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "PlaylistScreen", "Playlist löschen");
    }


    /**
     * upload Folder to Server
     *
     * @throws Exception e
     */
    @FXML
    private void addFolder() throws Exception {
        DirectoryChooser dChooser = new DirectoryChooser();
        File dir = dChooser.showDialog(null);
        if (dir != null) {
            ObservableList<String> list = FXCollections.observableArrayList();
            for (File file : listFilesInFolder(dir)) {
                list.add(file.getName());
                ClientInterface.addVideo(file);
            }
            listView.setItems(list);
        }
    }

    private ObservableList<File> listFilesInFolder(File directory) throws Exception {
        if (directory.isDirectory()) {
            ObservableList<File> list = FXCollections.observableArrayList();
            Media_General.Video_Format[] videoExtensionArray = Media_General.Video_Format.values();
            String[] extensions = new String[videoExtensionArray.length];
            for (int i = 0; i < videoExtensionArray.length; i++) {
                extensions[i] = videoExtensionArray[i].name().toLowerCase();
            }
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    listFilesInFolder(file);
                } else {
                    for (String extension : extensions) {
                        if (file.getName().endsWith(extension)) {
                            list.add(file);
                        }
                    }
                }
            }
            return list;
        }
        return null;
    }


    // @network
    @FXML
    private void addItem() throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file;
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            ClientInterface.addVideo(file);
        }

        if (ClientManager.retrieveIndex()) {
            setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "VideoMenu", "Videos");
        } else {
            setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }

    /**
     * if allVideos is selected it will delete the file from the server
     * if any other playlist is selected it will only delete the entry from the list
     *
     * @throws IOException io
     */
    // @network
    @FXML
    private void deleteItem() throws Exception {
        errorLabel.setText("");

        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

            //check if a video is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {

                final int selectedId = listView.getSelectionModel().getSelectedIndex();

                String itemToRemove = listView.getSelectionModel().getSelectedItem();

                final int newSelectedIdx = (selectedId == listView.getItems().size() - 1) ? selectedId - 1 : selectedId;

                listView.getItems().remove(selectedId);
                listView.getSelectionModel().select(newSelectedIdx);

                //ClientInterface.deleteFile(XML_Manager.XML_NODES.VIDEO, itemToRemove);
                ClientInterface.deleteVideo(itemToRemove);

                ClientManager.retrieveIndex();
                PlaylistControl.UpdateSuperPlaylists();
            } else {
                errorLabel.setText("Kein Video ausgewählt");
            }
        } else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }


    /**
     * call when superplaylist is updated - updates all custom playlists to match respective superplaylist
     *
     * @throws IOException io
     */
    static void cascadeToVideoPlaylists() throws IOException {

        //get content of allVideos
        String allVideosPath = XML_Shell.get_path_to_VideoDirectory() + "/" + allVideos + ".xml";
        List<String> allVideosContent = PlaylistControl.Parser(allVideosPath, XML_Manager.XML_SUB_NODES.Clip);

        // Videoplaylist contains the name of ALL files in XML_Files/video
        List<String> Videoplaylist = PlaylistControl.getVideoPlaylist();

        //checks all VideoPlaylists
        for (String i : Videoplaylist) {

            //dont want to go through .directory and allVideos
            if (!(i.equals(".directory") || i.equals(allVideos + ".xml"))) {

                //build path of playlist
                String path = XML_Shell.get_path_to_VideoDirectory() + "/" + i;

                //get all contents of i
                List<String> allcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Clip);

                //check through all contents:k of VideoPlaylist:i
                for (String k : allcontent) {

                    boolean flag = true;

                    //check if content:k is in allVideosContent set flag to false
                    for (String j : allVideosContent) {
                        if (k.equals(j)) {
                            flag = false;
                        }
                    }
                    //delete content from VideoPlaylist if it didnt match in allVideos
                    if (flag) {

                        //save content of path in ContentInPlaylist
                        String ContentInPlaylist = GeneralFile.returnContentOfFile(path);

                        //delete content
                        ContentInPlaylist = ContentInPlaylist.replace("<" + Subnode + ">" + k + "</" + Subnode + ">", "");

                        System.out.println("content deleted");

                        //write new content
                        PrintWriter printwriter = new PrintWriter(path);
                        printwriter.write(ContentInPlaylist);
                        printwriter.close();
                    }
                }
            }
        }
    }


    /**
     * playbutton for normal play
     *
     * @param event ActionEvent
     * @throws Exception e
     */
    @FXML
    private void normalPlay(ActionEvent event) throws Exception {
        ChooseOutputMenu.mode = "normal";
        showOutputMenu(event);
    }


    /**
     * playbutton for random order play
     *
     * @param event ActionEvent
     * @throws Exception e
     */
    @FXML
    private void randomPlay(ActionEvent event) throws Exception {
        //select random entry in listview
        int randomInt = (int) (Math.random() * NumberOfListViewItems);
        listView.getSelectionModel().select(randomInt);

        ChooseOutputMenu.mode = "random";

        //activate play
        showOutputMenu(event);
    }

    /**
     * button to go back to mainmenu
     *
     * @param event ActionEvent
     * @throws Exception e
     */
    @FXML
    private void showMainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }


    @FXML
    private void showYoutubeMenu(ActionEvent event) throws Exception {
        setNewScene(event, "YouTubeLink", "YouTube-Player");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ClientManager.create_index();
        errorLabel.setText("");

        try {
            initializeTopPanel();
            homeButton.setTooltip(new Tooltip("Hauptmenü"));
            addButton.setTooltip(new Tooltip("Hinzufügen..."));
            deleteButton.setTooltip(new Tooltip("Entfernen..."));
            playButton.setTooltip(new Tooltip("Wiedergabe starten..."));
            youtubePlayerButton.setTooltip(new Tooltip("YouTube-Player starten..."));
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PlaylistControl.UpdateSuperPlaylists();
            showPlaylistView();
            playlistView.getSelectionModel().select(0);
            currentPlaylist.setText(playlistView.getValue());
            showListView();
            listView.getSelectionModel().selectFirst();

            listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (MouseButton.PRIMARY.equals(event.getButton())) {
                        if (event.getClickCount() == 1) {
                            selectedVideo = listView.getSelectionModel().getSelectedItem();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
