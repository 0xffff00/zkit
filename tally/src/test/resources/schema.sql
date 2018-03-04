/*Table structure for table `tally_deal` */

DROP TABLE IF EXISTS `tally_deal`;

CREATE TABLE `tally_deal` (
  `buyer` varchar(200) NOT NULL,
  `seller` varchar(200) NOT NULL,
  `sn` int(11) NOT NULL,
  `type` char(4) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `volume` decimal(10,2) DEFAULT NULL,
  `amount` decimal(16,4) DEFAULT NULL,
  `unit` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`buyer`,`seller`,`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8