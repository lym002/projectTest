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
	<table id="drProductPrizeList" title="积分明细" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../detailintegral/detailintegralList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">明细ID</th>
	        <th data-options="field:'mobilePhone'"  width="10%">用户手机号</th>
	        <th data-options="field:'integralSourceId'" width="6%"  formatter="formaterProductPrizeStatus">积分来源</th>
	        <th data-options="field:'userDetailIntegral'" width="6%"  >积分数量</th>
	        <th data-options="field:'taskType'" width="6%"  >任务名称</th>
	        <th data-options="field:'expirationTime'" width="10%" formatter="formatDateBoxFull">失效日期</th>
	        <th data-options="field:'addTime'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'updateTime'" width="10%" formatter="formatDateBoxFull">修改时间</th>
	       <!--  <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th> -->
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	             
	                     手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:150px"/>
	                   积分来源:<select id="integralSourceId" name="integralSourceId" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='1' >投资</option>
		  					<option value='2' >签到</option>
		  					<option value='3' >任务</option>
	  				</select>
	  		时间：<input id="startaddTime" name="startaddTime" class="easyui-datebox" style="width:100px" />
	  			至:<input id="endaddTime" name="endaddTime" class="easyui-datebox" style="width:100px" />
	  			
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoDialog()">新增</a> -->
	    </form>
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
				startaddTime:$('#startaddTime').datebox('getValue'),
				endaddTime:$('#endaddTime').datebox('getValue'),
				mobilePhone:$('#mobilePhone').val(),
				integralSourceId:$('#integralSourceId').combobox('getValue'),
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

	

		
		//奖品状态	
	function formaterProductPrizeStatus(value,row,index){
		if(row.integralSourceId == 1){
			return '投资';
		}else if(row.integralSourceId == 2){
			return '签到';
		}else if(row.integralSourceId == 3){
			return '任务';
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
