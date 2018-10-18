
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
	<table id="drProductPrizeList" title="积分管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../detailintegral/integralManageList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">明细ID</th>
	        <th data-options="field:'realName'"  width="10%">用户名</th>
	        <th data-options="field:'mobilePhone'"  width="10%">手机号</th>
	        <th data-options="field:'grandIntegral'" width="6%"  >累计积分</th>
	        <th data-options="field:'userIntegralUse'" width="6%"  >剩余积分</th>
	        <th data-options="field:'useIntegral'" width="6%"  >使用积分</th>
	        <th data-options="field:'loseIntegral'" width="6%"  >过期积分</th>
	       <!-- <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th> -->
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	             
	                     手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:150px"/>
	                   积分类型:<select id="integralType" name="integralType" class="easyui-combobox" style="width:100px">
		  					<option value='1' >累计积分</option>
		  					<option value='2' >剩余积分</option>
		  					<option value='3' >使用积分</option>
		  					<option value='4' >过期积分</option>
	  				</select>
	  		积分大于:<input id="greaterIntegral" name="greaterIntegral" class="easyui-textbox"  size="15" style="width:150px"/>
	  		积分小于:<input id="lessIntegral" name="lessIntegral" class="easyui-textbox"  size="15" style="width:150px"/>
	  		<!-- 时间：<input id="startaddTime" name="startaddTime" class="easyui-datebox" style="width:100px" />
	  			至:<input id="endaddTime" name="endaddTime" class="easyui-datebox" style="width:100px" /> -->
	  			
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
				/* startaddTime:$('#startaddTime').datebox('getValue'),
				endaddTime:$('#endaddTime').datebox('getValue'), */
				mobilePhone:$('#mobilePhone').val(),
				greaterIntegral:$('#greaterIntegral').val(),
				lessIntegral:$('#lessIntegral').val(),
				integralType:$('#integralType').combobox('getValue'),
			}
		});
	};
	

	
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">编辑</a>    ';
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
