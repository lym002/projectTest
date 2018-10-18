package com.jsjf.service.integral.impl;

import com.alibaba.fastjson.JSONObject;
import com.jsjf.common.BaseResult;
import com.jsjf.common.SystemConstant;
import com.jsjf.common.Utils;
import com.jsjf.dao.integral.TaskIntegralRulesDao;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.model.integral.TaskIntegralRules;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.integral.UserDetailIntegralService;
import com.jsjf.service.member.DrMemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TaskIntegralRulesServiceImpl implements TaskIntegralRulesService {

    private transient static Logger log = Logger.getLogger(TaskIntegralRulesServiceImpl.class);

    @Autowired
    private TaskIntegralRulesDao taskIntegralRulesDao;
    @Autowired
    private DrMemberFundsRecordDAO drMemberFundsRecordDAO;
    @Autowired
    private UserDetailIntegralService userDetailIntegralService;
    @Autowired
    private DrMemberService drMemberService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return taskIntegralRulesDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TaskIntegralRules record) {
        return taskIntegralRulesDao.insert(record);
    }

    @Override
    public int insertSelective(TaskIntegralRules record) {
        return taskIntegralRulesDao.insertSelective(record);
    }

    @Override
    public TaskIntegralRules selectByPrimaryKey(Integer id) {
        return taskIntegralRulesDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TaskIntegralRules record) {
        return taskIntegralRulesDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TaskIntegralRules record) {
        return taskIntegralRulesDao.updateByPrimaryKey(record);
    }

    /***
     * 查询任务进度
     * @param uid
     * @return
     */
    @Override
    public BaseResult selectMyTask(Integer uid) {
        BaseResult br = new BaseResult();
        //查询每日任务完成进度参数
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        List<Object> maps = new ArrayList<>();
        Integer taskSize = 0;
        try {
            //查询今天用户的数据
            Date date = new Date();
            param.put("uid", uid);
            param.put("startTime", date);
            param.put("endTime", date);
            List<TaskIntegralRules> taskIntegralRulesList = taskIntegralRulesDao.selectByType(1);
            for (TaskIntegralRules rules : taskIntegralRulesList) {
                boolean flag = false;
                if (rules.getTaskType().equals(SystemConstant.RECHARGE_TYPE)) {
                    //查询今日充值的数据
                    param.put("tradeType", 1);
                    taskSize = drMemberFundsRecordDAO.selectMemberFundsRecordListCountByParam(param);
                    if (taskSize == null) taskSize = 0;
                    if (taskSize > 0) {//完成任务
                        flag = true;
                        taskSize = 1;
                    }
                } else if (rules.getTaskType().equals(SystemConstant.INVEST_TYPE_FIRST)) {
                    param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                    taskSize = drMemberFundsRecordDAO.selectInvestSumByOnlineTime(param);
                    if (taskSize == null) taskSize = 0;
                    if (taskSize > 0) {//完成任务
                        flag = true;
                        taskSize = 1;
                    }
                } else if (rules.getTaskType().equals(SystemConstant.INVEST_TYPE_ANY)){
                    taskSize = userDetailIntegralService.queryTodayInvestTask(uid);
                    if (taskSize == null) taskSize = 0;
                    if (taskSize > 0) {//完成任务
                        flag = true;
                        taskSize = 1;
                    }
                }
                jsonObject = new JSONObject();
                jsonObject.put("taskName", rules.getTaskType());
                jsonObject.put("picUrl", "");
                jsonObject.put("taskStatus", flag);
                jsonObject.put("remark", "");
                jsonObject.put("taskIntegral",rules.getTaskIntegral());
                maps.add(jsonObject);
            }
            //将日常任务存入map
            resultMap.put("dailyTask", maps);
            //清空数据
            param.clear();

            //查询新手任务完成进度
            maps = new ArrayList<>();
            maps = queryNewTask(uid);
            resultMap.put("newUserTask", maps);
            if(Utils.isObjectNotEmpty(uid)){
                DrMember m = drMemberService.selectByPrimaryKey(uid);
                resultMap.put("getRealVerify",m.getIsFuiou());
            }else {
                resultMap.put("getRealVerify",0);
            }
            br.setMap(resultMap);
        }catch (Exception e){
            param.clear();
            jsonObject.clear();
            maps.clear();
            log.error("查询任务错误！",e);
            br.setErrorCode("9999");
            br.setErrorMsg("系统错误！");
        }
        return br;
    }

    /**
     * 查询成长任务
     *
     * @param uid
     * @return
     */
    @Override
    public List<Object> queryNewTask(Integer uid) {
        //查询新手任务完成进度
        List<Object> maps = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        JSONObject jsonObject = null;
        Integer taskSize = 0;
        param.put("uid", uid);
        List<TaskIntegralRules> taskIntegralRulesList = taskIntegralRulesDao.selectByType(0);
        for (TaskIntegralRules rules : taskIntegralRulesList) {
            boolean flag = false;
            if (rules.getTaskType().equals(SystemConstant.RECHARGE_TYPE_FIRST)) {
                //查询今日充值的数据
                param.put("tradeType", 1);
                param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                taskSize = drMemberFundsRecordDAO.selectFristRechar(param);
                if (taskSize == null) taskSize = 0;
                if (taskSize > 0) {//完成任务
                    flag = true;
                }
            } else if (rules.getTaskType().equals(SystemConstant.INVEST_TYPE_FIRST)) { //首次投资
                param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                taskSize = drMemberFundsRecordDAO.selectInvestSumByOnlineTime(param);
                if (taskSize == null) taskSize = 0;
                if (taskSize > 0) {//完成任务
                    flag = true;
                }
            } else if (rules.getTaskType().equals(SystemConstant.INVEST_TYPE)) {  //今日首次投资
                param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                taskSize = drMemberFundsRecordDAO.selectInvestListCountByParam(param);
                if (taskSize == null) taskSize = 0;
                if (taskSize > 0) {//完成任务
                    flag = true;
                }
            } else if (rules.getTaskType().contains(SystemConstant.INVEST_TYPE_CUMULATIVE)){  //累计投资
                //累计投资  上线时间开始获得的累计投资
                String onlineTime = SystemConstant.INTEGRAL_ONLINE_TIME;
                param.clear();
                param.put("addTime", onlineTime);
                param.put("uid",uid);
                Integer sum = drMemberFundsRecordDAO.selectInvestSumByOnlineTime(param);//查询积分上线后的累计额
                if (sum == null) sum = 0;
                if (sum > rules.getTaskMoneyRequire()){
                    flag = true;
                }
            }
            jsonObject = new JSONObject();
            jsonObject.put("taskName", rules.getTaskType());
            jsonObject.put("picUrl", "");
            jsonObject.put("taskStatus", flag);//任务完成状态（true/false）
            jsonObject.put("remark", "");
            jsonObject.put("taskIntegral",rules.getTaskIntegral());
            maps.add(jsonObject);
        }
        param.clear();
        return maps;
    }

    /**
     * 增加任务积分
     *
     * @param uid
     * @param taskType
     */
    @Override
    public void addPoints(Integer uid, String taskType,Integer amount) {
        try {
            Map<String, Object> param = new HashMap<>();
            Integer taskSize = 0;
            Date date = new Date();
            Integer source = 3;
            Integer isOver = 0;
            param.put("uid", uid);
            TaskIntegralRules bean = new TaskIntegralRules();
            switch (taskType) {
                case SystemConstant.RECHARGE_TYPE:
                    param.put("tradeType", 1);
                    param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                    taskSize = drMemberFundsRecordDAO.selectFristRechar(param);
                    if (taskSize == null) taskSize = 0;
                    if (taskSize == 0) {
                        taskType = SystemConstant.RECHARGE_TYPE_FIRST;
                        bean = taskIntegralRulesDao.selectByTaskName(taskType);
                        if (bean != null) {
                            userDetailIntegralService.eranPoints(uid, bean.getTaskIntegral(), source, bean.getId());
                        }
                    }
                    return;
                case SystemConstant.INVEST_TYPE:
                    param.put("startDate", SystemConstant.INTEGRAL_ONLINE_TIME);
                    taskSize = drMemberFundsRecordDAO.selectInvestListCountByParam(param);
                    if (taskSize == null) taskSize = 0;
                    if (taskSize == 0) {
                        taskType = SystemConstant.INVEST_TYPE_FIRST;
                        //如果没有完成任务则加第一次任务
                        bean = taskIntegralRulesDao.selectByTaskName(taskType);
                        if (bean != null) {
                            userDetailIntegralService.eranPoints(uid, bean.getTaskIntegral(), source, bean.getId());
                        }
                    }
                    param.put("addTime", date);
                    param.put("endTime", date);
                    //查看充值和投资任务
                    taskSize = drMemberFundsRecordDAO.selectInvestSumByOnlineTime(param);
                    if (taskSize == null) taskSize = 0;
                    taskSize += amount;
                    taskType = SystemConstant.INVEST_TYPE_ANY;
                    bean = taskIntegralRulesDao.selectByTaskName(taskType);
                    //如果投资总额大于0
                    if (taskSize > 0) {
                        if (bean != null) {
                            isOver = userDetailIntegralService.queryTodayInvestTask(uid);
                            if (taskSize >= bean.getTaskMoneyRequire().intValue() && Utils.isObjectEmpty(isOver)) {//如果今日累计额大于
                                userDetailIntegralService.eranPoints(uid, bean.getTaskIntegral(), source, bean.getId());
                            }
                        }
                    }
                    return;
            }
        }catch (Exception e){
            log.error("任务增加积分出现错误！ uid："+ uid + "," + e.getMessage());
        }
    }

    /**
     * 增加累计积分
     *
     * @param uid
     * @param amountSum
     */
    @Override
    public void addPointsByInvest(Integer uid, Integer amountSum, BigDecimal invest, DrProductInfo info) {
        BigDecimal points = new BigDecimal(0);
        if (info.getPrizeId() != null || info.getType() != 2) {
            return;
        }
        try {
            if (amountSum == null) amountSum = 0;
            Map<String, Object> param = new HashMap<>();
            param.put("taskType", "累计投资");
            param.put("amount", amountSum);
            //查询累计金额给积分
            TaskIntegralRules rules = taskIntegralRulesDao.queryNextTaskByTaskMoney(param);
            param.clear();
            if (rules != null) {
                //查询添加后的累计金额
                param.put("addTime", SystemConstant.INTEGRAL_ONLINE_TIME);
                param.put("uid", uid);
                Integer sum = drMemberFundsRecordDAO.selectInvestSumByOnlineTime(param);//查询积分上线后的累计额
                List<TaskIntegralRules> list = new ArrayList<>();
                param.put("maxAmount", sum);
                param.put("amount", amountSum);
                param.put("taskType", "累计投资");
                list = taskIntegralRulesDao.queryTaskList(param);
                if (sum == null) sum = 0;
                //如果上线后累计额>根据增加金额前的累计额查询 出来的任务额度
                if (sum >= rules.getTaskMoneyRequire()) {
                    for (TaskIntegralRules rule : list) {
                        userDetailIntegralService.eranPoints(uid, rule.getTaskIntegral(), 3, rule.getId());
                    }
                }
            }
                //增加投资积分
                //查询产品期限
                if (info != null) {
                    //投资转换积分 ： 投资优币=投资金额/100*（投资期限（天）/30）
                    points = invest.divide(new BigDecimal(100)).setScale(2).multiply(
                            new BigDecimal(info.getDeadline())
                                    .divide(new BigDecimal(30)).setScale(2)
                    );
                    userDetailIntegralService.eranPoints(uid, points, 1, null);
                } else {
                    log.error("没有该产品！");
                    return;
                }
        } catch (Exception e) {
            log.error("增加投资积分错误", e);
        }
    }

}