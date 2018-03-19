package Client.Client_Super_Entertainment_Pi;

import Client.ErrorScreen.Network_Not_Available_Error;
import Client.ErrorScreen.Network_Not_Available_ErrorStarter;

import java.io.IOException;


public class ConnectionChecker implements Runnable {


    @Override
    public void run() {
        while (true) {
            try {
                Client_Manager.re_build_socket();
                Client_Manager.close_socket();
            } catch (IOException e) {
                new Network_Not_Available_ErrorStarter(Network_Not_Available_Error.class, null);
            }
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
