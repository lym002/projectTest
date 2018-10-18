package com.jsjf.service.product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.model.product.JsProductPrize;
import com.jsjf.model.product.JsProductPrizeOrderShare;

public interface JsproductPrizeService {
	/**
 	 * 添加礼品
 	 * @param  JsProductPrize
 	 * @return void
 	 * @throws SQLException;
 	 */
 	public BaseResult insertJsProductPrize(JsProductPrize jsProductPrize, MultipartFile pcImgUrlV, MultipartFile pcImgUrlH, MultipartFile h5ImgUrlV, MultipartFile h5ImgUrlH,
			MultipartFile pcDetail, MultipartFile h5Detail); 
// 	public BaseResult insertJsProductPrize(JsProductPrize jsProductPrize, List<MultipartFile> list); 
    
	/**
	 * 修改礼品
	 * @param  JsProductPrize
	 * @return void
	 * @throws SQLException;
	 */
	public BaseResult updateJsProductPrize(JsProductPrize jsProductPrize, MultipartFile pcImgUrlV, MultipartFile pcImgUrlH, MultipartFile h5ImgUrlV, MultipartFile h5ImgUrlH,MultipartFile pcDetail,MultipartFile h5Detail); 
	
	/**
	 * 获取礼品列表
	 * @return
	 */
	public BaseResult getJsProductPrizeList(Map<String,Object> param,PageInfo pi);
	
	/**
	 * 复制并添加
	 * @param id
	 */
	public void copyAddPrize(Integer id);
	
	/**
	 * 根据id获取jsproductPrize
	 * @param id
	 * @return
	 */
	public JsProductPrize getJsProductPrizeById(Integer id);
	
	/**
	 * 根据status查找奖品
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> getJsProductPrizeforProduct(Integer status);
	
	/**
	 * 产品修改的查找奖品
	 * @param prizeId
	 * @return
	 */
	public List<Map<String,Object>> getJsProductPrizeforProductUpdate(Integer prizeId);
	
	
	/**
	 * 获取晒单列表
	 * @return
	 */
	public BaseResult getJsProductPrizeOrderShareList(Map<String,Object> param,PageInfo pi);
	/**
	 * 修改晒单
	 * @return
	 */
	public void updateJsProductPrizeOrderShare(JsProductPrizeOrderShare obj);
	
	/***
	 * 添加,修改晒单
	 * @param obj
	 */
	public BaseResult insertUpdateJsProductOrderShare(JsProductPrizeOrderShare obj,MultipartFile h5ImgFile,MultipartFile pcImgFile,boolean isAdd);
	/***
	 * 查询
	 * @param obj
	 */
	public JsProductPrizeOrderShare selectJsProductOrderShare(Integer id);
	
	
	/**
	 * 获取投即送心愿列表
	 * @return
	 */
	public BaseResult getJsProductPrizWish(Map<String,Object> param,PageInfo pi);
	/**
	 * 获取投即送心愿列表
	 * @return
	 */
	public List<Map<String, Object>> getJsProductPrizWishExport(Map<String,Object> param);
	
	/**
	 * 查询用户晒单记录
	 * @param map
	 * @return
	 */
	public BaseResult selectOrderShare(Map<String, Object> map,PageInfo pi);
	
	/**
	 * 修改晒单
	 * @param obj
	 * @return
	 */
	public BaseResult updateOrderShare(JsProductPrizeOrderShare obj) throws SQLException;
	
	
	/**
	 * 下架 逻辑删除
	 */
	public void deleteProductPrize(JsProductPrize jsProductPrize)throws SQLException;
	
}
