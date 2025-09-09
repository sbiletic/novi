package view.login;

import controller.Controller;
import model.Role;
import model.User;
import model.services.UserService;
import view.user.UserFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private final Controller controller = Controller.getInstance();

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

        // Layout setup
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        add(loginButton, gbc);

        // Button Action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean status = controller.attemptLogin(username, password);
            if (status) {

                JOptionPane.showMessageDialog(this,
                        "Login successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                User object = new User();
                object = controller.getUserByUsername(username);
                if (object.getRole() == Role.USER){
                    new UserFrame();
                }

                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Login failed! Please check your username and password.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
