import java.awt.*;
import java.sql.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.*;

public class DashBoard extends JFrame {
    // Colours
    private final Color charcoal = new Color(40, 40, 40);
    private final Color dark = new Color(25, 25, 25);
    private final Color gold = new Color(212, 175, 55);
    private final Color BG = new Color(245, 245, 242);

    private JLabel inventoryValueLabel, monthlySalesLabel, monthlyProfitLabel;
    private JLabel borrowedAmountLabel, remainingAmountLabel;
    private PieChartPanel pieChartPanel;

    public DashBoard() {
        setTitle("Smart Pharmacy Management System - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        loadDashboardData();

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

        int y = 180;
        sidebar.add(menuButton("Medicine", y)); y += 62;
        sidebar.add(menuButton("Sale", y)); y += 62;
        sidebar.add(menuButton("Borrow", y)); y += 62;
        sidebar.add(menuButton("Udhaar", y)); y += 62;
        sidebar.add(menuButton("Search Menu", y)); y += 62;
        sidebar.add(menuButton("Exit", y));

        return sidebar;
    }

    private JButton menuButton(String text, int y) {
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
        // Main Connection between Multiple .Java Files
        if (text.equals("Exit")) {
            btn.addActionListener(e -> System.exit(0));
        } 
        else if (text.equals("Medicine")) {
            btn.addActionListener(e -> {
                new Medicine();
                dispose();
            });
        }
        else if (text.equals("Sale")) {
            btn.addActionListener(e -> {
                new Sale();
                dispose();
            });
        } 
        else if (text.equals("Borrow")) {
            btn.addActionListener(e -> {
                new Borrow();
                dispose();
            });
        }
        else if (text.equals("Udhaar")) {
            btn.addActionListener(e -> {
                new Udhaar();
                dispose();
            });
        }
        else if (text.equals("Search Menu")) {
            btn.addActionListener(e -> {
                new SearchMenu();
                dispose();
            });
        }
        else {
            btn.addActionListener(e ->
                    JOptionPane.showMessageDialog(this, text + " page will open here.")
            );
        }

        return btn;
    }
    // Main Panel
    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout(20, 15));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(charcoal);
        top.add(heading, BorderLayout.WEST);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(charcoal);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.addActionListener(e -> loadDashboardData());
        top.add(refreshBtn, BorderLayout.EAST);

        main.add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setOpaque(false);

        content.add(createBanner(), BorderLayout.NORTH);

        JPanel lower = new JPanel(new BorderLayout(20, 20));
        lower.setOpaque(false);

        JPanel centerArea = new JPanel(new GridLayout(1, 2, 20, 20));
        centerArea.setOpaque(false);

        centerArea.add(createCalendarPanel());

        pieChartPanel = new PieChartPanel();
        centerArea.add(pieChartPanel);

        lower.add(centerArea, BorderLayout.CENTER);
        lower.add(createRightStatsPanel(), BorderLayout.EAST);
        lower.add(createBottomStatsPanel(), BorderLayout.SOUTH);

        content.add(lower, BorderLayout.CENTER);
        main.add(content, BorderLayout.CENTER);

        return main;
    }
    // Subtitles
    private JPanel createBanner() {
        JPanel banner = new JPanel(null);
        banner.setPreferredSize(new Dimension(1000, 220));
        banner.setBackground(charcoal);
        banner.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel date = new JLabel(LocalDate.now().toString());
        date.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        date.setForeground(Color.LIGHT_GRAY);
        date.setBounds(35, 25, 300, 30);
        banner.add(date);

        JLabel welcome = new JLabel("Welcome back!");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 38));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(35, 85, 500, 50);
        banner.add(welcome);

        JLabel msg = new JLabel("Manage inventory, sales, credits and pharmacy records efficiently.");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        msg.setForeground(Color.WHITE);
        msg.setBounds(35, 140, 750, 30);
        banner.add(msg);

        JLabel insight = new JLabel("Real-time Pharmacy Insights");
        insight.setFont(new Font("Segoe UI", Font.BOLD, 15));
        insight.setForeground(gold);
        insight.setBounds(35, 175, 400, 25);
        banner.add(insight);

        return banner;    
    }
    // Statistics
    private JPanel createRightStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(300, 420));

        inventoryValueLabel = new JLabel("Rs. 0.00");
        monthlySalesLabel = new JLabel("Rs. 0.00");
        monthlyProfitLabel = new JLabel("Rs. 0.00");

        panel.add(statCard("Inventory Value", inventoryValueLabel));
        panel.add(statCard("Monthly Sales", monthlySalesLabel));
        panel.add(statCard("Monthly Profit", monthlyProfitLabel));

        return panel;
    }
    // Statistics
    private JPanel createBottomStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(800, 120));

        borrowedAmountLabel = new JLabel("Rs. 0.00");
        remainingAmountLabel = new JLabel("Rs. 0.00");

        panel.add(statCard("Borrowed Medicine Amount", borrowedAmountLabel));
        panel.add(statCard("Remaining Customer's Udhaar", remainingAmountLabel));

        return panel;
    }
    // Statistics
    private JPanel statCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(gold, 2));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(charcoal);
        titleLabel.setBounds(20, 18, 250, 25);
        card.add(titleLabel);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(dark);
        valueLabel.setBounds(20, 55, 250, 35);
        card.add(valueLabel);

        return card;
    }
    // Calendar
    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        LocalDate today = LocalDate.now();
        YearMonth ym = YearMonth.now();

        JLabel month = new JLabel(
                ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + ym.getYear()
        );
        month.setFont(new Font("Segoe UI", Font.BOLD, 26));
        month.setForeground(charcoal);
        month.setBounds(30, 25, 330, 35);
        panel.add(month);

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        int startX = 30;
        int startY = 85;
        int cellW = 58;
        int cellH = 34;

        for (int i = 0; i < days.length; i++) {
            JLabel d = new JLabel(days[i], SwingConstants.CENTER);
            d.setFont(new Font("Segoe UI", Font.BOLD, 13));
            d.setForeground(Color.GRAY);
            d.setBounds(startX + i * cellW, startY, cellW, cellH);
            panel.add(d);
        }

        LocalDate firstDay = ym.atDay(1);
        int firstDayIndex = firstDay.getDayOfWeek().getValue() % 7;
        int totalDays = ym.lengthOfMonth();

        for (int day = 1; day <= totalDays; day++) {
            int index = firstDayIndex + day - 1;
            int row = index / 7;
            int col = index % 7;

            JLabel dateLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

            if (day == today.getDayOfMonth()) {
                dateLabel.setOpaque(true);
                dateLabel.setBackground(charcoal);
                dateLabel.setForeground(Color.WHITE);
            } else {
                dateLabel.setForeground(charcoal);
            }

            dateLabel.setBounds(startX + col * cellW, startY + 45 + row * cellH, cellW, cellH);
            panel.add(dateLabel);
        }

        return panel;
    }
    // Stablishing Connection
    private Connection getConnection() throws Exception {
        // Using the DBConnection class to get the Connection
        return DBConnection.getConnection();
    }
    // Loading Dashboard Data From Database
    private void loadDashboardData() {
        double spent = getDouble("SELECT IFNULL(SUM(stock * purchase_price), 0) FROM medicine");

        double sold = getDouble("SELECT IFNULL(SUM(total_amount), 0) FROM sales");

        double gained = getDouble(
                "SELECT IFNULL(SUM((m.selling_price - m.purchase_price) * s.quantity), 0) " +
                "FROM sales s JOIN medicine m ON s.medicine_id = m.medicine_id"
        );

        double monthlySales = getDouble(
                "SELECT IFNULL(SUM(total_amount), 0) FROM sales " +
                "WHERE MONTH(sale_date)=MONTH(CURDATE()) AND YEAR(sale_date)=YEAR(CURDATE())"
        );

        double monthlyProfit = getDouble(
                "SELECT IFNULL(SUM((m.selling_price - m.purchase_price) * s.quantity), 0) " +
                "FROM sales s JOIN medicine m ON s.medicine_id = m.medicine_id " +
                "WHERE MONTH(s.sale_date)=MONTH(CURDATE()) AND YEAR(s.sale_date)=YEAR(CURDATE())"
        );

        double borrowed = getDouble(
                "SELECT IFNULL(SUM(b.quantity * m.purchase_price), 0) " +
                "FROM borrow b JOIN medicine m ON b.medicine_id = m.medicine_id " +
                "WHERE b.status = 'Unpaid'"
        );

        double remaining = getDouble(
                "SELECT IFNULL(SUM(amount), 0) FROM udhaar WHERE status='Unpaid'"
        );

        inventoryValueLabel.setText("Rs. " + format(spent));
        monthlySalesLabel.setText("Rs. " + format(monthlySales));
        monthlyProfitLabel.setText("Rs. " + format(monthlyProfit));
        borrowedAmountLabel.setText("Rs. " + format(borrowed));
        remainingAmountLabel.setText("Rs. " + format(remaining));
        // Sending Data to PieChart
        pieChartPanel.setValues(spent, sold, gained);
    }

    private double getDouble(String query) {
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double value = rs.getDouble(1);
                con.close();
                return value;
            }

            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    private String format(double value) {
        return String.format("%.2f", value);
    }
    // PieChart
    class PieChartPanel extends JPanel {

        private double spent = 0;
        private double sold = 0;
        private double gained = 0;

        public PieChartPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        }

        public void setValues(double spent, double sold, double gained) {
            this.spent = spent;
            this.sold = sold;
            this.gained = gained;
            repaint();
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(charcoal);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
            g2.drawString("Business Overview", 30, 45);

            double total = spent + sold + gained;

            if (total <= 0) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                g2.drawString("No data available yet", 30, 95);
                return;
            }

            int x = 50, y = 85, size = 210;
            int startAngle = 0;

            int spentAngle = (int) Math.round((spent / total) * 360);
            int soldAngle = (int) Math.round((sold / total) * 360);
            int gainedAngle = 360 - spentAngle - soldAngle;

            g2.setColor(new Color(80, 80, 80));
            g2.fillArc(x, y, size, size, startAngle, spentAngle);

            startAngle += spentAngle;
            g2.setColor(gold);
            g2.fillArc(x, y, size, size, startAngle, soldAngle);

            startAngle += soldAngle;
            g2.setColor(new Color(120, 120, 120));
            g2.fillArc(x, y, size, size, startAngle, gainedAngle);

            drawLegend(g2, 310, 115, new Color(80, 80, 80), "Spent", spent);
            drawLegend(g2, 310, 165, gold, "Sold", sold);
            drawLegend(g2, 310, 215, new Color(120, 120, 120), "Gained", gained);
        }

        private void drawLegend(Graphics2D g2, int x, int y, Color color, String label, double value) {
            g2.setColor(color);
            g2.fillRect(x, y, 18, 18);

            g2.setColor(charcoal);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            g2.drawString(label, x + 30, y + 15);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            g2.drawString("Rs. " + format(value), x + 30, y + 35);
        }
    }
}
