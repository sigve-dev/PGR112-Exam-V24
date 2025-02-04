CREATE USER 'eksamenv24'@'localhost' IDENTIFIED BY 'password';

DROP DATABASE IF EXISTS Funn;
CREATE DATABASE Funn;

GRANT ALL PRIVILEGES ON Funn.* to 'eksamenv24'@'localhost';