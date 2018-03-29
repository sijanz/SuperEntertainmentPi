package Client.ErrorScreen;

import Client.ClientGUI.Controller;
import Client.ClientManagement.ClientManager;
import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.SocketNetwork.SocketNetwork;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class NetworkNotAvailableErrorController extends Controller implements Initializable {


    @FXML
    TextField hostField;

    @FXML
    TextField portField;

    @FXML
    Label errorLabel;

    @FXML
    BorderPane MainPane;


    private String host = "";
    private String port = "";


    public void errorData() {
        host = hostField.getText();
        port = portField.getText();

        if (!host.equals("") && !port.equals("")) {
            try {
                setData();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setData() throws Exception {

        // @debug
        System.out.println(host + " " + port);

        try {
            SocketNetwork.setStandardPort(Integer.parseInt(port));
            SocketNetwork.setHostname(host);
            ConfigurationFileManager.setInitialServerPort(port);
            ConfigurationFileManager.setInitialServerIpAddress(host);
            ClientManager.rebuildSocket();
        } catch (Exception ex) {
            ex.printStackTrace();
            setErrorLabel();
            throw new Exception();
        }
    }


    @FXML
    private void initiateOfflineMode(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource( "../ClientGUI/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("");
        window.show();
        System.out.println("offline modus");
    }

    private void setErrorLabel() {
        errorLabel.setText("Die Daten stimmen nicht, bitte wiederholen Sie Ihre Eingabe");
    }


    @FXML
    private void backToLogin(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource( "../ClientGUI/Login.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Login");
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeIn(MainPane, 350);
    }
}
