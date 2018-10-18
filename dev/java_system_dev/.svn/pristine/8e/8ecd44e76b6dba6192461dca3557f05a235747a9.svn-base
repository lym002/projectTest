﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
<div>
	<form id="addJsProductPrizeForm" enctype="multipart/form-data">
		<input type="text" name="id" value="${obj.id }" hidden="hidden"/>
			<input type="text" name="isAdd" value="${isAdd }" hidden="hidden"/>
		<div title="" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" >
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">
					权重：<input id="sort" name="sort" type="text" value="${obj.sort }" class="easyui-numberbox" data-options="validType:'maxLength[5]',required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">
					   状态:<select id="isShow" name="isShow" class="easyui-combobox" style="width:100px">
		  					<option value='1'  <c:if test="${obj.isShow ==1 }">selected="selected"</c:if>>显示</option>
		  					<option value='0' <c:if test="${obj.isShow ==0 }">selected="selected"</c:if>>已删除</option>
	  				</select>
					</td>
				</tr>
				<tr>
				<td>pc图片:<input type="file" name="pcImgFile" id="pcImgFile" onchange="PreviewPcImage(this)" /></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
							</br><img src="${obj.pcImgUrl }" id="pcImgUrl"  height="160" width="300" onclick="openPic(this)" v="pcImgFile"/>pc图片
				</td>
				<td>h5图片:<input type="file" name="h5ImgFile" id="h5ImgFile" onchange="PreviewPcImage(this)"/></br><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
						</br><img src="${obj.h5ImgUrl }" id="h5ImgUrl"  height="160" width="300" onclick="openPic(this)" v="h5ImgFile"/>h5 图片</td>
				</tr>
			
			</table>
				<!-- 显示图片 -->
		<div id="addProductPicShow" class="easyui-dialog" title="查看图片" style="height:auto;width:700px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglook" src="" width="650px" height="auto" />
			</div>
			<!-- 显示图片 -->
		</div>
		</div>
		<div style="text-align:center;padding:30px 60px 30px 0px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfo();">提交</a>
		</div>
	</form>
</div>	
<div id="queryDrSubjectInfoWindow" class="easyui-window"
	data-options="closed:true,modal:true,minimizable:false,maximizable:false" style="padding:10px;">
</div>
<script type="text/javascript">

	function addDrProductInfo() {
		var flag = $("#addJsProductPrizeForm").form('enableValidation').form('validate');
		if(flag){
		$("#addJsProductPrizeForm").ajaxSubmit(
			{
				url : "${apppath}/productPrize/addUpdateJsProductOrderShare.do",
				type : "POST",
				dataType:"json",
				data : $("#addJsProductPrizeForm").serialize()+"&isAdd=${isAdd}",
				success : function(resultJson) {
				if (resultJson.success) {
					$.messager.alert("提示信息",resultJson.msg,"",function() {
						var currTab = parent.$('#main-center').tabs('getTab',"用户晒单");
						if (currTab != null) {
							var content = '<iframe scrolling="no" frameborder="0"  src="../productPrize/toJsProductOrderShare.do" style="width:100%;height:100%;"></iframe>';
							parent.$('#main-center').tabs
									('update',{tab : currTab,
										options : {
											content : content
										// 新内容的URL
										}
									});
						}
						parent.$('#main-center').tabs('close',"${isAdd eq 1?'新增晒单': '编辑晒单'}");
					});
					$("#addJsProductPrizeForm").resetForm(); // 提交后重置表单
				} else {
					$.messager.alert("提示信息",resultJson.errorMsg);
				}
			}
		});
		return false; // 阻止表单自动提交事件
		}
	}

	function PreviewPcImage(pcPutImg) {
		var file = pcPutImg.files[0];
		if (pcPutImg.value == "") {
			$.messager.alert("提示信息", "请上传图片");
			return false;
		}
		if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(pcPutImg.value)) {
			$.messager.alert("提示信息", "图片类型必须是.gif,jpeg,jpg,png中的一种");
			pcPutImg.value = "";
			return false;
		}
		var fileSize = 0;
     	if (file.size > 1024 * 1024) {
				fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
			if (fileSize > 5) {
                   $.messager.alert("提示信息",'错误，文件超过5MB，禁止上传！');
                   return false;
	  		}           	
         }
		if (pcPutImg) {
			if (window.navigator.userAgent.indexOf("MSIE") >= 1
					&& !(navigator.userAgent.indexOf("MSIE 10.0") > 0)) {
				pcPutImg.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters
						.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
			} else {
				if (pcPutImg.files) {
					var id = pcPutImg.id;
					if ("pcImgFile" == id) {
						$("#pcImgUrl").attr("src",
								window.URL.createObjectURL(pcPutImg.files[0]));
					} else if ("h5ImgFile" == id) {
						$("#h5ImgUrl").attr("src",
								window.URL.createObjectURL(pcPutImg.files[0]));
					}
				}
			}
		}
		return true;
	}
	
    
    function openPic(ths){
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
      		if(dFile.prop('files') && dFile.prop('files')[0]){
				$("#imglook").attr("src",window.URL.createObjectURL(dFile.prop('files')[0]));
      		}else if($(ths).attr("src")){
      			$("#imglook").attr("src",$(ths).attr("src"));
      		}else{
      			return;
      		}
      	}  
		$('#addProductPicShow').dialog('open');
		return true;
  }
</script>

</body>
</html>
