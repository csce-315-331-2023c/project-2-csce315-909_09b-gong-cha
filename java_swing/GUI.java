import java.sql.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/*
  TODO:
  1) Change credentials for your own team's database
  2) Change SQL command to a relevant query that retrieves a small amount of data
  3) Create a JTextArea object using the queried data
  4) Add the new object to the JPanel p
*/

public class GUI extends JFrame implements ActionListener {
    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    public static void main(String[] args)
    {
      //Building the connection
      Connection conn = null;
      //TODO STEP 1
      try {
        conn = DriverManager.getConnection(
          "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_09b_db",
          "csce315_909_<NET ID>",
          "<password>");
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
      }
      JOptionPane.showMessageDialog(null,"Opened database successfully");

      String name = "";
      try{
        //create a statement object
        Statement stmt = conn.createStatement();
        //create a SQL statement
        //TODO Step 2
        String sqlStatement = "SELECT * FROM order_ LIMIT 10;";
        //send statement to DBMS
        ResultSet result = stmt.executeQuery(sqlStatement);
        while (result.next()) {
          name += result.getString("order_id")+"\n";
        }
      } catch (Exception e){
        JOptionPane.showMessageDialog(null,"Error accessing Database.");
      }
      // create frames for the 3 possible windows
      loginFrame = new JFrame("Welcome to Gong Cha!");
      cashierFrame = new JFrame("Cashier");
      managerFrame = new JFrame("Manager");

      // create a object
      GUI s = new GUI();

      // create a panel
      JPanel loginPanel = new JPanel();
      JPanel cashierPanel = new JPanel();
      JPanel managerPanel = new JPanel();

      JButton exitButton = new JButton("Exit");
      JButton cashierButton = new JButton("Cashier");
      JButton cashierBackButton = new JButton("Back");
      JButton managerButton = new JButton("Manager");
      JButton managerBackButton = new JButton("Back");



      // add actionlistener to button
      exitButton.addActionListener(s);
      cashierButton.addActionListener(s);
      cashierBackButton.addActionListener(s);
      managerButton.addActionListener(s);
      managerBackButton.addActionListener(s);


      //TODO Step 3 
      JTextArea newTextArea = new JTextArea(9, 5);
      newTextArea.setText(name);
      //TODO Step 4
      loginPanel.add(newTextArea);


      // add buttons to panels
      loginPanel.add(exitButton);
      loginPanel.add(cashierButton);
      loginPanel.add(managerButton);

      cashierPanel.add(cashierBackButton);

      managerPanel.add(managerBackButton);
      
      // add panel to frame
      loginFrame.add(loginPanel);
      cashierFrame.add(cashierPanel);
      managerFrame.add(managerPanel);

      // set the size of frame
      loginFrame.setSize(640, 480);
      cashierFrame.setSize(640, 480);
      managerFrame.setSize(640, 480);


      loginFrame.setVisible(true);

      //closing the connection
      try {
        conn.close();
        JOptionPane.showMessageDialog(null,"Connection Closed.");
      } catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
      }
    }

    // if button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Exit")) {
            loginFrame.dispose();
        }
        if (s.equals("Back")) {
          loginFrame.setVisible(true);
          cashierFrame.setVisible(false);
          managerFrame.setVisible(false);
        }
        if (s.equals("Cashier")) {
            cashierFrame.setVisible(true);
            loginFrame.setVisible(false);;
        }
        if (s.equals("Manager")) {
            managerFrame.setVisible(true);
            loginFrame.setVisible(false);
        }
    }
}
