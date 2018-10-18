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
	<button type="button" onclick="queryProductRaise()">激活满标计息</button>
	<br>
	<br>
	<button type="button" onclick="queryProductRepay()">激活回款</button>
	<br>
	<br>
	<button type="button" onclick="queryTransferBmu()">激活转账</button>
</body>

<script type="text/javascript">
	
	function queryProductRaise(){
	
		$.messager.confirm('操作提示', '确定执行', function(r){
			if(r){
				$.ajax({
						url:"${apppath}/gukaimingTest/queryProductRaise.do",
						type:"POST",
						data:{},
						success:function(result){
							$.messager.alert("提示信息",result.msg);
						}
					});
			}
		
		});
	
	}
	function queryProductRepay(){
		$.messager.confirm('操作提示', '确定执行', function(r){
			if(r){
				$.ajax({
						url:"${apppath}/gukaimingTest/queryProductRepay.do",
						type:"POST",
						data:{},
						success:function(result){
							$.messager.alert("提示信息",result.msg);
						}
					});
			}
		});
	
	}
	function queryTransferBmu(){
		$.messager.confirm('操作提示', '确定执行', function(r){
			if(r){
				$.ajax({
						url:"${apppath}/gukaimingTest/queryTransferBmu.do",
						type:"POST",
						data:{},
						success:function(result){
							$.messager.alert("提示信息",result.msg);
						}
					});
			}
		});
	
	}
	
</script>
</html>
