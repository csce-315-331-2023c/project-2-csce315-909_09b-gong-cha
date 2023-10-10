package com.example.gongchapos;

import java.util.*;
import java.util.List;
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
    private double subtotal;
    private double tip;
    private double total;
    private JLabel subtotalLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;
    private JLabel managerSubtotalLabel;
    private JLabel managerTipLabel;
    private JLabel managerTotalLabel;

    //making private labels for tabbed pane
    JPanel CashierMilkTeaPanel;
    JPanel CashierSlushiePanel;
    JPanel CashierCoffeePanel;
    JPanel CashierOtherPanel;

    //manager chart
    JPanel managerChartPanel;
    

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
      /*
      The manager portal must be able to "create a product usage chart over a given time window".
      This chart is intended to display the amount of each inventory item / ingredient used  */

        //Tasks:
    //1. **managers to view, add, and update the items and prices**
    //2. **allow managers to view and update their inventory**.
    //3. "create a product usage chart over a given time window".
        //This chart is intended to display the amount of each inventory item / ingredient used 

    //TODO: await response from backend to get back an Object[][] data and a String[] columnNames to populate the table
    //CRUD operations for the inventory, Create, Read, Update, Delete
    //Create CreateInventoryItem function that sends info to backend to add a new item to the inventory
    //Read ReadInventoryItem function that sends info to backend to get info about an item in the inventory
    //Update UpdateInventoryItem function that sends info to backend to update info about an item in the inventory
    //Delete DeleteInventoryItem function that sends info to backend to delete an item from the inventory


    //requires a function to populate the panel with the inventory items, I want this to be in table form
      JButton exitButton = new JButton("Exit");
      exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierButton = new JButton("Cashier");
      cashierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierBackButton = new JButton("Back");
      JButton managerButton = new JButton("Manager");
      managerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton managerBackButton = new JButton("Back");

    //   ItemButton blackMilkTea= new ItemButton(1, this);
    //   ItemButton brownSugarMilkTea = new ItemButton(2, this);
    //   ItemButton caramelMilkTea = new ItemButton(3, this);
    //   ItemButton earlGreyMilkTea = new ItemButton(4, this);
    //   ItemButton earlGreyMilkTea3Js = new ItemButton(5, this);
    //   ItemButton greenMilkTea = new ItemButton(6, this);
    //   ItemButton oolongMilkTea = new ItemButton(7, this);
    //   ItemButton pearlMilkTea = new ItemButton(8, this);
    //   ItemButton strawberryMilkTea = new ItemButton(9, this);
    //   ItemButton wintermelonMilkTea = new ItemButton(10, this);

    //   ItemButton milkCoffee = new ItemButton(24, this);
    //   ItemButton coffeeMilkTea = new ItemButton(25, this);
    //   ItemButton milkFoamBlackCoffee = new ItemButton(26, this);
    //   ItemButton taroMilkSlush = new ItemButton(49, this);
    //   ItemButton strawberryMilkSlush = new ItemButton(48, this);
      
      // These variables are declared so we can access their values, part of adding a new drink in manager
      JTextField drinkID = new JTextField();
      JTextField drinkName = new JTextField();
      JTextField mediumPrice = new JTextField();
      JTextField largePrice = new JTextField();
      JTextField recipePrice = new JTextField();
      JTextField ingredientsQuantity = new JTextField();
      JTextField toppingsQuantity = new JTextField();
      JTextField ingredients = new JTextField();
      JTextField toppings = new JTextField();
      ButtonGroup slushieOptions = new ButtonGroup();
      JRadioButton isSlushy = new JRadioButton("Slushy");
      JRadioButton isNotSlushy = new JRadioButton("Not Slushy");
      ButtonGroup drinkType = new ButtonGroup();
      JRadioButton milkTea = new JRadioButton("Milk Tea");
      JRadioButton slushie = new JRadioButton("Slushie");
      JRadioButton coffee = new JRadioButton("Coffee");
      JRadioButton other = new JRadioButton("Other");
      // Repeat to add to modify drink panel
      JTextField drinkName2 = new JTextField();
      JTextField mediumPrice2 = new JTextField();
      JTextField largePrice2 = new JTextField();
      JTextField recipePrice2 = new JTextField();
      JTextField ingredientsQuantity2 = new JTextField();
      JTextField toppingsQuantity2 = new JTextField();
      JTextField ingredients2 = new JTextField();
      JTextField toppings2 = new JTextField();
      ButtonGroup slushieOptions2 = new ButtonGroup();
      JRadioButton isSlushy2 = new JRadioButton("Slushy");
      JRadioButton isNotSlushy2 = new JRadioButton("Not Slushy");
      ButtonGroup drinkType2 = new ButtonGroup();
      JRadioButton milkTea2 = new JRadioButton("Milk Tea");
      JRadioButton slushie2 = new JRadioButton("Slushie");
      JRadioButton coffee2 = new JRadioButton("Coffee");
      JRadioButton other2 = new JRadioButton("Other");

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
            //if an itembutton is pressed, call addItemToReceipt
            // if (buttonName.equals("<html>Black Milk Tea</html>")) { addItemToReceipt(blackMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Brown Sugar Milk Tea</html>")) { addItemToReceipt(brownSugarMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Caramel Milk Tea</html>")) { addItemToReceipt(caramelMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Earl Grey Milk Tea</html>")) { addItemToReceipt(earlGreyMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Earl Grey Milk Tea 3Js</html>")) { addItemToReceipt(earlGreyMilkTea3Js, itemListPanel); }
            // if (buttonName.equals("<html>Green Milk Tea</html>")) { addItemToReceipt(greenMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Oolong Milk Tea</html>")) { addItemToReceipt(oolongMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Pearl Milk Tea</html>")) { addItemToReceipt(pearlMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Strawberry Milk Tea</html>")) { addItemToReceipt(strawberryMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Wintermelon Milk Tea</html>")) { addItemToReceipt(wintermelonMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Milk Coffee</html>")) { addItemToReceipt(milkCoffee, itemListPanel); }
            // if (buttonName.equals("<html>Coffee Milk Tea</html>")) { addItemToReceipt(coffeeMilkTea, itemListPanel); }
            // if (buttonName.equals("<html>Milk Foam Black Coffee</html>")) { addItemToReceipt(milkFoamBlackCoffee, itemListPanel); }
            // if (buttonName.equals("<html>Taro Milk Slush</html>")) { addItemToReceipt(taroMilkSlush, itemListPanel); }
            // if (buttonName.equals("<html>Strawberry Milk Slush</html>")) { addItemToReceipt(strawberryMilkSlush, itemListPanel); }
            // //check to see if the clicked button is a drink button

            if (clickedButton instanceof ItemButton) {
                //call the function to add the drink to the receipt and cast to clicked button
                addItemToReceipt((ItemButton) clickedButton, itemListPanel);
            }

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
                //TODO: send data to backend to update inventory
                
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

            if (s.equals("Add New Drink")) {
                // String newDrinkID = drinkID.getText();
                String newDrinkName = drinkName.getText();
                String requestedIngredients = ingredients.getText();
                String requestedIngredientsQuantity = ingredientsQuantity.getText();
                String requestedToppings = toppings.getText();
                String requestedToppingsQuantity = toppingsQuantity.getText();
                String newMediumPrice = mediumPrice.getText();
                String newLargePrice = largePrice.getText();
                String newRecipePrice = recipePrice.getText();

                String[] newIngredients = requestedIngredients.split(",");
                String[] newIngredientsQuantity = requestedIngredientsQuantity.split(",");
                String[] newToppings = requestedToppings.split(",");
                String[] newToppingsQuantity = requestedToppingsQuantity.split(",");
                ArrayList<String> ingredientsArray = new ArrayList<>();
                ArrayList<String> ingredientsQuantityArray = new ArrayList<>();
                ArrayList<String> toppingsArray = new ArrayList<>();
                ArrayList<String> toppingsQuantityArray = new ArrayList<>();

                for (String ingredient : newIngredients) {
                    ingredientsArray.add(ingredient);
                }
                for (String topping : newToppings) {
                    toppingsArray.add(topping);
                }
                for (String quantity: newIngredientsQuantity) {
                    ingredientsQuantityArray.add(quantity);
                }
                for (String quantity: newToppingsQuantity) {
                    toppingsQuantityArray.add(quantity);
                }

                boolean isSlush = isSlushy.isSelected();

                if (newDrinkName.equals("")) {
                    JOptionPane.showMessageDialog(null, "No name provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (requestedIngredients.equals("")) {
                    JOptionPane.showMessageDialog(null, "No ingredients provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (requestedIngredientsQuantity.equals("")) {
                    JOptionPane.showMessageDialog(null, "No ingredient quantities provided", "Error", JOptionPane.ERROR_MESSAGE); 
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

                if (!requestedToppings.equals("")) {
                    if (requestedToppingsQuantity.equals("")) {
                        JOptionPane.showMessageDialog(null, "No topping quantities provided", "Error", JOptionPane.ERROR_MESSAGE); 
                        return;
                    }
                }
                // TODO:
                // CREATE SQL QUERY TO ADD DRINK INFO TO DATABASE
                JOptionPane.showMessageDialog(null, "Drink added successfully, restart application to use button", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            if (s.equals("Modify Drink")) {
                String drinkToModify = drinkID.getText();
                String modifiedDrinkName = drinkName2.getText();
                String requestedIngredients = ingredients2.getText();
                String requestedIngredientsQuantity = ingredientsQuantity2.getText();
                String requestedToppings = toppings2.getText();
                String requestedToppingsQuantity = toppingsQuantity2.getText();
                String modifiedMediumPrice = mediumPrice2.getText();
                String modifiedLargePrice = largePrice2.getText();
                String modifiedRecipePrice = recipePrice2.getText();

                String[] modifiedIngredients = requestedIngredients.split(",");
                String[] modifiedIngredientsQuantity = requestedIngredientsQuantity.split(",");
                String[] modifiedToppings = requestedToppings.split(",");
                String[] modifiedToppingsQuantity = requestedToppingsQuantity.split(",");
                ArrayList<String> ingredientsArray = new ArrayList<>();
                ArrayList<String> ingredientsQuantityArray = new ArrayList<>();
                ArrayList<String> toppingsArray = new ArrayList<>();
                ArrayList<String> toppingsQuantityArray = new ArrayList<>();

                for (String ingredient : modifiedIngredients) {
                    ingredientsArray.add(ingredient);
                }
                for (String topping : modifiedToppings) {
                    toppingsArray.add(topping);
                }
                for (String quantity: modifiedIngredientsQuantity) {
                    ingredientsQuantityArray.add(quantity);
                }
                for (String quantity: modifiedToppingsQuantity) {
                    toppingsQuantityArray.add(quantity);
                }

                boolean isSlush = isSlushy.isSelected();

                if (drinkToModify.equals("")) {
                    JOptionPane.showMessageDialog(null, "No drink ID provided", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (modifiedDrinkName.equals("")) {
                    JOptionPane.showMessageDialog(null, "No name provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (requestedIngredients.equals("")) {
                    JOptionPane.showMessageDialog(null, "No ingredients provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (requestedIngredientsQuantity.equals("")) {
                    JOptionPane.showMessageDialog(null, "No ingredient quantities provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (slushieOptions2.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "Slushie/Not Slushie not selected", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (modifiedMediumPrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No medium price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (modifiedLargePrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No large price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (modifiedRecipePrice.equals("")) {
                    JOptionPane.showMessageDialog(null, "No recipe price provided", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                else if (drinkType2.getSelection() == null) {
                    JOptionPane.showMessageDialog(null, "No drink type selected", "Error", JOptionPane.ERROR_MESSAGE); 
                    return;
                }

                if (!requestedToppings.equals("")) {
                    if (requestedToppingsQuantity.equals("")) {
                        JOptionPane.showMessageDialog(null, "No topping quantities provided", "Error", JOptionPane.ERROR_MESSAGE); 
                        return;
                    }
                }
                // TODO:
                // CREATE SQL QUERY TO ADD DRINK INFO TO DATABASE
                JOptionPane.showMessageDialog(null, "Drink modified successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }        
      };



      // add actionlistener to button
      exitButton.addActionListener(actionListener);
      cashierButton.addActionListener(actionListener);
      cashierBackButton.addActionListener(actionListener);
      managerButton.addActionListener(actionListener);
      managerBackButton.addActionListener(actionListener);

    //   blackMilkTea.addActionListener(actionListener);
    //   brownSugarMilkTea.addActionListener(actionListener);
    //   caramelMilkTea.addActionListener(actionListener);
    //   earlGreyMilkTea.addActionListener(actionListener);
    //   earlGreyMilkTea3Js.addActionListener(actionListener);
    //   greenMilkTea.addActionListener(actionListener);
    //   oolongMilkTea.addActionListener(actionListener);
    //   pearlMilkTea.addActionListener(actionListener);
    //   strawberryMilkTea.addActionListener(actionListener);
    //   wintermelonMilkTea.addActionListener(actionListener);
      
    //   milkCoffee.addActionListener(actionListener);
    //   coffeeMilkTea.addActionListener(actionListener);
    //   milkFoamBlackCoffee.addActionListener(actionListener);
    //   taroMilkSlush.addActionListener(actionListener);
    //   strawberryMilkSlush.addActionListener(actionListener);

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
      
      CashierMilkTeaPanel = new JPanel();
    //   CashierMilkTeaPanel.add(blackMilkTea);
    //   CashierMilkTeaPanel.add(brownSugarMilkTea);
    //   CashierMilkTeaPanel.add(caramelMilkTea);
    //   CashierMilkTeaPanel.add(earlGreyMilkTea);
    //   CashierMilkTeaPanel.add(earlGreyMilkTea3Js);
    //   CashierMilkTeaPanel.add(greenMilkTea);
    //   CashierMilkTeaPanel.add(oolongMilkTea);
    //   CashierMilkTeaPanel.add(pearlMilkTea);
    //   CashierMilkTeaPanel.add(strawberryMilkTea);
    //   CashierMilkTeaPanel.add(wintermelonMilkTea);

    //   //make cashierslushiepanel
      CashierSlushiePanel = new JPanel();
    //   CashierSlushiePanel.add(taroMilkSlush);
    //   CashierSlushiePanel.add(strawberryMilkSlush);

    //   //make cashiercoffeepanel
      CashierCoffeePanel = new JPanel();
    //   CashierCoffeePanel.add(milkCoffee);
    //   CashierCoffeePanel.add(coffeeMilkTea);
    //   CashierCoffeePanel.add(milkFoamBlackCoffee);

      //make cashierotherpanel
      CashierOtherPanel = new JPanel();
      JPanel managerInventoryPanel = new JPanel();
      JPanel changeDrinkPanel = new JPanel();
      JPanel addDrinkPanel = new JPanel();
      //make manageractionspanel
      JPanel managerActionsPanel = new JPanel();
      addDrinkPanel.setLayout(new BoxLayout(addDrinkPanel, BoxLayout.Y_AXIS));
      addDrinkPanel.add(Box.createVerticalStrut(10));
      changeDrinkPanel.setLayout(new BoxLayout(changeDrinkPanel, BoxLayout.Y_AXIS));
      changeDrinkPanel.add(Box.createVerticalStrut(47));
      // declare necessary sections to add a drink
      JLabel addDrinkLabel = new JLabel("ADD DRINK: ");
      JLabel nameLabel = new JLabel("Drink name: ");
      JLabel ingredientsLabel = new JLabel("Ingredients (separated by ','): ");
      JLabel ingredientsQuantityLabel = new JLabel("Quantity of Ingredients (in same order, separated by ','): ");
      JLabel toppingsLabel = new JLabel("Toppings (separated by ','): ");
      JLabel toppingsQuantityLabel = new JLabel("Quantity of Toppings (in same order, separated by ','): ");
      JLabel mediumLabel = new JLabel("Medium Price: ");
      JLabel largeLabel = new JLabel("Large Price: ");
      JLabel recipeLabel = new JLabel("Recipe Price: ");

      // declare necessary sections to modify a drink
      JLabel modifyDrinkLabel = new JLabel("MODIFY DRINK: ");
      JLabel drinkIDLabel = new JLabel("ID of drink to modify: ");
      JLabel nameLabel2 = new JLabel("Drink name: ");
      JLabel ingredientsLabel2 = new JLabel("Ingredients (separated by ','): ");
      JLabel ingredientsQuantityLabel2 = new JLabel("Quantity of Ingredients (in same order, separated by ','): ");
      JLabel toppingsLabel2 = new JLabel("Toppings (separated by ','): ");
      JLabel toppingsQuantityLabel2 = new JLabel("Quantity of Toppings (in same order, separated by ','): ");
      JLabel mediumLabel2 = new JLabel("Medium Price: ");
      JLabel largeLabel2 = new JLabel("Large Price: ");
      JLabel recipeLabel2 = new JLabel("Recipe Price: ");


      // Add Drink alignment
      addDrinkLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkID.setAlignmentX(Component.LEFT_ALIGNMENT);
      nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkName.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredients.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsQuantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsQuantity.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppings.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsQuantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsQuantity.setAlignmentX(Component.LEFT_ALIGNMENT);
      isSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);
      isNotSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
      largeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      largePrice.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipePrice.setAlignmentX(Component.LEFT_ALIGNMENT);

      // Modify Drink alignment
      modifyDrinkLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      nameLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      drinkName2.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredients2.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsQuantityLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      ingredientsQuantity2.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppings2.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsQuantityLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      toppingsQuantity2.setAlignmentX(Component.LEFT_ALIGNMENT);
      isSlushy2.setAlignmentX(Component.LEFT_ALIGNMENT);
      isNotSlushy2.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      mediumPrice2.setAlignmentX(Component.LEFT_ALIGNMENT);
      largeLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      largePrice2.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipeLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
      recipePrice2.setAlignmentX(Component.LEFT_ALIGNMENT);

      // Set dimensions
      drinkID.setMinimumSize(new Dimension(200, 20));
      drinkID.setMaximumSize(new Dimension(200, 20));
      drinkName.setMinimumSize(new Dimension(200, 20));
      drinkName.setMaximumSize(new Dimension(200, 20));
      ingredients.setMinimumSize(new Dimension(200, 100));
      ingredients.setMaximumSize(new Dimension(200, 100));
      ingredientsQuantity.setMinimumSize(new Dimension(200, 20));
      ingredientsQuantity.setMaximumSize(new Dimension(200, 20));
      mediumPrice.setMinimumSize(new Dimension(200, 20));
      mediumPrice.setMaximumSize(new Dimension(200, 20));
      largePrice.setMinimumSize(new Dimension(200, 20));
      largePrice.setMaximumSize(new Dimension(200, 20));
      recipePrice.setMinimumSize(new Dimension(200, 20));
      recipePrice.setMaximumSize(new Dimension(200, 20));
      toppings.setMinimumSize(new Dimension(200, 100));
      toppings.setMaximumSize(new Dimension(200, 100));
      toppingsQuantity.setMinimumSize(new Dimension(200, 20));
      toppingsQuantity.setMaximumSize(new Dimension(200, 20));
      // Again for modify drink
      drinkName2.setMinimumSize(new Dimension(200, 20));
      drinkName2.setMaximumSize(new Dimension(200, 20));
      ingredients2.setMinimumSize(new Dimension(200, 100));
      ingredients2.setMaximumSize(new Dimension(200, 100));
      ingredientsQuantity2.setMinimumSize(new Dimension(200, 20));
      ingredientsQuantity2.setMaximumSize(new Dimension(200, 20));
      mediumPrice2.setMinimumSize(new Dimension(200, 20));
      mediumPrice2.setMaximumSize(new Dimension(200, 20));
      largePrice2.setMinimumSize(new Dimension(200, 20));
      largePrice2.setMaximumSize(new Dimension(200, 20));
      recipePrice2.setMinimumSize(new Dimension(200, 20));
      recipePrice2.setMaximumSize(new Dimension(200, 20));
      toppings2.setMinimumSize(new Dimension(200, 100));
      toppings2.setMaximumSize(new Dimension(200, 100));
      toppingsQuantity2.setMinimumSize(new Dimension(200, 20));
      toppingsQuantity2.setMaximumSize(new Dimension(200, 20));
      
      // Add the objects in the correct order
      //   managerActionsPanel.add(drinkIDLabel);
      //   managerActionsPanel.add(drinkID);
      addDrinkPanel.add(addDrinkLabel);
      addDrinkPanel.add(nameLabel);
      addDrinkPanel.add(drinkName);
      addDrinkPanel.add(ingredientsLabel);
      addDrinkPanel.add(ingredients);
      addDrinkPanel.add(ingredientsQuantityLabel);
      addDrinkPanel.add(ingredientsQuantity);
      addDrinkPanel.add(toppingsLabel);
      addDrinkPanel.add(toppings);
      addDrinkPanel.add(toppingsQuantityLabel);
      addDrinkPanel.add(toppingsQuantity);
      slushieOptions.add(isSlushy);
      slushieOptions.add(isNotSlushy);
      addDrinkPanel.add(isSlushy);
      addDrinkPanel.add(isNotSlushy);
      addDrinkPanel.add(mediumLabel);
      addDrinkPanel.add(mediumPrice);
      addDrinkPanel.add(largeLabel);
      addDrinkPanel.add(largePrice);
      addDrinkPanel.add(recipeLabel);
      addDrinkPanel.add(recipePrice);
      drinkType.add(milkTea);
      drinkType.add(slushie);
      drinkType.add(coffee);
      drinkType.add(other);
      addDrinkPanel.add(milkTea);
      addDrinkPanel.add(slushie);
      addDrinkPanel.add(coffee);
      addDrinkPanel.add(other);

      // For change Drink
      changeDrinkPanel.add(modifyDrinkLabel);
      changeDrinkPanel.add(drinkIDLabel);
      changeDrinkPanel.add(drinkID);
      changeDrinkPanel.add(nameLabel2);
      changeDrinkPanel.add(drinkName2);
      changeDrinkPanel.add(ingredientsLabel2);
      changeDrinkPanel.add(ingredients2);
      changeDrinkPanel.add(ingredientsQuantityLabel2);
      changeDrinkPanel.add(ingredientsQuantity2);
      changeDrinkPanel.add(toppingsLabel2);
      changeDrinkPanel.add(toppings2);
      changeDrinkPanel.add(toppingsQuantityLabel2);
      changeDrinkPanel.add(toppingsQuantity2);
      slushieOptions2.add(isSlushy2);
      slushieOptions2.add(isNotSlushy2);
      changeDrinkPanel.add(isSlushy2);
      changeDrinkPanel.add(isNotSlushy2);
      changeDrinkPanel.add(mediumLabel2);
      changeDrinkPanel.add(mediumPrice2);
      changeDrinkPanel.add(largeLabel2);
      changeDrinkPanel.add(largePrice2);
      changeDrinkPanel.add(recipeLabel2);
      changeDrinkPanel.add(recipePrice2);
      drinkType2.add(milkTea2);
      drinkType2.add(slushie2);
      drinkType2.add(coffee2);
      drinkType2.add(other2);
      changeDrinkPanel.add(milkTea2);
      changeDrinkPanel.add(slushie2);
      changeDrinkPanel.add(coffee2);
      changeDrinkPanel.add(other2);

      // Create the new drink button
      JButton newDrinkButton = new JButton("Add New Drink");
      newDrinkButton.addActionListener(actionListener);
      addDrinkPanel.add(newDrinkButton);

      JButton changeDrinkButton = new JButton("Modify Drink");
      changeDrinkButton.addActionListener(actionListener);
      changeDrinkPanel.add(changeDrinkButton);

      managerActionsPanel.add(addDrinkPanel, BorderLayout.WEST);
      managerActionsPanel.add(changeDrinkPanel, BorderLayout.EAST);

      managerTabbedPane.addTab("Add/Modify Drink", null, managerActionsPanel, "Does nothing");
      managerTabbedPane.addTab("Inventory", null, managerInventoryPanel, "Does nothing");

      cashierTabbedPane.addTab("Milk Tea", null, CashierMilkTeaPanel, "Does nothing");
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

    ReloadButtons(actionListener);
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

      // Add the checkout buttons
      checkoutButton.addActionListener(actionListener);
      managerCheckoutButton.addActionListener(actionListener);

      receiptPanel2_bottom.add(checkoutButton);
    
      receiptPanel.add(receiptPanel2_bottom,BorderLayout.SOUTH);

      cashierFrame.add(receiptPanel, BorderLayout.EAST);    
      cashierFrame.add(cashierTabbedPane, BorderLayout.WEST);
      cashierFrame.add(cashierBackButton, BorderLayout.SOUTH);
      cashierFrame.add(cashierPanel);
      // cashierFrame.add(cashierDrinkPanel, BorderLayout.CENTER);

      managerFrame.add(managerReceiptPanel, BorderLayout.EAST);
      managerFrame.add(managerTabbedPane, BorderLayout.WEST);
      managerFrame.add(managerBackButton, BorderLayout.SOUTH);
      managerFrame.add(managerPanel);

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
      Object[] sugar = {"0%", "25%", "50%", "75%", "100%"};
        int sugarResult = JOptionPane.showOptionDialog(
                null,
                "How much sugar would you like?",
                "Sugar",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                sugar,
                sugar[0]
        );

    Object [] ice = {"light", "regular", "none"};
    int iceResult = JOptionPane.showOptionDialog(
            null,
            "How much ice would you like?",
            "Ice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            ice,
            ice[0]
    );

    
      // Create components for the new item
      JLabel itemLabel = new JLabel(itemName);
      JLabel priceLabel = new JLabel("    $" + itemPrice);
      JLabel sugarLabel = new JLabel("    Sugar: " + sugar[sugarResult]);
      JLabel iceLabel = new JLabel("    Ice: " + ice[iceResult]);

      // Add the item to the item list panel
      //style button to make it small and in same row as item and price labels
      JButton removeItemButton = new JButton("Remove");
      removeItemButton.setPreferredSize(new Dimension(100, 20));
        removeItemButton.setHorizontalAlignment(SwingConstants.CENTER);
        removeItemButton.setVerticalAlignment(SwingConstants.CENTER);
        JButton editItemButton = new JButton("Edit Toppings");
        editItemButton.setPreferredSize(new Dimension(100, 20));
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemListPanel.remove(itemLabel);
                itemListPanel.remove(priceLabel);
                itemListPanel.remove(sugarLabel);
                itemListPanel.remove(iceLabel);
                itemListPanel.remove(removeItemButton);
                itemListPanel.remove(editItemButton);
                itemListPanel.revalidate();
                itemListPanel.repaint();
            }
        });
    itemListPanel.add(removeItemButton);
    //create button to add toppings
    //should be a dropdown menu with checkboxes

        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog to select toppings and quantities
                JDialog dialog = new JDialog();
                dialog.setTitle("Edit Toppings");
                dialog.setLayout(new BorderLayout());

                JPanel toppingsPanel = new JPanel();
                toppingsPanel.setLayout(new BoxLayout(toppingsPanel, BoxLayout.Y_AXIS));

                // Sample toppings (you can replace this with your actual toppings)
                //TODO: query database for toppings and topping prices
                String[] availableToppings = {"Sugar", "Milk", "Honey", "Caramel"};

                // Create checkboxes for each topping
                List<JCheckBox> checkboxes = new ArrayList<>();
                for (String topping : availableToppings) {
                    JCheckBox checkbox = new JCheckBox(topping);
                    checkboxes.add(checkbox);
                    toppingsPanel.add(checkbox);
                }

                // Create a spinner to control the quantity
                SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 10, 1); // Change the range and step as needed
                JSpinner quantitySpinner = new JSpinner(spinnerModel);
                JLabel quantityLabel = new JLabel("Quantity:");

                // Create a button to confirm toppings selection
                JButton confirmButton = new JButton("Confirm");

                // Add action listener for the confirm button
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Process the selected toppings and quantity
                        int quantity = (int) quantitySpinner.getValue();
                        List<String> selectedToppings = new ArrayList<>();
                        for (JCheckBox checkbox : checkboxes) {
                            if (checkbox.isSelected()) {
                                selectedToppings.add(checkbox.getText());
                            }
                        }

                        // TODO: Add logic to update the receipt with selected toppings and quantity
                        // You can use selectedToppings and quantity here

                        // Close the dialog
                        dialog.dispose();
                    }
                });

                // Add components to the dialog
                dialog.add(toppingsPanel, BorderLayout.CENTER);
                JPanel controlPanel = new JPanel();
                controlPanel.add(quantityLabel);
                controlPanel.add(quantitySpinner);
                controlPanel.add(confirmButton);
                dialog.add(controlPanel, BorderLayout.SOUTH);

                // Set dialog properties
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    itemListPanel.add(editItemButton);
    itemListPanel.add(itemLabel);
    itemListPanel.add(priceLabel);
    itemListPanel.add(sugarLabel);
    itemListPanel.add(iceLabel);

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
  void ReloadButtons(ActionListener actionListener){
    //first, clear out the old buttons in the panels
    CashierMilkTeaPanel.removeAll();
    CashierSlushiePanel.removeAll();
    CashierCoffeePanel.removeAll();
    CashierOtherPanel.removeAll();

    //then, await query from Application.java to get the list of recipes
    
    //create arraylist of ItemButtons
    //for each recipe in the list of recipes, create a new ItemButton
    ArrayList<ItemButton> buttons = new ArrayList<ItemButton>();

    for (Recipe recette : app.recipes) {
        //then, create new buttons for each recipe
        ItemButton button = new ItemButton(recette.getRecipeID(), this);
        button.addActionListener(actionListener);
        buttons.add(button);
        //then, add the buttons to the panels, if item has slushie option, add to slushie panel
        if (recette.isSlush()) {
            CashierSlushiePanel.add(button);
        }
        //else if name contains coffee, add to coffee panel
        else if (recette.getRecipeName().contains("Coffee")) {
            CashierCoffeePanel.add(button);
        }
        else if (recette.getRecipeName().contains("Milk")) {
            CashierMilkTeaPanel.add(button);
        }
        else {
            CashierOtherPanel.add(button);
        }
    }

    
    //then, repaint the panels
    CashierCoffeePanel.revalidate();
    CashierCoffeePanel.repaint();
    CashierMilkTeaPanel.revalidate();
    CashierMilkTeaPanel.repaint();
    CashierSlushiePanel.revalidate();
    CashierSlushiePanel.repaint();
    CashierOtherPanel.revalidate();
    CashierOtherPanel.repaint();


    
  }
}

class ItemButton extends JButton {
    private Recipe recipe;

    //alternate constructor:
    // public ItemButton(int recipe_id, Recipe recette, GUI gui) {
    //     super("<html>" + gui.app.getRecipe(recipe_id).getRecipeName() + "</html>");
    //     recipe = gui.app.getRecipe(recipe_id);
        
    //     recipe = recette;
    //     setPreferredSize(new Dimension(100, 100));
    //     setBackground(Color.GREEN);
    //     setHorizontalAlignment(SwingConstants.CENTER);
    //     setVerticalAlignment(SwingConstants.CENTER);
    // }

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

