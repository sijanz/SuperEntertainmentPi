package Client.Client_Super_Entertainment_Pi;

import Client.ClientGUI.RemoteCaller;
import javafx.application.Application;


/**
 * @author Dustin
 */
class Client_Interaction_Starter extends Thread {

    private Class<RemoteCaller> app_class = null;
    private String[] args = null;

    Client_Interaction_Starter(Class<RemoteCaller> class1, String[] args) {
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
