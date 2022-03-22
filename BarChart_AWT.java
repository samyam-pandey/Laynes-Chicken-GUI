import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

// import org.jfree.data.category.CategoryDataset; 
// import org.jfree.ui.ApplicationFrame;
// import org.jfree.ui.RefineryUtilities;

import java.sql.*;
import java.time.DayOfWeek;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

// Old extends: ApplicationFrame
public class BarChart_AWT {

   class Order {
      String name;
      int quantity;
      String date;
      float price;
   }

   public static ChartPanel BarChart(String chartTitle, String xaxis, String yasix, DefaultCategoryDataset dataset) {
      // super("chart");
      JFreeChart barChart = ChartFactory.createBarChart(
            chartTitle, // Chart Title
            xaxis, // x-axis
            yasix, // y-axis
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

      ChartPanel chartPanel = new ChartPanel(barChart);
      // chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
      // setContentPane(chartPanel);
      return chartPanel;
   }

   public static DefaultCategoryDataset createDataset(int selection) {
      
      String output = queryData(selection);
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

      String[] rows = output.split("\n");
      String[][] table = new String[rows.length][];
      for (int i = 0; i < rows.length; i++) {
          String[] cols = rows[i].split(", ");
          table[i] = cols;                
      }

      switch(selection) {
         case 1:
            for (int i = 0; i < table.length; i++) {
               float price = Float.parseFloat(table[i][2]);
               dataset.addValue(price, table[i][0], table[i][3]);
            }
         case 2:
            for (int i = 0; i < table.length; i++) {
               int quantity = Integer.parseInt(table[i][1]);
               dataset.addValue(quantity, table[i][0], table[i][3]);
            }
         case 3:
            for (int i = 0; i < 10; i++) {
               double quantity = -12.0;
               dataset.addValue(quantity, table[i][0], table[i][3]);
               quantity += 7;
            }
      }


      return dataset;
   }

   public static String queryData(int caseNum) {
      Connection conn = null;
      try {
         Class.forName("org.postgresql.Driver");
         conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315902_12db",
               "csce315902_12user", "team12");
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      
      if (caseNum > 2) {
         caseNum = 2;
      }

      String output = "";

      switch (caseNum) {     
         case 1:  // Weekly
            System.out.println("Weekly");
            
            try {
               Statement stmt = conn.createStatement();
               String sqlStatement = "SELECT menu.name, customerorder.quantity, customerorder.price, customerorder.date FROM customerorder INNER JOIN menu "
               + "ON (customerorder.menukey = menu.menuid) WHERE date >= '2022/02/20' AND date < '2022/02/27' " 
               + ";";
   
               // send statement to DBMS
               ResultSet result = stmt.executeQuery(sqlStatement);
               
               while (result.next()) {
                   output += result.getString("name") + ", ";
                   output += result.getString("quantity") + ", ";
                   output += result.getString("price") + ", ";
                   output += result.getString("date") + "\n";
               }
   
               System.out.println("Queried \n");
               return output;
            }
            catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Error accessing Database in case 1");
            }
         case 2:
            System.out.println("Dialy");
            try {
               Statement stmt = conn.createStatement();
               String sqlStatement = "SELECT menu.name, customerorder.quantity, customerorder.price, customerorder.date FROM customerorder INNER JOIN menu "
               + "ON (customerorder.menukey = menu.menuid) WHERE date = '2022/02/15';"; 

               // send statement to DBMS
               ResultSet result = stmt.executeQuery(sqlStatement);
               
               while (result.next()) {
                  output += result.getString("name") + ", ";
                  output += result.getString("quantity") + ", ";
                  output += result.getString("price") + ", ";
                  output += result.getString("date") + "\n";
               }

               System.out.println("Queried \n");
               return output;
            }
            catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Error accessing Database in case 2");
            }
      }
      return null;
   }
    
   public JPanel panelCons(ChartPanel chart) {
      JPanel p = new JPanel();
      p = chart;
      return p;
   }

   CardLayout layout = new CardLayout();   
   JPanel cardPanel = new JPanel(layout);

   public BarChart_AWT() {
      TestCard();
   }

   private void TestCard() {
      JButton showOne = new JButton("Show One");
      JButton showTwo = new JButton("Show Two");

      JPanel buttonsPanel = new JPanel();
      buttonsPanel.add(showOne);
      buttonsPanel.add(showTwo);
      showOne.addActionListener(new ButtonListener());
      showTwo.addActionListener(new ButtonListener());

      DefaultCategoryDataset dataset = createDataset(1);
      ChartPanel chart1 = BarChart("Chart 1", "Menus", "Price $", dataset);

      DefaultCategoryDataset dataset2 = createDataset(3);
      ChartPanel chart2 = BarChart("Chart 2", "Menus", "Quantity", dataset2);
      
      JPanel p1 = panelCons(chart1);
      JPanel p2 = panelCons(chart2);

      cardPanel.add(p1, "panel 1");
      cardPanel.add(p2, "panel 2");

      JFrame frame = new JFrame("Test Card");
      frame.add(cardPanel);
      frame.add(buttonsPanel, BorderLayout.SOUTH);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   private class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();
         if ("Show One".equals(command)) {
            // layout.show(cardPanel, "panel 1");
            layout.show(cardPanel, "panel 1");
            
         }
         else if ("Show Two".equals(command)) {
            layout.show(cardPanel, "panel 2");
         }
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            BarChart_AWT test = new BarChart_AWT();
         }
      });
   }
   /*
      DefaultCategoryDataset dataset = createDataset(selection);
      ChartPanel chart = BarChart("test", dataset);
   */
}

