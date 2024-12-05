
---- CREATE TABLES-------
CREATE TABLE Supplier (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL UNIQUE
);
--sequences
CREATE SEQUENCE Supplier_seq
    MINVALUE 1
    MAXVALUE 999999
    INCREMENT BY 1
    START WITH 1388
    NOCACHE
    NOCYCLE;
CREATE TABLE Category
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    require_recipe BOOLEAN DEFAULT FALSE
);
CREATE TABLE Medicine
(
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   expiration_date DATE NOT NULL,
   stock INT CHECK (stock > 0),
   supplier_id INT,
   category_id INT,
   price DECIMAL(10, 2) NOT NULL,
   FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
       ON DELETE CASCADE
       ON UPDATE CASCADE,
   FOREIGN KEY (category_id) REFERENCES Category
       (id)
       ON DELETE CASCADE
       ON UPDATE CASCADE
);

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
    (name) VALUES ('Supplements');
INSERT INTO Category
    (name) VALUES ('Medicine');
INSERT INTO Category
    (name) VALUES ('Antibiotics');

-- Supplier
INSERT INTO Supplier (name) VALUES ('Bayer');
INSERT INTO Supplier (name) VALUES ('BioNTech');
INSERT INTO Supplier (name) VALUES ('Pfizer');

--Medicine

-- Insert into Medicine
-- table
INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Paracetamol', '2025-06-30', 100, 1, 1, 9.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Ibuprofen', '2024-12-15', 200, 1, 1, 14.50);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Amoxicillin', '2026-01-01', 150, 2, 2, 25.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Ciprofloxacin', '2025-03-20', 50, 2, 2, 32.75);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Aspirin', '2025-09-10', 300, 1, 1, 8.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Azithromycin', '2024-08-25', 120, 2, 2, 28.45);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Naproxen', '2026-05-12', 80, 1, 1, 16.30);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Cephalexin', '2024-11-19', 90, 2, 2, 21.10);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Diclofenac', '2025-07-07', 110, 1, 1, 12.75);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Doxycycline', '2026-03-05', 65, 2, 2, 26.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Metronidazole', '2024-10-10', 40, 2, 2, 19.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Ketoprofen', '2026-02-14', 70, 1, 1, 18.60);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Clindamycin', '2025-04-20', 55, 2, 2, 22.35);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Morphine', '2026-01-10', 30, 1, 1, 50.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Tetracycline', '2024-09-30', 150, 2, 2, 24.90);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Fentanyl', '2026-06-18', 20, 1, 1, 60.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Clarithromycin', '2024-07-15', 95, 2, 2, 27.50);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Codeine', '2025-12-01', 75, 1, 1, 35.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Erythromycin', '2025-08-22', 130, 2, 2, 23.75);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Tramadol', '2026-04-10', 60, 1, 1, 45.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Levofloxacin', '2025-02-18', 110, 2, 2, 30.50);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Hydrocodone', '2024-12-12', 40, 1, 1, 55.25);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Vancomycin', '2026-07-25', 25, 2, 2, 70.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Oxycodone', '2024-11-28', 50, 1, 1, 65.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Rifampicin', '2025-01-17', 85, 2, 2, 20.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Dilaudid', '2026-02-22', 35, 1, 1, 52.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Gentamicin', '2025-03-12', 75, 2, 2, 19.20);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Methadone', '2026-01-30', 50, 1, 1, 48.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Zithromax', '2024-06-20', 150, 2, 2, 25.80);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Pethidine', '2025-09-25', 45, 1, 1, 53.00);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Tobramycin', '2026-05-02', 55, 2, 2, 26.90);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Furosemide', '2024-10-11', 170, 1, 1, 13.50);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Neomycin', '2025-04-04', 70, 2, 2, 24.10);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Oxytocin', '2026-03-29', 25, 1, 1, 58.50);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Streptomycin', '2024-08-09', 100, 2, 2, 22.75);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Lidocaine', '2025-07-13', 140, 1, 1, 11.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Bacitracin', '2026-02-02', 90, 2, 2, 18.40);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Gabapentin', '2024-09-05', 200, 1, 1, 29.99);

INSERT INTO Medicine
    (name, expiration_date, stock, supplier_id, category_id, price)
VALUES ('Amikacin', '2025-06-17', 85, 2, 2, 32.10);

