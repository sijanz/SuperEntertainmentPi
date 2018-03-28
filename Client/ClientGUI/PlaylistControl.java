package Client.ClientGUI;

import General.GeneralUse.GeneralDirectory;
import General.GeneralUse.GeneralFile;
import General.XML_Service_Super_Entertainment_Pi.XML_Manager;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell_Working;

import java.io.*;
import java.util.List;

class PlaylistControl {


    /**
     * get all Playlist in respective directory
     */
    static List<String> getVideoPlaylist() {
        return GeneralDirectory.getAllFilesFromPath(XML_Shell.get_path_to_VideoDirectory());
    }
    static List<String> getMusicPlaylist() {
        return GeneralDirectory.getAllFilesFromPath(XML_Shell.get_path_to_MusicDirectory());
    }
    static List<String> getPicturePlaylist() {
        return GeneralDirectory.getAllFilesFromPath(XML_Shell.get_path_to_PictureDirectory());
    }


    // parseVideoList: helper method to create a String-ArrayList out of an input stream
    static List<String> Parser(String path, XML_Manager.XML_SUB_NODES sub) {
        return XML_Manager.get_Contents_of_specific_Media_Path(sub, path);
    }

    /**
     * update all-Playlists
     * @throws Exception e
     */
    static void UpdateSuperPlaylists() throws Exception {

        // ---VIDEO---

        //delete old playlist
        delete_VIDEOplaylist("allVideos");

        //get content for new playlist
        List<String> contentListVideo = Parser(XML_Shell_Working.get_path_of_index_XML_File(), XML_Manager.XML_SUB_NODES.Clip);

        //create new playlist without content
        create_New_VIDEOplaylist("allVideos");

        //get content of new File
        String newcontentVideo = GeneralFile.returnContentOfFile(XML_Shell.get_path_to_VideoDirectory() + "/allVideos.xml");

        //turn content from List<String> to String
        for (String i : contentListVideo) {
            newcontentVideo = newcontentVideo.replace("</VIDEO>", "<" + VideoMenu.Subnode + ">" + i + "</" + VideoMenu.Subnode + "></VIDEO>");
        }

        //add new content
        PrintWriter printwriter = new PrintWriter(XML_Shell.get_path_to_VideoDirectory() + "/allVideos.xml");
        printwriter.write(newcontentVideo);
        printwriter.close();

        // ---MUSIC---

        //delete old playlist
        delete_MUSICplaylist("allMusic");

        //get content for new playlist
        List<String> contentListMusic = Parser(XML_Shell_Working.get_path_of_index_XML_File(), XML_Manager.XML_SUB_NODES.Track);

        //create new playlist without content
        create_New_MUSICplaylist("allMusic");

        //get content of new File
        String newcontentMusic = GeneralFile.returnContentOfFile(XML_Shell.get_path_to_MusicDirectory() + "/allMusic.xml");

        //turn content from List<String> to String
        for (String i : contentListMusic) {
            newcontentMusic = newcontentMusic.replace("</MUSIC>", "<" + AudioMenu.Subnode + ">" + i + "</" + AudioMenu.Subnode + "></MUSIC>");
        }

        //add new content
        PrintWriter printwriterMusic = new PrintWriter(XML_Shell.get_path_to_MusicDirectory() + "/allMusic.xml");
        printwriterMusic.write(newcontentMusic);
        printwriterMusic.close();

        // ---PICTURES---

        //delete old playlist
        delete_PICTUREplaylist("allImages");

        //get content for new playlist
        List<String> contentListImage = Parser(XML_Shell_Working.get_path_of_index_XML_File(), XML_Manager.XML_SUB_NODES.Image);

        //create new playlist without content
        create_New_PICTUREplaylist("allImages");

        //get content of new File
        String newcontentImage = GeneralFile.returnContentOfFile(XML_Shell.get_path_to_PictureDirectory() + "/allImages.xml");

        //turn content from List<String> to String
        for (String i : contentListImage) {
            newcontentImage = newcontentImage.replace("</PICTURE>", "<" + ImageMenu.Subnode + ">" + i + "</" + ImageMenu.Subnode + "></PICTURE>");
        }

        //add new content
        PrintWriter printwriterImage = new PrintWriter(XML_Shell.get_path_to_PictureDirectory() + "/allImages.xml");
        printwriterImage.write(newcontentImage);
        printwriterImage.close();

        // ----------

        VideoMenu.cascadeToVideoPlaylists();
        AudioMenu.cascadeToMusicPlaylists();
        ImageMenu.cascadeToImagePlaylists();

        deleteDuplicates("video");
        deleteDuplicates("music");
        deleteDuplicates("picture");

    }

    /**
     * @param name type String
     * @throws IOException io
     * @author Ramin added Method to create new Playlists VIDEO + MUSIC
     */
    static void create_New_VIDEOplaylist(String name) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(XML_Shell_Working.get_path_of_XML_Directory() + "/samplePlaylist.xml"), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(XML_Shell.get_path_to_VideoDirectory() + "/" + name + ".xml"), "UTF-8"));

        String line;

        while ((line = reader.readLine()) != null) {
            writer.write(line);
        }

        // Closes reader
        reader.close();
        // Closes writer and flushes
        writer.close();

    }

    /**
     * @throws IOException io
     * @author Ramin
     * this method acts the same way as create_NEW_VIDEOplaylist
     **/
    static void create_New_MUSICplaylist(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(XML_Shell_Working.get_path_of_XML_Directory() + "/samplePlaylist.xml"), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(XML_Shell.get_path_to_MusicDirectory() + "/" + name + ".xml"), "UTF-8"));

        String line;

        while ((line = reader.readLine()) != null) {
            writer.write(line);
        }

        // Closes reader
        reader.close();
        // Closes writer and flushes
        writer.close();
    }

    /**
     * @throws IOException io
     * @author Ramin
     * this method acts the same way as create_NEW_VIDEOplaylist
     **/
    static void create_New_PICTUREplaylist(String name) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(XML_Shell_Working.get_path_of_XML_Directory() + "/samplePlaylist.xml"), "UTF-8"));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(XML_Shell.get_path_to_PictureDirectory() + "/" + name + ".xml"), "UTF-8"));

        String line;

        while ((line = reader.readLine()) != null) {
            writer.write(line);
        }

        // Closes reader
        reader.close();
        // Closes writer and flushes
        writer.close();
    }

    /**
     * @author Ramin
     * Method to delete a VIDEOplaylist
     * in this iteration: by entering name.
     * next up: by selecting playlist from view.
     */
    static void delete_VIDEOplaylist(String name) {
        File todelete = new File(XML_Shell.get_path_to_VideoDirectory() + "/" + name + ".xml");

        try {
            todelete.delete();
        } catch (Exception e) {
            System.err.println("an error occured");
            e.printStackTrace();
        }

    }

    /**
     * @param name String
     * @author Ramin
     * method acts same way as dele_VIDEOplaylist
     */
    static void delete_MUSICplaylist(String name) {

        File todelete = new File(XML_Shell.get_path_to_MusicDirectory() + "/" + name + ".xml");

        try {
            todelete.delete();
        } catch (Exception e) {
            System.err.println("an error occured");
            e.printStackTrace();
        }

    }

    /**
     * method acts same way as dele_VIDEOplaylist
     * @param name String
     * @author Ramin
     */

    static void delete_PICTUREplaylist(String name) {

        File todelete = new File(XML_Shell.get_path_to_PictureDirectory() + "/" + name + ".xml");

        try {
            todelete.delete();
        } catch (Exception e) {
            System.err.println("an error occured");
            e.printStackTrace();
        }

    }


    private static void deleteDuplicates(String name) throws Exception {

        XML_Manager.XML_SUB_NODES subnode = null;
        XML_Manager.XML_NODES node = null;
        String directory = null;
        boolean flag = true;
        int counter;

        switch (name) {
            case "video":
                subnode = XML_Manager.XML_SUB_NODES.Clip;
                node = XML_Manager.XML_NODES.VIDEO;
                directory = XML_Shell.get_path_to_VideoDirectory();
                break;
            case "music":
                subnode = XML_Manager.XML_SUB_NODES.Track;
                node = XML_Manager.XML_NODES.MUSIC;
                directory = XML_Shell.get_path_to_MusicDirectory();
                break;
            case "picture":
                subnode = XML_Manager.XML_SUB_NODES.Image;
                node = XML_Manager.XML_NODES.PICTURE;
                directory = XML_Shell.get_path_to_PictureDirectory();
                break;
            default:
                System.out.println("deleteDuplicates called with wrong parameter");
                flag = false;
                break;
        }
        if (flag) {
            List<String> playlists = GeneralDirectory.getAllFilesFromPath(directory);
            for (String i : playlists) {
                if (!i.equals(".directory") && !i.equals("allVideos.xml") && !i.equals("allMusic.xml") && !i.equals("allPictures.xml")) {
                    String filecontent = GeneralFile.returnContentOfFile(directory + "/" + i);
                    List<String> content = Parser(directory + "/" + i, subnode);
                    for (String k : content) {
                        counter = 0;
                        for (String l : content) {
                            if (k.equals(l)) {
                                counter++;
                            }
                            if (counter > 1) {

                                //delete all entries
                                filecontent = filecontent.replace("<" + subnode + ">" + k + "</" + subnode + ">", "");

                                //write one new entry
                                filecontent = filecontent.replace("</" + node + ">", "<" + subnode + ">" + k + "</" + subnode + "></" + node + ">");

                                //write
                                PrintWriter printwriter = new PrintWriter(directory + "/" + i);
                                printwriter.write(filecontent);
                                printwriter.close();

                                counter = 1;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Christian
     * removes [ and ] in String
     *
     * @param string to remove brackets from
     * @return String input string without brackets
     */
    static String noBrackets(String string) {
        string = string.replace("[", "");
        string = string.replace("]", "");
        return string;
    }


}
