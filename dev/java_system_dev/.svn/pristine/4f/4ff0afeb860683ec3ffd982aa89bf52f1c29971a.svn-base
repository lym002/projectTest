<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="drClaimsStatisList" title="债权统计" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../claims/drClaimsStatisList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drClaimsStatisTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'no'" width="10%">合同编号</th>
	        <th data-options="field:'name'" width="10%">债权名称</th>
	        <th data-options="field:'loanAmount'" width="10%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	       	<th data-options="field:'pendRepayAmount'" width="10%" styler="styleColor" formatter="formatAmount">待还金额(元)</th>
	       	<th data-options="field:'surplusAmount'" width="10%" styler="styleColor" formatter="formatAmount">剩余金额(元)</th>
	       	<th data-options="field:'statusName'" width="5%">状态</th>
	       	<th data-options="field:'isFuiou'" width="5%">存管开通</th>
	       	<th data-options="field:'bankName'" width="5%">开户银行</th>
	       	<th data-options="field:'bankNo'" width="5%">卡号</th>
	       	<th data-options="field:'loanDeadline'" width="5%">期限(天)</th>
	        <th data-options="field:'rate'" width="5%">利率(%)</th>
	        <th data-options="field:'code'" width="10%">对应标的编号</th>
	        <th data-options="field:'productCounts'" width="5%">对应产品数量</th>
	        <th data-options="field:'addDate'" width="8%" formatter="formatDateBoxFull">录入时间</th>
	        <th data-options="field:'endDate'" width="8%" formatter="formatDateBoxFull">还款时间</th>
			
	    </tr>
	    </thead>
	</table>
	<div id="drClaimsStatisTools" style="padding:5px;height:750">
	  	<form id="drClaimsStatisForm">
	  		合同编号:<input id="searchClaimsStatisNo" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		债权名称:<input id="searchClaimsStatisName" name="name" class="easyui-textbox"  size="15" style="width:150px"/>
	  		还款日期:<input id="searchClaimsStatisStartDate" name="dueStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchClaimsStatisEndDate" name="dueEndDate" class="easyui-datebox" style="width:100px"/>
	    	<a id="searchClaimsStatis" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetClaimsStatis" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetClaimsStatis').click(function(){
		$("#drClaimsStatisForm").form("reset");
		$("#drClaimsStatisList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchClaimsStatis').click(function(){
 		$('#drClaimsStatisList').datagrid({
			queryParams: {
				no: $('#searchClaimsStatisNo').val(),
				name: $('#searchClaimsStatisName').val(),
				dueStartDate: $('#searchClaimsStatisStartDate').datebox('getValue'),
				dueEndDate: $('#searchClaimsStatisEndDate').datebox('getValue'),
			}
		}); 
	});
</script>
</body>
</html>

