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
	<div id="addbtn" style="padding:5px;height:750">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">新增关联</a>
	</div>
	<!-- ------------------奖励关联列表 --------------------------->
	<table id="rewardtable" class="easyui-datagrid" style="height:99%;width:99.9%"></table>
	
	<!-- -----------------新增奖励关联------------------------------->
	<div id="addreward" class="easyui-dialog" style="height:65%;width:40%" closed="true"	buttons="addrewardBtn"  data-options="resizable:true,modal:true,closed:true">
		<div style="padding:5px;height:750">
			关联产品:<input id="product" name="product" style="width: 172px" class="easyui-combobox"/>
			奖励类型:<select id="type" name="type" style="width: 172px" class="easyui-combobox">
							<option value=''>  </option>
							<option value='1'>红包</option>
							<option value='2'>加息券</option>
						</select>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="select()">查询</a>
		</div>
		<div style="padding:5px;height:750">
			<table id="rewardlist" class="easyui-datagrid" style="height:400px;width:99.9%"></table>
		</div>
		<center>
		<div id="addrewardBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">生成</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addreward').dialog('close')">取消</a>
		</div>
		</center>
	</div>
</body>
<script type="text/javascript"> 
	//奖品关联列表
	var rewardtable = $('#rewardtable');
	
	$(function() {
		rewardtable.datagrid({
			url : "../productReward/selProductReward.do",
			title : '奖品关联列表',
			fitColumns : true,
			pagination : true,
			pageSize:20,
			singleSelect:true,
			pageList:[20,50],
			autoRowHeight : false,
			rownumbers:true, 
			toolbar:"#addbtn",
			fit:true,
			columns : [ [ 
				{
				field : 'id',
				title : 'id',
				align : "center",
				hidden:true
				},{
				field : 'fullName',
				title : '产品名称',
				width : '40%',
				align : "center"
				},{
				field : 'name',
				title : '关联奖励',
				width : '40%',
				align : "center"
				},{
				field : ' ',
				title : '操作',
				width : '20%',
				formatter:function(value,row,index){
					return "<a href='#' class='easyui-linkbutton' onclick=\"delReward(\'"+row.id+"')\">删除</a>";
				},
				align : "center"
				}
			] ]
		});
	}); 
	
	function add(){
		$('#addreward').dialog('open').dialog('setTitle', '新增奖励关联');
		$('#product').combobox('setValue','');
		$('#type').combobox('setValue','');
		$("#product").combobox({
            url : "../productReward/SelProductList.do",//返回json数据的url
            valueField : "id",//这个id和你返回json里面的id对应
            textField : "fullName" //这个text和你返回json里面的text对应
        }),
		rewardlist();
		select();
	}
	
	//奖励列表
	function rewardlist(){
		$('#rewardlist').datagrid({
			url : "../productReward/selParameterList.do",
			//title : '奖品关联列表',
			toolbar:"#addBtn",
			fit:true,
			checkbox:true,
			columns : [ [ {
				field:'ck',
				checkbox:true
				},{
				field : 'apid',
				title : 'id',
				align : "center",
				hidden:true
				},{
				field : 'name',
				title : '奖励名称',
				width : '50%',
				align : "center"
				},{
				field : 'typeName',
				title : '奖励类型',
				width : '50%',
				align : "center"
				}
			] ]
		});
	}
	
	//查询按钮
	function select(){
		$('#rewardlist').datagrid({
			queryParams: {
				type:$('#type').combobox('getValue')
				}
		})
	}
	
	//保存
	function save(){
		var rewardData = $('#rewardlist').datagrid('getChecked');

		if($('#product').combobox('getValue')==""){
			$.messager.alert('系统提示','请选择关联产品！');
			return false;
		}
		if(rewardData!=null && rewardData!=""){
			 $.messager.confirm('操作提示', '确定生成关联吗？', function (data) {		
				 	if(data){
				 		$.ajax({
							type: 'post',
							url : "../productReward/insertProductReward.do",
							cache : false,
							data : {
								conData:JSON.stringify(rewardData),
								id:$('#product').combobox('getValue')
							},
							cache : false,
							async : false,
							success : function(message) {
								if (message=='success') { 	
									$.messager.alert('操作提示','操作成功');
									$('#addreward').dialog('close');
									$('#rewardtable').datagrid("load",{});
								}  
								else {  
									$.messager.alert('操作提示','操作失败');
									return false;  
								} 
						    },  
						     error : function(message) {
							 $.messager.alert('操作提示','操作失败');
							 return false; 
						}
					});
				 	}
				 })
		}else{
			$.messager.alert('系统提示','请选择奖励数据！');
			return false;
		}
	}
	
	
	function delReward(id){
		 $.messager.confirm('操作提示', '确定删除关联吗？删除后将不再关联该产品！', function (data) {		
			 	if(data){
			 		$.ajax({
						type: 'post',
						url : "../productReward/deleteProductReward.do",
						cache : false,
						data : {
							id:id
						},
						cache : false,
						async : false,
						success : function(message) {
							if (message=='success') { 	
								$.messager.alert('操作提示','删除成功');
								$('#addreward').dialog('close');
								$('#rewardtable').datagrid("load",{});
							}  
							else {  
								$.messager.alert('操作提示','删除失败');
								return false;  
							} 
					    },  
					     error : function(message) {
						 $.messager.alert('操作提示','删除失败');
						 return false; 
					}
					 });
			 	}
		})
	}
</script>
</html>

