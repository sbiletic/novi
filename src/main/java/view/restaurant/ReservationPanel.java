package view.restaurant;

import controller.Controller;
import model.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * ReservationPanel - Displays reservations in a JTable with Restaurant + Confirm/Cancel buttons
 */
public class ReservationPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private final Controller controller = Controller.getInstance();

    public ReservationPanel(JFrame frame) {
        setLayout(new BorderLayout());

        // ✅ Removed text headers for Confirm/Cancel columns
        String[] columnNames = {"Name", "Phone Number", "Restaurant", "Date and Time", "Guests", "Status", "", ""};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // only confirm/cancel columns are editable
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);

        // Center align text for all non-button columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (!table.getColumnName(i).equals("") && !table.getColumnName(i).equals("")) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        loadReservations();

        // Add Confirm + Cancel buttons as separate columns
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Confirm"));
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), table, "Confirm"));

        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Cancel"));
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), table, "Cancel"));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadReservations() {
        // ✅ Sample data for testing with restaurant names
        Object[][] sampleData = {
                {"John Doe", "123456789", "Pasta Palace", "2025-09-12 19:00", 4, "Pending"},
                {"Alice Smith", "987654321", "Burger Barn", "2025-09-13 20:30", 2, "Pending"},
                {"Bob Johnson", "555666777", "Sushi World", "2025-09-14 18:15", 6, "Pending"},
                {"Emma Brown", "444555666", "Pizza Hub", "2025-09-15 21:00", 3, "Pending"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
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

    public ButtonEditor(JCheckBox checkBox, JTable table, String actionType) {
        this.tableRef = table;
        this.actionType = actionType;
        button = new JButton(actionType);

        button.addActionListener(e -> {
            int row = tableRef.getEditingRow();
            if (row >= 0) {
                if (actionType.equals("Confirm")) {
                    tableRef.setValueAt("Confirmed", row, 5); // update Status column
                } else if (actionType.equals("Cancel")) {
                    tableRef.setValueAt("Canceled", row, 5);
                }
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
