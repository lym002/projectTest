﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
<div id="${tabid }">
	<div title="产品信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">产品编号：</td>
				<td colspan="2">
					<input name="code" value="${drProductInfo.code}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td align="left">产品全称：</td>
				<td colspan="2">
					<input name="fullName" value="${drProductInfo.fullName}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
				<td align="left">产品简称：</td>
				<td colspan="2">
					<input name="simpleName" value="${drProductInfo.simpleName}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td align="left">产品类型：</td>
				<td colspan="2">
					<select name="type" style="width: 172px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${type}" var="map">
							<option value='${map.key }' <c:if test="${drProductInfo.type== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left">年化利率：</td>
				<td colspan="2">
					<input name="rate" value="${drProductInfo.rate}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0,max:20,precision:2"/>%
				</td>
				<td align="left">活动利率：</td>
				<td colspan="2">
					<input name="activityRate" value="${drProductInfo.activityRate}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0,max:20,precision:2"/>%
				</td>
			</tr>
			<tr>
				<td align="left">还款方式：</td>
				<td colspan="2">
					<select name="repayType" style="width: 172px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${repayType}" var="map">
							<c:if test="${map.key == drProductInfo.repayType}">
							<option value='${map.key }' <c:if test="${drProductInfo.repayType== map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td align="left">产品期限：</td>
				<td colspan="2">
					<input name="deadline" value="${drProductInfo.deadline}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0"/>天
				</td>
			</tr>
		</table>
	</div>
</div>	
</body>
</html>
