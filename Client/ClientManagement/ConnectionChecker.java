package Client.ClientManagement;

import Client.ErrorScreen.NetworkNotAvailableError;
import Client.ErrorScreen.NetworkNotAvailableErrorStarter;

import java.io.IOException;


public class ConnectionChecker implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                Client.Client_Super_Entertainment_Pi.Client_Manager.re_build_socket();
                ClientManager.closeSocket();
            } catch (IOException e) {
                new NetworkNotAvailableErrorStarter(NetworkNotAvailableError.class, null);
            }
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
