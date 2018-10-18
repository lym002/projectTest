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
	<table id="filingList" title="存管管理" style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">id</th>
	    	<th data-options="field:'fileStatus'" hidden="true">报备状态</th>
	    	<th data-options="field:'fullFileStatus'" hidden="true">满标放款报备状态</th>
	    	<th data-options="checkbox:true,field:'ck'" width="3%"></th>
	    	<th data-options="field:'mchntTxnSsn'" width="12%">流水号</th>
	        <th data-options="field:'mchTime'" width="6%">交易日期</th>
	        <th data-options="field:'itemNo'" width="6%">项目编号</th>
	       	<th data-options="field:'amount'" width="7%" formatter="formatAmount">金额</th>
			<th data-options="field:'code'" width="15%">合同号</th>
			<th data-options="field:'out_cust_no'" width="9%">出账人存管账户系统用户名</th>
			<th data-options="field:'realname'" width="6%">出账人平台用户名</th>			
			<th data-options="field:'in_cust_no'" width="8%">入账人存管账户系统用户名</th>
			<th data-options="field:'name'" width="12%">入账人平台用户名</th>
			<th data-options="field:'failureCause'" width="13%">失败原因</th>
			<!-- <th data-options="field:'_operate',align:'center'" width="5%" formatter="formatOper">基本操作</th> -->
	    </tr> 
	    </thead>
	</table>
	<div id="dealFilingToor" style="padding:5px;height:750">
	  	<form id="dealFilingForm">
	    	 业务类型: <select  id=businessType name="businessType" style="width:100px;" class="easyui-combobox" 
	    	 data-options="onSelect:function(){searchDealFilingBtn();}">
						<option value="0">投标</option>
						<option value="1">满标放款</option>
						<option value="3">回款</option>
	          		 </select>
	    	 失败原因: <select  id="failureCauseType" name="failureCauseType" style="width:160px;" class="easyui-combobox" data-options="onSelect:function(){searchDealFilingBtn();}" >
						<option value=''>全部</option>
						<option value="数据基本格式校验失败">数据基本格式校验失败</option>
						<option value="交易信息未到">交易信息未到</option>
						<option value="该记录已通过核验">该记录已通过核验</option>
						<option value="等待另一方">等待另一方</option>
						<option value="登录ID不匹配">登录ID不匹配</option>
						<option value="用户名不匹配">用户名不匹配</option>
						<option value="用户证件号不匹配">用户证件号不匹配</option>
						<option value="用户证件类型不匹配">用户证件类型不匹配</option>
						<option value="企业名称不匹配">企业名称不匹配</option>
						<option value="手机号不匹配">手机号不匹配</option>
						<option value="交易类型不匹配">交易类型不匹配</option>
						<option value="交易金额不匹配">交易金额不匹配</option>
						<option value="入账登录ID不匹配">入账登录ID不匹配</option>
						<option value="交易找不到对应项目">交易找不到对应项目</option>
						<option value="交易不对应相关项目信息">交易不对应相关项目信息</option>
						<option value="增加该数据异常">增加该数据异常</option>
						<option value="该记录已存在">该记录已存在</option>
	          		 </select>
	         <br>
	    	<a id="searchDealFilingBtn" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDealFilingBtn()">查询</a>
	    	<!-- <a id=resetDealFilingBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> -->
	    	<a id="searchBatchFilingBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="toOption()">批量报备</a>
	    </form>
	</div>
	<div id="addFilingTypeDialog" class="easyui-dialog" title="请选择报备类型"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductStartDateDialogBtn'" style="width:300px;height:200px;padding:5px;">
		<form id="addDrProductInfoStartDateForm">
			<table align="center">
				报备类型: <select  id=repairTpe name="repairTpe" style="width:150px;" class="easyui-combobox" data-options="editable:false">
							<option value=''>点我选择补报类型</option>
							<option value="0">投标</option>
							<option value="1">满标放款</option>
							<option value="3">回款</option>
							<option value="4">其它</option>
	          			</select>
			</table>
		</form>
		<div id="addDrProductStartDateDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="batchFilingAudit()">确定</a>
		</div>
	</div>
<script type="text/javascript">

	//当页面加载的时候就触发函数里面的代码
	$(document).ready(function() {
		searchDealFilingBtn();
		});
	
	//打开di
	function toOption() {
		$("#addFilingTypeDialog").dialog("open");
	}
	
/* 	//重置按钮
	$('#resetDealFilingBtn').click(function() {
		$("#dealFilingForm").form("reset");
		$("#filingList").datagrid("load", {});
	}); */
	
	
	//查询按钮
	function searchDealFilingBtn() {
		$('#filingList').datagrid({
			url : '../filing/dealFilingList.do',
			singleSelect : false,
			fitColumns : true,
			showFooter : true,
			pagination : true,
			rownumbers : true,
			pageSize : 10,
			pageList : [ 10, 20, 30 ],
			autoRowHeight : false,
			toolbar : "#dealFilingToor",
			fit : true,
			queryParams : {
				businessType : $('#businessType').combobox('getValue'),
				failureCauseType : $('#failureCauseType').combobox('getValue')
			}
		});
	}

	//批量报备
	function batchFilingAudit() {
		var carryListData = JSON.stringify($("#filingList").datagrid(
				'getChecked'));
		console.log(carryListData);
		var businessType = $('#businessType').combobox('getValue');
		var failureCauseType = $('#failureCauseType').combobox('getValue');
		var repairTpe = $('#repairTpe').combobox('getValue');
		var listData = new Array();
		var data;
		if (carryListData != null && carryListData != "") {
			for (var i = 0; i < carryListData.length; i++) {
				data = new Object();
				data["id"] = carryListData[i].id;
				data["paymentNum"] = carryListData[i].paymentNum;
				listData[i] = data;
			}
			$.ajax({
				type : 'post',
				url : "../filing/batchDealFilingAudit.do",
				cache : false,
				data : {
					"carryListData" : carryListData,
					"businessType" : businessType,
					"failureCauseType" : failureCauseType,
					"repairTpe" : repairTpe
				},
				cache : false,
				async : false,
				success : function(result) {
					if (result.success) {
						var msg = "";
						if (result.errorMsg) {
							var error = result.errorMsg.split(",");
							for (var i = 0; i < error.length - 1; i++) {
								msg += error[i] + "<br>";

							}
						}
						$.messager.alert('操作提示', '操作成功'+'<br>');
						// 						$("#filingList").datagrid("load", {});
						$('#searchDealFilingBtn').click();
						$("#addFilingTypeDialog").dialog("close");
					} else {
						$.messager.alert('操作提示', '操作失败' + result.errorMsg);
						return false;
					}
				},
				error : function(message) {
					$.messager.alert('操作提示', '操作失败' + result.errorMsg);
					return false;
				}
			});
		} else {
			$.messager.alert('系统提示', '请选择客户！');
			return false;
		}
	}
</script>
</body>
</html>

