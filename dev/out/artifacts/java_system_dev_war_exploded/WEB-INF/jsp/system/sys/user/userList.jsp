﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>币优铺理财 - 慧信财富管理有限公司</title>
	<%@ include file="../../../common/include/util.jsp" %>

<script type="text/javascript">
	$(function(){
		$("#${tabid} #userList").datagrid({
			title:"用户列表",
			width:"100%",
			singleSelect:true,
			fitColumns:true,
			url:'../user/userList.do',
			columns:[[
				{title:"userKy",field:"userKy",hidden:true},
				{title:"登陆ID",field:"loginId",width:$(this).width() * 0.1,align:"left"},
				{title:"专属ID",field:"recommCodes",width:$(this).width() * 0.05,align:"left"},
				{title:"姓名",field:"name",width:$(this).width() * 0.1,align:"left"},
				{title:"部门",field:"deptId",width:$(this).width() * 0.1,align:"left",
					formatter:function(value,rows){
						if(rows.deptId=="1"){
							return "电销一部";
						}
						else if(rows.deptId =="2"){
							return "电销二部";
						}
				}},
				{title:"手机",field:"mobile",width:$(this).width() * 0.1,align:"left"},
				{title:"用户角色",field:"roleName",width:$(this).width() * 0.1,align:"left"},
				{title:"邮箱",field:"email",width:$(this).width() * 0.1,align:"left"},
				{title:"地址",field:"address",width:$(this).width() * 0.1,align:"left"},
				{title:"注册时间",field:"registertime",width:$(this).width() * 0.15,align:"left",
					formatter:function(value,rows){
						return new Date(parseInt(rows.registertime)).toLocaleString().substr(0,20);     
					}
				},
				{title:"状态",field:"status",width:$(this).width() * 0.05,align:"left",
					formatter:function(value,rows){
						if(rows.status=="1"){
							return "正常";
						}else{
							return "禁用";
						}
				}},
			]],
			pagination:true,
			rownumbers:true,
			toolbar:"#tb"
		});
	});
	
	function searchForm(){
		var o = {};
		 $.each($("#queryForm").form().serializeArray(), function(index) {
				if (o[this['name']]) {
					o[this['name']] = o[this['name']] + "," + this['value'];
				} else {
					o[this['name']] = this['value'];
				}
			});
		 $("#${tabid} #userList").datagrid('load', o);
	}
	function edit(){
		var dg = $("#${tabid} #userList");
		var row  = dg.datagrid("getSelected");
		if(row == null || row ==undefined){
			$.messager.alert("提示","请选择需要编辑的行！");
			return;
		}
		$("#window").window({
			title:"用户修改",
			href:"../user/tomodify.do?userKy="+row.userKy+"&tabid=${tabid}"
		});
		$("#window").window("open");
	}
	function add(){
		$("#window").window({
			title:"用户添加",
			href:"../user/toAdd.do?tabid=${tabid}"
		});
		$("#window").window("open");
	}
	function closeWindow(){
		$("#window").window("close");
	}
</script>

</head>
<body style="width:100%;height:100%">
<div id="${tabid }" style="width:99.9%;height:99%;">
	<table id="userList" style="width:99.9%;height:99%;"></table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">编辑</a>
		</div>
		<div>
			<form id="queryForm">
			姓名: <input class="easyui-textbox" name="name" style="width:150px">
			部门: 
			<select name="deptId" class="easyui-combobox" style="width:100px">
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
			登陆ID: <input class="easyui-textbox" name="loginId" style="width:150px">
			用户角色
			<select id="role_ky" class="easyui-combobox" style="width: 150px;" name="roleKy" 
				data-options="valueField:'roleKy',textField:'roleName',url:'${apppath }/role/roleSelect.do?roleKy=${sysUser.roleKy }'"> 
			</select>
			状态: 
			<select name="status" class="easyui-combobox" style="width:100px">
				<option value="">全部</option>
				<option value="1">可用</option>
				<option value="0">禁用</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm()">查询</a>
			</form>
		</div>
	</div>
	<div id="window" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:300px;height:400px;padding:10px;">
	</div>
</div>
</body>
</html>
