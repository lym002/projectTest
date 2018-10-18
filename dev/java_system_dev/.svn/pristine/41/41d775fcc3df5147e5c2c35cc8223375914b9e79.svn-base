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
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
<script type="text/javascript">
	$(function(){
		
	});
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[id="context"]', {
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
<form id="form" enctype="multipart/form-data" >
	<div class="easyui-panel" style="width:100%;height:auto;padding:10px;" align="center">
	<table>
		<tr>
			<td>
			<input type="hidden" name="id" id="id" value="${jsSpecial.id}">
			<input type="hidden" id="picSize" value="${picSize}">
				PC头部banner:
				<input type="file" id="topPc" name="topPc"data-options="required:true"/>
			</td>
			<td>
				H5头部banner:
				<input type="file" id="topH5" name="topH5" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td >
				PC右侧banner:
				<input type="file" id="rightPc" name="rightPc" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<td >
					标题:
				<input name="title" id="title" class="easyui-textbox"  value="${jsSpecial.title}" data-options="required:true" 
				style="width:400px;height:22px;"/>
			</td>
		</tr>
		<tr>
			<td align="left" style="vertical-align: top;" colspan="2">	
				文本:
			</td>
		</tr>
		<tr>
			<td colspan="2">	
				<textarea cols="100" rows="15" name="context" id="context">${jsSpecial.context}</textarea>
			</td>
		</tr>
	</table>
	
	<div title="活动图片" class="easyui-panel" style="width:100%;height:auto;padding:10px;"data-options="collapsible:true,region:'center',tools:'#picAddBtn'">
			<table id="addProductPicTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
				<tr>
					<th>选择图片</th>
					<th>显示顺序(先显示数字大的)</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${jsSpecialPic}" var="v" varStatus="i">
				<tr>
					<td>
					<input name='jsSpecialPic[${i.index}].imgUrl' value="${v.imgUrl}" type="hidden"/>
						<input type='file' name='files' onchange="updateVerifyImage(this)" style='width:240px; 
		   	   				white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>
					</td>
		   	   		<td>
		   	   			<input name='jsSpecialPic[${i.index}].id' value="${v.id}" type="hidden"/>
		   	   			<input name="jsSpecialPic[${i.index}].showSort" value="${v.showSort}" type='text' class='easyui-numberbox'/> 
		   	   		</td>
		   	   		<td>
			   	   		<div class='btn-group'>
				   	   		<a href='javascript:void(0)' data-value="${v.id}" class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'>删除</a>
			   	   		</div>
		   	   		</td>
		   	   	</tr>
				</c:forEach>
			</table>
		</div>
		<div id="picAddBtn">
			<a href="javascript:void(0)" class="icon-add" onclick="addPic();"></a>
		</div>
	<div style="text-align:center;padding:30px 60px 10px 0px">
		<a id="picAddBtn" href="javascript:void(0)" data-options="iconCls:'icon-save'"
		class="easyui-linkbutton" onclick="addArticleBtn()">保存</a>
	</div>
	

	<div style="text-align:center;padding:30px 60px 10px 0px">
		请确认信息并上传至前台显示:
		<a id="submit" href="javascript:void(0)" data-options="iconCls:'icon-save'"
		class="easyui-linkbutton" onclick="submit()">上传</a>
	</div>
	</div>
</form>


</body>
<script type="text/javascript">
	function addArticleBtn(){
		var validate = $("#form").form("validate");
		if(!validate){
			return false;
		} 
		$("#form").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '${apppath}/jsSpecial/addJsSpecial.do', // 需要提交的 url
            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
            	var resultJson = jQuery.parseJSON(result);
				if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg,'',function(){
						toSaveView();
					});
				}else{
					$.messager.alert("提示信息",resultJson.errorMsg);
				};
            }
        });
        return false; // 阻止表单自动提交事件
	};
	
 	var a = '${picSize==null?0:picSize}';
	//添加图片
	function addPic(){
		
		var tr = "<tr>"+
			"<td>" +
			"<input name=\"jsSpecialPic["+a+"].id\"  type='hidden'/>"+
			"<input name=\"jsSpecialPic["+a+"].imgUrl\"  type='hidden'/>"+
			"<input type='file' name='files' onchange=\"updateVerifyImage(this)\" style='width:180px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
	   	   	"<td><input name=\"jsSpecialPic["+a+"].showSort\" type='text' class='easyui-numberbox' style='width: 100px' data-options='min:0,max:20,required:true'/>" +
	   	   	"</td>"+
		   		"<td><div class='btn-group'>" +
		   		 "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delPic(this)'> " +
		   		"删除</a></div></td></tr>"; 
		$("#addProductPicTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-numberbox').numberbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		});
		a++;
	}
		
	function delPic(obj){
		var id= $(obj).data("value");
		if(typeof(id) == "undefined"){
			$(obj).parent().parent().parent().remove();
		}else{
			$.ajax({
				url: '${apppath}/jsSpecial/deletePic.do?id='+id,
				dataType:'json',
				success:function(result){
					if(result.success){
						$(obj).parent().parent().parent().remove();
					}else{
						$.messager.alert("提示信息",resultJson.errorMsg);
					}
				}
	 		});
		}
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
	
	
	function toSaveView(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "专题页内容查看",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/jsSpecial/toViewJsSpecial.do'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
		parent.$('#main-center').tabs('close','专题页内容编辑');
		
	}
	
	function submit(){
		var url = "${apppath}/jsSpecial/submit.do";
		$.ajax({
			url:url,
			success:function(data){
				var resultJson = jQuery.parseJSON(data);
				var resultJson = eval(resultJson);
				if(resultJson.success){
					$.messager.alert("提示信息","上传成功");
				}else{
					$.messager.alert("提示信息","上传失败");
				};
			}
		});
	}
	
</script>
</html>