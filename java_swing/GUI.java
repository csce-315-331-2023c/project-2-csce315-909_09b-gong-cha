import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ExitButtonListener implements ActionListener {
    private Connection conn;

    public ExitButtonListener(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            conn.close(); // Close the database connection
            JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }
        System.exit(0); // Exit the application
    }
}


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

    /*
     * This function handles the button creation for drinks so they all 
     * follow the same style. It takes in a String of the text it will 
     * contain, and formats it using html methods and Java swing commands.
     */
    public static JButton createDrinkButton(String text) {
      JButton button = new JButton("<html><div style='text-align: center; vertical-align: middle;'>"+text+"</div></html>");

      button.setBackground(Color.GREEN);
      button.setPreferredSize(new Dimension(100, 100));
      button.setHorizontalAlignment(SwingConstants.CENTER);
      button.setVerticalAlignment(SwingConstants.CENTER);

      return button;
    }

    public static void main(String[] args)
    {
      //Pass in NetID and Password as command line arguments
      if(args.length != 2) {
        System.out.println("Error: Must supply NetID and Password as command line arguments");
        System.exit(0);
      }
      String netID = args[0];
      String password = args[1];
      System.out.println("NetID: " + netID + "\nPassword: " + password);
      //Building the connection
      Connection conn = null;

      //TODO STEP 1
      try {
        conn = DriverManager.getConnection(
          "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_09b_db",
          "csce315_909_"+netID,
          password);
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
      JPanel cashierDrinkPanel = new JPanel();
      JPanel managerPanel = new JPanel();
      JPanel managerDrinkPanel = new JPanel();

      JButton exitButton = new JButton("Exit");
      JButton cashierButton = new JButton("Cashier");
      JButton cashierBackButton = new JButton("Back");
      JButton managerButton = new JButton("Manager");
      JButton managerBackButton = new JButton("Back");

      JButton blackMilkTea= createDrinkButton("<html>Black<br>Milk Tea</html>");
      JButton brownSugarMilkTea = createDrinkButton("<html>Brown Sugar<br>Milk Tea</html>");
      JButton caramelMilkTea = createDrinkButton("<html>Caramel<br>Milk Tea</html>");
      JButton earlGreyMilkTea = createDrinkButton("<html>Earl Grey<br>Milk Tea</html>");
      JButton earlGreyMilkTea3Js = createDrinkButton("<html>Earl Grey<br>Milk Tea<br>3Js</html>");
      JButton greenMilkTea = createDrinkButton("<html>Green<br>Milk Tea</html>");
      JButton oolongMilkTea = createDrinkButton("<html>Oolong<br>Milk Tea</html>");
      JButton pearlMilkTea = createDrinkButton("<html>Pearl<br>Milk Tea</html>");
      JButton strawberryMilkTea = createDrinkButton("<html>Strawberry<br>Milk Tea</html>");
      JButton wintermelonMilkTea = createDrinkButton("<html>Wintermelon<br>Milk Tea</html>");

      // add actionlistener to button
      //make it override ActionListener and close connection if it is hit.
      // exitButton.addActionListener(s);
      // // ...

      // When initializing the exit button, pass the connection
      exitButton.addActionListener(new ExitButtonListener(conn));

      cashierButton.addActionListener(s);
      cashierBackButton.addActionListener(s);
      managerButton.addActionListener(s);
      managerBackButton.addActionListener(s);

      blackMilkTea.addActionListener(s);
      brownSugarMilkTea.addActionListener(s);
      caramelMilkTea.addActionListener(s);
      earlGreyMilkTea.addActionListener(s);
      earlGreyMilkTea3Js.addActionListener(s);
      greenMilkTea.addActionListener(s);
      oolongMilkTea.addActionListener(s);
      pearlMilkTea.addActionListener(s);
      strawberryMilkTea.addActionListener(s);
      wintermelonMilkTea.addActionListener(s);
      
      JTextArea newTextArea = new JTextArea(9, 5);
      newTextArea.setText(name);
      newTextArea.setEditable(false);
      loginPanel.add(newTextArea);


      // add buttons to panels
      loginPanel.add(exitButton);
      loginPanel.add(cashierButton);
      loginPanel.add(managerButton);

      cashierPanel.add(cashierBackButton);

      cashierDrinkPanel.add(blackMilkTea);
      cashierDrinkPanel.add(brownSugarMilkTea);
      cashierDrinkPanel.add(caramelMilkTea);
      cashierDrinkPanel.add(earlGreyMilkTea);
      cashierDrinkPanel.add(earlGreyMilkTea3Js);
      cashierDrinkPanel.add(greenMilkTea);
      cashierDrinkPanel.add(oolongMilkTea);
      cashierDrinkPanel.add(pearlMilkTea);
      cashierDrinkPanel.add(strawberryMilkTea);
      cashierDrinkPanel.add(wintermelonMilkTea);

      managerPanel.add(managerBackButton);
      
      // add panel to frame
      loginFrame.add(loginPanel);
      cashierFrame.add(cashierPanel);
      cashierFrame.add(cashierDrinkPanel, BorderLayout.CENTER);
      managerFrame.add(managerPanel);
      managerFrame.add(managerDrinkPanel, BorderLayout.CENTER);

      // set the size of frame
      loginFrame.setSize(640, 480);
      cashierFrame.setSize(640, 480);
      managerFrame.setSize(640, 480);

      // prevent resizability of the frames
      loginFrame.setResizable(false);
      cashierFrame.setResizable(false);
      managerFrame.setResizable(false);

      loginFrame.setVisible(true);

      // try {
      //   conn.close();
      //   JOptionPane.showMessageDialog(null,"Connection Closed.");
      // } catch(Exception e) {
      //   JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
      // }
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
