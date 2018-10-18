package com.jsjf.controller.index;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jsjf.common.BaseResult;
import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.system.SysBanner;
import com.jsjf.service.activity.JsProductReservationService;
import com.jsjf.service.member.DrMemberService;
import com.jsjf.service.product.DrProductInfoService;
import com.jsjf.service.product.DrProductInvestService;
import com.jsjf.service.system.SysArticleService;
import com.jsjf.service.system.SysBannerService;
import com.jsjf.service.system.impl.RedisClientTemplate;

@RequestMapping("/index")
@Controller
public class IndexController {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SysArticleService sysArticleService;
	@Autowired
	private SysBannerService sysBannerService;
	@Autowired
	private DrProductInfoService drProductInfoService;
	@Autowired
	private DrMemberService drMemberService;
	@Autowired
	private DrProductInvestService drProductInvestService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private JsProductReservationService jsProductReservationService;
	
	/**
	 * 首页产品展示
	 * @param req
	 * @return
	 */
	@RequestMapping("/indexProduct")
	@ResponseBody
	public String indexProduct(HttpServletRequest req){
		BaseResult br = new BaseResult();
		try {
			br = drProductInfoService.indexProductInfo();
			//砸蛋活动处理
			DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
			drProductInfoService.eggActivityRuleFilter((List<DrProductInfo>)br.getMap().get("mainPush"),(List<Map<String, Object>>)br.getMap().get("otherPro"),m);
			//约标 
			Map<String,Object> map = new HashMap<String, Object>();
			if(Utils.isObjectNotEmpty(br.getMap().get("activity"))){
				map = jsProductReservationService.
						reservationProduct((DrProductInfo)br.getMap().get("activity"),
											Utils.isObjectEmpty(m)?null:m.getUid());
				((Map<String,Object>)br.getMap()).putAll(map);	
			}
			map.clear();
			String activity_60 = redisClientTemplate.getProperties("activity_60");
			String activity_180 = redisClientTemplate.getProperties("activity_180");
			String activityStartDate = redisClientTemplate.getProperties("activityStartDate");
			String activityEndDate = redisClientTemplate.getProperties("activityEndDate");
			Date nowDate = new Date();
			Date startDate = Utils.parse(activityStartDate, "yyyy-MM-dd HH:mm:ss");
			Date endDate = Utils.parse(activityEndDate, "yyyy-MM-dd HH:mm:ss");
			if(nowDate.after(startDate)&&nowDate.before(endDate)){
				map.put("activity_60", activity_60);
				map.put("activity_180", activity_180);
			}else{
				map.put("activity_60", 0);
				map.put("activity_180", 0);
			}
			//投即送活动
//			map.put("investSendPrizeBannerUrl", redisClientTemplate.getProperties("investSendPrizeBannerUrl"));
//			map.put("investSendPrizeIndexUrl", redisClientTemplate.getProperties("investSendPrizeIndexUrl"));
			map.put("investGiftOn", redisClientTemplate.getProperties("investGiftOn"));//投即送活动开关
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("status", 5);
//			List<DrProductInfo> drProductInfoList = drProductInfoService.selectInvestSendListByParam(param);
			//首页投即送利率、天数等
			map.put("investSendInfo", getInvestInfoMap());
			//聚划算 等本等息
			param.clear();			
			param.put("repayTypes", new Integer[]{3,4});//3等本等息按周回款4等本等息按月回款
			param.put("statuses", new Integer[]{5,6,8,9});//
			param.put("orderStr", " ORDER BY status asc,fullDate DESC,startDate asc ");
			param.put("offset", 0);
			param.put("limit", 1);
			List<DrProductInfo> periodProList = drProductInfoService.selectProductbyMap(param);
			map.put("periodPro", null);
			map.put("periodProInvestCount", 0);
			if(!Utils.isEmptyList(periodProList)){
				map.put("periodPro", periodProList.get(0));
				param.clear();
				param.put("pid", periodProList.get(0).getId());
				map.put("periodProInvestCount", drProductInvestService.selectInvestCountByMap(param));
			}
			
			//视频图片及链接
			String videoImgUrl1 = redisClientTemplate.getProperties("videoImgUrl1");
			String videoImgUrl2 = redisClientTemplate.getProperties("videoImgUrl2");
			String videoUrl1 = redisClientTemplate.getProperties("videoUrl1");
			String videoUrl2 = redisClientTemplate.getProperties("videoUrl2");
			map.put("videoImgUrl1", videoImgUrl1);
			map.put("videoImgUrl2", videoImgUrl2);
			map.put("videoUrl1", videoUrl1);
			map.put("videoUrl2", videoUrl2);
			((Map<String,Object>)br.getMap()).putAll(map);	
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取首页产品信息失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	private Map<String,Object> getInvestInfoMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map.put("rate", redisClientTemplate.getProperties("investRate"));
			map.put("activityRate", redisClientTemplate.getProperties("investActivityRate"));
			map.put("deadline", redisClientTemplate.getProperties("investDeadline"));
			map.put("raiseDeadline", redisClientTemplate.getProperties("investRaiseDeadline"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	/**
	 * 首页banner
	 * @return
	 */
	@RequestMapping("/banner")
	@ResponseBody
	public String indexBanner(){
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", 1);//首页banner
			List<SysBanner>  list = sysBannerService.indexBanner(map);			
			
			map.clear();		
			map.put("banner", list);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取首页banner失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	/**
	 * 首页banner
	 * @return
	 */
	@RequestMapping("/advertisement")
	@ResponseBody
	public String advertisement(){
		BaseResult br = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			map.put("offset", 0);
			map.put("limit", 1);
			map.put("code", 6);//首页banner
			List<SysBanner>  popList = sysBannerService.indexBanner(map);
			map.put("code", 7);//首页banner
			List<SysBanner>  floatList = sysBannerService.indexBanner(map);
			
			map.clear();
			map.put("popList", popList);
			map.put("floatList", floatList);
			br.setMap(map);
			br.setSuccess(true);
		} catch (Exception e) {
			log.error("获取首页banner失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 首页新闻、动态、常见问题
	 * @return
	 */
	@RequestMapping("/indexArticle")
	@ResponseBody
	public String indexArticle(){
		BaseResult br = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 3);
		map.put("proId",1);//公司动态
		List<Map<String,Object>> noticeList = sysArticleService.getIndexArticle(map);
		
		map.clear();
		map.put("offset", 0);
		map.put("limit", 1);
		map.put("ishead", 1);
		map.put("proId",2);//公司新闻
		List<Map<String,Object>> newsList = sysArticleService.getIndexArticle(map);
		
		map.clear();
		map.put("offset", 0);
		map.put("limit", 8);
		map.put("proId",14);//紧急公告
		List<Map<String,Object>> urgentNoticeList = sysArticleService.getIndexArticle(map);
		
		map.clear();
		map.put("notice", noticeList);
		map.put("urgentNotice", urgentNoticeList);
		map.put("news", newsList);
		br.setMap(map);
		br.setSuccess(true);
		return JSON.toJSONString(br);
	}
	
	/**
	 * 注册人数、累计投资金额、累计收益
	 * @return regCount, investCumulative , profitCumulative
	 */
	@RequestMapping("/regAndInvestCount")
	@ResponseBody
	public String regAndInvestCount(){
		BaseResult br = new BaseResult();
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			String regCount = redisClientTemplate.get("regCount");
			String investCumulative = redisClientTemplate.get("investCumulative");
			String profitCumulative = redisClientTemplate.get("profitCumulative");
			if(!StringUtils.isNotEmpty(regCount)){
				map = drMemberService.selectIndexSummaryData();
				redisClientTemplate.setex("regCount",7200 ,map.get("regCount").toString());
				redisClientTemplate.set("investCumulative",map.get("investCumulative").toString());
				redisClientTemplate.set("profitCumulative",map.get("profitCumulative").toString());
			}else{
				map.put("regCount", regCount);
				map.put("investCumulative", investCumulative);
				map.put("profitCumulative", profitCumulative);
			}
			map.put("runTime", Utils.getQuot(new Date(), Utils.format("2016-09-21", "yyyy-MM-dd")));
			br.setMap(map);
			br.setSuccess(true);
		}catch (Exception e) {
			log.error("获取统计数据失败", e);
			br.setSuccess(false);
			br.setErrorCode("9999");
		}
		return JSON.toJSONString(br);
	}
	
	/**
	 * 投资记录
	 * @return
	 */
	@RequestMapping("/indexInvestLogs")
	@ResponseBody
	public String indexInvestLogs(){
		BaseResult br = new BaseResult();
		//投资记录
		Map<String,Object> map = new HashMap<String, Object>();
		PageInfo pi = new PageInfo();
		pi.setPageSize(18);
		br = drProductInvestService.selectInvestLogByParam(map, pi);
		br.setSuccess(true);
		return JSON.toJSONString(br);
	}
	
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest req,Map<String,Object> model){
//		try {
//			DrMember m = (DrMember)req.getSession().getAttribute(ConfigUtil.FRONT_LOGIN_USER);
//			Map<String, Object> map = new HashMap<String, Object>();
//			//banner
//			map.clear();
//			map.put("code", 1);
//			List<SysBanner> bannerList = sysBannerService.indexBanner(map);
//			model.put("bannerList", bannerList);
//			//公告
//			map.clear();
//			map.put("offset", 0);
//			map.put("limit", 5);
//			map.put("proId",PropertyUtil.getProperties("afficheProId").trim());
//			List<SysArticle> noticeList = sysArticleService.getIndexArticle(map);
//			model.put("noticeList", noticeList);
//			//是否有紧急公告
//			map.clear();
//			map.put("offset", 0);
//			map.put("limit", 1);
//			map.put("proId",PropertyUtil.getProperties("urgentProId"));
//			map.put("createTime",new Date());
//			List<SysArticle> urgentNotice = sysArticleService.getIndexArticle(map);
//			if(!Utils.isEmptyList(urgentNotice)){
//				model.put("urgentNotice", urgentNotice.get(0));
//			}
			
//			//新闻
//			map.clear();
//			map.put("offset", 0);
//			map.put("limit", 5);
//			map.put("proIds",new int[]{1,3});
//			List<SysArticle> newsList = sysArticleService.getArticleByParam(map);
//			model.put("newsList", newsList);
//			
//			//赤兔宝
//			List<DrFinancialProduct> ysFinancialProductList = ysFinancialProductService.getIndexList();
//			model.put("ysFinancialProductList", ysFinancialProductList);
//			//30天待还和待借笔数
//			if (Utils.isObjectNotEmpty(m)) {
//				map.clear();
//				map.put("uid", m.getUid());
//				map.put("date", Utils.getDayNumOfAppointDate(new Date(), -30, "yyyy-MM-dd"));
//				model.put("countMap", DrMemberService.queryCollectCountAndStayCount(map));
//			}
//			
//		} catch (Exception e) {
//			log.error("首页读取信息失败", e);
//		}
		return "index";
	}
	
	@RequestMapping("/exit")
	@ResponseBody
	public void exit(HttpServletRequest req){
		req.getSession().invalidate();
	}


}
