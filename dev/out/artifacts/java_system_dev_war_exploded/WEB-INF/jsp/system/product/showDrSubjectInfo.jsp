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
		<table align="center" cellspacing="1" cellpadding="1">
			<tr>
	   			<td>借款编号: </td>
	   			<td>
	   				<input value="${drSubjectInfo.code}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   			<td>借款用途：</td>
	   			<td>
	   				<input value="${drSubjectInfo.loanUse}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   		</tr>
			<tr>
	   			<td>借款金额: </td>
	   			<td>
					<input value="${drSubjectInfo.loanAmount}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   			<td>借款期限：</td>
	   			<td>
					<input value="${drSubjectInfo.loanDeadline}" type="text" class="easyui-textbox" disabled="disabled"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>标的利率: </td>
	   			<td>
	   				<input value="${drSubjectInfo.rate}" type="text" class="easyui-textbox" disabled="disabled" data-options="min:0,max:20,precision:2"/>
	   			</td>
	   			<td>产品类型：</td>
	   			<td>
	   				<select style="width: 175px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${dateType}" var="map">
							<option value='${map.key }' <c:if test="${drSubjectInfo.dateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
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
	   		<tr>
	   			<td>还款方式: </td>
	   			<td>
	   				<select name="repayType" style="width: 175px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${repayType}" var="map">
							<option value='${map.key }' <c:if test="${drSubjectInfo.repayType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
	   			</td>
	   			<td>资金类型：</td>
	   			<td>
	   				<select style="width: 175px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${type}" var="map">
							<option value='${map.key }' <c:if test="${drSubjectInfo.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
	   			</td>
	   		</tr>
		</table>
	</div>
	<div title="债权图片" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
		<table style="width:auto;" align="center" >
			<tr>
				<td>
	                <div id="img" style="overflow-x: auto;width: ${drClaimsPic.size()*210}px;">
	            		<c:forEach items="${drClaimsPic}" var="v">
					       	<div style="text-align: center;display: inline-block;">
					        	<img src="${v.bigUrl}" width="200px" height="200px">
					        	<p>图片名称：${v.name}</p>
					        </div>
	  					</c:forEach>
				    </div>
				</td>
			</tr>
		</table>
	</div>
</div>	
<script type="text/javascript">
	        layer.photosPage({
	            id: 100, //相册id，可选
	            parent:'#img'
	        });
</script>
</body>
</html>
