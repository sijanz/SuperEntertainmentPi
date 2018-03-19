package Client.ClientGUI;

import General.General_Super_Entertainment_Pi.General_Directory;
import General.General_Super_Entertainment_Pi.General_File;
import General.XML_Service_Super_Entertainment_Pi.XML_Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class PlaylistControlTest {
    @Before
    public void setUp() throws Exception {
        PlaylistControl.create_New_VIDEOplaylist("testPlaylist");
    }

    @After
    public void tearDown() throws Exception {
        PlaylistControl.delete_VIDEOplaylist("testPlaylist");
    }

    @Test
    public void getVideoPlaylist() throws Exception {
        List<String> list = General_Directory.get_all_Files_from_path(XML_Shell.get_path_to_VideoDirectory());
        assertEquals(list, PlaylistControl.getVideoPlaylist());
    }

    @Test
    public void create_New_VIDEOplaylist() throws Exception {
        assertTrue(General_File.check_if_file_exists(XML_Shell.get_path_to_VideoDirectory() + "/testPlaylist.xml"));
    }

    @Test
    public void delete_VIDEOplaylist() throws Exception {
        PlaylistControl.delete_VIDEOplaylist("testPlaylist");
        assertFalse(General_File.check_if_file_exists(XML_Shell.get_path_to_VideoDirectory() + "/testPlaylist.xml"));
    }

    @Test
    public void noBrackets() throws Exception {
        String testString = "[test]";
        assertEquals("test", PlaylistControl.noBrackets(testString));
    }

}