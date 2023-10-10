package com.example.gongchapos;

public class recipeIngredient {
    private
    int recipe_id;
    int ingredient_id;
    int quantity_used;
    
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


