﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="userAnalysisList" title="基本运营统计"
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../userAnalysis/userAnalysisList.do',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#userAnalysisTools'">
		<thead>
	    <tr>
	        <th data-options="field:'newUser'" width="5%">新增注册用户</th>
	        <th data-options="field:'totalUser'" width="5%" >累计注册用户</th>	     
	       	<th data-options="field:'newInvestUser'" width="5%">新增投资用户</th>
	       	<th data-options="field:'totalInvestUser'" width="5%">累计投资用户</th>
	       	<th data-options="field:'notReturnSumUser'" width="5%">在投用户数</th>
	       	<th data-options="field:'avgUserAmount'" width="10%" styler="styleColor" formatter="formatAmount">平均在投额度</th>	      	
	        <th data-options="field:'newInvestAmount'" width="10%" styler="styleColor" formatter="formatAmount">新增投资额</th>
	       	<th data-options="field:'totalInvestAmount'" width="10%" styler="styleColor" formatter="formatAmount">累计投资额</th>
	        <th data-options="field:'pendRepaymentAmount'" width="10%" styler="styleColor" formatter="formatAmount">待还款金额</th>
	        <th data-options="field:'userAvgInvestAmount'" width="10%" styler="styleColor" formatter="formatAmount">用户平均投资额</th>	        
			<th data-options="field:'addDate'"  width="18%" formatter="formatDateBoxFull">添加时间</th>
	    </tr>
	    </thead>
	</table>
	<div id="userAnalysisTools" style="padding:5px;height:750">
	  	<form id="userAnalysisForm">
	  		查询时间段:<input id="searchUserAnalysisStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchUserAnalysisEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	    	<a id="searchUserAnalysisBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetUserAnalysisBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetUserAnalysisBtn').click(function(){
		$("#userAnalysisForm").form("reset");
		$("#userAnalysisList").datagrid("load", {});
	});
	//查询按钮
	$('#searchUserAnalysisBtn').click(function(){
 		$('#userAnalysisList').datagrid({
			queryParams: {
				startDate: $('#searchUserAnalysisStartDate').datebox('getValue'),
				endDate: $('#searchUserAnalysisEndDate').datebox('getValue'),
			}
		}); 
	});
</script>
</body>
</html>

