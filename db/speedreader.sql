-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for speedreader
CREATE DATABASE IF NOT EXISTS `speedreader` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `speedreader`;

-- Dumping structure for table speedreader.favorite
CREATE TABLE IF NOT EXISTS `favorite` (
  `FavoriteId` int(11) NOT NULL AUTO_INCREMENT,
  `PatronId` int(11) NOT NULL,
  `PostId` int(11) NOT NULL,
  PRIMARY KEY (`FavoriteId`),
  KEY `post_favorite_fk` (`PostId`),
  KEY `patron_favorite_fk` (`PatronId`),
  CONSTRAINT `patron_favorite_fk` FOREIGN KEY (`PatronId`) REFERENCES `patron` (`PatronId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `post_favorite_fk` FOREIGN KEY (`PostId`) REFERENCES `post` (`PostId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.patron
CREATE TABLE IF NOT EXISTS `patron` (
  `PatronId` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(15) NOT NULL,
  `Password` varbinary(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `AccountCreated` timestamp NOT NULL DEFAULT current_timestamp(),
  `Salt` varbinary(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`PatronId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.patroncomment
CREATE TABLE IF NOT EXISTS `patroncomment` (
  `PatronCommentId` int(11) NOT NULL AUTO_INCREMENT,
  `CommentTime` datetime NOT NULL,
  `CommentContent` varchar(500) NOT NULL,
  `PostId` int(11) NOT NULL,
  `PatronId` int(11) NOT NULL,
  PRIMARY KEY (`PatronCommentId`),
  KEY `post_patroncomment_fk` (`PostId`),
  KEY `patron_patroncomment_fk` (`PatronId`),
  CONSTRAINT `patron_patroncomment_fk` FOREIGN KEY (`PatronId`) REFERENCES `patron` (`PatronId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `post_patroncomment_fk` FOREIGN KEY (`PostId`) REFERENCES `post` (`PostId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.permission
CREATE TABLE IF NOT EXISTS `permission` (
  `PermissionId` int(11) NOT NULL AUTO_INCREMENT,
  `PermissionName` varchar(50) NOT NULL,
  PRIMARY KEY (`PermissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.permissionlevel
CREATE TABLE IF NOT EXISTS `permissionlevel` (
  `PermissionLevelId` int(11) NOT NULL AUTO_INCREMENT,
  `PatronId` int(11) NOT NULL,
  `PermissionId` int(11) NOT NULL,
  PRIMARY KEY (`PermissionLevelId`),
  KEY `permission_permissionlevel_fk` (`PermissionId`),
  KEY `patron_permissionlevel_fk` (`PatronId`),
  CONSTRAINT `patron_permissionlevel_fk` FOREIGN KEY (`PatronId`) REFERENCES `patron` (`PatronId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `permission_permissionlevel_fk` FOREIGN KEY (`PermissionId`) REFERENCES `permission` (`PermissionId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.post
CREATE TABLE IF NOT EXISTS `post` (
  `PostId` int(11) NOT NULL AUTO_INCREMENT,
  `PostLink` varchar(2000) NOT NULL,
  `PostTitle` varchar(1000) NOT NULL,
  `PostDescription` varchar(5000) DEFAULT NULL,
  `SourceId` int(11) NOT NULL,
  `Date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`PostId`),
  KEY `source_post_fk` (`SourceId`),
  CONSTRAINT `source_post_fk` FOREIGN KEY (`SourceId`) REFERENCES `source` (`SourceId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=786 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.source
CREATE TABLE IF NOT EXISTS `source` (
  `SourceId` int(11) NOT NULL AUTO_INCREMENT,
  `SourceName` varchar(50) NOT NULL,
  `SourceLink` varchar(100) NOT NULL,
  PRIMARY KEY (`SourceId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table speedreader.subscription
CREATE TABLE IF NOT EXISTS `subscription` (
  `SubscriptionId` int(11) NOT NULL AUTO_INCREMENT,
  `PatronId` int(11) NOT NULL,
  `SourceId` int(11) NOT NULL,
  PRIMARY KEY (`SubscriptionId`),
  KEY `source_subscription_fk` (`SourceId`),
  KEY `patron_subscription_fk` (`PatronId`),
  CONSTRAINT `patron_subscription_fk` FOREIGN KEY (`PatronId`) REFERENCES `patron` (`PatronId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `source_subscription_fk` FOREIGN KEY (`SourceId`) REFERENCES `source` (`SourceId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
