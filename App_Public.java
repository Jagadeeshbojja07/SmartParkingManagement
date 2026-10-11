import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class App {
        static String vehicleType;

    static final String URL = "jdbc:mysql://localhost:3306/smart_parking";
    static final String USER = "root";
    static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    static JFrame frame;
    static JPanel parkingPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::showLogin);
    }

    // ================= PROFESSIONAL LOGIN =================
    static void showLogin() {
        frame = new JFrame("SmartPark | Login");
        frame.setSize(850, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(BG);

        JPanel brand = new JPanel();
        brand.setBackground(NAVY);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBorder(BorderFactory.createEmptyBorder(65, 55, 50, 40));

        JLabel car = new JLabel("🚗");
        car.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 65));
        car.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("SMARTPARK");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel line = new JLabel("SMART PARKING MANAGEMENT");
        line.setFont(new Font("Segoe UI", Font.BOLD, 12));
        line.setForeground(BLUE);
        line.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel info = new JLabel("<html><br>Park smarter. Drive easier.<br>"
                + "Manage slots, vehicles and reservations<br>"
                + "from one professional dashboard.</html>");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setForeground(MUTED);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);

        brand.add(car);
        brand.add(Box.createVerticalStrut(8));
        brand.add(title);
        brand.add(line);
        brand.add(info);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 20, 7, 20);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcome = new JLabel("Welcome Back");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(TEXT);

        JLabel hint = new JLabel("Sign in to access your parking dashboard");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(MUTED);

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        for (JTextField f : new JTextField[]{emailField, passwordField}) {
            f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            f.setForeground(TEXT);
            f.setBackground(CARD);
            f.setCaretColor(TEXT);
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(55, 75, 100)),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        }

        JButton loginButton = new JButton("SIGN IN  →");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        g.gridx=0; g.gridy=0; g.gridwidth=2;
        form.add(welcome, g);
        g.gridy++; form.add(hint, g);

        g.gridy++; g.gridwidth=1;
        JLabel el = new JLabel("EMAIL");
        el.setForeground(MUTED); el.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(el, g);
        g.gridx=1; form.add(emailField, g);

        g.gridx=0; g.gridy++;
        JLabel pl = new JLabel("PASSWORD");
        pl.setForeground(MUTED); pl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        form.add(pl, g);
        g.gridx=1; form.add(passwordField, g);

        g.gridx=0; g.gridy++; g.gridwidth=2;
        form.add(loginButton, g);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (login(email, password)) {
                frame.dispose();
                showParkingDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Email or Password!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        root.add(brand);
        root.add(form);
        frame.add(root);
        frame.setVisible(true);
    }

    // ================= LOGIN CHECK =================
    static boolean login(String email, String password) {

        try {
            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

            String sql =
                    "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            boolean success = rs.next();

            con.close();

            return success;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database Error!\n" + e.getMessage()
            );
            return false;
        }
    }

    // ================= PROFESSIONAL DASHBOARD =================
    static final Color NAVY = new Color(10, 20, 38);
    static final Color NAVY2 = new Color(18, 32, 56);
    static final Color CARD = new Color(25, 41, 68);
    static final Color BG = new Color(8, 17, 32);
    static final Color TEXT = new Color(235, 242, 250);
    static final Color MUTED = new Color(155, 170, 190);
    static final Color GREEN = new Color(35, 190, 115);
    static final Color RED = new Color(235, 75, 80);
    static final Color ORANGE = new Color(245, 160, 55);
    static final Color BLUE = new Color(55, 135, 235);

    static JLabel totalValue, availableValue, occupiedValue, reservedValue;

    static JButton navButton(String icon, String name) {
        JButton b = new JButton(icon + "   " + name);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setForeground(TEXT);
        b.setBackground(NAVY);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(13, 20, 13, 10));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    static JLabel statValue() {
        JLabel l = new JLabel("0");
        l.setFont(new Font("Segoe UI", Font.BOLD, 27));
        l.setForeground(TEXT);
        return l;
    }

    static JPanel statCard(String icon, String title, Color accent, JLabel[] holder) {
        JPanel p = new JPanel(new BorderLayout(12, 4));
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 65, 92)),
                BorderFactory.createEmptyBorder(13, 18, 13, 18)));

        JLabel i = new JLabel(icon);
        i.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        p.add(i, BorderLayout.WEST);

        JPanel words = new JPanel();
        words.setOpaque(false);
        words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));

        JLabel t = new JLabel(title.toUpperCase());
        t.setFont(new Font("Segoe UI", Font.BOLD, 11));
        t.setForeground(MUTED);

        JLabel v = statValue();
        v.setForeground(accent);
        holder[0] = v;

        words.add(t);
        words.add(Box.createVerticalStrut(3));
        words.add(v);
        p.add(words, BorderLayout.CENTER);
        return p;
    }

    static void showParkingDashboard() {
        frame = new JFrame("SmartPark | Parking Management");
        frame.setSize(1250, 800);
        frame.setMinimumSize(new Dimension(1050, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        // ---------- SIDEBAR ----------
        JPanel side = new JPanel();
        side.setBackground(NAVY);
        side.setPreferredSize(new Dimension(225, 0));
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(BorderFactory.createEmptyBorder(22, 12, 18, 12));

        JLabel logo = new JLabel("🚗  SMARTPARK");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(logo);

        JLabel sub = new JLabel("  PARKING MANAGEMENT");
        sub.setFont(new Font("Segoe UI", Font.BOLD, 9));
        sub.setForeground(BLUE);
        sub.setBorder(BorderFactory.createEmptyBorder(3, 10, 25, 0));
        side.add(sub);

        JButton dashboard = navButton("⌂", "Dashboard");
        JButton park = navButton("🚗", "Park Vehicle");
        JButton search = navButton("⌕", "Search Vehicle");
        JButton reserve = navButton("P", "Reserve Slot");
        JButton reservations = navButton("▣", "Reservations");
        JButton history = navButton("▤", "Parking History");
        JButton statistics = navButton("▥", "Statistics");

        JButton[] navs = {dashboard, park, search, reserve, reservations, history, statistics};
        for (JButton b : navs) {
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
            side.add(b);
            side.add(Box.createVerticalStrut(3));
        }

        side.add(Box.createVerticalGlue());

        JButton logout = navButton("⇥", "Logout");
        logout.setForeground(new Color(255, 120, 120));
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        side.add(logout);

        root.add(side, BorderLayout.WEST);

        // ---------- MAIN ----------
        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Parking Dashboard");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 29));
        welcome.setForeground(TEXT);

        JLabel desc = new JLabel("Monitor your parking facility in real time");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        desc.setForeground(MUTED);

        heading.add(welcome);
        heading.add(Box.createVerticalStrut(3));
        heading.add(desc);

        JLabel user = new JLabel("●  ADMIN");
        user.setFont(new Font("Segoe UI", Font.BOLD, 12));
        user.setForeground(GREEN);

        top.add(heading, BorderLayout.WEST);
        top.add(user, BorderLayout.EAST);
        main.add(top, BorderLayout.NORTH);

        // ---------- CONTENT ----------
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 4, 13, 0));
        stats.setOpaque(false);

        JLabel[] h = new JLabel[1];
        stats.add(statCard("▣", "Total Slots", BLUE, h));
        totalValue = h[0];
        h = new JLabel[1];
        stats.add(statCard("✓", "Available", GREEN, h));
        availableValue = h[0];
        h = new JLabel[1];
        stats.add(statCard("🚗", "Occupied", RED, h));
        occupiedValue = h[0];
        h = new JLabel[1];
        stats.add(statCard("P", "Reserved", ORANGE, h));
        reservedValue = h[0];

        content.add(stats, BorderLayout.NORTH);

        JPanel parkingWrap = new JPanel(new BorderLayout(0, 10));
        parkingWrap.setOpaque(false);

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);

        JLabel parkingTitle = new JLabel("🅿  LIVE PARKING AREA");
        parkingTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        parkingTitle.setForeground(TEXT);

        JLabel legend = new JLabel("● Available    ● Occupied    ● Reserved");
        legend.setFont(new Font("Segoe UI", Font.BOLD, 11));
        legend.setForeground(MUTED);

        titleRow.add(parkingTitle, BorderLayout.WEST);
        titleRow.add(legend, BorderLayout.EAST);
        parkingWrap.add(titleRow, BorderLayout.NORTH);

        parkingPanel = new JPanel(new GridLayout(0, 3, 14, 14));
        parkingPanel.setOpaque(false);
        parkingPanel.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2));

        JScrollPane scroll = new JScrollPane(parkingPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        parkingWrap.add(scroll, BorderLayout.CENTER);
        content.add(parkingWrap, BorderLayout.CENTER);

        main.add(content, BorderLayout.CENTER);
        root.add(main, BorderLayout.CENTER);

        // ---------- ACTIONS ----------
        history.addActionListener(e -> showParkingHistory());
        statistics.addActionListener(e -> showStatistics());
        search.addActionListener(e -> searchVehicle());
        reserve.addActionListener(e -> reserveSlot());
        reservations.addActionListener(e -> showReservations());
        park.addActionListener(e -> chooseFreeSlotAndPark());
        logout.addActionListener(e -> {
            frame.dispose();
            showLogin();
        });

        frame.add(root);
        loadSlots();
        frame.setVisible(true);
    }

    static void chooseFreeSlotAndPark() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT slot_number FROM parking_slots WHERE status='FREE'");
            java.util.ArrayList<String> free = new java.util.ArrayList<>();
            while (rs.next()) free.add(rs.getString("slot_number"));
            rs.close(); st.close(); con.close();

            if (free.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No parking slots are currently available.",
                        "Parking Full", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String slot = (String) JOptionPane.showInputDialog(frame,
                    "Select a free parking slot:", "Park Vehicle",
                    JOptionPane.QUESTION_MESSAGE, null, free.toArray(), free.get(0));
            if (slot != null) parkVehicle(slot);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Database Error!\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= LOAD PARKING SLOTS =================
    static void loadSlots() {

        parkingPanel.removeAll();

        try {

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

            String sql = "SELECT * FROM parking_slots";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String slot = rs.getString("slot_number");
                String status = rs.getString("status");
                String vehicle = rs.getString("vehicle_number");

                JPanel slotPanel = createSlotPanel(
                        id,
                        slot,
                        status,
                        vehicle
                );

                parkingPanel.add(slotPanel);
            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Database Error!\n" + e.getMessage()
            );
        }

        parkingPanel.revalidate();
        parkingPanel.repaint();
        updateDashboardCounts();
    }

    static void updateDashboardCounts() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
        "SELECT COUNT(*) total, " +
        "SUM(CASE WHEN ps.status='FREE' AND NOT EXISTS " +
        "(SELECT 1 FROM reservations r WHERE r.slot_number=ps.slot_number) " +
        "THEN 1 ELSE 0 END) free, " +
        "SUM(CASE WHEN ps.status='OCCUPIED' THEN 1 ELSE 0 END) occupied, " +
        "SUM(CASE WHEN EXISTS " +
        "(SELECT 1 FROM reservations r WHERE r.slot_number=ps.slot_number) " +
        "THEN 1 ELSE 0 END) reserved " +
        "FROM parking_slots ps"
);
            if (rs.next()) {
                totalValue.setText(String.valueOf(rs.getInt("total")));
                availableValue.setText(String.valueOf(rs.getInt("free")));
                occupiedValue.setText(String.valueOf(rs.getInt("occupied")));
                reservedValue.setText(String.valueOf(rs.getInt("reserved")));
            }
            rs.close(); st.close(); con.close();
        } catch (Exception e) {
            // Dashboard counts should never stop the parking screen.
        }
    }

    // ================= PROFESSIONAL SLOT CARD =================
    static JPanel createSlotPanel(int id, String slot, String status, String vehicle) {
        JPanel panel = new JPanel(new BorderLayout(8, 6));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        status.equalsIgnoreCase("FREE") ? new Color(45, 75, 105) :
                        status.equalsIgnoreCase("OCCUPIED") ? new Color(110, 45, 55) :
                        new Color(110, 80, 35), 1),
                BorderFactory.createEmptyBorder(13, 15, 13, 15)));
// Check whether this slot has an active reservation
if (status.equalsIgnoreCase("FREE")) {
    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        String reservationCheck =
                "SELECT 1 FROM reservations WHERE slot_number=? LIMIT 1";

        PreparedStatement reservationPs =
                con.prepareStatement(reservationCheck);

        reservationPs.setString(1, slot);

        ResultSet reservationRs =
                reservationPs.executeQuery();

        if (reservationRs.next()) {
            status = "RESERVED";
        }

        reservationRs.close();
        reservationPs.close();
        con.close();

    } catch (Exception e) {
        // Keep normal FREE status if reservation check fails
    }
}
        boolean free = status.equalsIgnoreCase("FREE");
        boolean occupied = status.equalsIgnoreCase("OCCUPIED");
        Color accent = free ? GREEN : occupied ? RED : ORANGE;
        panel.setBackground(free ? new Color(18, 48, 48) :
                            occupied ? new Color(48, 25, 36) :
                            new Color(52, 40, 22));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel sign = new JLabel("P");
        sign.setHorizontalAlignment(SwingConstants.CENTER);
        sign.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sign.setForeground(Color.WHITE);
        sign.setOpaque(true);
        sign.setBackground(BLUE);
        sign.setPreferredSize(new Dimension(40, 40));

        JLabel slotLabel = new JLabel("  " + slot);
        slotLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        slotLabel.setForeground(TEXT);

        JLabel statusLabel = new JLabel(status.toUpperCase());
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setForeground(accent);

        top.add(sign, BorderLayout.WEST);
        top.add(slotLabel, BorderLayout.CENTER);
        top.add(statusLabel, BorderLayout.EAST);
        panel.add(top, BorderLayout.NORTH);

        JPanel middle = new JPanel(new BorderLayout());
        middle.setOpaque(false);

        JLabel car = new JLabel(free ? "🅿" : "🚗", SwingConstants.CENTER);
        car.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        car.setForeground(accent);

        String vehicleText = vehicle == null || vehicle.trim().isEmpty()
                ? "Ready for parking" : vehicle;
        JLabel vehicleLabel = new JLabel(vehicleText, SwingConstants.CENTER);
        vehicleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        vehicleLabel.setForeground(free ? MUTED : TEXT);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(car, BorderLayout.CENTER);
        center.add(vehicleLabel, BorderLayout.SOUTH);

        middle.add(center, BorderLayout.CENTER);
        panel.add(middle, BorderLayout.CENTER);

        JButton action = new JButton(free ? "PARK VEHICLE" : "REMOVE VEHICLE");
        action.setFont(new Font("Segoe UI", Font.BOLD, 11));
        action.setForeground(Color.WHITE);
        action.setBackground(free ? new Color(28, 125, 100) : new Color(155, 55, 65));
        action.setFocusPainted(false);
        action.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        action.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (free) action.addActionListener(ev -> parkVehicle(slot));
        else action.addActionListener(ev -> removeVehicle(slot));

        panel.add(action, BorderLayout.SOUTH);
        return panel;
    }

    // ================= PARK VEHICLE =================
    static void parkVehicle(String slot) {

        String vehicle = JOptionPane.showInputDialog(
                frame,
                "Enter Vehicle Number:",
                "Park Vehicle",
                JOptionPane.QUESTION_MESSAGE
        );

        if (vehicle == null || vehicle.trim().isEmpty()) {
            return;
        }
         vehicleType = JOptionPane.showInputDialog(
    frame,
    "Enter Vehicle Type:\n1. 2-Wheeler\n2. 4-Wheeler"
);

if (vehicleType == null || vehicleType.trim().isEmpty()) {
    return;
}

        try {

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );
// CHECK IF SLOT IS RESERVED
String reservationSql =
        "SELECT vehicle_number FROM reservations WHERE slot_number=?";

PreparedStatement reservationPs =
        con.prepareStatement(reservationSql);

reservationPs.setString(1, slot);

ResultSet reservationRs =
        reservationPs.executeQuery();

if (reservationRs.next()) {

    String reservedVehicle =
            reservationRs.getString("vehicle_number");

    if (!reservedVehicle.equalsIgnoreCase(vehicle.trim())) {

        JOptionPane.showMessageDialog(
                frame,
                "Slot " + slot +
                " is reserved for vehicle " +
                reservedVehicle +
                ".\nThis vehicle cannot park here.",
                "Slot Reserved",
                JOptionPane.WARNING_MESSAGE
        );

        reservationRs.close();
        reservationPs.close();
        con.close();
        return;
    }
}

reservationRs.close();
reservationPs.close();
            String sql =
                    "UPDATE parking_slots " +
                    "SET status='OCCUPIED', vehicle_number=? " +
                    "WHERE slot_number=? AND status='FREE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, vehicle);
            ps.setString(2, slot);

            int result = ps.executeUpdate();

if (result > 0) {

    String historySql =
    "INSERT INTO parking_records " +
    "(vehicle_number, vehicle_type, slot_number, entry_time, fee) " +
    "VALUES (?, ?, ?, CURRENT_TIMESTAMP, 0)";
    PreparedStatement historyPs =
            con.prepareStatement(historySql);

    historyPs.setString(1, vehicle);
historyPs.setString(2, vehicleType);
historyPs.setString(3, slot);
    historyPs.executeUpdate();

    historyPs.close();
}

con.close();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Vehicle parked successfully!"
                );

                loadSlots();

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Slot is already occupied!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Database Error!\n" + e.getMessage()
            );
        }
    }
    

    // ================= REMOVE VEHICLE =================
    
    static void removeVehicle(String slot) {

        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Remove vehicle from slot " + slot + "?",
                "Remove Vehicle",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );
         String feeSql =
    "SELECT id, entry_time FROM parking_records " +
    "WHERE slot_number=? ORDER BY entry_time DESC LIMIT 1";

PreparedStatement feePs = con.prepareStatement(feeSql);
feePs.setString(1, slot);

ResultSet feeRs = feePs.executeQuery();

double fee = 0;

int recordId = -1;

if (feeRs.next()) {
    recordId = feeRs.getInt("id");

    Timestamp entryTime = feeRs.getTimestamp("entry_time");

    long durationMillis =
        System.currentTimeMillis() - entryTime.getTime();

    long hours = (durationMillis + 3599999) / 3600000;

    if (hours < 1) {
        hours = 1;
    }

 double rate;

if (vehicleType == null || 
    (!"1".equals(vehicleType) && !"2".equals(vehicleType))) {

    vehicleType = JOptionPane.showInputDialog(
        frame,
        "Enter Vehicle Type:\n1. 2-Wheeler\n2. 4-Wheeler"
    );

    if (vehicleType == null || 
        (!"1".equals(vehicleType) && !"2".equals(vehicleType))) {
        return;
    }
}

if ("1".equals(vehicleType)) {
    rate = 20.0;   // 2-wheeler
} else {
    rate = 40.0;   // 4-wheeler
}

fee = hours * rate;
}

feeRs.close();
feePs.close();

String feeUpdateSql =
    "UPDATE parking_records SET fee=? WHERE id=?";

PreparedStatement feeUpdatePs =
    con.prepareStatement(feeUpdateSql);

feeUpdatePs.setDouble(1, fee);
feeUpdatePs.setInt(2, recordId);

feeUpdatePs.executeUpdate();
feeUpdatePs.close();

JOptionPane.showMessageDialog(
    frame,
    "Parking Fee: ₹" + String.format("%.2f", fee),
    "Parking Fee",
    JOptionPane.INFORMATION_MESSAGE
);



            String sql =
                    "UPDATE parking_slots " +
                    "SET status='FREE', vehicle_number=NULL " +
                    "WHERE slot_number=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, slot);

            ps.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(
                    frame,
                    "Vehicle removed successfully!"
            );

            loadSlots();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    frame,
                    "Database Error!\n" + e.getMessage()
            );
        }
    }
    static void showParkingHistory() {
    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT id, vehicle_number, vehicle_type, slot_number, entry_time, fee " +
             "FROM parking_records ORDER BY entry_time DESC";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        StringBuilder history = new StringBuilder();

        history.append("PARKING HISTORY\n\n");

        while (rs.next()) {
    history.append("ID: ")
        .append(rs.getInt("id"))
        .append(" | Vehicle: ")
        .append(rs.getString("vehicle_number"))
        .append(" | Type: ")
        .append(rs.getString("vehicle_type"))
        .append(" | Slot: ")
        .append(rs.getString("slot_number"))
        .append(" | Entry: ")
        .append(rs.getString("entry_time"))
        .append(" | Fee: ₹")
        .append(rs.getDouble("fee"))
        .append("\n");

        }

        rs.close();
        ps.close();
        con.close();

        JOptionPane.showMessageDialog(
                frame,
                history.toString(),
                "Parking History",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                frame,
                "Database Error!\n" + e.getMessage()
        );
    }
}
static void showStatistics() {
    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT "
                + "COUNT(*) AS total, "
                + "SUM(CASE WHEN status='OCCUPIED' THEN 1 ELSE 0 END) AS occupied "
                + "FROM parking_slots";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int total = rs.getInt("total");
            int occupied = rs.getInt("occupied");
            int free = total - occupied;

            String message =
                    "PARKING STATISTICS\n\n"
                    + "Total Slots     : " + total + "\n"
                    + "Occupied Slots  : " + occupied + "\n"
                    + "Free Slots      : " + free;

            JOptionPane.showMessageDialog(
                    frame,
                    message,
                    "Parking Statistics",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
                frame,
                "Database Error!\n" + e.getMessage()
        );
    }
}
    // ================= VEHICLE SEARCH =================
static void searchVehicle() {

    String vehicle = JOptionPane.showInputDialog(
            frame,
            "Enter Vehicle Number:"
    );

    if (vehicle == null || vehicle.trim().isEmpty()) {
        return;
    }

    String sql =
            "SELECT slot_number, status, vehicle_number " +
            "FROM parking_slots " +
            "WHERE vehicle_number=?";

    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vehicle.trim());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String slot = rs.getString("slot_number");
            String status = rs.getString("status");
            String vehicleNumber = rs.getString("vehicle_number");

            String message =
                    "VEHICLE FOUND\n\n" +
                    "Vehicle Number : " + vehicleNumber + "\n" +
                    "Slot Number    : " + slot + "\n" +
                    "Status         : " + status;

            JOptionPane.showMessageDialog(
                    frame,
                    message,
                    "Vehicle Search",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    frame,
                    "Vehicle not found!",
                    "Vehicle Search",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                frame,
                "Database Error!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }
    static void reserveSlot() {

    String vehicle = JOptionPane.showInputDialog(
        frame,
        "Enter Vehicle Number:"
    );

    if (vehicle == null || vehicle.trim().isEmpty()) {
        return;
    }

    String slot = JOptionPane.showInputDialog(
        frame,
        "Enter Slot Number (A1-A5):"
    );

    if (slot == null || slot.trim().isEmpty()) {
        return;
    }

    vehicle = vehicle.trim();
    slot = slot.trim().toUpperCase();

    try {

        Connection con = DriverManager.getConnection(
            URL, USER, PASSWORD
        );

        // CHECK 1: Is this slot already reserved?
        String checkReservation =
            "SELECT vehicle_number FROM reservations WHERE slot_number=?";

        PreparedStatement checkPs =
            con.prepareStatement(checkReservation);

        checkPs.setString(1, slot);

        ResultSet checkRs = checkPs.executeQuery();

        if (checkRs.next()) {

            JOptionPane.showMessageDialog(
                frame,
                "Slot " + slot +
                " is already reserved by Vehicle " +
                checkRs.getString("vehicle_number"),
                "Reservation Failed",
                JOptionPane.ERROR_MESSAGE
            );

            checkRs.close();
            checkPs.close();
            con.close();
            return;
        }

        checkRs.close();
        checkPs.close();


        // CHECK 2: Is this slot occupied?
        String checkOccupied =
            "SELECT status FROM parking_slots WHERE slot_number=?";

        PreparedStatement occupiedPs =
            con.prepareStatement(checkOccupied);

        occupiedPs.setString(1, slot);

        ResultSet occupiedRs =
            occupiedPs.executeQuery();

        if (!occupiedRs.next()) {

            JOptionPane.showMessageDialog(
                frame,
                "Slot " + slot + " does not exist!",
                "Reservation Failed",
                JOptionPane.ERROR_MESSAGE
            );

            occupiedRs.close();
            occupiedPs.close();
            con.close();
            return;
        }

        String status = occupiedRs.getString("status");

        occupiedRs.close();
        occupiedPs.close();

        if ("OCCUPIED".equalsIgnoreCase(status)) {

            JOptionPane.showMessageDialog(
                frame,
                "Slot " + slot +
                " is already occupied!",
                "Reservation Failed",
                JOptionPane.ERROR_MESSAGE
            );

            con.close();
            return;
        }


        // CREATE RESERVATION
        String sql =
            "INSERT INTO reservations " +
            "(vehicle_number, slot_number) VALUES (?, ?)";

        PreparedStatement ps =
            con.prepareStatement(sql);

        ps.setString(1, vehicle);
        ps.setString(2, slot);

        ps.executeUpdate();

        ps.close();
        con.close();
updateDashboardCounts();
        JOptionPane.showMessageDialog(
            frame,
            "Slot Reserved Successfully!\\n\\n" +
            "Vehicle: " + vehicle +
            "\\nSlot: " + slot,
            "Reservation",
            JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
            frame,
            "Database Error!\\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
static void showReservations() {
    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT id, vehicle_number, slot_number, reservation_time, status " +
                     "FROM reservations ORDER BY reservation_time DESC";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        StringBuilder reservations = new StringBuilder();
        reservations.append("RESERVATIONS\n\n");

        boolean found = false;

        while (rs.next()) {
            found = true;

            reservations.append("ID: ")
                .append(rs.getInt("id"))
                .append(" | Vehicle: ")
                .append(rs.getString("vehicle_number"))
                .append(" | Slot: ")
                .append(rs.getString("slot_number"))
                .append(" | Status: ")
                .append(rs.getString("status"))
                .append("\n");
        }

        if (!found) {
            reservations.append("No reservations found.");
        }

        rs.close();
        ps.close();
        con.close();

        String[] options = {"Cancel Reservation", "Close"};

int choice = JOptionPane.showOptionDialog(
        frame,
        reservations.toString(),
        "Reservations",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[1]
);

if (choice == 0) {
    cancelReservation();
}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            frame,
            "Database Error!\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
static void cancelReservation() {

    String vehicle = JOptionPane.showInputDialog(
        frame,
        "Enter Vehicle Number to Cancel Reservation:"
    );

    if (vehicle == null || vehicle.trim().isEmpty()) {
        return;
    }

    try {
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "DELETE FROM reservations WHERE vehicle_number = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vehicle.trim());

        int result = ps.executeUpdate();

        if (result > 0) {
            JOptionPane.showMessageDialog(
                frame,
                "Reservation cancelled successfully!",
                "Cancel Reservation",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                frame,
                "No reservation found for this vehicle.",
                "Cancel Reservation",
                JOptionPane.WARNING_MESSAGE
            );
        }

        ps.close();
        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            frame,
            "Database Error!\n" + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
}

