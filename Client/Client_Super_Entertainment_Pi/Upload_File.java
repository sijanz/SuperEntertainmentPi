package Client.Client_Super_Entertainment_Pi;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Client.Configuration_File_Manager.Configuration_File_Manager;
import General.Backlog_Super_Entertainment_Pi.Backlog_Manager;
import General.General_Super_Entertainment_Pi.General_Date;
import General.Socket_Server_Super_Entertainment_Pi.Socket_Network;


/**
 * @author Dustin
 */
class Upload_File implements Runnable {

    private String path_to_file_to_upload = "";
    private Socket socket_to_upload = null;
    private int port_number = -42;

    Upload_File(int port_number, String path_to_file_to_upload) {
        this.port_number = port_number;
        this.path_to_file_to_upload = path_to_file_to_upload;
    }

    private void upload() throws IOException {

        System.out.println("upload was fired ");
        System.out.println("Port : " + this.port_number);
        System.out.println("path : " + this.path_to_file_to_upload);


        DataOutputStream dos = new DataOutputStream(this.socket_to_upload.getOutputStream());
        FileInputStream fis = new FileInputStream(this.path_to_file_to_upload);

        int fileSize = Integer.parseInt(String.valueOf(new File(this.path_to_file_to_upload).length()));
        byte[] buffer = new byte[fileSize];

        while (fis.read(buffer) > 0) {

            System.out.println("upload ");
            dos.write(buffer);
        }

        fis.close();
        dos.close();

        System.out.println("Socket will close ");


        this.socket_to_upload.close();
        //Client_Service.receiveMessageFromServer(Client_Manager.get_Client_Socket());
        System.out.println("Socket is closed ");
    }


    @Override
    public void run() {

        try {

            InetAddress test = Socket_Network.get_ip_address_in_Inet_format(Configuration_File_Manager.get_Server_Ip_address());
            System.out.println(test.getHostAddress() + "  received " + this.port_number);
            this.socket_to_upload = new Socket(test.getHostAddress(), this.port_number);

        } catch (IOException e1) {
            Backlog_Manager.write_to_backlog_file("Cannot build Socket at " + General_Date.get_Date_and_Time());

            e1.printStackTrace();
        }


        try {
            this.upload();
        } catch (IOException io) {
            Backlog_Manager.write_to_backlog_file("File Upload fails at " + General_Date.get_Date_and_Time() + " File was at " + this.path_to_file_to_upload);
        }


        try {
            this.socket_to_upload.close();
        } catch (IOException e) {
            e.printStackTrace();
            Backlog_Manager.write_to_backlog_file("Cannot close socket at " + General_Date.get_Date_and_Time());

        }

        System.out.println("Upload finished");
    }
}
