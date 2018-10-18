<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
</head>
<body>
	<table id="jsNoviceContinueRecordList" title="新手标续投管理" class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../noviceContinueRecord/jsNoviceContinueRecordList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#jsNCRTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="hidden"/>
	        <th data-options="field:'realName'" width="10%">客户姓名</th>
	        <th data-options="field:'recommCodes'" width="10%">推荐码</th>
	        <th data-options="field:'mobilePhone'" width="10%">手机号码</th>
	        <th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">订单日期</th>
	        <th data-options="field:'shouldTime'" width="10%" formatter="formatDate">还款日期</th>
	        <th data-options="field:'period'" width="10%" formatter="formatPeriod">续投产品</th>
	        <th data-options="field:'pCode'" width="10%">产品编号</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">续投金额</th>
	        <th data-options="field:'reward'" width="10%"styler="styleColor" formatter="formatAmount">续投奖励金额</th>
	        <th data-options="field:'status'" width="10%" formatter="formatStatus">状态</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsNCRTools" style="padding:5px;height:750">
	  	<form id="jsNCRForm">
	  		手机号码:<input id="searchMobilePhone" name="mobilePhone" class="easyui-textbox" size="30" style="width:150px"/>
<!-- 	  		客户姓名:<input id="searchRealName" name="realName" class="easyui-textbox" size="30" style="width:150px" /> -->
	  		推荐码:<input id="searchRecommCodes" name="recommCodes" class="easyui-textbox" size="30" style="width:100px"/>
	  		订单日期:<input id="searchStartAddTime" name="startAddTime" class="easyui-datebox"style="width:150px"/>到
	  		       <input id="searchEndAddTime" name="endAddTime" class="easyui-datebox" style="width:150px"/>
	  		还款日期:<input id="searchStartShouldTime" name="startShouldTime" class="easyui-datebox"style="width:150px"/>到
	  		       <input id="searchEndShouldTime" name="endShouldTime" class="easyui-datebox" style="width:150px"/>
	  		续投产品<select id="searchPeriod" name="period" style="width:100px" class="easyui-combobox">
	  					<option value="" selected="selected">全部</option>
	  					<option value="30">30天产品</option>
	  					<option value="60">60天产品</option>
	  					<option value="180">180天产品</option>
	  				</select> 
	  		预约状态:<select id="searchStatus" name="status" style="width:100px" class="easyui-combobox">
	  					<option value="" selected="selected">全部</option>
	  					<option value="0">待续投</option>
	  					<option value="1">已续投</option>

	  				</select>      
	    	<a id="searchJsNCRBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsNCRBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportJsNCRBtn" href="javascript:exportJsNCRBtn();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$("#resetJsNCRBtn").click(function(){
		$("#jsNCRForm").form("reset");
		$("#jsNoviceContinueRecordList").datagrid("load", {});
	});
	
	//查询按钮
	$("#searchJsNCRBtn").click(function(){
 		$("#jsNoviceContinueRecordList").datagrid({
			queryParams: {
 				mobilePhone:$("#searchMobilePhone").val(),
 				realName:$("#searchRealName").val(),
 				recommCodes:$("#searchRecommCodes").val(),
 				startAddTime:$("#searchStartAddTime").datebox("getValue"),
				endAddTime:$("#searchEndAddTime").datebox("getValue"),
 				startShouldTime:$("#searchStartShouldTime").datebox("getValue"),
				endShouldTime:$("#searchEndShouldTime").datebox("getValue"),
				period:$("#searchPeriod").combobox("getValue"),
				status:$("#searchStatus").combobox("getValue")
			}
		}); 
	});
	

	function formatAmount(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatStatus(val,row,index){
		if(val=='0'){
			return '待续投';
		}else {
			return '已续投';
		}
	}
	
	
	function exportJsNCRBtn(){
		window.location.href="../noviceContinueRecord/exportJsNCR.do?mobilePhone="+$('#searchMobilePhone').val()+
						"&realName="+encodeURIComponent(encodeURIComponent($('#searchRealName').val()))+
 						"&startAddTime="+$('#searchStartAddTime').datebox('getValue')+
 						"&recommCodes="+$('#searchRecommCodes').textbox('getValue')+
 						"&endAddTime="+$('#searchEndAddTime').datebox('getValue')+
 						"&startShouldTime="+$('#searchStartShouldTime').datebox('getValue')+
 						"&endShouldTime="+$('#searchEndShouldTime').datebox('getValue')+
 						"&period="+$('#searchPeriod').combobox('getValue')+
 						"&status="+$('#searchStatus').combobox('getValue');

	}
	
	function formatDate(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	
	function formatPeriod(value) {
		if (value == null || value == '') {  
	        return '';  
	    }   
	    return value+"天产品"; 
	}
</script>
</body>
</html>

