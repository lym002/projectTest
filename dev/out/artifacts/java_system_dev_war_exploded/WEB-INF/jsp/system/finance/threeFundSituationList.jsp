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
	<table id="threeFundSituation"  style="height:99%;width:99.9%"></table>
	
	<div id="threeFundSituationbtn" style="padding:5px;height:750">
		<form id="selthreeFundSituation">
			 时间:<input id="startdatetime" name="startdatetime" class="easyui-datebox" style="width:100px"/>
	  		至<input id="enddatetime" name="enddatetime" class="easyui-datebox" style="width:100px"/>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selthreeFundSituation()">查询</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetthreeFundSituation()">重置</a>
	        <a id="exportthreeFundSituation" href="javascript:exportthreeFundSituation();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>      
		</form>
	</div>
	
	<div id="addthreeFundSituation" class="easyui-dialog" style="width:400px;height:250px" title=""  
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addthreeFundSituationForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<table>
				<tr>
				<td>借款收款额:<input id="id" name="id" hidden="hidden" style="width:180px" type="text"/></td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addincomeAmount" id="addincomeAmount" style="width:180px"></td>
	           	</tr>
	           	<tr>
				<td>公司备付金充值:</td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addstandbyRechargeAmount" id="addstandbyRechargeAmount" style="width:180px"></td>
	           	</tr>
	           	<tr>
				<td>借款放款额:</td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addloanAmount" id="addloanAmount" style="width:180px"></td>
	           	</tr>
	           	<tr>
				<td>相关手续费:</td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addpoundageAmount" id="addpoundageAmount" style="width:180px"></td>
	           	</tr>	
	           	<tr>
				<td>公司备付金提取:</td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addstandbyWithdrawalAmount" id="addstandbyWithdrawalAmount" style="width:180px"></td>
	           	</tr>	
	           	<tr>
				<td>活动金:</td>
				<td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addactivityAmount" id="addactivityAmount" style="width:180px"></td>
	           	</tr>			
				</table>
				</center>
			</div>  
			</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="insertthreeFundSituation()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addthreeFundSituation').dialog('close')">取消</a>
			</div>
	</div>
<script type="text/javascript">
	var threeFundSituation = $('#threeFundSituation');
	
	  $(document).ready(function () {
		$('#threeFundSituation').datagrid({ 
		    onBeforeLoad: function (d) {
			    $.ajax({
				url:"../companyFundsLog/getThreeFundSituationSum.do",
				type:"POST",
				data:{
					startdatetime: $('#startdatetime').datebox("getValue"),
					enddatetime: $('#enddatetime').datebox("getValue")
				}, 
				success:function(result){
					$("#investAmount").text(result.investAmount);
					$("#rechargeAmount").text(result.rechargeAmount);
					$("#incomeAmount").text(result.incomeAmount);
					$("#standbyRechargeAmount").text(result.standbyRechargeAmount);
					$("#srSum").text(result.srSum); 
					$("#loanAmount").text(result.loanAmount);
					$("#withdrawalAmount").text(result.withdrawalAmount);
					$("#poundageAmount").text(result.poundageAmount);
					$("#standbyWithdrawalAmount").text(result.standbyWithdrawalAmount);
					$("#activityAmount").text(result.activityAmount);
					$("#zcSum").text(result.zcSum);		
				}
				});
			} 
    	});
	});  
	
	 threeFundSituation.datagrid({
		url : "../companyFundsLog/getThreeFundSituation.do",
		title : "第三方资金支出情况 "+
		"<span style='color: #0015FF;'>投资交易额合计</span>：<span id='investAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>客户充值合计</span>：<span id='rechargeAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>借款收款额合计</span>：<span id='incomeAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>公司备付金充值合计</span>：<span id='standbyRechargeAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>收入合计</span>：<span id='srSum' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>借款放款额</span>：<span id='loanAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>客户提现</span>：<span id='withdrawalAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>相关手续费</span>：<span id='poundageAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>公司备付金提取</span>：<span id='standbyWithdrawalAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>活动金</span>：<span id='activityAmount' style='color: red;'></span>"+
		"<span style='color: #0015FF;'>支出合计</span>：<span id='zcSum' style='color: red;'></span>",
		fitColumns : true,
		pagination : true,
		checkbox:true,
		pageSize:25,
		pageList:[25,50,100],
		autoRowHeight : false,
		toolbar:"#threeFundSituationbtn", 
		fit:true,
		columns : [ [ 
			{
			field : 'id',
			title : 'id',
			width : '10%',
			align : "center",
			hidden:true
			},{
			field : 'datetime',
			title : '时间',
			width : '8%',
			align : "center"
			},{
			field : 'investAmount',
			title : '投资交易额',
			width : '8%',
			align : "center"
			},{
			field : 'rechargeAmount',
			title : '客户充值',
			width : '8%',
			align : "center"
			},{
			field : 'incomeAmount',
			title : '借款收款额',
			width : '8%',
			align : "center"
			},{
			field : 'standbyRechargeAmount',
			title : '公司备付金充值',
			width : '8%',
			align : "center"
			},{
			field : 'srSum',
			title : '收入合计',
			width : '8%',
			align : "center"
			},{
			field : 'loanAmount',
			title : '借款放款额',
			width : '8%',
			align : "center"
			},{
			field : 'withdrawalAmount',
			title : '客户提现',
			width : '8%',
			align : "center"
			},{
			field : 'poundageAmount',
			title : '相关手续费',
			width : '8%',
			align : "center"
			},{
			field : 'standbyWithdrawalAmount',
			title : '公司备付金提取',
			width : '8%',
			align : "center"
			},{
			field : 'activityAmount',
			title : '活动金',
			width : '8%',
			align : "center"
			},{
			field : 'zcSum',
			title : '支出合计',
			width : '8%',
			align : "center"
			},{
			field : '',
			title : '修改',
			width : '8%',
			align : "center",
			formatter:function(value,row,index){
				var str="<a href='#' class='easyui-linkbutton' onclick=\"updatethreeFundSituation(\'"+index+"')\">修改</a>  "
				return str;
			}
			}
		] ]
	});
	
	//修改窗口
 	function updatethreeFundSituation(index){
 		threeFundSituation.datagrid('selectRow',index);// 关键在这里 
	    var row = threeFundSituation.datagrid('getSelected'); 
	    $('#addincomeAmount').numberbox("setValue",row.incomeAmount);
		$('#addstandbyRechargeAmount').numberbox("setValue",row.standbyRechargeAmount);
		$('#addloanAmount').numberbox("setValue",row.loanAmount);
		$('#addpoundageAmount').numberbox("setValue",row.poundageAmount);
		$('#addstandbyWithdrawalAmount').numberbox("setValue",row.standbyWithdrawalAmount);
		$('#addactivityAmount').numberbox("setValue",row.activityAmount);
		$('#id').val(row.id);  
		$("#addthreeFundSituation").dialog("open");
	} 
	//重置
	 function resetthreeFundSituation(){
		$("#selthreeFundSituation").form("reset");
		selthreeFundSituation();
	} 
	
	//查询
 	function selthreeFundSituation(){
		threeFundSituation.datagrid('reload', {
			startdatetime: $('#startdatetime').datebox("getValue"),
			enddatetime: $('#enddatetime').datebox("getValue")
		});
	} 
	
	//导出
	 function exportthreeFundSituation(){
		var map = {
				startdatetime: $('#startdatetime').datebox("getValue"),
				enddatetime: $('#enddatetime').datebox("getValue")
		};
		window.location.href="../companyFundsLog/exportThreeFundSituation.do?"+$.param(map);
	}
	
	function insertthreeFundSituation(){
		if($('#addincomeAmount').numberbox("getValue")==null || $('#addstandbyRechargeAmount').numberbox("getValue")==null
		   || $('#addloanAmount').numberbox("getValue")==null || $('#addpoundageAmount').numberbox("getValue")==null
		   || $('#addstandbyWithdrawalAmount').numberbox("getValue")==null || $('#addactivityAmount').numberbox("getValue")==null){
		   $.messager.alert("提示信息","请把信息填写完整")
			return false;
		}
		$.messager.confirm('操作提示', '确定提交吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../companyFundsLog/updateThreeFundSituation.do",
						data : {
							id:$('#id').val(),
							incomeAmount: $('#addincomeAmount').numberbox("getValue"),
							standbyRechargeAmount: $('#addstandbyRechargeAmount').numberbox("getValue"),
							loanAmount: $('#addloanAmount').numberbox("getValue"),
							poundageAmount: $('#addpoundageAmount').numberbox("getValue"),
							standbyWithdrawalAmount: $('#addstandbyWithdrawalAmount').numberbox("getValue"),
							activityAmount: $('#addactivityAmount').numberbox("getValue")
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#addthreeFundSituation").dialog("close");
								selthreeFundSituation();
							}else{
								$.messager.alert("提示信息",result.errorMsg);
								selthreeFundSituation();
							}
					    }
					 });
			 } 
		 })
	} 
</script>
</body>
</html>

