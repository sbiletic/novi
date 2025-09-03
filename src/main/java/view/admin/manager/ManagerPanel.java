package view.admin.manager;

import controller.Controller;
import model.Restaurant;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ManagerPanel extends JPanel {

    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JComboBox<User> managerComboBox;
    private JButton okButton;
    private final Controller controller = Controller.getInstance();
    private final JFrame parentFrame;


    public ManagerPanel(JFrame frame) {
        this.parentFrame = frame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Name label and field
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        // Address label and field
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        // Phone number label and field
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField(20);

        phoneField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // ignore non-digits
                }
            }
        });

        // Manager label and combobox
        JLabel managerLabel = new JLabel("Manager:");
        managerComboBox = new JComboBox<>(new User[]{});

        for (User user : controller.getAllUsersWithRestaurantRole()) {
            managerComboBox.addItem(user);
        }

        // OK button
        okButton = new JButton("OK");

        // Layout components
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name row
        gbc.gridx = 0; gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);

        // Address row
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(addressLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addressField, gbc);

        // Phone number row
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(phoneLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(phoneField, gbc);

        // Manager row
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(managerLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(managerComboBox, gbc);

        // OK button row
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        // Add action listener for OK button
        okButton.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            User selectedManager = (User) managerComboBox.getSelectedItem(); // ⚡ Use User instead of String

            // Process the inputs
            if (!name.isEmpty() && !address.isEmpty() && !phone.isEmpty() &&
                    selectedManager != null) {

                JOptionPane.showMessageDialog(this,
                        "Restaurant Added Successfully!\n" +
                                "Name: " + name + "\n" +
                                "Address: " + address + "\n" +
                                "Phone: " + phone + "\n" +
                                "Manager: " + selectedManager,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear fields after successful submission
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");
                managerComboBox.setSelectedIndex(0);

                // Create restaurant object and add to controller
                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurant.setAddress(address);
                restaurant.setPhone(phone);
                restaurant.setManager(selectedManager);
                controller.addRestaurant(restaurant);

            } else {
                JOptionPane.showMessageDialog(this,
                        "Please fill all fields and select a manager.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}