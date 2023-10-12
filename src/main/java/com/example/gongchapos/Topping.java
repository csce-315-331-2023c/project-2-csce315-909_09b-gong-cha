package com.example.gongchapos;

public class Topping {
    private int topping_id;
    private String topping_name;
    private int stock;
    private double unit_price;

    public Topping(int _topping_id, String _topping_name, int _stock, double _unit_price)
    {
        topping_id = _topping_id;
        topping_name = _topping_name;
        stock = _stock;
        unit_price = _unit_price;
    }

    public int getToppingId(){
        return this.topping_id;
    }

    public String getToppingName(){
        return this.topping_name;
    }

    public int getStock(){
        return this.stock;
    }

    public double getUnitPrice(){
        return this.unit_price;
    }

}