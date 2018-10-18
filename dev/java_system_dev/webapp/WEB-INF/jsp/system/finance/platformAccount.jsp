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
	<div id="addbtn" style="padding:5px;height:750">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">划拨</a>
	</div>
	<div style="height:100%;width:100%;">
		<table id="account"  style="height:99%;width:99.9%"></table>
	</div>
	<div id="addAccount" class="easyui-dialog" style="width:400px;height:200px"   
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addAccountForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:1000%;">
				<center>
				<table>
					<tr>
						<td>
							付款账户:
						</td>
						<td>
							<input id="out_acnt_nm" name="out_acnt_nm" style="width: 250px" class="easyui-textbox" data-options="required:true" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td>
							付款账号:
						</td>
						<td>
							<input id="out_fuiou_acnt" name="out_fuiou_acnt" style="width: 250px" class="easyui-textbox" data-options="required:true" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<td>
							收款账户:
						</td>
						<td>
							<input id="in_fuiou_acnt" name="in_fuiou_acnt" style="width: 250px" class="easyui-combobox"/>
						</td>
					</tr>
					<tr>
						<td>
							转账金额:
						</td>
						<td>
							<input id="amt" name="amt" style="width: 250px" class="easyui-numberbox"/>
						</td>
					</tr>
				</table>
				</center>
			</div>  
		</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addAccount').dialog('close')">取消</a>
			</div>
		</div> 
<script type="text/javascript">
	    
		//账户列表
		var account = $('#account');
		$(function() {
			account.datagrid({
				url : "../companyFundsLog/getPlatformAccount.do",
				title : '账户列表',
				fitColumns : true,
				autoRowHeight : false,
				toolbar:"#addbtn",
				singleSelect:true,
				fit:true,
				columns : [ [ 
					{
					field : 'acnt_nm',
					title : '账户名称',
					align : "center"
					},{
					field : 'fuiou_acnt',
					title : '账户',
					width : '10%',
					align : "center"
					},{
					field : 'user_id',
					title : 'user_id',
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
					},{
					field : 'cu_balance',
					title : '未转结余额',
					width : '10%',
					align : "center"
					}
				] ]
			});
		}); 
		
		function add(){
			var row = account.datagrid('getSelected');
			if(row != null) { 
				$('#addAccount').dialog('open').dialog('setTitle', '划拨');
				$('#out_fuiou_acnt').textbox('setValue',row.fuiou_acnt);
				$('#out_acnt_nm').textbox('setValue',row.acnt_nm);
				$("#in_fuiou_acnt").combobox({
		            url : "../companyFundsLog/getPlatformAccount.do",//返回json数据的url
		            valueField : "user_id",//这个id和你返回json里面的id对应
		            textField : "fuiouAccount" //这个text和你返回json里面的text对应  fuiou_acnt
		        })
			}else{
				$.messager.alert('操作提示','请选择付款账户!'); 
				return false;  	
			}
		}
		
		function save(){
			var row = account.datagrid('getSelected');
			if($('#in_fuiou_acnt').combobox('getValue')==null || $('#in_fuiou_acnt').combobox('getValue')==''){
				$.messager.alert('操作提示','请选择收款账户!'); 
				return false;  	
			}
			
			if($('#amt').numberbox('getValue')==null || $('#amt').numberbox('getValue')==''){
				$.messager.alert('操作提示','请填写付款金额!'); 
				return false;  	
			}
			
			if(row.fuiou_acnt==$('#in_fuiou_acnt').combobox('getValue')){
				$.messager.alert('操作提示','付款账户不能跟收款账户相同!'); 
				return false;  	
			}
			
			 $.messager.confirm('操作提示', '确定划拨吗！', function (data) {		
				 	if(data){
				 		$.ajax({
							type: 'post',
							url : "../companyFundsLog/remitAccount.do",
							data : {
								in_user_id : $('#in_fuiou_acnt').combobox('getValue'),
								in_fuiouAccount:$('#in_fuiou_acnt').combobox('getText'),
								out_user_id : row.user_id,
								out_fuiou_acnt:row.fuiou_acnt,
								amt:$('#amt').numberbox('getValue')
							},
							success : function(message) {
								if (message=='success') { 	
									$.messager.alert('操作提示','操作成功');
									$('#addAccount').dialog('close');
									account.datagrid("load", {})
								}  
								else {  
									$.messager.alert('操作提示',message);
									return false;  
								} 
						    }
						 });
				 	}
			 })
		}
</script>
</body>
</html>

