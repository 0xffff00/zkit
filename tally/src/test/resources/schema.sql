
/*Table structure for table `tally_bill` */

DROP TABLE IF EXISTS `tally_bill`;

CREATE TABLE `tally_bill` (
  `id` bigint(20) NOT NULL,
  `main_seller` varchar(200) DEFAULT NULL,
  `main_buyer` varchar(200) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `base_balance` decimal(12,3) DEFAULT NULL,
  `final_balance` decimal(12,3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tally_deal` */

DROP TABLE IF EXISTS `tally_deal`;

CREATE TABLE `tally_deal` (
  `id` bigint(20) NOT NULL,
  `bill_id` bigint(20) DEFAULT NULL,
  `invoice_id` bigint(20) DEFAULT NULL,
  `buyer` varchar(200) DEFAULT NULL,
  `seller` varchar(200) DEFAULT NULL,
  `make_date` date DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `volume` decimal(10,2) DEFAULT NULL,
  `amount` decimal(16,4) DEFAULT NULL,
  `unit` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;