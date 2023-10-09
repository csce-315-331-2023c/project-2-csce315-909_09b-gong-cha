package com.example.gongchapos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//create class for the POS system cashier end


public class GUI extends JFrame {
    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    //stuff for the cashier frame
    private JPanel itemListPanel; // Panel to hold item labels
    private double subtotal;
    private double tip;
    private double total;
    private JLabel subtotalLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;

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
      //initalize subtotal, tip, and total
      subtotal = 0;
      tip = 0;
      total = 0;

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
      //   JPanel managerDrinkPanel = new JPanel();

      JButton exitButton = new JButton("Exit");
      exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierButton = new JButton("Cashier");
      cashierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierBackButton = new JButton("Back");
      JButton managerButton = new JButton("Manager");
      managerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton managerBackButton = new JButton("Back");

      ItemButton blackMilkTea= new ItemButton("<html>Black<br>Milk Tea</html>", 8, 12.50);
      ItemButton brownSugarMilkTea = new ItemButton("<html>Brown Sugar<br>Milk Tea</html>", 8, 12.5);
      ItemButton caramelMilkTea = new ItemButton("<html>Caramel<br>Milk Tea</html>", 8, 12.5);
      ItemButton earlGreyMilkTea = new ItemButton("<html>Earl Grey<br>Milk Tea</html>", 8, 12.5);
      ItemButton earlGreyMilkTea3Js = new ItemButton("<html>Earl Grey<br>Milk Tea<br>3Js</html>", 8, 12.5);
      ItemButton greenMilkTea = new ItemButton("<html>Green<br>Milk Tea</html>", 8, 12.5);
      ItemButton oolongMilkTea = new ItemButton("<html>Oolong<br>Milk Tea</html>", 8, 12.5);
      ItemButton pearlMilkTea = new ItemButton("<html>Pearl<br>Milk Tea</html>", 8, 12.5);
      ItemButton strawberryMilkTea = new ItemButton("<html>Strawberry<br>Milk Tea</html>", 8, 12.5);
      ItemButton wintermelonMilkTea = new ItemButton("<html>Wintermelon<br>Milk Tea</html>", 8, 12.5);

      ItemButton milkCoffee = new ItemButton("<html>Milk<br>Coffee</html>", 10, 16);
      ItemButton coffeeMilkTea = new ItemButton("<html>Coffee<br>Milk Tea</html>", 9, 19);
      ItemButton milkFoamBlackCoffee = new ItemButton("<html>Milk Foam<br>Black Coffee</html>", 9, 13);
      ItemButton taroMilkSlush = new ItemButton("<html>Taro<br>Milk Slush</html>", 10, 20);
      ItemButton strawberryMilkSlush = new ItemButton("<html>Strawberry<br>Milk Slush</html>", 10, 20);
      
      ActionListener actionListener = new ActionListener() {
        // if button is pressed
        public void actionPerformed(ActionEvent e)
        {
            JButton clickedButton = (JButton) e.getSource();
            String buttonName = clickedButton.getText();
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
            //if a drink button is pressed, add it to the receipt panel
            if (buttonName.equals("<html>Black<br>Milk Tea</html>")) { addItemToReceipt(blackMilkTea); }
            if (buttonName.equals("<html>Brown Sugar<br>Milk Tea</html>")) { addItemToReceipt(brownSugarMilkTea); }
            if (buttonName.equals("<html>Caramel<br>Milk Tea</html>")) { addItemToReceipt(caramelMilkTea); }
            if (buttonName.equals("<html>Earl Grey<br>Milk Tea</html>")) { addItemToReceipt(earlGreyMilkTea); }
            if (buttonName.equals("<html>Earl Grey<br>Milk Tea<br>3Js</html>")) { addItemToReceipt(earlGreyMilkTea3Js); }
            if (buttonName.equals("<html>Green<br>Milk Tea</html>")) { addItemToReceipt(greenMilkTea); }
            if (buttonName.equals("<html>Oolong<br>Milk Tea</html>")) { addItemToReceipt(oolongMilkTea); }
            if (buttonName.equals("<html>Pearl<br>Milk Tea</html>")) { addItemToReceipt(pearlMilkTea); }
            if (buttonName.equals("<html>Strawberry<br>Milk Tea</html>")) { addItemToReceipt(strawberryMilkTea); }
            if (buttonName.equals("<html>Wintermelon<br>Milk Tea</html>")) { addItemToReceipt(wintermelonMilkTea); }
            if (buttonName.equals("<html>Milk<br>Coffee</html>")) { addItemToReceipt(milkCoffee); }
            if (buttonName.equals("<html>Coffee<br>Milk Tea</html>")) { addItemToReceipt(coffeeMilkTea); }
            if (buttonName.equals("<html>Milk Foam<br>Black Coffee</html>")) { addItemToReceipt(milkFoamBlackCoffee); }
            if (buttonName.equals("<html>Taro<br>Milk Slush</html>")) { addItemToReceipt(taroMilkSlush); }
            if (buttonName.equals("<html>Strawberry<br>Milk Slush</html>")) { addItemToReceipt(strawberryMilkSlush); }

            if (s.equals("Checkout")) {
                //ask for tip
                String tipString = JOptionPane.showInputDialog("Enter tip amount: ");
                tip = Double.parseDouble(tipString);
                //update tip and total
                total = subtotal + tip;
                //update tip and total labels
                tipLabel.setText("Tip: $" + tip);
                totalLabel.setText("Total: $" + total);
                //display total in jdialog box and clear if yes is clicked
                int result = JOptionPane.showConfirmDialog(null, "Total: $" + total + "\n" + "Clear receipt?", "Checkout", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { 
                    //clear receipt
                    itemListPanel.removeAll();
                    //reset subtotal, tip, and total
                    subtotal = 0;
                    tip = 0;
                    total = 0;
                    //update subtotal, tip, and total labels
                    subtotalLabel.setText("Subtotal: $" + subtotal);
                    tipLabel.setText("Tip: $" + tip);
                    totalLabel.setText("Total: $" + total);
                    
                    //repaint receipt panel
                    itemListPanel.revalidate();
                    itemListPanel.repaint();
                }
            }
        }        
      };


      // add actionlistener to button
      exitButton.addActionListener(actionListener);
      cashierButton.addActionListener(actionListener);
      cashierBackButton.addActionListener(actionListener);
      managerButton.addActionListener(actionListener);
      managerBackButton.addActionListener(actionListener);

      blackMilkTea.addActionListener(actionListener);
      brownSugarMilkTea.addActionListener(actionListener);
      caramelMilkTea.addActionListener(actionListener);
      earlGreyMilkTea.addActionListener(actionListener);
      earlGreyMilkTea3Js.addActionListener(actionListener);
      greenMilkTea.addActionListener(actionListener);
      oolongMilkTea.addActionListener(actionListener);
      pearlMilkTea.addActionListener(actionListener);
      strawberryMilkTea.addActionListener(actionListener);
      wintermelonMilkTea.addActionListener(actionListener);
      
      milkCoffee.addActionListener(actionListener);
      coffeeMilkTea.addActionListener(actionListener);
      milkFoamBlackCoffee.addActionListener(actionListener);
      taroMilkSlush.addActionListener(actionListener);
      strawberryMilkSlush.addActionListener(actionListener);

      // add buttons to panels
      loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
      loginPanel.add(Box.createVerticalStrut(10));
      loginPanel.add(exitButton);
      loginPanel.add(Box.createVerticalStrut(10));
      loginPanel.add(cashierButton);
      loginPanel.add(Box.createVerticalStrut(10));
      loginPanel.add(managerButton);

      cashierPanel.add(cashierBackButton);


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
      CashierSlushiePanel.add(taroMilkSlush);
      CashierSlushiePanel.add(strawberryMilkSlush);

      //make cashiercoffeepanel
      JPanel CashierCoffeePanel = new JPanel();
      CashierCoffeePanel.add(milkCoffee);
      CashierCoffeePanel.add(coffeeMilkTea);
      CashierCoffeePanel.add(milkFoamBlackCoffee);

      tabbedPane.addTab("Milk Tea", null, milkteaholder, "Does nothing");
      tabbedPane.addTab("Slushie", null, CashierSlushiePanel, "Does nothing");
      tabbedPane.addTab("Coffee", null, CashierCoffeePanel, "Does nothing");
      //put pearlmilkTea in the milk tea tab
      tabbedPane.setPreferredSize(new Dimension(824, 768));
      //put the teas in the milk tea tab
      
      // add panel to frame
      loginFrame.add(loginPanel);
    
      //TODO: add cancel order button, same as checkout button just clears the receipt panel
      //TODO: add a button to remove an item from the receipt panel
      //TODO: add a button to edit an item in the receipt panel and add toppings
      JPanel receiptPanel = new JPanel();

      receiptPanel.setPreferredSize(new Dimension(200, 768));
      receiptPanel.setBackground(Color.WHITE);
      receiptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
      //add name of server and an exit button that clears the receipt panel(i.e. the current order)
      receiptPanel.add(new JLabel("Server: " + "John Doe"));
      receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      
      itemListPanel = new JPanel();
      itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
      JScrollPane itemScrollPane = new JScrollPane(itemListPanel);
      receiptPanel.add(itemScrollPane);
      JPanel receiptPanel2_bottom = new JPanel();

      receiptPanel2_bottom.setLayout(new BoxLayout(receiptPanel2_bottom, BoxLayout.Y_AXIS));
      // Create and add labels for Subtotal, Tip, Total
      subtotalLabel = new JLabel("Subtotal: $" + subtotal);
      tipLabel = new JLabel("Tip: $" + tip);
      totalLabel = new JLabel("Total: $" + total);
      receiptPanel2_bottom.add(subtotalLabel);
      receiptPanel2_bottom.add(tipLabel);
      receiptPanel2_bottom.add(totalLabel);

      // Create the Checkout button
      JButton checkoutButton = new JButton("Checkout");
      checkoutButton.addActionListener(actionListener);

      receiptPanel2_bottom.add(checkoutButton);

      // //i want to make sure the information on the order is always at the bottom of the receipt panel
      // receiptPanel.add(Box.createVerticalGlue());
    
      //put receiptPanel2_bottom at the bottom of the receipt panel
      receiptPanel.add(receiptPanel2_bottom,BorderLayout.SOUTH);

      cashierFrame.add(receiptPanel, BorderLayout.EAST);    
      cashierFrame.add(tabbedPane, BorderLayout.WEST);
      cashierFrame.add(cashierBackButton, BorderLayout.SOUTH);
      cashierFrame.add(cashierPanel);
      // cashierFrame.add(cashierDrinkPanel, BorderLayout.CENTER);

      
      managerFrame.add(managerBackButton, BorderLayout.SOUTH);

      managerFrame.add(managerPanel);
    //   managerFrame.add(managerDrinkPanel, BorderLayout.CENTER);

      // set the size of frame to be desktop
      loginFrame.setSize(320, 240);
      cashierFrame.setSize(1024, 768);
      managerFrame.setSize(1024, 768);

      // prevent resizability of the frames
      loginFrame.setResizable(false);
      cashierFrame.setResizable(false);
      managerFrame.setResizable(false);

      loginFrame.setVisible(true);
    }

    private void addItemToReceipt(ItemButton itemButton) {
      // Get item details from the button
      //ask user if they want the medium price or large price
      
      String itemName = itemButton.getItemName();
      double itemPrice;

      itemName = itemName.replace("<html>", "");
      itemName = itemName.replace("<br>", " ");
      itemName = itemName.replace("</html>", "");

      Object[] options = {"Medium", "Large"};
      int result = JOptionPane.showOptionDialog(
              null,
              "Would you like the medium or large size?",
              "Size",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[0]
      );
  
      if (result == 0) {
          itemPrice = itemButton.getMediumPrice();
      } 
      else {
          itemPrice = itemButton.getLargePrice();
      }

      // Create components for the new item
      JLabel itemLabel = new JLabel(itemName);
      JLabel priceLabel = new JLabel("    $" + itemPrice);

      // Add the item to the item list panel
      //TODO: add a button to remove an item from the receipt panel
      //style button to make it small and in same row as item and price labels
      JButton removeItemButton = new JButton("Remove");
      removeItemButton.setPreferredSize(new Dimension(100, 20));
        removeItemButton.setHorizontalAlignment(SwingConstants.CENTER);
        removeItemButton.setVerticalAlignment(SwingConstants.CENTER);
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemListPanel.remove(itemLabel);
                itemListPanel.remove(priceLabel);
                itemListPanel.remove(removeItemButton);
                itemListPanel.revalidate();
                itemListPanel.repaint();
            }
        });
    itemListPanel.add(removeItemButton);
    //create button to add toppings
    //should be a dropdown menu with checkboxes
    JButton editItemButton = new JButton("Edit");
    editItemButton.setPreferredSize(new Dimension(100, 20));
    //TODO: add action listener for editItemButton

      itemListPanel.add(editItemButton);
      itemListPanel.add(itemLabel);
      itemListPanel.add(priceLabel);

      // Update the running total
      subtotal += itemPrice;
      total = subtotal + tip;

      // Update the labels in receiptPanel2
      subtotalLabel.setText("Subtotal: $" + subtotal);
      tipLabel.setText("Tip: $" + tip);
      totalLabel.setText("Total: $" + total);

      // Repaint the item list panel
      itemListPanel.revalidate();
      itemListPanel.repaint();
  }
}

class ItemButton extends JButton {
    private String itemName;
    private double mediumPrice;
    private double largePrice;

    public ItemButton(String itemName, double mediumPrice, double largePrice) {
        super(itemName);
        this.itemName = itemName;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;

        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.GREEN);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public String getItemName() {
        return itemName;
    }

    public double getMediumPrice() {
        return mediumPrice;
    }

    public double getLargePrice() {
        return largePrice;
    }
}



