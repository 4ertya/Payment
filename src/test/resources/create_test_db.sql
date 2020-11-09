DROP TABLE IF EXISTS `test_payment`.`transactions`;
DROP TABLE IF EXISTS `test_payment`.`cards`;
DROP TABLE IF EXISTS `test_payment`.`accounts`;
DROP TABLE IF EXISTS `test_payment`.`statuses`;
DROP TABLE IF EXISTS `test_payment`.`users`;
DROP TABLE IF EXISTS `test_payment`.`roles`;
DROP TABLE IF EXISTS `test_payment`.`currencies`;
DROP TABLE IF EXISTS `test_payment`.`user_statuses`;


CREATE TABLE IF NOT EXISTS `test_payment`.`currencies`
(
    `currency_id` INT        NOT NULL AUTO_INCREMENT,
    `currency`    VARCHAR(3) NOT NULL,
    PRIMARY KEY (`currency_id`)
);
CREATE TABLE IF NOT EXISTS `test_payment`.`statuses`
(
    `status_id`   INT         NOT NULL,
    `status_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_id`)
);







CREATE TABLE IF NOT EXISTS `test_payment`.`roles`
(
    `role_id` INT         NOT NULL AUTO_INCREMENT,
    `role`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8;






CREATE TABLE IF NOT EXISTS `test_payment`.`user_statuses`
(
    `user_status_id` INT         NOT NULL,
    `user_status`    VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_status_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;






CREATE TABLE IF NOT EXISTS `test_payment`.`users`
(
    `user_id`        INT         NOT NULL AUTO_INCREMENT,
    `login`          VARCHAR(45) NOT NULL,
    `password`       VARCHAR(45) NOT NULL,
    `email`          VARCHAR(45) NOT NULL,
    `role_id`        INT         NOT NULL,
    `user_status_id` INT         NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`user_id` ASC) VISIBLE,
    INDEX `fk_users_roles1_idx` (`role_id` ASC) VISIBLE,
    INDEX `fk_users_user_statuses1_idx` (`user_status_id` ASC) VISIBLE,
    CONSTRAINT `fk_users_roles1`
        FOREIGN KEY (`role_id`)
            REFERENCES `test_payment`.`roles` (`role_id`),
    CONSTRAINT `fk_users_user_statuses1`
        FOREIGN KEY (`user_status_id`)
            REFERENCES `test_payment`.`user_statuses` (`user_status_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 28
    DEFAULT CHARACTER SET = utf8;






CREATE TABLE IF NOT EXISTS `test_payment`.`accounts`
(
    `account_id`     INT             NOT NULL AUTO_INCREMENT,
    `account_number` VARCHAR(45)     NOT NULL,
    `balance`        DOUBLE UNSIGNED NOT NULL DEFAULT '0',
    `opening_date`   DATE            NOT NULL,
    `user_id`        INT             NOT NULL,
    `status_id`      INT             NOT NULL DEFAULT '1',
    `currency_id`    INT             NOT NULL,
    PRIMARY KEY (`account_id`),
    UNIQUE INDEX `account_number_UNIQUE` (`account_number` ASC) VISIBLE,
    INDEX `fk_account_users1_idx` (`user_id` ASC) VISIBLE,
    INDEX `fk_account_status1_idx` (`status_id` ASC) VISIBLE,
    INDEX `fk_account_currences1_idx` (`currency_id` ASC) VISIBLE,
    CONSTRAINT `fk_account_currences1`
        FOREIGN KEY (`currency_id`)
            REFERENCES `test_payment`.`currencies` (`currency_id`),
    CONSTRAINT `fk_account_status1`
        FOREIGN KEY (`status_id`)
            REFERENCES `test_payment`.`statuses` (`status_id`),
    CONSTRAINT `fk_account_users1`
        FOREIGN KEY (`user_id`)
            REFERENCES `test_payment`.`users` (`user_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 19
    DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS `test_payment`.`payment_systems`;

CREATE TABLE IF NOT EXISTS `test_payment`.`payment_systems`
(
    `payment_system_id` INT         NOT NULL AUTO_INCREMENT,
    `system_name`            VARCHAR(45) NOT NULL,
    PRIMARY KEY (`payment_system_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;






CREATE TABLE IF NOT EXISTS `test_payment`.`cards`
(
    `card_id`                INT         NOT NULL AUTO_INCREMENT,
    `card_number`            BIGINT      NOT NULL,
    `exp_date`               DATE        NOT NULL,
    `owner_name`             VARCHAR(45) NOT NULL,
    `owner_surname`          VARCHAR(45) NOT NULL,
    `pincode`                INT         NOT NULL,
    `cvv`                    INT         NOT NULL,
    `account_id`             INT         NOT NULL,
    `status_id`              INT         NOT NULL DEFAULT '1',
    `payment_system_id` INT         NOT NULL,
    PRIMARY KEY (`card_id`),
    UNIQUE INDEX `card_number_UNIQUE` (`card_number` ASC) VISIBLE,
    INDEX `fk_cards_account1_idx` (`account_id` ASC) VISIBLE,
    INDEX `fk_cards_status1_idx` (`status_id` ASC) VISIBLE,
    INDEX `fk_cards_payment_systems1_idx` (`payment_system_id` ASC) VISIBLE,
    CONSTRAINT `fk_cards_account1`
        FOREIGN KEY (`account_id`)
            REFERENCES `test_payment`.`accounts` (`account_id`),
    CONSTRAINT `fk_cards_payment_systems1`
        FOREIGN KEY (`payment_system_id`)
            REFERENCES `test_payment`.`payment_systems` (`payment_system_id`),
    CONSTRAINT `fk_cards_status1`
        FOREIGN KEY (`status_id`)
            REFERENCES `test_payment`.`statuses` (`status_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 16
    DEFAULT CHARACTER SET = utf8;




DROP TABLE IF EXISTS `test_payment`.`exchange`;

CREATE TABLE IF NOT EXISTS `test_payment`.`exchange`
(
    `id`              INT             NOT NULL AUTO_INCREMENT,
    `source_currency` VARCHAR(45)     NOT NULL,
    `final_currency`  VARCHAR(45)     NOT NULL,
    `rate`            DOUBLE UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 21
    DEFAULT CHARACTER SET = utf8;



DROP TABLE IF EXISTS `test_payment`.`transaction_types`;

CREATE TABLE IF NOT EXISTS `test_payment`.`transaction_types`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8;





CREATE TABLE IF NOT EXISTS `test_payment`.`transactions`
(
    `id`                   INT            NOT NULL AUTO_INCREMENT,
    `amount`               DECIMAL(10, 0) NOT NULL,
    `currency_id`          INT            NOT NULL,
    `destination`          VARCHAR(45)    NOT NULL,
    `card_id`              INT            NOT NULL,
    `transaction_types_id` INT            NOT NULL,
    `date`                 TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_transactions_cards_idx` (`card_id` ASC) VISIBLE,
    INDEX `fk_transactions_transaction_types1_idx` (`transaction_types_id` ASC) VISIBLE,
    CONSTRAINT `fk_transactions_cards`
        FOREIGN KEY (`card_id`)
            REFERENCES `test_payment`.`cards` (`card_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 24
    DEFAULT CHARACTER SET = utf8;



DROP TABLE IF EXISTS `test_payment`.`user_details`;

CREATE TABLE IF NOT EXISTS `test_payment`.`user_details`
(
    `user_id`         BIGINT                           NOT NULL,
    `ru_name`         VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `ru_surname`      VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `en_name`         VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `en_surname`      VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `gender`          VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `passport_series` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `passport_number` INT                              NULL DEFAULT NULL,
    `phone_number`    VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `location`        VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
    `passport_scan`   BLOB                             NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    INDEX `fk_user_details_users1_idx` (`user_id` ASC) VISIBLE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_bin;


