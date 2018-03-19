package Client.ClientGUI;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


/**
 * Controller for the welcome screen, leads to the login screen.
 *
 * @author Simon
 */
public class WelcomeScreen extends Controller {


    /**
     * Loads the login screen.
     *
     * @param event MouseEvent to trigger the scene change
     */
    @FXML
    private void loadLogin(MouseEvent event) throws Exception {
        setNewScene(event, "Login", "Login");
    }
}
