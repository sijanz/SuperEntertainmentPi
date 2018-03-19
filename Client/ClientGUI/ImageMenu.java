package Client.ClientGUI;

import Client.Client_Super_Entertainment_Pi.Client_Manager;
import General.General_Super_Entertainment_Pi.General_Directory;
import General.General_Super_Entertainment_Pi.General_File;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.Media_Super_Entertainment_Pi.Media_General;


import General.XML_Service_Super_Entertainment_Pi.XML_Shell_Working;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class ImageMenu extends Controller implements Initializable {

    //Name of the standard imagePlaylist
    private static String allImages = "allImages";

    //Number of Items in current playlist
    private int NumberOfListViewItems = 0;

    static String Subnode = "Image";

    private static String selectedImage;
    private int pos=0;
    private boolean isSlideShow=false;

    private boolean b;

    @FXML
    ListView<String> listView;

    @FXML
    ListView<String> listView1;

    @FXML
    ComboBox<String> playlistView;

    @FXML
    Button playButton;

    @FXML
    MenuButton deleteButton;

    @FXML
    MenuButton addButton;

    @FXML
    TextField searchField;

    @FXML
    ImageView imageView;

    @FXML
    ImageView imageView1;

    @FXML
    BorderPane MainPane;

    @FXML
    Button homeButton;

    @FXML
    Label currentPlaylist;

    @FXML
    Button slideshowButton;


    @FXML
    private void showImageOnPi(ActionEvent event) throws Exception {
        if (selectedImage != null) {
            ChooseOutputMenu.list = listView.getItems();
            MediaPlay.setMediaType(XML_Manager.XML_NODES.PICTURE);
            MediaPlay.setMediaToPlay(selectedImage);
            ClientInterface.playPicture(selectedImage);
            setNewScene(event, "ImagePlay", "Wiedergabe");
        }
    }

    /**
     * shows playlists in imagedirectory in playlistview
     */
    private void showPlaylistView() {
        // ImagePlaylist contains the name of ALL files in XML_Files/video
        List<String> ImagePlaylist = PlaylistControl.getPicturePlaylist();
        //writes entries in playlistview
        for (String i : ImagePlaylist) {
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
        if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

            //path to selected ImagePlaylist
            String selectedPlaylist = playlistView.getValue();
            selectedPlaylist = PlaylistControl.noBrackets(selectedPlaylist);
            String path = XML_Shell.get_path_to_PictureDirectory() + "/" + selectedPlaylist + ".xml";

            //clear content of listview
            listView.getItems().clear();

            //get content of playlist
            List<String> playlistcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Image);

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

                String Path = XML_Shell.get_path_to_PictureDirectory() + "/" + playlist + ".xml";
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

            }
        }
    }

    /**
     * move selected Item in listView one down
     * @throws Exception exception
     */
    @FXML
    private void contentdown() throws Exception {
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
                String Path = XML_Shell.get_path_to_PictureDirectory() + "/" + playlist + ".xml";
                String fullcontent = General_File.return_content_of_file(Path);

                //change place of content1 and content2
                fullcontent = fullcontent.replace("<" + Subnode + ">" + content1 + "</" + Subnode + "><" + Subnode + ">" + content2 + "</" + Subnode + ">", "<" + Subnode + ">" + content2 + "</" + Subnode + "><" + Subnode + ">" + content1 + "</" + Subnode + ">");

                //save new content
                PrintWriter printwriter = new PrintWriter(Path);
                printwriter.write(fullcontent);
                printwriter.close();

                //update listview
                showListView();

            }
        }
    }


    /**
     * add an existing Item to a Playlist
     * @throws Exception e
     */
    @FXML
    private void addItemToPlaylist() throws Exception {
        //check if an item is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {
            //save selected Image in selectedVideo in PlaylistScreen
            PlaylistScreen.selectedItem = (listView.getSelectionModel().getSelectedItem());
            //change stage to PlaylistScreen
            PlaylistScreen.home = "pictureadd";
            setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "PlaylistScreen", "Select Playlist");
        }
    }


    /**
     * changes scene - input name for the new playlist
     * @author Ramin
     * @throws Exception e
     */
    @FXML
    private void createPlaylist() throws Exception {

        PlaylistName.home = "picture";
        setNewSceneOverButton((Stage)addButton.getScene().getWindow(), "PlaylistName", "Neue Playlist erstellen");

    }

    /**
     * changes scene - input name for playlist to delete
     * @author Ramin
     * @throws Exception e
     */
    @FXML
    private void deletePlaylist() throws Exception {

        PlaylistScreen.home = "picturedelete";
        setNewSceneOverButton((Stage)addButton.getScene().getWindow(),"PlaylistScreen", "Playlist löschen");

    }


    @FXML
    private void addFolder() throws Exception {
        DirectoryChooser dChooser=new DirectoryChooser();
        File dir=dChooser.showDialog(null);
        if(dir!=null){
            List<File> list = listFilesInFolder(dir);
            System.out.println(list);
            for(File file: list){
                System.out.println(file);
                listView.getItems().add(file.getName());
                copyFile(file);
                ClientInterface.addPicture(file);
            }

        }

    }

    private ObservableList<File> listFilesInFolder(File directory)throws Exception{
        if(directory.isDirectory()){
            ObservableList<File> list= FXCollections.observableArrayList();
            Media_General.Picture_Format[] pictureExtensionArray=Media_General.Picture_Format.values();
            String[] extensions = new String[pictureExtensionArray.length];
            for(int i=0;i<pictureExtensionArray.length;i++){
                extensions[i]=pictureExtensionArray[i].name().toLowerCase();
            }
            for(File file:directory.listFiles()){
                for (String extension : extensions) {
                    if (file.getName().endsWith(extension)) {
                        list.add(file);
                    }
                }
            }
            return list;
        }
        return null;
    }

    /**
     * playbutton for normal play
     * @param event ActionEvent
     * @throws Exception exception
     */
    @FXML
    private void normalPlay(ActionEvent event) throws Exception {
        ChooseOutputMenu.mode = "normal";
        showOutputMenu(event);
    }


    /**
     * selects a random image from the current playlist and plays it
     */
    @FXML
    private void randomPlay(ActionEvent event) throws Exception {
        //select random entry in listview
        int randomInt = (int) (Math.random() * NumberOfListViewItems);
        listView.getSelectionModel().select(randomInt);
        ChooseOutputMenu.mode = "random";

        showOutputMenu(event);
    }

    @FXML
    private void showOutputMenu(ActionEvent event) throws Exception {
      if (selectedImage != null) {
            ChooseOutputMenu.home = "image";
            ChooseOutputMenu.list = listView.getItems();
            MediaPlay.setMediaType(XML_Manager.XML_NODES.AUDIO);
            ChooseOutputMenu.setMediaToPlay(selectedImage);
            ChooseOutputMenu.setMediaType(XML_Manager.XML_NODES.AUDIO);
            MediaPlay.setMediaToPlay(selectedImage);
            setNewScene(event, "ChooseOutputMenu", "Ausgabeart wählen");
        }
    }

    /**
     * upload selected file to server
     * @throws Exception e
     */
    @FXML
    private void addItem() throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.png, *.jpeg, *.jpg, *.gif)", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(filter);
        File file;
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            ClientInterface.addPicture(file);
            copyFile(file);
        }

        if (Client_Manager.retrieveIndex()) {
            setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "ImageMenu", "Bilder");
        } else {
            setNewSceneOverButton((Stage) addButton.getScene().getWindow(), "AuthenticationFailed", "Authentifizierung fehlgeschlagen!");
        }
    }


    private static void copyFile(File file) throws IOException{
        String str="myImages";
        File copyFile=new File(str+"/"+file.getName());
        try (InputStream inStream = new FileInputStream(file); OutputStream outStream = new FileOutputStream(copyFile)) {
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
        }
    }


    @FXML
    private void showImage() throws Exception{
        String str=listView.getSelectionModel().getSelectedItem();
        String available=checkImageInMyImage(str);
        if(available!=null) {
            File file =  new File (System.getProperty("user.dir") + "/myImages/" + available);
            Image image = new Image(new FileInputStream(file),imageView.getFitWidth(), imageView.getFitHeight(), true, true);
            imageView.setImage(image);
            if(isSlideShow){
                stopSlideShow();
            }

        }
        else{
            //no match was found in myImage
            imageView.setImage(null);
        }
    }


    private String checkImageInMyImage(String selectedImageName){
        if(selectedImageName!=null){
            String myImagePath=System.getProperty("user.dir") + "/myImages";
            File myImage=new File(myImagePath);
            String[] content =myImage.list();
            for(String i:content){
                if(i.equals(selectedImageName)){
                    return i;
                }
            }
        }
        return null;
    }


    @FXML
    private void imagePopup(MouseEvent event){
        try{
            String str=listView1.getSelectionModel().getSelectedItem();
            String available=checkImageInMyImage(str);
            if(MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount()==2 && available!=null){
                ImageView imageView = new ImageView();
                File file =  new File (System.getProperty("user.dir") + "/myImages/" + available);
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);
                imageView.setStyle("-fx-background-color: BLACK");
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);


                HBox hBox=new HBox();//add actions such as rotating image here
                hBox.setAlignment(Pos.CENTER);

                Button rotateImage=new Button("Rotate");
                Slider zoomImage=new Slider(0.2,1.3,0.8);
                hBox.getChildren().addAll(rotateImage,zoomImage);

                BorderPane mainPane=new BorderPane();
                mainPane.setTop(hBox);
                BorderPane imagePane=new BorderPane();
                mainPane.setCenter(imagePane);
                ScrollPane scrollPane=new ScrollPane();
                scrollPane.setPannable(true);
                Group g=new Group();
                g.getChildren().add(imageView);
                imagePane.setCenter(scrollPane);
                scrollPane.setContent(g);
                scrollPane.setStyle("-fx-background-color: BLACK");

                rotateImage.setOnAction(new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent arg0) {
                        // TODO Auto-generated method stub
                        imageView.setRotate(imageView.getRotate()+90);
                    }

                });

                zoomImage.valueProperty().addListener(new ChangeListener<Number>(){

                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        // TODO Auto-generated method stub
                        imageView.setScaleX(newValue.doubleValue());
                        imageView.setScaleY(newValue.doubleValue());
                    }

                });

                Stage newStage = new Stage();
                newStage.setFullScreen(true);
                newStage.setTitle(file.getName());
                imageView.setFitHeight(newStage.getHeight() - 100);

                Scene scene = new Scene(mainPane);
                newStage.setScene(scene);
                newStage.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private ArrayList<Image> getImages() throws Exception{
        ObservableList<String> list=listView.getItems();
        ArrayList<Image> imageArrayList=new ArrayList<Image>();
        String imagePath;
        for(String str:list){
            imagePath=System.getProperty("user.dir") + "/myImages/" + checkImageInMyImage(str);
            if(imagePath!=null){
                Image image=new Image(new FileInputStream(new File(imagePath)));
                imageArrayList.add(image);
            }
        }
        return imageArrayList;
    }

    @FXML
    private void slideShow() throws Exception{
        boolean isTimerCanceled=false;
        ArrayList<Image> list=getImages();
        if(isSlideShow==false && list.size()>1){
            Timer timer=new Timer();
            TimerTask task=new TimerTask(){

                @Override
                public void run() {

                    isSlideShow=true;
                    imageView1.setImage(list.get(pos++));
                    if(pos>=list.size() || b==true){
                        //stop the slideshow
                        //also when key is pressed
                        isSlideShow=false;//so that i can come later and click slideshow again
                        timer.cancel();
                        pos=0;
                        b=false;
                    }
                }

            };
            long period=3000;
            timer.schedule(task, 0, period);
            if(isTimerCanceled==true){
                task.cancel();
            }
        }
    }

    @FXML
    private void stopSlideShow(){
        if( isSlideShow==true){
            b=true;
        }
    }

    /**
     * if allVideos is selected it will delete the file from the server
     * if any other playlist is selected it will only delete the entry from the list
     * @throws Exception e
     */
    // @network
    @FXML
    private void deleteItem() throws Exception {
        //check if a image is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {

            //check if a playlist is selected
            if (playlistView.getSelectionModel().getSelectedIndex() != -1) {

                final int selectedId = listView.getSelectionModel().getSelectedIndex();

                String itemToRemove = listView.getSelectionModel().getSelectedItem();

                final int newSelectedIdx = (selectedId == listView.getItems().size() - 1) ? selectedId - 1 : selectedId;

                listView.getItems().remove(selectedId);
                listView.getSelectionModel().select(newSelectedIdx);

                //ClientInterface.deleteFile(XML_Manager.XML_NODES.PICTURE, itemToRemove);
                ClientInterface.deletePicture(itemToRemove);

                Client_Manager.retrieveIndex();
                PlaylistControl.UpdateSuperPlaylists();
            }
        }
    }

    /*possible problems: wrong path, name of contentfile differs from name in contentlist even so same entry
     *
     */

    private void updatePictures() {
        //get content
        List<String> contentList = PlaylistControl.Parser(XML_Shell_Working.get_path_of_index_XML_File(), XML_Manager.XML_SUB_NODES.Image);
        List<String> contentFile = General_Directory.get_all_Files_from_path("myImages/");

        for(String file: contentFile) {
            int counter = 0;
            //find match
            for(String list: contentList) {
                if(file.equals(list)) {
                    ++counter;
                }
            }
            //delete file
            if (counter == 0 && !file.equals(".directory")) {
                General_File.delete_file("myImages/" + file);
            }
        }
    }

    @FXML
    private void deleteItemFromPlaylist() throws Exception {
        //check if a image is selected
        if (listView.getSelectionModel().getSelectedIndex() != -1) {

            //check if a playlist is selected
            if (playlistView.getSelectionModel().getSelectedIndex() != -1) {
                //save Item to delete
                String ItemToDelete = listView.getSelectionModel().getSelectedItem();

                //get playlist path
                String Path = playlistView.getSelectionModel().getSelectedItem();
                Path = XML_Shell.get_path_to_PictureDirectory() + "/" + Path + ".xml";

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
            }
        }
    }

        /**
         * call when superplaylist is updated
         * updates all custom playlists to match respective superplaylist
         */
    static void cascadeToImagePlaylists() throws IOException {

        //get content of allVideos
        String allImagePath = XML_Shell.get_path_to_PictureDirectory() + "/" + allImages + ".xml";
        List<String> allImageContent = PlaylistControl.Parser(allImagePath, XML_Manager.XML_SUB_NODES.Image);

        // imageoplaylist contains the name of ALL files in XML_Files/video
        List<String> imageplaylist = PlaylistControl.getPicturePlaylist();

        //checks all VideoPlaylists
        for (String i : imageplaylist) {

            //dont want to go through .directory and allVideos
            if (!(i.equals(".directory") || i.equals(allImages + ".xml"))) {

                //build path of playlist
                String path = XML_Shell.get_path_to_PictureDirectory() + "/" + i;

                //get all contents of i
                List<String> allcontent = PlaylistControl.Parser(path, XML_Manager.XML_SUB_NODES.Image);

                //check through all contents:k of ImagePlaylist:i
                for (String k : allcontent) {

                    boolean flag = true;

                    //check if content:k is in allVideosContent set flag to false
                    for (String j : allImageContent) {
                        if (k.equals(j)) {
                            flag = false;
                        }
                    }
                    //delete content from ImagePlaylist if it didnt match in allVideos
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
     * @param event ActionEvent
     * @throws Exception e
     * @brief back button
     */
    @FXML
    private void showMainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            homeButton.setTooltip(new Tooltip("Hauptmenü"));
            playButton.setTooltip(new Tooltip("Bild auf dem Raspberry Pi anzeigen"));
            addButton.setTooltip(new Tooltip("Hinzufügen..."));
            deleteButton.setTooltip(new Tooltip("Entfernen..."));
            slideshowButton.setTooltip(new Tooltip("Slideshow starten"));
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PlaylistControl.UpdateSuperPlaylists();
            updatePictures();
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
                            selectedImage = listView.getSelectionModel().getSelectedItem();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
