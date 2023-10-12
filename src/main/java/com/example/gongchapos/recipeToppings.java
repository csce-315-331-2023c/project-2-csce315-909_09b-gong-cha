package com.example.gongchapos;

public class recipeToppings {
    private int recipe_id;
    private int topping_id;
    private int quantity_used;

    public recipeToppings(int _recipe_id, int _topping_id, int _quantity_used)
    {
        recipe_id = _recipe_id;
        topping_id = _topping_id;
        quantity_used = _quantity_used;
    }


    int getRecipeID()
    {
        return recipe_id;
    }

    int getToppingId()
    {
        return topping_id;
    }

    int getQuantityUsed()
    {
        return quantity_used;
    }
}
