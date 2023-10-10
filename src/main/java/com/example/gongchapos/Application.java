package com.example.gongchapos;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Application {

  GUI gui;
  List<Recipe> recipes = new ArrayList<Recipe>();
  Connection conn = null;



  public void run(String netID, String password)
  {
    ConnectToDatabase(netID, password);
    populateRecipes();
    gui = new GUI(this);
  }

  protected void ConnectToDatabase(String netID, String password)
  {
    try {
      conn = DriverManager.getConnection(
        "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_09b_db",
        "csce315_909_" + netID,
        password);
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
      JOptionPane.showMessageDialog(null,"Connection Closed.");
    } catch(Exception e) {
      JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
    }
  }

  public String BasicQuery(String query)
  {
    String output = "";
    try
    {
      Statement stmt = conn.createStatement();
      ResultSet result = stmt.executeQuery(query);
      while (result.next()) 
      {
        output += result.getString("order_id")+"\n";
      }
    } catch (Exception e){
        JOptionPane.showMessageDialog(null,"Error accessing Database");
    }

    return output;
  }

  private void populateRecipes()
  {
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

  // creates a new recipe and inserts it into the database
  public void createRecipe(int recipe_id, String recipe_name, boolean is_slush, double med_price, double large_price, double recipe_price)
  {
    Recipe newRecipe = new Recipe(recipe_id, recipe_name, is_slush, med_price, large_price, recipe_price);
    recipes.add(newRecipe);
    try
    {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("INSERT INTO Recipe VALUES(" + recipe_id + recipe_name + is_slush + med_price + large_price + recipe_price + ");");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
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



 // TODO: need to adjust
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



  // update recipe med_price
  public void updateMedPrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE recipe SET med_price =" + new_quantity + "WHERE recipe_id =" + recipe_id);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }
  // updates recipe large_price
  public void updateLargePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE recipe SET large_price =" + new_quantity + "WHERE recipe_id =" + recipe_id);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  // updates recipe recipe_price
  public void updateRecipePrice(int recipe_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE recipe SET recipe_price =" + new_quantity + "WHERE recipe_id =" + recipe_id);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }

  // update ingredient stock 
  public void updateIngredientStock(int ingredient_id, double new_quantity){
    try
    {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE ingredient SET stock =" + new_quantity + "WHERE ingredient_id =" + ingredient_id);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error accessing Database");
    }
  }  


}
