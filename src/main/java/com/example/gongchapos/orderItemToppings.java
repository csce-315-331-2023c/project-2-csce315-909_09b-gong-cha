package com.example.gongchapos;

/**
* Represents an order item with associated toppings and quantities used
* 
* @author Reid Jenkins
*/
public class orderItemToppings {
    private int order_item_id;
    private int topping_id;
    private int quantity_used;

    /**
    * Constructor for OrderItemToppings.
    *
    * @param _order_item_id The ID of the order item.
    * @param _topping_id The ID of the topping.
    * @param _quantity_used The quantity of the topping used.
    */
    public orderItemToppings(int _order_item_id, int _topping_id, int _quantity_used)
    {
        order_item_id = _order_item_id;
        topping_id = _topping_id;
        quantity_used = _quantity_used;
    }

    /**
    * Get the ID of the order item.
    *
    * @return The order item ID.
    */
    public int getOrderItemID()
    {
        return order_item_id;
    }

    /**
    * Get the ID of the topping.
    *
    * @return The topping ID.
    */
    public int getToppingId()
    {
        return topping_id;
    }

    /**
    * Get the quantity of the topping used.
    *
    * @return The quantity used.
    */
    public int getQuantityUsed()
    {
        return quantity_used;
    }
}
