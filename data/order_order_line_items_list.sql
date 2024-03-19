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
