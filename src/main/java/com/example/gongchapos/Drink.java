package com.example.gongchapos;

import java.util.*;
public class Drink extends Application{
    private
    int order_item_id;
    int recipe_id;
    int order_id;
    String Notes;
    boolean is_medium;
    String ice;
    String sugar;
    String[] ice_values = {"none", "light", "regular"};
    String[] sugar_values = {"0%", "30%", "50%", "70%", "100%"};
    double item_price;

    Map<Topping, Integer> used_toppings = new HashMap<Topping, Integer>();

    Recipe recipe;

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

    public
    int getOrderItemID()
    {
        return order_item_id;
    }

    void setOrderItemID(int id)
    {
        order_item_id = id;
    }

    int getRecipeID()
    {
        return recipe_id;
    }

    void setOrderID(int id)
    {
        order_id = id;
    }

    int getOrderID()
    {
        return order_id;
    }

    String getNotes()
    {
        return Notes;
    }

    boolean isMedium()
    {
        return is_medium;
    }

    String getIce()
    {
        return ice;
    }

    String getSugar()
    {
        return sugar;
    }

    double getItemPrice()
    {
        return item_price;
    }

    void insertTopping(Topping topping, int quantity)
    {
        used_toppings.put(topping, quantity);
        item_price += (topping.getUnitPrice() * quantity);
    }

    Map<Topping, Integer> getToppingsUsed()
    {
        return used_toppings;
    }

}
