INSERT INTO `play`.`Field` (`name`) VALUES ('Informatyka');

INSERT INTO `play`.`User` (`fieldID`, `loginSubmit`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `group`, `email`, `Status`)
VALUES ((select id from `play`.`Field` where name='Informatyka'), 'mateuszek', 'Mateusz', 'Wieczorek', 'Student', '92d7ddd2a010c59511dc2905b7e14f64', 1, 'I_2013', 'mati@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`User` (`loginSubmit`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
VALUES ('grzegorzmaslowski', 'Grzegorz', 'Maslowski', 'Teacher', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'grzegorz@edu.p.lodz.pl', 'ACTIVE');
INSERT INTO `play`.`User` (`loginSubmit`, `firstName`, `lastName`, `type`, `password`, `yearOfStudy`, `email`, `Status`)
VALUES ('igordudek', 'Igor', 'Dudek', 'Admin', '92d7ddd2a010c59511dc2905b7e14f64', 0, 'igor@edu.p.lodz.pl', 'ACTIVE');

commit;
-- haslo do userow: 1qaz@WSX