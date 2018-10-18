CREATE PROCEDURE `user_Analysis_2`()
begin
declare increaseUser int default 0;
declare totalUser_db int default 0;
declare totalRegister int default 0;
declare increaseInvester int default 0;
declare TotalInvester_db int default 0;
declare totalInvester int default 0;
declare increaseAmount decimal(15,4) default 0.0000;
declare TotalAmount_db decimal(15,4) default 0.0000;
declare totalAmount decimal(15,4) default 0.0000;
declare waitForRepayAmount decimal(15,4) default 0.0000;
declare userAvgAmount decimal(15,4) default 0.0000;
declare nowTime datetime;
select count(*) into increaseUser from dr_member where DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y-%m-%d') = DATE_FORMAT(regdate,'%Y-%m-%d');
select totalUser into totalUser_db from dr_user_analysis where DATE_FORMAT(addDate,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y-%m-%d');
set totalRegister=totalUser_db+increaseUser;
select count(DISTINCT uid) into increaseInvester from dr_product_invest where DATE_SUB(CURDATE(),INTERVAL 1 DAY)=DATE_FORMAT(investTime,'%Y-%m-%d') and status !=2 and uid not in (select distinct uid from dr_product_invest where status != 2 and DATE_SUB(CURDATE(),INTERVAL 1 DAY)> DATE_FORMAT(investTime,'%Y-%m-%d') GROUP BY uid );
select totalInvestUser into TotalInvester_db from dr_user_analysis where DATE_FORMAT(addDate,'%Y-%m-%d')=DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y-%m-%d');
set totalInvester=TotalInvester_db+increaseInvester;
select IFNULL(sum(amount),0) into increaseAmount from dr_product_invest  where DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y-%m-%d') = DATE_FORMAT(investTime,'%Y-%m-%d') and status !=2;
select totalInvestAmount into TotalAmount_db from dr_user_analysis where DATE_FORMAT(addDate,'%Y-%m-%d')=DATE_FORMAT(DATE_SUB(SYSDATE(),INTERVAL 1 DAY),'%Y-%m-%d');
set totalAmount=increaseAmount+TotalAmount_db;
select IFNULL(sum(IFNULL(shouldPrincipal,0)+IFNULL(shouldInterest,0)),0) into waitForRepayAmount from dr_product_invest_repayinfo where status in(0,2);
if totalInvester = 0 then
	set userAvgAmount=0 ;
ELSE
	set userAvgAmount=totalAmount/totalInvester ;
end if;

select current_timestamp() into nowTime;

 insert into dr_user_analysis values(null,increaseUser,totalRegister,increaseInvester,totalInvester,increaseAmount,totalAmount,waitForRepayAmount,userAvgAmount,nowTime);

end;


###创建事件（ user_Analysis ），并调用存储过程（ `user_Analysis_2`() ）
create event if not exists user_Analysis  
on schedule every 1 DAY STARTS '2016-10-01 00:00:01'
on completion preserve  
do call `user_Analysis_2`();
-------------------------------------------------------------------------------------------------dr_user_analysis-------------------------------------------------------------------------------------------------------------------



#存储过程 2 --- js_customer
---------------------------------------------------------------------------------------------------js_customer-------------------------------------------------------------------------------------------------------------------
###创建存储过程

CREATE PROCEDURE `sys_jsjf_js_customer`()
begin
REPLACE INTO js_customer (`uid`,`isbank`,`realname`,`mobilePhone`,`channelName`,`channelType`,`regdate`,`lastlogintime`,`customerStatus`,`customerType`,`isRecharge`,`isInvestment`,`lastPaymentTime`,`meets`,`lastCallTime`,`appointDate`,`balance`,`allot`,`regfrom`,`isFuiou`,`fuiou_balance`,`recommCodes`) 
SELECT mem.uid,if(mem.realverify OR mem.isFuiou,1,0) AS isbank,base.realname,mem.mobilePhone,IFNULL(channel.`name`,mem.toFrom) AS channelName,channel.type AS channelType,mem.regdate,mem.lastlogintime,
       CONCAT(if(st2.uid is NULL,0,1),if(st1.uid is NULL,0,1),IF(tz.uid is NULL,0,1),IF(cz.uid is NULL,0,1),mem.realverify) AS customerStatus,
       IFNULL(wlog.type,10) AS customerType,if(cz.uid is NULL,0,1) AS isRecharge,if(tz.uid is NULL,0,1) AS isInvestment,repay2.shouldTime AS LastPaymentTime,IFNULL(wlog.cs,0) AS meets,
       wlog.calldate AS lastCallTime,wlog2.appointDate,fund.balance,mem.allot,mem.regfrom,mem.isFuiou,fund.fuiou_balance,mem.recommCodes
FROM dr_member mem
LEFT JOIN dr_member_funds fund ON mem.uid = fund.uid
LEFT JOIN dr_member_base_info base ON mem.uid = base.uid
LEFT JOIN dr_channel_info channel ON LOCATE(channel.CODE,mem.toFrom)=1
LEFT JOIN (SELECT i.uid 
FROM (
SELECT min(pi.id) minid,pi.uid FROM dr_product_invest pi
LEFT JOIN dr_product_info pinfo ON pi.pid = pinfo.id
WHERE pinfo.type != 5 AND pi.`status` != 2  GROUP BY pi.uid ) i 
LEFT JOIN dr_product_invest invest on invest.id = i.minid
LEFT JOIN dr_product_info p ON invest.pid = p.id 
WHERE p.type = 3) st1 ON mem.uid = st1.uid
LEFT JOIN (SELECT i.uid 
FROM (
SELECT min(pi.id) minid,pi.uid FROM dr_product_invest pi
LEFT JOIN dr_product_info pinfo ON pi.pid = pinfo.id
WHERE pinfo.type != 5 AND pi.`status` != 2  GROUP BY pi.uid ) i 
LEFT JOIN dr_product_invest invest on invest.id = i.minid
LEFT JOIN dr_product_info p ON invest.pid = p.id 
WHERE p.type != 3) st2 ON mem.uid = st2.uid
LEFT JOIN (SELECT uid FROM dr_member_crush WHERE `status` = 1 GROUP BY uid) cz ON mem.uid = cz.uid
LEFT JOIN (SELECT uid FROM dr_product_invest i LEFT JOIN dr_product_info p ON i.pid = p.id WHERE i.`status` != 2 AND p.type != 5 GROUP BY uid) tz ON mem.uid = tz.uid
LEFT JOIN (SELECT w.moblie,w.type,w.calldate,a.cs FROM (SELECT moblie,MAX(id) AS mwid,COUNT(1) AS cs FROM dr_wincall_log WHERE moblie is not null GROUP BY moblie) a LEFT JOIN dr_wincall_log w ON a.mwid = w.id) wlog ON mem.mobilePhone = wlog.moblie
LEFT JOIN (SELECT w.appointDate,w.moblie FROM (SELECT moblie,MAX(id) AS mwid FROM dr_wincall_log WHERE moblie is not null AND cancelDate is NULL AND cancel_user_ky is NULL GROUP BY moblie) a 
           LEFT JOIN dr_wincall_log w ON a.mwid = w.id) wlog2 ON mem.mobilePhone = wlog2.moblie
LEFT JOIN (SELECT uid,shouldTime FROM (SELECT repay.id AS mid FROM dr_product_invest_repayinfo repay LEFT JOIN dr_product_info product ON repay.pid = product.id 
           WHERE product.type != 5 GROUP BY uid HAVING MAX(shouldTime)) a LEFT JOIN dr_product_invest_repayinfo b ON a.mid = b.id) repay2 ON mem.uid = repay2.uid;
end;

###创建事件（ sys_jsjf_js_customer ），并调用存储过程（ `sys_jsjf_js_customer`() ）
CREATE EVENT `sys_js_customer`
ON SCHEDULE EVERY 30 MINUTE STARTS '2017-04-01 00:00:00'
ON COMPLETION NOT PRESERVE
ENABLE
DO
CALL `sys_jsjf_js_customer`();
#事件 --- 失效红包
CREATE EVENT `coupon_invalidation_event` ON SCHEDULE EVERY 1 DAY STARTS '2016-06-26 00:00:00' ON COMPLETION PRESERVE ENABLE DO UPDATE `dr_member_favourable` SET `status`=2 WHERE `status`=0 AND `expireDate`<= CURDATE();

ALTER TABLE `js_customer`
ADD COLUMN `recommCodes`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐码' AFTER `fuiou_balance`;