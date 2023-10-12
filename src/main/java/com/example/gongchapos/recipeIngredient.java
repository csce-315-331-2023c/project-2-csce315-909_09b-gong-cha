package com.example.gongchapos;

public class recipeIngredient {
    private int recipe_id;
    private int ingredient_id;
    private int quantity_used;
    
    public recipeIngredient(int _recipe_id, int __ingredient_id, int _quantity_used)
    {
        recipe_id = _recipe_id;
        ingredient_id = __ingredient_id;
        quantity_used = _quantity_used;
    }

    public int getRecipeID(){
        return this.recipe_id;
    }
    public int getIngredientID(){
        return this.ingredient_id;
    }

    public int getQuantityUsed(){
        return this.quantity_used;
    }
}


