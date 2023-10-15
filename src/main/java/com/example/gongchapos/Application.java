package com.example.gongchapos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.time.*;

/**
 * This represents the opening of our application as well as the connection
 * of the application to the database
 * 
 * Author: @author Anton Hugo
 * Author2: @author Reid Jenkins
 */
public class Application {
  protected GUI gui;
  protected List<Recipe> recipes = new ArrayList<Recipe>();
  protected List<Topping> toppings = new ArrayList<Topping>();
  protected Connection conn = null;
  private int order_id = -1;
  private int item_id = -1;
  private Order order = null;

  private boolean isNewOrder = true;

  /**
   *  @return the order status
   */
  public boolean getOrderStatus()
  {
    return isNewOrder;
  }

  /**  
   * @param status - the status of the order
   */
  public void setOrderStatus(boolean status)
  {
    isNewOrder = status;
  }

  /** 
   * @param netID - the netID of the user
   * @param password - the password of the user
   */
  public void run(String netID, String password)
  {
    ConnectToDatabase(netID, password);
    populate();
    order_id = newOrderID();
    item_id = newItemID();
    gui = new GUI(this);
  }

  /**
   * Connects to AWS database by taking in the according netID and password
   * @param netID - Your given TAMU netID
   * @param password - Your password with the associated netID
   */
  protected void ConnectToDatabase(String netID, String password)
  {
    try {
      conn = DriverManager.getConnection(
        "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_09b_db",
        "csce315_909_" + netID,
        password);
        System.out.println("Connected to database successfully");
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName()+": "+e.getMessage());
      System.exit(0);
    }
  }

  /**
   * closes the connection to the database
   */
  protected void closeDatabase()
  {
    //closing the connection
    try {
      conn.close();
      System.out.println("Connectin Closed");
    } catch(Exception e) {
      System.out.println("Connection not closed");
    }
  }

  /**
   * Populates the recipes and toppings lists with data from the database
   * 
   */
  private void populate()
  {
    populateRecipes();
    populateToppings();
  }

  /**
   * Populates the recipes list with data from the database
   * 
   */
  private void populateRecipes()
  {
    recipes.clear();
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM Recipe;");
      while(result.next())
      {
        Recipe currentRecipe = new Recipe(result.getInt("Recipe_ID"), 
                                          result.getString("Recipe_Name"),
                                          result.getBoolean("Is_Slush"),
                                          result.getDouble("Med_Price"),
                                          result.getDouble("Large_Price"),
                                          result.getDouble("Recipe_Price"));

        recipes.add(currentRecipe);
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  /**
   * Populates the toppings list with data from the database
   */
  private void populateToppings()
  {
    toppings.clear();
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings;");
      while(result.next())
      {
        Topping currentTopping = new Topping(result.getInt("topping_id"),
                                             result.getString("topping_name"),
                                             result.getInt("stock"),
                                             result.getDouble("unit_price"));

        toppings.add(currentTopping);
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database Toppings");
    }
  }

  /** Finds the recipeID for the new drink
   * @return the next recipe_id
   */
  private int newRecipeID()
  {
    int recipe_id = -1;
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM recipe ORDER BY recipe_id DESC LIMIT 1;");
      while(result.next())
      {
        recipe_id = result.getInt("recipe_id") + 1;
      }
    } catch (Exception e){
      JOptionPane.showMessageDialog(null,"Error accessing Database");
    }

      return recipe_id;
  }

  /** Creates a new recipe and adds it to the database
  * @param recipe_name - the name of the recipe
  * @param is_slush - whether or not the recipe is a slush
  * @param med_price - the price of a medium drink
  * @param large_price - the price of a large drink
  * @param recipe_price - the price of the recipe
  * @param ingredinets - the ingredients used in the recipe
  * @param ingredients_quantity - the quantity of each ingredient used in the recipe
  * @param toppings_array - the toppings used in the recipe
  * @param toppings_quantity - the quantity of each topping used in the recipe
  */
  public void createRecipe(String recipe_name, boolean is_slush, double med_price, double large_price, double recipe_price, ArrayList<String> ingredinets, 
                           ArrayList<String> ingredients_quantity, ArrayList<String> toppings_array, ArrayList<String> toppings_quantity)
  {
    int recipe_id = newRecipeID();
    Recipe newRecipe = new Recipe(recipe_id, recipe_name, is_slush, med_price, large_price, recipe_price);
    recipes.add(newRecipe);
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO recipe VALUES('" + recipe_id + "','" + recipe_name + "','" + is_slush + "','" + med_price + "','" + large_price + "','" + recipe_price + "');");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }

    for(int i = 0; i < ingredinets.size(); i++)
    {
      int ingredient_id = -1;
      try
      {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM ingredient WHERE ingredient_name = '" + ingredinets.get(i).strip() + "';");
        while(result.next())
        {
          ingredient_id = result.getInt("ingredient_id");
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database 2");
      }

      recipeIngredient recipeIngredient = new recipeIngredient(recipe_id, ingredient_id, Integer.parseInt(ingredients_quantity.get(i).strip()));
      
      try
      {
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO recipe_ingredient VALUES('" + recipeIngredient.getRecipeID() + "','" + recipeIngredient.getIngredientID() + "','" + recipeIngredient.getQuantityUsed() + "');");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database");
      }
    }

    for(int i = 0; i < toppings_array.size(); i++)
    {
      
      Topping topping = getTopping(toppings_array.get(i).strip());
      recipeToppings recipeTopping = new recipeToppings(recipe_id, topping.getToppingId(), Integer.parseInt(toppings_quantity.get(i)));
      
      try
      {
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO recipe_toppings VALUES('" + recipeTopping.getRecipeID() + "','" + recipeTopping.getToppingId() + "','" + recipeTopping.getQuantityUsed() + "');");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database 4");
      }
    }
    populate();
  }

  /**  Returns a recipe given a name, returns null if recipe not found
  *  @param name - the name of the recipe
  *  @return Recipe
  */ 
  public Recipe getRecipe(String name)
  {
    Recipe outRecipe = null;
    for(Recipe currentRecipe : recipes)
    {
      if(currentRecipe.getRecipeName() == name)
      {
        outRecipe = currentRecipe;
        break;
      }
    }

    return outRecipe;
  }

  /** Retuns a recipe given a recipe_id, returns null if recipe not found
  * @param recipe_id - the id of the recipe
  * @return Recipe
  */
  public Recipe getRecipe(int recipe_id)
  {
    Recipe outRecipe = null;
    for(Recipe currentRecipe : recipes)
    {
      if(currentRecipe.getRecipeID() == recipe_id)
      {
        outRecipe = currentRecipe;
        break;
      }
    }

    return outRecipe;
  }

  /** Returns a topping given a topping_name, returns null if topping not found
   * @param topping_name - the name of the topping
   * @return Topping
  */
  public Topping getTopping(String topping_name)
  {
    Topping outTopping = null;
    for(Topping currentTopping : toppings)
    {
      if(topping_name.equals(currentTopping.getToppingName()))
      {
        outTopping = currentTopping;
        break;
      }
    }

    return outTopping;
  }
  
  /** adds ingredients into recipe_ingredients 
  * @param name - the name of the ingredient
  * @param price - the price of the ingredient
  * @param stock - the stock of the ingredient
  */ 
  public void addIngredients(String name, double price, int stock)
  {
    int ingredient_id = -1;
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM ingredient ORDER BY ingredient_id DESC LIMIT 1;");
      while(result.next())
      {
        ingredient_id = result.getInt("ingredient_id") + 1;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");  
    }
    
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO ingredient VALUES ('" + ingredient_id + "','" + name + "','" + price + "','" + stock + "');" );
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
    
  }


  /** adds toppings into recipe_toppings
  * @param name - the name of the topping
  * @param price - the price of the topping
  * @param stock - the stock of the topping
  */ 
  public void addToppings(String name, double price, int stock){
    int topping_id = -1;
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings ORDER BY topping_id DESC LIMIT 1;");
      while(result.next())
      {
        topping_id = result.getInt("topping_id") + 1;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");  
    }
    
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO toppings VALUES ('" + topping_id + "','" + name + "','" + price + "','" + stock + "');" );
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }


  /** update recipe med_price
  * @param recipe_id - the id of the recipe
  * @param new_quantity - the new quantity of the recipe
  */ 
  public void updateMedPrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET med_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }
  /** updates recipe large_price
   * @param recipe_id - the id of the recipe
   * @param new_quantity - the new quantity of the recipe
   */
  public void updateLargePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET large_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  /** updates recipe recipe_price
   * @param recipe_id - the id of the recipe
   * @param new_quantity - the new quantity of the recipe
   */
  public void updateRecipePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET recipe_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  /**
   * update ingredient stock 
   * @param ingredient_id - the id of the ingredient
   * @param new_quantity - the new quantity of the ingredient
   */
  public void updateIngredientStock(int ingredient_id, int new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET stock = " + new_quantity + "WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  /**
   * update ingredient name
   * @param ingredient_id  - the id of the ingredient
   * @param new_name - the new name of the ingredient
   */
  public void updateIngredientName(int ingredient_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET ingredient_name = '" + new_name + "' WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  /**
   * update ingredient unit price
   * @param ingredient_id  - the id of the ingredient
   * @param new_quantity - the new unit price of the ingredient
   */
  public void updateIngredientUnitPrice(int ingredient_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET unit_price = " + new_quantity + " WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  


  /**
   * update topping stock
   * @param topping_id  - the id of the topping
   * @param new_quantity - the new quantity of the topping
   */
  public void updateToppingsStock(int topping_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET stock = " + new_quantity + "WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  /**
   * update topping name
   * @param topping_id - the id of the topping
   * @param new_name - the new name of the topping
   */
  public void updateToppingsName(int topping_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET topping_name = '" + new_name + "' WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  /**
   * update topping unit price
   * @param topping_id - the id of the topping
   * @param new_quantity - the new quantity of the topping
   */
  public void updateToppingsUnitPrice(int topping_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET unit_price = " + new_quantity + " WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  /**
   * generate new order id
   * @return int
   */
  private int newOrderID()
  {
    int order_id = -1;
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM order_ ORDER BY Order_ID DESC LIMIT 1;");
      while(result.next())
      {
        order_id = result.getInt("Order_ID") + 1;
      }
    } catch (Exception e){
      JOptionPane.showMessageDialog(null,"Error accessing Database");
    }

      return order_id;
    }

  
  /**
   * generate new item id
   * @return int
   */
  private int newItemID()
  {
    int order_id = -1;
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM order_item ORDER BY order_item_id DESC LIMIT 1;");
      while(result.next())
      {
        order_id = result.getInt("order_item_id") + 1;
      }
      } catch (Exception e){
        JOptionPane.showMessageDialog(null,"Error accessing Database");
      }
      return order_id;
    }

  
  /**
   * create a new order function
   * @return Order
   */
  public Order createNewOrder()
  {
    LocalDate date = LocalDate.now();

    order = new Order(date.toString());
    int ID = order_id;
    order_id++;
    if(ID != -1)
    {
      order.setOrderID(ID);
      setOrderStatus(false);
    }
    
    return order;
  }

  /**
   * get current order
   * @return The current Order
   */
  public Order getOrder()
  {
    return order;
  }

  /**
   * add drink into order
   * @param recipe_ID - the id of the recipe
   * @param notes - any notes given to the cashier
   * @param is_medium - boolean for if it is a mediums sized drink
   * @param ice - the ice level of the drink
   * @param sugar - the sugar level of the drink
   * @param subtotal - the subtotal of the drik
   * @param toppings_used - any topping used in the drink
   * @param toppings_used_quantity - how much of each topping was used
   */
  public void addDrink(int recipe_ID, String notes, boolean is_medium, int ice, int sugar, double subtotal, List<String> toppings_used, List<Integer> toppings_used_quantity)
  {
    Recipe recipe = getRecipe(recipe_ID);

    Drink drink = new Drink(recipe, notes, is_medium, ice, sugar);
    drink.setOrderItemID(item_id);

    if(getOrderStatus())
    {
        setOrderStatus(false);
        order = createNewOrder();
    }
    
    order.setSubtotal(subtotal);
    order.addOrderItem(drink);

    for(int i = 0; i < toppings_used.size(); i++)
    {
      Topping topping = getTopping(toppings_used.get(i));
      drink.insertTopping(topping, toppings_used_quantity.get(i));
    }
  }

  /**
   * place order including the tip and coupon
   * @param tip - tip for the order
   * @param coupon - coupon code
   */
  public void placeOrder(double tip, String coupon)
  {
    order.setTip(tip);
    order.setCouponCode(coupon);

    LocalTime time = LocalTime.now();
    order.setTime(time.toString().substring(0, time.toString().length() - 7));
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO order_ VALUES ('" + order.getOrderID() + "','" + order.getDate() + "','" + order.getSubtotal() + "','" + order.getTip() + "','" + order.getCouponCode() + "','" + order.getTime() + "');");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }

    for(Drink current_drink : order.getOrderItems())
    {
      try
      {
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO order_item VALUES ('" + current_drink.getOrderItemID() + "','" + current_drink.getRecipeID() + "','" + order.getOrderID() + "','" + current_drink.getNotes() + "','" + current_drink.isMedium() + "','" + current_drink.getIce() + "','" + current_drink.getSugar() + "','" + current_drink.getItemPrice() + "');" );
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database");
      }

      for(Map.Entry<Topping,Integer> current_entry : current_drink.getToppingsUsed().entrySet())
      {
        orderItemToppings orderItemTopping = new orderItemToppings(current_drink.getOrderItemID(), current_entry.getKey().getToppingId(), current_entry.getValue());

       try
        {
          Statement stmt = conn.createStatement();
          stmt.execute("INSERT INTO order_item_toppings VALUES('" + orderItemTopping.getOrderItemID() + "','" + orderItemTopping.getToppingId() + "','" + orderItemTopping.getQuantityUsed() + "');");
        } catch (Exception e) {
          System.out.println(e);
          JOptionPane.showMessageDialog(null, "Error accessing Database 0");
        }
      }

      

    }
    setOrderStatus(true);
  }

  /**
   * 
   * @return Object[][] of ingredients
   */
  public Object[][] getIngredients(){

    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM ingredient ORDER BY ingredient_id ASC;");
      while(result.next())
      {
        ArrayList<String> cur_ingredient = new ArrayList<String>();
        String ingredient_id = String.valueOf(result.getInt("ingredient_id"));
        String ingredient_name = result.getString("ingredient_name");
        String unit_price = String.valueOf(result.getDouble("unit_price"));
        String stock = String.valueOf(result.getInt("stock"));
        String min_quantity = String.valueOf(result.getInt("minimum_quantity"));

        cur_ingredient.add(ingredient_id);
        cur_ingredient.add(ingredient_name);
        cur_ingredient.add(unit_price);
        cur_ingredient.add(stock);
        cur_ingredient.add(min_quantity);
        tempContainer.add(cur_ingredient);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][5];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[5];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

  /**
   * get toppings table from sql
   * @return Object[][] of info of toppings
   */

  public Object[][] getToppings(){

    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings ORDER BY topping_id ASC;");
      while(result.next())
      {
        ArrayList<String> cur_ingredient = new ArrayList<String>();
        String topping_id = String.valueOf(result.getInt("topping_id"));
        String topping_name = result.getString("topping_name");
        String unit_price = String.valueOf(result.getDouble("unit_price"));
        String stock = String.valueOf(result.getInt("stock"));
        String min_quantity = String.valueOf(result.getInt("minimum_quantity"));

        cur_ingredient.add(topping_id);
        cur_ingredient.add(topping_name);
        cur_ingredient.add(unit_price);
        cur_ingredient.add(stock);
        cur_ingredient.add(min_quantity);
        tempContainer.add(cur_ingredient);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][5];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[5];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }
  /**
   * update recipe_name of a recipe_id
   * @param recipe_id - the id of the recipe
   * @param new_name - the new name of the new recipe
   */
  public void updateRecipeName(int recipe_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET recipe_name  = '" + new_name + "' WHERE recipe_id = '" + recipe_id + "';");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  /**
   * get info of recipe table
   * @return Object[][]
   */
  public Object[][] getRecipes(){
    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM recipe ORDER BY recipe_id ASC;");
      while(result.next())
      {
        ArrayList<String> cur_recipe = new ArrayList<String>();
        String recipe_id = String.valueOf(result.getInt("recipe_id"));
        String recipe_name = result.getString("recipe_name");
        String is_slush = String.valueOf(result.getBoolean("is_slush"));
        String med_price = String.valueOf(result.getDouble("med_price"));
        String large_price = String.valueOf(result.getDouble("large_price"));
        String recipe_price = String.valueOf(result.getDouble("recipe_price"));

        cur_recipe.add(recipe_id);
        cur_recipe.add(recipe_name);
        cur_recipe.add(is_slush);
        cur_recipe.add(med_price);
        cur_recipe.add(large_price);
        cur_recipe.add(recipe_price);

        tempContainer.add(cur_recipe);
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][6];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[6];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

  /**
   * get ingredient id of ingredient name
   * @param ingredient_name - the name of the ingredient
   * @return int
   */
  public int getIngredientId(String ingredient_name){
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM ingredient;");
      while(result.next())
      {
        if (result.getString("ingredient_name").equals(ingredient_name)){
          return result.getInt("ingredient_id");
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
    return 0;
  }

  /**
   * remove all ingredients of recipe, adds all new ingredients
   * @param recipe_id - the id of the recipe
   * @param ingredient_names - the ingredient names
   * @param quantities - the quantities of the recipe ingredients
   */
  public void modifyMultipleIngredients(int recipe_id, ArrayList<String> ingredient_names, ArrayList<Integer> quantities){

    // remove ingredients with recipe_id from recipe_ingredient
    try
    {
      Statement stmt = conn.createStatement();
      String query = "DELETE FROM recipe_ingredient WHERE recipe_id = '" + recipe_id + "';" ;
      PreparedStatement st = conn.prepareStatement(query);
      st.executeUpdate();
      System.out.println("deleted recipe ingredients");

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "deletion");
    }

    // obtain ingredient_id using ingredient_name from arraylist of ingredient_names
    ArrayList<Integer> ingredient_ids = new ArrayList<Integer>();
    for(String cur_name: ingredient_names){
      ingredient_ids.add(getIngredientId(cur_name));
      System.out.println("ID: " + getIngredientId(cur_name));
    }

    // insert into table new ingredients with recipe id and quantity
    for(int i = 0; i < quantities.size(); i++){
      try
      {
        Statement stmt = conn.createStatement();
        boolean result = stmt.execute("INSERT INTO recipe_ingredient VALUES('" + recipe_id +"', '" + ingredient_ids.get(i) + "', '" + quantities.get(i) + "');");
        System.out.println("inserted");
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "insertion");
      }
    }

  }


  /**
   * get topping id of topping name
   * @param topping_name - the name of the topping
   * @return the topping id int
   */
  public int getToppingId(String topping_name){
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings;");
      while(result.next())
      {
        if (result.getString("topping_name").equals(topping_name)){
          return result.getInt("topping_id");
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
    return 0;

  }

  /**
   * removes all toppings of topping_id, adds all new toppings
   * @param recipe_id - the id of the recipe
   * @param topping_names - the toppings in the recipe
   * @param quantities - the quantities of the toppings in the recipe
   */
  public void modifyMultipleToppings(int recipe_id, ArrayList<String> topping_names, ArrayList<Integer> quantities){

      // remove toppings with recipe_id from recipe_toppings
      try
      {
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM recipe_toppings WHERE recipe_id = '" + recipe_id + "';" ;
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
        System.out.println("deleted recipe toppings");

      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "deletion");
      }

      // obtain topping_id using topping_name from arraylist of topping_names
      ArrayList<Integer> topping_ids = new ArrayList<Integer>();
      for(String cur_name: topping_names){
        topping_ids.add(getToppingId(cur_name));
        System.out.println("ID: " + getToppingId(cur_name));
      }

      // insert into table new toppings with toppings id and quantity
      for(int i = 0; i < quantities.size(); i++){
        try
        {
          Statement stmt = conn.createStatement();
          boolean result = stmt.execute("INSERT INTO recipe_toppings VALUES('" + recipe_id +"', '" + topping_ids.get(i) + "', '" + quantities.get(i) + "');");
          System.out.println("inserted");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "insertion");
        }
      }

    }

  /**
   * report of all ingredients in which the current stock is lower than the minimum recommended amount
   * @return Object[][] of info of ingredients where stock < minimum_quantity
   */
  public Object[][] restockReportIngredients(){

    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM ingredient WHERE stock < minimum_quantity;");
      while(result.next())
      {
        ArrayList<String> cur_ingredient = new ArrayList<String>();
        String ingredient_id = String.valueOf(result.getInt("ingredient_id"));
        String ingredient_name = result.getString("ingredient_name");
        String unit_price = String.valueOf(result.getDouble("unit_price"));
        String stock = String.valueOf(result.getInt("stock"));
        String min_quantity = String.valueOf(result.getInt("minimum_quantity"));

        cur_ingredient.add(ingredient_id);
        cur_ingredient.add(ingredient_name);
        cur_ingredient.add(unit_price);
        cur_ingredient.add(stock);
        cur_ingredient.add(min_quantity);
        tempContainer.add(cur_ingredient);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][5];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[5];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

  /**
   * report of all toppings in which the current stock is lower than the minimum recommended amount
   * @return Object[][] of info of toppings where stock < minimum_quantity
   */
  public Object[][] restockReportToppings(){

    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings WHERE stock < minimum_quantity;");
      while(result.next())
      {
        ArrayList<String> cur_topping = new ArrayList<String>();
        String topping_id = String.valueOf(result.getInt("topping_id"));
        String topping_name = result.getString("topping_name");
        String unit_price = String.valueOf(result.getDouble("unit_price"));
        String stock = String.valueOf(result.getInt("stock"));
        String min_quantity = String.valueOf(result.getInt("minimum_quantity"));

        cur_topping.add(topping_id);
        cur_topping.add(topping_name);
        cur_topping.add(unit_price);
        cur_topping.add(stock);
        cur_topping.add(min_quantity);
        tempContainer.add(cur_topping);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][5];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[5];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

  /**
   * Given a timestamp, display the list of inventory items that only sold less than 
   * 10% of their inventory between the timestamp and the current time, assuming no restocks 
   * have happened during the window.
   * @return Object[][] of excess ingredients (i.e. the ones that have been used 10%)
   */
  
   /*
    Use the below query to get the ingredients that have been used in the timeframe;
    
    SELECT Ingredient.Ingredient_Name, Subquery.Total_Used
    FROM (
        Select Ingredient_Name, SUM(Quantity_Used) AS Total_Used
        FROM Recipe_Ingredient NATURAL JOIN Ingredient NATURAL JOIN Order_ NATURAL JOIN Order_Item
        WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
        GROUP BY Ingredient_Name
        ORDER BY Ingredient_Name
    ) AS Subquery, Ingredient
    WHERE Subquery.Total_Used < Ingredient.Stock * .1 AND Ingredient.Ingredient_Name = Subquery.Ingredient_Name
    ORDER BY Total_Used;

    */
   public Object[][] excess_report(){
    ArrayList<ArrayList<String>> tempContainer = new ArrayList<ArrayList<String>>();

    //first, query the database for the stock of inventory items.

    //second, query the database for the total amount of inventory items that have been used in the timeframe
    //third, compare the two values and return the ones that have been used less than 10% of their stock
    //format the output into a 2d array and return it
    
    return null;
   }
}
