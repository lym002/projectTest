<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
    <table id="drGiftCardDetailList" title="兑换记录" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../giftCardSetUp/drGiftCardDetailList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#giftCardDetailListTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">ID</th>
				<th data-options="field:'name'" width="10%">礼品券名称</th>
				<th data-options="field:'giftCard'" width="10%">兑换券</th>
				<th data-options="field:'status'" formatter="formatDetailStatus" width="10%">状态</th>
				<th data-options="field:'channelName'" width="10%">渠道名称</th>
				<th data-options="field:'mobilePhone'" width="10%">用户手机号码</th>
				<th data-options="field:'investAmount'" styler="styleColor" width="10%">投资金额</th>
				<th data-options="field:'deadline'" styler="styleColor" width="10%">产品期限</th>
				<th data-options="field:'issueTime'" formatter="formatDateBoxFull" width="15%">发放时间</th>
			</tr>
		</thead>
	</table>
	<div id="giftCardDetailListTools" style="padding:5px;height:750">
		<form id="giftCardDetailListForm">
			渠道名称：<select id="searchChannel" name="channelId" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<c:forEach items="${channelList}" var="list">
						<option value='${list.id}'>${list.name}</option>
					</c:forEach>
			</select>
			礼品券名称：<input class="easyui-textbox" id="searchName" name="name" size="15" style="width:150px" />
			发放日期<input class="easyui-datetimebox" id="searchStartDate" name="startDate" style="width:150px" />
			到<input class="easyui-datetimebox" id="searchEndDate" name="endDate" style="width:150px" />
			
			<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
		</form>
	</div>
	<script type="text/javascript">
		//重置按钮
		$('#resetBtn').click(function(){
			$("#giftCardDetailListForm").form("reset");
			$("#drGiftCardDetailList").datagrid("load", {});
		});
		
		//查询按钮
		$('#searchBtn').click(function(){
	 		$('#drGiftCardDetailList').datagrid({
				queryParams: {
					name:$('#searchName').val(),
					channelId:$('#searchChannel').combobox('getValue'),
					startDate:$('#searchStartDate').combobox('getValue'),
					endDate:$('#searchEndDate').combobox('getValue'),
				}
			}); 
		});
		
		function getDate(obj){
			$('#searchStartDate').combobox('getValue');
		}
	
		//明细状态信息
		function formatDetailStatus(value,row,index){
			if(row.status == "1"){
				return '有效';
			}else if(row.status == "2"){
				return '已发放';
			}
			return '失效';
		}
	</script>
</body>
</html>
