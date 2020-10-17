CREATE TABLE customer
(
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(45)       NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE shipment
(
    id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    description VARCHAR(90)       NOT NULL,
    customer_id SMALLINT UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE tracking
(
    id SMALLINT UNSIGNED                                                   NOT NULL AUTO_INCREMENT,
    status      ENUM ('initiated','in progress','delivered','cancelled','returned') NOT NULL,
    shipment_id SMALLINT UNSIGNED,
    event_date datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (shipment_id) REFERENCES shipment (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


INSERT INTO customer (id, name)
VALUES (1, 'Evgeny Grazhdansky'),
       (2, 'Ivan Petrov'),
       (3, 'Evgeny Grazhdansky'),
       (4, 'Alla Petrova'),
       (5, 'Dmitri Sokolov'),
       (6, 'Oleg Smirnov'),
       (7, 'Dmitri Sokolov');

INSERT INTO shipment (id, description, customer_id)
VALUES (1, 'TP-Link AC1350 Powerline Adapter', 1),
       (2, 'Bosch MAZ2BC for Car MUC2', 7),
       (3, '345gPU Leather Wallet Case with Card Slots and Stand', 1),
       (4, 'Emsa 513357 Insulated Travel Mug  Blue', 3),
       (5, 'Jaques Chess Box Complete Hand Carved', 6),
       (6, 'BiC Kids Tropicolors Colouring Pencils (Pack of 24)', 6),
       (7, 'Panasonic KX-TS520GB', 7);

INSERT INTO tracking(id, status, shipment_id, event_date)
VALUES (1, 'initiated', 2, CURRENT_TIMESTAMP),
       (2, 'cancelled', 4, CURRENT_TIMESTAMP),
       (3, 'delivered', 1, CURRENT_TIMESTAMP),
       (4, 'in progress', 3,CURRENT_TIMESTAMP),
       (5, 'cancelled', 7, CURRENT_TIMESTAMP),
       (6, 'delivered', 5, CURRENT_TIMESTAMP),
       (7, 'in progress', 6, CURRENT_TIMESTAMP);
