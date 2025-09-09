package view.user;

import view.login.LoginFrame;
import javax.swing.*;

public class UserMenuBar extends JMenuBar {

    public UserMenuBar(JFrame frame) { // pass the frame so we can switch panels
        // File Menu
        JMenu fileMenu = new JMenu("File");

        // My Reservations option
        JMenuItem myReservationsItem = new JMenuItem("My Reservations");
        myReservationsItem.addActionListener(e -> {
            // TODO: Implement navigation to reservations view
            // This could open a dialog or switch to a different panel showing user's reservations
            JOptionPane.showMessageDialog(frame, "My Reservations feature - TODO: Implement",
                    "Feature", JOptionPane.INFORMATION_MESSAGE);
        });

        // Log Out option
        JMenuItem logOutItem = new JMenuItem("Log Out");
        logOutItem.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                frame.dispose(); // Close the current frame
                new LoginFrame(); // Open the LoginFrame (constructor makes it visible)
            }
        });

        // Exit option
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        fileMenu.add(myReservationsItem);
        fileMenu.addSeparator();
        fileMenu.add(logOutItem);
        fileMenu.add(exitItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Restaurant Reservation System\nUser Panel\nVersion 1.0",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e -> {
            // TODO: Implement help system
            JOptionPane.showMessageDialog(frame,
                    "Help system - TODO: Implement\n\nBasic usage:\n" +
                            "1. Select a restaurant\n" +
                            "2. Choose a date from the calendar\n" +
                            "3. Select time and number of guests\n" +
                            "4. Add optional notes\n" +
                            "5. Click OK to make reservation",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        add(fileMenu);
        add(helpMenu);
    }
}