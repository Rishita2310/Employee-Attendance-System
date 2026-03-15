import javax.swing.*;
import java.awt.*;

public class FullScreenFrame2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("True Full Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.setUndecorated(true); // remove borders & title bar

        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(frame); // full screen

        JLabel label = new JLabel("hello");
        label.setBounds(200, 100, 200, 100);
        frame.add(label);

        frame.setVisible(true);
    }
}