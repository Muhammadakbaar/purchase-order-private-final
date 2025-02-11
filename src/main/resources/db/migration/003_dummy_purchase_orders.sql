-- Insert data dummy untuk tabel purchase_order_headers
INSERT INTO purchase_order_headers (datetime, description, total_cost, total_price, created_by, updated_by, created_datetime, updated_datetime)
VALUES 
  (NOW(), 'PO untuk kebutuhan kantor', 34000000.00, 40000000.00, 'admin', 'admin', NOW(), NOW()),
  (NOW(), 'PO divisi IT', 10000000.00, 12000000.00, 'admin', 'editor', NOW(), NOW());