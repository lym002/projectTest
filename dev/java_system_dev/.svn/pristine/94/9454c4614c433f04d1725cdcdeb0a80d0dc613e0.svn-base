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
<script type="text/javascript" src="../js/common/ajaxfileupload.js"></script>


<script type="text/javascript">
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[id="content"]', {
			cssPath : '/js/kindeditor-4.1.5/plugins/code/prettify.css',
			uploadJson : '/js/kindeditor-4.1.5/jsp/upload_json.jsp',
			fileManagerJson : '/js/kindeditor-4.1.5/jsp/file_manager_json.jsp',
			allowFileManager : true,
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
<form id="updateArticleForm">
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
						<c:forEach items="${sysProgram}" var="vt">
			           <c:if test="${sysArticle.proId eq vt.proId }">${vt.proName}</c:if>
					</c:forEach>
			</td>
			<td align="left">
				作者:
			</td>
			<td>
				<input type="text" value="${sysArticle.writer}" name="writer" class="easyui-textbox" data-options="required:true"/>
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
				<input type="text" value="${sysArticle.source}" name="source" class="easyui-textbox" data-options="required:true"/>
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
			<td align="left" rowspan="2">
				<input class="easyui-textbox" name="description" data-options="multiline:true" value="${sysArticle.description}" style="height:23px"/>
			</td>
		</tr>
		<tr id="lsl" style="display:none">
			<td align="left">
				开放日关联:
			</td>
			<td colspan="3">
					<c:forEach items="${openDayList}" var="vt">
			           <c:if test="${sysArticle.openDayId eq vt.id }">${vt.openDayName}</c:if>
					</c:forEach> 
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
					<input type="file" id="filename" name="filename" onchange="PreviewImage('filename','imgfileArticlePicUpdate')" />
						<div style="color:red;">图片文件最大5MB，支持.jpg.jpeg的图片格式。</div>
					<img src="${sysArticle.litpic}" id="imgfileArticlePicUpdate" height="160" width="200" /> 
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
</body>
<script type="text/javascript">
	$(document).ready(function(){
 		$("input").attr("disabled",true);
 		$("textarea").attr("disabled",true);
 		$("select").attr("disabled",true);
 		var openDayList = $(openDayList);
 		if(openDayList!=null){
 			$('#lsl').show();
 		}
 	});
</script>
</html>

