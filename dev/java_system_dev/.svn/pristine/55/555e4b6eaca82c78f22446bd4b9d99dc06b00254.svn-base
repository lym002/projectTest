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
	<table id="redEnvelopeList" title="红包管理" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/redEnvelopeManager.do',
	    method:'post',rownumbers:true,showFooter: true,
	    pagination:true,toolbar:'#redEnvelopeTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">红包ID</th>
				<th data-options="field:'code'" width="8%" formatter="formatCode">红包编号</th>
				<th data-options="field:'name'" width="6%" formatter="formatName">红包名称</th>
				<th data-options="field:'raisedRates'" width="6%"  styler="styleColor" formatter="formatRaisedRates">红包返现率</th>
				<th data-options="field:'amount'" styler="styleColor" width="6%" formatter="formatAmount">红包金额</th>
				<th data-options="field:'enableAmount'" styler="styleColor" width="8%" formatter="formatAmount">启用金额</th>
				<th data-options="field:'orderAmount'" styler="styleColor" width="8%" formatter="formatAmount">订单金额</th>
				<th data-options="field:'grantQty'" width="4%">发放数量</th>
				<th data-options="field:'usedQty'" width="4%" formatter="formatOpen">已使用数量</th>
				<th data-options="field:'sendQty'" width="5%">已发放数量</th>
				<th data-options="field:'surplusQty'" width="4%">剩余数量</th>
				<th data-options="field:'deadline'" width="5%">有效期限(天)</th>
				<th data-options="field:'productDeadline'" width="5%">产品期限(天)</th>
				<th data-options="field:'applicableScenarios'" width="5%" formatter="formatScenarios">适用场景</th>
				<th data-options="field:'status'" formatter="formatStatus" width="4%">状态</th>
				<th data-options="field:'addName'" width="4%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="8%">添加时间</th>
				<th data-options="field:'updateName'" width="4%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="8%">修改时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="redEnvelopeTools" style="padding:5px;height:750">
		<form id="redEnvelopeForm">		
			红包类型：<select id="searchType" name="type" style="width:100px;" class="easyui-combobox">
				<option value='' selected="selected">全部</option>
				<option value="1">返现红包</option>
				<option value="5">比例红包</option>
			</select>
			红包金额：<input class="easyui-numberbox" id="searchRedEnvelopeAmount" name="amount" size="15" style="width:150px" />
			红包名称：<input class="easyui-textbox" id="searchRedEnvelopeName" name="name" size="15" style="width:150px" />
			状态：<select
				id="searchActivityStatus" name="status" style="width:100px;"
				class="easyui-combobox">
				<option value=''>全部</option>
				<option value='0'>生效中</option>
				<option value='1'>使用中</option>
				<option value='2'>已失效</option>
			</select> 
			时间:<input  id="startTime" name="startTime" class="easyui-datebox" style="width:100px"/>
	  				-<input  id="endTime" name="endTime" class="easyui-datebox" style="width:100px"/>
			<a id="searchActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addredEnvelopeDialog()">新增</a>
		</form>
	</div>


	<!-- 新增红包活动 -->
	<div id="addRedEnvelopeDialog" class="easyui-dialog" title="新增红包"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addRedEnvelopeBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="addRedEnvelopeForm">
			<table align="center">
<!-- 				<input type="text" name="type" hidden="hidden" value="1" readonly="readonly"/> -->
				<tr>
					<td  style="width:100px">红包编号:</td>
					<td><input type="text" name="code" class="easyui-textbox" readonly="readonly"/></td>
					<td  style="width:100px">红包类型:</td>
					<td>
					<!--<select id="type" name="type" style="width:100px;"class="easyui-combobox" >
 							<option value='1' >返现</option> 
 							<option value='5' >比例</option> 
						</select>   -->	
		
						<input id="type" class="easyui-combobox" name="type" data-options="    
						        valueField: 'label',    
						        textField: 'value',    
						        data:[{
										label: '1',
										value: '返现'
									},{
										label: '5',
										value: '比例'
									}
						        ],   
						        onSelect: function(rec){  
						        	if(rec.label==1){
						        		$('#type1').show();
						        		$('#type1_1').numberbox({ required:true});
						        		$('#type5_1').numberbox({ required:false});
						        		$('#type5').hide();
						        	}else{
						        		$('#type5').show();
						        		$('#type5_1').numberbox({ required:true});
						        		$('#type1_1').numberbox({ required:false});
						        		$('#type1').hide();
						        	}
						        },required:true" />   
						
		
					</td>					
				</tr>
				<tr>
					<td style="width:100px">红包名称:</td>
					<td><input type="text" name="name" class="easyui-textbox" data-options="required:true" /></td>
				</tr>
				<tr id="type1">
					<td style="width:100px"  >红包金额:</td>
					<td   >
						<input type="text" id="type1_1" name="amount" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true" />
					</td>					
				</tr>
				<tr id="type5">				
					<td style="width:100px"  >红包比例:</td>
					<td >
						<input id="type5_1" type="text" name="raisedRates" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true" />
					</td>
					<td><span>%</span></td>
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
					<td style="width:120px">
						<input id="deadline" style="width:110px" name="deadline" type="text" class="easyui-numberbox" data-options="required:true" > (天)
					</td>
					<td>产品期限≥:</td>
					<td style="width:120px">
						<input id="productDeadline" style="width:110px" name="productDeadline" type="text" class="easyui-numberbox" data-options="required:true" > (天)
					</td>
				</tr>
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addRedEnvelope()">添加</a>
		</div>
	</div>
	
	<div id="updateActivityWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:550px;height:380px;padding:10px;">
	</div>
		
	<script type="text/javascript">
	//重置按钮
	$('#resetActivityBtn').click(function(){
		$("#redEnvelopeForm").form("reset");
		$("#redEnvelopeList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchActivityBtn').click(function(){
 		$('#redEnvelopeList').datagrid({
			queryParams: {
				type:$('#searchType').combobox("getValue"),
				amount:$('#searchRedEnvelopeAmount').val(),
				name:$('#searchRedEnvelopeName').val(),
				status:$('#searchActivityStatus').combobox("getValue"),
				startTime:$('#startTime').datetimebox("getValue"),
				endTime:$('#endTime').datetimebox("getValue")
			}
		}); 
	});
	
	//跳转到添加页面
	function addredEnvelopeDialog(){  
		$("#addRedEnvelopeForm").form("reset");
		$("#addRedEnvelopeDialog").dialog("open");
	}

	function addRedEnvelope(){
		var validate = $("#addRedEnvelopeForm").form("validate");
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
		$.ajax({
         	url: "${apppath}/activity/addActivityParameter.do",
           	type: 'POST',
           	data:$("#addRedEnvelopeForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#redEnvelopeList").datagrid("reload");
					$("#addRedEnvelopeDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
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
		$('#redEnvelopeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#redEnvelopeList').datagrid('getSelected'); 
		$("#updateActivityWindow").window({
			title:"修改红包",
			href:"../activity/toActivityModify.do?id="+row.id
		});
		$("#updateActivityWindow").window("open");
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){
		if(isNaN(row.id))
			return ;
		if(2 != row.status){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改信息</a>';  
		}
    	
	} 
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(isNaN(row.status))
			return ;
		if(row.status=="0"){
			return '生效中';
		}else if(row.status=="1"){
			return '使用中';
		}else{
			return '已失效';
		}
	}
	
	function formatAmount(value,rec,index){
		if(isNaN(value)){
			return value;
		}else if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatScenarios(value,row,index){
		if(value==0){
			return '电销使用';
		}else if(value==1){
			return '活动使用';
		}else if(value==2){
			return '运营使用';
		}else{
			return '';
		}
	}
	//跳转到对应的投资订单页面
	function formatOpen(value,row,index){
			if(!isNaN(row.id))
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
	//格式化返现率
	function formatRaisedRates(value,rec,index){
		if(isNaN(value))
			return value;
		if(value==0||value==null)
			return '0.00';
		else
			return (value + '%');
	}
	//
	function formatCode(value,rec,index){
		if(value =='使用红包用户数')
			return "<span style='color:red'>"+value+"</span>";		
		else
			return value;
	}
	//
	function formatName(value,rec,index){
		if(!isNaN(value))
			return "<span style='color:red'>"+value+"</span>";		
		else
			return value;
	}
</script>
</body>
</html>