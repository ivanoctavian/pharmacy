
---- CREATE TABLES-------
-- SUPPLIER --
CREATE SEQUENCE Supplier_seq
    MINVALUE 1
    MAXVALUE 999999
    INCREMENT BY 1
    START WITH 1388
    NOCACHE
    NOCYCLE;
CREATE TABLE Supplier (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL UNIQUE
);

-- CATEGORY --
CREATE SEQUENCE Category_seq
    MINVALUE 1
    MAXVALUE 999999
    INCREMENT BY 1
    START WITH 201901
    NOCACHE
    NOCYCLE;
CREATE TABLE Category
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    require_recipe BOOLEAN DEFAULT FALSE
);

-- MEDICINE --
CREATE SEQUENCE Medicine_seq
    MINVALUE 1
    MAXVALUE 999999
    INCREMENT BY 1
    START WITH 371001
    NOCACHE
    NOCYCLE;
CREATE TABLE Medicine
(
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL UNIQUE,
   expiration_date DATE NOT NULL,
   stock INT CHECK (stock > 0),
   supplier_id INT,
   category_id INT,
   substance_name VARCHAR (255),
   producer VARCHAR (255),
   price DECIMAL(10, 2) NOT NULL,
   FOREIGN KEY (supplier_id) REFERENCES Supplier(id)
       ON DELETE CASCADE
       ON UPDATE CASCADE,
   FOREIGN KEY (category_id) REFERENCES Category
       (id)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);


-- USERS --
CREATE SEQUENCE Users_seq
    MINVALUE 1
    MAXVALUE 999999
    INCREMENT BY 1
    START WITH 822001
    NOCACHE
    NOCYCLE;
CREATE TABLE Users (
   id INT PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   enabled BOOLEAN DEFAULT TRUE,
   role VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


--- INITIAL DATA ----
--- users
INSERT INTO Users (username, password, enabled, role)
VALUES ('ivan_octavian', '1234', TRUE, 'ROLE_ADMIN');--only him can access the endpoints for management of users.

INSERT INTO Users (username, password, enabled, role)
VALUES ('mara.enache', 'parola1234', TRUE, 'ROLE_EMPLOYEE');

INSERT INTO Users (username, password, enabled, role)
VALUES ('stefan_1998', 'pwd1998', TRUE, 'ROLE_EMPLOYEE');

INSERT INTO Users (username, password, enabled, role)
VALUES ('ali_medea', 'pwdmedea', FALSE, 'ROLE_EMPLOYEE');

-- Category

INSERT INTO Category
(name, require_recipe) VALUES ('Supplements', FALSE );
INSERT INTO Category
(name, require_recipe) VALUES ('Medicine', FALSE );
INSERT INTO Category
    (name, require_recipe) VALUES ('Antibiotics', TRUE);

-- Supplier
INSERT INTO Supplier (name) VALUES ('Bayer');
INSERT INTO Supplier (name) VALUES ('BioNTech');
INSERT INTO Supplier (name) VALUES ('Pfizer');

--Medicine

-- Insert into Medicine
-- table

