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
<div id="${tabid }">
	<form id="addDrProductInfoForm" enctype="multipart/form-data">
		<div title="体验标信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px">
			<table id="productInfo" title="体验标新增" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">产品编号：</td>
					<td colspan="2">
						<input name="code" value="${productCode }" type="hidden"/>
						<input name="code" value="${productCode }" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
					
					<td align="left">产品类型：</td>
					<td colspan="2">
						<input name="type" value="5" type="hidden"/>
						<input name="type" value="体验标" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">产品全称：</td>
					<td colspan="2">
						<input id="productFullName" name="fullName" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">产品简称：</td>
					<td colspan="2">
						<input id="productSimpleName" name="simpleName" type="text" class="easyui-textbox" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">年化利率：</td>
					<td colspan="2">
						<input id="productRate" name="rate" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:20,precision:2"/>%
					</td>
					<td align="left">活动利率：</td>
					<td colspan="2">
						<input id="productActivityRate" name="activityRate" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:10,precision:2"/>%
					</td>
				</tr>
				<tr>
					<td align="left">还款方式：</td>
					<td colspan="2">
						<select name="repayType" style="width: 172px" class="easyui-combobox" id="repayType" disabled="disabled">
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2}">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td align="left">产品期限：</td>
					<td colspan="2">
						<input id="productDeadline" name="deadline" type="text" class="easyui-numberbox" data-options="required:true,min:1,max:1000" />天
					</td>
				</tr>
				<tr>
					<td align="left">是否开启：</td>
					<td colspan="2">
						<input id="productStatus" name="status" checked="checked" value = "5" type="radio" />开启
						<input id="productStatus2" name="status"  value = "6" type="radio" />关闭
					</td>
				</tr>
			</table>
		</div>
		<!-- 显示图片 -->
		<div style="text-align:center;padding:30px 60px 30px 0px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfo();">提交</a>
		</div>
	</form>
</div>	
<div id="queryDrSubjectInfoWindow" class="easyui-window"
	data-options="closed:true,modal:true,minimizable:false,maximizable:false" style="padding:10px;">
</div>
<script type="text/javascript">
	var j=0;
	
	$("#sid").combobox({    
	    onSelect: function(rec){   
		    $.ajax({
				url: '${apppath}/product/getClaimsPicInfo.do?id='+$('#sid').combobox('getValue'),
				dataType:'json',
				success:function(result){
					$(".isShowPicInfo").remove();
					if(result != ''){
						$.each(result, function (n, v) {
						var tr = "<tr class='isShowPicInfo'><td></td><td>"+
								"<input name=\"drClaimsPic["+n+"].id\" value='"+v.id+"' type='hidden'/>"+
								"<a href='javascript:void(0)' data-value='"+v.bigUrl+"' onclick=\"showPic(this)\">"+v.name+"</a>"+
								"</td>" +
								"<td></td>"+
								"<td>";
							if(v.isShow == 1){
								tr+="<input class='check-box checked' type='checkbox' name=\"drClaimsPic["+n+"].isShow\" value='1' checked='checked' onclick='changeOptions(this)'/>";
							}else{
								tr+="<input class='check-box' type='checkbox' name=\"drClaimsPic["+n+"].isShow\" value='1' onclick='changeOptions(this)'/>";
							}
								tr+="</td>" +
					   	   		"<td><input name=\"drClaimsPic["+n+"].showSort\" value="+v.showSort+" type='text' class='easyui-numberbox' data-options='min:0,max:20,required:false'/>" +
					   	   		"</td></tr>"; 
							$('#productInfo').append(tr);
							$('.easyui-numberbox').numberbox({    
							}); 
						});
					}
				}
			}); 
       	}
	}); 

 	function addDrProductInfo(){
 		var productStatus = $('input:radio:checked').val();
		var flag = $("#addDrProductInfoForm").form('enableValidation').form('validate');
		/* if($("#repayType").combobox('getValue') == 2){
			var deadline = $("#productDeadline").val();
			if(deadline%30 != 0){
				$.messager.alert("提示信息","按月付息到期还本,期限应为30的倍数");
				return false;
			}
		} */
		if(flag){
			$("#addDrProductInfoForm").ajaxSubmit({
				url:"${apppath}/product/addExperienceDrProductInfo.do",
				type:"POST",
				//data:$("#addDrProductInfoForm").serialize(),
				data:{ 
						id: $("#drProductId").val(), 
						code: $("#drProductCode").val(), 
						fullName :$("#drProductFullName").val(),
						simpleName :$("#drProductSimpleName").val(),
						rate :$("#productRate").val(),
						activityRate :$("#productActivityRate").val(),
						repayType :$("#productRepayType").val(),
						deadline :$("#productDeadline").val(),
						status:productStatus
					},
	      		success:function(data){
	      		    var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if(resultJson.success){
						$.messager.alert("提示信息",resultJson.msg,"",function(){
							var currTab = parent.$('#main-center').tabs('getTab', "体验标管理");
							if(currTab != null){
								var content = '<iframe scrolling="no" frameborder="0"  src="../product/toExperienceDrProductInfoList.do" style="width:100%;height:100%;"></iframe>';  
								parent.$('#main-center').tabs('update', {
									tab: currTab,
									options: {
										content: content  // 新内容的URL
									}
								});
							}
							parent.$('#main-center').tabs('close','体验标新增');
						});
						$("#addDrProductInfoForm").resetForm(); // 提交后重置表单
					}else{
						$.messager.alert("提示信息",resultJson.errorMsg);
					}            	
				}
	        });
	        return false; // 阻止表单自动提交事件
		}
	} 
	
	//显示标的详情
	function queryDrSubjectInfoBtn(index){
		if($('#sid').combobox('getValue') == ""){
			$.messager.alert("提示信息","请关联标的");
			return;
		}
		$("#queryDrSubjectInfoWindow").window({
			title:"标的详情",
			width:$(this).width()*0.5,
			height:$(this).height()*0.6,
			href:"../product/showDrSubjectInfo.do?id="+$('#sid').combobox('getValue'),
		});
		$("#queryDrSubjectInfoWindow").window("open").window("center");
	}
	
	//复制操作
	function copyDrProductInfo(){
		$.ajax({
			url: "${apppath}/product/copyDrProductInfo.do?code="+$("#productCode").val(),
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#productType").combobox('select',result.map.drProductInfo.type);
					$("#repayType").combobox('select',result.map.drProductInfo.repayType);
					$("#atid").combobox('select',result.map.drProductInfo.atid);
					if(2 == result.map.drProductInfo.type){
						$('.isShowPicInfo').remove();
						$("#sid").combobox({    
						    url:'${apppath}/product/getDrSubjectInfo.do?type='+result.map.drProductInfo.type,  
						    valueField:'id',    
						    textField:'name',
						    onLoadSuccess:function(){
						    	$('.isShowPicInfo').remove();
						    	var data = $('#sid').combobox('getData');
					            if (data.length > 0) {
					            	$.each(data, function (n, v) {
					            		if(v.id == result.map.drProductInfo.sid){
					            			$("#sid").combobox('select',result.map.drProductInfo.sid);
					            		}
					                });
					            }
						    }
						});
						if(typeof(result.map.drClaimsPic) != "undefined"){
							$.each(result.map.drClaimsPic, function (n, v) {
								var tr = "<tr class='isShowPicInfo' ><td></td><td>"+
											"<input name=\"drClaimsPic["+n+"].id\" value='"+v.id+"' type='hidden'/>"+
											"<a href='javascript:void(0)' data-value='"+v.bigUrl+"' onclick=\"showPic(this)\">"+v.name+"</a>"+
											"</td>" +
											"<td></td>"+
											"<td>";
										if(v.isShow == 1){
											tr+="<input class='check-box checked' type='checkbox' name=\"drClaimsPic["+n+"].isShow\" value='1' checked='checked' onclick='changeOptions(this)'/>";
										}else{
											tr+="<input class='check-box' type='checkbox' name=\"drClaimsPic["+n+"].isShow\" value='1' onclick='changeOptions(this)'/>";
										}
											tr+="</td>" +
								   	   		"<td><input name=\"drClaimsPic["+n+"].showSort\" value="+v.showSort+" type='text' class='easyui-numberbox' data-options='min:0,max:20,required:false'/>" +
								   	   		"</td></tr>"; 
								$('#productInfo').append(tr);
								$('.easyui-numberbox').numberbox({    
								}); 
							});
						}
					}
		
					$("#productFullName").textbox('setValue',result.map.drProductInfo.fullName);
					$("#productSimpleName").textbox('setValue',result.map.drProductInfo.simpleName);
					$("#productAmount").numberbox('setValue',result.map.drProductInfo.amount);
					$("#productRate").numberbox('setValue',result.map.drProductInfo.rate);
					$("#productActivityRate").numberbox('setValue',result.map.drProductInfo.activityRate);
					$("#productDeadline").numberbox('setValue',result.map.drProductInfo.deadline);
					$("#productLeastaAmount").numberbox('setValue',result.map.drProductInfo.leastaAmount);
					$("#productRaiseDeadline").numberbox('setValue',result.map.drProductInfo.raiseDeadline);
					$("#productIncreasAmount").numberbox('setValue',result.map.drProductInfo.increasAmount);	
					$("#productMaxAmount").numberbox('setValue',result.map.drProductInfo.maxAmount);
					if(1 == result.map.drProductInfo.isShow){
						$("#productIsShow").attr("checked",'true');
					}
					if(1 == result.map.drProductInfo.isHot){
						$("#productIsHot").attr("checked",'true');
					}
					if(1 == result.map.drProductInfo.isDeductible){
						$("#productIsDeductible").attr("checked",'true');
					}
					if(1 == result.map.drProductInfo.isInterest){
						$("#productIsInterest").attr("checked",'true');
					}
					if(1 == result.map.drProductInfo.isCash){
						$("#productIsCash").attr("checked",'true');
					}		
					if(1 == result.map.drProductInfo.isDouble){
						$("#productIsDouble").attr("checked",'true');
					}				
					$("#productIntroduce").textbox('setValue',result.map.drProductInfo.introduce);
					$("#productBorrower").textbox('setValue',result.map.drProductInfo.borrower);	
					$("#productRepaySource").textbox('setValue',result.map.drProductInfo.repaySource);
					$("#productWindMeasure").textbox('setValue',result.map.drProductInfo.windMeasure);	
					$("#accept").textbox('setValue',result.map.drProductInfo.accept);			
					
					$("#addProductExtendTable tr").remove();
					$.each(result.map.drProductExtend, function (n, v) {
					if(v.title!=null && v.title!=""){
						var title="标题：<input name=\"drProductExtend["+n+"].title\" value="+v.title+" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"
					}else{
						var title="标题：<input name=\"drProductExtend["+n+"].title\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"
					}
					
					if(v.content!=null && v.content!=""){
						var content="内容：<input class='easyui-textbox' name=\"drProductExtend["+n+"].content\" value="+v.content+" style='height:60px;width:450px;' data-options=\"multiline:true,validType:'maxLength[2000]'\"/>"
					}else{
						var content="内容：<input class='easyui-textbox' name=\"drProductExtend["+n+"].content\"  style='height:60px;width:450px;' data-options=\"multiline:true,validType:'maxLength[2000]'\"/>"
					}
						
						var tr = "<tr><td>"+title+
								"</td>" +
								"<td>" +content+
								"</td>" +
					   	   		"<td><div class='btn-group'>" +
					   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delExtend(this)'> " +
					   	   		"删除</a></div></td></tr>"; 
						$("#addProductExtendTable").append(tr);
						$('.easyui-textbox').textbox({    
						}); 
						$('.easyui-linkbutton').linkbutton({    
						});
						j++;
					});		
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	};
	
	//显示照片
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
	
	var i=0;
		//添加图片
	function addProductPic(){
		var tr = "<tr><td>"+
			"<input name=\"drProductPic["+i+"].name\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
			"</td>" +
			"<td>" +
			"<input name=\"drProductPic["+i+"].bigUrl\"  type='hidden'/>"+
			"<input type='file' name='productFiles' onchange=\"addVerifyImage(this)\" style='width:240px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'/>"+
			"</td>" +
			"<td>"+
				"<input class='check-box checked' type='checkbox' name=\"drProductPic["+i+"].isShow\" value='1' checked='checked' onclick='changeOptions(this)'/>"+
			"</td>"+
	   	   	"<td><input name=\"drProductPic["+i+"].showSort\" type='text' class='easyui-numberbox' style='width: 100px' data-options='min:0,max:20,required:true'/>" +
	   	   	"</td>"+
   	   		"<td><div class='btn-group'>" +
   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-search' onclick=\"addPreviewImage(this)\">"+
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
	
		//添加产品扩展信息
 	function addProductExtend(){
		var tr = "<tr><td>"+
			"标题：<input name=\"drProductExtend["+j+"].title\" type='text' class='easyui-textbox' data-options=\"validType:'maxLength[50]'\"/>"+
			"</td>" +
			"<td>" +
			"内容：<input class='easyui-textbox' name=\"drProductExtend["+j+"].content\" style='height:60px;width: 450px;' data-options=\"multiline:true,validType:'maxLength[2000]'\"/>"+
			"</td>" +
   	   		"<td><div class='btn-group'>" +
   	   		"<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' onclick='delExtend(this)'> " +
   	   		"删除</a></div></td></tr>"; 
		$("#addProductExtendTable").append(tr);
		$('.easyui-textbox').textbox({    
		}); 
		$('.easyui-linkbutton').linkbutton({    
		});
		j++;
	} 
	
	
	function changeOptions(ths) {
		var $this = $(ths);
		
		if ($this.hasClass('checked')) {
    		$this.parents('tr').find('.easyui-numberbox').numberbox({ 
				required:false
			});
			$this.removeClass('checked');
		}else {
		    $this.parents('tr').find('.easyui-numberbox').numberbox({ 
				required:true
			});
			$this.addClass('checked'); 
		}
	}
	
		//验证图片
	function addVerifyImage(ths) {
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
    	return true;  
	}
	
	//图片预览
	function addPreviewImage(ths) {
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
			var localImagId = document.getElementById("imglook");
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			document.selection.empty();
      	}else{
			$("#imglook").attr("src",window.URL.createObjectURL(dFile.prop('files')[0]));
      	}  
		$('#addProductPicShow').dialog('open');
		return true;
	}	
	
	function delPic(obj){
		$(obj).parent().parent().parent().remove();
	}
	
	function delExtend(obj){
		$(obj).parent().parent().parent().remove();
	}
	
	
	//图片预览
	function PreviewImage(acceptPicAddFile) {
		if (acceptPicAddFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(acceptPicAddFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            acceptPicAddFile.value = "";  
            return false;  
        }
	    if(acceptPicAddFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
				acceptPicAddFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(acceptPicAddFile.files)  
                {  
                	$("#showAcceptPicAddPic").attr("src",window.URL.createObjectURL(acceptPicAddFile.files[0]));  
                }  
             }
         }  
		return true;
	}
</script>

</body>
</html>
