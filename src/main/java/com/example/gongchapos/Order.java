package com.example.gongchapos;

import java.util.*;


public class Order extends Application{
    private

    int order_id;
    String date;
    double subtotal;
    double tip;
    String coupon_code;
    String time;

    List<Drink> order_items = new ArrayList<Drink>();

    public Order(String _date)
    {
        date = _date;
    }
    
    public int getOrderID()
    {
        return order_id;
    }

    public void setOrderID(int id)
    {
        order_id = id;
    }

    public String getDate()
    {
        return date;
    }

    public double getSubtotal()
    {
        return subtotal;
    }

    public void setSubtotal(double total)
    { 
        subtotal = total;
    }

    public double getTip()
    {
        return tip;
    }

    public void setTip(double _tip)
    {
        tip = _tip;
    }

    public void setCouponCode(String code)
    {
        coupon_code = code;
    }

    public String getCouponCode()
    {
        return coupon_code;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String _time)
    {
        time = _time;
    }

    public void addOrderItem(Drink drink)
    {
        drink.setOrderID(order_id);
        order_items.add(drink);
    }
}
