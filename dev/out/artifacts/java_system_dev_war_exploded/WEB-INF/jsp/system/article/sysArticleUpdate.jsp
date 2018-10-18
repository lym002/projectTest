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
<form id="updateArticleForm" enctype="multipart/form-data">
<input name="artiId" type="hidden" value="${sysArticle.artiId}" />
	<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
	<table>
		<tr>
			<td align="left">
				标题:
			</td>
			<td colspan="11">
				<input type="text" value="${sysArticle.title}" name="title" size="155" class="easyui-textbox" data-options="required:true" />
			</td>
		</tr>
		<tr>
			<td align="left">
				发布时间:
			</td>
			<td>
				<input name="createTime" class="easyui-datetimebox" value="${createTime}" style="width:150px" data-options="required:true"/>
			</td>
			<td align="left">
				栏目:
			</td>
			<td>
				<select name="proId" style="width:100px;" class="easyui-combobox" data-options="required:true,validType:'selectValueRequired'">
				 	<option value='-1'>请选择栏目</option>
					<c:forEach items="${sysProgram}" var="vt">
			           <c:if test="${sysArticle.proId eq vt.proId }"> <option value="${vt.proId }" selected="selected">${vt.proName}</option></c:if>
			           <c:if test="${sysArticle.proId != vt.proId }"> <option value="${vt.proId }">${vt.proName}</option></c:if>
					</c:forEach>
	           	</select>
			</td>
			<td align="left">
				作者:
			</td>
			<td>
				<input type="text" value="${sysArticle.writer}" name="writer" class="easyui-textbox"/>
			</td>
			<td align="left">
				关键字:
			</td>
			<td>
				<input type="text" value="${sysArticle.keywords}" name="keywords" class="easyui-textbox" />
						<span style="color:red">关键字之间请以空格隔开</span>
			</td>
			<td align="left">
				页面标题:
			</td>
			<td>
				<input name="metatitle" value="${sysArticle.metatitle}" class="easyui-textbox"/>
			</td>
		</tr>
		<tr>	
			<td align="left">
				来源:
			</td>
			<td>
				<input type="text" value="${sysArticle.source}" name="source" class="easyui-textbox"/>
			</td>
			<td align="left">
				是否头条:
			</td>
			<td>
				<select name="ishead" style="width: 70px" class="easyui-combobox">
						<option value="1" <c:if test="${sysArticle.ishead== 1}"> selected="selected" </c:if>>是</option>
						<option value="0" <c:if test="${sysArticle.ishead== 0}"> selected="selected" </c:if>>否</option>
				</select>
			</td>
			<td>
				页面关键字:
			</td>
			<td>
				<input name="metakyword" value="${sysArticle.metakyword}" class="easyui-textbox"/>
			</td>
			<td align="left">
				排序:
			</td>
			<td>
				<input type="text" value="${sysArticle.sortRank}" name="sortRank" class="easyui-numberbox" data-options="required:true,min:0,max:9"  />
				<span style="color:red;">填写0-9，数字越大排的越前</span>
			</td>
			<td>
				页面描述:
			</td>
			<td>
				<input class="easyui-textbox" name="description" data-options="multiline:true" value="${sysArticle.description}" style="height:23px"/>
			</td>
		</tr>
		<tr id="lsl" style="display:none">
			<td align="left">
				开放日关联:
			</td>
			<td colspan="3">
				<select name="openDayId" style="width: 172px" class="easyui-combobox" id="openDayId">
							<option value="">选择</option>
							<c:forEach items="${openDayList}" var="vt">
			           		<c:if test="${sysArticle.openDayId eq vt.id }"> <option value="${vt.id }" selected="selected">${vt.openDayName}</option></c:if>
			           		<c:if test="${sysArticle.openDayId != vt.id }"> <option value="${vt.id }">${vt.openDayName}</option></c:if>
							</c:forEach>
						</select>  
			</td>
		</tr>
		<tr>
			<td align="left">
				关联银行:
			</td>
			<td colspan="11">
				<select id="updArticleGrid" class="easyui-combogrid" name="bankName" style="height:38px;width:700px;padding-bottom: 3px;" data-options="
					panelWidth: 700,
					multiple: true,
					multiline:true,
					editable:false,
					editable:false,
					idField: 'bankName',
					textField: 'bankName',
					url: '../article/drAllSysBank.do',
					method: 'get',
					columns: [[
						{field:'id',checkbox:true},
						{field:'bankName',title:'银行名称',width:80},
					]],
					fitColumns: true,
					onLoadSuccess: function (data) {
						var coupons = '${sysArticle.bankName}';
						var obj = coupons.split(',');
						$('#updArticleGrid').combogrid('setValues', obj);
					}
				">
				</select>
			</td>
		</tr>

		
		
	</table>
	</div>
	<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
	<table>
		<tr>
			<td align="left" style="vertical-align: top;">
				上传图片:
			</td>
			<td>		
					<input type="file" id="articleUpdPicFile" name="articleUpdPicFile" onchange="PreviewImage(this)" />
						<div style="color:red;">图片文件最大5MB，支持.jpg.jpeg的图片格式。</div>
					<img src="${apppath }${sysArticle.litpic}" id="showArticleUpdPic" height="160" width="200" /> 
			</td>
			<td align="left" style="vertical-align: top;">	
				文章概要:
			</td>
			<td>	
					<textarea cols="50" rows="9" name="summaryContents" id="summaryContents">${sysArticle.summaryContents}</textarea>
			</td>
			<td align="left" style="vertical-align: top;">	
				文本:
			</td>
			<td>	
					<textarea cols="50" rows="9" name="content" id="content">${sysArticle.content}</textarea>
			</td>
		</tr>		
	</table>
	</div>
</form>
</div>
<div style="text-align:center;padding:30px 60px 10px 0px">
	<a id="picAddBtn" href="javascript:void(0)" data-options="iconCls:'icon-save'"
	class="easyui-linkbutton" onclick="updateArticleBtn()">修改</a>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
			var openDayList = $(openDayList);
			if(openDayList!=null){
				$('#lsl').show();
			}
		});

	function updateArticleBtn(){
		var validate = $("#updateArticleForm").form("validate");
		if(!validate){
			return false;
		}
		$("#updateArticleForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '${apppath}/article/articleUpdate.do', // 需要提交的 url
            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
                var resultJson = jQuery.parseJSON(result);
				if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg,"",function(){
					var currTab = parent.$('#main-center').tabs('getTab', "文章管理");
						var content = '<iframe scrolling="no" frameborder="0"  src="../article/toArticleList.do" style="width:100%;height:100%;"></iframe>';  
						parent.$('#main-center').tabs('update', {
							tab: currTab,
							options: {
								content: content  // 新内容的URL
							}
						});
					parent.$('#main-center').tabs('close','修改文章');
					});
				}else{
					$.messager.alert("提示信息",resultJson.errorMsg);
				};
            	$("#updateArticleForm").resetForm(); // 提交后重置表单
            }
        });
        return false; // 阻止表单自动提交事件
	};

	//图片预览
	function PreviewImage(articleUpdPicFile) {
		if (articleUpdPicFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(articleUpdPicFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            articleUpdPicFile.value = "";  
            return false;  
        }
	    if(articleUpdPicFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0)){    
				articleUpdPicFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleUpdPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				imgObjPreview.style.display = 'none';
				document.selection.empty();
      		}else{ 
                if(articleUpdPicFile.files)  
                {  
                	$("#showArticleUpdPic").attr("src",window.URL.createObjectURL(articleUpdPicFile.files[0]));  
                }  
             }
         }  
		return true;
	}
</script>
</html>

