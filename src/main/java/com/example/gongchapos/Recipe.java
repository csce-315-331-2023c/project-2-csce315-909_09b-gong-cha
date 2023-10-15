package com.example.gongchapos;

import java.util.*;


/**
 * Represents a recipe with associated attributes like ID, name, slush status, medium and large prices, and recipe price.
 * 
 * @author Reid Jenkins
 */
public class Recipe extends Application{
    private int recipe_id;
    private String recipe_name;
    private boolean is_slush;
    private double med_price;
    private double large_price;
    private double recipe_price;

    protected Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();

    /**
     * Constructor for Recipe.
     *
     * @param _recipe_id The ID of the recipe.
     * @param _recipe_name The name of the recipe.
     * @param _is_slush The slush status of the recipe.
     * @param _med_price The price for a medium size.
     * @param _large_price The price for a large size.
     * @param _recipe_price The overall recipe price.
     */
    public Recipe(int _recipe_id, String _recipe_name, boolean _is_slush, double _med_price, double _large_price, double _recipe_price)
    {
        recipe_id = _recipe_id;
        recipe_name = _recipe_name;
        is_slush = _is_slush;
        med_price = _med_price;
        large_price = _large_price;
        recipe_price = _recipe_price;

    }

    /**
     * Get the ID of the recipe.
     *
     * @return The recipe ID.
     */
    public int getRecipeID()
    {
        return recipe_id;
    }

    /**
     * Get the name of the recipe.
     *
     * @return The recipe name.
     */
    public String getRecipeName()
    {
        return recipe_name;
    }

    /**
     * Check if the recipe is a slush.
     *
     * @return True if it's a slush, false otherwise.
     */
    public boolean isSlush()
    {
        return is_slush;
    }

    /**
     * Get the price for a medium size of the recipe.
     *
     * @return The price for a medium size.
     */
    public double getMediumPrice()
    {
        return med_price;
    }

    /**
     * Get the price for a large size of the recipe.
     *
     * @return The price for a large size.
     */
    public double getLargePrice()
    {
        return large_price;
    }

    /**
     * Get the overall price of the recipe.
     *
     * @return The recipe price.
     */
    public double getRecipePrice()
    {
        return recipe_price;
    }
}