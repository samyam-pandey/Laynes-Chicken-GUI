import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

/*
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;
import org.jfree.ui.*;
*/


public class popularityTest extends JFrame implements ActionListener {
    static JFrame f;
    
    public static void main(String[] args) {
        // Building the connection
        Connection conn = null;

        // TODO STEP 1
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315902_12db",
                    "csce315902_12user", "team12");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        // JOptionPane.showMessageDialog(null, "Opened database successfully");

        String output = "";
        /*
        String name = "";
        String menukey = "";
        String quantity = "";
        */
        try {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            
            // Testing sql commands
            /*
            String sqlStatement = "select * from menu;";          
            String sqlStatement2 = "SELECT menukey, quantity, date FROM customerorder WHERE date >= '2022/02/20' AND date < '2022/02/27';";
            String sqlStatement3 = "select * from customerordertest;";
            */

            String sqlStatement4 = "SELECT menu.name, customerorder.quantity, customerorder.price, customerorder.date FROM customerorder INNER JOIN menu "
            + "ON (customerorder.menukey = menu.menuid) WHERE date >= '2022/02/20' AND date < '2022/02/27' " 
            + "ORDER BY customerorder.quantity DESC LIMIT 20;";

            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement4);
            
            while (result.next()) {
                output += result.getString("name") + ", ";
                output += result.getString("quantity") + ", ";
                output += result.getString("price") + ", ";
                output += result.getString("date") + "\n";
            }

            // convert output into 2D array
            String[] rows = output.split("\n");
            String[][] table = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                String[] cols = rows[i].split(", ");
                table[i] = cols;                
            }
            
            System.out.println(table[2][1]);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
        // create a new frame
        f = new JFrame("DB GUI");

        // create a object
        GUI s = new GUI();

        // create a panel
        JPanel p = new JPanel();
        JButton b = new JButton("Close");

        // add actionlistener to button
        b.addActionListener(s);
        


        // TODO Step 3 and 4
        String text = "Popularity!!! \n \n";
        JTextArea textArea = new JTextArea(text);
        p.add(textArea);

        // adding text to jTextArea
        textArea.append(output);

        // add button to panel
        p.add(b);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        // f.show();
        // closing the connection

        try {
            conn.close();
            // JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Close")) {
            f.dispose();
        }
    }
}