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
<link rel="stylesheet" href="../js/kindeditor-4.1.5/themes/default/default.css" />
<link rel="stylesheet" href="../js/kindeditor-4.1.5/plugins/code/prettify.css" />
<script charset="utf-8" src="../js/kindeditor-4.1.5/kindeditor.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.5/lang/zh_CN.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.5/plugins/code/prettify.js"></script>
<style type="text/css">
.file {
    position: relative;
    display: inline-block;
    background: #D0EEFF;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
</style>
</head>
<body>
<div>
	<form id="addJsPublicWelfareForm" enctype="multipart/form-data">
			<input type="text" name="id" value="${obj.id }" hidden="hidden"/>
			<input type="text" name="isAdd" value="${isAdd }" hidden="hidden"/>
		<div title="" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" >
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
				
					<td align="left">活动时间：</td>
					<td align="left" >
						<input id="activityTime" name="activityTime"  value="${obj.activityTime }" class="easyui-textbox" data-options="required:true"/>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="left">状态：</td>
					<td align="left">
					   <select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value='1'  <c:if test="${obj.status ==1 }">selected="selected"</c:if>>启用</option>
		  					<option value='0' <c:if test="${obj.status ==0 }">selected="selected"</c:if>>未启用</option>
	  				</select>
					</td>
				</tr>
				
				<tr>
					<td align="left">PC头部banner:</td>
					<td align="left">
					   <input id="pcBanner" name="pcBanner" placeholder="pcBanner" value="${obj.pcBanner }"  class='easyui-textbox' data-options="required:true" style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('pcBanner')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" >
						<a href="javascript:;" class="file">选择文件
							<input  type="file" name='pcBannerFile'  id="pcBannerFile" onchange="addVerifyPrincipleImage(this,'pcBanner')" />
						</a>
					</td>
					<td align="left">H5头部banner:</td>
					<td align="left">
					   <input id="h5Banner" name="h5Banner" placeholder="h5Banner" value="${obj.h5Banner }"  class='easyui-textbox' data-options="required:true" style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('h5Banner')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" >
						<a href="javascript:;" class="file">选择文件
							<input  type="file" name='h5BannerFile'  id="h5BannerFile" onchange="addVerifyPrincipleImage(this,'h5Banner')" />
						</a>
					</td>
				</tr>
				
				<tr>
					<td align="left"> H5列表banner:</td>
					<td align="left">
					<input id="h5ListBanner" name="h5ListBanner" placeholder="h5ListBanner" value="${obj.h5ListBanner }"  class='easyui-textbox' data-options="required:true" style='width:100px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('h5ListBanner')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" >
						<a href="javascript:;" class="file">选择文件
							<input  type="file" name='h5ListBannerFile'  id="h5ListBannerFile" onchange="addVerifyPrincipleImage(this,'h5ListBanner')" />
						</a>
					</td>
					
				</tr>
				
				<tr>
					<td align="left">活动视频地址：</td>
					<td align="left">
						<input id="videoUrl" name="videoUrl" value="${obj.videoUrl }" class="easyui-textbox" />
					</td>
					<td></td>
					<td align="left">活动图片：</td>
					<td align="left">
						<input id="imgUrl" name="imgUrl" placeholder="imgUrl" value="${obj.imgUrl }" class='easyui-textbox' style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;' data-options="required:true"/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('imgUrl')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" >
						<a href="javascript:;" class="file">选择文件
							<input  type="file" name='imgUrlFile'  id="imgUrlFile" onchange="addVerifyPrincipleImage(this,'imgUrl')" />
						</a>
					</td>
				</tr>
				<tr>
					<td align="left">列表标题:</td>
					<td align="left" colspan="5">
						<input id="titleList" name="titleList"  value="${obj.titleList }" size="70%" class="easyui-textbox" data-options="required:true"/>
					</td>					
				</tr>
				<tr>
					<td align="left">文章标题:</td>
					<td align="left" colspan="5">
						<input id="title" name="title"  value="${obj.title }"  size="70%" class="easyui-textbox" data-options="required:true"/>
					</td>					
				</tr>
				<tr>
					<td align="left">摘要:</td>
					<td align="left" colspan="5">
						<input id="summary" name="summary"  value="${obj.summary }" class="easyui-textbox"  style="width:600px;height:100px" data-options="required:true,multiline:true,validType:'maxLength[200]'"/>
					</td>					
				</tr>
				<tr>
					<td align="left">内容:</td>
					<td align="left" colspan="5">
						<textarea id="content" name="content"   style="width:600px;height:200px"  class="easyui-validatebox" >${obj.content } </textarea>
					</td>					
				</tr>
			
			</table>
			
			<div title="活动现场图片" class="easyui-panel" style="width:100%;height:auto;padding:10px;"data-options="collapsible:true,region:'center',tools:'#productPicAddBtn'">
				<table id="addProductPicTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
					<tr>
						<th>选择图片</th>						
						<th>显示顺序(先显示数字小的)</th>
						<th align="center">操作</th>
					</tr>
					<c:forEach items="${extend}" var="v" varStatus="i">
						<tr>
							<td>
								<input name='jsActivityExtendPic[${i.index}].id' value="${v.id}" type="hidden"/>
								<input name='jsActivityExtendPic[${i.index}].imgUrl' value="${v.imgUrl}" type="hidden"/>
								<input type='file' name='extendFiles' onchange="updateVerifyImage(this)" style='width:240px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
							</td>
							<td>
					   	   			<input name="jsActivityExtendPic[${i.index}].showSort" value="${v.showSort}" type='text' class='easyui-numberbox' /> 
				   	   		</td>
				   	   		<td>
					   	   		<div class='btn-group'>
						   	   		<a href='javascript:void(0)' data-value="${v.imgUrl}" class='easyui-linkbutton' iconCls='icon-search' onclick="updatePreviewImage(this)">查看</a>
						   	   		<a href='javascript:void(0)' data-value="${v.id}" class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'>删除</a>
					   	   		</div>
				   	   		</td>
				   	   	</tr>
					</c:forEach>
				</table>
			</div>
			<div id="productPicAddBtn">
				<a href="javascript:void(0)" class="icon-add" onclick="addProductPic();"></a>
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
<div id="picShow" class="easyui-dialog" title="查看图片" style="height:auto;width:550px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglooks" src="" width="500px" height="auto" />
			</div>
			<!-- 显示图片 -->
</div>
	

<script type="text/javascript">

	function addDrProductInfo() {
		var flag = $("#addJsPublicWelfareForm").form('enableValidation').form('validate');
		
		$.messager.confirm('提交确认', '确定提交?', function(r){
			if (r){
				if(flag){
				
					$("#addJsPublicWelfareForm").ajaxSubmit(
						{
							url : "${apppath}/activityOffline/addUpdateJsPublicWelfare.do",
							type : "POST",
							dataType:"json",							
							success : function(resultJson) {
							if (resultJson.success) {
								$.messager.alert("提示信息",resultJson.msg,"",function() {
									var currTab = parent.$('#main-center').tabs('getTab',"公益活动");
									if (currTab != null) {
										var content = '<iframe scrolling="no" frameborder="0"  src="../activityOffline/toJsPublicWelfare.do" style="width:100%;height:100%;"></iframe>';
										parent.$('#main-center').tabs
												('update',{tab : currTab,
													options : {
														content : content
													// 新内容的URL
													}
												});
									}
									parent.$('#main-center').tabs('close',"${isAdd eq 1?'新增公益活动': '编辑公益活动'}");
								});
								$("#addJsPublicWelfareForm").resetForm(); // 提交后重置表单
							} else {
								$.messager.alert("提示信息",resultJson.errorMsg);
							}
						}
					});
					return false; // 阻止表单自动提交事件
				}
			}else{
				return false;
			}
		});
		
	}

  
  	//查看图片
	function openPic(ths){
		var src = $('#'+ths).val();
		
		if(src ==null || src == ''){
			$.messager.alert("提示信息","请上传图片");
			return ;		
		}
		$("#imglooks").attr("src",src);
		$('#picShow').dialog('open');
		
 	}
  //图片预览
	function updatePreviewImage(ths) {
		if(typeof($(ths).data("value")) == "undefined" || $(ths).data("value") == "undefined"){
			var dFile = $(ths).parents('tr').find('input[type="file"]');
		    if (dFile.val() == "" ) {
		        $.messager.alert("提示信息","请上传图片");
		        return false;
		    }
			var type = dFile.val().substring(dFile.val().lastIndexOf(".") + 1, dFile.val().length).toLowerCase();
		    if (type != "jpg" && type != "bmp" && type != "gif" && type != "png") {
		        $.messager.alert("提示信息","请上传正确的图片格式");
		        return false;
		    }
	        if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    
	      	{    
				dFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("imglooks");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
	      	}else{
				$("#imglooks").attr("src",window.URL.createObjectURL(dFile.prop('files')[0]));
	      	}  
			$('#picShow').dialog('open');
			return true;
		}else{
			$("#imglooks").attr("src",$(ths).data("value"));
			$('#picShow').dialog('open');
		}
	}
  
  	//验证图片
	function addVerifyPrincipleImage(ths,id) {
		if (ths.value == "") {  
			 $('#'+id).textbox('setValue',"");
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    } 
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            ths.value = "";  
            return false;  
        }
         if(ths){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {   
				ths.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(ths.files)  
                {  
                	$('#'+id).textbox('setValue',window.URL.createObjectURL(ths.files[0]));
                	return true;
                }  
             }
         }  
        $('#'+id).textbox('setValue',ths.value);
    	return true;
	}
	
	//验证图片
	function updateVerifyImage(ths) {
		if (ths.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    } 
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(ths.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            ths.value = "";  
            return false;  
        }
        $(ths).prev('input').val('use');
        $(ths).parents('tr').find('a').eq(0).data("value", "undefined");
    	return true;  
	}
	

	
	var i = '${extendSize}';
		//添加图片
	function addProductPic(){
		var tr = "<tr><td>"+
			
			"<input name=\"jsActivityExtendPic["+i+"].imgUrl\"  type='hidden'/>"+
			"<input type='file' name='extendFiles' onchange=\"updateVerifyImage(this)\" style='width:180px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
	   	   	"<td><input name=\"jsActivityExtendPic["+i+"].showSort\" type='text' class='easyui-numberbox'  data-options='min:0,max:20,required:true'/>" +
	   	   	"</td>"+
   	   		"<td><div class='btn-group'>" +
   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-search' onclick=\"updatePreviewImage(this)\">"+
   	   		"查看</a>"+" "+"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'> " +
   	   		"删除</a></div></td></tr>"; 
		$("#addProductPicTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-numberbox').numberbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		});
		i++;
	}
	
	function delPic(obj){
		var id= $(obj).data("value");
		if(typeof(id) == "undefined"){
			$(obj).parent().parent().parent().remove();
		}else{
			$.ajax({
				url: '${apppath}/activityOffline/deleteJsacExtendPic.do?id='+id,
				dataType:'json',
				success:function(result){
					if(result.success){
						$(obj).parent().parent().parent().remove();
					}else{
						$.messager.alert("提示信息",result.errorMsg);
					}
				}
	 		});
		}
	}
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[id="content"]', {
			cssPath : '/js/kindeditor-4.1.5/plugins/code/prettify.css',
			uploadJson : '../article/file_upload.do',
			allowFileManager : true,
			items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "code", "cut", "copy", "paste", 
			"plainpaste", "wordpaste",
"|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", 
"indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", 
"formatblock", "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", 
"lineheight", "removeformat", "|", "image", "flash", "media", "insertfile", "table", "hr", "emoticons",
 "baidumap", "pagebreak", "anchor", "link", "unlink"],
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					//document.forms['example'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					//document.forms['example'].submit();
				});
			},
			afterChange : function() {
				this.sync();
			}
		});
		prettyPrint();
	});
	
</script>

</body>
</html>
