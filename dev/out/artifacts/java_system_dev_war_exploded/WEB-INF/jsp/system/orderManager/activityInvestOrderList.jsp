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
	<table id="drInvestOrderList" title="优惠投资订单 -${ drActivityParameter.code}"
	class="easyui-datagrid" style="height:99%;"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../investOrder/activityInvestOrderList.do?id= ${id }',
	    method:'post',rownumbers:true,showFooter: true,
	    pagination:true,toolbar:'#drProductInvestTools'">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="4%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="5%" >手机号码</th>
	        <th data-options="field:'fullName'" width="8%">产品名称</th>
	        <th data-options="field:'investTime'" width="8%">订单时间</th>
	        <th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	        <th data-options="field:'chanelName'" width="5%">注册渠道</th>
	        <th data-options="field:'regdate'" width="8%">注册日期</th>
	        <th data-options="field:'amount'" width="5%" styler="styleColor" formatter="formatAmount">订单金额</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	    	<a id="exportDrProductInvest" href="javascript:exportInvestOrder();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//导出
	function exportInvestOrder(){
		window.location.href="../investOrder/exporActivitytInvestOrderInfo.do?id="+${id};
	}

</script>
</body>
</html>