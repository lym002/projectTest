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
	<table id="regMemberInfoList" title="注册用户 <span style='color: #0015FF;'>合计</span>：
	<span id='channelCount' style='color: red;' ></span><span>个渠道</span>,<span id='investCount' style='color: red;' ></span><span>个投资用户</span>,<span id='realVerifyCount' style='color: red;' ></span><span>个绑卡用户</span>,
	<span id='investSum' style='color: red;' ></span><span>元投资</span>" 
	class="easyui-datagrid" style="height:99%;"
	data-options="singleSelect:true,
	    fitColumns:true, 
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drProductInvestTools'">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="5%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="8%" >手机号码</th>
	        <th data-options="field:'recommCodes'" width="8%" >推荐码</th>
	        <th data-options="field:'recomRealName'" width="5%">推荐人姓名</th>
	        <th data-options="field:'recomMobilePhone'" width="5%">推荐人号码</th>
	        <th data-options="field:'regdate'" width="12%">注册日期</th>
	        <th data-options="field:'bankName'" width="12%">绑卡银行</th>
	        <th data-options="field:'isFuiou'" width="12%" formatter="formatIsFuiou">存管</th>
	        <th data-options="field:'channelName'" width="5%">注册渠道</th>
	        <th data-options="field:'regfrom'" width="5%">注册终端</th>
	        <th data-options="field:'isCrush'" width="5%">是否充值</th>
	        <th data-options="field:'isInvest'" width="5%">是否投资</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		用户手机:<input id="searchRegMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
	  		<!-- 用户姓名:<input id="searchRegRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/> -->
	  		注册日期:<input id="searchRegStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchRegEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
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
			是否投资:<select id="isInvest" class="easyui-combobox" name="isInvest" style="width:200px;">   
					    <option value="">全部</option>   
					    <option value="1">是</option>   
					    <option value="0">否</option>   
					</select> 
			是否充值:<select id="isCrush" class="easyui-combobox" name="isCrush" style="width:200px;">   
					    <option value="">全部</option>   
					    <option value="1">是</option>   
					    <option value="0">否</option>   
					</select> 
	    	<a id="search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="export" href="javascript:exportRegMemberInfo();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	
<script type="text/javascript">
	
	//添加基本操作链接
	function formatIsFuiou(val,row,index){ 
		if(row.isFuiou==0){
			return "未开通";
		}else if(row.isFuiou == 1){
			return "已开通";
		}
	} 	
	$(document).ready(function () {
			selectInvestMemberInfoDataAjax();
	});
	
			function selectInvestMemberInfoDataAjax(){$.ajax({
					url:"${apppath}/regMemberInfo/selectInvestMemberInfoDataSum.do",
					type:"POST",
					data:$("#drProductInvestForm").serialize()+"&cids="+$('#cid').combogrid('getValues')+"", 
					success:function(result){
					console.log(result)
						if(result.success==true){
							console.log(result.map.recordSum);
							if(result.map.realVerifyCount == undefined){
								$("#realVerifyCount").text(0);
							}else{
								$("#realVerifyCount").text(" "+result.map.realVerifyCount+" ");
							}
							if(result.map.channelCount == undefined){
								$("#channelCount").text(0);
							}else{
								$("#channelCount").text(" "+result.map.channelCount+" ");
							}
							if(result.map.investCount == undefined){
								$("#investCount").text(0);
							}else{
								$("#investCount").text(" "+result.map.investCount+" ");
							}
							if(fmoney(result.map.investSum,2) == undefined){
								$("#investSum").text(0);
							}else{
								$("#investSum").text(" "+fmoney(result.map.investSum,2)+" ");
							}
						 	
					      }
					      
				    	}
					});
				}
	//查询按钮
	$('#search').click(function(){
		var cids = $('#cid').combogrid('getValues')+"";
 		$('#regMemberInfoList').datagrid({
 			url: '../regMemberInfo/regMemberInfoList.do',
			queryParams: {
				/* realname:$('#searchRegRealname').val(), */
				isInvest:$('#isInvest').combo("getValue")+"",
				isCrush:$('#isCrush').combo("getValue")+"",
				mobilephone:$('#searchRegMobilephone').val(),
				startDate:$('#searchRegStartDate').combobox('getValue'),
				endDate:$('#searchRegEndDate').combobox('getValue'),
				cids:cids
			}
		}); 
		 //调用ajax方法
		 selectInvestMemberInfoDataAjax();
	});
	
	//导出
	function exportRegMemberInfo(){
		window.location.href="../regMemberInfo/exportRegMemberInfo.do?isInvest="+$('#isInvest').combo("getValue")+
						"&mobilephone="+$('#searchRegMobilephone').val()+
						"&startDate="+$('#searchRegStartDate').combobox('getValue')+
						"&endDate="+$('#searchRegEndDate').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues')+
						"&isCrush="+$('#isCrush').combo("getValue")+"";
	}
</script>
</body>
</html>