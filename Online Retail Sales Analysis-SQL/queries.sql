-- Top-Selling Products --
SELECT 
    p.name,
    SUM(oi.quantity) AS total_sold
FROM Order_Items oi 
JOIN Products p ON oi.product_id=p.product_id
GROUP BY p.name
ORDER BY total_sold DESC;

-- Identify most valuable customers --
SELECT 
    c.name,c.customer_id,SUM(p.price * oi.quantity) as total_spent
FROM Customers c
    JOIN Orders o ON c.customer_id=o.customer_id
    JOIN Order_Items oi ON o.order_id=oi.order_id
    JOIN Products p ON oi.product_id=p.product_id
GROUP BY c.name,c.customer_id
ORDER BY total_spent DESC;

-- Monthly revenue calculation --
SELECT
    DATE_FORMAT(o.order_date,'%Y-%m') AS month,
    SUM(p.price * oi.quantity) AS revenue
FROM Orders o
JOIN Order_Items oi ON o.order_id=oi.order_id
JOIN Products p ON oi.product_id=p.product_id
GROUP BY month
ORDER BY month;

-- Category-wise sales analysis --
SELECT 
    p.category,
    SUM(p.price * oi.quantity) AS total_sales
FROM Products p
JOIN Order_Items oi ON p.product_id = oi.product_id
GROUP BY p.category
ORDER BY total_sales DESC;

-- Detect inactive customers --
SELECT 
    c.customer_id,
    c.name
FROM Customers c
LEFT JOIN Orders o ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL;


-- -- 1--
-- SELECT 
--     p.name,
--     SUM(oi.quantity) AS total_sold
-- FROM Order_Items oi
-- JOIN Products p ON oi.product_id=p.product_id
-- GROUP BY p.name 
-- ORDER BY total_sold;

-- -- 2 --
-- SELECT 
--     c.name,
--     SUM(p.price * oi.quantity) AS total_spent
-- FROM Customers c
--     JOIN Orders o ON c.customer_id=o.customer_id
--     JOIN Order_Items oi o.order_id=oi.order_id
--     JOIN Products p oi.product_id=p.product_id
-- GROUP BY c.name
-- ORDER BY total_spent DESC;

-- -- 3 --
-- SELECT 
--     DATE_FORMAT(o.order_date, '%Y-%m') AS month,
--     SUM(p.price*oi.quantity) As revenue
-- FROM Orders o 
-- JOIN Order_Items oi ON o.order_id=oi.order_id
-- JOIN Products p ON oi.product_id=p.product_id
-- GROUP BY month
-- ORDER BY month;

-- -- 4 --
-- SELECT 
--     p.category,
--     SUM(p.price * oi.quantity) AS total_sales
-- FROM Products p
-- JOIN Order_Items oi ON p.product_id=oi.product_id
-- JOIN Orders o ON oi.order_id=o.order_id
-- GROUP BY p.category
-- ORDER BY total_sales DESC;

-- -- 5--
-- SELECT 
--     c.name,c.customer_id
-- FROM Customers c
-- JOIN Orders o On c.customer_id=o.customer_id
-- WHERE order_id is NULL;