/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : teacherselect

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 29/04/2023 16:52:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `adminToken` varchar(50) NOT NULL COMMENT '管理员密钥',
  `phone` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `admin_token` varchar(50) NOT NULL,
  PRIMARY KEY (`adminToken`),
  UNIQUE KEY `adminToken` (`adminToken`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for basicuser
-- ----------------------------
DROP TABLE IF EXISTS `basicuser`;
CREATE TABLE `basicuser` (
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '无名' COMMENT '姓名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号（唯一）',
  `role` int NOT NULL DEFAULT '0' COMMENT '用户角色（学生、老师、管理员）',
  `departmentId` int DEFAULT NULL COMMENT '部门',
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_phone` (`phone`) USING BTREE,
  KEY `login_index` (`phone`,`password`) USING BTREE,
  KEY `role` (`role`),
  KEY `departmentId` (`departmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parentId` int DEFAULT '0' COMMENT '父级部门（顶级部门 0）',
  `departname` varchar(50) DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `departname` (`departname`),
  KEY `parentId` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int NOT NULL COMMENT '唯一主键',
  `rolename` varchar(50) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`roleid`),
  UNIQUE KEY `roleid` (`roleid`),
  KEY `rolename` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentNo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `grade` int DEFAULT NULL COMMENT '年级',
  `cla` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '无班级' COMMENT '班级',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  PRIMARY KEY (`studentNo`) USING BTREE,
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `StudentNo` (`studentNo`),
  KEY `FIND_INDEX` (`grade`,`cla`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生信息表';

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `workNo` varchar(50) NOT NULL COMMENT '工号',
  `phone` varchar(50) NOT NULL COMMENT '手机号',
  `position` varchar(50) NOT NULL DEFAULT '讲师' COMMENT '职位',
  `pro_title` varchar(50) DEFAULT '无' COMMENT '职称',
  `work_no` varchar(50) NOT NULL,
  PRIMARY KEY (`workNo`),
  UNIQUE KEY `workNo` (`workNo`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师信息表';

SET FOREIGN_KEY_CHECKS = 1;
