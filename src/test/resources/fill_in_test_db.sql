INSERT INTO roles (`role_id`, `role`)
VALUES ('1', 'ADMIN');
INSERT INTO roles (`role_id`, `role`)
VALUES ('2', 'USER');

INSERT INTO statuses (`status_id`, `status_name`)
VALUES ('1', 'ACTIVE');
INSERT INTO statuses (`status_id`, `status_name`)
VALUES ('2', 'BLOCKED');

INSERT INTO payment_systems (`payment_system_id`, `system_name`)
VALUES ('1', 'VISA');
INSERT INTO payment_systems (`payment_system_id`, `system_name`)
VALUES ('2', 'MASTERCARD');

INSERT INTO user_statuses (`user_status_id`, `user_status`)
VALUES ('1', 'NEW');
INSERT INTO user_statuses (`user_status_id`, `user_status`)
VALUES ('2', 'WAITING');
INSERT INTO user_statuses (`user_status_id`, `user_status`)
VALUES ('3', 'VERIFIED');

INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('1', 'USD', 'BYN', '2.66');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('2', 'USD', 'RUB', '80.57');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('3', 'USD', 'EUR', '0.86');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('4', 'USD', 'GBP', '0.77');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('5', 'EUR', 'USD', '1.16');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('6', 'EUR', 'BYN', '3.09');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('7', 'EUR', 'RUB', '93.79');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('8', 'EUR', 'GBP', '0.9');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('9', 'GBP', 'USD', '1.29');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('10', 'GBP', 'RUB', '104.04');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('11', 'GBP', 'BYN', '3.43');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('12', 'GBP', 'EUR', '1.11');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('13', 'BYN', 'USD', '0.38');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('14', 'BYN', 'EUR', '0.32');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('15', 'BYN', 'RUB', '30.31');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('16', 'BYN', 'GBP', '0.29');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('17', 'RUB', 'USD', '0.012');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('18', 'RUB', 'EUR', '0.011');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('19', 'RUB', 'GBP', '0.0096');
INSERT INTO exchange (`id`, `source_currency`, `final_currency`, `rate`)
VALUES ('20', 'RUB', 'BYN', '0.03');

INSERT INTO currencies (`currency_id`, `currency`)
VALUES ('1', 'USD');
INSERT INTO currencies (`currency_id`, `currency`)
VALUES ('2', 'EUR');
INSERT INTO currencies (`currency_id`, `currency`)
VALUES ('3', 'BYN');
INSERT INTO currencies (`currency_id`, `currency`)
VALUES ('4', 'RUB');
INSERT INTO currencies (`currency_id`, `currency`)
VALUES ('5', 'GBP');

INSERT INTO transaction_types (`id`, `type`)
VALUES ('1', 'TRANSFER');
INSERT INTO transaction_types (`id`, `type`)
VALUES ('2', 'PAYMENT');
INSERT INTO transaction_types (`id`, `type`)
VALUES ('3', 'DEPOSIT');
INSERT INTO transaction_types (`id`, `type`)
VALUES ('4', 'WITHDRAWAL');

INSERT INTO users (`user_id`, `login`, `password`, `email`, `role_id`, `user_status_id`)
VALUES ('1', 'admin', 'admin', 'palchmail@gmail.com', '1', '3');

INSERT INTO accounts (`account_id`, `account_number`, `balance`, `opening_date`, `user_id`, `status_id`, `currency_id`)
VALUES ('1', '100000USD1000000000', '1000', '2020-11-08', '1', '1', '1');

INSERT INTO user_details (`user_id`, `ru_name`, `ru_surname`, `en_name`, `en_surname`, `gender`,
                          `passport_series`, `passport_number`, `phone_number`, `location`)
VALUES ('1', 'Дмитрий', 'Пальчинский', 'Dzmitry', 'Palchynski', 'М', 'МР', '2756569', '+375-29-593-67-98',
        'г.Минск ул.Славинского');

INSERT INTO cards (`card_id`, `card_number`, `exp_date`, `owner_name`, `owner_surname`, `pincode`,
                   `cvv`, `account_id`, `status_id`, `payment_system_id`)
VALUES ('1', '4444333322221111', '2023-11-01', 'Dzmitry', 'Palchynski', '123', '321', '1', '1', '1');



