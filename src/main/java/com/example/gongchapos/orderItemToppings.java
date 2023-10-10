package com.example.gongchapos;

public class orderItemToppings {
    private
    int order_item_id;
    int topping_id;
    int quantity_used;

    public orderItemToppings(int _order_item_id, int _topping_id, int _quantity_used)
    {
        order_item_id = _order_item_id;
        topping_id = _topping_id;
        quantity_used = _quantity_used;
    }


    public
    int getOrderItemID()
    {
        return order_item_id;
    }

    int getToppingId()
    {
        return topping_id;
    }

    int getQuantityUsed()
    {
        return quantity_used;
    }
}
