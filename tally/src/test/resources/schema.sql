
/*Table structure for table `tally_bill` */

# DROP TABLE IF EXISTS `tally_bill`;
#
# CREATE TABLE `tally_bill` (
#   `id` bigint(20) NOT NULL,
#   `main_seller` varchar(200) DEFAULT NULL,
#   `main_buyer` varchar(200) DEFAULT NULL,
#   `start_date` date DEFAULT NULL,
#   `end_date` date DEFAULT NULL,
#   `desc` varchar(2000) DEFAULT NULL,
#   `base_balance` decimal(16,4) DEFAULT NULL,
#   `final_balance` decimal(16,4) DEFAULT NULL,
#   PRIMARY KEY (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tally_deal` */

DROP TABLE IF EXISTS `tally_deal`;

CREATE TABLE `tally_deal` (
  `id` INT NOT NULL,
  `type` CHAR(4) DEFAULT NULL,
  `buyer` VARCHAR(200) DEFAULT NULL,
  `seller` VARCHAR(200) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `desc` VARCHAR(255) DEFAULT NULL,
  `price` DECIMAL(10,2) DEFAULT NULL,
  `volume` DECIMAL(10,2) DEFAULT NULL,
  `amount` DECIMAL(16,4) DEFAULT NULL,
  `unit` VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;