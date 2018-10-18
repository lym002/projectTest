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
		editor1.readonly(true);
	});
</script>

</head>
<body>

<form id="form" enctype="multipart/form-data" >
	<div class="easyui-panel" style="width:100%;height:97%;padding:10px;" align="center">
	<table>
		<tr>
			<td>
				PC头部banner:
				<input type="file" id="topPc" name="topPc" disabled="disabled"/>
			</td>
			<td>
				H5头部banner:
				<input type="file" id="topH5" name="topH5" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<td >
				PC右侧banner:
				<input type="file" id="rightPc" name="rightPc" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<td >
					标题:
				<input name="title" id="title" class="easyui-textbox" disabled="disabled" value="${jsSpecial.title}" style="width:400px;height:22px;"/>
				
			</td>
		</tr>
		<tr>
			<td align="left" style="vertical-align: top;" colspan="2">	
				文本:
			</td>
		</tr>
		<tr>
			<td colspan="2">	
				<textarea cols="100" rows="20" name="context" id="context"  style="disabled:true">${jsSpecial.context}</textarea>
			</td>
		</tr>
	</table>
	
	<div title="活动图片" class="easyui-panel" style="width:80%;height:auto;padding:10px;"data-options="collapsible:true,region:'center'">
			<table id="addProductPicTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
				
				<tr>
		   	   		<td>
		   	   		<div id="imgs" class="imgs" style="overflow-x: auto;width: ${jsSpecialPic.size()*210}px;">
	            		<c:forEach items="${jsSpecialPic}" var="v">
				        	<div style="text-align: center;display: inline-block;">
					        	<img src="${v.imgUrl}" width="200px" height="200px">
					        </div>
	  					</c:forEach>
				    </div>
				    </td>
		   	   	</tr>
			</table>
		</div>
	<div style="text-align:center;padding:30px 60px 10px 0px">
		<a id="picAddBtn" href="javascript:void(0)" data-options="iconCls:'icon-save'"
		class="easyui-linkbutton" onclick="toSaveView()">编辑</a>
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
	
	var i=0;
	//添加图片
	function addPic(){
		var tr = "<tr>"+
			"<td>" +
			"<input type='file' name='files' onchange=\"addVerifyImage(this)\" style='width:180px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
	   	   	"<td><input name=\"jsSpecialPic["+i+"].showSort\" type='text' class='easyui-numberbox' style='width: 100px' data-options='min:0,max:20,required:true'/>" +
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
		i++;
	}
		
	function delPic(obj){
		$(obj).parent().parent().parent().remove();
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
				title : "专题页内容编辑",
				content:"<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/jsSpecial/toJsSpecial.do'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
		parent.$('#main-center').tabs('close','专题页内容管理');
		parent.$('#main-center').tabs('close','专题页内容查看');
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
	layer.photosPage({
        id: 100, //相册id，可选
        parent:'#imgs'
    });
	
	function showPic(ths){
		var src = $(ths).data('value');
        layer.photos({
            json:  { //您服务端接口需严格按照下述格式返回
		        "status": 1,    //请求的状态，1表示成功，其它表示失败
		        "msg": "",  //状态提示语
		        "title": "",    //相册标题
		        "id": "",    //相册id
		        "start": 0, //初始显示的图片序号，默认0
		        "data": [   //相册包含的图片，数组格式
		            {
		                "name": "", //图片名
		                "pid": "", //图片id
		                "src": src, //原图地址
		                "thumb": "", //缩略图地址
		                "area": [638, 851] //原图宽高
		            }
		        ]
		    },
        });
	}
</script>
</html>