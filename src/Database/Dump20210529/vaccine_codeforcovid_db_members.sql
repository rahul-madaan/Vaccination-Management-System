-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: vaccine_codeforcovid_db
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `RefID` int NOT NULL AUTO_INCREMENT,
  `PhoneNumber` varchar(10) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `AadhaarNumber` varchar(255) NOT NULL,
  `DOB` date NOT NULL,
  `Age` int,
  `Gender` tinyint(1) DEFAULT NULL,
  `Dose1Status` varchar(30) NOT NULL DEFAULT 'Not vaccinated',
  `Dose2Status` varchar(30) DEFAULT 'Not vaccinated',
  `Dose1CentreID` int DEFAULT NULL,
  `Dose2CentreID` int DEFAULT NULL,
  `Dose1Date` varchar(10) DEFAULT NULL,
  `Dose2Date` varchar(10) DEFAULT NULL,
  `Dose1Slot` int DEFAULT NULL,
  `Dose2Slot` int DEFAULT NULL,
  `Dose1VaccineName` varchar(30) DEFAULT NULL,
  `Dose2VaccineName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`AadhaarNumber`),
  UNIQUE KEY `members_RefID_uindex` (`RefID`),
  UNIQUE KEY `members_AdhaarNumber_uindex` (`AadhaarNumber`),
  KEY `members_login_users_PhoneNumber_fk` (`PhoneNumber`),
  KEY `members_vaccinecentres_CentreID_fk_2` (`Dose2CentreID`),
  KEY `members_vaccinecentres_CentreID_fk` (`Dose1CentreID`),
  CONSTRAINT `members_login_users_PhoneNumber_fk` FOREIGN KEY (`PhoneNumber`) REFERENCES `login_users` (`PhoneNumber`) ON DELETE CASCADE,
  CONSTRAINT `members_vaccinecentres_CentreID_fk` FOREIGN KEY (`Dose1CentreID`) REFERENCES `vaccinecentres` (`CentreID`) ON DELETE CASCADE,
  CONSTRAINT `members_vaccinecentres_CentreID_fk_2` FOREIGN KEY (`Dose2CentreID`) REFERENCES `vaccinecentres` (`CentreID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1596071 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (1596070,'7042921117','Mehak A','111177770000','2010-06-16',11,1,'Vaccinated','Not vaccinated',43,NULL,'Jun10',NULL,1,NULL,'COVAXIN',NULL),(1596069,'','Ashish Kharbanda','123412340987','2001-01-01',20,0,'Vaccinated','Not vaccinated',210,210,'Jun10','Jun10',1,1,'COVAXIN','COVAXIN');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `AgeEntry_insert` BEFORE INSERT ON `members` FOR EACH ROW BEGIN
        set NEW.age = YEAR(CURDATE()) - YEAR(NEW.DOB);
	END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateMemberCount_insert` BEFORE INSERT ON `members` FOR EACH ROW BEGIN
		DECLARE tempPhoneNumber varchar(10);
		update login_users SET numberOfDependents = numberOfDependents + 1 where phonenumber = NEW.phonenumber;
	END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `AgeEntry_update` BEFORE UPDATE ON `members` FOR EACH ROW BEGIN
        set NEW.age = YEAR(CURDATE()) - YEAR(NEW.DOB);
	END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `updateMemberCount_delete` BEFORE DELETE ON `members` FOR EACH ROW BEGIN
		DECLARE tempPhoneNumber varchar(10);
		update login_users SET numberOfDependents = numberOfDependents - 1 where phonenumber = old.phonenumber;
	END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-09 19:31:23
