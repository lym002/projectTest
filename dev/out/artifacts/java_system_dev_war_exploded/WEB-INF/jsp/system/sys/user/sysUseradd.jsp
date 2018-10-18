﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>用户添加 - 币优铺理财公司数据信息管理中心</title>

</head>
<body>
<div>
	<form:form id="form" modelAttribute="sysUser" method="post" style="align:center">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>登陆ID</td>
	    			<td>
	    				<input name="loginId" class="easyui-textbox" type="text" data-options="required:true"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>姓名</td>
	    			<td><input name="name" class="easyui-textbox" data-options="required:true" /></td>
	    		</tr>
	    		<tr>
	    			<td>手机</td>
	    			<td><input name="mobile" class="easyui-textbox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>电话</td>
	    			<td><input name="phone" class="easyui-textbox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱</td>
	    			<td><input name="email" class="easyui-textbox" data-options="required:true,validType:'email'" /></td>
	    		</tr>
	    		<tr>
	    			<td>地址</td>
	    			<td><input name="address" class="easyui-textbox" data-options="required:true"/></td>
	    		</tr>
	    		<tr>
	    			<td>用户角色</td>
	    			<td>
	    				<select value="${sysUser.roleKy }" id="role_ky" class="easyui-combobox" style="width: 150px;"
	    				name="roleKy" data-options="valueField:'roleKy',textField:'roleName',url:'${apppath }/role/roleSelect.do?roleKy=${sysUser.roleKy }'" /> 
	    			</td>
	    		</tr>
				<tr>
					<td>部门</td>
					<td><select id = "deptId" name="deptId" class="easyui-combobox"
						style="width: 100px">
							<option value=''>全部</option>
							<c:forEach items="${deptCode}" var="map">
								<c:if test="${map.key == 1}">
									<option value='${map.key }'>${map.value }</option>
								</c:if>
								<c:if test="${map.key == 2}">
									<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>

			</table>
		</form:form>
		<div style="text-align:center;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submit()">添加</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow()">取消</a>
	    </div>
</div>
<script type="text/javascript">
	function closeWindow(){
		$("#window").window("close");
	}
	function submit(){
		var flag = $("#form").form('enableValidation').form('validate');
		if(flag){
			$.ajax({
				url:"${apppath}/user/addUser.do",
				type:"POST",
				data:$("#form").serialize(),  
				success:function(result){
					var result = eval(result);
					if(result.message=="ok"){
						$.messager.alert("提示信息","添加成功！");
						$("#window").window("close");
						$("#form").form("clear");
						$("#userList").datagrid("reload");
					}else if(result.message=="moreLoginId"){
						$.messager.alert("提示信息","登陆ID已存在！");
					}else{
						$.messager.alert("提示信息","系统错误");
					}
				}
			});
		}
	}
</script>
</body>
</html>