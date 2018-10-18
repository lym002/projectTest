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
	<table id="drProductPrizeOrderShareList" title="礼品管理"  style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	    	<th data-options="field:'mobilePhone'" width="10%">用户名</th>
	        <th data-options="field:'describes'" width="10%">TA说</th>
	       	<th data-options="field:'url',align:'center'" width="10%" formatter="imgFormatter">图片</th>
	        <th data-options="field:'isShow'" width="6%" formatter="formatIsShow">审核状态</th>
	        <th data-options="field:'addtime',align:'center'" formatter="formatDateBoxFull" width="12%">时间</th>	        
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsProductPrizeTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeOrderShareForm" target="_blank" method="post">
	                   状态:<select id="isShow" name="isShow" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0'>不显示</option>		  					
		  					<option value='1'>显示</option>
		  					<option value='2' selected="selected">待审核</option>
	  				</select>
	  		用户名:<input id="mobilePhone" name="mobilePhone" type="text" class="easyui-numberbox" style="width:100px"/>
	  		晒单时间:<input class="easyui-datebox" id="startAddtime" style="width:100px"/>
	  		至<input class="easyui-datebox" id="endAddtime" style="width:100px"/>

	  		<a id="searchJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" onclick="searchJsProductPrize()" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a id="exportJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" onclick="exportJsProductPrize()" iconCls="icon-redo">导出</a>
	    </form>
	</div>
		<div id="picShow" class="easyui-dialog" title="查看图片" style="height:auto;width:550px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="500px" height="auto" />
			</div>
			<!-- 显示图片 -->
		</div>
	
	<!-- 晒单审核 -->	
	<div id="updatePrizeOrderShare" class="easyui-dialog" style="height:40%;width:40%" closed="true"	buttons="updatePrizeOrderShareBtn"  data-options="resizable:true,modal:true,closed:true">
		<center>
			<div>
				<table>
					<tr>
						<td>TA说:<input type="hidden" id="id"></td>
						<td><input id="searchdescribes" class="easyui-textbox" data-options="multiline:true" style="width:200px;height:50px" disabled="disabled"></td>
						<td>晒单图片:</td>
						<td rowspan="6"><img id="searchurl" height="200" width="200" /> </td>
					</tr>
					<tr>
						<td>晒单用户:</td>
						<td><input id="searchmobilePhone" class="easyui-textbox" disabled="disabled"></td>
						<td></td>
					</tr>
					<tr>
						<td>晒单时间:</td>
						<td><input id="searchaddtime" class="easyui-textbox" disabled="disabled"></td>
						<td></td>
					</tr>
					<tr>
						<td>设置权重:</td>
						<td><input id="searchsort" class="easyui-numberbox"></td>
						<td></td>
					</tr>
					<tr>
						<td>审核状态:</td>
						<td>
							<select id="searchisShow" name="searchisShow" class="easyui-combobox" style="width:100px">
			  					<option value='0'>不显示</option>		  					
			  					<option value='1'>显示</option>
			  					<option value='2'>待审核</option>
		  					</select>
	  					</td>
						<td></td>
					</tr>
					<tr>
						<td>审核备注:</td>
						<td ><input id="searchremark" class="easyui-textbox" data-options="multiline:true" style="width:200px;height:50px"></td>
						<td></td>
					</tr>
				</table>
			</div>	
			<div id="updatePrizeOrderShareBtn" style="padding:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updatePrizeOrderShare').dialog('close')">取消</a>
			</div>
		</center>
	</div>
<script type="text/javascript">

	$(document).ready(function () {
		searchJsProductPrize();
	});
	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeOrderShareForm").form("reset");
		$("#drProductPrizeOrderShareList").datagrid("load", {});
	});
	//查询按钮
	function searchJsProductPrize(){
 		$('#drProductPrizeOrderShareList').datagrid({
 			url:"../productPrize/selectOrderShare.do",
 			fitColumns : true,
 			rownumbers:true,
 			showFooter: true,
 			pagination:true,
 			nowrap: false,
 			pageSize:25,
 			pageList:[25,50,100],
 			toolbar:"#jsProductPrizeTools", 
 			queryParams: {
				isShow:$('#isShow').combobox('getValue'),
				mobilePhone:$('#mobilePhone').textbox('getValue'),
				startAddtime:$('#startAddtime').datebox('getValue'),
				endAddtime:$('#endAddtime').datebox('getValue')
				}
		});
	};

	//操作链接
	function formatOper(val,row,index){  
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">审核</a>';
				return	oper;
    }
   
	function update(index){
		$('#drProductPrizeOrderShareList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeOrderShareList').datagrid('getSelected'); 
	    $("#id").val(row.id);
		$("#searchdescribes").textbox("setValue",row.describes);
		$("#searchmobilePhone").textbox("setValue",row.mobilePhone);
		$("#searchaddtime").textbox("setValue",formatDateBoxFull(row.addtime));
		$("#searchsort").numberbox("setValue",row.sort);
		$("#searchisShow").combobox("setValue",row.isShow);
		$("#searchremark").textbox("setValue",row.remark);
		$("#searchurl").attr("src",row.url);
		$('#updatePrizeOrderShare').dialog('open').dialog('setTitle', '晒单审核');
	}

		//奖品状态	
	function formatIsShow(value,row,index){
		if(row.isShow == 1){
			return '显示';
		}else if(row.isShow == 0){
			return '不显示';
		}else if(row.isShow == 2){
			return '待审核';
		}
	}
	
	//显示图片
	function imgFormatter(value,row,index){
		if(value !=null){
    	return '<img style="width:30px; height:30px" onclick="openPic(this)" lsl="'+value+'" src="' + value + '">';
		}
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
	
	//提交
	function save(){
		$.ajax({
			type: 'post',
			url:"../productPrize/updateOrderShare.do",
			data : {
				id : $("#id").val(),
				sort : $("#searchsort").numberbox("getValue"),
				isShow:$("#searchisShow").combobox('getValue'),
				remark:$("#searchremark").textbox('getValue')
			},
			success : function(result) {
				if(result.success){
					$("#updatePrizeOrderShare").dialog("close");
					searchJsProductPrize();
					$.messager.alert("提示信息",result.msg);
				}else{
					$('#drProductLoanList').datagrid('reload');
					$.messager.alert("提示信息",result.errorMsg);
				}
				
		    }
		 });
	}
	
	function exportJsProductPrize(){
		window.location.href="../productPrize/exportJsProductPrize.do?isShow="+$('#isShow').combobox('getValue')+
		"&mobilephone="+$('#mobilePhone').textbox('getValue')+
		"&startAddtime="+$('#startAddtime').datebox('getValue')+
		"&endAddtime="+$('#endAddtime').datebox('getValue');
	}
</script>
</body>
</html>

