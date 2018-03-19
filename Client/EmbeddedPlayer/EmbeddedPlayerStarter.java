package Client.EmbeddedPlayer;

import Client.Configuration_File_Manager.Configuration_File_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;

import javax.swing.*;


/**
 * The only purpose of this class is to create a new JFrame before starting the EmbeddedPlayer because of a bug in the vlcj-framework.
 *
 * @author Simon
 */
public class EmbeddedPlayerStarter {


    /**
     * Starts a new instance of the EmbeddedPlayer.
     *
     * @param mediaType the type of media to be played
     * @param mediaName the name of the media file to be played
     */
    public static void startEmbeddedPlayer(XML_Manager.XML_NODES mediaType, String mediaName) {

        // please eat this JFrame, garbage collector.
        new JFrame();

        // start the player
        new EmbeddedPlayer(createMrl(mediaType, mediaName), false);
    }

    public static void startYouTubePlayer(String url) {
        new JFrame();

        new EmbeddedPlayer(url, true);
    }


    /**
     * Creates a MRL out of the type of the media, the name of the media and the ip address of the server.
     *
     * @param mediaType type of the media
     * @param mediaName name of the media
     * @return the created MRL
     */
    static String createMrl(XML_Manager.XML_NODES mediaType, String mediaName) {
        String mrl = "smb://";
        mrl += Configuration_File_Manager.get_Server_Ip_address();
        switch (mediaType) {
            case VIDEO:
                mrl += "/video/";
                break;
            case AUDIO:
                mrl += "/music/";
                break;
            case PICTURE:
                mrl += "/picture/";
                break;
        }
        mrl += mediaName;
        EmbeddedPlayer.setMediaType(mediaType);

        return mrl;
    }
}
