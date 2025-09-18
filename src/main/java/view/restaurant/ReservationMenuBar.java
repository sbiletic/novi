package view.restaurant;

import controller.Controller;
import model.Reservation;
import utils.FileStorage;
import view.admin.AdminFrame;
import view.login.LoginFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;

public class ReservationMenuBar extends JMenuBar {

    private final Controller controller = Controller.getInstance();

    public ReservationMenuBar(JFrame frame, ReservationPanel reservationPanel) {
        JMenu menu = new JMenu("Options");

        // ---------------- Back to Admin ----------------
        JMenuItem backToAdmin = new JMenuItem("Back to Admin");
        backToAdmin.addActionListener(e -> {
            frame.dispose();
            new AdminFrame().setVisible(true);
        });

        // ---------------- Log Out ----------------
        JMenuItem logout = new JMenuItem("Log Out");
        logout.addActionListener(e -> {
            frame.dispose();
            new LoginFrame().setVisible(true);
        });

        // ---------------- Save Reservations ----------------
        JMenuItem saveData = new JMenuItem("Save Reservations...");
        saveData.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Reservations");

            FileNameExtensionFilter binFilter = new FileNameExtensionFilter("Binary (*.bin)", "bin");
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text (*.txt)", "txt");

            chooser.addChoosableFileFilter(binFilter);
            chooser.addChoosableFileFilter(txtFilter);
            chooser.setFileFilter(binFilter); // default je bin

            int choice = chooser.showSaveDialog(frame);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                // Add extension if missing
                if (chooser.getFileFilter() == binFilter && !file.getName().toLowerCase().endsWith(".bin")) {
                    file = new File(file.getAbsolutePath() + ".bin");
                } else if (chooser.getFileFilter() == txtFilter && !file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                List<Reservation> reservations = controller.getAllReservations();
                FileStorage.saveReservations(reservations, file);
                JOptionPane.showMessageDialog(frame,
                        "Reservations saved to " + file.getAbsolutePath());
            }
        });

        // ---------------- Load Reservations ----------------
        JMenuItem loadData = new JMenuItem("Load Reservations...");
        loadData.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Load Reservations");

            FileNameExtensionFilter binFilter = new FileNameExtensionFilter("Binary (*.bin)", "bin");
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text (*.txt)", "txt");

            chooser.addChoosableFileFilter(binFilter);
            chooser.addChoosableFileFilter(txtFilter);
            chooser.setFileFilter(binFilter);

            int choice = chooser.showOpenDialog(frame);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                List<Reservation> reservations = FileStorage.loadReservations(file);
                controller.loadReservations(reservations);

                // 🔥 Refresh the table so UI shows loaded reservations
                reservationPanel.refreshTable();

                JOptionPane.showMessageDialog(frame,
                        "Loaded " + reservations.size() + " reservations from " + file.getAbsolutePath());
            }
        });

        // ---------------- Add to Menu ----------------
        menu.add(saveData);
        menu.add(loadData);
        menu.add(backToAdmin);
        menu.add(logout);
        add(menu);
    }
}
