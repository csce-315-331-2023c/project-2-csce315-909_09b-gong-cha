package com.example.gongchapos;

import java.util.*;
public class Drink extends Application{
    private int order_item_id;
    private int recipe_id;
    private int order_id;
    private String Notes;
    private boolean is_medium;
    private String ice;
    private String sugar;
    private String[] ice_values = {"none", "light", "regular"};
    private String[] sugar_values = {"0%", "30%", "50%", "70%", "100%"};
    private double item_price;

    private Map<Topping, Integer> used_toppings = new HashMap<Topping, Integer>();

    private Recipe recipe;

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

    public int getOrderItemID()
    {
        return order_item_id;
    }

    public void setOrderItemID(int id)
    {
        order_item_id = id;
    }

    public int getRecipeID()
    {
        return recipe_id;
    }

    public void setOrderID(int id)
    {
        order_id = id;
    }

    public int getOrderID()
    {
        return order_id;
    }

    public String getNotes()
    {
        return Notes;
    }

    public boolean isMedium()
    {
        return is_medium;
    }

    public String getIce()
    {
        return ice;
    }

    public String getSugar()
    {
        return sugar;
    }

    public double getItemPrice()
    {
        return item_price;
    }

    public void insertTopping(Topping topping, int quantity)
    {
        used_toppings.put(topping, quantity);
        item_price += (topping.getUnitPrice() * quantity);
    }

    public Map<Topping, Integer> getToppingsUsed()
    {
        return used_toppings;
    }

}
