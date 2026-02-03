import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteEmp implements ActionListener{
    JFrame frame;
    JPanel panel,panel1;
    JLabel idLabel,nameLabel, ageLabel,genderLabel,contactLabel,deptLabel,desgLabel,salaryLabel,cityLabel,emailLabel;
    JButton searchButton,delete,backButton,refeshButton;
    JTextField idField,nameField,contactField,salaryField,ageField,genderField,cityField,deptField,desgField,emailField;
    Connection connection;
    CallableStatement callableStatement;
    PreparedStatement ForGetId;
    int id ;

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

    public DeleteEmp(){

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
        frame.setTitle("Delete emp detail");
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
        panel.setBounds(250, 90, 800, 500);
        panel.setBackground(Color.WHITE);

        JLabel PanelTitle = new JLabel("<html>Delete<br>Employee<br>Details</html>");
        PanelTitle.setFont(new Font("Fira Code", Font.BOLD, 43));
        PanelTitle.setBounds(20,70,270,200);
        PanelTitle.setForeground(Color.WHITE);

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
        searchButton.setFont(new Font("Fira Code", Font.BOLD, 15));
        searchButton.addActionListener(this);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusable(false);
        searchButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(false);
        searchButton.setContentAreaFilled(false);

        nameLabel = new JLabel("Name :");
        nameLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        nameField.setFont(new Font("Fira Code", Font.BOLD, 15));
        nameLabel.setBounds(350, y, labelW, h);
        nameField.setBounds(500, y, fieldW, h);
        y += 40;

        ageLabel = new JLabel("Age :");
        ageLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        ageField = new JTextField();
        ageField.setEditable(false);
        ageField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        ageField.setFont(new Font("Fira Code", Font.BOLD, 15));
        ageLabel.setBounds(350, y, labelW, h);
        ageField.setBounds(500, y, fieldW, h);
        y += 40;

        genderLabel = new JLabel("Gender :");
        genderLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderField = new JTextField();
        genderField.setEditable(false);
        genderField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        genderField.setFont(new Font("Fira Code", Font.BOLD, 15));
        genderLabel.setBounds(350, y, labelW, h);
        genderField.setBounds(500, y, fieldW, h);
        y += 40;

        contactLabel = new JLabel("Contact No :");
        contactLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactField = new JTextField();
        contactField.setEditable(false);
        contactField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        contactField.setFont(new Font("Fira Code", Font.BOLD, 15));
        contactLabel.setBounds(350, y, labelW, h);
        contactField.setBounds(500, y, fieldW, h);
        y += 40;

        emailLabel = new JLabel("Email : ");
        emailLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        emailField = new JTextField();
        emailField.setEditable(false);
        emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        emailField.setFont(new Font("Fira Code", Font.BOLD, 15));
        emailLabel.setBounds(350, y, labelW, h);
        emailField.setBounds(500, y, fieldW, h);
        y += 40;

        deptLabel = new JLabel("Department :");
        deptLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptField = new JTextField();
        deptField.setEditable(false);
        deptField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        deptField.setFont(new Font("Fira Code", Font.BOLD, 15));
        deptLabel.setBounds(350, y, labelW, h);
        deptField.setBounds(500, y, fieldW, h);
        y += 40;


        desgLabel = new JLabel("Designation :");
        desgLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        desgField = new JTextField();
        desgField.setEditable(false);
        desgField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        desgField.setFont(new Font("Fira Code", Font.BOLD, 15));
        desgLabel.setBounds(350, y, labelW, h);
        desgField.setBounds(500, y, fieldW, h);
        y += 40;

        salaryLabel = new JLabel("Salary :");
        salaryLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField = new JTextField();
        salaryField.setEditable(false);
        salaryField.setFont(new Font("Fira Code", Font.BOLD, 15));
        salaryField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        salaryLabel.setBounds(350, y, labelW, h);
        salaryField.setBounds(500, y, fieldW, h);
        y += 40;

        cityLabel = new JLabel("City : ");
        cityLabel.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityField = new JTextField();
        cityField.setEditable(false);
        cityField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        cityField.setFont(new Font("Fira Code", Font.BOLD, 15));
        cityLabel.setBounds(350,y,labelW,h);
        cityField.setBounds(500,y,fieldW,h);
        y += 40;

        refeshButton = new JButton("Refresh");
        refeshButton.setFocusable(false);
        refeshButton.addActionListener(this);
        refeshButton.setBounds(600, y+10, 130, 35);
        refeshButton.setForeground(Color.BLACK);
        refeshButton.setFont(new Font("Fira code",Font.BOLD,17));
        refeshButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        refeshButton.setFocusPainted(false);
        refeshButton.setOpaque(false);
        refeshButton.setContentAreaFilled(false);

        delete = new JButton("Delete");
        delete.setFocusable(false);
        delete.addActionListener(this);
        delete.setBounds(400,y+10,130,35);
        delete.setFont(new Font("Fira code",Font.BOLD,17));
        delete.setForeground(Color.BLACK);
        delete.setBorder(new RoundedBorder(Color.BLACK, 17));
        delete.setFocusPainted(false);
        delete.setOpaque(false);
        delete.setContentAreaFilled(false);

        backButton = new JButton("back");
        backButton.addActionListener(this);
        backButton.setBounds(100,y+10,100,35);
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);

        // Add components
        panel.add(nameLabel); panel.add(nameField);
        panel.add(ageLabel); panel.add(ageField);
        panel.add(genderLabel); panel.add(genderField);
        panel.add(contactLabel); panel.add(contactField);
        panel.add(deptLabel); panel.add(deptField);
        panel.add(desgLabel); panel.add(desgField);
        panel.add(salaryLabel); panel.add(salaryField);
        panel.add(cityLabel); panel.add(cityField);
        panel.add(emailLabel); panel.add(emailField);
        panel.add(delete);
        panel.add(backButton);
        panel.add(refeshButton);

        nameField.show(false);  nameLabel.show(false);
        ageLabel.show(false);  ageField.show(false);
        genderLabel.show(false);  genderField.show(false);
        contactLabel.show(false);  contactField.show(false);
        deptLabel.show(false);  deptField.show(false);
        desgLabel.show(false);  desgField.show(false);
        salaryLabel.show(false); salaryField.show(false);
        cityLabel.show(false); cityField.show(false);
        emailLabel.show(false); emailField.show(false);
        delete.show(false);
        refeshButton.show(false);

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
        deptLabel.show(true);  deptField.show(true);
        desgLabel.show(true);  desgField.show(true);
        salaryLabel.show(true); salaryField.show(true);
        cityLabel.show(true); cityField.show(true);
        emailLabel.show(true); emailField.show(true);
        delete.show(true);
        refeshButton.show(true);

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
        String name = "";
        int age = 0;
        String number = "";
        String gender = "";
        String city = "";
        String dept = "";
        String desg = "";
        Double salary = 0.0;
        String email = "";

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
        genderField.setText(gender); genderField.setEditable(false);
        deptField.setText(dept);
        desgField.setText(desg);
        cityField.setText(city);
        emailField.setText(email);
        salaryField.setText(salary + "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String getId = idField.getText();
            if(getId.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter id");
                frame.dispose();
                new DeleteEmp();
                return;
            }
            for (int i = 0; i < getId.length(); i++) {
                if(getId.charAt(i) < '0' || getId.charAt(i) > '9'){
                    JOptionPane.showMessageDialog(null,"Enter valid id");
                    idField.setText("");
                    frame.dispose();
                    new DeleteEmp();
                    return;
                }
            }
            id = Integer.parseInt(getId);

            String query = "SELECT E_id from emp_details";

            DoublyList DL = new DoublyList();

            try {
                Statement getAllId = connection.createStatement();
                ResultSet rs = getAllId.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt(1);
                    DL.InsertLast(id);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            boolean present = DL.Search(id);
            if(!present){
                JOptionPane.showMessageDialog(null,"Id not Found");
                frame.dispose();
                new DeleteEmp();
                return;
            }else {
                try {
                    show();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if(e.getSource() == delete){

            String q_emp = "DELETE from emp_details where E_id = ? ";
            String q_work = "DELETE from works where E_id = ? ";

            try {
                PreparedStatement p_emp = connection.prepareStatement(q_emp);
                PreparedStatement p_work = connection.prepareStatement(q_work);

                p_emp.setInt(1,id);
                p_work.setInt(1,id);

                int r1 = p_emp.executeUpdate();
                int r2 = p_work.executeUpdate();

                if(r1 > 0 && r2>0){
                    JOptionPane.showMessageDialog(null,"Details delete of id " + id);
                    frame.dispose();
                    new DeleteEmp();
                    return;
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        if(e.getSource() == backButton){
            frame.dispose();
            new HomePageAdmin();
        }
        if (e.getSource() == refeshButton) {
            frame.dispose();
            new DeleteEmp();
        }
    }
}




