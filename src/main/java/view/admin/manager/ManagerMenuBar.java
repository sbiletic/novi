package view.admin.manager;

import view.admin.AdminFrame;
import javax.swing.*;

public class ManagerMenuBar extends JMenuBar {

    public ManagerMenuBar(JFrame frame) {
        JMenu managerMenu = new JMenu("Manager");

        JMenuItem userManagerItem = new JMenuItem("User Manager");
        userManagerItem.addActionListener(e -> {
            frame.dispose();
            new AdminFrame().setVisible(true);
        });
        managerMenu.add(userManagerItem);

        add(managerMenu);
    }
}