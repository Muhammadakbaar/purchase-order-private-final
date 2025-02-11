-- Insert data dummy untuk tabel users
INSERT INTO users (first_name, last_name, email, phone, created_by, updated_by, created_datetime, updated_datetime)
VALUES 
  ('John', 'Doe', 'john@example.com', '08123456789', 'admin', 'admin', NOW(), NOW()),
  ('Jane', 'Smith', 'jane@example.com', '08123456780', 'admin', 'admin', NOW(), NOW()),
  ('Admin', 'System', 'admin@example.com', '08120000000', 'system', 'system', NOW(), NOW());