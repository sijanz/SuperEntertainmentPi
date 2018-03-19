package Client.ClientManagement;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class Client_ManagerTest {
    @Before
    public void setUp() throws Exception {
        User.setUsername("Bernd");
    }

    @Test
    public void sendCredentials() throws Exception {
        User.setPassword("LAUERT");
        assertTrue(ClientManager.sendCredentials());
    }

    @Test
    public void sendWrongCredentials() throws Exception {
        User.setPassword("sep");
        assertFalse(ClientManager.sendCredentials());
    }

    @Test
    public void retrieveIndex() throws Exception {
        User.setToken("1337");
        assertFalse(ClientManager.retrieveIndex());
    }
}