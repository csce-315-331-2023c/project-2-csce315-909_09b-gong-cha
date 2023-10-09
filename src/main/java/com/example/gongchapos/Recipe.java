package com.example.gongchapos;

public class Recipe {
    private
    int recipe_id;
    String recipe_name;
    boolean is_slush;
    double med_price;
    double large_price;
    double recipe_price;

    public Recipe(int _recipe_id, String _recipe_name, boolean _is_slush, double _med_price, double _large_price, double _recipe_price)
    {
        recipe_id = _recipe_id;
        recipe_name = _recipe_name;
        is_slush = _is_slush;
        med_price = _med_price;
        large_price = _large_price;
        recipe_price = _recipe_price;
    }

    public
    int getRecipeID()
    {
        return recipe_id;
    }

    String getRecipeName()
    {
        return recipe_name;
    }

    boolean isSlush()
    {
        return is_slush;
    }

    double getMediumPrice()
    {
        return med_price;
    }

    double getLargePrice()
    {
        return large_price;
    }

    double getRecipePrice()
    {
        return recipe_price;
    }

}
