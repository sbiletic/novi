package view.login;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add menu bar
        setJMenuBar(new LoginMenuBar(this));

        // Add panel
        add(new LoginPanel());

        setVisible(true);
    }
}
