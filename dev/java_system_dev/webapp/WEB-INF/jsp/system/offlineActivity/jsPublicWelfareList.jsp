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
	<table id="drPublicWelfareList" title="公益活动" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../activityOffline/jsPublicWelfareList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#drPublicWelfareTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	       	<th data-options="field:'title'" width="10%">标题</th>
	       	<th data-options="field:'summary'" width="15%" formatter="formatSummary" >摘要</th>
	       	<th data-options="field:'activityTime'" width="10%">时间</th>
	       	<th data-options="field:'status'" width="5%" formatter="formatStatus" >状态</th>
<!-- 	       	<th data-options="field:'addtime'" width="15%">添加时间</th> -->
	       	<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drPublicWelfareTools" style="padding:5px;height:750">
	  	<form id="drPublicWelfareForm" target="_blank" method="post">
	                   标题:<input class="easyui-textbox" id="title"  name="title" />
	                   是否首投：<select id="searchStatus" name="status" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<option value='0'>未启用</option>
					<option value='1'>已启用</option>
				</select>
<!-- 	      	时间:<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px"/>到 -->
<!-- 	  				<input id="endDate" name="endDate" class="easyui-datebox" style="width:100px"/> -->
	  		<a id="searchdrPublicWelfare" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetdrPublicWelfare" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrPublicWelfareBtn()">新增</a>
	    </form>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetdrPublicWelfare').click(function(){
		$("#drPublicWelfareForm").form("reset");
		$("#drPublicWelfareList").datagrid("load", {});
	});
	//查询按钮
	$('#searchdrPublicWelfare').click(function(){
 		$('#drPublicWelfareList').datagrid({
			queryParams: {
				title:$('#title').textbox('getValue'),		
				status:$('#searchStatus').textbox('getValue')		
// 				startDate:$('#startDate').datebox('getValue'),		
// 				endDate:$('#endDate').datebox('getValue')	
			}
		});
	});
	//添加基本操作链接
		//操作链接
	function formatOper(val,row,index){  
			var oper = '';
			if(row.status ==1){
				oper += '<a href="#" class="btn l-btn l-btn-small" onclick="toUpdateStatus('+index+',0)">删除</a>';
			}else if(row.status ==0){
				oper += '<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">编辑</a>';
				oper += '<a href="#" class="btn l-btn l-btn-small" onclick="toUpdateStatus('+index+',1)">启用</a>';
			}
				return	oper;
    }
	
	  	//跳转到产品添加页面
	function addDrPublicWelfareBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "新增公益活动",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activityOffline/toAddUpdateJsPublicWelfare.do?isAdd=1'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
    	//跳转到产品添加页面
	function update(index){
		$('#drPublicWelfareList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drPublicWelfareList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "编辑公益活动",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activityOffline/toAddUpdateJsPublicWelfare.do?isAdd=0&id="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	//修改状态
	function toUpdateStatus(index,status){
		$('#drPublicWelfareList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drPublicWelfareList').datagrid('getSelected'); 
		$.ajax({
			url: '${apppath}/activityOffline/toUpdateJsActivityOfflineStatus.do',
				data:{"id":row.id,"status":status},
				dataType:'json',
				success:function(result){
					if(result.success){
						$("#drPublicWelfareForm").form("reset");
						$("#drPublicWelfareList").datagrid("load", {});
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
		});
	}
	//修改显示是否头条信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '未启用';
		}else if(row.status=="1"){
			return '已启用';
		}
	}
	//修改显示是否头条信息
	function formatSummary(value,row,index){
		if(row.summary && row.summary.length >= 100){
			return row.summary+"******";
		}else {
			return row.summary;
		}
	}
	
</script>
</body>
</html>

