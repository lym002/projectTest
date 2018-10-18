<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
	<div id="customerbtn">
		<form id="selCustomer">
			手机号: <input class="easyui-textbox" name="phone" id="phone" style="width:150px">
			企业名称:<input class="easyui-textbox" name="name" id="name" style="width:150px">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selCustomer()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetCustomer()">重置</a>
		</form>
	</div>
	<div style="height: 100%; width: 100%;">
		<table id="customer" style="height: 99%; width: 99.9%"></table>
	</div>
	<div id="recharge" class="easyui-dialog"
		style="width: 600px; height: 250px"
		data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
		<form id="addRecharge" enctype="multipart/form-data">
			<div style="margin-bottom: 20px; width: 100%; height: 100%;">
				<center>
					<table>
						<tr>
							<td style="width: 350px">企业名称:<input id="id" type="hidden"></td>
							<td><input class="easyui-textbox" name="rechargecompanyName" id="rechargecompanyName" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td style="width: 350px">账户可用余额:</td>
							<td><input class="easyui-textbox" name="rechargecompanyName" id="rechargecompanyName" style="width:150px" data-options="required:true" disabled="disabled"></td>
						</tr>
						<tr>
							<td style="width: 350px">法人:</td>
							<td><input class="easyui-textbox" name="rechargecustNm" id="rechargecustNm" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td style="width: 350px">开户行:</td>
							<td><input class="easyui-textbox" name="rechargebankNm" id="rechargebankNm" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td style="width: 350px">银行卡号:</td>
							<td><input class="easyui-textbox" name="rechargecardNo" id="rechargecardNo" style="width:150px" data-options="required:true" disabled="disabled"></td>
						</tr>
						<tr>
							<td>充值金额: </td>
							<td><input class="easyui-textbox" name="rechargeamount" id="rechargeamount" style="width:150px"></td>
							<td style="width:350px">充值后剩余余额:</td>
							<td></td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		<div style="text-align: center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="rechargeSave()">确定</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#recharge').dialog('close')">取消</a>
		</div>
	</div>
	<div id="withdrawals" class="easyui-dialog"
		style="width: 600px; height: 250px"
		data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
		<form id="addWithdrawals" enctype="multipart/form-data">
			<div style="margin-bottom: 20px; width: 100%; height: 100%;">
				<center>
					<table>
						<tr>
							<td style="width: 350px">企业名称:<input id="id" type="hidden"></td>
							<td><input class="easyui-textbox" name="companyName" id="companyName" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td style="width: 350px">账户可用余额:</td>
							<td><input class="easyui-textbox" name="ca_balance" id="ca_balance" style="width:150px" data-options="required:true" disabled="disabled"></td>
						</tr>
						<tr>
							<td style="width: 350px">法人:</td>
							<td><input class="easyui-textbox" name="custNm" id="custNm" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td style="width: 350px">开户行:</td>
							<td><input class="easyui-textbox" name="bankNm" id="bankNm" style="width:150px" data-options="required:true" disabled="disabled"></td>
							<td style="width: 350px">银行卡号:</td>
							<td><input class="easyui-textbox" name="cardNo" id="cardNo" style="width:150px" data-options="required:true" disabled="disabled"></td>
						</tr>
						<tr>
							<td>提现金额: </td>
							<td><input class="easyui-textbox" name="amount" id="amount" style="width:150px"></td>
							<td></td>
							<td></td>
						</tr>
							<td style="width:350px">提现后剩余余额:</td>
							<td></td>
							<td style="width:350px">已扣除手续费:</td>
							<td></td>						
					</table>
				</center>
			</div>
		</form>
		<div style="text-align: center;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="withdrawalSave()">确定</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="javascript:$('#withdrawals').dialog('close')">取消</a>
		</div>
	</div>
		<form id="storageForm" id="storageForm" method="POST" target="_blank">
		   <input type="hidden" id ="storageJson" name="json" value="" />			
		 </form>
	<script type="text/javascript">
	    
		var customer = $('#customer');
		$(function() {
			customer.datagrid({
				url : "../claims/selectCustomerFuiou.do",
				title : '企业列表',
				fitColumns : true,
				pagination : true,
				autoRowHeight : false,
				singleSelect:true,
				pageSize:25,
				pageList:[25,50,100],
				toolbar:"#customerbtn",
				fit:true,
				columns : [ [ 
					{
					field : 'id',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'bankNm',
					title : '开户行',
					width : '10%',
					align : "center"
					},{
					field : 'cardNo',
					title : '卡号',
					width : '10%',
					align : "center"	
					},{
					field : 'mobileNo',
					title : '手机号',
					width : '10%',
					align : "center"
					},{
					field : 'certifId',
					title : '身份证号码',
					width : '10%',
					align : "center"
					},{
					field : 'custNm',
					title : '企业名称',
					width : '10%',
					align : "center"
					},{
					field : 'ct_balance',
					title : '账面总余额',
					width : '10%',
					align : "center"
					},{
					field : 'ca_balance',
					title : '可用余额',
					width : '10%',
					align : "center"
					},{
					field : 'cf_balance',
					title : '冻结余额',
					width : '10%',
					align : "center"
					}/* ,{
					field : ' ',
					title : '操作',
					width : '6%',
					formatter:function(value,row,index){
							var ele = '<a href="#"  onclick="addwithdrawals(' + JSON.stringify(row).replace(/"/g, '&quot;') + ');">提现</a>';
								ele =ele+'  <a href="#"  onclick="addrecharge(' + JSON.stringify(row).replace(/"/g, '&quot;') + ');">充值</a>';
							return ele;
	                      },
						align : "center"
					} */
				] ]
			});
		}); 
		
		function addwithdrawals(row){
			$('#companyName').textbox('setValue',row.companyName);
			$('#custNm').textbox('setValue',row.custNm);
			$('#cardNo').textbox('setValue',row.cardNo);
			$('#bankNm').textbox('setValue',row.bankNm);
			$('#ca_balance').textbox('setValue',row.ca_balance);
			$('#id').val(row.id);
			$('#withdrawals').dialog('open').dialog('setTitle', '提现');
		}
		
		function addrecharge(row){
			$('#rechargecompanyName').textbox('setValue',row.companyName);
			$('#rechargecustNm').textbox('setValue',row.custNm);
			$('#rechargecardNo').textbox('setValue',row.cardNo);
			$('#rechargebankNm').textbox('setValue',row.bankNm);
			$('#id').val(row.id);
			$('#recharge').dialog('open').dialog('setTitle', '充值');
		}
		addrecharge
		
		//查询按钮
		function selCustomer(){
	 		$('#customer').datagrid({
				queryParams: {
					name:$('#name').textbox("getValue"),
					phone:$('#phone').textbox("getValue")
					}
			})
		}
		
		//重置按钮
		function resetCustomer(){
			$("#selCustomer").form("reset");
			$("#customer").datagrid("load", {});
		};	
	
		function withdrawalSave(){
			if($("#amount").textbox('getValue')==null || $("#amount").textbox('getValue')==''){
				$.messager.alert("操作提示", "请填写提现金额");
				return false;
			}
			$.ajax({
				type: 'post',
				url : "../claims/customerwithdrawals.do",
				data : {
					id :$("#id").val(),
					amount : $("#amount").textbox('getValue')
				},
				success:function(result){
					if(result.success){
						$.messager.alert("操作提示", result.msg);
						getStorageForm(result.map.signature);
						$("#storageForm")[0].action=result.map.fuiouUrl;
						$("#storageForm")[0].submit();
					}else{
						$.messager.alert("提示信息",result.errorMsg);					}
				}
			 });
		}
		
		function rechargeSave(){
			if($("#rechargeamount").textbox('getValue')==null || $("#rechargeamount").textbox('getValue')==''){
				$.messager.alert("操作提示", "请填写充值金额");
				return false;
			}
			  $.ajax({
				type: 'post',
				url : "../claims/customerrecharge.do",
				data : {
					id :$("#id").val(),
					amount : $("#rechargeamount").textbox('getValue'),
					type:2,
				},
				success:function(result){
					if(result.success){
						$.messager.alert("操作提示", result.msg);
						getStorageForm(result.map.signature);
						$("#storageForm")[0].action=result.map.fuiouUrl;
						$("#storageForm")[0].submit();
					}else{
						$.messager.alert("提示信息",result.errorMsg);				
					}
				}
			 });  
		}
		
		function getStorageForm(json) {
			json = JSON.parse(json);
			for(var key in json.message){
				if(key !="signature") {
					$("#storageForm").prepend('<input type="hidden" name="'+key+'" value="'+json.message[key]+'" /><br/>');
				}
			}
			$("#storageForm").prepend('<input type="hidden" name="signature" value="'+json.signature+'" /><br/>');
		}


</script>
</body>
</html>

