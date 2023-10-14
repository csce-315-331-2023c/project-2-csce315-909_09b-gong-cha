package com.example.gongchapos;

/**
 * Represents a relationship between a recipe and a topping, including the quantity used.
 * 
 * @author Reid Jenkins
 */
public class recipeToppings {
    private int recipe_id;      // The ID of the associated recipe
    private int topping_id;     // The ID of the associated topping
    private int quantity_used;  // The quantity of the topping used
    
    /**
     * Constructs a new recipeToppings instance.
     *
     * @param _recipe_id The ID of the associated recipe.
     * @param _topping_id The ID of the associated topping.
     * @param _quantity_used The quantity of the topping used.
     */
    public recipeToppings(int _recipe_id, int _topping_id, int _quantity_used) {
        recipe_id = _recipe_id;
        topping_id = _topping_id;
        quantity_used = _quantity_used;
    }

    /**
     * Gets the ID of the associated recipe.
     *
     * @return The ID of the associated recipe.
     */
    public int getRecipeID() {
        return recipe_id;
    }

    /**
     * Gets the ID of the associated topping.
     *
     * @return The ID of the associated topping.
     */
    public int getToppingId() {
        return topping_id;
    }

    /**
     * Gets the quantity of the topping used in the recipe.
     *
     * @return The quantity of the topping used.
     */
    public int getQuantityUsed() {
        return quantity_used;
    }
}