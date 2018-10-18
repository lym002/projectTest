/*
Navicat MySQL Data Transfer

Source Server         : byp
Source Server Version : 50505
Source Host           : 192.168.1.11:3306
Source Database       : byp

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-12-08 12:49:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `byp_activity_integral`
-- ----------------------------
DROP TABLE IF EXISTS `byp_activity_integral`;
CREATE TABLE `byp_activity_integral` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activityId` mediumint(11) DEFAULT NULL COMMENT '获取积分的活动Id',
  `type` mediumint(11) DEFAULT NULL COMMENT '积分类型(1.投资获取,2.签到获取,3活动获取)',
  `status` tinyint(2) DEFAULT 1 COMMENT '1可用,2不可用',
  `details` varchar(255) DEFAULT NULL COMMENT '积分详情',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `activityIdsy` (`activityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of byp_activity_integral
-- ----------------------------

/*
Navicat MySQL Data Transfer

Source Server         : byp
Source Server Version : 50505
Source Host           : 192.168.1.11:3306
Source Database       : byp

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-12-08 12:49:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `byp_member_integral`
-- ----------------------------
DROP TABLE IF EXISTS `byp_member_integral`;
CREATE TABLE `byp_member_integral` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `integral_type_id` mediumint(11) DEFAULT NULL,
  `uid` mediumint(11) DEFAULT NULL COMMENT '用户ID',
  `amount` int(11) DEFAULT NULL COMMENT '数量/金额',
  `addtime` datetime DEFAULT NULL COMMENT '添加时间',
  `isDouble` mediumint(11) DEFAULT 1 COMMENT '积分获取倍数(默认值1)',
  PRIMARY KEY (`id`),
  KEY `integral_type_id` (`integral_type_id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of byp_member_integral
-- ----------------------------


CREATE table byp_device_message(
	id  int(11) NOT NULL AUTO_INCREMENT,
	uid mediumint(8) NOT NULL COMMENT '用户id',
	phoneId varchar(25) COMMENT '设备id',
	other varchar(25) COMMENT '其他',
    PRIMARY KEY (`id`),
	KEY `device_key` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;;

-- ----------------------------
-- 积分系统新建的表
-- ----------------------------
-- ----------------------------
-- Table structure for `byp_integral_source`
-- ----------------------------
DROP TABLE IF EXISTS `byp_integral_source`;
CREATE TABLE `byp_integral_source` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`expiration_date`  decimal(2,1) NULL DEFAULT '' ,
`is_using`  int(2) NOT NULL DEFAULT 0 COMMENT '是否启用（0：启用，1：禁用）' ,
`convert_rules`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '兑换规则' ,
`disclaimer`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '免责声明' ,
`add_time`  datetime NOT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT '' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='积分来源表'
AUTO_INCREMENT=2

;

-- ----------------------------
-- Table structure for `byp_invest_rules`
-- ----------------------------
DROP TABLE IF EXISTS `byp_invest_rules`;
CREATE TABLE `byp_invest_rules` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`vip_level`  int(2) NOT NULL COMMENT 'vip等级' ,
`invest_day`  int(4) NOT NULL COMMENT '投资天数' ,
`invest_money`  decimal(12,2) NOT NULL COMMENT '投资金额' ,
`vip_multiple`  decimal(4,2) NULL DEFAULT '' ,
`activity_multiple`  decimal(4,2) NULL DEFAULT '' ,
`add_time`  datetime NOT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT '' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='投资积分规则表'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Table structure for `byp_signin_rules`
-- ----------------------------
DROP TABLE IF EXISTS `byp_signin_rules`;
CREATE TABLE `byp_signin_rules` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`signin_day`  int(11) NOT NULL DEFAULT 0 COMMENT '连续签到天数' ,
`signin_integral`  decimal(12,2) NOT NULL COMMENT '连续签到能获得的积分' ,
`add_time`  datetime NOT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT '' COMMENT '修改时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='签到规则表'
AUTO_INCREMENT=1

;



-- ----------------------------
-- Table structure for `byp_task_integral_rules`
-- ----------------------------
DROP TABLE IF EXISTS `byp_task_integral_rules`;
CREATE TABLE `byp_task_integral_rules` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'id\r\n' ,
`task_type`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类型' ,
`task_integral`  decimal(12,2) NOT NULL COMMENT '任务积分' ,
`is_first_task`  int(2) NOT NULL COMMENT '是否为首次奖励任务（0：是，1：不是）' ,
`task_money_require`  decimal(12,2) NULL DEFAULT '' COMMENT '完成任务对地金额要求' ,
`add_time`  datetime NOT NULL COMMENT '添加时间' ,
`update_time`  datetime NULL DEFAULT '' COMMENT '修改时间' ,
`is_open`  int(2) NULL DEFAULT '' COMMENT '是否启用任务' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='积分任务规则表'
AUTO_INCREMENT=19

;


-- ----------------------------
-- Table structure for `byp_user_detail_integral`
-- ----------------------------
DROP TABLE IF EXISTS `byp_user_detail_integral`;
CREATE TABLE `byp_user_detail_integral` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`uid`  int(11) NOT NULL COMMENT '用户id' ,
`integral_source_id`  int(11) NOT NULL COMMENT '积分来源（1:投资 2：签到 3：任务）' ,
`user_detail_integral`  decimal(12,2) NOT NULL COMMENT '用户积分明细' ,
`expiration_time`  datetime NOT NULL COMMENT '失效时间' ,
`add_time`  datetime NOT NULL COMMENT '记录添加时间' ,
`update_time`  datetime NULL DEFAULT '' COMMENT '修改时间' ,
`task_integral_id`  int(11) NULL DEFAULT '' COMMENT '任务ID' ,
PRIMARY KEY (`id`),
INDEX `uid_index` (`uid`) USING BTREE ,
INDEX `source_id_index` (`integral_source_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='积分明细表'
AUTO_INCREMENT=120

;


/**-------------------头条设备-----------------------**/
-- ----------------------------
-- Table structure for `byp_toutiao`
-- ----------------------------
DROP TABLE IF EXISTS `byp_toutiao`;
CREATE TABLE `byp_toutiao` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `aid` varchar(255) DEFAULT NULL COMMENT '广告计划 id',
  `cid` varchar(255) DEFAULT NULL COMMENT '广告创意 id',
  `csite` varchar(255) DEFAULT NULL COMMENT '广告投放位置1=头条信息流、3=详情页、11=段子 信息流',
  `ctype` varchar(255) DEFAULT NULL COMMENT '创意样式2=小图模式 3=大图式 4=组图模式 5=视频',
  `mac` varchar(255) DEFAULT NULL COMMENT '⽤户终端的 eth0 接⼝的 MAC 地址',
  `mac1` varchar(255) DEFAULT NULL COMMENT '⽤户终端的 eth0 接⼝的 MAC 地址',
  `ua` varchar(255) DEFAULT NULL COMMENT '客户端上报数据 时http 的 header 中的 user_agent',
  `ua1` varchar(255) DEFAULT NULL COMMENT 'user_agent，urlencode 编码',
  `idfa` varchar(255) DEFAULT NULL COMMENT 'iOS IDFA 适用 iOS6及以上',
  `androidid` varchar(255) DEFAULT NULL COMMENT '用户终端的AndroidID,md5 加密',
  `androidid1` varchar(255) DEFAULT NULL COMMENT '用户终端，原值（备注：安卓硬件设备唯一标识）',
  `imei` varchar(255) DEFAULT NULL COMMENT '用户终端的IMEI,15 位数字；取 md5sum 摘要',
  `uuid` varchar(255) DEFAULT NULL COMMENT '用户终端的 UUID；原值（备注：安卓手机系统生成的设备ID）',
  `openudid` varchar(255) DEFAULT NULL COMMENT '原值（备注：IOS 手机用软件生成的一个可变的替代 UDID 的标识，通过第三方的Open UDID SDK 生成）',
  `udid` varchar(255) DEFAULT NULL COMMENT 'iOS UDID,md5 加密，取 md5 摘要（备注：IOS 手机硬件唯一标',
  `os` varchar(255) DEFAULT NULL COMMENT '客户端操作系统的类型0–Android； 1–iOS；2– WP； 3-Others',
  `ip` varchar(255) DEFAULT NULL COMMENT '媒体投放系统获取的用户终端的公共IP 地址',
  `ts` varchar(255) DEFAULT NULL COMMENT '客户端触发监测的时间',
  `convert_id` varchar(255) DEFAULT NULL COMMENT '转化跟踪id',
  `callback_url` varchar(255) DEFAULT NULL COMMENT '激活回调地址（方案一）',
  `callback_param` varchar(255) DEFAULT NULL COMMENT '激活回调参数（方案二）',
  `sign` varchar(255) DEFAULT '' COMMENT '签名',
  `created_time` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;


//-----------2018-02-06vip表--------------

-- ----------------------------
-- Table structure for `byp_member_growth_value_detail`
-- ----------------------------
DROP TABLE IF EXISTS `byp_member_growth_value_detail`;
CREATE TABLE `byp_member_growth_value_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `growth_value` decimal(11,0) DEFAULT NULL COMMENT '成长值详情',
  `created_time` datetime DEFAULT NULL COMMENT '添加时间',
  `growth_value_type` int(11) DEFAULT NULL COMMENT '成长值来源类型（1投资，其他暂定）',
  `growth_value_detail` varchar(255) DEFAULT NULL COMMENT '成长值详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户成长值明细表';


-- ----------------------------
-- Table structure for `byp_vip_activity_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `byp_vip_activity_parameter`;
CREATE TABLE `byp_vip_activity_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员月度红包自增id',
  `vip_level` int(11) NOT NULL COMMENT 'vip等级',
  `code` varchar(255) DEFAULT NULL COMMENT '红包code',
  `is_birthday` int(2) DEFAULT 0 COMMENT '是否是生日红包(0是1否)',
  `is_open` int(2) DEFAULT 0 COMMENT '是否启用该红包',
  `created_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='vip红包信息表';


-- ----------------------------
-- Table structure for `byp_vip_equities`
-- ----------------------------
DROP TABLE IF EXISTS `byp_vip_equities`;
CREATE TABLE `byp_vip_equities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equities_name` varchar(255) NOT NULL COMMENT '权益名称',
  `status` int(11) DEFAULT 0 COMMENT '是否启用',
  `equities_explain` varchar(255) DEFAULT NULL COMMENT '权益说明',
  `open_level` varchar(255) DEFAULT NULL COMMENT '权益开放等级',
  `update_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='vip权益信息表';


-- ----------------------------
-- Table structure for `byp_vip_info`
-- ----------------------------
DROP TABLE IF EXISTS `byp_vip_info`;
CREATE TABLE `byp_vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `vip_level` int(11) NOT NULL DEFAULT 1 COMMENT 'vip等级',
  `vip_name` varchar(255) NOT NULL COMMENT 'vip名称',
  `level_update_red_packet` varchar(255) DEFAULT NULL COMMENT '升级红包（红包code码）',
  `growth_value_min` decimal(11,2) DEFAULT NULL COMMENT '成长值最小',
  `growth_value_max` decimal(11,2) DEFAULT NULL COMMENT '成长值最大',
  `integral_multiply` double(11,1) DEFAULT 1.0 COMMENT '优币翻倍系数',
  `rights_and_interests_id` varchar(255) DEFAULT NULL COMMENT '权益id(多选暂定)',
  `free_withdraw_deposit` int(11) DEFAULT 2 COMMENT '免费提现次数',
  `created_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='vip基础信息表';

-- ----------------------------
-- Table structure for `byp_member_vip_info`
-- ----------------------------
DROP TABLE IF EXISTS `byp_member_vip_info`;
CREATE TABLE `byp_member_vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `growth_value` decimal(11,2) DEFAULT 0.00 COMMENT '成长值',
  `vip_level` int(11) DEFAULT 0 COMMENT 'vip等级，对应vip_info.vip_level',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户vip信息表';

-- ----------------------------
-- Table structure for `byp_vip_equities_member`
-- ----------------------------
DROP TABLE IF EXISTS `byp_vip_equities_member`;
CREATE TABLE `byp_vip_equities_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `mobile_phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `vip_level` int(2) DEFAULT NULL COMMENT 'vip等级',
  `vip_equities_id` int(2) DEFAULT NULL COMMENT '权益id',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='会员权益领取表\r\n';



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `byp_bind_weixin`
--绑定用户微信号
-- ----------------------------
DROP TABLE IF EXISTS `byp_bind_weixin`;
CREATE TABLE `byp_bind_weixin` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) NOT NULL COMMENT '用户ID',
  `open_id` varchar(50) NOT NULL COMMENT '微信openid',
  `is_model_push` char(1) NOT NULL DEFAULT '1' COMMENT '是否开启模板消息通知(1,开启；2，关闭)',
  `add_time` datetime NOT NULL COMMENT '绑定时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openIdUnique` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


CREATE TABLE `byp_member_invite` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `invest_id` int(12) DEFAULT NULL COMMENT '投资id',
  `referrerId` int(12) DEFAULT NULL COMMENT '推荐人ID',
  `user_id` int(12) DEFAULT NULL COMMENT '用户id',
  `invite_bonus` decimal(12,2) DEFAULT NULL COMMENT '投资红利',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `status` int(2) DEFAULT 0 COMMENT '0红利未发放，1红利已发放',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='邀请好友投资返利记录表';