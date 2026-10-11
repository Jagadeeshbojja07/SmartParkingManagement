-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_parking
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `parking_records`
--

DROP TABLE IF EXISTS `parking_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_number` varchar(20) DEFAULT NULL,
  `slot_number` varchar(20) DEFAULT NULL,
  `entry_time` datetime DEFAULT NULL,
  `exit_time` datetime DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT '0.00',
  `vehicle_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_records`
--

LOCK TABLES `parking_records` WRITE;
/*!40000 ALTER TABLE `parking_records` DISABLE KEYS */;
INSERT INTO `parking_records` VALUES (1,'AP39AB1234','A1','2026-08-22 20:17:08',NULL,0.00,NULL),(2,'AP39AB1234','A1','2026-08-22 20:18:21',NULL,0.00,NULL),(3,'AP39AB1234','A1','2026-08-24 16:19:30',NULL,0.00,NULL),(4,'ap39cd1234','A2','2026-08-24 16:21:06',NULL,0.00,NULL),(5,'AP39TEST1234','A3','2026-08-26 13:07:04',NULL,20.00,NULL),(6,'AP39TEST1234','A2','2026-08-26 13:24:06',NULL,80.00,NULL),(7,'AP39CS1234','A3','2026-08-26 14:16:23',NULL,40.00,NULL),(8,'AP39CS2348','A2','2026-08-26 14:31:41',NULL,20.00,NULL),(9,'AP39TEST1234','A3','2026-08-26 14:59:19',NULL,40.00,NULL),(10,'AP39CS2345','A4','2026-08-26 14:59:48',NULL,20.00,NULL),(11,'AP39CS3453','A3','2026-08-26 15:00:11',NULL,20.00,NULL),(12,'AP39CS1234','A2','2026-08-31 13:55:06',NULL,20.00,NULL),(13,'AP39DS3421','A4','2026-09-02 18:49:39',NULL,1040.00,NULL),(14,'AP45DR4567','A1','2026-09-02 18:50:35',NULL,5160.00,NULL),(15,'AP39DE9345','A2','2026-09-03 20:05:55',NULL,40.00,'2'),(16,'AP39CS1234','A2','2026-09-04 15:34:00',NULL,40.00,'2'),(17,'AP39CS3456','A2','2026-09-10 19:34:33',NULL,1300.00,'1'),(18,'AP49CR2345','A3','2026-09-10 19:35:16',NULL,5700.00,'1'),(19,'apd345fdnrj','A4','2026-09-10 20:05:50',NULL,20.00,'1'),(20,'AP39CR5667','A5','2026-09-10 20:16:42',NULL,6520.00,'1'),(21,'AP39CS4343','A4','2026-09-10 20:19:08',NULL,5680.00,'1'),(22,'AP39CD4567','A2','2026-09-13 12:17:54',NULL,20.00,'1'),(23,'add','A1','2026-09-17 16:46:26',NULL,20.00,'1'),(24,'dbt','A1','2026-09-17 16:46:46',NULL,2380.00,'1'),(25,'AP49XDRETTG','A1','2026-09-22 15:41:10',NULL,20.00,'1'),(26,'FRGRR','A2','2026-09-22 15:42:03',NULL,20.00,'1'),(27,'AP39CS4545','A2','2026-09-22 15:44:09',NULL,20.00,'1'),(28,'dsf','A2','2026-09-22 16:36:11',NULL,20.00,'1'),(29,'rjfe','A2','2026-09-22 16:38:17',NULL,40.00,'2'),(30,'7707','A1','2026-09-24 09:13:21',NULL,40.00,'4'),(31,'7707','A1','2026-09-24 09:47:37',NULL,20.00,'4'),(32,'3304','A2','2026-10-11 13:37:13',NULL,20.00,'1');
/*!40000 ALTER TABLE `parking_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parking_slots`
--

DROP TABLE IF EXISTS `parking_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parking_slots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `slot_number` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `vehicle_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking_slots`
--

LOCK TABLES `parking_slots` WRITE;
/*!40000 ALTER TABLE `parking_slots` DISABLE KEYS */;
INSERT INTO `parking_slots` VALUES (1,'A1','FREE',NULL),(2,'A2','FREE',NULL),(3,'A3','FREE',NULL),(4,'A4','FREE',NULL),(5,'A5','FREE',NULL);
/*!40000 ALTER TABLE `parking_slots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_number` varchar(20) NOT NULL,
  `slot_number` varchar(10) NOT NULL,
  `reservation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(20) DEFAULT 'RESERVED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Admin','admin@gmail.com','admin123','ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-11 14:25:29
