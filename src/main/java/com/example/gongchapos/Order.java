package com.example.gongchapos;

import java.util.*;

/**
 * Represents a singular order requested at the boba shop
 * 
 * @author Reid Jenkins
 */
public class Order extends Application{
    private String date;
    private int order_id;
    private double subtotal;
    private double tip;
    private String coupon_code;
    private String time;

    private List<Drink> order_items = new ArrayList<Drink>();

    /**
     * Constructor for Order
     * @param _date
     */
    public Order(String _date)
    {
        date = _date;
    }
    
    /**
     * Get the Order ID of the Order
     * @return int
     */
    public int getOrderID()
    {
        return order_id;
    }

    /**
     * Set the Order ID of the Order
     * @param id
     */
    public void setOrderID(int id)
    {
        order_id = id;
    }

    /**
     * Get the date of the Order
     * @return String
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Set the date of the Order
     * @return double
     */
    public double getSubtotal()
    {
        return subtotal;
    }

    /**
     * Set the subtotal of the Order
     * @param total
     */
    public void setSubtotal(double total)
    { 
        subtotal = total;
    }

    /**
     * Get the tip of the Order
     * @return double
     */
    public double getTip()
    {
        return tip;
    }

    /**
     * Set the tip of the Order
     * @param _tip
     */
    public void setTip(double _tip)
    {
        tip = _tip;
    }

    /**
     * Set the coupon code of the Order
     * @param code
     */
    public void setCouponCode(String code)
    {
        coupon_code = code;
    }

    /**
     * Get the coupon code of the Order
     * @return String
     */
    public String getCouponCode()
    {
        return coupon_code;
    }

    /**
     * Get the time of the Order
     * @return String
     */
    public String getTime()
    {
        return time;
    }

    /**
     * Set the time of the Order
     * @param _time
     */
    public void setTime(String _time)
    {
        time = _time;
    }

    /**
     * Add a Drink to the Order
     * @param drink
     */
    public void addOrderItem(Drink drink)
    {
        drink.setOrderID(order_id);
        order_items.add(drink);
    }

    /**
     * Get the List of Drinks in the Order
     * @return List<Drink>
     */
    public List<Drink> getOrderItems()
    {
        return order_items;
    }
}
