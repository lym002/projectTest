﻿
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
	<table id="drProductPrizeList" title="积分来源" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../integral/integralSourceList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">来源ID</th>
	        <th data-options="field:'expirationDate'"  width="10%">失效时间（年）</th>
	        <th data-options="field:'isUsing'" width="6%"  formatter="formaterProductPrizeStatus">是否启用</th>
	        <th data-options="field:'convertRules'"  width="20%">兑换规则</th>
	        <th data-options="field:'disclaimer'"  width="20%">免责申明</th>
	        <th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'updateTime'" width="10%" formatter="formatDateBoxFull">修改时间</th>
	        <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	              
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoDialog()">新增</a> -->
	    </form>
	</div>
	
	
	<%--修改 --%>
	<div id="updateActivityWindow" class="easyui-dialog" title="修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="updateSysProgramForm">
			<table id="jptable" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<input id="id" name="id" readonly="readonly" type="hidden" />
				<tr>
					<td align="left">
					失效时间（年）：<input id="expirationDate" name="expirationDate" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">是否启用：
						<select name="isUsing" style="width: 172px" class="easyui-combobox" id="isUsing">
							<option value='0' selected="selected">启用</option>
							<option value='1' selected="selected">禁用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">兑换规则：<textarea rows="5" cols="50" id="convertRules" name="convertRules" style="resize: none"></textarea>
				</tr>
				<tr>
					<td align="left">免责申明：<textarea rows="5" cols="50" id="disclaimer" name="disclaimer" style="resize: none"></textarea>
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
				
			}
		});
	};
	

	
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改</a>    ';
		return articleOper;
	} 	

   

	
	function updatePrize(){
		var validate = $("#updateSysProgramForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/integral/updateIntegralSource.do",
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


		
		//奖品状态	
	function formaterProductPrizeStatus(value,row,index){
		if(row.isUsing == 0){
			return '启用';
		}else if(row.isUsing == 1){
			return '禁用';
		}
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
