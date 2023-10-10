package com.example.gongchapos;

public class Ingredient {
    private
    int ingredient_id;
    String ingredient_name;
    int stock;
    double unit_price;

    public Ingredient(int _ingredient_id, String _ingredient_name, int _stock, double _unit_price)
    {
        
    }
    
    public int getID(){
        return this.ingredient_id;
    }

    public String getName(){
        return this.ingredient_name;
    }

    public int getStock(){
        return this.stock;
    }

    public double getUnitPrice(){
        return this.unit_price;
    }


}

