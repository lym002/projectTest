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
	<table id="fundsRecord"  style="height:99%;width:99.9%" title="投资客户账户记录<span style='color: #0015FF;'>合计</span>：
		<span style='color: #0015FF;'>收入合计</span>：<span id='sr' style='color: red;'></span>
		<span style='color: #0015FF;'>支出合计</span>：<span id='zc' style='color: red;'></span>">
		<thead>
	    <tr>
	        <th data-options="field:'realname'" width="5%" >客户姓名</th>
	        <th data-options="field:'recommCodes'" width="5%" >推荐码</th>
	        <th data-options="field:'mobilePhone'" width="10%">手机</th>
	       	<th data-options="field:'addTime'" width="10%">交易时间</th>
	       	<th data-options="field:'tradeType'" width="10%">交易类型</th>	   
	        <th data-options="field:'sr'" width="10%">收入</th>
	       	<th data-options="field:'zc'" width="10%">支出</th>	     
	        <th data-options="field:'balance'" width="10%">余额</th>
	        <th data-options="field:'isFuiou'" width="5%" formatter='fromatterIsFuiou'>是否存管</th>
			<th data-options="field:'remark'" width="10%" >备注</th>
	    </tr>
	    </thead>
	</table>
	
	<div id="fundsRecordbtn" style="padding:5px;height:750">
		<form id="selfundsRecord">
<!-- 			客户姓名:<input id="realname" name="realname" class="easyui-textbox"  size="15" style="width:200px" hidden="true"/> -->
			推荐码:<input id="recommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
			手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:200px"/>
	                    交易类型:<select  id="tradeType" name="tradeType" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">充值</option>
				 	<option value="2">提现</option>
				 	<option value="3">投资</option>
				 	<option value="4">活动</option>
				 	<option value="5">提现手续费</option>
				 	<option value="6">回款</option>
				 	<option value="7">体验金</option>
	           	 </select>
	                    交易时间:<input id="startaddTime" name="startaddTime" class="easyui-datebox" style="width:100px"/>
	  		至<input id="endaddTime" name="endaddTime" class="easyui-datebox" style="width:100px"/>
	  		收支类型：<select  id="type" name="type" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">收入</option>
				 	<option value="0">支出</option>
	           	 </select>
	  		渠道：<select  id="isFuiou" name="isFuiou" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">存管</option>
				 	<option value="0">金运通</option>
	           	 </select>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selfundsRecord()">查询</a>
	       	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetfundsRecord()">重置</a>
	        <a id="exportfundsRecord" href="javascript:exportfundsRecord();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>       
	        
		</form>
	</div>
<script type="text/javascript">
	var fundsRecord = $('#fundsRecord');
	
	$(document).ready(function () {
		$('#startaddTime').datebox('setValue',getdate());
		$('#endaddTime').datebox('setValue',getdate());
		selfundsRecord();
	});
	
	//重置
	function resetfundsRecord(){
		$("#selfundsRecord").form("reset");
		selfundsRecord();
	}
	function fromatterIsFuiou(value){
		if(value==0){
			return '金运通';
		}else if(value==1){
			return '存管';
		}
		return '';
	}
	
	//查询
	function selfundsRecord(){
		fundsRecord.datagrid({
			url : "../member/getFundsRecord.do",
 			toolbar:'#fundsRecordbtn',
 			singleSelect:true,
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:25,
 			pageList:[25,50,100],
 			autoRowHeight : false,
 			fit:true,
			queryParams:{
// 			realname: $('#realname').textbox("getValue"),
			mobilePhone: $('#mobilePhone').textbox("getValue"),
			tradeType: $('#tradeType').combobox("getValue"),
			startaddTime: $('#startaddTime').datebox("getValue"),
			endaddTime: $('#endaddTime').datebox("getValue"),
			recommCodes: $('#recommCodes').textbox("getValue"),
			type: $('#type').combobox("getValue"),
			isFuiou: $('#isFuiou').textbox("getValue")
			},
		  	onBeforeLoad: function (d) {
			    $.ajax({
				url:"../member/fundsRecordSum.do",
				type:"POST",
				data:{
// 					realname: $('#realname').textbox("getValue"),
					recommCodes: $('#recommCodes').textbox("getValue"),
					mobilePhone: $('#mobilePhone').textbox("getValue"),
					tradeType: $('#tradeType').combobox("getValue"),
					startaddTime: $('#startaddTime').datebox("getValue"),
					endaddTime: $('#endaddTime').datebox("getValue"),
					type: $('#type').combobox("getValue"),
					isFuiou: $('#isFuiou').textbox("getValue")
				}, 
				success:function(result){
					$("#sr").text(result.sr);
					$("#zc").text(result.zc);
				}
				});
			} 
		});
	}
	
	//导出
	function exportfundsRecord(){
		var map = {
// 			realname: $('#realname').textbox("getValue"),
			mobilePhone: $('#mobilePhone').textbox("getValue"),
			tradeType: $('#tradeType').combobox("getValue"),
			startaddTime: $('#startaddTime').datebox("getValue"),
			endaddTime: $('#endaddTime').datebox("getValue"),
			type: $('#type').combobox("getValue")
		};
		window.location.href="../member/exportFundsRecord.do?"+$.param(map);
	}
</script>
</body>
</html>

