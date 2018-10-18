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
	<table id="drProductLoanList" title="放款审核管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../product/drProductLoanListSQ.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#drProductLoanTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>	
	    	<th data-options="field:'phone'">手机号</th>	       	
	    	<th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'simpleName'" width="15%">产品名称</th>
	       	<th data-options="field:'rate'" width="5%">产品利率</th>
	       	<th data-options="field:'deadline'" width="5%">期限(天)</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">产品金额(元)</th>
	       	<th data-options="field:'alreadyRaiseAmount'" width="10%" styler="styleColor" formatter="formatAmount">已募集金额(元)</th>
	       	<th data-options="field:'repayTypeName'" width="8%">还款方式</th>
	        <th data-options="field:'statusName'" width="5%">产品状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
	       	<th data-options="field:'establish'" width="10%" formatter="iFormatDateBoxFull">成立日期</th>
	       	<th data-options="field:'expireDate'" width="10%" formatter="iFormatDateBoxFull">回款日期</th>
	       	<th data-options="field:'fullDate'" width="10%" formatter="iFormatDateBoxFull">募集成功日期</th>
	       	<th data-options="field:'actLoanTime'" width="10%" formatter="iFormatDateTimeBoxFull">实际放款日期</th>
	        <th data-options="field:'loanStatus'" width="5%" formatter="isloanStatus">放款状态</th>
	        <th data-options="field:'loanName'" width="5%" >借款方</th>
	        <th data-options="field:'project_no'" width="5%" formatter="isDepository">是否存管项目</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor">利息</th>
	        <th data-options="field:'heji'" width="5%" styler="styleColor">本息合计</th>
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductLoanTools" style="padding:5px;height:750">
	  	<form id="drProductLoanForm" target="_blank" method="post">
	  		产品名称:<input id="searchDrProductLoanSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		借款方:<input id="searchDrProductLoanloanName" name="loanName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		 还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
  						<option value=''>全部</option>
						<c:forEach items="${repayType}" var="map">
							<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
							<option value='${map.key }'>${map.value }</option>
							</c:if>
						</c:forEach>
					</select>
	  		回款日期:<input id="searchDrProductLoanloanstartDate" name="refundStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanEndDate" name="refundEndDate" class="easyui-datebox" style="width:100px"/>
	  		募集成功日期:<input id="searchDrProductLoanloanFullstartDate" name="fullStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanFullEndDate" name="fullEndDate" class="easyui-datebox" style="width:100px"/>
	  		实际放款日期:<input id="startactLoanTime" name="startactLoanTime" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endactLoanTime" name="endactLoanTime" class="easyui-datebox" style="width:100px"/>
	                   放款状态:<select id="searchDrProductLoanStatus" name="loanStatus" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='1'>已放款</option>
		  					<option value='3'>申请放款</option>
	  				</select>
	  		是否为存管项目:<select id="searchDrProductDepository" name="project_no" class="easyui-combobox" style="width:100px">
		 					<option value='2'>全部</option>
		 					<option value='0'>是</option>
		 					<option value='1'>否</option>
 				</select>	
	  		<a id="searchDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	<!-- -----------------放款------------------------------->
	<div id="addLending" class="easyui-dialog" style="height:280px;width:300px" closed="true"	buttons="addLendingBtn"  data-options="resizable:true,modal:true,closed:true">
		<center>
			<div style="padding:5px;height:50">
				企业名字：<input type="text" id="loanName" value="" disabled="disabled">
			</div>
			<div style="padding:5px;height:50">
				放款银行：<input type="text" id="bankName" value="" disabled="disabled">
			</div>
			<div style="padding:5px;height:50">
				放款账户：<input type="text" id="bankNo" value="" disabled="disabled">
			</div>
			<div style="padding:5px;height:50">
				放款金额(元)：<input type="text" id="alreadyRaiseAmount" value="" disabled="disabled"  formatter="formatAmount">
			</div>
			<div>
			
				放款描述:<input type="text" id="simpleName" value="" disabled="disabled">
			</div>
			<div style="padding:5px;height:50">
				实际放款时间：<input id="actLoanTime" name="actLoanTime" class="easyui-datetimebox" style="width:140px"/>
				<input id="line" type="hidden"/>
			</div>
			<div id="addLendingBtn" style="padding:5px">
				<a href="#" class="easyui-linkbutton" id = "ok" iconCls="icon-ok" onclick="updateDrProductLoanBtn()">通过</a> 
				<a href="#" class="easyui-linkbutton" id = "cancel" iconCls="icon-cancel" onclick="repulseDrProductLoanBtn()">拒绝</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="javascript:$('#addLending').dialog('close')">关闭</a>
			</div>
		</center>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetDrProductLoan').click(function(){
		$("#drProductLoanForm").form("reset");
		$("#drProductLoanList").datagrid("load", {});
	});
	//查询按钮
	$('#searchDrProductLoan').click(function(){
 		$('#drProductLoanList').datagrid({
			queryParams: {
				loanName:$('#searchDrProductLoanloanName').val(),
				refundStartDate:$('#searchDrProductLoanloanstartDate').datebox('getValue'),
				refundEndDate:$('#searchDrProductLoanloanEndDate').datebox('getValue'),
				fullStartDate:$('#searchDrProductLoanloanFullstartDate').datebox('getValue'),
				fullEndDate:$('#searchDrProductLoanloanFullEndDate').datebox('getValue'),
				simpleName: $('#searchDrProductLoanSimpleName').val(),
				loanStatus:$('#searchDrProductLoanStatus').combobox('getValue'),
				startactLoanTime:$('#startactLoanTime').datebox('getValue'),
				endactLoanTime:$('#endactLoanTime').datebox('getValue'),
				repayType:$('#repayType').combobox('getValue'),
				project_no:$("#searchDrProductDepository").combobox('getValue')
			}
		});
	});
	
	
	
	//添加放款基本操作链接
	function formatOper(val,row,index){  
		if(row.loanStatus=="3"){
			if(row.status=="6"||row.status=="9"||row.status=="8"){
				return	'<a href="#" class="btn l-btn l-btn-small" onclick="check('+index+')">放款</a>'+"   ";
        	}
		}
    }
    
	
	//放款操作
	function updateDrProductLoanBtn(){
		$('#drProductLoanList').datagrid('selectRow',$('#line').val());// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 
		
		if($('#actLoanTime').datetimebox("getValue")==null || $('#actLoanTime').datetimebox("getValue")==""){
			$.messager.alert('系统提示','请选择放款时间！');  
			return false;
		}
		var url = "${apppath}/product/drProductLoanSQ.do?id="+row.id+"&actLoanTime="+$('#actLoanTime').datetimebox("getValue")+"&phone="+row.phone;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				$('#addLending').dialog('close');
				$('#drProductLoanList').datagrid('reload');
				if(result.success){
					$.messager.alert("操作提示", result.msg);
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	//放款操作
	function repulseDrProductLoanBtn(){
		$('#drProductLoanList').datagrid('selectRow',$('#line').val());// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 

		var url = "${apppath}/product/updateDrProductLoanBtn.do?id="+row.id+"&actLoanTime="+$('#actLoanTime').datetimebox("getValue");
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				$('#addLending').dialog('close');
				$('#drProductLoanList').datagrid('reload');
				if(result.success){
					$.messager.alert("操作提示", result.msg);
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	
	function isloanStatus(val,row,index){ 
		if(val == 0){
			return "未放款";
		}
		if(val == 1){
			return "已放款";
		}
		if(val == 2){
			return "已回款";
		}
		if(val == 3){
			return "申请放款";
		}
		
	}
	
	//修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

//查看
function check(index){
	$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	var row = $('#drProductLoanList').datagrid('getSelected');
	//去后台根据选中产品的id查询出对应的信息
	var url = "../product/drProductInfoByidSQ.do?id="+row.id;
	$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#loanName").val(result.map.info.loanName);
					$("#bankName").val(result.map.info.bankName);
					$("#bankNo").val(result.map.info.bankNO);
					$("#alreadyRaiseAmount").val(result.map.info.alreadyRaiseAmount);
					$("#simpleName").val(result.map.info.simpleName);
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	$('#line').val(index);
	$('#addLending').dialog('open').dialog('setTitle', '查看-放款');
	$('#actLoanTime').datetimebox('setValue','');	
	if(row.loanStatus == 1){
		$('#ok').linkbutton({disabled:true});
		$('#cancel').linkbutton({disabled:true});
	}
	else if(row.loanStatus == 3){
		$('#ok').linkbutton({disabled:false});
		$('#cancel').linkbutton({disabled:false});
	}
}
</script>
</body>
</html>

