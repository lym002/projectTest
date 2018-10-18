package com.jsjf.service.integral.impl;

import com.jsjf.common.SystemConstant;
import com.jsjf.common.Utils;
import com.jsjf.dao.integral.TaskIntegralRulesDao;
import com.jsjf.dao.member.DrMemberFundsRecordDAO;
import com.jsjf.model.integral.TaskIntegralRules;
import com.jsjf.service.integral.TaskIntegralRulesService;
import com.jsjf.service.integral.UserDetailIntegralService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 增加任务积分
     *
     * @param uid
     * @param taskType
     */
    @Override
    public void addPoints(Integer uid, String taskType, Integer amount) {
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


}