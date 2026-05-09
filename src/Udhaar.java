import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Udhaar extends JFrame {
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JTextField customerField, amountField;
    private JComboBox<String> statusBox;

    private JTable udhaarTable;
    private DefaultTableModel tableModel;

    private int selectedUdhaarId = -1;
    // Main Interface
    public Udhaar() {
        setTitle("Add Udhaar - Smart Pharmacy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadUdhaarData();

        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(260, 720));
        sidebar.setBackground(dark);

        JLabel title1 = new JLabel("SMART");
        title1.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title1.setForeground(Color.WHITE);
        title1.setBounds(45, 30, 200, 35);
        sidebar.add(title1);

        JLabel title2 = new JLabel("PHARMACY");
        title2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title2.setForeground(gold);
        title2.setBounds(45, 60, 200, 35);
        sidebar.add(title2);

        JLabel title3 = new JLabel("MANAGEMENT");
        title3.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title3.setForeground(Color.WHITE);
        title3.setBounds(45, 90, 200, 35);
        sidebar.add(title3);

        JLabel title4 = new JLabel("SYSTEM");
        title4.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title4.setForeground(gold);
        title4.setBounds(45, 120, 200, 35);
        sidebar.add(title4);

        JButton backBtn = sidebarButton("Back to Dashboard", 220);
        backBtn.addActionListener(e -> {
            new DashBoard();
            dispose();
        });
        sidebar.add(backBtn);

        JButton exitBtn = sidebarButton("Exit", 285);
        exitBtn.addActionListener(e -> System.exit(0));
        sidebar.add(exitBtn);

        return sidebar;
    }

    private JButton sidebarButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(25, y, 210, 44);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(charcoal);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(gold);
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(charcoal);
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }
    // Main Panel
    private JPanel createMainPanel() {
        JPanel main = new JPanel(null);
        main.setBackground(BG);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(charcoal);
        headerPanel.setBounds(50, 35, 1120, 110);
        main.add(headerPanel);

        JLabel heading = new JLabel("Udhaar");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 20, 400, 45);
        headerPanel.add(heading);

        JLabel subHeading = new JLabel("Manage Customer Credit Records");
        subHeading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subHeading.setForeground(gold);
        subHeading.setBounds(32, 68, 400, 25);
        headerPanel.add(subHeading);

        JPanel formPanel = createFormPanel();
        formPanel.setBounds(50, 170, 1120, 250);
        main.add(formPanel);

        JPanel tablePanel = createTablePanel();
        tablePanel.setBounds(50, 450, 1120, 360);
        main.add(tablePanel);

        return main;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(gold, 2));

        JLabel title = new JLabel("Udhaar Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(30, 20, 300, 35);
        panel.add(title);

        JLabel customerLabel = label("Customer Name");
        customerLabel.setBounds(30, 75, 200, 25);
        panel.add(customerLabel);

        customerField = input();
        customerField.setBounds(30, 105, 270, 38);
        panel.add(customerField);

        JLabel amountLabel = label("Amount");
        amountLabel.setBounds(340, 75, 200, 25);
        panel.add(amountLabel);

        amountField = input();
        amountField.setBounds(340, 105, 270, 38);
        panel.add(amountField);

        JLabel statusLabel = label("Status");
        statusLabel.setBounds(650, 75, 200, 25);
        panel.add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"Unpaid", "Paid"});
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        statusBox.setBounds(650, 105, 270, 38);
        panel.add(statusBox);

        JButton addBtn = actionButton("ADD");
        addBtn.setBounds(30, 185, 110, 40);
        addBtn.addActionListener(e -> addUdhaar());
        panel.add(addBtn);

        JButton updateBtn = actionButton("UPDATE");
        updateBtn.setBounds(160, 185, 110, 40);
        updateBtn.addActionListener(e -> updateUdhaar());
        panel.add(updateBtn);

        JButton deleteBtn = actionButton("DELETE");
        deleteBtn.setBounds(290, 185, 110, 40);
        deleteBtn.addActionListener(e -> deleteUdhaar());
        panel.add(deleteBtn);

        JButton clearBtn = actionButton("CLEAR");
        clearBtn.setBounds(420, 185, 110, 40);
        clearBtn.addActionListener(e -> clearFields());
        panel.add(clearBtn);

        return panel;
    }
    // Table Panel
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel title = new JLabel("Udhaar Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(20, 10, 300, 35);
        panel.add(title);

        tableModel = new DefaultTableModel(
                new String[]{"Udhaar ID", "Customer Name", "Amount", "Date", "Status"}, 0
        );

        udhaarTable = new JTable(tableModel);
        udhaarTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        udhaarTable.setRowHeight(28);
        udhaarTable.setSelectionBackground(gold);
        udhaarTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = udhaarTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(charcoal);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(udhaarTable);
        scrollPane.setBounds(20, 55, 1080, 280);
        panel.add(scrollPane);

        udhaarTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && udhaarTable.getSelectedRow() != -1) {
                int row = udhaarTable.getSelectedRow();

                selectedUdhaarId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                customerField.setText(tableModel.getValueAt(row, 1).toString());
                amountField.setText(tableModel.getValueAt(row, 2).toString());
                statusBox.setSelectedItem(tableModel.getValueAt(row, 4).toString());
            }
        });

        return panel;
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(charcoal);
        return lbl;
    }

    private JTextField input() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return field;
    }

    private JButton actionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(charcoal);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(gold);
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(charcoal);
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }
    // Stablishing Connection
    private Connection getConnection() throws Exception {
        // Using the DBConnection class to get the Connection
        return DBConnection.getConnection();
    }
    // Add Method
    private void addUdhaar() {
        if (!validateFields()) return;

        try {
            Connection con = getConnection();

            String query = "INSERT INTO udhaar (customer_name, amount, date, status) VALUES (?, ?, CURDATE(), ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, customerField.getText());
            stmt.setDouble(2, Double.parseDouble(amountField.getText()));
            stmt.setString(3, statusBox.getSelectedItem().toString());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Udhaar Added Successfully");

            con.close();
            clearFields();
            loadUdhaarData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add Error: " + e.getMessage());
        }
    }
    // Update Method
    private void updateUdhaar() {
        if (selectedUdhaarId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record from the table first");
            return;
        }

        if (!validateFields()) return;

        try {
            Connection con = getConnection();

            String query = "UPDATE udhaar SET customer_name=?, amount=?, status=? WHERE udhaar_id=?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, customerField.getText());
            stmt.setDouble(2, Double.parseDouble(amountField.getText()));
            stmt.setString(3, statusBox.getSelectedItem().toString());
            stmt.setInt(4, selectedUdhaarId);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Udhaar Updated Successfully");

            con.close();
            clearFields();
            loadUdhaarData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }
    // Delete Method
    private void deleteUdhaar() {
        if (selectedUdhaarId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record from the table first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this udhaar record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection con = getConnection();

            String query = "DELETE FROM udhaar WHERE udhaar_id=?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, selectedUdhaarId);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Udhaar Deleted Successfully");

            con.close();
            clearFields();
            loadUdhaarData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
        }
    }
    // Loading Data for Display Table
    private void loadUdhaarData() {
        tableModel.setRowCount(0);

        try {
            Connection con = getConnection();

            String query = "SELECT udhaar_id, customer_name, amount, date, status FROM udhaar ORDER BY udhaar_id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("udhaar_id"),
                        rs.getString("customer_name"),
                        rs.getDouble("amount"),
                        rs.getDate("date"),
                        rs.getString("status")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Error: " + e.getMessage());
        }
    }
    // Validation
    private boolean validateFields() {
        if (customerField.getText().isEmpty() || amountField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return false;
        }

        try {
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number");
            return false;
        }

        return true;
    }
    // Clearing Fields
    private void clearFields() {
        selectedUdhaarId = -1;
        customerField.setText("");
        amountField.setText("");
        statusBox.setSelectedIndex(0);
        udhaarTable.clearSelection();
    }
}
