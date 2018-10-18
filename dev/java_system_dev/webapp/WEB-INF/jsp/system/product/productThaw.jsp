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
	<div style="height:100%;width:100%;">
		<table id="product"  style="height:99%;width:99.9%"></table>
	</div>
	
<script type="text/javascript">
	    
		//账户列表
		var product = $('#product');
		$(function() {
			product.datagrid({
				url : "../product/getProductThaw.do",
				title : '待解冻产品列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				fit:true,
				columns : [ [ 
					{
					field : 'id',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'sid',
					title : 'sid',
					align : "center",
					hidden:true
					},{
					field : 'code',
					title : '产品编号',
					width : '12%',
					align : "center"
					},{
					field : 'fullName',
					title : '产品名称',
					width : '8%',
					align : "center"
					},{
					field : 'rate',
					title : '产品利率',
					width : '4%',
					align : "center"	
					},{
					field : 'deadline',
					title : '产品期限',
					width : '4%',
					align : "center"	
					},{
					field : 'amount',
					title : '产品金额',
					width : '5%',
					align : "center"
					},{
					field : 'alreadyRaiseAmount',
					title : '已募集金额',
					width : '5%',
					align : "center"
					},{
					field : 'status',
					title : '产品状态',
					width : '5%',
					align : "center"
					},{
					field : 'establish',
					title : '成立日期',
					width : '10%',
					align : "center"
					},{
					field : 'expireDate',
					title : '回款日期',
					width : '10%',
					align : "center"
					},{
					field : 'fullDate',
					title : '募集成功日期',
					width : '10%',
					align : "center"
					},{
					field : 'actLoanTime',
					title : '实际放款日期',
					width : '10%',
					align : "center"
					},{
					field : ' ',
					title : '操作',
					width : '6%',
					formatter:function(value,row,index){
							var ele = '<a href="#"  onclick="thaw(' + JSON.stringify(row).replace(/"/g, '&quot;') + ');">解冻</a>';
							return ele;
	                      },
						align : "center"
					}
				] ]
			});
		}); 
		
		function thaw(row){
			 $.messager.confirm('操作提示', '确定解冻吗！', function (data) {		
				 	if(data){
				 		$.ajax({
							type: 'post',
							url : "../product/thaw.do",
							data : {
								id : row.id
								},
							success : function(message) {
								if (message=='success') { 	
									$.messager.alert('操作提示','操作成功');
									product.datagrid("load", {})
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

