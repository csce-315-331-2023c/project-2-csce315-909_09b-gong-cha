# script used to generate database of 365 days of sales
import pandas as pd
import numpy as np

recipes = pd.read_csv('recipes.csv')

x = pd.date_range(end='10/2/2023', periods=365, freq = 'D')
print(x)

file = open("Orders.csv", "w")

# features: order ID, order item, order price

# distribution of orders: 100 a day, 200 on peak days

# peak days are saturday, sundays

# each order has (1, 5) items

# each order item has 



for i in x:
    print(i.month, i.day, i.year)



