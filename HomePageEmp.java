import javax.swing.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.*;

public class HomePageEmp implements ActionListener {
    JFrame frame;
    Connection connection;
    JPanel panel,panel1;
    JLabel  nameLabel, ageLabel, genderLabel, contactLabel, deptLabel, desgLabel, salaryLabel, cityLabel, emailLabel, leaveLeft, leaveRequired,qrLabel;
    JTextField nameField, contactField, salaryField, ageField, genderField, cityField, deptField, desgField, emailField;
    JCheckBox casualLeave, sickLeave, bereavementLeave;
    JButton backButton;
    BufferedImage qrImage;
    static int Emp_id;
    String name = "";
    int age = 0;
    String number = "";
    String gender = "";
    String city = "";
    String dept = "";
    String desg = "";
    double salary = 0.0;
    String email = "";
    String leaveType = "";
    static int left = 0;

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

    HomePageEmp(int id) throws Exception {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Emp_id = id;

        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1400, 800);
        panel1.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 09_45_07 AM.png"); // put your image path here
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0,0,1400,800);

        ImageIcon SideImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 07_22_20 PM.png"); // put your image path here
        JLabel SideImageLabel = new JLabel(SideImage);
        SideImageLabel.setBounds(0,0,300,400);
        SideImageLabel.setBorder(new RoundedBorder(Color.GREEN, 30));

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
        panel.setBounds(80, 100, 750, 400); // Increased size
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel PanelTitle = new JLabel("<html>Employee<br>Details</html>");
        PanelTitle.setFont(new Font("Fira Code", Font.BOLD, 43));
        PanelTitle.setBounds(20,70,270,200);
        PanelTitle.setForeground(Color.WHITE);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        nameField.setFont(new Font("Fira Code", Font.BOLD, 15));

        ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        ageField = new JTextField();
        ageField.setEditable(false);
        ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        ageField.setFont(new Font("Fira Code", Font.BOLD, 15));

        genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderField = new JTextField();
        genderField.setEditable(false);
        genderField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        genderField.setFont(new Font("Fira Code", Font.BOLD, 15));

        contactLabel = new JLabel("Contact No:");
        contactLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactField = new JTextField();
        contactField.setEditable(false);
        contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        contactField.setFont(new Font("Fira Code", Font.BOLD, 15));

        emailLabel = new JLabel("Email : ");
        emailLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        emailField = new JTextField();
        emailField.setEditable(false);
        emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        emailField.setFont(new Font("Fira Code", Font.BOLD, 15));

        deptLabel = new JLabel("Department:");
        deptLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptField = new JTextField();
        deptField.setEditable(false);
        deptField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        deptField.setFont(new Font("Fira Code", Font.BOLD, 15));

        desgLabel = new JLabel("Designation:");
        desgLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        desgField = new JTextField();
        desgField.setEditable(false);
        desgField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        desgField.setFont(new Font("Fira Code", Font.BOLD, 15));

        salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField = new JTextField();
        salaryField.setEditable(false);
        salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        salaryField.setFont(new Font("Fira Code", Font.BOLD, 15));

        cityLabel = new JLabel("City : ");
        cityLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityField = new JTextField();
        cityField.setEditable(false);
        cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        cityField.setFont(new Font("Fira Code", Font.BOLD, 15));

        backButton = new JButton("Log Out");
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        backButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        

        // New dimensions
        int y = 25, h = 30;
        int labelW = 140, fieldW = 250;

        nameLabel.setBounds(320, y, labelW, h);
        nameField.setBounds(450, y, fieldW, h);
        y += 40;

        ageLabel.setBounds(320, y, labelW, h);
        ageField.setBounds(450, y, fieldW, h);
        y += 40;

        genderLabel.setBounds(320, y, labelW, h);
        genderField.setBounds(450, y, fieldW, h);
        y += 40;

        contactLabel.setBounds(320, y, labelW, h);
        contactField.setBounds(450, y, fieldW, h);
        y += 40;

        emailLabel.setBounds(320, y, labelW, h);
        emailField.setBounds(450, y, fieldW, h);
        y += 40;

        deptLabel.setBounds(320, y, labelW, h);
        deptField.setBounds(450, y, fieldW, h);
        y += 40;

        desgLabel.setBounds(320, y, labelW, h);
        desgField.setBounds(450, y, fieldW, h);
        y += 40;

        salaryLabel.setBounds(320, y, labelW, h);
        salaryField.setBounds(450, y, fieldW, h);
        y += 40;

        cityLabel.setBounds(320, y, labelW, h);
        cityField.setBounds(450, y, fieldW, h);

        backButton.setBounds(100, y, 120, 35);

        String query_emp = "SELECT * from emp_details where E_id = ?";
        String query_work = "SELECT * from works where E_id = ?";

        PreparedStatement p_emp = connection.prepareStatement(query_emp);
        PreparedStatement p_work = connection.prepareStatement(query_work);

        p_emp.setInt(1, id);
        p_work.setInt(1, id);

        ResultSet r_emp = p_emp.executeQuery();
        ResultSet r_work = p_work.executeQuery();


        while (r_emp.next()) {
            name = r_emp.getString(2);
            age = r_emp.getInt(3);
            number = r_emp.getString(4);
            gender = r_emp.getString(5);
            city = r_emp.getString(7);
        }
        while (r_work.next()) {
            dept = r_work.getString(2);
            desg = r_work.getString(3);
            salary = r_work.getDouble(4);
            email = r_work.getString(5);
        }

        nameField.setText(name);
        ageField.setText(age + "");
        contactField.setText(number);
        deptField.setText(dept);
        desgField.setText(desg);
        salaryField.setText(salary + "");
        cityField.setText(city);
        emailField.setText(email);
        genderField.setText(gender);

        String getId = String.valueOf(Emp_id);

        qrLabel = new JLabel(){
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
        qrLabel.setBounds(900,150,300,300);
        qrLabel.setBorder(new RoundedBorder(Color.BLACK, 20));

        int size = 300;
        BitMatrix bitMatrix = new MultiFormatWriter().encode(getId, BarcodeFormat.QR_CODE, size, size);
        qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        qrLabel.setIcon(new ImageIcon(qrImage));

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(genderLabel);
        panel.add(genderField);
        panel.add(contactLabel);
        panel.add(contactField);
        panel.add(deptLabel);
        panel.add(deptField);
        panel.add(desgLabel);
        panel.add(desgField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(backButton);

        panel1.add(qrLabel);
        panel1.add(backgroundLabel);
        panel.add(PanelTitle);
        panel.add(SideImageLabel);
        
        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            new LoginPage();
        }
    }

   
    
}
