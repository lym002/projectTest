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
	<table id="drProductLoanRecordList" title="新手标放款回款" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../loanRecord/drProductLoanRecordList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#drProductLoanTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'pid'" hidden="true"></th>	    	
	    	<th data-options="field:'contractCode'" width="10%">合同编号</th>
	        <th data-options="field:'simpleName'" width="10%">项目简称</th>
	       	<th data-options="field:'loanName'" width="10%">债权名称</th>
	       	<th data-options="field:'company'" width="10%">收款单位全称</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">项目借款金额(元)</th>
	       	<th data-options="field:'prePayDate'" width="10%" formatter="iFormatDateBoxFull">预计付款日期</th>
	       	<th data-options="field:'shouldDate'" width="10%" formatter="iFormatDateBoxFull">回款日期</th>
	       	<th data-options="field:'fullDate'" width="10%" formatter="iFormatDateBoxFull">募集成功日期</th>
	       	<th data-options="field:'loanTime'" width="10%" formatter="iFormatDateTimeBoxFull">放款日期</th>
	       	<th data-options="field:'addDate'" width="10%" formatter="iFormatDateTimeBoxFull">新增时间</th>
	       	<th data-options="field:'updateTime'" width="10%" formatter="iFormatDateTimeBoxFull">更新时间</th>
	        <th data-options="field:'deadline'" width="5%" >期限</th>
	        <th data-options="field:'rate'" width="5%" >年化利率</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor">利息</th>
	        <th data-options="field:'loanStatus'" width="5%" formatter="iformatter">放款状态</th>
	        <th data-options="field:'principalInterest'" width="5%" styler="styleColor">预计应收本息</th>
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductLoanTools" style="padding:5px;height:750">
	  	<form id="drProductLoanForm" target="_blank" method="post">
	  		产品简称:<input id="searchDrProductLoanSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		借款方:<input id="searchDrProductLoanCompany" name="company" class="easyui-textbox"  size="15" style="width:200px"/>
	  		回款日期:<input id="searchDrProductLoanloanstartDate" name="refundStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanEndDate" name="refundEndDate" class="easyui-datebox" style="width:100px"/>
	  		募集成功日期:<input id="searchDrProductLoanloanFullstartDate" name="fullStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanFullEndDate" name="fullEndDate" class="easyui-datebox" style="width:100px"/>
	  		放款日期:<input id="startactLoanTime" name="startactLoanTime" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endactLoanTime" name="endactLoanTime" class="easyui-datebox" style="width:100px"/>
	                   放款状态:<select id="searchDrProductLoanStatus" name="loanStatus" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0'>未放款</option>
		  					<option value='1'>已放款</option>
		  					<option value='2'>已回款</option>
	  				</select>
	  		<a id="searchDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exprotProductLoanAuditList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出放款审核表</a>
	    	<a id="productPaymentNoticeList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出项目还款通知表</a>
	    	<a id="productReturnNoticeList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" >导出项目回款通知表</a>
	    </form>
	</div>
	
	<!-- -----------------放款------------------------------->
	<div id="addLending" class="easyui-dialog" style="height:15%;width:20%" closed="true"	buttons="addLendingBtn"  data-options="resizable:true,modal:true,closed:true">
		<center>
		<div style="padding:5px;height:50">
			实际放款时间：<input id="loanTime" name="loanTime" class="easyui-datetimebox" style="width:140px"/>
			<input id="line" type="hidden"/>
		</div>
		<div id="addLendingBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateDrProductLoanBtn()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addLending').dialog('close')">取消</a>
		</div>
		</center>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetDrProductLoan').click(function(){
		$("#drProductLoanForm").form("reset");
		$("#drProductLoanRecordList").datagrid("load", {});
	});
	//查询按钮
	$('#searchDrProductLoan').click(function(){
 		$('#drProductLoanRecordList').datagrid({
			queryParams: {
				company:$('#searchDrProductLoanCompany').val(),
				refundStartDate:$('#searchDrProductLoanloanstartDate').datebox('getValue'),
				refundEndDate:$('#searchDrProductLoanloanEndDate').datebox('getValue'),
				fullStartDate:$('#searchDrProductLoanloanFullstartDate').datebox('getValue'),
				fullEndDate:$('#searchDrProductLoanloanFullEndDate').datebox('getValue'),
				simpleName: $('#searchDrProductLoanSimpleName').val(),
				loanStatus:$('#searchDrProductLoanStatus').combobox('getValue'),
				startactLoanTime:$('#startactLoanTime').datebox('getValue'),
				endactLoanTime:$('#endactLoanTime').datebox('getValue')
			}
		});
	});
	//导出放款审批表
	$('#exprotProductLoanAuditList').click(function(){
		/* window.location.href="../loanRecord/exprotProductLoanRecordAuditList.do?company="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanCompany').val()))+
		"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
		"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
		"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
		"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
		"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
		"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
		"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
		"&endactLoanTime="+$('#endactLoanTime').datebox('getValue'); */
		//改造导出功能查询无数据时报404错
		var url = "../loanRecord/exprotProductLoanRecordAuditList.do";
		$('#drProductLoanForm').form('submit',{
			url:url,
			success:function(data){
				console.log(data);
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	//导出项目还款通知表
	$('#productPaymentNoticeList').click(function(){
		/* window.location.href="../loanRecord/productPaymentRecordNoticeList.do?company="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanCompany').val()))+
		"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
		"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
		"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
		"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
		"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
		"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
		"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
		"&endactLoanTime="+$('#endactLoanTime').datebox('getValue'); */
		var url= "../loanRecord/productPaymentRecordNoticeList.do";
		$('#drProductLoanForm').form('submit',{
			url:url,
			success:function(data){
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	
	    
	//导出项目回款通知表
	$('#productReturnNoticeList').click(function(){
		var startDate = $('#searchDrProductLoanloanstartDate').datebox('getValue');//回款开始日期
		var EndDate = $('#searchDrProductLoanloanEndDate').datebox('getValue');//回款结束日期
		if(startDate == null || EndDate == null || startDate == "" || EndDate == ""){
			$.messager.alert('系统提示','请选择回款日期！');  
			return false;
		}else{
			/* window.location.href="../loanRecord/productReturnNoticeList.do?company="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanCompany').val()))+
			"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
			"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
			"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
			"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
			"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
			"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
			"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
			"&endactLoanTime="+$('#endactLoanTime').datebox('getValue'); */
			var url= "../loanRecord/productReturnNoticeList.do";
				$('#drProductLoanForm').form('submit',{
					url:url,
					success:function(data){
						console.log(data);
						var d = $.parseJSON(data);
						if(!d.success){
							alert(d.msg);
						}
					}
				});
		};
	});
	
	
	//添加放款基本操作链接
	function formatOper(val,row,index){  
		if(row.loanStatus=="1" || row.loanStatus=="0"){
			if(row.loanStatus=="0"){
				return '<a href="#" class="btn l-btn l-btn-small" onclick="addLending('+index+')">放款</a>'+"   "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="updateRefundDrProductLoanRecordBtn('+index+')">回款</a>'+"   "+
	            '<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   ";
			}
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateRefundDrProductLoanRecordBtn('+index+')">回款</a>'+"   "+
            '<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   ";
		}
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   ";
    }
		//放款操作
	function updateDrProductLoanBtn(){
		$('#drProductLoanRecordList').datagrid('selectRow',$('#line').val());// 关键在这里 
	    var row = $('#drProductLoanRecordList').datagrid('getSelected'); 
		
		if($('#loanTime').datetimebox("getValue")==null || $('#loanTime').datetimebox("getValue")==""){
			$.messager.alert('系统提示','请选择实际放款时间！');  
			return false;
		}
		var url = "${apppath}/loanRecord/updateDrProductLoanRecordBtn.do?id="+row.id+"&loanTime="+$('#loanTime').datetimebox("getValue");
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$('#addLending').dialog('close');
					$('#drProductLoanRecordList').datagrid('reload');
					$.messager.alert("操作提示", result.msg);
				}else{
					$('#drProductLoanRecordList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
		//回款操作
	function updateRefundDrProductLoanRecordBtn(index){
		$('#drProductLoanRecordList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductLoanRecordList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定回款？", function(ensure){
			if(ensure){
				var url = "../loanRecord/updateReFundRecordDrProductLoan.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drProductLoanRecordList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$('#drProductLoanRecordList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	}
	function iformatter(value,row,index){
		if(row.loanStatus == "0"){
			return "未放款";
		}else if(row.loanStatus == "1"){
			return "已放款";
		}else{
			return "已回款";
		}
	}
	//跳转到产品显示页面
	function toProductInfoBtn(index){
		$('#drProductLoanRecordList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductLoanRecordList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "产品显示",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/product/toShowDrProductInfo.do?id="+row.pid+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	//修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
	
	//弹出放款框
function addLending(index){
	$('#line').val(index);
	$('#addLending').dialog('open').dialog('setTitle', '确认放款');
	$('#loanTime').datetimebox('setValue','');	
}
</script>
</body>
</html>

