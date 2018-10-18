package com.jsjf.controller.task;

import com.jsjf.dao.integral.UserDetailIntegralDao;
import com.jsjf.dao.member.DrMemberDAO;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 积分过期
 *
 * @author admin
 * @create 2018-03-21 9:34
 **/
@Component
public class IntegralTask {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private UserDetailIntegralDao userDetailIntegralDao;
    @Autowired
    private DrMemberDAO drMemberDAO;

    @Scheduled(cron="0 59 23 * * ?")
    public void integralExpiredOn(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (sdf.format(getExpirationTime()).equals(sdf.format(date))) {
            log.info("执行积分过期操作----------------------------start");
            Map<String,Object> param = new HashMap<>();
            List<Map<String,Object>> result = new ArrayList<>();
            param.put("expirationTime",sdf.format(date));
            //获取所有用户过期的积分
            result = userDetailIntegralDao.queryExpirationIntegral(param);
            if (result != null && result.size() >0){
                //执行修改用户积分操作
                for (Map<String, Object> map : result) {
                    drMemberDAO.updateUserIntegralUseByUid(map);
                }
            }
            log.info("执行积分过期操作----------------------------end");
        }
    }

    /**
     * 获取过期时间
     * @return
     */
    public Date getExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = "";
        try {
            calendar.setTime(date);
            if (calendar.get(Calendar.MONTH)+1>6) {
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }else{
                calendar.set(Calendar.MONTH, 5);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            dateString = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendar.getTime());
            date = sdf.parse(dateString);
        }catch (Exception e){
            log.error("过期时间获取错误！",e);
        }
        return date;
    }


}
