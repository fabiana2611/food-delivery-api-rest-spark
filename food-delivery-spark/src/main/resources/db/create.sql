/*
DROP TABLE IF EXISTS `restaurants`;
DROP TABLE IF EXISTS `foodtypes`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `reviews`;
DROP TABLE IF EXISTS `restaurants_foodtypes`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `Orders`;
DROP TABLE IF EXISTS `orders_products`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;
*/



CREATE TABLE IF NOT EXISTS restaurants (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 address VARCHAR,
 zipcode VARCHAR,
 phone VARCHAR,
 website VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS foodtypes (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
);

CREATE TABLE IF NOT EXISTS reviews (
 id int PRIMARY KEY auto_increment,
 writtenby VARCHAR,
 content VARCHAR,
 rating VARCHAR,
 restaurantid INTEGER,
 createdat BIGINT
);

CREATE TABLE IF NOT EXISTS restaurants_foodtypes (
 id int PRIMARY KEY auto_increment,
 foodtypeid INTEGER,
 restaurantid INTEGER
);

CREATE TABLE IF NOT EXISTS products (
 id int PRIMARY KEY auto_increment,
 productName VARCHAR, 
 productDescription VARCHAR, 
 productPrice decimal, 
 restaurantId INTEGER, 
 foodtypeId INTEGER
);

CREATE TABLE IF NOT EXISTS Orders (
 id int PRIMARY KEY auto_increment,
 userId INTEGER,
 orderStatus INTEGER,
 orderAddress VARCHAR,
 orderDate DATE
);

CREATE TABLE IF NOT EXISTS orders_products (
 id int PRIMARY KEY auto_increment,
 orderId INTEGER,
 productId INTEGER,
 quantity INTEGER
);

CREATE TABLE IF NOT EXISTS roles (
  id int PRIMARY KEY auto_increment,
  name VARCHAR
);

CREATE TABLE IF NOT EXISTS users (
  id int PRIMARY KEY auto_increment,
  active int,
  email varchar,
  lastName varchar,
  name varchar,
  password varchar,
  roleId int
);

