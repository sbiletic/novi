package view.admin;

import view.admin.manager.ManagerFrame;
import view.login.LoginFrame;
import javax.swing.*;

public class AdminMenuBar extends JMenuBar {

    public AdminMenuBar(JFrame frame) {
        JMenu adminMenu = new JMenu("Admin");

        JMenuItem restaurantManagerItem = new JMenuItem("Restaurant Manager");
        restaurantManagerItem.addActionListener(e -> {
            frame.dispose();
            new ManagerFrame().setVisible(true);
        });
        adminMenu.add(restaurantManagerItem);

        JMenuItem goBackItem = new JMenuItem("Go to login form");
        goBackItem.addActionListener(e -> {
            frame.dispose();
            new LoginFrame().setVisible(true);
        });
        adminMenu.add(goBackItem);

        add(adminMenu);
    }
}