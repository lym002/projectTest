<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../../common/include/util.jsp" %>

<style type="text/css">
.onediv{width:350px; height:300px;float:left;margin:0 0 0 5px;  }
.twodiv{width:200px; height:300px;float:left;margin:0 0 0 5px;  }
</style>

</head>
<body>
	<table id="roleList" title="角色列表" class="easyui-datagrid" style="width:99.9%;height:99%;"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../role/roleList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#roleTools'">
		<thead>
	    <tr>
			<th data-options="field:'roleKy'" width="5%">角色主键</th>
	        <th data-options="field:'roleCode'" width="10%">角色编号</th>
	        <th data-options="field:'roleName'" width="15%">角色名称</th>
	        <th data-options="field:'roleRemarks'" width="40%">角色备注</th>
	        <th data-options="field:'status'" width="10%" formatter="formatStatus">状态</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="18%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="roleTools" style="padding:5px;height:750">
	  	<form id="roleForm">
	    	角色名称：<input class="easyui-textbox" id="searchRoleName" name="roleName" size="15" style="width:100px"/>
	  		状态： <select  id="searchRoleStatus" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<option value='1'>正常</option>
					<option value='0'>禁用</option>
	           </select>
	    	<a id="searchRoleBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetRoleBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addRoleWindow()">添加角色</a>
	    </form>
	
	</div>
	
	<div id="addRoleWindow" class="easyui-window" title="角色添加" 
		data-options="iconCls:'icon-add',closed:true,minimizable:false,resizable:false,maximizable:false" style="width:700px;height:400px;padding:10px;">
		<form id="addRoleForm">
		<div class="onediv">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>角色名称:</td>
	    			<td><input id="roleName" class="easyui-textbox" type="text" name="roleName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>角色编码:</td>
	    			<td><input id="roleCode" class="easyui-textbox" type="text" name="roleCode" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>角色描述:</td>
	    			<td><input class="easyui-textbox" type="text" name="roleRemarks" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
	    				<select name="status" class="easyui-combobox">
	    					<option value="1">可用</option>
	    					<option value="0">禁用</option>
	    				</select>
	    			</td>
	    		</tr>
			</table>
		<div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="checkRole()">添加</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearRole()">清空</a>
    	</div>
		</div>	
		<div class="twodiv">
		<ul id="roletree" class="easyui-tree" data-options="url:'../role/menuTree.do',method:'get',animate:true,checkbox:true"></ul>
    	</div>
		</form>
	</div>
	<div id="updateRole" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:700px;height:450px;padding:10px;">
	</div>
<script type="text/javascript">
	$(function () {
	    $("#roletree").tree({
	    	cache:false,  
	    	cascadeCheck: false,
	        onCheck: function (node, checked) {
	            if (checked) {
	                var parentNode = $("#roletree").tree('getParent', node.target);
	                if (parentNode != null) {
	                    $("#roletree").tree('check', parentNode.target);
	                }
	            } else {
                        var childNode = $("#roletree").tree('getChildren', node.target);
                        if (childNode.length > 0) {
                            for (var i = 0; i < childNode.length; i++) {
                                $("#roletree").tree('uncheck', childNode[i].target);
                            }
                        }
                    }
	        }
	    });
	});
	//重置按钮
	$('#resetRoleBtn').click(function(){
		$("#roleForm").form("reset");
		$("#roleList").datagrid("load", {});
	});
	//查询按钮
	$('#searchRoleBtn').click(function(){
 		$('#roleList').datagrid({
			queryParams: {
				roleName: $('#searchRoleName').val(),
				status: $('#searchRoleStatus').combobox("getValue"),
			}
		}); 
	});
	
	function addRoleWindow(){ 
		$("#addRoleForm").form("reset");
		$("#addRoleWindow").window("open");
	 	$("#roletree").tree('collapseAll');
	 }
	//添加按钮
 	function checkRole(){
 		var validate = $("#addRoleWindow").form("validate");
		if(!validate){
			return false;
		}
		var roleName=$("#roleName").val();
	 	var roleCode=$("#roleCode").val();
		$.ajax({
			url:"../role/isExistNameOrCode.do",
			data:"roleName="+roleName+"&roleCode="+ roleCode,
			dataType:"json",
			type : "post",
			success:function(data){
				var msg = data.message;
				if(msg == "ok"){
					addRole();
				}else{
					if(msg =="nameerror"){
						$.messager.alert("操作提示", "角色名存在！");
					}else{
						$.messager.alert("操作提示", "角色编码存在!");
					}
					return false;
				}
			},
			error:  function (event, XMLHttpRequest, ajaxOptions, thrownError) {
				$.messager.alert("操作提示", "系统异常，请联系管理员。");
				return false;
			}
		});
	}
	 function addRole(){ 
		 var selectedRightsList="";		
		 var node = $("#roletree").tree('getChecked');
		 for(var i=0;i<node.length;i++)  
	     {  
	     	selectedRightsList += "&selectedRights=" + node[i].id;
	     }
		$(function(){
				var data = $("#addRoleForm").serialize() + selectedRightsList;
				$.ajax({
				url:"../role/tosysRoleAdd.do",
				type : "post",
				data : data,
				dataType:"json",
				success: function(d){
				var msg = d.message;
					if(msg == "ok"){
						$.messager.alert("操作提示", "添加成功！");
						$("#addRoleWindow").window("close");
						$('#roleList').datagrid('reload');
						
					}else{
						$.messager.alert("操作提示", "添加失败，请重试！");
					}
					
				},
				error:function(args){
				}
			});
		});
	} 
	
	//清空按钮
	function clearRole(){
		$("#addRoleForm").form("reset");
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
    	return '<a href="#" onclick="updateRoleWindow('+index+')">修改</a>';  
	} 
	
	//更改状态
	function updateStatus(index){  
		$('#roleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#roleList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改状态吗？', function(r){
			if(r){
				var url = "../role/updateRoleStatus.do?roleKy="+row.roleKy+"&status="+row.status;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(){
						$('#roleList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
			  	});
			}
		});
	} 
	
	//添加状态链接
	function formatStatus(value,row,index){
		if(row.status=="1"){
			return '<a href="#" onclick="updateStatus('+index+')">可用</a>';
		}else{
			return '<a href="#" onclick="updateStatus('+index+')">禁用</a>';
		}
	}
	
	//跳转修改也页面
	function updateRoleWindow(index){  
		$('#roleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#roleList').datagrid('getSelected'); 
		$("#updateRole").window({
			title:"角色修改",
			href:"../role/toUpdateRole.do?roleKy="+row.roleKy
		});
		$("#updateRole").window("open");
	} 

</script>
</body>
</html>

