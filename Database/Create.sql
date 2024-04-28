DROP SCHEMA IF EXISTS `intergrate-kp-2` ;
CREATE SCHEMA IF NOT EXISTS `intergrate-kp-2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `intergrate-kp-2` ;
-- Table `intergrate-kp-2`.`task`
DROP TABLE IF EXISTS `intergrate-kp-2`.`task` ;
CREATE TABLE IF NOT EXISTS `intergrate-kp-2`.`task` (
  `taskId` INT NOT NULL auto_increment,
  `taskTitle` VARCHAR(100) NOT NULL,
  `taskDescription` VARCHAR(500) NULL DEFAULT NULL,
  `taskAssignees` VARCHAR(30) NULL DEFAULT NULL,
  `taskStatus` ENUM('No Status', 'To Do', 'Doing', 'Done') NULL DEFAULT 'No Status',
  `createdOn` DATETIME  DEFAULT CURRENT_TIMESTAMP,
  `updatedOn` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`taskId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


DROP USER IF EXISTS 'dev'@'%';
CREATE USER 'dev'@'%' IDENTIFIED BY 'ip23kp2mysql';
GRANT ALL ON `intergrate-kp-2`.* TO 'dev'@'%';
