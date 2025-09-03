package view.login;

import view.admin.AdminFrame;
import javax.swing.*;

public class LoginMenuBar extends JMenuBar {

    public LoginMenuBar(JFrame frame) { // pass the frame so we can switch panels
        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        // Admin Panel option
        JMenuItem adminItem = new JMenuItem("Admin Panel");
        adminItem.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            new AdminFrame().setVisible(true); // Open the AdminFrame
        });
        fileMenu.add(adminItem);
        fileMenu.add(exitItem);

        add(fileMenu);
    }
}
