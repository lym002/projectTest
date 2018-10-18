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
	<table id="appRenewalList" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../app/appRenewalList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#appRenewalTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">ID</th>
				<th data-options="field:'version'" width="8%">最低版本号</th>
				<th data-options="field:'releaseVersion'" width="8%">发布版本号</th>
				<th data-options="field:'isForceUpdate'" width="8%" formatter="formatIsForceUpdate">是否强制</th>
				<th data-options="field:'containers'" width="8%" formatter="formatContainers">系统</th>
				<th data-options="field:'content'" width="20%" >正文内容</th>
				<th data-options="field:'remark'" width="10%" >备注</th>
				<th data-options="field:'status'" width="5%" formatter="formatStatus">状态</th>
				<th data-options="field:'addName'" width="8%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="10%">添加时间</th>
				<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="appRenewalTools" style="margin-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addAppRenewalDialog()">添加</a>
	</div>
	
	<!-- 新增APP更新 -->
	<div id="addAppRenewalDialog" class="easyui-dialog" title="栏目添加"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addAppRenewalBtn'"
		style="width:550px;height:380px;padding:20px;">
		<form id="addAppRenewalForm">
			<table align="center">
				<tr>
					<td style="width:100px">最低版本号:</td>
					<td>
						<input id="addVersion" name="version" style="width:150px" class="easyui-textbox">
					</td>
					<td style="width:100px">发布版本号:</td>
					<td>
						<input id="addReleaseVersion" name="releaseVersion" style="width:150px" class="easyui-textbox" >
					</td>
				</tr>
				<tr>
					<td style="width:100px">是否强制更新</td>
    				<td>
    					<select id="addIsForceUpdate" name="isForceUpdate" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='0'>非强制更新</option>
							<option value='1'>强制更新</option>
						</select>
    				</td>
    				<td>系统</td>
    				<td>
    					<select id="addContainers" name="containers" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='1'>IOS</option>
							<option value='2'>Android</option>
						</select>
    				</td>
				</tr>
				<tr>
					<td>正文内容:<br/>(换行使用^<br/>符号)</td>
					<td colspan="3">
						<input id="addContent" class="easyui-textbox" name="content" style="height:100px;width:400px;" data-options="required:true,multiline:true,validType:'maxLength[500]'"/>
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3">
						<input id="addRemark" class="easyui-textbox" name="remark" style="height:100px;width:400px;" data-options="multiline:true,validType:'maxLength[255]'"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="addAppRenewalBtn"  style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addAppRenewal()">新增</a>
		</div>
	</div>
	
	<div id="updateAppRenewalWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:380px;padding:10px;">
	</div>
	
<script type="text/javascript">
	function formatIsForceUpdate(val,row,index){
		if(val==0){
			return '非强制更新';
		}
		return '强制更新';
	}
	
	function formatContainers(val,row,index){
		if(val==1){
			return 'ios';
		}else if(val ==2 ){
			return 'Android';
		}
		return '';
	}
	
	function formatStatus(val,row,index){
		if(val==1){
			return '有效';
		}
		return '无效';
	}
	function formatOper(val,row,index){
		if(row.status==1){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="showAppRenewalBtn('+index+')">查看</a>'+"  "+
				'<a href="#" class="btn l-btn l-btn-small" onclick="updateAppRenewalBtn('+index+')">修改</a>';
		}
		return '<a href="#" class="btn l-btn l-btn-small" onclick="showAppRenewalBtn('+index+')">查看</a>';
	}
	
	//跳转到添加页面
	function addAppRenewalDialog(){  
		$("#addAppRenewalForm").form("reset");
		$("#addAppRenewalDialog").dialog("open");
	}
	
	function addAppRenewal(){
		var validate = $("#addAppRenewalForm").form("validate");
		if(!validate){
			return false;
		}
		var isForceUpdate = $('#addIsForceUpdate').combobox('getValue');
		var version = $('#addVersion').val();
		if(isForceUpdate==0 && version==''){
			$.messager.alert("提示信息","非强制更新请填写版本号");
			return false;
		}
		$.ajax({
          	url: "${apppath}/app/addAppRenewal.do",
            type: 'POST',
            data:$("#addAppRenewalForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#appRenewalList").datagrid("reload");
					$("#addAppRenewalDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
	
	function showAppRenewalBtn(index){
		$('#appRenewalList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appRenewalList').datagrid('getSelected');
	    $("#updateAppRenewalWindow").window({
			title:"查看",
			href:"../app/toAppRenewalShow.do?id="+row.id
		});
		$("#updateAppRenewalWindow").window("open");
	}
	
	function updateAppRenewalBtn(index){
		$('#appRenewalList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#appRenewalList').datagrid('getSelected');
	    $("#updateAppRenewalWindow").window({
			title:"查看",
			href:"../app/toAppRenewalModify.do?id="+row.id
		});
		$("#updateAppRenewalWindow").window("open");
	}
</script>	
</body>
</html>