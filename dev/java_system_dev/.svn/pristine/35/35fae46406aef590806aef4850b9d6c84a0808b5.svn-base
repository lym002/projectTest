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
	<table id="collectionDrClaimsInfoList" title="债权催收
	<span style='color: #0015FF;'>共找到</span><span id='collectionDrClaimsCounts' style='color: red;'></span><span style='color: #0015FF;'>条记录</span>,
	<span style='color: #0015FF;'>待还款总金额为</span>：<span id='collectionDrClaimsSum' style='color: red;'></span>" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../claims/collectionDrClaimsLoanList.do',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#collectionDrClaimsInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'no'" width="10%">借款合同编号</th>
	        <th data-options="field:'name'" width="15%">借款产品名称</th>
	       	<th data-options="field:'companyName'" width="15%">借款企业名称</th>
	        <th data-options="field:'loanAmount'" width="10%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	       	<th data-options="field:'receiveInterest'" width="10%" styler="styleColor" formatter="formatAmount">应收利息(元)</th>
	       	<th data-options="field:'pendRepayAmount'" width="10%" styler="styleColor" formatter="formatAmount">待还款金额(元)</th>
	       	<th data-options="field:'rate'" width="4%">借款利率</th>
	        <th data-options="field:'statusName'" width="4%">当前状态</th>
	        <th data-options="field:'endDate'" width="8%" formatter="formatDateBoxFull">到期日期</th>
			<th data-options="field:'operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="collectionDrClaimsInfoTools" style="padding:5px;height:750">
	  	<form id="collectionDrClaimsInfoForm">
	  		到期日期:<input id="searchCollectionClaimsLoanDueStartDate" name="dueStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchCollectionClaimsLoanDueEndDate" name="dueEndDate" class="easyui-datebox" style="width:100px"/>
	  		当前状态: <select  id="searchCollectionClaimsLoanStatus" name="status" style="width:100px;" class="easyui-combobox">
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 5 }">
								<option value='${map.key}'>${map.value }</option>
							</c:if>
						</c:forEach>
	           		</select>
	    	<a id="searchCollectionClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetCollectionClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="collectionDrClaimsInfoDown()">下载</a>	
	    </form>
	</div>
<script type="text/javascript">
	$(document).ready(function () {
		$('#collectionDrClaimsInfoList').datagrid({ 
		    onBeforeLoad: function (d) {
			    $.ajax({
					url:"${apppath}/claims/drClaimsLoanSum.do",
					type:"POST",
					data:$("#collectionDrClaimsInfoForm").serialize(), 
					success:function(result){
						$("#collectionDrClaimsSum").text(fmoney(result.pendRepayAmount,2));
						$("#collectionDrClaimsCounts").text(result.total);
					}
				});
			} 
    	});
	});
	//重置按钮
	$('#resetCollectionClaimsLoan').click(function(){
		$("#collectionDrClaimsInfoForm").form("reset");
		$("#collectionDrClaimsInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchCollectionClaimsLoan').click(function(){
 		$('#collectionDrClaimsInfoList').datagrid({
			queryParams: {
				dueStartDate: $('#searchCollectionClaimsLoanDueStartDate').datebox('getValue'),
				dueEndDate: $('#searchCollectionClaimsLoanDueEndDate').datebox('getValue'),
				status: $('#searchCollectionClaimsLoanStatus').combobox('getValue'),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowClaimsInfoBtn('+index+')">查看</a>';
	} 
	
	//跳转到债权显示页面
	function toShowClaimsInfoBtn(index){
		$('#collectionDrClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#collectionDrClaimsInfoList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权显示",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toShowClaimsInfo.do?lid="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	function collectionDrClaimsInfoDown(){
		location.href="${apppath}/claims/exportLoanRecord.do?dueStartDate="+$('#searchCollectionClaimsLoanDueStartDate').datebox('getValue')+
						"&dueEndDate="+$('#searchCollectionClaimsLoanDueEndDate').datebox('getValue')+
						"&status="+$('#searchCollectionClaimsLoanStatus').combobox('getValue');
	};
</script>
</body>
</html>

