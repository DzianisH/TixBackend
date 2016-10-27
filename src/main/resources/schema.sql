DROP DATABASE IF EXISTS tix;
CREATE DATABASE tix;
USE tix;

DROP TABLE IF EXISTS User;
CREATE TABLE User(
  id INT PRIMARY KEY AUTO_INCREMENT,
  color INT,
  login VARCHAR(31)
);

INSERT INTO User(color, login) VALUE (1, 'root');
INSERT INTO User(color, login) VALUE (2342, 'karamba');
INSERT INTO User(color, login) VALUE (0x00ff0000, 'lol');