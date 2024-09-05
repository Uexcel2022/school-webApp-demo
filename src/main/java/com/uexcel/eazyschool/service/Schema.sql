use eazyschool

CREATE TABLE IF NOT EXISTS `contact_msg` (
    `id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `mobile_num` varchar(30) NOT NULL,
    `email` varchar(50) NOT NULL,
    `subject` varchar(50) NOT NULL,
    `message` varchar(500) NOT NULL,
    `status` varchar(10) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP NULL,
    `updated_by` varchar(50) NULL
);

CREATE TABLE IF NOT EXISTS `holidays`(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    `day` varchar(50) NOT NULL,
    `reason` varchar(50) NOT NULL,
    `type` varchar(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP NULL,
    `updated_by` varchar(50) NULL
);