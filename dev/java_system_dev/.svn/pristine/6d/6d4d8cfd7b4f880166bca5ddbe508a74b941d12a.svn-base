<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
	<div id="userbtn">
		<form id="seluser">
			姓名: <input class="easyui-textbox" name="name" id="name" style="width:150px">
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="seluser()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetuser()">重置</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="adduser()">绑定</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteuser()">解绑</a>
	    </form>
	</div>
	<div style="height:100%;width:100%;">
		<table id="user"  style="height:99%;width:99.9%"></table>
	</div>
	<div id="adduser" class="easyui-dialog" style="width:400px;height:150px"   
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addInvestForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<table>
				<tr><td>坐席号:<input class="easyui-textbox" name="num" id="num" style="width:150px"></td></tr>
				<tr><td>分机号:<input class="easyui-textbox" name="phone" id="phone" style="width:150px"></td></tr>			
				</table>
				</center>
			</div>  
		</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#adduser').dialog('close')">取消</a>
			</div>
		</div>
<script type="text/javascript">
	    
		//电销用户列表
		var user = $('#user');
		$(function() {
			user.datagrid({
				url : "../user/selectUserCallNum.do",
				title : '用户列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				sortName:'mobile',  
                sortOrder: 'desc',  
                remoteSort:'false',  
				toolbar:"#userbtn",
				fit:true,
				columns : [ [ 
					{
					field : 'user_ky',
					title : 'user_ky',
					align : "center",
					hidden:true
					},{
					field : 'name',
					title : '姓名',
					width : '10%',
					sortable:'true',
					align : "center"
					},{
					field : 'mobile',
					title : '手机号',
					width : '10%',
					sortable:'true',
					align : "center"	
					},{
					field : 'num',
					title : '坐席号',
					width : '10%',
					sortable:'true',
					align : "center"	
					},{
					field : 'phone',
					title : '分机号',
					sortable:'true',
					width : '10%',
					align : "center"
					}
				] ]
			});
		}); 
		
		$("#user").datagrid({
			 onSortColumn: function (sort, order) {
			            user.datagrid('reload',{
			            	sort: sort,
			            	order: order
			            });
			        }
			})
		
		//查询按钮
		function seluser(){
	 		$('#user').datagrid({
				queryParams: {
					name:$('#name').val()
					}
			})
		}
		
		//重置按钮
		function resetuser(){
			$("#seluser").form("reset");
			$("#user").datagrid("load", {});
		};	
		
		//解绑
		function deleteuser(){
			var row = user.datagrid('getSelected');
			if(row != null) { 
				if(row.phone!=null && row.phone!=''){
					 $.messager.confirm('操作提示', '确定删除绑定吗！', function (data) {		
						 	if(data){
						 		$.ajax({
									type: 'post',
									url : "../user/deleteUserCallNum.do",
									data : {
										userKy : row.userKy
									},
									success : function(message) {
										if (message=='success') { 	
											$.messager.alert('操作提示','操作成功');
											seluser();
										}  
										else {  
											$.messager.alert('操作提示','操作失败');
											return false;  
										} 
								    }
								 });
						 	}
					 })
				}else{
					$.messager.alert('操作提示','只能解绑已绑定过的用户!'); 
					return false;  	
				}
			}else{
				$.messager.alert('操作提示','请选择要解绑的用户!'); 
				return false;  	
			}
		}	
		
		//绑定
		function adduser(){
			$('#num').textbox("setValue","");
			$('#phone').textbox("setValue","");
			var row = user.datagrid('getSelected');
			if(row != null) { 
				if(row.phone==null){
					$('#adduser').dialog('open').dialog('setTitle', '绑定');
				}else{
					$.messager.alert('操作提示','只能绑定未绑定过的用户!'); 
					return false;  	
				}
			}else{
				$.messager.alert('操作提示','请选择要绑定的用户!'); 
				return false;  	
			}
		}	
		
		//绑定提交
		function save(){
			if($("#num").textbox('getValue')==null){
				$.messager.alert("系统提示","请填写坐席号");
		        return false;  
			}
			if($("#phone").textbox('getValue')==null){
				$.messager.alert("系统提示","请填写分机号");
		        return false;  
			}
			var row = user.datagrid('getSelected');
			$.ajax({
				type: 'post',
				url : "../user/addCallNum.do",
				data : {
					userKy : row.userKy,
					num : $("#num").textbox('getValue'),
					phone : $("#phone").textbox('getValue')
				},
				success : function(message) {
					if (message=='success') { 	
						$.messager.alert('操作提示','操作成功');
						$('#adduser').dialog('close');
						seluser();
					}else {  
						$.messager.alert('操作提示','改用户已绑定分机');
						return false;  
					} 
			    }
			 });
		}	
</script>
</body>
</html>

