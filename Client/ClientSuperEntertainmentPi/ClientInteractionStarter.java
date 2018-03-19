package Client.ClientSuperEntertainmentPi;

import Client.ClientGUI.RemoteCaller;
import javafx.application.Application;


/**
 * @author Dustin
 */
class ClientInteractionStarter extends Thread {

    private Class<RemoteCaller> app_class;
    private String[] args;

    ClientInteractionStarter(Class<RemoteCaller> class1, String[] args) {
        this.app_class = class1;
        this.args = args;
    }


    @Override
    public void run() {
        synchronized (this) {
            Application.launch(this.app_class, args);
            notify();
        }
    }

}
