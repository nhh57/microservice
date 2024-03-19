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
