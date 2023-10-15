package com.example.gongchapos;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//create class for the POS system cashier end

/**
 * Represents the construction and design of the POS GUI
 * 
 * @author Ren Mai
 * @author Brenndan Croteau
 */
public class GUI extends JFrame {
    //make list of order items

    static JFrame loginFrame;
    static JFrame cashierFrame;
    static JFrame managerFrame;

    //stuff for the cashier frame
    private JPanel itemListPanel; // Panel to hold item labels
    private double subtotal;
    private double tip = 0;
    private double total;
    private JLabel subtotalLabel;
    private JLabel tipLabel;
    private JLabel totalLabel;

    //making private labels for tabbed pane
    JPanel CashierMilkTeaPanel;
    JPanel CashierSlushiePanel;
    JPanel CashierCoffeePanel;
    JPanel CashierOtherPanel;

    //manager chart
    JPanel managerChartPanel;

    //list of drinks
    ArrayList<_drink> drinks = new ArrayList<>();

    protected Application app = null;

    /**
     * Constructor to initialize the GUI class taking into consideration an Application
     * 
     * @param _app - Application object used to open our GUI
     */
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
    /**
     * This function creates a JButton with proper formatting to be a drink. Used to decrease code length
     * 
     * @param text The text that will be contained in the button
     * @return The formatted JButton object
     */
    public static JButton createDrinkButton(String text) {
      JButton button = new JButton("<html><div style='text-align: center; vertical-align: middle;'>"+text+"</div></html>");

      button.setBackground(Color.GREEN);
      button.setPreferredSize(new Dimension(100, 100));
      button.setHorizontalAlignment(SwingConstants.CENTER);
      button.setVerticalAlignment(SwingConstants.CENTER);

      return button;
    }    

    /**
     * This function creates a JPanel with proper formatting to be the receipt section
     * 
     * @return The formatted JPanel object
     */
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

    /**
     * This function adds formatting and Java Swing elements to our application,
     * both the manager view and cashier view. It also dynamically loads buttons
     * based on the drinks in our inventory, and calls functions from Application
     * to allow it to interact with our database.
     */
    public void launchGUI()
    {
      //initalize subtotal, tip, and total
      subtotal = 0;
      tip = 0;
      total = 0;

      // create frames for the 3 possible windows
      loginFrame = new JFrame("Welcome to Gong Cha!");
      cashierFrame = new JFrame("Cashier");
      managerFrame = new JFrame("Manager");

      // create a panel
      JPanel loginPanel = new JPanel();
      JPanel cashierPanel = new JPanel();
      // JPanel cashierDrinkPanel = new JPanel();
      JPanel managerPanel = new JPanel();

      JButton exitButton = new JButton("Exit");
      exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierButton = new JButton("Cashier");
      cashierButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton cashierBackButton = new JButton("Back");
      JButton managerButton = new JButton("Manager");
      managerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      JButton managerBackButton = new JButton("Back");

      JPanel changeDrinkPanel = new JPanel();
      JPanel addDrinkPanel = new JPanel();  
      JPanel addIngredientPanel = new JPanel();
      JPanel changeIngredientPanel = new JPanel();
      JPanel addToppingPanel = new JPanel();
      JPanel changeToppingPanel = new JPanel();


      // These variables are declared so we can access their values, part of adding a new drink in manager
      JTextField drinkID = CreateNewTextField();
      JTextField drinkName = CreateNewTextField();
      JTextField mediumPrice = CreateNewTextField();
      JTextField largePrice = CreateNewTextField();
      JTextField recipePrice = CreateNewTextField();
      JTextField ingredientsQuantity = CreateNewTextField();
      JTextField toppingsQuantity = CreateNewTextField();
      JTextField ingredients = CreateNewTextField();
      JTextField toppings = CreateNewTextField();
      ButtonGroup slushieOptions = new ButtonGroup();
      JRadioButton isSlushy = new JRadioButton("Slushy");
      JRadioButton isNotSlushy = new JRadioButton("Not Slushy");

      // Repeat to add to modify drink panel
      JTextField drinkName2 = CreateNewTextField();
      JTextField mediumPrice2 = CreateNewTextField();
      JTextField largePrice2 = CreateNewTextField();
      JTextField recipePrice2 = CreateNewTextField();
      JTextField ingredientsQuantity2 = CreateNewTextField();
      JTextField toppingsQuantity2 = CreateNewTextField();
      JTextField ingredients2 = CreateNewTextField();
      JTextField toppings2 = CreateNewTextField();

      // Repeat to add to add ingredient panel
      JTextField ingredientName = CreateNewTextField();
      JTextField unitPrice = CreateNewTextField();
      JTextField stock = CreateNewTextField();

      // Repeat to add to modify ingredient panel
      JTextField modifyIngredientID = CreateNewTextField();
      JTextField modifyIngredientName = CreateNewTextField();
      JTextField modifyUnitPrice = CreateNewTextField();
      JTextField modifyStock = CreateNewTextField();

      // Repeat to add to add topping panel
      JTextField toppingName = CreateNewTextField();
      JTextField toppingUnitPrice = CreateNewTextField();
      JTextField toppingStock = CreateNewTextField();

      // Repeat to add to modify topping panel
      JTextField modifyToppingID = CreateNewTextField();
      JTextField modifyToppingName = CreateNewTextField();
      JTextField modifyToppingUnitPrice = CreateNewTextField();
      JTextField modifyToppingStock = CreateNewTextField();

      JButton checkoutButton = new JButton("Checkout");
      JButton managerCheckoutButton = new JButton("Checkout");

      JPanel changeInventoryPanel = new JPanel();
      JPanel viewDrinksPanel = new JPanel();
      JPanel managerInventoryPanel = new JPanel();
      JPanel recommendedPurchases = new JPanel();
      
      String[] columnNames = {"Ingredient_ID", "Ingredient_Name", "Unit_Price", "Stock", "Minimum_Quantity"};
      // Make a JTable out of the data returned from function in Application.java
      Object[][] data = app.getIngredients();
      JTable inventoryTable = new JTable(data, columnNames);
      JScrollPane inventoryScrollPane = new JScrollPane(inventoryTable);
      inventoryScrollPane.setPreferredSize(new Dimension(800, 500));
      managerInventoryPanel.add(inventoryScrollPane);

      String[] columnNamesToppings = {"Topping_ID", "Topping_Name", "Unit_Price", "Stock", "Minimum_Quantity"};
      // Make a JTable out of the data returned from function in Application.java
      Object[][] dataToppings = app.getToppings();
      JTable inventoryTable2 = new JTable(dataToppings, columnNamesToppings);
      JScrollPane inventoryScrollPaneToppings = new JScrollPane(inventoryTable2);
      inventoryScrollPaneToppings.setPreferredSize(new Dimension(800, 150));
      managerInventoryPanel.add(inventoryScrollPaneToppings);

      String[] columnNamesDrinks = {"Recipe_ID", "Recipe_Name", "isSlush", "Med_Price", "Large_Price", "Recipe_Price"};
      // Make a JTable out of drink data returned from function in Application.java
      Object[][] dataDrinks = app.getRecipes();
      JTable drinksTable = new JTable(dataDrinks, columnNamesDrinks);
      JScrollPane drinkScrollPane = new JScrollPane(drinksTable);
      drinkScrollPane.setPreferredSize(new Dimension(800, 650));
      viewDrinksPanel.add(drinkScrollPane);

      String[] columnNamesInventory = {"Ingredient_ID", "Ingredient_Name", "Unit_Price", "Stock", "Minimum_Quantity"};
      // Make a JTable out of drink data returned from function in Application.java
      Object[][] dataInventory = app.restockReportIngredients();
      JTable inventoryTable3 = new JTable(dataInventory, columnNamesInventory);
      JScrollPane inventoryScrollPaneRecommended = new JScrollPane(inventoryTable3);
      inventoryScrollPaneRecommended.setPreferredSize(new Dimension(800, 150));
      JLabel recommendedIngredientsLabel = new JLabel("The table below shows all ingredients where the stock is below the minimum recommended amount.");
      recommendedPurchases.add(recommendedIngredientsLabel);
      recommendedPurchases.add(inventoryScrollPaneRecommended);

      String[] columnNamesInventoryToppings = {"Topping_ID", "Topping_Name", "Unit_Price", "Stock", "Minimum_Quantity"};
      // Make a JTable out of drink data returned from function in Application.java
      Object[][] dataInventoryToppings = app.restockReportToppings();
      JTable inventoryTable4 = new JTable(dataInventoryToppings, columnNamesInventoryToppings);
      JScrollPane inventoryScrollPaneRecommendedToppings = new JScrollPane(inventoryTable4);
      inventoryScrollPaneRecommendedToppings.setPreferredSize(new Dimension(800, 150));
      JLabel recommendedToppingsLabel = new JLabel("The table below shows all toppings where the stock is below the minimum recommended amount.");
      recommendedPurchases.add(recommendedToppingsLabel);
      recommendedPurchases.add(inventoryScrollPaneRecommendedToppings);

      ActionListener actionListener = new ActionListener() {
        // if button is pressed
        public void actionPerformed(ActionEvent e)
        {
            String changeDrinkIDStr = drinkID.getText();
            int changeDrinkID = -1;
            if (!changeDrinkIDStr.equals("")) {
                changeDrinkID = Integer.parseInt(changeDrinkIDStr);
            }
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

            if (clickedButton instanceof ItemButton) {
                //call the function to add the drink to the receipt and cast to clicked button
                addItemToReceipt((ItemButton) clickedButton, itemListPanel);
            }

            if (clickedButton == checkoutButton) {
                //ask for tip
                String tipString = JOptionPane.showInputDialog("Enter tip amount: ");
                //if Double.parseDouble(tipString) is empty, tip = 0 or if tip is negative, tip = 0
                if (tipString.equals("") || tip < 0) {
                    tip = 0;
                }
                else{
                    tip = Double.parseDouble(tipString);
                }
                //update tip and total
                total = subtotal + tip;
                //update tip and total labels
                tipLabel.setText("Tip: $" + tip);
                totalLabel.setText("Total: $" + total);
                //display total in jdialog box and clear if yes is clicked

                // app.createNewOrder();
                for (_drink drink : drinks) {
                    List<String> toppingsUsed = new ArrayList<>();
                    List<Integer> toppingsQuantity = new ArrayList<>();
                    //if drink.toppings is not null, add toppings to toppingsUsed and toppingsQuantity
                    if(!(drink.toppings == null)) {
                        for (_topping top : drink.toppings) {
                            toppingsUsed.add(top.topping.getToppingName());
                            toppingsQuantity.add(top.quantity);
                        }
                    }
                    app.addDrink(drink.recipe.getRecipeID(), "", drink.is_medium, drink.ice, drink.sugar, subtotal, toppingsUsed, toppingsQuantity);

                }
                app.placeOrder(tip, "");

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

                    // app.setOrderStatus(true);
                    
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

                if (!requestedToppings.equals("")) {
                    if (requestedToppingsQuantity.equals("")) {
                        JOptionPane.showMessageDialog(null, "No topping quantities provided", "Error", JOptionPane.ERROR_MESSAGE); 
                        return;
                    }
                }
                // CREATE SQL QUERY TO ADD DRINK INFO TO DATABASE
                app.createRecipe(newDrinkName, isSlush, Integer.parseInt(newMediumPrice), Integer.parseInt(newLargePrice), Integer.parseInt(newRecipePrice), ingredientsArray, ingredientsQuantityArray, toppingsArray, toppingsQuantityArray);
                //reload buttons
                ReloadButtons(this);
                JOptionPane.showMessageDialog(null, "Drink added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            if (s.equals("Change Name")) {
                String changedName = drinkName2.getText();
                // Create SQL query to change the name in the database using changeDrinkID and new name
                // Call it
                app.updateRecipeName(changeDrinkID, changedName);
            }
            if (s.equals("Change Ingredients")) {
                String newIngredientsStr = ingredients2.getText();
                String newIngredientsQuantitiesStr = ingredientsQuantity2.getText();
                ArrayList<String> newIngredientsArr = new ArrayList<>();
                ArrayList<Integer> newIngredientsQuantitiesArr = new ArrayList<>();
                if (!newIngredientsQuantitiesStr.equals("")) {
                    String[] newIngredientsStrArr = newIngredientsStr.split(",");
                    String[] newIngredientsQuantitiesStrArr = newIngredientsQuantitiesStr.split(",");
                    for (String topping : newIngredientsStrArr) {
                        newIngredientsArr.add(topping);
                    }
                    for (String quantity : newIngredientsQuantitiesStrArr) {
                        newIngredientsQuantitiesArr.add(Integer.parseInt(quantity));
                    }
                }
                // Create SQL query to change the ingredients in the database using changeDrinkID and newIngredients
                // Call it
                app.modifyMultipleIngredients(changeDrinkID, newIngredientsArr, newIngredientsQuantitiesArr);
            }
            if (s.equals("Change Toppings")) {
                String newToppingsStr = toppings2.getText();
                String newToppingsQuantitiesStr = toppingsQuantity2.getText();
                ArrayList<String> newToppingsArr = new ArrayList<>();
                ArrayList<Integer> newToppingsQuantitiesArr = new ArrayList<>();
                if (!newToppingsQuantitiesStr.equals("")) {
                    String[] newToppingsStrArr = newToppingsStr.split(",");
                    String[] newToppingsQuantitiesStrArr = newToppingsQuantitiesStr.split(",");
                    for (String topping : newToppingsStrArr) {
                        newToppingsArr.add(topping);
                    }
                    for (String quantity : newToppingsQuantitiesStrArr) {
                        newToppingsQuantitiesArr.add(Integer.parseInt(quantity));
                    }
                }
                // Create SQL query to change the ingredients in the database using changeDrinkID and newToppings
                // Call it
                app.modifyMultipleToppings(changeDrinkID, newToppingsArr, newToppingsQuantitiesArr);
            }
            if (s.equals("Change Medium Price")) {
                String priceStr = mediumPrice2.getText();
                double price = 0;
                if (!priceStr.equals("")) {
                    price = Double.parseDouble(priceStr);
                }
                // Call SQL query with ID and new price
                app.updateMedPrice(changeDrinkID, price);
            }
            if (s.equals("Change Large Price")) {
                String priceStr = largePrice2.getText();
                double price = 0;
                if (!priceStr.equals("")) {
                    price = Double.parseDouble(priceStr);
                }
                // Call SQL query with ID and new price
                app.updateLargePrice(changeDrinkID, price);
            }
            if (s.equals("Change Recipe Price")) {
                String priceStr = recipePrice2.getText();
                double price = 0;
                if (!priceStr.equals("")) {
                    price = Double.parseDouble(priceStr);
                }
                // Call SQL query with ID and new price
                app.updateRecipePrice(changeDrinkID, price);
            }
            if (s.equals("Add New Ingredient")) {
                String ingredientNameStr = ingredientName.getText();
                String ingredientUnitPrice = unitPrice.getText();
                String ingredientStock = stock.getText();
                if (!ingredientUnitPrice.equals("")) {
                    double unitPriceDBL = Double.parseDouble(ingredientUnitPrice);
                }
                if (!ingredientStock.equals("")) {
                    int stockINT = Integer.parseInt(ingredientStock);
                }
                
                // Create SQL query to add a new ingredient and call it using ingredient name, unitPriceDBL, and stockINT
                app.addIngredients(ingredientNameStr, Double.parseDouble(ingredientUnitPrice), Integer.parseInt(ingredientStock));
            }
            if (s.equals("Change Ingredient Name")) {
                String ingredientIDStr = modifyIngredientID.getText();
                String ingredientNameStr = modifyIngredientName.getText();
                int ingredientID = -1;
                if (!ingredientIDStr.equals("")) {
                    ingredientID = Integer.parseInt(ingredientIDStr);
                }
                // Create SQL query to change ingredient name given int ID and String name
                // Call query
                app.updateIngredientName(ingredientID, ingredientNameStr);
            }
            if (s.equals("Change Unit Price")) {
                String ingredientIDStr = modifyIngredientID.getText();
                String ingredientUnitPrice = modifyUnitPrice.getText();
                double newUnitPrice = -1;
                int ingredientID = -1;
                if (!ingredientIDStr.equals("")) {
                    ingredientID = Integer.parseInt(ingredientIDStr);
                }
                if (!ingredientUnitPrice.equals("")) {
                    newUnitPrice = Double.parseDouble(ingredientUnitPrice);
                }
                // Create SQL query to change unit price given int ID and double unit_price
                // Call query
                app.updateIngredientUnitPrice(ingredientID, newUnitPrice);
            }
            if (s.equals("Change Stock")) {
                String ingredientIDStr = modifyIngredientID.getText();
                String ingredientStockStr = modifyStock.getText();
                int ingredientStock = -1;
                int ingredientID = -1;
                if (!ingredientIDStr.equals("")) {
                    ingredientID = Integer.parseInt(ingredientIDStr);
                }
                if (!ingredientStockStr.equals("")) {
                    ingredientStock = Integer.parseInt(ingredientStockStr);
                }
                // Create SQL query to change stock given int ID and int stock
                app.updateIngredientStock(ingredientID, ingredientStock);
            }
            if(s.equals("Add New Topping")) {
                String toppingNameStr = toppingName.getText();
                String toppingUnitPriceStr = toppingUnitPrice.getText();
                String toppingStockStr = toppingStock.getText();
                double unitPriceDBL = -1;
                int stockINT = -1;
                if (!toppingUnitPriceStr.equals("")) {
                    unitPriceDBL = Double.parseDouble(toppingUnitPriceStr);
                }
                if (!toppingStockStr.equals("")) {
                    stockINT = Integer.parseInt(toppingStockStr);
                }
                // Create SQL query to add a new topping and call it using topping name, unitPriceDBL, and stockINT
                app.addToppings(toppingNameStr, unitPriceDBL, stockINT);
            }
            if(s.equals("Change Topping Name")) {
                String toppingIDStr = modifyToppingID.getText();
                String toppingNameStr = modifyToppingName.getText();
                int toppingID = -1;
                if (!toppingIDStr.equals("")) {
                    toppingID = Integer.parseInt(toppingIDStr);
                }
                // Create SQL query to change topping name given int ID and String name
                // Call query
                app.updateToppingsName(toppingID, toppingNameStr);
            }
            if(s.equals("Change Topping Unit Price")) {
                String toppingIDStr = modifyToppingID.getText();
                String toppingUnitPrice = modifyToppingUnitPrice.getText();
                double newUnitPrice = -1;
                int toppingID = -1;
                if (!toppingIDStr.equals("")) {
                   toppingID = Integer.parseInt(toppingIDStr);
                }
                if (!toppingUnitPrice.equals("")) {
                    newUnitPrice = Double.parseDouble(toppingUnitPrice);
                }
                // Create SQL query to change unit price given int ID and double unit_price
                // Call query
                app.updateToppingsUnitPrice(toppingID, newUnitPrice);
            }
            if(s.equals("Change Topping Stock")) {
                String toppingIDStr = modifyToppingID.getText();
                String toppingStockStr = modifyToppingStock.getText();
                int toppingStock = -1;
                int toppingID = -1;
                if (!toppingIDStr.equals("")) {
                    toppingID = Integer.parseInt(toppingIDStr);
                }
                if (!toppingStockStr.equals("")) {
                    toppingStock = Integer.parseInt(toppingStockStr);
                }
                // Create SQL query to change stock given int ID and int stock
                // Call query
                app.updateToppingsStock(toppingID, toppingStock);
            }
        }        
      };

      JButton changeName = new JButton("Change Name");
      JButton changeIngredients = new JButton("Change Ingredients");
      JButton changeToppings = new JButton("Change Toppings");
      JButton changeMedPrice = new JButton("Change Medium Price");
      JButton changeLrgPrice = new JButton("Change Large Price");
      JButton changeRecipePrice = new JButton("Change Recipe Price");
      JButton changeSlushy = new JButton("Change Slushy");

      JButton changeIngredientName = new JButton("Change Ingredient Name");
      JButton changeUnitPrice = new JButton("Change Unit Price");
      JButton changeStock = new JButton ("Change Stock");

      JButton changeToppingName = new JButton("Change Topping Name");
      JButton changeToppingUnitPrice = new JButton("Change Topping Unit Price");
      JButton changeToppingStock = new JButton ("Change Topping Stock");

      changeName.addActionListener(actionListener);
      changeIngredients.addActionListener(actionListener);
      changeToppings.addActionListener(actionListener);
      changeMedPrice.addActionListener(actionListener);
      changeLrgPrice.addActionListener(actionListener);
      changeRecipePrice.addActionListener(actionListener);
      changeSlushy.addActionListener(actionListener);
      changeIngredientName.addActionListener(actionListener);
      changeUnitPrice.addActionListener(actionListener);
      changeStock.addActionListener(actionListener);
      changeToppingName.addActionListener(actionListener);
      changeToppingUnitPrice.addActionListener(actionListener);
      changeToppingStock.addActionListener(actionListener);

      // add actionlistener to button
      exitButton.addActionListener(actionListener);
      cashierButton.addActionListener(actionListener);
      cashierBackButton.addActionListener(actionListener);
      managerButton.addActionListener(actionListener);
      managerBackButton.addActionListener(actionListener);

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

      //make cashierslushiepanel
      CashierSlushiePanel = new JPanel();

      //make cashiercoffeepanel
      CashierCoffeePanel = new JPanel();

      //make cashierotherpanel
      CashierOtherPanel = new JPanel();

      //make manageractionspanel
      JPanel managerActionsPanel = new JPanel();
      addDrinkPanel.setLayout(new BoxLayout(addDrinkPanel, BoxLayout.Y_AXIS));
      addDrinkPanel.add(Box.createVerticalStrut(10));
      changeDrinkPanel.setLayout(new BoxLayout(changeDrinkPanel, BoxLayout.Y_AXIS));
    
      // Do same thing for ingredients
      addIngredientPanel.setLayout(new BoxLayout(addIngredientPanel, BoxLayout.Y_AXIS));
      changeIngredientPanel.setLayout(new BoxLayout(changeIngredientPanel, BoxLayout.Y_AXIS));
      addToppingPanel.setLayout(new BoxLayout(addToppingPanel, BoxLayout.Y_AXIS));
      changeToppingPanel.setLayout(new BoxLayout(changeToppingPanel, BoxLayout.Y_AXIS));

      // declare necessary sections to add a drink
      JLabel addDrinkLabel = CreateNewLabel("ADD DRINK: ");
      JLabel nameLabel = CreateNewLabel("Drink name: ");
      JLabel ingredientsLabel = CreateNewLabel("Ingredients (separated by ','): ");
      JLabel ingredientsQuantityLabel = CreateNewLabel("Quantity of Ingredients (in same order, separated by ','): ");
      JLabel toppingsLabel = CreateNewLabel("Toppings (separated by ','): ");
      JLabel toppingsQuantityLabel = CreateNewLabel("Quantity of Toppings (in same order, separated by ','): ");
      JLabel mediumLabel = CreateNewLabel("Medium Price: ");
      JLabel largeLabel = CreateNewLabel("Large Price: ");
      JLabel recipeLabel = CreateNewLabel("Recipe Price: ");

      // declare necessary sections to modify a drink
      JLabel modifyDrinkLabel = CreateNewLabel("MODIFY DRINK: ");
      JLabel drinkIDLabel = CreateNewLabel("ID of drink to modify: ");
      JLabel nameLabel2 = CreateNewLabel("Drink name: ");
      JLabel ingredientsLabel2 = CreateNewLabel("Ingredients (separated by ','): ");
      JLabel ingredientsQuantityLabel2 = CreateNewLabel("Quantity of Ingredients (in same order, separated by ','): ");
      JLabel toppingsLabel2 = CreateNewLabel("Toppings (separated by ','): ");
      JLabel toppingsQuantityLabel2 = CreateNewLabel("Quantity of Toppings (in same order, separated by ','): ");
      JLabel mediumLabel2 = CreateNewLabel("Medium Price: ");
      JLabel largeLabel2 = CreateNewLabel("Large Price: ");
      JLabel recipeLabel2 = CreateNewLabel("Recipe Price: ");

      // declare necessary sections to add an ingredient
      JLabel addIngredientLabel = CreateNewLabel("ADD INGREDIENT: ");
      JLabel ingredientNameLabel = CreateNewLabel("Ingredient Name: ");
      JLabel ingredientUnitPriceLabel = CreateNewLabel("Unit Price: ");
      JLabel ingredientStockLabel = CreateNewLabel("Stock: ");

      // declare necessary sections to modify an ingredient
      JLabel modifyIngredientLabel = CreateNewLabel("MODIFY INGREDIENT: ");
      JLabel ingredientIDLabel = CreateNewLabel("Ingredient ID (REQUIRED): ");
      JLabel modifyNameLabel = CreateNewLabel("Ingredient Name: ");
      JLabel modifyUnitPriceLabel = CreateNewLabel("Unit Price: ");
      JLabel modifyStockLabel = CreateNewLabel("Stock: ");
    
      // declare necessary sections to add a topping
      JLabel addToppingLabel = CreateNewLabel("ADD TOPPING: ");
      JLabel toppingNameLabel = CreateNewLabel("Topping Name: ");
      JLabel toppingUnitPriceLabel = CreateNewLabel("Unit Price: ");
      JLabel toppingStockLabel = CreateNewLabel("Stock: ");

      // declare necessary sections to modify a topping
      JLabel modifytoppingLabel = CreateNewLabel("MODIFY TOPPING: ");
      JLabel toppingIDLabel = CreateNewLabel("Topping ID (REQUIRED): ");
      JLabel modifyToppingNameLabel = CreateNewLabel("Topping Name: ");
      JLabel modifyToppingUnitPriceLabel = CreateNewLabel("Unit Price: ");
      JLabel modifyToppingStockLabel = CreateNewLabel("Stock: ");

      // Add Drink alignment
      isSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);
      isNotSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);
      
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

      // For change Drink
      changeDrinkPanel.add(modifyDrinkLabel);
      changeDrinkPanel.add(drinkIDLabel);
      changeDrinkPanel.add(drinkID);
      changeDrinkPanel.add(nameLabel2);
      changeDrinkPanel.add(drinkName2);
      changeDrinkPanel.add(changeName);
      changeDrinkPanel.add(ingredientsLabel2);
      changeDrinkPanel.add(ingredients2);
      changeDrinkPanel.add(ingredientsQuantityLabel2);
      changeDrinkPanel.add(ingredientsQuantity2);
      changeDrinkPanel.add(changeIngredients);
      changeDrinkPanel.add(toppingsLabel2);
      changeDrinkPanel.add(toppings2);
      changeDrinkPanel.add(toppingsQuantityLabel2);
      changeDrinkPanel.add(toppingsQuantity2);
      changeDrinkPanel.add(changeToppings);
      changeDrinkPanel.add(mediumLabel2);
      changeDrinkPanel.add(mediumPrice2);
      changeDrinkPanel.add(changeMedPrice);
      changeDrinkPanel.add(largeLabel2);
      changeDrinkPanel.add(largePrice2);
      changeDrinkPanel.add(changeLrgPrice);
      changeDrinkPanel.add(recipeLabel2);
      changeDrinkPanel.add(recipePrice2);
      changeDrinkPanel.add(changeRecipePrice);

      // For add ingredient
      addIngredientPanel.add(addIngredientLabel);
      addIngredientPanel.add(ingredientNameLabel);
      addIngredientPanel.add(ingredientName);
      addIngredientPanel.add(ingredientUnitPriceLabel);
      addIngredientPanel.add(unitPrice);
      addIngredientPanel.add(ingredientStockLabel);
      addIngredientPanel.add(stock);

      // For modify ingredient
      changeIngredientPanel.add(modifyIngredientLabel);
      changeIngredientPanel.add(ingredientIDLabel);
      changeIngredientPanel.add(modifyIngredientID);
      changeIngredientPanel.add(modifyNameLabel);
      changeIngredientPanel.add(modifyIngredientName);
      changeIngredientPanel.add(changeIngredientName);
      changeIngredientPanel.add(modifyUnitPriceLabel);
      changeIngredientPanel.add(modifyUnitPrice);
      changeIngredientPanel.add(changeUnitPrice);
      changeIngredientPanel.add(modifyStockLabel);
      changeIngredientPanel.add(modifyStock);
      changeIngredientPanel.add(changeStock);

      // For add topping
      addToppingPanel.add(addToppingLabel);
      addToppingPanel.add(toppingNameLabel);
      addToppingPanel.add(toppingName);
      addToppingPanel.add(toppingUnitPriceLabel);
      addToppingPanel.add(toppingUnitPrice);
      addToppingPanel.add(toppingStockLabel);
      addToppingPanel.add(toppingStock);

      // For modify topping
      changeToppingPanel.add(modifytoppingLabel);
      changeToppingPanel.add(toppingIDLabel);
      changeToppingPanel.add(modifyToppingID);
      changeToppingPanel.add(modifyToppingNameLabel);
      changeToppingPanel.add(modifyToppingName);
      changeToppingPanel.add(changeToppingName);
      changeToppingPanel.add(modifyToppingUnitPriceLabel);
      changeToppingPanel.add(modifyToppingUnitPrice);
      changeToppingPanel.add(changeToppingUnitPrice);
      changeToppingPanel.add(modifyToppingStockLabel);
      changeToppingPanel.add(modifyToppingStock);
      changeToppingPanel.add(changeToppingStock);
    
      // Create the new drink button
      JButton newDrinkButton = new JButton("Add New Drink");
      newDrinkButton.addActionListener(actionListener);
      addDrinkPanel.add(newDrinkButton);

      JButton newIngredientButton = new JButton("Add New Ingredient");
      newIngredientButton.addActionListener(actionListener);
      addIngredientPanel.add(newIngredientButton);

      JButton newToppingButton = new JButton("Add New Topping");
      newToppingButton.addActionListener(actionListener);
      addToppingPanel.add(newToppingButton);

      managerActionsPanel.add(addDrinkPanel, BorderLayout.WEST);
      managerActionsPanel.add(changeDrinkPanel, BorderLayout.EAST);

      changeInventoryPanel.add(addIngredientPanel, BorderLayout.WEST);
      changeInventoryPanel.add(changeIngredientPanel, BorderLayout.WEST);
      changeInventoryPanel.add(addToppingPanel, BorderLayout.EAST);
      changeInventoryPanel.add(changeToppingPanel, BorderLayout.EAST);

      managerTabbedPane.addTab("Inventory", null, managerInventoryPanel, "Does nothing");
      managerTabbedPane.addTab("Add/Update Inventory Items", null, changeInventoryPanel, "Does nothing");
      managerTabbedPane.addTab("Drinks", null, viewDrinksPanel, "Does nothing");
      managerTabbedPane.addTab("Add/Modify Drink", null, managerActionsPanel, "Does nothing");
      managerTabbedPane.addTab("Recommended Restock", null, recommendedPurchases, "Does nothing");

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

    /**
     * Adds an item to the receipt panel.
     *
     * @param itemButton The button representing the item.
     * @param panel The panel where the item will be added.
     */
    private void addItemToReceipt(ItemButton itemButton, JPanel panel) {
      // Get item details from the button
      //ask user if they want the medium price or large price

      String itemName = itemButton.getItemName();
      double itemPrice;

      List<String> selectedToppings = new ArrayList<String>();
            List<Integer> selectedToppingsQuantity = new ArrayList<>();

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
      
      boolean size;
      if (result == 0) {
          itemPrice = itemButton.getMediumPrice();
          size = true;
      } 
      else {
          itemPrice = itemButton.getLargePrice();
          size = false;
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

    //create new drink object
      _drink newDrink = new _drink(itemButton.getRecipe(), size, iceResult, sugarResult);
      //add drink to array
      drinks.add(newDrink);

      JLabel itemLabel = new JLabel(itemName);

      JLabel priceLabel = new JLabel("    $" + itemPrice);
      JLabel sugarLabel = new JLabel("    Sugar: " + sugar[sugarResult]);
      JLabel iceLabel = new JLabel("    Ice: " + ice[iceResult]);
      JPanel minitoppanel = new JPanel();
      //make minitoppanel boxlayout and scrollable
      minitoppanel.setLayout(new BoxLayout(minitoppanel, BoxLayout.Y_AXIS));
      //make panel grow with the items placed in it
      minitoppanel.setAlignmentX(Component.LEFT_ALIGNMENT);      
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
                itemListPanel.remove(minitoppanel);
                //remove drink from drinks
                //subtract from subtotal the price of the item
                //TODO: query database for price of toppings too.

                subtotal -= newDrink.price;
                drinks.remove(newDrink);

                //update subtotal and total labels
                subtotalLabel.setText("Subtotal: $" + subtotal);
                total = subtotal + tip;
                totalLabel.setText("Total: $" + total);
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
                //clear toppings arraylist
                if (newDrink.toppings != null){
                    newDrink.toppings.clear();
                }
                //if the toppings panel is not empty, clear it
                if (minitoppanel.getComponentCount() != 0){
                    minitoppanel.removeAll();
                }
                //clear toppings quantity arraylist
                selectedToppingsQuantity.clear();

                JDialog dialog = new JDialog();
                dialog.setTitle("Edit Toppings");
                dialog.setLayout(new BorderLayout());

                JPanel toppingsPanel = new JPanel();
                toppingsPanel.setLayout(new BoxLayout(toppingsPanel, BoxLayout.Y_AXIS));

                // Sample toppings (you can replace this with your actual toppings)
                Vector<String> availableToppings = new Vector<String>();

                for (Topping top : app.toppings) {
                    availableToppings.add(top.getToppingName());
                }

                //create spinners for each topping
                List<JSpinner> spinners = new ArrayList<>();
                for (String topping : availableToppings) {
                    SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 10, 1); // Change the range and step as needed
                    JSpinner spinner = new JSpinner(spinnerModel);
                    JLabel spinnerLabel = new JLabel(topping);

                    toppingsPanel.add(spinnerLabel);
                    toppingsPanel.add(spinner);
                    //add spinner to list of spinners
                    spinners.add(spinner);
                }

                // Create a button to confirm toppings selection
                JButton confirmButton = new JButton("Confirm");

                // Add action listener for the confirm button
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Process the selected toppings and quantity
                        int quantity;
                        // double topping_price = 0;
                        ArrayList<_topping> selectedToppings = new ArrayList<>();
                        //iterate over spinners and get quantity, if not 0, add to list of toppings
                        for (int i = 0; i < spinners.size(); i++) {
                            JSpinner spinner = spinners.get(i);
                            String topping = availableToppings.get(i);
                            Topping topping_fr = app.toppings.get(i);
                            quantity = (int) spinner.getValue();
                            if (quantity != 0) {
                                //add topping to list of toppings
                                _topping newTopping = new _topping(topping_fr, topping, quantity);
                                minitoppanel.add(new JLabel("    "+topping + ": " + quantity));
                                
                                // topping_price = topping_fr.getUnitPrice() * quantity;

                                selectedToppings.add(newTopping);
                                selectedToppingsQuantity.add(quantity);
                            }
                        }



                        itemListPanel.revalidate();
                        itemListPanel.repaint();
                        // Close the dialog
                        subtotal -= newDrink.price;

                        newDrink.toppings = selectedToppings;
                        newDrink.updateprice();

                        //print out the toppings and quantities
                        //update the subtotal and total
                        subtotal += newDrink.price;
                        total = subtotal + tip;
                    
                        //update subtotal and total labels
                        subtotalLabel.setText("Subtotal: $" + subtotal);
                        totalLabel.setText("Total: $" + total);

                        dialog.dispose();
                        minitoppanel.revalidate();
                        minitoppanel.repaint();
                    }
                });

                // Add components to the dialog
                dialog.add(toppingsPanel, BorderLayout.CENTER);
                JPanel controlPanel = new JPanel();

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
    itemListPanel.add(minitoppanel);

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

    /**
     * Reloads buttons in the Cashier interface based on the provided action listener.
     *
     * @param actionListener The action listener to be added to each button.
     */
    void ReloadButtons(ActionListener actionListener){
        //first, clear out the old buttons in the panels
        CashierMilkTeaPanel.removeAll();
        CashierSlushiePanel.removeAll();
        CashierCoffeePanel.removeAll();
        CashierOtherPanel.removeAll();

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

    /**
     * Creates a new JLabel with the specified text and left alignment.
     *
     * @param text The text to be displayed on the label.
     * @return A new JLabel with the specified text and left alignment.
     */
    JLabel CreateNewLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Creates a new JTextField with left alignment and a minimum size of 200x20 pixels.
     *
     * @return A new JTextField with left alignment and a minimum size of 200x20 pixels.
     */
    JTextField CreateNewTextField() {
        JTextField textField = new JTextField();
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setMinimumSize(new Dimension(200, 20));
        return textField;
    }
}

/**
 * Custom JButton class representing an item button in the GUI.
 * 
 * @author Ren Mai
 */
class ItemButton extends JButton {
    private Recipe recipe;

    /**
     * Constructs a new ItemButton with the specified recipe ID and GUI.
     *
     * @param recipe_id The ID of the recipe associated with this button.
     * @param gui The GUI object that contains the application.
     */
    public ItemButton(int recipe_id, GUI gui) {
        super("<html>" + gui.app.getRecipe(recipe_id).getRecipeName() + "</html>");
        recipe = gui.app.getRecipe(recipe_id);

        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.GREEN);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Gets the ID of the associated item.
     *
     * @return The ID of the associated item.
     */
    public int getItemID() {
        return recipe.getRecipeID();
    }

    /**
     * Gets the name of the associated item.
     *
     * @return The name of the associated item.
     */
    public String getItemName() {
        return recipe.getRecipeName();
    }

    /**
     * Gets the price of the associated item in medium size.
     *
     * @return The price of the associated item in medium size.
     */
    public double getMediumPrice() {
        return recipe.getMediumPrice();
    }

    /**
     * Gets the price of the associated item in large size.
     *
     * @return The price of the associated item in large size.
     */
    public double getLargePrice() {
        return recipe.getLargePrice();
    }

    /**
     * Gets the Recipe object associated with this button.
     *
     * @return The Recipe object associated with this button.
     */
    public Recipe getRecipe() {
        return recipe;
    }
}

//make a class for toppings
/**
 * Represents a topping associated with a drink.
 * 
 * @author Ren Mai
 */
class _topping {
    String name;     // The name of the topping
    Topping topping; // The Topping object
    int quantity;    // The quantity of the topping

    /**
     * Constructs a new _topping instance.
     *
     * @param topping The Topping object associated with this topping.
     * @param name The name of the topping.
     * @param quantity The quantity of the topping.
     */
    public _topping(Topping topping, String name, int quantity){
        this.topping = topping;
        this.name = name;
        this.quantity = quantity;
    }
}

/**
 * Represents a drink item with associated toppings, size, ice level, sugar level, and price.
 * 
 * @author Ren Mai
 */
class _drink {
    Recipe recipe;          // The Recipe associated with the drink
    ArrayList<_topping> toppings; // The list of toppings on the drink
    boolean is_medium;      // Indicates if the drink is of medium size
    int ice;                // The level of ice in the drink
    int sugar;              // The level of sugar in the drink
    double price;           // The total price of the drink

    /**
     * Constructs a new _drink instance.
     *
     * @param recipe The Recipe object associated with this drink.
     * @param is_medium Indicates if the drink is of medium size.
     * @param ice The level of ice in the drink.
     * @param sugar The level of sugar in the drink.
     */
    public _drink(Recipe recipe, boolean is_medium, int ice, int sugar){
        this.recipe = recipe;
        this.is_medium = is_medium;
        this.ice = ice;
        this.sugar = sugar;

        if (is_medium) {
            price = recipe.getMediumPrice();
        } else {
            price = recipe.getLargePrice();
        }
    }

    /**
     * Updates the price of the drink based on the recipe, toppings, and size.
     */
    public void updateprice(){
        if(is_medium){
            price = recipe.getMediumPrice();
        } else {
            price = recipe.getLargePrice();
        }

        for(_topping topping : toppings){
            price += topping.topping.getUnitPrice() * topping.quantity;
            // price += topping.quantity * topping.topping.getUnitPrice(); 
        }
    }
}