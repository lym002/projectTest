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
	<table id="drChannelInfoOrderListFirst" title="渠道订单变更" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drChannelInfoOrderTools'">
		<thead>
	    <tr>
	    	<!-- <th data-options="field:'id'" hidden="true">序号</th> -->
	    	<th data-options="field:'uid'" hidden="true">序号</th>
	    	<th data-options="field:'realName'" width="10%" >客户姓名</th>
	        <th data-options="field:'mobilephone'" width="10%">手机号码</th>
	        <th data-options="field:'regDate'" width="10%" formatter="formatDateBoxFull">注册时间</th>
	        <th data-options="field:'toFrom'" width="10%">渠道编号</th>
	       	<th data-options="field:'toFromName'" width="10%">渠道名称</th>
	        <th data-options="field:'_operate',align:'center'" width="5%" formatter="formatOper">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drChannelInfoOrderTools" style="padding:5px;height:750">
	  	<form id="drChannelInfoOrderForm">
	  		注册时间:<input id="ssearchDrChannelInfoOrderStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchDrChannelInfoOrderEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		手机号码:<input id="searchDrChannelInfoOrderPhone" name="mobilephone" class="easyui-textbox"  size="15" style="width:100px"/>
	    	<a id="searchFistDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	<div id="updateDrProductInfoDialog" class="easyui-dialog" title="变更渠道"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateDrProductInfoBtn'" style="width:700px;height:400px;padding:5px;">
		<form id="channelUpdate" >
			<table align="center" id = "updateDrProductInfoDialogTab">
						<!-- <input id="channelUid" name="uid" type="hidden" class="easyui-textbox"/> -->
						<span id = "channelUid"></span>
				<tr>
					<td align="center">当前渠道名称：</td>
					<td>
						<!-- <input id="channelName" type="text" class="easyui-textbox"/> -->
						<span id="channelName"></span>
					</td>
					<td align="center">当前渠道号：</td>
					<td>
						<!-- <input id="channelCode"  type="text" class="easyui-textbox"/> -->
						<span id="channelCode"></span>
					</td>
				</tr>
				<tr>
					<td align="center">变更渠道号：</td>
					<td>
					<input id="newChannelCode" class="easyui-combobox" data-options="valueField:'name',textField:'code',url:'../channel/getDrChannelInfoList.do',
						onChange: function (n,o) {
							var newChannelName = $('#newChannelCode').combobox('getValue');
							var newChannelCode = $('#newChannelCode').combobox('getText');
							$('#newChannelName').text(newChannelName);
							//$('#changeCode').textbox('setValue',newChannelCode);
						}" />
					</td>
				</tr>
				
				<tr>
					<td align="center"  id="raiseDeadline1">变更渠道名称：</td>
					<td>
						<!-- <input id="newChannelName" name="name" type="text"  class="easyui-textbox"/> -->
						<span id = "newChannelName"></span>
					</td>
				</tr>
			</table>
		</form>
		<div id="updateDrProductInfoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="commitChannelUpdate()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">

	//添加基本操作链接
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoForElseBtn('+index+')">变更</a>';
	}
	//打开dialog
	function updateDrProductInfoForElseBtn(index){  
		$('#drChannelInfoOrderListFirst').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drChannelInfoOrderListFirst').datagrid('getSelected'); 
		var url = "${apppath}/channel/getDrChannelInfoList.do";
		/* $("#channelName").textbox('setValue',row.name);
		$("#channelCode").textbox('setValue',row.code); */
		$("#channelName").text(row.toFrom);
		$("#channelCode").text(row.toFromName);
		/* $("#channelUid").textbox('setValue',row.uid); */
		$("#channelUid").val(row.uid);
		$("#updateDrProductInfoDialog").dialog("open");
	}
	
	function closeDialog(){
		$("#updateDrProductInfoDialog").dialog("close");
	}
	
	//提交变更
	function commitChannelUpdate(){
		var newChannelCode = $("#newChannelCode").combobox('getText');
		$.ajax({
          	url: "${apppath}/channel/drChannelInfoOrderUpdate.do",
            type: 'POST',
            data:{
            	code: $("#newChannelCode").combobox('getText'),
            	uid: $("#channelUid").val(),
            },
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drChannelInfoOrderListFirst").datagrid("reload");
					$("#updateDrProductInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });
		
	}

	//重置按钮
	$('#resetDrChannelInfoOrder').click(function(){
		$("#drChannelInfoOrderForm").form("reset");
		$("#drChannelInfoOrderListFirst").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchFistDrChannelInfoOrder').click(function(){
		//渠道用户
		$('#drChannelInfoOrderListFirst').datagrid({
			url:"../channel/drChannelInfoOrderUpdateList.do",
			queryParams: {
				startDate: $('#ssearchDrChannelInfoOrderStartDate').datebox('getValue'),
				endDate: $('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				mobilephone: $('#searchDrChannelInfoOrderPhone').val(),
			
			}
		});
	});
	
	
	
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

