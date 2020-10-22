-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: localhost    Database: liburuak
-- ------------------------------------------------------
-- Server version	8.0.21-0ubuntu0.20.04.4

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
-- Table structure for table `Argitaletxea`
--

DROP TABLE IF EXISTS `Argitaletxea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Argitaletxea` (
  `izena` varchar(45) NOT NULL,
  PRIMARY KEY (`izena`),
  UNIQUE KEY `izena_UNIQUE` (`izena`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Argitaletxea`
--

LOCK TABLES `Argitaletxea` WRITE;
/*!40000 ALTER TABLE `Argitaletxea` DISABLE KEYS */;
/*!40000 ALTER TABLE `Argitaletxea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LiburuArgit`
--

DROP TABLE IF EXISTS `LiburuArgit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LiburuArgit` (
  `liburuIsbn` varchar(45) NOT NULL,
  `argitIzen` varchar(45) NOT NULL,
  PRIMARY KEY (`liburuIsbn`,`argitIzen`),
  UNIQUE KEY `liburuIsbn_UNIQUE` (`liburuIsbn`),
  UNIQUE KEY `argitIzen_UNIQUE` (`argitIzen`),
  CONSTRAINT `fk_LiburuArgit_Argitaletxea` FOREIGN KEY (`argitIzen`) REFERENCES `Argitaletxea` (`izena`),
  CONSTRAINT `fk_LiburuArgit_Liburua` FOREIGN KEY (`liburuIsbn`) REFERENCES `Liburua` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LiburuArgit`
--

LOCK TABLES `LiburuArgit` WRITE;
/*!40000 ALTER TABLE `LiburuArgit` DISABLE KEYS */;
/*!40000 ALTER TABLE `LiburuArgit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Liburua`
--

DROP TABLE IF EXISTS `Liburua`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Liburua` (
  `isbn` varchar(45) NOT NULL,
  `izenburua` varchar(45) DEFAULT NULL,
  `azpititulua` varchar(45) DEFAULT NULL,
  `orriKop` int DEFAULT NULL,
  `argazkiIzen` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  UNIQUE KEY `isbn_UNIQUE` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Liburua`
--

LOCK TABLES `Liburua` WRITE;
/*!40000 ALTER TABLE `Liburua` DISABLE KEYS */;
/*!40000 ALTER TABLE `Liburua` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-22 17:25:51
