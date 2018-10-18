
<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="drProductPrizeList" title="积分投資规则" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../taskrules/taskRulesList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">ID</th>
	        <th data-options="field:'taskType'"  width="10%">任务名称</th>
	        <th data-options="field:'taskIntegral'" width="6%"  >任务积分</th>
	        <th data-options="field:'isFirstTask'" width="6%"  formatter="formaterisFirstTask">是否为单次任务</th>
	         <th data-options="field:'isOpen'" width="6%"  formatter="formaterProductPrizeStatus">是否开启任务</th>
	         <th data-options="field:'taskMoneyRequire'" width="6%"  >完成任务所需金额</th>
	        <th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'updateTime'" width="10%" formatter="formatDateBoxFull">修改时间</th>
	        <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	              任务名称:<input id="taskType" name="taskType" class="easyui-textbox"  size="15" style="width:200px"/>
	      是否为单次任务:<select id="isFirstTask" name="isFirstTask" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0'>是</option>
		  					<option value='1'>否</option>
	  				</select>
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoDialog()">新增</a>
	    </form>
	</div>
	
	
	<%--规则新增 --%>
	<div id="addDrProductInfoDialog" class="easyui-dialog" title="新增规则"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="addJsProductPrizeForm">
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">任务名称：<input id="'taskType" name="taskType" type="text" class="easyui-textbox" data-options="required:true"/>
					任务积分：<input id="taskIntegral" name="taskIntegral" type="text" class="easyui-numberbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">是否为单次任务：
					<select id="isFirstTask" name="isFirstTask" class="easyui-combobox" style="width:100px">
		  					<option value='0'>是</option>
		  					<option value='1'>否</option>
	  				</select>
					是否开启任务：<select id="isOpen" name="isOpen" class="easyui-combobox" style="width:100px">
		  					<option value='0'>开启</option>
		  					<option value='1'>关闭</option>
	  				</select>
					</td>
				</tr>
				<tr>
					<td align="left">完成任务所需金额：<input id="taskMoneyRequire" name="taskMoneyRequire" type="text" class="easyui-numberbox" data-options="required:true"/>
					
					</td>
				</tr>
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addRedEnvelope()">添加</a>
		</div>
	</div>
	
	<%--修改 --%>
	<div id="updateActivityWindow" class="easyui-dialog" title="修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="updateSysProgramForm">
			<table id="jptable" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<input id="id" name="id" readonly="readonly" type="hidden" />
				<tr>
					<td align="left">任务名称：<input id="'taskType" name="taskType" type="text" class="easyui-textbox" data-options="required:true"/>
					任务积分：<input id="taskIntegral" name="taskIntegral" type="text" class="easyui-numberbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">是否为单次任务：
					<select id="isFirstTask" name="isFirstTask" class="easyui-combobox" style="width:100px">
		  					<option value='0'>是</option>
		  					<option value='1'>不是</option>
	  				</select>
					是否开启任务：<select id="isOpen" name="isOpen" class="easyui-combobox" style="width:100px">
		  					<option value='0'>开启</option>
		  					<option value='1'>关闭</option>
	  				</select>
					</td>
				</tr>
					<tr>
					<td align="left">完成任务所需金额：<input id="taskMoneyRequire" name="taskMoneyRequire" type="text" class="easyui-numberbox" data-options="required:true"/>
					
					</td>
				</tr>
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updatePrize()">修改</a>
		</div>
	</div>
	
	
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeForm").form("reset");
		$("#drProductPrizeList").datagrid("load", {});
	});
	//查询按钮
	function searchJsProductPrize(){
 		$('#drProductPrizeList').datagrid({
			queryParams: {
				taskType:$('#taskType').val(),
				isFirstTask:$('#isFirstTask').combobox('getValue'),
			}
		});
	};
	

	
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改</a>    ';
		articleOper += '<a href="#" class="btn l-btn l-btn-small" onclick="deleteTask('+index+')">删除</a>    ';
		return articleOper;
	} 	

   

	
	function updatePrize(){
		var validate = $("#updateSysProgramForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/taskrules/updateTaskRules.do",
            type: 'POST',
            data:$("#updateSysProgramForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#drProductPrizeList").datagrid("reload");
				$("#updateActivityWindow").dialog("close");
			}else{
				$.messager.alert("提示信息",result.msg);
			}
			}
        });
		 return false;
	}

	//跳转到修改页面
	function updateActivityWindow(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$("#updateSysProgramForm").form("load",row);
		$("#updateActivityWindow").dialog("open");
	}

	//跳转到添加页面
	function addDrProductInfoDialog(){  
		$("#addJsProductPrizeForm").form("reset");
		$("#addDrProductInfoDialog").dialog("open");
	}
	
	//添加规则
	function addRedEnvelope(){
		var validate = $("#addJsProductPrizeForm").form("validate");
		if(!validate){
			return false;
		}
	
		$.ajax({
         	url: "${apppath}/taskrules/addTaskRules.do",
           	type: 'POST',
           	data:$("#addJsProductPrizeForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeList").datagrid("reload");
					$("#addDrProductInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.msg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}

		
	function formaterProductPrizeStatus(value,row,index){
		if(row.isOpen == 0){
			return '启用';
		}else if(row.isOpen == 1){
			return '禁用';
		}
	}

	function formaterisFirstTask(value,row,index){
		if(row.isFirstTask == 0){
			return '是';
		}else if(row.isFirstTask == 1){
			return '否';
		}
	}
	
	function deleteTask(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
        $.messager.confirm('操作提示', '确定要删除吗?', function(r){
        	if(r){
	            $.ajax({
	            	url: "${apppath}/taskrules/deleteTask.do?id="+row.id,
	                type: 'POST',
	               success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#drProductPrizeList").datagrid("reload");
						}else{
							$.messager.alert("提示信息",result.msg);
						}
					}
	            });
            }
        });
	}
	
	$(document).ready(function () {
/* 		$('#drProductPrizeList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../prizemanage/prizeLogCount.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeLogForm").serialize(), 
					success:function(data){
						$('#productPrizeCount').text(data.count);
					}
				});
			} 
    	}); */
	});
</script>
</body>
</html>

