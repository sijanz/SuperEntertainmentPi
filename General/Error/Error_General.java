package General.Error;

import Client.ClientSuperEntertainmentPi.ClientManager;


/**
 * @author Dustin
 */
abstract class Error_General {


    /*
     * We shall provide a fnction to close the connection tio the server
     * */
    void close_connection_to_Server() {

        ClientManager.closeSocket();
    }
    static void close_connection_to_Server_static() {
        ClientManager.closeSocket();
    }

}
