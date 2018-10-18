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
	<table id="drProductPrizeOrderShareList" title="礼品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../productPrize/jsProductOrderShareList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#jsProductPrizeTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	    	<th data-options="field:'pcImgUrl',align:'center'" width="10%" formatter="imgFormatter">PC图片</th>
	        <th data-options="field:'h5ImgUrl',align:'center'" width="10%" formatter="imgFormatter">H5图片</th>
	       	<th data-options="field:'sort',align:'center'" width="5%">权重</th>
	        <th data-options="field:'isShow',align:'center'" width="6%" formatter="formatIsShow">状态</th>
	        <th data-options="field:'addtime',align:'center'" formatter="formatDateBoxFull" width="12%">时间</th>	        
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsProductPrizeTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeOrderShareForm" target="_blank" method="post">
	                   状态:<select id="searchIsShow" name="isShow" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>		  					
		  					<option value='1'>显示</option>
		  					<option value='0'>隐藏</option>
	  				</select>
	  		<a id="searchJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoBtn()">新增</a>
	    </form>
	</div>
		<div id="picShow" class="easyui-dialog" title="查看图片" style="height:auto;width:550px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="500px" height="auto" />
			</div>
			<!-- 显示图片 -->
		</div>
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeOrderShareForm").form("reset");
		$("#drProductPrizeOrderShareList").datagrid("load", {});
	});
	//查询按钮
	$('#searchJsProductPrize').click(function(){
 		$('#drProductPrizeOrderShareList').datagrid({
			queryParams: {
				isShow:$('#searchIsShow').combobox('getValue')			}
		});
	});

	//操作链接
	function formatOper(val,row,index){  
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">编辑</a>';
			if(row.isShow ==1){
				oper += '<a href="#" class="btn l-btn l-btn-small" onclick="todelete('+index+')">删除</a>';
			}
				return	oper;
    }
    
    	//跳转到产品添加页面
	function addDrProductInfoBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "新增晒单",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/productPrize/toAddUpdateJsProductOrderShare.do?isAdd=1'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
    	//跳转到产品添加页面
	function update(index){
		$('#drProductPrizeOrderShareList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeOrderShareList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "编辑晒单",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/productPrize/toAddUpdateJsProductOrderShare.do?isAdd=0&id="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
    	//跳转到产品添加页面
	function todelete(index){
		$('#drProductPrizeOrderShareList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeOrderShareList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/productPrize/deleteJsProductOrderShare.do?id="+row.id,
	        type: 'POST',
	        dateType:"json",
	        success:function(result){
	        console.log(result);
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeOrderShareList").datagrid("load", {});
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		 });
	}

		//奖品状态	
	function formatIsShow(value,row,index){
		if(row.isShow == 1){
			return '显示';
		}else if(row.isShow == 0){
			return '已删除';
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

