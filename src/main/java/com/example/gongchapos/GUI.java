package com.example.gongchapos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    private Application app = null;

    public GUI(Application _app)
    {
      app = _app;
      launchGUI();
    }

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


    public void launchGUI()
    {
      JOptionPane.showMessageDialog(null,"Opened database successfully");

      String name = app.BasicQuery("SELECT * FROM order_ LIMIT 5");  

      // create frames for the 3 possible windows
      loginFrame = new JFrame("Welcome to Gong Cha!");
      cashierFrame = new JFrame("Cashier");
      managerFrame = new JFrame("Manager");

      // create a object
      // GUI s = new GUI();

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
      exitButton.addActionListener(this);
      cashierButton.addActionListener(this);
      cashierBackButton.addActionListener(this);
      managerButton.addActionListener(this);
      managerBackButton.addActionListener(this);

      blackMilkTea.addActionListener(this);
      brownSugarMilkTea.addActionListener(this);
      caramelMilkTea.addActionListener(this);
      earlGreyMilkTea.addActionListener(this);
      earlGreyMilkTea3Js.addActionListener(this);
      greenMilkTea.addActionListener(this);
      oolongMilkTea.addActionListener(this);
      pearlMilkTea.addActionListener(this);
      strawberryMilkTea.addActionListener(this);
      wintermelonMilkTea.addActionListener(this);
      
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

    }

    // if button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Exit")) {
          app.closeDatabase();
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
