package Client.ErrorScreen;

import Client.ClientGUI.Controller;
import Client.Client_Super_Entertainment_Pi.Client_Manager;
import Client.Configuration_File_Manager.Configuration_File_Manager;
import General.Socket_Server_Super_Entertainment_Pi.Socket_Network;
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


public class Network_Not_Available_Error_Controller extends Controller implements Initializable {


    @FXML
    TextField hostfield;

    @FXML
    TextField portfield;

    @FXML
    Label errorLabel;

    @FXML
    BorderPane MainPane;


    private String host = "";
    private String port = "";

    public void errordata() {

        host = hostfield.getText();
        port = portfield.getText();

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

        System.out.println(host + " " + port);

        try {

            Socket_Network.set_standard_port(Integer.parseInt(port));

            Socket_Network.set_hostname(host);

            Configuration_File_Manager.set_initial_Server_Port(port);

            Configuration_File_Manager.set_initial_Server_Ip_Adress(host);

            Client_Manager.re_build_socket();
        } catch (Exception ex) {
            ex.printStackTrace();
            set_error_label();
            throw new Exception();
        }

    }

    @FXML
    private void offline(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource( "../ClientGUI/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("");
        window.show();
        System.out.println("offline modus");
    }

    private void set_error_label() {
        errorLabel.setText("Die Daten stimmen nicht , bitte wiederholen Sie Ihre Eingabe");
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
