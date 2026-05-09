-- Create Database
CREATE DATABASE final_project;
USE final_project;

-- Table: users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Table: medicine
CREATE TABLE medicine (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    purchase_price DECIMAL(10,2),
    selling_price DECIMAL(10,2),
    stock INT DEFAULT 0
);

-- Table: sales
CREATE TABLE sales (
    sale_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id INT,
    quantity INT,
    total_amount DECIMAL(10,2),
    sale_date DATE,
    FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: borrow
CREATE TABLE borrow (
    borrow_id INT AUTO_INCREMENT PRIMARY KEY,
    chemist_name VARCHAR(100),
    medicine_id INT,
    quantity INT,
    borrow_date DATE,
    status VARCHAR(20),
    FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: supply
CREATE TABLE supply (
    supply_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id INT,
    quantity INT,
    supply_date DATE,
    FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: udhaar
CREATE TABLE udhaar (
    udhaar_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    amount DECIMAL(10,2),
    date DATE,
    status VARCHAR(20)
);

-- Default User
INSERT INTO users (username, password)
VALUES ('admin', '1234');