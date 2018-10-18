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
	<!-- 新增APP更新 -->
	<div style="width:auto;height:auto;padding:10px;">
		<form id="updateAppRenewalForm">
			<input id="updateId" name="id" value="${sar.id}" hidden="true"/>
			<table align="center">
				<tr>
					<td style="width:100px">最低版本号:</td>
					<td>
						<input id="addVersion" name="version" style="width:150px" class="easyui-textbox" value="${sar.version}">
					</td>
					<td style="width:100px">发布版本号:</td>
					<td>
						<input id="addReleaseVersion" name="releaseVersion" style="width:150px" class="easyui-textbox" value="${sar.releaseVersion}">
					</td>
				</tr>
				<tr>
					<td style="width:100px">是否强制更新</td>
    				<td>
    					<select id="addIsForceUpdate" name="isForceUpdate" style="width:150px;" class="easyui-combobox" data-options="required:true" >
							<option value='0' <c:if test="${sar.isForceUpdate==0}">selected="selected"</c:if>>非强制更新</option>
							<option value='1' <c:if test="${sar.isForceUpdate==1}">selected="selected"</c:if>>强制更新</option>
						</select>
    				</td>
    				<td>系统</td>
    				<td>
    					<select id="addContainers" name="containers" style="width:150px;" class="easyui-combobox" data-options="required:true" disabled="disabled">
							<option value='1' <c:if test="${sar.containers==1}">selected="selected"</c:if>>IOS</option>
							<option value='2' <c:if test="${sar.containers==2}">selected="selected"</c:if>>Android</option>
						</select>
    				</td>
				</tr>
				<tr>
					<td>正文内容:<br/>(换行使用^<br/>符号)</td>
					<td colspan="3">
						<input id="addContent" class="easyui-textbox" value="${sar.content }" name="content" style="height:100px;width:400px;" data-options="required:true,multiline:true,validType:'maxLength[500]'" />
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3">
						<input id="addRemark" class="easyui-textbox" value="${sar.remark }" name="remark" style="height:100px;width:400px;" data-options="multiline:true,validType:'maxLength[255]'"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="showAppRenewalBtn"  style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateAppRenewal()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">关闭</a>
		</div>
	</div>

<script type="text/javascript">
	function cancel(){
		$("#updateAppRenewalWindow").window("close");
	}
	
	function updateAppRenewal(){
		var validate = $("#updateAppRenewalForm").form("validate");
		if(!validate){
			return false;
		}
		var isForceUpdate = $('#addIsForceUpdate').combobox('getValue');
		var version = $('#addVersion').val();
		if(isForceUpdate==0 && version==''){
			$.messager.alert("提示信息","非强制更新请填写版本号");
			return false;
		}
		$.ajax({
          	url: "${apppath}/app/updateAppRenewal.do",
          	type: 'POST',
            data:$("#updateAppRenewalForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateAppRenewalWindow").window("close");
					$("#updateAppRenewalForm").form("clear");
					$("#appRenewalList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
</script>	
</body>
</html>