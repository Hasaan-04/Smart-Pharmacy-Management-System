import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Sale extends JFrame {

    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JComboBox<String> medicineBox;
    private JTextField quantityField, priceField, totalField, stockField;
    private JTable salesTable;
    private DefaultTableModel tableModel;
    // Main Interface
    public Sale() {
        setTitle("Add Sale - Smart Pharmacy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadMedicines();
        loadSalesData();

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
    // Buttons
    private JButton sidebarButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(25, y, 210, 44);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(charcoal);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        JLabel heading = new JLabel("Sale");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 20, 400, 45);
        headerPanel.add(heading);

        JLabel subHeading = new JLabel("Manage Sales Records");
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

        JLabel title = new JLabel("Sale Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(30, 20, 300, 35);
        panel.add(title);

        JLabel medicineLabel = label("Medicine Name");
        medicineLabel.setBounds(30, 75, 200, 25);
        panel.add(medicineLabel);

        medicineBox = new JComboBox<>();
        medicineBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        medicineBox.setBounds(30, 105, 270, 38);
        medicineBox.addActionListener(e -> loadSelectedMedicineDetails());
        panel.add(medicineBox);

        JLabel quantityLabel = label("Quantity");
        quantityLabel.setBounds(340, 75, 200, 25);
        panel.add(quantityLabel);

        quantityField = input();
        quantityField.setBounds(340, 105, 270, 38);
        quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculateTotal();
            }
        });
        panel.add(quantityField);

        JLabel priceLabel = label("Selling Price");
        priceLabel.setBounds(650, 75, 200, 25);
        panel.add(priceLabel);

        priceField = input();
        priceField.setBounds(650, 105, 270, 38);
        priceField.setEditable(false);
        panel.add(priceField);

        JLabel stockLabel = label("Available Stock");
        stockLabel.setBounds(30, 155, 200, 25);
        panel.add(stockLabel);

        stockField = input();
        stockField.setBounds(30, 185, 270, 38);
        stockField.setEditable(false);
        panel.add(stockField);

        JLabel totalLabel = label("Total Amount");
        totalLabel.setBounds(340, 155, 200, 25);
        panel.add(totalLabel);

        totalField = input();
        totalField.setBounds(340, 185, 270, 38);
        totalField.setEditable(false);
        panel.add(totalField);

        JButton addBtn = actionButton("ADD SALE");
        addBtn.setBounds(650, 185, 130, 40);
        addBtn.addActionListener(e -> addSale());
        panel.add(addBtn);

        JButton clearBtn = actionButton("CLEAR");
        clearBtn.setBounds(800, 185, 100, 40);
        clearBtn.addActionListener(e -> clearFields());
        panel.add(clearBtn);

        return panel;
    }
    // Display Table
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel title = new JLabel("Sales Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(20, 10, 300, 35);
        panel.add(title);

        tableModel = new DefaultTableModel(
                new String[]{"Sale ID", "Medicine", "Quantity", "Selling Price", "Total Amount", "Date"}, 0
        );

        salesTable = new JTable(tableModel);
        salesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        salesTable.setRowHeight(28);

        JTableHeader header = salesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(charcoal);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBounds(20, 55, 1080, 280);
        panel.add(scrollPane);

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
    // Buttons
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
    // Loading Medicine for Display
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

            String query = "SELECT selling_price, stock FROM medicine WHERE name=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, medicineBox.getSelectedItem().toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                priceField.setText(String.valueOf(rs.getDouble("selling_price")));
                stockField.setText(String.valueOf(rs.getInt("stock")));
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
            double total = quantity * price;

            totalField.setText(String.format("%.2f", total));

        } catch (Exception e) {
            totalField.setText("");
        }
    }
    // Add Method
    private void addSale() {
        if (medicineBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a medicine");
            return;
        }

        if (quantityField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            int availableStock = Integer.parseInt(stockField.getText());

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                return;
            }

            if (quantity > availableStock) {
                JOptionPane.showMessageDialog(this, "Not enough stock available");
                return;
            }

            int medicineId = getSelectedMedicineId();
            double totalAmount = Double.parseDouble(totalField.getText());

            Connection con = getConnection();

            String insertSale = "INSERT INTO sales (medicine_id, quantity, total_amount, sale_date) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement stmt = con.prepareStatement(insertSale);
            stmt.setInt(1, medicineId);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, totalAmount);
            stmt.executeUpdate();

            String updateStock = "UPDATE medicine SET stock = stock - ? WHERE medicine_id = ?";
            PreparedStatement pst2 = con.prepareStatement(updateStock);
            pst2.setInt(1, quantity);
            pst2.setInt(2, medicineId);
            pst2.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(this, "Sale Added Successfully");

            clearFields();
            loadMedicines();
            loadSalesData();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sale Error: " + e.getMessage());
        }
    }
    // Loading Sales Data
    private void loadSalesData() {
        tableModel.setRowCount(0);

        try {
            Connection con = getConnection();

            String query =
                    "SELECT s.sale_id, m.name, s.quantity, m.selling_price, s.total_amount, s.sale_date " +
                    "FROM sales s JOIN medicine m ON s.medicine_id = m.medicine_id " +
                    "ORDER BY s.sale_id DESC";

            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("sale_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("selling_price"),
                        rs.getDouble("total_amount"),
                        rs.getDate("sale_date")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sales Load Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        quantityField.setText("");
        totalField.setText("");

        if (medicineBox.getItemCount() > 0) {
            medicineBox.setSelectedIndex(0);
            loadSelectedMedicineDetails();
        }
    }
}
