import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AttendanceViewer {
    JFrame frame;
    JTable table;
    DefaultTableModel model;
    JButton button;
    JPanel panel, panel1;

    class RoundedBorder extends javax.swing.border.AbstractBorder {
        private Color color;
        private int radius;

        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    public AttendanceViewer() {
        frame = new JFrame();
        frame.setTitle("Employee Attendance Details");
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(true); 
        frame.setOpacity(0f); // start fully transparent

        frame.setLocationRelativeTo(null); // Center the frame (before maximization, if desired)

        // Set the frame to be maximized both horizontally and vertically
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Timer timer = new Timer(40, new ActionListener() {
            float opacity = 0f;
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f; // increase opacity
                if (opacity >= 1f) {
                    opacity = 1f;
                    ((Timer)e.getSource()).stop(); // stop animation
                }
                frame.setOpacity(opacity);
            }
        });
        timer.start();

        // Background panel
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1400, 800);
        panel1.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 09_45_07 AM.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1400, 800);

        // White rounded panel for content
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.PINK);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBounds(150, 50, 900, 450);
        panel.setBackground(Color.WHITE);

        // Table setup
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        button = new JButton("    Back    ");
        button.setFont(new Font("Fira Code", Font.BOLD, 17));
        button.setFocusable(false);
        button.setBorder(new RoundedBorder(Color.orange, 15));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomePageAdmin();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(button);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Add everything
        panel1.add(backgroundLabel);
        
        frame.add(panel);
        frame.add(panel1);

        loadAttendance();
        frame.setVisible(true);
    }

    void loadAttendance() {
        model.setRowCount(0);
        model.setColumnCount(0);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM attendance");

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage());
        }
    }
}
