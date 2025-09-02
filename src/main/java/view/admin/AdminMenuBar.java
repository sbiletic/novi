package view.admin;

import view.login.LoginPanel;
import javax.swing.*;

public class AdminMenuBar extends JMenuBar {

    public AdminMenuBar(JFrame frame) {
        JMenu adminMenu = new JMenu("Admin");

        JMenuItem restaurantManagerItem = new JMenuItem("Restaurant Manager");
        restaurantManagerItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Restaurant Manager selected");
        });
        adminMenu.add(restaurantManagerItem);

        JMenuItem goBackItem = new JMenuItem("Go Back");
        goBackItem.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel());
            frame.revalidate();
            frame.repaint();
        });
        adminMenu.add(goBackItem);

        add(adminMenu);
    }
}