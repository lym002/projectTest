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
	<table id="recommendedSettingsList" title="好友推荐设置" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../recommendedSettings/recommendedSettingsList.do',
	    method:'post',rownumbers:true, 
	    pagination:true">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden"></th>
				<th data-options="field:'modality'" hidden="hidden"></th>
				<th data-options="field:'normName'" width="10%">奖励标准</th>
				<th data-options="field:'period'" width="5%">累计周期(天)</th>
				<th data-options="field:'modalityName'" width="8%">奖励形式</th>
				<th data-options="field:'standardName'" width="8%">奖励基准</th>
				<th data-options="field:'productName'" width="15%">适用产品</th>
				<th data-options="field:'startTime'" formatter="formatDate">开始时间</th>
				<th data-options="field:'endTime'" formatter="formatDate">结束时间</th>
				<th data-options="field:'status'" formatter="formatStatus">状态</th>
				<th data-options="field:'addName'">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull">添加时间</th>
				<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	
	<div id="showDetailsWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:500px;height:450px;padding:10px;">
	</div>
	<script type="text/javascript">
		//添加基本操作链接
	function formatOper(val,row,index){  
		if("0"== row.status){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateSettingsWindow('+index+')">查看并修改</a>';
		}else{
			return '<a href="#" class="btn l-btn l-btn-small" onclick="showDetailsWindow('+index+')">查看详情</a>';
		}
    	
	} 
	
	function formatDate(value,row,index){
		if(value!=''&&value!=null){
			var unixTimestamp = new Date(value);  
			return unixTimestamp.toLocaleString();
		}else{
			return '';
		}
	}
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '生效中';
		}else{
			return '已失效';
		}
	}
	
	//修改(新增)好友推荐设置
	function showDetailsWindow(index){
		$('#recommendedSettingsList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#recommendedSettingsList').datagrid('getSelected'); 
		$("#showDetailsWindow").window({
			title:"查看详情",
			href:"../recommendedSettings/toSettingsDetailsList.do?id="+row.id
		});
		$("#showDetailsWindow").window("open");
	}
	
	function formatDate(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt = parseToDate(value);  
    return dt.format("yyyy-MM-dd");  
} 
	
	//跳转到修改页面
	function updateSettingsWindow(index){
		$('#recommendedSettingsList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#recommendedSettingsList').datagrid('getSelected'); 
		$("#showDetailsWindow").window({
			title:"查看详情",
			href:"../recommendedSettings/toModify.do?id="+row.id
		});
		$("#showDetailsWindow").window("open");
	}
	</script>
  </body>
</html>
