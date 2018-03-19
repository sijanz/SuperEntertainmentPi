package Client.ClientManagement;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Client.ConfigurationFileManager.ConfigurationFileManager;
import General.Backlog.BacklogManager;
import General.General_Super_Entertainment_Pi.General_Date;
import General.Socket_Server_Super_Entertainment_Pi.Socket_Network;


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
            InetAddress test = Socket_Network.get_ip_address_in_Inet_format(ConfigurationFileManager.getServerIpAddress());

            // @debug
            System.out.println(test.getHostAddress() + "  received " + this.portNumber);

            this.socketToUpload = new Socket(test.getHostAddress(), this.portNumber);

        } catch (IOException e1) {
            BacklogManager.writeToBacklogFile("Cannot build Socket at " + General_Date.get_Date_and_Time());
            e1.printStackTrace();
        }

        try {
            this.upload();
        } catch (IOException io) {
            BacklogManager.writeToBacklogFile("File Upload fails at " + General_Date.get_Date_and_Time() + " File was at " + this.pathToFileToUpload);
        }

        try {
            this.socketToUpload.close();
        } catch (IOException e) {
            e.printStackTrace();
            BacklogManager.writeToBacklogFile("Cannot close socket at " + General_Date.get_Date_and_Time());

        }

        // @debug
        System.out.println("Upload finished");
    }
}
