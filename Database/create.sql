set character_set_client='utf8';
set character_set_connection='utf8';
set character_set_database='utf8';
set character_set_results='utf8';
set character_set_server='utf8';

DROP SCHEMA IF EXISTS `intergrate-kp-2` ;
CREATE SCHEMA IF NOT EXISTS `intergrate-kp-2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `intergrate-kp-2` ;
-- Table `intergrate-kp-2`.`task`
DROP TABLE IF EXISTS `intergrate-kp-2`.`task` ;
CREATE TABLE IF NOT EXISTS `intergrate-kp-2`.`task` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `assignees` VARCHAR(30) NULL DEFAULT NULL,
  `status` ENUM('NO_STATUS', 'TO_DO', 'DOING', 'DONE') NULL DEFAULT 'NO_STATUS',
  `createdOn` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedOn` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
ALTER DATABASE `intergrate-kp-2` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


DROP USER IF EXISTS 'dev'@'%';
CREATE USER 'dev'@'%' IDENTIFIED BY 'ip23kp2mysql';
GRANT ALL ON `intergrate-kp-2`.* TO 'dev'@'%';
