/**
 * 修改了dr_member增加了拥有积分的字段默认值为0
 */
ALTER TABLE `dr_member`
ADD COLUMN `user_integral`  int(11) NULL DEFAULT 0 COMMENT '用户拥有积分' AFTER `is_byp_old_user`;
/**
 * 增加了一个奖品兑换所需积分
 */
ALTER TABLE `byp_commodity`
ADD COLUMN `need_points`  int(11) NULL COMMENT '兑换所需积分点数' AFTER `type`;
ALTER TABLE `byp_commodity`
ADD COLUMN `activity_id`  varchar(255) NULL COMMENT '活动对应id:yd1,yd2,yd3...元旦活动id' AFTER `need_points`;
/*
 * 加了注解
 */
ALTER TABLE `byp_activity_integral`
COMMENT='积分类型表';
ALTER TABLE `byp_member_integral`
COMMENT='用户积分明细表';
/*
 * 加入奖品url图
 */
ALTER TABLE `byp_commodity`
ADD COLUMN `product_url`  varchar(255) NULL COMMENT '产品图片地址' AFTER `activity_id`;

/*
  dr_member 的  user_integral 类型改成  decimal
  byp_commodity 的 need_points 类型改成 decimal
  byp_member_integral  amount类型改成 decimal
 */

ALTER TABLE `byp_member_integral`
MODIFY COLUMN `amount`  decimal(12,2) NULL DEFAULT DEFAULT 0.00 COMMENT '数量/金额' AFTER `uid`;

ALTER TABLE `byp_commodity`
MODIFY COLUMN `need_points`  decimal(12,2) NULL DEFAULT DEFAULT 0.00 COMMENT '兑换所需积分点数' AFTER `type`;

ALTER TABLE `dr_member`
MODIFY COLUMN `user_integral`  decimal(12,2) NOT NULL DEFAULT 0.00 COMMENT '用户拥有积分' AFTER `is_byp_old_user`;

/**
 * 用户表增加：可用积分，
 * 上次签到时间，
 * 签到天数
 */
ALTER TABLE `byp`.`dr_member`
  ADD COLUMN `user_integral_use` DECIMAL(12,2) DEFAULT 0.00 NOT NULL COMMENT '用户可用积分' AFTER `user_integral`,
  ADD COLUMN `last_sign_in_time` DATETIME NULL COMMENT '用户上次签到时间' AFTER `user_integral_use`,
  ADD COLUMN `sign_in_number_days` INT(11) NULL COMMENT '用户签到天数' AFTER `last_sign_in_time`;

/**
 * 添加字段，库存，红包code
 */
ALTER TABLE `byp`.`byp_commodity`
  ADD COLUMN `code` VARCHAR(50) DEFAULT '' NULL COMMENT '红包编码' AFTER `product_url`,
  ADD COLUMN `repertory` DECIMAL(9,0) NULL COMMENT '库存' AFTER `code`;
/*
POS机DDL增加
 */
ALTER TABLE `dr_member_crush`
MODIFY COLUMN `type`  tinyint(1) UNSIGNED NULL DEFAULT '' COMMENT '类型，1金运通认证, 2金运通网银 3盛付通认证 4融宝认证 5存管快捷充值 6存管网银充值 7POS机充值' AFTER `sessionToken`;

/*
用户VIP基本信息表添加字段
 */
ALTER TABLE `byp`.`byp_member_vip_info`
  ADD COLUMN `expiration_time` datetime NULL COMMENT '过期时间' AFTER `vip_level`,
  ADD COLUMN `add_time` datetime NULL COMMENT '添加时间' AFTER `expiration_time`,
  ADD COLUMN `update_time` datetime NULL COMMENT '修改时间' AFTER `add_time`,
  ADD COLUMN `is_forbidden` INT(2) DEFAULT 1 NOT NULL COMMENT '是否禁用(0禁用，1启用)' AFTER `update_time`;

/**
添加数量单位
 */
ALTER TABLE `byp`.`byp_commodity_detail`
  ADD COLUMN `commodity_count` INT(11) DEFAULT 1 NULL COMMENT '数量' AFTER `address`;
/**
默认登陆注册有1颗心，字段复用以前的积分字段，该字段为双旦积分字段，更新用户数据为默认值
 */
ALTER TABLE `byp`.`dr_member`
  CHANGE `user_integral` `user_integral` DECIMAL(12,2) DEFAULT 1 NOT NULL COMMENT '用户拥有积分';
UPDATE dr_member SET user_integral = 1;


