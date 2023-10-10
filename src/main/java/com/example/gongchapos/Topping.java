package com.example.gongchapos;

public class Topping {
    private
    int topping_id;
    String topping_name;
    int stock;
    double unit_price;

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