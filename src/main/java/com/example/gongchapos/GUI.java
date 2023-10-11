package com.example.gongchapos;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI extends JFrame {
    //make list of order items

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
      ButtonGroup slushieOptions2 = new ButtonGroup();
      JRadioButton isSlushy2 = new JRadioButton("Slushy");
      JRadioButton isNotSlushy2 = new JRadioButton("Not Slushy");

    JButton checkoutButton = new JButton("Checkout");
    JButton managerCheckoutButton = new JButton("Checkout");

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
                tip = Double.parseDouble(tipString);
                //update tip and total
                total = subtotal + tip;
                //update tip and total labels
                tipLabel.setText("Tip: $" + tip);
                totalLabel.setText("Total: $" + total);
                //display total in jdialog box and clear if yes is clicked

                app.createNewOrder();
                for (_drink drink : drinks) {
                    //create toppings used List<string>
                    //create toppings quantity List<int>
                    List<String> toppingsUsed = new ArrayList<String>();
                    List<Integer> toppingsQuantity = new ArrayList<>();
                    for (_topping top : drink.toppings) {
                        toppingsUsed.add(top.topping.getToppingName());
                        toppingsQuantity.add(top.quantity);
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

                    app.setOrderStatus(true);
                    
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
                // TODO:
                // CREATE SQL QUERY TO ADD DRINK INFO TO DATABASE
                app.createRecipe(newDrinkName, isSlush, Integer.parseInt(newMediumPrice), Integer.parseInt(newLargePrice), Integer.parseInt(newRecipePrice), ingredientsArray, ingredientsQuantityArray, toppingsArray, toppingsQuantityArray);
                
                JOptionPane.showMessageDialog(null, "Drink added successfully, restart application to use button", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            if (s.equals("Change Name")) {
                String changedName = drinkName2.getText();
                // TODO:
                // Create SQL query to change the name in the database using an ID and new name
                // Call it
            }
            if (s.equals("Change Ingredients")) {
                String newIngredientsStr = ingredients2.getText();
                if (!newIngredientsStr.equals("")) {
                    String[] newIngredientsStrArr = newIngredientsStr.split(",");
                    int[] newIngredients = new int[newIngredientsStrArr.length];
                    for (int i = 0; i < newIngredientsStrArr.length; i++) {
                        newIngredients[i] = Integer.parseInt(newIngredientsStrArr[i]);
                    }
                }
                // TODO:
                // Create SQL query to change the ingredients in the database using an ID and newIngredients
                // Call it
            }
            if (s.equals("Change Toppings")) {
                String newToppingsStr = toppings2.getText();
                if (!newToppingsStr.equals("")) {
                    String[] newToppingsStrArr = newToppingsStr.split(",");
                    int[] newToppings = new int[newToppingsStrArr.length];
                    for (int i = 0; i < newToppingsStrArr.length; i++) {
                        newToppings[i] = Integer.parseInt(newToppingsStrArr[i]);
                    }
                }
                // TODO:
                // Create SQL query to change the ingredients in the database using an ID and newToppings
                // Call it
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
            if (s.equals("Change Slushy")) {
                boolean isASlushy = isSlushy2.isSelected();
                // TODO:
                // Create SQL query that can change bool is or isnt slushy
                // Call it
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

      changeName.addActionListener(actionListener);
      changeIngredients.addActionListener(actionListener);
      changeToppings.addActionListener(actionListener);
      changeMedPrice.addActionListener(actionListener);
      changeLrgPrice.addActionListener(actionListener);
      changeRecipePrice.addActionListener(actionListener);
      changeSlushy.addActionListener(actionListener);

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

      JPanel changeInventoryPanel = new JPanel();
      JPanel viewDrinksPanel = new JPanel();
      JPanel managerInventoryPanel = new JPanel();
      
      String[] columnNames = {"Ingredient_ID", "Ingredient_Name", "Unit_Price", "Stock"};
      // Make a JTable out of the data returned from function in Application.java
      Object[][] data = app.getIngredients();
      JTable inventoryTable = new JTable(data, columnNames);
      JScrollPane inventoryScrollPane = new JScrollPane(inventoryTable);
      managerInventoryPanel.add(inventoryScrollPane);

      String[] columnNamesToppings = {"Topping_ID", "Topping_Name", "Unit_Price", "Stock"};
      // Make a JTable out of the data returned from function in Application.java
      Object[][] dataToppings = app.getToppings();
      JTable inventoryTable2 = new JTable(dataToppings, columnNamesToppings);
      JScrollPane inventoryScrollPaneToppings = new JScrollPane(inventoryTable2);
      managerInventoryPanel.add(inventoryScrollPaneToppings);

      //make manageractionspanel
      JPanel managerActionsPanel = new JPanel();
      addDrinkPanel.setLayout(new BoxLayout(addDrinkPanel, BoxLayout.Y_AXIS));
      addDrinkPanel.add(Box.createVerticalStrut(10));
      changeDrinkPanel.setLayout(new BoxLayout(changeDrinkPanel, BoxLayout.Y_AXIS));
    //   changeDrinkPanel.add(Box.createVerticalStrut(47));
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


      // Add Drink alignment
      isSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);
      isNotSlushy.setAlignmentX(Component.LEFT_ALIGNMENT);

      // Modify Drink alignment
      isSlushy2.setAlignmentX(Component.LEFT_ALIGNMENT);
      isNotSlushy2.setAlignmentX(Component.LEFT_ALIGNMENT);
      
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
      slushieOptions2.add(isSlushy2);
      slushieOptions2.add(isNotSlushy2);
      changeDrinkPanel.add(isSlushy2);
      changeDrinkPanel.add(isNotSlushy2);
      changeDrinkPanel.add(changeSlushy);
      changeDrinkPanel.add(mediumLabel2);
      changeDrinkPanel.add(mediumPrice2);
      changeDrinkPanel.add(changeMedPrice);
      changeDrinkPanel.add(largeLabel2);
      changeDrinkPanel.add(largePrice2);
      changeDrinkPanel.add(changeLrgPrice);
      changeDrinkPanel.add(recipeLabel2);
      changeDrinkPanel.add(recipePrice2);
      changeDrinkPanel.add(changeRecipePrice);

      // Create the new drink button
      JButton newDrinkButton = new JButton("Add New Drink");
      newDrinkButton.addActionListener(actionListener);
      addDrinkPanel.add(newDrinkButton);

      managerActionsPanel.add(addDrinkPanel, BorderLayout.WEST);
      managerActionsPanel.add(changeDrinkPanel, BorderLayout.EAST);

      managerTabbedPane.addTab("Inventory", null, managerInventoryPanel, "Does nothing");
      managerTabbedPane.addTab("Add/Update Inventory Items", null, changeInventoryPanel, "Does nothing");
      managerTabbedPane.addTab("Drinks", null, viewDrinksPanel, "Does nothing");
      managerTabbedPane.addTab("Add/Modify Drink", null, managerActionsPanel, "Does nothing");

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

    //TODO: DRINK FUNCTIONALITY
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
                JDialog dialog = new JDialog();
                dialog.setTitle("Edit Toppings");
                dialog.setLayout(new BorderLayout());

                JPanel toppingsPanel = new JPanel();
                toppingsPanel.setLayout(new BoxLayout(toppingsPanel, BoxLayout.Y_AXIS));

                // Sample toppings (you can replace this with your actual toppings)
                //TODO: query database for toppings and topping prices
                //create string of available toppings based on database toppings.
                //vector of toppings
                Vector<String> availableToppings = new Vector<String>();

                for (Topping top : app.toppings) {
                    availableToppings.add(top.getToppingName());
                }
                // String[] availableToppings = {"Sugar", "Milk", "Honey", "Caramel"};

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
                        int topping_price = 0;
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
                                
                                topping_price += newTopping.topping.unit_price * quantity;
                                minitoppanel.add(new JLabel(topping + ": " + quantity));

                                selectedToppings.add(newTopping);
                                selectedToppingsQuantity.add(quantity);
                            }
                        }
                        //update the subtotal and total
                        subtotal += topping_price;
                        total = subtotal + tip;
                        System.out.print("subtotal: " + subtotal + " total: " + total);
                        //update subtotal and total labels
                        subtotalLabel.setText("Subtotal: $" + subtotal);
                        totalLabel.setText("Total: $" + total);

                        itemListPanel.revalidate();
                        itemListPanel.repaint();
                        // Close the dialog
                        newDrink.toppings = selectedToppings;
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

    app.addDrink(itemButton.getItemID(), "note", size, iceResult, sugarResult, subtotal, selectedToppings, selectedToppingsQuantity);

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

  JLabel CreateNewLabel(String text) {
    JLabel label = new JLabel(text);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    return label;
  }

  JTextField CreateNewTextField() {
      JTextField textField = new JTextField();
      textField.setAlignmentX(Component.LEFT_ALIGNMENT);
      textField.setMinimumSize(new Dimension(200, 20));
      return textField;
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

    public int getItemID() {
        return recipe.getRecipeID();
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

    public Recipe getRecipe() {
        return recipe;
    }
}

//make a class for toppings
class _topping{
    String name;
    Topping topping;
    int quantity;

    public _topping(Topping topping, String name, int quantity){
        this.topping = topping;
        this.name = name;
        this.quantity = quantity;

    }
}

class _drink{
    Recipe recipe;
    ArrayList<_topping> toppings;
    boolean is_medium;
    int ice;
    int sugar;
    double price; //price of drink is calculated using recipe price and topping prices

    public _drink(Recipe recipe, boolean is_medium,int ice, int sugar){
        this.recipe = recipe;
        this.is_medium = is_medium;
        this.ice = ice;
        this.sugar = sugar;
        if (is_medium) {
            price = recipe.getMediumPrice();
        }
        else{
            price = recipe.getLargePrice();
        }
    }

    public void updateprice(){
        if(is_medium){
            price = recipe.getMediumPrice();
        }
        else{
            price = recipe.getLargePrice();
        }

        for(_topping topping : toppings){
            price += topping.quantity * topping.topping.unit_price; 
        }
    }
}


