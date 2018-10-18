﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>币优铺理财 - 慧信财富管理有限公司</title>
	<%@ include file="../../../common/include/util.jsp" %>



</head>
<body style="width:100%;height:100%">
	<table id="redisList" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, 
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#redisTools'">
		<thead>
			<tr>
				<th data-options="field:'key',width:'10%'" >key</th>	
				<th data-options="field:'value',width:'80%'" >value</th>	
			</tr>
		</thead>
	</table>
	<div id="redisTools" style="margin-bottom:5px">
			<form id="redisForm">		
				redisKey:<input id="redisKey" name="redisKey" size="30" class="easyui-textbox"  />
				type:<select id="type" name="type" style="width:100px;" class="easyui-combobox">
						<option value="1">List</option>
						<option value="0">String</option>
						<option value="2">hash</option>
						<option value="3">set</option>
					</select>
				valueType:<select id="valueType" name="valueType" style="width:100px;" class="easyui-combobox">
						<option value="0">binary</option>
						<option value="1">String</option>
					</select>
			</form>
	    	<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
	
</body>

<script type="text/javascript">
	//查询按钮
	$('#searchBtn').click(function(){
 		$('#redisList').datagrid({
 			url:'../gukaimingTest/selectRedis.do',
			queryParams: {
				redisKey: $('#redisKey').textbox('getValue'),
				type: $('#type').combobox('getValue'),
				valueType: $('#valueType').combobox('getValue'),
			}
		}); 
	});
	
</script>
</html>
