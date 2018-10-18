package com.jsjf.controller.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.system.SysChooseOption;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.member.DrWinCallLogService;
import com.jsjf.service.system.SysChooseOptionService;

@RequestMapping("/membercall")
@Controller
public class DrWinCallLogController {
	private Logger log = Logger.getLogger(DrWinCallLogController.class);
	
	@Autowired
	private DrWinCallLogService callLogService;
	
	@Autowired
	private SysChooseOptionService sysChooseOptionService;
	
	@Autowired
	private DrMemberService drMemberService;
	
	/**
	 * 查询客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/selWincallLog",method = RequestMethod.POST)
	@ResponseBody
	public String selWincallLog(HttpServletRequest req,Integer page,Integer rows,
			@RequestParam(value="moblie",required=false) String moblie,String recommCodes) {
		Map<String,Object> map=new HashMap();
		if((moblie!=null && !moblie.equals("")) || (recommCodes!=null && !recommCodes.equals(""))){
			map.put("moblie",moblie);
			map.put("recommCodes",recommCodes);
		}else{
			map.put("moblie","00000000000000000");
		}
		if(page == null){
			page = PageInfo.DEFAULT_PAGE_ON;
		}
		if(rows == null){
			rows = 5;
		}
		PageInfo pi = new PageInfo(page,rows);
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<Map<String,Object>> list= callLogService.selWincallLog(map);
		int count=callLogService.selWincallLogCount(map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("rows", list);
		resultMap.put("total", count);
		JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	/**
	 * 新增客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping("/savewincalllog")
	@ResponseBody
	public String savewincalllog(HttpServletRequest req,
			@RequestParam(value="moblie",required=false) String moblie,
			@RequestParam(value="remerk",required=false) String remerk,
			/*@RequestParam(value="title",required=false) String title,*/
			@RequestParam(value="calldate",required=false) String calldate,
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="type",required=false) Integer type,
			@RequestParam(value="appointDate",required=false) String appointDate) {
		Map<String,Object> map=new HashMap();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		if(moblie.length()>6){//moblie可能为手机号也可能为推荐码
			map.put("moblie", moblie);//手机号
		}else{//推荐码
			DrMember drMember=new DrMember();
			drMember.setRecommCodes(moblie);
			drMember= drMemberService.selectByMobilephone(drMember);
			map.put("moblie", drMember.getMobilephone());//手机号
		}
		
		map.put("remerk", remerk);
		map.put("title", "");
		map.put("calldate",calldate);
		map.put("name", name);
		map.put("userKy", usersVo.getUserKy());
		map.put("type", type);
		if(appointDate!=null && !appointDate.equals("")){
			map.put("appointDate", appointDate);
		}else{
			map.put("appointDate", null);
		}
		callLogService.insert(map);
		return "success";
	}
	
	
	/**
	 * 查询客户zhuangt
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/seltype",method = RequestMethod.POST)
	@ResponseBody
	public String seltype() {
		List<SysChooseOption> list=sysChooseOptionService.select();
		JSONArray jsonArray=JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	/**
	 * 新增客户的电话记录
	 * @param map
	 * @return
	 */
	@RequestMapping("/updatewincalllog")
	@ResponseBody
	public String updatewincalllog(HttpServletRequest req,
			@RequestParam(value="id",required=false) Integer id) {
		Map<String,Object> map=new HashMap();
		SysUsersVo usersVo = (SysUsersVo) req.getSession().getAttribute(ConfigUtil.ADMIN_LOGIN_USER);
		map.put("userKy", usersVo.getUserKy());
		map.put("id", id);
		callLogService.update(map);
		return "success";
	}
	
}
