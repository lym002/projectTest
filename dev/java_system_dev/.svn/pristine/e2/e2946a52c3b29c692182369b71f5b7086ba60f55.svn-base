<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="couponPutList" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../activity/getCouponPutList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#couponPutTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'"  hidden="true">ID</th>
	    	<th data-options="field:'name'" width="10%">名称</th>
	        <th data-options="field:'addtime'" width="10%" formatter="formatDate">导入时间</th>
	        <th data-options="field:'type'" width="6%" formatter="formatType">奖励类型</th>
	        <th data-options="field:'status'" width="6%" formatter="formatStatus">状态</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="couponPutTools" style="padding:5px;height:750">
	  	<form id="couponPutForm">
	  		导入文件名称:<input id="searchName" name="name" class="easyui-textbox"  size="15" style="width:200px"/>
	  		奖励类型:<select name="type" id="searchType" class="easyui-combobox" >
	  					<option value="" selected="selected">全部</option>
	  					<option value="1" >红包</option>
	  					<option value="2" >加息券</option>
	  					<option value="3" >体验金</option>
	  					<option value="4" >翻倍券</option>
<!-- 	  					<option value="5" >比例红包</option> -->
	  				</select>
	  		状态:<select name="status" id="searchStatus"  class="easyui-combobox" >
	  				<option value="" selected="selected">全部</option>
	  				<option value="0" >待校验</option>
	  				<option value="1" >校验失败</option>
	  				<option value="2" >待审核</option>
	  				<option value="3" >成功</option>
	  				<option value="4" >不通过</option>
	  			</select>
	  		导入时间:<input id="searchStartDate" name="startDate" class="easyui-datebox"  size="15" style="width:100px"/>
	  			-<input id="searchEndDate" name="endDate" class="easyui-datebox"  size="15" style="width:100px"/>
	    	<a id="searchCouponPutBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetCouponPutBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<c:if test="${isAudit != 1 }">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addCouponPutDialog()">新建奖励</a>
	    	</c:if>
	    </form>
	</div>
	<!-- 新增-->
	<div id="addCouponPutDialog" class="easyui-dialog" title="新建任务"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addCouponPutBtn'"
		style="width:400px;height:200px;padding:20px;">
		<form id="addCouponPutForm" enctype="multipart/form-data">
			<table align="center">
				<tr>
					<td >excle格式:</td>
					<td>
						|手机号|推荐码|数值|编号|(1个格1值)
					</td>
				</tr>
				<tr>
					<td >导入文件:</td>
					<td>
						<input id="fileImport" name="fileImport" style="width:200px"  type="file" >
						<div id="fileName"></div>
					</td>
				</tr>			
				<tr>
					<td >奖励类型:</td>
					<td>
						<select name="type" id="type" class="easyui-combobox" >
	  					<option value="1" >红包</option>
	  					<option value="2" >加息券</option>
	  					<option value="3" >体验金</option>
	  					<option value="4" >翻倍券</option>
<!-- 	  					<option value="5" >比例红包</option> -->
	  				</select>
					</td>
				</tr>				
							
			</table>
		</form>
		<div id="addCouponPutBtn"  style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addCouponPut()">导入</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">关闭</a>
		</div>
	</div>
	
	<div id="updateCouponPutWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:800px;height:400px;padding:10px;">
	</div>
<script type="text/javascript">
	var isResetUploadId = 0;
	function addCouponPut(){
		var validate = $("#addCouponPutForm").form("validate");
		if(!validate){
			return false;
		}
        var file = document.getElementById('fileImport').files[0];
        if (file == null) {
			$.messager.alert("提示信息",'文件不能为空'); 
        	return false;
        }
        var fileName = file.name;
        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
        //这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
        if (file_typename != '.xlsx') {
        	$.messager.alert("提示信息",'文件类型不是正确的.xlsx格式'); 
        	return false;
		}
		var fileSize = 0;
     	if (file.size > 1024 * 1024) {
				fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
			if (fileSize > 10) {
                   $.messager.alert("提示信息",'错误，文件超过10MB，禁止上传！');
                   return false;
	  		}
           	fileSize = fileSize.toString() + 'MB';
         }else {
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
         }
			//将文件名和文件大小显示在前端label文本中
			document.getElementById('fileName').innerHTML = "<span style='color:Blue'>文件名: " + file.name + ',大小: ' + fileSize + "</span>";
            
            var form = $("#addCouponPutForm");
            
			$("#addCouponPutForm").ajaxSubmit({
	            type: 'post', // 提交方式 get/post
	            url: "${apppath}/activity/addCouponPut.do?isResetUploadId="+isResetUploadId,
	            dataType:"json",
	            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
					if(result.success){
						$.messager.alert("提示信息",result.msg);
						$("#couponPutList").datagrid("reload");
						$("#addCouponPutDialog").dialog("close");
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}	
	        });
	        return false;// 阻止表单自动提交事件
	}
	
	//跳转到添加页面
	function addCouponPutDialog(){  
		$("#addCouponPutForm").form("reset");
		$("#addCouponPutDialog").dialog("open");
		isResetUploadId = 0;
	  	var title = "新建任务";
	  	$("#fileName").html("");
		$("#addCouponPutDialog").attr("title",title);	
	}


	function cancel(){
			$("#addCouponPutDialog").dialog("close");
	}
	//时间格式
	function formatDate(value,row,index){ 
		if(value == null){
			return "";
		}
		var unixTimestamp = new Date(value);  
		return unixTimestamp.toLocaleString();  
	}
		//修改显示状态信息
	function formatType(value,row,index){
		if(value == 1){
			return '红包';
		}else if(value == 2){
			return '加息券';
		}else if(value == 3){
			return '体验金';
		}else if(value == 4){
			return '翻倍券';
		}else if(value == 5){
			return '比例红包';
		}
	}
		//修改显示状态信息
	function formatStatus(value,row,index){
		if(value == 0){
			return '待校验';
		}else if(value == 1){
			return '校验失败';
		}else if(value == 2){
			return '待审核';
		}else if(value == 3){
			return '成功';
		}else if(value == 4){
			return '不通过';
		}
	}
	//重置按钮
	$('#resetCouponPutBtn').click(function(){
		$("#couponPutForm").form("reset");
		$("#couponPutList").datagrid("load", {});
	});
	//查询按钮
	$('#searchCouponPutBtn').click(function(){
 		$('#couponPutList').datagrid({
			queryParams: {
				name: $('#searchName').textbox("getValue"),
				type: $('#searchType').combobox("getValue"),
				status: $('#searchStatus').combobox("getValue"),
				startDate: $('#searchStartDate').datebox("getValue"),
				endDate: $('#searchEndDate').datebox("getValue")
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){
		var isAudit = '${isAudit}';
		var couponPutOper = '<a href="#" class="couponPutBtn" onclick="toSelectCouponPut('+index+')">查看明细</a>'+' ';
		if(isAudit == '1' && row.status ==2){
			couponPutOper += '<a href="#" class="couponPutBtn" onclick="toAuditCouponPut('+index+')">审核</a>';
		}else if(isAudit != '1' && row.status ==0){
			couponPutOper += '<a href="#" class="couponPutBtn" onclick="toCheckOutCouponPut('+index+')">校验数据</a>';
		}else if(isAudit != '1' && (row.status ==1 || row.status ==4)){
			couponPutOper += '<a href="#" class="couponPutBtn" onclick="toUpLoadCouponPut('+index+')">重新上传</a>';
		} 
		return couponPutOper;
	}
	//跳转审核
	function toAuditCouponPut(index){
		$('#couponPutList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponPutList').datagrid('getSelected'); 
	
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "审核优惠券发放",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activity/toChannelCouponPutDetail.do?id="+row.id+"&isAudit=1' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		
		mainTab.tabs("add",detailTab);
	}
	
	
	//校验数据
	function toCheckOutCouponPut(index){
		$('#couponPutList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponPutList').datagrid('getSelected');
	    $.ajax({
          	url: "${apppath}/activity/toCheckOutCouponPut.do",
            type: 'POST',
            data:{id:row.id},  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#couponPutList").datagrid("reload");
					$("#addCouponPutDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });
	}
	//重新上传
	function toUpLoadCouponPut(index){
		$('#couponPutList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponPutList').datagrid('getSelected');
	    isResetUploadId = row.id;
    	$("#fileName").html("");
	    var title = "重新上传";
		$("#addCouponPutDialog").attr("title",title);
		$("#addCouponPutForm").form("reset");
		$("#addCouponPutDialog").dialog("open");
	}
	//查看详情
	function toSelectCouponPut(index){
		$('#couponPutList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#couponPutList').datagrid('getSelected'); 
	
	   	$("#updateCouponPutWindow").window({
			title:"查看",
			href:"../activity/toChannelCouponPutDetail.do?id="+row.id
		});
		$("#updateCouponPutWindow").window("open");		
		$("#addCouponPutForm").form("reset");	
	}
	
</script>
</body>
</html>

