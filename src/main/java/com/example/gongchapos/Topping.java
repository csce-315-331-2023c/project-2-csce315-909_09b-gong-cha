package com.example.gongchapos;

public class Topping {
    private int topping_id;
    private String topping_name;
    private int stock;
    private double unit_price;

    /**
     * Constructs a Topping object
     * @param _topping_id
     * @param _topping_name
     * @param _stock
     * @param _unit_price
     * @return Topping
     */
    public Topping(int _topping_id, String _topping_name, int _stock, double _unit_price)
    {
        topping_id = _topping_id;
        topping_name = _topping_name;
        stock = _stock;
        unit_price = _unit_price;
    }

    /**
     * Gets the topping id
     * @return int
     */
    public int getToppingId(){
        return this.topping_id;
    }

    /**
     * Gets the name of the topping
     * @return String
     */
    public String getToppingName(){
        return this.topping_name;
    }
    
    /**
     * Gets the stock of a topping
     * @return int
     */
    public int getStock(){
        return this.stock;
    }

    /**
     * Gets the unit price of a Topping
     * @return double 
     */
    public double getUnitPrice(){
        return this.unit_price;
    }

}