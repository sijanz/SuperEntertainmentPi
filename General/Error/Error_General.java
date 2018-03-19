package General.Error;

import Client.Client_Super_Entertainment_Pi.Client_Manager;


/**
 * @author Dustin
 */
abstract class Error_General {


    /*
     * We shall provide a fnction to close the connection tio the server
     * */
    void close_connection_to_Server() {

        Client_Manager.close_socket();
    }
    static void close_connection_to_Server_static() {
        Client_Manager.close_socket();
    }

}
