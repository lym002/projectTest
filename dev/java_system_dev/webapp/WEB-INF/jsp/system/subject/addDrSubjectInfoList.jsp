<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="addDrSubjectInfoList" title="标的新增" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../subject/addDrSubjectInfoList.do',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#addDrSubjectInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'no'" width="20%">债权编号</th>
	        <th data-options="field:'name'" width="15%">债权名称</th>
	        <th data-options="field:'loanAmount'" width="20%" styler="styleColor" formatter="formatAmount">债权金额(元)</th>
	        <th data-options="field:'endDate'" width="15%" formatter="formatDateBoxFull">到期日期</th>
	        <th data-options="field:'statusName'" width="15%">当前状态</th>
			<th data-options="field:'operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="addDrSubjectInfoTools" style="padding:5px;height:750">
	  	<form id="addDrSubjectInfoForm">
	  		债权编号:<input id="searchAddDrSubjectInfoName" name="name" class="easyui-textbox"  size="15" style="width:200px"/>
	  		债权金额:<input id="searchAddDrSubjectInfoAmount" name="amount" class="easyui-textbox"  size="15" style="width:100px"/>
	  		到期日期:<input id="searchAddDrSubjectInfoDueStartDate" name="dueStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchAddDrSubjectInfoDueEndDate" name="dueEndDate" class="easyui-datebox" style="width:100px"/>
	  		当前状态: <select  id="searchAddDrSubjectInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 2 }">
							<option value='${map.key}'>${map.value }</option>
							</c:if>
						</c:forEach>
	           		</select>
	    	<a id="searchAddDrSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetAddDrSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetAddDrSubjectInfo').click(function(){
		$("#addDrSubjectInfoForm").form("reset");
		$("#addDrSubjectInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchAddDrSubjectInfo').click(function(){
 		$('#addDrSubjectInfoList').datagrid({
			queryParams: {
				name: $('#searchAddDrSubjectInfoName').val(),
				amount: $('#searchAddDrSubjectInfoAmount').val(),
				dueStartDate: $('#searchAddDrSubjectInfoDueStartDate').datebox('getValue'),
				dueEndDate: $('#searchAddDrSubjectInfoDueEndDate').datebox('getValue'),
				status: $('#searchAddDrSubjectInfoStatus').combobox('getValue'),
			}
		}); 
	});
	
	//操作
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="toAddDrSubjectInfoBtn('+index+')">审核</a>';
	} 
	
	//跳转到债权显示页面
	function toAddDrSubjectInfoBtn(index){
		$('#addDrSubjectInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#addDrSubjectInfoList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "标的审核",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/subject/toAuditDrSubjectInfo.do?lid="+row.id+"'></iframe>",
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
</body>
</html>

