package Server.Server_Super_Entertainment_Pi;

import java.io.*;
import java.net.*;


/**
 * @author Dustin
 */
public class Send_Files implements Runnable {

    private String filepath = "";

    private ServerSocket upload_socket = null;
    private Socket connection_to_upload = null;

    private int file_size = -42;

    Send_Files(ServerSocket upload_socket, String file_path, int file_size) {

        this.upload_socket = upload_socket;
        this.filepath = file_path;
        this.file_size = file_size;

    }


    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }

    private void saveFile(Socket socket) throws Exception {
        InputStream in = socket.getInputStream();
        OutputStream out = new FileOutputStream(filepath);
        copy(in, out);
        out.close();
        in.close();
        out.close();
    }

    public void run() {

        while (true) {
            try {
                this.connection_to_upload = this.upload_socket.accept();
                this.saveFile(connection_to_upload);
                this.connection_to_upload.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}

