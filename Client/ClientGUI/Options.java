package Client.ClientGUI;

import Client.Client_Super_Entertainment_Pi.Client_Manager;
import Client.Client_Super_Entertainment_Pi.User;
import Client.EasterEgg.EasterEgg;
import Client.EmbeddedPlayer.EmbeddedPlayerStarter;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import javafx.fxml.FXML;
import Client.Client_Super_Entertainment_Pi.Client_Service;
import General.General_Super_Entertainment_Pi.General_Purpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Options extends Controller implements Initializable {

    @FXML
    BorderPane MainPane;
    @FXML
    Label labelPsw;
    @FXML
    PasswordField old;
    @FXML
    PasswordField new1;
    @FXML
    PasswordField new2;
    @FXML
    Button easterEggButton;

    private static boolean passwordFlag;

    @FXML
    private void mainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }

    @FXML
    private void EntryScreen(ActionEvent event) throws Exception {
        setNewScene(event, "OptionsEntry", "Einstellungen");
    }

    //change password of current account
    @FXML
    private void changePsw(ActionEvent event) throws Exception {
        passwordFlag = true;
        setNewScene(event, "OptionsChangePsw", "Einstellungen Passwort ändern");
    }

    //delete current account
    @FXML
    private void deleteAcc(ActionEvent event) throws Exception {
        Client_Manager.deleteUser();
        setNewScene(event, "Login", "Login");
    }

    //logout user and go to login screen
    @FXML
    private void logout(ActionEvent event) throws Exception {
        Client_Manager.logout();
        setNewScene(event, "Login", "Login");
    }

    @FXML
    private void checkChangePsw(ActionEvent event) throws Exception {
        labelPsw.setText("");
        // if new1 and new2 match
        if (new1.getText().equals(new2.getText())) {

            Client_Manager.changePassword(new1.getText());

            passwordFlag = false;
            setNewScene(event, "OptionsEntry", "Einstellungen");

        } else {
            labelPsw.setText("Die Passwörter stimmen \n nicht überein");
        }
    }

    @FXML
    private void changeToken() {
        System.out.println("old token: " + User.getToken());
        User.setToken("test");
        System.out.println("new token: " + User.getToken());
    }

    @FXML
    private void runEasterEgg() {
        new Thread(new EasterEgg()).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!passwordFlag) {
            easterEggButton.setTooltip(new Tooltip("Easter Egg"));
        }
        try {
            initializeTopPanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fadeIn(MainPane, 350);
    }
}
