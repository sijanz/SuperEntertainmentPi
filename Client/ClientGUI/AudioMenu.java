package Client.ClientGUI;

import Client.ClientSuperEntertainmentPi.ClientManager;
import General.General_Super_Entertainment_Pi.General_File;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.Media_Super_Entertainment_Pi.Media_General;

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
import java.util.ResourceBundle;
import java.util.List;
import java.lang.String;

public class AudioMenu extends Controller implements Initializable {

    //Name of the Standard Video Playlist
    private static String allMusic = "allMusic";

    //Number of Items in current playlist
    private int NumberOfListViewItems = 0;

    static String Subnode = "Track";

    //MusicLabel
    private static String selectedAudio;

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
    private void showOutputMenu(ActionEvent event) throws Exception {
        if (selectedAudio != null) {
            ChooseOutputMenu.home = "audio";
            ChooseOutputMenu.list = listView.getItems();
            MediaPlay.setMediaType(XML_Manager.XML_NODES.AUDIO);
            ChooseOutputMenu.setMediaToPlay(selectedAudio);
            ChooseOutputMenu.setMediaType(XML_Manager.XML_NODES.AUDIO);
            MediaPlay.setMediaToPlay(selectedAudio);
            setNewScene(event, "ChooseOutputMenu", "Ausgabeart wählen");
        }
    }


    /**
     * show playlists in musicdirectory in playlistview
     */
    private void showPlaylistView() {
        // playlist contains the name of ALL files in XML_Files/music
        List<String> playlist = PlaylistControl.getMusicPlaylist();
        //writes entries in playlistview
        for (String i : playlist) {
            i = i.replace(".xml", "");
            if (!i.equals(".directory")) {
                playlistView.getItems().add(i);
            }
        }
    }

    /**
     * updates listview with content of selected playlist
     * @throws Exception e
     */
    @FXML
    private void showListView() throws Exception {
        errorLabel.setText("");

        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

            //path to selected musicplaylist
            String selectedPlaylist = playlistView.getValue();
            selectedPlaylist = PlaylistControl.noBrackets(selectedPlaylist);
            String path = XML_Shell.get_path_to_MusicDirectory() + "/" + selectedPlaylist + ".xml";

            //clear content of listview
            listView.getItems().clear();

            //get content of playlist
            List<String> playlistcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Track);

            //show content of selected playlist in listview and sets NumberofListViewItems
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
     * @throws Exception e
     */
    @FXML
    private void contentup() throws Exception {
        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {
            //check if a track is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {

                //save current playlist
                String playlist = playlistView.getValue();
                playlist = PlaylistControl.noBrackets(playlist);

                //get selected entry and entry above
                String content1 = listView.getSelectionModel().getSelectedItems().toString();
                listView.getSelectionModel().selectPrevious();
                String content2 = listView.getSelectionModel().getSelectedItems().toString();
                content1 = PlaylistControl.noBrackets(content1);
                content2 = PlaylistControl.noBrackets(content2);

                String Path = XML_Shell.get_path_to_MusicDirectory() + "/" + playlist + ".xml";
                //get complete content
                String fullcontent = General_File.return_content_of_file(Path);

                //change place of content1 and content2
                fullcontent = fullcontent.replace("<" + Subnode + ">" + content2 + "</" + Subnode + "><" + Subnode + ">" + content1 + "</" + Subnode + ">", "<" + Subnode + ">" + content1 + "</" + Subnode + "><" + Subnode + ">" + content2 + "</" + Subnode + ">");

                //save new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(fullcontent);
                printwriter.close();

                //update listview
                showListView();

            }  else {
                errorLabel.setText("Kein Titel ausgewählt");
            }
        }  else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }

    /**
     * move selected Item in listview one down
     * @throws Exception e
     */
    @FXML
    private void contentdown() throws Exception {
        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {
            //check if a track is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {

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
                String Path = XML_Shell.get_path_to_MusicDirectory() + "/" + playlist + ".xml";
                String fullcontent = General_File.return_content_of_file(Path);

                //change place of content1 and content2
                fullcontent = fullcontent.replace("<" + Subnode + ">" + content1 + "</" + Subnode + "><" + Subnode + ">" + content2 + "</" + Subnode + ">", "<" + Subnode + ">" + content2 + "</" + Subnode + "><" + Subnode + ">" + content1 + "</" + Subnode + ">");

                //save new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(fullcontent);
                printwriter.close();

                //update listview
                showListView();
            }  else {
                errorLabel.setText("Kein Titel ausgewählt");
            }
        }  else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }

    /**
     * add an Item from one playlist to another one
     * @throws Exception e
     */
    @FXML
    private void addItemToPlaylist() throws Exception {
        errorLabel.setText("");
        //check if an item is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {

            //save selected track in selectedItem in PlaylistScreen
            PlaylistScreen.selectedItem = listView.getSelectionModel().getSelectedItem();

            //change stage to PlaylistScreen
            PlaylistScreen.home = "musicadd";
            setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "PlaylistScreen", "Select Playlist");
        } else {
            errorLabel.setText("Kein Titel ausgewählt");
        }
    }

    /**
     * changes scene - input name for the new playlist
     * @author Ramin
     * @throws Exception e
     */
    @FXML
    private void createPlaylist() throws Exception {
        PlaylistName.home = "music";
        setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "PlaylistName", "Neue Playlist erstellen");

    }

    /**
     * changes scene - input name for playlist to delete
     * @author Ramin
     * @throws Exception e
     */
    @FXML
    private void deletePlaylist() throws Exception {
        PlaylistScreen.home = "musicdelete";
        setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "PlaylistScreen", "Playlist löschen");

    }

    @FXML
    private void addFolder() throws Exception{
        DirectoryChooser dChooser=new DirectoryChooser();
        File dir=dChooser.showDialog(null);
        if(dir!=null){
            ObservableList<String> list=FXCollections.observableArrayList();
            for(File file:listFilesInFolder(dir)){
                list.add(file.getName());
                ClientInterface.addAudio(file);
            }
            listView.setItems(list);
        }

    }

    private ObservableList<File> listFilesInFolder(File directory) throws Exception{
        if(directory.isDirectory()){
            ObservableList<File> list= FXCollections.observableArrayList();
            Media_General.Music_Format[] musicExtensionArray=Media_General.Music_Format.values();
            String[] extensions = new String[musicExtensionArray.length];
            for(int i=0;i<musicExtensionArray.length;i++){
                extensions[i]=musicExtensionArray[i].name().toLowerCase();
            }
            for(File file:directory.listFiles()){
                if(file.isDirectory()){
                    listFilesInFolder(file);
                }
                else{
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
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(filter);
        File file;
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            ClientInterface.addAudio(file);
        }

        if (ClientManager.retrieveIndex()) {
            setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "AudioMenu", "Musik");
        } else {
            setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }

    /**
     * delete
     * @throws IOException io
     */
    // @network
    @FXML
    private void deleteItem() throws Exception {
        errorLabel.setText("");

        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

            //check if a track is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {

                final int selectedId = listView.getSelectionModel().getSelectedIndex();

                String itemToRemove = listView.getSelectionModel().getSelectedItem();

                final int newSelectedIdx = (selectedId == listView.getItems().size() - 1) ? selectedId - 1 : selectedId;

                listView.getItems().remove(selectedId);
                listView.getSelectionModel().select(newSelectedIdx);

                //ClientInterface.deleteFile(XML_Manager.XML_NODES.AUDIO, itemToRemove);
                ClientInterface.deleteAudio(itemToRemove);

                ClientManager.retrieveIndex();
                PlaylistControl.UpdateSuperPlaylists();
            } else {
                errorLabel.setText("Kein Titel ausgewählt");
            }
        }  else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }

    @FXML
    private void deleteItemFromPlaylist() throws Exception {
        errorLabel.setText("");

        //check if a playlist is selected
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

            //check if a track is selected
            if (listView.getSelectionModel().getSelectedIndex() != -1) {

                //save Item to delete
                String ItemToDelete = listView.getSelectionModel().getSelectedItem();

                //get playlist path
                String Path = playlistView.getSelectionModel().getSelectedItem();
                Path = XML_Shell.get_path_to_MusicDirectory() + "/" + Path + ".xml";

                //save content of path in ContentInPlaylist
                String ContentInPlaylist = General_File.return_content_of_file(Path);

                //delete content
                ContentInPlaylist = ContentInPlaylist.replace("<" + Subnode + ">" + ItemToDelete + "</" + Subnode + ">", "");

                //write new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(ContentInPlaylist);
                printwriter.close();

                //update listview
                showListView();

            } else {
                errorLabel.setText("Kein Titel ausgewählt");
            }
        } else {
            errorLabel.setText("Keine Playlist ausgewählt");
        }
    }

    /**
     * @param event ActionEvent
     * @throws Exception e
     * @brief button to go back to mainmenu
     */
    @FXML
    private void showMainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }

    /**
     * @brief call when superplaylist is updated - updates all custom playlists to match respective superplaylist
     */
    static void cascadeToMusicPlaylists() throws IOException {

        //get content of allMusic
        String allMusicPath = XML_Shell.get_path_to_MusicDirectory() + "/" + allMusic + ".xml";
        List<String> allMusicContent = PlaylistControl.Parser(allMusicPath, XML_Manager.XML_SUB_NODES.Track);

        // Musicplaylist contains the name of ALL files in XML_Files/music
        List<String> Musicplaylist = PlaylistControl.getMusicPlaylist();

        //checks all MusicPlaylists
        for (String i : Musicplaylist) {

            //dont want to go through .directory and allMusic
            if (!(i.equals(".directory") || i.equals(allMusic + ".xml"))) {

                //build path of playlist
                String path = XML_Shell.get_path_to_MusicDirectory() + "/" + i;

                //get all contents of i
                List<String> allcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Track);

                //check through all contents:k of MusicPlaylist:i
                for (String k : allcontent) {

                    boolean flag = true;

                    //check if content:k is in allVideosContent set flag to false
                    for (String j : allMusicContent) {
                        if (k.equals(j)) {
                            flag = false;
                        }
                    }
                    //delete content from MusicPlaylist if it didnt match in allMusic
                    if (flag) {

                        //save content of path in ContentInPlaylist
                        String ContentInPlaylist = General_File.return_content_of_file(path);

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
     * selects a random video from the current playlist and plays it
     */
    @FXML
    private void randomPlay(ActionEvent event) throws Exception {
        int randomInt = (int) (Math.random() * NumberOfListViewItems);
        listView.getSelectionModel().select(randomInt);
        ChooseOutputMenu.mode = "random";
        showOutputMenu(event);
    }

    @FXML
    private void normalPlay(ActionEvent event) throws Exception {
        ChooseOutputMenu.mode = "normal";
        showOutputMenu(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            homeButton.setTooltip(new Tooltip("Hauptmenü"));
            addButton.setTooltip(new Tooltip("Hinzufügen..."));
            deleteButton.setTooltip(new Tooltip("Entfernen..."));
            playButton.setTooltip(new Tooltip("Wiedergabe starten..."));
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
                            selectedAudio = listView.getSelectionModel().getSelectedItem();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
