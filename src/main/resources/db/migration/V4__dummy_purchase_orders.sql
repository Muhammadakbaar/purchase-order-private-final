-- Insert data dummy ke po_h
INSERT INTO po_h (datetime, description, total_cost, total_price, created_by, updated_by)
VALUES 
  (NOW(), 'PO untuk kebutuhan kantor', 34000000.00, 40000000.00, 'admin', 'admin'),
  (NOW(), 'PO divisi IT', 10000000.00, 12000000.00, 'admin', 'editor');

INSERT INTO po_d (item_id, item_qty, item_price, item_cost, poh_id)
VALUES 
  (1, 10, 15000000.00, 12000000.00, 1),
  (2, 5, 8000000.00, 6000000.00, 1);