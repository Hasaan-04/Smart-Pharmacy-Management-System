import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark_charcoal = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color border = new Color(200, 200, 200);

    public LoginPage() {
        setTitle("Smart Pharmacy Management System");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(null);
        container.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0, 0, 520, 520);
        leftPanel.setBackground(Color.WHITE);

        JLabel systemTitle = new JLabel("SMART PHARMACY");
        systemTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        systemTitle.setForeground(charcoal);
        systemTitle.setBounds(70, 70, 400, 40);
        leftPanel.add(systemTitle);

        JLabel systemSub = new JLabel("System Login");
        systemSub.setFont(new Font("Segoe UI", Font.BOLD, 16));
        systemSub.setForeground(Color.DARK_GRAY);
        systemSub.setBounds(70, 125, 300, 25);
        leftPanel.add(systemSub);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(charcoal);
        userLabel.setBounds(75, 180, 250, 25);
        leftPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        usernameField.setBounds(75, 210, 330, 38);
        usernameField.setBorder(BorderFactory.createLineBorder(border));
        leftPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(charcoal);
        passLabel.setBounds(75, 270, 250, 25);
        leftPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passwordField.setBounds(75, 300, 330, 38);
        passwordField.setBorder(BorderFactory.createLineBorder(border));
        leftPanel.add(passwordField);

        JButton login = new JButton("LOGIN");
        login.setFont(new Font("Segoe UI", Font.BOLD, 15));
        login.setForeground(Color.WHITE);
        login.setBackground(charcoal);
        login.setFocusPainted(false);
        login.setBorderPainted(false);
        login.setBounds(75, 370, 330, 42);
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(login);

        JLabel note = new JLabel("Authorized Access Only");
        note.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        note.setForeground(Color.GRAY);
        note.setBounds(75, 430, 330, 20);
        note.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(note);

        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(520, 0, 380, 520);
        rightPanel.setBackground(dark_charcoal);

        JLabel welcome = new JLabel("Hey there!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(0, 150, 380, 45);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(welcome);

        JLabel line1 = new JLabel("Your Pharmacy,");
        line1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        line1.setForeground(Color.WHITE);
        line1.setBounds(0, 225, 380, 25);
        line1.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(line1);

        JLabel line2 = new JLabel("Organized and Optimized.");
        line2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        line2.setForeground(Color.WHITE);
        line2.setBounds(0, 245, 380, 25);
        line2.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(line2);

        JLabel footer = new JLabel("Inventory • Sales • Credits");
        footer.setFont(new Font("Segoe UI", Font.BOLD, 13));
        footer.setForeground(gold);
        footer.setBounds(0, 305, 380, 25);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(footer);

        container.add(leftPanel);
        container.add(rightPanel);
        add(container);
        // Mouse Hover Effect
        login.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                login.setBackground(gold);
                login.setForeground(Color.BLACK);
            }

            public void mouseExited(MouseEvent evt) {
                login.setBackground(charcoal);
                login.setForeground(Color.WHITE);
            }
        });

        login.addActionListener(e -> checkLogin());

        setVisible(true);
    }
    // Stablishing Connection
    private void checkLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Using the DBConnection class to get the Connection
            Connection con = DBConnection.getConnection();  

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                new DashBoard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
