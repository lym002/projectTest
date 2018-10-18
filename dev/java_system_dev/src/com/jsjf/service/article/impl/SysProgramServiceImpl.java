package com.jsjf.service.article.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsjf.common.BaseResult;
import com.jsjf.common.PageInfo;
import com.jsjf.dao.article.SysProgramDAO;
import com.jsjf.model.article.SysProgram;
import com.jsjf.model.system.SysUsersVo;
import com.jsjf.service.article.SysProgramService;

@Service
@Transactional
public class SysProgramServiceImpl implements SysProgramService {

	@Autowired
	public SysProgramDAO sysProgramDAO;

	@Override
	public BaseResult getSysProgramList(SysProgram sysProgram, PageInfo pi) {
		Map<String,PageInfo> resultMap = new HashMap<String,PageInfo>();
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("offset",pi.getPageInfo().getOffset()); 
		map.put("limit",pi.getPageInfo().getLimit()); 
		List<SysProgram> list = sysProgramDAO.getSysProgramList(map);
		Integer total = sysProgramDAO.getSysProgramCounts(map);
		pi.setTotal(total);
		pi.setRows(list);
		resultMap.put("page", pi);
		BaseResult br = new BaseResult();
		br.setMap(resultMap);
		return br;
	}
	
	@Override
	public BaseResult addSysProgram(SysProgram sysProgram,SysUsersVo usersVo) {
		BaseResult result = new BaseResult();
		try{
			sysProgram.setCreateUser(usersVo.getUserKy().shortValue());
			sysProgramDAO.insertSysProgram(sysProgram);
			result.setSuccess(true);
			result.setMsg("保存成功！");
		}catch (Exception e) {
			result.setErrorMsg("保存失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult updateSysProgram(SysProgram sysProgram, SysUsersVo usersVo) {
		BaseResult result = new BaseResult();
		try{
			sysProgram.setUpdateUser(usersVo.getUserKy().shortValue());
			sysProgramDAO.updateSysProgramById(sysProgram);
			result.setSuccess(true);
			result.setMsg("修改成功！");
		}catch (Exception e) {
			result.setErrorMsg("修改失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult deleteSysProgram(SysProgram sysProgram) {
		BaseResult result = new BaseResult();
		try{
			sysProgram.setStatus((short)0);
			sysProgramDAO.updateSysProgramById(sysProgram);
			result.setSuccess(true);
			result.setMsg("删除成功！");
		}catch (Exception e) {
			result.setErrorMsg("删除失败！");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BaseResult recoverSysProgram(SysProgram sysProgram) {
		BaseResult result = new BaseResult();
		try{
			sysProgram.setStatus((short)1);
			sysProgramDAO.updateSysProgramById(sysProgram);
			result.setSuccess(true);
			result.setMsg("恢复成功！");
		}catch (Exception e) {
			result.setErrorMsg("恢复失败！");
			e.printStackTrace();
		}
		return result;
	}


}
