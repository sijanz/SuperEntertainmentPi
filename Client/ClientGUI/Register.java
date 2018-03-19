package Client.ClientGUI;

import Client.ClientSuperEntertainmentPi.ClientManager;
import Client.ClientSuperEntertainmentPi.User;
import General.Error.Error_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

// TODO: label
public class Register extends Controller implements Initializable{

    @FXML
    BorderPane MainPane;
    @FXML
    TextField nameField;
    @FXML
    PasswordField passwordField1;
    @FXML
    PasswordField passwordField2;
    @FXML
    Label errorLabel;

    private static ActionEvent current_event = null;

    @FXML
    private void registertologin(ActionEvent event) throws Exception {setNewScene(event, "Login", "Login - Super Entertainment Pi");}

    @FXML
    private void register(ActionEvent event) throws  Exception {

        Error_Handler.setRegister(this);

        errorLabel.setText("");

        if(nameField.getText().equals("")) {
            errorLabel.setText("Gib einen Namen ein");
        } else if(passwordField1.getText().equals("") || passwordField2.getText().equals("")) {
            errorLabel.setText("Gib ein Passwort ein");
        } else if (passwordField1.getText().equals(passwordField2.getText())){
            current_event = event;

            // set credentials
            User.setUsername(nameField.getText());
            User.setPassword(passwordField1.getText());

            if(ClientManager.register()) {
                registerResponse("jumpToLogin");
            } else {
                errorLabel.setText("Bitte versuchen Sie es erneut");
            }

        } else {
            errorLabel.setText("Die Passwörter stimmen nicht überein!");
        }
    }

    public void registerResponse(String in) {

        switch (in) {
            case "jumpToLogin":
                try {
                    setNewScene(current_event, "Login", "Login");
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "taken":
                errorLabel.setText("Der Benutzername ist bereits vergeben.");
                break;
            case "cannotRegister":
                errorLabel.setText("Registrierung fehlgeschlagen. Bitte versuchen sie es erneut");
                break;
            default:
                errorLabel.setText("ERROR in Login.java @loginresponse");
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeIn(MainPane, 350);
    }
}
