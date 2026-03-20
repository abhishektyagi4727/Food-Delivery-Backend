-- ============================================
-- FOOD DELIVERY - INITIAL DATA
-- SAFE VERSION - Only inserts if data doesn't exist
-- ============================================

-- ============================================
-- INSERT CATEGORIES (skip if exists)
-- ============================================
INSERT IGNORE INTO categories (name, created_at) VALUES
('Pizza', NOW()),
('Burger', NOW()),
('Sushi', NOW()),
('Desserts', NOW()),
('Beverages', NOW());

-- ============================================
-- INSERT PRODUCTS (skip if exists based on name)
-- ============================================

-- Pizzas (category_id = 1)
INSERT IGNORE INTO products (name, description, price, image_url, status, category_id, created_at) VALUES
('Margherita Pizza', 'Classic pizza with tomato sauce, fresh mozzarella, and basil', 299.00, '/images/pizza/margherita.jpg', 'available', 1, NOW()),
('Pepperoni Pizza', 'Pizza topped with pepperoni, mozzarella cheese, and tomato sauce', 399.00, '/images/pizza/pepperoni.jpg', 'available', 1, NOW()),
('Veggie Pizza', 'Loaded with fresh bell peppers, onions, mushrooms, olives, and cheese', 349.00, '/images/pizza/veggie.jpg', 'available', 1, NOW()),
('BBQ Chicken Pizza', 'Grilled chicken with BBQ sauce, red onions, and cilantro', 449.00, '/images/pizza/bbq_chicken.jpg', 'available', 1, NOW()),
('Paneer Tikka Pizza', 'Indian style pizza with paneer tikka, onions, and capsicum', 399.00, '/images/pizza/paneer_tikka.jpg', 'available', 1, NOW());

-- Burgers (category_id = 2)
INSERT IGNORE INTO products (name, description, price, image_url, status, category_id, created_at) VALUES
('Classic Burger', 'Beef patty with lettuce, tomato, onion, cheese, and special sauce', 199.00, '/images/burger/classic.jpg', 'available', 2, NOW()),
('Chicken Burger', 'Grilled chicken patty with mayo, lettuce, and pickles', 219.00, '/images/burger/chicken.jpg', 'available', 2, NOW()),
('Veggie Burger', 'Plant-based patty with fresh lettuce, tomato, and vegan sauce', 179.00, '/images/burger/veggie.jpg', 'available', 2, NOW()),
('Double Cheese Burger', 'Double beef patty with double cheese, bacon, and special sauce', 349.00, '/images/burger/double_cheese.jpg', 'available', 2, NOW()),
('Spicy Chicken Burger', 'Crispy chicken patty with jalapenos and spicy mayo', 239.00, '/images/burger/spicy_chicken.jpg', 'available', 2, NOW());

-- Sushi (category_id = 3)
INSERT IGNORE INTO products (name, description, price, image_url, status, category_id, created_at) VALUES
('California Roll', 'Crab meat, avocado, and cucumber rolled in sushi rice and seaweed', 349.00, '/images/sushi/california.jpg', 'available', 3, NOW()),
('Salmon Nigiri', 'Fresh salmon slice over pressed sushi rice', 399.00, '/images/sushi/salmon_nigiri.jpg', 'available', 3, NOW()),
('Vegetable Roll', 'Cucumber, avocado, and carrot rolled with sushi rice', 249.00, '/images/sushi/vegetable.jpg', 'available', 3, NOW()),
('Dragon Roll', 'Eel, cucumber, topped with avocado and eel sauce', 499.00, '/images/sushi/dragon.jpg', 'available', 3, NOW()),
('Spicy Tuna Roll', 'Fresh tuna mixed with spicy mayo and cucumber', 449.00, '/images/sushi/spicy_tuna.jpg', 'available', 3, NOW());

-- Desserts (category_id = 4)
INSERT IGNORE INTO products (name, description, price, image_url, status, category_id, created_at) VALUES
('Chocolate Cake', 'Rich chocolate layer cake with chocolate ganache', 149.00, '/images/desserts/chocolate_cake.jpg', 'available', 4, NOW()),
('Cheesecake', 'Creamy New York style cheesecake with berry compote', 179.00, '/images/desserts/cheesecake.jpg', 'available', 4, NOW()),
('Ice Cream', 'Vanilla ice cream scoop with chocolate sauce', 99.00, '/images/desserts/ice_cream.jpg', 'available', 4, NOW()),
('Brownie', 'Warm chocolate brownie with vanilla ice cream', 129.00, '/images/desserts/brownie.jpg', 'available', 4, NOW()),
('Gulab Jamun', 'Traditional Indian dessert - fried milk solids in sugar syrup', 89.00, '/images/desserts/gulab_jamun.jpg', 'available', 4, NOW());

-- Beverages (category_id = 5)
INSERT IGNORE INTO products (name, description, price, image_url, status, category_id, created_at) VALUES
('Cola', 'Refreshing cola drink (500ml)', 49.00, '/images/beverages/cola.jpg', 'available', 5, NOW()),
('Orange Juice', 'Fresh squeezed orange juice', 79.00, '/images/beverages/juice.jpg', 'available', 5, NOW()),
('Mineral Water', 'Natural mineral water (1L)', 29.00, '/images/beverages/water.jpg', 'available', 5, NOW()),
('Lemonade', 'Fresh lemon juice with mint and soda', 69.00, '/images/beverages/lemonade.jpg', 'available', 5, NOW()),
('Cold Coffee', 'Chilled coffee with milk and ice cream', 99.00, '/images/beverages/cold_coffee.jpg', 'available', 5, NOW()),
('Masala Chai', 'Traditional Indian spiced tea', 39.00, '/images/beverages/chai.jpg', 'available', 5, NOW());

-- ============================================
-- INSERT USERS (skip if exists)
-- ============================================

-- Admin User (password: admin123 - will be encrypted by BCrypt)
INSERT IGNORE INTO users (name, email, password, phone, address, created_at) VALUES
('Administrator', 'admin@fooddelivery.com', 'admin123', '9876543210', 'Admin Office, Mumbai', NOW());

-- Sample Regular Users
INSERT IGNORE INTO users (name, email, password, phone, address, created_at) VALUES
('Rahul Sharma', 'rahul.sharma@example.com', 'rahul123', '9876543211', 'Andheri East, Mumbai - 400069', NOW()),
('Priya Patel', 'priya.patel@example.com', 'priya456', '9876543212', 'Connaught Place, Delhi - 110001', NOW());

-- ============================================
-- VERIFY DATA (Shows counts - this won't affect data)
-- ============================================
SELECT 'CATEGORIES' AS Table_Name, COUNT(*) AS Record_Count FROM categories
UNION ALL
SELECT 'PRODUCTS', COUNT(*) FROM products
UNION ALL
SELECT 'USERS', COUNT(*) FROM users;

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