package view.restaurant;

import view.admin.AdminFrame;
import javax.swing.*;

public class ReservationMenuBar extends JMenuBar {

    public ReservationMenuBar(JFrame frame) {
        JMenu menu = new JMenu("Options");

        JMenuItem backToAdmin = new JMenuItem("Back to Admin");
        backToAdmin.addActionListener(e -> {
            frame.dispose();
            new AdminFrame().setVisible(true);
        });

        JMenuItem logout = new JMenuItem("Log Out");
        logout.addActionListener(e -> {
            frame.dispose();
            JOptionPane.showMessageDialog(null, "Logged out!");
        });

        menu.add(backToAdmin);
        menu.add(logout);
        add(menu);
    }
}
