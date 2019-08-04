
-- Inventory
insert into Inventory(inventory_id) values(1);
insert into Inventory(inventory_id) values(2);
insert into Inventory(inventory_id) values(3);

-- Warehouse
insert into warehouse(warehouse_id, locality, quantity, type, inventory_id) values(1, 'SP', 3, 0, 1);
insert into warehouse(warehouse_id, locality, quantity, type, inventory_id) values(2, 'MG', 10, 1, 2);

-- Product
insert into Product(product_id, sku, name, inventory_id) values(1, 12345, 'Creme Facial', 1);
insert into Product(product_id, sku, name, inventory_id) values(2, 123456, 'Creme Corporal', 2);
insert into Product(product_id, sku, name, inventory_id) values(3, 123457, 'Esmalte vermelho', 3);
