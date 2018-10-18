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
	<table id="drInvestOrderList" title="体验标投资订单 "
	style="height:99%;">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="4%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="5%" >手机号码</th>
	        <th data-options="field:'recomRealName'" width="5%">推荐人姓名</th>
	        <th data-options="field:'recomMobilePhone'" width="5%">推荐人号码</th>
	        <th data-options="field:'fullName'" width="8%">产品名称</th>
	        <th data-options="field:'investTime'" width="8%">订单时间</th>
	        <th data-options="field:'joinType'" width="4%">投资终端</th>
	        <th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	        <th data-options="field:'chanelName'" width="5%">注册渠道</th>
	        <th data-options="field:'regdate'" width="8%">注册日期</th>
	        <th data-options="field:'amount'" width="5%" styler="styleColor" formatter="formatAmount">体验金金额</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor" formatter="formatAmount">预计收益</th>
	        <th data-options="field:'statusName'" width="5%">投资状态</th>
	        
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		产品名称:<input id="searchDrProductInvestFullName" name="fullName" class="easyui-textbox" value="" size="30" style="width:100px"/>
	  		用户手机:<input id="searchDrProductInvestMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
	  		用户姓名:<input id="searchDrProductInvestRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/>
	  		订单日期:<input id="searchDrProductInvestStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  			  <input id="searchDrProductInvestEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		渠道名称:<select class="easyui-combogrid" id="cid" name="cid" style="width:240px;padding-bottom: 3px;" data-options="
							panelWidth: 240,
							multiple: true,
							multiline:true,
							editable:false,
							idField: 'id',
							textField: 'name',
							url: '../channel/drAllChannelInfoList.do?orders=1',
							method: 'get',
							columns: [[
								{field:'id',checkbox:true},
								{field:'code',title:'渠道号',width:80},
								{field:'name',title:'渠道名称',width:80},
							]],
							fitColumns: true
						">
					</select>
	    	<a id="searchInvestOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInvestOrder()">查询</a>
	    	<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
	    	<a id="exportDrProductInvest" href="javascript:exportInvestOrder();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetBtn').click(function(){
		$("#drProductInvestForm").form("reset");
		$("#drInvestOrderList").datagrid("load", {});
	});
	//查询按钮
	function searchInvestOrder(){
		var cids = $('#cid').combogrid('getValues')+"";
 		$('#drInvestOrderList').datagrid({
 			url: '../investOrder/experienceInvestOrderList.do',
			queryParams: {
				realname:$('#searchDrProductInvestRealname').val(),
				fullName:$('#searchDrProductInvestFullName').val(),
				mobilephone:$('#searchDrProductInvestMobilephone').val(),
				startDate:$('#searchDrProductInvestStartDate').combobox('getValue'),
				endDate:$('#searchDrProductInvestEndDate').combobox('getValue'),
				cids:cids
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#drProductInvestTools'
		});
	};
	
	//导出
	function exportInvestOrder(){
		window.location.href="../investOrder/exportExperienceInvestOrderInfo.do?realname="+encodeURIComponent(encodeURIComponent($('#searchDrProductInvestRealname').val()))+
						"&fullName="+encodeURIComponent(encodeURIComponent($('#searchDrProductInvestFullName').val()))+
						"&mobilephone="+$('#searchDrProductInvestMobilephone').val()+
						"&startDate="+$('#searchDrProductInvestStartDate').combobox('getValue')+
						"&endDate="+$('#searchDrProductInvestEndDate').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues');
	}

	//获取当前时间
	function getdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate 
	return currentdate; 
	} 
	
	//首次查询
	window.onload = function(){
		$('#searchDrProductInvestStartDate').datebox('setValue',getdate());
		$('#searchDrProductInvestEndDate').datebox('setValue',getdate());
		searchInvestOrder();
	}
	
</script>
</body>
</html>