import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.DataBufferByte;
import java.sql.*;

public class scanQR implements ActionListener {
    JFrame frame;
    JButton backButton;
    JPanel panel,panel1;
    boolean running = true;

    static {
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java4120.dll");
    }

    private String lastDetected = "";

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

    public void startQRScanner() {
        frame = new JFrame("Employee QR Code Scanner");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        panel.setBounds(100, 50, 700, 500);
        panel.setBackground(Color.WHITE);

        ImageIcon SideImage = new ImageIcon("C:\\Users\\vedpa\\OneDrive\\Desktop\\java_Group_project\\ChatGPT Image Aug 18, 2025, 07_22_20 PM.png"); // put your image path here
        JLabel SideImageLabel = new JLabel();
        SideImageLabel.setBounds(0,0,700,500);
        SideImageLabel.setBorder(new RoundedBorder(Color.PINK, 30));
        
        JLabel label = new JLabel();
        label.setBounds(15,15,730,470);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Fira code",Font.BOLD,17));
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(1050, 50, 120, 35);
        backButton.addActionListener(this);
        backButton.setBorder(new RoundedBorder(Color.WHITE, 17));
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);

        panel.add(label);
        panel.add(SideImageLabel);

        panel1.add(backButton);
        panel1.add(backgroundLabel);

        frame.add(panel);
        frame.add(panel1);
        
        frame.setVisible(true);

        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not available.");
            return;
        }

        Thread cameraThread = new Thread(() -> {
            Mat frameMat = new Mat();
            while (running) {
                if (camera.read(frameMat)) {
                    BufferedImage image = matToBufferedImage(frameMat);
                    label.setIcon(new ImageIcon(image));

                    try {
                        Result result = decodeQRCode(image);
                        if (result != null && !result.getText().equals(lastDetected)) {
                            lastDetected = result.getText();
                            handleQRCode(lastDetected);
                        }
                    } catch (NotFoundException ignore) {}
                }
            }
        });
        cameraThread.start();
        
    }

    public static Result decodeQRCode(BufferedImage image) throws NotFoundException {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        return new MultiFormatReader().decode(bitmap);
    }

    public static BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_3BYTE_BGR;
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);
        BufferedImage img = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return img;
    }

    private void handleQRCode(String empId) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM emp_details WHERE E_id = ?");
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Invalid Employee ID!");
                return;
            }

            
            ps = con.prepareStatement("SELECT * FROM attendance WHERE E_id = ? AND date = CURRENT_DATE");
            ps.setString(1, empId);
            rs = ps.executeQuery();

            if (!rs.next()) {
                
                ps = con.prepareStatement("INSERT INTO attendance (E_id, date, entry_time) VALUES (?, CURRENT_DATE, CURRENT_TIME)");
                ps.setString(1, empId);
                ps.executeUpdate();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Entry log created for Employee ID: " + empId);

            } else {
                
                Time exitTime = rs.getTime("exit_time");
                Time entryTime = rs.getTime("entry_time");

                if (exitTime == null) {
                    
                    ps = con.prepareStatement(
                            "UPDATE attendance " +
                            "SET exit_time = CURRENT_TIME, " +
                            "work_minutes = TIMESTAMPDIFF(MINUTE, entry_time, CURRENT_TIME) " +
                            "WHERE E_id = ? AND date = CURRENT_DATE"
                    );
                    ps.setString(1, empId);
                    ps.executeUpdate();
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Exit log created for Employee ID: " + empId);
                } else {
                    JOptionPane.showMessageDialog(null, "Already marked entry & exit for today.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            running = false;
            frame.dispose();
            new HomePageAdmin();
        }
    }
}
