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
	<table id="product"  style="height:99%;width:99.9%"></table>
	<div id="productbtn" style="padding:5px;height:750">
		<form id="selproduct">
			产品编号:<input id="code" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
			项目名称:<input id="fullName" name="fullName" class="easyui-textbox"  size="15" style="width:100px"/>
			借款方:<input id="loanName" name="loanName" class="easyui-textbox"  size="15" style="width:100px"/>
			项目合同:<input id="contractCode" name="contractCode" class="easyui-textbox"  size="15" style="width:100px"/>
			满标日期:<input id="startfullDate" name="startfullDate" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endfullDate" name="endfullDate" class="easyui-datebox"  size="15" style="width:100px"/>
			付款日期:<input id="startloanTime" name="startloanTime" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endloanTime" name="endloanTime" class="easyui-datebox"  size="15" style="width:100px"/>
			预计还款日期:<input id="startexpireDate" name="startexpireDate" class="easyui-datebox"  size="15" style="width:100px"/>至
				  <input id="endexpireDate" name="endexpireDate" class="easyui-datebox"  size="15" style="width:100px"/>
			还款状态：<select  id="isReimbursement" name="isReimbursement" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="9">已还款</option>
				 	<option value="0">未还款</option>
	           	 </select>
            <br/>服务费收款日期:<input id="startserviceTime" name="startserviceTime" class="easyui-datebox"  size="15" style="width:100px"/>至
			<input id="endserviceTime" name="endserviceTime" class="easyui-datebox"  size="15" style="width:100px"/>
			服务费收取状态：<select  id="isService" name="isService" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">已收</option>
				 	<option value="2">未收</option>
	           	   </select>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selproduct()">查询</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetproduct()">重置</a>
	        <a id="exportproduct" href="javascript:exportproduct();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	        
	        
		</form>
	</div>
<script type="text/javascript">
	var product = $('#product');
	product.datagrid({
		url : "../product/getProductInfoDetail.do",
		title : '项目数据情况',
		fitColumns : true,
		pagination : true,
		checkbox:true,
		pageSize:25,
		pageList:[25,50,100],
		autoRowHeight : false,
		toolbar:"#productbtn", 
		fit:true,
		columns : [ [ 
			{
			field : 'code',
			title : '项目产品编号',
			align : "center"
			},{
			field : 'contractCode',
			title : '项目合同编号',
			align : "center"
			},{
			field : 'fullName',
			title : '项目名称',
			align : "center"
			},{
			field : 'loanName',
			title : '借款方',
			align : "center"
			},{
			field : 'amount',
			title : '项目借款金额',
			align : "center"
			},{
			field : 'rate',
			title : '年化利率',
			align : "center"
			},{
			field : 'deadline',
			title : '期限',
			align : "center"
			},{
			field : 'interest',
			title : '利息',
			align : "center"
			},{
			field : 'principalInterest',
			title : '应收本息',
			align : "center"
			},{
			field : 'fullDate',
			title : '满标日期',
			align : "center"
			},{
			field : 'loanTime',
			title : '实际付款日期',
			align : "center"
			},{
			field : 'loanAmount',
			title : '付款金额',
			align : "center"
			},{
			field : 'expireDate',
			title : '预计还款日',
			align : "center"
			},{
			field : 'factTime',
			title : '实际还款日',
			align : "center"
			},{
			field : 'factPrincipal',
			title : '还款本金',
			align : "center"
			},{
			field : 'factInterest',
			title : '还款利息',
			align : "center"
			},{
			field : 'isReimbursement',
			title : '已还/未还',
			align : "center"
			},{
			field : 'a',
			title : '预计服务费',
			align : "center",
			formatter:function(value,row,index){
				var amount=0;
				if(row.countAmount<=row.sumamount){//首笔 预计费用总额=产品金额*8%
					amount=row.amount*8/100;
				}else{
					var a=row.countAmount-row.sumamount;//计算超出多少
					if(a>=row.amount){
						amount=row.amount*1*row.deadline/30/100;//全部超出
					}else{
						var b=row.amount-a;//非部分
						amount=b*8/100;
						amount=(a*1*row.deadline/30/100)+amount;//超出部分	
					}	
				}
				var guarantee=0;
				if(row.countAmount<=row.sumamount){//首笔 产品金额*1.5%
					guarantee=row.amount*1.5/100;
				}else{
					var a=row.countAmount-row.sumamount;//计算超出多少
					if(a<row.amount){//超出部分
						var b=row.amount-a;//非部分
						guarantee=b*1.5/100;//超出部分
					}
				}
				return amount-guarantee;
			}
			},{
			field : 'serviceTime',
			title : '实际收到日期',
			align : "center"
			},{
			field : 'serviceCharge',
			title : '已收服务费',
			align : "center"
			},{
			field : 'b',
			title : '已收/未收',
			align : "center",
			formatter:function(value,row,index){
				if(row.serviceCharge!=null && row.serviceCharge!=''){
					return "已收";
				}else{
					return "未收";
				}				
			}
			},{
			field : 'c',
			title : '是否首笔',
			align : "center",
			formatter:function(value,row,index){
				if(row.countAmount<=row.sumamount){//首笔
					return "是";
				}else{
					return "否";
				}				
			}	
			}
		] ]
	});
	
	//查询按钮
	function selproduct(){
		product.datagrid('reload', {
				code: $('#code').textbox("getValue"),
				fullName:$('#fullName').textbox("getValue"),
				loanName: $('#loanName').textbox("getValue"),
				contractCode: $('#contractCode').textbox("getValue"),
				startfullDate: $('#startfullDate').datebox("getValue"),
				endfullDate: $('#endfullDate').datebox("getValue"),
				startloanTime: $('#startloanTime').datebox("getValue"),
				endloanTime: $('#endloanTime').datebox("getValue"),
				startexpireDate: $('#startexpireDate').datebox("getValue"),
				endexpireDate: $('#endexpireDate').datebox("getValue"),
				isReimbursement: $('#isReimbursement').combobox("getValue"),
				startserviceTime:$('#startserviceTime').datebox("getValue"),
				endserviceTime:$('#endserviceTime').datebox("getValue"),
				isService: $('#isService').combobox("getValue")
           	});
	}
	
	function exportproduct(){
		var map = {
				code: $('#code').textbox("getValue"),
				fullName:$('#fullName').textbox("getValue"),
				loanName: $('#loanName').textbox("getValue"),
				contractCode: $('#contractCode').textbox("getValue"),
				startfullDate: $('#startfullDate').datebox("getValue"),
				endfullDate: $('#endfullDate').datebox("getValue"),
				startloanTime: $('#startloanTime').datebox("getValue"),
				endloanTime: $('#endloanTime').datebox("getValue"),
				startexpireDate: $('#startexpireDate').datebox("getValue"),
				endexpireDate: $('#endexpireDate').datebox("getValue"),
				isReimbursement: $('#isReimbursement').combobox("getValue"),
				startserviceTime:$('#startserviceTime').datebox("getValue"),
				endserviceTime:$('#endserviceTime').datebox("getValue"),
				isService: $('#isService').combobox("getValue")
		};
		window.location.href="../product/exportProductInfoDetail.do?"+$.param(map);
	}
	
	function resetproduct(){
		$("#selproduct").form("reset");
	}
</script>
</body>
</html>

