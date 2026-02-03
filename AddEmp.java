import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AddEmp  implements ActionListener,FocusListener {
    JFrame frame;
    JPanel panel,panel1,panel2;
    JLabel idLabel,nameLabel,dobLabel,genderLabel,contactLabel,deptLabel,desgLabel,salaryLabel,joinDateLabel,EmpCity;
    JButton submitButton,backButton;
    JTextField idField,nameField,contactField,salaryField,joinDateField,cityField;
    JDateChooser dobChooser;
    JComboBox<String> deptBox,desgBox,genderBox;
    Connection connection;
    CallableStatement callableStatement;
    PreparedStatement ForGetId;
    int id = 0;
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

    public AddEmp() {

        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);

            String getId = "select MAX(id) from getId";
            ResultSet r;

            ForGetId = connection.prepareStatement(getId);
            r = ForGetId.executeQuery();

            while (r.next()){
                id = r.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        frame = new JFrame();
        frame.setTitle("Add emp detail");
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
        SideImageLabel.setBounds(0,0,300,500);
        SideImageLabel.setBorder(new RoundedBorder(Color.GREEN, 30));

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
        panel.setLayout(null);
        panel.setBounds(250, 90, 800, 500);
        panel.setBackground(Color.WHITE);

        JLabel PanelTitle = new JLabel("<html>Add<br>Employee<br>Details</html>");
        PanelTitle.setFont(new Font("Fira Code", Font.BOLD, 43));
        PanelTitle.setBounds(20,70,270,200);
        PanelTitle.setForeground(Color.WHITE);

        int y = 30, h = 30;
        int labelW = 140, fieldW = 250;

        idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        idField = new JTextField();
        idField.setText(String.valueOf(id));
        idField.setFont(new Font("Fira Code", Font.BOLD, 15));
        idField.setFocusable(false);
        idField.setEditable(false);
        idField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        idLabel.setBounds(350, y, labelW, h);
        idField.setBounds(500, y, fieldW, h);
        y += 40;

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        nameField.addFocusListener(this);
        nameField.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameLabel.setBounds(350, y, labelW, h);
        nameField.setBounds(500, y, fieldW, h);
        y += 40;


        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        dobChooser = new JDateChooser();
        dobChooser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        dobChooser.setDateFormatString("yyyy-MM-dd");
        dobChooser.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        dobChooser.setFont(new Font("Fira Code", Font.BOLD, 15));
        dobChooser.addFocusListener(this);
        dobLabel.setBounds(350, y, labelW, h);
        dobChooser.setBounds(500, y, fieldW, h);
        y += 40;

        genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        genderBox.addFocusListener(this);
        genderBox.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderLabel.setBounds(350, y, labelW, h);
        genderBox.setBounds(500, y, fieldW, h);
        y += 40;


        contactLabel = new JLabel("Contact No:");
        contactLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactField = new JTextField();
        contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        contactField.addFocusListener(this);
        contactField.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactLabel.setBounds(350, y, labelW, h);
        contactField.setBounds(500, y, fieldW, h);
        y += 40;

        deptLabel = new JLabel("Department:");
        deptLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        String[] departments = {"HR", "IT", "FINANCE", "CIVIL", "CSE", "EC"};
        deptBox = new JComboBox<>(departments);
        deptBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        deptBox.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        deptBox.addFocusListener(this);
        deptBox.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptLabel.setBounds(350, y, labelW, h);
        deptBox.setBounds(500, y, fieldW, h);
        y += 40;

        desgLabel = new JLabel("Designation:");
        desgLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        String[] designations = {"Manager", "Developer", "Accountant", "Intern", "Team Lead"};
        desgBox = new JComboBox<>(designations);
        desgBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        desgBox.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        desgBox.addFocusListener(this);
        desgBox.setFont(new Font("Fira Code", Font.BOLD, 15));
        desgLabel.setBounds(350, y, labelW, h);
        desgBox.setBounds(500, y, fieldW, h);
        y += 40;

        salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField = new JTextField();
        salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        salaryField.addFocusListener(this);
        salaryField.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryLabel.setBounds(350, y, labelW, h);
        salaryField.setBounds(500, y, fieldW, h);
        y += 40;

        EmpCity = new JLabel("City : ");
        EmpCity.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityField = new JTextField();
        cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        cityField.addFocusListener(this);
        cityField.setFont(new Font("Fira Code", Font.BOLD, 15));
        EmpCity.setBounds(350,y,labelW,h);
        cityField.setBounds(500,y,fieldW,h);
        y += 40;

        joinDateLabel = new JLabel("Date of Joining:");
        joinDateLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        joinDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        joinDateField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        joinDateField.setEditable(false);
        joinDateField.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        joinDateField.addFocusListener(this);
        joinDateField.setFont(new Font("Fira Code", Font.BOLD, 15));
        joinDateLabel.setBounds(350, y, labelW, h);
        joinDateField.setBounds(500, y, fieldW, h);
        y += 50;


        submitButton = new JButton("Submit");
        submitButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        submitButton.setFont(new Font("Fira code",Font.BOLD,17));
        submitButton.setForeground(Color.BLACK);
        submitButton.addActionListener(this);
        submitButton.setBounds(460, y+10, 120, 35);
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setOpaque(false);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(100, y+10, 120, 35);
        backButton.addActionListener(this);
        backButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);

        // Add components
        panel.add(idLabel); panel.add(idField);
        panel.add(nameLabel); panel.add(nameField);
        panel.add(dobLabel); panel.add(dobChooser);
        panel.add(genderLabel); panel.add(genderBox);
        panel.add(contactLabel); panel.add(contactField);
        panel.add(deptLabel); panel.add(deptBox);
        panel.add(desgLabel); panel.add(desgBox);
        panel.add(salaryLabel); panel.add(salaryField);
        panel.add(EmpCity);  panel.add(cityField);
        panel.add(joinDateLabel); panel.add(joinDateField);
        panel.add(submitButton);
        panel.add(backButton);

        panel1.add(backgroundLabel);
        panel.add(PanelTitle);
        panel.add(SideImageLabel);

        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == submitButton) {
            try {
                String name = nameField.getText();

                Date dob = dobChooser.getDate();

                if (dob == null) {
                    JOptionPane.showMessageDialog(null, "Enter Birth Date", null,JOptionPane.ERROR_MESSAGE);
                }
                String dobStr = new SimpleDateFormat("yyyy-MM-dd").format(dob);
                LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                if(age <18)
                {
                    JOptionPane.showMessageDialog(null, "employee age must be greater than 18", null,JOptionPane.WARNING_MESSAGE);
                }

                String gender = genderBox.getSelectedItem().toString();

                String contact = contactField.getText();

                String dept = deptBox.getSelectedItem().toString();

                String desg = desgBox.getSelectedItem().toString();

                String city = cityField.getText();

                if (name.isEmpty()) {
                    nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter name",null,JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (city.isEmpty()) {
                    cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter city",null,JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (contact.isEmpty()) {
                    contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter contact number",null,JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!name.matches("[a-zA-Z ]+")) {
                    nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter valid name", null, JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!city.matches("[a-zA-Z ]+")) {
                    cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter valid city name", null, JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double salary = 0;
                if (!salaryField.getText().isEmpty()) {
                    try {
                        salary = Double.parseDouble(salaryField.getText());
                    } catch (NumberFormatException ex) {
                        salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                        JOptionPane.showMessageDialog(null,"Enter valid Salary");
                        return;
                    }
                } else {
                    salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null, "Enter Salary");
                    return;
                }

                String joinDate = joinDateField.getText();

                if(contact.length() != 10){
                    contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null,"Enter valid number");
                    return;
                }

                for(int i = 0; i < contact.length();i++){
                    if(contact.charAt(i) < '0' || contact.charAt(i) > '9'){
                        contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                        JOptionPane.showMessageDialog(null, "Contact number Must contain Numbers",null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String[] Firstname = name.split(" ");

                String email = Firstname[0] + dept + id + "@" + comName+ ".com";

                CallableStatement c1 = connection.prepareCall("{call addEmpDetails(?,?,?,?,?,?,?)}");
                c1.setInt(1,id);
                c1.setString(2,name);
                c1.setInt(3,age);
                c1.setString(4,contact);
                c1.setString(5,gender);
                c1.setString(6,joinDate);
                c1.setString(7,city);

                c1.executeUpdate();

                CallableStatement c2 = connection.prepareCall("{call addComDetails(?,?,?,?,?)}");
                c2.setInt(1,id);
                c2.setString(2,dept);
                c2.setString(3,desg);
                c2.setDouble(4,salary);
                c2.setString(5,email);

                c2.executeUpdate();

                PreparedStatement p1 = connection.prepareStatement("INSERT INTO getid values(?)");

                p1.setInt(1,id+1);

                p1.executeUpdate();

                frame.dispose();
                new AddEmp();

            } 
            catch (Exception ex) {
            }

        }
        if(e.getSource() == backButton){
            frame.dispose();
            new HomePageAdmin();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == nameField) {
            nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
        }
        if (e.getSource() == dobChooser) {
            dobChooser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
        }
        if (e.getSource() == genderBox) {
            genderBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == contactField) {
            contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == deptBox) {
            deptBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == desgBox) {
            desgBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == salaryField) {
            salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == cityField) {
            cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
        if (e.getSource() == joinDateField) {
            joinDateField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
       if (e.getSource() == nameField) {
            nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == dobChooser) {
            dobChooser.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == genderBox) {
            genderBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == contactField) {
            contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == deptBox) {
            deptBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == desgBox) {
            desgBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == salaryField) {
            salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == cityField) {
            cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == joinDateField) {
            joinDateField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
    }
}
