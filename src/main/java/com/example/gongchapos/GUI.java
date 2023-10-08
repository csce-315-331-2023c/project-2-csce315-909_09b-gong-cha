package com.example.gongchapos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    private Application app = null;

    public void setApp(Application _app)
    {
      app = _app;
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
      // JPanel cashierDrinkPanel = new JPanel();
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

      JButton milkcoffee = createDrinkButton("<html>Milk<br>Coffee</html>");
      JButton CoffeeMilkTea = createDrinkButton("<html>Coffee<br>Milk Tea</html>");
      JButton MilkFoamBlackCoffee = createDrinkButton("<html>Milk Foam<br>Black Coffee</html>");
      JButton TaroMilkSlush = createDrinkButton("<html>Taro<br>Milk Slush</html>");
      JButton StrawberryMilkSlush = createDrinkButton("<html>Strawberry<br>Milk Slush</html>");
      
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
      
      milkcoffee.addActionListener(this);
      CoffeeMilkTea.addActionListener(this);
      MilkFoamBlackCoffee.addActionListener(this);
      TaroMilkSlush.addActionListener(this);
      StrawberryMilkSlush.addActionListener(this);
        
      JTextArea newTextArea = new JTextArea(9, 5);
      newTextArea.setText(name);
      newTextArea.setEditable(false);
      loginPanel.add(newTextArea);


      // add buttons to panels
      loginPanel.add(exitButton);
      loginPanel.add(cashierButton);
      loginPanel.add(managerButton);

      cashierPanel.add(cashierBackButton);

      // cashierDrinkPanel.add(blackMilkTea);
      // cashierDrinkPanel.add(brownSugarMilkTea);
      // cashierDrinkPanel.add(caramelMilkTea);
      // cashierDrinkPanel.add(earlGreyMilkTea);
      // cashierDrinkPanel.add(earlGreyMilkTea3Js);
      // cashierDrinkPanel.add(greenMilkTea);
      // cashierDrinkPanel.add(oolongMilkTea);
      // cashierDrinkPanel.add(pearlMilkTea);
      // cashierDrinkPanel.add(strawberryMilkTea);
      // cashierDrinkPanel.add(wintermelonMilkTea);

      JTabbedPane tabbedPane = new JTabbedPane();
      
      JPanel milkteaholder = new JPanel();
      milkteaholder.add(blackMilkTea);
      milkteaholder.add(brownSugarMilkTea);
      milkteaholder.add(caramelMilkTea);
      milkteaholder.add(earlGreyMilkTea);
      milkteaholder.add(earlGreyMilkTea3Js);
      milkteaholder.add(greenMilkTea);
      milkteaholder.add(oolongMilkTea);
      milkteaholder.add(pearlMilkTea);
      milkteaholder.add(strawberryMilkTea);
      milkteaholder.add(wintermelonMilkTea);

      //make cashierslushiepanel
      JPanel CashierSlushiePanel = new JPanel();
      CashierSlushiePanel.add(TaroMilkSlush);
      CashierSlushiePanel.add(StrawberryMilkSlush);

      //make cashiercoffeepanel
      JPanel CashierCoffeePanel = new JPanel();
      CashierCoffeePanel.add(milkcoffee);
      CashierCoffeePanel.add(CoffeeMilkTea);
      CashierCoffeePanel.add(MilkFoamBlackCoffee);

      tabbedPane.addTab("Milk Tea", null, milkteaholder, "Does nothing");
      tabbedPane.addTab("Slushie", null, CashierSlushiePanel, "Does nothing");
      tabbedPane.addTab("Coffee", null, CashierCoffeePanel, "Does nothing");
      //put pearlmilkTea in the milk tea tab
      tabbedPane.setPreferredSize(new Dimension(824, 768));
      //put the teas in the milk tea tab
      
      // add panel to frame
      loginFrame.add(loginPanel);
      
      //make "receipt" jpanel that will always be on the side of the screen
      //make it so that it is always on the right side of the screen
      //make it so that it is always on top of the other panels
      //make it so that it is always the same size
      //make it so that it is scrollable
      //the receipt panel will be edited based on current orders, which will be handled by backend
      JPanel receiptPanel = new JPanel();
      receiptPanel.setPreferredSize(new Dimension(200, 768));
      receiptPanel.setBackground(Color.WHITE);
      receiptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
      //add name of server and an exit button that clears the receipt panel(i.e. the current order)
      receiptPanel.add(new JLabel("Server:"));
      // receiptPanel.add(new JLabel("Receipt"));
      receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      //function to edit receipt panel and add more orders/items to it
      //at the bottom of the receipt panel, we will have a part that says Subtotal, Tip, Total, and a checkout button which ends a transaction
      //the checkout button will also clear the receipt panel
      //the Jlabels for subtotal, tip, and total will be updated based on the current order, but should be always on the bottom of the receipt panel
      JPanel receiptPanel2_bottom = new JPanel();
      //receiptpanel2_bottom should be same width as receipt panel
      // receiptPanel2_bottom.setSize(new Dimension(200, 100)); //currently not working
      
      receiptPanel2_bottom.setLayout(new BoxLayout(receiptPanel2_bottom, BoxLayout.Y_AXIS));
      receiptPanel2_bottom.add(new JLabel("Subtotal:"));
      receiptPanel2_bottom.add(new JLabel("Tip:"));
      receiptPanel2_bottom.add(new JLabel("Total:"));
      JButton checkoutButton = new JButton("Checkout");
      receiptPanel2_bottom.add(checkoutButton);

      //i want to make sure the information on the order is always at the bottom of the receipt panel
      receiptPanel.add(Box.createVerticalGlue());
    
      //put receiptPanel2_bottom at the bottom of the receipt panel
      receiptPanel.add(receiptPanel2_bottom);

      cashierFrame.add(receiptPanel, BorderLayout.EAST);    
      cashierFrame.add(tabbedPane, BorderLayout.WEST);
      cashierFrame.add(cashierBackButton, BorderLayout.SOUTH);
      cashierFrame.add(cashierPanel);
      // cashierFrame.add(cashierDrinkPanel, BorderLayout.CENTER);

      
      managerFrame.add(managerBackButton, BorderLayout.SOUTH);

      managerFrame.add(managerPanel);
      managerFrame.add(managerDrinkPanel, BorderLayout.CENTER);

      // set the size of frame to be desktop
      loginFrame.setSize(640, 480);
      cashierFrame.setSize(1024, 768);
      managerFrame.setSize(1024, 768);

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