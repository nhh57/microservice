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
