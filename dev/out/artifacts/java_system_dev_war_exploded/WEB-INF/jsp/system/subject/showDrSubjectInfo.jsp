<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
</head>
<body>
<div id="${tabid }">
	<div title="标的信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px">
		<input id="drSubjectInfoLid" type="hidden" value="${drSubjectInfo.lid }" name="lid"/>
		<table align="center" cellspacing="1" cellpadding="1">
			<tr>
	   			<td>标的编号: </td>
	   			<td>
	   				<input value="${drSubjectInfo.code}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   			<td>债权编号：</td>
	   			<td>
	   				<input value="${drSubjectInfo.no}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   			<td>
	   				<a href="#" class="easyui-linkbutton" onclick="showDrSubjectInfoBtn();">查看</a>
				</td>
	   		</tr>
			<tr>
	   			<td>标的状态: </td>
	   			<td>
   					<select style="width: 175px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${status}" var="map">
							<option value='${map.key }' <c:if test="${drSubjectInfo.status == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
	   			</td>
	   			<td>标的类型：</td>
	   			<td>
   					<select style="width: 175px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${type}" var="map">
							<option value='${map.key }' <c:if test="${drSubjectInfo.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>标的金额(万元): </td>
	   			<td>
	   				<input value="${drSubjectInfo.amount}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   			<td>剩余金额(万元)：</td>
	   			<td>
	   				<input value="${drSubjectInfo.surplusAmount}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>起始日期: </td>
	   			<td>
	   				<input value="${subjectStartDate}" type="text" class="easyui-datebox" disabled="disabled"/>
	   			</td>
	   			<td>到期日期：</td>
	   			<td>
	   				<input value="${subjectEndDate}"  type="text" class="easyui-datebox" disabled="disabled"/>
	   			</td>
	   		</tr>
		</table>
	</div>
	<div title="审核详情" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px">
		<table style="width:auto;" align="center" >
			<c:forEach items="${drAuditInfo}" var="v" varStatus="i">
			<tr>
				<td align="left">审核人员：</td>
				<td>
					${v.name}
				</td>
				<td align="left">审核时间：</td>
				<td>
					<fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核结果：</td>
				<td colspan="3">
					<c:forEach items="${auditType}" var="map">
						<c:if test="${v.status == map.key}">${map.value }</c:if>
					</c:forEach>
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核意见：</td>
				<td colspan="3">
					<input class="easyui-textbox" data-options="multiline:true" value="${v.opinion}" style="height:123px;width: 500px;" disabled="disabled"/>
				</td>
	   	   	</tr>
	   	   	<c:if test="${drAuditInfo.size() != i.index+1}">
	   	   		<tr><td colspan="4"><div style="border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;"></div></td></tr>
	   	   	</c:if>
			</c:forEach>
		</table>
	</div>
<script type="text/javascript">
	//跳转到债权显示页面
	function showDrSubjectInfoBtn(index){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权详情",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toShowClaimsInfo.do?lid="+$("#drSubjectInfoLid").val()+"'></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	}
</script>
</div>
</body>
</html>
