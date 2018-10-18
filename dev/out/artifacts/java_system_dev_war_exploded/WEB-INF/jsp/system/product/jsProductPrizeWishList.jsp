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
	<table id="drProductPrizeWishList" title="礼品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../productPrize/jsProductPrizeWishList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#jsProductPrizeTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	       	<th data-options="field:'realname'" width="5%">姓名</th>
	       	<th data-options="field:'mobilePhone'" width="10%">联系方式</th>
	       	<th data-options="field:'recommCodes'" width="10%">推荐码</th>
	       	<th data-options="field:'addtime'" width="10%">提交时间</th>
	       	<th data-options="field:'url'" width="15%">礼品链接</th>
	       	<th data-options="field:'remark'" width="12%">备注</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsProductPrizeTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeWishForm" target="_blank" method="post">
	                   账号:<input class="easyui-textbox" id="mobilePhone"  name="mobilePhone" />
	      	时间:<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		<a id="searchJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="exportBtn()">导出</a>
	    </form>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeWishForm").form("reset");
		$("#drProductPrizeWishList").datagrid("load", {});
	});
	//查询按钮
	$('#searchJsProductPrize').click(function(){
 		$('#drProductPrizeWishList').datagrid({
			queryParams: {
				mobilePhone:$('#mobilePhone').textbox('getValue'),		
				startDate:$('#startDate').datebox('getValue'),		
				endDate:$('#endDate').datebox('getValue')	
			}
		});
	});
	function exportBtn(){
		window.location.href="../productPrize/jsProductPrizeWishListExport.do?startDate="+$('#startDate').datebox('getValue')+
						"&endDate="+$('#endDate').datebox('getValue')+
						"&mobilePhone="+$('#mobilePhone').textbox('getValue');
	}
</script>
</body>
</html>

