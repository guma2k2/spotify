-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: spotify
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` longblob,
  `name` varchar(255) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `thumbnail` longblob,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcu3j9xc5tkron8507kg5n87yo` (`user_id`),
  CONSTRAINT `FKcu3j9xc5tkron8507kg5n87yo` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,NULL,'album1','2023-09-17 21:14:50.523522',NULL,2),(2,NULL,'album2','2023-09-17 21:14:50.523590',NULL,2),(3,NULL,'album4','2023-09-17 21:14:50.523604',NULL,3),(4,NULL,'album5','2023-09-17 21:14:50.523622',NULL,3),(5,NULL,'album6','2023-09-17 21:14:50.523632',NULL,4),(6,NULL,'album7','2023-09-17 21:14:50.523641',NULL,4),(7,NULL,'album8','2023-09-17 21:14:50.523649',NULL,5),(8,NULL,'album9','2023-09-17 21:14:50.523657',NULL,5),(9,NULL,'album10','2023-09-17 21:14:50.523665',NULL,5);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_song`
--

DROP TABLE IF EXISTS `album_song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album_song` (
  `album_id` bigint NOT NULL,
  `song_id` bigint NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`album_id`,`song_id`),
  KEY `FK1631o3hvb3y9ktuxuusnx72v7` (`song_id`),
  CONSTRAINT `FK1631o3hvb3y9ktuxuusnx72v7` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `FK1m0sexh6p6kk409ptssu2kkgy` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_song`
--

LOCK TABLES `album_song` WRITE;
/*!40000 ALTER TABLE `album_song` DISABLE KEYS */;
INSERT INTO `album_song` VALUES (1,1,'2023-09-17 21:14:50.612908'),(1,2,'2023-09-17 21:14:50.612949'),(1,3,'2023-09-17 21:14:50.612960'),(2,4,'2023-09-17 21:14:50.612969'),(2,5,'2023-09-17 21:14:50.612976'),(2,6,'2023-09-17 21:14:50.612984'),(3,7,'2023-09-17 21:14:50.612991'),(3,9,'2023-09-17 21:14:50.612998'),(4,8,'2023-09-17 21:14:50.613012'),(4,10,'2023-09-17 21:14:50.613005'),(5,11,'2023-09-17 21:14:50.613019'),(5,12,'2023-09-17 21:14:50.613025'),(6,13,'2023-09-17 21:14:50.613032'),(6,14,'2023-09-17 21:14:50.613038'),(7,15,'2023-09-17 21:14:50.613052'),(7,16,'2023-09-17 21:14:50.613059'),(8,17,'2023-09-17 21:14:50.613065'),(8,18,'2023-09-17 21:14:50.613072'),(9,19,'2023-09-17 21:14:50.613078'),(9,20,'2023-09-17 21:14:50.613085');
/*!40000 ALTER TABLE `album_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` longblob,
  `thumbnail` longblob,
  `title` varchar(100) DEFAULT NULL,
  `paren_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lnmf77qvjnr2lmyxrrydom9hd` (`title`),
  KEY `FKeudwhy69xeyhrp00kvcfjxwjj` (`paren_id`),
  CONSTRAINT `FKeudwhy69xeyhrp00kvcfjxwjj` FOREIGN KEY (`paren_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,NULL,'HOME',NULL),(2,NULL,NULL,'Danh sách phát trên Spotify',1),(3,NULL,NULL,'TẬP TRUNG MẠNH',1),(4,NULL,NULL,'PodCasts',NULL),(5,NULL,NULL,'Podcast hàng đầu',4),(6,NULL,NULL,'Các câu chuyện',4),(7,NULL,NULL,'BẢNG XẾP HẠNG',NULL),(8,NULL,NULL,'Bảng xếp hạng bài hát hàng tuần',7),(9,NULL,NULL,'Bảng xếp hạng nổi bật',7),(10,NULL,NULL,'KHÁM PHÁ',NULL),(11,NULL,NULL,'Nghỉ ngơi',10),(12,NULL,NULL,'Thư giãn',10),(13,NULL,NULL,'MỚI PHÁT HÀNH',NULL),(14,NULL,NULL,'Bản mới phát hành nhất',13),(15,NULL,NULL,'Album và đĩa đơn mới',13),(16,NULL,NULL,'NHẠC VIỆT',NULL),(17,NULL,NULL,'Diva nhạc Việt',16),(18,NULL,NULL,'Nhạc viện thịnh hành',16),(19,NULL,NULL,'POP',NULL),(20,NULL,NULL,'Chìm đắm trong thế giới Barbie',19),(21,NULL,NULL,'Latin POP',19),(22,NULL,NULL,'Mọi giai điệu nhạc Pop',19),(23,NULL,NULL,'KPOP',NULL),(24,NULL,NULL,'Phim Hàn Quốc và nhiều nội dung khác',23),(25,NULL,NULL,'Kpop không thể thiếu',23),(26,NULL,NULL,'TÂM TRẠNG',NULL),(27,NULL,NULL,'Tâm trạng của bạn hôm này thế nào ?',26),(28,NULL,NULL,'Những giai điệu u sầu nhất thế giói',26),(29,NULL,NULL,'KHÔNG GIAN LÃNG MẠN',NULL),(30,NULL,NULL,'Nhạc trữ tình phổ biến',29),(31,NULL,NULL,'Mùa cưới',29),(32,NULL,NULL,'SỨC KHỎE',NULL),(33,NULL,NULL,'Danh sách phổ biến về sức khỏe',32),(34,NULL,NULL,'Âm nhạc và thiên nhiên',32),(35,NULL,NULL,'ROCK',NULL),(36,NULL,NULL,'Nhịp đập của Rock',35),(37,NULL,NULL,'J-Rock tuần qua',35),(38,NULL,NULL,'LEAGUE OF LEGEND',NULL),(39,NULL,NULL,'Thanh âm của LOL',38),(40,NULL,NULL,'Danh sách phát trò chơi phổ biến',38),(41,NULL,NULL,'TRÊN XE',NULL),(42,NULL,NULL,'Danh sách phát trên xe phổ biến',41),(43,NULL,NULL,'THỊNH HÀNH',NULL),(44,NULL,NULL,'TẬP TRUNG',NULL),(45,NULL,NULL,'MÙA HÈ',NULL),(46,NULL,NULL,'Danh sách phát MÙA HÈ phổ biến',45),(47,NULL,NULL,'CHƠI GAME',NULL),(48,NULL,NULL,'Danh sách phát trò chơi phổ biến vãi ',47),(49,NULL,NULL,'CỔ ĐIỂN',NULL),(50,NULL,NULL,'Danh sách phát nhạc cổ điển nổi bật',49),(51,NULL,NULL,'DU LỊCH',NULL),(52,NULL,NULL,'Danh sách phát du lịch phổ biến',51),(53,NULL,NULL,'ANIME',NULL),(54,NULL,NULL,'Danh sách phát Anime phổ biến',53),(55,NULL,NULL,'NHẠC KHÔNG LỜI',NULL),(56,NULL,NULL,'Nhạc không lời giúp dễ ngủ',55),(57,NULL,NULL,'Danh sách nhạc ru ngủ phổ biến',55),(58,NULL,NULL,'HIP HOP',NULL),(59,NULL,NULL,'Danh sách nhạc HipHop phổ biến',58),(60,NULL,NULL,'Chất liệu cổ điển',58);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_playlist`
--

DROP TABLE IF EXISTS `category_playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_playlist` (
  `category_id` int NOT NULL,
  `playlist_id` bigint NOT NULL,
  PRIMARY KEY (`category_id`,`playlist_id`),
  KEY `FKtpylpxfpdarni925bi05drts8` (`playlist_id`),
  CONSTRAINT `FK5x1cxmpwlvemhqn9cf5tkn5r1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKtpylpxfpdarni925bi05drts8` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_playlist`
--

LOCK TABLES `category_playlist` WRITE;
/*!40000 ALTER TABLE `category_playlist` DISABLE KEYS */;
INSERT INTO `category_playlist` VALUES (2,1),(3,2),(2,3),(3,4),(2,5),(2,6),(2,7),(2,8),(5,9),(5,10),(6,11),(6,12),(9,13),(9,14),(8,15),(8,16),(14,17),(14,18),(15,19),(15,20),(11,21),(11,22),(12,23),(12,24),(18,25),(18,26);
/*!40000 ALTER TABLE `category_playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `image` longblob,
  `name` varchar(30) DEFAULT NULL,
  `thumbnail` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (1,'playlist_1_Child_1_Desc',NULL,'playlist1Child1',NULL),(2,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(3,'playlist_2_Child_1_Desc',NULL,'playlist2Child1',NULL),(4,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(5,'playlist_1_Child_1_Desc',NULL,'playlist1Child1',NULL),(6,'playlist_2_Child_1_Desc',NULL,'playlist2Child1',NULL),(7,'playlist_1_Child_1_Desc',NULL,'playlist1Child1',NULL),(8,'playlist_2_Child_1_Desc',NULL,'playlist2Child1',NULL),(9,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(10,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(11,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(12,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(13,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(14,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(15,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(16,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(17,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(18,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(19,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(20,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(21,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(22,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(23,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(24,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL),(25,'playlist_1_Child_2_Desc',NULL,'playlist1Child2',NULL),(26,'playlist_2_Child_2_Desc',NULL,'playlist2Child2',NULL);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_song`
--

DROP TABLE IF EXISTS `playlist_song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist_song` (
  `playlist_id` bigint NOT NULL,
  `song_id` bigint NOT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`playlist_id`,`song_id`),
  KEY `FK8l4jevlmxwsdm3ppymxm56gh2` (`song_id`),
  CONSTRAINT `FK8l4jevlmxwsdm3ppymxm56gh2` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `FKji5gt6i2hcwyt9x1fcfndclva` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_song`
--

LOCK TABLES `playlist_song` WRITE;
/*!40000 ALTER TABLE `playlist_song` DISABLE KEYS */;
INSERT INTO `playlist_song` VALUES (1,1,'2023-09-17 21:15:12.387653'),(1,2,'2023-09-17 21:15:12.387687'),(2,3,'2023-09-17 21:15:12.387705'),(2,4,'2023-09-17 21:15:12.387710'),(3,5,'2023-09-17 21:15:12.387694'),(3,6,'2023-09-17 21:15:12.387700'),(4,7,'2023-09-17 21:15:12.387716'),(4,8,'2023-09-17 21:15:12.387721');
/*!40000 ALTER TABLE `playlist_song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_user`
--

DROP TABLE IF EXISTS `playlist_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) DEFAULT NULL,
  `playlist_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt2uh0hf0hqh8ta2tyqsxfia11` (`playlist_id`),
  KEY `FKa95po8gb8eehss7tjcv56pbro` (`user_id`),
  CONSTRAINT `FKa95po8gb8eehss7tjcv56pbro` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt2uh0hf0hqh8ta2tyqsxfia11` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_user`
--

LOCK TABLES `playlist_user` WRITE;
/*!40000 ALTER TABLE `playlist_user` DISABLE KEYS */;
INSERT INTO `playlist_user` VALUES (3,'2023-09-17 21:58:43.133651',1,1);
/*!40000 ALTER TABLE `playlist_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'ROLE_ARTIST'),(1,'ROLE_CUSTOMER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `song` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `audio` varchar(255) DEFAULT NULL,
  `duration` int NOT NULL,
  `genre` enum('CLASSICAL','Hip_Hop','POP','Rock') DEFAULT NULL,
  `image` longblob,
  `lyric` varchar(1000) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `view_count` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gsbhuqas8496b2f9ui482pc01` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
INSERT INTO `song` VALUES (1,NULL,282,'POP',NULL,'lyric','song10','2023-09-17 21:14:30.706424',0),(2,NULL,282,'Rock',NULL,'lyric','song4','2023-09-17 21:14:30.706389',0),(3,NULL,282,'CLASSICAL',NULL,'lyric','song5','2023-09-17 21:14:30.706396',0),(4,NULL,282,'Hip_Hop',NULL,'lyric','song3','2023-09-17 21:14:30.706379',0),(5,NULL,282,'POP',NULL,'lyric','song6','2023-09-17 21:14:30.706401',0),(6,NULL,282,'Hip_Hop',NULL,'lyric','song7','2023-09-17 21:14:30.706407',0),(7,NULL,282,'CLASSICAL',NULL,'lyric','song9','2023-09-17 21:14:30.706419',0),(8,NULL,282,'Rock',NULL,'lyric','song8','2023-09-17 21:14:30.706412',0),(9,NULL,281,'POP',NULL,'lyric','song1','2023-09-17 21:14:30.706323',0),(10,NULL,282,'CLASSICAL',NULL,'lyric','song2','2023-09-17 21:14:30.706369',0),(11,NULL,281,'POP',NULL,'lyric','song11','2023-09-17 21:14:30.706430',0),(12,NULL,282,'CLASSICAL',NULL,'lyric','song12','2023-09-17 21:14:30.706436',0),(13,NULL,282,'Rock',NULL,'lyric','song14','2023-09-17 21:14:30.706451',0),(14,NULL,282,'Hip_Hop',NULL,'lyric','song13','2023-09-17 21:14:30.706445',0),(15,NULL,282,'Rock',NULL,'lyric','song18','2023-09-17 21:14:30.706473',0),(16,NULL,282,'CLASSICAL',NULL,'lyric','song15','2023-09-17 21:14:30.706456',0),(17,NULL,282,'POP',NULL,'lyric','song16','2023-09-17 21:14:30.706462',0),(18,NULL,282,'CLASSICAL',NULL,'lyric','song19','2023-09-17 21:14:30.706478',0),(19,NULL,282,'Hip_Hop',NULL,'lyric','song17','2023-09-17 21:14:30.706467',0),(20,NULL,282,'POP',NULL,'lyric','song20','2023-09-17 21:14:30.706484',0);
/*!40000 ALTER TABLE `song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(20) NOT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  `last_name` varchar(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photo` longblob,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKcg8vm2yga4tm8kvsid9aqkt55` (`role_id`),
  CONSTRAINT `FKcg8vm2yga4tm8kvsid9aqkt55` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'vinh@gmail.com','vinh','MALE','vinh','$2a$10$0cwCsqTK9LNFGchP0VlzfupjmP/bB/s15i0ktM0mglsdaOr6c2KIS',NULL,1),(2,'phiphi@gmail.com','phi','MALE','phi','$2a$10$pB1Ur7.DAOqZawdbcmrlIOOiT9L4yIGAEA3owDUE4KJqTgI3JFpXq',NULL,2),(3,'vinhh@gmail.com','vinhpro','MALE','vinh','$2a$10$V7B6XWHGKxqdwYY3G9tDOO83EHFtghVhE4XZozlA3qkm95kakOJYe',NULL,2),(4,'phii@gmail.com','phii','MALE','phii','$2a$10$aL1pIrQ22wtpu3cEVVn0Aey63FoR8foMN9jgf3pCSLnmB5K.75QWi',NULL,2),(5,'thuan@gmail.com','thuan','MALE','thuan','$2a$10$CKqg2giC.mPXesfbPyTJ0OGEi.ZZUY7wKAMUKXmdKoa53thGX/qR6',NULL,2),(6,'phiphi1@gmail.com','phiiii','MALE','phiiiii','$2a$10$1fCaqYKRhy9BMz3RnhLtwuzkPut69JraYxCVCfxOl/XOZWUyuItxK',NULL,2),(7,'thuan1@gmail.com','thuann','MALE','thuann','$2a$10$echzd4Xs1ei/H/rDAZPD6uV7Tz4Ys8TtuMDBc4XYDxtpEB5iNyaBu',NULL,2),(8,'vinh1@gmail.com','vinhh','MALE','vinhh','$2a$10$o2jqDLFo2hqLOpsRl1XrlOkhjpvN6mwhpCdi0/YxpXHOpqg0ZMIHu',NULL,2),(9,'vinhh2@gmail.com','avinhpro','MALE','vinh','$2a$10$Vuqf4DQhXZNqyYFM0LvP1Oi8vBV12NQ.T1MIdUfsT6qiM.q5JKw96',NULL,2),(10,'phii2@gmail.com','phiiiiii','MALE','phii','$2a$10$C6NQO7fMN6mVlNnCCPaNdepnQo2xUPk1pWCgvMfDvNI94ylBqFmXG',NULL,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_song`
--

DROP TABLE IF EXISTS `user_song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_song` (
  `user_id` bigint NOT NULL,
  `song_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`song_id`),
  KEY `FK7n6l7aiyapjhlsihmcgias0i9` (`song_id`),
  CONSTRAINT `FK7n6l7aiyapjhlsihmcgias0i9` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `FKkka153146crhbp0bie5cduo37` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_song`
--

LOCK TABLES `user_song` WRITE;
/*!40000 ALTER TABLE `user_song` DISABLE KEYS */;
INSERT INTO `user_song` VALUES (1,1),(2,2),(2,3),(2,4),(3,5),(3,6),(4,7),(4,8),(5,9),(5,10),(6,11),(6,12),(7,13),(7,14),(8,15),(8,16),(9,17),(9,18),(10,19),(10,20);
/*!40000 ALTER TABLE `user_song` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-17 22:01:20
