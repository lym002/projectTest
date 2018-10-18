package com.jsjf.service.activity;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.activity.JsChannelCouponPut;

public interface JsChannelCouponPutService {
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public JsChannelCouponPut selectObjectById(Integer id);
	/**
	 * 添加
	 * @param jsChannelCouponPut
	 */
	public void insert (JsChannelCouponPut jsChannelCouponPut);
	/**
	 * 修改
	 * @param jsChannelCouponPut
	 */
	public void update (JsChannelCouponPut jsChannelCouponPut);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public PageInfo selectObjectList(PageInfo info ,JsChannelCouponPut jsChannelCouponPut);
	/**
	 * 查询
	 * @param map
	 * @return
	 */
	public PageInfo selectCouponDetailList(PageInfo info ,Integer putId);
	/**
	 * 添加发放任务
	 * @param fileImport
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public BaseResult addCouponPut(MultipartFile fileImport,JsChannelCouponPut jsChannelCouponPut) throws Exception;
	/**
	 * 修改发放任务
	 * @param fileImport
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public BaseResult UpDateCouponPut(MultipartFile fileImport,JsChannelCouponPut put) throws Exception;
	
	/**
	 * 校验数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void checkOutCouponPut(BaseResult result,JsChannelCouponPut put)throws Exception;
	/**
	 *  审核发放渠道优惠券
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void auditCouponPut(BaseResult result,JsChannelCouponPut put)throws Exception;

}
