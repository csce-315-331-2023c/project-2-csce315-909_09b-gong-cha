package com.example.gongchapos;

/**
 * Represents a relationship between a recipe and an ingredient, including the quantity used.
 * 
 * @author Reid Jenkins
 */
public class recipeIngredient {
    private int recipe_id;      // The ID of the associated recipe
    private int ingredient_id;  // The ID of the associated ingredient
    private int quantity_used;  // The quantity of the ingredient used in the recipe
    
    /**
     * Constructs a new recipeIngredient instance.
     *
     * @param _recipe_id The ID of the associated recipe.
     * @param _ingredient_id The ID of the associated ingredient.
     * @param _quantity_used The quantity of the ingredient used.
     */
    public recipeIngredient(int _recipe_id, int _ingredient_id, int _quantity_used) {
        recipe_id = _recipe_id;
        ingredient_id = _ingredient_id;
        quantity_used = _quantity_used;
    }

    /**
     * Gets the ID of the associated recipe.
     *
     * @return The ID of the associated recipe.
     */
    public int getRecipeID() {
        return this.recipe_id;
    }

    /**
     * Gets the ID of the associated ingredient.
     *
     * @return The ID of the associated ingredient.
     */
    public int getIngredientID() {
        return this.ingredient_id;
    }

    /**
     * Gets the quantity of the ingredient used in the recipe.
     *
     * @return The quantity of the ingredient used.
     */
    public int getQuantityUsed() {
        return this.quantity_used;
    }
}