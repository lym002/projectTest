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
	<table id="drGiftCardSetUpList" title="兑换券管理" class="easyui-datagrid"
		style="height:99%;width:99.9%"
		data-options="singleSelect:true,
	    fitColumns:true, url: '../giftCardSetUp/drGiftCardSetUpList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#giftCardSetUpListTools'">
		<thead>
			<tr>
				<th data-options="field:'id'" hidden="hidden">ID</th>
				<th data-options="field:'name'" width="6%">礼品券名称</th>
				<th data-options="field:'code'" width="6%">礼品券编号</th>
				<th data-options="field:'channelName'" width="5%">渠道名称</th>
				<th data-options="field:'channelCode'" width="5%">渠道编号</th>
				<th data-options="field:'status'" formatter="formatStatus" width="4%">状态</th>
				<th data-options="field:'startTime'" formatter="formatDateBoxFull" width="8%">活动开始时间</th>
				<th data-options="field:'endTime'" formatter="formatDateBoxFull" width="8%">活动结束时间</th>
				<th data-options="field:'totalQty'" styler="styleColor" width="4%">兑发放总量</th>
				<th data-options="field:'useQty'" styler="styleColor" width="4%">已发数量</th>
				<th data-options="field:'validQty'" styler="styleColor" width="4%">未发数量</th>
				<th data-options="field:'failureQty'" styler="styleColor" width="4%">失效数量</th>
				<th data-options="field:'onceQty'" styler="styleColor" width="4%">单送数量</th>
				<th data-options="field:'addName'" width="4%">添加人</th>
				<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="8%">添加时间</th>
				<th data-options="field:'updateName'" width="4%">修改人</th>
				<th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="8%">修改时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">基本操作</th>
			</tr>
		</thead>
	</table>
	<div id="giftCardSetUpListTools" style="padding:5px;height:750">
		<form id="giftCardSetUpListForm">
			渠道名称：<select id="searchChannel" name="channel" style="width:150px" class="easyui-combobox">
					<option value='' selected="selected">全部</option>
					<c:forEach items="${channelList}" var="list">
						<option value='${list.id}'>${list.name}</option>
					</c:forEach>
			</select>
			礼品券名称：<input class="easyui-textbox" id="searchName" name="name" size="15" style="width:150px" />
			<a id="searchBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
			<a id="resetBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addGiftCardSetUpDialogBtn()">新增</a>
		</form>
	</div>

	<div id="addGiftCardSetUpDialog" class="easyui-dialog" title="新增兑换券规则" 
		data-options="iconCls:'icon-add',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addGiftCardSetUpBtn'"
		style="width:550px;height:200px;padding:20px;">
		<form id="addDrGiftCardSetUpFrom">
			<table align="center">
				<tr>
					<td>礼品券名称</td>
					<td><input type="text" id="addName" name="name" class="easyui-textbox" data-options="required:true" /></td>
					<td>渠道</td>
					<td><select id="addChannel" name="channelId" style="width:150px" class="easyui-combobox">
							<option value='' selected="selected">-请选择-</option>
							<c:forEach items="${channelList}" var="list">
								<option value='${list.id}'>${list.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>开始时间</td>
					<td><input id="addStartTime" name="startTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" /></td>
					<td>结束时间</td>
					<td><input id="addEndTime" name="endTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>单送数量</td>
					<td><input id="addOnceQty" name= "onceQty" style="width:150px" class="easyui-numberbox" data-options="required:true" /></td>
					<td/><td/>
				</tr>
			</table>
		</form>
		<div id="#addGiftCardSetUpBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addGiftCardSetUp()">添加</a>
		</div>
	</div>
	
	<div id="updateGiftCardSetUpDialog" class="easyui-dialog" title="修改兑换券规则"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateDrGiftCardSetUpBtn'" 
		style="width:550px;height:250px;padding:20px;">
		<form id="updateGiftCardSetUpForm">
			<input type="hidden" id="drGiftCardSetUpId"  name="id"/>
			<table align="center">
				<tr>
					<td>礼品券编号</td>
					<td><input type="text" id="updateCode" name="code" class="easyui-textbox" data-options="required:true" disabled="disabled"/></td>
					<td>礼品券名称</td>
					<td><input type="text" id="updateName" name="name" class="easyui-textbox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>渠道</td>
					<td><select id="updateChannel" name="channelId" style="width:150px" class="easyui-combobox" disabled="disabled">
							<option value='' selected="selected">-请选择-</option>
							<c:forEach items="${channelList}" var="list">
								<option value='${list.id}'>${list.name}</option>
							</c:forEach>
						</select>
					</td>
					<td></td><td></td>
				</tr>
				<tr>
					<td>开始时间</td>
					<td><input id="updateStartTime" name="startTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" /></td>
					<td>结束时间</td>
					<td><input id="updateEndTime" name="endTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>单送数量</td>
					<td><input id="updateOnceQty" name= "onceQty" style="width:150px" class="easyui-numberbox" data-options="required:true" /></td>
					<td/><td/>
				</tr>
			</table>
		</form>
		<div id="updateDrGiftCardSetUpBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateGiftCardSetUp()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="uploadFileDialog" class="easyui-dialog" title="导入礼品券"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#uploadFileDialogBtn'" style="width:400px;height:160px;padding:5px;">
		<form id="uploadFileDialogForm" enctype="multipart/form-data">
			<input type="hidden" id="uploadFileParentId"  name="parentId"/>
			<table align="center">
				<tr>
					<td>
						<input id="uploadFile" type="file" name="file" onchange="verifyFile(this)"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div id="uploadFileDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="uploadGiftCardDetail()">上传</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	<div id="#drCardDetailTools" style="padding:5px;height:450">
	  	<form id="drCardDetailForm">
	  		<input type="hidden" id="drCardId"  name="parentId"/>
	    </form>
	</div>
	<div id="cardDetailListDialog" class="easyui-dialog" title="礼品券明细"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:50%;height:60%;padding:5px;">
		<table id="drCardDetailList"
			class="easyui-datagrid" style="height:99%;width:99.9%"
			data-options="singleSelect:true,
			    fitColumns:true,
			    method:'post',rownumbers:true, 
			    pagination:true,toolbar:'#drCardDetailTools'">
				<thead>
			    <tr>
			    	<th data-options="field:'id'" hidden="true">序号</th>
			    	<th data-options="field:'giftCard'" width="15%">兑换券号</th>
			        <th data-options="field:'status'" width="15%" formatter="formatDetailStatus">状态</th>
			        <th data-options="field:'addName'" width="15%">添加人</th>
			        <th data-options="field:'addTime'" width="15%" formatter="formatDateBoxFull">添加时间</th>
			        <td/>
			    </tr>
			    </thead>
			</table>
	</div>
		
	<script type="text/javascript">
		//重置按钮
		$('#resetBtn').click(function(){
			$("#giftCardSetUpListForm").form("reset");
			$("#drGiftCardSetUpList").datagrid("load", {});
		});
		
		//查询按钮
		$('#searchBtn').click(function(){
	 		$('#drGiftCardSetUpList').datagrid({
				queryParams: {
					name:$('#searchName').val(),
					channelId:$('#searchChannel').combobox("getValue"),
				}
			}); 
		});
		
		//跳转到添加页面
		function addGiftCardSetUpDialogBtn(){  
			$("#addDrGiftCardSetUpFrom").form("reset");
			$("#addGiftCardSetUpDialog").dialog("open");
		}
	
		function addGiftCardSetUp(){
			var validate = $("#addDrGiftCardSetUpFrom").form("validate");
			if(!validate){
				return false;
			}
			var channel=$("#addChannel").combobox("getValue");
			if(channel==''){
				$.messager.alert("提示信息","请选择渠道！");
				return false;
			}
			
			$.ajax({
	         	url: "../giftCardSetUp/addGiftCardSetUp.do",
	           	type: 'POST',
	           	data:$("#addDrGiftCardSetUpFrom").serialize(),  
	           	success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#drGiftCardSetUpList").datagrid("reload");
						$("#addGiftCardSetUpDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	       	});
	        return false; // 阻止表单自动提交事件
		}
		
		
		
		//添加基本操作链接
		function formatOper(val,row,index){  
			if(0 != row.status){
				return '<a href="#" class="btn l-btn l-btn-small" onclick="updateGiftCardSetUpWindow('+index+')">修改</a>'+' '
					+'<a href="#" class="btn l-btn l-btn-small" onclick="importCardDetail('+index+')">导入</a>'+ ' '
					+'<a href="#" class="btn l-btn l-btn-small" onclick="showCardDetail('+index+')">查看</a>';  
			}else{
				return '<a href="#" class="btn l-btn l-btn-small" onclick="showCardDetail('+index+')">查看</a>';
			}
	    	
		} 
		//导入文件窗口
		function importCardDetail(index){
			$("#uploadFileDialogForm").form("reset");
			$('#drGiftCardSetUpList').datagrid('selectRow',index);
			var row = $('#drGiftCardSetUpList').datagrid('getSelected');
			$("#uploadFileParentId").val(row.id);
			$("#uploadFileDialog").dialog("open");
		}
		//验证文件
		function verifyFile(obj) {
			if (obj.value == "") {  
				$.messager.alert("提示信息","请上传文件");
		        return false;  
		    } 
	        if (! /\.(xls)$/.test(obj.value)) {  
	        	$.messager.alert("提示信息","文件类型必须是.xls");
	            obj.value = "";  
	            return false;  
	        }
	    	return true;  
		}
		
		//关闭添加渠道窗口
		function closeDialog(){  
			$("#drGiftCardSetUpList").datagrid("reload");
			$("#updateGiftCardSetUpDialog").dialog("close");
			$("#uploadFileDialog").dialog("close");
			$("#addGiftCardSetUpDialog").dialog("close");
		}
		
		//打开修改窗口
		function updateGiftCardSetUpWindow(index){  
			$('#drGiftCardSetUpList').datagrid('selectRow',index);// 关键在这里 
		    var row = $('#drGiftCardSetUpList').datagrid('getSelected'); 
			var url = "../giftCardSetUp/queryGiftCardSetUp.do?id="+row.id;
			$.ajax({
				url: url,
				dataType:"json",
				success:function(result){
					if(result.success){
						$("#drGiftCardSetUpId").val(result.map.drGiftCardSetUp.id);
						$("#updateCode").textbox('setValue',result.map.drGiftCardSetUp.code);
						$("#updateName").textbox('setValue',result.map.drGiftCardSetUp.name);
						$("#updateChannel").combobox('setValue',result.map.drGiftCardSetUp.channelId);
						$("#updateStartTime").datetimebox('setValue',formatDate(new Date(result.map.drGiftCardSetUp.startTime)));
						$("#updateEndTime").datetimebox('setValue',formatDate(new Date(result.map.drGiftCardSetUp.endTime)));
						$("#updateOnceQty").textbox('setValue',result.map.drGiftCardSetUp.onceQty);
						$("#updateGiftCardSetUpDialog").dialog("open");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
		  	});
		}
		
		//修改
		function updateGiftCardSetUp(){
			var validate = $("#updateGiftCardSetUpForm").form("validate");
			if(!validate){
				return false;
			}
			var channel=$("#updateChannel").combobox("getValue");
			if(channel=='' || channel=='undefined'){
				$.messager.alert("提示信息","请选择渠道！");
				return false;
			}
			
			$.ajax({
	         	url: "../giftCardSetUp/updateGiftCardSetUp.do",
	           	type: 'POST',
	           	data:$("#updateGiftCardSetUpForm").serialize(),  
	           	success:function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#drGiftCardSetUpList").datagrid("reload");
						$("#updateGiftCardSetUpDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	       	});
	        return false; // 阻止表单自动提交事件
		}
		
		
		//头状态信息
		function formatStatus(value,row,index){
			if(row.status == "1"){
				return '<a href="#" onclick="updateDrGiftCardSetUpStatus('+index+')">有效</a>';
			}else{
				return '失效';
			}
		}
		
		//明细状态信息
		function formatDetailStatus(value,row,index){
			if(row.status == "1"){
				return '<a href="#" onclick="updateDrGiftCardDetailStatus('+index+')">有效</a>';
			}else if(row.status == "2"){
				return '已发放';
			}
			return '失效';
		}
		
		//修改兑换券状态
		function updateDrGiftCardDetailStatus(index){
			$('#drCardDetailList').datagrid('selectRow',index);// 关键在这里 
		    var row = $('#drCardDetailList').datagrid('getSelected');
		    $.messager.confirm('操作提示', '你确定更改此兑换券吗？', function(r){
				if(r){
					var url = "../giftCardSetUp/updateDrGiftCardDetailStatus.do?id="+row.id+"&status="+row.status;
					$.ajax({
						url: url,
						dataType:"json",
						success:function(result){
							if(result.success){
								$.messager.alert("操作提示", result.msg);
								$("#drCardDetailList").datagrid('reload');
							}else{
								$.messager.alert("提示信息",result.errorMsg);
							}
						}
				  	});
				}
				return false;
			});
		}
		
		//修改头部状态
		function updateDrGiftCardSetUpStatus(index){
			$('#drGiftCardSetUpList').datagrid('selectRow',index);// 关键在这里 
		    var row = $('#drGiftCardSetUpList').datagrid('getSelected'); 
			$.messager.confirm('操作提示', '你确定更改吗？', function(r){
				if(r){
					var url = "../giftCardSetUp/updateGiftCardSetUpStatus.do?id="+row.id+"&status="+row.status;
					$.ajax({
						url: url,
						dataType:"json",
						success:function(result){
							if(result.success){
								$.messager.alert("操作提示", result.msg);
								$("#drGiftCardSetUpList").datagrid('reload');
							}else{
								$.messager.alert("提示信息",result.errorMsg);
							}
						}
				  	});
				}
				return false;
			});
		}
		
		//导入
		function uploadGiftCardDetail(){
			$("#uploadFileDialogForm").ajaxSubmit({
				url:"../giftCardSetUp/uploadGiftCardDetail.do",
				type:"POST",
				data:$("#uploadFileDialogForm").serialize(),
				success: function(result){
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#drGiftCardSetUpList").datagrid("reload");
						$("#uploadFileDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
			});
		}
		
		function showCardDetail(index){
			$("#drCardDetailForm").form("reset");
			$("#cardDetailListDialog").dialog("open");
			$('#drGiftCardSetUpList').datagrid('selectRow',index);
			var row = $('#drGiftCardSetUpList').datagrid('getSelected');
			$("#drCardId").val(row.id);
			$('#drCardDetailList').datagrid({
			    url: "${apppath}/giftCardSetUp/queryGiftCardDetail.do",
			    queryParams:{
			        parentId:$("#drCardId").val()
			    }
			});
		}
		
		function formatDate(now) { 
			var year=now.getYear(); 
			var month=now.getMonth()+1; 
			var date=now.getDate(); 
			var hour=now.getHours(); 
			var minute=now.getMinutes(); 
			var second=now.getSeconds(); 
			return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
		} 
		
	</script>
</body>
</html>