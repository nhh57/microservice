create table users
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) null,
    name     varchar(255) null,
    password varchar(255) null
);


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



create table product
(
    id          bigint auto_increment
        primary key,
    category_id int            null,
    description varchar(255)   null,
    name        varchar(255)   null,
    price       decimal(19, 2) null
);

INSERT INTO testdata.product (id, category_id, description, name, price) VALUES (1, 1, 'iPhone 10', 'iPhone-10', 100000.00);
INSERT INTO testdata.product (id, category_id, description, name, price) VALUES (2, 2, 'iPhone 12', 'iPhone-12', 100000.00);
INSERT INTO testdata.product (id, category_id, description, name, price) VALUES (3, 3, 'iPhone 14', 'iPhone-14', 120000.00);
INSERT INTO testdata.product (id, category_id, description, name, price) VALUES (4, 4, 'iPhone 15', 'iPhone-15', 140000.00);


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


create table `order`
(
    id           bigint auto_increment
        primary key,
    order_number varchar(255) null
);

INSERT INTO testdata.`order` (id, order_number) VALUES (1, '3e6298c5-e828-467a-9c16-505539fc190c');
INSERT INTO testdata.`order` (id, order_number) VALUES (2, '8c30ec4c-9077-46df-b84b-3e7218636b96');
INSERT INTO testdata.`order` (id, order_number) VALUES (3, '6f0a1084-b658-4802-a0cc-6088a6333f55');
INSERT INTO testdata.`order` (id, order_number) VALUES (4, '01f5dfaf-b87e-48fd-9f8e-d608f19c5557');


create table order_order_line_items_list
(
    order_id                 bigint not null,
    order_line_items_list_id bigint not null,
    constraint UK_8qilr6dskq3lxwemieidrids
        unique (order_line_items_list_id),
    constraint FKp3hkbtjut4uh2o7ncyu5x5983
        foreign key (order_id) references `order` (id),
    constraint FKpjil8gfh7nwyrwd5lef2x41gj
        foreign key (order_line_items_list_id) references `t_order_line-items` (id)
);

INSERT INTO testdata.order_order_line_items_list (order_id, order_line_items_list_id) VALUES (1, 1);
INSERT INTO testdata.order_order_line_items_list (order_id, order_line_items_list_id) VALUES (2, 2);
INSERT INTO testdata.order_order_line_items_list (order_id, order_line_items_list_id) VALUES (3, 3);
INSERT INTO testdata.order_order_line_items_list (order_id, order_line_items_list_id) VALUES (4, 4);
