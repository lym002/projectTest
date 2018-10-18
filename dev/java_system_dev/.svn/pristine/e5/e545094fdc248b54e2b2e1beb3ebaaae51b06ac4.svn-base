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
	<table id="incrRestList" title="加息券管理" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/incrRestManager.do?',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#incrRestTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">加息券ID</th>
				<th data-options="field:'code'" width="10%">加息券编号</th>
				<th data-options="field:'name'" width="10%">加息券名称</th>
				<th data-options="field:'raisedRates'" width="6%"  styler="styleColor" formatter="formatRaisedRates">加息额度</th>
				<th data-options="field:'enableAmount'" width="10%"  styler="styleColor" formatter="formatAmount">启用金额</th>
				<th data-options="field:'grantQty'" width="6%">发放数量</th>
				<th data-options="field:'usedQty'" width="6%" formatter="formatOpen">已使用数量</th>
				<th data-options="field:'sendQty'" width="6%">已发放数量</th>
				<th data-options="field:'surplusQty'" width="6%">剩余数量</th>
				<th data-options="field:'deadline'" width="6%">有效期限</th>
				<th data-options="field:'productDeadline'" width="6%">有效期限</th>
				<th data-options="field:'applicableScenarios'" width="6%" formatter="formatScenarios">适用场景</th>
				<th data-options="field:'status'" width="6%" formatter="formatStatus">状态</th>
				<th data-options="field:'addName'" width="6%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDate" width="10%">添加时间</th>
				<th data-options="field:'updateName'" width="6%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDate" width="10%">修改时间</th>
				<th data-options="field:'_operate',width:200,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="incrRestTools" style="padding:5px;height:750">
		<form id="incrRestForm">
			<input id="searchType" name="type" value="2" hidden="hidden" /> 
			加息额度：<input class="easyui-numberbox" id="searchRaisedRates" precision="1"   name="raisedRates" size="15" style="width:150px" />&nbsp;&nbsp; 
			加息券名称：<input class="easyui-textbox" id="searchName" name="name" size="50" style="width:150px" />
			状态：<select id="searchActivityStatus" name="status" style="width:100px;" class="easyui-combobox">
				<option value=''>全部</option>
				<option value='0'>生效中</option>
				<option value='1'>已使用</option>
				<option value='2'>已失效</option>
			</select> 
			<a id="searchActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addredEnvelopeDialog()">新增</a>
		</form>
	</div>


	<!-- 新增加息券活动 -->
	<div id="addIncrRestDialog" class="easyui-dialog" title="新增加息券"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addRedEnvelopeBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="addIncrRestForm">
			<table align="center">
				<input type="text" name="type" hidden="hidden" value="2" readonly="readonly"/>
				<tr>
					<td style="width:100px">加息券编号:</td>
					<td><input type="text" name="code" class="easyui-textbox" readonly="readonly"/></td>
				</tr>
				<tr>
					<td style="width:100px">加息券名称:</td>
					<td><input type="text" name="name" class="easyui-textbox" data-options="required:true" /></td>
					<td style="width:100px">加息券额度:</td>
					<td style="width:120px"><input type="text" style="width:110px" name="raisedRates" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true" />%
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
						<input id="optimization" name="applicableProducts" type="checkbox" value="2" />票据优选 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="security" name="applicableProducts" type="checkbox" value="3" />票据安选
					</td>
				</tr> -->
				<tr>
					<td>适用用户：</td>
					<td colspan="3">
						<input id="all" name="applicableUser" type="radio" value="0" />所有用户 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="general" name=applicableUser type="radio" value="1" />普通用户 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="woolParty" name="applicableUser" type="radio" value="2" />羊毛党
					</td>
				</tr>
				<tr>
					<td>有效期限:</td>
					<td style="width:120px">
						<input id="addDeadline" name="deadline" class="easyui-numberbox" style="width:110px"  data-options="required:true" />天
					</td>
					<td>产品期限≥:</td>
					<td style="width:120px">
						<input id="addProductDeadline" name="productDeadline" class="easyui-numberbox" style="width:110px"  data-options="required:true" />天
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
		$("#incrRestForm").form("reset");
		$("#incrRestList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchActivityBtn').click(function(){
 		$('#incrRestList').datagrid({
			queryParams: {
				raisedRates: $('#searchRaisedRates').val(),
				name:$('#searchName').val(),
				status: $('#searchActivityStatus').combobox('getValue'),
			}
		}); 
	});
	
	//跳转到添加页面
	function addredEnvelopeDialog(){  
		$("#addIncrRestForm").form("reset");
		$("#addIncrRestDialog").dialog("open");
	}
	
	function addRedEnvelope(){
		var validate = $("#addIncrRestForm").form("validate");
		if(!validate){
			return false;
		}
		if(!validata("applicableScenarios")){
			$.messager.alert("提示信息","选择适用场景");
			return false;
		}
		/* if(!validata("applicableProducts")){
			$.messager.alert("提示信息","选择适用产品");
			return false;
		} */
		if(!validata("applicableUser")){
			$.messager.alert("提示信息","选择适用用户群");
			return false;
		}

		$.ajax({
          	url: "${apppath}/activity/addActivityParameter.do",
            type: 'POST',
            data:$("#addIncrRestForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#incrRestList").datagrid("reload");
					$("#addIncrRestDialog").dialog("close");
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
	
	var varify;//用于查询验证,验证开始时间是否小于结束时间
	$.extend($.fn.validatebox.defaults.rules, {//验证开始时间小于结束时间
		md: {
			validator: function(value, param){
				startTime2 =$(param[0]).datetimebox('getValue');
				var d1 = $.fn.datebox.defaults.parser(startTime2);
				var d2 = $.fn.datebox.defaults.parser(value);
				varify=d2>=d1;
				return varify;
			},
			message: '结束时间要大于开始时间！'
		}
	})
	 $.fn.datebox.defaults.formatter = function(date){//对于时间格式的转换
		return date.format("yyyy-MM-dd");
	};
	
	//跳转到加息券活动修改页面
	function updateActivityWindow(index){
		$('#incrRestList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#incrRestList').datagrid('getSelected'); 
		$("#updateActivityWindow").window({
			title:"修改加息券",
			href:"../activity/toActivityModify.do?id="+row.id
		});
		$("#updateActivityWindow").window("open");
	}
	function toSelectRegMemberWindow(index){
		$('#incrRestList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#incrRestList').datagrid('getSelected'); 
	    var mainTab = parent.$('#main-center'); 
		 var detailTab = {
				title : "选择发放用户列表",
				content:"<iframe scrolling='no' width='100%' height='100%' frameborder='0'  src='../regMemberInfo/toSelectRegMemberInfoList.do?id="+row.id+"'></iframe>", 
				closable : true
		}; 
		 mainTab.tabs("add",detailTab); 
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		var status;
		if(2 != row.status){
	    	return '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改信息</a>'+"  "+
	    	 '<a href="#" class="btn l-btn l-btn-small" onclick="toSelectRegMemberWindow('+index+')">发放加息劵</a>';
	    	
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
	function formatRaisedRates(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value + '%');
	}
	function formatRedEnvelopeType(value,row,index){
		if(row.redEnvelopeType =="0"){
			return '抵用券';
		}else if(row.redEnvelopeType=="1"){
			return '返现券';
		}else{
			return '其他';
		}
	}
	//修改显示生效时间
	function formatEffectiveTime(val,row,index){
		var startDate;
		var endDate;
		if(row.startTime!=''&&row.startTime!=null){
			startDate = new Date(row.startTime);  
		}
		if(row.endTime!=''&&row.endTime!=null){
			endDate =new Date(row.endTime); 
		} 
		return startDate.format("yyyy-MM-dd hh:mm:ss")+'-'+endDate.format("yyyy-MM-dd hh:mm:ss");
	}
	
	function formatDate(value,row,index){
		if(value!=''&&value!=null){
			var unixTimestamp = new Date(value);  
			return unixTimestamp.toLocaleString();
		}else{
			return '';
		}
	}
	
	function formatScenarios(value,row,index){
		if(value==0){
			return '电销使用';
		}else{
			return '活动使用';
		}
	}
	
	//跳转到对应的投资订单页面
	function formatOpen(value,row,index){
			return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.id+"')\">"+value+"</a>";	
	}
	function selectData(id){//跳转到优惠对应投资订单页面
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "优惠投资订单",
				content:"<iframe width='100%' height='100%' frameborder='0' src='../investOrder/toActivityInvestOrder.do?id="+id+"'></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab); 
		mainTab.tabs("add",detailTab);
	}
	//格式化日期
	Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()  
        // millisecond  
    }  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
            .substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
} 
</script>
</body>
</html>