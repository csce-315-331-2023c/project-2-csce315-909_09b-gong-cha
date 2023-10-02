# script used to generate database of 365 days of sales
import pandas as pd
import numpy as np
import random
recipes = pd.read_csv('recipes.csv')
toppings = pd.read_csv('csv_files\Toppings.csv')
range_of_days = pd.date_range(end='10/2/2023', periods=365, freq = 'D')


# distribution of orders: 100 a day, 200 on peak days

# peak days are saturday, sundays

# each order has [1, 5] items

# each order item has [0, 2] toppings
file = open("Order_Item.csv", "w")
file_toppings = open("csv_files/Order_Item_Toppings.csv", "w")
# Order_Item.csv features: Order_Item_ID, Order_ID, Recipe_ID, Notes, Is_Medium, Ice, Sugar, Item_Price

# Order_Toppings.csv features: Order_Item_ID, Topping_Id
Ice = ["Light", "Regular", "None"]
Sugar = ["0%", "30%", "50%", "70%", "100%"]
Order_ID = 0
Order_Item_ID= 0

file.write("Date, Order_Item_ID, Order_ID, Recipe_ID, Notes, Is_Medium, Ice, Sugar, Item_Price\n")
for date in range_of_days:
    print(date.month, date.day, date.year)
    num_orders = 100
    
    day_of_week = date.day%7
    if(day_of_week == 6 or day_of_week == 7):
        num_orders = 200
    
    
    
    for order in range(num_orders):
        
        num_items = random.randint(1,5)
        
    
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
            
            file.write(
                str(date.month) + "/" + str(date.day) + "/" + str(date.year) + "," +
                str(Order_ID) + ", " +
                str(Order_Item_ID) + ", " +
                str(recipe_id) + ", " +
                str("") + ", " +
                str(size) + ", " +
                str(ice) + ", " +
                sugar + ", " +
                str(item_price) + 
                "\n"
                )
    
    
            for topping in range(num_toppings):
    
                _topping = random.randint(0, 7)
                _topping = toppings.iloc[_topping, 1]  
                file_toppings.write(
                    str(Order_Item_ID) +
                    "," +
                    str(_topping)
                    +
                    "\n"
                )
            Order_Item_ID+=1
        Order_ID+=1
        
file.close()
file_toppings.close()
    




    


