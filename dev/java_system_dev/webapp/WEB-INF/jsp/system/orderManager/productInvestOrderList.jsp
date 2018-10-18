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
	<table id="drInvestOrderList"
	style="height:99%;">
		<thead>
	    <tr>
	        <th data-options="field:'fullName'" width="8%">产品名称</th>
	        <th data-options="field:'investTime'" width="8%">订单时间</th>
	        <th data-options="field:'deadline'" width="5%" >项目周期(天)</th>
	       <th data-options="field:'amount'" width="5%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor" formatter="formatAmount">预计收益</th>
	        <th data-options="field:'status'" width="5%" formatter="formatterStatus">投资状态</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
		<%-- 用户姓名:<input type="text" value="${m.realName}"> --%>
		<%-- 手机号码:<input type="text" value="${m.mobilephone}"	> --%>
		推荐码:<input type="text" value="${m.recommCodes}">
		注册渠道:<input type="text" value="${m.toFrom}">
	  	<form id="drProductInvestForm">
	  		<input  type="hidden" id="uid" name="uid" value="${uid}">
	  		产品名称:<input id="searchDrProductTypeName" name="typeName" class="easyui-textbox"  size="30" style="width:100px"/>
	  		投资状态:<select  id="status" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="0">未处理</option>
				 	<option value="1">已成功</option>
				 	<option value="2">失败</option>
				 	<option value="3">已回款</option>
	           	 </select>
	  		项目周期:<select  id="deadline" name="deadline" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="30">30~40</option>
				 	<option value="60">60</option>
				 	<option value="150">150</option>
				 	<option value="180">180</option>
	           	 </select>
  		  	 订单时间:<input id="searchDrProductInvestStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
  			  <input id="searchDrProductInvestEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	    	<a id="searchInvestOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInvestOrder()">查询</a>
	    	<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetBtn').click(function(){
		$("#drProductInvestForm").form("reset");
		$("#drInvestOrderList").datagrid("load", {uid:$("#uid").val()});
	});
	//查询按钮
	function searchInvestOrder(){
 		$('#drInvestOrderList').datagrid({
 			url: '../investOrder/getProductInvestListByUid.do',
			queryParams: {
				uid:$("#uid").val(),
				fullName:$('#searchDrProductTypeName').val(),
				status:$('#status').combobox('getValue'),
				deadline:$('#deadline').combobox('getValue'),
				startDate:$('#searchDrProductInvestStartDate').combobox('getValue'),
				endDate:$('#searchDrProductInvestEndDate').combobox('getValue'),
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#drProductInvestTools'
		});
	};
	
	//获取当前时间
	function getdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate 
	return currentdate; 
	} 
	
	//首次查询
	window.onload = function(){
		/* $('#searchDrProductInvestStartDate').datebox('setValue',getdate());
		$('#searchDrProductInvestEndDate').datebox('setValue',getdate()); */
		searchInvestOrder();
	}
	
	function formatterStatus(val,row,index){
		if(row.status==0){
			return "未处理";
		}
		if(row.status == 1){
			return "成功";
		}
		if(row.status == 2){
			return "失败";
		}
		if(row.status == 3){
			return "已回款";
		}
	}
</script>
</body>
</html>