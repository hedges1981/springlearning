CREATE DATABASE  IF NOT EXISTS `jpalearning` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jpalearning`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
DROP TABLE IF EXISTS `CascadeDemo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CascadeDemo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CascadePersist_1_idx` (`emp_id`),
  CONSTRAINT `fk_CascadePersist_1` FOREIGN KEY (`emp_id`) REFERENCES `emp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `CascadeDemo` WRITE;
/*!40000 ALTER TABLE `CascadeDemo` DISABLE KEYS */;
INSERT INTO `CascadeDemo` (`id`, `name`, `emp_id`) VALUES (1,'cp1',91),(2,'cp2',91);
/*!40000 ALTER TABLE `CascadeDemo` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`, `name`) VALUES (1,'sales'),(2,'cleaning'),(3,'payroll'),(4,'marketing'),(5,'shithouse');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `design_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `design_project` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `design_notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_design_project_1_idx` (`project_id`),
  CONSTRAINT `fk_design_project_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `design_project` WRITE;
/*!40000 ALTER TABLE `design_project` DISABLE KEYS */;
INSERT INTO `design_project` (`id`, `project_id`, `design_notes`) VALUES (0,1,'some design notes');
/*!40000 ALTER TABLE `design_project` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(45) NOT NULL,
  `dogName` varchar(45) DEFAULT NULL,
  `comments` varchar(45) DEFAULT NULL,
  `picture` blob,
  `employeeType` varchar(45) DEFAULT NULL,
  `employeeSuspendedStatus` int(11) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `startTime` time DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `parking_space_id` int(11) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `county` varchar(45) DEFAULT NULL,
  `postcode` varchar(45) DEFAULT NULL,
  `desk_id` int(11) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `empcol` varchar(45) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parking_space_id_UNIQUE` (`parking_space_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `emp` WRITE;
/*!40000 ALTER TABLE `emp` DISABLE KEYS */;
INSERT INTO `emp` (`id`, `phone`, `dogName`, `comments`, `picture`, `employeeType`, `employeeSuspendedStatus`, `startDate`, `startTime`, `dept_id`, `parking_space_id`, `street`, `city`, `county`, `postcode`, `desk_id`, `firstName`, `lastName`, `empcol`, `salary`) VALUES (1,'01978761725','dbfc5715-3e45-4480-bec9-0b92c7109836','Is a clown',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,1,'john1','This will get saved, as e1merged is now the managed object',NULL,1),(2,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,'john2','this WILL get saved as employee is  not detached',NULL,2),(3,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'john3','smith3',NULL,3),(4,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,'john4','smith4',NULL,4),(5,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,5,'john5','smith5',NULL,5),(6,'01978761725','402f6787-f65b-4ac8-807a-7bab105b7e84','Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,6,'john6','smith6',NULL,6),(7,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,7,'john7','smith7',NULL,7),(8,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,8,'john8','smith8',NULL,8),(9,'01978761725',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,9,'john9','smith9',NULL,9),(10,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,10,'john10','smith10',NULL,10),(11,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,11,'john11','smith11',NULL,11),(12,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13',NULL,1,NULL,NULL,NULL,NULL,NULL,12,'john12','smith12',NULL,12),(13,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:27:51',4,NULL,NULL,NULL,NULL,NULL,13,'john13','smith13',NULL,13),(14,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:35:39',NULL,NULL,NULL,NULL,NULL,NULL,14,'john14','smith14',NULL,14),(15,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:41:34',NULL,NULL,NULL,NULL,NULL,NULL,15,'john15','smith15',NULL,15),(16,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:45:40',NULL,NULL,NULL,NULL,NULL,NULL,16,'john16','smith16',NULL,16),(17,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:46:34',NULL,NULL,NULL,NULL,NULL,NULL,17,'john17','smith17',NULL,17),(18,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:50:45',NULL,NULL,NULL,NULL,NULL,NULL,18,'john18','smith18',NULL,18),(19,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','10:55:03',NULL,NULL,NULL,NULL,NULL,NULL,19,'john19','smith19',NULL,19),(20,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','11:08:46',NULL,NULL,NULL,NULL,NULL,NULL,20,'john20','smith20',NULL,20),(21,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','11:35:11',NULL,NULL,NULL,NULL,NULL,NULL,21,'john21','smith21',NULL,21),(23,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-13','14:37:24',1,NULL,NULL,NULL,NULL,NULL,23,'john23','smith23',NULL,23),(24,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','16:45:21',2,1,NULL,NULL,NULL,NULL,24,'john24','smith24',NULL,24),(25,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','16:58:17',3,2,NULL,NULL,NULL,NULL,25,'john25','smith25',NULL,25),(26,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:02:37',4,3,NULL,NULL,NULL,NULL,26,'john26','smith26',NULL,26),(27,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:05:39',1,4,NULL,NULL,NULL,NULL,27,'john27','smith27',NULL,27),(28,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:08:45',1,5,NULL,NULL,NULL,NULL,28,'john28','smith28',NULL,28),(29,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:10:56',1,6,NULL,NULL,NULL,NULL,29,'john29','smith29',NULL,29),(30,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:11:28',1,7,NULL,NULL,NULL,NULL,30,'john30','smith30',NULL,30),(31,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-14','17:13:21',1,8,NULL,NULL,NULL,NULL,31,'john31','smith31',NULL,31),(32,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-15','16:37:13',1,9,NULL,NULL,NULL,NULL,32,'john32','smith32',NULL,32),(33,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-15','16:38:14',1,10,NULL,NULL,NULL,NULL,33,'john33','smith33',NULL,33),(34,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-15','16:41:10',1,11,NULL,NULL,NULL,NULL,34,'john34','smith34',NULL,34),(35,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-15','16:41:59',1,12,NULL,NULL,NULL,NULL,35,'john35','smith35',NULL,35),(36,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:29:36',1,13,NULL,NULL,NULL,NULL,36,'john36','smith36',NULL,36),(37,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:33:14',1,14,NULL,NULL,NULL,NULL,37,'john37','smith37',NULL,37),(38,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:37:19',1,15,NULL,NULL,NULL,NULL,38,'john38','smith38',NULL,38),(39,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:38:54',1,16,NULL,NULL,NULL,NULL,39,'john39','smith39',NULL,39),(40,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:40:16',1,17,NULL,NULL,NULL,NULL,40,'john40','smith40',NULL,40),(41,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:41:02',1,18,NULL,NULL,NULL,NULL,41,'john41','smith41',NULL,41),(42,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','16:43:08',1,19,NULL,NULL,NULL,NULL,42,'john42','smith42',NULL,42),(43,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','17:12:58',1,20,'High Street','Chester','Cheshire','CH40JY',43,'john43','smith43',NULL,43),(45,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','17:38:43',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',45,'john45','smith45',NULL,45),(46,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','17:40:10',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',46,'john46','smith46',NULL,46),(47,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-16','17:40:48',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',47,'john47','smith47',NULL,47),(48,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:04:24',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',48,'john48','smith48',NULL,48),(49,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:05:29',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',49,'john49','smith49',NULL,49),(50,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:06:31',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',50,'john50','smith50',NULL,50),(51,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:07:19',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',51,'john51','smith51',NULL,51),(52,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:19:03',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',52,'john52','smith52',NULL,52),(53,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:21:16',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',53,'john53','smith53',NULL,53),(54,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:24:20',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',54,'john54','smith54',NULL,54),(55,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:26:16',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',55,'john55','smith55',NULL,55),(56,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','15:27:51',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',56,'john56','smith56',NULL,56),(57,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:39:48',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',57,'john57','smith57',NULL,57),(58,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:43:46',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',58,'john58','smith58',NULL,58),(59,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:45:53',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',59,'john59','smith59',NULL,59),(60,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:49:44',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',60,'john60','smith60',NULL,60),(61,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:52:33',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',61,'john61','smith61',NULL,61),(62,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:53:58',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',62,'john62','smith62',NULL,62),(63,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:57:14',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',63,'john63','smith63',NULL,63),(64,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','16:59:09',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',64,'john64','smith64',NULL,64),(65,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','17:04:05',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',65,'john65','smith65',NULL,65),(66,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','17:06:20',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',66,'john66','smith66',NULL,66),(67,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-19','17:07:20',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',67,'john67','smith67',NULL,67),(68,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-20','09:19:06',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',68,'john68','smith68',NULL,68),(69,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-20','09:21:32',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',69,'john69','smith69',NULL,69),(70,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-20','09:22:44',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',70,'john70','smith70',NULL,70),(71,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-20','09:35:19',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',71,'john71','smith71',NULL,71),(72,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-20','09:37:01',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',72,'john72','smith72',NULL,72),(73,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-22','16:58:05',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',73,'john73','smith73',NULL,73),(74,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-22','16:58:57',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',74,'john74','smith74',NULL,74),(75,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-22','17:01:30',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',75,'john75','smith75',NULL,75),(76,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-22','17:19:36',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',NULL,'john76','smith76',NULL,76),(77,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-22','17:24:33',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',NULL,'john77','smith77',NULL,77),(78,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-27','14:48:32',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',NULL,NULL,NULL,NULL,78),(79,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-10-27','14:50:19',NULL,NULL,'High Street','Chester','Cheshire','CH40JY',NULL,NULL,NULL,NULL,79),(80,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Neville','Norriss',NULL,80),(81,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Neville','Norriss',NULL,81),(82,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Neville','Norriss',NULL,82),(83,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'mr','Smith',NULL,83),(84,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Neville','Norriss',NULL,84),(86,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Neville','Norriss',NULL,86),(91,'01978null',NULL,'Is a clown',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Mavis','Dog',NULL,91),(92,'01978761725',NULL,'Is a clown','thisIsAMadeUpPicture','CONTRACT_EMPLOYEE',0,'2015-11-09','11:44:55',1,21,'High Street','Chester','Cheshire','CH40JY',NULL,NULL,NULL,NULL,92);
/*!40000 ALTER TABLE `emp` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `emp_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp_phone` (
  `emp_id` int(11) NOT NULL,
  `phone_id` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`,`phone_id`),
  KEY `fk_emp_phone_1_idx` (`emp_id`),
  KEY `fk_emp_phone_2_idx` (`phone_id`),
  CONSTRAINT `fk_emp_phone_1` FOREIGN KEY (`emp_id`) REFERENCES `emp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emp_phone_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `emp_phone` WRITE;
/*!40000 ALTER TABLE `emp_phone` DISABLE KEYS */;
INSERT INTO `emp_phone` (`emp_id`, `phone_id`) VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `emp_phone` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `emp_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp_project` (
  `emp_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`,`project_id`),
  KEY `fk_emp_project_1_idx` (`emp_id`),
  KEY `fk_emp_project_2_idx` (`project_id`),
  CONSTRAINT `fk_emp_project_1` FOREIGN KEY (`emp_id`) REFERENCES `emp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emp_project_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `emp_project` WRITE;
/*!40000 ALTER TABLE `emp_project` DISABLE KEYS */;
INSERT INTO `emp_project` (`emp_id`, `project_id`) VALUES (1,1),(1,2),(2,1),(3,1),(4,1);
/*!40000 ALTER TABLE `emp_project` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `emp_relative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp_relative` (
  `emp_id` int(11) NOT NULL,
  `relative_type` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`emp_id`,`relative_type`),
  KEY `fk_emp_relative_1_idx` (`emp_id`),
  CONSTRAINT `fk_emp_relative_1` FOREIGN KEY (`emp_id`) REFERENCES `emp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `emp_relative` WRITE;
/*!40000 ALTER TABLE `emp_relative` DISABLE KEYS */;
INSERT INTO `emp_relative` (`emp_id`, `relative_type`, `name`) VALUES (1,'225a8f00-c390-4f32-b4d0-7ab591','aName'),(1,'6146b718-be58-4db0-8c06-e109b3','aName'),(1,'d8404e7a-a318-49f0-9a23-1ca955','aName'),(1,'dog','mavis'),(1,'e2f42470-746b-4842-a34b-1fcb22','aName'),(1,'eb6f3491-6122-48d5-b816-345e85','aName'),(1,'mother','mother name updated'),(1,'sister','kkkkkkkk');
/*!40000 ALTER TABLE `emp_relative` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holiday` (
  `emp_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `days` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`,`start_date`,`days`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `holiday` WRITE;
/*!40000 ALTER TABLE `holiday` DISABLE KEYS */;
INSERT INTO `holiday` (`emp_id`, `start_date`, `days`) VALUES (1,'2015-10-16',3),(1,'2015-10-23',2),(45,'2015-10-16',10),(45,'2015-10-16',11),(46,'2015-10-16',10),(46,'2015-10-16',11),(47,'2015-10-16',10),(47,'2015-10-16',11),(48,'2015-10-19',10),(48,'2015-10-19',11),(49,'2015-10-19',10),(49,'2015-10-19',11),(50,'2015-10-19',10),(50,'2015-10-19',11),(51,'2015-10-19',10),(51,'2015-10-19',11),(52,'2015-10-19',10),(52,'2015-10-19',11),(53,'2015-10-19',10),(53,'2015-10-19',11),(54,'2015-10-19',10),(54,'2015-10-19',11),(55,'2015-10-19',10),(55,'2015-10-19',11),(56,'2015-10-19',10),(56,'2015-10-19',11),(57,'2015-10-19',10),(57,'2015-10-19',11),(58,'2015-10-19',10),(58,'2015-10-19',11),(59,'2015-10-19',10),(59,'2015-10-19',11),(60,'2015-10-19',10),(60,'2015-10-19',11),(61,'2015-10-19',10),(61,'2015-10-19',11),(62,'2015-10-19',10),(62,'2015-10-19',11),(63,'2015-10-19',10),(63,'2015-10-19',11),(64,'2015-10-19',10),(64,'2015-10-19',11),(65,'2015-10-19',10),(65,'2015-10-19',11),(66,'2015-10-19',10),(66,'2015-10-19',11),(67,'2015-10-19',10),(67,'2015-10-19',11),(68,'2015-10-20',10),(68,'2015-10-20',11),(69,'2015-10-20',10),(69,'2015-10-20',11),(70,'2015-10-20',10),(70,'2015-10-20',11),(71,'2015-10-20',10),(71,'2015-10-20',11),(72,'2015-10-20',10),(72,'2015-10-20',11),(73,'2015-10-22',10),(73,'2015-10-22',11),(74,'2015-10-22',10),(74,'2015-10-22',11),(75,'2015-10-22',10),(75,'2015-10-22',11),(76,'2015-10-22',10),(76,'2015-10-22',11),(77,'2015-10-22',10),(77,'2015-10-22',11),(78,'2015-10-27',10),(78,'2015-10-27',11),(79,'2015-10-27',10),(79,'2015-10-27',11);
/*!40000 ALTER TABLE `holiday` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `new_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `new_table` (
  `emp_id` int(11) NOT NULL,
  `phone_id` int(11) NOT NULL,
  PRIMARY KEY (`emp_id`,`phone_id`),
  KEY `fk_new_table_1_idx` (`emp_id`),
  KEY `fk_new_table_2_idx` (`phone_id`),
  CONSTRAINT `fk_new_table_1` FOREIGN KEY (`emp_id`) REFERENCES `emp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_new_table_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `new_table` WRITE;
/*!40000 ALTER TABLE `new_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `new_table` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `nicknames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nicknames` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  PRIMARY KEY (`emp_id`,`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `nicknames` WRITE;
/*!40000 ALTER TABLE `nicknames` DISABLE KEYS */;
INSERT INTO `nicknames` (`emp_id`, `nickname`) VALUES (1,'dsdsdsoooo'),(1,'qwqwqwoooo');
/*!40000 ALTER TABLE `nicknames` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `parking_space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking_space` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `location_UNIQUE` (`location`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `parking_space` WRITE;
/*!40000 ALTER TABLE `parking_space` DISABLE KEYS */;
INSERT INTO `parking_space` (`id`, `location`) VALUES (13,'0305847a-510f-4b18-bb2c-beac087830ca'),(11,'1c0c5d5b-4fbe-4633-a198-b17abe32c45e'),(19,'2979464b-f8c0-425a-baef-8f028678749b'),(6,'3c0f5ce8-9cb8-4704-b010-fec42900cea4'),(1,'413b32c9-6207-4f37-bcd2-98a44f6e2f07'),(17,'4d560556-5d7e-4b7b-a1bf-ca9799ba06e0'),(14,'51970752-9155-4f3d-a906-2e109ea905e2'),(3,'532558b4-5b07-46d8-bfc8-501c1f93f8f2'),(12,'59b83f24-3d23-4a36-bded-abed8b8ccba9'),(20,'5d491e8a-1526-4538-852a-ee4e644cabb0'),(18,'64ecea5e-c235-4fb0-8d12-1a53d0fca34b'),(9,'6c4ecf1e-fa4e-4ec7-b8e5-d0c9d09b190a'),(5,'76e17839-ee13-4ab3-a85c-8b688f7f8875'),(4,'7e4bb906-23a5-48fc-83a3-624ca9a2451e'),(16,'9c31ea45-456a-4374-8e23-33a46350e947'),(8,'c7082abd-ff4e-4bbf-b026-b1b557e07526'),(2,'c896bd07-9ea5-4bcc-8787-332fd61c11b8'),(21,'cc211e27-adc4-4392-917b-8db1ded5442f'),(7,'da3e26a6-1345-4b6b-af1d-50f976765618'),(15,'efae72df-5789-41a8-a4ea-f43b58d3ded9'),(10,'fa920901-9cc5-4d34-b1c1-60a15365ff7c');
/*!40000 ALTER TABLE `parking_space` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `number` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` (`id`, `type`, `number`) VALUES (1,'home','0767676767676'),(2,'mobile','5656565656565656'),(3,'work','5656565656565');
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `print_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `print_queue` varchar(45) NOT NULL,
  `print_order` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `print_job` WRITE;
/*!40000 ALTER TABLE `print_job` DISABLE KEYS */;
INSERT INTO `print_job` (`id`, `print_queue`, `print_order`) VALUES (1,'queue1',1),(2,'queue1',2),(3,'queue1',3),(4,'queue1',4),(5,'queue1',5),(6,'queue1',6),(7,'queue1',7),(8,'queue1',8),(9,'queue1',9);
/*!40000 ALTER TABLE `print_job` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `print_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_queue` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `print_queue` WRITE;
/*!40000 ALTER TABLE `print_queue` DISABLE KEYS */;
INSERT INTO `print_queue` (`name`) VALUES ('queue1');
/*!40000 ALTER TABLE `print_queue` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `name`) VALUES (1,'X'),(2,'Y');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `quality_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quality_project` (
  `id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `quality_project_notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_quality_project_1_idx` (`project_id`),
  CONSTRAINT `fk_quality_project_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `quality_project` WRITE;
/*!40000 ALTER TABLE `quality_project` DISABLE KEYS */;
INSERT INTO `quality_project` (`id`, `project_id`, `quality_project_notes`) VALUES (0,2,'quality project');
/*!40000 ALTER TABLE `quality_project` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_type` varchar(25) NOT NULL,
  `salary` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` (`id`, `job_type`, `salary`) VALUES (1,'cleaner',10),(2,'gimp',20),(3,'manager',30);
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
