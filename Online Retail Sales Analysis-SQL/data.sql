-- Customers table --
INSERT INTO Customers (customer_id, name, city) VALUES
(1, "Sadhana", "Bangalore"),
(2, "Priya", "Chennai"),
(3, "Sam", "Coimbatore"),
(4, "Raja", "Hyderabad"),
(5, "Rahul", "Chennai"),
(6, "Kumar", "Kolkata");

-- Products table --
INSERT INTO Products (product_id, name, category, price) VALUES
(101, "Laptop", "Electronics", 50000),
(102, "Mobile", "Electronics",25000),
(103, "Watch", "Fashion",5000),
(104, "Shoes", "Fashion", 3000),
(105, "Chair", "Furniture", 2000),
(106, "Fan", "Appliances", 2500),
(107, "Kettle", "Appliances", 800);

-- Orders table --
INSERT INTO Orders (order_id, customer_id,order_date) VALUES
(1, 1, "2026-03-19"),
(2, 5, "2026-01-26"),
(3, 3, "2025-12-29"),
(4, 5, '2026-02-02'),
(5, 2, "2026-04-03");

-- Order_Items Table --
INSERT INTO Order_Items (order_id, product_id, quantity) VALUES
(3, 103, 2),
(1, 102, 1),
(2, 101, 1),
(3, 106, 1);


