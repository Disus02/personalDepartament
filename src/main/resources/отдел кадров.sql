-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: personel_department
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `correspondent_account`
--

DROP TABLE IF EXISTS `correspondent_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `correspondent_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `correspondent_account`
--

LOCK TABLES `correspondent_account` WRITE;
/*!40000 ALTER TABLE `correspondent_account` DISABLE KEYS */;
INSERT INTO `correspondent_account` VALUES (1,44,'код');
/*!40000 ALTER TABLE `correspondent_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division`
--

DROP TABLE IF EXISTS `division`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `division` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `Organization_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_division_Organization1_idx` (`Organization_id`),
  CONSTRAINT `fk_division_Organization1` FOREIGN KEY (`Organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division`
--

LOCK TABLES `division` WRITE;
/*!40000 ALTER TABLE `division` DISABLE KEYS */;
INSERT INTO `division` VALUES (1,'Транспортный цех',1),(2,'Информационный отдел',1);
/*!40000 ALTER TABLE `division` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_type`
--

DROP TABLE IF EXISTS `document_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `documentNumber` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_type`
--

LOCK TABLES `document_type` WRITE;
/*!40000 ALTER TABLE `document_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `document_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `id` int NOT NULL AUTO_INCREMENT,
  `documentNumber` int NOT NULL,
  `dateIssue` date NOT NULL,
  `pathDocument` varchar(250) NOT NULL,
  `worker_id` int NOT NULL,
  `document_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Документы_worker1_idx` (`worker_id`),
  KEY `fk_Документы_document_type1_idx` (`document_type_id`),
  CONSTRAINT `fk_Документы_document_type1` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`id`),
  CONSTRAINT `fk_Документы_worker1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education`
--

DROP TABLE IF EXISTS `education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `education` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nameInstitution` varchar(150) NOT NULL,
  `numberDiplom` int NOT NULL,
  `dateIssue` date NOT NULL,
  `assignedQualification` varchar(100) NOT NULL,
  `worker_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Образование_worker1_idx` (`worker_id`),
  CONSTRAINT `fk_Образование_worker1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education`
--

LOCK TABLES `education` WRITE;
/*!40000 ALTER TABLE `education` DISABLE KEYS */;
/*!40000 ALTER TABLE `education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holiday`
--

DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holiday` (
  `worker_id` int NOT NULL,
  `holiday_id` int NOT NULL,
  PRIMARY KEY (`worker_id`,`holiday_id`),
  KEY `fk_Сотрудник_has_Отпуска_Отпуска1_idx` (`holiday_id`),
  KEY `fk_Сотрудник_has_Отпуска_Сотрудник_idx` (`worker_id`),
  CONSTRAINT `fk_Сотрудник_has_Отпуска_Отпуска1` FOREIGN KEY (`holiday_id`) REFERENCES `timetable` (`id`),
  CONSTRAINT `fk_Сотрудник_has_Отпуска_Сотрудник1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holiday`
--

LOCK TABLES `holiday` WRITE;
/*!40000 ALTER TABLE `holiday` DISABLE KEYS */;
INSERT INTO `holiday` VALUES (1,1);
/*!40000 ALTER TABLE `holiday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numberDay` int NOT NULL,
  `quantityHours` int DEFAULT NULL,
  `status_id` int NOT NULL,
  `timesheet_worker_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_marks_status1_idx` (`status_id`),
  KEY `fk_marks_timesheet_worker1_idx` (`timesheet_worker_id`),
  CONSTRAINT `fk_marks_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `fk_marks_timesheet_worker1` FOREIGN KEY (`timesheet_worker_id`) REFERENCES `timesheet_worker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks`
--

LOCK TABLES `marks` WRITE;
/*!40000 ALTER TABLE `marks` DISABLE KEYS */;
INSERT INTO `marks` VALUES (1,1,8,1,1),(2,2,8,1,1),(3,3,8,1,1),(4,4,8,1,1);
/*!40000 ALTER TABLE `marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical`
--

DROP TABLE IF EXISTS `medical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numberReg` int NOT NULL,
  `startHospital` date NOT NULL,
  `endHospital` date NOT NULL,
  `worker_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Больничный_worker1_idx` (`worker_id`),
  CONSTRAINT `fk_Больничный_worker1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical`
--

LOCK TABLES `medical` WRITE;
/*!40000 ALTER TABLE `medical` DISABLE KEYS */;
/*!40000 ALTER TABLE `medical` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `INN` int NOT NULL,
  `KPP` int NOT NULL,
  `payment` bigint NOT NULL,
  `BIK` int NOT NULL,
  `phone` varchar(50) NOT NULL,
  `nameDirector` varchar(150) NOT NULL,
  `address` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'ООО\"ВЕКТОР\"',770440758,770401001,4080281002,45209777,'+7(3812)673614','Терехов В.В.','город Омск улица Окружная дом 19');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passport`
--

DROP TABLE IF EXISTS `passport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `series` int NOT NULL,
  `number` int NOT NULL,
  `dateIssue` date NOT NULL,
  `whomIssued` varchar(150) NOT NULL,
  `registrationPlace` varchar(200) NOT NULL,
  `devisionCode` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passport`
--

LOCK TABLES `passport` WRITE;
/*!40000 ALTER TABLE `passport` DISABLE KEYS */;
INSERT INTO `passport` VALUES (1,2214,243675,'2007-05-12','ГУ МВД России по Нижегородской области','Нижегородская область Павловский район село Лаптево улица Советская дом 6',342054),(2,2213,435567,'2001-05-12','ГУ МВД по нижегородской области','Нижегородская область, Павловский район, село Ярымово, д. 5',52011),(3,432576,2131,'2002-10-04','ГУ МВД по Нижегородской области','г. Павлово, улица Короленко, дом 17',5234);
/*!40000 ALTER TABLE `passport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_code`
--

DROP TABLE IF EXISTS `payment_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_code` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_code`
--

LOCK TABLES `payment_code` WRITE;
/*!40000 ALTER TABLE `payment_code` DISABLE KEYS */;
INSERT INTO `payment_code` VALUES (1,2000,'основной код, с его помощью отражается заработная плата, то есть все суммы, начисленные за исполнение работником его трудовых обязанностей, за исключением премий;'),(2,2002,'суммы премий за исполнение трудовых обязанностей, которые выплачиваются не за счет прибыли организации, то есть участвуют в снижении налоговой базы на прибыль;'),(3,2003,'суммы премий, выплачиваемые за счет прибыли организации, либо целевых поступлений денежных средств;'),(4,2012,'оплата ежегодных оплачиваемых отпусков;'),(5,2300,'оплата листов по временной нетрудоспособности;'),(6,2530,'код, отражающий суммы, выплаченные работнику в натуральной форме, то есть не деньгами; данный код можно внести, только если есть возможность указать точное количество дней (часов), за которые выплачивается зарплата.');
/*!40000 ALTER TABLE `payment_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_type`
--

DROP TABLE IF EXISTS `position_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `salary` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_type`
--

LOCK TABLES `position_type` WRITE;
/*!40000 ALTER TABLE `position_type` DISABLE KEYS */;
INSERT INTO `position_type` VALUES (1,'инженер','50000 руб'),(2,'системный администратор','40000 руб'),(3,'технолог-программист','30000 руб');
/*!40000 ALTER TABLE `position_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `worker_id` int NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`worker_id`,`post_id`),
  KEY `fk_Сотрудник_has_Должности_Должнос_idx` (`post_id`),
  KEY `fk_Сотрудник_has_Должности_Сотрудн_idx` (`worker_id`),
  CONSTRAINT `fk_Сотрудник_has_Должности_Должност1` FOREIGN KEY (`post_id`) REFERENCES `position_type` (`id`),
  CONSTRAINT `fk_Сотрудник_has_Должности_Сотрудни1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Я'),(2,'К'),(3,'ОТ'),(4,'В'),(5,'Б'),(6,'ДО'),(7,'Р'),(8,'ОЖ'),(9,'НН');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timesheet`
--

DROP TABLE IF EXISTS `timesheet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timesheet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numberDoc` int NOT NULL,
  `dateCompilation` date NOT NULL,
  `dateStart` date NOT NULL,
  `dateEnd` date NOT NULL,
  `division_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_timesheet_division1_idx` (`division_id`),
  CONSTRAINT `fk_timesheet_division1` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timesheet`
--

LOCK TABLES `timesheet` WRITE;
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` VALUES (1,9,'2021-05-04','2021-04-01','2021-04-30',1);
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timesheet_worker`
--

DROP TABLE IF EXISTS `timesheet_worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timesheet_worker` (
  `id` int NOT NULL AUTO_INCREMENT,
  `worker_id` int NOT NULL,
  `timesheet_id` int NOT NULL,
  `payment_code_id` int NOT NULL,
  `correspondent_account_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Т сотрудника_worker1_idx` (`worker_id`),
  KEY `fk_timesheet_worker_timesheet1_idx` (`timesheet_id`),
  KEY `fk_timesheet_worker_payment_code1_idx` (`payment_code_id`),
  KEY `fk_timesheet_worker_correspondent_account1_idx` (`correspondent_account_id`),
  CONSTRAINT `fk_timesheet_worker_correspondent_account1` FOREIGN KEY (`correspondent_account_id`) REFERENCES `correspondent_account` (`id`),
  CONSTRAINT `fk_timesheet_worker_payment_code1` FOREIGN KEY (`payment_code_id`) REFERENCES `payment_code` (`id`),
  CONSTRAINT `fk_timesheet_worker_timesheet1` FOREIGN KEY (`timesheet_id`) REFERENCES `timesheet` (`id`),
  CONSTRAINT `fk_Т сотрудника_worker1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timesheet_worker`
--

LOCK TABLES `timesheet_worker` WRITE;
/*!40000 ALTER TABLE `timesheet_worker` DISABLE KEYS */;
INSERT INTO `timesheet_worker` VALUES (1,1,1,1,1),(2,3,1,1,1);
/*!40000 ALTER TABLE `timesheet_worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timetable` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateStart` date NOT NULL,
  `dateEnd` date NOT NULL,
  `numberOrder` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,'2021-05-12','2021-05-20',1),(2,'2020-03-02','2020-04-10',2);
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_role1_idx` (`role_id`),
  CONSTRAINT `fk_users_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_book`
--

DROP TABLE IF EXISTS `work_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `series` int NOT NULL,
  `number` int NOT NULL,
  `dateIssue` date NOT NULL,
  `whomIssued` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_book`
--

LOCK TABLES `work_book` WRITE;
/*!40000 ALTER TABLE `work_book` DISABLE KEYS */;
INSERT INTO `work_book` VALUES (1,1231,4625251,'2018-02-24','ООО\"ВЕКТОР\"'),(2,2131,213432,'2017-02-09','ОАО \"СЧВ\"'),(3,299876,3121,'2010-05-22','ОАО \"ПАЗ\"');
/*!40000 ALTER TABLE `work_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `patronymic` varchar(50) NOT NULL,
  `address` varchar(150) NOT NULL,
  `birthday` date NOT NULL,
  `INN` int NOT NULL,
  `dateEmployment` date NOT NULL,
  `photoPath` varchar(200) NOT NULL,
  `SNILS` int NOT NULL,
  `numberService` int NOT NULL,
  `work_book_id` int NOT NULL,
  `passport_id` int NOT NULL,
  `division_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_worker_passport1_idx` (`passport_id`),
  KEY `fk_worker_division1_idx` (`division_id`),
  KEY `fk_worker_work_book1_idx` (`work_book_id`),
  CONSTRAINT `fk_worker_division1` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`),
  CONSTRAINT `fk_worker_passport1` FOREIGN KEY (`passport_id`) REFERENCES `passport` (`id`),
  CONSTRAINT `fk_worker_work_book1` FOREIGN KEY (`work_book_id`) REFERENCES `work_book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES (1,'Алексей','Лапушкин','Александрович','город Павлово улица Правика дом 13','1993-02-21',770708389,'2018-06-12','image/1.jpg',761234560,1,1,1,1),(2,'Илья','Серебряков','Викторович','г. Павлово, улица Мичурина, д 6','1991-05-17',534675,'2017-05-25','image/2.jpg',3452732,2,2,2,2),(3,'Виталий','Горохов','Григорьевич','г. Павлово, улица Короленко, дом 17','1986-09-11',875432,'2019-05-10','image/3.jpg',3547658,3,3,3,2);
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-25  1:10:57
