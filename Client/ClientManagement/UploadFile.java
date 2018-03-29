package Client.ClientManagement;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.Backlog.BacklogManager;
import General.GeneralUse.GeneralDate;
import General.SocketNetwork.SocketNetwork;


/**
 * @author Dustin
 */
class UploadFile implements Runnable {

    private String pathToFileToUpload;
    private Socket socketToUpload = null;
    private int portNumber;

    UploadFile(int portNumber, String pathToFileToUpload) {
        this.portNumber = portNumber;
        this.pathToFileToUpload = pathToFileToUpload;
    }

    private void upload() throws IOException {

        // @debug
        System.out.println("upload was fired ");
        System.out.println("Port : " + this.portNumber);
        System.out.println("path : " + this.pathToFileToUpload);


        DataOutputStream dos = new DataOutputStream(this.socketToUpload.getOutputStream());
        FileInputStream fis = new FileInputStream(this.pathToFileToUpload);

        int fileSize = Integer.parseInt(String.valueOf(new File(this.pathToFileToUpload).length()));
        byte[] buffer = new byte[fileSize];

        while (fis.read(buffer) > 0) {

            // @debug
            System.out.println("upload ");

            dos.write(buffer);
        }

        fis.close();
        dos.close();

        // @debug
        System.out.println("Socket will close ");


        this.socketToUpload.close();

        // @debug
        System.out.println("Socket is closed ");
    }


    @Override
    public void run() {
        try {
            InetAddress test = SocketNetwork.getIpAddressInInetFormat(ConfigurationFileManager.getServerIpAddress());

            // @debug
            System.out.println(test.getHostAddress() + "  received " + this.portNumber);

            this.socketToUpload = new Socket(test.getHostAddress(), this.portNumber);

        } catch (IOException e1) {
            BacklogManager.writeToBacklogFile("Cannot build Socket at " + GeneralDate.getDateAndTime());
            e1.printStackTrace();
        }

        try {
            this.upload();
        } catch (IOException io) {
            BacklogManager.writeToBacklogFile("File Upload fails at " + GeneralDate.getDateAndTime() + " File was at " + this.pathToFileToUpload);
        }

        try {
            this.socketToUpload.close();
        } catch (IOException e) {
            e.printStackTrace();
            BacklogManager.writeToBacklogFile("Cannot close socket at " + GeneralDate.getDateAndTime());

        }

        // @debug
        System.out.println("Upload finished");
    }
}
