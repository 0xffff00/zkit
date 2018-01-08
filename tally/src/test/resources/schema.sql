
/*Table structure for table `tally_bill` */

DROP TABLE IF EXISTS `tally_bill`;

CREATE TABLE `tally_bill` (
  `id` bigint(20) NOT NULL,
  `main_seller` varchar(200) DEFAULT NULL,
  `main_buyer` varchar(200) DEFAULT NULL,
  `make_date` date DEFAULT NULL,
  `desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tally_bill_item` */

DROP TABLE IF EXISTS `tally_bill_item`;

CREATE TABLE `tally_bill_item` (
  `bill_id` bigint(20) NOT NULL,
  `deal_id` bigint(20) NOT NULL,
  `seq` int(11) NOT NULL,
  PRIMARY KEY (`bill_id`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `tally_deal` */

DROP TABLE IF EXISTS `tally_deal`;

CREATE TABLE `tally_deal` (
  `id` bigint(20) NOT NULL,
  `invoice_id` bigint(20) DEFAULT NULL,
  `buyer` varchar(200) DEFAULT NULL,
  `seller` varchar(200) DEFAULT NULL,
  `make_date` date DEFAULT NULL,
  `price` decimal(14,4) DEFAULT NULL,
  `volume` decimal(14,4) DEFAULT NULL,
  `amount` decimal(18,4) DEFAULT NULL,
  `unit` varchar(60) DEFAULT NULL,
  `type` char(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
