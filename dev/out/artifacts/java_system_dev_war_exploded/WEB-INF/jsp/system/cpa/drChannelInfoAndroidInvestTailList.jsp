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
	<table id="drChannelInfoList" title="安卓渠道下载包用户行为跟踪" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../androidInvestTail/queryAndroidInvestTailList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drChannelInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'mobilephone'" width="10%">用户手机号</th>
	    	<th data-options="field:'toFrom'" width="10%">渠道来源</th>
	    	<th data-options="field:'isReg'" width="10%">是否注册</th>
	    	<th data-options="field:'isBuildCard'" width="10%">是否绑卡</th>
	        <th data-options="field:'sumAmount'" width="10%">投资额</th>
	        <th data-options="field:'investTime'" width="10%" formatter="formatDateBoxFull">投资时间</th>
	         <th data-options="field:'regDate'" width="10%" formatter="formatDateBoxFull">注册时间</th>
			<!-- <th data-options="field:'_operate',align:'center'" width="20%" formatter="formatOper">基本操作</th> -->
	    </tr>
	    </thead>
	</table>
	<div id="drChannelInfoTools" style="padding:5px;height:750">
	  	<form id="drChannelInfoForm">
	  		用户手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		渠道来源:<input id="toFrom" name="toFrom" class="easyui-textbox"  size="15" style="width:100px"/>
	  		渠道名称:
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
	  		是否绑卡:<select  id="isBuildCard" name="isBuildCard" style="width:100px;" class="easyui-combobox">
					<option value=''>全部</option>
					<option value='1'>是</option>
					<option value='2'>否</option>
         		  </select>
	  		是否注册:<select  id="isReg" name="isReg" style="width:100px;" class="easyui-combobox">
					<option value=''>全部</option>
					<option value='1'>是</option>
					<option value='2'>否</option>
         		  </select>
         	投资额：<input id="startInvestMoney" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
         	至：<input id="endInvestMoney" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
         	注册时间:<input id="searchDrChannelInfoOrderStartDate" name="infoOrderStartDate" class="easyui-datebox" style="width:100px"/>
	  		至：<input id="searchDrChannelInfoOrderEndDate" name="infoOrderEndDate" class="easyui-datebox" style="width:100px"/>
	  		投资时间:<input id="startInvestMoneyDate" name="startMoneyDate" class="easyui-datebox" style="width:100px"/>
	  		至：<input id="endInvestMoneyDate" name="endMoneyDate" class="easyui-datebox" style="width:100px"/>
	    	<a id="searchDrChannelInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrChannelInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="searchFistDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">首投查询</a>
	    	<a id="searchAgainDrChannelInfoOrder" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" >复投查询</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" onclick="downloadDetail()">导出</a>
	    </form>
	</div>
	
	
<script type="text/javascript">
	//重置按钮
	$('#resetDrChannelInfo').click(function(){
		$("#drChannelInfoForm").form("reset");
		$("#drChannelInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrChannelInfo').click(function(){
		var cid = $('#cid').combogrid('getValues')+"";
 		$('#drChannelInfoList').datagrid({
 			url:"../androidInvestTail/queryAndroidInvestTailList.do",
			queryParams: {
				mobilePhone: $('#mobilePhone').val(),
				toFrom: $('#toFrom').val(),
				isBuildCard:$('#isBuildCard').combobox('getValue'),
				isReg:$('#isReg').combobox('getValue'),
				startInvestMoney: $('#startInvestMoney').val(),
				endInvestMoney:$('#endInvestMoney').val(),
				searchDrChannelInfoOrderStartDate: $('#searchDrChannelInfoOrderStartDate').datebox('getValue'),
				searchDrChannelInfoOrderEndDate:$('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				startInvestMoneyDate: $('#startInvestMoneyDate').datebox('getValue'),
				endInvestMoneyDate:$('#endInvestMoneyDate').datebox('getValue'),
				cid:cid,
			}
		}); 
	});
	
	//导出
	function downloadDetail(){
		window.location.href="../androidInvestTail/downloadDetail.do?mobilePhone="+$('#mobilePhone').val()
				+"&toFrom="+$('#toFrom').val()+"&isBuildCard="+$('#isBuildCard').combobox('getValue')
				+"&isReg="+$('#isReg').combobox('getValue')+"&startInvestMoney="+$('#startInvestMoney').val()
				+"&endInvestMoney="+$('#endInvestMoney').val()
				+"&searchDrChannelInfoOrderStartDate="+$('#searchDrChannelInfoOrderStartDate').datebox('getValue')
				+"&searchDrChannelInfoOrderEndDate="+$('#searchDrChannelInfoOrderEndDate').datebox('getValue')
				+"&startInvestMoneyDate="+$('#startInvestMoneyDate').datebox('getValue')
				+"&endInvestMoneyDate="+$('#endInvestMoneyDate').datebox('getValue');
	}
	
	//首投
	$('#searchFistDrChannelInfoOrder').click(function(){
 		$('#drChannelInfoList').datagrid({
 			url:"../androidInvestTail/searchFirst.do",
			queryParams: {
				mobilePhone: $('#mobilePhone').val(),
				toFrom: $('#toFrom').val(),
				isBuildCard:$('#isBuildCard').combobox('getValue'),
				isReg:$('#isReg').combobox('getValue'),
				startInvestMoney: $('#startInvestMoney').val(),
				endInvestMoney:$('#endInvestMoney').val(),
				searchDrChannelInfoOrderStartDate: $('#searchDrChannelInfoOrderStartDate').datebox('getValue'),
				searchDrChannelInfoOrderEndDate:$('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				startInvestMoneyDate: $('#startInvestMoneyDate').datebox('getValue'),
				endInvestMoneyDate:$('#endInvestMoneyDate').datebox('getValue'),
			}
		}); 
	});
	
	
	//复投
	$('#searchAgainDrChannelInfoOrder').click(function(){
		$('#drChannelInfoList').datagrid({
			url:"../androidInvestTail/searchAgain.do",
			queryParams: {
				mobilePhone: $('#mobilePhone').val(),
				toFrom: $('#toFrom').val(),
				isBuildCard:$('#isBuildCard').combobox('getValue'),
				isReg:$('#isReg').combobox('getValue'),
				startInvestMoney: $('#startInvestMoney').val(),
				endInvestMoney:$('#endInvestMoney').val(),
				searchDrChannelInfoOrderStartDate: $('#searchDrChannelInfoOrderStartDate').datebox('getValue'),
				searchDrChannelInfoOrderEndDate:$('#searchDrChannelInfoOrderEndDate').datebox('getValue'),
				startInvestMoneyDate: $('#startInvestMoneyDate').datebox('getValue'),
				endInvestMoneyDate:$('#endInvestMoneyDate').datebox('getValue'),
			}
		}); 
	});
	/* //添加基本操作链接
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrChannelInfoBtn('+index+')">修改</a>&nbsp;&nbsp;&nbsp;'+
				'<a href="#" class="btn l-btn l-btn-small" onclick="uploadFile('+index+')">导入关键字</a>&nbsp;&nbsp;&nbsp;'+
				'<a href="#" class="btn l-btn l-btn-small" onclick="keyWordsListBtn('+index+')">查看关键字</a>';
	}  */
	

</script>
</body>
</html>
