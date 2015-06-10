-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2015-06-11 00:26:22
-- 服务器版本: 5.5.43-0ubuntu0.14.04.1
-- PHP 版本: 5.5.9-1ubuntu4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `danmaku`
--
CREATE DATABASE IF NOT EXISTS `danmaku` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `danmaku`;

-- --------------------------------------------------------

--
-- 表的结构 `dmk_danmaku`
--

CREATE TABLE IF NOT EXISTS `dmk_danmaku` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(64) NOT NULL,
  `username` varchar(32) NOT NULL,
  `content` text NOT NULL,
  `font_size` tinyint(4) unsigned NOT NULL DEFAULT '40',
  `color_r` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `color_g` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `color_b` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `speed` tinyint(4) unsigned NOT NULL DEFAULT '5',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
