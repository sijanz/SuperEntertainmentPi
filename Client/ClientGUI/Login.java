package Client.ClientGUI;

import Client.ClientManagement.ClientManager;
import Client.ClientManagement.ClientService;
import Client.ClientManagement.User;
import General.Error.Error_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Login extends Controller implements Initializable {

    private static ActionEvent current_event = null;
    private static boolean welcomed = false;

    @FXML
    BorderPane MainPane;

    @FXML
    TextField nameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;


    @FXML
    private void logintoregister(ActionEvent event) throws Exception {
        setNewScene(event, "Register", "Registrieren");
    }


    //TODO closebutton
    @FXML
    private void login(ActionEvent event) {

        current_event = event;

        // set username and password
        User.setUsername(nameField.getText());
        User.setPassword(passwordField.getText());

        if (!User.getUsername().equals("") && !User.getPassword().equals("")) {
            if (ClientManager.sendCredentials()) {

                // set token
                User.setToken(ClientService.getLastMessageFromServer());

                loginResponse("valid");
            } else {
                errorLabel.setText("Bitte einen gültigen Namen und Passwort eingeben");
            }
        } else {
            errorLabel.setText("Bitte einen gültigen Namen und Passwort eingeben");
        }
    }


    public void loginResponse(String in) {

        switch (in) {
            case "wrongin":
                errorLabel.setText("Benutzername oder Passwort falsch");
                break;
            case "valid":
                try {
                    setNewScene(current_event, "MainMenu", "Super Entertainment Mediacenter");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "loggedIn":
                errorLabel.setText("Benutzer bereits eingeloggt");
                break;
            case "hash":
                errorLabel.setText("Hash ist inkorrekt");
                break;
            case "pswhash":
                errorLabel.setText("Passwort Hash ist inkorrekt");
                break;
            default:
                errorLabel.setText("ERROR in Login.java @loginresponse");
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Error_Handler.setLogin(this);
        if (welcomed) {
            fadeIn(MainPane, 350);
        } else {
            fadeIn(MainPane, 1000);
        }
        welcomed = true;
    }
}