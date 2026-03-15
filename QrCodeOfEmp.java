import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import javax.imageio.ImageIO;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QrCodeOfEmp  implements ActionListener {
    Connection connection;
    JFrame frame;
    JTextField idField;
    JButton generateButton,backButton;
    JLabel qrLabel,Idlabel;
    BufferedImage qrImage;
    int id ;
    JPanel panel,panel1;

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

    public QrCodeOfEmp() {

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
        frame.setTitle("QR Code Generator");
        frame.setLayout(null);
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel1 = new JPanel();
        panel1.setBounds(0, 0, 1200, 600);
        panel1.setLayout(null);

        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Admin\\IdeaProjects\\Project_sem2\\src\\ChatGPT Image Aug 18, 2025, 09_45_07 AM.png"); // put your image path here
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0,0,1200,600);

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
        panel.setBounds(350, 50, 500, 400);
        panel.setBackground(Color.WHITE);

        Idlabel = new JLabel("Enter ID : ");
        Idlabel.setBounds(60,30,100,30);
        Idlabel.setFont(new Font("Fira Code", Font.BOLD, 17));

        idField = new JTextField();
        idField.setBounds(150,30,100,30);
        idField.setBorder(new RoundedBorder(Color.BLACK, 17));
        idField.setFont(new Font("Fira Code", Font.BOLD, 15));

        generateButton = new JButton("Generate");
        generateButton.setFocusable(false);
        generateButton.addActionListener(this);
        generateButton.setBounds(280,30,130,30);
        generateButton.setForeground(Color.BLACK);
        generateButton.setFont(new Font("Fira code",Font.BOLD,17));
        generateButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        generateButton.setFocusPainted(false);
        generateButton.setOpaque(false);
        generateButton.setContentAreaFilled(false);

        qrLabel = new JLabel();
        qrLabel.setBounds(80,100,250,250);
        qrLabel.setBorder(new RoundedBorder(Color.BLACK, 20));

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setBounds(380,350,100,30);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setBorder(new RoundedBorder(Color.BLACK, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);


        panel.add(Idlabel);
        panel.add(idField);
        panel.add(generateButton);
        panel.add(qrLabel);
        panel.add(backButton);

        panel1.add(backgroundLabel);

        frame.add(panel);
        frame.add(panel1);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == generateButton) {

            String getId = idField.getText().trim();
            if (getId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter some text.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
                    int check = rs.getInt(1);
                    SL.InsertLast(check);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            boolean present = SL.Search(id);
            if(present) {
                try {
                    int size = 250;
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(getId, BarcodeFormat.QR_CODE, size, size);
                    qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                    qrLabel.setIcon(new ImageIcon(qrImage));

                    // Optionally save it
                    int save = JOptionPane.showConfirmDialog(frame, "Do you want to save the QR code as an image?");
                    if (save == JOptionPane.YES_OPTION) {
                        File output = new File("C:\\Users\\Admin\\IdeaProjects\\Project_sem2\\src\\ImageOfQrCode\\Emp" + id + ".png");
                        ImageIO.write(qrImage, "PNG", output);
                        JOptionPane.showMessageDialog(frame, "QR code saved as Emp" + id + ".png");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to generate QR Code", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(null,"Id not found",null,JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if(e.getSource() == backButton){
            frame.dispose();
            new HomePageAdmin();
        }
    }
}