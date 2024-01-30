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

public class SignupFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignupFrame() {
        setTitle("Signup Page");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
        setVisible(true);
    }

    private void createUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        JButton signupButton = new JButton("Signup");
//        signupButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                performSignup();
//            }
//        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(new JLabel()); // Empty label as a placeholder
        panel.add(signupButton);

        add(panel);


        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmationPassword = confirmPasswordField.getText();
            List<User> users = ShopData.readFromFile(FileNames.USERS_FILE);

            String existingUsername = null;

            Optional<User> first = users.stream()
                    .filter(user -> user.getUserName().equals(username))
                    .findFirst();


            if(first.isPresent()) {
                existingUsername = first.get().getUserName();
            }

            if(username.equals(existingUsername)){
                JOptionPane.showMessageDialog(this,username + " already exists. Try another");
            }else if(!password.equals(confirmationPassword)){
                JOptionPane.showMessageDialog(this,"Password and confirmation password" +
                        " does not match. Please check it again");
            }else{
                users.add(new User(usernameField.getText(),passwordField.getText(),false));
                JOptionPane.showMessageDialog(this,"Signup successful");
                ShopData.SaveToFile(users, FileNames.USERS_FILE);
                dispose();
                new LoginFrame();
            }
        });



    }

//    private void performSignup() {
//        String username = usernameField.getText();
//        char[] passwordChars = passwordField.getPassword();
//        char[] confirmPasswordChars = confirmPasswordField.getPassword();
//
//        String password = new String(passwordChars);
//        String confirmPassword = new String(confirmPasswordChars);
//
//        if (!password.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter.");
//            passwordField.setText("");
//            confirmPasswordField.setText("");
//            return;
//        }
//
//
//        JOptionPane.showMessageDialog(this, "Signup successful!");
//        // Clear the password fields after signup
//        passwordField.setText("");
//        confirmPasswordField.setText("");
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignupFrame().setVisible(true);
            }
        });
    }
}
