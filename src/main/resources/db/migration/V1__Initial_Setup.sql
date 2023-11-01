
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
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `thumbnail` longblob,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcu3j9xc5tkron8507kg5n87yo` (`user_id`),
  CONSTRAINT `FKcu3j9xc5tkron8507kg5n87yo` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (7,'lalung.jpeg','Lạ Lùng','2023-09-30 21:12:08.874646',NULL,18),(9,'vianhdaucobiet.jpeg','Vì anh đâu có biết ','2023-09-30 21:18:37.096549',NULL,18),(11,'buocquamuacodon.jpeg','Bước Qua Mùa Cô Đơn','2023-09-30 22:17:02.525401',NULL,18),(13,'buocquanhau.jpeg','Bước Qua Nhau','2023-09-30 22:25:13.257391',NULL,18),(14,'phutbandau.jpeg','Phút Ban Đầu','2023-09-30 22:27:30.524735',NULL,18),(15,'dongkiemem.jpeg','Đông Kiếm Em','2023-09-30 22:29:22.038163',NULL,18),(16,'buctranhtunuocmat.jpeg','Bức Tranh Từ Nước Mắt','2023-10-01 07:10:00.873818',NULL,28),(17,'chicomotngdeyeu.jpeg','Chỉ Có Một Người Để Yêu Trên Thế Gian','2023-10-01 07:12:47.670723',NULL,28),(18,'daydutnoidau.jpeg','Day Dứt Nỗi Đau','2023-10-01 07:15:19.431256',NULL,28),(19,'motbuocyeuvandamdau.jpeg','Một bước yêu, vạn dặm đau','2023-10-01 07:17:09.321696',NULL,28),(20,'duoinhungconmua.jpeg','Dưới những cơn mưa','2023-10-01 07:18:47.868464',NULL,28),(21,'chamdaynoidau.jpeg','Chạm Đáy Nỗi Đau','2023-10-01 07:42:47.898548',NULL,16),(22,'emkosaichungtasai.jpeg','Em Không Sai, Chúng Ta Sai','2023-10-01 07:44:56.713921',NULL,16),(23,'yeuthuonglabaoto.jpeg','Yêu Thương Là Bão Tố','2023-10-01 07:51:14.957700',NULL,29),(24,'motvannam.jpeg','Một Vạn Năm','2023-10-01 07:57:49.180285',NULL,18),(25,'nhuonglaiem.jpeg','Nhường Lại Em (feat. Phúc Du)','2023-10-01 08:04:02.317484',NULL,18),(26,'cota.jpeg','Cô Ta','2023-10-01 08:06:55.093686',NULL,18),(27,'vutrusongsong.jpeg','Vũ Trụ Song Song','2023-10-01 08:10:56.954951',NULL,18),(28,'conlaigi.jpeg','Còn Lại Gì?','2023-10-01 08:13:41.607394',NULL,18),(29,'cothelataisao.jpeg','Có Thể Là Tại Sao?','2023-10-01 08:19:05.600429',NULL,18),(30,'doai.jpeg','Do Ai?','2023-10-01 08:19:05.600429',NULL,18),(31,'muonroimasaocon.jpeg','Muộn Rồi Mà Sao Còn','2023-10-01 08:21:34.266089',NULL,6);
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
INSERT INTO `album_song` VALUES (7,2,'2023-09-30 21:39:30.902197'),(9,1,'2023-09-30 21:37:56.970505'),(11,4,'2023-09-30 21:37:56.970505'),(13,5,'2023-09-30 22:25:13.314099'),(14,6,'2023-09-30 22:27:30.557503'),(15,7,'2023-09-30 22:29:22.057012'),(16,8,'2023-10-01 07:10:00.927593'),(17,9,'2023-10-01 07:12:47.705326'),(18,10,'2023-10-01 07:15:19.454734'),(19,11,'2023-10-01 07:17:09.342207'),(20,12,'2023-10-01 07:18:47.893039'),(21,13,'2023-10-01 07:42:47.931170'),(22,14,'2023-10-01 07:44:56.739053'),(23,15,'2023-10-01 07:51:14.987608'),(24,4,'2023-10-01 07:58:37.847275'),(24,16,'2023-10-01 08:07:15.879766'),(24,17,'2023-10-01 08:07:17.846507'),(25,16,'2023-10-01 08:04:02.362415'),(26,17,'2023-10-01 08:06:55.118353'),(28,18,'2023-10-01 08:13:41.628751'),(29,19,'2023-10-01 08:16:07.960752'),(30,20,'2023-10-01 08:19:05.632395'),(31,21,'2023-10-01 08:21:34.292737');
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
  `image` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `paren_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeudwhy69xeyhrp00kvcfjxwjj` (`paren_id`),
  CONSTRAINT `FKeudwhy69xeyhrp00kvcfjxwjj` FOREIGN KEY (`paren_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'ea364e99656e46a096ea1df50f581efe.png','Bred.jpg','Dành cho bạn',NULL),(2,'topsongs.jpeg','Bviolet.jpeg','Bảng xếp hạng',NULL),(3,'newRelease.jpeg','Bpink.jpeg','Mới phát hành',NULL),(4,'discover.jpeg','Bviolet.jpeg','Khám phá',NULL),(5,'viet.jpeg','Bgreen.jpeg','Nhạc Việt ',NULL),(6,'kpop.jpeg','Byellow.jpeg','K-Pop',NULL),(7,'pop.jpeg','Bgreen.jpeg','Pop',NULL),(8,'tamtrang.jpeg','Bhealth.png','Tâm trạng',NULL),(9,'romantic.jpeg','BBlue.jpeg','Không gian lãng mạn',NULL),(10,'health.jpeg','BblackEase.png','Sức khỏe',NULL),(11,'hiphop.jpeg','Bromantic.png','Hip hop',NULL),(12,'relax.jpeg','Bgreen.jpeg','Thư giãn',NULL),(13,'focus.jpeg','BblackEase.png','Tập trung ',NULL),(14,'sleep.jpeg','Bleep.png','Ngủ ngon',NULL),(15,NULL,NULL,'Danh sách phát trên Spotify',1),(16,'focus.jpeg','BblackEase.png','Tập trung',1),(17,'topsongs.jpeg','Bviolet.jpeg','Bảng xếp hạng Nổi bật',2),(18,NULL,NULL,'Bản Mới Phát Hành Hay nhất',3),(19,NULL,NULL,'Nhạc Việt thịnh hành',5),(20,NULL,NULL,'Diva nhạc Việt',5),(21,NULL,NULL,'Những đóa hồng nhạc Việt',5),(22,NULL,NULL,'Kpop không thể thiếu ',6),(23,NULL,NULL,'Phim Hàn Quốc và nhiều nội dung khác',6),(24,NULL,NULL,'Tâm trạng của bạn hôm nay thế nào?',8),(25,NULL,NULL,'Những giai điệu u sầu nhất thế giới',8),(26,'sad.jpeg','Bviolet.jpeg','Buồn ',NULL),(27,NULL,NULL,'Nhạc Buồn ',26),(28,NULL,NULL,'Không kìm được nước mắt ?',26),(29,NULL,NULL,'Nhạc buồn không lời',26),(30,NULL,NULL,'Dành cho những trái tim tan vỡ ?',26),(31,'happy.jpeg','Bpop.png','Vui vẻ',NULL),(32,NULL,NULL,'Nhạc vui',31);
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
INSERT INTO `category_playlist` VALUES (15,1),(15,2),(15,3),(15,4),(15,5),(16,6),(16,7),(16,8),(16,9),(16,10),(17,11),(17,12),(17,13),(18,14),(19,15),(19,16),(19,17),(20,18),(21,19),(22,20),(23,21),(24,22),(24,23),(24,24),(25,25),(25,26),(27,27),(30,28),(30,29),(28,30),(28,31),(27,32),(27,33),(27,34),(32,35),(32,36);
/*!40000 ALTER TABLE `category_playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `django_migrations`
--

DROP TABLE IF EXISTS `django_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `django_migrations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `django_migrations`
--

LOCK TABLES `django_migrations` WRITE;
/*!40000 ALTER TABLE `django_migrations` DISABLE KEYS */;
INSERT INTO `django_migrations` VALUES (1,'SentimentApp','0001_initial','2023-10-22 01:53:37.292280'),(2,'SentimentApp','0002_rename_sentiment_sentiment_sentiment','2023-10-22 01:53:37.347137'),(3,'contenttypes','0001_initial','2023-10-22 01:54:33.107165'),(4,'auth','0001_initial','2023-10-22 01:54:33.472438'),(5,'admin','0001_initial','2023-10-22 01:54:33.568421'),(6,'admin','0002_logentry_remove_auto_add','2023-10-22 01:54:33.579927'),(7,'admin','0003_logentry_add_action_flag_choices','2023-10-22 01:54:33.589659'),(8,'contenttypes','0002_remove_content_type_name','2023-10-22 01:54:33.656156'),(9,'auth','0002_alter_permission_name_max_length','2023-10-22 01:54:33.700560'),(10,'auth','0003_alter_user_email_max_length','2023-10-22 01:54:33.725264'),(11,'auth','0004_alter_user_username_opts','2023-10-22 01:54:33.734716'),(12,'auth','0005_alter_user_last_login_null','2023-10-22 01:54:33.786613'),(13,'auth','0006_require_contenttypes_0002','2023-10-22 01:54:33.790236'),(14,'auth','0007_alter_validators_add_error_messages','2023-10-22 01:54:33.799000'),(15,'auth','0008_alter_user_username_max_length','2023-10-22 01:54:33.860479'),(16,'auth','0009_alter_user_last_name_max_length','2023-10-22 01:54:33.908894'),(17,'auth','0010_alter_group_name_max_length','2023-10-22 01:54:33.934511'),(18,'auth','0011_update_proxy_permissions','2023-10-22 01:54:33.951044'),(19,'auth','0012_alter_user_first_name_max_length','2023-10-22 01:54:33.994656'),(20,'sessions','0001_initial','2023-10-22 01:54:34.019005');
/*!40000 ALTER TABLE `django_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`

DROP TABLE IF EXISTS `follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follower` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `followed_uer_id` bigint DEFAULT NULL,
  `following_uer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd7l25pq8rea9pmpdqkfqfomvr` (`followed_uer_id`),
  KEY `FK2r3fhhcxrndln7lxxv6fqkyy9` (`following_uer_id`),
  CONSTRAINT `FK2r3fhhcxrndln7lxxv6fqkyy9` FOREIGN KEY (`following_uer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKd7l25pq8rea9pmpdqkfqfomvr` FOREIGN KEY (`followed_uer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follower`
--

LOCK TABLES `follower` WRITE;
/*!40000 ALTER TABLE `follower` DISABLE KEYS */;
INSERT INTO `follower` VALUES (1,2,1),(2,3,1),(8,5,1),(9,1,6);
/*!40000 ALTER TABLE `follower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(300) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES (1,'Zach Bryan & Kacey Musgraves are on top of the Hottest 50!','tophits.jpeg','Today\'s Top Hits',NULL),(2,'New music from Doja Cat, Lil Tecca and Moneybagg Yo.','racaviar.jpeg','RapCaviar',NULL),(3,'The biggest songs of the 2010s.','alloutof2010.jpeg','All Out 2010s',NULL),(4,'Rock legends & epic songs that continue to inspire generations. Cover: Foo Fighters','rockClassic.jpeg','Rock Classics',NULL),(5,'Kick back to the best new and recent chill hits.','ChillHits.jpeg','Chill Hits',NULL),(6,'Peaceful piano to help you slow down, breathe, and relax.','Peaceful.jpeg','Peaceful Piano',NULL),(7,'Keep calm and focus with ambient and post-rock music.','DeepFocus.jpeg','Deep Focus',NULL),(8,'Focus with soft study music in the background','Instrumental Study.jpeg','Instrumental Study',NULL),(9,'Uptempo instrumental hip hop beats.','Focus Flow.jpeg','Focus Flow',NULL),(10,'Focus with melodic techno and tech house.','Beats to think to.jpeg','Beats to think to',NULL),(11,'Thông tin cập nhật hằng tuần về những bản nhạc được nghe nhiều nhất hiện nay tại Toàn Cầu.','baihathangdau.jpeg','Bài hát hàng đầu tại Toàn Cầu',NULL),(12,'Thông tin cập nhật hằng tuần về những bản nhạc được nghe nhiều nhất hiện nay tại Hoa Kỳ.','Topusa.jpeg','Bài hát hàng đầu tại Hoa Kỳ','Topusa.jpeg'),(13,'Thông tin cập nhật hằng ngày về những bản nhạc được nghe nhiều nhất hiện nay tại Toàn Cầu.','modify.jpeg','50 bài hát hàng đầu tại Toàn Cầu',NULL),(14,'Những chiếc nhạc mới trong tuần, được tuyển chọn cẩn thận. Ảnh bìa: Orange','friday.jpeg','New Music Friday Vietnam',NULL),(15,'Những gì mà người bên cạnh bạn đang nghe. Ảnh bìa: Phương Mỹ Chi','thienhagnhegi.jpeg','Thiên Hạ Nghe Gì',NULL),(16,'V-Pop nở hoa trên những ca khúc này. Ảnh bìa: Orange','VpopNotneed.jpeg','V-Pop Không Thể Thiếu',NULL),(17,'Quán quen, nhạc cũ, cảm giác lạ.','cafe.jpeg','Cà Phê Quán Quen',NULL),(18,'Hồng Nhung','HongNhung.jpeg','Hồng Nhung Và Những Bài Top Ten',NULL),(19,'Một nửa mềm mại của làng nhạc Việt. Ảnh bìa: Văn Mai Hương','doahong.jpeg','Đóa Hồng Nhạc Việt',NULL),(20,'Turn on the movement with the latest and greatest in K-Pop! Cover: D.O','khothethieu.webp','K-Pop On',NULL),(21,'Hot & new K-Drama OSTs are here! (Cover: My Lovely Boxer)','koreanOsts.jpeg','Korean OSTs',NULL),(22,'Những ? lãng đãng khó phai.','lofithatlauphai.jpeg','lofi thật lâu phai','lofithatlauphai.jpeg'),(23,'Điều buồn nhất là anh biết lại làm như không biết... Ảnh bìa: JSOL, Hoàng Duyên','dieubuonnha.jpeg','Điều Buồn Nhất',NULL),(24,'Giọt mưa trên cửa kính gợi ký ức về một thời đã xa. Ảnh bìa: Văn Mai Hương, GREY D','phutcodonngaymua.jpeg','Phút Cô Đơn Ngày Mưa',NULL),(25,'Songs for a broken heart','sadSongs.jpeg','Sad Songs',NULL),(26,'For all of us dancing on our own.','sadbops.jpeg','Sad Bops',NULL),(27,'đừng buồn nửa nhé, yeu cauu~','buonthinghe.jpeg','Buồn thì nghe',NULL),(28,'Songs about missing that someone special and wanting everything to be as before.','imissu.jpeg','I Miss You',NULL),(29,'Indie pop when ur crushing','textmeback.jpeg','text me back',NULL),(30,'Those quintessential sad songs that everyone knows.','classicforcrying.jpeg','Classics For Crying',NULL),(31,'time for a good cry','imnotcrying.jpeg','i\'m not crying. you are.',NULL),(32,'cảm ơn đã nghe list nhạc của mikkkk','buonlamgi.jpeg','Buon lam gi?',NULL),(33,'buon lam nghe di roi khoc them skr skr','buonthicukhocdi.jpeg','buon thi cu khoc di',NULL),(34,'Anh không thể nói được vì sao mình yêu em, nhưng anh biết rõ rằng, em chính là lý do mà anh không yêu người khác.','buonthinghe.jpeg','buon thi nghe:(',NULL),(35,'vui thì vui, không vui thì cũng phải vui :D','playlistvuive.jpeg','một playlist vui vẻ',NULL),(36,'vui hihi ','nhacvietvui.jpeg','Nhạc việt vui tươi, trẻ trung ?',NULL),(37,NULL,NULL,'Liked Songs',NULL),(38,NULL,NULL,'Liked Songs',NULL),(39,NULL,NULL,'Liked Songs',NULL),(40,NULL,NULL,'Liked Songs',NULL);
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
INSERT INTO `playlist_song` VALUES (1,1,'2023-09-27 11:16:00.479276'),(1,2,'2023-10-01 07:52:43.347561'),(1,4,'2023-10-01 07:52:45.836344'),(1,6,'2023-10-01 07:52:47.440202'),(1,7,'2023-10-01 07:52:49.576948'),(1,11,'2023-10-01 07:52:52.642690'),(27,4,'2023-10-01 07:53:06.940617'),(27,10,'2023-10-01 07:53:10.389940'),(27,14,'2023-10-01 07:53:17.191087'),(27,15,'2023-10-01 07:53:14.099487'),(37,2,'2023-10-06 21:54:46.995791');
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
  `playlist_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt2uh0hf0hqh8ta2tyqsxfia11` (`playlist_id`),
  KEY `FKntk9vrue8e161mif4ivlc03vh` (`user_id`),
  CONSTRAINT `FKntk9vrue8e161mif4ivlc03vh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt2uh0hf0hqh8ta2tyqsxfia11` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_user`
--

LOCK TABLES `playlist_user` WRITE;
/*!40000 ALTER TABLE `playlist_user` DISABLE KEYS */;
INSERT INTO `playlist_user` VALUES (2,'2023-09-28 12:02:45.809677',1,1),(3,'2023-10-01 07:04:54.970816',37,28),(4,'2023-10-01 07:47:50.885404',38,29),(5,'2023-10-01 07:59:56.672875',39,30),(6,'2023-09-28 12:02:45.809677',40,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_CUSTOMER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SentimentApp_sentiment`
--

DROP TABLE IF EXISTS `SentimentApp_sentiment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SentimentApp_sentiment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL,
  `sentiment` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SentimentApp_sentiment`
--

LOCK TABLES `SentimentApp_sentiment` WRITE;
/*!40000 ALTER TABLE `SentimentApp_sentiment` DISABLE KEYS */;
INSERT INTO `SentimentApp_sentiment` VALUES (1,'buồn','sad'),(2,'cảm xúc vui','happy'),(3,'vui','happy'),(4,'cảm xúc buồn','sad'),(5,'Âm nhạc bùng nổ','energetic'),(6,'vui nhộn','happy'),(7,'sống động','happy'),(8,'hạnh phúc','happy'),(11,' đau buồn','sad'),(12,'đầy niềm vu','happy'),(14,'u sầu','sad'),(15,'xúc động','sad'),(16,'phấn khích','happy'),(17,' lặng lẽ','sad'),(18,'buồn đêm mưa','sad'),(19,'đầy nỗi buồn','sad'),(20,'nhạc u buồn','sad'),(21,'nhạc thư giãn','relax'),(22,'dễ chịu','relax'),(23,'hư thái','relax'),(25,'Nhạc nền thư giã','relax'),(26,'tươi sáng','happy'),(27,'đáng yêu','happy'),(28,'nhạc sôi động','happy'),(31,'nhạc buồn da diết','sad'),(35,'nhạc suy ','sad'),(38,'Ca khúc yên bình','relax'),(39,'Bản nhạc dễ ngủ','relax'),(40,'Nhạc nhẹ nhàng','relax'),(41,'Nhạc dễ thư giãn tâm trí','relax'),(42,'Ca khúc du dương','relax'),(43,'Bài hát dễ ngồi yên và thư giãn','relax'),(44,'Nhạc đầy năng lượng','energetic');
/*!40000 ALTER TABLE `SentimentApp_sentiment` ENABLE KEYS */;
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
  `duration` int DEFAULT NULL,
  `genre` enum('CLASSICAL','Hip_Hop','POP','Rock') DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `lyric` varchar(5000) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `view_count` bigint DEFAULT NULL,
  `label` varchar(30) DEFAULT NULL,
  `status` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gsbhuqas8496b2f9ui482pc01` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
INSERT INTO `song` VALUES (1,'ViAnhDauCoBiet-ThaoChi_3h8te.mp3',241,'CLASSICAL','vianhdaucobiet.jpeg','<p>Vì anh đâu có biết trái tim ngổn ngang</p><p>Vì anh đâu có biết đúng sai ngỡ ngàng</p><p>Vì anh luôn hối tiếc chiếc ôm dở dang</p><p>Chìm trong đôi mắt biếc anh không thể mang</p><p>Vì anh đâu có biết giấu đi thời gian</p><p>Vì anh đâu có biết nắng mai đang tàn</p><p>Vì anh luôn hối tiếc anh không thể mang</p><p>Mùi hương trên mái tóc giữ theo câu chuyện</p><p>Đánh rơi loay hoay lạc trong từng cơn mưa</p><p>Ngu ngơ bước theo nơi đôi bờ mi đón đưa</p><p>Bâng khuâng chìm trong làn mây thưa</p><p>Em ơi có rơi vào nhạt nhoà hay chưa</p><p>Hay khi ngày em vô tình rơi</p><p>Chơi vơi cuốn theo con tim này đi khắp nơi</p><p>Ngân nga lời ca còn buông lơi</p><p>Mang theo đắm say ngày nào còn trên môi</p><p>Huh huh huh</p><p>Bởi vì anh bởi vì anh</p><p>Huh huh huh</p><p>Bởi vì anh bởi vì anh huh</p><p>Vì anh đâu có biết trái tim ngổn ngang</p><p>Vì anh đâu có biết đúng sai ngỡ ngàng</p><p>Vì anh luôn hối tiếc chiếc ôm dở dang</p><p>Chìm trong đôi mắt biếc anh không thể mang</p><p>Vì anh đâu có biết giấu đi thời gian</p><p>Vì anh đâu có biết nắng mai đang tàn</p><p>Vì anh luôn hối tiếc anh không thể mang</p><p>Mùi hương trên mái tóc giữ theo câu chuyện</p><p>Đánh rơi em là mây trôi trong gió</p><p>Anh là cây nhiều đắn đo</p><p>Cành lá không còn xanh như giấc mơ của anh</p><p>Rơi xuống đi tìm kiếm câu trả lời anh đánh rơi</p><p>Vươn dài đôi tay ôm theo mây anh không có</p><p>Khi ngày hôm nay còn lại gì ngoài lí do</p><p>Nhìn áng mây mỏng manh bay lướt qua thật nhanh</p><p>Anh ngỡ ra rằng chính câu trả lời anh đánh rơi là</p><p>Bởi vì anh bởi vì anh bởi vì anh</p><p>Là bởi vì anh bởi vì anh bởi vì anh huh</p><p>Vì anh đâu có biết trái tim ngổn ngang</p><p>Vì anh đâu có biết đúng sai ngỡ ngàng</p><p>Vì anh luôn hối tiếc chiếc ôm dở dang</p><p>Chìm trong đôi mắt biếc anh không thể mang</p><p>Vì anh đâu có biết giấu đi thời gian</p><p>Vì anh đâu có biết nắng mai đang tàn</p><p>Vì anh luôn hối tiếc anh không thể mang</p><p>Mùi hương trên mái tóc giữ theo câu chuyện đánh rơi</p><p>Vì anh đâu có biết</p><p>Vì anh đâu có biết trái tim ngổn ngang</p><p>Vì anh đâu có biết đúng sai ngỡ ngàng</p><p>Vì anh luôn hối tiếc chiếc ôm dở dang</p><p>Chìm trong đôi mắt biếc anh không thể mang</p><p>Vì anh đâu có biết giấu đi thời gian</p><p>Vì anh đâu có biết nắng mai đang tàn</p><p>Vì anh luôn hối tiếc anh không thể mang</p><p>Mùi hương trên mái tóc giữ theo câu chuyện đánh rơi</p><p>Vì anh đâu có biết giấu đi thời gian</p><p>Vì anh đâu có biết bởi vì anh đâu có hay</p><p>Rằng chính anh chẳng có em</p>','Vì anh đâu có biết','2023-09-30 21:12:08.678510',2,'sad',_binary ''),(2,'la lung.mp3',261,'POP','lalung.jpeg','<p>Kìa màn đêm hiu hắt mang tên em</p><p>Quay về trong ký ức của anh qua thời gian</p><p>Chiều lặng im nghe gió đung đưa cây</p><p>Như là bao nỗi nhớ cuốn anh trôi về đâu</p><p>Này gió đừng hát</p><p>Và mang nỗi nhớ chạy đi</p><p>Quên âu lo quên hết suy tư một đời</p><p>Mưa trong anh sẽ vơi</p><p>Nhưng đôi môi đang vấn vương</p><p>Chỉ tình cờ nhìn em rồi mang theo những cơn đau thét gào</p><p>Lạ lùng em tới</p><p>Hãy tới bên anh trong chiều đông xa vắng</p><p>Mà sao giờ đây nhìn lại chẳng còn thấy em?</p><p>Lạ lùng em với...</p><p>Gió hát lên câu ca làm anh thao thức</p><p>Mà bao say mê nồng nàn giờ đã phai mau</p><p>Kìa nắng ngập tràn</p><p>Nhưng giấc mơ lại vừa bay đi</p><p>Gạt hết cuộc đời lẻ loi</p><p>Thôi mình anh, lại ngồi nhớ em</p><p>Kìa màn đêm hiu hắt mang tên em</p><p>Quay về trong kí ức của anh qua thời gian</p><p>Chiều lặng im nghe gió đung đưa cây</p><p>Như là bao nỗi nhớ cuốn anh trôi về đâu</p><p>Này gió đừng hát và mang nỗi nhớ chạy đi</p><p>Quên âu lo quên hết suy tư một đời</p><p>Mưa trong anh sẽ vơi</p><p>Nhưng đôi môi đang vấn vương</p><p>Chỉ tình cờ nhìn em rồi mang theo những cơn đau thét gào</p><p>Lạ lùng em tới</p><p>Hãy tới bên anh trong chiều đông xa vắng</p><p>Mà sao giờ đây nhìn lại chẳng còn thấy em?</p><p>Lạ lùng em với gió hát lên câu ca làm anh thao thức</p><p>Mà bao say mê nồng nàn giờ đã phai mau</p><p>Kìa nắng ngập tràn</p><p>Nhưng giấc mơ lại vừa bay đi</p><p>Gạt hết cuộc đời lẻ loi</p><p>Thôi mình anh</p><p>Lại ngồi nhớ em</p>','Lạ Lùng','2023-09-30 21:12:08.678510',0,'mood',_binary ''),(4,'buoc qua mua co don.mp3',278,'POP','buocquamuacodon.jpeg','<p>Chào cơn mưa</p><p>Làm sao cứ kéo ta quay lại</p><p>Những rung động con tim</p><p>Lần đầu hai ta gặp gỡ</p><p>Chào hàng cây</p><p>Làm sao cố níu tay nhau lại</p><p>Để thấy nồng nàn</p><p>Đang về trên đôi mắt em</p><p>Chợt nhìn đôi bàn tay em run nắm lấy bờ vai, rất lâu</p><p>Cuối thu với anh là ngày khiến hai hàng mi rối bời</p><p>Vì ngày ấy gặp nhau không ai dám nói một câu, chào nhau</p><p>Cứ đắm đuối</p><p>Cứ thế hát bài hát chia xa</p><p>Mùa thu rơi vào em, vào trong giấc mơ hôm qua</p><p>Mùa thu ôm mình em, chạy xa vòng tay vội vã</p><p>Lời em nói ngày xưa đâu đây</p><p>Vẫn âm thầm chìm vào trong mây</p><p>Đến bao giờ, dặn lòng anh không mong nhớ</p><p>Mùa thu rơi vào em, vào trong chiếc hôn ngây thơ</p><p>Mùa thu không cần anh, vì em giờ đây còn mãi hững hờ</p><p>Ngày mai kia nếu có phút giây vô tình thấy nhau sẽ nói câu gì...</p><p>Hay ta chỉ nhìn</p><p>Lặng lẽ</p><p>Đi qua</p><p>Chào cơn mưa</p><p>Làm sao cứ kéo ta quay lại</p><p>Những rung động con tim</p><p>Lần đầu hai ta gặp gỡ</p><p>Chào hàng cây</p><p>Làm sao cố níu tay nhau lại</p><p>Để thấy nồng nàn</p><p>Đang về trên đôi mắt em</p><p>Chợt nhìn đôi bàn tay em run nắm lấy bờ vai, rất lâu</p><p>Cuối thu, với anh là ngày khiến hai hàng mi rối bời</p><p>Vì ngày ấy gặp nhau không ai dám nói một câu, chào nhau</p><p>Cứ đắm đuối chẳng thể chia xa</p><p>Mùa thu rơi vào em, vào trong giấc mơ hôm qua</p><p>Mùa thu ôm mình em, chạy xa vòng tay vội vã</p><p>Lời em nói ngày xưa đâu đây</p><p>Vẫn âm thầm chìm vào trong mây</p><p>Đến bao giờ, dặn lòng anh không mong nhớ</p><p>Mùa thu rơi vào em, vào trong chiếc hôn ngây thơ</p><p>Mùa thu không cần anh, vì em giờ đây còn mãi hững hờ</p><p>Ngày mai kia nếu có phút giây vô tình thấy nhau sẽ nói câu gì...</p><p>Hay ta chỉ nhìn</p><p>Lặng lẽ...</p><p>Đi qua</p>','Bước Qua Mùa Cô Đơn','2023-09-30 21:12:08.678510',0,'happy',_binary ''),(5,'buoc qua nhau.mp3',257,'POP','buocquanhau.jpeg','<p>Cuộc đời cứ trôi</p><p>Ta nhìn lại ngày tháng còn bên nhau</p><p>Cùng những thăng trầm</p><p>Tại sao không vẫy tay chào nơi ta đứng bây giờ</p><p>Hai nơi hai người dưng</p><p>Đợi em bước qua</p><p>Để khiến anh nhận ra là đôi mắt em còn đang buồn</p><p>Màu hoa cài áo vẫn còn như lời hứa đã từng</p><p>Giờ đây còn như xưa</p><p>Dòng người vội vàng bước qua</p><p>Chợt như chiếc hôn thế thôi</p><p>Đôi môi chia làm đôi</p><p>Như ta đang mong vậy thôi</p><p>Người nghẹn ngào bước đi</p><p>Chợt như chúng ta quay về</p><p>Giấu trái tim mình và đừng thổn thức khi thấy nhau oh</p><p>Đoàn tàu kia dừng lại</p><p>Còn hai ta bước qua nhau</p><p>Cuộc đời cứ trôi</p><p>Ta nhìn lại ngày tháng còn bên nhau</p><p>Cùng những thăng trầm</p><p>Và tại sao không vẫy tay chào nơi ta đứng bây giờ</p><p>Hai nơi hai người dưng</p><p>Đợi em bước qua</p><p>Để khiến anh nhận ra là đôi mắt em còn đang buồn</p><p>Và màu hoa cài áo vẫn còn như lời hứa đã từng</p><p>Giờ đây còn như xưa</p><p>Dòng người vội vàng bước qua</p><p>Chợt như chiếc hôn thế thôi</p><p>Đôi môi chia làm đôi</p><p>Như ta đang mong vậy thôi</p><p>Người nghẹn ngào bước đi</p><p>Chợt như chúng ta quay về</p><p>Giấu trái tim mình và đừng thổn thức khi thấy nhau oh</p><p>Đoàn tàu kia dừng lại (đoàn tàu kia)</p><p>Còn hai ta trôi đi theo mây trời</p><p>Từng cảm xúc trong tim anh đang cô đơn cùng với ngàn lời</p><p>Viết riêng cho bài ca tình đầu</p><p>Chỉ còn lại một thói quen từ lâu woh</p><p>Dòng người vội vàng bước qua</p><p>Chợt như chiếc hôn thế thôi</p><p>Đôi môi chia làm đôi</p><p>Như ta đang mong vậy thôi oh oh</p>','Bước Qua Nhau','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(6,'phut ban dau.mp3',243,'POP','phutbandau.jpeg','<p>Có ô cửa sổ nhỏ, lay hồn ai qua đây</p><p>Có giấc mơ là lạ, chạm vào trong ký ức</p><p>Dường như mây đang khóc</p><p>Mưa đang hát</p><p>Hát bài hát mùa yêu</p><p>Dường như mây đang đến</p><p>Mưa đi mất</p><p>Tôi xao xuyến</p><p>Em lưu luyến</p><p>Gần em tôi thấy bâng khuâng, bâng khuâng</p><p>Con tim này như chết lặng</p><p>Bóng dáng người như cơn gió lạ</p><p>Mang em về gần lại bên tôi</p><p>Dường như góc phố thênh thang, thênh thang</p><p>Sương đêm làm ướt tóc ai?</p><p>Đừng để nỗi nhớ miên man trong đêm thật dài</p><p>Hãy để khoảnh khắc tim tôi, tim em thật gần</p><p>Với nhau</p><p>Có cơn mưa đầu mùa, xa thật xa chốn ấy</p><p>Có cô em ngày nào, tóc ngắn ngang vai</p><p>Dường như mây đang khóc</p><p>Mưa đang hát</p><p>Hát bài hát mùa yêu</p><p>Dường như mây đang đến</p><p>Mưa đi mất</p><p>Tôi xao xuyến</p><p>Em lưu luyến</p><p>Gần em tôi thấy bâng khuâng, bâng khuâng</p><p>Con tim này như chết lặng</p><p>Bóng dáng người như cơn gió lạ</p><p>Mang em về gần lại bên tôi</p><p>Dường như góc phố thênh thang, thênh thang</p><p>Sương đêm làm ướt tóc ai?</p><p>Đừng để nỗi nhớ miên man trong đêm thật dài</p><p>Hãy để khoảnh khắc tim tôi, tim em thật gần, với nhau</p><p>Và tôi mong sớm mai đây khi em bên tôi, cùng say giấc nồng</p><p>Khẽ nhắc về mùa yêu thương đã qua</p><p>Khẽ tìm lại giây phút ban đầu, nhé em</p><p><br></p>','Phút Ban Đầu','2023-09-30 21:12:08.678510',0,'mood',_binary ''),(7,'dong kiem em.mp3',246,'POP','dongkiemem.jpeg','<p>Tôi hát cho màu xanh mãi xanh</p><p>Cho một người lặng im biết yêu</p><p>Và tôi viết cho mùa yêu xốn xang</p><p>Cho một đời nhớ thương vẹn nguyên</p><p>Cô đơn đến thế</p><p>Mưa rơi lách tách kì cục đợi ai</p><p>Sâu trong ánh mắt</p><p>Tôi ngu ngơ mơ thời gian dừng trôi</p><p>Còn lại đây nhớ mong còn lại tôi với ai</p><p>Giờ này em chắc đang ngủ say</p><p>Hay là em còn đang khóc một mình</p><p>Như làn sương muộn màng</p><p>Lạc trong đêm con tim em lạc trong đêm</p><p>Lòng phiêu du nhớ em mùa thu đã đi qua</p><p>Đông kiếm em mùa đông kiếm em</p><p>Huh hah</p><p>Mùa đông kiếm em đông kiếm em</p><p>Tôi biết em sợ đông sẽ tan</p><p>Em lạ lùng gọi trong vô vọng</p><p>Và tôi nhớ ly cà phê sắp tan</p><p>Biết bao giờ là em sẽ về</p><p>Cô đơn đến thế</p><p>Mưa rơi lách tách kì cục đợi ai</p><p>Sâu trong ánh mắt</p><p>Tôi ngu ngơ mơ thời gian dừng trôi</p><p>Còn lại đây nhớ mong còn lại tôi với ai</p><p>Giờ này em chắc đang ngủ say</p><p>Hay là em còn đang khóc một mình</p><p>Như làn sương muộn màng</p><p>Lạc trong đêm con tim em lạc trong đêm</p><p>Lòng phiêu du nhớ em mùa thu đã đi qua</p><p>Đông kiếm em mùa đông kiếm em</p><p>Huh hah</p><p>Mùa đông kiếm em đông kiếm em</p><p>Còn lại tôi với ai</p><p>Còn lại bao nhớ mong</p><p>Giờ này em chắc đang ngủ say</p>','Đông Kiếm Em','2023-09-30 21:12:08.678510',0,'mood',_binary ''),(8,'buc tranh tu nuoc mat.mp3',259,'POP','buoc tranh tu nuoc mat .jpeg','<p>Chuyện hai chúng ta bây giờ khác rồi</p><p>Thật lòng anh không muốn ai phải bối rối</p><p>Sợ em nhìn thấy nên anh đành phải lẳng lặng đứng xa</p><p>Chuyện tình thay đổi nên bây giờ trở thành người thứ ba</p><p>Trách ai bây giờ? Trách mình thôi</p><p>Nhìn em hạnh phúc bên ai càng làm anh tan nát lòng</p><p>Mới hiểu tại sao tình yêu người ta sợ khi cách xa</p><p>Điều anh lo lắng cứ vẫn luôn xảy ra</p><p>Nếu không đổi thay chẳng có ai sống được vì thiếu mất yêu thương</p><p>Thời gian giết chết cuộc tình còn đau hơn giết chính mình</p><p>Tại sao mọi thứ xung quanh vẫn thế, chỉ lòng người thay đổi?</p><p>Giờ em chỉ là tất cả quá khứ anh phải cố xóa trong nước mắt</p><p>Trong tình yêu, thuộc về ai không quan trọng</p><p>Một giây mơ màng là đã mất nhau</p><p>Càng nghĩ đến em anh càng hối hận</p><p>Vì xa em nên mất em thật ngu ngốc</p><p>Giờ tình anh như bức tranh bằng nước mắt, không màu sắc</p><p>Nhẹ nhàng và trong suốt cho dù đau đớn vẫn lặng yên</p><p>Trách ai bây giờ? Trách mình thôi</p><p>Nhìn em hạnh phúc bên ai càng làm anh tan nát lòng</p><p>Mới hiểu tại sao tình yêu người ta sợ khi cách xa</p><p>Điều anh lo lắng cứ vẫn luôn xảy ra</p><p>Nếu không đổi thay chẳng có ai sống được vì thiếu mất yêu thương</p><p>Thời gian giết chết cuộc tình còn đau hơn giết chính mình</p><p>Tại sao mọi thứ xung quanh vẫn thế, chỉ lòng người thay đổi?</p><p>Giờ em chỉ là tất cả quá khứ anh phải cố xóa trong nước mắt</p><p>Nụ cười em vẫn như xưa mà lòng em sao khác rồi</p><p>Nỗi đau này chỉ mình anh nhận lấy vì anh đã sai</p><p>Giờ anh phải cố giữ nước mắt đừng rơi</p><p>Bức tranh tình yêu của em từ lâu đã không hề có anh</p><p>Thời gian giết chết cuộc tình còn đau hơn giết chính mình</p><p>Tại sao mọi thứ xung quanh vẫn thế, chỉ lòng người thay đổi?</p><p>Giờ em chỉ là tất cả quá khứ anh phải cố xóa trong nước mắt</p>','Bức Tranh Từ Nước Mắt','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(9,'buc tranh tu nuoc mat.mp3',286,'CLASSICAL','chico1ng.jpeg','<p>Bỗng nhìn từ xa</p><p>Nhận ra ai đang vẫy tay</p><p>Muốn quay lưng ngay từ giây phút ấy</p><p>Rồi lặng im đi hết con đường</p><p>Mà sao không thể bước tới</p><p>Tưởng đã quên bấy lâu</p><p>Tại sao tim còn run lên</p><p>Vì bước chân của em</p><p>Lời hỏi thăm ngập ngừng</p><p>Lâu nay hạnh phúc không em</p><p>Mà sao không thể nào</p><p>Nói hết được những suy tư của anh</p><p>Chào em không vấn vương</p><p>Nhưng trong lòng muốn ôm chặt em</p><p>Mà nếu như vậy anh sẽ</p><p>Không thể nào buông tay em</p><p>I can\'t forget everything about you</p><p>Tại vì chẳng có ai ngờ</p><p>Chỉ có một người</p><p>Để yêu trên thế gian</p><p>Một người còn yêu nên vẫn nhớ</p><p>Một người thì vô tâm hững hờ</p><p>Tại sao anh cứ như vậy</p><p>Dù anh chẳng thể hạnh phúc hơn</p><p>And If I can</p><p>I would never let you go</p><p>Dù người vẫn muốn đi tìm</p><p>Hạnh phúc sau này</p><p>Cùng ai không phải anh</p><p>Chỉ cần một giây em nhớ đến</p><p>Thì anh cũng mong ở bên</p><p>Dù mai em có thế nào</p><p>Thì anh vẫn mãi không thể</p><p>Ngừng yêu đến mãi suốt cuộc đời</p><p>Lời hỏi thăm ngập ngừng</p><p>Lâu nay hạnh phúc không em</p><p>Mà sao không thể nào</p><p>Nói hết được những suy tư của anh</p><p>Chào em không vấn vương</p><p>Nhưng trong lòng muốn ôm chặt em</p><p>Mà nếu như vậy anh sẽ</p><p>Không thể nào buông tay em</p><p>I can\'t forget everything about you</p><p>Tại vì chẳng có ai ngờ</p><p>Chỉ có một người</p><p>Để yêu trên thế gian</p><p>Một người còn yêu nên vẫn nhớ</p><p>Một người thì vô tâm hững hờ</p><p>Tại sao anh cứ như vậy</p><p>Dù anh chẳng thể hạnh phúc hơn</p><p>And If I can</p><p>I would never let you go</p><p>Dù người vẫn muốn đi tìm</p><p>Hạnh phúc sau này</p><p>Cùng ai không phải anh</p><p>Chỉ cần một giây em nhớ đến</p><p>Thì anh cũng mong ở bên</p><p>Dù mai em có thế nào</p><p>Thì anh vẫn mãi không thể</p><p>Ngừng yêu đến mãi suốt cuộc đời</p><p>Ngừng yêu đến mãi suốt cuộc đời</p><p><br></p>','Chỉ Có Một Người Để Yêu Trên Thế Gian','2023-09-30 21:12:08.678510',0,'mood',_binary ''),(10,'day dut noi dau.mp3',298,'CLASSICAL','daydutnoidau.jpeg','<p>Chợt tỉnh giấc</p><p>Điều anh đã thấy như mới vừa xảy ra</p><p>Trong mơ anh thấy em tìm anh</p><p>Gục vào vai anh mà</p><p>Tại sao thức dậy nước mắt rơi?</p><p>Ngày xưa em</p><p>Từng ra đi bỏ lại người tổn thương là anh</p><p>Nhớ em day dứt không thể buông hết kỷ niệm cũ</p><p>Tại sao vẫn mong một người</p><p>Một người đã từng làm anh đau</p><p>Từng giờ từng phút ký ức bên nhau làm anh nhớ em</p><p>Mình chia tay nhau là điều anh chẳng muốn</p><p>Giá như ngày em thay đổi anh có tình yêu mới</p><p>Giá như tình yêu chỉ là một cơn mưa nước mắt đi qua</p><p>Từ ngày mình chia tay không thể nào yêu ai khác em</p><p>Người không yêu anh thật lòng sao anh vẫn tin</p><p>Mỗi khi tình yêu bắt đầu, mưa cũng là hạnh phúc</p><p>Đến khi niềm tin tan dần</p><p>Một người lặng khóc trông một người</p><p>Bước đi</p><p>Điện thoại rung lên trong đêm</p><p>Giật mình không tin người nhắn lại là em</p><p>Giờ anh nên vui hay anh phải nén đau vào sâu thẳm trong lòng anh</p><p>Vì em nói nhớ anh, rất muốn gặp anh mà sợ ngày mai cũng chỉ còn mỗi anh</p><p>Từng giờ từng phút ký ức bên nhau làm anh nhớ em</p><p>Mình chia tay nhau là điều anh chẳng muốn</p><p>Giá như ngày em thay đổi, anh có tình yêu mới</p><p>Giá như tình yêu chỉ là một cơn mưa nước mắt đi qua</p><p>Từ ngày mình chia tay không thể nào yêu ai khác em</p><p>Người không yêu anh thật lòng sao anh vẫn tin?</p><p>Mỗi khi tình yêu bắt đầu mưa cũng là hạnh phúc</p><p>Đến khi niềm tin tan dần</p><p>Một người lặng khóc trong tuyệt vọng</p><p>Mà người ơi nếu như em là anh em có dám tin?</p><p>Vào tình yêu của chúng ta, đã đi qua những giới hạn</p><p>Giá như thời gian quay lại anh vẫn còn tin em</p><p>Chắc không buồn như bây giờ</p><p>Còn yêu nhưng chẳng đến được với nhau</p><p>Từng một lần tổn thương rồi lần sau có thế không?</p><p>Dù tình yêu lớn đến đâu</p><p>Nước mắt rơi đã quá nhiều</p><p>Mỗi khi tình yêu bắt đầu, mưa cũng là hạnh phúc</p><p>Đến khi niềm tin tan dần</p><p>Một người lặng khóc trông theo một người</p><p>Bước đi</p>','Day Dứt Nỗi Đau','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(11,'mot buoc yeu van dam dau.mp3',298,'CLASSICAL','motbuocyeu.jpeg','<p>Một thế giới hư ảo, nhưng thật ấm áp</p><p>Em xuất hiện khiến những băng giá đời anh bỗng dần tan đi</p><p>Cuộc đời anh đặt tên là muộn phiền nên làm sao dám mơ mình may mắn</p><p>Được trọn vẹn cùng em</p><p>Ta phải xa em mặc kệ nước mắt em rơi</p><p>Vì những nguyên do cả đời không dám đối diện</p><p>Chỉ còn vài gang tấc nhưng lại xa xôi</p><p>Tình mình tựa đôi đũa lệch, đành buông trôi</p><p>Cầu mong em sẽ sớm quên được tất cả</p><p>Tìm thấy một người, xứng đáng ở bên</p><p>Từ nay duyên kiếp bỏ lại phía sau</p><p>Ngày và bóng tối chẳng còn khác nhau</p><p>Chẳng có nơi nào yên bình được như em bên anh</p><p>Hạt mưa bỗng hoá thành màu nỗi đau</p><p>Trời như muốn khóc ngày mình mất nhau</p><p>Có bao nhiêu đôi ngôn tình</p><p>Cớ sao lìa xa mình ta?</p><p>Là nhân duyên trời ban</p><p>Cớ sao mình chẳng thể thành đôi?</p><p>Tại sao quá ngu ngốc</p><p>Bỏ lại mảnh ghép mà đối với nhau là tất cả</p><p>Còn mình thì vụn vỡ</p><p>Thế giới thực tại ồn ào vẫn thấy cô đơn</p><p>Còn hai ta thì khác, chỉ nhìn thôi tim đã thấu</p><p>Từ nay duyên kiếp bỏ lại phía sau</p><p>Ngày và bóng tối chẳng còn khác nhau</p><p>Chẳng có nơi nào yên bình được như em bên anh</p><p>Hạt mưa bỗng hóa thành màu nỗi đau</p><p>Trời như muốn khóc ngày mình mất nhau</p><p>Có bao nhiêu đôi ngôn tình</p><p>Cớ sao lìa xa mình ta?</p><p>Từ nay danh giới của hai chúng ta</p><p>Là yêu nhưng không thể nào bước qua</p><p>Ngọn cỏ ven đường thôi mà</p><p>Làm sao với được mây?</p><p>Từ sau câu giã từ êm ái kia</p><p>Chẳng cơn bão lớn nào bằng bão lòng</p><p>Gặp trong mơ mà cũng không dám gào lên</p><p>\"Anh thương em\"</p>','Một bước yêu, vạn dặm đau','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(12,'duoi nhung con mua.mp3',297,'CLASSICAL','duoinhungconmua.jpeg','<p>Trời trắng xoá màu mưa</p><p>Mọi thứ đang lu mờ quá nhanh</p><p>Phố vắng ướt nhoà</p><p>Đã khắc sâu hơn những nỗi buồn</p><p>Nhận ra ngần ấy năm, em vẫn không thuộc về anh</p><p>Anh đã có tất cả nhưng tim em thì không</p><p>Và những gì đã từng tồn tại giữa hai chúng ta</p><p>Có lẽ không phải tình yêu em mong đợi (khi ta yêu nhau như xưa)</p><p>Ngày mà em quyết rời anh</p><p>Mọi thứ cứ ngỡ vẫn nguyên vẹn</p><p>Nhưng thật ra từ sâu trong lòng anh</p><p>Hy vọng cuối đã tắt</p><p>Nếu đang yêu nhau chỉ cần nhìn mưa sẽ nhớ nhau hơn?</p><p>Thế nhưng sao chia tay lại sợ giọt mưa thấm đẫm cô đơn</p><p>Cứ phải nghĩ hoài</p><p>Giờ ai kia đang ở đâu và đang vui như thế nào?</p><p>Có ai chỉ còn một mình mà không ghét những cơn mưa?</p><p>Lý do chia tay là gì, chẳng còn ý nghĩa cho ai</p><p>Khi người ở lại giờ đã mất đi tất cả</p><p>Nhìn mưa trong nỗi đau</p><p>Oh no babe, sao em lại mang những cảm xúc sẻ chia với ai</p><p>Mang hết những ấm áp xa khỏi nơi tim anh (How you feel that I am breaking up inside)_</p><p>(When you leave my life)</p><p>(I get lost in my mind)</p><p>Mưa làm đêm dài hơn em biết không?</p><p>Anh lại mang ký ức trở về</p><p>Sao anh không thể nào buông tay để quên được em?</p><p>Đã lâu ánh sáng mặt trời chẳng còn sưởi ấm nơi đây</p><p>Ký ức của đôi ta đang chìm dần vào trong góc tối tim anh</p><p>Chỉ còn đôi lần được mơ thấy ta lúc xưa</p><p>Làm anh thêm nhớ em</p><p>Có ai chỉ còn một mình mà không ghét những cơn mưa?</p><p>Lý do chia tay là gì, chẳng còn ý nghĩa cho ai</p><p>Khi người ở lại giờ đã mất đi tất cả</p><p>Chẳng muốn tin chính mình</p><p>Đã lâu ánh sáng mặt trời chẳng còn sưởi ấm nơi đây</p><p>Ký ức của đôi ta đang chìm dần vào trong góc tối tim anh</p><p>Chỉ còn đôi lần được mơ thấy ta lúc xưa</p><p>Làm anh thêm nhớ em</p><p>Có ai chỉ còn một mình mà không ghét những cơn mưa?</p><p>Lý do chia tay là gì, chẳng còn ý nghĩa cho ai</p><p>Khi người ở lại giờ đã mất đi tất cả</p><p>Nhìn mưa trong nỗi đau</p>','Dưới những cơn mưa','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(13,'cham day noi dau.mp3',293,'CLASSICAL','chamdaynoidau.jpeg','<p>Luôn bên em là tôi</p><p>Lâu nay không chút thay đổi</p><p>Thế nhưng bây giờ em muốn chia tay vì tôi vẫn còn trẻ con</p><p>Babe, 가지마</p><p>Stay here with me, 가지마</p><p>Hụt hẫng</p><p>Gạt nước mắt nhớ ngày buồn nhất</p><p>Không muốn ai thay mình chăm sóc em những ngày tới</p><p>Thoáng nghĩ đã đau lòng nhưng trách ai đây ngoài tôi</p><p>Em bước đi nhẹ nhàng (em đang xa tôi nhẹ nhàng)</p><p>Nhưng trong anh bão tố (giấu hết bão tố trong tim)</p><p>Nghẹn câu \"em đừng đi nữa\"</p><p>Nhưng tại môi mím chặt, chẳng thể một lần nói ra</p><p>Chẳng ai có thể chỉ một ngày mà tốt hơn</p><p>Chẳng lầm lỗi nào chỉ một giây mà xóa mờ</p><p>Mình không thể lâu dài chỉ vì tôi ngây ngô</p><p>(Ngốc nghếch nên tình thơ mới chết, vô vọng trong bóng tối mình tôi)</p><p>Thời gian sẽ minh chứng tất cả</p><p>Và cũng có thể ngoảnh đi, bỏ mặc chúng ta</p><p>Ngón tay ấy buông xuôi vì chẳng cần tôi ở bên</p><p>Đã từ chối cơ hội để đợi tôi vững vàng</p><p>Ngồi khóc giữa cơn mưa mới thấu đâu là chạm đáy của nỗi đau</p><p>Ngày cuối ở bên</p><p>Môi tôi không thể nuôi can đảm để thốt lên</p><p>Rời xa vòng tay</p><p>Em cho tôi cảm giác nhung nhớ đến thế nào</p><p>Từ biệt chưa nói câu chào, mà sao muốn bước đi vội</p><p>Mới có nhau thôi, lại xóa hết những yêu thương rồi</p><p>Bàn tay tôi nắm chặt</p><p>Ngước lên bầu trời để nước mắt không tuôn rơi</p><p>Người là ánh sáng dẫn tôi tìm những giấc mơ</p><p>Giờ phía trước mịt mù trong bóng tối nỗi sợ, I\'m losing you</p><p>Babe, 가지마</p><p>Stay here with me, 가지마</p><p>Hụt hẫng</p><p>Gạt nước mắt nhớ ngày buồn nhất</p><p>Từ sâu trong lòng không muốn ai thay mình chăm sóc em những ngày tới (ngày tới)</p><p>Thoáng nghĩ đã đau lòng nhưng trách ai đây ngoài tôi</p><p>Em bước đi nhẹ nhàng (em đang xa tôi nhẹ nhàng)</p><p>Nhưng trong anh bão tố (giấu hết bão tố trong tim)</p><p>Nghẹn câu \"em đừng đi nữa\"</p><p>Nhưng tại môi mím chặt, chẳng thể một lần nói ra</p><p>Chẳng ai có thể chỉ một ngày mà tốt hơn</p><p>Chẳng lầm lỗi nào chỉ một giây mà xóa mờ</p><p>Mình không thể lâu dài chỉ vì tôi ngây ngô</p><p>(Ngốc nghếch nên tình thơ mới chết, vô vọng trong bóng tối mình tôi)</p><p>Thời gian sẽ minh chứng tất cả</p><p>Và cũng có thể ngoảnh đi, bỏ mặc chúng ta</p><p>Ngón tay ấy buông xuôi vì chẳng cần tôi ở bên</p><p>Đã từ chối cơ hội để đợi tôi vững vàng</p><p>Ngồi khóc giữa cơn mưa mới thấu đâu là chạm đáy của nỗi đau</p><p>Babe, babe, 가지마</p><p>Babe, 가지마</p><p>Mình không thể lâu dài chỉ vì tôi ngây ngô</p><p>Thời gian sẽ minh chứng tất cả</p><p>Và cũng có thể ngoảnh đi, bỏ mặc chúng ta</p><p>Ngón tay ấy buông xuôi vì chẳng cần tôi ở bên</p><p>Đã từ chối cơ hội để đợi tôi vững vàng</p><p>Ngồi khóc giữa cơn mưa mới thấu đâu là chạm đáy của nỗi đau</p><p>Không muốn ai thay mình chăm sóc em những ngày tới</p><p>Thoáng nghĩ đã đau lòng nhưng trách ai đây ngoài tôi</p>','Chạm Đáy Nỗi Đau','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(14,'Em khong saichung ta sai.mp3',291,'CLASSICAL','emkosaichungtasai.jpeg','<p>Không thể tin vào giây phút ấy</p><p>Không ngờ đến ngày ta chia tay</p><p>Xin lỗi anh không giữ lời hứa</p><p>Không một ai được phép</p><p>Tổn thương lên người con gái ấy</p><p>Nhưng dù sao điều anh muốn biết</p><p>Khoảng cách nào mà ta tạo ra dấu chấm hết?</p><p>Phải nhận đau một lần mới thấu</p><p>Nếu đã là của nhau không giữ chặt tay sẽ vụt mất về sau</p><p>Anh thật sự ngu ngốc</p><p>Bảo vệ người ấy cũng không xong</p><p>Nỡ làm người yêu khóc</p><p>Thế thì còn xứng đáng yêu không?</p><p>Anh biết rằng anh sai</p><p>Nhưng không bao giờ tha thứ</p><p>Người nào tổn thương đến trái tim em như anh đã từng như thế</p><p>Anh thật lòng xin lỗi</p><p>Nhưng chẳng thể níu kéo nên thôi</p><p>Vẫn là vì anh sai</p><p>Vẫn là anh cố chấp ngày dài</p><p>Ðiều sau cuối anh làm</p><p>Nụ cười em đem hết đi</p><p>Cứ gói nỗi buồn lại để anh mang</p><p>Bao lần ta bỏ qua cho nhau</p><p>Nhưng nỗi lòng cả hai tạo ra nhiều vết xước</p><p>Hình như sau mọi lần cãi vã</p><p>Anh dần dần nhận ra ta không còn...</p><p>Thân nhau yêu nhau như lúc ngày xưa</p><p>Anh thật sự ngu ngốc</p><p>Bảo vệ người ấy cũng không xong</p><p>Nỡ làm người yêu khóc</p><p>Thế thì còn xứng đáng yêu không?</p><p>Anh biết rằng anh sai</p><p>Nhưng không bao giờ tha thứ</p><p>Người nào tổn thương đến trái tim em như anh đã từng như thế</p><p>Anh thật lòng xin lỗi</p><p>Nhưng chẳng thể níu kéo nên thôi</p><p>Vẫn là vì anh sai</p><p>Vẫn là anh cố chấp ngày dài</p><p>Ðiều sau cuối anh làm</p><p>Nụ cười em đem hết đi</p><p>Cứ gói nỗi buồn lại để anh mang</p><p>Đành để em cứ đi như vậy</p><p>Nếu em không còn yêu anh nữa</p><p>Mỏi mệt vì tình yêu đến vậy</p><p>Chia tay để tìm người tốt hơn (em sẽ quay trở về)</p><p>Giật mình anh mới biết anh quá vô tâm</p><p>Đến ngay cả một người cũng đánh mất</p><p>Anh thật sự ngu ngốc</p><p>Bảo vệ người ấy cũng không xong</p><p>Nỡ làm người yêu khóc</p><p>Thế thì còn xứng đáng yêu không?</p><p>Anh biết rằng anh sai</p><p>Nhưng không bao giờ tha thứ</p><p>Người làm tổn thương đến trái tim em như anh đã từng như thế</p><p>Anh thật lòng xin lỗi</p><p>Nhưng chẳng thể níu kéo nên thôi</p><p>Vẫn là vì anh sai</p><p>Nhưng vẫn là anh cố chấp ngày dài</p><p>Ðiều sau cuối anh làm</p><p>Nụ cười em đem hết đi</p><p>Cứ gói nỗi buồn lại để anh mang</p><p>Ðiều sau cuối anh làm</p><p>Niềm vui em hãy đem theo</p><p>Cứ gói nỗi buồn lại để anh mang</p><p><br></p>','Em Không Sai, Chúng Ta Sai','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(15,'yeu thuong la bao to.mp3',243,'CLASSICAL','yeuthuonglabaoto.jpeg','<p>Tình yêu có còn tồn tại ở trong hai chúng ta</p><p>Tình yêu có còn ngọt ngào tựa như đã thiết tha</p><p>Cớ sao giấc mơ ngày nào vội tàn theo gió bay</p><p>Thương nhớ xa tầm tay</p><p>Ngày xưa ấy ta vội vàng tìm nhau chẳng đắn đo</p><p>Mình cứ thế yêu nồng nàn rồi xa chẳng lý do</p><p>Để ai nhớ ai dại khờ ngày dài trôi ngẩn ngơ</p><p>Ôm trái tim vụn vỡ</p><p>Lời yêu thương tựa như bão tố</p><p>Chìm vào sâu cơn đau anh vẫy vùng</p><p>Anh đang cố với lấy một chút mong manh niềm tin cứu chính mình</p><p>Một lần yêu mà đau đến thế</p><p>Một lần tin mà xa xôi đến thế</p><p>Ôm thương nhớ vô vọng chết trong lòng em ơi</p><p>Ngày xưa ấy ta vội vàng tìm nhau chẳng đắn đo</p><p>Mình cứ thế yêu nồng nàn rồi xa chẳng lý do</p><p>Để ai nhớ ai dại khờ ngày dài trôi ngẩn ngơ</p><p>Ôm trái tim vụn vỡ</p><p>Lời yêu thương tựa như bão tố</p><p>Chìm vào sâu cơn đau anh vẫy vùng</p><p>Anh đang cố với lấy một chút mong manh niềm tin cứu chính mình</p><p>Một lần yêu mà đau đến thế</p><p>Một lần tin mà xa xôi đến thế</p><p>Ôm thương nhớ vô vọng chết trong lòng em ơi</p><p>Lời yêu thương tựa như bão tố</p><p>Chìm vào sâu cơn đau anh vẫy vùng</p><p>Anh đang cố với lấy một chút mong manh niềm tin cứu chính mình</p><p>Một lần yêu mà đau đến thế</p><p>Một lần tin mà xa xôi đến thế</p><p>Ôm thương nhớ vô vọng chết trong lòng em ơi</p>','Yêu Thương Là Bão Tố','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(16,'chay ve khoc voi anh.mp3',214,'CLASSICAL','nhuonglaiem.jpeg','<p>Cơn mưa chìm vào tháng tư, anh như mất hồn</p><p>Khi ấy ai đưa em đi qua con ngõ nhỏ</p><p>Bài hát anh đưa cho em nghe</p><p>Mà sao em quên ngay khi từ ngày đầu</p><p>Hết rồi (hết rồi)... những gì (những gì)</p><p>Hết rồi (hết rồi)... thế nhé</p><p>Thì thôi đành phải vỡ nát</p><p>Mặc cho lòng người vội vã về nhau (về nhau)</p><p>Theo cơn giông anh thu mình lại</p><p>Thì thôi anh xin được...</p><p>Anh xin được nhường lại em</p><p>Cơn mưa đầu hè lướt nhanh, tim anh khép lại</p><p>Khi ấy mưa đưa em đi về tận cuối trời</p><p>Đường phố đôi ta đi bên nhau</p><p>Tại sao đôi tay em buông chẳng một lời?</p><p>Anh biết nghe thì thật lạ nhưng nếu như hai ta được lại quen (quen)</p><p>Vẫn thương em bằng tất cả anh có dù biết có ngày phải nhường lại em (em)</p><p>Được yêu bao nhiêu cũng là may mắn (may mắn) kể cả chỉ một ngày mến nhau (một ngày)</p><p>Chuyện tình cảm đếch phải xe máy mà đã mất công đổ là cứ phải đến đâu</p><p>Nếu ngày bờ môi chẳng thể say đắm (say đắm), còn tay nắm chỉ để giằng xé nhau</p><p>Khi ấy như một gã hậu vệ tồi (tồi), anh sẽ chẳng giỏi níu kéo đâu</p><p>Vì tình yêu là một con thuyền lớn nhưng ở trước sóng đời nó cũng chỉ nhỏ teo (yeah)</p><p>Người từng chở che như bến đỗ đến lúc chỉ giữ nhau lại như mỏ neo</p><p>Tuổi trẻ là cơn mưa đầu hạ, đẹp long lanh nhưng mau lắm dừng</p><p>Anh chỉ vui vì trước khi nó tạnh, ta đã có dịp được tắm cùng</p><p>Có sao nếu như hết duyên nhưng ta không nợ một lời chân thành chúc?</p><p>Chúc cho một bước ta xa nhau, là một bước gần hơn với hạnh phúc</p><p>Ta xưa chẳng giàu hi vọng nhưng trời cho mỗi đứa mình khoản vay (vay)</p><p>Một người yêu mình khờ dại (dại) mà không hề bắt phải trả ngay (ngay)</p><p>Nhưng giờ đã đến hạn cuối (cuối) nên thôi cho dù không đành (đành)</p><p>Nhường lại cho thế giới một cô gái đã từng yêu anh</p><p>Nhưng giờ đã đến hạn cuối (cuối) nên thôi cho dù không đành</p><p>Nhường lại cho thế giới (giới)</p><p>Một cô gái đã từng yêu anh</p><p>Thì thôi đành phải vỡ nát</p><p>Mặc cho lòng người vội vã về nhau (về nhau)</p><p>Theo cơn giông anh thu mình lại</p><p>Thì thôi anh xin được...</p><p>Anh xin được nhường lại em</p>','Nhường Lại Em (feat. Phúc Du)','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(17,'y2mate.com - 31077  Wn  Official Video  ft titie Duongg.mp3',214,'CLASSICAL','cota.jpeg','<p>Cô ấy nói rằng anh đang nhớ em</p><p>Cô nói rằng anh đã nhắc tên em trong giấc mộng anh say tối qua</p><p>Buông hết kỷ niệm anh với cô ta</p><p>Trong màn đêm tối tăm quạnh hiu</p><p>Tôi ngồi đây ôm nỗi đau này</p><p>Chia đôi cuộc tình vỡ tan từ lâu</p><p>Cô ta là người khiến anh gầy hao</p><p>Thân xác điêu tàn</p><p>Thân xác điêu tàn</p><p>Không thể xóa nhòa đi những tổn thương sâu trong lòng em</p><p>Nỗi đau chồng lên duyên kiếp lỡ làng</p><p>Anh bước chẳng màng ngồi đây khóc than</p><p>Tan nát cõi lòng ngày anh bước đi</p><p>Phai dấu tình đôi ta trước khi anh</p><p>Gieo xuống toàn đau thương trái ngang</p><p>Em đã tàn phai theo những oán than</p><p>Trong màn đêm tối tăm quạnh hiu</p><p>Tôi ngồi đây ôm nỗi đau này</p><p>Chia đôi cuộc tình vỡ tan từ lâu</p><p>Cô ta là người khiến anh gầy hao</p><p>Thân xác điêu tàn</p><p>Thân xác điêu tàn</p><p>Không thể xóa nhòa đi những tổn thương sâu trong lòng em</p><p>Nỗi đau chồng lên duyên kiếp lỡ làng</p><p>Anh bước chẳng màng ngồi đây khóc than</p>','Cô Ta','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(18,'chay ve khoc voi anh.mp3',275,'CLASSICAL','conlaigi.jpeg','<p>Nhìn vào phút giây còn đầy vơi</p><p>Nhìn vào trái tim còn thảnh thơi</p><p>Thì anh, còn</p><p>Anh, còn lại gì</p><p>Nhìn vào xác xuân đang tàn phai</p><p>Nhìn vào lá cây như buồn thiu</p><p>Thì anh, còn</p><p>Anh</p><p>Còn đây đó nụ cười</p><p>Còn đâu đó mặt trời như sáng lên</p><p>Còn đây đó một thời</p><p>Còn đâu đó giọng người như hét lên</p><p>Và thì thầm</p><p>Hét lên và thì thầm</p><p>Còn đây đó nụ cười</p><p>Còn đâu đó mặt trời như sáng lên</p><p>Còn đây đó một đời</p><p>Còn đâu đó giọng người như hét lên</p><p>Và thì thầm</p><p>Hét lên và thì thầm</p><p>Tên em</p><p>Gọi nắng quay về</p><p>Gọi nắng quay về</p><p>Quay về</p><p>Gọi nắng quay về</p><p>Gọi nắng...</p><p>Sao em không quay về</p><p>Còn đây đó nụ cười</p><p>Còn đâu đó mặt trời như sáng lên</p><p>Còn đây đó nụ cười</p><p>Còn đâu đó mặt trời như sáng lên</p><p>Và lụi tàn</p><p>Sáng lên và lụi tàn</p>','Còn Lại Gì?','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(19,'y2mate.com - 31077  Wn  Official Video  ft titie Duongg.mp3',148,'CLASSICAL','cothelataisao.jpeg','<p>Thì thầm với em một câu</p><p>Hát cho em một bài ca</p><p>Nhưng sao em lại khóc?</p><p>Mặt đất cứ xoay vòng xoay</p><p>Lòng người cứ như trò chơi</p><p>Nhưng sao hai ta lại không thể gần nhau</p><p>Lại không thể gần nhau</p><p>Lại không thể</p><p>Không...</p><p>Hãy nói cho anh nghe tại sao</p><p>Về ước mơ của ngày năm ấy...</p><p>Hãy nói cho anh nghe tại sao</p><p>Tại sao...</p><p>Em nói đi em</p><p>Em nói đi em...</p><p>Khi vết thương này</p><p>Chẳng còn gì để lại</p>','Có Thể Là Tại Sao?','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(20,'chay ve khoc voi anh.mp3',216,'CLASSICAL','doai.jpeg','<p>Hãy nhớ tên anh</p><p>Khi trời mưa rào</p><p>Khi mặt trăng đang rơi</p><p>Rơi, rơi, rơi</p><p>Nhưng em à</p><p>Em à</p><p>Phố xá đông vui</p><p>Khi còn em chờ</p><p>Khi còn bao nỗi nhớ thương</p><p>Thương, thương</p><p>Và anh...</p><p>Hhh-h anh</p><p>Hhh-h anh</p><p>Và sẽ đưa em về</p><p>Nhưng em à</p><p>Em à</p><p>Ai, ai, ai?</p><p>Và ai...</p><p>Và ai...</p><p>Ai...</p><p>Ai cho em nụ cười?</p><p>Và ai cho em tình người?</p><p>Ai?</p><p>Ai...</p><p>Và ai...</p><p>Ai...</p><p>Ai cho em nụ cười?</p><p>Và ai cho em tình người?</p><p>Và ai?</p><p>Ai...</p><p>Và ai cho em tình người?</p>','Do Ai?','2023-09-30 21:12:08.678510',0,'sad',_binary ''),(21,'chay ve khoc voi anh.mp3',275,'CLASSICAL','muonroimasaocon.jpeg','<p>Muộn rồi mà sao còn</p><p>Nhìn lên trần nhà rồi quay ra lại quay vào</p><p>Nằm trằn trọc vậy đến sáng mai</p><p>Ôm tương tư nụ cười của ai đó</p><p>Làm con tim ngô nghê như muốn khóc oà</p><p>Vắt tay lên trên trán mơ mộng</p><p>Được đứng bên em trong nắng xuân hồng</p><p>Một giờ sáng</p><p>Trôi qua trôi nhanh kéo theo ưu phiền miên man</p><p>Âm thầm gieo tên em vẽ lên hi vọng</p><p>Đúng là yêu thật rồi còn không thì hơi phí này</p><p>Cứ thế loanh quanh loanh quanh loanh quanh lật qua lật lại hai giờ</p><p>Những ngôi sao trên cao</p><p>Là người bạn tâm giao</p><p>Lắng nghe anh luyên thuyên về một tình đầu đẹp tựa chiêm bao</p><p>Có nghe thôi đã thấy ngọt ngào đủ biết anh si mê em nhường nào</p><p>Ít khi văn thơ anh dạt dào bụng đói nhưng vui quên luôn cồn cào</p><p>Nắm đôi tay kiêu sa</p><p>Được một lần không ta</p><p>Nghĩ qua thôi con tim trong anh đập tung lên rung nóc rung nhà</p><p>Hoá ra yêu đơn phương một người</p><p>Hoá ra khi tơ vương một người</p><p>Ba giờ đêm vẫn ngồi cười</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong cơn mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Có thế cũng khiến anh vui điên lên</p><p>Ngỡ như em đang bên</p><p>Chắp bút đôi ba câu thơ ngọt ngào</p><p>Muốn em đặt tên</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong giấc mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Yêu đến vậy thôi phát điên rồi làm sao giờ</p><p>Chịu</p><p>Đêm nay không ngủ tay kê lên tủ</p><p>Miên man anh tranh thủ</p><p>Chơi vơi suy tư bao nhiêu cho đủ</p><p>Yêu em ngu ngơ mình tôi</p><p>Yêu không quan tâm ngày trôi</p><p>Yêu ánh mắt bờ môi</p><p>Yêu đơn phương vậy thôi</p><p>Lại còn chối</p><p>Con tim thẹn thùng đập lạc lối liên hồi</p><p>Đừng chày cối</p><p>Miệng cười cả ngày vậy là chết toi rồi</p><p>Ngày càng nhiều thêm</p><p>Tình yêu cho em ngày càng nhiều thêm</p><p>Muốn nắm đôi bàn tay đó một lần</p><p>Du dương chìm sâu trong từng câu ca dịu êm</p><p>Những ngôi sao trên cao là người bạn tâm giao</p><p>Lắng nghe anh luyên thuyên về một tình đầu đẹp tựa chiêm bao</p><p>Có nghe tôi đã thấy ngọt ngào đủ biết anh si mê em nhường nào</p><p>Ít khi văn thơ anh dạt dào bụng đói nhưng vui quên luôn cồn cào</p><p>Nắm đôi tay kiêu sa</p><p>Được một lần không ta</p><p>Nghĩ qua thôi con tim trong anh đập tung lên rung nóc rung nhà</p><p>Hoá ra yêu đơn phương một người</p><p>Hoá ra khi tơ vương một người</p><p>Ba giờ đêm vẫn ngồi cười</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong cơn mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Có thế cũng khiến anh vui điên lên</p><p>Ngỡ như em đang bên</p><p>Chắp bút đôi ba câu thơ ngọt ngào</p><p>Muốn em đặt tên</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong giấc mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Yêu đến vậy thôi phát điên rồi làm sao giờ</p><p>Em xinh như một thiên thần</p><p>Như một thiên thần</p><p>Như một thiên thần</p><p>Ngỡ như em là thiên thần</p><p>Xinh như một thiên thần</p><p>Như một thiên thần</p><p>Em xinh như một thiên thần</p><p>Như một thiên thần</p><p>Như một thiên thần</p><p>Ngỡ như em là thiên thần</p><p>Ngỡ như ngỡ như</p><p>Ngỡ như ngỡ như ngỡ như</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong cơn mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Có thế cũng khiến anh vui điên lên</p><p>Ngỡ như em đang bên</p><p>Chắp bút đôi ba câu thơ ngọt ngào</p><p>Muốn em đặt tên</p><p>Cứ ôm anh đi ôm anh đi ôm anh đi ôm anh đi</p><p>Ôm trong giấc mơ trong cơn mơ trong cơn mơ ôm trong cơn mơ</p><p>Yêu đến vậy thôi phát điên rồi làm sao giờ</p>','Muộn Rồi Mà Sao Còn','2023-09-30 21:12:08.678510',0,'mood',_binary '');
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
  `role_id` int DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `date_of_brith` datetime(6) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKcg8vm2yga4tm8kvsid9aqkt55` (`role_id`),
  CONSTRAINT `FKcg8vm2yga4tm8kvsid9aqkt55` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hyung@gmail.com','taehyung','MALE','','$2a$10$.2hz73BXj6kg1DOGRBDHouUHHRtRXHA5Drcm.Q3msFGfBAa7PWC2e',1,NULL,NULL,'V.jpeg'),(2,'dojoOfficial@gmail.com','Doja ','FEMALE','Cat','$2a$10$lqVM9StqRYo3CPx0VjGLAu.TZSWrYiggoaLBmdeyqXq4eHLJqjQNa',1,NULL,NULL,'dojo.webp'),(3,'taylor@gmail.com','Taylor','FEMALE','Swift','$2a$10$q15lBnPU62FBBC3Q87YeCuKEF/9DsIIZvHSkGS9ZMq3U94VrTBvfa',1,NULL,NULL,'taylor.webp'),(4,'olivia@gmail.com','Olivia','FEMALE','Rodrigo','$2a$10$ml/KSJLxfyVzwBd29u6NaOslaV4mWCmXUn9YgVv22DAYXGN6ZGyUy',1,NULL,NULL,'Olivia.webp'),(5,'den@gmail.com','Đen','MALE','','$2a$10$CrHkPKw65XN0kIZgjB4jcuRTSJA8EFLGK591.HGCFCgp4sWaJDUNC',1,NULL,NULL,'den.jpeg'),(6,'st@gmail.com','Sơn Tùng ','MALE','M-TP','$2a$10$7/i1Mg57XMYVDF2y60dlzOJkuLZuSCvKgr/XCT/BbOD1EJdt9X4nm',1,NULL,NULL,'st.jpeg'),(7,'gunna@gmail.com','Gunna','MALE','','$2a$10$AYq4/yNLslKTbW8ub9f9/O9U9rrcV.8qCuAqjWXTdN61qdFEq7VAC',1,NULL,NULL,'gunna.jpeg'),(8,'travis@gmail.com','Travis ','MALE','Scott','$2a$10$qbOzozWFDQ3vFdmHXZ8Deu/z30umN.uX9NGCAxbLqNjRrjJ5C6czq',1,NULL,NULL,'travis.jpeg'),(9,'drake@gmail.com','Drake','MALE','','$2a$10$Cn3CXEhoHcvNAw1BxLUB8.rZi/E2lQXgAOrsxUTlp6hjEuinY1eV2',1,NULL,NULL,'drake.jpeg'),(10,'bruno@gmail.com','Bruno ','MALE','Mars','$2a$10$5ohHSVxyDgCDzbuqaKstfe4VeUQS9InD8cP53.CiI080acp37WFjS',1,NULL,NULL,'brunom.webp'),(11,'bill@gmail.com','billie','FEMALE','eilish','$2a$10$jBnw6RnpGTF90v3gcV3/JuT0yWBHf5NqAwBhlIqqXQYkNy1A6.gna',1,NULL,NULL,'bill.jpeg'),(12,'tee@gmail.com','JustaTee','MALE','','$2a$10$XxKCv5dYFRQo62WEDV7eZeOH.XFe1d6rNQFoZ/iZ3sGJeHind4FyC',1,NULL,NULL,'justaTee.jpeg'),(13,'mkc@gmail.com','MCK','MALE','','$2a$10$6ZbML/SxhsoVfGRZxJhJWOjhtQFHEo3TxQ./j8kAYr2Jalf31hXY2',1,NULL,NULL,'nger.jpeg'),(14,'tlinh@gmail.com','Tlinh','FEMALE','','$2a$10$QwU4hooLb.Z.0mNp9a9VZuDcx9bo8fgOPWzsFQuY2x0MhoNjhwPQC',1,NULL,NULL,'tlinh.jpeg'),(15,'min@gmail.com','Min','FEMALE','','$2a$10$PC/de0zbi6dfnnaban5W1eLGfDR5M1ooknBjd5iriojiPD2QcOT96',1,NULL,NULL,'min.png'),(16,'erik@gmail.com','Erik','MALE','','$2a$10$VdWQlMRFFKxh6flWZ5kqw.hjqNHgD.95gwdqOnuwZvpA/IFZdOo0u',1,NULL,NULL,'erik.jpeg'),(17,'andree@gmail.com','Andree','MALE','Right Hand','$2a$10$zrGEiO6G63AwKtq4oItowu4p8EC4AHLEVXo/qAUMJOlksh.RUM6Lq',1,NULL,NULL,'andree.jpeg'),(18,'vu@gmail.com','Vũ.','MALE','','$2a$10$CexYb9SmH6VhwsjcG73Xhe8wuSAhHFXemXr1QSQEui54CqKb3Xm56',1,NULL,NULL,'vu.jpeg'),(19,'jungcut@gmail.com','Jung','MALE','Kook','$2a$10$gt810aSnW9Tc1pCKX9TXT./KcEiYRKyqClKKEJsp1UhBYAme6DcKu',1,NULL,NULL,'jungcook.jpeg'),(20,'hieu2@gmail.com','HIEUTHUHAI','MALE','','$2a$10$Hm6EUBNhnj3t4jEeNCACBuLogFha.zQSOchbhYZ3BmF7G28R9NGFy',1,NULL,NULL,'hieu2.jpeg'),(21,'grey@gmail.com','Grey','MALE','D','$2a$10$4iaYpZ0rHAk2zRmZno6Nb.QTvF3lYp6VXwyxjAS7S1oT57IrqYes.',1,NULL,NULL,'grey.jpeg'),(22,'onion@gmail.com','Onion','MALE','','$2a$10$PozkAxO6xcolOmaGieT5Y.BBzWu1Vg9fs0QRtPiJFJSj1.7A.kogO',1,NULL,NULL,'onionn.jpeg'),(23,'vmh@gmail.com','Văn Mai','FEMALE','Hương','$2a$10$ovdWNVGJFQt3eaT9SaDPYuHpbKljAwJCx8PqHXvnq2zaAhpND6beO',1,NULL,NULL,'vmh.jpeg'),(24,'trungq@gmail.com','Trung','MALE','Quân','$2a$10$aePo34k3UnGtug0N56X.desWDVZ9LK09I7Rhb5MwENVIQuZvr0oqW',1,NULL,NULL,'trungq.jpeg'),(25,'boom@gmail.com','Pháo','FEMALE','','$2a$10$i6a2gs9qvtD.T7KUsqch6u1AtpY3U82m8Wqx6bFN7qeJNV0blK8se',1,NULL,NULL,'pháo.jpeg'),(26,'pl@gmail.com','Phương','FEMALE','Ly','$2a$10$G.Op2BpIAxmtBUNMV/e1wedwLfzAU1Jr2UTPAxITzVqRNNCxhirGy',1,NULL,NULL,'pl.jpeg'),(27,'madihu@gmail.com','Madihu','MALE','','$2a$10$8Rwdxf6KtrJrnmVPrELr2uxTg/Pc2CvRDWMsp7yButNCHPGGeRqE2',1,NULL,NULL,'madihu.jpeg'),(28,'siro@gmail.com','Mr.Siro','MALE','','$2a$10$Hii0RWoItYcQaun1VH5v6eve4o8fO0Ln9yIWivMKPqJVLpNP5/hWi',1,NULL,NULL,'siro.jpeg'),(29,'hquan@gmail.com','Hùng ','MALE','Quân','$2a$10$XQ.oEgRZM4rs9AxuM0VE6O5Uj78whpL4WAkyK0Gj3vEQmuqWlTcsO',1,NULL,NULL,'hungquan.jpeg'),(30,'phucku@gmail.com','phuc','MALE','du','$2a$10$gt.gz9hK8k1AqRs51P9ff.zFXiuZvQg.h7JiCJ4lKT2BxKVDMXNb.',1,NULL,NULL,'phucdu.jpeg');
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
INSERT INTO `user_song` VALUES (18,1),(18,2),(18,4),(18,5),(18,6),(18,7),(28,8),(28,9),(28,10),(28,11),(28,12),(16,13),(16,14),(29,15),(18,16),(30,16),(18,17),(18,18),(18,19),(18,20),(6,21);
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

-- Dump completed on 2023-10-29 14:55:22
