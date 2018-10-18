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
	<table id="regMemberInfoList" title="选择发放用户 --${drActivityParameter.name}--${drActivityParameter.raisedRates}--${drActivityParameter.enableAmount}</span>" 
	class="easyui-datagrid" style="height:99%;"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../regMemberInfo/selectRegMemberInfoList.do',
	    method:'post',rownumbers:true, singleSelect:false,
	    pagination:true,toolbar:'#drProductInvestTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'uid'" hidden="true">用户ID</th>
	    	<th data-options="field:'batchLoan',checkbox:true"></th>
	        <th data-options="field:'realname'" width="5%">用户姓名</th>
	        <th data-options="field:'mobilePhone'" width="8%" >手机号码</th>
	        <th data-options="field:'recomRealName'" width="5%">推荐人姓名</th>
	        <th data-options="field:'recomMobilePhone'" width="5%">推荐人号码</th>
	        <th data-options="field:'regdate'" width="12%">注册日期</th>
	        <th data-options="field:'shouldTime'" width="10%" formatter="iFormatDateBoxFull">回款日期</th>
	        <th data-options="field:'bankName'" width="12%">绑卡银行</th>
	        <th data-options="field:'channelName'" width="5%">注册渠道</th>
	        <th data-options="field:'keyWord'" width="5%">注册关键字</th>
	        <th data-options="field:'regfrom'" width="5%">注册终端</th>
	        <th data-options="field:'isCrush'" width="5%">是否充值</th>
	        <th data-options="field:'isInvest'" width="5%">是否投资</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInvestTools" style="padding:5px;height:750">
	  	<form id="drProductInvestForm">
	  		用户手机:<input id="searchRegMobilephone" name="mobilephone" class="easyui-textbox"  size="30" style="width:100px"/>
	  		用户姓名:<input id="searchRegRealname" name="realname" class="easyui-textbox"  size="30" style="width:100px"/>
	  		注册日期:<input id="searchRegStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchRegEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		回款日期:<input id="searchDrProductLoanloanstartDate" name="refundStartDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchDrProductLoanloanEndDate" name="refundEndDate" class="easyui-datebox" style="width:100px"/>		
	  		渠道名称:<select class="easyui-combogrid" id="cid" name="cid" style="width:120px;padding-bottom: 3px;" data-options="
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
			关键字:<input id="searchRegKeyWord" name="keyWord" class="easyui-textbox" size="50" style="width:100px"/>
			是否投资:<select id="isInvest" class="easyui-combobox" name="isInvest" style="width:100px;">   
					    <option value="">全部</option>   
					    <option value="1">是</option>   
					    <option value="0">否</option>   
					</select> 
			是否充值:<select id="isCrush" class="easyui-combobox" name="isCrush" style="width:100px;">   
					    <option value="">全部</option>   
					    <option value="1">是</option>   
					    <option value="0">否</option>   
					</select> 
			是否查首投<select id="isFirst" class="easyui-combobox" name="isFirst" style="width:100px;">   
					    <option value="">全部</option>   
					    <option value="1">是</option>   
					</select> 
	    	<a id="search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateBatchGiveOutMember()">批量发放</a>
	    </form>
	</div>
	
<script type="text/javascript">
	
	$(document).ready(function () {
			selectInvestMemberInfoDataAjax();
	});
	
			function selectInvestMemberInfoDataAjax(){
				$.ajax({
					url:"${apppath}/regMemberInfo/selectInvestMemberInfoDataSum.do",
					type:"POST",
					data:$("#drProductInvestForm").serialize()+"&cids="+$('#cid').combogrid('getValues')+"", 
					success:function(result){
					console.log(result)
						if(result.success==true){
							console.log(result.map.recordSum);
							$("#realVerifyCount").text(" "+result.map.realVerifyCount+" ");
						 	$("#channelCount").text(" "+result.map.channelCount+" ");
						 	$("#investCount").text(" "+result.map.investCount+" ");
						 	$("#investSum").text(" "+fmoney(result.map.investSum,2)+" ");
					      }
					      
				    	}
					});
				}
	//查询按钮
	$('#search').click(function(){
		var cids = $('#cid').combogrid('getValues')+"";
 		$('#regMemberInfoList').datagrid({
 			url: '../regMemberInfo/selectRegMemberInfoList.do',
			queryParams: {
				realname:$('#searchRegRealname').val(),
				keyWord:$('#searchRegKeyWord').val(),
				isInvest:$('#isInvest').combo("getValue")+"",
				isCrush:$('#isCrush').combo("getValue")+"",
				isFirst:$('#isFirst').combo("getValue")+"",
				mobilephone:$('#searchRegMobilephone').val(),
				startDate:$('#searchRegStartDate').combobox('getValue'),
				endDate:$('#searchRegEndDate').combobox('getValue'),
				refundStartDate:$('#searchDrProductLoanloanstartDate').combobox('getValue'),
				refundEndDate:$('#searchDrProductLoanloanEndDate').combobox('getValue'),
				cids:cids
			}
		}); 
		 //调用ajax方法
		 selectInvestMemberInfoDataAjax();
	});
	//重置按钮
	$('#resetBtn').click(function(){
		$("#drProductInvestForm").form("reset");
		$("#regMemberInfoList").datagrid("load", {});
	});
	//批量发放加息劵
	 function updateBatchGiveOutMember(){
		var checkedItems = $('#regMemberInfoList').datagrid('getChecked');
		if(checkedItems == ""){
			$.messager.alert("操作提示", "请选择要发放的数据！");
		 	return;
		}
		var uids = [];
		i=0;
		$.each(checkedItems, function(index, item){
			uids.push(item.uid);
			i++;
			
		}); 
		var counts = i;
		$.messager.confirm("发放提示", "共计"+counts+"张"+"是否确认批量发放？", function(ensure){
			if(ensure){
				var url = "../member/updateBatchGiveOutMember.do?uids="+uids;
					$.ajax({
			         	url: url,
			           	type: 'POST',
			           	dataType:"json",
			           	data:{"id":'${id}'},  
			           	success:function(result){
							if(result.success){
								$.messager.alert("提示信息",result.msg);
								$("#regMemberInfoList").datagrid("reload");
								 $("#addRedEnvelopeDialog").dialog("close");
							}else{
								$.messager.alert("提示信息",result.errorMsg);
							}
						}	
			       	});
			}
		});
	}
	//修改时间的显示样式，只显示到年月日
		function iFormatDateBoxFull(value) {  
		    if (value == null || value == '') {  
		        return '';  
		    }  
		    var dt = parseToDate(value);  
		    return dt.format("yyyy-MM-dd");  
		}
</script>
</body>
</html>