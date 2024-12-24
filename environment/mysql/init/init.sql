-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: testdata
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `testdata`
--

/*!40000 DROP DATABASE IF EXISTS `testdata`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `testdata` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `testdata`;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'3e6298c5-e828-467a-9c16-505539fc190c'),(2,'8c30ec4c-9077-46df-b84b-3e7218636b96'),(3,'6f0a1084-b658-4802-a0cc-6088a6333f55'),(4,'01f5dfaf-b87e-48fd-9f8e-d608f19c5557');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_order_line_items_list`
--

DROP TABLE IF EXISTS `order_order_line_items_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_order_line_items_list` (
  `order_id` bigint NOT NULL,
  `order_line_items_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_8qilr6dskq3lxwemieidrids` (`order_line_items_list_id`),
  KEY `FKp3hkbtjut4uh2o7ncyu5x5983` (`order_id`),
  CONSTRAINT `FKp3hkbtjut4uh2o7ncyu5x5983` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `FKpjil8gfh7nwyrwd5lef2x41gj` FOREIGN KEY (`order_line_items_list_id`) REFERENCES `t_order_line-items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_order_line_items_list`
--

LOCK TABLES `order_order_line_items_list` WRITE;
/*!40000 ALTER TABLE `order_order_line_items_list` DISABLE KEYS */;
INSERT INTO `order_order_line_items_list` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `order_order_line_items_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `payment_date` datetime DEFAULT NULL,
  `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `saga_step` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `retry_count` int DEFAULT '0',
  `last_error` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_payment` (`order_id`),
  CONSTRAINT `FK_order_payment` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,100000.00,NULL,'Credit Card','PENDING',NULL,'INITIATED',0,NULL),(2,2,120000.00,NULL,'PayPal','PENDING',NULL,'PAYMENT_PENDING',0,NULL);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_line-items`
--

DROP TABLE IF EXISTS `t_order_line-items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_line-items` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `order_id` bigint NOT NULL COMMENT 'Reference to order table',
  `ticket_item_id` bigint NOT NULL COMMENT 'Reference to ticket item',
  `quantity` int NOT NULL COMMENT 'Quantity of tickets ordered',
  `price` bigint NOT NULL COMMENT 'Price of the ticket ordered',
  `total` bigint NOT NULL COMMENT 'Total amount for the line item',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Created timestamp',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Updated timestamp',
  PRIMARY KEY (`id`),
  KEY `idx_ticket_item_id` (`ticket_item_id`),
  KEY `idx_order_id` (`order_id`),
  CONSTRAINT `FK_order_line_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  CONSTRAINT `FK_ticket_item_line_item` FOREIGN KEY (`ticket_item_id`) REFERENCES `ticket_item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Table for order line items';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_line-items`
--

LOCK TABLES `t_order_line-items` WRITE;
/*!40000 ALTER TABLE `t_order_line-items` DISABLE KEYS */;
INSERT INTO `t_order_line-items` VALUES (5,1,1,1,100000,200000,'2024-12-24 22:26:17','2024-12-24 22:26:19'),(6,2,2,2,200000,200000,'2024-12-24 22:26:17','2024-12-24 22:26:19'),(7,3,3,3,300000,900000,'2024-12-24 22:26:17','2024-12-24 22:26:19'),(8,4,4,4,200000,200000,'2024-12-24 22:26:17','2024-12-24 22:26:19');
/*!40000 ALTER TABLE `t_order_line-items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ticket name',
  `desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'ticket description',
  `start_time` datetime NOT NULL COMMENT 'ticket sale start time',
  `end_time` datetime NOT NULL COMMENT 'ticket sale end time',
  `status` int NOT NULL DEFAULT '0' COMMENT 'ticket sale activity status',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Last update time',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation time',
  PRIMARY KEY (`id`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ticket table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'Đợt Mở Bán Vé Ngày 12/12','Sự kiện mở bán vé đặc biệt cho ngày 12/12','2024-12-12 00:00:00','2024-12-12 23:59:59',1,'2024-12-24 15:08:11','2024-12-24 15:08:11'),(2,'Đợt Mở Bán Vé Ngày 01/01','Sự kiện mở bán vé cho ngày đầu năm mới 01/01','2025-01-01 00:00:00','2025-01-01 23:59:59',1,'2024-12-24 15:08:11','2024-12-24 15:08:11');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_item`
--

DROP TABLE IF EXISTS `ticket_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Ticket title',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Ticket description',
  `stock_initial` int NOT NULL DEFAULT '0' COMMENT 'Initial stock quantity (e.g., 1000 tickets)',
  `stock_available` int NOT NULL DEFAULT '0' COMMENT 'Current available stock (e.g., 900 tickets)',
  `is_stock_prepared` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Indicates if stock is pre-warmed (0/1)',
  `price_original` bigint NOT NULL COMMENT 'Original ticket price',
  `price_flash` bigint NOT NULL COMMENT 'Discounted price during flash sale',
  `sale_start_time` datetime NOT NULL COMMENT 'Flash sale start time',
  `sale_end_time` datetime NOT NULL COMMENT 'Flash sale end time',
  `status` int NOT NULL DEFAULT '0' COMMENT 'Ticket status (e.g., active/inactive)',
  `activity_id` bigint NOT NULL COMMENT 'ID of associated activity',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp of the last update',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation timestamp',
  PRIMARY KEY (`id`),
  KEY `idx_end_time` (`sale_end_time`),
  KEY `idx_start_time` (`sale_start_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Table for ticket details';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_item`
--

LOCK TABLES `ticket_item` WRITE;
/*!40000 ALTER TABLE `ticket_item` DISABLE KEYS */;
INSERT INTO `ticket_item` VALUES (1,'Vé Sự Kiện 12/12 - Hạng Phổ Thông','Vé phổ thông cho sự kiện ngày 12/12',1000,1000,0,100000,10000,'2024-12-12 00:00:00','2024-12-12 23:59:59',1,1,'2024-12-24 15:08:11','2024-12-24 15:08:11'),(2,'Vé Sự Kiện 12/12 - Hạng VIP','Vé VIP cho sự kiện ngày 12/12',500,500,0,200000,15000,'2024-12-12 00:00:00','2024-12-12 23:59:59',1,1,'2024-12-24 15:08:11','2024-12-24 15:08:11'),(3,'Vé Sự Kiện 01/01 - Hạng Phổ Thông','Vé phổ thông cho sự kiện ngày 01/01',2000,2000,0,100000,10000,'2025-01-01 00:00:00','2025-01-01 23:59:59',1,2,'2024-12-24 15:08:11','2024-12-24 15:08:11'),(4,'Vé Sự Kiện 01/01 - Hạng VIP','Vé VIP cho sự kiện ngày 01/01',1000,1000,0,200000,15000,'2025-01-01 00:00:00','2025-01-01 23:59:59',1,2,'2024-12-24 15:08:11','2024-12-24 15:08:11');
/*!40000 ALTER TABLE `ticket_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-24 15:27:04
