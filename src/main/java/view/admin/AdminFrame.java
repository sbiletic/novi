package view.admin;

import javax.swing.*;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(new AdminMenuBar(this));

        add(new AdminPanel(this));

        setVisible(true);
    }
}