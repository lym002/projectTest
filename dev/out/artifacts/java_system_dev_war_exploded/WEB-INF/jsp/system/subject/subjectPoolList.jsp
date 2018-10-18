<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="subjectPoolList" title="投资池" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../subject/subjectPoolList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#subjectPoolTools',onBeforeLoad:init">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'code',formatter:formatOperCode" width="15%">标的编号</th>
	        <th data-options="field:'no'" width="10%">债权编号</th>
	       	<th data-options="field:'amount'" width="15%" styler="styleColor" formatter="formatAmount">标的金额(元)</th>
	        <th data-options="field:'remainsAmount'" width="15%" styler="styleColor" formatter="formatAmount">剩余可匹配金额(元)</th>
	        <th data-options="field:'endDate'" width="10%" formatter="formatDate">到期日期</th>
	        <th data-options="field:'mappingCount',formatter:formatOperCount"  width="10%">已匹配产品数量</th>
	        <th data-options="field:'mappingAmount'" styler="styleColor" formatter="formatAmount" width="15%">已匹配金额</th>
	    </tr>
	    </thead>
	</table>
	<div id="subjectPoolTools" style="padding:5px;height:750">
	  	<form id="drSubjectForm">
	  		标的编号:<input id="searchSubjectInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		债权编号:<input id="searchSubjectInfoLname" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		<input id="searchProductCode" name="pcode" value="${pcode}" type="hidden">
	  		募集成功日期<input id="searchSubjectStartDate" name="startDate" class="easyui-datebox" style="width:100px">
	  		到<input id="searchSubjectEndDate" name="endDate"  class="easyui-datebox" style="width:100px">
	    	<a id="searchSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	<div id="showDrSubjectInfoWindow" class="easyui-window"
		data-options="closed:true,modal:true,minimizable:false,maximizable:false" style="padding:10px;">
	</div>
	
<script type="text/javascript">
	var pcode = '${code}';
	function init(param){
		param.pcode = pcode;
	}

	//重置按钮
	$('#resetSubjectInfo').click(function(){
		$("#drSubjectForm").form("reset");
		$("#subjectPoolList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchSubjectInfo').click(function(){
 		$('#subjectPoolList').datagrid({
			queryParams: {
				pcode:$('#searchProductCode').val(''),
				code: $('#searchSubjectInfoCode').val(),
				no: $('#searchSubjectInfoLname').val(),
				startDate:$('#searchSubjectStartDate').datebox('getValue'),
				endDate:$('#searchSubjectEndDate').datebox('getValue'),
			}
		}); 
	});
	
	function formatAmount(value,row,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatDate(value,row,index){
		if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	}
	
	function formatOperCode(value,row,index){
		var id = "'"+row.id+ "'";
		return '<a href="#" onclick="toShowSubjectInfo('+id+')">'+value+'</a>';
	}
	
	function formatOperCount(value,row,index){
		var code = "'"+row.code+ "'";
		return '<a href="#" onclick="toProductInvestList('+code+')">'+value+'</a>';
	}
	
	//跳转标的详情页面
	function toShowSubjectInfo(id){
		$("#showDrSubjectInfoWindow").window({
			title:"标的详情",
			width:$(this).width()*0.5,
			height:$(this).height()*0.6,
			href:"../subject/showDrSubjectInfo.do?id="+id
		});
		$("#showDrSubjectInfoWindow").window("open").window("center");
	}

	
	function toProductInvestList(scode){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "投资详情",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/invest/toInvestList.do?scode="+scode+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		mainTab.tabs("add",detailTab);
	}
	
	
</script>
</body>
</html>

