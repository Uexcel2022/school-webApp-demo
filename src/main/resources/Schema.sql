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
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `holidays`(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    `day` varchar(50) NOT NULL,
    `reason` varchar(50) NOT NULL,
    `type` varchar(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `person` (
    `id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50) NOT NULL,
    `mobile_number` varchar(30) NOT NULL,
    `email` varchar(50) NOT NULL,
    `pwd` varchar(50) NOT NULL,
    `role_id` INTEGER NULL,
    `address_id` INTEGER NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL,
    FOREIGN  KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY(address_id) REFERENCES address(id)
    );

CREATE TABLE IF NOT EXISTS `address` (
    `id` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `address1` varchar(200) NOT NULL,
    `address2` varchar(200) NOT NULL,
    `city` varchar(50) NOT NULL,
    `state` varchar(50) NOT NULL,
    `zip_code` varchar(500) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
    );

CREATE TABLE IF NOT EXISTS `roles`(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    `role_name` varchar(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
    );


INSERT INTO eazyschool.person (`name`, mobile_number, email, pwd, role_id, created_at, created_by)
VALUES ('Udoka', '07081023546','uexcel@gmail.com','jvman19#', 1 ,CURDATE(),'uexcel');

CREATE TABLE IF NOT EXISTS `class`(
                                      id bigint AUTO_INCREMENT PRIMARY KEY,
                                      `name` varchar(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` varchar(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` varchar(50) DEFAULT NULL
    );

alter table eazyschool.person
    add column `class_id` bigint null after address_id,
add constraint `fk_class_id` foreign key(`class_id`) references `class`(id)