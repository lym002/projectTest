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
	<table id="drChannelInfoOrderListFirst" title="渠道订单" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, 
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drChannelInfoOrderTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'uid'" hidden="true">序号</th>
	    	<th data-options="field:'realName'" width="10%" >客户姓名</th>
	        <th data-options="field:'mobilephone'" width="10%">手机号码</th>
	        <th data-options="field:'fullName'" width="10%">产品名称</th>
	        <th data-options="field:'amount'" width="10%" styler="styleColor" formatter="formatAmount">订单金额</th>
	        <th data-options="field:'deadline'" width="5%">投资产品期限</th>
	     	<th data-options="field:'repayTypeName'" width="8%">还款方式</th>
	        <th data-options="field:'investTime'" width="10%" formatter="formatDateBoxFull">订单时间</th>
	        <th data-options="field:'regDate'" width="10%" formatter="formatDateBoxFull">注册时间</th>
	        <th data-options="field:'code'" width="10%">渠道编号</th>
	       	<th data-options="field:'name'" width="10%">渠道名称</th>
	        <th data-options="field:'cardInfo'" width="5%" formatter="haveCardInfo">绑卡信息</th>
	    </tr>
	    </thead>
	</table>
	<div id="drChannelInfoOrderTools" style="padding:5px;height:750">
	  	<form id="drChannelInfoOrderForm">
	  		注册时间:<input id="ssearchDrChannelInfoOrderStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchDrChannelInfoOrderEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		订单时间:<input id="ssearchDrChannelInfoOrderStartInvestDate" name="startInvestDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchDrChannelInfoOrderEndInvestDate" name="endInvestDate" class="easyui-datebox" style="width:100px"/>
	  		手机号码:<input id="searchDrChannelInfoOrderPhone" name="mobilephone" class="easyui-textbox"  size="15" style="width:100px"/>
			<input type="hidden" id="isAuth" value=${isAuth}>
			渠道名称:
         	<c:if test="${isAuth == 1 }">
				<select  id="searchDrChannelInfoOrderCode" name="code" style="width:100px;" class="easyui-combobox">
					<c:forEach items="${channel }" var="map">
						<c:if test="${map.code == code }">
							<option value='${map.code }'>${map.name }</option>
						</c:if>
					</c:forEach>
         			</select>
				</c:if>
			<c:if test="${isAuth == 0 }">
				<select class="easyui-combogrid" id="cid" name="cid" style="width:240px;padding-bottom: 3px;" data-options="
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
					</c:if>
			 绑卡状态:<select  id="isbank" name="isbank" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="0">否</option>
				 	<option value="1">是</option>
	           	 </select>
         	还款方式:<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType">
  						<option value=''>全部</option>
						<c:forEach items="${repayType}" var="map">
							<c:if test="${map.key == 1 || map.key == 2 || map.key == 3 || map.key == 4 }">
							<option value='${map.key }'>${map.value }</option>
							</c:if>
						</c:forEach>
					</select>
	                       项目周期:<select  id="deadline" name="deadline" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">1</option>
				 	<option value="7">7</option>
				 	<option value="30">30</option>
				 	<option value="35">35</option>
				 	<option value="60">60</option>
				 	<option value="90">90</option>
				 	<option value="150">150</option>
				 	<option value="180">180</option>
	        </select>
	    	<a id="searchFistDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="drChannelInfoOrderDownFirst()">导出</a>	
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrChannelInfoOrder').click(function(){
		$("#drChannelInfoOrderForm").form("reset");
		$("#drChannelInfoOrderListFirst").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchFistDrChannelInfoOrder').click(function(){
		var isAuth = $('#isAuth').val();
		if(isAuth == 1){
			//渠道用户
			$('#drChannelInfoOrderListFirst').datagrid({
				url:"../channel/drChannelInfoOrderListFirst.do",
			queryParams: {
				startDate: $('#ssearchDrChannelInfoOrderStartDate').datebox('getValue'),
				endDate: $('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				startInvestDate:$('#ssearchDrChannelInfoOrderStartInvestDate').datebox('getValue'),
				endInvestDate:$('#searchDrChannelInfoOrderEndInvestDate').datebox('getValue'),
				mobilephone: $('#searchDrChannelInfoOrderPhone').val(),
				code: $('#searchDrChannelInfoOrderCode').combobox('getValue'),
				isbank:$('#isbank').combobox('getValue'),
				deadline:$('#deadline').combobox('getValue'),
				repayType:$('#repayType').combobox('getValue')
			}
		}); 
		}
		if(isAuth == 0){
			//非渠道用户
			var cids = $('#cid').combogrid('getValues')+"";
			$('#drChannelInfoOrderListFirst').datagrid({
				url:"../channel/drChannelInfoOrderListFirst.do",
				queryParams: {
					startDate: $('#ssearchDrChannelInfoOrderStartDate').datebox('getValue'),
					endDate: $('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
					startInvestDate:$('#ssearchDrChannelInfoOrderStartInvestDate').datebox('getValue'),
					endInvestDate:$('#searchDrChannelInfoOrderEndInvestDate').datebox('getValue'),
					mobilephone: $('#searchDrChannelInfoOrderPhone').val(),
					cids:cids,
					isbank:$('#isbank').combobox('getValue'),
					deadline:$('#deadline').combobox('getValue'),
					repayType:$('#repayType').combobox('getValue')
				}
			}); 
		}
	});
	//导出按钮
	function drChannelInfoOrderDownFirst(){
		var isAuth = $('#isAuth').val();
		if(isAuth == 1){
			location.href="${apppath}/channel/exportChannelOrderRecordFirst.do?mobilephone="+$('#searchDrChannelInfoOrderPhone').val()
			+"&startDate="+$('#ssearchDrChannelInfoOrderStartDate').datebox('getValue')+
			"&endDate="+$('#searchDrChannelInfoOrderEndDate').datebox('getValue')+
			"&startInvestDate="+$('#ssearchDrChannelInfoOrderStartInvestDate').datebox('getValue')+
			"&endInvestDate="+$('#searchDrChannelInfoOrderEndInvestDate').datebox('getValue')+
			"&code="+$('#searchDrChannelInfoOrderCode').combobox('getValue')+
			"&deadline="+$('#deadline').combobox('getValue')+
			"&isbank="+$('#isbank').combobox('getValue')+
			"&repayType="+$('#repayType').combobox('getValue');
		}
		if(isAuth == 0){
			location.href="${apppath}/channel/exportChannelOrderRecordFirst.do?mobilephone="+$('#searchDrChannelInfoOrderPhone').val()
							+"&startDate="+$('#ssearchDrChannelInfoOrderStartDate').datebox('getValue')+
							"&endDate="+$('#searchDrChannelInfoOrderEndDate').datebox('getValue')+
							"&startInvestDate="+$('#ssearchDrChannelInfoOrderStartInvestDate').datebox('getValue')+
							"&endInvestDate="+$('#searchDrChannelInfoOrderEndInvestDate').datebox('getValue')+
							"&cids="+$('#cid').combogrid('getValues')+
							"&deadline="+$('#deadline').combobox('getValue')+
							"&isbank="+$('#isbank').combobox('getValue')+
							"&repayType="+$('#repayType').combobox('getValue');
		}
	};
	
	
	function haveCardInfo(val,row,index){
		if(row.realName){
			return "是";
		}else{
			return "否";
		}
		
	}
</script>
</body>
</html>

