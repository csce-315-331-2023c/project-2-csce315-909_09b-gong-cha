package com.example.gongchapos;

import java.sql.*;

import javax.swing.JOptionPane;

public class Application {

  Connection conn = null;

  protected void ConnectToDatabase(String netID, String password)
  {
    try {
      conn = DriverManager.getConnection(
        "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_09b_db",
        "csce315_909_" + netID,
        password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName()+": "+e.getMessage());
      System.exit(0);
    }
  }

  protected void closeDatabase()
  {
    //closing the connection
    try {
      conn.close();
      JOptionPane.showMessageDialog(null,"Connection Closed.");
    } catch(Exception e) {
      JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
    }
  }

  public String BasicQuery(String query)
  {
    String output = "";
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery(query);
      while (result.next()) 
      {
        output += result.getString("order_id")+"\n";
      }
    } catch (Exception e){
        JOptionPane.showMessageDialog(null,"Error accessing Database.");
    }

    return output;
  }
    
}
