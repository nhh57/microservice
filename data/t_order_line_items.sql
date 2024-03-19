create table `t_order_line-items`
(
    id       bigint auto_increment
        primary key,
    price    decimal(19, 2) null,
    quantity int            null,
    sku_code varchar(255)   null
);

INSERT INTO testdata.`t_order_line-items` (id, price, quantity, sku_code) VALUES (1, 1200.00, 1, 'iphone_13');
INSERT INTO testdata.`t_order_line-items` (id, price, quantity, sku_code) VALUES (2, 1200.00, 1, 'iphone_13');
INSERT INTO testdata.`t_order_line-items` (id, price, quantity, sku_code) VALUES (3, 1200.00, 1, 'iPhone-12');
INSERT INTO testdata.`t_order_line-items` (id, price, quantity, sku_code) VALUES (4, 1200.00, 1, 'iPhone-12');
