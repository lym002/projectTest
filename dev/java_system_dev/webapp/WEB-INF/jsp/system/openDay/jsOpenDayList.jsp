<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
	<table id="table" title="期数管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../openDay/jsOpenDayList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#tools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">期数</th>
	    	<th data-options="field:'openDayName'">期数</th>
	    	<th data-options="field:'openDayDate'" width="10%" formatter="iFormatDateBoxFull">活动日期</th>
	        <th data-options="field:'subscribeStartDate'" width="10%" formatter="iFormatDateBoxFull">预约开始时间</th>
	        <th data-options="field:'subscribeEndDate'" width="10%" formatter="iFormatDateBoxFull">预约结束时间</th>
	       	<th data-options="field:'status'" width="5%" formatter="formaterProductPrizeStatus">状态</th>
	        <th data-options="field:'imgUrl'" width="6%" formatter="imgFormatter">图片</th>
	        <th data-options="field:'_operate',align:'center'" width="7%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="tools" style="padding:5px;height:750">
	  	<form id="form" target="_blank" method="post">
	  		期数:<input id="openDayName" name="openDayName" class="easyui-textbox"  size="15" style="width:200px"/>
	                   活动时间:<input id="startDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="endDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		<a id="search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDialog()">新增</a>
	    </form>
	</div>
	
	<div id="addDialog" class="easyui-dialog" title="添加"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addBtn'" 
		style="width:500px;height:300px;padding:5px;">
		<form id="addForm" enctype="multipart/form-data">	
			<table align="center">
				<tr>
					<td align="left">期数:</td>
					<td >
						<input id ="openDayName" name="openDayName" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				 <tr>
					<td align="left">活动时间:</td>
					<td >
						<input id="openDayDate" name="openDayDate" class="easyui-datebox" style="width:100px" data-options="required:true"/>
					</td>
				</tr> 
			 	<tr>
					<td align="left">列表图:</td>
					<td >
						<input type="file" name="img" id="img"/>
					</td>
				</tr>
				<tr>
					<td align="left">
					<input type="checkbox" id="lsl" name="status" onclick="checkValue()">
					预约:</td>
					<td>
						<input id="subscribeStartDate" name="subscribeStartDate" class="easyui-datebox" style="width:100px"/>-
						<input id="subscribeEndDate" name="subscribeEndDate" class="easyui-datebox" style="width:100px"/>
					</td>
				</tr> 
			</table>
		</form>
		<div id="addBtn" style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="add()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="updateDialog" class="easyui-dialog" title="修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateBtn'" 
		style="width:500px;height:300px;padding:5px;">
		<form id="updateForm" enctype="multipart/form-data">	
			<table align="center">
				<tr>
					<td align="left">期数:</td>
					<td >
						<input type="hidden" name="id" id="id">
						<input id ="openDayNameNew" name="openDayName" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				 <tr>
					<td align="left">活动时间:</td>
					<td >
						<input id="openDayDateNew" name="openDayDate" class="easyui-datebox" style="width:100px" 
							formatter="iFormatDateBoxFull" data-options="required:true"/>
					</td>
				</tr> 
			 	<tr>
					<td align="left">列表图:</td>
					<td >
						<input type="file" name="img" id="img" onchange="PreviewImage(this)" style="width:180px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"/>
					</td>
				</tr>
				<tr>
					<td align="left">
					<input type="checkbox" id="lslNew" name="status" onclick="updateCheckValue()">
					预约:</td>
					<td>
						<input id="startTime" name="subscribeStartDate" class="easyui-datebox" style="width:100px" formatter="iFormatDateBoxFull"/>-
						<input id="endTime" name="subscribeEndDate" class="easyui-datebox" style="width:100px" formatter="iFormatDateBoxFull"/>
					</td>
				</tr> 
			</table>
		</form>
		<div id="updateBtn" style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateSubmit()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	<div id="picShow" class="easyui-dialog" title="查看图片" style="height:auto;width:550px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="500px" height="auto" />
			</div>
			<!-- 显示图片 -->
		</div>
	<!-- 预约列表 -->
	<div id="logList" class="easyui-dialog" style="height:400px;width:600px" closed="true"	buttons="addBtn"  
			data-options="resizable:true,modal:true,closed:true">
	
		<table id="logTable" class="easyui-datagrid" style="height:99%;width:99.9%">
		</table>
		<div id="logTools" style="padding:5px;height:750">
			<form id="logForm" target="_blank" method="post">
						<input type="hidden" id="openDayId" name="openDayId"/>	
					<a id="exlog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
			<!-- <span style='color: #0015FF;'>共</span><span id='jsOpenDaylog' style='color: red;'></span><span style='color: #0015FF;'>笔</span> -->
			</form>
		</div>
	</div>
	
<script type="text/javascript">

	//重置按钮
	$('#reset').click(function(){
		$("#form").form("reset");
		$("#list").datagrid("load", {});
	});
	//查询按钮
	$('#search').click(function(){
 		$('#table').datagrid({
			queryParams: {
				openDayName:$('#openDayName').val(),
				startDate:$('#startDate').datebox('getValue'),
				endDate:$('#endDate').datebox('getValue')
			}
		});
	});
	
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	

	
	
	//导出预约表表
	$('#exlog').click(function(){
		var openDayId = $("#openDayId").val();
		var url= "${apppath}/openDay/exportOpenDayLog.do?openDayId="+openDayId; 
		window.location.href = url;
	}); 
	
	//操作链接
	function formatOper(val,row,index){ 
		if(row.status == 1){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">编辑</a>'+"   "+
            '<a href="#" class="btn l-btn l-btn-small" onclick="openLog('+index+')">查看预约</a>'+"   "+
            '<a href="#" class="btn l-btn l-btn-small" onclick="soldOut('+index+')">已结束</a>'+"   ";
		}else{
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">编辑</a>'+"   "+
            '<a href="#" class="btn l-btn l-btn-small" onclick="openLog('+index+')">查看预约</a>'+"   ";
		}
				
    }
	
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	}

function openLog(index){
	$('#table').datagrid('selectRow',index);
	 var row = $('#table').datagrid('getSelected'); 
		$('#openDayId').val(row.id);
			$('#logList').dialog('open').dialog('setTitle', '预约列表');
			var logTable = $('#logTable');
			logTable.datagrid({
				url : "${apppath}/openDay/jsOpenDayLogList.do?openDayId="+row.id,
				fitColumns : true,
				pagination : true,
				checkbox:true,
				pageSize:10,
				pageList:[10,20,25,50],
				autoRowHeight : true,
				toolbar:"#logTools",
				fit:true,
				columns : [ [ 
					{
					field : 'userName',
					title : '用户姓名',
					width : '15%',
					align : "center"
					},{
					field : 'mobilePhone',
					title : '手机号',
					width : '15%',
					align : "center"
					},{
					field : 'recommCodes',
					title : '推荐码',
					width : '10%',
					align : "center"
					},{
					field : 'gender',
					title : '性别',
					width : '10%',
					align : "center"
					},{
					field : 'city',
					title : '城市',
					width : '20%',
					align : "center"
					},{
					field : 'appointmentTime',
					title : '预约时间',
					width : '30%',
					formatter:iFormatDateTimeBoxFull,
					align : "center"	
					} ]]
			});
			
		}
		
	
	function formaterProductPrizeStatus(value,row,index){
		if(row.status == 0){
			return '未开始';
		}else if(row.status == 1){
			return '预约中';
		}else if( row.status == 2 ){
			return '已结束';
		}
	}
	//显示图片
	function imgFormatter(value,row,index){
		if(row.imgUrl !=null){
    	return '<img style="width:30px; height:30px" onclick="openPic(this)" lsl="'+row.imgUrl+'" src="' + value + '">';
		}
	}
 	//添加
	function addDialog(){
		$("#addForm").form("reset");
		$("#addDialog").dialog("open");
	}
	
	function add(){
		var flag = $("#addForm").form('enableValidation').form('validate');
		if(flag){
			$("#addForm").ajaxSubmit(
				{
					url : "${apppath}/openDay/addJsOpenDay.do",
					type : "POST",
					data : $("#addForm").serialize(),
					success : function(data) {
					var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if (resultJson.success) {
						$.messager.alert("提示信息",resultJson.msg,"",function(){
							$("#addForm").form("reset");// 提交后重置表单
							$("#addDialog").dialog("close");
							$("#table").datagrid("reload", {});
						});
					} else {
						$.messager.alert("提示信息",resultJson.errorMsg,"",function(){
							$("#addForm").form("reset"); // 提交后重置表单
							$("#addDialog").dialog("close");
							$("#table").datagrid("reload", {});
						});
					}
				}
			});
			return false; // 阻止表单自动提交事件
		} 
	}
	
	
	//修改
	function update(index){
		$('#table').datagrid('selectRow',index);
		 var row = $('#table').datagrid('getSelected'); 
		 var url = "${apppath}/openDay/getJsOpenDayById.do?id="+row.id;
		 $("#id").attr("value",row.id);
		 $.ajax({
			 url:url,
			 dataType : "json",
			 success:function(data){
				 $("#updateForm").form("reset");
				 $("#openDayNameNew").textbox('setValue',data.map.jsOpenDay.openDayName);
				 var openDayDate = iFormatDateBoxFull(data.map.jsOpenDay.openDayDate);
				 $("#openDayDateNew").datebox('setValue', openDayDate);
					 $('#lslNew').removeAttr("disabled");
				 if(data.map.jsOpenDay.status == 1 ){
					 $("#lslNew").prop("checked",true);
				 }else if(data.map.jsOpenDay.status == 2){
					 $("#lslNew").prop("checked",false);
					 $("#lslNew").prop("disabled",true);
				 }else{
					 $("#lslNew").prop("checked",false);
				 } 
				 $("#statusNew").attr('value',data.map.jsOpenDay.status);
				 var subscribeStartDate = iFormatDateBoxFull(data.map.jsOpenDay.subscribeStartDate);
				 $("#startTime").datebox('setValue',subscribeStartDate);
				 var subscribeEndDate = iFormatDateBoxFull(data.map.jsOpenDay.subscribeEndDate);
				 $("#endTime").datebox('setValue',subscribeEndDate);
				 $("#updateDialog").dialog("open");
			 }
		 });
	}
	
	
	function updateSubmit(){
		var flag = $("#updateForm").form('enableValidation').form('validate');
		if(flag){
			$("#updateForm").ajaxSubmit(
				{
					url : "${apppath}/openDay/update.do",
					type : "POST",
					data : $("#updateForm").serialize(),
					success : function(data) {
					var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if (resultJson.success) {
							$.messager.alert("提示信息",resultJson.msg);
							 $("#updateForm").form("reset"); // 提交后重置表单
							$("#updateDialog").dialog("close");
							$("#updateForm").form("reset"); 
							$("#table").datagrid("reload", {});
					} else {
						$.messager.alert("提示信息",resultJson.errorMsg);
							 $("#updateForm").form("reset");// 提交后重置表单
							$("#updateDialog").dialog("close");
							$("#updateForm").form("reset");
							$("#table").datagrid("reload", {});
					}
				}
			});
			return false; // 阻止表单自动提交事件
		} 
	}
	
	//添加的时候赋值
	function checkValue(){
		if($("#lsl").is(":checked")){
			$("#lsl").attr("value",1);
		}else{
			$("#lsl").attr("value",0);
		}
	}
	
//修改的时候赋值
	function updateCheckValue(){
		if($("#lslNew").is(":checked")){
			$("#lslNew").attr("value",1);
		}else{
			$("#lslNew").attr("value",0);
		}
	}
	
	//已结束
	function soldOut(index){
		$('#table').datagrid('selectRow',index);
		 var row = $('#table').datagrid('getSelected');
		 var url = "${apppath}/openDay/soldOut.do?id="+row.id;
		 $.ajax({
			 url:url,
			 dataType:'json',
			 success:function(data){
					if (data.success) {
						$.messager.alert("提示信息",data.msg);
						$("#table").datagrid("reload", {});
					} else {
						$.messager.alert("提示信息",data.errorMsg);
					}
			 }
		 });
	}

	//关闭Dialog
	function closeDialog(){  
		$("#table").datagrid("reload");
		$(".easyui-dialog").dialog("close");
		$("#updateForm").form("reset"); // 提交后重置表单
		$("#addForm").form("reset");// 提交后重置表单
	}
	
	//放大图片
	function openPic(ths){
		if($(ths).attr("lsl") == ""){
			return false;
		}
		if(typeof($(ths).prop("src")) == "undefined" || $(ths).prop("src") == "undefined"){
		   	var fileId = $(ths).attr("v");
		   	var dFile = $("#"+fileId);
		       if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    
		     	{    
				dFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("imglook");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
		     	}else{
				$("#imglook").attr("src",window.URL.createObjectURL(dFile.prop('files')[0]));
		     	}  
			$('#picShow').dialog('open');
			return true;
			}else{
				$("#imglook").attr("src",$(ths).prop("src"));
				$('#picShow').dialog('open');
			}
  }
	
	function PreviewImage(pcPutImg){
		if (pcPutImg.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(pcPutImg.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            pcPutImg.value = "";  
            return false;  
        }
		return true;
	}
	
</script>
</body>
</html>

