SELECT Recipe_Name, Ingredient_Name, Recipe_Ingredient.Quantity_Used FROM Recipe NATURAL JOIN Recipe_Ingredient NATURAL JOIN Ingredient WHERE Recipe_Name = 'Black Milk Tea';
