-- MySQL dump 10.13  Distrib 5.5.30, for osx10.9 (x86_64)
--
-- Host: localhost    Database: informea_odata_test_source
-- ------------------------------------------------------
-- Server version	5.5.30-log

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
-- Table structure for table `ai_country`
--

DROP TABLE IF EXISTS `ai_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_country` (
  `id` smallint(2) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the country. Primary Key',
  `code` varchar(3) NOT NULL COMMENT 'ISO 3-letter code of the country',
  `code2l` varchar(2) DEFAULT NULL COMMENT 'ISO 2-letter code',
  `name` varchar(64) NOT NULL COMMENT 'Name of the country in English',
  `long_name` varchar(64) DEFAULT NULL COMMENT 'Country''s full name in english or original language',
  `icon_large` varchar(255) DEFAULT 'unknown' COMMENT 'Relative path to the large image of country''s flag',
  `icon_medium` varchar(255) NOT NULL DEFAULT 'unknown' COMMENT 'Relative path to the medium image of country''s flag',
  `eu_member` varchar(20) DEFAULT NULL COMMENT 'Year entering EU. Null means non-member',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8 COMMENT='Hold the list of countries';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_country`
--

LOCK TABLES `ai_country` WRITE;
/*!40000 ALTER TABLE `ai_country` DISABLE KEYS */;
INSERT INTO `ai_country` VALUES (1,'AFG','AF','Afghanistan','Islamic Republic of Afghanistan','images/country/Afghanistan_large.png','images/country/Afghanistan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(2,'ALB','AL','Albania','Republic of Albania','images/country/Albania_large.png','images/country/Albania_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(3,'DZA','DZ','Algeria','People\'s Democratic Republic of Algeria','images/country/Algeria_large.png','images/country/Algeria_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(4,'AND','AD','Andorra','Principality of Andorra','images/country/Andorra_large.png','images/country/Andorra_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(5,'AGO','AO','Angola','Republic of Angola','images/country/Angola_large.png','images/country/Angola_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(6,'ATG','AG','Antigua and Barbuda','Antigua and Barbuda','images/country/Antigua_and_Barbuda_large.png','images/country/Antigua_and_Barbuda_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(7,'ARG','AR','Argentina','República Argentina','images/country/Argentina_large.png','images/country/Argentina_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(8,'ARM','AM','Armenia','Republic of Armenia','images/country/Armenia_large.png','images/country/Armenia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(9,'AUS','AU','Australia','Australia','images/country/Australia_large.png','images/country/Australia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(10,'AUT','AT','Austria','Republic of Austria','images/country/Austria_large.png','images/country/Austria_medium.png','1995','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(11,'AZE','AZ','Azerbaijan','Republic of Azerbaijan','images/country/Azerbaijan_large.png','images/country/Azerbaijan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(12,'BHS','BS','Bahamas','Commonwealth of The Bahamas','images/country/Bahamas_large.png','images/country/Bahamas_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(13,'BHR','BH','Bahrain','Kingdom of Bahrain','images/country/Bahrain_large.png','images/country/Bahrain_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(14,'BGD','BD','Bangladesh','People\'s Republic of Bangladesh','images/country/Bangladesh_large.png','images/country/Bangladesh_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(15,'BRB','BB','Barbados','Republic of Belarus','images/country/Barbados_large.png','images/country/Barbados_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(16,'BLR','BY','Belarus','Republic of Belarus','images/country/Belarus_large.png','images/country/Belarus_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(17,'BEL','BE','Belgium','Kingdom of Belgium','images/country/Belgium_large.png','images/country/Belgium_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(18,'BLZ','BZ','Belize','Belize','images/country/Belize_large.png','images/country/Belize_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(19,'BEN','BJ','Benin','Republic of Benin','images/country/Benin_large.png','images/country/Benin_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(20,'BTN','BT','Bhutan','Kingdom of Bhutan','images/country/Bhutan_large.png','images/country/Bhutan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(21,'BIH','BA','Bosnia and Herzegovina','Bosnia and Herzegovina','images/country/Bosnia_and_Herzegovina_large.png','images/country/Bosnia_and_Herzegovina_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(22,'BWA','BW','Botswana','Republic of Botswana','images/country/Botswana_large.png','images/country/Botswana_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(23,'BRA','BR','Brazil','Federative Republic of Brazil','images/country/Brazil_large.png','images/country/Brazil_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(24,'BRN','BN','Brunei Darussalam','Brunei Darussalam','images/country/Brunei_Darussalam_large.png','images/country/Brunei_Darussalam_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(25,'BGR','BG','Bulgaria','Republic of Bulgaria','images/country/Bulgaria_large.png','images/country/Bulgaria_medium.png','2007','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(26,'BFA','BF','Burkina Faso','Burkina Faso','images/country/Burkina_Faso_large.png','images/country/Burkina_Faso_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(27,'BDI','BI','Burundi','Republic of Burundi','images/country/Burundi_large.png','images/country/Burundi_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(28,'KHM','KH','Cambodia','Kingdom of Cambodia','images/country/Cambodia_large.png','images/country/Cambodia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(29,'CMR','CM','Cameroon','Republic of Cameroon','images/country/Cameroon_large.png','images/country/Cameroon_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(30,'CAN','CA','Canada','Canada','images/country/Canada_large.png','images/country/Canada_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(31,'CPV','CV','Cape Verde','Republic of Cape Verde','images/country/Cape_Verde_large.png','images/country/Cape_Verde_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(32,'CAF','CF','Central African Republic','Central African Republic','images/country/Central_African_Republic_large.png','images/country/Central_African_Republic_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(33,'TCD','TD','Chad','Republic of Chad','images/country/Chad_large.png','images/country/Chad_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(34,'CHL','CL','Chile','Republic of Chile','images/country/Chile_large.png','images/country/Chile_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(35,'CHN','CN','China','People\'s Republic of China','images/country/China_large.png','images/country/China_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(36,'COL','CO','Colombia','Republic of Colombia','images/country/Colombia_large.png','images/country/Colombia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(37,'COM','KM','Comoros','Union of the Comoros','images/country/Comoros_large.png','images/country/Comoros_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(38,'COG','CG','Congo','Congo, Democratic Republic of the','images/country/Congo_large.png','images/country/Congo_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(39,'CRI','CR','Costa Rica','Republic of Costa Rica','images/country/Costa_Rica_large.png','images/country/Costa_Rica_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(40,'HRV','HR','Croatia','Republic of Croatia','images/country/Croatia_large.png','images/country/Croatia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(41,'CUB','CU','Cuba','Republic of Cuba','images/country/Cuba_large.png','images/country/Cuba_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(42,'CYP','CY','Cyprus','Republic of Cyprus','images/country/Cyprus_large.png','images/country/Cyprus_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(43,'CZE','CZ','Czech Republic','Czech Republic','images/country/Czech_Republic_large.png','images/country/Czech_Republic_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(44,'CIV','CI','Côte d\'Ivoire','Republic of Côte d\'Ivoire','images/country/Cote_d_Ivoire_large.png','images/country/Cote_d_Ivoire_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(45,'DNK','DK','Denmark','Kingdom of Denmark','images/country/Denmark_large.png','images/country/Denmark_medium.png','1973','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(46,'DJI','DJ','Djibouti','Republic of Djibouti','images/country/Djibouti_large.png','images/country/Djibouti_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(47,'DMA','DM','Dominica','Commonwealth of Dominica','images/country/Dominica_large.png','images/country/Dominica_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(48,'DOM','DO','Dominican Republic','Dominican Republic','images/country/Dominican_Republic_large.png','images/country/Dominican_Republic_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(49,'ECU','EC','Ecuador','Republic of Ecuador','images/country/Ecuador_large.png','images/country/Ecuador_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(50,'EGY','EG','Egypt','Arab Republic of Egypt','images/country/Egypt_large.png','images/country/Egypt_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(51,'SLV','SV','El Salvador','Republic of El Salvador','images/country/El_Salvador_large.png','images/country/El_Salvador_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(52,'GNQ','GQ','Equatorial Guinea','Republic of Equatorial Guinea','images/country/Equatorial_Guinea_large.png','images/country/Equatorial_Guinea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(53,'ERI','ER','Eritrea','State of Eritrea','images/country/Eritrea_large.png','images/country/Eritrea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(54,'EST','EE','Estonia','Republic of Estonia','images/country/Estonia_large.png','images/country/Estonia_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(55,'ETH','ET','Ethiopia','Federal Democratic Republic of Ethiopia','images/country/Ethiopia_large.png','images/country/Ethiopia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(56,'FJI','FJ','Fiji','Republic of the Fiji Islands','images/country/Fiji_large.png','images/country/Fiji_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(57,'FIN','FI','Finland','Republic of Finland','images/country/Finland_large.png','images/country/Finland_medium.png','1995','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(58,'FRA','FR','France','French Republic','images/country/France_large.png','images/country/France_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(59,'GAB','GA','Gabon','Gabonese Republic','images/country/Gabon_large.png','images/country/Gabon_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(60,'GMB','GM','Gambia','Republic of The Gambia','images/country/Gambia_large.png','images/country/Gambia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(61,'GEO','GE','Georgia','Georgia','images/country/Georgia_large.png','images/country/Georgia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(62,'DEU','DE','Germany','Federal Republic of Germany','images/country/Germany_large.png','images/country/Germany_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(63,'GHA','GH','Ghana','Republic of Ghana','images/country/Ghana_large.png','images/country/Ghana_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(64,'GRC','GR','Greece','Hellenic Republic','images/country/Greece_large.png','images/country/Greece_medium.png','1981','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(65,'GRD','GD','Grenada','Grenada','images/country/Grenada_large.png','images/country/Grenada_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(66,'GTM','GT','Guatemala','Republic of Guatemala','images/country/Guatemala_large.png','images/country/Guatemala_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(67,'GIN','GN','Guinea','Republic of Guinea','images/country/Guinea_large.png','images/country/Guinea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(68,'GNB','GW','Guinea-Bissau','Republic of Guinea-Bissau','images/country/Guinea-Bissau_large.png','images/country/Guinea-Bissau_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(69,'GUY','GY','Guyana','Co-operative Republic of Guyana','images/country/Guyana_large.png','images/country/Guyana_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(70,'HTI','HT','Haiti','Republic of Haiti','images/country/Haiti_large.png','images/country/Haiti_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(71,'HND','HN','Honduras','Republic of Honduras','images/country/Honduras_large.png','images/country/Honduras_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(72,'HUN','HU','Hungary','Republic of Hungary','images/country/Hungary_large.png','images/country/Hungary_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(73,'ISL','IS','Iceland','Republic of Iceland','images/country/Iceland_large.png','images/country/Iceland_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(74,'IND','IN','India','Republic of India','images/country/India_large.png','images/country/India_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(75,'IDN','ID','Indonesia','Republic of Indonesia','images/country/Indonesia_large.png','images/country/Indonesia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(76,'IRQ','IQ','Iraq','Republic of Iraq','images/country/Iraq_large.png','images/country/Iraq_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(77,'IRL','IE','Ireland','Ireland','images/country/Ireland_large.png','images/country/Ireland_medium.png','1973','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(78,'ISR','IL','Israel','State of Israel','images/country/Israel_large.png','images/country/Israel_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(79,'ITA','IT','Italy','Italian Republic','images/country/Italy_large.png','images/country/Italy_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(80,'JAM','JM','Jamaica','Jamaica','images/country/Jamaica_large.png','images/country/Jamaica_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(81,'JPN','JP','Japan','Japan','images/country/Japan_large.png','images/country/Japan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(82,'JOR','JO','Jordan','Hashemite Kingdom of Jordan','images/country/Jordan_large.png','images/country/Jordan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(83,'KAZ','KZ','Kazakhstan','Republic of Kazakhstan','images/country/Kazakhstan_large.png','images/country/Kazakhstan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(84,'KEN','KE','Kenya','Republic of Kenya','images/country/Kenya_large.png','images/country/Kenya_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(85,'KIR','KI','Kiribati','Republic of Kiribati','images/country/Kiribati_large.png','images/country/Kiribati_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(86,'KWT','KW','Kuwait','State of Kuwait','images/country/Kuwait_large.png','images/country/Kuwait_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(87,'KGZ','KG','Kyrgyzstan','Kyrgyz Republic','images/country/Kyrgyzstan_large.png','images/country/Kyrgyzstan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(88,'LAO','LA','Lao People\'s Democratic Republic','Lao People\'s Democratic Republic','images/country/Lao_People_s_Democratic_Republic_large.png','images/country/Lao_People_s_Democratic_Republic_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(89,'LVA','LV','Latvia','Republic of Latvia','images/country/Latvia_large.png','images/country/Latvia_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(90,'LBN','LB','Lebanon','Lebanese Republic','images/country/Lebanon_large.png','images/country/Lebanon_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(91,'LSO','LS','Lesotho','Kingdom of Lesotho','images/country/Lesotho_large.png','images/country/Lesotho_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(92,'LBR','LR','Liberia','Republic of Liberia','images/country/Liberia_large.png','images/country/Liberia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(93,'LBY','LY','Libya','Libya','images/country/Libyan_Arab_Jamahiriya_large.png','images/country/Libyan_Arab_Jamahiriya_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(94,'LIE','LI','Liechtenstein','Principality of Liechtenstein','images/country/Liechtenstein_large.png','images/country/Liechtenstein_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(95,'LTU','LT','Lithuania','Republic of Lithuania','images/country/Lithuania_large.png','images/country/Lithuania_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(96,'LUX','LU','Luxembourg','Grand Duchy of Luxembourg','images/country/Luxembourg_large.png','images/country/Luxembourg_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(97,'MDG','MG','Madagascar','Republic of Madagascar','images/country/Madagascar_large.png','images/country/Madagascar_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(98,'MWI','MW','Malawi','Republic of Malawi','images/country/Malawi_large.png','images/country/Malawi_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(99,'MYS','MY','Malaysia','Malaysia','images/country/Malaysia_large.png','images/country/Malaysia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(100,'MDV','MV','Maldives','Republic of Maldives','images/country/Maldives_large.png','images/country/Maldives_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(101,'MLI','ML','Mali','Republic of Mali','images/country/Mali_large.png','images/country/Mali_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(102,'MLT','MT','Malta','Republic of Malta','images/country/Malta_large.png','images/country/Malta_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(103,'MHL','MH','Marshall Islands','Republic of the Marshall Islands','images/country/Marshall_Islands_large.png','images/country/Marshall_Islands_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(104,'MRT','MR','Mauritania','Islamic Republic of Mauritania','images/country/Mauritania_large.png','images/country/Mauritania_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(105,'MUS','MU','Mauritius','Republic of Mauritius','images/country/Mauritius_large.png','images/country/Mauritius_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(106,'MEX','MX','Mexico','United Mexican States','images/country/Mexico_large.png','images/country/Mexico_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(107,'MCO','MC','Monaco','Principality of Monaco','images/country/Monaco_large.png','images/country/Monaco_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(108,'MNG','MN','Mongolia','Mongolia','images/country/Mongolia_large.png','images/country/Mongolia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(109,'MNE','ME','Montenegro','Montenegro','images/country/Montenegro_large.png','images/country/Montenegro_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(110,'MAR','MA','Morocco','Kingdom of Morocco','images/country/Morocco_large.png','images/country/Morocco_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(111,'MOZ','MZ','Mozambique','Republic of Mozambique','images/country/Mozambique_large.png','images/country/Mozambique_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(112,'MMR','MM','Myanmar','Myanmar','images/country/Myanmar_large.png','images/country/Myanmar_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(113,'NAM','NA','Namibia','Republic of Namibia','images/country/Namibia_large.png','images/country/Namibia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(114,'NRU','NR','Nauru','Republic of Nauru','images/country/Nauru_large.png','images/country/Nauru_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(115,'NPL','NP','Nepal','Federal Democratic Republic of Nepal','images/country/Nepal_large.png','images/country/Nepal_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(116,'NLD','NL','Netherlands','Kingdom of the Netherlands','images/country/Netherlands_large.png','images/country/Netherlands_medium.png','Founder','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(117,'NZL','NZ','New Zealand','New Zealand','images/country/New_Zealand_large.png','images/country/New_Zealand_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(118,'NIC','NI','Nicaragua','Republic of Nicaragua','images/country/Nicaragua_large.png','images/country/Nicaragua_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(119,'NER','NE','Niger','Republic of Niger','images/country/Niger_large.png','images/country/Niger_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(120,'NGA','NG','Nigeria','Federal Republic of Nigeria','images/country/Nigeria_large.png','images/country/Nigeria_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(121,'NOR','NO','Norway','Kingdom of Norway','images/country/Norway_large.png','images/country/Norway_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(122,'OMN','OM','Oman','Sultanate of Oman','images/country/Oman_large.png','images/country/Oman_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(123,'PAK','PK','Pakistan','Islamic Republic of Pakistan','images/country/Pakistan_large.png','images/country/Pakistan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(124,'PLW','PW','Palau','Republic of Palau','images/country/Palau_large.png','images/country/Palau_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(125,'PAN','PA','Panama','Republic of Panama','images/country/Panama_large.png','images/country/Panama_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(126,'PNG','PG','Papua New Guinea','Independent State of Papua New Guinea','images/country/Papua_New_Guinea_large.png','images/country/Papua_New_Guinea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(127,'PRY','PY','Paraguay','Republic of Paraguay','images/country/Paraguay_large.png','images/country/Paraguay_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(128,'PER','PE','Peru','Republic of Peru','images/country/Peru_large.png','images/country/Peru_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(129,'PHL','PH','Philippines','Republic of the Philippines','images/country/Philippines_large.png','images/country/Philippines_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(130,'POL','PL','Poland','Republic of Poland','images/country/Poland_large.png','images/country/Poland_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(131,'PRT','PT','Portugal','Portuguese Republic','images/country/Portugal_large.png','images/country/Portugal_medium.png','1986','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(132,'QAT','QA','Qatar','State of Qatar','images/country/Qatar_large.png','images/country/Qatar_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(133,'ROU','RO','Romania','Romania','images/country/Romania_large.png','images/country/Romania_medium.png','2007','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(134,'RUS','RU','Russian Federation','Russian Federation','images/country/Russian_Federation_large.png','images/country/Russian_Federation_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(135,'RWA','RW','Rwanda','Republic of Rwanda','images/country/Rwanda_large.png','images/country/Rwanda_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(136,'KNA','KN','Saint Kitts and Nevis','Federation of Saint Christopher and Nevis','images/country/Saint_Kitts_and_Nevis_large.png','images/country/Saint_Kitts_and_Nevis_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(137,'LCA','LC','Saint Lucia','Saint Lucia','images/country/Saint_Lucia_large.png','images/country/Saint_Lucia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(138,'VCT','VC','Saint Vincent and the Grenadines','Saint Vincent and the Grenadines','images/country/Saint_Vincent_and_the_Grenadines_large.png','images/country/Saint_Vincent_and_the_Grenadines_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(139,'WSM','WS','Samoa','Independent State of Samoa','images/country/Samoa_large.png','images/country/Samoa_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(140,'SMR','SM','San Marino','San Marino','images/country/San_Marino.png','images/country/San_Marino_small.png','378','2014-01-22 13:28:31','iddah','2014-01-22 13:28:31',NULL),(141,'STP','ST','Sao Tome and Principe','Democratic Republic of São Tomé and Príncipe','images/country/Sao_Tome_and_Principe_large.png','images/country/Sao_Tome_and_Principe_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(142,'SAU','SA','Saudi Arabia','Kingdom of Saudi Arabia','images/country/Saudi_Arabia_large.png','images/country/Saudi_Arabia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(143,'SEN','SN','Senegal','Republic of Senegal','images/country/Senegal_large.png','images/country/Senegal_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(144,'SRB','RS','Serbia','Republic of Serbia','images/country/Serbia_large.png','images/country/Serbia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(145,'SYC','SC','Seychelles','Republic of Seychelles','images/country/Seychelles_large.png','images/country/Seychelles_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(146,'SLE','SL','Sierra Leone','Republic of Sierra Leone','images/country/Sierra_Leone_large.png','images/country/Sierra_Leone_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(147,'SGP','SG','Singapore','Republic of Singapore','images/country/Singapore_large.png','images/country/Singapore_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(148,'SVK','SK','Slovakia','Slovak Republic','images/country/Slovakia_large.png','images/country/Slovakia_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(149,'SVN','SI','Slovenia','Republic of Slovenia','images/country/Slovenia_large.png','images/country/Slovenia_medium.png','2004','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(150,'SLB','SB','Solomon Islands','Solomon Islands','images/country/Solomon_Islands_large.png','images/country/Solomon_Islands_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(151,'SOM','SO','Somalia','Republic of Somalia','images/country/Somalia_large.png','images/country/Somalia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(152,'ZAF','ZA','South Africa','Republic of South Africa','images/country/South_Africa_large.png','images/country/South_Africa_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(153,'ESP','ES','Spain','Kingdom of Spain','images/country/Spain_large.png','images/country/Spain_medium.png','1986','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(154,'LKA','LK','Sri Lanka','Democratic Socialist Republic of Sri Lanka','images/country/Sri_Lanka_large.png','images/country/Sri_Lanka_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(155,'SDN','SD','Sudan','Republic of the Sudan','images/country/Sudan_large.png','images/country/Sudan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(156,'SUR','SR','Suriname','Republic of Suriname','images/country/Suriname_large.png','images/country/Suriname_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(157,'SWZ','SZ','Swaziland','Kingdom of Swaziland','images/country/Swaziland_large.png','images/country/Swaziland_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(158,'SWE','SE','Sweden','Kingdom of Sweden','images/country/Sweden_large.png','images/country/Sweden_medium.png','1995','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(159,'CHE','CH','Switzerland','Swiss Confederation','images/country/Switzerland_large.png','images/country/Switzerland_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(160,'SYR','SY','Syrian Arab Republic','Syrian Arab Republic','images/country/Syrian_Arab_Republic_large.png','images/country/Syrian_Arab_Republic_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(161,'TJK','TJ','Tajikistan','Republic of Tajikistan','images/country/Tajikistan_large.png','images/country/Tajikistan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(162,'THA','TH','Thailand','Kingdom of Thailand','images/country/Thailand_large.png','images/country/Thailand_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(163,'TLS','TL','Timor-Leste','Timor-Leste','images/country/Timor-Leste_large.png','images/country/Timor-Leste_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(164,'TGO','TG','Togo','Togolese Republic','images/country/Togo_large.png','images/country/Togo_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(165,'TON','TO','Tonga','Kingdom of Tonga','images/country/Tonga_large.png','images/country/Tonga_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(166,'TTO','TT','Trinidad and Tobago','Republic of Trinidad and Tobago','images/country/Trinidad_and_Tobago_large.png','images/country/Trinidad_and_Tobago_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(167,'TUN','TN','Tunisia','Republic of Tunisia','images/country/Tunisia_large.png','images/country/Tunisia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(168,'TUR','TR','Turkey','Republic of Turkey','images/country/Turkey_large.png','images/country/Turkey_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(169,'TKM','TM','Turkmenistan','Turkmenistan','images/country/Turkmenistan_large.png','images/country/Turkmenistan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(170,'TUV','TV','Tuvalu','Tuvalu','images/country/Tuvalu_large.png','images/country/Tuvalu_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(171,'UGA','UG','Uganda','Republic of Uganda','images/country/Uganda_large.png','images/country/Uganda_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(172,'UKR','UA','Ukraine','Ukraine','images/country/Ukraine_large.png','images/country/Ukraine_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(173,'ARE','AE','United Arab Emirates','United Arab Emirates','images/country/United_Arab_Emirates_large.png','images/country/United_Arab_Emirates_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(174,'URY','UY','Uruguay','Eastern Republic of Uruguay','images/country/Uruguay_large.png','images/country/Uruguay_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(175,'UZB','UZ','Uzbekistan','Republic of Uzbekistan','images/country/Uzbekistan_large.png','images/country/Uzbekistan_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(176,'VUT','VU','Vanuatu','Republic of Vanuatu','images/country/Vanuatu_large.png','images/country/Vanuatu_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(177,'VNM','VN','Viet Nam','Socialist Republic of Vietnam','images/country/Viet_Nam_large.png','images/country/Viet_Nam_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(178,'YEM','YE','Yemen','Republic of Yemen','images/country/Yemen_large.png','images/country/Yemen_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(179,'ZMB','ZM','Zambia','Republic of Zambia','images/country/Zambia_large.png','images/country/Zambia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(180,'ZWE','ZW','Zimbabwe','Republic of Zimbabwe','images/country/Zimbabwe_large.png','images/country/Zimbabwe_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(181,'COK','CK','Cook Islands','Cook Islands','images/country/cook_large.png','images/country/cook_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(182,'BOL','BO','Bolivia','Plurinational State of Bolivia','images/country/bolivia_large.png','images/country/bolivia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(183,'COD','CD','Democratic Republic of the Congo','Congo, Democratic Republic of the','images/country/Democratic_Republic_of_the_Congo_large.png','images/country/Democratic_Republic_of_the_Congo_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(184,'EUR','EU','European Union','European Union','images/country/eu_large.png','images/country/eu_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(185,'FSM','FM','Micronesia','Micronesia, Federated States of','images/country/micronesia_large.png','images/country/micronesia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(186,'GBR','GB','United Kingdom','United Kingdom of Great Britain and Northern Ireland','images/country/gb_large.png','images/country/gb_medium.png','1973','0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(187,'IRN','IR','Iran','Islamic Republic of Iran','images/country/iran_large.png','images/country/iran_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(188,'PRK','KP','DPRK','Democratic People\'s Republic of Korea','images/country/north_korea_large.png','images/country/north_korea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(189,'KOR','KR','Republic of Korea','Republic of Korea','images/country/korea_large.png','images/country/korea_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(190,'MDA','MD','Moldova','Republic of Moldova','images/country/moldova_large.png','images/country/moldova_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(191,'MKD','MK','The former Yugoslav Republic of Macedonia','The former Yugoslav Republic of Macedonia','images/country/macedonia_large.png','images/country/macedonia_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(192,'NIU','NU','Niue','Niue','images/country/niue_large.png','images/country/niue_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(193,'TZA','TZ','Tanzania','United Republic of Tanzania','images/country/tanzania_large.png','images/country/tanzania_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(194,'VEN','VE','Venezuela','Bolivarian Republic of Venezuela','images/country/venezuela_large.png','images/country/venezuela_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(195,'SSD','SS','South Sudan','South Sudan','images/country/South_Sudan_medium.png','images/country/South_Sudan_small.png','0','2014-01-23 04:00:44','iddah','2014-01-23 04:00:44',NULL),(213,'GRL','GL','Greenland','Greenland','images/country/Greenland_large.png','images/country/Greenland_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(232,'PCN','PN','Pitcairn','Pitcairn','images/country/Pitcairn_large.png','images/country/Pitcairn_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(233,'PRI','PR','Puerto Rico','Puerto Rico','images/country/Puerto_Rico_large.png','images/country/Puerto_Rico_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(244,'USA','US','United States','United States of America','images/country/United_States_large.png','images/country/United_States_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL),(245,'VAT','VA','Vatican City','State of the Vatican City','images/country/Holy_See_vatican_City_State_large.png','images/country/Holy_See_vatican_City_State_medium.png',NULL,'0000-00-00 00:00:00',NULL,'2012-12-12 17:17:50',NULL);
/*!40000 ALTER TABLE `ai_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_country_plan`
--

DROP TABLE IF EXISTS `ai_country_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_country_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Internal ID of the report',
  `original_id` varchar(64) NOT NULL COMMENT 'ID in original MEA database',
  `id_event` int(11) unsigned DEFAULT NULL,
  `id_country` smallint(2) unsigned NOT NULL COMMENT 'Country ID',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Treaty for the plan',
  `type` enum('nama','nap','napa','nbsap','nip','nwp','cepa') DEFAULT NULL,
  `title` varchar(255) NOT NULL COMMENT 'Title of the report',
  `submission` date DEFAULT NULL COMMENT 'Date of submission',
  `document_url` varchar(255) DEFAULT NULL COMMENT 'URL of the report document',
  `obligation_format_id` int(11) DEFAULT NULL,
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was last updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_original_id` (`original_id`),
  KEY `idx_id_country` (`id_country`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_is_indexed` (`is_indexed`),
  KEY `id_event` (`id_event`),
  CONSTRAINT `fk_ai_country_plan_ai_event` FOREIGN KEY (`id_event`) REFERENCES `ai_event` (`id`),
  CONSTRAINT `fk_ai_country_plan_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_ai_country_plan_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='National Plans';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_country_plan`
--

LOCK TABLES `ai_country_plan` WRITE;
/*!40000 ALTER TABLE `ai_country_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_country_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_country_report`
--

DROP TABLE IF EXISTS `ai_country_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_country_report` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Internal ID of the report',
  `original_id` varchar(64) NOT NULL COMMENT 'ID in original MEA Database',
  `id_event` int(11) unsigned DEFAULT NULL,
  `id_country` smallint(2) unsigned NOT NULL COMMENT 'Country ID',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Treaty for the report',
  `title` varchar(255) NOT NULL COMMENT 'Title of the report',
  `submission` date DEFAULT NULL COMMENT 'Date of submission',
  `document_url` varchar(255) NOT NULL COMMENT 'URL of the report document',
  `obligation_format_id` int(11) DEFAULT NULL,
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was last updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_original_id` (`original_id`),
  KEY `idx_id_country` (`id_country`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_is_indexed` (`is_indexed`),
  KEY `id_event` (`id_event`),
  CONSTRAINT `fk_ai_country_report_ai_event` FOREIGN KEY (`id_event`) REFERENCES `ai_event` (`id`),
  CONSTRAINT `fk_ai_country_report_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_ai_country_report_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Country Reports for Conventions';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_country_report`
--

LOCK TABLES `ai_country_report` WRITE;
/*!40000 ALTER TABLE `ai_country_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_country_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_country_site`
--

DROP TABLE IF EXISTS `ai_country_site`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_country_site` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Internal ID of the site',
  `original_id` varchar(64) NOT NULL COMMENT 'ID in original MEA Database',
  `id_country` smallint(2) unsigned NOT NULL COMMENT 'Country where site is located',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Treaty which treaty covers',
  `name` varchar(255) NOT NULL COMMENT 'Name of the site',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL to site presentation',
  `latitude` double DEFAULT NULL COMMENT 'Site''s geographical latitude',
  `longitude` double DEFAULT NULL COMMENT 'Site''s geographical longitude',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was last updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_original_id` (`original_id`),
  KEY `idx_id_country` (`id_country`),
  KEY `idx_id_treaty` (`id_treaty`),
  CONSTRAINT `fk_ai_country_site_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_ai_country_site_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Keeps the Convention sites (Ramsar, WHC) for each country';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_country_site`
--

LOCK TABLES `ai_country_site` WRITE;
/*!40000 ALTER TABLE `ai_country_site` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_country_site` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision`
--

DROP TABLE IF EXISTS `ai_decision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the decision. Primary Key',
  `original_id` varchar(128) DEFAULT NULL COMMENT 'Identification of the record in original database',
  `id_organization` smallint(2) unsigned DEFAULT NULL COMMENT 'Optional organization - track source if id_treaty is null',
  `link` varchar(255) DEFAULT NULL COMMENT 'Link to the decision page',
  `short_title` varchar(255) NOT NULL COMMENT 'Short title of the decision',
  `long_title` text COMMENT 'Long title of the decision',
  `summary` text,
  `type` enum('decision','resolution','recommendation','legislation','case') NOT NULL DEFAULT 'decision' COMMENT 'Type of decision',
  `status` enum('draft','active','amended','retired','revised','adopted') DEFAULT NULL,
  `number` varchar(64) NOT NULL COMMENT 'Decision number',
  `id_treaty` int(11) unsigned DEFAULT NULL COMMENT 'MEA (treaty) that issued this decision',
  `published` date DEFAULT NULL,
  `updated` datetime DEFAULT NULL COMMENT 'Date when decision was last updated',
  `id_meeting` int(11) unsigned DEFAULT NULL COMMENT 'Link to event when meeting was held',
  `meeting_title` varchar(255) DEFAULT NULL COMMENT 'Title of the decision''s meeting',
  `meeting_url` varchar(255) DEFAULT NULL COMMENT 'URL to the meeting event',
  `body` text COMMENT 'Body text of the decision',
  `display_order` int(11) NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was created on database',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `original_id` (`original_id`),
  UNIQUE KEY `original_id_2` (`original_id`),
  KEY `idx_id_type` (`type`),
  KEY `idx_id_status` (`status`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_id_meeting` (`id_meeting`),
  KEY `idx_is_indexed` (`is_indexed`),
  KEY `idx_comp_id_treaty_published` (`id_treaty`,`published`),
  KEY `id_organization` (`id_organization`),
  CONSTRAINT `fk_ai_decision_ai_organization` FOREIGN KEY (`id_organization`) REFERENCES `ai_organization` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_decision_event` FOREIGN KEY (`id_meeting`) REFERENCES `ai_event` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_decision_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table contains the decisions';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision`
--

LOCK TABLES `ai_decision` WRITE;
/*!40000 ALTER TABLE `ai_decision` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision_attributes`
--

DROP TABLE IF EXISTS `ai_decision_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision_attributes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_decision` int(11) unsigned NOT NULL COMMENT 'Decision ID',
  `id_attribute` varchar(128) NOT NULL COMMENT 'Attribute ID such as: pages, reference_number etc.',
  `attribute_name` varchar(128) DEFAULT NULL COMMENT '(Optional) Atribute name in human language',
  `value` text COMMENT 'Actual value',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `idx_unq_ai_decision_attributes` (`id_decision`,`id_attribute`),
  KEY `id_decision` (`id_decision`),
  CONSTRAINT `fk_ai_decision_attributes_decision` FOREIGN KEY (`id_decision`) REFERENCES `ai_decision` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision_attributes`
--

LOCK TABLES `ai_decision_attributes` WRITE;
/*!40000 ALTER TABLE `ai_decision_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision_country`
--

DROP TABLE IF EXISTS `ai_decision_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision_country` (
  `id_decision` int(11) unsigned NOT NULL,
  `id_country` smallint(2) unsigned NOT NULL,
  PRIMARY KEY (`id_decision`,`id_country`),
  KEY `id_decision` (`id_decision`),
  KEY `id_country` (`id_country`),
  CONSTRAINT `fk_ai_decision_country_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_ai_decision_country_decision` FOREIGN KEY (`id_decision`) REFERENCES `ai_decision` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision_country`
--

LOCK TABLES `ai_decision_country` WRITE;
/*!40000 ALTER TABLE `ai_decision_country` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision_paragraph`
--

DROP TABLE IF EXISTS `ai_decision_paragraph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision_paragraph` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique identification of the article. Primary Key',
  `id_decision` int(11) unsigned NOT NULL COMMENT 'Link to decision',
  `order` tinyint(1) unsigned NOT NULL COMMENT 'Order of the article in decision.',
  `official_order` varchar(255) DEFAULT NULL COMMENT 'Paragraph title as appear in the treaty, such as a), i), 3.1) etc.',
  `indent` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Ident of paragraph, can be 1, 2, 3 ... Meaning the depth of the paragraph',
  `title` varchar(255) NOT NULL COMMENT 'Title of the article',
  `content` text NOT NULL COMMENT 'Article paragraph',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  KEY `idx_id_decision` (`id_decision`),
  KEY `idx_is_indexed` (`is_indexed`),
  CONSTRAINT `fk_decision_paragraph_decision` FOREIGN KEY (`id_decision`) REFERENCES `ai_decision` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the paragraphs of a decision text';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision_paragraph`
--

LOCK TABLES `ai_decision_paragraph` WRITE;
/*!40000 ALTER TABLE `ai_decision_paragraph` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision_paragraph` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision_paragraph_vocabulary`
--

DROP TABLE IF EXISTS `ai_decision_paragraph_vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision_paragraph_vocabulary` (
  `id_decision_paragraph` int(11) unsigned NOT NULL COMMENT 'Link to decision paragraph',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'Link to vocabulary term',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_decision_paragraph`,`id_concept`),
  KEY `idx_id_decision_paragraph` (`id_decision_paragraph`),
  KEY `idx_id_concept` (`id_concept`),
  CONSTRAINT `fk_decision_paragraph_vocabulary_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_decision_paragraph_vocabulary_decision_paragraph` FOREIGN KEY (`id_decision_paragraph`) REFERENCES `ai_decision_paragraph` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the terms tagged to each paragraph of the decision';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision_paragraph_vocabulary`
--

LOCK TABLES `ai_decision_paragraph_vocabulary` WRITE;
/*!40000 ALTER TABLE `ai_decision_paragraph_vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision_paragraph_vocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_decision_vocabulary`
--

DROP TABLE IF EXISTS `ai_decision_vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_decision_vocabulary` (
  `id_decision` int(11) unsigned NOT NULL COMMENT 'Decision ID. Primary Key',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'Vocabulary term ID. Primary Key',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_decision`,`id_concept`),
  KEY `idx_id_decision` (`id_decision`),
  KEY `idx_id_concept` (`id_concept`),
  CONSTRAINT `fk_decision_vocabulary_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_decision_vocabulary_decision` FOREIGN KEY (`id_decision`) REFERENCES `ai_decision` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold keywords related to entire decision (many-to-many rel.)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_decision_vocabulary`
--

LOCK TABLES `ai_decision_vocabulary` WRITE;
/*!40000 ALTER TABLE `ai_decision_vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_decision_vocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_document`
--

DROP TABLE IF EXISTS `ai_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_document` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the document',
  `original_id` varchar(128) DEFAULT NULL COMMENT 'Identification of the record in original database',
  `mime` enum('doc','xls','pdf','odt','rtf','txt','htm','html') NOT NULL DEFAULT 'pdf' COMMENT 'Document MIME type',
  `url` varchar(255) DEFAULT NULL COMMENT 'Document URL on MEA website',
  `id_decision` int(11) unsigned DEFAULT NULL COMMENT 'ID of the decision (if applicable)',
  `path` varchar(255) DEFAULT NULL COMMENT 'Absolute path on disk to the document',
  `language` varchar(32) DEFAULT NULL COMMENT 'Document language',
  `size` int(11) unsigned DEFAULT NULL COMMENT 'Size of the document in bytes',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Indexed by Solr',
  `filename` varchar(255) DEFAULT NULL COMMENT 'Original filename',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `original_id` (`original_id`),
  KEY `idx_id_decision` (`id_decision`),
  KEY `idx_is_indexed` (`is_indexed`),
  CONSTRAINT `fk_documents_decisions` FOREIGN KEY (`id_decision`) REFERENCES `ai_decision` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_document`
--

LOCK TABLES `ai_document` WRITE;
/*!40000 ALTER TABLE `ai_document` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_event`
--

DROP TABLE IF EXISTS `ai_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_event` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Hold the unique identification of the event inside database',
  `original_id` varchar(64) DEFAULT NULL,
  `id_organization` smallint(2) unsigned DEFAULT NULL,
  `id_treaty` int(11) unsigned DEFAULT NULL COMMENT 'Treaty of this event',
  `event_url` varchar(255) DEFAULT NULL COMMENT 'URL to the event page',
  `title` varchar(400) NOT NULL,
  `abbreviation` varchar(16) DEFAULT NULL,
  `description` text COMMENT 'Event description',
  `start` datetime NOT NULL COMMENT 'Start date of the event',
  `end` datetime DEFAULT NULL COMMENT 'Event end date',
  `repetition` enum('weekly','monthly','yearly') DEFAULT NULL COMMENT 'Event repetition interval',
  `id_event_previous` int(11) unsigned DEFAULT NULL COMMENT 'Link to previous related event',
  `kind` enum('official','partner','interest') DEFAULT NULL COMMENT 'Kind of event (MEA official event, MEA partner event, of interest event)',
  `type` enum('cop','subsidiary','expert','working','symposia','conference','workshop','informal','scc','stc','technical meeting','negotiation meeting') DEFAULT NULL,
  `access` enum('public','invitation') DEFAULT NULL COMMENT 'Event access type (public, by invitation only)',
  `status` enum('tentative','confirmed','postponed','cancelled','nodate','over') DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL COMMENT 'Path to image file related to the event',
  `image_copyright` varchar(255) DEFAULT NULL COMMENT 'Copyright holder of the image',
  `location` varchar(255) DEFAULT NULL COMMENT 'Event location',
  `city` varchar(64) DEFAULT NULL COMMENT 'City where event is held',
  `id_country` smallint(2) unsigned DEFAULT NULL COMMENT 'Country where event is held',
  `latitude` double DEFAULT NULL COMMENT 'Geographical latitude',
  `longitude` double DEFAULT NULL COMMENT 'Geographical longitude',
  `id_person` int(11) unsigned DEFAULT NULL COMMENT 'Foreign key to contact person',
  `use_informea` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If TRUE, appear in InforMEA website?',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was last updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_original_id` (`original_id`),
  KEY `idx_start` (`start`),
  KEY `idx_end` (`end`),
  KEY `idx_id_event_previous` (`id_event_previous`),
  KEY `idx_id_country` (`id_country`),
  KEY `idx_id_person` (`id_person`),
  KEY `id_treaty` (`id_treaty`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_is_indexed` (`is_indexed`),
  KEY `idx_comp_treaty_start` (`id_treaty`,`start`),
  KEY `fk_ai_event_organization` (`id_organization`),
  CONSTRAINT `fk_ai_event_organization` FOREIGN KEY (`id_organization`) REFERENCES `ai_organization` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_event_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_event_event` FOREIGN KEY (`id_event_previous`) REFERENCES `ai_event` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_event_person` FOREIGN KEY (`id_person`) REFERENCES `ai_people` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_event_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1597 DEFAULT CHARSET=utf8 COMMENT='Hold the events';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_event`
--

LOCK TABLES `ai_event` WRITE;
/*!40000 ALTER TABLE `ai_event` DISABLE KEYS */;
INSERT INTO `ai_event` VALUES (1596,'cbd-COP-05',NULL,1,'http://www.cbd.int/doc/meetings/cop/cop-05/','Fifth Ordinary Meeting of the Conference of the Parties to the Convention on Biological Diversity','COP 5','Sample description of COP 5','2000-05-10 14:00:00','2000-05-15 15:00:00','yearly',NULL,'official','cop','invitation','confirmed','image url','image copyright text','UNEP Headquarters','Nairobi',84,-1.274359,36.81311,NULL,1,'2012-09-27 17:19:04','kelly','2014-11-10 12:09:57','sync_service',1);
/*!40000 ALTER TABLE `ai_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_organization`
--

DROP TABLE IF EXISTS `ai_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_organization` (
  `id` smallint(2) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the organization',
  `name` varchar(255) DEFAULT NULL COMMENT 'Name of the organization',
  `description` text COMMENT 'Description of the organization',
  `address` varchar(255) DEFAULT NULL COMMENT 'Main address',
  `city` varchar(64) DEFAULT NULL COMMENT 'City',
  `id_country` smallint(2) unsigned DEFAULT NULL COMMENT 'Country',
  `url` varchar(255) DEFAULT NULL COMMENT 'Organization''s website URL',
  `depository` varchar(128) DEFAULT NULL COMMENT 'Place (country) where the Convention is deposited',
  `use_informea` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If TRUE, appear in InforMEA website?',
  `rec_created` timestamp NULL DEFAULT NULL COMMENT 'Date when record was last updated',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was added into database',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_name` (`name`),
  KEY `idx_id_country` (`id_country`),
  CONSTRAINT `fk_organization_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_organization`
--

LOCK TABLES `ai_organization` WRITE;
/*!40000 ALTER TABLE `ai_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_people`
--

DROP TABLE IF EXISTS `ai_people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_people` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the contact person inside database',
  `original_id` varchar(64) DEFAULT NULL COMMENT 'Identification of the record in original database',
  `id_country` smallint(2) unsigned DEFAULT NULL COMMENT 'Link to country where this person is assigned',
  `prefix` varchar(20) DEFAULT NULL COMMENT 'Name prefix such as Mr., Dr. etc.',
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL COMMENT 'Last name',
  `position` varchar(255) DEFAULT NULL COMMENT 'Professional position',
  `institution` varchar(255) DEFAULT NULL COMMENT 'Person''s organization',
  `department` varchar(255) DEFAULT NULL COMMENT 'Department inside organisation',
  `address` varchar(255) DEFAULT NULL COMMENT 'Person''s mail address or institution address',
  `email` varchar(64) DEFAULT NULL COMMENT 'E-Mail',
  `telephone` varchar(64) DEFAULT NULL COMMENT 'Telephone number',
  `fax` varchar(64) DEFAULT NULL COMMENT 'Fax number',
  `is_primary` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '1 means primary NFP',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_original_id` (`original_id`),
  KEY `idx_is_indexed` (`is_indexed`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold contact persons information';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_people`
--

LOCK TABLES `ai_people` WRITE;
/*!40000 ALTER TABLE `ai_people` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_people_treaty`
--

DROP TABLE IF EXISTS `ai_people_treaty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_people_treaty` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `id_people` int(11) unsigned NOT NULL COMMENT 'ID of the person',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'ID of the treaty',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ai_people_treaty_uniq` (`id_people`,`id_treaty`),
  KEY `idx_id_people` (`id_people`),
  KEY `idx_id_treaty` (`id_treaty`),
  CONSTRAINT `fk_ai_people_treaty_people` FOREIGN KEY (`id_people`) REFERENCES `ai_people` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ai_people_treaty_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Assignments of people to treaty (many-to-many)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_people_treaty`
--

LOCK TABLES `ai_people_treaty` WRITE;
/*!40000 ALTER TABLE `ai_people_treaty` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_people_treaty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_region`
--

DROP TABLE IF EXISTS `ai_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_region` (
  `id` tinyint(1) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `slug` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_region_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_region`
--

LOCK TABLES `ai_region` WRITE;
/*!40000 ALTER TABLE `ai_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty`
--

DROP TABLE IF EXISTS `ai_treaty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of the treaty. Primary key.',
  `id_organization` smallint(2) unsigned NOT NULL COMMENT 'MEA that owns this treaty',
  `id_parent` int(11) unsigned DEFAULT NULL,
  `short_title` varchar(255) NOT NULL COMMENT 'Short title of the treaty',
  `short_title_alternative` varchar(64) DEFAULT NULL COMMENT 'Alternative title shown in UI',
  `long_title` varchar(255) DEFAULT NULL COMMENT 'Long title of the treaty',
  `abstract` text COMMENT 'Another field for description of the treaty',
  `primary` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Is MEA''s primary treaty',
  `year` year(4) DEFAULT NULL COMMENT 'Year when the treaty is entering into force(?)',
  `start` datetime DEFAULT NULL COMMENT 'Used for searching, based on year',
  `url` varchar(255) DEFAULT NULL COMMENT 'Link to treaty website',
  `url_treaty_text` varchar(255) DEFAULT NULL,
  `url_parties` varchar(255) DEFAULT NULL COMMENT 'URL to convention website page with member parties',
  `url_wikipedia` varchar(255) DEFAULT NULL,
  `url_elearning` varchar(255) DEFAULT NULL,
  `number_of_parties` tinyint(2) unsigned DEFAULT NULL COMMENT 'Number of parties that signed the treaty',
  `theme` varchar(64) DEFAULT NULL COMMENT 'Treaty main theme',
  `theme_secondary` varchar(64) DEFAULT NULL COMMENT 'Sub-theme',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Show in front-end, use it to query data etc.',
  `order` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Order as displayed in UI',
  `is_protocol` tinyint(1) unsigned DEFAULT '0',
  `regional` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'This treaty is global (0) or regional instrument (1)',
  `logo_medium` varchar(255) DEFAULT NULL COMMENT 'Treaty Logo',
  `odata_name` varchar(32) NOT NULL COMMENT 'Name as comes from Odata protocol',
  `use_informea` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If TRUE, appear in InforMEA website?',
  `ratification_xml_url` varchar(1000) DEFAULT NULL,
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_id_odata_name` (`odata_name`),
  KEY `idx_id_organization` (`id_organization`),
  KEY `idx_unq_id_organization` (`id_organization`,`primary`),
  KEY `idx_id_theme` (`theme`),
  KEY `idx_is_indexed` (`is_indexed`),
  KEY `idx_comp_id_start` (`id`,`start`),
  KEY `fk_ai_treaty_ai_treaty_parent` (`id_parent`),
  CONSTRAINT `ai_treaty_ibfk_1` FOREIGN KEY (`id_parent`) REFERENCES `ai_treaty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_organization` FOREIGN KEY (`id_organization`) REFERENCES `ai_organization` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Hold the MEA (treaties) information';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty`
--

LOCK TABLES `ai_treaty` WRITE;
/*!40000 ALTER TABLE `ai_treaty` DISABLE KEYS */;
INSERT INTO `ai_treaty` VALUES (1,2,NULL,'TEST','TEST','The Convention on Biological Diversity','Signed by 150 government leaders at the 1992 Rio Earth Summit, the Convention on Biological Diversity is dedicated to promoting sustainable development. Conceived as a practical tool for translating the principles of Agenda 21 into reality, the Convention recognizes that biological diversity is about more than plants, animals and micro organisms and their ecosystems â it is about people and our need for food security, medicines, fresh air and water, shelter, and a clean and healthy environment in which to live. The CBD entered into force on 29 December 1993. It has three main objectives: 1) the conservation of biological diversity; 2) the sustainable use of its components; and 3) the fair and equitable sharing of the benefits arising out of the utilization of genetic resources. The Secretariat of the Convention is located in Montreal, and administered by the United Nations Environment Programme. ',1,1992,'1993-12-23 00:00:00','http://www.cbd.int/convention','http://www.cbd.int/convention/text/default.shtml','http://www.cbd.int/convention/parties/list/default.shtml',NULL,NULL,194,'Biological Diversity','Biological Diversity',1,25,0,0,'images/treaty/logo_cbd.png','test',1,'https://treaties.un.org/doc/Publication/MTDSG/Volume%20II/Chapter%20XXVII/XXVII-8.en.xml','2013-10-22 18:39:31','cristiroma','2014-10-27 07:35:22','cristiroma',1);
/*!40000 ALTER TABLE `ai_treaty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_article`
--

DROP TABLE IF EXISTS `ai_treaty_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique identification of the article. Primary Key',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Link to treaty',
  `order` tinyint(1) unsigned NOT NULL COMMENT 'Order of the article (or number) in the treaty. Article 0 is the preamble',
  `official_order` varchar(255) DEFAULT NULL COMMENT 'Official title of article such as 3.1, 3.2, I, a) etc.',
  `title` varchar(255) DEFAULT NULL COMMENT 'Title of the article',
  `content` text COMMENT 'Article paragraph',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_id_treaty_order` (`id_treaty`,`order`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_is_indexed` (`is_indexed`),
  CONSTRAINT `fk_article_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the articles of a treaty';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_article`
--

LOCK TABLES `ai_treaty_article` WRITE;
/*!40000 ALTER TABLE `ai_treaty_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_article_paragraph`
--

DROP TABLE IF EXISTS `ai_treaty_article_paragraph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_article_paragraph` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Internal ID',
  `id_treaty_article` int(11) unsigned NOT NULL COMMENT 'Article from the treaty',
  `order` tinyint(1) unsigned NOT NULL COMMENT 'Order of the paragraph',
  `official_order` varchar(255) DEFAULT NULL COMMENT 'Paragraph title as appear in the treaty, such as a), i), 3.1) etc.',
  `indent` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Type of paragraph, can be 1, 2, 3 ... Meaning the depth of the paragraph',
  `content` text NOT NULL COMMENT 'Paragraph content',
  `is_indexed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  KEY `idx_id_treaty_article` (`id_treaty_article`),
  KEY `idx_is_indexed` (`is_indexed`),
  CONSTRAINT `fk_treaty_article_paragraph_treaty_article` FOREIGN KEY (`id_treaty_article`) REFERENCES `ai_treaty_article` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the paragraphs of a treaty''s article';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_article_paragraph`
--

LOCK TABLES `ai_treaty_article_paragraph` WRITE;
/*!40000 ALTER TABLE `ai_treaty_article_paragraph` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_article_paragraph` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_article_paragraph_vocabulary`
--

DROP TABLE IF EXISTS `ai_treaty_article_paragraph_vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_article_paragraph_vocabulary` (
  `id_treaty_article_paragraph` int(11) unsigned NOT NULL COMMENT 'ID of the article paragraph',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'ID of concept',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_treaty_article_paragraph`,`id_concept`),
  KEY `idx_id_treaty_article_paragraph` (`id_treaty_article_paragraph`),
  KEY `idx_id_concept` (`id_concept`),
  CONSTRAINT `fk_treaty_article_paragraph_vocabulary_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_article_paragraph_vocabulary_treaty_article_paragraph` FOREIGN KEY (`id_treaty_article_paragraph`) REFERENCES `ai_treaty_article_paragraph` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the keyword tags for paragraphs from a treaty article';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_article_paragraph_vocabulary`
--

LOCK TABLES `ai_treaty_article_paragraph_vocabulary` WRITE;
/*!40000 ALTER TABLE `ai_treaty_article_paragraph_vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_article_paragraph_vocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_article_vocabulary`
--

DROP TABLE IF EXISTS `ai_treaty_article_vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_article_vocabulary` (
  `id_treaty_article` int(11) unsigned NOT NULL COMMENT 'ID of the article',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'Vocabulary term',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_treaty_article`,`id_concept`),
  KEY `idx_id_treaty_article` (`id_treaty_article`),
  KEY `idx_id_concept` (`id_concept`),
  CONSTRAINT `fk_treaty_article_vocabulary_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_article_vocabulary_treaty_article` FOREIGN KEY (`id_treaty_article`) REFERENCES `ai_treaty_article` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the keywords tags for treaty''s articles';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_article_vocabulary`
--

LOCK TABLES `ai_treaty_article_vocabulary` WRITE;
/*!40000 ALTER TABLE `ai_treaty_article_vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_article_vocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_country`
--

DROP TABLE IF EXISTS `ai_treaty_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_country` smallint(2) unsigned NOT NULL COMMENT 'Link to country',
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Link to the MEA',
  `date` date DEFAULT NULL COMMENT 'Date when when relevant event happened. For instance entry into force',
  `status` enum('signature','succesion','ratification','acceptance','approval','accesion','entryIntoForce') DEFAULT NULL COMMENT 'Specify the status of an endorsement date',
  `legal_instrument_name` varchar(255) DEFAULT NULL COMMENT 'Name of the legal instrument (for instance â€œBasel Protocol on Liability and Compensation for Damage Resulting from Transboundary Movements of Hazardous Wastes and their Disposalâ€)',
  `legal_instrument_type` enum('convention','protocol','amendment') DEFAULT NULL COMMENT 'Type of instrument',
  `parent_legal_instrument` varchar(255) DEFAULT NULL COMMENT 'Which legal instrument this one depends on.',
  `declarations` text COMMENT 'A formal declaration made by the country which specifies particular aspects or options chosen',
  `reservations` text,
  `notes` text COMMENT 'An additional note that accompanies the ratifications status. Usually used to specify territorial scope of the legal agreement.',
  `year` int(11) NOT NULL COMMENT '@todo: Deprecated by date - remove when done',
  `signed` int(11) unsigned DEFAULT NULL COMMENT '@todo: Deprecated by date - remove when done',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_treaty_country_unique` (`id_country`,`id_treaty`,`status`),
  KEY `idx_id_country` (`id_country`),
  KEY `idx_id_treaty` (`id_treaty`),
  CONSTRAINT `fk_treaty_country_country` FOREIGN KEY (`id_country`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_country_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The list of countries that adhered to a treaty';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_country`
--

LOCK TABLES `ai_treaty_country` WRITE;
/*!40000 ALTER TABLE `ai_treaty_country` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_parties`
--

DROP TABLE IF EXISTS `ai_treaty_parties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_parties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_created` datetime NOT NULL,
  `rec_author` varchar(32) NOT NULL,
  `rec_updated` datetime DEFAULT NULL,
  `rec_updated_author` varchar(32) NOT NULL,
  `id_country_id` int(11) NOT NULL,
  `id_treaty_id` int(10) unsigned NOT NULL,
  `date` date NOT NULL,
  `status` varchar(32) NOT NULL,
  `declarations` longtext NOT NULL,
  `reservations` longtext NOT NULL,
  `notes` longtext NOT NULL,
  `legal_instrument_name` varchar(128) NOT NULL,
  `legal_instrument_type` varchar(32) NOT NULL,
  `parent_legal_instrument` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ai_treaty_parties_eaf3724d` (`id_country_id`),
  KEY `ai_treaty_parties_e36e919b` (`id_treaty_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_parties`
--

LOCK TABLES `ai_treaty_parties` WRITE;
/*!40000 ALTER TABLE `ai_treaty_parties` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_parties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_ratification`
--

DROP TABLE IF EXISTS `ai_treaty_ratification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_ratification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `treaty_id` int(11) unsigned NOT NULL,
  `country_id` smallint(2) unsigned NOT NULL,
  `date1` date DEFAULT NULL,
  `date2` date DEFAULT NULL,
  `status` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ai_treaty_ratification_eaf3724d` (`country_id`),
  KEY `ai_treaty_ratification_e36e919b` (`treaty_id`),
  CONSTRAINT `fk_treaty_ratification_country` FOREIGN KEY (`country_id`) REFERENCES `ai_country` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_ratification_treaty` FOREIGN KEY (`treaty_id`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Treaty ratification data - Since 2014-04-30';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_ratification`
--

LOCK TABLES `ai_treaty_ratification` WRITE;
/*!40000 ALTER TABLE `ai_treaty_ratification` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_ratification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_region`
--

DROP TABLE IF EXISTS `ai_treaty_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aitreaty_id` int(10) unsigned NOT NULL,
  `airegion_id` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `aitreaty_id` (`aitreaty_id`,`airegion_id`),
  KEY `airegion_id_refs_id_8ce8cd17` (`airegion_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_region`
--

LOCK TABLES `ai_treaty_region` WRITE;
/*!40000 ALTER TABLE `ai_treaty_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_region_old`
--

DROP TABLE IF EXISTS `ai_treaty_region_old`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_region_old` (
  `id_treaty` int(11) unsigned NOT NULL DEFAULT '0',
  `id_region` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_treaty`,`id_region`),
  KEY `fk_treaty_region_region` (`id_region`),
  CONSTRAINT `fk_treaty_region_region` FOREIGN KEY (`id_region`) REFERENCES `ai_region` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_region_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_region_old`
--

LOCK TABLES `ai_treaty_region_old` WRITE;
/*!40000 ALTER TABLE `ai_treaty_region_old` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_region_old` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_treaty_vocabulary`
--

DROP TABLE IF EXISTS `ai_treaty_vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ai_treaty_vocabulary` (
  `id_treaty` int(11) unsigned NOT NULL COMMENT 'Id of the treaty',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'ID of the concept',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_treaty`,`id_concept`),
  KEY `idx_id_treaty` (`id_treaty`),
  KEY `idx_id_concept` (`id_concept`),
  CONSTRAINT `fk_treaty_vocabulary_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_treaty_vocabulary_treaty` FOREIGN KEY (`id_treaty`) REFERENCES `ai_treaty` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hold the keyword tags for treaties';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_treaty_vocabulary`
--

LOCK TABLES `ai_treaty_vocabulary` WRITE;
/*!40000 ALTER TABLE `ai_treaty_vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_treaty_vocabulary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geg_ai_theme`
--

DROP TABLE IF EXISTS `geg_ai_theme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geg_ai_theme` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique theme ID',
  `title` varchar(128) NOT NULL COMMENT 'Theme title',
  `ui_index_label` varchar(64) DEFAULT NULL COMMENT 'Label as shown in index page',
  `image` varchar(129) DEFAULT NULL,
  `image_slider` varchar(129) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_id` (`id`) USING BTREE,
  UNIQUE KEY `idx_title` (`title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geg_ai_theme`
--

LOCK TABLES `geg_ai_theme` WRITE;
/*!40000 ALTER TABLE `geg_ai_theme` DISABLE KEYS */;
/*!40000 ALTER TABLE `geg_ai_theme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `informea_meetings`
--

DROP TABLE IF EXISTS `informea_meetings`;
/*!50001 DROP VIEW IF EXISTS `informea_meetings`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `informea_meetings` (
  `id` tinyint NOT NULL,
  `treaty` tinyint NOT NULL,
  `url` tinyint NOT NULL,
  `start` tinyint NOT NULL,
  `end` tinyint NOT NULL,
  `repetition` tinyint NOT NULL,
  `kind` tinyint NOT NULL,
  `type` tinyint NOT NULL,
  `access` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `imageUrl` tinyint NOT NULL,
  `imageCopyright` tinyint NOT NULL,
  `location` tinyint NOT NULL,
  `city` tinyint NOT NULL,
  `country` tinyint NOT NULL,
  `latitude` tinyint NOT NULL,
  `longitude` tinyint NOT NULL,
  `updated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `informea_meetings_description`
--

DROP TABLE IF EXISTS `informea_meetings_description`;
/*!50001 DROP VIEW IF EXISTS `informea_meetings_description`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `informea_meetings_description` (
  `id` tinyint NOT NULL,
  `meeting_id` tinyint NOT NULL,
  `language` tinyint NOT NULL,
  `description` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `informea_meetings_title`
--

DROP TABLE IF EXISTS `informea_meetings_title`;
/*!50001 DROP VIEW IF EXISTS `informea_meetings_title`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `informea_meetings_title` (
  `id` tinyint NOT NULL,
  `meeting_id` tinyint NOT NULL,
  `language` tinyint NOT NULL,
  `title` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `voc_concept`
--

DROP TABLE IF EXISTS `voc_concept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voc_concept` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID of the term',
  `term` varchar(255) NOT NULL COMMENT 'The term in English language. Must be unique',
  `description` text COMMENT 'Description of the term in English. Optional',
  `reference_url` text COMMENT 'Term definition URL',
  `tag` varchar(250) DEFAULT NULL COMMENT 'Shot description used for tag cloud',
  `id_source` int(11) unsigned NOT NULL COMMENT 'Term source. FK to source table',
  `top_concept` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'If TRUE, this is a top concept in hierarchy (no broader terms)',
  `popularity` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Popularity of the term. How often was accessed.',
  `order` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Themes order in UI',
  `substantive` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Substantive (1) or generic (0)',
  `geg_tools_url` varchar(255) DEFAULT NULL,
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when term was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when term was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_term_id_source` (`term`,`id_source`),
  KEY `idx_id_source` (`id_source`),
  CONSTRAINT `fk_concept_source` FOREIGN KEY (`id_source`) REFERENCES `voc_source` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Terms definitions';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voc_concept`
--

LOCK TABLES `voc_concept` WRITE;
/*!40000 ALTER TABLE `voc_concept` DISABLE KEYS */;
/*!40000 ALTER TABLE `voc_concept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voc_relation`
--

DROP TABLE IF EXISTS `voc_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voc_relation` (
  `id_concept` int(10) unsigned NOT NULL COMMENT 'Base term',
  `target_term` int(10) unsigned NOT NULL COMMENT 'Term that is in relation with the concept',
  `relation` int(10) unsigned NOT NULL COMMENT 'Type of relation',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id_concept`,`target_term`,`relation`),
  KEY `idx_relation` (`relation`),
  KEY `idx_id_concept` (`id_concept`),
  KEY `idx_target_term` (`target_term`),
  CONSTRAINT `fk_relation_concept_destination` FOREIGN KEY (`target_term`) REFERENCES `voc_concept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_relation_concept_source` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_relation_relation_type` FOREIGN KEY (`relation`) REFERENCES `voc_relation_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Define relationships between terms';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voc_relation`
--

LOCK TABLES `voc_relation` WRITE;
/*!40000 ALTER TABLE `voc_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `voc_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voc_relation_type`
--

DROP TABLE IF EXISTS `voc_relation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voc_relation_type` (
  `id` int(10) unsigned NOT NULL COMMENT 'Relation ID',
  `identification` varchar(40) NOT NULL,
  `description` varchar(255) NOT NULL COMMENT 'Describe in english the relation type',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_description` (`description`),
  UNIQUE KEY `idx_identification` (`identification`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Define possible relations between terms';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voc_relation_type`
--

LOCK TABLES `voc_relation_type` WRITE;
/*!40000 ALTER TABLE `voc_relation_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `voc_relation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voc_source`
--

DROP TABLE IF EXISTS `voc_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voc_source` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID of the source',
  `name` varchar(64) NOT NULL COMMENT 'Name of the provider (ex. CBD)',
  `url` varchar(255) NOT NULL COMMENT 'Provider''s website address',
  `rec_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date when record was created',
  `rec_author` varchar(32) DEFAULT NULL COMMENT 'Record author - username',
  `rec_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date when record was updated',
  `rec_updated_author` varchar(32) DEFAULT NULL COMMENT 'Update author - username',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`),
  UNIQUE KEY `idx_unq_url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Keep the source of term. These are providers such as MEAs';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voc_source`
--

LOCK TABLES `voc_source` WRITE;
/*!40000 ALTER TABLE `voc_source` DISABLE KEYS */;
/*!40000 ALTER TABLE `voc_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voc_synonym`
--

DROP TABLE IF EXISTS `voc_synonym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voc_synonym` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary key of this table',
  `id_concept` int(11) unsigned NOT NULL COMMENT 'Term ID, foreign key to voc_concept table',
  `synonym` varchar(64) NOT NULL COMMENT 'Synonym',
  `rec_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rec_author` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unq_id_concept_synonym` (`id_concept`,`synonym`),
  KEY `idx_id_concept` (`id_concept`),
  KEY `synonym` (`synonym`),
  CONSTRAINT `fk_voc_synonym_voc_concept` FOREIGN KEY (`id_concept`) REFERENCES `voc_concept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table keeps the synonyms for terms used in Solr search';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voc_synonym`
--

LOCK TABLES `voc_synonym` WRITE;
/*!40000 ALTER TABLE `voc_synonym` DISABLE KEYS */;
/*!40000 ALTER TABLE `voc_synonym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `informea_meetings`
--

/*!50001 DROP TABLE IF EXISTS `informea_meetings`*/;
/*!50001 DROP VIEW IF EXISTS `informea_meetings`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`informea`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `informea_meetings` AS select `a`.`id` AS `id`,`b`.`odata_name` AS `treaty`,`a`.`event_url` AS `url`,`a`.`start` AS `start`,`a`.`end` AS `end`,`a`.`repetition` AS `repetition`,`a`.`kind` AS `kind`,`a`.`type` AS `type`,`a`.`access` AS `access`,`a`.`status` AS `status`,`a`.`image` AS `imageUrl`,`a`.`image_copyright` AS `imageCopyright`,`a`.`location` AS `location`,`a`.`city` AS `city`,`c`.`code` AS `country`,`a`.`latitude` AS `latitude`,`a`.`longitude` AS `longitude`,`a`.`rec_updated` AS `updated` from ((`ai_event` `a` join `ai_treaty` `b` on(((`a`.`id_treaty` = `b`.`id`) and (`b`.`odata_name` <> 'protocolwaterhealth')))) join `ai_country` `c` on((`a`.`id_country` = `c`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `informea_meetings_description`
--

/*!50001 DROP TABLE IF EXISTS `informea_meetings_description`*/;
/*!50001 DROP VIEW IF EXISTS `informea_meetings_description`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`informea`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `informea_meetings_description` AS select concat(`ai_event`.`id`,'-en') AS `id`,`ai_event`.`id` AS `meeting_id`,'en' AS `language`,`ai_event`.`description` AS `description` from `ai_event` where ((`ai_event`.`description` is not null) and (trim(`ai_event`.`description`) <> '')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `informea_meetings_title`
--

/*!50001 DROP TABLE IF EXISTS `informea_meetings_title`*/;
/*!50001 DROP VIEW IF EXISTS `informea_meetings_title`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`informea`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `informea_meetings_title` AS select concat(`ai_event`.`id`,'-en') AS `id`,`ai_event`.`id` AS `meeting_id`,'en' AS `language`,`ai_event`.`title` AS `title` from `ai_event` where ((`ai_event`.`title` is not null) and (trim(`ai_event`.`title`) <> '')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-10 14:10:00
