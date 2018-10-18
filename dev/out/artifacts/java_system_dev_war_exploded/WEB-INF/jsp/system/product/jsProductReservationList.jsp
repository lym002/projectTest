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
	<table id="jsProductReservationList" title="预约管理" class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../preservation/jsProductReservationList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#jsReservationTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="hidden"/>
	        <th data-options="field:'name'" width="10%">项目名称</th>
	        <th data-options="field:'periods'" width="10%">预约期数</th>
	        <th data-options="field:'dateInterval'" width="20%" >预约时间</th>
	        <th data-options="field:'total'" width="10%" styler="styleColor">参与人数</th>
	        <th data-options="field:'totalAmount'" width="10%" styler="styleColor" formatter="formatAmount">预约总额</th>
	        <th data-options="field:'status'" width="10%" formatter="formatStatus">预约状态</th>
	        <th data-options="field:'addName'" width="10%">添加人</th>
	        <th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsReservationTools" style="padding:5px;height:750">
	  	<form id="jsReservationForm">
	  		项目名称:<input id="searchJsReservationName" name="name" class="easyui-textbox" size="30" style="width:150px"/>
	  		预约时间:<input id="searchJsReservationStartTime" name="startTime" class="easyui-datetimebox"style="width:150px"/>至
	  		       <input id="searchJsReservationEndTime" name="endDate" class="easyui-datetimebox" style="width:150px"/>
	  		预约状态:<select id="searchJsReservationStatus" name="status" style="width:100px" class="easyui-combobox">
	  					<option value="" selected="selected">全部</option>
	  					<option value="0">待开启</option>
	  					<option value="1">已开启</option>
	  					<option value="2">已关闭</option>
	  				</select>      
	    	<a id="searchJsReservationBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsReservationBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addJsProductReservationBtn()">新增规则</a>
	    </form>
	</div>

	<!-- 新增 -->
	<div id="addJsPReservationDialog" class="easyui-dialog" title="新增预约管理"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addJsPReservationBtn'"
		style="width:480px;height:200px;padding:20px;">
		<form id="addJsPReservationForm">
			<table align="center">
				<tr>
					<td style="width:100px">项目名称:</td>
					<td colspan="3">
						<input type="text" name="name" class="easyui-textbox" style="width:250px" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr>
				<tr>
					<td>预约期数:</td>
					<td colspan="3">
						<input type="text" name="periods" class="easyui-numberbox" style="width:250px" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr>
				<tr>
					<td>预约时间：</td>
					<td>
						<input id="addJsReservationStartTime" name="startTime" class="easyui-datetimebox"style="width:150px" data-options="required:true"/>
					</td>
					<td>至</td>
					<td>
	  		       		<input id="addJsReservationEndTime" name="endTime" class="easyui-datetimebox" style="width:150px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr> 
			</table>
		</form>
		<div id="#addJsPReservationBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addReservationBtn()">确认</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelReservationBtn()">取消</a>
		</div>
	</div>

	<div id="updateJsPReservationWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:380px;padding:10px;">
	</div>
	
	<!-- 详情 -->
	<div id="jsPReservationLogTools" style="padding:5px;height:550">
	  	<form id="jsPReservationLogForm">
	  		<input type="hidden" id="jsPReservationLogPrid"  name="prid"/>
	  		用户名:<input id="searchRealName" name="realName" class="easyui-textbox"  size="15" style="width:100px"/>
	  		手机号码:<input id="searchMobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		预约时间:<input id="searchLogStartTime" name="logStartTime" class="easyui-datetimebox" style="width:150px"/>
	  		至<input id="searchLogEndTime" name="logEndTime" class="easyui-datetimebox" style="width:150px"/>
	    	<a id="searchDrChannelKeyWord" href="javascript:searcheJsPReservationLogBtn()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="searchDrChannelKeyWord" href="javascript:exportJsPReservationLogBtn();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	<div id="jsPReservationLogDialog" class="easyui-dialog" title="查看预约记录"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:800px;height:500px;padding:5px;">
		<table id="jsPReservationLogList"
			class="easyui-datagrid" style="height:99%;width:99.9%"
			data-options="singleSelect:true,
			    fitColumns:true,
			    method:'post',rownumbers:true, 
			    pagination:true,toolbar:'#jsPReservationLogTools'">
				<thead>
			    <tr>
			    	<th data-options="field:'id'" hidden="true">序号</th>
			    	<th data-options="field:'prid'" hidden="true">规则id</th>
			        <th data-options="field:'realName'" width="20%">用户姓名</th>
			        <th data-options="field:'mobilePhone'" width="20%">手机号码</th>
			        <th data-options="field:'addTime'" width="25%" formatter="formatDateBoxFull">预约时间</th>
			        <th data-options="field:'amount'" width="20%" styler="styleColor" formatter="formatAmount">预约金额</th>
			    </tr>
			    </thead>
			</table>
	</div>
	
<script type="text/javascript">
	//重置按钮
	$("#resetJsReservationBtn").click(function(){
		$("#jsReservationForm").form("reset");
		$("#jsProductReservationList").datagrid("load", {});
	});
	
	//查询按钮
	$("#searchJsReservationBtn").click(function(){
 		$("#jsProductReservationList").datagrid({
			queryParams: {
 				name:$("#searchJsReservationName").val(),
				startTime:$("#searchJsReservationStartTime").datetimebox("getValue"),
 				endTime:$("#searchJsReservationEndTime").datetimebox("getValue"),
				status:$("#searchJsReservationStatus").combobox("getValue")
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(2 != row.status){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateJsPReservation('+index+')">编辑</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="showJPRLog('+index+')">详情</a>';  
		}else{
			return '<a href="#" class="btn l-btn l-btn-small" onclick="showJPRLog('+index+')">详情</a>';
		}
    	
	} 

	function formatAmount(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatStatus(val,row,index){
		if(val=='0'){
			return '待开启';
		}else if(val=='1'){
			return '开启';
		}else{
			return '关闭';
		}
	}
	
	function addJsProductReservationBtn(){
		$("#addJsPReservationForm").form("reset");
		$("#addJsPReservationDialog").dialog("open");
	}
	
	function addReservationBtn(){
		var validate = $("#addJsPReservationForm").form("validate");
		if(!validate){
			return false;
		}
		
		$.ajax({
         	url: "${apppath}/preservation/addReservation.do",
           	type: 'POST',
           	data:$("#addJsPReservationForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#jsProductReservationList").datagrid("reload");
					$("#addJsPReservationDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}
	
	//取消新增
	function cancelReservationBtn(){
		$("#jsProductReservationList").datagrid("reload");
		$("#jsPReservationLogDialog").dialog("close");
		$("#addJsPReservationDialog").dialog("close");
	}
	
	function updateJsPReservation(index){
		$("#jsProductReservationList").datagrid("selectRow",index);// 关键在这里 
	    var row = $("#jsProductReservationList").datagrid("getSelected"); 

		$("#updateJsPReservationWindow").window({
			title:"修改预约",
 			href:"../preservation/toReservationModify.do?id="+row.id
		});
		$("#updateJsPReservationWindow").window("open");
	}
	
	function searcheJsPReservationLogBtn(){
		$("#jsPReservationLogList").datagrid({
		    url: "${apppath}/preservation/queryJsPReservationLog.do",
		    queryParams:{
		    	prid:$("#jsPReservationLogPrid").val(),
		        realName:$("#searchRealName").val(),
		        mobilePhone:$("#searchMobilePhone").val(),
		        logStartTime:$("#searchLogStartTime").datetimebox("getValue"),
		        logEndTime:$("#searchLogEndTime").datetimebox("getValue")
		    }
		});
	}
	
	
	function showJPRLog(index){
		$("#jsPReservationLogForm").form("reset");
		$("#jsPReservationLogDialog").dialog("open");
		$("#jsProductReservationList").datagrid("selectRow",index);// 关键在这里 
	    var row = $("#jsProductReservationList").datagrid("getSelected"); 
		$("#jsPReservationLogPrid").val(row.id);
		$("#jsPReservationLogList").datagrid({
			 url: "${apppath}/preservation/queryJsPReservationLog.do",
		    queryParams:{
		    	prid:$("#jsPReservationLogPrid").val(),
		        realName:$("#searchRealName").val(),
		        mobilePhone:$("#searchMobilePhone").val(),
		        logStartTime:$("#searchLogStartTime").datetimebox("getValue"),
		        logEndTime:$("#searchLogEndTime").datetimebox("getValue")
		    }
		});
	}
	
	function exportJsPReservationLogBtn(){
		window.location.href="../preservation/exportJsPReservationLog.do?prid="+$('#jsPReservationLogPrid').val()+
						"&realName="+encodeURIComponent(encodeURIComponent($('#searchRealName').val()))+
						"&mobilePhone="+$('#searchMobilePhone').val()+
						"&logStartTime="+$('#searchLogStartTime').datetimebox('getValue')+
						"&logEndTime="+$('#searchLogEndTime').datetimebox('getValue');
	}
</script>
</body>
</html>

