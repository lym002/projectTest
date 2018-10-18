package com.jsjf.controller.integral;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.integral.TaskRulesBean;
import com.jsjf.service.integral.TaskRulesService;

@Controller
@RequestMapping("/taskrules")
public class TaskRulesController {
	
	private Logger log = Logger.getLogger(TaskRulesController.class);
	
	@Autowired
	private TaskRulesService taskRulesService;
	
	@RequestMapping("taskRulesView")
	public String investrules(){
		return "/system/integral/taskRulesList";
	}
	
	@RequestMapping("taskRulesList")
	@ResponseBody
	public PageInfo taskRulesList(HttpServletRequest req,TaskRulesBean taskRulesBean,
			Integer page, Integer rows){
		PageInfo info = new PageInfo(page, rows);
		taskRulesService.queryTaskRulesList(info, taskRulesBean);
		
		return info;
	}
	
	@RequestMapping("addTaskRules")
	@ResponseBody
	public BaseResult addTaskRules(HttpServletRequest request,TaskRulesBean taskRulesBean){
		BaseResult br = new BaseResult();
		br = taskRulesService.addTaskRules(taskRulesBean);
		return br;
	}
	
	@RequestMapping("/updateTaskRules")
	@ResponseBody
	public BaseResult updateTaskRules(TaskRulesBean taskRulesBean){
		BaseResult br = new BaseResult();
		br = taskRulesService.updateTaskRules(taskRulesBean);
		return br;
	}
	
	@RequestMapping("/deleteTask")
	@ResponseBody
	public BaseResult deleteTask(String id){
		BaseResult br = new BaseResult();
		br = taskRulesService.deleteTask(id);
		return br;
	}
	
}
