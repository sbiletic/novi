package view.user;

import javax.swing.*;

public class UserFrame extends JFrame {

    public UserFrame() {
        setTitle("Make a Reservation - User");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(new UserMenuBar(this));

        add(new UserPanel());

        setVisible(true);
    }
}