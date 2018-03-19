package General.Error;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @author Christian
 */
public class ErrorCaller extends Client.ClientGUI.Controller {


    void showErrorSalt(Stage stage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("SaltError.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showErrorAction(Stage stage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("NotPermittedActionError.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    void showErrorKey(Stage stage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("KeyError.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void close() {
        try {
            //ClientInterface.un_log_in();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
