﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
</head>
<body>
<div>
	<form:form id="form" modelAttribute="sysUser" action="${apppath }/user/update.do" method="post" style="align:center">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>登陆ID</td>
	    			<td>
	    				<input name="loginId" value="${sysUser.loginId }" readonly="readonly"/>
	    				<input name="userKy" value="${sysUser.userKy }" readonly="readonly" type="hidden"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>姓名</td>
	    			<td><input name="name" class="easyui-validatebox"  type="text" value="${sysUser.name }" data-options="required:true" /></td>
	    		</tr>
	    		<tr>
	    			<td>手机</td>
	    			<td><input name="mobile" value="${sysUser.mobile }" class="easyui-validatebox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>电话</td>
	    			<td><input name="phone" value="${sysUser.phone }" class="easyui-validatebox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱</td>
	    			<td><input name="email" value="${sysUser.email }" class="easyui-validatebox" data-options="required:true,validType:'email'" /></td>
	    		</tr>
	    		<tr>
	    			<td>地址</td>
	    			<td><input name="address" value="${sysUser.address }" class="easyui-validatebox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>用户角色</td>
	    			<td>
	    				<select value="${sysUser.roleKy }" id="role_ky" class="easyui-combobox" style="width: 150px;"
	    				name="roleKy" data-options="valueField:'roleKy',textField:'roleName',url:'${apppath }/role/roleSelect.do?roleKy=${sysUser.roleKy }'" /> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>状态</td>
	    			<td>
	    				<input type="radio" name="status" value="1" <c:if test="${sysUser.status eq 1 }">checked</c:if> ><span>正常</span>
	    				<input type="radio" name="status" value="0" <c:if test="${sysUser.status eq 0 }">checked</c:if> ><span>禁用</span>
	    			</td>
	    		</tr>
	    	<tr>
					<td>部门</td>
					<td><select id = "deptId" name="deptId" class="easyui-combobox"
						style="width: 100px">
							<option value=''>全部</option>
							<c:forEach items="${deptCode}" var="map">
								<c:if test="${map.key == 1}">
									<option value='${map.key }'  <c:if test="${map.key == sysUser.deptId }">selected="selected"</c:if>>${map.value }</option>
								</c:if>
								<c:if test="${map.key == 2}">
									<option value='${map.key }' <c:if test="${map.key == sysUser.deptId }">selected="selected"</c:if>>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>

			</table>
		</form:form>
		<div style="text-align:center;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submit()">保存</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetPassword()">重置密码</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow()">取消</a>
	    </div>
</div>
<script type="text/javascript">
	function closeWindow(){
		$("#window").window("close");
	}
	function submit(){
		$.ajax({
			url:"${apppath}/user/update.do",
			type:"POST",
			data:$("#form").serialize(),  
			success:function(result){
				var result = eval(result);
				if(result.message=="ok"){
					$.messager.alert("提示信息","修改成功！","info");
					$("#window").window("close");
					$("#form").form("clear");
					$("#userList").datagrid("reload");
				}
			}
		});
	}
	
	function resetPassword(){
		$.ajax({
			url:"${apppath}/user/resetPassword.do",
			type:"POST",
			data:$("#form").serialize(),  
			success:function(result){
				var result = eval(result);
				if(result.message=="ok"){
					$.messager.alert("提示信息","密码重置成功！","info");
					$("#window").window("close");
					$("#form").form("clear");
					$("#userList").datagrid("reload");
				}
			}
		});
	}
</script>
</body>
</html>