package com.jsjf.service.integral.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.integral.TaskRulesDao;
import com.jsjf.model.integral.InvestRulesBean;
import com.jsjf.model.integral.TaskRulesBean;
import com.jsjf.service.integral.TaskRulesService;

@Service
@Transactional
public class TaskRulesServiceImpl implements TaskRulesService {
	private Logger log = Logger.getLogger(TaskRulesServiceImpl.class);
	
	@Autowired
	private TaskRulesDao taskRulesDao;

	@Override
	public PageInfo queryTaskRulesList(PageInfo info,
			TaskRulesBean taskRulesBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", info.getPageInfo().getOffset());
		map.put("limit",  info.getPageInfo().getLimit());
		
		map.put("taskType", taskRulesBean.getTaskType());
		map.put("isFirstTask", taskRulesBean.getIsFirstTask());
		map.put("orders", "id desc");
		
		List<InvestRulesBean> rows = taskRulesDao.queryTaskRulesList(map);
		int total = taskRulesDao.queryTaskRulesListCount(map);
		
		info.setRows(rows);
		info.setTotal(total);
		
		return info;
		
	}

	@Override
	public BaseResult addTaskRules(TaskRulesBean taskRulesBean) {
		BaseResult br = new BaseResult();
		try{
			taskRulesBean.setAddTime(new Date());
			taskRulesDao.addTaskRules(taskRulesBean);
			br.setSuccess(true);
			br.setMsg("添加成功");
		}catch(Exception e){
			log.error("添加失败", e);
			br.setSuccess(false);
			br.setMsg("添加失败");
		}
		return br;
	}

	@Override
	public BaseResult updateTaskRules(TaskRulesBean taskRulesBean) {
		BaseResult br = new BaseResult();
		try{
			taskRulesBean.setUpdateTime(new Date());
			taskRulesDao.updateTaskRules(taskRulesBean);
			br.setSuccess(true);
			br.setMsg("修改成功");
		}catch(Exception e){
			log.error("修改失败", e);
			br.setSuccess(false);
			br.setMsg("修改失败");
		}
		return br;
	}

	@Override
	public BaseResult deleteTask(String id) {
		BaseResult br = new BaseResult();
		try{
			taskRulesDao.deleteByPrimaryKey(Integer.parseInt(id));
			br.setSuccess(true);
			br.setMsg("删除成功");
		}catch(Exception e){
			log.error("删除失败", e);
			br.setSuccess(false);
			br.setMsg("删除失败");
		}
		return br;
	}

}
