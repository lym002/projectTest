
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
	<table id="drProductPrizeList" title="5880年化奖励管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../festivaIactivity/yearInvestList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'uid'" hidden="true">ID</th>
	    	<th data-options="field:'mobilePhone'" width="10%">用户手机</th>
	        <th data-options="field:'amount'"  width="10%">投资金额</th>
	        <th data-options="field:'price'"  width="5%">京东卡奖励</th>
	    	<th data-options="field:'tjStartTime'"  width="10%"  formatter="formatDateBoxFull">最近投资时间</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	    	<form id="drProductPrizeForm" target="_blank" method="post">
	  		手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="11" style="width:200px"/>
	      	   日期:<input id="starttime" name="starttime" class="easyui-datebox" style="width:100px" />
	  		至:<input id="endtime" name="endtime" class="easyui-datebox" style="width:100px"/>
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeForm").form("reset");
		$("#drProductPrizeList").datagrid("load", {});
	});
	//查询按钮
	function searchJsProductPrize(){
 		$('#drProductPrizeList').datagrid({
			queryParams: {
				mobilePhone:$('#mobilePhone').val(),
				starttime:$('#starttime').datebox('getValue'),
				endtime:$('#endtime').datebox('getValue')
			}
		});
	};
	

	
	
	

	
	$(document).ready(function () {
/* 		$('#drProductPrizeList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../prizemanage/prizeLogCount.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeLogForm").serialize(), 
					success:function(data){
						$('#productPrizeCount').text(data.count);
					}
				});
			} 
    	}); */
	});
</script>
</body>
</html>

