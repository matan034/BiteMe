-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: biteme
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `AccountNum` int NOT NULL AUTO_INCREMENT,
  `Balance` int NOT NULL DEFAULT '0',
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `ID` varchar(45) NOT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `W4C` int DEFAULT NULL,
  PRIMARY KEY (`AccountNum`,`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (22,0,'Danny','Aibi','318239639','16535156','1@gmail.com',1234),(23,0,'Danny','Aibi','1','1','21@gmail.com',NULL),(24,0,'Danny','Aibi','3','3','3@gmail.com',NULL),(25,0,'Danny','Aibi','5','5','5@gmail.com',NULL),(26,0,'Danny','Aibi','6','6','6@gmail.com',NULL),(27,0,'Danny','Aibi','4','0546535156','danny@gmail.com',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `BranchID` int NOT NULL AUTO_INCREMENT,
  `BranchName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`BranchID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,'North Branch'),(2,'Center Branch'),(3,'South Branch');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `businessaccount`
--

DROP TABLE IF EXISTS `businessaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `businessaccount` (
  `AccountNum` int NOT NULL,
  `EmployerNum` int NOT NULL,
  `MonthlyLimit` int DEFAULT '0',
  `IsApproved` int DEFAULT '0',
  PRIMARY KEY (`AccountNum`,`EmployerNum`),
  KEY `EmployerNum_idx` (`EmployerNum`),
  CONSTRAINT `AccountNum33` FOREIGN KEY (`AccountNum`) REFERENCES `account` (`AccountNum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `EmployerNum3` FOREIGN KEY (`EmployerNum`) REFERENCES `employer` (`EmployerNum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `businessaccount`
--

LOCK TABLES `businessaccount` WRITE;
/*!40000 ALTER TABLE `businessaccount` DISABLE KEYS */;
INSERT INTO `businessaccount` VALUES (27,1,6,0);
/*!40000 ALTER TABLE `businessaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `CustomerNumber` int NOT NULL AUTO_INCREMENT,
  `ID` varchar(9) NOT NULL,
  `AccountNum` int NOT NULL,
  `Status` enum('Active','Frozen') NOT NULL,
  PRIMARY KEY (`CustomerNumber`,`ID`,`AccountNum`),
  KEY `ID` (`ID`),
  KEY `AccountNum` (`AccountNum`),
  CONSTRAINT `AccountNum` FOREIGN KEY (`AccountNum`) REFERENCES `account` (`AccountNum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ID` FOREIGN KEY (`ID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'318239639',22,'Active');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data`
--

DROP TABLE IF EXISTS `data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data` (
  `BranchID` int NOT NULL,
  `OrdersSummaryID` int NOT NULL,
  `Income` decimal(2,0) DEFAULT NULL,
  `CounterIsLate` int DEFAULT '0',
  PRIMARY KEY (`BranchID`,`OrdersSummaryID`),
  KEY `OrdersSummaryID_idx` (`OrdersSummaryID`),
  CONSTRAINT `OrdersSummaryID` FOREIGN KEY (`OrdersSummaryID`) REFERENCES `orderbytype` (`BranchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data`
--

LOCK TABLES `data` WRITE;
/*!40000 ALTER TABLE `data` DISABLE KEYS */;
/*!40000 ALTER TABLE `data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_details`
--

DROP TABLE IF EXISTS `delivery_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_details` (
  `OrderID` int NOT NULL,
  `DeliveryType` enum('Private','Shared','Robot') DEFAULT NULL,
  `RecieverName` varchar(45) DEFAULT NULL,
  `BusinessName` varchar(45) DEFAULT NULL,
  `RecieverPhone` varchar(45) DEFAULT NULL,
  `DeliveryStreet` varchar(45) DEFAULT NULL,
  `DeliveryCity` varchar(45) DEFAULT NULL,
  `DeliveryZip` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  CONSTRAINT `OrderID1` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_details`
--

LOCK TABLES `delivery_details` WRITE;
/*!40000 ALTER TABLE `delivery_details` DISABLE KEYS */;
INSERT INTO `delivery_details` VALUES (18,'Private','m m','m','m','m','m','m');
/*!40000 ALTER TABLE `delivery_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `DishID` int NOT NULL AUTO_INCREMENT,
  `DishName` varchar(45) NOT NULL,
  `DishType` varchar(45) NOT NULL,
  `Price` decimal(2,0) NOT NULL,
  `ChooseSize` int DEFAULT '0',
  `ChooseCookingLevel` int DEFAULT '0',
  `ChooseExtras` int DEFAULT '0',
  `DishImage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DishID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES (1,'Hamburger','Main Dish',25,1,1,1,'/img/hamburger.jpg'),(2,'Pizza','Main Dish',20,1,0,1,'/img/pizaa.jpg'),(3,'Fries','Appetizer',15,1,0,0,'/img/fries.jpg'),(4,'Cola','Drink',5,0,0,0,'/img/cola.jpg'),(5,'Carlsberg','Drink',10,1,0,0,'/img/Carlsberg.jpg');
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishinmenu`
--

DROP TABLE IF EXISTS `dishinmenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishinmenu` (
  `DishID` int NOT NULL,
  `MenuID` int NOT NULL,
  PRIMARY KEY (`DishID`,`MenuID`),
  KEY `MenuID_idx` (`MenuID`),
  CONSTRAINT `DishID` FOREIGN KEY (`DishID`) REFERENCES `dishes` (`DishID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MenuID` FOREIGN KEY (`MenuID`) REFERENCES `menu` (`MenuID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishinmenu`
--

LOCK TABLES `dishinmenu` WRITE;
/*!40000 ALTER TABLE `dishinmenu` DISABLE KEYS */;
INSERT INTO `dishinmenu` VALUES (1,7),(2,7),(3,7),(4,7),(5,7);
/*!40000 ALTER TABLE `dishinmenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishinorder`
--

DROP TABLE IF EXISTS `dishinorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishinorder` (
  `DishID` int NOT NULL,
  `OrderNumber` int NOT NULL,
  `Size` varchar(45) DEFAULT NULL,
  `CookingLevel` varchar(45) DEFAULT NULL,
  `Extras` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DishID`,`OrderNumber`),
  KEY `OrderID_idx` (`OrderNumber`),
  CONSTRAINT `DishID1` FOREIGN KEY (`DishID`) REFERENCES `dishes` (`DishID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OrderID` FOREIGN KEY (`OrderNumber`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishinorder`
--

LOCK TABLES `dishinorder` WRITE;
/*!40000 ALTER TABLE `dishinorder` DISABLE KEYS */;
INSERT INTO `dishinorder` VALUES (1,14,'Small','Rare',' '),(1,17,'Small','Rare',' '),(3,16,'Small',' ',' '),(3,17,'Small',' ',' '),(4,15,' ',' ',' '),(5,18,'Small',' ',' ');
/*!40000 ALTER TABLE `dishinorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employer` (
  `EmployerNum` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `IsApproved` int DEFAULT '0',
  `Address` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EmployerNum`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employer`
--

LOCK TABLES `employer` WRITE;
/*!40000 ALTER TABLE `employer` DISABLE KEYS */;
INSERT INTO `employer` VALUES (1,'Refael',1,'Leshem','054653551'),(2,'Intel',0,'123','123');
/*!40000 ALTER TABLE `employer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `MenuID` int NOT NULL AUTO_INCREMENT,
  `RestaurantNum` int NOT NULL,
  `MenuType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`MenuID`,`RestaurantNum`),
  KEY `Number_idx` (`RestaurantNum`),
  CONSTRAINT `Number` FOREIGN KEY (`RestaurantNum`) REFERENCES `restaurant` (`Number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (7,1,'All');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `ResturantNumber` int NOT NULL,
  `CustomerNumber` int NOT NULL,
  `SupplyWay` enum('Take-away','Delivery','Order-in') NOT NULL,
  `IsEarlyOrder` int NOT NULL DEFAULT '0',
  `RequestOrderTime` varchar(45) NOT NULL,
  `RecivedOrderTime` varchar(45) DEFAULT NULL,
  `CustomerReciveTime` varchar(45) DEFAULT NULL,
  `IsApproved` int DEFAULT '0',
  `IsArrived` int DEFAULT '0',
  PRIMARY KEY (`OrderID`,`ResturantNumber`,`CustomerNumber`),
  KEY `CustomerNumber_idx` (`CustomerNumber`),
  KEY `Number_idx` (`ResturantNumber`),
  CONSTRAINT `CustomerNumber` FOREIGN KEY (`CustomerNumber`) REFERENCES `customers` (`CustomerNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Number1` FOREIGN KEY (`ResturantNumber`) REFERENCES `restaurant` (`Number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (14,1,1,'Take-away',1,'12:12 2021-12-20',NULL,NULL,0,0),(15,1,1,'Delivery',1,'14:12 2021-12-21',NULL,NULL,0,0),(16,1,1,'Take-away',1,'14:10 2021-12-20',NULL,NULL,0,0),(17,1,1,'Take-away',1,'15:21 2021-12-21',NULL,NULL,0,0),(18,2,1,'Delivery',1,'12:12 2021-12-21',NULL,NULL,0,0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderbytype`
--

DROP TABLE IF EXISTS `orderbytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderbytype` (
  `BranchID` int NOT NULL,
  `StartersDishes` int DEFAULT NULL,
  `mainDishes` int DEFAULT NULL,
  `SaladDishes` int DEFAULT NULL,
  `Dessert` int DEFAULT NULL,
  `Drinkes` int DEFAULT NULL,
  PRIMARY KEY (`BranchID`),
  CONSTRAINT `BranchID2` FOREIGN KEY (`BranchID`) REFERENCES `branch` (`BranchID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderbytype`
--

LOCK TABLES `orderbytype` WRITE;
/*!40000 ALTER TABLE `orderbytype` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderbytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privateaccount`
--

DROP TABLE IF EXISTS `privateaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privateaccount` (
  `AccountNum` int NOT NULL,
  `CreditCardNumber` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`AccountNum`),
  CONSTRAINT `AccountNum1` FOREIGN KEY (`AccountNum`) REFERENCES `account` (`AccountNum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privateaccount`
--

LOCK TABLES `privateaccount` WRITE;
/*!40000 ALTER TABLE `privateaccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `privateaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `ReportID` int NOT NULL,
  `ReportName` varchar(45) NOT NULL,
  `ReportType` varchar(45) NOT NULL,
  `ReportDate` varchar(45) NOT NULL,
  PRIMARY KEY (`ReportID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `Number` int NOT NULL AUTO_INCREMENT,
  `BranchNum` int DEFAULT NULL,
  `IsApproved` int DEFAULT '0',
  `Name` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Manager` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Number`),
  KEY `BranchID_idx` (`BranchNum`),
  KEY `ID_idx` (`Manager`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,1,1,'BBB - Kiryon','Derech Akko 4','Kiryat Bialik','American','123456789'),(2,2,1,'McDicks','Old mall','Akko','Yummy','123456789');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `UserID` varchar(9) NOT NULL,
  `SupplierNumber` int NOT NULL AUTO_INCREMENT,
  `Position` varchar(45) DEFAULT NULL,
  `IsApproved` int DEFAULT '0',
  `Permission` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SupplierNumber`,`UserID`),
  KEY `ID_idx` (`UserID`),
  CONSTRAINT `UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `ID` varchar(9) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Phone` varchar(45) NOT NULL,
  `Type` varchar(45) NOT NULL,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `IsLoggedIn` int NOT NULL DEFAULT '0',
  `Status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('123456789','Kobi','Cohen','kobi@gmail.com','021212122','Restaurant Manager','kobi','123',0,'Active'),('312165913','Matan','Weisberg','matan@gmail.com','05121122','Customer','matan','123',1,'Active'),('318239639','danny','aibi','dan@gamil.com','123123','CEO','danny','123',1,'Frozen');
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

-- Dump completed on 2021-12-14 20:40:02
