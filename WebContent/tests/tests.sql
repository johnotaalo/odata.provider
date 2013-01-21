-- MySQL dump 10.13  Distrib 5.1.56, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: odata_api
-- ------------------------------------------------------
-- Server version	5.1.56

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

--
-- Table structure for table `informea_contacts`
--

DROP TABLE IF EXISTS `informea_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_contacts` (
  `id` varchar(20) NOT NULL,
  `country` varchar(20) DEFAULT NULL,
  `prefix` varchar(20) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `position` varchar(20) DEFAULT NULL,
  `institution` varchar(20) DEFAULT NULL,
  `department` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `primary` tinyint(1) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_contacts`
--

LOCK TABLES `informea_contacts` WRITE;
/*!40000 ALTER TABLE `informea_contacts` DISABLE KEYS */;
INSERT INTO `informea_contacts` VALUES ('1','ro','prefix','firstName','lastName','position','institution','department','address','email','phone','fax',1,'2011-03-03 03:03:03'),('2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_contacts_treaties`
--

DROP TABLE IF EXISTS `informea_contacts_treaties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_contacts_treaties` (
  `id` varchar(20) DEFAULT NULL,
  `contact_id` varchar(20) DEFAULT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  UNIQUE KEY `id` (`id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `informea_contacts_treaties_fk` FOREIGN KEY (`contact_id`) REFERENCES `informea_contacts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_contacts_treaties`
--

LOCK TABLES `informea_contacts_treaties` WRITE;
/*!40000 ALTER TABLE `informea_contacts_treaties` DISABLE KEYS */;
INSERT INTO `informea_contacts_treaties` VALUES ('1','1','cbd'),('2','1','basel');
/*!40000 ALTER TABLE `informea_contacts_treaties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_country_profiles`
--

DROP TABLE IF EXISTS `informea_country_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_country_profiles` (
  `id` varchar(20) NOT NULL,
  `country` varchar(20) DEFAULT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `entryIntoForce` tinyint(2) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_country_profiles`
--

LOCK TABLES `informea_country_profiles` WRITE;
/*!40000 ALTER TABLE `informea_country_profiles` DISABLE KEYS */;
INSERT INTO `informea_country_profiles` VALUES ('1','es','cbd',127,'2011-04-04 04:04:04'),('2',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_country_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_country_reports`
--

DROP TABLE IF EXISTS `informea_country_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_country_reports` (
  `id` varchar(20) NOT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `submission` datetime DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_country_reports`
--

LOCK TABLES `informea_country_reports` WRITE;
/*!40000 ALTER TABLE `informea_country_reports` DISABLE KEYS */;
INSERT INTO `informea_country_reports` VALUES ('1','cbd','es','2011-05-05 05:05:05','http://url.com','2011-06-06 06:06:06'),('2','invalid',NULL,NULL,NULL,NULL),('3',NULL,NULL,NULL,NULL,NULL),('4',NULL,NULL,NULL,NULL,NULL),('5',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_country_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_country_reports_title`
--

DROP TABLE IF EXISTS `informea_country_reports_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_country_reports_title` (
  `id` varchar(20) NOT NULL,
  `country_report_id` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_report_id` (`country_report_id`),
  CONSTRAINT `informea_country_reports_title_fk` FOREIGN KEY (`country_report_id`) REFERENCES `informea_country_reports` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_country_reports_title`
--

LOCK TABLES `informea_country_reports_title` WRITE;
/*!40000 ALTER TABLE `informea_country_reports_title` DISABLE KEYS */;
INSERT INTO `informea_country_reports_title` VALUES ('1','1','en','English'),('2','1','es','Spanish'),('3','2',NULL,'Chinese'),('4','4','cn',NULL),('5',NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_country_reports_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions`
--

DROP TABLE IF EXISTS `informea_decisions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions` (
  `id` varchar(20) NOT NULL,
  `link` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `number` varchar(20) DEFAULT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `meetingId` varchar(20) DEFAULT NULL,
  `meetingTitle` varchar(20) DEFAULT NULL,
  `meetingUrl` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions`
--

LOCK TABLES `informea_decisions` WRITE;
/*!40000 ALTER TABLE `informea_decisions` DISABLE KEYS */;
INSERT INTO `informea_decisions` VALUES ('1','http://decision.link','decision','active','NUMBER1','basel','2011-02-02 01:01:01','2011-03-03 03:03:03','meetingId','meetingTitle','http://meetingUrl'),('2','www.decision.link','dec','act',NULL,'notreaty',NULL,'2011-03-03 03:03:03','meetingId','meetingTitle','meetingUrl'),('3','www.decision.link','dec','act',NULL,'notreaty','2011-02-02 01:01:01','2011-03-03 03:03:03',NULL,NULL,'meetingUrl');
/*!40000 ALTER TABLE `informea_decisions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_content`
--

DROP TABLE IF EXISTS `informea_decisions_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `content` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_content_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_content`
--

LOCK TABLES `informea_decisions_content` WRITE;
/*!40000 ALTER TABLE `informea_decisions_content` DISABLE KEYS */;
INSERT INTO `informea_decisions_content` VALUES (1,'1','es','Spanish'),(2,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_decisions_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_documents`
--

DROP TABLE IF EXISTS `informea_decisions_documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `diskPath` varchar(255) DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  `mimeType` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_documents_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_documents`
--

LOCK TABLES `informea_decisions_documents` WRITE;
/*!40000 ALTER TABLE `informea_decisions_documents` DISABLE KEYS */;
INSERT INTO `informea_decisions_documents` VALUES (1,'1','/tmp/test.pdf','http://test.pdf','pdf','en','test.pdf'),(2,'1','/tmp/test.doc','http://test.doc','doc','ar','test.doc'),(3,'2','invalid','invalid','invalid',NULL,NULL),(4,'3','test.doc','http://test.doc','doc','en','test.doc');
/*!40000 ALTER TABLE `informea_decisions_documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_keywords`
--

DROP TABLE IF EXISTS `informea_decisions_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `namespace` varchar(20) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_keywords_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_keywords`
--

LOCK TABLES `informea_decisions_keywords` WRITE;
/*!40000 ALTER TABLE `informea_decisions_keywords` DISABLE KEYS */;
INSERT INTO `informea_decisions_keywords` VALUES (1,'1','http://www.unep.org','Biological Diversity'),(2,'1','http://www.unep.org','Endangered Species'),(3,'2',NULL,'null namespace'),(4,'2','http://null.term',NULL),(5,'3',NULL,NULL);
/*!40000 ALTER TABLE `informea_decisions_keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_longtitle`
--

DROP TABLE IF EXISTS `informea_decisions_longtitle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_longtitle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `long_title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_longtitle_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_longtitle`
--

LOCK TABLES `informea_decisions_longtitle` WRITE;
/*!40000 ALTER TABLE `informea_decisions_longtitle` DISABLE KEYS */;
INSERT INTO `informea_decisions_longtitle` VALUES (1,'1','cn','Chinese'),(2,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_decisions_longtitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_summary`
--

DROP TABLE IF EXISTS `informea_decisions_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `summary` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_summary_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_summary`
--

LOCK TABLES `informea_decisions_summary` WRITE;
/*!40000 ALTER TABLE `informea_decisions_summary` DISABLE KEYS */;
INSERT INTO `informea_decisions_summary` VALUES (1,'1','de','Deutsch'),(2,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_decisions_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_decisions_title`
--

DROP TABLE IF EXISTS `informea_decisions_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_decisions_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `decision_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `decision_id` (`decision_id`),
  CONSTRAINT `informea_decisions_title_fk` FOREIGN KEY (`decision_id`) REFERENCES `informea_decisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_decisions_title`
--

LOCK TABLES `informea_decisions_title` WRITE;
/*!40000 ALTER TABLE `informea_decisions_title` DISABLE KEYS */;
INSERT INTO `informea_decisions_title` VALUES (1,'1','en','English'),(2,'1','ro','Romaneste'),(3,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_decisions_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_meetings`
--

DROP TABLE IF EXISTS `informea_meetings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_meetings` (
  `id` varchar(20) NOT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `repetition` varchar(20) DEFAULT NULL,
  `kind` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `access` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `imageUrl` varchar(20) DEFAULT NULL,
  `imageCopyright` varchar(20) DEFAULT NULL,
  `location` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `latitude` double(15,3) DEFAULT NULL,
  `longitude` double(15,3) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_meetings`
--

LOCK TABLES `informea_meetings` WRITE;
/*!40000 ALTER TABLE `informea_meetings` DISABLE KEYS */;
INSERT INTO `informea_meetings` VALUES ('1','basel','http://www.test.org','2010-10-10 01:01:01','2010-10-11 02:02:02','yearly','official','cop','public','confirmed','http://imageurl','img copyright','location','city','ro',1.010,2.020,'2010-01-01 01:01:01'),('2','invalid','http://www.test.org','2010-10-10 01:01:01','2010-10-11 02:02:02','yearly','official','cop','public','confirmed','http://imageurl','img copyright','location','city','ro',1.010,2.020,'2010-01-01 01:01:01'),('3',NULL,'http://www.test.org','2010-10-10 01:01:01','2010-10-11 02:02:02','yearly','official','cop','public','confirmed','http://imageurl','img copyright','location','city','ro',1.010,2.020,'2010-01-01 01:01:01'),('4','basel','www.test.org',NULL,'2010-10-11 02:02:02','yearly','official','cop','public','confirmed','http://imageurl','img copyright','location',NULL,NULL,1.010,2.020,'2010-01-01 01:01:01'),('5','basel','www.test.org','2010-10-10 01:01:01','2010-10-11 02:02:02','yearly','official','cop','public','confirmed','http://imageurl','img copyright','location','city','Romania',1.010,2.020,'2010-01-01 01:01:01');
/*!40000 ALTER TABLE `informea_meetings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_meetings_description`
--

DROP TABLE IF EXISTS `informea_meetings_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_meetings_description` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `description` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id` (`meeting_id`),
  CONSTRAINT `informea_meetings_description_fk` FOREIGN KEY (`meeting_id`) REFERENCES `informea_meetings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_meetings_description`
--

LOCK TABLES `informea_meetings_description` WRITE;
/*!40000 ALTER TABLE `informea_meetings_description` DISABLE KEYS */;
INSERT INTO `informea_meetings_description` VALUES (1,'1','en','desc_en'),(2,'1','es','desc_es'),(3,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_meetings_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_meetings_title`
--

DROP TABLE IF EXISTS `informea_meetings_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_meetings_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` varchar(20) NOT NULL,
  `language` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id` (`meeting_id`),
  CONSTRAINT `informea_meetings_title_fk` FOREIGN KEY (`meeting_id`) REFERENCES `informea_meetings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_meetings_title`
--

LOCK TABLES `informea_meetings_title` WRITE;
/*!40000 ALTER TABLE `informea_meetings_title` DISABLE KEYS */;
INSERT INTO `informea_meetings_title` VALUES (1,'1','ro','Romaneste'),(2,'1','en','English'),(3,'2',NULL,NULL);
/*!40000 ALTER TABLE `informea_meetings_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_national_plans`
--

DROP TABLE IF EXISTS `informea_national_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_national_plans` (
  `id` varchar(20) NOT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  `submission` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_national_plans`
--

LOCK TABLES `informea_national_plans` WRITE;
/*!40000 ALTER TABLE `informea_national_plans` DISABLE KEYS */;
INSERT INTO `informea_national_plans` VALUES ('1','cbd','ro','nama','http://url.com','2011-03-03 03:03:03','2011-04-04 04:04:04'),('2',NULL,NULL,NULL,NULL,NULL,NULL),('3',NULL,NULL,NULL,NULL,NULL,NULL),('4',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_national_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_national_plans_title`
--

DROP TABLE IF EXISTS `informea_national_plans_title`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_national_plans_title` (
  `id` varchar(20) NOT NULL,
  `national_plan_id` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_national_plans_title`
--

LOCK TABLES `informea_national_plans_title` WRITE;
/*!40000 ALTER TABLE `informea_national_plans_title` DISABLE KEYS */;
INSERT INTO `informea_national_plans_title` VALUES ('1','1','ro','Romaneste'),('2','1','en','English'),('3','2','ro',NULL),('4','3',NULL,'Romaneste');
/*!40000 ALTER TABLE `informea_national_plans_title` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_sites`
--

DROP TABLE IF EXISTS `informea_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_sites` (
  `id` varchar(20) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `treaty` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  `latitude` double(15,3) DEFAULT NULL,
  `longitude` double(15,3) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_sites`
--

LOCK TABLES `informea_sites` WRITE;
/*!40000 ALTER TABLE `informea_sites` DISABLE KEYS */;
INSERT INTO `informea_sites` VALUES ('1','whc','ro','cbd','Name','http://url.com','2011-03-03 03:03:03'),('2',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `informea_sites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informea_sites_name`
--

DROP TABLE IF EXISTS `informea_sites_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informea_sites_name` (
  `id` varchar(20) NOT NULL,
  `site_id` varchar(20) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `site_id` (`site_id`),
  CONSTRAINT `informea_sites_name_fk` FOREIGN KEY (`site_id`) REFERENCES `informea_sites` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informea_sites_name`
--

LOCK TABLES `informea_sites_name` WRITE;
/*!40000 ALTER TABLE `informea_sites_name` DISABLE KEYS */;
INSERT INTO `informea_sites_name` VALUES ('1','1','en','English'),('2','1','ro','Romaneste'),('3','2',NULL,NULL);
/*!40000 ALTER TABLE `informea_sites_name` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-07-26 15:12:53
