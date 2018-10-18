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
	<table id="appPushList" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../app/appAppPushList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#appPushTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">ID</th>
				<th data-options="field:'title'" width="10%">标题</th>
				<th data-options="field:'content'" width="25%">发送内容</th>
				<th data-options="field:'channel'" width="6%" formatter="formatChannel">渠道</th>
				<th data-options="field:'platform'" width="6%" formatter="formatPaltform">平台</th>
				<th data-options="field:'type'" width="6%" formatter="formatType">推送方式</th>
				<th data-options="field:'sendStartDate'" width="16%" formatter="formatSendTime">发送区间</th>
				<th data-options="field:'sendTime'" width="10%" >发送时间</th>
				<th data-options="field:'status'" width="5%" formatter="formatStatus">启用状态</th>		
				<th data-options="field:'_operate',align:'center',formatter:formatOper" width="15%">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="appPushTools" style="margin-bottom:5px">
			<form id="appPushForm">
			活动标题:<input id="appPushTitle" name="title" class="easyui-textbox"  size="15" style="width:200px"/>
			发送时间:<input id="appPushStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>-
					<input id="appPushEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
			发送状态:<select id="appPushStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value="">全部 </option>
						<option value="1">已完成</option>
						<option value="0">待发送</option>
					</select>
			平台:<select id="platform" name="platform" style="width:100px;" class="easyui-combobox">
						<option value="">全部 </option>
						<option value="1">IOS</option>
						<option value="2">android</option>
					</select>
			</form>
	    	<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addappPushDialog()">新建发送任务</a>
	</div>
	
	<div id="updateAppPushWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:400px;padding:10px;">
	</div>
	
<script type="text/javascript">
	function cancel(){
			$("#addappPushDialog").dialog("close");
	}
	$(".fruit").on('click',function(){
		if(!$(this).find(".checkbox").prop('checked')){
			$(this).find("td").eq(1).find('input').prop('disabled',true);
		}else{
			$(this).find("td").eq(1).find('input').prop('disabled',false);
		}
	});
	
	//重置按钮
	$('#resetBtn').click(function(){
		$("#appPushForm").form("reset");
		$("#appPushList").datagrid("load", {});
	});
	//查询按钮
	$('#searchBtn').click(function(){
 		$('#appPushList').datagrid({
			queryParams: {
				title: $("#appPushTitle").textbox("getValue"),
				startDate: $("#appPushStartDate").datebox("getValue"),
				endDate: $("#appPushEndDate").datebox("getValue"),
				status: $("#appPushStatus").combobox("getValue"),
				platform: $("#platform").combobox("getValue")
			
			}
		}); 
	});
	function formatSendTime(val,row,index){
		if(row.type == 0)
			return "";
		if(row.type == 1)
			return row.sendStartDate;
		return row.sendStartDate+" 至 "+row.sendEndDate;
	}
	
	function formatType(val,row,index){
		if(val == 0){
			return "立即";
		}else if(val == 1){
			return "定时";
		}else if(val == 2){
			return "定期";
		}else{
			return "";
		}
	}
	function formatChannel(val,row,index){
		if(val == 0){
			return "极光";
		}else if(val == 1){
			return "友盟";
		}
	}
	function formatPaltform(val,row,index){
		if(row.platform == 1){
			return "IOS";
		}else if(row.platform == 2){
			return "android";
		}else{
			return "全部";
		}
	}
	
	function formatStatus(val,row,index){
		if(val==1){
			return '已启用';
		}else if(val ==0 ){
			return '未启用';
		}
		return '已删除';
	}
	function formatOper(val,row,index){
		if(row.status==0){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="showappPushBtn('+index+')">查看</a>'+"  "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="updateappPushBtn('+index+')">修改</a>'+" "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="deletePush('+index+')">删除</a>'+" "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="executePushBtn('+index+')">启用</a>';
		}
		return '<a href="#" class="btn l-btn l-btn-small" onclick="showappPushBtn('+index+')">查看</a>';
	}
	//查看
	function showappPushBtn(index){
		$('#appPushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appPushList').datagrid('getSelected');
	    $("#updateAppPushWindow").window({
			title:"查看",
			href:"../app/selectAppPush.do?id="+row.id
		});
		$("#updateAppPushWindow").window("open");		
		$("#addappPushForm").form("reset");	
	}
	//跳转到添加页面
	function addappPushDialog(){  
		 $("#updateAppPushWindow").window({
			title:"新增",
			href:"../app/selectAppPush.do"
		});
		$("#updateAppPushWindow").window("open");
	}
	//修改
	function updateappPushBtn(index){
		$('#appPushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appPushList').datagrid('getSelected');
	    $("#updateAppPushWindow").window({
			title:"修改",
			href:"../app/selectAppPush.do?isEdit=1&id="+row.id
		});
		$("#updateAppPushWindow").window("open");
	}
	//执行
	function executePushBtn(index){
		$('#appPushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appPushList').datagrid('getSelected');
	   $.ajax({
          	url: "${apppath}/app/executePushBtn.do",
          	type: 'POST',
            data:{id:row.id},  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#appPushForm").form("reset");
					$("#appPushList").datagrid("load", {});				
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
	}
	//删除
	function deletePush(index){
		$('#appPushList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appPushList').datagrid('getSelected');
	   $.ajax({
          	url: "${apppath}/app/deletePush.do",
          	type: 'POST',
            data:{id:row.id},  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);	
					$("#appPushForm").form("reset");
					$("#appPushList").datagrid("load", {});				
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
     }
  
  
 
</script>	
</body>
</html>