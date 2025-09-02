package view.admin;

import controller.Controller;
import model.Role;
import model.User;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton restaurantRadio;
    private JRadioButton userRadio;
    private ButtonGroup radioGroup;
    private JButton okButton;
    private final Controller controller = Controller.getInstance();
    private final JFrame parentFrame;

    public AdminPanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Radio buttons
        restaurantRadio = new JRadioButton("Restaurant");
        userRadio = new JRadioButton("User");

        // Group radio buttons
        radioGroup = new ButtonGroup();
        radioGroup.add(restaurantRadio);
        radioGroup.add(userRadio);

        // OK button
        okButton = new JButton("OK");

        // Layout components
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Username row
        gbc.gridx = 0; gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        // Password row
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // Radio buttons row
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.add(restaurantRadio);
        radioPanel.add(userRadio);
        add(radioPanel, gbc);

        // OK button row
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        // Add action listener for OK button
        okButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Role selectedRole = null;
            if (restaurantRadio.isSelected()) {
                selectedRole = Role.RESTAURANT;
            } else if (userRadio.isSelected()) {
                selectedRole = Role.USER;
            }

            // Process the inputs
            if (!username.isEmpty() && !password.isEmpty() && selectedRole != null) {
                JOptionPane.showMessageDialog(this,
                        "Processing: " + selectedRole + "\n" +
                                "Username: " + username + "\n" +
                                "Password: " + password,
                        "Processing Request",
                        JOptionPane.INFORMATION_MESSAGE);

                usernameField.setText("");
                passwordField.setText("");
                radioGroup.clearSelection();

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(selectedRole);
                controller.addUser(user);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please fill all fields and select an option.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}