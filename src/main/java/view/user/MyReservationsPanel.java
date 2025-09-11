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

    private User loggedInUser = Controller.getLoggedInUser();

    public MyReservationsPanel() {
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        loadReservations();
    }

    private void initializeComponents() {
        // Create table model with columns (including hidden reservation column)
        String[] columnNames = {"Restaurant", "Date", "Time", "Guests", "Notes", "Status", "Action", "ReservationObj"};
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

        for (int i = 0; i < reservationsTable.getColumnCount() - 2; i++) { // -2 to exclude Action and ReservationObj columns
            reservationsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set up the delete button column
        reservationsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        reservationsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Hide the reservation object column
        reservationsTable.getColumn("ReservationObj").setMinWidth(0);
        reservationsTable.getColumn("ReservationObj").setMaxWidth(0);
        reservationsTable.getColumn("ReservationObj").setWidth(0);

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
        tableModel.setRowCount(0);

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
                    "Delete",
                    reservation // Store the entire reservation object in hidden column
            };
            tableModel.addRow(row);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(false);
            setText("DELETE");
            setFont(new Font("Arial", Font.BOLD, 10));
            setForeground(Color.WHITE);
            setBackground(Color.RED);
            setBorder(BorderFactory.createEmptyBorder());
            setFocusPainted(false);
            setContentAreaFilled(false);
            setToolTipText("Delete Reservation");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw rounded rectangle
            g2.setColor(Color.RED);
            int width = getWidth() - 4;
            int height = getHeight() - 8;
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            g2.fillRoundRect(x, y, width, height, 8, 8);

            // Draw border
            g2.setColor(new Color(180, 0, 0));
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, width, height, 8, 8);

            // Draw text
            g2.setColor(Color.WHITE);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            String text = "DELETE";
            int textX = (getWidth() - fm.stringWidth(text)) / 2;
            int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(text, textX, textY);

            g2.dispose();
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Determine if button is being pressed
                    Color bgColor = getModel().isPressed() ? new Color(200, 0, 0) : Color.RED;

                    // Draw rounded rectangle
                    g2.setColor(bgColor);
                    int width = getWidth() - 4;
                    int height = getHeight() - 8;
                    int x = (getWidth() - width) / 2;
                    int y = (getHeight() - height) / 2;
                    g2.fillRoundRect(x, y, width, height, 8, 8);

                    // Draw border
                    g2.setColor(new Color(180, 0, 0));
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(x, y, width, height, 8, 8);

                    // Draw text
                    g2.setColor(Color.WHITE);
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    String text = "DELETE";
                    int textX = (getWidth() - fm.stringWidth(text)) / 2;
                    int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(text, textX, textY);

                    g2.dispose();
                }
            };

            button.setOpaque(false);
            button.setText("DELETE");
            button.setFont(new Font("Arial", Font.BOLD, 10));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.RED);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
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
            selectedRow = row;
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                int option = JOptionPane.showConfirmDialog(
                        MyReservationsPanel.this,
                        "Are you sure you want to delete this reservation?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    // Get the reservation object from the hidden column
                    Reservation reservationToDelete = (Reservation) tableModel.getValueAt(selectedRow, 7);

                    // Call controller method with the actual reservation ID
                    boolean success = controller.removeReservation(reservationToDelete.getId());

                    if (success) {
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(MyReservationsPanel.this,
                                "Reservation deleted successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MyReservationsPanel.this,
                                "Failed to delete reservation. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
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