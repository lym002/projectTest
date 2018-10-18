<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
	<div id="activitiesbtn" style="padding:5px;height:750">
		<form id="activitiesform">
	  		活动名称:<input id="name" name="name" class="easyui-textbox"  size="15" style="width:100px"/>
	  		创建时间:<input class="easyui-datebox" id="startTime" name="startTime" style="width:100px"/> 至 <input class="easyui-datebox" id="endTime" name="endTime" style="width:100px"/>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selactivitiesList()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetactivities()">重置</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增活动模板</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="updaterow()">编辑活动模板</a>
	    </form>
	</div>
	<!-- ------------------活动模板列表 --------------------------->
	<table id="activitiestable" class="easyui-datagrid" style="height:99%;width:99.9%"></table>
	
	<!-- -----------------新增活动模板------------------------------->
	<div id="addActivity" class="easyui-dialog" style="height:70%;width:40%" closed="true"	buttons="addBtn"  data-options="resizable:true,modal:true,closed:true">
		<div style="padding:5px;height:750">
			活动名称:<input id="activityname" name="activityname" class="easyui-textbox"  size="15" style="width:100px"/>
			抽奖代码编号:<input id="codeFixation" name="codeFixation"  class="easyui-textbox"  size="15" style="width:100px"/><font color="#D0D0D0">(字母)</font>
			<input id="digit" name="digit" class="easyui-numberbox"  data-options="min:3,max:6" style="width:100px"/><font color="#D0D0D0">(位)</font>
		</div>
		<div style="padding:5px;height:750">
			<table id="activityPrize" class="easyui-datagrid" style="height:400px;width:99.9%"></table>
		</div>
		<div id="toolActivity" style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true"  onclick="addPrize()">新增</a>
				<a href="#" class="easyui-linkbutton"  iconCls="icon-remove" plain="true"  onclick="delPrize()">删除</a>
		</div>
		<center>
		<div id="addBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActivity').dialog('close')">取消</a>
		</div>
		</center>
	</div>
</body>
<script type="text/javascript"> 
		//活动模板列表
		var activities = $('#activitiestable');
		$(function() {
			activities.datagrid({
				url : "../activityTemplate/selActivityTemplate.do",
				title : '模板列表',
				fitColumns : true,
				pagination : true,
				pageSize:20,
				singleSelect:true,
				pageList:[20,50],
				autoRowHeight : false,
				toolbar:"#activitiesbtn",
				fit:true,
				columns : [ [ 
					{
					field : 'id',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'name',
					title : '活动名称',
					width : '25%',
					align : "center"
					},{
					field : 'createTime',
					title : '创建时间',
					width : '25%',
					align : "center"
					},{
					field : 'codeFixation',
					title : '抽奖编码-固定',
					width : '25%',
					align : "center"
					},{
					field : 'digit',
					title : '位数',
					width : '25%',
					align : "center"
					}
				] ]
			});
		}); 
		
		//查询按钮
		function selactivitiesList(){
	 		activities.datagrid({
				queryParams: {
					name:$('#name').textbox('getValue'),
					startTime:$('#startTime').datebox('getValue'),
					endTime:$('#endTime').datebox('getValue')
					}
			})
		}
		
		//重置
		function resetactivities(){
			$("#activitiesform").form("reset");
			activities.datagrid("load", {});
		};
		
		
		//新增活动模板
		function resetactivities(){
			$("#activitiesform").form("reset");
			activities.datagrid("load", {});
		};
		
		//弹出新增模板框
		var id="";
		function add(){
			$('#addActivity').dialog('open').dialog('setTitle', '新增活动模板');
			$('#activityname').textbox('setValue',"");
			$('#codeFixation').textbox('setValue',"");
			$('#digit').numberbox('setValue',""); 
			loadActivityPrize();//加载产品表
		}	
		
		//加载产品表
		function loadActivityPrize(){
			$('#activityPrize').datagrid({
				url : "../activityTemplate/selActivityPrize.do?atid=00000000000000",
				title : '奖品列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				toolbar:"#toolActivity",
				onClickRow:onClickRow,
				fit:true,
				columns : [ [ 
					{
					field : 'name',
					title : '奖品名称',
					editor:{type:'textbox'},
					width : '50%',
					align : "center"
					},{
					field : 'amount',
					title : '数量',
					editor:{type:'textbox'},
					width : '50%',
					align : "center"
					}
				] ]
			});
		}
		
		//判断信息是否填写完整
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
				if ($('#activityPrize').datagrid('validateRow', editIndex)){
					//获取奖品名称
					var ed = $('#activityPrize').datagrid('getEditor', {index:editIndex,field:'name'});
					if(ed==null){
						editIndex = undefined;
						return false;
					}
					var name = $(ed.target).textbox('getValue');
					//获取奖品数量
					var ed1 = $('#activityPrize').datagrid('getEditor', {index:editIndex,field:'amount'});
					var amount = $(ed1.target).textbox('getValue');
					if(name=="" ||  amount==""){
						$.messager.alert('操作提示','请把奖品信息填写完整','error');
						return false;
					}else{
						$('#activityPrize').datagrid('endEdit', editIndex);
						editIndex = undefined;
						return true;
					}
					 
				} else {
					return false;
				}
			}
		
		//新增一行				
		function addPrize(){
				if (endEditing()){
					$('#activityPrize').datagrid('appendRow',{name:''});
					 editIndex = $('#activityPrize').datagrid('getRows').length-1;
					$('#activityPrize').datagrid('selectRow', editIndex)
					.datagrid('beginEdit', editIndex); 
				}
		}
		
		
		//删除一行
		function delPrize(){
			$('#activityPrize').datagrid('endEdit', editIndex);
			var row = $('#activityPrize').datagrid('getSelected');
			if(row != null) { 
				if (editIndex == undefined){return}
				$('#activityPrize').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
			}else{
					$.messager.alert('操作提示','请选择要删除的数据!'); 
					return false;  	
			}
				editIndex = undefined;
			}
			
		//表点击事件
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#activityPrize').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#activityPrize').datagrid('selectRow', editIndex);
				}
			}
		}
		
		//保存
		function save(){
		if($("#activityname").textbox('getValue')==null || $("#activityname").textbox('getValue')=="" ||$("#codeFixation").textbox('getValue')==null || $("#codeFixation").textbox('getValue')=="" ||$("#digit").numberbox('getValue')==null || $("#digit").numberbox('getValue')==""){
			$.messager.alert('系统提示','请把模板信息填写完整！');
			return false
		}
		if($("#codeFixation").textbox('getValue').length>6){
			$.messager.alert('系统提示','抽奖代码编号长度不能大于6！');
			return false
		}
		
		var parent=/^[A-Za-z]+$/;
		if(!parent.test($("#codeFixation").textbox('getValue')))
		  {
		  	$.messager.alert('系统提示','抽奖代码编号只能输入英文！');
			return false
		  }
		  
		if($("#digit").numberbox('getValue')>6){
			$.messager.alert('系统提示','抽奖代码位数值不能大于6！');
			return false
		}
		
			if (endEditing()){
				var rows  = $('#activityPrize').datagrid("getRows");
				if(id==null || id==""){//新增活动模板
					$.ajax({
						url : "../activityTemplate/addActivityTemplate.do",
						cache : false,
						type: "post",
						async : false,
						data : { 
								conData : JSON.stringify(rows),
								activityname:$("#activityname").textbox('getValue'),
								codeFixation:$("#codeFixation").textbox('getValue'),
								digit:$("#digit").numberbox('getValue')
								
							},
						success : function(result) {
								if(result=="success"){
									$.messager.alert('操作提示','操作成功');
									$('#addActivity').dialog('close');
									selactivitiesList();//刷新活动模板lieb
								}
							}
							});
				}else{//修改活动模板
					$.ajax({
						url : "../activityTemplate/updateActivityTemplate.do",
						cache : false,
						type: "post",
						async : false,
						data : { 
								conData : JSON.stringify(rows),
								activityname:$("#activityname").textbox('getValue'),
								codeFixation:$("#codeFixation").textbox('getValue'),
								digit:$("#digit").numberbox('getValue'),
								atid:id
							},
						success : function(result) {
								if(result=="success"){
									$.messager.alert('操作提示','操作成功');
									$('#addActivity').dialog('close');
									selactivitiesList();//刷新活动模板列表
							}
							}
						});
				}
				}
		}
		
		var id=null;
		function updaterow(){
			var row = activities.datagrid('getSelected');
			if(row != null) { 
				$('#addActivity').dialog('open').dialog('setTitle', '编辑活动模板');
				$('#activityname').textbox('setValue',row.name);
				$('#codeFixation').textbox('setValue',row.codeFixation);
				$('#digit').numberbox('setValue',row.digit); 
				id=row.id;
				//加载产品表
				$('#activityPrize').datagrid({
				url : "../activityTemplate/selActivityPrize.do?atid="+id,
				title : '奖品列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				toolbar:"#toolActivity",
				onClickRow:onClickRow,
				fit:true,
				columns : [ [ 
					{
					field : 'name',
					title : '奖品名称',
					editor:{type:'textbox'},
					width : '50%',
					align : "center"
					},{
					field : 'amount',
					title : '数量',
					editor:{type:'textbox'},
					width : '50%',
					align : "center"
					}
				] ]
			});	          
			}else{
				$.messager.alert('操作提示','请选择要编辑的数据!'); 
				return false;  	
			}
		}
</script>
</html>

