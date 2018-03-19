package Server.ServerGUI;

import Client.ConfigurationFileManager.ConfigurationFileManager;

import javax.swing.*;
import java.awt.*;


/**
 * The graphical user interface for the server.
 *
 * @author Simon
 */
public class ServerGUI extends Thread {


    /**
     * Initializes a JFrame showing a background image.
     */
    public void run() {
        JFrame frame = new JFrame();

        // set content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(40, 40, 40));

        // adding the ip label
        JLabel ipLabel = new JLabel("Es konnte keine IP-Addresse bezogen werden, bitte die Netzwerkeinstellungen überprüfen!", SwingConstants.CENTER);
        try {
            ipLabel.setText("IP-Adresse: " + ConfigurationFileManager.getServerIpAddress() + "      Port: " + ConfigurationFileManager.getServerPort());
            ipLabel.setForeground(new Color(198, 5, 60));
        } catch (Exception e) {
            e.printStackTrace();
        }

        contentPane.add(ipLabel, BorderLayout.NORTH);

        // add background
        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("SepBackground.jpg"));
        background.setHorizontalAlignment(JLabel.CENTER);
        background.setVerticalAlignment(JLabel.CENTER);

        contentPane.add(background, BorderLayout.CENTER);

        frame.setContentPane(contentPane);

        // show the whole thing
        frame.setUndecorated(true);
        frame.setBounds(0, 0, frame.getToolkit().getScreenSize().width, frame.getToolkit()
                .getScreenSize().height);
        frame.setVisible(true);
    }
}