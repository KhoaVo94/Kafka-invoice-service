DROP TABLE IF EXISTS `invoice_test`.`persons`;
CREATE TABLE `invoice_test`.`persons` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `address_line` VARCHAR(200),
    `city` VARCHAR(100) NOT NULL,
    `country` VARCHAR(100) NOT NULL,
    `state` VARCHAR(5) NOT NULL,
    `zip` INTEGER NOT NULL,
    `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `invoice_test`.`items`;
CREATE TABLE `invoice_test`.`items` (
      `id` INTEGER NOT NULL AUTO_INCREMENT,
      `name` VARCHAR(100) NOT NULL,
      `description` VARCHAR(200),
      `rate` DECIMAL NOT NULL,
      `quantity` INTEGER NOT NULL,
      `invoice_number` VARCHAR(50) NOT NULL,
      `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `invoice_test`.`invoices`;
CREATE TABLE `invoice_test`.`invoices` (
     `invoice_number` VARCHAR(50) NOT NULL,
     `seller_id` INTEGER NOT NULL,
     `customer_id` INTEGER NOT NULL,
     `discount_rate` INTEGER NOT NULL DEFAULT 0,
     `tax_rate` INTEGER NOT NULL DEFAULT 0,
     `invoice_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`invoice_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;