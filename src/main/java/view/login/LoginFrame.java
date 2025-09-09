package view.login;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(new LoginMenuBar(this));

        add(new LoginPanel());

        setVisible(true);
    }
}
