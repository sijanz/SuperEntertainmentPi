package Client.ClientGUI;

import General.Error.Error_Handler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class RemoteCaller extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Willkommen!");
        Parent parent = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
        stage.setScene(new Scene(parent));
        stage.getIcons().add(new Image("Images/raspberry-pi-logo.png"));
        stage.setResizable(false);
        stage.show();
        Error_Handler.setRemoteCaller(stage);
    }
}
