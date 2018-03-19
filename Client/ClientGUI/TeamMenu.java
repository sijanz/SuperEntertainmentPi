package Client.ClientGUI;

import Client.EasterEgg.EasterEgg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class TeamMenu extends Controller implements Initializable {

    @FXML
    BorderPane MainPane;

    @FXML
    Button homeButton;

    @FXML
    Button facebookButton;

    @FXML
    Button twitterButton;

    @FXML
    Button youtubeButton;


    @FXML
    private void showMainMenu(ActionEvent event) throws Exception {
        setNewScene(event, "MainMenu", "Super Entertainment Mediacenter");
    }

    @FXML
    private void showFacebook() throws Exception {
        openLinkInBrowser("https://www.facebook.com/uni.due");
    }

    @FXML
    private void showTwitter() throws Exception {
        openLinkInBrowser("https://twitter.com/unidue");
    }

    @FXML
    private void showYoutube() throws Exception {
        openLinkInBrowser("https://www.youtube.com/user/UDEchannel");
    }

    private static void openLinkInBrowser(String url) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        if (os.contains("win")) {
            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (os.contains("mac")) {

            rt.exec("open " + url);

        } else if (os.contains("nix") || os.contains("nux")) {

            // build a list of browsers to try
            String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};

            // build a command string which looks like "browser1 "url" || browser2 "url" ||..."
            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++)
                cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeTopPanel();
            homeButton.setTooltip(new Tooltip("Hauptmenü"));
            facebookButton.setTooltip(new Tooltip("https://www.facebook.com/uni.due"));
            twitterButton.setTooltip(new Tooltip("https://twitter.com/unidue"));
            youtubeButton.setTooltip(new Tooltip("https://www.youtube.com/user/UDEchannel"));
            fadeIn(MainPane, 350);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
