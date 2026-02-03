import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageAdmin  implements ActionListener {
    JFrame frame;
    JButton AddDetail,UpdateDetail, RemoveDetail,ViewDetail,ScanQr,logOut,Attendance;
    JPanel panel1,panel;
    static String comName = "TechLynx";

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

    public HomePageAdmin() {

        frame = new JFrame();
        frame.setTitle("Login Page");
        frame.setBounds(30,30,1200,600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(true); 
        frame.setOpacity(0f);
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

        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1400, 800);
        panel1.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("C:\\\\Users\\\\vedpa\\\\OneDrive\\\\Desktop\\\\java_Group_project\\\\changeImage.png"); // put your image path here
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0,0,1400,800);

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Clip panel into rounded rectangle
                Shape clip = new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setClip(clip);

                // Fill background (inside rounded shape)
                g2.setColor(Color.WHITE);
                g2.fill(clip);

                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.PINK);  // Border color
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };
        panel.setBounds(300, 150, 700, 400);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setOpaque(false);
        
        ImageIcon rightPanelImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 07_22_20 PM.png"); // put your image path here
        JLabel rightPanelLabel = new JLabel(rightPanelImage);
        rightPanelLabel.setBounds(10,10,200,380);
        

        ImageIcon LeftPanelImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 07_22_20 PM.png"); // put your image path here
        JLabel LeftPanelLabel = new JLabel(LeftPanelImage);
        LeftPanelLabel.setBounds(500,10,190,380);

        JLabel line1 = new JLabel("Welcome To");
        line1.setFont(new Font("Arial", Font.BOLD, 30));
        line1.setBounds(265, 100, 200, 30);

        JLabel line2 = new JLabel("      "+comName);
        line2.setFont(new Font("Arial", Font.BOLD, 27));
        line2.setBounds(250, 180, 220, 50);
        line2.setBorder(new RoundedBorder(Color.BLACK, 30));
                
        AddDetail = new JButton("Add Details");
        AddDetail.setBounds(10, 50, 200, 50);
        AddDetail.addActionListener(this);
        AddDetail.setFocusable(false);
        AddDetail.setFont(new Font("Fira code",Font.BOLD,17));
        AddDetail.setForeground(Color.WHITE);
        AddDetail.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        AddDetail.setContentAreaFilled(false);
        AddDetail.setFocusPainted(false);
        AddDetail.setOpaque(false);

        UpdateDetail = new JButton("Update Details");
        UpdateDetail.setBounds(10, 130, 200, 50);
        UpdateDetail.addActionListener(this);
        UpdateDetail.setFocusable(false);
        UpdateDetail.setFont(new Font("Fira code",Font.BOLD,17));
        UpdateDetail.setForeground(Color.WHITE);
        UpdateDetail.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        UpdateDetail.setContentAreaFilled(false);
        UpdateDetail.setFocusPainted(false);
        UpdateDetail.setOpaque(false);

        RemoveDetail = new JButton("Remove Details");
        RemoveDetail.setBounds(10, 210, 200, 50);
        RemoveDetail.addActionListener(this);
        RemoveDetail.setFocusable(false);
        RemoveDetail.setFont(new Font("Fira code",Font.BOLD,17));
        RemoveDetail.setForeground(Color.WHITE);
        RemoveDetail.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        RemoveDetail.setContentAreaFilled(false);
        RemoveDetail.setFocusPainted(false);
        RemoveDetail.setOpaque(false);

        ViewDetail = new JButton("View Details");
        ViewDetail.setBounds(10, 290, 200, 50);
        ViewDetail.addActionListener(this);
        ViewDetail.setFocusable(false);
        ViewDetail.setFont(new Font("Fira code",Font.BOLD,17));
        ViewDetail.setForeground(Color.WHITE);
        ViewDetail.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        ViewDetail.setContentAreaFilled(false);
        ViewDetail.setFocusPainted(false);
        ViewDetail.setOpaque(false);

        ScanQr = new JButton("Take Attendance");
        ScanQr.setBounds(500, 70, 190, 50);
        ScanQr.addActionListener(this);
        ScanQr.setFocusable(false);
        ScanQr.setFont(new Font("Fira code",Font.BOLD,17));
        ScanQr.setForeground(Color.WHITE);
        ScanQr.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        ScanQr.setContentAreaFilled(false);
        ScanQr.setFocusPainted(false);
        ScanQr.setOpaque(false);

        logOut = new JButton("log out");
        logOut.setBounds(980, 30, 150, 40);
        logOut.addActionListener(this);
        logOut.setFocusable(false);
        logOut.setFont(new Font("Fira code",Font.BOLD,17));
        logOut.setForeground(Color.WHITE);
        logOut.setBorder(new RoundedBorder(Color.WHITE, 17));
        logOut.setContentAreaFilled(false);
        logOut.setFocusPainted(false);
        logOut.setOpaque(false);

        Attendance = new JButton("Check Attendance");
        Attendance.setBounds(500, 175, 190, 50);
        Attendance.addActionListener(this);
        Attendance.setFocusable(false);
        Attendance.setFont(new Font("Fira code",Font.BOLD,15));
        Attendance.setForeground(Color.WHITE);
        Attendance.setBorder(new RoundedBorder(Color.GREEN.darker(), 17));
        Attendance.setContentAreaFilled(false);
        Attendance.setFocusPainted(false);
        Attendance.setOpaque(false);


        panel.add(AddDetail);
        panel.add(UpdateDetail);
        panel.add(RemoveDetail);
        panel.add(ViewDetail);
        panel.add(ScanQr);
        panel.add(Attendance);
        panel.add(line1);
        panel.add(line2);

        panel.add(rightPanelLabel);
        panel.add(LeftPanelLabel);

        panel1.add(backgroundLabel);

        frame.add(logOut);

        

        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == AddDetail){
            frame.dispose();
            new AddEmp();
        }
        if(e.getSource() == UpdateDetail){
            frame.dispose();
            new UpdateEmp();
        }
        if(e.getSource() == RemoveDetail){
            frame.dispose();
            new DeleteEmp();
        }
        if(e.getSource() == ViewDetail){
            frame.dispose();
            new ViewEmp();
        }
        if (e.getSource() == logOut) {
            frame.dispose();
            new LoginPage();
        }
        if (e.getSource() == Attendance) {
            frame.dispose();
            new AttendanceViewer();
        }
        if (e.getSource() == ScanQr) {
            frame.dispose();
           SwingUtilities.invokeLater(() -> new scanQR().startQRScanner());
        }
        
    }
}
