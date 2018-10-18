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
	<table id="repayDrClaimsInfoList" title="债权还款 
	<span style='color: #0015FF;'>共找到</span><span id='repayDrClaimsCounts' style='color: red;'></span><span style='color: #0015FF;'>条记录</span>,
	<span style='color: #0015FF;'>待还款总金额为</span>：<span id='repayDrClaimsSum' style='color: red;'></span>" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../claims/repayDrClaimsLoanList.do',
	    method:'post',rownumbers:true,singleSelect:false,
	    pagination:true,toolbar:'#repayDrClaimsInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'batchLoan',checkbox:true"></th>
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
	<div id="repayDrClaimsInfoTools" style="padding:5px;height:750">
	  	<form id="repayDrClaimsInfoForm">
	  		到期日期:<input id="searchRepayClaimsLoanDueStartDate" name="dueStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchRepayClaimsLoanDueEndDate" name="dueEndDate" class="easyui-datebox" style="width:100px"/>
	  		当前状态: <select  id="searchRepayClaimsLoanStatus" name="status" style="width:100px;" class="easyui-combobox">
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 5 }">
								<option value='${map.key}'>${map.value }</option>
							</c:if>
						</c:forEach>
	           		</select>
	    	<a id="searchRepayClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetRepayClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	   		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateBatchRepayDrClaimsInfo()">批量还款</a>	
	    </form>
	</div>
<script type="text/javascript">
	$(document).ready(function () {
		$('#repayDrClaimsInfoList').datagrid({ 
		    onBeforeLoad: function (d) {
			    $.ajax({
					url:"${apppath}/claims/drClaimsLoanSum.do",
					type:"POST",
					data:$("#repayDrClaimsInfoForm").serialize(),  
					success:function(result){
						$.messager.progress("close");
						$("#repayDrClaimsSum").text(fmoney(result.pendRepayAmount,2));
						$("#repayDrClaimsCounts").text(result.total);
					}
				});
			} 
    	});
	});
	//重置按钮
	$('#resetRepayClaimsLoan').click(function(){
		$("#repayDrClaimsInfoForm").form("reset");
		$("#repayDrClaimsInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchRepayClaimsLoan').click(function(){
 		$('#repayDrClaimsInfoList').datagrid({
			queryParams: {
				dueStartDate: $('#searchRepayClaimsLoanDueStartDate').datebox('getValue'),
				dueEndDate: $('#searchRepayClaimsLoanDueEndDate').datebox('getValue'),
				status: $('#searchRepayClaimsLoanStatus').combobox('getValue'),
			}
		}); 
	});
	
	//操作
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowClaimsInfoBtn('+index+')">查看</a>'+"   "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="repayClaimsInfoBtn('+index+')">还款</a>';
	} 
	
	//批量还款
	function updateBatchRepayDrClaimsInfo(){
		var checkedItems = $('#repayDrClaimsInfoList').datagrid('getChecked');
		if(checkedItems == ""){
			$.messager.alert("操作提示", "请选择要还款的数据！");
		 	return;
		}
		var ids = [];
		var i = 0;
		var total = 0;
		$.each(checkedItems, function(index, item){
			ids.push(item.id);
			i++;
			total+=item.loanAmount+item.receiveInterest;
		});               
		var amount = total;
		var counts = i;
		$.messager.confirm("还款提示", "一共"+counts+"笔债权，还款总金额为"+amount+"元，是否确认还款？", function(ensure){
			if(ensure){
				var url = "${apppath}/claims/updateBatchRepayDrClaimsInfo.do?ids="+ids;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#repayDrClaimsInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	};
	
	//还款操作
	function repayClaimsInfoBtn(index){  
		$('#repayDrClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#repayDrClaimsInfoList').datagrid('getSelected'); 
	    var total = row.loanAmount+row.receiveInterest;
		$.messager.confirm("还款提示", "借款合同编号"+row.no+"，还款金额"+total+"元，是否确认还款？", function(ensure){
			if(ensure){
				var url = "${apppath}/claims/updateRepayClaimsInfo.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#repayDrClaimsInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	
	//跳转到债权显示页面
	function toShowClaimsInfoBtn(index){
		$('#repayDrClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#repayDrClaimsInfoList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权显示",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toShowClaimsInfo.do?lid="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
</script>
</body>
</html>

