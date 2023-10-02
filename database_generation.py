# script used to generate database of 365 days of sales
import pandas as pd
import numpy as np

recipes = pd.read_csv('recipes.csv')

x = pd.date_range(end='10/2/2023', periods=365, freq = 'D')
print(x)


# distribution of orders: 100 a day, 200 on peak days

# peak days are saturday, sundays

# each order has [1, 5] items

# each order item has [0, 2] toppings

file = open("Order_Item.csv", "w")

# Order_Item.csv features: Order_Item_ID, Order_ID, Recipe_ID, Notes, Is_Medium, Ice, Sugar, Item_Price

# Order_Toppings.csv features: Order_Item_ID, Topping_Id

for i in x:
    print(i.month, i.day, i.year)



