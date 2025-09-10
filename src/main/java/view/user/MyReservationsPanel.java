package view.user;

import controller.Controller;
import model.Reservation;
import model.ReservationStatus;
import model.User;
import model.services.ReservationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyReservationsPanel extends JPanel {

    private JTable reservationsTable;
    private DefaultTableModel tableModel;
    private final Controller controller = Controller.getInstance();

    private ReservationService reservationService = new ReservationService();
    private User loggedInUser = Controller.getLoggedInUser();

    public MyReservationsPanel() {
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        loadReservations();
    }

    private void initializeComponents() {
        // Create table model with columns
        String[] columnNames = {"Restaurant", "Date", "Time", "Guests", "Notes", "Status", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the Action column (delete button) should be editable
                return column == 6;
            }
        };

        reservationsTable = new JTable(tableModel);
        reservationsTable.setRowHeight(40);
        reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < reservationsTable.getColumnCount() - 1; i++) {
            reservationsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set up the delete button column
        reservationsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        reservationsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Set preferred column widths
        reservationsTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Restaurant
        reservationsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Date
        reservationsTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Time
        reservationsTable.getColumnModel().getColumn(3).setPreferredWidth(70);  // Guests
        reservationsTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Notes
        reservationsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Status
        reservationsTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Action
    }

    private void layoutComponents() {
        // Title
        JLabel titleLabel = new JLabel("My Reservations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Table with scroll pane
        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Reservations"));

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadReservations());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshButton);

        // Layout
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadReservations() {
        // Clear existing data
        tableModel.setRowCount(0);

        // TODO: Implement logic to get user's reservations from database
         User loggedInUser = Controller.getLoggedInUser();
         List<Reservation> userReservations = controller.getUserReservations(loggedInUser.getUsername());

         for (Reservation reservation : userReservations) {
             String restaurantName = controller.getRestaurantById(reservation.getRestaurantId()).getName();
             Object[] row = {
                 restaurantName,
                 reservation.getLocalDate().toString(),
                 reservation.getTime(),
                 reservation.getNumberOfGuests(),
                 reservation.getMessage() != null ? reservation.getMessage() : "",
                 reservation.getStatus() != null ? reservation.getStatus() : ReservationStatus.PENDING,
                 "Delete"
             };
             tableModel.addRow(row);
         }

        // Sample data for testing - remove this when implementing actual logic
        Object[][] sampleData = {
                {"Restaurant A", "2024-01-15", "19:00", 4, "Birthday celebration", "Confirmed", "Delete"},
                {"Restaurant B", "2024-01-20", "20:30", 2, "Anniversary dinner", "Pending", "Delete"},
                {"Restaurant C", "2024-01-25", "18:00", 6, "Business meeting", "Confirmed", "Delete"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    // Custom button renderer for the delete button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("-");
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            setToolTipText("Delete Reservation");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Custom button editor for handling delete button clicks
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setText("🗑️");
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setToolTipText("Delete Reservation");

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            selectedRow = row;
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // TODO: Implement reservation deletion logic here
                int option = JOptionPane.showConfirmDialog(
                        MyReservationsPanel.this,
                        "Are you sure you want to delete this reservation?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    // TODO: Get reservation ID from the selected row and delete from database
                    // String restaurantName = (String) tableModel.getValueAt(selectedRow, 0);
                    // String date = (String) tableModel.getValueAt(selectedRow, 1);
                    // String time = (String) tableModel.getValueAt(selectedRow, 2);
                    //
                    // Find the reservation by these details and delete it
                    // boolean success = controller.deleteReservation(reservationId);
                    //
                    // if (success) {
                    //     tableModel.removeRow(selectedRow);
                    //     JOptionPane.showMessageDialog(MyReservationsPanel.this,
                    //         "Reservation deleted successfully!",
                    //         "Success",
                    //         JOptionPane.INFORMATION_MESSAGE);
                    // } else {
                    //     JOptionPane.showMessageDialog(MyReservationsPanel.this,
                    //         "Failed to delete reservation. Please try again.",
                    //         "Error",
                    //         JOptionPane.ERROR_MESSAGE);
                    // }

                    // For now, just remove from table (replace with actual deletion logic)
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(MyReservationsPanel.this,
                            "Reservation deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}