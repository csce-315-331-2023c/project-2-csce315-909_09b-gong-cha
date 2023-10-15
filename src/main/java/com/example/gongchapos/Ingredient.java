package com.example.gongchapos;

/**
 * Represents an ingredient with associated attributes like ID, name, stock, and unit price.
 * 
 * @author Reid Jenkins, Anton Ramirez
 */
public class Ingredient {
    private int ingredient_id;
    private String ingredient_name;
    private int stock;
    private double unit_price;

    /**
     * Constructor for Ingredient.
     *
     * @param _ingredient_id The ID of the ingredient.
     * @param _ingredient_name The name of the ingredient.
     * @param _stock The current stock of the ingredient.
     * @param _unit_price The unit price of the ingredient.
     */
    public Ingredient(int _ingredient_id, String _ingredient_name, int _stock, double _unit_price)
    {
        // Initialize the ingredient attributes
        ingredient_id = _ingredient_id;
        ingredient_name = _ingredient_name;
        stock = _stock;
        unit_price = _unit_price;
    }

    public Ingredient(int _ingredient_id, String _ingredient_name, int _stock)
    {
        // Initialize the ingredient attributes
        ingredient_id = _ingredient_id;
        ingredient_name = _ingredient_name;
        stock = _stock;
    }
    
    /**
     * Get the ID of the ingredient.
     *
     * @return The ingredient ID.
     */
    public int getID(){
        return this.ingredient_id;
    }

    /**
     * Get the name of the ingredient.
     *
     * @return The ingredient name.
     */
    public String getName(){
        return this.ingredient_name;
    }

    /**
     * Get the current stock of the ingredient.
     *
     * @return The current stock.
     */
    public int getStock(){
        return this.stock;
    }

    /**
     * Get the unit price of the ingredient.
     *
     * @return The unit price.
     */
    public double getUnitPrice(){
        return this.unit_price;
    }

    /**
     * Sets the unit price
     * @param price the unit price
     */
    public void setUnitPrice(double price){
        unit_price = price;
    }
}