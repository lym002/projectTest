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
	<table id="companyFundsLogList" title="平台收支记录 <span style='color: #0015FF;'>收入合计</span>：
		<span id='companyFundsLogIncome' style='color: red;'></span>
		<span style='color: #0015FF;'>支出合计</span>：<span id='companyFundsLogPay' style='color: red;'></span>
		<span style='color: #0015FF;'>剩余合计</span>：<span id='companyFundsLogSum' style='color: red;'></span>
		<%-- <span style='color: #0015FF;'>存管账户账面总余额</span>：<span id='ct_balance' style='color: red;'></span> 
		<span style='color: #0015FF;'>存管账户可用余额</span>：<span id='ca_balance' style='color: red;'></span> 
		<span style='color: #0015FF;'>存管账户冻结金额</span>：<span id='cf_balance' style='color: red;'></span>--%>" 
		 style="height:99%;width:99.9%">
		<thead>
	    <tr>
	        <th data-options="field:'pcode'" width="10%">产品编号</th>
	        <th data-options="field:'fundTypeName'" width="10%" >交易类型</th>	     
	       	<th data-options="field:'typeName'" >收入/支出</th>
	       	<th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">金额</th>	      	
	        <th data-options="field:'realName'" width="8%">用户姓名</th>
	       	<th data-options="field:'phone'" width="10%">用户手机号</th>
	        <th data-options="field:'remark'" width="25%">备注</th>
			<th data-options="field:'addTime'"  width="15%" formatter="formatDateBoxFull">交易时间</th>
	    </tr>
	    </thead>
	</table>
	<div id="companyFundsLogTools" style="padding:5px;height:750">
	  	<form id="companyFundsLogForm">
	  		产品编号:<input id="searchCompanyFundsLogLcoding" name="pcode" class="easyui-textbox"  size="15" style="width:200px"/>
	  		交易时间:<input id="searchCompanyFundsLogStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchCompanyFundsLogEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		收支类型: <select  id="searchCompanyFundsLogType" name="type" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach var="map" items="${fundtype}">
						<option value='${map.key}'>${map.value}</option>
			        </c:forEach>
	           </select>
	    	<a id="searchCompanyFundsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchCompanyFundsLogBtn()">查询</a>
	    	<a id=resetCompanyFundsLogBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportCompanyFundsLogList" href="javascript:exportCompanyFundsLogList();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    	<br>
	    	<label><input type="checkbox" value=""/> 全部 </label>
			<c:forEach var="map" items="${companyFundsType}">
				<label>
				<input type="checkbox" name="fundTypes" value="${map.key}" id="${map.key}" />${map.value}
				</label>
		    </c:forEach>
	    </form>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		$('#searchCompanyFundsLogStartDate').datebox('setValue',getdate());
		$('#searchCompanyFundsLogEndDate').datebox('setValue',getdate());
		searchCompanyFundsLogBtn();		   
	});
	//重置按钮
	$('#resetCompanyFundsLogBtn').click(function(){
		$("#companyFundsLogForm").form("reset");
		$("#companyFundsLogList").datagrid("load", {});
	});
	//查询按钮
	function searchCompanyFundsLogBtn(){
 		$('#companyFundsLogList').datagrid({
 			url: '../companyFundsLog/companyFundsLogList.do',
 			singleSelect:false,
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:25,
 			pageList:[25,50,100],
 			autoRowHeight : false,
 			toolbar:"#companyFundsLogTools", 
 			fit:true,
			queryParams: {
				startDate: $('#searchCompanyFundsLogStartDate').datebox('getValue'),
				endDate: $('#searchCompanyFundsLogEndDate').datebox('getValue'),
				pcode: $('#searchCompanyFundsLogLcoding').val(),
				type: $('#searchCompanyFundsLogType').combobox("getValue"),
				fundTypes:companyFundsLogCBAll(),
			},
			onBeforeLoad: function (d) {
			    $.ajax({
				url:"${apppath}/companyFundsLog/companyFundsLogSum.do",
				type:"POST",
				data:$("#companyFundsLogForm").serialize(),  
				success:function(result){
					$("#companyFundsLogIncome").text(result.companyFundsLogIncome);
					$("#companyFundsLogPay").text(result.companyFundsLogPay);
					$("#companyFundsLogSum").text(accSub(result.companyFundsLogIncome,result.companyFundsLogPay));
					/* $("#ct_balance").text(result.ct_balance);
					$("#ca_balance").text(result.ca_balance);
					$("#cf_balance").text(result.cf_balance); */
				}
				});
			} 
		}); 
 	}
	
	function companyFundsLogCBAll(){//输出选中的值
		var checks="";  
		$('input[name="fundTypes"]:checked').each(function(){
        	checks +=$(this).val()+",";
		});  
		return checks;  
	} 
	
	$(function(){
		$("#companyAmount").bind("keyup",function(){
			var balance = $("#companyBalance").val();
			var money=$("#companyAmount").val();
			var totalAmount = (parseFloat(balance)+parseFloat(money));
			totalAmount = Math.round(totalAmount*100)/100;
			$("#companySumAmount").val(totalAmount);
			$("#companyAmountCapital").html(cmycurd(money));
			
		});
		
	});
	
	function exportCompanyFundsLogList(){
		var map = {
	    startDate: $('#searchCompanyFundsLogStartDate').datebox('getValue'),
		endDate: $('#searchCompanyFundsLogEndDate').datebox('getValue'),
		pcode: $('#searchCompanyFundsLogLcoding').val(),
		type: $('#searchCompanyFundsLogType').combobox("getValue"),
		fundTypes:companyFundsLogCBAll()
		};
		window.location.href="../companyFundsLog/exportCompanyFundsLogList.do?"+$.param(map);
	}
	
</script>
</body>
</html>

