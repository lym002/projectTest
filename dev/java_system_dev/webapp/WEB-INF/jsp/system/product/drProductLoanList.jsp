﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="drProductLoanList" title="放回款管理
	<span style='color: #0015FF;'>放款总额</span>：<span id='loanSum' style='color: red;'></span>" 
	
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../product/drProductLoanList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#drProductLoanTools',  
	    onBeforeLoad: function (d) {
			    $.ajax({
				url:'${apppath}/product/drProductLoanAmountSum.do',
				type:'POST',
				data:$('#drProductLoanForm').serialize(),  
				success:function(result){
					$('#loanSum').text(result.amountSum);
				}
				});
			} ">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'phone'" <c:if test="${menuType ==2 }"> formatter='formatterPhone'</c:if>>手机号</th>
	    	<th data-options="field:'surplusDay'" hidden="true"></th>
	    	<th data-options="field:'isSid'" hidden="true"></th>	    	
	    	<th data-options="field:'renewal'" hidden="true">判断是否续发</th>	
	    	<th data-options="field:'bespoke'" hidden="true">取消预约</th>		    	
	    	<th data-options="field:'project_no'" width="6%" formatter="formatProject_no">产品编号</th>
	    	<th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'simpleName'" width="15%">产品名称</th>
	       	<th data-options="field:'rate'" width="5%">产品利率</th>
	       	<th data-options="field:'activityRate'" width="5%">活动利率</th>
	       	<th data-options="field:'deadline'" width="5%">期限(天)</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">产品金额(元)</th>
	       	<th data-options="field:'alreadyRaiseAmount'" width="10%" styler="styleColor" formatter="formatAmount">已募集金额(元)</th>
	       	<th data-options="field:'repayTypeName'" width="8%">还款方式</th>
	        <th data-options="field:'statusName'" width="5%">产品状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
	       	<th data-options="field:'establish'" width="10%" formatter="iFormatDateBoxFull">成立日期</th>
	       	<th data-options="field:'expireDate'" width="10%" formatter="iFormatDateBoxFull">回款日期</th>
	       	<th data-options="field:'fullDate'" width="10%" formatter="formatDateBoxFull">募集成功日期</th>
	       	<th data-options="field:'actLoanTime'" width="10%" formatter="iFormatDateTimeBoxFull">实际放款日期</th>
	        <th data-options="field:'loanStatus'" width="5%" formatter="isloanStatus">放款状态</th>
	        <th data-options="field:'loanName'" width="5%" >借款方</th>
	        <th data-options="field:'project_no'" width="5%" formatter="isDepository">是否存管项目</th>
	        <th data-options="field:'interest'" width="5%" styler="styleColor">基本利息</th>
	        <th data-options="field:'shouldInterest'" width="5%" styler="styleColor">活动利息</th>
	        <th data-options="field:'heji'" width="5%" styler="styleColor">本息合计</th>
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductLoanTools" style="padding:5px;height:750">
	  	<form id="drProductLoanForm" target="_blank" method="post">
	  		产品名称:<input id="searchDrProductLoanSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		借款方:<input id="searchDrProductLoanloanName" name="loanName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		回款日期:<input id="searchDrProductLoanloanstartDate" name="refundStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanEndDate" name="refundEndDate" class="easyui-datebox" style="width:100px"/>
	  		募集成功日期:<input id="searchDrProductLoanloanFullstartDate" name="fullStartDate" class="easyui-datebox" style="width:150px"/>到
	  				<input id="searchDrProductLoanloanFullEndDate" name="fullEndDate" class="easyui-datebox" style="width:150px"/>
	  		实际放款日期:<input id="startactLoanTime" name="startactLoanTime" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endactLoanTime" name="endactLoanTime" class="easyui-datebox" style="width:100px"/>
	   	          还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
	  						<option value=''>全部</option>
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>       </br>    
	                   放款状态:<select id="searchDrProductLoanStatus" name="loanStatus" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0'>未放款</option>
		  					<option value='1'>已放款</option>
		  					<option value='2'>已回款</option>
	  				</select>
 			 渠道:<select id="searchDrProductDepository" name="project_no" class="easyui-combobox" style="width:100px">
		 					<option value='2'>全部</option>
		 					<option value='0'>银行存管</option>
		 					<option value='1'>金运通</option>
 				</select>	
	  		<a id="searchDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchDrProductLoan()" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exprotProductLoanAuditList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出放款审核表</a>
	    	<a id="productPaymentNoticeList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出项目还款通知表</a>
	    	<!-- <a id="productReturnNoticeList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" >导出项目回款通知表</a> -->
	    </form>
	</div>
	
	<!-- -----------------放款------------------------------->
	<div id="addLending" class="easyui-dialog" style="height:30%;width:20%" closed="true"	buttons="addLendingBtn"  data-options="resizable:true,modal:true,closed:true">
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
		<div style="padding:5px;height:50">
			实际放款时间：<input id="actLoanTime" name="actLoanTime" class="easyui-datetimebox" style="width:140px"/>
			<input id="line" type="hidden"/>
		</div>
		<div id="addLendingBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateDrProductLoanBtn()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addLending').dialog('close')">取消</a>
		</div>
		</center>
	</div>
 	<div id="addLend" class="easyui-dialog" style="height:30%;width:20%" closed="true"	buttons="addLendingBtn"  data-options="resizable:true,modal:true,closed:true">
		<center>
		<div style="padding:5px;height:50">
			企业名字：<input type="text" id="loanNameH" value="" disabled="disabled">
		</div>
		<div style="padding:5px;height:50">
			放款银行：<input type="text" id="bankNameH" value="" disabled="disabled">
		</div>
		<div style="padding:5px;height:50">
			放款账户：<input type="text" id="bankNoH" value="" disabled="disabled">
		</div>
		<div style="padding:5px;height:50">
			回款金额(元)：<input type="text" id="alreadyRaiseAmountH" value="" disabled="disabled"  formatter="formatAmount">
		</div>
		<div id="addLendingBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateRefundDrProductLoanBtn()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addLend').dialog('close')">取消</a>
		</div>
		</center>
	</div> 
	<form id="storageForm" id="storageForm" method="POST" target="_blank">
	   <input type="hidden" id ="storageJson" name="json" value="" />			
	</form>
<script type="text/javascript">
	var menuType='${menuType}';

	//重置按钮
	$('#resetDrProductLoan').click(function(){
		$("#drProductLoanForm").form("reset");
		searchDrProductLoan();
	});
	//查询按钮
	function searchDrProductLoan(){
 		$('#drProductLoanList').datagrid({
			queryParams: {
				loanName:$('#searchDrProductLoanloanName').val(),
				refundStartDate:$('#searchDrProductLoanloanstartDate').datebox('getValue'),
				refundEndDate:$('#searchDrProductLoanloanEndDate').datebox('getValue'),
				fullStartDate:$('#searchDrProductLoanloanFullstartDate').datetimebox('getValue'),
				fullEndDate:$('#searchDrProductLoanloanFullEndDate').datetimebox('getValue'),
				simpleName: $('#searchDrProductLoanSimpleName').val(),
				loanStatus:$('#searchDrProductLoanStatus').combobox('getValue'),
				startactLoanTime:$('#startactLoanTime').datebox('getValue'),
				endactLoanTime:$('#endactLoanTime').datebox('getValue'),
				repayType:$('#repayType').combobox('getValue'),
				project_no:$("#searchDrProductDepository").combobox('getValue')
			}
		});
	};
	//导出放款审批表
	$('#exprotProductLoanAuditList').click(function(){
		/* window.location.href="../product/exprotProductLoanAuditList.do?loanName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanloanName').val()))+
		"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
		"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
		"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
		"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
		"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
		"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
		"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
		"&endactLoanTime="+$('#endactLoanTime').datebox('getValue');*/
		var url= "../product/exprotProductLoanAuditList.do"; 
		$('#drProductLoanForm').form('submit',{
			url:url,
			success:function(data){
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	//导出项目还款通知表
	$('#productPaymentNoticeList').click(function(){
		/* window.location.href="../product/productPaymentNoticeList.do?loanName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanloanName').val()))+
		"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
		"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
		"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
		"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
		"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
		"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
		"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
		"&endactLoanTime="+$('#endactLoanTime').datebox('getValue');*/
		var url= "../product/productPaymentNoticeList.do";  
		$('#drProductLoanForm').form('submit',{
			url:url,
			success:function(data){
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	
	    
	//导出项目回款通知表
	/* $('#productReturnNoticeList').click(function(){
		var startDate = $('#searchDrProductLoanloanstartDate').datebox('getValue');//回款开始日期
		var EndDate = $('#searchDrProductLoanloanEndDate').datebox('getValue');//回款结束日期
		if(startDate == null || EndDate == null || startDate == "" || EndDate == ""){
			$.messager.alert('系统提示','请选择回款日期！');  
			return false;
		}else{
			 window.location.href="../product/productReturnNoticeList.do?loanName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanloanName').val()))+
			"&refundStartDate="+$('#searchDrProductLoanloanstartDate').datebox('getValue')+
			"&refundEndDate="+$('#searchDrProductLoanloanEndDate').datebox('getValue')+
			"&fullStartDate="+$('#searchDrProductLoanloanFullstartDate').datebox('getValue')+
			"&fullEndDate="+$('#searchDrProductLoanloanFullEndDate').datebox('getValue')+
			"&simpleName="+encodeURIComponent(encodeURIComponent($('#searchDrProductLoanSimpleName').val()))+
			"&loanStatus="+$('#searchDrProductLoanStatus').combobox('getValue')+
			"&startactLoanTime="+$('#startactLoanTime').datebox('getValue')+
			"&endactLoanTime="+$('#endactLoanTime').datebox('getValue');
			var url= "../product/productReturnNoticeList.do"; 
			$('#drProductLoanForm').form('submit',{
				url:url,
				success:function(data){
					var d = $.parseJSON(data);
					if(!d.success){
						alert(d.msg);
					}
				}
			});
		}
	}); */
	
	
	//添加放款基本操作链接
	function formatOper(val,row,index){  
		if(row.loanStatus=="0"){
			if((row.status=="6"||row.status=="9"||row.status=="8") && menuType=="1"){
				return	'<a href="#" class="btn l-btn l-btn-small" onclick="addLending('+index+')">存管放款</a>'+"   "+
	                    '<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   ";
        	}		
		}
		
		if(row.loanStatus=="1"&&row.surplusDay<=5){
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateRefundDrProductLoanBtn('+index+')">回款</a>'+"   "+
            '<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+"   ";
		}
		
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="toProductInfoBtn('+index+')">查看</a>'+" ";
    }
		//放款操作
	function updateDrProductLoanBtn(){
		$('#drProductLoanList').datagrid('selectRow',$('#line').val());// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 
		
		if($('#actLoanTime').datetimebox("getValue")==null || $('#actLoanTime').datetimebox("getValue")==""){
			$.messager.alert('系统提示','请选择存管放款时间！');  
			return false;
		}
		var url = "${apppath}/product/drProductLoanSQ.do?id="+row.id+"&actLoanTime="+$('#actLoanTime').datetimebox("getValue")+"&phone="+row.phone;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$('#addLending').dialog('close');
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("操作提示", result.msg);
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
		//回款操作
	function updateRefundDrProductLoanBtn(index){
		$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定回款？", function(ensure){
			if(ensure){
				var url = "../product/updateReFundDrProductLoan.do?phone="+row.phone+"&heji="+row.heji+"&code="+row.code+"&id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							if(result.map!=null){
								getStorageForm(result.map.signature);
								$("#storageForm")[0].action=result.map.fuiouUrl;
								$("#storageForm")[0].submit();
								$.messager.alert("操作提示", result.msg); 
								$('#drProductLoanList').datagrid('reload');
							}else{
								$.messager.alert("操作提示", result.msg); 
								$('#drProductLoanList').datagrid('reload');
							}
						}else{
							$('#drProductLoanList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	}
	
	function getStorageForm(json) {
		json = JSON.parse(json);
		for(var key in json.message){
			if(key !="signature") {
				$("#storageForm").prepend('<input type="hidden" name="'+key+'" value="'+json.message[key]+'" /><br/>');
			}
		}
		$("#storageForm").prepend('<input type="hidden" name="signature" value="'+json.signature+'" /><br/>');
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
		
	}
	//跳转到产品显示页面
	function toProductInfoBtn(index){
		$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductLoanList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "产品显示",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/product/toShowDrProductInfo.do?id="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	//修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	function formatProject_no(value) {  
	   	if(value ){
	   		return  '银行存管';
	   }
	    return "金运通";  
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
	
	//弹出放款框
function addLending(index){
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
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	$('#line').val(index);
	$('#addLending').dialog('open').dialog('setTitle', '存管放款');
	$('#actLoanTime').datetimebox('setValue','');	
}
//放款状态更改
function updateLoanStatus(index){
	$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	var row = $('#drProductLoanList').datagrid('getSelected');
	
	$.messager.confirm("提示", "确认更改为已放款?", function (r) {
        if (r) {
			$.ajax({
				url: "../product/updateLoanStatus.do?id="+row.id+"&phone="+row.phone+"&amount="+row.amount,
				dataType:"json",
				success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						/* $("#drProductLoanForm").form("reset");
						$("#drProductLoanList").datagrid("load", {}); */
						$('#drProductLoanList').datagrid('reload');
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
		  	});
        }
    });
}

//回款弹窗信息
function addLend(index){
	$('#drProductLoanList').datagrid('selectRow',index);// 关键在这里 
	var row = $('#drProductLoanList').datagrid('getSelected');
	//去后台根据选中产品的id查询出对应的信息
	var url = "../product/drProductInfoByidSQ.do?id="+row.id;
	$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#loanNameH").val(result.map.info.loanName);
					$("#bankNameH").val(result.map.info.bankName);
					$("#bankNoH").val(result.map.info.bankNO);
					$("#alreadyRaiseAmountH").val(result.map.info.alreadyRaiseAmount);
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	$('#line').val(index);
	$('#addLend').dialog('open').dialog('setTitle', '回款');
}
	function formatterPhone(val){ 
		if(val)
			return val.substr(0,3)+'****'+val.substr(val.length-4);
		return val;
	}

</script>
</body>
</html>

