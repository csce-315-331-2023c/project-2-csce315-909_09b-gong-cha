--Feel free to practice SQL queries here and then copy them into the main file!
--REQUIRED QUERIES:
-- 52 Weeks of Sales History: select count of orders grouped by week.
SELECT COUNT(Order_ID), EXTRACT(WEEK FROM Date_) AS Weeks FROM Order_ GROUP BY EXTRACT(WEEK FROM Date_) ORDER BY Weeks ASC;

-- Realistic Sales History: select count of orders, sum of order total grouped by hour.
SELECT EXTRACT(HOUR FROM Time_) AS Hour_of_Day,COUNT(Order_ID), SUM(Subtotal + Tip) FROM Order_ GROUP BY EXTRACT(HOUR FROM Time_) ORDER BY EXTRACT(HOUR FROM Time_) ASC;

-- 2 Peak Days: select top 10 sums of order total grouped by day in descending order.
-- I'm a bit confused as to what they are asking for here, but I assume first we determine the dates of the two peak days.
-- Then we select the top 10 sums of order total grouped by day in descending order.
SELECT Date_, Order_ID, SUM(Subtotal + Tip) AS TotalSales
FROM Order_
INNER JOIN (
    SELECT Date_, SUM(Subtotal + Tip) AS DailyTotal
    FROM Order_
    GROUP BY Date_
    ORDER BY DailyTotal DESC
    LIMIT 2
) AS Peak_Days ON Order_.Date_ = Peak_Days.Date_
GROUP BY DAY FROM Peak_Days.Date_
ORDER BY TotalSales DESC
LIMIT 10;


--first select the two peak days, then subquery those two days to find the top ten

SELECT Date_, SUM(Subtotal + Tip) FROM Order_ GROUP BY Date_ ORDER BY SUM(Subtotal + Tip) DESC LIMIT 2;
WITH ALLORDERS AS (
    SELECT o.Order_Id, o.Date_, o.Subtotal + o.Tip AS Total
    FROM Order_ o
    JOIN (
        SELECT Date_, SUM(Subtotal + COALESCE(Tip, 0.00)) AS Total
        FROM Order_
        GROUP BY Date_
        ORDER BY Total DESC
        LIMIT 2
    ) TopDates ON o.Date_ = TopDates.Date_
)

SELECT a.Order_Id, a.Date_, a.Total
FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY Date_ ORDER BY Total DESC) AS rn
    FROM ALLORDERS
) a
WHERE a.rn <= 10
ORDER BY a.Date_, a.Total;


-- 20 Items in Inventory: select row count from inventory.
SELECT COUNT(Ingredient_ID) FROM Ingredient;

--count most used number of toppings
SELECT Topping_ID, COUNT(Topping_ID) FROM Order_Item_Toppings GROUP BY Topping_ID ORDER BY COUNT(Topping_ID) DESC LIMIT 5;

-- Somewhat more complex queries

--select most used ingredient
SELECT Ingredient_Name, SUM(Quantity_Used) AS Total_Used FROM Recipe_Ingredient NATURAL JOIN Ingredient GROUP BY Ingredient_Name ORDER BY Total_Used DESC LIMIT 1;

-- Manager View Query, Select all ORDERS return a table showing the net profit for each day
Select Date_, SUM(Subtotal + Tip) AS Total_Profit FROM Order_ GROUP BY Date_;

-- Manager View Query, Select all ORDERS return a table showing the net profit between a set period of time
Select Date_, SUM(Subtotal + Tip) AS Total_Profit FROM Order_ WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-30' GROUP BY Date_ ORDER BY Date_ ASC;

-- Manager View Query, SELECT ORDERS between set period of time and return something that we can create a pie chart of to see which orders are most popular
-- TODO: Unfinished, but this requires us to do a join between this query and the Order_Item table to get the number of Order_Item Occurences. Then we can use this to create a pie chart


-- Return the top 5 most popular drinks (by number of orders)
SELECT Recipe_Name, COUNT(Recipe_Name) from Recipe NATURAL JOIN Order_Item GROUP BY Recipe_Name ORDER BY COUNT(Recipe_Name) DESC LIMIT 5;

-- Return current state of Ingredients and Toppings (stock)
Select Ingredient_Name, Ingredient.stock, Topping_Name, Toppings.stock FROM Ingredient, Toppings;

-- Return Names of Recipes that are slushies
SELECT Recipe_Name FROM Recipe WHERE Is_Slush = TRUE;

-- Return Names of Recipes that are not slushies
SELECT Recipe_Name FROM Recipe WHERE Is_Slush = FALSE;

-- Return Names of Recipes and Prices that are not slushies and have a medium price of less than $18
SELECT Recipe_Name, Med_Price, Large_Price FROM Recipe WHERE Is_Slush = FALSE AND Med_Price < 18;

-- return toppings that have stock lower than 20
SELECT * FROM Toppings WHERE Stock < 20;

-- return Recipes that have the word "lemon" in the name
SELECT * FROM Recipe WHERE Recipe_Name LIKE '%Lemon%';

--return the ingredients and quantity of ingredients in a particular recipe (Black Milk Tea)
SELECT Recipe_Name, Ingredient_Name, Recipe_Ingredient.Quantity_Used FROM Recipe NATURAL JOIN Recipe_Ingredient NATURAL JOIN Ingredient WHERE Recipe_Name = 'Black Tea';

SELECT Recipe_Name, Topping_Name, Recipe_Toppings.Quantity_Used FROM Recipe NATURAL JOIN Recipe_Toppings NATURAL JOIN Toppings WHERE Recipe_Name = 'Lemon Ai-Yu White Pearls';
--return all the order items for a given Order_ (we use the Order_ID to identify the order)
Select * FROM Order_Item WHERE Order_ID = 5;

SELECT * FROM Order_Item WHERE date_ = '2023-10-10';
--return the number of order items on a particular day (requries join between Order_ and Order_Item) TODO: currently not working
SELECT Order_ID, Order_.Date_, COUNT(Order_Item_ID) FROM Order_ NATURAL JOIN Order_Item WHERE Date_ = '2020-11-01';

-- return profit of an order by first calculating the total cost of the order and then subtracting the total cost from the total price taken from the recipe_price from recipe table 
SELECT SUM(-Recipe_Price + Item_Price) FROM Order_Item, Recipe WHERE Order_ID = 1 AND Order_Item.Recipe_ID = Recipe.Recipe_ID;

-- return the total cost to make Recipe with Recipe_ID = 1 by summing the cost of each ingredient in the recipe
SELECT SUM(Quantity_Used * Ingredient.Unit_Price) FROM Recipe_Ingredient, Ingredient WHERE Recipe_ID = 1 AND Recipe_Ingredient.Ingredient_ID = Ingredient.Ingredient_ID;
-- SELECT SUM(Quantity_Used * Unit_Price) FROM Recipe_Ingredient, Ingredient WHERE Recipe_ID = 1;

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


Select SUM(Subtotal + Tip) FROM Order_;

--return all ingredients used where SUM(Quantity_Used) < Ingredient.Stock * .1

SELECT Ingredient_Name, SUM(Quantity_Used) AS Total_Used 
FROM Recipe_Ingredient NATURAL JOIN Ingredient NATURAL JOIN Order_ 
WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-30'
GROUP BY Ingredient_Name
ORDER BY Total_Used;


--use the above query to return the ingredients where the sum of the quantity used is less than 10% of the stock
Select Ingredient_Name, SUM(Quantity_Used) AS Total_Used
FROM Recipe_Ingredient NATURAL JOIN Ingredient NATURAL JOIN Order_
WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
GROUP BY Ingredient_Name
HAVING SUM(Quantity_Used) < Ingredient.Stock * .1
ORDER BY Total_Used;


SELECT Ingredient_Name, SUM(Quantity_Used) AS Total_Used
FROM Recipe_Ingredient NATURAL JOIN Ingredient NATURAL JOIN Order_ NATURAL JOIN Order_Item
WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-2'
GROUP BY Ingredient_Name
ORDER BY Total_Used;

--return all ingredients used where SUM(Quantity_Used) < Ingredient.Stock * .1
--use above query as a subquery, so we can access Total_Used
SELECT Ingredient.Ingredient_Name, Subquery.Total_Used
FROM (
    Select Ingredient_Name, SUM(Quantity_Used) AS Total_Used
    FROM Recipe_Ingredient NATURAL JOIN Ingredient NATURAL JOIN Order_ NATURAL JOIN Order_Item
    WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
    GROUP BY Ingredient_Name
    ORDER BY Ingredient_Name
) AS Subquery, Ingredient
WHERE Subquery.Total_Used < Ingredient.Stock * .1 AND Ingredient.Ingredient_Name = Subquery.Ingredient_Name
ORDER BY Total_Used;

--toppings version
SELECT Toppings.Topping_Name, Subquery.Total_Used
FROM (
    SELECT Topping_Name, SUM(Quantity_Used) AS Total_Used
    FROM Recipe_Toppings NATURAL JOIN Toppings NATURAL JOIN Order_ NATURAL JOIN Order_Item
    WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
    GROUP BY Toppings.Topping_Name;
) AS Subquery, Toppings
WHERE Subquery.Total_Used < Toppings.Stock * .1 AND Toppings.Topping_Name = Subquery.Topping_Name
ORDER BY Total_Used;

--get sum of all toppings used in a recipe, from the recipe to extra toppings
SELECT Topping_Name, SUM(Total_Used) AS Combined_Total_Used
FROM (
    SELECT Topping_Name, SUM(Quantity_Used) AS Total_Used
    FROM Recipe_Toppings
    NATURAL JOIN Toppings
    NATURAL JOIN Order_
    NATURAL JOIN Order_Item
    WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
    GROUP BY Topping_Name

    UNION

    SELECT Topping_Name, SUM(Quantity_Used) AS Total_Used
    FROM Order_Item_Toppings
    NATURAL JOIN Toppings
    NATURAL JOIN Order_
    NATURAL JOIN Order_Item
    WHERE Date_ BETWEEN '2022-11-01' AND '2022-11-02'
    GROUP BY Topping_Name
) AS subquery
GROUP BY Topping_Name;
