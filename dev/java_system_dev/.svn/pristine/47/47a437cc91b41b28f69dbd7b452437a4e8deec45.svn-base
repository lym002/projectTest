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
<div style="width:auto;height:380px;padding:1px;">
    	<form id="updateSettings">
    		<div style="width:auto;height:110px">
	    		<table>
	    			<tr>
	    				<td style="width:80px">奖励标准:</td>
	    				<td>
	    					<select name="norm" style="width: 150px" class="easyui-combobox" disabled="disabled">
								<c:forEach items="${norm}" var="map">
									<option value='${map.key }' <c:if test="${settings.norm== map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:forEach>
							</select>
	    				</td>
	    				<td style="width:80px">累计周期:</td>
	    				<td >
	    					<input id="period" name="period" disabled="disabled" class="easyui-numberbox" style="width: 130px" value='${settings.period}' <c:if test="${settings.norm== 1}">disabled="disabled"</c:if>/>天
	    				</td>
	    			</tr>
	    	
	    			<tr>
	    				<td>奖励形式 :</td>
	    				<td>
	    					<select name="modality" style="width: 150px" class="easyui-combobox" disabled="disabled">
								<c:forEach items="${modality}" var="map">
									<option value='${map.key }' <c:if test="${settings.modality== map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:forEach>
							</select>
	    				</td>
	    				<td>奖励基准 :</td>
	    				<td>
	    					<select name="standard" style="width: 150px" class="easyui-combobox" disabled="disabled">
								<c:forEach items="${standard}" var="map">
									<option value='${map.key }' <c:if test="${settings.standard== map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:forEach>
							</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>适用产品</td>
	    				<td colspan="3">
	    					<c:forEach items="${productType}" var="map">
	    						<input value="${map.key}" name="products" disabled="disabled" type="checkbox" <c:if test="${settings.products.contains(map.key)}">checked="checked"</c:if>/>${map.value}
	    					</c:forEach>
	    				<td>
	    			</tr>
	    			<tr>
	    				<td>开始时间:</td>
	    				<td>
	    					<input id="updateStartTime" name="startTime" style="width: 150px" disabled="disabled" class="easyui-datebox" value="<fmt:formatDate value="${settings.startTime}" pattern="yyyy-MM-dd" />"/>
	    				</td>
	    				<td>结束时间:</td>
	    				<td>
	    					<input id="updateEndTime" name="endTime" style="width: 150px" disabled="disabled" class="easyui-datebox" value="<fmt:formatDate value="${settings.endTime}" pattern="yyyy-MM-dd" />">
	    				</td>
	    			</tr>
	    		</table>
    		</div>
    		<div style="width:auto;height:280px">
    		    <table id="dg" title="好友推荐设置详情" class="easyui-datagrid"
					style="height:99%;width:99%"
					data-options="singleSelect:true,
				    fitColumns:true, url: '../recommendedSettings/settingsDetailsList.do?rid=${settings.id}',
				    method:'post',rownumbers:true,pagination:false">
					<thead>
						<tr>
							<th data-options="field:'id'" hidden="hidden">ID</th>
							<th data-options="field:'rid'" hidden="hidden">rid</th>
							<th data-options="field:'amount'" formatter="formatAmount" width="45%" align="center">金额</th>
							<th data-options="field:'reward'" width="45%" formatter="formatAmount" align="center">奖励</th>
						</tr>
					</thead>
				</table>   
    		</div>
    	</form>
    </div>
</body>
</html>
