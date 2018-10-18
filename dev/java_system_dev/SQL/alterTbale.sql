ALTER TABLE dr_member ADD is_byp_old_user INT (4) DEFAULT (1) COMMENT '是否为币优铺老系统导入的用户，1不是老用户  2是老用户 3是老用户登录后并且发送红包' AFTER mchnt_txn_dt;
#3是老用户并且买入了一次理财产品的


alter table dr_user_analysis add not_return_sum_user int(11) COMMENT '在投用户数';
#用户分析表字段添加