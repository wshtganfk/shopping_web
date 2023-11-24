
drop table if exists Permission;
drop table if exists Order_item;
drop table if exists orders;
drop table if exists Order_item;
drop table if exists Watchlist;
drop table if exists User;
drop table if exists Product;
drop table if exists Watchlist;


CREATE TABLE IF NOT EXISTS User (
    user_id bigint auto_increment PRIMARY KEY,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    role int not null,
    unique(username),
    unique(email)
);
INSERT INTO User(username, password, email, role) VALUES
        ( 'user1', 'user1', '456@gmail.com', 0),
        ( 'user2', 'user2', '123@gmail.com', 0),
        ( 'seller1', 'seller1', '888@gmail.com', 1),
        ( 'seller2', 'seller2', '222@gmail.com', 1);



CREATE TABLE IF NOT EXISTS Permission (
    permission_id bigint auto_increment PRIMARY KEY,
    value varchar(255) not null,
    user_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
    );
INSERT INTO Permission(user_id, value) VALUES
        ( 1, 'read'),
        ( 1, 'update'),
        ( 3, 'write'),
        ( 3, 'read'),
        ( 3, 'update');


CREATE TABLE IF NOT EXISTS orders (
    order_id bigint auto_increment PRIMARY KEY,
    date_placed timestamp not null,
    order_status varchar(255) not null,
    user_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
INSERT INTO orders(date_placed, order_status, user_id) VALUES
        ( '2023-04-10 10:00:00', 'completed', 1),
        ( '2023-05-10 18:00:00', 'canceled', 1),
        ( '2023-06-10 13:00:00', 'processing', 1),
        ( '2023-08-10 12:00:00', 'processing', 2);



CREATE TABLE IF NOT EXISTS Product (
    product_id bigint auto_increment PRIMARY KEY,
    description varchar(255) not null,
    name varchar(255) not null,
    quantity int not null,
    retail_price double not null,
    wholesale_price double not null
    );
INSERT INTO Product(description, name, quantity, retail_price, wholesale_price) VALUES
     ( 'cigarette','Marlboro', 100, 8.99, 5.99),
     ( 'whisky','Suntory 12', 20, 200.00, 160.00),
     ( 'whisky','gold label', 50, 79.99, 69.99),
     ( 'whisky','Hibiki 21', 1, 1299.00, 1200.00);



CREATE TABLE IF NOT EXISTS order_item (
    item_id bigint auto_increment PRIMARY KEY,
    purchased_price double not null,
    quantity int not null,
    wholesale_price double not null,
    order_id bigint not null,
    product_id bigint not null,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
    );
INSERT INTO order_item(purchased_price, quantity, wholesale_price, order_id, product_id) VALUES
    ( 8.99, 10, 5.99, 1, 1),
    ( 200.00, 1, 160.00, 2, 2),
    ( 79.99, 3, 69.99, 3, 3),
    ( 1299.00, 1, 1200.00, 4, 4);

CREATE TABLE IF NOT EXISTS Watchlist (
                                    watchlist_id bigint auto_increment PRIMARY KEY,
                                    user_id bigint not null,
                                    product_id bigint not null,
                                    FOREIGN KEY (user_id) REFERENCES User(user_id),
                                    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);
INSERT INTO Watchlist(user_id, product_id) VALUES
    ( 1, 1),
    ( 1, 2),
    ( 1, 3),
    ( 2, 2);



