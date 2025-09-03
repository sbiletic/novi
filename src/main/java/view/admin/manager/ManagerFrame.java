package view.admin.manager;

import javax.swing.*;

public class ManagerFrame extends JFrame {

    public ManagerFrame() {
        setTitle("Restaurant Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(new ManagerMenuBar(this));

        add(new ManagerPanel(this));

        setVisible(true);
    }
}