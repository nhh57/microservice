create table t_inventory
(
    id       bigint auto_increment
        primary key,
    quantity int          null,
    sku_code varchar(255) null
);

INSERT INTO testdata.t_inventory (id, quantity, sku_code) VALUES (1, 0, 'iPhone-10');
INSERT INTO testdata.t_inventory (id, quantity, sku_code) VALUES (2, 4, 'iPhone-12');
INSERT INTO testdata.t_inventory (id, quantity, sku_code) VALUES (3, 6, 'iPhone-14');
INSERT INTO testdata.t_inventory (id, quantity, sku_code) VALUES (4, 7, 'iPhone-15');
