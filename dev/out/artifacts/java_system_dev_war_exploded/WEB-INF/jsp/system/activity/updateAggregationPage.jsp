<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/include/util.jsp" %>
<link rel="stylesheet" href="../js/kindeditor-4.1.5/themes/default/default.css" />
<link rel="stylesheet" href="../js/kindeditor-4.1.5/plugins/code/prettify.css" />
<script charset="utf-8" src="../js/kindeditor-4.1.5/kindeditor.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.5/lang/zh_CN.js"></script>
<script charset="utf-8" src="../js/kindeditor-4.1.5/plugins/code/prettify.js"></script>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script type="text/javascript">
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

</head>
<body>
<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
<form id="addArticleForm" enctype="multipart/form-data">
	<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
			<input type = "hidden" name = "id" value = "${jsActivityAggregationPage.id}"/>
	<table >
		<tr>
			<td align="left">
					活动标题:
			</td>
			<td colspan="3">
				<input type="text" name="title" size="50" value = "${jsActivityAggregationPage.title}"  class="easyui-textbox" data-options="required:true" />
			</td>
			<td align="left">
					活动时间:
			</td>
			<td>
				<input type="text" name="activityDate" value = "${jsActivityAggregationPage.activityDate}"  style="width:100px;" class="easyui-textbox"/>
			</td>
		</tr>
		</table>
		</div>
		<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
		<table cellspacing="15px" >
		<tr>
			<td align="left" style="vertical-align: top;">
				PC图片:
			</td>
			<td align="left" style="vertical-align: top;">
				APP图片:
			</td>
		</tr>
		<tr>
			<td>		
					<input type="file" id="pcPic" name="pcPicFile" onchange="PreviewImage(this)" />
						<div style="color:red;">图片文件最大5MB，支持.jpg.jpeg的图片格式。</div>
					<img src="${jsActivityAggregationPage.pcPic}" id="pcPicSrc"  height="160" width="260" /> 
			</td>
			<td>		
					<input type="file" id="appPic" name="appPicFile" onchange="PreviewImage(this)" />
						<div style="color:red;">图片文件最大5MB，支持.jpg.jpeg的图片格式。</div>
					<img src="${jsActivityAggregationPage.appPic}" id="appPicSrc"  height="160" width="260" /> 
			</td>
		</tr>
		<tr>
			<td align="left" style="vertical-align: top;">
				活动PC链接:<input type="text" name="pcUrl" value = "${jsActivityAggregationPage.pcUrl}"  style="width:200px;" class="easyui-textbox"/>
			</td>
			<td align="left" style="vertical-align: top;">
				活动APP链接:<input type="text" name="appUrl" value = "${jsActivityAggregationPage.appUrl}"  style="width:200px;" class="easyui-textbox"/>
			</td>
		</tr>
		<tr>
			<td align="left" style="vertical-align: top;">
				活动状态:
				<select id="status" name="status">
								<c:choose>
								<c:when test="${jsActivityAggregationPage.status == 1}">
								<option value="1" selected="selected">进行中</option>
								<option value="2">已结束</option>
								<option value="3">活动暂定</option>
  								</c:when>
  								<c:when test="${jsActivityAggregationPage.status == 3}">
  								<option value="1" >进行中</option>
								<option value="2">已结束</option>
								<option value="3" selected="selected">活动暂定</option>
								</c:when>
								<c:otherwise>
								<option value="1">进行中</option>
								<option value="2" selected="selected">已结束</option>
								<option value="3">活动暂定</option>
  								</c:otherwise>
								</c:choose>
						</select>
						</td>
			<td align="left" style="vertical-align: top;">
				置顶:<select id="isTop" name ="isTop">
								<c:choose>
								<c:when test="${jsActivityAggregationPage.isTop == 0}">
								<option value="0" selected = "selected">不置顶</option>
                    			<option value="1">置顶</option>
  								</c:when>
								<c:otherwise>
								<option value="0">不置顶</option>
                    			<option value="1" selected = "selected">置顶</option>
  								</c:otherwise>
								</c:choose>
                 </select>
			</td>
			<td align="right" style="vertical-align: top;">
							终端类型:
					</td>
					<td align="left">
					<input id="PC" name="terminalTypePC" type="checkbox" value="1" <c:if test="${jsActivityAggregationPage.terminalTypePC == 1}"> checked="checked"</c:if>/>PC</td>
					<td align="left">
					<input id="H5" name="terminalTypeH5" type="checkbox" value="1" <c:if test="${jsActivityAggregationPage.terminalTypeH5 == 1}"> checked="checked" </c:if>/>H5</td>
					<td align="left">
					<input id="IOS" name="terminalTypeIOS" type="checkbox" value="1" <c:if test="${jsActivityAggregationPage.terminalTypeIOS == 1}"> checked="checked" </c:if>/>IOS</td>
					<td align="left">
					<input id="android" name="terminalTypeAndroid" type="checkbox" value="1" <c:if test="${jsActivityAggregationPage.terminalTypeAndroid == 1}"> checked="checked" </c:if>/>安卓</td>
		</tr>
	</table>
	<div style="text-align:center;padding:30px 60px 10px 0px">
		<a id="picAddBtn" href="javascript:void(0)" data-options="iconCls:'icon-save'"
		class="easyui-linkbutton" onclick="updateArticleBtn()">更新</a>
	</div>
	</div>
</form>
</div>

</body>
<script type="text/javascript">
	function updateArticleBtn(){
		var validate = $("#addArticleForm").form("validate");
		if(!validate){
			return false;
		}
		$("#addArticleForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '${apppath}/activity/updateAggregationPage.do', // 需要提交的 url
            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
            	var resultJson = jQuery.parseJSON(result);
				if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg,"",function(){
					var currTab = parent.$('#main-center').tabs('getTab', "活动聚合页管理");
						var content = '<iframe scrolling="no" frameborder="0"  src="../activity/toAllActivityList.do" style="width:100%;height:100%;"></iframe>';  
						parent.$('#main-center').tabs('update', {
							tab: currTab,
							options: {
								content: content  // 新内容的URL
							}
						});
					parent.$('#main-center').tabs('close','修改活动聚合页');
					});
				}else{
					$.messager.alert("提示信息",resultJson.errorMsg);
				};
            	$("#addArticleForm").resetForm(); // 提交后重置表单
            }
        });
        return false; // 阻止表单自动提交事件
	};

	//图片预览
	function PreviewImage(articleAddPicFile) {
		if (articleAddPicFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(articleAddPicFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            articleAddPicFile.value = "";  
            return false;  
        }
	    if(articleAddPicFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
				articleAddPicFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(articleAddPicFile.files)  
                {  
                	var id = articleAddPicFile.id;
                	if("pcPic" == id){
                		$("#pcPicSrc").attr("src",window.URL.createObjectURL(articleAddPicFile.files[0]));  
                	}else if("appPic" == id){
                		$("#appPicSrc").attr("src",window.URL.createObjectURL(articleAddPicFile.files[0]));  
                	} 
                }  
             }
         }  
		return true;
	}
	
</script>
</html>