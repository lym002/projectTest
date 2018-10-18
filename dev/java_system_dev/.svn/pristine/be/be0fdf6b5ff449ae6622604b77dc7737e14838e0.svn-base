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
	<table id="drCpsFavourableRuleList" title="促复投红包发送规则" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../cps/cpsFavourableRuleList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#cpsFavourableRuleTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">ID</th>
				<th data-options="field:'minAmount'" width="6%">最小金额</th>
				<th data-options="field:'maxAmount'" width="6%">最大金额</th>
				<th data-options="field:'code_1'" width="8%">红包1</th>
				<th data-options="field:'code_2'" width="8%">红包2</th>
				<th data-options="field:'code_3'" width="8%">红包3</th>
				<th data-options="field:'isFirst'" width="5%" formatter="formatIsFirst">是否首笔投资</th>
				<th data-options="field:'isCps'" width="5%" formatter="formatIsCPS">渠道</th>
				<th data-options="field:'status'" formatter="formatStatus" width="4%">状态</th>
				<th data-options="field:'addUserName'" width="4%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="8%">添加时间</th>
				<!-- <th data-options="field:'updUserName'" width="4%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="8%">修改时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>  -->
			</tr>
		</thead>
	</table>
	<div id="cpsFavourableRuleTools" style="padding:5px;height:750">
		<form id="cpsFavourableRuleForm">
			是否首投：<select id="searchIsFirst" name="isFirst" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<option value='0'>否</option>
					<option value='1'>是</option>
					
			</select>
			渠道：<select id="searchIsCps" name="isCps" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<option value='0'>非CPS渠道</option>
					<option value='1'>CPS渠道</option>
					
			</select>
			状态：<select id="searchStatus" name="status" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<option value='0'>失效</option>
					<option value='1'>有效</option>
					
			</select>
			<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addCpsFavourableRuleDialogBtn()">新增</a>
		</form>
	</div>

	<div id="addCpsRuleDialog" class="easyui-dialog" title="新增促复投红包发送规则" 
		data-options="iconCls:'icon-add',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addCpsRuleBtn'"
		style="width:800px;height:400px;padding:20px;">
		<form id="addCpsFavourableRuleFrom">
			<table align="center">
				<tr>
					<td>范围区间：</td>
					<td><input type="text" id="minAmount" name="minAmount" class="easyui-textbox" data-options="required:true" />
						-
						<input type="text" id="maxAmount" name="maxAmount" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>是否首笔：</td>
					<td><select id="isFirst" name="isFirst" style="width:150px" class="easyui-combobox" data-options="required:true">
							<option value='1' selected="selected">是</option>
							<option value='0'>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>渠道：</td>
					<td><select id="isCps" name="isCps" style="width:150px" class="easyui-combobox" data-options="required:true">
							<option value='1' selected="selected">CPS渠道</option>
							<option value='0'>非CPS渠道</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>红包1：</td>
					<td>
						<select id="addCouponType1" name="activityId_1" style="width:150px;" data-options="required:true">
							<option value="" >-请选择-</option>
						</select>
					
				</tr>
				<tr>
					<td>红包2：</td>
					<td>
						<select id="addCouponType2" name="activityId_2" style="width:150px;" data-options="required:true">
							<option value="" >-请选择-</option>
						</select>
				</tr>
				<tr>
					<td>红包3：</td>
					<td>
						<select id="addCouponType3" name="activityId_3" style="width:150px;">
							<option value="" >-请选择-</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="#addCpsRuleBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addCpsRule()">添加</a>
		</div>
	</div>
		
	<script type="text/javascript">
		
	     //重置按钮
		$('#resetBtn').click(function(){
			$("#addCpsFavourableRuleFrom").form("reset");
			$("#drCpsFavourableRuleList").datagrid("load", {});
		});
		
		//查询按钮
		$("#searchBtn").click(function(){
	 		$("#drCpsFavourableRuleList").datagrid({
				queryParams: {
					isCps:$("#searchIsCps").combobox("getValue"),
					isFirst:$("#searchIsFirst").combobox("getValue"),
					status:$("#searchStatus").combobox("getValue")
				}
			}); 
		});
		
		$(function(){
			//根据优惠券类型获取优惠券
			$("#addCouponType1").combogrid({
		     	panelWidth:460,
		     	url:'../activity/drActivityByCouponTypeList.do?scene=1&types=5,1',
		     	idField:'id',    
    			textField:'name',
		       	columns:[[    
		       		{field:'id',title:'编号',hidden:true},
			        {field:'code',title:'编号',width:120},    
			        {field:'name',title:'名称',width:200},    
			        {field:'raisedRates',title:'比例',width:60},  
			        {field:'amount',title:'金额',width:60}     
			    ]]
		     });
		     $("#addCouponType2").combogrid({
		     	panelWidth:460,
		     	url:'../activity/drActivityByCouponTypeList.do?scene=1&types=1,5',
		     	idField:'id',    
    			textField:'name',
		       	columns:[[
		       		{field:'id',title:'编号',hidden:true},
			        {field:'code',title:'编号',width:120},    
			        {field:'name',title:'名称',width:200},    
			        {field:'raisedRates',title:'比例',width:60},  
			        {field:'amount',title:'金额',width:60}       
			    ]]
		     });
		     $("#addCouponType3").combogrid({
		     	panelWidth:460,
		     	url:'../activity/drActivityByCouponTypeList.do?scene=1&types=1,5',
		     	idField:'id',    
    			textField:'name',
		       	columns:[[    
		       		{field:'id',title:'编号',hidden:true},
			        {field:'code',title:'编号',width:120},    
			        {field:'name',title:'名称',width:200},    
			        {field:'raisedRates',title:'比例',width:60},  
			        {field:'amount',title:'金额',width:60}    
			    ]]
		     });
		     $("#isFirst").combobox({
		     	onSelect:function(rec){
		     		if(rec.value==0){
		     			$("#tr_3").show();
		     		}else{
		     			$("#addCouponType3").combogrid("setValue","");
		     		}
		     	}
		     });
		});
	     
	     //跳转到添加页面
		function addCpsFavourableRuleDialogBtn(){  
			$("#addCpsFavourableRuleFrom").form("reset");
			$("#addCpsRuleDialog").dialog("open");
		}
	     
		function addCpsRule(){
			var validate = $("#addCpsFavourableRuleFrom").form("validate");
			if(!validate){
				return false;
			}
			
			var isFirst = $("#isFirst").combobox('getValue');
			if(isFirst=='0'){
				var activityId_3 = $('#addCouponType3').combogrid('getValue');
				if(activityId_3 == ''){
					$.messager.alert("提示信息","红包3未选择");
					return false;
				}
			}else{
				$("#addCouponType3").combogrid("setValue","");
			}
			$.ajax({
	         	url: "../cps/addMemberCpsRule.do",
	           	type: 'POST',
	           	data:$("#addCpsFavourableRuleFrom").serialize(),  
	           	success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#drCpsFavourableRuleList").datagrid("reload");
						$("#addCpsRuleDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	       	});
	        return false; // 阻止表单自动提交事件
		}
		
		
		//状态信息
		function formatStatus(value,row,index){
			if(row.status == "1"){
				return '<a href="#" onclick="updateStatus('+row.id+')">有效</a>';
			}else{
				return '失效';
			}
		}
		function updateStatus(id){
			$.ajax({
	          	url: "${apppath}/cps/updateStatus.do?id="+id,
	            type: 'POST',
	            success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#drCpsFavourableRuleList").datagrid("reload");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	        });
			
		}
		
		//是否首笔投资
		function formatIsFirst(value,row,index){
			if(row.isFirst == "1"){
				return '是';
			}
			return '否';
		}
		//渠道
		function formatIsCPS(value,row,index){
			if(row.isCps == "1"){
				return 'CPS渠道';
			}
			return '非CPS渠道';
		}

		
		function formatDate(now) { 
			var year=now.getYear(); 
			var month=now.getMonth()+1; 
			var date=now.getDate(); 
			var hour=now.getHours(); 
			var minute=now.getMinutes(); 
			var second=now.getSeconds(); 
			return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
		} 
		
	</script>
</body>
</html>