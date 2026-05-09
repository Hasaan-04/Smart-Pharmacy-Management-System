import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Medicine extends JFrame {
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JTextField nameField, categoryField, purchaseField, sellingField, stockField;
    private JTable medicineTable;
    private DefaultTableModel tableModel;

    private int selectedMedicineId = -1;
    // Main Interface
    public Medicine() {
        setTitle("Add Medicine - Smart Pharmacy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadMedicineData();

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
        // Mouse Effect
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

        JLabel heading = new JLabel("Medicine");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 20, 400, 45);
        headerPanel.add(heading);

        JLabel subHeading = new JLabel("Manage Medicine Records");
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

        JLabel title = new JLabel("Medicine Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(30, 20, 300, 35);
        panel.add(title);

        JLabel nameLabel = label("Medicine Name");
        nameLabel.setBounds(30, 75, 200, 25);
        panel.add(nameLabel);

        nameField = input();
        nameField.setBounds(30, 105, 270, 38);
        panel.add(nameField);

        JLabel categoryLabel = label("Category");
        categoryLabel.setBounds(340, 75, 200, 25);
        panel.add(categoryLabel);

        categoryField = input();
        categoryField.setBounds(340, 105, 270, 38);
        panel.add(categoryField);

        JLabel purchaseLabel = label("Purchase Price");
        purchaseLabel.setBounds(650, 75, 200, 25);
        panel.add(purchaseLabel);

        purchaseField = input();
        purchaseField.setBounds(650, 105, 270, 38);
        panel.add(purchaseField);

        JLabel sellingLabel = label("Selling Price");
        sellingLabel.setBounds(30, 155, 200, 25);
        panel.add(sellingLabel);

        sellingField = input();
        sellingField.setBounds(30, 185, 270, 38);
        panel.add(sellingField);

        JLabel stockLabel = label("Stock Quantity");
        stockLabel.setBounds(340, 155, 200, 25);
        panel.add(stockLabel);

        stockField = input();
        stockField.setBounds(340, 185, 270, 38);
        panel.add(stockField);

        JButton addBtn = actionButton("ADD");
        addBtn.setBounds(650, 185, 100, 40);
        addBtn.addActionListener(e -> addMedicine());
        panel.add(addBtn);

        JButton updateBtn = actionButton("UPDATE");
        updateBtn.setBounds(770, 185, 100, 40);
        updateBtn.addActionListener(e -> updateMedicine());
        panel.add(updateBtn);

        JButton deleteBtn = actionButton("DELETE");
        deleteBtn.setBounds(890, 185, 100, 40);
        deleteBtn.addActionListener(e -> deleteMedicine());
        panel.add(deleteBtn);

        JButton clearBtn = actionButton("CLEAR");
        clearBtn.setBounds(1010, 185, 90, 40);
        clearBtn.addActionListener(e -> clearFields());
        panel.add(clearBtn);

        return panel;
    }
    // Dispaly Table
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel title = new JLabel("Medicine Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(20, 10, 300, 35);
        panel.add(title);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Category", "Purchase Price", "Selling Price", "Stock"}, 0
        );

        medicineTable = new JTable(tableModel);
        medicineTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        medicineTable.setRowHeight(28);
        medicineTable.setSelectionBackground(gold);
        medicineTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = medicineTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(charcoal);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        scrollPane.setBounds(20, 55, 1080, 280);
        panel.add(scrollPane);

        medicineTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && medicineTable.getSelectedRow() != -1) {
                int row = medicineTable.getSelectedRow();

                selectedMedicineId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                categoryField.setText(tableModel.getValueAt(row, 2).toString());
                purchaseField.setText(tableModel.getValueAt(row, 3).toString());
                sellingField.setText(tableModel.getValueAt(row, 4).toString());
                stockField.setText(tableModel.getValueAt(row, 5).toString());
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
    // Add Method
    private void addMedicine() {
        if (!validateFields()) return;

        try {
            Connection con = getConnection();

            String query = "INSERT INTO medicine (name, category, purchase_price, selling_price, stock) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, nameField.getText());
            stmt.setString(2, categoryField.getText());
            stmt.setDouble(3, Double.parseDouble(purchaseField.getText()));
            stmt.setDouble(4, Double.parseDouble(sellingField.getText()));
            stmt.setInt(5, Integer.parseInt(stockField.getText()));

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Medicine Added Successfully");

            con.close();
            clearFields();
            loadMedicineData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add Error: " + e.getMessage());
        }
    }
    // Update Method
    private void updateMedicine() {
        if (selectedMedicineId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a medicine from the table first");
            return;
        }

        if (!validateFields()) return;

        try {
            Connection con = getConnection();

            String query = "UPDATE medicine SET name=?, category=?, purchase_price=?, selling_price=?, stock=? WHERE medicine_id=?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, nameField.getText());
            stmt.setString(2, categoryField.getText());
            stmt.setDouble(3, Double.parseDouble(purchaseField.getText()));
            stmt.setDouble(4, Double.parseDouble(sellingField.getText()));
            stmt.setInt(5, Integer.parseInt(stockField.getText()));
            stmt.setInt(6, selectedMedicineId);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Medicine Updated Successfully");

            con.close();
            clearFields();
            loadMedicineData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }
    // Delete Method
    private void deleteMedicine() {
        if (selectedMedicineId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a medicine from the table first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this medicine?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection con = getConnection();

            String query = "DELETE FROM medicine WHERE medicine_id=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, selectedMedicineId);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Medicine Deleted Successfully");

            con.close();
            clearFields();
            loadMedicineData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
        }
    }
    // Loading Medicine for Display
    private void loadMedicineData() {
        tableModel.setRowCount(0);

        try {
            Connection con = getConnection();

            String query = "SELECT medicine_id, name, category, purchase_price, selling_price, stock FROM medicine";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("medicine_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("purchase_price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("stock")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Error: " + e.getMessage());
        }
    }
    // Validation
    private boolean validateFields() {
        if (
                nameField.getText().isEmpty() ||
                categoryField.getText().isEmpty() ||
                purchaseField.getText().isEmpty() ||
                sellingField.getText().isEmpty() ||
                stockField.getText().isEmpty()
        ) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return false;
        }

        try {
            Double.parseDouble(purchaseField.getText());
            Double.parseDouble(sellingField.getText());
            Integer.parseInt(stockField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Prices must be numbers and stock must be an integer");
            return false;
        }

        return true;
    }
    // Clearing
    private void clearFields() {
        selectedMedicineId = -1;
        nameField.setText("");
        categoryField.setText("");
        purchaseField.setText("");
        sellingField.setText("");
        stockField.setText("");
        medicineTable.clearSelection();
    }
}
