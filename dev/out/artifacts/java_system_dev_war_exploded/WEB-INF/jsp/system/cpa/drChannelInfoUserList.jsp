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
	<table id="drChannelInfoUserList" title="渠道注册用户" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drChannelInfoUserTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'realName'" width="5%">客户姓名</th>
	    	<th data-options="field:'cardInfo'" width="5%" formatter="haveCardInfo">绑卡信息</th>
<!-- 	    	<th data-options="field:'isFuiou'" width="5%" formatter="formatIsFuiou">存管开通</th> -->
	        <th data-options="field:'mobilephone'" width="10%">手机号码</th>
	        <th data-options="field:'regDate'" width="10%" formatter="formatDateBoxFull">注册时间</th>
	        <th data-options="field:'regIp'" width="10%" >注册IP</th>
	        <th data-options="field:'code'" width="10%">渠道编号</th>
	       	<th data-options="field:'name'" width="10%">渠道名称</th>
	    </tr>
	    </thead>
	</table>
	<div id="drChannelInfoUserTools" style="padding:5px;height:750">
	  	<form id="drChannelInfoUserForm">
	  		注册时间:<input id="ssearchDrChannelInfoUserStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchDrChannelInfoUserEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		手机号码:<input id="searchDrChannelInfoUserPhone" name="mobilephone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		<input type="hidden" id="isAuth" value=${isAuth}>
			渠道名称:
				<c:if test="${isAuth == 1 }">
				<select  id="searchDrChannelInfoUserCode" name="code" style="width:100px;" class="easyui-combobox">
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
	    	<a id="searchDrChannelInfoUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrChannelInfoUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="drChannelInfoUserDown()">导出</a>	
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrChannelInfoUser').click(function(){
		$("#drChannelInfoUserForm").form("reset");
		$("#drChannelInfoUserList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrChannelInfoUser').click(function(){
		var isAuth = $('#isAuth').val();
		if(isAuth ==1 ){
			$('#drChannelInfoUserList').datagrid({
				url: '../channel/drChannelInfoUserList.do',
				queryParams: {
					startDate: $('#ssearchDrChannelInfoUserStartDate').datebox('getValue'),
					endDate: $('#searchDrChannelInfoUserEndDate').datebox('getValue'),
					mobilephone: $('#searchDrChannelInfoUserPhone').val(),
					code: $('#searchDrChannelInfoUserCode').combobox('getValue'),
					isbank:$('#isbank').combobox('getValue'),
				}
			}); 	
		}
		if(isAuth == 0){
			var cids = $('#cid').combogrid('getValues')+"";
	 		$('#drChannelInfoUserList').datagrid({
	 			url: '../channel/drChannelInfoUserList.do',
				queryParams: {
					startDate: $('#ssearchDrChannelInfoUserStartDate').datebox('getValue'),
					endDate: $('#searchDrChannelInfoUserEndDate').datebox('getValue'),
					mobilephone: $('#searchDrChannelInfoUserPhone').val(),
					isbank:$('#isbank').combobox('getValue'),
					cids:cids,
				}
			}); 
		}
	});

	function drChannelInfoUserDown(){
		var isAuth = $('#isAuth').val();
		if(isAuth ==1){
			location.href="${apppath}/channel/exportChannelUserRecord.do?mobilephone="+$('#searchDrChannelInfoUserPhone').val()
			+"&startDate="+$('#ssearchDrChannelInfoUserStartDate').datebox('getValue')+
			"&endDate="+$('#searchDrChannelInfoUserEndDate').datebox('getValue')+
			"&code="+$('#searchDrChannelInfoUserCode').combobox('getValue')+
			"&isbank="+$('#isbank').combobox('getValue');
		}
		if(isAuth == 0){
		location.href="${apppath}/channel/exportChannelUserRecord.do?mobilephone="+$('#searchDrChannelInfoUserPhone').val()
						+"&startDate="+$('#ssearchDrChannelInfoUserStartDate').datebox('getValue')+
						"&endDate="+$('#searchDrChannelInfoUserEndDate').datebox('getValue')+
						"&isbank="+$('#isbank').combobox('getValue')+
						"&cids="+$('#cid').combogrid('getValues');
		}
	};
	function haveCardInfo(val,row,index){
		if(row.realName){
			return "是";
		}else{
			return "否";
		}
		
	}
	function formatIsFuiou(val,row,index){
		if(row.isFuiou){
			return "已开通";
		}else{
			return "未开通";
		}
		
	}
</script>
</body>
</html>

