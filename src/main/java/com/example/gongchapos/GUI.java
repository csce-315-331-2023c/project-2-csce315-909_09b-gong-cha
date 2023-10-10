package com.example.gongchapos;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.example.gongchapos.Application;

//create class for the POS system cashier end


public class GUI extends JFrame {
    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    //stuff for the cashier frame
    private JPanel itemListPanel; // Panel to hold item labels
    private JPanel managerItemListPanel;
    private double subtotal;
    private double tip;
    private double total;
    private JLabel subtotalLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;
    private JLabel managerSubtotalLabel;
    private JLabel managerTipLabel;
    private JLabel managerTotalLabel;

    protected Application app = null;

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

    public static JPanel createReceiptPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 768));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //add name of server and an exit button that clears the receipt panel(i.e. the current order)
        panel.add(new JLabel("Server: " + "John Doe"));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return panel;
    }

    public void launchGUI()
    {
      JOptionPane.showMessageDialog(null,"Opened database successfully");

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

      ItemButton blackMilkTea= new ItemButton(1, this);
      ItemButton brownSugarMilkTea = new ItemButton(2, this);
      ItemButton caramelMilkTea = new ItemButton(3, this);
      ItemButton earlGreyMilkTea = new ItemButton(4, this);
      ItemButton earlGreyMilkTea3Js = new ItemButton(5, this);
      ItemButton greenMilkTea = new ItemButton(6, this);
      ItemButton oolongMilkTea = new ItemButton(7, this);
      ItemButton pearlMilkTea = new ItemButton(8, this);
      ItemButton strawberryMilkTea = new ItemButton(9, this);
      ItemButton wintermelonMilkTea = new ItemButton(10, this);

      ItemButton milkCoffee = new ItemButton(24, this);
      ItemButton coffeeMilkTea = new ItemButton(25, this);
      ItemButton milkFoamBlackCoffee = new ItemButton(26, this);
      ItemButton taroMilkSlush = new ItemButton(49, this);
      ItemButton strawberryMilkSlush = new ItemButton(48, this);
      
      // These variables are declared so we can access their values, part of adding a new drink in manager
      JTextField drinkID = new JTextField();
      JTextField drinkName = new JTextField();
      JTextField mediumPrice = new JTextField();
      JTextField largePrice = new JTextField();
      JTextField recipePrice = new JTextField();
      JTextArea ingredients = new JTextArea();
      JTextArea toppings = new JTextArea();
      ButtonGroup slushieOptions = new ButtonGroup();
      JRadioButton option1 = new JRadioButton("Slushy");
      JRadioButton option2 = new JRadioButton("Not Slushy");
      ButtonGroup drinkType = new ButtonGroup();
      JRadioButton milkTea = new JRadioButton("Milk Tea");
      JRadioButton slushie = new JRadioButton("Slushie");
      JRadioButton coffee = new JRadioButton("Coffee");
      JRadioButton other = new JRadioButton("Other");

    JButton checkoutButton = new JButton("Checkout");
    JButton managerCheckoutButton = new JButton("Checkout");

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
                System.exit(0);
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
            if (buttonName.equals("<html>Black Milk Tea</html>")) { addItemToReceipt(blackMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Brown Sugar Milk Tea</html>")) { addItemToReceipt(brownSugarMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Caramel Milk Tea</html>")) { addItemToReceipt(caramelMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Earl Grey Milk Tea</html>")) { addItemToReceipt(earlGreyMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Earl Grey Milk Tea 3Js</html>")) { addItemToReceipt(earlGreyMilkTea3Js, itemListPanel); }
            if (buttonName.equals("<html>Green Milk Tea</html>")) { addItemToReceipt(greenMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Oolong Milk Tea</html>")) { addItemToReceipt(oolongMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Pearl Milk Tea</html>")) { addItemToReceipt(pearlMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Strawberry Milk Tea</html>")) { addItemToReceipt(strawberryMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Wintermelon Milk Tea</html>")) { addItemToReceipt(wintermelonMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Milk Coffee</html>")) { addItemToReceipt(milkCoffee, itemListPanel); }
            if (buttonName.equals("<html>Coffee Milk Tea</html>")) { addItemToReceipt(coffeeMilkTea, itemListPanel); }
            if (buttonName.equals("<html>Milk Foam Black Coffee</html>")) { addItemToReceipt(milkFoamBlackCoffee, itemListPanel); }
            if (buttonName.equals("<html>Taro Milk Slush</html>")) { addItemToReceipt(taroMilkSlush, itemListPanel); }
            if (buttonName.equals("<html>Strawberry Milk Slush</html>")) { addItemToReceipt(strawberryMilkSlush, itemListPanel); }

            if (clickedButton == checkoutButton) {
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
            if (clickedButton == managerCheckoutButton) {
                //ask for tip
                String tipString = JOptionPane.showInputDialog("Enter tip amount: ");
                tip = Double.parseDouble(tipString);
                //update tip and total
                total = subtotal + tip;
                //update tip and total labels
                managerTipLabel.setText("Tip: $" + tip);
                managerTotalLabel.setText("Total: $" + total);
                //display total in jdialog box and clear if yes is clicked
                int result = JOptionPane.showConfirmDialog(null, "Total: $" + total + "\n" + "Clear receipt?", "Checkout", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { 
                    //clear receipt
                    managerItemListPanel.removeAll();
                    //reset subtotal, tip, and total
                    subtotal = 0;
                    tip = 0;
                    total = 0;
                    //update subtotal, tip, and total labels
                    managerSubtotalLabel.setText("Subtotal: $" + subtotal);
                    managerTipLabel.setText("Tip: $" + tip);
                    managerTotalLabel.setText("Total: $" + total);
                    
                    //repaint receipt panel
                    managerItemListPanel.revalidate();
                    managerItemListPanel.repaint();
                }
            }

            if (s.equals("Add New Drink")) {
                String newDrinkID = drinkID.getText();
                String newDrinkName = drinkName.getText();
                String requestedIngredients = ingredients.getText();
                String requestedToppings = toppings.getText();
                String newMediumPrice = mediumPrice.getText();
                String newLargePrice = largePrice.getText();
                String newRecipePrice = recipePrice.getText();

                String[] newIngredients = requestedIngredients.split(",");
                String[] newToppings = requestedToppings.split(",");
                ArrayList<String> ingredientsArray = new ArrayList<>();
                ArrayList<String> toppingsArray = new ArrayList<>();

                for (String ingredient : newIngredients) {
                    ingredientsArray.add(ingredient);
                }
                for (String topping : newToppings) {
                    toppingsArray.add(topping);
                }

                boolean isSlush = option1.isSelected();

                System.out.print(newDrinkName);

                // Basic error handling cases
                if (newDrinkID.equals("")) { 
                    JOptionPane.showMessageDialog(null, "No ID provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (newDrinkName.equals("")) {
                    JOptionPane.showMessageDialog(null, "No name provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (requestedIngredients.equals("")) {
                    JOptionPane.showMessageDialog(null, "No ingredients provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (slushieOptions.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "Slushie/Not Slushie not selected", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (newMediumPrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No medium price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (newLargePrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No large price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (newRecipePrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No recipe price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (drinkType.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "No drink type selected", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                // TODO:
                // CREATE SQL QUERY TO ADD DRINK INFO TO DATABASE
                JOptionPane.showMessageDialog(null, "Drink added successfully, restart application to use button", "Success", JOptionPane.INFORMATION_MESSAGE);
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


      JTabbedPane cashierTabbedPane = new JTabbedPane();
      JTabbedPane managerTabbedPane = new JTabbedPane();
      
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

      //make cashierotherpanel
      JPanel CashierOtherPanel = new JPanel();

      JPanel managerSlushiePanel = new JPanel();
      JPanel managerCoffeePanel = new JPanel();
      JPanel managerOtherPanel = new JPanel();
      JPanel managerMilkTeaPanel = new JPanel();
      JPanel managerInventoryPanel = new JPanel();

      //make manageractionspanel
      JPanel managerActionsPanel = new JPanel();
      managerActionsPanel.setLayout(new BoxLayout(managerActionsPanel, BoxLayout.Y_AXIS));
      managerActionsPanel.add(Box.createVerticalStrut(10));
      // declare necessary sections to add a drink
      JLabel drinkIDLabel = new JLabel("Drink ID: ");
      JLabel nameLabel = new JLabel("Drink name: ");
      JLabel ingredientsLabel = new JLabel("Ingredients (separated by ','): ");
      JLabel toppingsLabel = new JLabel("Toppings (separated by ','): ");
      JLabel mediumLabel = new JLabel("Medium Price: ");
      JLabel largeLabel = new JLabel("Large Price: ");
      JLabel recipeLabel = new JLabel("Recipe Price: ");

      // Set alignment
      drinkIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkID.setAlignmentX(Component.LEFT_ALIGNMENT);
      nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkName.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredients.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppings.setAlignmentX(Component.LEFT_ALIGNMENT);
      option1.setAlignmentX(Component.LEFT_ALIGNMENT);
      option2.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
      largeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      largePrice.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipePrice.setAlignmentX(Component.LEFT_ALIGNMENT);

      // Set dimensions
      drinkID.setMinimumSize(new Dimension(200, 20));
      drinkID.setMaximumSize(new Dimension(200, 20));
      drinkName.setMinimumSize(new Dimension(200, 20));
      drinkName.setMaximumSize(new Dimension(200, 20));
      ingredients.setMinimumSize(new Dimension(200, 100));
      ingredients.setMaximumSize(new Dimension(200, 100));
      mediumPrice.setMinimumSize(new Dimension(200, 20));
      mediumPrice.setMaximumSize(new Dimension(200, 20));
      largePrice.setMinimumSize(new Dimension(200, 20));
      largePrice.setMaximumSize(new Dimension(200, 20));
      recipePrice.setMinimumSize(new Dimension(200, 20));
      recipePrice.setMaximumSize(new Dimension(200, 20));
      toppings.setMinimumSize(new Dimension(200, 100));
      toppings.setMaximumSize(new Dimension(200, 100));
      
      // Add the objects in the correct order
      managerActionsPanel.add(drinkIDLabel);
      managerActionsPanel.add(drinkID);
      managerActionsPanel.add(nameLabel);
      managerActionsPanel.add(drinkName);
      managerActionsPanel.add(ingredientsLabel);
      managerActionsPanel.add(ingredients);
      managerActionsPanel.add(toppingsLabel);
      managerActionsPanel.add(toppings);
      slushieOptions.add(option1);
      slushieOptions.add(option2);
      managerActionsPanel.add(option1);
      managerActionsPanel.add(option2);
      managerActionsPanel.add(mediumLabel);
      managerActionsPanel.add(mediumPrice);
      managerActionsPanel.add(largeLabel);
      managerActionsPanel.add(largePrice);
      managerActionsPanel.add(recipeLabel);
      managerActionsPanel.add(recipePrice);
      drinkType.add(milkTea);
      drinkType.add(slushie);
      drinkType.add(coffee);
      drinkType.add(other);
      managerActionsPanel.add(milkTea);
      managerActionsPanel.add(slushie);
      managerActionsPanel.add(coffee);
      managerActionsPanel.add(other);

      // Create the new drink button
      JButton newDrinkButton = new JButton("Add New Drink");
      newDrinkButton.addActionListener(actionListener);
      managerActionsPanel.add(newDrinkButton);

      managerTabbedPane.addTab("Milk Tea", null, managerMilkTeaPanel, "Does nothing");
      managerTabbedPane.addTab("Slushie", null, managerSlushiePanel, "Does nothing");
      managerTabbedPane.addTab("Coffee", null, managerCoffeePanel, "Does nothing");
      managerTabbedPane.addTab("Other", null, managerOtherPanel, "Does nothing");
      managerTabbedPane.addTab("Manager", null, managerActionsPanel, "Does nothing");
      managerTabbedPane.addTab("Inventory", null, managerInventoryPanel, "Does nothing");
      
      cashierTabbedPane.addTab("Milk Tea", null, milkteaholder, "Does nothing");
      cashierTabbedPane.addTab("Slushie", null, CashierSlushiePanel, "Does nothing");
      cashierTabbedPane.addTab("Coffee", null, CashierCoffeePanel, "Does nothing");
      cashierTabbedPane.addTab("Other", null, CashierOtherPanel, "Does nothing");
      //put pearlmilkTea in the milk tea tab
      cashierTabbedPane.setPreferredSize(new Dimension(824, 768));
      managerTabbedPane.setPreferredSize(new Dimension(824, 768));
      //put the teas in the milk tea tab
      
      // add panel to frame
      loginFrame.add(loginPanel);
    
      JPanel receiptPanel = createReceiptPanel();
      JPanel managerReceiptPanel = createReceiptPanel();

    //   receiptPanel.setPreferredSize(new Dimension(200, 768));
    //   receiptPanel.setBackground(Color.WHITE);
    //   receiptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //   receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
    //   //add name of server and an exit button that clears the receipt panel(i.e. the current order)
    //   receiptPanel.add(new JLabel("Server: " + "John Doe"));
    //   receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      
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

      // Repeat for manager side
      managerItemListPanel = new JPanel();
      managerItemListPanel.setLayout(new BoxLayout(managerItemListPanel, BoxLayout.Y_AXIS));
      JScrollPane managerItemScrollPane = new JScrollPane(managerItemListPanel);
      managerReceiptPanel.add(managerItemScrollPane);
      JPanel managerReceiptPanel2_bottom = new JPanel();

      managerReceiptPanel2_bottom.setLayout(new BoxLayout(managerReceiptPanel2_bottom, BoxLayout.Y_AXIS));
      managerSubtotalLabel = new JLabel("Subtotal: $" + subtotal);
      managerTipLabel = new JLabel("Tip: $" + tip);
      managerTotalLabel = new JLabel("Total: $" + total);

      managerReceiptPanel2_bottom.add(managerSubtotalLabel);
      managerReceiptPanel2_bottom.add(managerTipLabel);
      managerReceiptPanel2_bottom.add(managerTotalLabel);

      // Add the checkout buttons
      checkoutButton.addActionListener(actionListener);
      managerCheckoutButton.addActionListener(actionListener);

      receiptPanel2_bottom.add(checkoutButton);
      managerReceiptPanel2_bottom.add(managerCheckoutButton);

      // //i want to make sure the information on the order is always at the bottom of the receipt panel
      // receiptPanel.add(Box.createVerticalGlue());
    
      //put receiptPanel2_bottom at the bottom of the receipt panel
      receiptPanel.add(receiptPanel2_bottom,BorderLayout.SOUTH);
      managerReceiptPanel.add(managerReceiptPanel2_bottom,BorderLayout.SOUTH);

      cashierFrame.add(receiptPanel, BorderLayout.EAST);    
      cashierFrame.add(cashierTabbedPane, BorderLayout.WEST);
      cashierFrame.add(cashierBackButton, BorderLayout.SOUTH);
      cashierFrame.add(cashierPanel);
      // cashierFrame.add(cashierDrinkPanel, BorderLayout.CENTER);

      managerFrame.add(managerReceiptPanel, BorderLayout.EAST);
      managerFrame.add(managerTabbedPane, BorderLayout.WEST);
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

    private void addItemToReceipt(ItemButton itemButton, JPanel panel) {
      // Get item details from the button
      //ask user if they want the medium price or large price
      String itemName = itemButton.getItemName();
      double itemPrice;

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
      panel.add(itemLabel);
      panel.add(priceLabel);

      // Update the running total
      subtotal += itemPrice;
      total = subtotal + tip;

      // Update the labels in receiptPanel2
      subtotalLabel.setText("Subtotal: $" + subtotal);
      tipLabel.setText("Tip: $" + tip);
      totalLabel.setText("Total: $" + total);

      // Repaint the item list panel
      panel.revalidate();
      panel.repaint();
  }
}

class ItemButton extends JButton {
    private Recipe recipe;

    public ItemButton(int recipe_id, GUI gui) {
        super("<html>" + gui.app.getRecipe(recipe_id).getRecipeName() + "</html>");
        recipe = gui.app.getRecipe(recipe_id);
 
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.GREEN);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    public String getItemName() {
        return recipe.getRecipeName();
    }

    public double getMediumPrice() {
        return recipe.getMediumPrice();
    }

    public double getLargePrice() {
        return recipe.getLargePrice();
    }
}



