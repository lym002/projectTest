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
	<table id="doubleCouponsList" title="翻倍券管理" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/doubleCouponsManager.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#doubleCouponsTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">翻倍ID</th>
				<th data-options="field:'code'" width="10%">翻倍券编号</th>
				<th data-options="field:'name'" width="10%">翻倍券名称</th>
				<th data-options="field:'multiple'" styler="styleColor" width="6%">翻倍倍数</th>
				<th data-options="field:'enableAmount'" styler="styleColor" width="8%" formatter="formatAmount">启用金额</th>
				<th data-options="field:'sendQty'" width="7%">发放数量</th>
				<th data-options="field:'usedQty'" width="7%"  formatter="formatOpen">已使用数量</th>
				<th data-options="field:'surplusQty'" width="6%">剩余数量</th>
				<th data-options="field:'deadline'" width="6%">有效期限(天)</th>
				<th data-options="field:'productDeadline'" width="6%">产品期限(天)</th>
				<th data-options="field:'applicableScenarios'" width="6%" formatter="formatScenarios">适用场景</th>
				<th data-options="field:'status'" formatter="formatStatus" width="6%">状态</th>
				<th data-options="field:'addName'" width="6%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="10%">添加时间</th>
				<th data-options="field:'updateName'" width="6%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="10%">修改时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="doubleCouponsTools" style="padding:5px;height:750">
		<form id="doubleCouponsForm">
			<input id="searchType" name="type" value="4" hidden="hidden" /> 
			翻倍券编号：<input class="easyui-textbox" id="searchDoubleCouponsCode" name="code" size="15" style="width:150px" />
			翻倍券名称：<input class="easyui-textbox" id="searchDoubleCouponsName" name="name" size="15" style="width:150px" />
			翻倍倍数：<input class="easyui-numberbox" id="searchDoubleCouponsMultiple" data-options="precision:2,groupSeparator:','"  name="multiple" size="15" style="width:150px" />
			状态：<select id="searchActivityStatus" name="status" style="width:100px;" class="easyui-combobox">
					<option value=''>全部</option>
					<option value='0'>生效中</option>
					<option value='1'>使用中</option>
					<option value='2'>已失效</option>
				</select> 
			<a id="searchActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addDoubleCouponsDialog()">新增</a>
		</form>
	</div>


	<!-- 新增红包活动 -->
	<div id="addDoubleCouponsDialog" class="easyui-dialog" title="新增翻倍券"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDoubleCouponsBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="addDoubleCouponsForm">
			<table align="center">
				<input type="text" name="type" hidden="hidden" value="4" readonly="readonly"/>
				<tr>
					<td style="width:90px">翻倍券编号:</td>
					<td><input type="text" name="code" class="easyui-textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td style="width:90px">翻倍券名称:</td>
					<td><input type="text" name="name" class="easyui-textbox" data-options="required:true" /></td>
					<td style="width:90px">翻倍倍数:</td>
					<td><input type="text" name="multiple" class="easyui-numberbox" value="2.00" min="1.00" data-options="precision:2,groupSeparator:',',required:true" />
					</td>
				</tr>
				<tr>
					<td>启用金额:</td>
					<td><input type="text" name="enableAmount" class="easyui-numberbox" data-options="groupSeparator:',',required:true" /></td>
					<td>发放数量:</td>
					<td><input type="text" name="grantQty" class="easyui-numberbox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>适用场景：</td>
					<td colspan="3">
						<input id="telephoneSales" name="applicableScenarios" type="radio" value="0" />电销使用 
						<input id="activity" name="applicableScenarios" type="radio" value="1" />活动使用
						<input id="operational" name="applicableScenarios" type="radio" value="2" />运营使用
					</td>
				</tr> 
				<!-- <tr>
					<td>适用产品：</td>
					<td colspan="3">
						<input id="optimization" name="applicableProducts" type="checkbox" value="2" />票据优选
						<input id="security" name="applicableProducts" type="checkbox" value="3" />票据安选
					</td>
				</tr> -->
				<tr>
					<td>适用用户：</td>
					<td colspan="3">
						<input id="all" name="applicableUser" type="radio" value="0" />所有用户 
						<input id="general" name=applicableUser type="radio" value="1" />普通用户 
						<input id="woolParty" name="applicableUser" type="radio" value="2" />羊毛党
					</td>
				</tr>
				<tr>
					<td>有效期限:</td>
					<td>
						<input id="deadline" name="deadline" type="text" class="easyui-numberbox" data-options="required:true" style="width:80px"> (天)
					</td>
					<td>产品期限≥:</td>
					<td>
						<input id="productDeadline" name="productDeadline" type="text" class="easyui-numberbox" data-options="required:true" style="width:80px"> (天)
					</td>
				</tr>
			</table>
		</form>
		<div id="#addDoubleCouponsBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDoubleCoupons()">添加</a>
		</div>
	</div>
	
	<div id="updateActivityWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:380px;padding:10px;">
	</div>
		
	<script type="text/javascript">
	//重置按钮
	$('#resetActivityBtn').click(function(){
		$("#doubleCouponsForm").form("reset");
		$("#doubleCouponsList").datagrid("load", {});
	});
	
	
	//查询按钮
	$('#searchActivityBtn').click(function(){
 		$('#doubleCouponsList').datagrid({
			queryParams: {
				multiple:$('#searchDoubleCouponsMultiple').val(),
				code:$('#searchDoubleCouponsCode').val(),
				name:$('#searchDoubleCouponsName').val(),
				status:$('#searchActivityStatus').combobox("getValue"),
			}
		}); 
	});
	
	//跳转到添加页面
	function addDoubleCouponsDialog(){  
		$("#addDoubleCouponsForm").form("reset");
		$("#addDoubleCouponsDialog").dialog("open");
	}

	function addDoubleCoupons(){
		var validate = $("#addDoubleCouponsForm").form("validate");
		if(!validate){
			return false;
		}
		
		if(!validata("applicableScenarios")){
			$.messager.alert("提示信息","选择适用场景");
			return false;
		}
		if(!validata("applicableUser")){
			$.messager.alert("提示信息","选择适用用户群");
			return false;
		}
// 		if($('input[name="applicableScenarios"]:checked').val()==1){
			/* $.messager.confirm("操作提示", "活动类型的翻倍券有且只能存在一个，您确定要执行操作吗？", function (data) {
	            if (data) {
	                $.ajax({
			         	url: "${apppath}/activity/addActivityParameter.do",
			           	type: 'POST',
			           	data:$("#addDoubleCouponsForm").serialize(),  
			           	success:function(result){
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#doubleCouponsList").datagrid("reload");
								$("#addDoubleCouponsDialog").dialog("close");
							}else{
								$.messager.alert("提示信息",result.errorMsg);
							}
						}	
			       	});
        			return false; // 阻止表单自动提交事件
	            }else {
	                return false; 
	            }
        	}); */
// 		}else{
			 $.ajax({
	         	url: "${apppath}/activity/addActivityParameter.do",
	           	type: 'POST',
	           	data:$("#addDoubleCouponsForm").serialize(),  
	           	success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#doubleCouponsList").datagrid("reload");
						$("#addDoubleCouponsDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	       	});
   			return false; // 阻止表单自动提交事件
// 		}
		
	}
	
	function validata(str){
		var checkboxes = document.getElementsByName(str);
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
     			return true;
     		}
		}
		return false;
	}
	
	
	//跳转到红包活动修改页面
	function updateActivityWindow(index){
		$('#doubleCouponsList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#doubleCouponsList').datagrid('getSelected'); 
		$("#updateActivityWindow").window({
			title:"修改翻倍券",
			href:"../activity/toActivityModify.do?id="+row.id
		});
		$("#updateActivityWindow").window("open");
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(2 != row.status){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改信息</a>';  
		}
	} 
	
	function formatScenarios(value,row,index){
		if(value==0){
			return '电销使用';
		}else{
			return '活动使用';
		}
	}
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '生效中';
		}else if(row.status=="1"){
			return '使用中';
		}else{
			return '已失效';
		}
	}
	
	function formatAmount(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	//跳转到对应的投资订单页面
	function formatOpen(value,row,index){
			return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.id+"')\">"+value+"</a>";	
	}
	function selectData(id){//跳转到优惠对应投资订单页面
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "优惠投资订单",
				content:"<iframe width='100%' height='100%' frameborder='0' src='../investOrder/toActivityInvestOrder.do.do?id="+id+"'></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab); 
		mainTab.tabs("add",detailTab);
	}
</script>
</body>
</html>