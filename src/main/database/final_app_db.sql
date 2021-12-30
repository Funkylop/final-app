-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema final_app
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema final_app
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `final_app` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `final_app` ;

-- -----------------------------------------------------
-- Table `final_app`.`games`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`games` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idGame_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `game_title_uindex` (`title` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role_` VARCHAR(20) NOT NULL DEFAULT 'USER',
  `status` VARCHAR(20) NULL DEFAULT 'BANNED',
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idUser_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`game_objects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`game_objects` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_game` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `status` ENUM('AVAILABLE', 'SOLD') NOT NULL DEFAULT 'AVAILABLE',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `text` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Game_object_User1_idx` (`id_user` ASC) VISIBLE,
  INDEX `fk_Game_object_Game1_idx` (`id_game` ASC) VISIBLE,
  CONSTRAINT `fk_Game_object_Game1`
    FOREIGN KEY (`id_game`)
    REFERENCES `final_app`.`games` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_Game_object_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `final_app`.`users` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`posts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_game_object` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idPost_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `post_idGameObject_uindex` (`id_game_object` ASC) VISIBLE,
  INDEX `fk_Post_Game_object1_idx` (`id_game_object` ASC) VISIBLE,
  CONSTRAINT `fk_Post_Game_object1`
    FOREIGN KEY (`id_game_object`)
    REFERENCES `final_app`.`game_objects` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(255) NOT NULL,
  `approved` TINYINT NULL DEFAULT '0',
  `id_author` INT NOT NULL,
  `id_post` INT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `mark` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idComment_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `comment_idAuthor_idPost_uindex` (`id_author` ASC, `id_post` ASC) VISIBLE,
  INDEX `fk_Comment_User1_idx` (`id_author` ASC) VISIBLE,
  INDEX `fk_Comment_Post1_idx` (`id_post` ASC) VISIBLE,
  CONSTRAINT `fk_Comment_Post1`
    FOREIGN KEY (`id_post`)
    REFERENCES `final_app`.`posts` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`id_author`)
    REFERENCES `final_app`.`users` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`tokens` (
  `confirmation_token` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `token_type` VARCHAR(50) NOT NULL,
  UNIQUE INDEX `token_confirmationToken_uindex` (`confirmation_token` ASC) VISIBLE,
  INDEX `fk_token_idx` (`email` ASC) INVISIBLE,
  CONSTRAINT `fk_users`
    FOREIGN KEY (`email`)
    REFERENCES `final_app`.`users` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`waiting_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`waiting_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idwaiting_list_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_waiting_list_User1_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_waiting_list_User1`
    FOREIGN KEY (`id_user`)
    REFERENCES `final_app`.`users` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
