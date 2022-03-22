import java.io.*;
import java.sql.*;
// import java.util.concurrent.ThreadLocalRandom;

public class unitConversion {
    public static void main(String args[]) 
  {
    
    Connection conn = null;
    String teamNumber = "12";
    String sectionNumber = "902";
    String dbName = "csce315" + sectionNumber + "_" + teamNumber + "db";
    String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
    String userName = "csce315" + sectionNumber + "_" + teamNumber + "user";
    String userPassword = "team12";
    
    // connecting the database
    try 
    {
      conn = DriverManager.getConnection(dbConnectionString, userName, userPassword);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.err.println(e.getClass().getName()+": "+e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    
    // Reading Files
    try 
    {
      // connect to server
      // Statement stmt = conn.createStatement();
      Statement stmt = conn.createStatement();
      File file = new File("Project2Data/UnitConversion.csv");

      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      
      // Variables
      String item;
      String description;
      String quantity;
      String lbs;
      String price;
      String line = "";
      String[] tempArr;
      String priceNew;
      
      
      // change the loop to while
      while ((line = br.readLine()) != null)
      {
        String statement = "INSERT INTO unitconversion(item, description, quantity," +
        " lbs, price) VALUES (";
        
        // read and store the line into a array
        tempArr = line.split(",");

        if (tempArr[0] == "Item") {
            System.out.println("here");
        }
        // assign information into variables
        item = tempArr[0];
        description = tempArr[1];
        quantity = tempArr[2];
        lbs = tempArr[3];
        price = tempArr[4];
        
        // modify inputs
        priceNew = price.replace("$", "");          // remove $ in the input
        priceNew = priceNew.replaceAll("\\s", "");  // remove whire spaces

        // make SQL statement to execute

        statement = statement + "'" + item + "', '" + description
          + "', " + quantity +  ", " + lbs + ", " + priceNew + ");\n";

        System.out.print(statement);
        stmt.executeUpdate(statement);
      }
      br.close();
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
      System.err.println(e.getClass().getName()+": "+e.getMessage());
      System.exit(0);
    }
  }
    
}
