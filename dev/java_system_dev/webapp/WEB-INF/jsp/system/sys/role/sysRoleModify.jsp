﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<style type="text/css">
.onediv{width:350px; height:300px;float:left;margin:0 0 0 5px;  }
.twodiv{width:200px; height:300px;float:left;margin:0 0 0 5px;  }
</style>
</head>
<body>
<div>
	<form id="updateRoleForm" >
		<div class="onediv">
	    	<table cellpadding="5">
	     		<tr>
	    			<td>角色名称:</td>
	    			<td>
	    				<input id="updateRoleName" name="roleName" value="${sysRoleVo.roleName }" class="easyui-textbox" type="text"  data-options="required:true"></input>
	    				<input id="updateRoleKy" name="roleKy" value="${sysRoleVo.roleKy }" readonly="readonly" type="hidden"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>角色编码:</td>
	    			<td>
	    				<input id="updateRoleCode" name="roleCode" value="${sysRoleVo.roleCode }" class="easyui-textbox" type="text"  data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>角色描述:</td>
	    			<td>
	    				<input value="${sysRoleVo.roleRemarks }" class="easyui-textbox" type="text" name="roleRemarks" data-options="required:true" ></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
	    				<input type="radio" name="status" value="1" <c:if test="${sysRoleVo.status eq 1 }">checked</c:if> ><span>正常</span>
	    				<input type="radio" name="status" value="0" <c:if test="${sysRoleVo.status eq 0 }">checked</c:if> ><span>禁用</span>
	    			</td>
	    		</tr>
			</table>
			<div style="text-align:center;">
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateRole()">修改</a>
	    		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeRoleWindow()">取消</a>
    		</div>
		</div>
	<div class="twodiv">
		<ul id="updateRoletree" class="easyui-tree" ></ul>
   	</div>
	</form>
</div>
<script type="text/javascript">
	//加载树
	$(function() {
		$("#updateRoletree").tree({  
	 		url:'../role/menuTree.do',  
	 		method: 'GET',  
	 		checkbox: true,
	 		   	cache:false,  
	    	cascadeCheck: false,  
	 		onLoadSuccess:function(node,data){  
	 			var roleKy = $("#updateRoleKy").val();
		  		//绑定权限  
				$.ajax({  
                       url:"../role/queryRoleMenuByRoleKy.do?roleKy="+roleKy,  
                        cache:false,  
                        dataType:'json',  
                        success:function(data){   
                        	//关闭所有节点
							//$("#updateRoletree").tree("collapseAll");
							//根据后台数据选中节点
                            for(var i=0;i<data.length;i++)  
                            {  
                            	var node = $('#updateRoletree').tree('find',data[i]); 
                              	$('#updateRoletree').tree('check',node.target);  
                              	//展开选中的节点
								//$('#updateRoletree').tree('expand', node.target);
                            }  
                       }  
                 }) ;
                
	 		},
	 		  onCheck: function (node, checked) {
	            if (checked) {
	                var parentNode = $("#updateRoletree").tree('getParent', node.target);
	                if (parentNode != null) {
	                    $("#updateRoletree").tree('check', parentNode.target);
	                }
	            } else {
                        var childNode = $("#updateRoletree").tree('getChildren', node.target);
                        if (childNode.length > 0) {
                            for (var i = 0; i < childNode.length; i++) {
                                $("#updateRoletree").tree('uncheck', childNode[i].target);
                            }
                        }
                    }
	        }
		}); 
	});
	
	//修改按钮
	 function updateRole(){ 
	  	var validate = $("#updateRoleForm").form("validate");
		if(!validate){
			return false;
		}
		 var selectedRightsList="";		
		 var node = $("#updateRoletree").tree('getChecked');
		 for(var i=0;i<node.length;i++)  
	     {  
	     	selectedRightsList += "&selectedRights=" + node[i].id;
	     }
		$(function(){
				var data = $("#updateRoleForm").serialize() + selectedRightsList;
				$.ajax({
				url:"../role/updateRoleMenu.do",
				type : "post",
				data : data,
				dataType:"json",
				success: function(d){
				var msg = d.message;
					if(msg == "ok"){
						$.messager.alert("操作提示", "修改成功！");
						$("#updateRole").window("close");
						$('#roleList').datagrid('reload');
						
					}else{
						$.messager.alert("操作提示", "修改失败，请重试！");
					}
					
				},
				error:function(args){
				}
			});
		});
	} 
	
	//清空按钮
	function closeRoleWindow(){
		$("#updateRole").window("close");
	}
</script>
</body>
</html>