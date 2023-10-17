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
     * @param _date - the date the order was taken
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
     * @param id - the ID of the order
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
     * @param total - the subtotal of the order
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
     * @param _tip - The tip provided
     */
    public void setTip(double _tip)
    {
        tip = _tip;
    }

    /**
     * Set the coupon code of the Order
     * @param code - The coupon code provided
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
     * @param _time - The time the order was taken
     */
    public void setTime(String _time)
    {
        time = _time;
    }

    /**
     * Add a Drink to the Order
     * @param drink - A drink object being added to the order
     */
    public void addOrderItem(Drink drink)
    {
        drink.setOrderID(order_id);
        order_items.add(drink);
    }

    /**
     * Get the List of Drinks in the Order
     * @return A list of Drink objects which are the order items
     */
    public List<Drink> getOrderItems()
    {
        return order_items;
    }

    public void clearOrderItems()
    {
        order_items.clear();
    }
}
