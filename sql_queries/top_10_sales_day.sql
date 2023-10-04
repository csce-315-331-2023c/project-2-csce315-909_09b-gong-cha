SELECT date_, COUNT(*) as order_count, SUM(subtotal + tip) as sales
FROM order_ 
GROUP BY date_
ORDER BY sales DESC
LIMIT 10;