<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<title>币优铺理财 - 慧信财富管理有限公司</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="内容描述">
	<meta name="author" content="作者">
	<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript">
	var status = [{"value":"1","text":"可用"},{"value":"0","text":"禁用"}];
	$(function(){
			$("#${tabid } #tg").treegrid({
				url:"../menu/menuList.do",
				idField:"menuKy",
				treeField:"name", 
				collapsible: true,
				columns:[[
				  {title:'编号',field:'menuKy',width:$(this).width() * 0.05,align:'right'},
		          {title:'菜单名称',field:'name',width:$(this).width() * 0.2,align:'center',editor:"text"},
		          {title:'URL地址',field:'url',width:$(this).width() * 0.2,align:'center',editor:"text"},
		          {title:'菜单级别',field:'grade',width:$(this).width() * 0.1,align:'center',editor:"numberbox"},
		          {title:'父菜单编号',field:'parent',width:$(this).width() * 0.05,align:'center',editor:"text"},
		          {title:'位置序号',field:'position',width:$(this).width() * 0.05,align:'center',editor:"text"},
		          {title:'所属系统',field:'itsSystem',width:$(this).width() * 0.05,align:'center',editor:{type:'combobox',options: {data:itsSystem,textField:'text',valueField:'value' }},
		        	  formatter:function(value,rec){
		        	  		if(rec.itsSystem=='0'){
		        	  			return "币优铺后台";
		        	  		}
		        	  		else if(rec.itsSystem == '1'){
		        	  			return "报表系统";
		        	  		}
						} },
		          {title:'状态',field:'status',width:$(this).width() * 0.2,align:'left',editor:{type:'combobox',options: {data:status,textField:'text',valueField:'value' }},
		        	  formatter:function(value,rec){
		        	  		if(rec.status=='0'){
		        	  			return "禁用";
		        	  		}else{
		        	  			return "可用";
		        	  		}
						}  
		          }
				]]
			});
			
		});
</script>
</head>
<body>
<div id="${tabid }">
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#window').window('open');">菜单录入</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">取消</a>
	</div>
	<table id="tg" class="easyui-treegrid" style="height: 500px;"></table>
	
	<div id="window" class="easyui-window" title="菜单录入" 
		data-options="iconCls:'icon-add',closed:true,minimizable:false,resizable:false,maximizable:false" style="width:700px;height:400px;padding:10px;">
		<div style="padding:10px 60px 20px 60px">
		<form:form id="addform" action="../menu/addMenu.do" modelAttribute="sysMenu" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>菜单名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>URL地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="url"></input></td>
	    		</tr>
	    		<tr>
	    			<td>菜单级别:</td>
	    			<td>
	    				<select name="grade">
	    					<option value="1">一级菜单</option>
	    					<option value="2">二级菜单</option>
	    					<option value="3">三级菜单</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>父菜单编号:</td>
	    			<td>
						<input class="easyui-textbox" type="text" name="parent" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>所属系统:</td>
	    			<td>
			        	<select  id="itsSystem" name="itsSystem" style="width:100px;" class="easyui-combobox">
						<c:forEach var="map" items="${itsSystem}">
						<option value='${map.key}'>${map.value}</option>
			       		</c:forEach>
	          			</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
	    				<select name="status">
	    					<option value="1">可用</option>
	    					<option value="0">禁用</option>
	    				</select>
	    			</td>
	    		</tr>
			</table>
		</form:form>
		 <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">添加</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">清空</a>
	    </div>
		</div>
	</div>
	
	<script type="text/javascript">
		var editingId;
		
		function edit(){
			if (editingId != undefined){
				$("#${tabid } #tg").treegrid('select', editingId);
				return;
			}
			var row = $("#${tabid } #tg").treegrid('getSelected');
			if (row){
				editingId = row.menuKy;
				$('#tg').treegrid('beginEdit', editingId);
			}else{
				alert("请选择要修改的行");
			}
		}
		function save(){
			if (editingId != undefined){
				var t = $("#${tabid } #tg");
				var update = t.treegrid('endEdit', editingId);
				
				var rows = eval(t.treegrid('getSelected'));
				if(rows.menuKy==editingId){
					$.ajax({
						url: "../menu/updateMenu.do",
						type: "POST",
						data : {"str":JSON.stringify(rows)},
						success : function(result){
							if(result == "success"){
								alert("修改成功");
							}else{
								alert("修改失败");
							}
						}
					});
				}
				editingId = undefined;

			}
		}
		function cancel(){
			if (editingId != undefined){
				$("#${tabid } #tg").treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}
		function submitForm(){
			if($("#addform").form('enableValidation').form('validate')){
				$.ajax({
					url:"${apppath}/menu/addMenu.do",
					type:"POST",
					data:$("#addform").serialize(),
					async:false,
					success:function(data){
						$.messager.alert("提示信息","添加成功！");
					}
				});
				$("#addform").form('clear');
				$("#window").window('close');
				$("#${tabid } #tg").treegrid("reload");
			}
		}
		function clearForm(){
			$("#addform").form('clear');
		}
		
	</script>
</div>
</body>
</html>