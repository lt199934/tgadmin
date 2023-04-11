/*
 Navicat Premium Data Transfer

 Source Server         : MySQL5.5
 Source Server Type    : MySQL
 Source Server Version : 50554 (5.5.54)
 Source Host           : localhost:3306
 Source Schema         : tgadmin

 Target Server Type    : MySQL
 Target Server Version : 50554 (5.5.54)
 File Encoding         : 65001

 Date: 08/04/2023 16:28:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'title',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容',
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 'Yarn的安装和使用', '![头像.jpeg](http://localhost:8082/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg)\r\n## 简介\r\n\r\nYarn是facebook发布的一款取代npm的包管理工具。\r\n\r\n官网简介：\r\n\r\n> Yarn 对你的代码来说是一个软件包管理器， 你可以通过它使用全世界开发者的代码，或者分享自己的代码给全世界的开发者。Yarn 做这些事情快捷、安全、可靠，所以你不用担心什么。\r\n\r\n通过 Yarn 你可以使用其他开发者针对不同问题的解决方案，简化你开发软件的过程。 如果使用过程中遇到问题，你可以将其上报或者贡献解决方案。一旦问题被修复， 你可以使用 Yarn 更新。\r\n\r\n代码通过 软件包（package） 的方式被共享。一个软件包里包含了所有需要共享的代码，以及一个描述软件包信息的文件 package.json （叫做 清单）。\r\n\r\n## yarn的特点\r\n1. 速度超快\r\n2. Yarn 缓存了每个下载过的包，所以再次使用时无需重复下载。 同时利用并行下载以最大化资源利用率，因此安装速度更快。\r\n3. 超级安全\r\n4. 在执行代码之前，Yarn 会通过算法校验每个安装包的完整性。\r\n5. 超级可靠\r\n6. 使用详细、简洁的锁文件格式和明确的安装算法，Yarn 能够保证在不同系统上无差异的工作。\r\n## 安装\r\n```\r\nnpm install -g yarn\r\n```\r\n\r\n## 更新\r\n```\r\nyarn set version latest\r\n```\r\n\r\n## 使用\r\n显示命令列表\r\n```\r\nyarn help\r\n```\r\n\r\n初始化一个新项目\r\n```\r\nyarn init\r\n```\r\n\r\n安装所有依赖项\r\n```\r\nyarn\r\nyarn install\r\n```\r\n\r\n添加依赖项\r\n```\r\nyarn add [package]\r\nyarn add [package]@[version]\r\nyarn add [package]@[tag]\r\n```\r\n\r\n将依赖项添加到不同的依赖类别中\r\n```\r\nyarn add [package] --dev  # dev dependencies\r\nyarn add [package] --peer # peer dependencies\r\n```\r\n\r\n更新依赖项\r\n```\r\nyarn up [package]\r\nyarn up [package]@[version]\r\nyarn up [package]@[tag]\r\n```\r\n\r\n删除依赖项\r\n```\r\nyarn remove [package]\r\n更新 Yarn 本体\r\nyarn set version latest\r\nyarn set version from sources\r\n```\r\n', '超级管理员', '2023-03-20 12:38:25');
INSERT INTO `article` VALUES (2, 'dgfdfg', 'dfgfg', '超级管理员', '2023-03-21 03:40:50');
INSERT INTO `article` VALUES (3, 'fdgdfg', 'rertret', '超级管理员', '2023-03-21 03:41:34');

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `category_id` int(11) NOT NULL COMMENT '分类id',
  `aiticle_id` int(11) NOT NULL COMMENT '文章id',
  PRIMARY KEY (`category_id`, `aiticle_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES (1, 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类别名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, NULL, '站长随笔', 'write', '个人博客撰写');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'user', 'el-icon-user', 'icon');
INSERT INTO `sys_dict` VALUES (2, 'menu', 'el-icon-menu', 'icon');
INSERT INTO `sys_dict` VALUES (3, 'setting', 'el-icon-setting', 'icon');
INSERT INTO `sys_dict` VALUES (4, 'collection', 'el-icon-collection', 'icon');
INSERT INTO `sys_dict` VALUES (5, 'delete', 'el-icon-delete', 'icon');
INSERT INTO `sys_dict` VALUES (6, 'document', 'el-icon-document', 'icon');
INSERT INTO `sys_dict` VALUES (7, 'share', 'el-icon-share', 'icon');
INSERT INTO `sys_dict` VALUES (10, 'star-off', 'el-icon-star-off', 'icon');
INSERT INTO `sys_dict` VALUES (11, 'plus', 'el-icon-plus', 'icon');
INSERT INTO `sys_dict` VALUES (12, 'minus', 'el-icon-minus', 'icon');
INSERT INTO `sys_dict` VALUES (13, 'upload2', 'el-icon-upload2', 'icon');
INSERT INTO `sys_dict` VALUES (14, 'download', 'el-icon-download', 'icon');
INSERT INTO `sys_dict` VALUES (15, 'message-solid', 'el-icon-message-solid', 'icon');
INSERT INTO `sys_dict` VALUES (16, 'upload', 'el-icon-upload', 'icon');
INSERT INTO `sys_dict` VALUES (17, 's-home', 'el-icon-s-home', 'icon');
INSERT INTO `sys_dict` VALUES (18, 's-promotion', 'el-icon-s-promotion', 'icon');
INSERT INTO `sys_dict` VALUES (19, 'top', 'el-icon-top', 'icon');
INSERT INTO `sys_dict` VALUES (20, 'bottom', 'el-icon-bottom', 'icon');
INSERT INTO `sys_dict` VALUES (21, 'back', 'el-icon-back', 'icon');
INSERT INTO `sys_dict` VALUES (22, 'right', 'el-icon-right', 'icon');
INSERT INTO `sys_dict` VALUES (23, 'edit-outline', 'el-icon-edit-outline', 'icon');
INSERT INTO `sys_dict` VALUES (24, 'notebook-2', 'el-icon-notebook-2', 'icon');
INSERT INTO `sys_dict` VALUES (25, 'collection-tag', ' el-icon-collection-tag', 'icon');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小（kb）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载路径',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件地址',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件md5',
  `is_Delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除 0：未删除 1：删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用链接  1：可用  0：禁用用',
  `upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_md5`(`md5`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (54, '头像.jpeg', 'jpeg', 83, 'http://localhost:8080/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg', 'F:\\项目\\SpringBoot+vue\\admin\\files\\8ac6e82ffda74cf788b1419c3e9448f3.jpeg', '97df35d0a65b96dfef919b6b2f07b147', 0, 1, '2023-03-19 17:46:35');
INSERT INTO `sys_file` VALUES (55, '笑脸.jpeg', 'jpeg', 30, 'http://localhost:8080/file/e4f6e9bcf3f34b1cad0a63c5008bb7cf.jpeg', 'F:\\项目\\SpringBoot+vue\\admin\\files\\e4f6e9bcf3f34b1cad0a63c5008bb7cf.jpeg', '8e82e171c126ab1aa508dd43c75db94e', 0, 1, '2023-03-19 17:46:42');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路由',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, '欢迎页', '/', 'el-icon-s-home', '主页', 'Index');
INSERT INTO `sys_menu` VALUES (2, NULL, '系统管理', '', 'el-icon-setting', '系统管理', '');
INSERT INTO `sys_menu` VALUES (3, 2, '用户管理', '/user', 'el-icon-s-custom', '用户管理', 'User');
INSERT INTO `sys_menu` VALUES (4, 2, '角色管理', '/role', 'el-icon-user', '角色管理', 'Role');
INSERT INTO `sys_menu` VALUES (5, 2, '字典管理', '/dicts', 'el-icon-star-off', '字典管理', 'Dict');
INSERT INTO `sys_menu` VALUES (6, 2, '菜单管理', '/menu', 'el-icon-menu', '菜单管理', 'Menu');
INSERT INTO `sys_menu` VALUES (7, 2, '文件管理', '/files', 'el-icon-folder', '文件管理', 'Files');
INSERT INTO `sys_menu` VALUES (9, NULL, '文章管理', '', 'el-icon-document', NULL, '');
INSERT INTO `sys_menu` VALUES (10, 9, '所有文章', '/all', 'el-icon-notebook-2', NULL, 'Article');
INSERT INTO `sys_menu` VALUES (11, 9, '写文章', '/write', 'el-icon-edit-outline', NULL, 'Write');
INSERT INTO `sys_menu` VALUES (12, 9, '分类', '/category', 'el-icon-message-solid', '文章分类', 'Category');
INSERT INTO `sys_menu` VALUES (13, NULL, '接口文档', '/doc', 'el-icon-notebook-2', NULL, 'swagger/index');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `flag` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '控制所有权限', 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, '普通用户', '部分权限', 'ROLE_USER');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '123456' COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别（0为女，1为男）',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `online_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '在线状态 0为离线，1为在线',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '账户状态 0为正常，1为异常，2为注销',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1830133764 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '超级管理员', 'http://localhost:8082/file/e4f6e9bcf3f34b1cad0a63c5008bb7cf.jpeg', 'ROLE_ADMIN', 1, 22, 'admin@qq.com', '13456789087', '四川成都双流应龙北二路', 1, 0, '2023-03-12 06:54:44');
INSERT INTO `sys_user` VALUES (2, 'test', 'test', '用户', 'http://localhost:8082/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg', 'ROLE_USER', 1, 22, 'test@163.com', NULL, NULL, 1, 0, '2023-04-12 10:51:25');
INSERT INTO `sys_user` VALUES (3, 'user', '123456', '用火狐好', 'http://localhost:8080/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg', NULL, 0, NULL, '123@123.com', NULL, NULL, 0, 0, '2023-03-12 15:15:30');
INSERT INTO `sys_user` VALUES (4, 'aaa', '123456', NULL, 'http://localhost:8080/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg', NULL, NULL, NULL, NULL, NULL, NULL, 0, 2, '2023-03-12 15:20:52');
INSERT INTO `sys_user` VALUES (5, 'bbb', '123456', NULL, 'http://localhost:8080/file/8ac6e82ffda74cf788b1419c3e9448f3.jpeg', NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, '2023-03-12 15:23:15');
INSERT INTO `sys_user` VALUES (6, 'ccc', '123456', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2023-03-12 15:28:42');
INSERT INTO `sys_user` VALUES (1737859074, 'lb', '123', '李白', '', NULL, 0, 22, 'admin@qq.com', '13456789087', '四川成都双流', 0, 0, '2023-03-14 02:30:46');
INSERT INTO `sys_user` VALUES (1763024897, 'test1', 'test', '用户', '', NULL, 1, 22, 'test@163.com', '', '', 0, 1, '2023-03-14 02:30:46');
INSERT INTO `sys_user` VALUES (1763024898, 'user2', '123456', '用火狐好', '', NULL, 0, 18, '123@123.com', '', '', 0, 0, '2023-03-14 02:30:46');
INSERT INTO `sys_user` VALUES (1830133761, 'swk', '123456', '孙悟空', '', NULL, 1, 23, '', '', '', 0, 2, '2023-03-14 02:30:46');
INSERT INTO `sys_user` VALUES (1830133762, 'dj', '123456', '妲己', '', NULL, 0, 35, '', '', '', 0, 1, '2023-08-14 02:30:46');
INSERT INTO `sys_user` VALUES (1830133763, 'ajl', '123456', '安其拉', '', NULL, 0, 25, '', '', '', 0, 0, '2023-03-14 02:30:46');

SET FOREIGN_KEY_CHECKS = 1;
