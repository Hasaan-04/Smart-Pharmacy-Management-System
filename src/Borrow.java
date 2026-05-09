import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Borrow extends JFrame {
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JTextField chemistField, quantityField, priceField, totalField;
    private JComboBox<String> medicineBox, statusBox;

    private JTable borrowTable;
    private DefaultTableModel tableModel;

    private int selectedBorrowId = -1;
    private int oldMedicineId = -1;
    private int oldQuantity = 0;
    // Main Interface
    public Borrow() {
        setTitle("Add Borrow - Smart Pharmacy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadMedicines();
        loadBorrowData();

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

        JLabel heading = new JLabel("Borrow");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 20, 400, 45);
        headerPanel.add(heading);

        JLabel subHeading = new JLabel("Manage Borrow Records");
        subHeading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subHeading.setForeground(gold);
        subHeading.setBounds(32, 68, 350, 25);
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

        JLabel title = new JLabel("Borrow Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(30, 20, 300, 35);
        panel.add(title);

        JLabel chemistLabel = label("Chemist Name");
        chemistLabel.setBounds(30, 75, 200, 25);
        panel.add(chemistLabel);

        chemistField = input();
        chemistField.setBounds(30, 105, 270, 38);
        panel.add(chemistField);

        JLabel medicineLabel = label("Medicine Name");
        medicineLabel.setBounds(340, 75, 200, 25);
        panel.add(medicineLabel);

        medicineBox = new JComboBox<>();
        medicineBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        medicineBox.setBounds(340, 105, 270, 38);
        medicineBox.addActionListener(e -> loadSelectedMedicineDetails());
        panel.add(medicineBox);

        JLabel quantityLabel = label("Quantity");
        quantityLabel.setBounds(650, 75, 200, 25);
        panel.add(quantityLabel);

        quantityField = input();
        quantityField.setBounds(650, 105, 270, 38);
        quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculateTotal();
            }
        });
        panel.add(quantityField);

        JLabel priceLabel = label("Purchase Price");
        priceLabel.setBounds(30, 155, 200, 25);
        panel.add(priceLabel);

        priceField = input();
        priceField.setBounds(30, 185, 270, 38);
        priceField.setEditable(false);
        panel.add(priceField);

        JLabel totalLabel = label("Borrow Amount");
        totalLabel.setBounds(340, 155, 200, 25);
        panel.add(totalLabel);

        totalField = input();
        totalField.setBounds(340, 185, 270, 38);
        totalField.setEditable(false);
        panel.add(totalField);

        JLabel statusLabel = label("Status");
        statusLabel.setBounds(650, 155, 200, 25);
        panel.add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"Unpaid", "Paid"});
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        statusBox.setBounds(650, 185, 270, 38);
        panel.add(statusBox);

        JButton addBtn = actionButton("ADD");
        addBtn.setBounds(950, 60, 130, 38);
        addBtn.addActionListener(e -> addBorrow());
        panel.add(addBtn);

        JButton updateBtn = actionButton("UPDATE");
        updateBtn.setBounds(950, 110, 130, 38);
        updateBtn.addActionListener(e -> updateBorrow());
        panel.add(updateBtn);

        JButton clearBtn = actionButton("CLEAR");
        clearBtn.setBounds(950, 160, 130, 38);
        clearBtn.addActionListener(e -> clearFields());
        panel.add(clearBtn);

        return panel;
    }
    // Display Table
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel title = new JLabel("Borrow Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(20, 10, 300, 35);
        panel.add(title);

        tableModel = new DefaultTableModel(
                new String[]{"Borrow ID", "Chemist", "Medicine", "Quantity", "Purchase Price", "Amount", "Date", "Status"}, 0
        );

        borrowTable = new JTable(tableModel);
        borrowTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        borrowTable.setRowHeight(28);
        borrowTable.setSelectionBackground(gold);
        borrowTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = borrowTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(charcoal);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(borrowTable);
        scrollPane.setBounds(20, 55, 1080, 280);
        panel.add(scrollPane);

        borrowTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && borrowTable.getSelectedRow() != -1) {
                int row = borrowTable.getSelectedRow();

                selectedBorrowId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());

                chemistField.setText(tableModel.getValueAt(row, 1).toString());
                medicineBox.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                quantityField.setText(tableModel.getValueAt(row, 3).toString());
                statusBox.setSelectedItem(tableModel.getValueAt(row, 7).toString());

                loadSelectedMedicineDetails();
                calculateTotal();

                try {
                    oldMedicineId = getSelectedMedicineId();
                    oldQuantity = Integer.parseInt(quantityField.getText());
                } catch (Exception ex) {
                    oldMedicineId = -1;
                    oldQuantity = 0;
                }
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
    // Loading Medicine Data
    private void loadMedicines() {
        medicineBox.removeAllItems();

        try {
            Connection con = getConnection();

            String query = "SELECT name FROM medicine ORDER BY name";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                medicineBox.addItem(rs.getString("name"));
            }

            con.close();

            if (medicineBox.getItemCount() > 0) {
                loadSelectedMedicineDetails();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Medicine Load Error: " + e.getMessage());
        }
    }
  
    private int getSelectedMedicineId() throws Exception {
        Connection con = getConnection();

        String query = "SELECT medicine_id FROM medicine WHERE name=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, medicineBox.getSelectedItem().toString());

        ResultSet rs = stmt.executeQuery();

        int id = -1;
        if (rs.next()) {
            id = rs.getInt("medicine_id");
        }

        con.close();
        return id;
    }

    private void loadSelectedMedicineDetails() {
        if (medicineBox.getSelectedItem() == null) return;

        try {
            Connection con = getConnection();

            String query = "SELECT purchase_price FROM medicine WHERE name=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, medicineBox.getSelectedItem().toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                priceField.setText(String.valueOf(rs.getDouble("purchase_price")));
                calculateTotal();
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Selection Error: " + e.getMessage());
        }
    }

    private void calculateTotal() {
        try {
            if (quantityField.getText().isEmpty() || priceField.getText().isEmpty()) {
                totalField.setText("");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            totalField.setText(String.format("%.2f", quantity * price));

        } catch (Exception e) {
            totalField.setText("");
        }
    }
    // Add Method
    private void addBorrow() {
        if (!validateFields()) return;

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            int medicineId = getSelectedMedicineId();

            Connection con = getConnection();

            String insertBorrow = "INSERT INTO borrow (chemist_name, medicine_id, quantity, borrow_date, status) VALUES (?, ?, ?, CURDATE(), ?)";
            PreparedStatement stmt = con.prepareStatement(insertBorrow);

            stmt.setString(1, chemistField.getText());
            stmt.setInt(2, medicineId);
            stmt.setInt(3, quantity);
            stmt.setString(4, statusBox.getSelectedItem().toString());

            stmt.executeUpdate();

            String updateStock = "UPDATE medicine SET stock = stock + ? WHERE medicine_id = ?";
            PreparedStatement stmt2 = con.prepareStatement(updateStock);
            stmt2.setInt(1, quantity);
            stmt2.setInt(2, medicineId);
            stmt2.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(this, "Borrow Record Added Successfully");

            clearFields();
            loadMedicines();
            loadBorrowData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Borrow Error: " + e.getMessage());
        }
    }
    // Update Method
    private void updateBorrow() {
        if (selectedBorrowId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a borrow record from the table first");
            return;
        }

        if (!validateFields()) return;

        try {
            int newQuantity = Integer.parseInt(quantityField.getText());
            int newMedicineId = getSelectedMedicineId();

            Connection con = getConnection();

            String updateBorrow = "UPDATE borrow SET chemist_name=?, medicine_id=?, quantity=?, status=? WHERE borrow_id=?";
            PreparedStatement stmt = con.prepareStatement(updateBorrow);

            stmt.setString(1, chemistField.getText());
            stmt.setInt(2, newMedicineId);
            stmt.setInt(3, newQuantity);
            stmt.setString(4, statusBox.getSelectedItem().toString());
            stmt.setInt(5, selectedBorrowId);

            stmt.executeUpdate();

            if (oldMedicineId != -1) {
                String undoOldStock = "UPDATE medicine SET stock = stock - ? WHERE medicine_id = ?";
                PreparedStatement stmt2 = con.prepareStatement(undoOldStock);
                stmt2.setInt(1, oldQuantity);
                stmt2.setInt(2, oldMedicineId);
                stmt2.executeUpdate();

                String applyNewStock = "UPDATE medicine SET stock = stock + ? WHERE medicine_id = ?";
                PreparedStatement stmt3 = con.prepareStatement(applyNewStock);
                stmt3.setInt(1, newQuantity);
                stmt3.setInt(2, newMedicineId);
                stmt3.executeUpdate();
            }

            con.close();

            JOptionPane.showMessageDialog(this, "Borrow Record Updated Successfully");

            clearFields();
            loadMedicines();
            loadBorrowData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }
    // Load
    private void loadBorrowData() {
        tableModel.setRowCount(0);

        try {
            Connection con = getConnection();

            String query =
                    "SELECT b.borrow_id, b.chemist_name, m.name, b.quantity, m.purchase_price, " +
                    "(b.quantity * m.purchase_price) AS amount, b.borrow_date, b.status " +
                    "FROM borrow b JOIN medicine m ON b.medicine_id = m.medicine_id " +
                    "ORDER BY b.borrow_id DESC";

            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("borrow_id"),
                        rs.getString("chemist_name"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("purchase_price"),
                        rs.getDouble("amount"),
                        rs.getDate("borrow_date"),
                        rs.getString("status")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Borrow Load Error: " + e.getMessage());
        }
    }
    // Validation
    private boolean validateFields() {
        if (
                chemistField.getText().isEmpty() ||
                medicineBox.getSelectedItem() == null ||
                quantityField.getText().isEmpty()
        ) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number");
            return false;
        }

        return true;
    }
    // Clearing Fields
    private void clearFields() {
        selectedBorrowId = -1;
        oldMedicineId = -1;
        oldQuantity = 0;

        chemistField.setText("");
        quantityField.setText("");
        totalField.setText("");
        statusBox.setSelectedIndex(0);

        borrowTable.clearSelection();

        if (medicineBox.getItemCount() > 0) {
            medicineBox.setSelectedIndex(0);
            loadSelectedMedicineDetails();
        }
    }
}
