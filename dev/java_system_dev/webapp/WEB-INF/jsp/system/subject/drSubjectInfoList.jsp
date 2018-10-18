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
	<table id="drSubjectInfoList" title="标的查询" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../subject/drSubjectInfoList.do',
	    method:'post',rownumbers:true,remoteSort:true,
	    pagination:true,toolbar:'#drSubjectInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	        <th data-options="field:'code'" width="15%">标的编号</th>
	    	<th data-options="field:'no'" width="15%">债权编号</th>
	    	<th data-options="field:'companyName'" width="15%">公司名称</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">标的金额(元)</th>
	       	<th data-options="field:'surplusAmount'" width="10%" styler="styleColor" formatter="formatAmount" sortable="true">剩余金额(元)</th>
	        <th data-options="field:'endDate'" width="20%" formatter="formatDateBoxFull">到期日期</th>
	        <th data-options="field:'statusName'" width="15%">标的状态</th>
			<th data-options="field:'operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drSubjectInfoTools" style="padding:5px;height:750">
	  	<form id="drSubjectInfoForm">
	  		标的编号:<input id="searchDrSubjectInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		债权编号:<input id="searchDrSubjectInfoNo" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		标的金额:<input id="searchDrSubjectInfoAmount" name="amount" class="easyui-textbox"  size="15" style="width:100px"/>
	  		剩余金额:<input id="searchDrSubjectInfoSurplusAmount" name="surplusAmount" class="easyui-textbox"  size="15" style="width:100px"/>
	  		到期日期:<input id="searchDrSubjectInfoStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrSubjectInfoEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		标的状态: <select  id="searchDrSubjectInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
	  					<option value=''>全部</option>
						<c:forEach items="${status}" var="map">
							<option value='${map.key}'>${map.value }</option>
						</c:forEach>
	           		</select>
	    	<a id="searchDrSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportDrSubjectInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	<div id="showDrSubjectInfoWindow" class="easyui-window"
		data-options="closed:true,modal:true,minimizable:false,maximizable:true" style="padding:1px;">
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrSubjectInfo').click(function(){
		$("#drSubjectInfoForm").form("reset");
		$("#drSubjectInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrSubjectInfo').click(function(){
 		$('#drSubjectInfoList').datagrid({
			queryParams: {
				code: $('#searchDrSubjectInfoCode').val(),
				no: $('#searchDrSubjectInfoNo').val(),
				amount: $('#searchDrSubjectInfoAmount').val(),
				surplusAmount: $('#searchDrSubjectInfoSurplusAmount').val(),
				startDate: $('#searchDrSubjectInfoStartDate').datebox('getValue'),
				endDate: $('#searchDrSubjectInfoEndDate').datebox('getValue'),
				status: $('#searchDrSubjectInfoStatus').combobox('getValue'),
			}
		}); 
	});
	
	//操作
	function formatOper(val,row,index){  
		if(row.status=="1"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowDrSubjectInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="toSubjectProductInfoBtn('+index+')">查看产品</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrSubjectInfoBtn('+index+')">入池</a>';
					
		}else{
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowDrSubjectInfoBtn('+index+')">查看</a>'+" "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="toSubjectProductInfoBtn('+index+')">查看产品</a>';
					
		}
	} 
	
	//跳转标的详情页面
	function toShowDrSubjectInfoBtn(index){
		$('#drSubjectInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drSubjectInfoList').datagrid('getSelected'); 
		$("#showDrSubjectInfoWindow").window({
			title:"标的详情",
			width:$(this).width()*0.5,
			height:$(this).height()*0.6,
			href:"../subject/showDrSubjectInfo.do?id="+row.id
		});
		$("#showDrSubjectInfoWindow").window("open").window("center");
	}
	//跳转到标的产品列表页面
	function toSubjectProductInfoBtn(index){
		$('#drSubjectInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drSubjectInfoList').datagrid('getSelected'); 
		 var mainTab = parent.$('#main-center'); 
		 var detailTab = {
				title : "标的产品列表",
				content:"<iframe scrolling='no' width='100%' height='100%' frameborder='0'  src='../subject/toSubjectDrProductInfoList.do?id="+row.id+"'></iframe>", 
				closable : true
		}; 
		 mainTab.tabs("add",detailTab); 
	}
	
	//入池操作
	function updateDrSubjectInfoBtn(index){  
		$('#drSubjectInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drSubjectInfoList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定将标的进入定期产品标的池？", function(ensure){
			if(ensure){
				var url = "${apppath}/subject/updateDrSubjectInfo.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drSubjectInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	//导出操作
	$('#exportDrSubjectInfo').click(function(){
		var order = $('#drSubjectInfoList').datagrid('options').sortOrder;
		window.location.href="../subject/exportDrSubjectInfo.do?code="+$('#searchDrSubjectInfoCode').val()+
		"&order="+order+
		"&amount="+$('#searchDrSubjectInfoAmount').val()+
		"&no="+$('#searchDrSubjectInfoNo').val()+
		"&surplusAmount="+$('#searchDrSubjectInfoSurplusAmount').val()+		
		"&startDate="+$('#searchDrSubjectInfoStartDate').datebox('getValue')+
		"&endDate="+$('#searchDrSubjectInfoEndDate').datebox('getValue')+
		"&status="+$('#searchDrSubjectInfoStatus').combobox('getValue');
	});
</script>
</body>
</html>

