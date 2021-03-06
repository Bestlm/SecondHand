/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.62-log : Database - db1
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db1`;

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `cost` float DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `productId` (`product_id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;

/*Data for the table `cart` */

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` int(11) NOT NULL COMMENT '订单主键',
  `product_id` int(11) NOT NULL COMMENT '商品主键',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `cost` float NOT NULL COMMENT '消费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___66E1BD8E2F10007B` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

/*Data for the table `order_detail` */

insert  into `order_detail`(`id`,`order_id`,`product_id`,`quantity`,`cost`) values (86,109,733,2,304),(112,138,760,1,11000),(113,138,733,1,152);

/*Table structure for table `orderw` */

DROP TABLE IF EXISTS `orderw`;

CREATE TABLE `orderw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户主键',
  `login_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_address` varchar(255) DEFAULT NULL COMMENT '用户地址',
  `cost` float DEFAULT NULL COMMENT '总金额',
  `serialnumber` varchar(255) DEFAULT NULL COMMENT '订单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;

/*Data for the table `orderw` */

insert  into `orderw`(`id`,`user_id`,`login_name`,`user_address`,`cost`,`serialnumber`,`create_time`,`update_time`) values (109,10,'cgn','软件园',1482,'E96F8AF1E5E0D5BF1646F9344E31DA14','2019-10-22 21:20:24','2021-01-01 14:48:34'),(138,16,'ct','东莞松山湖',11152,'308A3C7903622A2970CE68AE1F9063A6','2021-01-02 21:25:36','2021-01-02 21:25:36');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `price` float NOT NULL COMMENT '价格',
  `stock` int(11) NOT NULL COMMENT '库存',
  `categorylevelone_id` int(11) DEFAULT NULL COMMENT '分类1',
  `categoryleveltwo_id` int(11) DEFAULT NULL COMMENT '分类2',
  `categorylevelthree_id` int(11) DEFAULT NULL COMMENT '分类3',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___94F6E55132E0915F` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=780 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`name`,`description`,`price`,`stock`,`categorylevelone_id`,`categoryleveltwo_id`,`categorylevelthree_id`,`file_name`) values (733,'香奈儿','订单',152,8,548,654,655,'baby_1.jpg'),(734,'洗面奶','',152,1,548,654,655,'baby_2.jpg'),(735,'水啫喱水','',152,989,548,654,655,'baby_3.jpg'),(736,'香水','',152,997,548,654,655,'baby_4.jpg'),(737,'香水','',152,110,548,654,655,'baby_5.jpg'),(738,'润肤露','',45,109,548,654,655,'baby_6.jpg'),(739,'洁面装','',156,99,548,654,655,'bk_2.jpg'),(740,'电饭锅','',158,98,628,656,659,'bk_1.jpg'),(741,'婴儿喂奶装','',569,100,632,637,653,'bk_3.jpg'),(742,'坚果套餐','',158,996,660,661,662,'bk_4.jpg'),(743,'超甜蜜崭','',589,996,660,661,663,'bk_5.jpg'),(744,'华为2566','',589,994,670,671,672,'de1.jpg'),(745,'荣耀3C','',589,100,670,671,672,'de2.jpg'),(746,'小米手环','',963,100,670,674,675,'de3.jpg'),(747,'华为2265','',896,1000,670,671,673,'de4.jpg'),(748,'越南坚果','',520,1,660,661,662,'de5.jpg'),(749,'日本进口马桶','',5866,97,628,657,0,'food_1.jpg'),(750,'联想Y系列','',569,1000,670,690,691,'food_2.jpg'),(751,'脑白金1号','',589,1000,676,677,680,'food_3.jpg'),(752,'莫里斯按','',589,1000,676,678,0,'food_4.jpg'),(753,'三鹿好奶粉','',859,100,676,679,0,'food_5.jpg'),(754,'儿童牛奶','',5896,100,676,679,0,'food_6.jpg'),(755,'软沙发','',8596,99,628,696,0,'food_b1.jpg'),(756,'收纳盒','',5966,100,628,696,0,'food_b2.jpg'),(757,'洗衣液','',58,998,628,696,0,'food_r.jpg'),(758,'红短沙发','',596,123,628,696,0,'fre_1.jpg'),(759,'新西兰奶粉','',5896,100,676,679,0,'fre_2.jpg'),(760,'婴儿车','',11000,99,681,682,687,'fre_3.jpg'),(761,'夏款婴儿车','',963,100,681,682,688,'fre_4.jpg'),(762,'抗压旅行箱','',569,1000,681,683,685,'fre_5.jpg'),(763,'透明手提箱','',8596,1000,681,683,684,'fre_6.jpg'),(764,'婴儿果粉','',5896,997,660,661,662,'milk_1.jpg'),(765,'椰子粉','',5963,1000,660,661,662,'milk_2.jpg'),(766,'坚果蛋糕','',200,1,660,661,663,'milk_3.jpg'),(767,'编制手提箱','',5896,1000,681,682,688,'milk_4.jpg'),(768,'纸箱','',5896,3,681,682,687,'milk_5.jpg'),(769,'健胃液','',152,1000,676,679,0,'milk_6.jpg'),(770,'联想NTC','',8596,100,670,671,673,'milk_7.jpg'),(771,'香水1',NULL,100,100,548,654,655,'milk_8.jpg'),(772,'香水2',NULL,100,100,548,654,655,'pro1.jpg'),(773,'香水3',NULL,100,100,548,654,655,'pro2.jpg'),(774,'香水4',NULL,100,100,548,654,655,'pro3.jpg'),(775,'香水5',NULL,100,100,548,654,655,'pro4.jpg'),(776,'香水6',NULL,1,1,548,654,655,'pro5.jpg'),(778,'我自己加的',NULL,123,123,548,654,655,'12.png'),(779,'我自己加',NULL,456,456,548,654,655,'13.png');

/*Table structure for table `product_category` */

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `parent_id` int(11) NOT NULL COMMENT '父级目录id',
  `type` int(11) DEFAULT NULL COMMENT '级别(1:一级 2：二级 3：三级)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___9EC2A4E236B12243` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=utf8;

/*Data for the table `product_category` */

insert  into `product_category`(`id`,`name`,`parent_id`,`type`) values (548,'化妆品',0,1),(628,'家用商品',0,1),(654,'面部护理',548,2),(655,'少女派',654,3),(656,'餐具',628,2),(657,'卫具',628,2),(658,'叉子',656,3),(659,'锅',656,3),(660,'进口食品',0,1),(661,'零食/糖果/巧克力',660,2),(662,'坚果',661,3),(663,'蜜饯',661,3),(669,'孕期教育',546,3),(670,'电子商品',0,1),(671,'手机',670,2),(672,'华为手机',671,3),(673,'联想手机',671,3),(674,'手环',670,2),(675,'小米手环',674,3),(676,'保健食品',0,1),(677,'老年保健品',676,2),(678,'中年营养品',676,2),(679,'儿童保健品',676,2),(680,'脑白金',677,3),(681,'箱包',0,1),(682,'旅行箱',681,2),(683,'手提箱',681,2),(684,'大型',683,3),(685,'小型',683,3),(686,'中型',683,3),(687,'大型',682,3),(688,'中型',682,3),(689,'小型',682,3),(690,'电脑',670,2),(691,'联想电脑',690,3),(692,'刀叉',656,3),(693,'碗筷',656,3),(696,'客厅专用',628,2);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `version` int(10) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`name`,`age`,`version`,`create_time`,`update_time`) values (1,'李四',22,1,NULL,NULL),(2,'王五',33,1,NULL,NULL),(3,'张三',44,1,NULL,NULL),(4,'开心猫',123,1,NULL,NULL),(5,'dkk',111,1,NULL,NULL),(6,'dkk',24,1,NULL,NULL),(7,'陈小样',123,1,NULL,NULL),(8,'ahsisa',12,1,NULL,NULL),(16,'里斯u昂',122,1,NULL,NULL),(17,'这是17',1,2,'2020-12-18 04:19:07','2020-12-18 12:51:43');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(255) NOT NULL COMMENT '登录名',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `gender` int(11) NOT NULL DEFAULT '1' COMMENT '性别(1:男 0：女)',
  `identity_code` varchar(60) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(80) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `file_name` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK__EASYBUY___C96109CC3A81B327` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`login_name`,`user_name`,`password`,`gender`,`identity_code`,`email`,`mobile`,`file_name`,`create_time`,`update_time`) values (2,'admin','系统管理员','123123',0,'130406198302141869','hello11@bdqn.com','1583233515','7.jpg','2020-05-18 06:22:27','2020-05-18 06:22:32'),(10,'cgn','张飞','123',1,'140225189987854589','1044732267@qq.com','13366055011','2.jpg','2020-05-18 06:22:34','2020-05-18 06:22:37'),(11,'hyl','曹操','1231',1,'140225198874584539','1044732267@qq.com','13366055010','3.jpg','2020-05-18 06:22:35','2020-05-18 06:22:37'),(12,'ck','关羽','123',1,'140225189987854589','1044732267@qq.com','13366055010','4.jpg','2020-05-18 06:22:36','2020-05-18 06:22:38'),(13,'www','叶晨','123456qwe',0,'412727199909091234','2677909066@qq.com','15745674895',NULL,'2020-12-30 20:19:59','2020-12-30 20:19:59'),(16,'ct','逻辑鬼才','123qwe',0,'414141199909091234','2677909066@qq.com','15712345671','4.jpg','2020-12-30 20:32:43','2020-12-30 20:32:43'),(17,'qw','叶晨','12345qqw',1,'414141199909091234','aa123@qq.com','15712345671',NULL,'2020-12-30 20:52:08','2020-12-30 20:52:08');

/*Table structure for table `user_address` */

DROP TABLE IF EXISTS `user_address`;

CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户主键',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `isdefault` int(11) DEFAULT '0' COMMENT '是否是默认地址（1:是 0否）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

/*Data for the table `user_address` */

insert  into `user_address`(`id`,`user_id`,`address`,`remark`,`isdefault`,`create_time`,`update_time`) values (11,16,'广州市二沙岛','朋友家',0,'2019-06-03 02:32:39','2021-01-02 14:06:37'),(15,16,'深信服','公司',0,'2021-01-01 21:21:05','2021-01-02 21:21:08'),(34,16,'郑州市花园区','学校',0,'2019-05-30 20:21:32','2021-01-02 14:06:49'),(35,16,'东莞松山湖','公司',0,'2019-06-02 20:06:41','2021-01-02 16:37:13'),(50,16,'北京搜狗','公司',1,'2021-01-02 16:37:14','2021-01-02 16:37:14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
