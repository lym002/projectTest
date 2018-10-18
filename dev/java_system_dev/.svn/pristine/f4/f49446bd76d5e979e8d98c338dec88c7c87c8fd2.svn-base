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
    function updateUserPassword(){
		var validate = $("#updateUserPasswordForm").form("validate");
			if(!validate){
				return false;
		}
		var oldPassword = $("#oldPassword").val();
		var password = $("#password").val();
		var spassword = $("#spassword").val();
		//密码为八位及以上并且字母数字特殊字符三项都包括
	      var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");;
	    
	     var enoughRegex = new RegExp("(?=.{6,}).*", "g");
	     if (false == enoughRegex.test(password)) {
	         $('#passstrength').html('<font color="red">长度不够！</font>');
	         return false;
	     } else if (strongRegex.test(password)) {
	         $('#passstrength').className = 'ok';
	     }else{
	    	 $('#passstrength').html('<font color="red">请输入大写字母、小写字母数字特殊字符三项都包括的密码！</font>');
	    	 return false;
	     }
		if(password != spassword){
			$.messager.alert("提示信息","两次密码不一样！");
			return false;
		}
		if(oldPassword == spassword && oldPassword == password){
			$.messager.alert("提示信息","原密码不能跟新密码一样！");
			return false;
		}
		$.ajax({
			url:"${apppath}/user/updateUserPassword.do",
			type:"POST",
			data:$("#updateUserPasswordForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg,"",function(){
					window.location.href ="../user/toLoginUser.do";
					});
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				};
			}
		});
    } 
    
     		
</script>
</head>
<body>
<div id="updateUserPasswordDialog" class="easyui-dialog" title="修改密码"
		data-options="iconCls:'icon-edit',closed:false,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateUserPassword'" style="width:340px;height:200px;padding:5px;">
	<form id="updateUserPasswordForm">
		<table align="center">
			<tr>
				<td align="right">
					请输入原密码:
				</td>
				<td>	
					<input id="oldPassword" name="oldPassword" class="easyui-textbox" data-options="required:true" type="password"/>
				</td>
			</tr>
			<tr>
				<td align="right"> 
					新密码:
				</td>
				<td>		
					<input id="password" class="easyui-textbox" name="password" data-options="required:true" validType="length[8,18]" type="password"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					请再次输入密码:
				</td>
				<td>		
					<input id="spassword" class="easyui-textbox" data-options="required:true" validType="length[8,18]" type="password"/>
				</td>
			</tr>
			
		</table>
		<span id="passstrength" ></span>
	</form>
	<div id="updateUserPassword">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateUserPassword()">修改</a>
	</div>
</div>
</body>
</html>


