package Client.EasterEgg;

import javax.swing.*;
import java.awt.*;


public class EasterEgg implements Runnable {

    @Override
    public void run() {
        JFrame frame = new JFrame();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new Board(), BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        JButton exitButton = new JButton("Beenden");
        exitButton.addActionListener(actionEvent -> frame.dispose());
        buttonPane.add(exitButton);
        buttonPane.setBackground(Color.black);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        frame.add(contentPane);
        frame.setResizable(false);
        frame.pack();

        frame.setTitle("Easter Egg");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
}