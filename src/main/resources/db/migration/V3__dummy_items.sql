-- Insert data dummy untuk tabel items
INSERT INTO items (name, description, price, cost, created_by, updated_by, created_datetime, updated_datetime)
VALUES 
  ('Laptop', 'High-performance laptop', 15000000.00, 12000000.00, 'admin', 'admin', NOW(), NOW()),
  ('Printer', 'Wireless printer', 3000000.00, 2500000.00, 'admin', 'admin', NOW(), NOW()),
  ('Monitor', '27-inch 4K monitor', 5000000.00, 4000000.00, 'admin', 'admin', NOW(), NOW());