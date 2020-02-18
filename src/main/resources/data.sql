DROP TABLE IF EXISTS order_details;
CREATE TABLE `order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `customer_id` varchar(255) DEFAULT NULL,
  `item_id` varchar(255) DEFAULT NULL,
  `order_date` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;