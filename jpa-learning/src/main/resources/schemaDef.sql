CREATE  TABLE `emp` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `phone` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );

ALTER TABLE `emp` ADD COLUMN `dogName` VARCHAR(45) NULL  AFTER `phone`;

ALTER TABLE `emp` ADD COLUMN `comments` VARCHAR(45) NULL  AFTER `dogName` ;