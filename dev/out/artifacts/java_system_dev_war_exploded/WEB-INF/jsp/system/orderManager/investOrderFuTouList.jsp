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
	<table id="drInvestOrderList" title="投资订单 "
	style="height:99%;">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="4%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="5%" >手机号码</th>
	        <th data-options="field:'regdate'" width="8%">注册日期</th>
	        <th data-options="field:'chanelName'" width="5%">注册渠道</th>
	        <th data-options="field:'count_30'" width="5%">30~40</th>
	        <th data-options="field:'count_60'" width="5%">60</th>
	        <th data-options="field:'count_90'" width="5%" >90/150</th>
	        <th data-options="field:'count_180'" width="5%" >180</th>
	        <th data-options="field:'count_all'" width="4%">投资总次数</th>
	        <th data-options="field:'uid'" width="4%" hidden ="true">用户id</th>
	        <th data-options="field:'investAmountSUM'" width="6%" styler="styleColor" formatter="formatAmount">投资总和</th>
	        <th data-options="field:'resultDate'" width="6%" formatter="formatOper">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		<!-- 用户姓名:<input id="searchDrProductInvestRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/> -->
	  		用户手机:<input id="searchDrProductInvestMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
  		         注册日期:<input id="searchDrProductInvestStartRegDate" name="startRegDate" class="easyui-datebox" style="width:100px"/>到
  			  <input id="searchDrProductInvestEndRegDate" name="endRegDate" class="easyui-datebox" style="width:100px"/>
	  		渠道名称:<select class="easyui-combogrid" id="cid" name="cids" style="width:240px;padding-bottom: 3px;" data-options="
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
			投资总次数:<input id="count_allStart" name="count_allStart" class="easyui-textbox"  size="30" style="width:50px"/>至
			<input id="count_allEnd" name="count_allEnd" class="easyui-textbox"  size="30" style="width:50px"/>
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
 			url: '../investOrder/getInvestListForFuTou.do',
			queryParams: {
				realname:$('#searchDrProductInvestRealname').val(),
				mobilephone:$('#searchDrProductInvestMobilephone').val(),
				startRegDate:$('#searchDrProductInvestStartRegDate').combobox('getValue'),
				endRegDate:$('#searchDrProductInvestEndRegDate').combobox('getValue'),
				cids:cids,
				count_allStart:$("#count_allStart").val(),
				count_allEnd:$("#count_allEnd").val(),
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#drProductInvestTools'
		});
	};
	
	//导出
	function exportInvestOrder(){
		window.location.href="../investOrder/exportInvestListForFuTou.do?mobilephone="+$('#searchDrProductInvestMobilephone').val()+
						"&startRegDate="+$('#searchDrProductInvestStartRegDate').combobox('getValue')+
						"&endRegDate="+$('#searchDrProductInvestEndRegDate').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues')+
						"&count_allStart="+$('#count_allStart').val()+
						"&count_allEnd="+$('#count_allEnd').val();
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
		$('#searchDrProductInvestStartRegDate').datebox('setValue',getdate());
		$('#searchDrProductInvestEndRegDate').datebox('setValue',getdate());
		searchInvestOrder();
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.uid != null){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowInvestListBtn('+index+')">查看</a>'+"   ";
		}
	} 
	
	function toShowInvestListBtn(index){
		//跳转到产品显示页面
		$('#drInvestOrderList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drInvestOrderList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "投资列表",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/investOrder/toProductInvestListByUid.do?uid="+row.uid+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
</script>
</body>
</html>