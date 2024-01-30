package GUI;

import Model.User;
import Repo.ShopData;
import utilitites.FileNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton = new JButton("Sigup");

    public LoginFrame() {
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        setVisible(true);
    }

    private void createUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        signupButton.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signupButton);
        panel.add(loginButton);


        add(panel);
    }

    private void performLogin() {
//        String password = new String(passwordChars);

//
//        if ("admin".equals(username) && "password".equals(password)) {
//            JOptionPane.showMessageDialog(this, "Login successful!");
//        } else {
//            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
//        }
//
//        // Clear the password field after login attempt
//        passwordField.setText("");

        loginButton.addActionListener(e -> {
            // Get the text in the text fields
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // Get the user details from the database
            List<User> users = ShopData.readFromFile(FileNames.USERS_FILE);
            if(users==null) return;
            Optional<User> first = users.stream()
                    .filter(user -> user.getUserName().equals(username))
                    .findFirst();

            if(first.isEmpty()){
                JOptionPane.showMessageDialog(this,"Username doesn't exists. Please try again.");
                return;
            }

            User user = first.get();

            if(!user.getPassword().equals(password)){
                //Show incorrect password message to user
                JOptionPane.showMessageDialog(this,"Incorrect password");
            }else{
                //Show login success message to user
                JOptionPane.showMessageDialog(this,"Login successful");
                ShopData.setCurrentUser(user);
                dispose();
                new ShoppingCart();
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
