import java.io.*;
// import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class customerOrder 
{
  public static void main(String args[]) 
  {
    /*
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
    */
    // Reading Files
    try 
    {
      // connect to server
      // Statement stmt = conn.createStatement();
      
      File file = new File("Project2Data/Sales.csv");
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      
      // Variables
      String day = "0";
      String date;
      String menuKey;
      String quantity;
      String price;
      String priceNew;
      String line = "1";
      String[] tempArr;
      int employeeid = 1;
      int locationid = 123;
      // Boolean loop = true;
      
      // change the loop to while
      while (line != null)
      {
        String statement = "INSERT INTO customerorder(menuKey, quantity," +
        " price, date, employeeid, locationcode) VALUES (";
        
        // read and store the line into a array
        line = br.readLine();
        tempArr = line.split(",");
        
        // assigne information into virables
        day = tempArr[0];
        menuKey = tempArr[1];
        quantity = tempArr[2];
        price = tempArr[3];
        
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        employeeid = randomNum;
        
        // modify inputs
        priceNew = price.replace("$", "");          // remove $ in the input
        priceNew = priceNew.replaceAll("\\s", "");  // remove whire spaces
        date = "2/" + day + "/2022";

        // make SQL statement to execute

        statement = statement + "" + menuKey + ", " + quantity
          + ", " + priceNew +  ", '" + date + "', " + employeeid
          + ", " + locationid + ");\n";


        System.out.print(statement);

        //stmt.executeUpdate(statement);
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
