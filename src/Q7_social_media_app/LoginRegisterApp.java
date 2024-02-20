package src.Q7_social_media_app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;

    public LoginRegisterApp() {
        setTitle("Login and Register App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Login components
        JLabel loginLabel = new JLabel("Login:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Register components
        JLabel registerLabel = new JLabel("Register:");
        registerUsernameField = new JTextField();
        registerPasswordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        // Adding components to the panel
        panel.add(loginLabel);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        panel.add(Box.createVerticalStrut(10)); // Spacing

        panel.add(registerLabel);
        panel.add(new JLabel("New Username:"));
        panel.add(registerUsernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(registerPasswordField);
        panel.add(registerButton);

        // Adding panel to the frame
        add(panel);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (LoginLogic.validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }

    private void register() {
        String newUsername = registerUsernameField.getText();
        String newPassword = new String(registerPasswordField.getPassword());

        if (RegisterLogic.performRegistration(newUsername, newPassword)) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginRegisterApp().setVisible(true);
            }
        });
    }
}

