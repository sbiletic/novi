package view.login;

import view.admin.AdminPanel; // make sure you have this
import javax.swing.*;

public class LoginMenuBar extends JMenuBar {

    public LoginMenuBar(JFrame frame) { // pass the frame so we can switch panels
        JMenu fileMenu = new JMenu("File");

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        // Admin Panel option
        JMenuItem adminItem = new JMenuItem("Admin Panel");
        adminItem.addActionListener(e -> {
            // Swap current panel with AdminPanel
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new AdminPanel(frame));
            frame.revalidate();
            frame.repaint();
        });
        fileMenu.add(adminItem);
        fileMenu.add(exitItem);

        add(fileMenu);
    }
}
