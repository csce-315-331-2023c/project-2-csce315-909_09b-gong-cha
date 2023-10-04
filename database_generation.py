# script used to generate database of 365 days of sales
import pandas as pd
import numpy as np
import random
recipes = pd.read_csv('Recipes.csv')
toppings = pd.read_csv('csv_files/Toppings.csv')
range_of_days = pd.date_range(end='10/2/2023', periods=365, freq = 'D')


# distribution of orders: around 100 a day, around 200 on peak days

# peak days are saturday, sundays

# each order has [1, 5] items

# each order item has [0, 2] toppings
order_item = open("csv_order_items/Order_Item.csv", "w")
order_receipt = open("csv_order_items/Order.csv", "w")
order_item_toppings = open("csv_order_items/Order_Item_Toppings.csv", "w")
# Order_Item.csv features: Order_Item_ID, Order_ID, Recipe_ID, Notes, Is_Medium, Ice, Sugar, Item_Price

# Order_Toppings.csv features: Order_Item_ID, Topping_Id
Ice = ["Light", "Regular", "None"]
Sugar = ["0%", "30%", "50%", "70%", "100%"]
Order_ID = 0
Order_Item_ID= 0

order_item.write("Order_Item_ID, Recipe_ID, Order_ID, Notes, Is_Medium, Ice, Sugar, Item_Price\n")
order_item_toppings.write("Order_Item_Id, Topping_ID, Quantity_Used\n")
order_receipt.write("Order_ID, Date_, Subtotal, Tip, Coupon_Code, Time_\n")

for date in range_of_days:
    print(date.month, date.day, date.year)
    num_orders = random.randint(100, 150)
    
    day_of_week = date.day%7
    if(day_of_week == 6 or day_of_week == 7):
        num_orders = random.randint(200, 250)
    
    
    
    for order in range(num_orders):
        
        num_items = random.randint(1,5)
        
        total_price = 0
        for item in range(num_items):
            drink_index = random.randint(0,9)
            size = random.randint(0,1)
            recipe_id = recipes.iloc[drink_index, 0]
            size = (size==1) # medium
            num_toppings = random.randint(0,2)
            ice = random.randint(0,2)
            ice = Ice[ice]
            sugar = Sugar[random.randint(0,4)]
            item_price = recipes.iloc[drink_index, 4]
            if(size):
                item_price = recipes.iloc[drink_index, 3]
            
            total_price += item_price    
    
            for topping in range(num_toppings):
                _topping = random.randint(1, 8)
                # _topping = toppings.iloc[_topping, 1]
                quantity = random.randint(1, 3)  
                order_item_toppings.write(
                    str(Order_Item_ID) +
                    "," +
                    str(_topping) +
                    "," +
                    str(quantity) +
                    "\n"
                )
                item_price += quantity*toppings.iloc[_topping - 1, 2]
                
            #order_item should write the "Order_Item_ID, Recipe_ID, Order_ID, Notes, Is_Medium, Ice, Sugar, Item_Price\n" and order should write the "Order_ID, Date, Subtotal, Tip, Coupon_Code\n" After we finish creating an order Item and 
            #this ensures that we have the right item price after topping adjustment
            order_item.write(
                str(Order_Item_ID) + ", " +
                str(recipe_id) + ", " +
                str(Order_ID) + ", " +
                str("") + ", " +
                str(size) + ", " +
                str(ice) + ", " +
                sugar + ", " +
                str(item_price) + 
                "\n"
            )
            
            Order_Item_ID+=1
            
        tip = random.randint(0, 500)/100 # the order is in range [0, 5] dollars
        #we want to format date as YYYY-MM-DD
        #for the sake of time, we will use the Houston, TX location hours
        hour = random.randint(11, 22) #open from 11am to 11pm, but we assume it closes at 22:59
        minute = random.randint(0, 59) # for the sake of time, we will randomize all of these times, so the data won't be sorted by time.
        
        order_receipt.write(
            str(Order_ID) + "," +
            str(date.year) + "-" + str(date.month) + "-" + str(date.day) + "," +
            str(total_price) + "," +
            str(tip) + "," +
            str("") + "," +
            str(hour) + ":" + str(minute) +
            "\n"
        )
        Order_ID+=1
        
order_item.close()
order_item_toppings.close()
order_receipt.close()
    




    


