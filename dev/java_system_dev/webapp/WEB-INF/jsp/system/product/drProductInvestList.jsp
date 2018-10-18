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
	<table id="drProductInvestList" title="投资记录" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../invest/drProductInvestList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drProductInvestTools',onBeforeLoad:init">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="10%">客户姓名</th>
	        <th data-options="field:'recommCodes'" width="10%">推荐码</th>
	        <th data-options="field:'mobilephone'" width="10%" >账号</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'repayTypeName'" width="10%">还款方式</th>
	        <th data-options="field:'typeName'" width="10%">投资项目</th>
	        <th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	        <th data-options="field:'rate'" width="10%">项目预期年化率</th>
	        <th data-options="field:'investTime'" width="12%" formatter="formatDateBoxFull">订单日期</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		<input id="searchDrProductScode" name="scode" value="${scode}" type="hidden"/>
	  		产品编号:<input id="searchDrProductInvestCode" name="code" class="easyui-textbox" value="${code}" size="30" style="width:180px"/>
<!-- 	  		客户姓名:<input id="searchDrProductInvestRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/> -->
	  		推荐码:<input id="searchDrProductInvestRecommCodes" name="recommCodes" class="easyui-textbox"  size="30" style="width:100px"/>
	  		手机号:<input id="searchDrProductInvestMobile" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
  			还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
  						<option value=''>全部</option>
						<c:forEach items="${repayType}" var="map">
							<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
							<option value='${map.key }'>${map.value }</option>
							</c:if>
						</c:forEach>
					</select>
	  		订单日期:<input id="searchDrProductInvestStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductInvestEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	    	<a id="searchDrProductInvest" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductInvest" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	var code = '${code}';
	var scode='${scode}';
	function init(param){
		param.scode = scode;
		param.code = code;
	}

	//重置按钮
	$('#resetDrProductInvest').click(function(){
		$("#drProductInvestForm").form("reset");
		$('#searchDrProductScode').textbox('setValue','');
		$('#searchDrProductInvestCode').textbox('setValue','');
		scode='';
		code='';
		$("#drProductInvestList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrProductInvest').click(function(){
		code=$('#searchDrProductInvestCode').val();
 		$('#drProductInvestList').datagrid({
			queryParams: {
				scode:'',
				realname:$('#searchDrProductInvestRealname').val(),
				recommCodes:$('#searchDrProductInvestRecommCodes').val(),
				mobilephone:$('#searchDrProductInvestMobile').val(),
				code:$('#searchDrProductInvestCode').val(),
				startDate:$('#searchDrProductInvestStartDate').datebox('getValue'),
				endDate:$('#searchDrProductInvestEndDate').datebox('getValue'),
				repayType:$('#repayType').combobox('getValue'),
			}
		}); 
	});

</script>
</body>
</html>