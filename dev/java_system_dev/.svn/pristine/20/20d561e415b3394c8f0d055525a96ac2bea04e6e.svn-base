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
	<table id="sysProgramList" title="栏目列表 " 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../program/sysProgramList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#sysProgramTools',onLoadSuccess:sysProgramBtn">
		<thead>
	    <tr>
	    	<th data-options="field:'proId'" width="10%" hidden="true">ID</th>
	        <th data-options="field:'proName'" width="20%">栏目名称</th>
	       	<th data-options="field:'createName'" width="10%">创建人</th>	     
	       	<th data-options="field:'updateTime'" width="10%" formatter="formatDateBoxFull">最后编辑时间</th>	      	
	       	<th data-options="field:'updateName'" width="10%">最后操作人</th>
			<th data-options="field:'createTime'" width="10%" formatter="formatDateBoxFull">创建时间</th>
			<th data-options="field:'status'" width="3%" formatter="formatStatus">状态</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="20%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="sysProgramTools" style="padding:5px;height:750">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSysProgramDialog()">新增栏目</a>
	</div>
	<div id="addSysProgramDialog" class="easyui-dialog" title="添加栏目"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addSysProgramBtn'" style="width:300px;height:240px;padding:5px;">
		<form id="addSysProgramForm">
			<table align="center">
				<tr>
					<td>
						栏目名称:
					</td>
					<td>	
						<input type="text" name="proName" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						栏目描述:
					</td>
					<td>		
						<input class="easyui-textbox" name="description" data-options="multiline:true" style="height:60px"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="addSysProgramBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSysProgram()">添加</a>
		</div>
	</div>
	<div id="updateSysProgramDialog" class="easyui-dialog" title="修改栏目"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false,buttons:'#updateSysProgramBtn'" style="width:300px;height:240px;padding:5px;">
		<form id="updateSysProgramForm">
			<input name="proId" type="hidden" />
			<table align="center">
				<tr>
					<td>
						栏目名称:
					</td>
					<td>	
						<input type="text" name="proName" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						栏目描述:
					</td>
					<td>		
						<input class="easyui-textbox" name="description" data-options="multiline:true" style="height:60px"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="updateSysProgramBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateSysProgram()">修改</a>
		</div>
	</div>
	<div id="showSysProgramDialog" class="easyui-dialog" title="查看栏目"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:300px;height:200px;padding:5px;">
		<form id="showSysProgramForm">
			<table align="center">
				<tr>
					<td>
						栏目名称:
					</td>
					<td>	
						<input type="text" name="proName" class="easyui-textbox" disabled="disabled" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						栏目描述:
					</td>
					<td>		
						<input class="easyui-textbox" name="description" disabled="disabled" data-options="multiline:true" style="height:60px"/>
					</td>
				</tr>
			</table>
		</form>	
	</div>
<script type="text/javascript">
	function sysProgramBtn (){
			$('.sysProgramBtn').linkbutton();
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="sysProgramBtn" onclick="showSysProgram('+index+')">查看</a>'+' ';
		if(row.status==1){
			articleOper += '<a href="#" class="sysProgramBtn" onclick="toUpdateSysProgram('+index+')">修改</a>'+' ';
			articleOper += '<a href="#" class="sysProgramBtn" onclick="deleteSysProgram('+index+')">删除</a>'+' ';
		}
		if(row.status==0){
			articleOper += '<a href="#" class="sysProgramBtn" onclick="recoverSysProgram('+index+')">恢复</a>'+' ';
		}
		return articleOper;
	} 	
	
	//跳转栏目修改页面
 	function toUpdateSysProgram(index){
		$('#sysProgramList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysProgramList').datagrid('getSelected'); 
		$("#updateSysProgramForm").form("load",row);
		$("#updateSysProgramDialog").dialog("open");
	} 
	
	//修改栏目
	function updateSysProgram(){  
   		$.ajax({
          	url: "${apppath}/program/updateSysProgram.do",
            type: 'POST',
            data:$("#updateSysProgramForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#sysProgramList").datagrid("reload");
				$("#updateSysProgramDialog").dialog("close");
			}else{
				$.messager.alert("提示信息",result.errorMsg);
			}
			}
        });
	}
	
	//跳转栏目显示页面
 	function showSysProgram(index){
		$('#sysProgramList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysProgramList').datagrid('getSelected'); 
		$("#showSysProgramForm").form("load",row);
		$("#showSysProgramDialog").dialog("open");
	} 
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '失效';
		}else if(row.status=="1"){
			return '有效';
		}
	}
	
	//跳转栏目添加页面
	function addSysProgramDialog(){  
		$("#addSysProgramForm").form("reset");
		$("#addSysProgramDialog").dialog("open");
	}

	//添加栏目
	function addSysProgram(){  
   		$.ajax({
          	url: "${apppath}/program/addSysProgram.do",
            type: 'POST',
            data:$("#addSysProgramForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#sysProgramList").datagrid("reload");
				$("#addSysProgramDialog").dialog("close");
			}else{
				$.messager.alert("提示信息",result.errorMsg);
			}
			}
        });
	}
	
	function deleteSysProgram(index) {
		$('#sysProgramList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysProgramList').datagrid('getSelected'); 
        $.messager.confirm('操作提示', '确定要删除吗?', function(r){
        	if(r){
	            $.ajax({
	            	url: "${apppath}/program/deleteSysProgram.do?proId="+row.proId,
	                type: 'POST',
	               success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#sysProgramList").datagrid("reload");
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
	            });
            }
        });
 	}
 	
 	function recoverSysProgram(index) {
 		$('#sysProgramList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysProgramList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/program/recoverSysProgram.do?proId="+row.proId,
	        type: 'POST',
	        success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#sysProgramList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
       });
 	}
	
</script>
</body>
</html>

