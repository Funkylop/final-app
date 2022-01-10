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
-- Table `final_app`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`users` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `email` VARCHAR(255) NOT NULL,
                                                   `password` VARCHAR(255) NOT NULL,
                                                   `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                   `role_` VARCHAR(20) NULL DEFAULT 'USER',
                                                   `status` VARCHAR(20) NULL DEFAULT 'BANNED',
                                                   `first_name` VARCHAR(100) NOT NULL,
                                                   `last_name` VARCHAR(100) NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE INDEX `idUser_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 89
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`admins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`admins` (
                                                    `id` INT NOT NULL,
                                                    PRIMARY KEY (`id`),
                                                    INDEX `fk_admins_users1_idx` (`id` ASC) VISIBLE,
                                                    CONSTRAINT `fk_admins_users1`
                                                        FOREIGN KEY (`id`)
                                                            REFERENCES `final_app`.`users` (`id`)
                                                            ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


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
    AUTO_INCREMENT = 7
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`game_objects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`game_objects` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `user_id` INT NULL DEFAULT NULL,
                                                          `game_id` INT NULL DEFAULT NULL,
                                                          `title` VARCHAR(255) NULL DEFAULT NULL,
                                                          `status` VARCHAR(50) NULL DEFAULT 'AVAILABLE',
                                                          `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                          `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                          `text` VARCHAR(255) NULL DEFAULT NULL,
                                                          PRIMARY KEY (`id`),
                                                          INDEX `fk_Game_object_User1_idx` (`user_id` ASC) VISIBLE,
                                                          INDEX `fk_Game_object_Game1_idx` (`game_id` ASC) VISIBLE,
                                                          CONSTRAINT `fk_Game_object_Game1`
                                                              FOREIGN KEY (`game_id`)
                                                                  REFERENCES `final_app`.`games` (`id`)
                                                                  ON DELETE CASCADE,
                                                          CONSTRAINT `fk_Game_object_User1`
                                                              FOREIGN KEY (`user_id`)
                                                                  REFERENCES `final_app`.`users` (`id`)
                                                                  ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 14
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
    AUTO_INCREMENT = 9
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`comments` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `message` VARCHAR(255) NOT NULL,
                                                      `approved` TINYINT NULL DEFAULT '0',
                                                      `author_id` INT NOT NULL,
                                                      `post_id` INT NOT NULL,
                                                      `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                      `mark` INT NOT NULL DEFAULT '0',
                                                      PRIMARY KEY (`id`),
                                                      UNIQUE INDEX `idComment_UNIQUE` (`id` ASC) VISIBLE,
                                                      UNIQUE INDEX `comment_idAuthor_idPost_uindex` (`author_id` ASC, `post_id` ASC) VISIBLE,
                                                      INDEX `fk_Comment_User1_idx` (`author_id` ASC) VISIBLE,
                                                      INDEX `fk_Comment_Post1_idx` (`post_id` ASC) VISIBLE,
                                                      CONSTRAINT `fk_Comment_Post1`
                                                          FOREIGN KEY (`post_id`)
                                                              REFERENCES `final_app`.`posts` (`id`)
                                                              ON DELETE CASCADE,
                                                      CONSTRAINT `fk_Comment_User1`
                                                          FOREIGN KEY (`author_id`)
                                                              REFERENCES `final_app`.`users` (`id`)
                                                              ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 20
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`common_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`common_users` (
                                                          `id` INT NOT NULL,
                                                          PRIMARY KEY (`id`),
                                                          INDEX `fk_common_user_users1_idx` (`id` ASC) VISIBLE,
                                                          CONSTRAINT `fk_common_user_users1`
                                                              FOREIGN KEY (`id`)
                                                                  REFERENCES `final_app`.`users` (`id`)
                                                                  ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`tokens` (
                                                    `confirmation_token` VARCHAR(255) NOT NULL,
                                                    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                    `token_type` VARCHAR(50) NULL DEFAULT NULL,
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `user_id` INT NULL DEFAULT NULL,
                                                    PRIMARY KEY (`id`),
                                                    UNIQUE INDEX `token_confirmation_token_uindex` (`confirmation_token` ASC) VISIBLE,
                                                    INDEX `tokens_users_id_fk` (`user_id` ASC) VISIBLE,
                                                    CONSTRAINT `tokens_users_id_fk`
                                                        FOREIGN KEY (`user_id`)
                                                            REFERENCES `final_app`.`users` (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 9
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`traders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`traders` (
                                                     `id` INT NOT NULL,
                                                     PRIMARY KEY (`id`),
                                                     INDEX `fk_traders_users1_idx` (`id` ASC) VISIBLE,
                                                     CONSTRAINT `fk_traders_users1`
                                                         FOREIGN KEY (`id`)
                                                             REFERENCES `final_app`.`users` (`id`)
                                                             ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `final_app`.`waiting_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `final_app`.`waiting_list` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `id_user` INT NULL DEFAULT NULL,
                                                          PRIMARY KEY (`id`),
                                                          UNIQUE INDEX `idwaiting_list_UNIQUE` (`id` ASC) VISIBLE,
                                                          INDEX `fk_waiting_list_User1_idx` (`id_user` ASC) VISIBLE,
                                                          CONSTRAINT `fk_waiting_list_User1`
                                                              FOREIGN KEY (`id_user`)
                                                                  REFERENCES `final_app`.`users` (`id`)
                                                                  ON DELETE CASCADE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 8
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
