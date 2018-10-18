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
	<br><br>
	手机号:<input id = "mobile"type="text" name="mobile"> 
	<br><br>
	<button type="button" onclick="queryDenyMobile(1)">黑名单查询</button>
	<br>
	<br>
<!-- 	<button type="button" onclick="queryDenyMobile(2)">黑名单解除</button> -->
	<br>
	<br>
</body>

<script type="text/javascript">
	
	function queryDenyMobile(type){
		var	mobile = $("#mobile").val();
		if(!mobile){
			$.messager.alert("提示信息","参数不能为空");
			return false;
		}
		$.ajax({
				url:"${apppath}/gukaimingTest/queryRedis.do",
				type:"POST",
				data:{"mobile":mobile,"type":type},
				success:function(result){
					if(result.success){	
						if(type ==1 && '手机存在!'== result.msg){
							$.messager.confirm('操作提示', "手机号:"+mobile+" -->"+result.msg+";确定解除?", function(r){
								if(r){								
									$.ajax({
										url:"${apppath}/gukaimingTest/queryRedis.do",
										type:"POST",
										data:{"mobile":mobile,"type":2},
										dataType:"json",
										success:function(result){
											if(result.success){
												$.messager.alert("提示信息","手机号:"+mobile+" -->"+result.msg);
											}else{
												$.messager.alert("提示信息","手机号:"+mobile+" -->"+result.errorMsg);
											}
										}
								  	});
								}
								return false;
							});
						}else{
							$.messager.alert("提示信息","手机号:"+mobile+" -->"+result.msg);
						}
					}else{
						$.messager.alert("提示信息","手机号:"+mobile+" -->"+result.errorMsg);
					}
				}
			});
	
	}
	
	
</script>
</html>
