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
	<table id="drInvestOrderList" title="投资订单 "
	style="height:99%;">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="4%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="5%" >手机号码</th>
	        <th data-options="field:'recommCodes'" width="5%" >推荐号</th>
	        <th data-options="field:'recomRealName'" width="5%">推荐人姓名</th>
	        <th data-options="field:'recomMobilePhone'" width="5%">推荐人号码</th>
	        <th data-options="field:'fullName'" width="8%">产品名称</th>
	        <th data-options="field:'investTime'" width="8%">订单时间</th>
	        <th data-options="field:'joinType'" width="4%">投资终端</th>
	        <th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	        <th data-options="field:'chanelName'" width="5%">注册渠道</th>
	        <th data-options="field:'regdate'" width="8%">注册日期</th>
	        <th data-options="field:'amount'" width="5%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor" formatter="formatAmount">预计收益</th>
	       	<th data-options="field:'repayTypeName'" width="8%" >还款方式</th>
	        <th data-options="field:'faAmount1'" width="5%" styler="styleColor" formatter="formatAmount">红包返现</th>
	        <th data-options="field:'multiple'" width="4%" styler="styleColor">翻倍倍数</th>
	        <th data-options="field:'raisedRates'" width="4%">加息利率</th>
	        <th data-options="field:'resultDate'" width="6%">计息日期</th>
	        <th data-options="field:'shouldTime'" width="6%">还款日期</th>
	        <th data-options="field:'statusName'" width="5%">投资状态</th>
	        <th data-options="field:'usedTime'" width="8%">活动兑换日期</th>
	        <th data-options="field:'faAmount2'" width="5%">活动金额</th>
	        <th data-options="field:'typeName'" width="4%">活动类型</th>
	        <th data-options="field:'faStatus'" width="4%">活动状态</th>
	        <th data-options="field:'options'" width="5%">专属理财师</th>
	        
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		产品名称:<input id="searchDrProductInvestFullName" name="fullName" class="easyui-textbox" value="" size="30" style="width:100px"/>
	  		用户手机:<input id="searchDrProductInvestMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
	  		<!-- 用户姓名:<input id="searchDrProductInvestRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/> -->
	  		还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
	  						<option value=''>全部</option>
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
	  		项目周期:<select name="deadline" style="width:150px" class="easyui-combobox" id="searchDrProductInvestDeadline">
			  			<option value="" selected="selected">全部</option>
			  			<option value="1">1</option>
			  			<option value="15">15</option>
			  			<option value="30">30</option>
			  			<option value="35">35</option>
			  			<option value="60">60</option>
			  			<option value="90">90</option>
			  			<option value="150">150</option>
			  			<option value="180">180</option>
	  				</select>
	  		订单日期:<input id="searchDrProductInvestStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  			  <input id="searchDrProductInvestEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		还款日期:<input id="searchDrProductInvestStartShouldTime" name="startShouldTime" class="easyui-datebox" style="width:100px"/>到
	  			  <input id="searchDrProductInvestEndShouldTime" name="endShouldTime" class="easyui-datebox" style="width:100px"/>
  		         注册日期:<input id="searchDrProductInvestStartRegDate" name="startRegDate" class="easyui-datebox" style="width:100px"/>到
  			  <input id="searchDrProductInvestEndRegDate" name="endRegDate" class="easyui-datebox" style="width:100px"/>
	  		渠道名称:<select class="easyui-combogrid" id="cid" name="cid" style="width:240px;padding-bottom: 3px;" data-options="
							panelWidth: 240,
							multiple: true,
							multiline:true,
							editable:false,
							idField: 'id',
							textField: 'name',
							url: '../channel/drAllChannelInfoList.do?orders=1',
							method: 'get',
							columns: [[
								{field:'id',checkbox:true},
								{field:'code',title:'渠道号',width:80},
								{field:'name',title:'渠道名称',width:80},
							]],
							fitColumns: true
						">
					</select>
	    	<a id="searchInvestOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInvestOrder()">查询</a>
	    	<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
	    	<a id="exportDrProductInvest" href="javascript:exportInvestOrder();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetBtn').click(function(){
		$("#drProductInvestForm").form("reset");
		$("#drInvestOrderList").datagrid("load", {});
	});
	//查询按钮
	function searchInvestOrder(){
		var cids = $('#cid').combogrid('getValues')+"";
 		$('#drInvestOrderList').datagrid({
 			url: '../investOrder/investOrderList.do',
			queryParams: {
				/* realname:$('#searchDrProductInvestRealname').val(), */
				fullName:$('#searchDrProductInvestFullName').val(),
				mobilephone:$('#searchDrProductInvestMobilephone').val(),
				startDate:$('#searchDrProductInvestStartDate').combobox('getValue'),
				endDate:$('#searchDrProductInvestEndDate').combobox('getValue'),
				startShouldTime:$('#searchDrProductInvestStartShouldTime').combobox('getValue'),
				endShouldTime:$('#searchDrProductInvestEndShouldTime').combobox('getValue'),
				startRegDate:$('#searchDrProductInvestStartRegDate').combobox('getValue'),
				endRegDate:$('#searchDrProductInvestEndRegDate').combobox('getValue'),
				cids:cids,
				deadline:$("#searchDrProductInvestDeadline").combobox("getValue"),
				repayType:$("#repayType").combobox("getValue")
			},singleSelect:true,
		    fitColumns:true,
		    method:'post',rownumbers:true,showFooter: true,
		    pagination:true,toolbar:'#drProductInvestTools'
		});
	};
	
	//导出
	function exportInvestOrder(){
		window.location.href="../investOrder/exportInvestOrderInfo.do?fullName="+encodeURIComponent(encodeURIComponent($('#searchDrProductInvestFullName').val()))+
						"&mobilephone="+$('#searchDrProductInvestMobilephone').val()+
						"&startDate="+$('#searchDrProductInvestStartDate').combobox('getValue')+
						"&endDate="+$('#searchDrProductInvestEndDate').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues')+
						"&startShouldTime="+$('#searchDrProductInvestStartShouldTime').combobox('getValue')+
						"&endShouldTime="+$('#searchDrProductInvestEndShouldTime').combobox('getValue')+
						"&startRegDate="+$('#searchDrProductInvestStartRegDate').combobox('getValue')+
						"&endRegDate="+$('#searchDrProductInvestEndRegDate').combobox('getValue')+
						"&deadline="+$('#searchDrProductInvestDeadline').combobox('getValue')+
						"&repayType="+$("#repayType").combobox("getValue");
	}

	//获取当前时间
	function getdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate 
	return currentdate; 
	} 
	
	//首次查询
	window.onload = function(){
		$('#searchDrProductInvestStartDate').datebox('setValue',getdate());
		$('#searchDrProductInvestEndDate').datebox('setValue',getdate());
		searchInvestOrder();
	}
	
</script>
</body>
</html>