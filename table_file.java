import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class table_file {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://localhost:3306/java_practice";
        String user = "root";
        String pass = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        Class.forName(driver);

        Connection connection = DriverManager.getConnection(url,user,pass);

        System.out.println(connection != null ? "done" : "not done");

        JFrame frame = new JFrame();

        frame.setVisible(true);
        frame.setLayout(null);
        frame.setBounds(250,50,600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("id");
        model1.addColumn("name");
        model1.addColumn("dept");
        model1.addColumn("mark");


        JTable table1 = new JTable(model1);
        table1.setBounds(0,0,600,100);
        JScrollPane pane1 = new JScrollPane(table1);
        pane1.setBounds(0,0,500,100);

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(pane1);


        String query = "SELECT * FROM student";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet r = preparedStatement.executeQuery();

        while (r.next()){
            int id = r.getInt(1);
            String name = r.getString(2);
            String dept = r.getString(3);
            int mark = r.getInt(4);

            model1.addRow(new Object[]{id,name,dept,mark});
        }

        frame.setVisible(true);
    }
}

/*import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class table_file {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Null Layout Table Example");
        frame.setLayout(null);
        frame.setBounds(250, 50, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create data for table1
        Vector<String> v = new Vector<>();
        for (int i = 1; i <= 20; i++) {
            v.add("Value " + i);
        }

        // Model for table1
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("name", v);
        model1.addColumn("age", v);

        JTable table1 = new JTable(model1);
        JScrollPane pane1 = new JScrollPane(table1);
        pane1.setBounds(10, 10, 550, 200); // adjusted to fit in frame

        frame.getContentPane().add(pane1);

        frame.setVisible(true);
    }
}*/