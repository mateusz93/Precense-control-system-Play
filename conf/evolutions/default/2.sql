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

INSERT INTO `play`.`student_group` (`name`) VALUES ('I_2017');
INSERT INTO `play`.`student_group` (`name`) VALUES ('SPD_2016');
INSERT INTO `play`.`student_group` (`name`) VALUES ('IO_2017');
INSERT INTO `play`.`student_group` (`name`) VALUES ('BD_2017');
INSERT INTO `play`.`student_group` (`name`) VALUES ('ES_2017');

INSERT INTO `play`.`user` (`fieldID`, `username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `groupID`, `email`, `Status`)
  VALUES ((select id from `play`.`field` where name='Informatyka'), 'mateuszek', 'Mateusz', 'Wieczorek', 'Student', '92d7ddd2a010c59511dc2905b7e14f64', 1, (select id from `play`.`student_group` where name='I_2017'), 'mati@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`user` (`username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
  VALUES ('grzegorzmaslowski', 'Grzegorz', 'Maslowski', 'Teacher', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'grzegorz@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`user` (`username`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
  VALUES ('igordudek', 'Igor', 'Dudek', 'Admin', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'igor@edu.p.lodz.pl', 'ACTIVE');

commit;

-- student
-- mati@edu.p.lodz.pl
-- 1qaz@WSX

-- teacher
-- grzegorz@edu.p.lodz.pl
-- 1qaz@WSX

-- admin
-- igor@edu.p.lodz.pl
-- 1qaz@WSX