<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
</head>
<body>
<div style="width:auto;height:auto;padding:10px;">
	<form id="updateJsPReservationForm" >
	    	<table align="center">
				<input id="updateId" name="id" value="${jsPReservation.id}" readonly="readonly" type="hidden"/>
				<tr>
					<td style="width:100px">项目名称:</td>
					<td colspan="3">
						<input type="text" name="name" class="easyui-textbox" value="${jsPReservation.name}" style="width:250px" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr>
				<tr>
					<td>预约期数:</td>
					<td colspan="3">
						<input type="text" name="periods" class="easyui-numberbox" value="${jsPReservation.periods}" readonly="readonly" style="width:250px" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr>
				<tr>
					<td>预约时间：</td>
					<td>
						<input id="updateJsReservationStartTime" name="startTime" value="<fmt:formatDate value="${jsPReservation.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"  class="easyui-datetimebox"style="width:150px" data-options="required:true"/>
					</td>
					<td>至</td>
					<td>
	  		       		<input id="updateJsReservationEndTime" name="endTime" value="<fmt:formatDate value="${jsPReservation.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />" class="easyui-datetimebox" style="width:150px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr> 
				<tr>
					<td>预约状态:</td>
					<td colspan="3">
						<select id="updateStatus" name="status" style="width:250px" class="easyui-combobox" data-options="required:true">
							<option value="0" <c:if test="${jsPReservation.status==0}">selected="selected"</c:if>>待开启</option>
							<option value="1" <c:if test="${jsPReservation.status==1}">selected="selected"</c:if>>开    启</option>
							<option value="2" <c:if test="${jsPReservation.status==2}">selected="selected"</c:if>>关    闭</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4">
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:20px;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="JsReservationUpdate()">确认修改</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeJsReservationWindow()">取消</a>
	    </div>
</div>
<script type="text/javascript">
	
	//关闭窗口
	function closeJsReservationWindow(){
		$("#updateJsPReservationWindow").window("close");
	}
	
	//提交
	function JsReservationUpdate(){
		var validate = $("#updateJsPReservationForm").form("validate");
		if(!validate){
			return false;
		}
		
		$.ajax({
			url:"${apppath}/preservation/modifyJsPReservation.do",
			type:"POST",
			data:$("#updateJsPReservationForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateJsPReservationWindow").window("close");
					$("#updateJsPReservationForm").form("clear");
					$("#jsProductReservationList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
		
	}
</script>
</body>
</html>