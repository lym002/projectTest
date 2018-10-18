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
	<table id="investmentPoolList" title="投资池" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../product/investmentPoolList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#investmentPoolTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'code',formatter:formatOperCode" width="15%">产品编号</th>
	        <th data-options="field:'simpleName'" width="20%">产品名称</th>
	       	<th data-options="field:'rate'" width="5%">产品利率(%)</th>
	        <th data-options="field:'amount'" width="15%" styler="styleColor" formatter="formatAmount">产品金额(元)</th>
	       	<th data-options="field:'repayTypeName'" width="10%">还款方式</th>
	        <th data-options="field:'raiseSuccessDate'" width="10%" formatter="formatDate">募集成功日期</th>
	        <th data-options="field:'mappingCount',formatter:formatOperCount"  width="10%">已匹配标的数量</th>
	        <th data-options="field:'mappingAmount'" styler="styleColor" formatter="formatAmount" width="15%">已匹配金额</th>
	    </tr>
	    </thead>
	</table>
	<div id="investmentPoolTools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
	  		产品编号:<input id="searchDrProductInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品简称:<input id="searchDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
	  						<option value=''>全部</option>
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
	  		募集成功日期<input id="searchDrProductStartDate" name="startDate" class="easyui-datebox" style="width:100px">
	  		到<input id="searchDrProductEndDate" name="endDate"  class="easyui-datebox" style="width:100px">
	    	<a id="searchDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
<script type="text/javascript">

	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		$("#investmentPoolList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrProductInfo').click(function(){
 		$('#investmentPoolList').datagrid({
			queryParams: {
				code: $('#searchDrProductInfoCode').val(),
				simpleName: $('#searchDrProductInfoSimpleName').val(),
				searchStartDate:$('#searchDrProductStartDate').datebox('getValue'),
				searchEndDate:$('#searchDrProductEndDate').datebox('getValue'),
				repayType:$('#repayType').combobox('getValue')
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
		if(value!=''&&value!=null){
			var unixTimestamp = new Date(value);  
			return unixTimestamp.toLocaleString();
		}else{
			return '';
		}
	}
	
	function formatOperCode(value,row,index){
		var code = "'"+row.code+ "'";
		return '<a href="#" onclick="toProductInvest('+code+')">'+value+'</a>';
	}
	
	function formatOperCount(value,row,index){
		var code = "'"+row.code+ "'";
		return '<a href="#" onclick="toSubject('+code+')">'+value+'</a>';
	}
	
	function toProductInvest(code){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "投资详情",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/invest/toDrProductInvestList.do?code="+code+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		mainTab.tabs("add",detailTab);
	}
	
	function toSubject(code){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "标的详情",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/subject/toSubjectPoolList.do?code="+code+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		mainTab.tabs("add",detailTab);
	}
	
	
</script>
</body>
</html>

