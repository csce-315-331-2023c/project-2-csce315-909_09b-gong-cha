package com.example.gongchapos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.time.*;

/**
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
   * @return the order status
   */
  public boolean getOrderStatus()
  {
    return isNewOrder;
  }

  /**
   *  @param status - the status of the order
   *  @return void
   */
  public void setOrderStatus(boolean status)
  {
    isNewOrder = status;
  }

  /**
   * @param netID - the netID of the user
   * @param password - the password of the user
   * @return void
   */
  public void run(String netID, String password)
  {
    ConnectToDatabase(netID, password);
    populate();
    order_id = newOrderID();
    item_id = newItemID();
    gui = new GUI(this);
  }

 
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

  /*
  * closes the connection to the database
  * @param none
  * @return void
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

  /*
   * Populates the recipes and toppings lists with data from the database
   * @param none
   * @return void
   */
  private void populate()
  {
    populateRecipes();
    populateToppings();
  }

  /*
   * Populates the recipes list with data from the database
   * @param none
   * @return void
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

  /*
   * Populates the toppings list with data from the database
   * @param none
   * @return void
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

  /*
   * @return the next recipe_id
   * @param none
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

  /*
  * Creates a new recipe and adds it to the database
  * @param recipe_name - the name of the recipe
  * @param is_slush - whether or not the recipe is a slush
  * @param med_price - the price of a medium drink
  * @param large_price - the price of a large drink
  * @param recipe_price - the price of the recipe
  * @param ingredinets - the ingredients used in the recipe
  * @param ingredients_quantity - the quantity of each ingredient used in the recipe
  * @param toppings_array - the toppings used in the recipe
  * @param toppings_quantity - the quantity of each topping used in the recipe
  * @return void
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

  /* 
  *  Returns a recipe given a name, returns null if recipe not found
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

  /* 
  * Retuns a recipe given a recipe_id, returns null if recipe not found
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

  /* 
   * Returns a topping given a topping_name, returns null if topping not found
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
  
  /*adds ingredients into recipe_ingredients 
  * @param name - the name of the ingredient
  * @param price - the price of the ingredient
  * @param stock - the stock of the ingredient
  * @return void
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


  /* adds toppings into recipe_toppings
  * @param name - the name of the topping
  * @param price - the price of the topping
  * @param stock - the stock of the topping
  * @return void
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


  /* update recipe med_price
  * @param recipe_id - the id of the recipe
  * @param new_quantity - the new quantity of the recipe
  * @return void
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
  /*
   * updates recipe large_price
   * @param recipe_id - the id of the recipe
   * @param new_quantity - the new quantity of the recipe
   * @return void
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

  /*
   * updates recipe recipe_price
   * @param recipe_id - the id of the recipe
   * @param new_quantity - the new quantity of the recipe
   * @return void
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
   * @return void
   */
  // update ingredient stock 
  public void updateIngredientStock(int ingredient_id, int new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET stock = " + new_quantity + "WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  public void updateIngredientName(int ingredient_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET ingredient_name = '" + new_name + "' WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  public void updateIngredientUnitPrice(int ingredient_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET unit_price = " + new_quantity + " WHERE ingredient_id = " + ingredient_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  public void updateToppingsStock(int topping_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET stock = " + new_quantity + "WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  public void updateToppingsName(int topping_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET topping_name = '" + new_name + "' WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

  public void updateToppingsUnitPrice(int topping_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE toppings SET unit_price = " + new_quantity + " WHERE topping_id = " + topping_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  

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

  public Order getOrder()
  {
    return order;
  }

  public void addDrink(int recipe_ID, String notes, boolean is_medium, int ice, int sugar, double subtotal, List<String> toppings_used, List<Integer> toppings_used_quantity)
  {
    Recipe recipe = getRecipe(recipe_ID);

    Drink drink = new Drink(recipe, notes, is_medium, ice, sugar);
    drink.setOrderItemID(item_id);
    item_id++;
    if(getOrderStatus())
    {
        setOrderStatus(false);
        order = createNewOrder();
    }
    System.out.println("Order ID: " + order.getOrderID());
    
    order.setSubtotal(subtotal);
    order.addOrderItem(drink);

    for(int i = 0; i < toppings_used.size(); i++)
    {
      Topping topping = getTopping(toppings_used.get(i));
      orderItemToppings orderItemTopping = new orderItemToppings(drink.getOrderItemID(), topping.getToppingId(), toppings_used_quantity.get(i));

       try
        {
          Statement stmt = conn.createStatement();
          stmt.execute("INSERT INTO order_item_toppings VALUES('" + orderItemTopping.getOrderItemID() + "','" + orderItemTopping.getToppingId() + "','" + orderItemTopping.getQuantityUsed() + "');");
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Error accessing Database");
        }
    }
  }

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

    for(Drink current_drink : order.order_items)
    {
      try
      {
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO order_item VALUES ('" + current_drink.getOrderItemID() + "','" + current_drink.getRecipeID() + "','" + order.getOrderID() + "','" + current_drink.getNotes() + "','" + current_drink.isMedium() + "','" + current_drink.getIce() + "','" + current_drink.getSugar() + "','" + current_drink.getItemPrice() + "');" );
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database");
      }

      

    }
    setOrderStatus(true);
  }

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
        String stock = String.valueOf(result.getDouble("stock"));

        cur_ingredient.add(ingredient_id);
        cur_ingredient.add(ingredient_name);
        cur_ingredient.add(unit_price);
        cur_ingredient.add(stock);
        tempContainer.add(cur_ingredient);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][4];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[4];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

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
        String stock = String.valueOf(result.getDouble("stock"));

        cur_ingredient.add(topping_id);
        cur_ingredient.add(topping_name);
        cur_ingredient.add(unit_price);
        cur_ingredient.add(stock);
        tempContainer.add(cur_ingredient);

      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  
    Object[][] toReturn = new Object[tempContainer.size()][4];
    for(int i = 0; i < tempContainer.size(); i++){
      ArrayList<String> cur_arr = tempContainer.get(i);
      Object[] cur = new Object[4];
      cur = cur_arr.toArray();
      toReturn[i] = cur; // error here
    }
    return toReturn;  
  }

  public void updateRecipeName(int recipe_id, String new_name){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET recipe_name  = '" + new_name + "' WHERE recipe_id = '" + recipe_id + "';");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

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


  
}
