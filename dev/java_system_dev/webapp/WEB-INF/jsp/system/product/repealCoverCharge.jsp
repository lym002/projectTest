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
      	  <!--   <a id="importCharge" href="javascript:importCharge();" class="easyui-linkbutton" iconCls="icon-add">导入服务费</a>
       	    <a id="importjsinvoice" href="javascript:importjsinvoice();" class="easyui-linkbutton" iconCls="icon-add">导入开票信息</a>  -->    	
		</form>
	</div>
	
<script type="text/javascript">
	var covercharge = $('#covercharge');
	covercharge.datagrid({
		url : "../product/getProductServiceManagement.do", 
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
				return toDecimal2(amount);
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
				return toDecimal2(amount-guarantee);
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
				return toDecimal2(amount);
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
				/* if(row.serviceStatus=='' || row.serviceStatus==null || row.serviceStatus==3){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"addcharge(\'"+index+"')\">服务费经办</a>  "
				} */
				if(row.serviceStatus==2){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"delcharge(\'"+row.code+"')\">服务费撤销</a>  "
				}
			/* 	if(row.invoiceStatus=='' || row.invoiceStatus==null || row.invoiceStatus==3){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"addinvotice(\'"+index+"')\">开票信息经办</a>  "
				} */
				if(row.invoiceStatus==2){
					str=str+"<a href='#' class='easyui-linkbutton' onclick=\"delinvotice(\'"+row.code+"')\">开票信息撤销</a>  "
				}
				return str;
			}
			}				
		] ]
	});
	
	
	//服务费撤销
	function delcharge(code){
		 $.messager.confirm('操作提示', '确定撤销服务费吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../product/deleteCharge.do",
						data : {
							code:code
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
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
	
	
	//开票信息撤销
	function delinvotice(code){
		 $.messager.confirm('操作提示', '确定撤销开票信息吗！', function (data) {		
			 if(data){
				 $.ajax({
						type: 'post',
						url : "../product/deleteInvoice.do",
						data : {
							code:code
						},
						success : function(result) {
							if(result.success){
								$.messager.alert("提示信息",result.msg);
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
	
	function selcovercharge(){
		covercharge.datagrid('reload', {
				productName:$("#productName").textbox('getValue'),
				productCode:$("#productCode").textbox('getValue'),
				loanName:$("#loanName").textbox('getValue'),
				startserviceTime:$("#startserviceTime").datebox('getValue'),
				endserviceTime:$("#endserviceTime").datebox('getValue'),
				startinvoiceTime:$("#startinvoiceTime").datebox('getValue'),
				endinvoiceTime:$("#endinvoiceTime").datebox('getValue'),
				number:$("#number").textbox('getValue')
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
				number:$("#number").textbox('getValue')
		};
		window.location.href="../product/exportCharge.do?"+$.param(map);
	}
	
	function toDecimal2(num) { 
	    var bb = num+"";  
	    var dian = bb.indexOf('.');  
	    var result = "";  
	    if(dian == -1){  
	        result =  num.toFixed(2);  
	    }else{  
	    	result=bb.substring(0,bb.lastIndexOf('.')+3);
	    }  
	    return result;
	  } 
</script>
</body>
</html>

