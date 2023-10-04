--return the name of the top 5 most common toppings that are present in orders
SELECT T.Topping_Name, COUNT(OIT.Topping_ID) AS Topping_Count
FROM Order_Item_Toppings OIT
JOIN Toppings T ON OIT.Topping_ID = T.Topping_ID
GROUP BY OIT.Topping_ID, T.Topping_Name
ORDER BY Topping_Count DESC
LIMIT 5;
