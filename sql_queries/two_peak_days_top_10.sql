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