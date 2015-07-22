--schema and data for the db used for the testing

CREATE  TABLE `person` (
  `person_id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `dob` DATETIME NOT NULL ,
  `sex` VARCHAR(45) NOT NULL ,
  `car_make` VARCHAR(45) NULL ,
  PRIMARY KEY (`person_id`) );