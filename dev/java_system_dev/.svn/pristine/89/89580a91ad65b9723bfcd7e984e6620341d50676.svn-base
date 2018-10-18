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
	<table id="jsMemberInfoList" title="订单管理	<span style='color: #0015FF;'>总金额统计</span>：<span id='investAmountSum' style='color: red;'></span>"  
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#jsMemberInfoTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'realname'" width="5%">客户姓名</th>	
	    	<th data-options="field:'mobilePhone'" width="7%">账号</th>	
	    	<th data-options="field:'recommCodes'" width="5%">推荐码</th>	
	    	<th data-options="field:'allotName'" width="5%">所属部门</th>	
	    	<th data-options="field:'userName'" width="5%">电销人员</th>		 	  	   	    	
	    	<th data-options="field:'investAmount'" width="7%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'fullName'" width="11%">标的名称</th>
	       	<th data-options="field:'deadline'" width="5%">项目周期(天)</th>
	       	<th data-options="field:'rate'" width="5%">项目预期年化率</th>
	       	<th data-options="field:'addTime'" width="10%" formatter="iFormatDateTimeBoxFull">订单日期</th>
	       	<th data-options="field:'prizeName'" width="10%" >商品名称</th>
	       	<th data-options="field:'phone'" width="5%" >收货人电话</th>
	       	<th data-options="field:'name'" width="5%"  >收货人姓名</th>
	       	<th data-options="field:'address'" width="20%"  >收货地址</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsMemberInfoTools" style="padding:5px;height:750">
	  	<form id="jsMemberInfoForm" target="_blank" method="post">
	  		产品编号:<input id="searchProductCode" name="productCode" class="easyui-textbox"  size="15" style="width:200px"/>
	  		商品名称:<input id="searchProductName" name="productName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		项目周期:<input id="searchProductDeadline" name="deadline" class="easyui-numberbox"  size="15" style="width:200px"/>
	  		<!-- 客户名称:<input id="searchDrMemberName" name="name" class="easyui-textbox"  size="15" style="width:200px"/> -->
	  		账号:<input id="searchPhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:200px"/>
	  		订单日期:<input id="searchStartDate" name="orderFormStart" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchEndDate" name="orderFormEnd" class="easyui-datebox" style="width:100px"/>
	  		<a id="searchJsMemberInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsMemberInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exprotJsMemberInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	
<script type="text/javascript">


	//重置按钮
	$('#resetJsMemberInfo').click(function(){
		$("#jsMemberInfoForm").form("reset");
		$("#jsMemberInfoList").datagrid("load", {});
	});
	//查询按钮
	$('#searchJsMemberInfo').click(function(){
 		$('#jsMemberInfoList').datagrid({
 			url: '../jsMemberInfo/JsMemberInfoList.do',
			queryParams: {
				productCode:$('#searchProductCode').val(),
				productName:$('#searchProductName').val(),
				deadline:$('#searchProductDeadline').numberbox('getValue'),
				name:$('#searchDrMemberName').val(),
				mobilePhone:$('#searchPhone').val(),
				orderFormStart:$('#searchStartDate').datebox('getValue'),
				orderFormEnd:$('#searchEndDate').datebox('getValue')
			},
			 onBeforeLoad: function (d) {
			    $.ajax({
				url:"${apppath}/jsMemberInfo/getInvestAmountSum.do",
				type:"POST",
				data:$("#jsMemberInfoForm").serialize(),  
				success:function(result){
					$("#investAmountSum").text(fmoney(result,2));
				}
				});
			} 
		});
	});
	//导出放款审批表
	$('#exprotJsMemberInfo').click(function(){
		/* window.location.href="../jsMemberInfo/exportJsMemberInfo.do?productCode="+$('#searchProductCode').val()+
						"&name="+$('#searchDrMemberName').val()+
						"&orderFormStart="+$('#searchStartDate').datebox('getValue')+"&orderFormEnd="+$('#searchEndDate').datebox('getValue'); */
						
		var url= "../jsMemberInfo/exportJsMemberInfo.do"; 
		$('#jsMemberInfoForm').form('submit',{
			url:url,
			success:function(data){
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	
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
</script>
</body>
</html>

