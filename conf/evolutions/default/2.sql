INSERT INTO `play`.`field` (`name`) VALUES ('Informatyka');
INSERT INTO `play`.`field` (`name`) VALUES ('Elektronika');
INSERT INTO `play`.`field` (`name`) VALUES ('Matematyka');
INSERT INTO `play`.`field` (`name`) VALUES ('Fizyka techniczna');
INSERT INTO `play`.`field` (`name`) VALUES ('Filologia angielska');
INSERT INTO `play`.`field` (`name`) VALUES ('Automatyka i robotyka');
INSERT INTO `play`.`field` (`name`) VALUES ('Mechatronika');
INSERT INTO `play`.`field` (`name`) VALUES ('Budownictwo');
INSERT INTO `play`.`field` (`name`) VALUES ('Zarządzanie');
INSERT INTO `play`.`field` (`name`) VALUES ('Prawo');
INSERT INTO `play`.`field` (`name`) VALUES ('Ekonomia');
INSERT INTO `play`.`field` (`name`) VALUES ('Psychologia');
INSERT INTO `play`.`field` (`name`) VALUES ('Biotechnologia');
INSERT INTO `play`.`field` (`name`) VALUES ('Geodezja i kartografia');
INSERT INTO `play`.`field` (`name`) VALUES ('Logistyka');
INSERT INTO `play`.`field` (`name`) VALUES ('Medycyna');
INSERT INTO `play`.`field` (`name`) VALUES ('Fizjoterapia');
INSERT INTO `play`.`field` (`name`) VALUES ('Chemia');

INSERT INTO `play`.`user` (`fieldID`, `username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `group`, `email`, `Status`)
  VALUES ((select id from `play`.`field` where name='Informatyka'), 'mateuszek', 'Mateusz', 'Wieczorek', 'Student', '92d7ddd2a010c59511dc2905b7e14f64', 1, 'I_2013', 'mati@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`user` (`username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
  VALUES ('grzegorzmaslowski', 'Grzegorz', 'Maslowski', 'Teacher', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'grzegorz@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`user` (`username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
  VALUES ('igordudek', 'Igor', 'Dudek', 'Admin', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'igor@edu.p.lodz.pl', 'ACTIVE');

INSERT INTO `play`.`subject` (`name`, `description`, `fieldid`, `yearOfStudy`, `quantity`, `minQuantity`)
  VALUES ('Podstawy programowania', 'Nauka od podstaw jezyka C++', (select id from `play`.`field` where name='Informatyka'), 1, 15, 13);
INSERT INTO `play`.`subject` (`name`, `description`, `fieldid`, `yearOfStudy`, `quantity`, `minQuantity`)
  VALUES ('Chemia ogólna', 'Nauczenie studenta ogólnej wiedzy z zakresu chemii', (select id from `play`.`field` where name='Chemia'), 1, 15, 13);
INSERT INTO `play`.`subject` (`name`, `description`, `fieldid`, `yearOfStudy`, `quantity`, `minQuantity`)
  VALUES ('Geometria wykreślna', 'Zrozumienie istoty odwzorowań metodą rzutowania elementów przestrzeni trójwymiarowej na płaszczyznę rysunku.',
        (select id from `play`.`field` where name='Budownictwo'), 1, 15, 13);
commit;
-- haslo do userow: 1qaz@WSX

--student
--mati@edu.p.lodz.pl
--1qaz@WSX

--teacher
--grzegorz@edu.p.lodz.pl
--1qaz@WSX

--admin
--igor@edu.p.lodz.pl
--1qaz@WSX