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
	<table id="ysFeedbackList" title="意见反馈 " 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../feedBack/getYsFeedbackList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#ysFeedbackTools',onLoadSuccess:ysFeedbackBtn">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="hidden">ID</th>
	    	<th data-options="field:'realName'" width="5%">姓名</th>
	    	<th data-options="field:'recommCodes'" width="5%">推荐码</th>
	        <th data-options="field:'mobilePhone'" width="10%">联系方式</th>
	        <th data-options="field:'content'" width="30%">反馈内容</th>
	       	<th data-options="field:'feedbackTime'" width="10%" formatter="formatDateBoxFull">提交时间</th>	      	
			<th data-options="field:'statusName'" width="10%">是否处理</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="ysFeedbackTools" style="padding:5px;height:750">
		<form id="ysFeedbackForm">
	  		联系方式:<input id="searchysFeedbackTitle" name="contactInformation" class="easyui-textbox"  size="15" style="width:100px"/>
	  		状态: <select  id="searchysFeedbackStatus" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="0">未处理</option>
				 	<option value="1">已处理</option>
	           	 </select>
	    	<a id="searchysFeedbackBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetysFeedbackBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	<div id="updateysFeedbackWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:450px;height:350px;padding:10px;">
	</div>
<script type="text/javascript">
	function formatDate(value,row,index){
		if(value!=''&&value!=null){
			var unixTimestamp = new Date(value);  
			return unixTimestamp.toLocaleString();
		}else{
			return '';
		}
	}
	
	function ysFeedbackBtn (){
			$('.ysFeedbackBtn').linkbutton();
	}
	//重置按钮
	$('#resetysFeedbackBtn').click(function(){
		$("#ysFeedbackForm").form("reset");
		$("#ysFeedbackList").datagrid("load", {});
	});
	//查询按钮
	$('#searchysFeedbackBtn').click(function(){
 		$('#ysFeedbackList').datagrid({
			queryParams: {
				contactInformation: $('#searchysFeedbackTitle').val(),
				status: $('#searchysFeedbackStatus').combobox("getValue"),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper="";
		if(row.status==0){
			articleOper += '<a href="#" class="ysFeedbackBtn" onclick="updateysFeedbackWindow('+index+')">点击处理</a>'+' ';
		}
		if(row.status==1){
			articleOper += '<a href="#" class="ysFeedbackBtn" onclick="updateysFeedbackWindow('+index+')">查看</a>'+' ';
		}
		return articleOper;
	} 	
	
	//跳转广告修改页面
 	function updateysFeedbackWindow(index){
 		$('#ysFeedbackList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#ysFeedbackList').datagrid('getSelected'); 
		$("#updateysFeedbackWindow").window({
			title:"处理广告信息",
			href:"../feedBack/selectByPrimaryKey.do?id="+row.id,
		});
		$("#updateysFeedbackWindow").window("open");
	} 
</script>
</body>
</html>

