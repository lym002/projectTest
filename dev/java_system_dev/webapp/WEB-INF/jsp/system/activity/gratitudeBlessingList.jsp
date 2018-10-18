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
	<table id="gratitudeBlessingList" title="感恩回馈"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	    	<th data-options="field:'mobilePhone'" width="10%">用户名</th>
	        <th data-options="field:'blessing'" width="30%">祝福帖</th>
	        <th data-options="field:'status'" width="6%" formatter="formatStatus">状态</th>
	        <th data-options="field:'addtime',align:'center'" formatter="formatDateBoxFull" width="12%">时间</th>	        
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="gratitudeBlessingTools" style="padding:5px;height:750">
	  	<form id="gratitudeBlessingForm" target="_blank" method="post">
	                   状态:<select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0' selected="selected">待审核</option>		  					
		  					<option value='1'>显示</option>
		  					<option value='2'>不显示</option>
	  				</select>
	  		用户名:<input id="mobilePhone" name="mobilePhone" type="text" class="easyui-numberbox" style="width:100px"/>
	  		祝福提交时间:<input class="easyui-datebox" id="startaddtime" style="width:100px"/>
	  		至<input class="easyui-datebox" id="endaddtime" style="width:100px"/>

	  		<a id="searchGratitudeBlessing" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchGratitudeBlessing()" iconCls="icon-search">查询</a>
	    	<a id="resetGratitudeBlessing" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>

	<!--审核 -->	
	<div id="updateGratitudeBlessing" class="easyui-dialog" style="height:25%;width:20%" closed="true"	buttons="updateGratitudeBlessingBtn"  data-options="resizable:true,modal:true,closed:true">
		<center>
			<span><h3>确认在前台页面上显示吗？</h3></span>
			<input id="id" type="hidden">
			<div id="updateGratitudeBlessingBtn" style="padding:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateBlessing(1)">显示</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="updateBlessing(2)">不显示</a>
			</div>
		</center>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		searchGratitudeBlessing();
	});
	//重置按钮
	$('#resetGratitudeBlessing').click(function(){
		$("#gratitudeBlessingForm").form("reset");
		searchGratitudeBlessing();
	});
	//查询按钮
	function searchGratitudeBlessing(){
 		$('#gratitudeBlessingList').datagrid({
 			url:"../gratitudeBlessing/selectGratitudeBlessing.do",
 			fitColumns : true,
 			rownumbers:true,
 			showFooter: true,
 			pagination:true,
 			nowrap: false,
 			pageSize:25,
 			pageList:[25,50,100],
 			toolbar:"#gratitudeBlessingTools", 
 			queryParams: {
				status:$('#status').combobox('getValue'),
				mobilePhone:$('#mobilePhone').textbox('getValue'),
				startaddtime:$('#startaddtime').datebox('getValue'),
				endaddtime:$('#endaddtime').datebox('getValue')
				}
		});
	};


	//审核状态
	function formatStatus(value,row,index){
		if(row.status == 0){
			return '待审核';
		}else if(row.status == 1){
			return '显示';
		}else if(row.status == 2){
			return '不显示';
		}
	}
	
	//操作链接
	function formatOper(val,row,index){  
		if(row.status==0){
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="update('+row.id+')">审核</a>';
			return	oper;
		}
    }
	
	function update(id){
		$('#id').val(id);
		$('#updateGratitudeBlessing').dialog('open').dialog('setTitle', '审核');
	}
	
	function updateBlessing(status){
		$.ajax({
			type: 'post',
			url:"../gratitudeBlessing/updateGratitudeBlessing.do",
			data : {
				id : $("#id").val(),
				status : status
			},
			success : function(result) {
				if(result=="success"){
					$("#updateGratitudeBlessing").dialog("close");
					searchGratitudeBlessing();
					$.messager.alert("提示信息","审核成功");
				}else{
					$("#updateGratitudeBlessing").dialog("close");
					searchGratitudeBlessing();
					$.messager.alert("提示信息","审核失败");
				}
			}
		 });
	}
</script>
</body>
</html>

