## CHANGELOG for Database Design and Justifications:
If you had to make changes to your database design, you will need to describe and justify these changes during your demo.


## CHANGES MADE TO THE DATABASE DESIGN:

#### Updates to Order_Item:
- Realized that we need Order_ID in Order_Item entity as foreign key.
- Added Item_Price to Order_Item, as we realized that topping modifications make it so that we can't rely on the Recipe price fields entirely (i.e. Med_Price and Large_Price). We use Med_Price and Large_Price to act as a base price, and update if necessary.

#### Updates to Recipe:
- Added Recipe_Price field to Recipe entity to track the cost to produce a certain recipe. This will allow us to return information on net profit by showing the cost to make a drink.

#### Updates to Toppings
- Added Stock field to Toppings to keep track of toppings storage. Initially we assumed that we would not need to keep track of toppings inventory, but after creating the script for the database, we realized that it is an important field to have in the case of queries.
 
#### Updates to Ingredient:
- Got rid of Order_Item_ID FK, as it was an artifact from an earlier iterations of the database design. 
- Got rid of Unit_Weight Field, as one assumption we make about the restaurant is that they don't need to keep track of actual measurements as kg or tsp (as recipes can be trade secrets). We can proceed fine with simply using units (with hidden associated measurements).