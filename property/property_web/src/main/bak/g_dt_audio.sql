/*
Navicat MySQL Data Transfer

Source Server         : 滴答叫人
Source Server Version : 50520
Source Host           : 115.28.209.17:33061
Source Database       : labor

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2016-02-29 18:00:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `g_dt_audio`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_audio`;
CREATE TABLE `g_dt_audio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '语音名称',
  `path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `size` decimal(11,0) DEFAULT NULL COMMENT '文件大小 单位KB',
  `time` decimal(11,0) DEFAULT NULL COMMENT '播放时间：单位s',
  `mediaid` varchar(100) DEFAULT NULL,
  `uploadwxtime` datetime DEFAULT NULL,
  `roletype` int(11) DEFAULT NULL,
  `primaryid` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_dt_audio
-- ----------------------------

-- ----------------------------
-- Table structure for `g_dt_img`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_img`;
CREATE TABLE `g_dt_img` (
  `imgid` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `groupid` int(11) DEFAULT NULL COMMENT '图片所在组ID（g_dt_imggroup）',
  `name` varchar(900) DEFAULT NULL COMMENT '图片描述名称',
  `img` varchar(400) DEFAULT NULL COMMENT '图片地址',
  `roletype` int(11) DEFAULT NULL COMMENT '1:固特异官方 2：经销商',
  `primaryid` int(11) DEFAULT NULL COMMENT '当roletype 为1时，primaryid 对应的是t_dt_user中的值，当roletype 为2时，primaryid对应的值是t_agency_info中的agencyid',
  `mediaid` varchar(200) DEFAULT NULL COMMENT '微信上传后的标识',
  `uploadwxtime` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`imgid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `g_dt_imgtext`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_imgtext`;
CREATE TABLE `g_dt_imgtext` (
  `imgtextid` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `imgtexttype` int(11) DEFAULT NULL COMMENT '1:单图 2：多图',
  `roletype` int(11) DEFAULT '1' COMMENT '1：固特异官方  2：经销商',
  `primaryid` int(11) DEFAULT NULL COMMENT '1：当roletype为1时，t_dt_user 中的id ',
  `createtime` datetime DEFAULT NULL,
  `mediaid` varchar(100) DEFAULT NULL COMMENT '微信返回的唯一标识',
  `uploadwxtime` datetime DEFAULT NULL COMMENT '微信的创建时间',
  PRIMARY KEY (`imgtextid`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `g_dt_imgtextlist`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_imgtextlist`;
CREATE TABLE `g_dt_imgtextlist` (
  `imgtextlistid` int(11) NOT NULL AUTO_INCREMENT,
  `imgtextid` int(11) DEFAULT NULL COMMENT '编号',
  `title` varchar(800) DEFAULT NULL COMMENT '标题',
  `author` varchar(200) DEFAULT NULL COMMENT '作者',
  `imgurl` varchar(400) DEFAULT NULL COMMENT '图片地址',
  `linkurl` varchar(400) DEFAULT NULL COMMENT '原文连接',
  `content` text COMMENT '正文内容',
  `ifviewcontent` int(11) DEFAULT NULL COMMENT '封面图片显示在正文中',
  `summary` varchar(8000) DEFAULT NULL COMMENT '摘要',
  `ifforward` int(11) DEFAULT NULL COMMENT '是否直接跳转',
  `thumbmediaid` varchar(255) DEFAULT NULL COMMENT '封面图片的缩略图，也需要上传，微信返回的标识',
  `thumbwxtime` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`imgtextlistid`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `g_dt_keyrule`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_keyrule`;
CREATE TABLE `g_dt_keyrule` (
  `ruleid` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `rulename` varchar(200) DEFAULT NULL COMMENT '规则名称',
  `replytype` varchar(20) DEFAULT NULL COMMENT '回复类型',
  `replycontent` varchar(200) DEFAULT NULL COMMENT '回复内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `replycount` int(11) DEFAULT '0' COMMENT '回复次数',
  `ifactive` int(11) DEFAULT '1' COMMENT '1:开启 0：关闭',
  PRIMARY KEY (`ruleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_dt_keyrule
-- ----------------------------

-- ----------------------------
-- Table structure for `g_dt_templet`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_templet`;
CREATE TABLE `g_dt_templet` (
  `templetid` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复ID',
  `templetname` varchar(200) DEFAULT NULL,
  `templettype` varchar(100) DEFAULT NULL COMMENT '参考t_dt_msgtype 返回类型',
  `typeid` int(11) DEFAULT NULL COMMENT '1：菜单相关素材2:关注后回复 3：关键字自动回复 4：未匹配关键字自动回复  5：群发信息',
  PRIMARY KEY (`templetid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `g_dt_textkey`
-- ----------------------------
DROP TABLE IF EXISTS `g_dt_textkey`;
CREATE TABLE `g_dt_textkey` (
  `keyid` int(11) NOT NULL AUTO_INCREMENT COMMENT '关键字 自动递增',
  `name` varchar(200) DEFAULT NULL COMMENT '关键字',
  `matetype` int(11) DEFAULT NULL COMMENT '0:完全匹配 1：模糊匹配',
  `ruleid` int(11) DEFAULT NULL COMMENT '规则编号',
  PRIMARY KEY (`keyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_dt_textkey
-- ----------------------------

-- ----------------------------
-- Table structure for `g_gty_group`
-- ----------------------------
DROP TABLE IF EXISTS `g_gty_group`;
CREATE TABLE `g_gty_group` (
  `groupid` int(11) NOT NULL COMMENT '组ID',
  `groupname` varchar(200) DEFAULT NULL COMMENT '组名称',
  `grouptype` int(11) DEFAULT NULL COMMENT '组类型1:粉丝 3：经销商分组'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `g_sy_notice`
-- ----------------------------
DROP TABLE IF EXISTS `g_sy_notice`;
CREATE TABLE `g_sy_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `templetid` varchar(400) DEFAULT NULL COMMENT '模板ID 对应的表t_dt_templet中的templetid',
  `operator` int(11) DEFAULT NULL COMMENT '操作人',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_sy_notice
-- ----------------------------

-- ----------------------------
-- Table structure for `g_sy_noticesend`
-- ----------------------------
DROP TABLE IF EXISTS `g_sy_noticesend`;
CREATE TABLE `g_sy_noticesend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `msgtype` int(11) DEFAULT NULL COMMENT '消息类型 ',
  `content` varchar(10000) DEFAULT NULL COMMENT '消息具体内容',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  `ifread` int(11) DEFAULT NULL COMMENT '是否阅读',
  `sendstatus` int(11) DEFAULT '0' COMMENT '发送状态:0未审核；1审核通过；2审核拒绝 ；3发送成功 ；4发送失败',
  `groupid` varchar(255) DEFAULT NULL COMMENT '发送的组',
  `roletype` int(11) DEFAULT NULL COMMENT '1：固特异 2：经销商',
  `primaryid` int(11) DEFAULT NULL COMMENT '当roletype 为1时，primaryid 对应的是t_dt_user中的值，当roletype 为2时，primaryid对应的值是t_agency_info中的agencyid',
  `refusereason` int(11) DEFAULT NULL COMMENT '拒绝原因 1 含有法律禁止信息 2 主题不明或描述不清 3重复提交 4 图片使用不当 5 信息不准确 ',
  `otherreason` varchar(1000) DEFAULT NULL COMMENT '其他原因',
  `createtime` datetime DEFAULT NULL,
  `msgid` varchar(255) DEFAULT NULL COMMENT '微信群发的标识',
  `sendcount` int(11) DEFAULT '0' COMMENT '发送成功的粉丝数',
  `totalcount` int(11) DEFAULT '0' COMMENT 'group_id下粉丝数；或者openid_list中的粉丝数',
  `errorcount` int(11) DEFAULT '0' COMMENT '发送失败的粉丝数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for `g_sy_noticeset`
-- ----------------------------
DROP TABLE IF EXISTS `g_sy_noticeset`;
CREATE TABLE `g_sy_noticeset` (
  `noticesetid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `messagecount` int(11) DEFAULT NULL COMMENT '发送数量',
  `roletype` int(11) DEFAULT NULL COMMENT '2：固特异  3：经销商',
  PRIMARY KEY (`noticesetid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



-- ----------------------------
-- Table structure for `t_dt_msgtype`
-- ----------------------------
DROP TABLE IF EXISTS `t_dt_msgtype`;
CREATE TABLE `t_dt_msgtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `key` varchar(200) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `ifactive` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '类型：0 表示请求类型，1表示返回类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dt_msgtype
-- ----------------------------
INSERT INTO `t_dt_msgtype` VALUES ('1', '文本', 'txt', '请求消息类型：文本', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('2', '点击事件', 'click', '请求消息类型：点击', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('3', '图文消息', 'article', '请求消息类型：图文', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('4', '地理位置', 'location', '请求消息类型：地理', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('5', '图片', 'img', '请求消息类型：图片', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('6', '链接指令', 'link', '请求消息类型：链接', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('7', '菜单', 'view', '菜单', '1', '0');
INSERT INTO `t_dt_msgtype` VALUES ('8', '文本', 'txt', '返回消息类型：文本', '1', '1');
INSERT INTO `t_dt_msgtype` VALUES ('9', '图片', 'img', '返回消息类型：图片', '1', '1');
INSERT INTO `t_dt_msgtype` VALUES ('10', '图文消息', 'article', '返回消息类型：图文', '1', '1');
INSERT INTO `t_dt_msgtype` VALUES ('11', '链接', 'link', '返回消息类型：链接', '1', '1');
INSERT INTO `t_dt_msgtype` VALUES ('12', '语音', 'audio', '返回消息类型：语音', '1', '1');
INSERT INTO `t_dt_msgtype` VALUES ('13', '语音', 'audio', '请求消息类型：语音', '1', '0');