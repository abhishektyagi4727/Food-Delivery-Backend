-- ============================================
-- FOOD DELIVERY - INITIAL DATA
-- POSTGRESQL COMPATIBLE VERSION
-- ============================================

-- ============================================
-- INSERT CATEGORIES (skip if exists)
-- ============================================
INSERT INTO categories (name, created_at)
SELECT 'Pizza', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Pizza');

INSERT INTO categories (name, created_at)
SELECT 'Burger', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Burger');

INSERT INTO categories (name, created_at)
SELECT 'Sushi', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Sushi');

INSERT INTO categories (name, created_at)
SELECT 'Desserts', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Desserts');

INSERT INTO categories (name, created_at)
SELECT 'Beverages', NOW()
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Beverages');

-- ============================================
-- INSERT PRODUCTS (skip if exists based on name)
-- ============================================

-- Pizzas (category_id = 1)
INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Margherita Pizza', 'Classic pizza with tomato sauce, fresh mozzarella, and basil', 299.00, '/images/pizza/margherita.jpg', 'available', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Margherita Pizza');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Pepperoni Pizza', 'Pizza topped with pepperoni, mozzarella cheese, and tomato sauce', 399.00, '/images/pizza/pepperoni.jpg', 'available', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Pepperoni Pizza');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Veggie Pizza', 'Loaded with fresh bell peppers, onions, mushrooms, olives, and cheese', 349.00, '/images/pizza/veggie.jpg', 'available', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Veggie Pizza');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'BBQ Chicken Pizza', 'Grilled chicken with BBQ sauce, red onions, and cilantro', 449.00, '/images/pizza/bbq_chicken.jpg', 'available', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'BBQ Chicken Pizza');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Paneer Tikka Pizza', 'Indian style pizza with paneer tikka, onions, and capsicum', 399.00, '/images/pizza/paneer_tikka.jpg', 'available', 1, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Paneer Tikka Pizza');

-- Burgers (category_id = 2)
INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Classic Burger', 'Beef patty with lettuce, tomato, onion, cheese, and special sauce', 199.00, '/images/burger/classic.jpg', 'available', 2, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Classic Burger');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Chicken Burger', 'Grilled chicken patty with mayo, lettuce, and pickles', 219.00, '/images/burger/chicken.jpg', 'available', 2, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Chicken Burger');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Veggie Burger', 'Plant-based patty with fresh lettuce, tomato, and vegan sauce', 179.00, '/images/burger/veggie.jpg', 'available', 2, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Veggie Burger');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Double Cheese Burger', 'Double beef patty with double cheese, bacon, and special sauce', 349.00, '/images/burger/double_cheese.jpg', 'available', 2, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Double Cheese Burger');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Spicy Chicken Burger', 'Crispy chicken patty with jalapenos and spicy mayo', 239.00, '/images/burger/spicy_chicken.jpg', 'available', 2, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Spicy Chicken Burger');

-- Sushi (category_id = 3)
INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'California Roll', 'Crab meat, avocado, and cucumber rolled in sushi rice and seaweed', 349.00, '/images/sushi/california.jpg', 'available', 3, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'California Roll');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Salmon Nigiri', 'Fresh salmon slice over pressed sushi rice', 399.00, '/images/sushi/salmon_nigiri.jpg', 'available', 3, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Salmon Nigiri');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Vegetable Roll', 'Cucumber, avocado, and carrot rolled with sushi rice', 249.00, '/images/sushi/vegetable.jpg', 'available', 3, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Vegetable Roll');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Dragon Roll', 'Eel, cucumber, topped with avocado and eel sauce', 499.00, '/images/sushi/dragon.jpg', 'available', 3, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Dragon Roll');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Spicy Tuna Roll', 'Fresh tuna mixed with spicy mayo and cucumber', 449.00, '/images/sushi/spicy_tuna.jpg', 'available', 3, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Spicy Tuna Roll');

-- Desserts (category_id = 4)
INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Chocolate Cake', 'Rich chocolate layer cake with chocolate ganache', 149.00, '/images/desserts/chocolate_cake.jpg', 'available', 4, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Chocolate Cake');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Cheesecake', 'Creamy New York style cheesecake with berry compote', 179.00, '/images/desserts/cheesecake.jpg', 'available', 4, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Cheesecake');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Ice Cream', 'Vanilla ice cream scoop with chocolate sauce', 99.00, '/images/desserts/ice_cream.jpg', 'available', 4, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Ice Cream');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Brownie', 'Warm chocolate brownie with vanilla ice cream', 129.00, '/images/desserts/brownie.jpg', 'available', 4, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Brownie');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Gulab Jamun', 'Traditional Indian dessert - fried milk solids in sugar syrup', 89.00, '/images/desserts/gulab_jamun.jpg', 'available', 4, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Gulab Jamun');

-- Beverages (category_id = 5)
INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Cola', 'Refreshing cola drink (500ml)', 49.00, '/images/beverages/cola.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Cola');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Orange Juice', 'Fresh squeezed orange juice', 79.00, '/images/beverages/juice.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Orange Juice');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Mineral Water', 'Natural mineral water (1L)', 29.00, '/images/beverages/water.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Mineral Water');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Lemonade', 'Fresh lemon juice with mint and soda', 69.00, '/images/beverages/lemonade.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Lemonade');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Cold Coffee', 'Chilled coffee with milk and ice cream', 99.00, '/images/beverages/cold_coffee.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Cold Coffee');

INSERT INTO products (name, description, price, image_url, status, category_id, created_at)
SELECT 'Masala Chai', 'Traditional Indian spiced tea', 39.00, '/images/beverages/chai.jpg', 'available', 5, NOW()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Masala Chai');

-- ============================================
-- INSERT USERS (skip if exists)
-- ============================================

-- Admin User (password: admin123 - will be encrypted by BCrypt)
INSERT INTO users (name, email, password, phone, address, created_at)
SELECT 'Administrator', 'admin@fooddelivery.com', 'admin123', '9876543210', 'Admin Office, Mumbai', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@fooddelivery.com');

-- Sample Regular Users
INSERT INTO users (name, email, password, phone, address, created_at)
SELECT 'Rahul Sharma', 'rahul.sharma@example.com', 'rahul123', '9876543211', 'Andheri East, Mumbai - 400069', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'rahul.sharma@example.com');

INSERT INTO users (name, email, password, phone, address, created_at)
SELECT 'Priya Patel', 'priya.patel@example.com', 'priya456', '9876543212', 'Connaught Place, Delhi - 110001', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'priya.patel@example.com');

-- ============================================
-- VERIFY DATA (Shows counts - PostgreSQL compatible)
-- ============================================
SELECT 'CATEGORIES' AS Table_Name, COUNT(*)::integer AS Record_Count FROM categories
UNION ALL
SELECT 'PRODUCTS', COUNT(*)::integer FROM products
UNION ALL
SELECT 'USERS', COUNT(*)::integer FROM users;

-- ============================================
-- SHOW PRODUCTS BY CATEGORY (Verification query)
-- ============================================
SELECT 
    c.name AS Category,
    p.name AS Product,
    p.price AS Price,
    p.status AS Status
FROM products p
JOIN categories c ON p.category_id = c.id
ORDER BY c.name, p.price;