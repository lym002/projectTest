package com.jsjf.dao.product;

import java.util.List;

import com.jsjf.model.product.DrProductPic;

public interface DrProductPicDAO {
	
	
 	/**
 	 * 根据条件得到产品图片
 	 * @param pid
 	 * @return List<DrProductPic>
 	 */
     public List<DrProductPic> getDrProductPicByPid(Integer pid); 
     
}
