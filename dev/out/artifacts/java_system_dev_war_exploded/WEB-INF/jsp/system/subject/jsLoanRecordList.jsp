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
	<table id="getJsLoanRecordList" title="放款记录" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../subject/getJsLoanRecordList.do',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#getJsLoanRecordTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'contractCode'" width="6%">借款合同编号</th>
	        <th data-options="field:'claimsName'" width="10%">资产名称</th>
	        <th data-options="field:'company'" width="12%">企业名称</th>
	        <th data-options="field:'amount'" width="6%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	        <th data-options="field:'loanName'" width="10%">贷款户名</th>
	        <th data-options="field:'bankName'" width="8%">开户行</th>
	        <th data-options="field:'bankNo'" width="8%">银行账号</th>
	        <th data-options="field:'startTime'" width="8%" formatter="formatDateBoxFull">新增日期</th>
	        <th data-options="field:'endTime'" width="8%" formatter="formatDateBoxFull">截止日期</th>
	        <th data-options="field:'claimsLoanStauts'" width="4%" formatter="formatLoanStatus">债权状态</th>
	        <th data-options="field:'addtime'" width="8%" formatter="formatDateBoxFull">放款日期</th>
	        <th data-options="field:'productName'" width="8%">产品名称</th>
	    </tr>
	    </thead>
	</table>
	<div id="getJsLoanRecordTools" style="padding:5px;height:750">
	  	<form id="getJsLoanRecordForm">
	  		合同编号编号:<input id="searchgetJsLoanRecordContractCode" name="contractCode" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品名称:<input id="searchgetJsLoanRecordProductName" name="productName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		企业名称:<input id="searchgetJsLoanRecordCompany" name="company" class="easyui-textbox"  size="15" style="width:200px"/>
	  		资产名称:<input id="searchgetJsLoanRecordClaimsName" name="claimsName" class="easyui-textbox"  size="15" style="width:200px"/>
	    	<a id="searchgetJsLoanRecord" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetgetJsLoanRecord" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetgetJsLoanRecord').click(function(){
		$("#getJsLoanRecordForm").form("reset");
		$("#getJsLoanRecordList").datagrid("load", {});
	});
	function formatLoanStatus (val,row,index){
		if(row.claimsLoanStauts == 5){
			return "待还款";
		}else if(row.claimsLoanStauts ==6){
			return "已还款";
		}else if(row.claimsLoanStauts == 7){
			return "已作废";
		}
	
	} 
	//查询按钮
	$('#searchgetJsLoanRecord').click(function(){
 		$('#getJsLoanRecordList').datagrid({
			queryParams: {
				contractCode: $('#searchgetJsLoanRecordContractCode').val(),
				productName: $('#searchgetJsLoanRecordProductName').val(),
				company: $('#searchgetJsLoanRecordCompany').val(),
				claimsName: $('#searchgetJsLoanRecordClaimsName').val(),
				
			}
		}); 
	});
	

</script>
</body>
</html>

