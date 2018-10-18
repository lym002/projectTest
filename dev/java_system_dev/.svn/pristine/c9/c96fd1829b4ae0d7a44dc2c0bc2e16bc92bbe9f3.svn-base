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
	<table id="covercharge"  style="height:99%;width:99.9%"></table>
	<div id="coverchargebtn" style="padding:5px;height:750">
		<form id="selcovercharge">
			项目名称:<input id="productName" name="productName" class="easyui-textbox"  size="15" style="width:200px"/>
			项目产品编号:<input id="productCode" name="productCode" class="easyui-textbox"  size="15" style="width:200px"/>
			借款方名称:<input id="loanName" name="loanName" class="easyui-textbox"  size="15" style="width:200px"/>
			收款时间:<input id="startserviceTime" name="startserviceTime" class="easyui-datebox" style="width:100px"/>
	  		至<input id="endserviceTime" name="endserviceTime" class="easyui-datebox" style="width:100px"/>
	  		开票时间:<input id="startinvoiceTime" name="startinvoiceTime" class="easyui-datebox" style="width:100px"/>
	  		至<input id="endinvoiceTime" name="endinvoiceTime" class="easyui-datebox" style="width:100px"/>
			开票号码:<input id="number" name="number" class="easyui-textbox"  size="15" style="width:200px"/>		
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selcovercharge()">查询</a>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetcovercharge()">重置</a>
      	    <a id="exportCharge" href="javascript:exportCharge();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
		</form>
	</div>
	
	<!-- 服务费经办 -->
	<div id="addcharge" class="easyui-dialog" style="width:400px;height:250px" title="服务费经办"  
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addchargeForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<table>
				<tr><td>产品编号:</td><td><input class="easyui-textbox" name="code" id="code" style="width:180px" readonly="readonly"></td></tr>
				<tr><td>已收服务费金额:</td><td><input class="easyui-numberbox" data-options="min:0,precision:2" name="serviceCharge" id="serviceCharge" style="width:180px" readonly="readonly"></td></tr>		
				<tr><td>已收担保费金额:</td><td><input class="easyui-numberbox" data-options="min:0,precision:2" name="receivedGuarantee" id="receivedGuarantee" style="width:180px" readonly="readonly"></td></tr>		
				<tr><td>收款时间:</td><td><input class="easyui-datetimebox" name="serviceTime" id="serviceTime" style="width:180px" readonly="readonly"></td></tr>				
				<tr><td>备注:</td><td><input class="easyui-textbox" name="serviceRemerk" id="serviceRemerk" style="width:180px"></td></tr>
				<tr><td>审核:</td>
					<td>
						<input name="serviceStatus" type="radio" value="1" checked="checked"/>通过
						<input name="serviceStatus" type="radio" value="2" />驳回
					</td>
				</tr>								
				</table>
				</center>
			</div>  
			</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="insertCharge()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addcharge').dialog('close')">取消</a>
			</div>
	</div>
	
	<!-- 开票信息经办 -->
	<div id="addinvotice" class="easyui-dialog" style="width:400px;height:250px" title="开票信息经办"  
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addinvoticeForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<table>
				<tr><td>产品编号:</td><td><input class="easyui-textbox" name="invoticecode" id="invoticecode" style="width:180px" readonly="readonly"></td></tr>
				<tr><td>开票金额:</td><td><input class="easyui-numberbox" data-options="min:0,precision:2" name="invoiceAmount" id="invoiceAmount" style="width:180px" readonly="readonly"></td></tr>		
				<tr><td>开票号码:</td><td><input class="easyui-textbox" name="invoiceNumber" id="invoiceNumber" style="width:180px" readonly="readonly"></td></tr>		
				<tr><td>开票时间:</td><td><input class="easyui-datetimebox" name="invoiceTime" id="invoiceTime" style="width:180px" readonly="readonly"></td></tr>				
				<tr><td>备注:</td><td><input class="easyui-textbox" name="invoiceRemerk" id="invoiceRemerk" style="width:180px"></td></tr>
				<tr><td>审核: </td>
					<td>
						<input  name="invoiceStatus" type="radio" value="1" checked="checked" />通过
						<input  name="invoiceStatus" type="radio" value="2" />驳回
					</td>
				</tr>						
				</table>
				</center>
			</div>  
			</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="insertInvotice()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addinvotice').dialog('close')">取消</a>
			</div>
	</div>
<script type="text/javascript">
	var covercharge = $('#covercharge');
	covercharge.datagrid({
		url : "../product/getProductServiceManagement.do?invoiceStatus=1&serviceStatus=1", 
		title : '服务费列表',
		fitColumns : true,
		pagination : true,
		pageSize:25,
		pageList:[25,50,100],
		singleSelect:true,
		autoRowHeight : false,
		toolbar:"#coverchargebtn", 
		fit:true,
		columns : [ [ 
			{
			field : 'fullName',
			title : '项目名称',
			align : "center"
			},{
			field : 'code',
			title : '项目产品编号',
			align : "center"
			},{
			field : 'contractCode',
			title : '项目合同编号',
			align : "center"
			},{
			field : 'loanName',
			title : '借款方名称',
			align : "center"
			},{
			field : 'deadline',
			title : '期限',
			align : "center"
			},{
			field : 'amount',
			title : '产品金额',
			align : "center"
			},{
			field : 'yearAmount',
			title : '本年度累计额借款',
			align : "center"
			},{
			field : 'countAmount',
			title : '累计额借款',
			align : "center"
			},{
			field : 'loanAmount',
			title : '授信额度',
			align : "center"
			},{
			field : 'interest',
			title : '预计费用总额',
			align : "center",
			formatter:function(value,row,index){
				var amount=0;
				if(row.countAmount<=row.loanAmount){//首笔 预计费用总额=产品金额*8%
					amount=row.amount*8/100;
				}else{
					var a=row.countAmount-row.loanAmount;//计算超出多少
					if(a>=row.amount){
						amount=row.amount*1*row.deadline/30/100;//全部超出
					}else{
						var b=row.amount-a;//非部分
						amount=b*8/100;
						amount=(a*1*row.deadline/30/100)+amount;//超出部分	
					}	
				}
				return amount;
			}
			},{
			field : 'principalInterest',
			title : '预计服务费',
			align : "center",
			formatter:function(value,row,index){
				var amount=0;
				if(row.countAmount<=row.loanAmount){//首笔 预计费用总额=产品金额*8%
					amount=row.amount*8/100;
				}else{
					var a=row.countAmount-row.loanAmount;//计算超出多少
					if(a>=row.amount){
						amount=row.amount*1*row.deadline/30/100;//全部超出
					}else{
						var b=row.amount-a;//非部分
						amount=b*8/100;
						amount=(a*1*row.deadline/30/100)+amount;//超出部分	
					}	
				}
				var guarantee=0;
				if(row.countAmount<=row.loanAmount){//首笔 产品金额*1.5%
					guarantee=row.amount*1.5/100;
				}else{
					var a=row.countAmount-row.loanAmount;//计算超出多少
					if(a<row.amount){//超出部分
						var b=row.amount-a;//非部分
						guarantee=b*1.5/100;//超出部分
					}
				}
				return amount-guarantee;
			}
			},{
			field : 'fullDate',
			title : '预计担保费',
			align : "center",
			formatter:function(value,row,index){
				var amount=0;
				if(row.countAmount<=row.loanAmount){//首笔 产品金额*1.5%
					amount=row.amount*1.5/100;
				}else{
					var a=row.countAmount-row.loanAmount;//计算超出多少
					if(a<row.amount){//超出部分
						var b=row.amount-a;//非部分
						amount=b*1.5/100;//超出部分
					}
				}
				return amount;
			}
			},{
			field : 'serviceCharge',
			title : '已收服务费',
			align : "center"
			},{
			field : 'receivedGuarantee',
			title : '已收担保费',
			align : "center"
			},{
			field : 'serviceTime',
			title : '收款时间',
			align : "center"
			},{
			field : 'serviceRemerk',
			title : '备注',
			align : "center"
			},{
			field : 'serviceStatus',
			title : '服务费审核状态',
			align : "center",
			formatter:function(value,row,index){
				if(value==1){
					return "服务费待复核";
				}else if(value==2){
					return "服务费复核通过";
				}else if(value==3){
					return "服务费复核驳回";
				}
			}
			},{
			field : 'invoiceTime',
			title : '开票时间',
			align : "center"
			},{
			field : 'invoiceAmount',
			title : '开票金额',
			align : "center"
			},{
			field : 'invoiceNumber',
			title : '开票号码',
			align : "center"
			},{
			field : 'invoiceRemerk',
			title : '开票备注',
			align : "center"
			},{
			field : 'invoiceStatus',
			title : '开票信息复核状态',
			align : "center",
			formatter:function(value,row,index){
				if(value==1){
					return "开票信息待复核";
				}else if(value==2){
					return "开票信息复核通过";
				}else if(value==3){
					return "开票信息复核驳回";
				}
			}
			},{
			field : 'isReimbursement',
			title : '操作',
			align : "center",
			formatter:function(value,row,index){
				var str="";
				if(row.serviceStatus==1){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"addcharge(\'"+index+"')\">服务费复核</a>  "
				}
				if(row.invoiceStatus==1){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"addinvotice(\'"+index+"')\">开票信息复核</a>  "
				}
				return str;
			}
			}				
		] ]
	});
	
	function selcovercharge(){
		covercharge.datagrid('reload', {
				productName:$("#productName").textbox('getValue'),
				productCode:$("#productCode").textbox('getValue'),
				loanName:$("#loanName").textbox('getValue'),
				startserviceTime:$("#startserviceTime").datebox('getValue'),
				endserviceTime:$("#endserviceTime").datebox('getValue'),
				startinvoiceTime:$("#startinvoiceTime").datebox('getValue'),
				endinvoiceTime:$("#endinvoiceTime").datebox('getValue'),
				number:$("#number").textbox('getValue'),
				serviceStatus:1,
				invoiceStatus:1
		 });
	}
	
	function resetcovercharge(){
		$("#selcovercharge").form("reset");
		selcovercharge();
	}
	
	function exportCharge(){
		var map = {
				productName:$("#productName").textbox('getValue'),
				productCode:$("#productCode").textbox('getValue'),
				loanName:$("#loanName").textbox('getValue'),
				startserviceTime:$("#startserviceTime").datebox('getValue'),
				endserviceTime:$("#endserviceTime").datebox('getValue'),
				startinvoiceTime:$("#startinvoiceTime").datebox('getValue'),
				endinvoiceTime:$("#endinvoiceTime").datebox('getValue'),
				number:$("#number").textbox('getValue'),
				serviceStatus:1,
				invoiceStatus:1
		};
		window.location.href="../product/exportCharge.do?"+$.param(map);
	}
	
	//服务费经办窗口
	function addcharge(index){
		covercharge.datagrid('selectRow',index);// 关键在这里 
	    var row = covercharge.datagrid('getSelected'); 
		$("#addchargeForm").form("reset");
		$("#code").textbox("setValue",row.code);
		$("#serviceCharge").numberbox("setValue",row.serviceCharge);
		$("#receivedGuarantee").numberbox("setValue",row.receivedGuarantee);
		$("#serviceRemerk").textbox("setValue",row.serviceRemerk);
		$("#serviceTime").datetimebox("setValue",row.serviceTime);
		$("#addcharge").dialog("open");
	}
	
	//开票信息经办窗口
	function addinvotice(index){
		covercharge.datagrid('selectRow',index);// 关键在这里 
	    var row = covercharge.datagrid('getSelected'); 
		$("#addinvoticeForm").form("reset");
		$("#invoticecode").textbox("setValue",row.code);
		$("#invoiceAmount").numberbox("setValue",row.invoiceAmount);
		
		$("#invoiceNumber").textbox("setValue",row.invoiceNumber);
		$("#invoiceRemerk").textbox("setValue",row.invoiceRemerk);
		$("#invoiceTime").datetimebox("setValue",row.invoiceTime);
		$("#addinvotice").dialog("open");
	}
	
	function insertCharge(){
		var serviceStatus = $("input[name='serviceStatus']:checked").val();
		$.messager.confirm('操作提示', '确定提交吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../product/updateCharge.do",
						data : {
							code:$("#code").textbox('getValue'),
							serviceStatus:serviceStatus,
							serviceRemerk:$("#serviceRemerk").textbox('getValue')
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#addcharge").dialog("close");
								selcovercharge();
							}else{
								$.messager.alert("提示信息",result.errorMsg);
								selcovercharge();
							}
					    }
					 });
			 } 
		 })
	}
	
	function insertInvotice(){
		var invoiceStatus = $("input[name='invoiceStatus']:checked").val();
		$.messager.confirm('操作提示', '确定提交吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../product/updateInvoice.do",
						data : {
							code:$("#invoticecode").textbox('getValue'),
							invoiceStatus:invoiceStatus,
							invoiceRemerk:$("#invoiceRemerk").textbox('getValue')
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#addinvotice").dialog("close");
								selcovercharge();
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

