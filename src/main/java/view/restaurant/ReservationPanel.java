package view.restaurant;

import controller.Controller;
import model.Reservation;
import model.Restaurant;
import model.User;
import model.ReservationStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ReservationPanel - Displays reservations in a JTable with Restaurant + Confirm/Cancel buttons
 */
public class ReservationPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private final Controller controller = Controller.getInstance();
    private User loggedInUser = Controller.getLoggedInUser();

    public ReservationPanel(JFrame frame) {
        setLayout(new BorderLayout());

        // Dodan ID stupac (skriven kasnije)
        String[] columnNames = {"ID", "Name", "Restaurant", "Date and Time", "Guests", "Status", "", ""};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // samo Confirm/Cancel stupci su editable
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);

        // Center align text za sve osim botuna
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < table.getColumnCount() - 2; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        loadReservations();

        // Confirm button
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Confirm"));
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), table, "Confirm", this));

        // Cancel button
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Cancel"));
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), table, "Cancel", this));

        // Sakrij ID stupac (vidljiv u modelu, ne u prikazu)
        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadReservations() {
        loadAllReservations();
    }

    /**
     * Load all reservations for managers to view
     */
    private void loadAllReservations() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Get all restaurants managed by the logged-in manager
        List<Restaurant> getAllRestaurants = controller.getAllRestaurants();
        getAllRestaurants.removeIf(restaurant -> !restaurant.getManager().getUsername().equals(loggedInUser.getUsername()));

        List<Reservation> allReservations = new ArrayList<>();

        // Collect all reservations from all managed restaurants
        for (Restaurant restaurant : getAllRestaurants) {
            allReservations.addAll(controller.getReservationsByRestaurantId(restaurant.getId()));
        }

        // Populate the table with reservation data
        for (Reservation reservation : allReservations) {
            // Format date and time together
            String formattedDateTime = reservation.getLocalDate() + " " + reservation.getTime();

            Object[] rowData = {
                    reservation.getId(), // skriven ID
                    reservation.getUsername(),
                    controller.getRestaurantByRestaurantId(reservation.getRestaurantId()),
                    formattedDateTime,
                    reservation.getNumberOfGuests(),
                    reservation.getStatus(),
                    "", // Confirm button column
                    ""  // Cancel button column
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Public method to refresh the table data
     * Useful for updating the view after status changes
     */
    public void refreshTable() {
        loadReservations();
    }
}

/**
 * Renders a single button inside a JTable cell
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer(String label) {
        setText(label);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

/**
 * Handles Confirm/Cancel button clicks
 */
class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private JTable tableRef;
    private String actionType; // "Confirm" or "Cancel"
    private final Controller controller = Controller.getInstance();
    private ReservationPanel parentPanel; // da možemo refreshtati tablicu

    public ButtonEditor(JCheckBox checkBox, JTable table, String actionType, ReservationPanel parentPanel) {
        this.tableRef = table;
        this.actionType = actionType;
        this.parentPanel = parentPanel;
        button = new JButton(actionType);

        button.addActionListener(e -> {
            int row = tableRef.getEditingRow();
            if (row >= 0) {
                int modelRow = tableRef.convertRowIndexToModel(row);
                int reservationId = (int) tableRef.getModel().getValueAt(modelRow, 0);

                ReservationStatus newStatus = actionType.equals("Confirm")
                        ? ReservationStatus.COMPLETED
                        : ReservationStatus.CANCELLED;

                controller.updateReservation(reservationId, newStatus);

                parentPanel.refreshTable();
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
