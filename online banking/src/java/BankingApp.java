import javax.swing.*;
import java.awt.*;

public class BankingApp extends JFrame {
    private User currentUser;

    public BankingApp() {
        loginScreen();
    }

    private void loginScreen() {
        getContentPane().removeAll();
        setTitle("Online Banking System - Login");
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(loginBtn); add(regBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            currentUser = BankOperations.login(username, password);
            if (currentUser != null) {
                homeScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        regBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (BankOperations.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!");
            }
        });

        revalidate();
        repaint();
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void homeScreen() {
        getContentPane().removeAll();
        setTitle("Welcome, " + currentUser.getUsername());
        setLayout(new GridLayout(5, 1, 5, 5));

        JLabel balanceLabel = new JLabel("Balance: ₹" + currentUser.getBalance());
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        add(balanceLabel);
        add(depositBtn);
        add(withdrawBtn);
        add(logoutBtn);

        depositBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter deposit amount:");
            if (amtStr != null) {
                double amt = Double.parseDouble(amtStr);
                currentUser.setBalance(currentUser.getBalance() + amt);
                BankOperations.updateBalance(currentUser);
                balanceLabel.setText("Balance: ₹" + currentUser.getBalance());
            }
        });

        withdrawBtn.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter withdraw amount:");
            if (amtStr != null) {
                double amt = Double.parseDouble(amtStr);
                if (amt <= currentUser.getBalance()) {
                    currentUser.setBalance(currentUser.getBalance() - amt);
                    BankOperations.updateBalance(currentUser);
                    balanceLabel.setText("Balance: ₹" + currentUser.getBalance());
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                }
            }
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            loginScreen();
        });

        revalidate();
        repaint();
        setSize(300, 250);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankingApp::new);
    }
}
