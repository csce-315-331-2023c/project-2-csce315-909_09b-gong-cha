--Feel free to practice SQL queries here and then copy them into the main file!

-- Somewhat more complex queries

-- Manager View Query, Select all ORDERS return a table showing the net profit for each day
Select Order_Date, SUM(Net_Profit) AS Total_Profit FROM Order_ GROUP BY Order_Date;

-- Manager View Query, SELECT ORDERS between set period of time and return something that we can create a pie chart of to see which orders are most popular
-- TODO: Unfinished, but this requires us to do a join between this query and the Order_Item table to get the number of Order_Item Occurences. Then we can use this to create a pie chart
SELECT * FROM Order_ WHERE order_date
BETWEEN 'STARTING_DATE_TIME' AND 'ENDING_DATE_TIME';

-- Return sale profit of N peak days, requires us to first compute the net profit of each day, sorting, and then selecting the top n days

-- Return the top 5 most popular drinks (by number of orders)
SELECT Recipe_Name from Recipe NATURAL JOIN Order_Item GROUP BY Recipe_Name ORDER BY COUNT(Recipe_Name) DESC LIMIT 5;

-- Return current state of Ingredients and Toppings (stock)
Select Ingredient_Name, Ingredient.stock, Topping_Name, Toppings.stock FROM Ingredient, Toppings;

-- Return Names of Recipes that are slushies
SELECT Recipe_Name FROM Recipe WHERE Is_Slush = TRUE;

-- Return Names of Recipes that are not slushies
SELECT Recipe_Name FROM Recipe WHERE Is_Slush = FALSE;

-- Return Names of Recipes and Prices that are slushies and have a medium price of less than $18
SELECT Recipe_Name, Med_Price, Large_Price FROM Recipe WHERE Is_Slush = TRUE AND Med_Price < 18;

-- return toppings that have stock lower than 20
SELECT * FROM Toppings WHERE Stock < 20;

-- return Recipes that have the word "lemon" in the name
SELECT * FROM Recipe WHERE Recipe_Name LIKE '%Lemon%';

--return the ingredients and quantity of ingredients in a particular recipe (Honey Lemon Slush)
SELECT Recipe_Name, Ingredient_Name, Recipe_Ingredient.Quantity_Used FROM Recipe NATURAL JOIN Recipe_Ingredient NATURAL JOIN Ingredient WHERE Recipe_Name = 'Caramel Milk Tea';

--return all the order items for a given Order_ (we use the Order_ID to identify the order)
Select * FROM Order_Item WHERE Order_ID = 1;

--return the number of order items on a particular day (requries join between Order_ and Order_Item)
SELECT Order_Date, COUNT(Order_Item_ID) FROM Order_ NATURAL JOIN Order_Item WHERE Order_Date = '2020-11-01' GROUP BY Order_Date;

-- return profit of an order by first calculating the total cost of the order and then subtracting the total cost from the total price taken from the recipe_price from recipe table
SELECT SUM(Recipe_Price - Item_Price) FROM Order_Item, Recipe WHERE Order_ID = 1;

-- return the total cost of an order by summing the cost of each ingredient in the recipe
SELECT SUM(Quantity_Used * Unit_Price) FROM Recipe_Ingredient, Ingredient WHERE Recipe_ID = 1;

-- VIEWS and SIMPLE QUERIES for verifying Db is working
-- return all the orders
SELECT * FROM Order_;

-- return all the order_items
SELECT * FROM Order_Item;

-- return all the ingredients
SELECT * FROM Ingredient;

-- return all the toppings
SELECT * FROM Toppings;

-- return all the recipes
SELECT * FROM Recipe;

-- return all recipe_ingredients
SELECT * FROM Recipe_Ingredient;

-- return all from Order_Item_Toppings
SELECT * FROM Order_Item_Toppings;


