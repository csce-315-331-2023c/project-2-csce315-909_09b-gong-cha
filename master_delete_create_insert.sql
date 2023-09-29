--Rollback, transaction, updates, cascade delete:


--Clear out all old tables 
DROP TABLE IF EXISTS public.Order_;
DROP TABLE IF EXISTS public.Order_Item;
DROP TABLE IF EXISTS public.Recipe;
DROP TABLE IF EXISTS public.Recipe_Ingredient;
DROP TABLE IF EXISTS public.Ingredient;
DROP TABLE IF EXISTS public.Toppings; 

--Table: Order REQUIRES UNDERSCORE because of naming conventions in SQL
CREATE TABLE IF NOT EXISTS public.Order_(
	Order_Id serial NOT NULL,
	Date_ date NOT NULL,
	Subtotal numeric NOT NULL,
	Tip numeric,
	Coupon_Code varchar(128)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Order_
    OWNER to postgres;
	
--Table: Order_Item
CREATE TABLE IF NOT EXISTS public.Order_Item(
	--insert items
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Order_Item
    OWNER to postgres;
	
--Table: Recipe
CREATE TABLE IF NOT EXISTS public.Recipe(
	--insert items
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Recipe
    OWNER to postgres;
	
--Table: Recipe_Ingredient
CREATE TABLE IF NOT EXISTS public.Recipe_Ingredient(
	--insert items
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Recipe_Ingredient
    OWNER to postgres;
	
--Table: Ingredient
CREATE TABLE IF NOT EXISTS public.Ingredient(
	--insert items
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Ingredient
    OWNER to postgres;
	
--Table: Toppings
CREATE TABLE IF NOT EXISTS public.Toppings(
	--insert items
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.Toppings
    OWNER to postgres;

--Populate with example data