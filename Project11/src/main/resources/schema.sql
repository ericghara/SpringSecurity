    DROP TABLE IF EXISTS customer;
    CREATE TABLE customer (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    service_rendered VARCHAR(75),
    address VARCHAR(50)
    );
