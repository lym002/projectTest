<%@ include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>币优铺理财 - 币优铺理财信息数据管理中心</title>
	<%@ include file="../../../common/include/util.jsp" %>
</head>
<script type="text/javascript">
        var userKy='${userKy}';
   		function updateCall(){
   			var validate = $("#updateForm").form("validate");
			if(!validate){
				return false;
			}
			$.ajax({
				type: 'post',
				url : "../user/updateCallNum.do",
				data : {
					userKy : userKy,
					phone : $('#updatephone').textbox('getValue')
				},
				success : function(message) {
					if (message=='success') { 	
						$.messager.alert('操作提示','操作成功');
						$('#phone').textbox('setValue',$('#updatephone').textbox('getValue'));
						$('#updatephone').textbox('setValue',"");
					}  
					else {  
						$.messager.alert('操作提示','操作失败');
						return false;  
					} 
			    }
			 });
   		}
</script>
</head>
<body>
<div>
	<form id="updateForm">
		<table align="center">
			<tr>
				<td align="right">
					登录名:
				</td>
				<td>	
					<input id="loginId" name="loginId" value='${login_id}' disabled=true class="easyui-textbox"  />
				</td>
			</tr>
			<tr>
				<td align="right"> 
					工号:
				</td>
				<td>		
					<input id="num" class="easyui-textbox" value='${num}' disabled=true />
				</td>
			</tr>
			<tr>
				<td align="right">
					原分机号:
				</td>
				<td>		
					<input id="phone" class="easyui-textbox" value='${phone}' disabled=true />
				</td>
			</tr>
			<tr>
				<td align="right">
					修改分机号:
				</td>
				<td>		
					<input id="updatephone" class="easyui-textbox"  data-options="required:true" />
				</td>
			</tr>
		</table>
	</form>
	<center>
	<div id="updateCallNum">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateCall()">修改</a>
	</div>
	</center>
</div>
</body>
</html>


