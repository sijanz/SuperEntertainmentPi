package Client.ClientGUI;

import Client.ClientManagement.ClientManager;
import Client.ClientManagement.ClientService;
import General.GeneralUse.GeneralFile;
import General.GeneralUse.GeneralPurpose;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_NODES;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager.XML_SUB_NODES;

import java.io.*;


/**
 * Contains all methods that are called by a controller and provides an application programming interface for the
 * communication with the server.
 *
 * @author Simon
 */
class ClientInterface {


    /**
     * Creates a xml file that includes the command to save a video on the server.
     *
     * @param video the video to save
     */
    static void deleteVideo(String video) throws Exception {
        deleteFile(XML_NODES.Delete_VIDEO, video);
    }


    /**
     * Creates a xml file that includes the command to save an audio file on the server.
     *
     * @param audio the audio file to save
     */
    static void deleteAudio(String audio) throws Exception {
        deleteFile(XML_NODES.Delete_MUSIC, audio);
    }


    /**
     * Creates a xml file that includes the command to save a video on the server.
     *
     * @param picture the picture to save
     */
    static void deletePicture(String picture) throws Exception {
        deleteFile(XML_NODES.Delete_PICTURE ,  picture);
    }


    /**
     * Creates a xml file that includes the command to save a video on the server.
     *
     * @param video the video to save
     */
    static void addVideo(File video) throws Exception {
        addFile(video, XML_NODES.UPLOAD_Video, XML_SUB_NODES.Clip);
    }


    /**
     * Creates a xml file that includes the command to save an audio file on the server.
     *
     * @param audio the audio file to save
     */
    static void addAudio(File audio) throws Exception {
        addFile(audio, XML_NODES.UPLOAD_Audio, XML_SUB_NODES.Track);
    }


    /**
     * Creates a xml file that includes the command to save a video on the server.
     *
     * @param picture the picture to save
     */
    static void addPicture(File picture) throws Exception {
        addFile(picture, XML_NODES.UPLOAD_Picture, XML_SUB_NODES.Image);
    }


    /**
     * Creates a xml file that includes the command to save a video on the server.
     *
     * @param videoName the video to delete
     */
    private static void deleteFile(XML_Manager.XML_NODES mediaType, String videoName) throws IOException {
        GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());

        XML_Manager.create_Message(XML_NODES.COMMAND, XML_Shell.get_path_of_communication_XML_File());

        XML_Manager.append_new_Message(XML_NODES.COMMAND, XML_SUB_NODES.Delete, XML_Shell.get_path_of_communication_XML_File(), videoName);

        ClientManager.rebuildSocket();

        // send XML file to server
        ClientService.sendMessageToServer(ClientManager.getClientSocket(), GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));

        // wait for confirmation
       // ClientService.receiveMessageFromServer(ClientManager.getClientSocket());
    }


    /**
     * Helper method to create and send a xml file to upload a media file.
     *
     * @param media      the media file
     * @param uploadType (XML_NODES.UPLOAD_Video | XML_NODES.UPLOAD_Audio | XML_NODES.UPLOAD_Picture)
     * @param fileType   (Clip | Tracḱ | Image)
     */
    private static void addFile(File media, XML_NODES uploadType, XML_SUB_NODES fileType) throws Exception {

        // delete old communication.xml
        GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());

        // create xml file
        XML_Manager.create_Message(uploadType, XML_Shell.get_path_of_communication_XML_File());


        XML_Manager.append_new_Message(uploadType, fileType, XML_Shell.get_path_of_communication_XML_File(), media.getName());
        XML_Manager.append_new_Message(uploadType, XML_SUB_NODES.Upload_File_Size, XML_Shell.get_path_of_communication_XML_File(),
                media.length() + "");

        XML_Manager.append_new_Message(uploadType, XML_SUB_NODES.User_Name, XML_Shell.get_path_of_communication_XML_File(), "Bernd");
        XML_Manager.append_new_Message(uploadType, XML_Manager.XML_SUB_NODES.Hash_of_the_File, XML_Shell.get_path_of_communication_XML_File(), GeneralPurpose.createHashOfFile(media));


        // @debug
        System.out.println(GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()) + " <  ------ " + XML_Shell.get_path_of_communication_XML_File());

        // rebuild socket
        ClientManager.rebuildSocket();

        // send XML file to server
        ClientService.sendMessageToServer(ClientManager.getClientSocket(), GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));

        // wait for confirmation
        ClientService.receiveMessageFromServer(ClientManager.getClientSocket());

        // upload
        ClientService.manageUpload(ClientService.getLastMessageFromServer(), media.getPath());

        ClientManager.closeSocket();

        // get new index
        //  ClientManager.create_index();
    }


    /**
     * Helper method to create and send a xml file containing a command to the server.
     *
     * @param fileName    the filename to operate on
     * @param commandName the name of the command
     */
    private static void sendCommand(String fileName, XML_SUB_NODES commandName) throws Exception {

        // delete old XML-file
        GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());

        // create message
        XML_Manager.create_Message(XML_NODES.COMMAND, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(XML_NODES.COMMAND, commandName, XML_Shell.get_path_of_communication_XML_File(), fileName);

        // @debug
        System.out.println(GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));

        // rebuild socket
        ClientManager.rebuildSocket();

        // send message to server
        ClientService.sendMessageToServer(ClientManager.getClientSocket(), GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));
    }


    /**
     * Helper method to create and send a xml file containing a command to the server.
     *
     * @param commandName the name of the command
     */
    private static void sendCommand(XML_SUB_NODES commandName) throws Exception {

        // delete old XML-file
        GeneralFile.deleteFile(XML_Shell.get_path_of_communication_XML_File());

        // create message
        XML_Manager.create_Message(XML_NODES.COMMAND, XML_Shell.get_path_of_communication_XML_File());
        XML_Manager.append_new_Message(XML_NODES.COMMAND, commandName, XML_Shell.get_path_of_communication_XML_File());

        // @debug
        System.out.println(GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));

        // rebuild socket
        ClientManager.rebuildSocket();

        // send message to server
        ClientService.sendMessageToServer(ClientManager.getClientSocket(), GeneralFile.returnContentOfFile(XML_Shell.get_path_of_communication_XML_File()));
    }


    /**
     * Commits the command to start a video to the server.
     *
     * @param selectedVideo the video to play
     */
    static void playVideo(String selectedVideo) throws Exception {
        sendCommand(selectedVideo, XML_SUB_NODES.Play_Video);
    }


    static void playMusic(String selectedMusic) throws Exception {
        sendCommand(selectedMusic, XML_SUB_NODES.Play_Music);
    }


    static void playPicture(String selectedPicture) throws Exception {
        sendCommand(selectedPicture, XML_SUB_NODES.Play_Picture);
    }


    static void playNewMedia(String mediaToPlay, XML_NODES mediaType) throws Exception {
        switch (mediaType) {
            case VIDEO:
                stopVideo();
                sendCommand(mediaToPlay, XML_SUB_NODES.Play_Video);
                break;
            case AUDIO:
                stopMusic();
                sendCommand(mediaToPlay, XML_SUB_NODES.Play_Music);
                break;
            case PICTURE:
                stopPicture();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendCommand(mediaToPlay, XML_SUB_NODES.Play_Picture);
        }
    }


    static void stopVideo() throws Exception {
        sendCommand(XML_SUB_NODES.Stop_Video);
    }


    static void stopMusic() throws Exception {
        sendCommand(XML_SUB_NODES.Stop_Music);
    }


    static void stopPicture() throws Exception {
        sendCommand(XML_SUB_NODES.Stop_Picture);
    }


    static void pause() throws Exception {
        sendCommand(XML_SUB_NODES.Pause);
    }


    static void volumeUp() throws Exception {
        sendCommand(XML_SUB_NODES.Volume_Up);
    }


    static void volumeDown() throws Exception {
        sendCommand(XML_SUB_NODES.Volume_Down);
    }


    static void fastForward() throws Exception {
        sendCommand(XML_SUB_NODES.Fast_Forward);
    }


    static void rewind() throws Exception {
        sendCommand(XML_SUB_NODES.Rewind);
    }
}

