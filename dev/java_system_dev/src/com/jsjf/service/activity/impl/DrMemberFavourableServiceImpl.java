package com.jsjf.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.common.Utils;
import com.jsjf.dao.activity.DrActivityParameterDAO;
import com.jsjf.dao.activity.DrMemberFavourableDAO;
import com.jsjf.model.activity.DrActivityParameter;
import com.jsjf.model.activity.DrMemberFavourable;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.activity.DrMemberFavourableService;
@Service
@Transactional
public class DrMemberFavourableServiceImpl implements DrMemberFavourableService {
	@Autowired
	DrActivityParameterDAO drActivityParameterDAO;
	@Autowired
	DrMemberFavourableDAO drMemberFavourableDAO;
	
	@Override
	public void selectActivity(Integer uid,
			DrActivityParameter dap, SysUsersVo usersVo) throws Exception
			{
		dap.setSurplusQty(dap.getSurplusQty()-1);//修改剩余数量
		//修改活动
		drActivityParameterDAO.updateActivityParameter(dap);
		
		//插入发放的表dr_member_favourable
		DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(), uid, dap.getType(), dap.getCode(), dap.getName(), dap.getAmount(), dap.getRaisedRates(),
				dap.getEnableAmount(),  0,Utils.getDayNumOfDate(1-dap.getDeadline()), null,"客服发放",usersVo.getUserKy().intValue(),1,dap.getProductDeadline(),dap.getMultiple());
		
		drMemberFavourableDAO.insertIntoInfo(dmf);
	}

	@Override
	public BaseResult selectFavourableByParam(Map<String, Object> map,
			PageInfo pi) {
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		
		List<DrMemberFavourable> list = drMemberFavourableDAO.getMemberFavourable(map);
		Integer total = drMemberFavourableDAO.getMemberFavourableTotal(map);
		map.clear();
		pi.setTotal(total);
		pi.setRows(list);
		map.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(map);
		return br;
	}
	
	/**
	 * 批量发放加息劵操作
	 */
	@Override
	public void batchSelectActivity(Integer[] uids, DrActivityParameter dap, SysUsersVo usersVo) throws Exception{
 		for(Integer uid :uids){
			
			dap.setSurplusQty(dap.getSurplusQty()-1);//修改剩余数量
			//修改活动
			drActivityParameterDAO.updateActivityParameter(dap);
			
			//插入发放的表dr_member_favourable
			DrMemberFavourable dmf = new DrMemberFavourable(dap.getId(), uid, dap.getType(), dap.getCode(), dap.getName(), dap.getAmount(), dap.getRaisedRates(),
					dap.getEnableAmount(),  0,Utils.getDayNumOfDate(1-dap.getDeadline()), null,"客服发放",usersVo.getUserKy().intValue(),1,dap.getProductDeadline(),dap.getMultiple());
			
			drMemberFavourableDAO.insertIntoInfo(dmf);
		}
	}

}
