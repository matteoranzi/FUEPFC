-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: fuepfc
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.18.04.2

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
-- Table structure for table `games_data`
--

DROP TABLE IF EXISTS `games_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_data` (
  `version_uuid` varchar(40) NOT NULL,
  `game_uuid` varchar(40) NOT NULL,
  `game_name` varchar(30) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`version_uuid`),
  UNIQUE KEY `games_data_name_uindex` (`game_uuid`),
  UNIQUE KEY `games_data_name_uindex_2` (`game_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games_data`
--

LOCK TABLES `games_data` WRITE;
/*!40000 ALTER TABLE `games_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `games_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_data`
--

DROP TABLE IF EXISTS `users_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_data` (
  `uuid` varchar(40) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `crypted_password` varchar(100) NOT NULL,
  `password_salt` varchar(30) NOT NULL,
  `registration_date` datetime NOT NULL,
  `session_key` varchar(40) DEFAULT NULL,
  `session_expiration_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `users_uuid_uindex` (`uuid`),
  UNIQUE KEY `users_username_uindex` (`username`),
  UNIQUE KEY `users_data_session_key_uindex` (`session_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_data`
--

LOCK TABLES `users_data` WRITE;
/*!40000 ALTER TABLE `users_data` DISABLE KEYS */;
INSERT INTO `users_data` VALUES ('8194f7c6-fdb5-4828-8295-2ddc0d73159d','Francesco','Valcanover','francescovalcanover','francescovalcanover@gmail.com','$2a$10$Z2jIslDmQnvrbdN4.vQMz.Fi9WCs5JdJAg02zQjHyCgxL0Wg6AUfC','$2a$10$Z2jIslDmQnvrbdN4.vQMz.','2019-03-18 11:30:02','bae18431-f596-4bf2-8686-34c49ee5598a','2019-03-18 12:30:11'),('ce12bab5-14d6-4c68-8cb4-529aa70b4d5e','Matteo','Ranzi','matteoranzi','matteoranzi2000@gmail.com','$2a$10$mwhpOrGP2q98aKNY1iz2x.t0po9LyKk.XwgsG9mRO9SGTn6Hbsm9m','$2a$10$mwhpOrGP2q98aKNY1iz2x.','2019-03-11 22:30:09','7894c4d1-d983-47b7-a794-d1385361ce98','2019-03-18 18:06:14');
/*!40000 ALTER TABLE `users_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_scores`
--

DROP TABLE IF EXISTS `users_scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_scores` (
  `user_uuid` varchar(40) NOT NULL,
  `game_uuid` varchar(40) NOT NULL,
  `total_scores` int(11) DEFAULT NULL,
  `best_score` int(11) DEFAULT NULL,
  `total_winning` int(11) DEFAULT NULL,
  PRIMARY KEY (`game_uuid`,`user_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_scores`
--

LOCK TABLES `users_scores` WRITE;
/*!40000 ALTER TABLE `users_scores` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_session`
--

DROP TABLE IF EXISTS `users_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_session` (
  `uuid_user` varchar(40) NOT NULL,
  `uuid_game` varchar(40) NOT NULL,
  `uuid_session_key` varchar(40) NOT NULL,
  PRIMARY KEY (`uuid_user`,`uuid_game`),
  UNIQUE KEY `user_session_uuid_game_uindex` (`uuid_game`),
  UNIQUE KEY `user_session_uuid_user_uindex` (`uuid_user`),
  UNIQUE KEY `users_session_uuid_session_key_uindex` (`uuid_session_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_session`
--

LOCK TABLES `users_session` WRITE;
/*!40000 ALTER TABLE `users_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_session` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-18 22:28:29
