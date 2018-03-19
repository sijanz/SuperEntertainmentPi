package Client.ClientSuperEntertainmentPi;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserTest {
    @Before
    public void setUp() throws Exception {
        User.setUsername("Bernd");
        User.setPassword("lauert");
        User.setToken("1337");
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("Bernd", User.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("lauert", User.getPassword());
    }

    @Test
    public void getToken() throws Exception {
        assertEquals("1337", User.getToken());
    }

    @Test
    public void setUsername() throws Exception {
        User.setUsername("Simon");
        assertEquals("Simon", User.getUsername());
    }

    @Test
    public void setToken() throws Exception {
        User.setToken("b3rnd");
        assertEquals("b3rnd", User.getToken());
    }

    @Test
    public void setPassword() throws Exception {
        User.setPassword("password");
        assertEquals("password", User.getPassword());
    }
}