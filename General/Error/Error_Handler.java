package General.Error;
import Client.ClientGUI.*;
import General.General_Super_Entertainment_Pi.General_Purpose;
import javafx.stage.Stage;


/**
 * @author Dustin
 */
public class Error_Handler extends Error_General {

	/*
     * This class shall handle error_messages.
	 * 
	 * With the Error Messages, specific actions, shall be handled 
	 * 
	 * */
	
	/*
	 * This Method, and its polymorphous "siblings" shall be called, if the client
	 * receives an error message, or another "thing" that indicates some malicious, wrong, 
	 * unspecified behaviour 
	 * */
	
	/*
	 * We will put all Error Mesages here...
	 * Its much more convinient, to put them in a central place....
	 * 
	 * */

    private static Client.ClientGUI.Register register;
    private static Client.ClientGUI.Login login;
    private static javafx.stage.Stage stage;

    public static void setRemoteCaller (javafx.stage.Stage stage) {Error_Handler.stage = stage; }
    public static void setRegister(Client.ClientGUI.Register register) { Error_Handler.register = register; }
    public static void setLogin (Client.ClientGUI.Login login) { Error_Handler.login = login; }

    public static void handle_Error(General_Purpose.Error_Types Error_Message) {

        Register register = Error_Handler.register;
        Login login = Error_Handler.login;
        Stage stage = Error_Handler.stage;
        ErrorCaller ec = new ErrorCaller();



        switch (Error_Message) {

            case Invalid_Key:
                register.registerResponse("keyinvalid");
                close_connection_to_Server_static();
                break;
            case User_Name_is_used:
                register.registerResponse("taken");
                close_connection_to_Server_static();
                break;
            case Invalid_Salt:
                ec.showErrorSalt(stage);
	        		/* We will get new data, if something happend while the user tries to
	        		 * communicate ...... so we have to handle the new auth data ......
	        		 *  extract_authentication_scheme(received_message );
					    set_last_Message_from_Server(received_message );
	        		 * */
                break;
            case User_does_not_exist:
                // handl the error with error handler class
                //User_does_not_exist_Error error = new User_does_not_exist_Error();
                login.loginResponse("wrongin");
                close_connection_to_Server_static();
                break;
            case Password_Hash_incorrect:
                //login
                login.loginResponse("pswhash");
                close_connection_to_Server_static();
                break;
            case User_is_logged_in:
                //login
                login.loginResponse("loggedIn");
                close_connection_to_Server_static();
                break;
            case Hash_is_incorrect:
                //login
                login.loginResponse("hash");
                close_connection_to_Server_static();

                break;
            case Cannot_Create_Register_Key:
                // admin bildschirm registrierung
                // client ausloggen, bildschirm mit nachricht: register key konnte nicht erstellt werden
                ec.showErrorKey(stage);
                break;
            case Cannot_Create_Admin_Register_Key:
                // dasselbe wie oben
                ec.showErrorKey(stage);
                break;
            case Cannot_Create_Admin_Key:
                // dasselbe wie oben
                ec.showErrorKey(stage);
                break;
            case Cannot_Register:
                // das ist beim registrierungsbildschirm
                // einfach neu veruschen , kann nicht registireen als erorr label
                register.registerResponse("cannotRegister");
                break;
            case Not_Permitted_Action:
                //Fenster mit "Virgang nihct erlaubt, Ip adresse ist geblockt"
                // Anwendung schließen
                ec.showErrorAction(stage);
                break;
            case Invalid_Admin_Key:
                register.registerResponse("adminkeyinvalid");
                break;
        }
    }


    public static void handle_Error(String Error_Message) {
        //Prints Error Code in fornt of the user
        System.out.println("Error Code is " + Error_Message);
    }

}