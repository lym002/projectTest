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
	<table id="auditDrProductInfoList" title="产品审核" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../product/auditDrProductInfoList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#auditDrProductInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'code'" width="20%">产品编号</th>
	        <th data-options="field:'simpleName'" width="15%">产品名称</th>
	        <th data-options="field:'typeName'" width="15%">产品类型</th>
	        <th data-options="field:'amount'" width="15%" styler="styleColor" formatter="formatAmount">产品金额(元)</th>
	       	<th data-options="field:'deadline'" width="5%">产品期限(天)</th>
	       	<th data-options="field:'rate'" width="5%">产品利率(%)</th>
	        <th data-options="field:'statusName'" width="5%">产品状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
			<th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="auditDrProductInfoTools" style="padding:5px;height:750">
	  	<form id="auditDrProductInfoForm">
	  		产品编号:<input id="searchUpdateDrProductInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品简称:<input id="searchUpdateDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品状态: <select  id="searchUpdateDrProductInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value='100'>全部</option>
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 1 || map.key == 3}">
							<option value='${map.key }'>${map.value }</option>
							</c:if>
						</c:forEach>
	           		</select>
	    	<a id="searchAuditDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetAuditDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetAuditDrProductInfo').click(function(){
		$("#auditDrProductInfoForm").form("reset");
		$("#auditDrProductInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchAuditDrProductInfo').click(function(){
 		$('#auditDrProductInfoList').datagrid({
			queryParams: {
				code: $('#searchUpdateDrProductInfoCode').val(),
				simpleName: $('#searchUpdateDrProductInfoSimpleName').val(),
				status: $('#searchUpdateDrProductInfoStatus').combobox('getValue'),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status=="1" || row.status=="3"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toAuditProductInfoBtn('+index+')">审核</a>';
		}
	} 
	//跳转到产品审核页面
	function toAuditProductInfoBtn(index){
		$('#auditDrProductInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#auditDrProductInfoList').datagrid('getSelected'); 
	    $.ajax({
			url: "${apppath}/product/isOperate.do?id="+row.id+"&operate=audit",
			dataType:"json",
			success:function(result){
				if(result.success){
					var mainTab = parent.$('#main-center');
					var detailTab = {
							title : "产品管理-审核",
							content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toAuditDrProductInfo.do?id="+row.id+"'></iframe>",
							closable : true
					};
					mainTab.tabs("add",detailTab);
				}else{
					$.messager.alert("提示信息",result.errorMsg,function(){
						$('#drProductInfoList').datagrid('reload');
					});
				}
			}
 		});
	}
	
</script>
</body>
</html>

