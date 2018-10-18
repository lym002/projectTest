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
	<table id="memberCrushList" title="充值待审" class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../crush/memberCrushList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#memberCrushTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
			<th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">充值时间</th>
	        <th data-options="field:'realName'" width="10%" >用户姓名</th>
	        <th data-options="field:'recommCodes'" width="10%" >推荐码</th>
	        <th data-options="field:'phone'" width="10%">用户手机号</th>
	       	<th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">充值金额</th>
	       	<th data-options="field:'channelName'" width="5%">充值渠道</th>
	       	<th data-options="field:'name'" width="10%">操作人员</th>	       	
	        <th data-options="field:'statusName'" width="5%">充值状态</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="memberCrushTools" style="padding:5px;height:750">
	  	<form id="memberCrushForm">
	  		充值时间：<input id="searchMemberCrushStartDate" name="startDate" class="easyui-datebox"/>
	  		至<input id="searchMemberCrushEndDate" name="endDate" class="easyui-datebox"/>
	  		用户手机号：<input id="searchMemberCrushPhone" name="phone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		推荐码：<input id="searchRecommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
	    	<a id="searchMemberCrushBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetMemberCrushBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetMemberCrushBtn').click(function(){
		$("#memberCrushForm").form("reset");
		$("#memberCrushList").datagrid("load", {});
	});
	//查询按钮
	$('#searchMemberCrushBtn').click(function(){
 		$('#memberCrushList').datagrid({
			queryParams: {
				startDate: $('#searchMemberCrushStartDate').datebox('getValue'),
				endDate: $('#searchMemberCrushEndDate').datebox('getValue'),
				recommCodes: $('#searchRecommCodes').textbox('getValue'),
				phone: $('#searchMemberCrushPhone').val(),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		return '<a href="#" class="btn l-btn l-btn-small" onclick="memberCrushAudit('+index+')">审核</a>'+"   "+
		'<a href="#" class="btn l-btn l-btn-small" onclick="memberCrushRefuse('+index+')">拒绝</a>';
	} 

	//拒绝操作
	function memberCrushRefuse(index){  
		$('#memberCrushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#memberCrushList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定拒绝吗？', function(ensure){
			if(ensure){
				var url = "${apppath}/crush/memberCrushRefuse.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#memberCrushList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	
	//审核操作
	function memberCrushAudit(index){  
		$('#memberCrushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#memberCrushList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定审核通过吗？', function(ensure){
			if(ensure){
				var url = "${apppath}/crush/memberCrushAudit.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#memberCrushList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
</script>
</body>
</html>

