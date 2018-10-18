package com.jsjf.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsjf.common.ConfigUtil;
import com.jsjf.common.PropertyUtil;
import com.jsjf.common.Utils;
import com.jsjf.model.system.SysMenuVo;
import com.jsjf.service.system.SysMenuVoService;

@Controller
@RequestMapping("/menu")
public class SysMenuController {
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	SysMenuVoService sysMenuVoService;

	@RequestMapping("/delete")
	public String deleteMenu(@ModelAttribute("sysMenu") SysMenuVo menuVo){
		try {
			SysMenuVo sysMenu = new SysMenuVo();
			sysMenu.setMenuKy(menuVo.getMenuKy());
			sysMenu.setStatus(menuVo.getStatus());
			sysMenuVoService.update(sysMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/toMenuList")
	public String toMenuList(@ModelAttribute("sysMenu") SysMenuVo menuVo,Map<String,Object> model){
		try {
			model.put("itsSystem",  ConfigUtil.dictionaryMap.get(PropertyUtil.getProperties("itsSystem")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "system/sys/menuList";
	}
	
	@RequestMapping("/menuList")
	public String menuList(HttpServletRequest request,HttpServletResponse response){
		List<SysMenuVo> list = sysMenuVoService.getMenuList();
		JSONArray array = JSONArray.fromObject(list);
		
		//对一级菜单添加收缩属性
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			object.put("_parentId", object.get("parent"));//定义父节点
			if(!object.get("parent").toString().equals("0")&&object.get("grade").toString().equals("2")){
				for (int j = 0; j < array.size(); j++) {
					JSONObject objectPar = array.getJSONObject(j);
					if(objectPar.get("state")==null&&object.get("parent").toString().equals(objectPar.get("menuKy").toString())){
						objectPar.put("state", "closed");
					}
				}
			}
		}
		StringBuffer jsonStr = new StringBuffer("{\"total\":" + list.size()
				+ ",\"rows\":");
		jsonStr.append(array.toString()+"}");
		Utils.outJsonStr(jsonStr.toString(),response,"text/html;charset=UTF-8");
		return null;
	}
	
	@RequestMapping("addMenu")
	public void addMenu(HttpServletRequest request,@ModelAttribute("sysMenu") SysMenuVo menuVo){
		sysMenuVoService.insert(menuVo);
	}
	
	@RequestMapping("updateMenu")
	public void updateMenu(HttpServletRequest request,HttpServletResponse response){
		String jsonStr = request.getParameter("str");
		try {
			JSONObject obj = JSONObject.fromObject(jsonStr);
			SysMenuVo menuVo = new SysMenuVo();
			menuVo.setMenuKy(Long.valueOf(obj.get("menuKy").toString()));
			menuVo.setName(obj.get("name").toString());
			menuVo.setUrl(obj.get("url").toString());
			menuVo.setParent(Integer.parseInt(obj.get("parent").toString()));
			menuVo.setPosition(Short.parseShort(obj.get("position").toString()));
			menuVo.setGrade(Integer.parseInt(obj.get("grade").toString()));
			menuVo.setStatus(Short.parseShort(obj.get("status").toString()));
			menuVo.setItsSystem(Integer.parseInt(obj.get("itsSystem").toString()));
			
		
			sysMenuVoService.update(menuVo);
			response.getWriter().print("success");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			try {
				response.getWriter().print("error");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
