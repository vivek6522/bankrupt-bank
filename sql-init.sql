
CREATE DATABASE `bankrupt`;
USE `bankrupt`;

CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(512) NOT NULL,
  `email` varchar(512) NOT NULL
) ENGINE='InnoDB' AUTO_INCREMENT=1;


CREATE TABLE `contracts` (
  `iban` varchar(64) NOT NULL PRIMARY KEY,
  `type` varchar(128) NOT NULL,
  `balance` decimal NOT NULL,
  `client_id` varchar(128) NOT NULL
) ENGINE='InnoDB';

CREATE TABLE `account` (
  `id` varchar(64) NOT NULL PRIMARY KEY,
  `type` varchar(128) NOT NULL,
  `balance` decimal NOT NULL
) ENGINE='InnoDB';

INSERT INTO `contracts` (`iban`, `type`, `balance`, `client_id`) VALUES ('NL80ABNA0419499482', 'current', '50000', uuid());
INSERT INTO `contracts` (`iban`, `type`, `balance`, `client_id`) VALUES ('NL80ABNA0419499483', 'savings', '50000', uuid());
INSERT INTO `contracts` (`iban`, `type`, `balance`, `client_id`) VALUES ('NL95ABNA0547637861', 'current', '0.0', uuid());
INSERT INTO `contracts` (`iban`, `type`, `balance`, `client_id`) VALUES ('NL95ABNA0123456789', 'current', '12345', uuid());

INSERT INTO `account` (`id`, `type`, `balance`) VALUES ('NL80ABNA0419499482', 'CURRENT', '50000');
INSERT INTO `account` (`id`, `type`, `balance`) VALUES ('NL80ABNA0419499483', 'SAVINGS', '50000');
INSERT INTO `account` (`id`, `type`, `balance`) VALUES ('NL95ABNA0547637861', 'CURRENT', '0');
INSERT INTO `account` (`id`, `type`, `balance`) VALUES ('NL95ABNA0123456789', 'CURRENT', '12345');

CREATE TABLE `activity` (
  `id` varchar(64) NOT NULL PRIMARY KEY,
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  `source_account_id` varchar(64) NOT NULL,
  `target_account_id` varchar(64) NOT NULL,
  `amount` bigint NOT NULL
) ENGINE='InnoDB';

CREATE TABLE `payments` (
  `id` varchar(64) NOT NULL PRIMARY KEY,
  `debtor_iban` varchar(64) NOT NULL,
  `creditor_iban` varchar(64) NOT NULL,
  `amount` decimal NOT NULL,
  `description` varchar(140),
  `creditor_name` varchar(70) NOT NULL
) ENGINE='InnoDB';

CREATE TABLE `transactions` (
  `id` varchar(64) NOT NULL,
  `status` varchar(32) NOT NULL,
  `submitted_on` timestamp DEFAULT CURRENT_TIMESTAMP,
  `final_balance` decimal NOT NULL,
  FOREIGN KEY (`id`) REFERENCES `payments` (`id`) ON DELETE RESTRICT
) ENGINE='InnoDB';
