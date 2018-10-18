package com.jsjf.dao.product;

import java.util.List;
import java.util.Map;

public interface JsProductPrizeWishDAO {
	
	/**
	 * 通过id获取信息
	 * @param uid
	 * @return
	 */
	 public Map<String,Object> selectByPrimaryKey(Integer id);
	 
	 
	 /**
	  * 查询列表
	  * @param map
	  * @return
	  */
	 public List<Map<String,Object>> selectObjectList(Map<String,Object> map);
	 /**
	  * 查询总数
	  * @param map
	  * @return
	  */
	 public int selectObjectCount(Map<String,Object> map);

}
