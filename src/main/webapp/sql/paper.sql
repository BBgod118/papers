/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : paper

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-10-17 13:46:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dataset
-- ----------------------------
DROP TABLE IF EXISTS `dataset`;
CREATE TABLE `dataset` (
  `dataset_id` int(11) NOT NULL AUTO_INCREMENT,
  `data_name` varchar(50) NOT NULL,
  `storage_address` varchar(120) NOT NULL,
  `upload_date` datetime NOT NULL,
  `upload_user_id` int(11) NOT NULL,
  `upload_role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`dataset_id`),
  KEY `fk_upload_data_id` (`upload_user_id`),
  CONSTRAINT `fk_upload_data_id` FOREIGN KEY (`upload_user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dataset
-- ----------------------------
INSERT INTO `dataset` VALUES ('1', '附件1-9.docx', 'D:\\Spring\\papers\\target\\paperManagementSystem-1.0-SNAPSHOT\\upload\\papers\\', '2019-10-15 17:07:06', '7', 'admin');
INSERT INTO `dataset` VALUES ('2', '附件1-9.docx', 'D:\\Spring\\papers\\target\\paperManagementSystem-1.0-SNAPSHOT\\upload\\papers\\', '2019-10-15 17:08:44', '34', 'teacher');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`group_id`),
  KEY `fk_group_teacher_id` (`teacher_id`),
  KEY `fk_group_student_id` (`student_id`),
  CONSTRAINT `fk_group_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_group_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES ('4', '34', '5');
INSERT INTO `group` VALUES ('5', '34', '19');
INSERT INTO `group` VALUES ('6', '35', '28');
INSERT INTO `group` VALUES ('7', '35', '29');

-- ----------------------------
-- Table structure for papers
-- ----------------------------
DROP TABLE IF EXISTS `papers`;
CREATE TABLE `papers` (
  `papers_id` int(11) NOT NULL AUTO_INCREMENT,
  `thesis_title` varchar(30) NOT NULL,
  `storage_address` varchar(120) NOT NULL,
  `upload_date` datetime NOT NULL,
  `student_id` int(11) NOT NULL,
  `paper_topic_id` int(11) NOT NULL,
  PRIMARY KEY (`papers_id`),
  KEY `fk_papers_student_id` (`student_id`),
  KEY `fk_papers_paper_topic_id` (`paper_topic_id`),
  CONSTRAINT `fk_papers_paper_topic_id` FOREIGN KEY (`paper_topic_id`) REFERENCES `paper_topic` (`paper_topic_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_papers_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of papers
-- ----------------------------
INSERT INTO `papers` VALUES ('4', '服装系统', '多福多寿犯得上', '2019-10-22 18:00:00', '5', '5');
INSERT INTO `papers` VALUES ('6', '整合文档.docx', 'D:\\Spring\\papers\\target\\paperManagementSystem-1.0-SNAPSHOT\\upload\\papers\\', '2019-10-14 16:21:42', '19', '6');

-- ----------------------------
-- Table structure for papers_rating
-- ----------------------------
DROP TABLE IF EXISTS `papers_rating`;
CREATE TABLE `papers_rating` (
  `papers_rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `fraction` int(4) NOT NULL,
  `papers_id` int(11) NOT NULL,
  PRIMARY KEY (`papers_rating_id`),
  KEY `fk_papers_id` (`papers_id`),
  CONSTRAINT `fk_papers_id` FOREIGN KEY (`papers_id`) REFERENCES `papers` (`papers_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of papers_rating
-- ----------------------------
INSERT INTO `papers_rating` VALUES ('4', '90', '4');

-- ----------------------------
-- Table structure for paper_topic
-- ----------------------------
DROP TABLE IF EXISTS `paper_topic`;
CREATE TABLE `paper_topic` (
  `paper_topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `paper_topic` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `release_date` datetime NOT NULL,
  `closing_date` datetime NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `state` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`paper_topic_id`),
  KEY `fk_paper_topic_teacher_id` (`teacher_id`),
  CONSTRAINT `fk_paper_topic_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of paper_topic
-- ----------------------------
INSERT INTO `paper_topic` VALUES ('5', '网上服装销售系统', '2019-10-12 17:47:50', '2019-10-22 19:00:00', '34', '1');
INSERT INTO `paper_topic` VALUES ('6', '水果超市系统', '2019-10-13 17:47:47', '2019-10-22 19:00:00', '34', '1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `perms_name` varchar(30) NOT NULL,
  `url` varchar(120) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for presentation
-- ----------------------------
DROP TABLE IF EXISTS `presentation`;
CREATE TABLE `presentation` (
  `presentation_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(30) NOT NULL,
  `upload_date` datetime NOT NULL,
  `file_type` varchar(30) NOT NULL,
  `student_id` int(11) NOT NULL,
  `paper_topic_id` int(11) NOT NULL,
  PRIMARY KEY (`presentation_id`),
  KEY `fk_presentation_student_id` (`student_id`),
  KEY `fk_presentaion_paper_topic_id` (`paper_topic_id`),
  CONSTRAINT `fk_presentaion_paper_topic_id` FOREIGN KEY (`paper_topic_id`) REFERENCES `paper_topic` (`paper_topic_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_presentation_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of presentation
-- ----------------------------
INSERT INTO `presentation` VALUES ('3', '服装系统开题报告', '2019-10-30 15:00:00', 'opening_question', '5', '5');
INSERT INTO `presentation` VALUES ('4', '服装系统中期报告', '2019-10-30 16:00:00', 'medium_term', '5', '5');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL,
  `remarks` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'student', '学生');
INSERT INTO `role` VALUES ('2', 'teacher', '教师');
INSERT INTO `role` VALUES ('3', 'admin', '管理员');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_permission_id`),
  KEY `fk_role_id_two` (`role_id`),
  KEY `fk_permission_id` (`permission_id`),
  CONSTRAINT `fk_permission_id` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_role_id_two` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for student_information
-- ----------------------------
DROP TABLE IF EXISTS `student_information`;
CREATE TABLE `student_information` (
  `student_information_id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(30) NOT NULL DEFAULT 'null',
  `department` varchar(30) NOT NULL DEFAULT 'null',
  `classes` varchar(30) NOT NULL DEFAULT 'null',
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`student_information_id`),
  KEY `fk_student_information_id` (`student_id`),
  CONSTRAINT `fk_student_information_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student_information
-- ----------------------------
INSERT INTO `student_information` VALUES ('1', '2017级', '信息工程系', '软件17-4', '5');
INSERT INTO `student_information` VALUES ('11', '2017级', '信息工程系', '软件17-2', '19');
INSERT INTO `student_information` VALUES ('18', '2017级', '信息工程系', '软件1班', '28');
INSERT INTO `student_information` VALUES ('19', '2017级', '信息工程系', '软件4班', '29');
INSERT INTO `student_information` VALUES ('20', '2017级', '道桥系', '桥梁2班', '30');
INSERT INTO `student_information` VALUES ('21', '2017级', '轨道工程系', '轨道3班', '31');
INSERT INTO `student_information` VALUES ('22', '2017级', '轨道工程系', '轨道3班', '32');
INSERT INTO `student_information` VALUES ('23', '2017级', '建筑系', '建筑5班', '33');

-- ----------------------------
-- Table structure for student_selected_topic
-- ----------------------------
DROP TABLE IF EXISTS `student_selected_topic`;
CREATE TABLE `student_selected_topic` (
  `student_selected_topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `paper_topic_id` int(11) NOT NULL,
  PRIMARY KEY (`student_selected_topic_id`),
  KEY `fk_student_selected_topic_id` (`student_id`),
  KEY `fk_paper_topic_id` (`paper_topic_id`),
  CONSTRAINT `fk_paper_topic_id` FOREIGN KEY (`paper_topic_id`) REFERENCES `paper_topic` (`paper_topic_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student_selected_topic
-- ----------------------------
INSERT INTO `student_selected_topic` VALUES ('1', '5', '5');
INSERT INTO `student_selected_topic` VALUES ('4', '19', '6');

-- ----------------------------
-- Table structure for teacher_information
-- ----------------------------
DROP TABLE IF EXISTS `teacher_information`;
CREATE TABLE `teacher_information` (
  `teacher_information_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_title` varchar(20) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`teacher_information_id`),
  KEY `fk_teacher_information_id` (`teacher_id`),
  CONSTRAINT `fk_teacher_information_id` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teacher_information
-- ----------------------------
INSERT INTO `teacher_information` VALUES ('4', '教授', '34');
INSERT INTO `teacher_information` VALUES ('5', '副教授', '35');
INSERT INTO `teacher_information` VALUES ('6', '副教授', '36');
INSERT INTO `teacher_information` VALUES ('7', '讲师', '37');
INSERT INTO `teacher_information` VALUES ('8', '教授', '38');
INSERT INTO `teacher_information` VALUES ('9', '讲师', '39');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `user_pwd` varchar(120) NOT NULL,
  `real_name` varchar(20) NOT NULL,
  `user_email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'null',
  `user_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'null',
  `state` int(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('5', '20172114', 'cf2e83c269678d6ff02c1bb4856b19b3', '杨涛', 'null', 'null', '1');
INSERT INTO `user` VALUES ('7', 'admin', 'c41d7c66e1b8404545aa3a0ece2006ac', '管理员', 'null', 'null', '1');
INSERT INTO `user` VALUES ('19', '20172115', 'b7ca26dfbf935ef396d448d5026aff83', '李明', 'null', '139986536589', '1');
INSERT INTO `user` VALUES ('28', '20172221', '71343d192939c3ff0ce179974e9b1ea2', '杜超', 'null', '18998756542', '1');
INSERT INTO `user` VALUES ('29', '20172125', 'd95b3625039bb2dde0c17de095bb7703', '张惠妹', 'null', '13256897854', '1');
INSERT INTO `user` VALUES ('30', '20172116', '57c2dbaa98b60f28215139611a8507a2', '李刚', 'null', '12358969875', '1');
INSERT INTO `user` VALUES ('31', '20172117', '6a2713d6af26024000dcda8d0c9c4ab0', '张惠妹', 'null', '13991334398', '1');
INSERT INTO `user` VALUES ('32', '20172118', '699231724fdf425f26d4aeda88ffdd7d', '李波', 'null', '15898756944', '1');
INSERT INTO `user` VALUES ('33', '20172119', '52a60741fa906c49ba8b6493e7416ba5', '王超', 'null', '19987455569', '1');
INSERT INTO `user` VALUES ('34', '101012', 'db802d2c357d8710d43d9b93ec289e29', '何继超', 'null', '18998756542', '1');
INSERT INTO `user` VALUES ('35', '101013', '922bba6b567b0068a703165b9ea3cd80', '张红梅', 'null', '13256897854', '1');
INSERT INTO `user` VALUES ('36', '101014', '43bc302be69372a4329749bc59a5e970', '张毅', 'null', '12358969875', '1');
INSERT INTO `user` VALUES ('37', '101015', 'a7b0e2a423d398bbe3bd99f342541ecb', '何万军', 'null', '13991334398', '1');
INSERT INTO `user` VALUES ('38', '101016', 'a50d8cded53930052678193e86088ab7', '华国杨', 'null', '15898756944', '1');
INSERT INTO `user` VALUES ('39', '101017', 'd8035969a2dfad00a8cf67316d16b1a2', '史宏湖', 'null', '19987455569', '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('4', '5', '1');
INSERT INTO `user_role` VALUES ('6', '7', '3');
INSERT INTO `user_role` VALUES ('22', '19', '1');
INSERT INTO `user_role` VALUES ('30', '28', '1');
INSERT INTO `user_role` VALUES ('31', '29', '1');
INSERT INTO `user_role` VALUES ('32', '30', '1');
INSERT INTO `user_role` VALUES ('33', '31', '1');
INSERT INTO `user_role` VALUES ('34', '32', '1');
INSERT INTO `user_role` VALUES ('35', '33', '1');
INSERT INTO `user_role` VALUES ('36', '34', '2');
INSERT INTO `user_role` VALUES ('37', '35', '2');
INSERT INTO `user_role` VALUES ('38', '36', '2');
INSERT INTO `user_role` VALUES ('39', '37', '2');
INSERT INTO `user_role` VALUES ('40', '38', '2');
INSERT INTO `user_role` VALUES ('41', '39', '2');
