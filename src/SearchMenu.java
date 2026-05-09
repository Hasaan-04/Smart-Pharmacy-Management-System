import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SearchMenu extends JFrame {
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JComboBox<String> searchTypeBox, statusBox;
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    // Main Interface
    public SearchMenu() {
        setTitle("Searching Menu - Smart Pharmacy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        updateTableColumns();
        searchRecords();

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

    private JPanel createMainPanel() {
        JPanel main = new JPanel(null);
        main.setBackground(BG);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(charcoal);
        headerPanel.setBounds(50, 35, 1120, 110);
        main.add(headerPanel);

        JLabel heading = new JLabel("Search Menu");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 20, 450, 45);
        headerPanel.add(heading);

        JLabel subHeading = new JLabel("Search pharmacy records");
        subHeading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        subHeading.setForeground(gold);
        subHeading.setBounds(32, 68, 400, 25);
        headerPanel.add(subHeading);

        JPanel searchPanel = createSearchPanel();
        searchPanel.setBounds(50, 170, 1120, 170);
        main.add(searchPanel);

        JPanel tablePanel = createTablePanel();
        tablePanel.setBounds(50, 370, 1120, 420);
        main.add(tablePanel);

        return main;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(gold, 2));

        JLabel title = new JLabel("Search Filters");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(30, 20, 300, 35);
        panel.add(title);

        JLabel typeLabel = label("Search Type");
        typeLabel.setBounds(30, 70, 200, 25);
        panel.add(typeLabel);

        searchTypeBox = new JComboBox<>(new String[]{"Medicine", "Borrow", "Udhaar"});
        searchTypeBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        searchTypeBox.setBounds(30, 100, 240, 38);
        panel.add(searchTypeBox);

        JLabel searchLabel = label("Search Text");
        searchLabel.setBounds(310, 70, 200, 25);
        panel.add(searchLabel);

        searchField = input();
        searchField.setBounds(310, 100, 300, 38);
        panel.add(searchField);

        JLabel statusLabel = label("Status");
        statusLabel.setBounds(650, 70, 200, 25);
        panel.add(statusLabel);

        statusBox = new JComboBox<>(new String[]{"All", "Unpaid", "Paid"});
        statusBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        statusBox.setBounds(650, 100, 200, 38);
        panel.add(statusBox);

        JButton searchBtn = actionButton("SEARCH");
        searchBtn.setBounds(890, 100, 100, 38);
        searchBtn.addActionListener(e -> searchRecords());
        panel.add(searchBtn);

        JButton clearBtn = actionButton("CLEAR");
        clearBtn.setBounds(1010, 100, 90, 38);
        clearBtn.addActionListener(e -> clearSearch());
        panel.add(clearBtn);

        searchTypeBox.addActionListener(e -> {
            updateTableColumns();
            searchRecords();
        });

        statusBox.addActionListener(e -> searchRecords());

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchRecords();
            }
        });

        return panel;
    }
    // Dispaly Table
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel title = new JLabel("Search Results");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(charcoal);
        title.setBounds(20, 10, 300, 35);
        panel.add(title);

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultTable.setRowHeight(28);
        resultTable.setSelectionBackground(gold);
        resultTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = resultTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(charcoal);
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(20, 55, 1080, 340);
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

    private void updateTableColumns() {
        String type = searchTypeBox.getSelectedItem().toString();

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        if (type.equals("Medicine")) {
            statusBox.setEnabled(false);

            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Category");
            tableModel.addColumn("Purchase Price");
            tableModel.addColumn("Selling Price");
            tableModel.addColumn("Stock");
        } 
        else if (type.equals("Borrow")) {
            statusBox.setEnabled(true);

            tableModel.addColumn("Borrow ID");
            tableModel.addColumn("Chemist");
            tableModel.addColumn("Medicine");
            tableModel.addColumn("Quantity");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Date");
            tableModel.addColumn("Status");
        } 
        else if (type.equals("Udhaar")) {
            statusBox.setEnabled(true);

            tableModel.addColumn("Udhaar ID");
            tableModel.addColumn("Customer");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Date");
            tableModel.addColumn("Status");
        }
    }
    // Searching
    private void searchRecords() {
        if (searchTypeBox == null || tableModel == null) return;

        String type = searchTypeBox.getSelectedItem().toString();

        if (type.equals("Medicine")) {
            searchMedicine();
        } else if (type.equals("Borrow")) {
            searchBorrow();
        } else if (type.equals("Udhaar")) {
            searchUdhaar();
        }
    }
    // Searching Medicine
    private void searchMedicine() {
        tableModel.setRowCount(0);

        String searchText = searchField.getText();

        try {
            Connection con = getConnection();

            String query =
                    "SELECT medicine_id, name, category, purchase_price, selling_price, stock " +
                    "FROM medicine " +
                    "WHERE name LIKE ? OR category LIKE ? " +
                    "ORDER BY name";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");

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
            JOptionPane.showMessageDialog(this, "Medicine Search Error: " + e.getMessage());
        }
    }
    // Searching Borrown
    private void searchBorrow() {
        tableModel.setRowCount(0);

        String searchText = searchField.getText();
        String status = statusBox.getSelectedItem().toString();

        try {
            Connection con = getConnection();

            String query =
                    "SELECT b.borrow_id, b.chemist_name, m.name, b.quantity, " +
                    "(b.quantity * m.purchase_price) AS amount, b.borrow_date, b.status " +
                    "FROM borrow b JOIN medicine m ON b.medicine_id = m.medicine_id " +
                    "WHERE (b.chemist_name LIKE ? OR m.name LIKE ?) ";

            if (!status.equals("All")) {
                query += "AND b.status = ? ";
            }

            query += "ORDER BY b.borrow_id DESC";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");

            if (!status.equals("All")) {
                stmt.setString(3, status);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("borrow_id"),
                        rs.getString("chemist_name"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("amount"),
                        rs.getDate("borrow_date"),
                        rs.getString("status")
                });
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Borrow Search Error: " + e.getMessage());
        }
    }
    // Searching Udhaar
    private void searchUdhaar() {
        tableModel.setRowCount(0);

        String searchText = searchField.getText();
        String status = statusBox.getSelectedItem().toString();

        try {
            Connection con = getConnection();

            String query =
                    "SELECT udhaar_id, customer_name, amount, date, status " +
                    "FROM udhaar " +
                    "WHERE customer_name LIKE ? ";

            if (!status.equals("All")) {
                query += "AND status = ? ";
            }

            query += "ORDER BY udhaar_id DESC";

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");

            if (!status.equals("All")) {
                stmt.setString(2, status);
            }

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
            JOptionPane.showMessageDialog(this, "Udhaar Search Error: " + e.getMessage());
        }
    }
    // Clearing Fields
    private void clearSearch() {
        searchField.setText("");
        statusBox.setSelectedIndex(0);
        searchRecords();
    }
}
