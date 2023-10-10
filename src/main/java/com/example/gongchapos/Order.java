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
    

    public
    int getOrderID()
    {
        return order_id;
    }

    void setOrderID(int id)
    {
        order_id = id;
    }

    String getDate()
    {
        return date;
    }

    double getSubtotal()
    {
        return subtotal;
    }

    void setSubtotal(double total)
    { 
        subtotal = total;
    }

    double getTip()
    {
        return tip;
    }

    void setTip(double _tip)
    {
        tip = _tip;
    }

    void setCouponCode(String code)
    {
        coupon_code = code;
    }

    String getCouponCode()
    {
        return coupon_code;
    }

    String getTime()
    {
        return time;
    }

    void setTime(String _time)
    {
        time = _time;
    }

    void addOrderItem(Drink drink)
    {
        drink.setOrderID(order_id);
        order_items.add(drink);
    }
}
