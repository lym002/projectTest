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
	<table id="auditDrClaimsInfoList" title="债权审核" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../claims/auditDrClaimsLoanList.do?isAuditEdit=1',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#auditDrClaimsInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'no'" width="20%">借款合同编号</th>
	        <th data-options="field:'name'" width="15%">产品名称</th>
	       	<th data-options="field:'companyName'" width="15%">企业名称</th>
    		<th data-options="field:'bankName'" width="15%">开户行</th>
	       	<th data-options="field:'bankNo'" width="15%">银行卡号</th>
	       	<th data-options="field:'isFuiou'" width="5%" formatter="formatIsFuiou">存管开通</th>
	        <th data-options="field:'loanAmount'" width="15%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	        <th data-options="field:'addDate'" width="10%" formatter="formatDateBoxFull">新增日期</th>
	        <th data-options="field:'statusName'" width="5%">当前状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
			<th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="auditDrClaimsInfoTools" style="padding:5px;height:750">
	  	<form id="auditDrClaimsInfoForm">
	  		合同编号:<input id="searchAuditClaimsLoanNo" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品名称:<input id="searchAuditClaimsLoanName" name="name" class="easyui-textbox"  size="15" style="width:150px"/>
	  		企业名称:<input id="searchAuditClaimsLoanCompanyName" name="companyName" class="easyui-textbox"  size="15" style="width:150px"/>
	  		录入日期:<input id="searchAuditClaimsLoanStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchAuditClaimsLoanEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		待审状态: <select  id="searchAuditClaimsLoanStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value='100'>全部</option>
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 1}">
							<option value='${map.key }'>${map.value }</option>
							</c:if>
						</c:forEach>
						<option value='101'>维护待审</option>
	           		</select>
	    	<a id="searchAuditClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetAuditClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetAuditClaimsLoan').click(function(){
		$("#auditDrClaimsInfoForm").form("reset");
		$("#auditDrClaimsInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchAuditClaimsLoan').click(function(){
 		$('#auditDrClaimsInfoList').datagrid({
			queryParams: {
				no: $('#searchAuditClaimsLoanNo').val(),
				name: $('#searchAuditClaimsLoanName').val(),
				companyName: $('#searchAuditClaimsLoanCompanyName').val(),
				startDate: $('#searchAuditClaimsLoanStartDate').datebox('getValue'),
				endDate: $('#searchAuditClaimsLoanEndDate').datebox('getValue'),
				status: $('#searchAuditClaimsLoanStatus').combobox('getValue')
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status=="1" || row.status=="3"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="auditDrClaimsInfoBtn('+index+')">审核</a>';
		}
		if(row.isAuditEdit == "1"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="auditDrClaimsInfoBtn('+index+')">审核维护</a>';
		}
	} 
	
	//跳转到债权审核页面
	function auditDrClaimsInfoBtn(index){
		$('#auditDrClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#auditDrClaimsInfoList').datagrid('getSelected'); 
		/*$.ajax({
			url: "${apppath}/claims/isOperate.do?id="+row.id+"&operate=audit",
			dataType:"json",
			success:function(result){
				if(result.success){
				}else{
					$.messager.alert("提示信息",result.errorMsg,function(){
						$('#drClaimsInfoList').datagrid('reload');
					});
				}
			}
 		}); */
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权管理-审核",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toAuditClaimsInfo.do?lid="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//
	function formatIsFuiou(val,row,index) {  
		console.log(row.user_id);
	    if (row.user_id ) {  
	        return '已开通';  
	    }else{
	  		return '未开通';
	    }  
	} 
	
</script>
</body>
</html>

