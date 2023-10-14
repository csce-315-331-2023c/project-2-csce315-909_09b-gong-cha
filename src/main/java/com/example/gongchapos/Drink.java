package com.example.gongchapos;

import java.util.*;

/**
 * This class extends the Application class and represents a Drink object.
 * 
 * @author Reid Jenkins
 */
public class Drink extends Application{
    private int order_item_id; // Unique ID for this order item.
    private int recipe_id; // ID of the recipe used for this drink.
    private int order_id; // ID of the order this drink belongs to.
    private String Notes; // Additional notes for the drink.
    private boolean is_medium; // Indicates if the drink is of medium size.
    private String ice; // The type of ice used in the drink.
    private String sugar; // The level of sugar in the drink.
    private String[] ice_values = {"none", "light", "regular"}; // Possible values for ice.
    private String[] sugar_values = {"0%", "30%", "50%", "70%", "100%"}; // Possible values for sugar.
    private double item_price; // Price of the drink.

    private Map<Topping, Integer> used_toppings = new HashMap<Topping, Integer>(); // Map of toppings used in the drink.

    private Recipe recipe; // The recipe used for this drink.

    /**
     * Constructor for the Drink class.
     * @param _recipe The recipe for the drink.
     * @param _notes Additional notes for the drink.
     * @param _is_medium Indicates if the drink is medium-sized.
     * @param _ice Index representing the type of ice.
     * @param _sugar Index representing the sugar level.
     */
    public Drink(Recipe _recipe, String _notes, boolean _is_medium, int _ice, int _sugar)
    {
        recipe = _recipe;
        recipe_id = _recipe.getRecipeID();
        Notes = _notes;
        is_medium = _is_medium;
        ice = ice_values[_ice];
        sugar = sugar_values[_sugar];

        if(is_medium)
        {
            item_price = recipe.getMediumPrice();
        }
        else
        {
            item_price = recipe.getLargePrice();
        }
    }

    /**
     * Gets the unique ID of the order item.
     * @return The order item ID.
     */
    public int getOrderItemID()
    {
        return order_item_id;
    }

    /**
     * Sets the unique ID of the order item.
     * @param id The order item ID to set.
     */
    public void setOrderItemID(int id)
    {
        order_item_id = id;
    }

    /**
     * Gets the ID of the recipe.
     * @return The recipe ID.
     */
    public int getRecipeID()
    {
        return recipe_id;
    }

    /**
     * Sets the order ID.
     * @param id The order ID to set.
     */
    public void setOrderID(int id)
    {
        order_id = id;
    }

    /**
     * Gets the order ID.
     * @return The order ID.
     */
    public int getOrderID()
    {
        return order_id;
    }

    /**
     * Gets any additional notes for the drink.
     * @return The notes.
     */
    public String getNotes()
    {
        return Notes;
    }

    /**
     * Checks if the drink is medium-sized.
     * @return True if medium-sized, false otherwise.
     */
    public boolean isMedium()
    {
        return is_medium;
    }

    /**
     * Gets the type of ice used in the drink.
     * @return The type of ice.
     */
    public String getIce()
    {
        return ice;
    }

    /**
     * Gets the sugar level of the drink.
     * @return The sugar level.
     */
    public String getSugar()
    {
        return sugar;
    }

    /**
     * Gets the price of the drink.
     * @return The price of the drink.
     */
    public double getItemPrice()
    {
        return item_price;
    }

    /**
     * Inserts a topping into the drink.
     * @param topping The topping to insert.
     * @param quantity The quantity of the topping.
     */
    public void insertTopping(Topping topping, int quantity)
    {
        used_toppings.put(topping, quantity);
        item_price += (topping.getUnitPrice() * quantity);
    }

    /**
     * Gets the map of toppings used in the drink.
     * @return A map of toppings and their quantities.
     */
    public Map<Topping, Integer> getToppingsUsed()
    {
        return used_toppings;
    }
}
