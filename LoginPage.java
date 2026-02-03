import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class LoginPage implements ActionListener, FocusListener {
    JFrame frame;
    JTextField UserField;
    JPasswordField PassWordField;
    JLabel UserName, PassWord, title, logo, background;
    JButton button,closeButton;
    JPanel panel,panel1;
    String AdminPassWord = "Admin@123";
    static String comName = "TechLynx";

    JCheckBox adminCheck, employeeCheck;
    Connection connection;

    //  give rounded Border to --> textField,label,button
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

    LoginPage() {

        // for JDBC connection
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

        // GUI -->  frame set up
        frame = new JFrame();
        frame.setTitle("Login Page");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setUndecorated(true); 
        frame.setOpacity(0f); 
        frame.setLocationRelativeTo(null); 

        // Set the frame to be maximized both horizontally and vertically
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // open frame with animatation
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

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // White rounded area (opaque inside)
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.PINK);  // Border color
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);

                g2.dispose();
            }
        };

        panel.setBackground(Color.WHITE);
        panel.setBounds(200, 140, 900, 400);
        panel.setLayout(null);
        panel.setBorder(new RoundedBorder(Color.BLACK, 30));

        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1400, 800);
        panel1.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\changeImage.png"); // put your image path here
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0,0,1400,800);

        ImageIcon SideImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 07_22_20 PM.png"); // put your image path here
        JLabel SideImageLabel = new JLabel(SideImage);
        SideImageLabel.setBounds(0,0,200,400);
        SideImageLabel.setBorder(new RoundedBorder(Color.GREEN, 30));

        JLabel PanelTitle = new JLabel("<html>Welcome To<br><br> <br>TechLynx</html>");
        PanelTitle.setFont(new Font("Fira Code", Font.BOLD, 33));
        PanelTitle.setBounds(20,0,170,400);
        PanelTitle.setForeground(Color.WHITE);

        UserName = new JLabel("UserId    : ");
        UserName.setBounds(230, 60, 400, 20);
        UserName.setForeground(Color.BLACK);
        UserName.setFont(new Font("Fira Code", Font.BOLD, 20));

        PassWord = new JLabel("Password : ");
        PassWord.setBounds(230, 155, 400, 20);
        PassWord.setForeground(Color.BLACK);
        PassWord.setFont(new Font("Fira Code", Font.BOLD, 20));

        UserField = new JTextField();
        UserField.setBounds(350, 53, 200, 30);
        UserField.setFont(new Font("Fira Code", Font.BOLD, 20));
        UserField.addFocusListener(this);
        UserField.setHorizontalAlignment(JTextField.CENTER);
        UserField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        PassWordField = new JPasswordField();
        PassWordField.setBounds(350, 150, 200, 30);
        PassWordField.setFont(new Font("Fira Code", Font.PLAIN, 20));
        PassWordField.addFocusListener(this);
        PassWordField.setHorizontalAlignment(JTextField.CENTER);
        PassWordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

        // Checkboxes for Admin and Employee
        adminCheck = new JCheckBox("Admin");
        adminCheck.setBounds(250, 220, 100, 25);
        adminCheck.setBackground(Color.WHITE);
        adminCheck.setForeground(Color.BLACK);
        adminCheck.setFocusable(false);

        employeeCheck = new JCheckBox("Employee");
        employeeCheck.setBounds(460, 220, 120, 25);
        employeeCheck.setBackground(Color.WHITE);
        employeeCheck.setForeground(Color.BLACK);
        employeeCheck.setFocusable(false);

        // Ensure only one can be selected
        adminCheck.addActionListener(e -> {
            if (adminCheck.isSelected()) {
                employeeCheck.setSelected(false);
            }
        });

        employeeCheck.addActionListener(e -> {
            if (employeeCheck.isSelected()) {
                adminCheck.setSelected(false);
            }
        });

        ImageIcon image = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\logo.jpg");

        button = new JButton("Login");
        button.setBounds(300, 300, 200, 35);
        button.setFont(new Font("Fira Code", Font.BOLD, 20));
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBorder(new RoundedBorder(Color.orange, 15));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton = new JButton("Exit");
        closeButton.setFont(new Font("Fira code",Font.BOLD,17));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        closeButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(false);
        closeButton.setContentAreaFilled(false);

        logo = new JLabel(image);
        logo.setBounds(570, 60, 300, 200);

        closeButton.setBounds(980,30,150,40);

        // add element in panel
        panel1.add(closeButton);
        panel1.add(backgroundLabel);
      
        panel.add(UserName);
        panel.add(PassWord);
        panel.add(UserField);
        panel.add(PassWordField);
        panel.add(adminCheck);
        panel.add(employeeCheck);
        panel.add(button);
        panel.add(logo);

        panel.add(PanelTitle);
        panel.add(SideImageLabel);

        // add panel in frame
        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);
        
    }

    public static void main(String[] args) {
        new LoginPage();
    }

    public void actionPerformed(ActionEvent e) {

        // check if close button is clicked
        if (e.getSource() == closeButton) {
            frame.dispose();
        }
        
        if (e.getSource() == button) {
            String role = "";
        
            if (adminCheck.isSelected()) {
                role = "Admin";
            } else if (employeeCheck.isSelected()) {
                role = "Employee";
            }

            if (role.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select either Admin or Employee");
                return;
            }

            if (role.equals("Employee")) {

                String userName = UserField.getText();
                String userPassWord = PassWordField.getText();

                if (userName.isEmpty() || userPassWord.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter all details",null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(int i = 0; i < userName.length();i++){
                    if(userName.charAt(i) < '0' || userName.charAt(i) > '9'){
                        JOptionPane.showMessageDialog(null, "Username Must contain Numbers",null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                int userID = Integer.parseInt(userName);
                boolean b = false;
                boolean c = false;
                try {

                    String queryId = "SELECT E_id FROM emp_details";
                    int id = 0;
                    Statement statement = connection.createStatement();
                    ResultSet r = statement.executeQuery(queryId);

                    while (r.next()) {
                        id = r.getInt(1);

                        if (id == userID) {
                            b = true;
                        }
                    }

                    if (b) {
                        
                        String queryDept = "SELECT E_dept FROM works _at WHERE E_id = " + userID;
                        Statement statement1 = connection.createStatement();
                        ResultSet r1 = statement1.executeQuery(queryDept);

                        String dept = "";
                        while (r1.next()) {
                            dept = r1.getString(1);
                        }
                        String passWord = dept + userID;

                        if (passWord.equals(userPassWord)) {
                            c = true;
                        }
                    }

                
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (b && c) {
                    frame.dispose();
                    try {
                        new HomePageEmp(userID);
                    } catch (Exception ex) {
                        // it throw exception when it is called from another class
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Employee not found", null, JOptionPane.ERROR_MESSAGE);
                    frame.dispose();
                    new LoginPage();
                    return;
                }
            }

            
            if (role.equals("Admin")) {

                String adminPassWord = PassWordField.getText();
                String userName = UserField.getText();

                if (!userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "If you are admin, please enter only PassWord", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (adminPassWord.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter PassWord",null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (adminPassWord.equals(AdminPassWord)) {
                    frame.dispose();
                    new HomePageAdmin();
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid PassWord",null, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
    }

    public void focusGained(FocusEvent e) {
        if (e.getSource() == UserField) {
           UserField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
        } else if (e.getSource() == PassWordField) {
            PassWordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == UserField) {
            UserField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        } else if (e.getSource() == PassWordField) {
            PassWordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        }
    }
}
