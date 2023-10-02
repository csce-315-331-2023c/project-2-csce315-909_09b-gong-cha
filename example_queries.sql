--Feel free to practice SQL queries here and then copy them into the main file!

-- Manager View Query, Select all ORDERS return a table showing the net profit for each day
Select Order_Date, SUM(Net_Profit) AS Total_Profit FROM Order_ GROUP BY Order_Date;
-- Manager View Query, SELECT ORDERS between set period of time and return something that we can create a pie chart of to see which orders are most popular

-- Return sale profit of N peak days, requires us to first compute the net profit of each day, sorting, and then selecting the top n days


-- Return current state of Ingredients and Toppings (stock)

-- Return Recipes that are slushies
SELECT * FROM Recipe WHERE Is_Slush = TRUE;

-- Return Recipes that are not slushies
SELECT * FROM Recipe WHERE Is_Slush = FALSE;

-- Return Recipes that are slushies and have a medium price of less than $5
SELECT * FROM Recipe WHERE Is_Slush = TRUE AND Med_Price < 5;

-- return toppings that have stock lower than 20
SELECT * FROM Toppings WHERE Stock < 20;

-- return Recipes that have the word "lemon" in the name
SELECT * FROM Recipe WHERE Recipe_Name LIKE '%Lemon%';

--return the ingredients and quantity of ingredients in a particular recipe (Lemon Yuzu)
SELECT Ingredient_Name, Quantity FROM Recipe NATURAL JOIN Recipe_Ingredient NATURAL JOIN Ingredient WHERE Recipe_Name = 'Lemon Yuzu';

--return all the order items for a given Order_ (we use the Order_ID to identify the order)
Select * FROM Order_Item WHERE Order_ID = 1;
--return the number of order items on a particular day (requries join between Order_ and Order_Item)

--

--

--