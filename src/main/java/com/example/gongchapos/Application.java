package com.example.gongchapos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.time.*;

public class Application {

  protected GUI gui;
  protected List<Recipe> recipes = new ArrayList<Recipe>();
  protected List<Topping> toppings = new ArrayList<Topping>();
  protected Connection conn = null;
  private Order order = null;

  private boolean isNewOrder = true;


  public boolean getOrderStatus()
  {
    return isNewOrder;
  }

  public void setOrderStatus(boolean status)
  {
    isNewOrder = status;
  }

  public void run(String netID, String password)
  {
    ConnectToDatabase(netID, password);
    populate();
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

  private void populate()
  {
    populateRecipes();
    populateToppings();
  }

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

  // creates a new recipe and inserts it into the database
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
    
  }

  // Retuns a recipe given a name, returns null if recipe not found
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

  // Retuns a recipe given a recipe_id, returns null if recipe not found
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
  
  // adds ingredients into recipe_ingredients
  public void addIngredients(ArrayList<recipeIngredient> ingredients){

    for(recipeIngredient cur_ingredient: ingredients){
      try
      {
        Statement stmt = conn.createStatement();
        stmt.executeQuery("INSERT INTO recipe_ingredient VALUES (" + cur_ingredient.getRecipeID() + cur_ingredient.getIngredientID() + cur_ingredient.getQuantityUsed() + ")" );
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database");
      }
    }
  }


  // adds toppings into recipe_toppings
  // public void addToppings(ArrayList<Topping> toppings){
  //   for(Topping cur_topping: toppings){

  //   }
  // }


  // update recipe med_price
  public void updateMedPrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET med_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }
  // updates recipe large_price
  public void updateLargePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET large_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  // updates recipe recipe_price
  public void updateRecipePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE recipe SET recipe_price =" + new_quantity + "WHERE recipe_id =" + recipe_id + ";");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  // update ingredient stock 
  public void updateIngredientStock(int ingredient_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.execute("UPDATE ingredient SET stock =" + new_quantity + "WHERE ingredient_id =" + ingredient_id + ";");
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
    int ID = newOrderID();
    if(ID != -1)
    {
      order.setOrderID(ID);
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
    drink.setOrderItemID(newItemID());

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
      ResultSet result = stmt.executeQuery("SELECT * FROM ingredient;");
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
      ResultSet result = stmt.executeQuery("SELECT * FROM toppings;");
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



  
}
