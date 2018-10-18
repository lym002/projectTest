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
	<table id="companyAccount"  style="height:99%;width:99.9%"></table>
	
	<div id="companyAccountbtn" style="padding:5px;height:750">
		<form id="selcompanyAccount">
			 交易时间:<input id="startaddTime" name="startaddTime" class="easyui-datebox" style="width:100px"/>
	  		至<input id="endaddTime" name="endaddTime" class="easyui-datebox" style="width:100px"/>
			资金类型: <select  id="companyfunds" name="companyfunds" style="width:100px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<c:forEach var="map" items="${companyfunds}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
	           		</select>
	              
	  		收支类型: <select  id="type" name="type" style="width:100px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<c:forEach var="map" items="${type}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
	          		</select>
	     	  通道:<select  id="channelType" name="channelType" style="width:100px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<option value='1'>金运通</option>
						<option value='2'>存管</option>
	          		</select>
	                     产品名称:<input id="fullName" name="fullName" class="easyui-textbox" style="width:100px"/>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selcompanyAccount()">查询</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetcompanyAccount()">重置</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addcompanyAccount()">新增</a>
	        <a id="exportcompanyAccount" href="javascript:exportcompanyAccount();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>      
		</form>
	</div>
	
	<div id="addcompanyAccount" class="easyui-dialog" style="width:400px;height:270px" title=""  
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addcompanyAccountForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<table>
				<tr>
				<td>交易时间:<input id="id" name="id" hidden="hidden" style="width:180px" type="text"/></td>
				<td><input id="addTime" name="addTime" class="easyui-datetimebox" style="width:180px"/></td>
				</tr>
				<tr>
					<td>资金类型:</td>
					<td>
						<select  id="addcompanyfunds" name="addcompanyfunds" style="width:180px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<c:forEach var="map" items="${companyfunds}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
	           			</select>
	           		</td>
	           	</tr>		
	           	<tr>
					<td>收支类型:</td>
					<td>
						<select  id="addtype" name="addtype" style="width:180px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<c:forEach var="map" items="${type}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
	          			</select>
	           		</td>
	           	</tr>		
				<tr><td>金额:</td><td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addamount" id="addamount" style="width:180px"></td></tr>						
<!-- 				<tr><td>余额:</td><td><input class="easyui-numberbox" data-options="min:0,precision:2" name="addbalance" id="addbalance" style="width:180px"></td></tr>						
 -->				<tr><td>备注:</td><td><input class="easyui-textbox" name="addremark" id="addremark" style="width:180px"></td></tr>
				<tr>
				<td>通道:</td>
				<td>
					<select  id="addchannelType" name="addchannelType" style="width:180px;" class="easyui-combobox">
					 	<option value=''>全部</option>
						<option value='1'>金运通</option>
						<option value='2'>存管</option>
	          		</select>
           		</td>
	           	</tr>	
	           	<tr>
				<td>产品名称:</td>
				<td>
					<input class="easyui-textbox" name="fullName" id="fullName" style="width:180px">
           		</td>
	           	</tr>			
				</table>
				</center>
			</div>  
			</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="insertcompanyAccount()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addcompanyAccount').dialog('close')">取消</a>
			</div>
	</div>
<script type="text/javascript">
	var companyAccount = $('#companyAccount');
	
	 $(document).ready(function () {
		$('#companyAccount').datagrid({ 
		    onBeforeLoad: function (d) {
			    $.ajax({
				url:"../companyFundsLog/companyAccountSum.do",
				type:"POST",
				data:{
					companyfunds: $('#companyfunds').combobox("getValue"),
					startaddTime: $('#startaddTime').datebox("getValue"),
					endaddTime: $('#endaddTime').datebox("getValue"),
					type: $('#type').combobox("getValue"),
					channelType: $('#channelType').combobox("getValue"),
					fullName: $('#fullName').textbox("getValue")
				}, 
				success:function(result){
					$("#sr").text(result.sr);
					$("#zc").text(result.zc);
				}
				});
			} 
    	});
	}); 
	
	companyAccount.datagrid({
		url : "../companyFundsLog/getCompanyAccount.do",
		title : "公司账户查询  <span style='color: #0015FF;'>收入合计</span>：<span id='sr' style='color: red;'></span><span style='color: #0015FF;'>支出合计</span>：<span id='zc' style='color: red;'></span>",
		fitColumns : true,
		pagination : true,
		checkbox:true,
		pageSize:25,
		pageList:[25,50,100],
		autoRowHeight : false,
		toolbar:"#companyAccountbtn", 
		fit:true,
		columns : [ [ 
			{
			field : 'id',
			title : 'id',
			width : '10%',
			align : "center",
			hidden:true
			},{
			field : 'addTime',
			title : '交易时间',
			width : '10%',
			align : "center"
			},{
			field : 'companyfundsName',
			title : '资金类型',
			width : '10%',
			align : "center"
			},{
			field : 'sr',
			title : '收入',
			width : '10%',
			align : "center"
			},{
			field : 'zc',
			title : '支出',
			width : '10%',
			align : "center"
			}/* ,{
			field : 'balance',
			title : '余额',
			width : '10%',
			align : "center"
			} */,{
			field : 'remark',
			title : '备注',
			width : '10%',
			align : "center"
			},{
			field : 'fullName',
			title : '产品',
			width : '10%',
			align : "center"
			},{
			field : 'channelTypeName',
			title : '通道',
			width : '10%',
			align : "center"
			},{
			field : '',
			title : '修改',
			width : '10%',
			align : "center",
			formatter:function(value,row,index){
				var str="<a href='#' class='easyui-linkbutton' onclick=\"updatecompanyAccount(\'"+index+"')\">修改</a>  "
				return str;
			}
			}
		] ]
	});
	
	//修改窗口
	function updatecompanyAccount(index){
		companyAccount.datagrid('selectRow',index);// 关键在这里 
	    var row = companyAccount.datagrid('getSelected'); 
		$('#addcompanyfunds').combobox("setValue",row.companyfunds);
		$('#addTime').datetimebox("setValue",row.addTime);
		$('#addtype').combobox("setValue",row.type);
		if(row.type==0){
			$('#addamount').numberbox("setValue",row.zc);
		}else{
			$('#addamount').numberbox("setValue",row.sr);
		}
		$('#addchannelType').combobox("setValue",row.channelType);
		
		/* $('#addbalance').numberbox("setValue",row.balance), */
		$('#addremark').textbox("setValue",row.remark),
		$('#fullName').textbox("setValue",row.fullName),
		 $('#id').val(row.id);
		$("#addcompanyAccount").dialog("open");
	}
	//重置
	function resetcompanyAccount(){
		$("#selcompanyAccount").form("reset");
		selcompanyAccount();
	}
	
	//查询
	function selcompanyAccount(){
		companyAccount.datagrid('reload', {
			companyfunds: $('#companyfunds').combobox("getValue"),
			startaddTime: $('#startaddTime').datebox("getValue"),
			endaddTime: $('#endaddTime').datebox("getValue"),
			type: $('#type').combobox("getValue"),
			channelType: $('#channelType').combobox("getValue"),
			fullName: $('#fullName').textbox("getValue")
		});
	}
	
	//导出
	function exportcompanyAccount(){
		var map = {
			companyfunds: $('#companyfunds').combobox("getValue"),
			startaddTime: $('#startaddTime').datebox("getValue"),
			endaddTime: $('#endaddTime').datebox("getValue"),
			type: $('#type').combobox("getValue"),
			channelType: $('#channelType').combobox("getValue"),
			fullName: $('#fullName').textbox("getValue")
		};
		window.location.href="../companyFundsLog/exportcompanyAccount.do?"+$.param(map);
	}
	
	function addcompanyAccount(){
		$("#addcompanyAccountForm").form("reset");
		$("#addcompanyAccount").dialog("open");
	}
	
	function insertcompanyAccount(){
		if($('#addcompanyfunds').combobox("getValue")==null || $('#addcompanyfunds').combobox("getValue")=='' || $('#addTime').datetimebox("getValue")==null
			||$('#addtype').combobox("getValue")==null || $('#addtype').combobox("getValue")=='' || $('#addchannelType').combobox("getValue")==null || $('#addchannelType').combobox("getValue")==''
			|| $('#addamount').numberbox("getValue")==null){
			$.messager.alert("提示信息","请把信息填写完整");
			return false;
		}
		$.messager.confirm('操作提示', '确定提交吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../companyFundsLog/insertcompanyAccount.do",
						data : {
							companyfunds: $('#addcompanyfunds').combobox("getValue"),
							id:$('#id').val(),
							addTime: $('#addTime').datetimebox("getValue"),
							type: $('#addtype').combobox("getValue"),
							channelType: $('#addchannelType').combobox("getValue"),
							amount: $('#addamount').numberbox("getValue"),
							remark: $('#addremark').textbox("getValue"),
							fullName:$('#fullName').textbox("getValue")
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#addcompanyAccount").dialog("close");
								selcompanyAccount();
							}else{
								$.messager.alert("提示信息",result.errorMsg);
								selcovercharge();
							}
					    }
					 });
			 } 
		 })
	}
</script>
</body>
</html>

