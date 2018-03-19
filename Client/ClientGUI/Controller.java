package Client.ClientGUI;

import Client.ClientSuperEntertainmentPi.User;
import Client.Configuration_File_Manager.Configuration_File_Manager;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Super class for every controller, provides basic methods for them.
 *
 * @author Simon
 */
public abstract class Controller {

    @FXML
    Label clockLabel;

    @FXML
    Label ipLabel;

    @FXML
    Label serverIpLabel;

    @FXML
    Label userLabel;

    @FXML
    Button shuffleButton;

    @FXML
    Button pauseButton;

    @FXML
    Button stopButton;

    @FXML
    Button rewindButton;

    @FXML
    Button fastForwardButton;

    @FXML
    Button previousItemButton;

    @FXML
    Button nextItemButton;

    @FXML
    Button lowerButton;

    @FXML
    Button louderButton;


    /**
     * Sets a new scene on the same stage.
     *
     * @param event    the ActionEvent to pass on
     * @param fxmlName the name of the .fxml file
     * @param title    the title of the new scene
     */
    void setNewScene(Event event, String fxmlName, String title) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlName + ".fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }


    /**
     * Sets a new scene on the same stage via a button event.
     *
     * @param window   the current stage
     * @param fxmlName the name of the .fxml file
     * @param title    the title of the new scene
     */
    void setNewSceneOverButton(Stage window, String fxmlName, String title) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlName + ".fxml"));
        Scene scene = new Scene(parent);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }


    /**
     * Emmanuel: Compares two strings.
     *
     * @param element the first string
     * @param field   the second string
     * @return true if the same, false otherwise
     */
    boolean compare(String element, String field) {
        //012
        //1 4
        String[] fieldA = field.toLowerCase().split(" ");
        for (String i : fieldA) {
            if (!element.toLowerCase().contains(i)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Sets the label for the ip address, user name and the current time.
     */
    void initializeTopPanel() throws Exception {
        setIp(ipLabel);
        setServerIp(serverIpLabel);
        setUsername(userLabel);
        setTime(clockLabel);
    }


    /**
     * Retrieves the ip address.
     *
     * @return the ip address
     */
    private String getIp() throws Exception {
        String rawIp = InetAddress.getLocalHost().toString();
        int begin = rawIp.indexOf('/') + 1;
        return rawIp.substring(begin, rawIp.length());
    }


    /**
     * Retrieves the ip address of the server.
     *
     * @return the ip address of the server
     */
    private String getServerIp() throws Exception {
        return Configuration_File_Manager.get_Server_Ip_address();
    }


    /**
     * Sets the ip address to the ip label.
     *
     * @param ipLabel the label
     */
    private void setIp(Label ipLabel) throws Exception {
        ipLabel.setText(getIp());
    }


    /**
     * Sets the ip address of the server to the ip label.
     *
     * @param serverIpLabel the label
     */
    private void setServerIp(Label serverIpLabel) throws Exception {
        serverIpLabel.setText(getServerIp());
    }


    /**
     * Sets the user name to the user name label.
     *
     * @param userLabel the label
     */
    private void setUsername(Label userLabel) {
        userLabel.setText(User.getUsername());
    }


    /**
     * Retrieves the current time.
     *
     * @return the current time
     */
    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }


    /**
     * Sets the current time to the time label.
     *
     * @param clockLabel the label
     */
    private void setTime(Label clockLabel) {
        clockLabel.setText(getTime());
    }


    /**
     * Implements the transition effect.
     *
     * @param rootPane the main pane to apply the effect to
     */
    public void fadeIn(BorderPane rootPane, int duration) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(duration));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }


    /**
     * Sets tooltips to media control buttons.
     */
    void initializeMediaButtonPane() {
        shuffleButton.setTooltip(new Tooltip("Shuffle"));
        pauseButton.setTooltip(new Tooltip("Play/ Pause"));
        stopButton.setTooltip(new Tooltip("Stop"));
        rewindButton.setTooltip(new Tooltip("Zurückspulen (30 Sekunden)"));
        fastForwardButton.setTooltip(new Tooltip("Vorspulen (30 Sekunden)"));
        lowerButton.setTooltip(new Tooltip("Leiser"));
        louderButton.setTooltip(new Tooltip("Lauter"));
    }
}
