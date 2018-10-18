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
	<table id="tasteMoneyList" title="体验金设置" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/tasteMoneyManager.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#tasteMoneyTools'" >
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden" ></th>
				<th data-options="field:'name'" width="8%" >体验金名称</th>
				<th data-options="field:'code'" width="8%" >体验金编号</th>
				<th data-options="field:'amount'" formatter="formatAmount" styler="styleColor" width="8%">体验金金额</th>
				<th data-options="field:'enableAmount'" formatter="formatAmount" styler="styleColor" width="8%">启用金额</th>
				<th data-options="field:'sendQty'" width="6%">已发数量</th>
				<th data-options="field:'usedQty'" width="6%">已使用数量</th>
				<!-- <th data-options="field:'applicableProducts'"formatter="formatProducts" width="6%">适用产品</th> -->
				<th data-options="field:'deadline'" width="6%">有效期限(天)</th>
				<th data-options="field:'applicableUser'" formatter="formatUsers" width="6%">适用用户</th>
				<th data-options="field:'status'" formatter="formatStatus" width="6%">状态</th>
				<th data-options="field:'addName'" width="8%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDate" width="10%">添加时间</th>
				<th data-options="field:'updateName'" width="8%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDate" width="10%">修改时间</th>
				<th
					data-options="field:'_operate',width:80,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="tasteMoneyTools" style="padding:5px;height:750">
		<form id="tasteMoneyForm">
			<input id="searchType" name="type" value="3" hidden="hidden" /> 
			体验金金额：<input class="easyui-numberbox" id="searchTasteMoneyAmount" name="amount" size="15" style="width:150px" />
			体验金名称：<input class="easyui-textbox" id="searchTasteMoneyName" name="name" size="15" style="width:150px" />
			状态：<select
				id="searchActivityStatus" name="status" style="width:100px;"
				class="easyui-combobox">
				<option value=''>全部</option>
				<option value='0'>生效中</option>
				<option value='1'>使用中</option>
				<option value='2'>已失效</option>
			</select> 
			<a id="searchActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetActivityBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addTasteMoneyBtn()">新增</a>
		</form>
	</div>
	
	<!-- 新增体验金 -->
	<div id="addTasteMoneyDialog" class="easyui-dialog" title="新增红包"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addTasteMoneyBtn'"
		style="width:600px;height:400px;padding:20px;">
		<form id="addTasteMoneyForm">
			<table align="center">
				<input type="text" name="type" hidden="hidden" value="3" readonly="readonly"/>
				<tr>
					<td>体验金名称:</td>
					<td><input id="addName" type="text" name="name" class="easyui-textbox" data-options="required:true" size="20px"/>
					<td>体验金编号:</td>
					<td><input id="addCode" type="text" name="code" class="easyui-textbox" readonly="readonly"/>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>体验金金额:</td>
					<td><input id="addAmount"  name="amount" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true"/>
					</td>
				<td>启用金额:</td>
					<td><input id="addEnableAmount" name="enableAmount" class="easyui-numberbox" data-options="groupSeparator:',',required:true" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>有效期限:</td>
					<td style="width:120px">
						<input id="addDeadline" style="width:110px" name="deadline" type="text" class="easyui-numberbox" data-options="required:true" > (天)
					</td>
					<td>发放数量:</td>
					<td><input type="text" name="grantQty" class="easyui-numberbox" data-options="required:true" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>适用用户：</td>
					<td colspan="15">
						<input id="addAll" name="applicableUser" type="radio" value="0"/>所有用户 &nbsp;&nbsp;
						<input id="addGeneral" name=applicableUser type="radio" value="1" />普通用户 &nbsp;&nbsp;
						<input id="addWoolParty" name="applicableUser" type="radio" value="2" />羊毛党
					</td>
				</tr>
				<tr>
					<td>适用场景：</td>
					<td colspan="15">
						<input id="telephoneSales" name="applicableScenarios" type="radio" value="0" />电销使用 
						<input id="activity" name="applicableScenarios" type="radio" value="1" />活动使用
						<input id="operational" name="applicableScenarios" type="radio" value="2" />运营使用
					</td>
				</tr> 
			</table>
		</form>
		<div id="#addTasteMoneyBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="submitActivityAdd()">添加</a>
		</div>
	</div>
	
	<!-- <div id="queryTasteMoneyDialog" class="easyui-dialog" title="体验金-查看"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#queryTasteMoneyBtn'"
		style="width:400px;height:250px;padding:10px;">
		<form id="updateTasteMoneyBtnForm" >
	    	<table align="center">
				<input id="updateActivityId" name="id"  readonly="readonly" type="hidden"/>
				<tr>
					<td>体验金名称:</td>
					<td><input id="updateName" type="text" name="name" class="easyui-textbox" data-options="required:true" readonly="readonly"/>
				</td>
				<tr>
					<td>体验金编号:</td>
					<td><input id="updateCode" type="text" name="code" class="easyui-textbox" data-options="required:true" readonly="readonly"/>
				</td>
				<tr>
					<td>体验金金额:</td>
					<td><input id="updateAmount"  name="amount" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',required:true" readonly="readonly"/>
				</td>
				</tr>
				<tr>
					<td>启用金额:</td>
					<td><input id="updateEnableAmount" name="enableAmount" class="easyui-numberbox" data-options="groupSeparator:',',required:true" readonly="readonly"/></td>
					
				</tr>
				<tr>
					<td>已发数量:</td>
					<td><input  id="updateGrantQty" name="grantQty" class="easyui-numberbox" data-options="required:true" readonly="readonly" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>适用用户：</td>
					<td colspan="2">
						<input id="all" name="applicableUser" type="radio" value="0"  readonly="readonly"/>所有用户 &nbsp;&nbsp;
						<input id="general" name=applicableUser type="radio" value="1"  readonly="readonly"/>普通用户 &nbsp;&nbsp;
						<input id="woolParty" name="applicableUser" type="radio" value="2"   readonly="readonly"/>羊毛党
					</td>
				</tr>
			</table>
		</form>
		<div id="#queryTasteMoneyBtn" align="center">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeActivityUpdateWindow()">关闭</a>
	    </div>
	 </div> -->
	<script type="text/javascript">
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status == "0"){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateTasteMoneyBtn('+index+')">关闭</a>';
		}else{
			return '';
		}
	} 
	
	//重置按钮
	$('#resetActivityBtn').click(function(){
		$("#tasteMoneyForm").form("reset");
		$("#tasteMoneyList").datagrid("load");
	});
	
	//查询按钮
	$('#searchActivityBtn').click(function(){
 		$('#tasteMoneyList').datagrid({
			queryParams: {
				amount:$('#searchTasteMoneyAmount').val(),
				name:$('#searchTasteMoneyName').val(),
				status:$('#searchActivityStatus').combobox("getValue"),
			}
		}); 
	});
	//新增
	function addTasteMoneyBtn(){
		$("#addTasteMoneyForm").form("reset");
		$("#addTasteMoneyDialog").dialog("open");
	}
	
	
	function updateTasteMoneyBtn(index){
		$('#tasteMoneyList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#tasteMoneyList').datagrid('getSelected');
	    var DrActivityParameter={
	    	id:row.id,
	    };
	    $.messager.confirm('操作提示', '你确定关闭['+row.name+']吗？', function(r){
			if(r){
				$.ajax({
					url:"${apppath}/activity/updateStatus.do",
					type:"POST",
					data:DrActivityParameter,  
					success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#tasteMoneyForm").form("reset");
							$("#tasteMoneyList").datagrid("load");
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
				});
			}
		});
	    
	}
	
	/* //跳转到修改页面
	function updateTasteMoneyBtn(index){  
		$('#tasteMoneyList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#tasteMoneyList').datagrid('getSelected'); 
		var url = "${apppath}/activity/queryTasteMoney.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#updateActivityId").val(result.map.drActivityParameter.id);
					$("#updateCode").textbox('setValue',result.map.drActivityParameter.code);
					$("#updateName").textbox('setValue',result.map.drActivityParameter.name);
					$("#updateAmount").numberbox('setValue',result.map.drActivityParameter.amount);
					$("#updateEnableAmount").numberbox('setValue',result.map.drActivityParameter.enableAmount);
					$("#updateGrantQty").numberbox('setValue',result.map.drActivityParameter.grantQty);
					initradio("applicableUser",result.map.drActivityParameter.applicableUser);
					$("#queryTasteMoneyDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	} */
	
	function initradio(rName,rValue){
       var rObj = document.getElementsByName(rName);
       for(var i = 0;i < rObj.length;i++){
           if(rObj[i].value == rValue){
               rObj[i].checked =  'checked';
           }
       }
   	}
   	
   	function submitActivityAdd(){
		var validate = $("#addTasteMoneyForm").form("validate");
		if(!validate){
			return false;
		}
		
		if(!validata("applicableUser")){
			$.messager.alert("提示信息","选择适用用户群");
			return false;
		}
		$.ajax({
         	url: "${apppath}/activity/addActivityParameter.do",
           	type: 'POST',
           	data:$("#addTasteMoneyForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#tasteMoneyList").datagrid("reload");
					$("#addTasteMoneyDialog").dialog("close");
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
	//修改设置
	/**function submitActivityAdd(){  
		var validate = $("#updateTasteMoneyBtnForm").form("validate");
		if(!validate){
			return false;
		}
   		$.ajax({
          	url: "${apppath}/activity/modifyTasteMoney.do",
            type: 'POST',
            data:$("#updateTasteMoneyBtnForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#tasteMoneyList").datagrid("reload");
					$("#queryTasteMoneyDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });
	}**/
	
	//格式化金额
	function formatAmount(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatStatus(value,row,index){
		if(value=="0"){
			return "生效中";
		}else{
			return "已失效";
		}
	}
	
	//关闭窗口
	/* function closeActivityUpdateWindow(){  
		$("#tasteMoneyList").datagrid("reload");
		$("#queryTasteMoneyDialog").dialog("close");
	} */
	
	//修改显示状态信息
	function formatProducts(value,row,index){
		if(value=="0"){
			return '新手标';
		}else if(value=="1"){
			return '票据安选';
		}else{
			return '票据优选';
		}
	}
	
	function formatUsers(value,row,index){
		if(value=="1"){
			return '普通用户';
		}else if(value=="2"){
			return '羊毛党';
		}else{
			return '所有用户';
		}
	}
	
	function formatDate(value,row,index){
		if(value!=''&&value!=null){
			var unixTimestamp = new Date(value);  
			return unixTimestamp.toLocaleString();
		}else{
			return '';
		}
	}
</script>
</body>
</html>