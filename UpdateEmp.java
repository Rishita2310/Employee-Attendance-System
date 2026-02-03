import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class UpdateEmp  implements ActionListener , FocusListener {
    JFrame frame;
    JPanel panel,panel1;
    JLabel idLabel,nameLabel, ageLabel,genderLabel,contactLabel,deptLabel,desgLabel,salaryLabel,cityLabel;
    JButton UpdateButton,searchButton,refresh,backButton;
    JTextField idField,nameField,contactField,salaryField,ageField,genderField,cityField;
    JComboBox<String> deptBox,desgBox;
    Connection connection;
    int id ;
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


    public UpdateEmp(){

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
        frame = new JFrame();
        frame.setTitle("Update emp detail");
        frame.setBounds(20, 20, 1200, 600); // Increased size
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
        panel.setBounds(250, 90, 800, 470);
        panel.setBackground(Color.WHITE);

        JLabel PanelTitle = new JLabel("<html>Update<br>Employee<br>Details</html>");
        PanelTitle.setFont(new Font("Fira Code", Font.BOLD, 43));
        PanelTitle.setBounds(20,70,270,200);
        PanelTitle.setForeground(Color.WHITE);

        // New dimensions
        int y = 70, h = 30;
        int labelW = 140, fieldW = 250;

        idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        idField = new JTextField();
        idField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        idField.setFont(new Font("Fira Code", Font.BOLD, 17));
        idLabel.setBounds(350, 30, 140, 30);
        idField.setBounds(500, 30, 150, 30);

        searchButton = new JButton("Search");
        searchButton.setBounds(680,30,100,30);
        searchButton.addActionListener(this);
        searchButton.setFont(new Font("Fira code",Font.BOLD,17));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusable(false);
        searchButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(false);
        searchButton.setContentAreaFilled(false);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        nameField.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameField.addFocusListener(this);
        nameLabel.setBounds(350, y, labelW, h);
        nameField.setBounds(500, y, fieldW, h);
        y += 40;

        ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        ageField = new JTextField();
        ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        ageField.setFont(new Font("Fira Code", Font.BOLD, 15));
        ageField.addFocusListener(this);
        ageLabel.setBounds(350, y, labelW, h);
        ageField.setBounds(500, y, fieldW, h);
        y += 40;

        genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderField = new JTextField();
        genderField.setEditable(false);
        genderField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        genderField.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderField.addFocusListener(this);
        genderLabel.setBounds(350, y, labelW, h);
        genderField.setBounds(500, y, fieldW, h);
        y += 40;

        contactLabel = new JLabel("Contact No:");
        contactLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactField = new JTextField();
        contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        contactField.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactField.addFocusListener(this);
        contactLabel.setBounds(350, y, labelW, h);
        contactField.setBounds(500, y, fieldW, h);
        y += 40;

        deptLabel = new JLabel("Department:");
        deptLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        String[] departments = {"HR", "IT", "Finance", "Civil", "CSE", "EC"};
        deptBox = new JComboBox<>(departments);
        deptBox.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        deptBox.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptBox.addFocusListener(this);
        deptLabel.setBounds(350, y, labelW, h);
        deptBox.setBounds(500, y, fieldW, h);
        y += 40;

        desgLabel = new JLabel("Designation:");
        desgLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        String[] designations = {"Manager", "Developer", "Accountant", "Intern", "Team Lead"};
        desgBox = new JComboBox<>(designations);
        desgBox.setBorder(new RoundedBorder(Color.DARK_GRAY,15));
        desgBox.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptBox.addFocusListener(this);
        desgLabel.setBounds(350, y, labelW, h);
        desgBox.setBounds(500, y, fieldW, h);
        y += 40;


        salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField = new JTextField();
        salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        salaryField.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField.addFocusListener(this);
        salaryLabel.setBounds(350, y, labelW, h);
        salaryField.setBounds(500, y, fieldW, h);
        y += 40;

        cityLabel = new JLabel("City : ");
        cityLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityField = new JTextField();
        cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        cityField.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityField.addFocusListener(this);
        cityLabel.setBounds(350,y,labelW,h);
        cityField.setBounds(500,y,fieldW,h);
        y += 40;

        UpdateButton = new JButton("Update");
        UpdateButton.addActionListener(this);
        UpdateButton.setBounds(380, y+15, 140, 35);
        UpdateButton.setFont(new Font("Fira code",Font.BOLD,20));
        UpdateButton.setForeground(Color.BLACK);
        UpdateButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        UpdateButton.setFocusPainted(false);
        UpdateButton.setOpaque(false);
        UpdateButton.setContentAreaFilled(false);

        backButton = new JButton("back");
        backButton.addActionListener(this);
        backButton.setBounds(100,y+15,120,35);
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);

        refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        refresh.setBounds(570, y+15, 140, 35);
        refresh.setFont(new Font("Fira code",Font.BOLD,20));
        refresh.setBorder(new RoundedBorder(Color.BLACK, 17));
        refresh.setFocusPainted(false);
        refresh.setOpaque(false);
        refresh.setContentAreaFilled(false);

        // Add components
        panel.add(nameLabel); panel.add(nameField);
        panel.add(ageLabel); panel.add(ageField);
        panel.add(genderLabel); panel.add(genderField);
        panel.add(contactLabel); panel.add(contactField);
        panel.add(deptLabel); panel.add(deptBox);
        panel.add(desgLabel); panel.add(desgBox);
        panel.add(salaryLabel); panel.add(salaryField);
        panel.add(cityLabel); panel.add(cityField);
        panel.add(UpdateButton);
        panel.add(refresh);
        panel.add(backButton);

        nameField.show(false);  nameLabel.show(false);
        ageLabel.show(false);  ageField.show(false);
        genderLabel.show(false);  genderField.show(false);
        contactLabel.show(false);  contactField.show(false);
        deptLabel.show(false);  deptBox.show(false);
        desgLabel.show(false);  desgBox.show(false);
        salaryLabel.show(false); salaryField.show(false);
        cityLabel.show(false); cityField.show(false);
        UpdateButton.show(false);
        refresh.show(false);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(searchButton);

        panel.add(PanelTitle);
        panel.add(SideImageLabel);

        panel1.add(backgroundLabel);

        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);

    }

    void show() throws Exception {
        nameField.show(true);  nameLabel.show(true);
        ageLabel.show(true);  ageField.show(true);
        genderLabel.show(true);  genderField.show(true);
        contactLabel.show(true);  contactField.show(true);
        deptLabel.show(true);  deptBox.show(true);
        desgLabel.show(true);  desgBox.show(true);
        salaryLabel.show(true); salaryField.show(true);
        cityLabel.show(true); cityField.show(true);
        UpdateButton.show(true);
        refresh.show(true);

        idField.setEditable(false);


        String query_emp = "SELECT * from emp_details where E_id = ?";
        String query_work = "SELECT * from works where E_id = ?";

        PreparedStatement p_emp = connection.prepareStatement(query_emp);
        PreparedStatement p_work = connection.prepareStatement(query_work);

        p_emp.setInt(1,id);
        p_work.setInt(1,id);

        ResultSet r_emp = p_emp.executeQuery();
        ResultSet r_work = p_work.executeQuery();

        // nameLabel, ageLabel,genderLabel,contactLabel,deptLabel,desgLabel,salaryLabel,cityLabel
        String nameBefore = "";
        int ageBefore = 0;
        String numberBefore = "";
        String gender = "";
        String cityBefore = "";
        String deptBefore = "";
        String desgBefore = "";
        Double salaryBefore = 0.0;

        while (r_emp.next()) {
            nameBefore = r_emp.getString(2);
            ageBefore = r_emp.getInt(3);
            numberBefore = r_emp.getString(4);
            gender = r_emp.getString(5);
            cityBefore = r_emp.getString(7);
        }
        while (r_work.next()) {
            deptBefore = r_work.getString(2);
            desgBefore = r_work.getString(3);
            salaryBefore = r_work.getDouble(4);
        }

        nameField.setText(nameBefore);
        ageField.setText(ageBefore + "");
        contactField.setText(numberBefore);
        genderField.setText(gender); genderField.setEditable(false);
        deptBox.setSelectedItem(deptBefore);
        desgBox.setSelectedItem(desgBefore);
        cityField.setText(cityBefore);
        salaryField.setText(salaryBefore + "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {
            String getId = idField.getText();
            if(getId.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter id");
                return;
            }
            for (int i = 0; i < getId.length(); i++) {
                if(getId.charAt(i) < '0' || getId.charAt(i) > '9'){
                    JOptionPane.showMessageDialog(null,"Enter valid id");
                    idField.setText("");
                    return;
                }
            }
            id = Integer.parseInt(getId);

            String query = "SELECT E_id from emp_details";

            SimpleList SL = new SimpleList();

            try {
                Statement getAllId = connection.createStatement();
                ResultSet rs = getAllId.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt(1);
                    SL.InsertLast(id);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            boolean present = SL.Search(id);
            if(!present){
                JOptionPane.showMessageDialog(null,"Id not Found");
                return;
            }else {
                try {
                    show();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if(e.getSource() == UpdateButton ){
            String name = nameField.getText();
            String age = ageField.getText();
            String contact = contactField.getText();
            String dept = deptBox.getSelectedItem().toString();
            String desg = desgBox.getSelectedItem().toString();

            String city = cityField.getText();

            if(name.isEmpty()){
                nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                JOptionPane.showMessageDialog(null,"Enter name",null,JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(age.isEmpty()){
                ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                JOptionPane.showMessageDialog(null,"Enter age",null,JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(contact.isEmpty()){
                contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                JOptionPane.showMessageDialog(null,"Enter contact number",null,JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(city.isEmpty()){
                cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                JOptionPane.showMessageDialog(null,"Enter city",null,JOptionPane.WARNING_MESSAGE);
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


            for (int i = 0; i < age.length(); i++) {
                if(age.charAt(i) < '0' || age.charAt(i) > '9'){
                    ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                    JOptionPane.showMessageDialog(null,"Enter valid age");
                    return;
                }
            }

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

            int ageInt = Integer.parseInt(age);

            String[] FirstName = name.split(" ");

            String email = FirstName[0] + dept + id + "@" + comName + ".com";

            try {
                PreparedStatement p_emp = connection.prepareStatement("UPDATE emp_details SET E_name = ? , E_age = ? , E_phoneNum = ? , E_city = ? where E_id = ?");
                PreparedStatement p_work = connection.prepareStatement("UPDATE works SET E_dept = ? , E_desig = ? , E_salary = ? , E_mail = ? where E_id = ?");

                p_emp.setString(1,name);
                p_emp.setInt(2,ageInt);
                p_emp.setString(3,contact);
                p_emp.setString(4,city);
                p_emp.setInt(5,id);

                p_work.setString(1,dept);
                p_work.setString(2,desg);
                p_work.setDouble(3, salary);
                p_work.setString(4,email);
                p_work.setInt(5,id);

                int r1 = p_emp.executeUpdate();
                int r2 = p_work.executeUpdate();

                if(r1 > 0 && r2 > 0){
                    JOptionPane.showMessageDialog(null,"update complete details on id " + id,null,JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null,"Not Update on id " + id,null,JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose();
                new UpdateEmp();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(e.getSource() == refresh){
            frame.dispose();
            new UpdateEmp();
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
        if (e.getSource() == ageField) {
            ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
        }
        if (e.getSource() == genderField) {
            genderField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.green));
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
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == nameField) {
            nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == ageField) {
            ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        }
        if (e.getSource() == genderField) {
            genderField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
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
    }
}
