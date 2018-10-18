<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
<div class="easyui-panel" style="width:100%;height:auto;padding:10px;">
<form id="updateSysBannerForm" enctype="multipart/form-data">
<input name="id" type="hidden" value="${sysBanner.id}" />
	<table>
		<tr>
			<td>
				标题:
				<input type="text" name="title" size="50" value="${sysBanner.title}" class="easyui-textbox" data-options="required:true" />
			</td>
		</tr>
		<tr>	
			<td>
				最低版本号:
				<input id="minVersion" type="text" name="minVersion" value="${sysBanner.minVersion}" size="50" class="easyui-textbox" />
			</td>
		</tr>
		<tr>	
			<td>
				说明:
				<input type="text" name="remark" size="50" value="${sysBanner.remark}" class="easyui-textbox" />
			</td>
		</tr>
		<tr>	
			<td>
				广告位置:
				<select id="code" name="code" style="width:100px;" class="easyui-combobox">
					<c:forEach var="map" items="${bannercode}">
						<option value='${map.key}' <c:if test="${sysBanner.code == map.key }" >selected="selected"</c:if>>${map.value}</option>
			        </c:forEach>
	           	</select>
			</td>
		</tr>
		<tr>	
			<td>
				跳转地址:
				<input type="text" name="location" value="${sysBanner.location}" class="easyui-textbox"/>
				<div style="color:red">请以http://格式输入如：http://www.baidu.com</div>
			</td>
		</tr>
		<tr>	
			<td>
				跳转背景:
				<input type="text" class="easyui-textbox" value="${sysBanner.color}" name="color"/>
			</td>
		</tr>
		<tr>
			<td>
				排序级别:
				<input type="text" name="sort" value="${sysBanner.sort}" class="easyui-numberbox" data-options="min:0,max:9" />
				<div style="color:red">填写数字，按照广告的先后顺序，数字越大越是往前</div>
			</td>
		</tr>
		<tr>
			<td>
				上传图片:
			</td>
		</tr>
		<tr>
			<td>		
				<input type="file" id="bannerUpdPicFile" name="bannerUpdPicFile" onchange="PreviewImage(this)" />
					<div style="color:red;">图片文件最大5MB，支持.jpg.jpeg的图片格式。</div>
				<img src="${sysBanner.imgUrl}" id="showBannerUpdPic"  height="160" width="200"  /> 
			</td>
		</tr>
	</table>
</form>
<div style="text-align:center;padding:30px 60px 10px 0px">
	<a id="picAddBtn" href="javascript:void(0)" data-options="iconCls:'icon-save'"
	class="easyui-linkbutton" onclick="updateSysBanner()">修改</a>
</div>
</div>

<script type="text/javascript">
	function updateSysBanner(){
		var validate = $("#updateSysBannerForm").form("validate");
		if(!validate){
			return false;
		}
		var code = $("#code").combobox('getValue');
		if(code == 4){
			var minVersion = $("#minVersion").val();
			if(minVersion == null || minVersion == ""||minVersion =="undefined" || minVersion.trim()==""){
				$.messager.alert("提示信息","请输入最低版本号！");
				return false;
			}
		}
		$("#updateSysBannerForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '${apppath}/banner/updateSysBanner.do', // 需要提交的 url
            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
               	var resultJson = jQuery.parseJSON(result);
            	if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg,"",function(){
						var currTab = parent.$('#main-center').tabs('getTab', "广告发布");
						var content = '<iframe scrolling="no" frameborder="0"  src="../banner/toSysBannerList.do" style="width:100%;height:100%;"></iframe>';  
						parent.$('#main-center').tabs('update', {
							tab: currTab,
							options: {
								content: content  // 新内容的URL
							}
						});
						parent.$('#main-center').tabs('close','修改广告');
						$("#sysBannerList").datagrid("reload");
					});
				}else{
					$.messager.alert("提示信息",resultJson.errorMsg);
				};
            	$("#updateSysBannerForm").resetForm(); // 提交后重置表单
            }
        });
        return false; // 阻止表单自动提交事件
	};

	//图片预览
	function PreviewImage(bannerUpdPicFile) {
		if (bannerUpdPicFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    } 
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(bannerUpdPicFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            bannerUpdPicFile.value = "";  
            return false;  
        }
	    if(bannerUpdPicFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0)){    
				bannerUpdPicFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showBannerUpdPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				imgObjPreview.style.display = 'none';
				document.selection.empty();
      		}else{ 
                if(bannerUpdPicFile.files)  
                {  
                	$("#showBannerUpdPic").attr("src",window.URL.createObjectURL(bannerUpdPicFile.files[0]));  
                }  
             }
         }  
		return true;
	}
</script>
</body>
</html>

