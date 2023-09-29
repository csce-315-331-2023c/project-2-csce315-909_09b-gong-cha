-- Manager View Query, Select ORDERS betweeen a set period of time and return a table showing the net profit for each day

-- Manager View Query, SELECT ORDERS between set period of time and return something that we can create a pie chart of to see which orders are most popular

-- Return sale profit of N peak days

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

