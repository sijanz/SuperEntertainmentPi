package Client.ErrorScreen;

public class NetworkNotAvailableErrorStarter {

    public NetworkNotAvailableErrorStarter(Class<NetworkNotAvailableError> classToBeStarted, String[] args) {
        javafx.application.Application.launch(classToBeStarted, args);
    }
}
