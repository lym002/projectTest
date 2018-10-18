<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
<div class="easyui-panel" style="width:100%;height:100%;">
<div class="easyui-panel" title="活动详情"  style="width:80%;height:180px;padding:10px;">
			<table  style="width:100%" class="siteBasicInfo">
				<tr>
					<td>上架时间：${activityProduct.startDate}</td>
					<td>募集状态：${activityProduct.cnvalue}</td>
					<td>募集完成时间：${activityProduct.fullDate}</td>
					<td rowspan='4'>
						<table>
							<tr>
								<td>中奖号码：<font color="red">${activityProduct.prizeCode}</font></td>
							</tr>
							<tr>
								<td>中奖信息发布状态：${activityProduct.statusCode}</td>
							</tr>
							<tr>
								<td>
								 <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="exportluckCodes()">导出幸运码</a>  
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>活动产品名称：${activityProduct.name}</td>
					<td colspan='2'>活动状态：${activityProduct.status}</td>
				</tr>
				<tr>
					<td>活动奖品：</td>
					<td colspan='2'></td>
				</tr>
				<tr>
					<td>${activityPrize.name}</td>
					<td colspan='2'></td>
				</tr>
				<tr>
					<td>移动端短标题：<span id="refreshAppTitle"><c:choose>
						<c:when test="${activityProduct.appTitle == null}">${activityTitle}</c:when>
						<c:otherwise>${activityProduct.appTitle}</c:otherwise>
					</c:choose></span><a href="#" class="easyui-linkbutton" onclick="editAppTitle()">编辑</a></td>
					<td colspan='5'>活动标-活动详情图：<span id="refreshDetailImg"><c:choose>
						<c:when test="${detailImg == null}">系统默认图</c:when>
						<c:otherwise>${detailImg}</c:otherwise>
					</c:choose>
					</span>
					<a href="#" class="easyui-linkbutton" onclick="uploadDetailImg()">
					<c:choose>
					<c:when test="${detailImg!=null}">重新上传</c:when>
					<c:otherwise>上传</c:otherwise>
					</c:choose>
					</a>
					
					<c:choose>
						<c:when test="${activityProduct.pcDetailImg == null}">
							<input type="hidden" id="pcDetailImg" value="${activityImg}"/>
						</c:when>
						<c:otherwise>
							<input type="hidden" id="pcDetailImg" value="${activityProduct.pcDetailImg}"/>	
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<td>
					<a href="#" class="easyui-linkbutton" onclick="editActivityDetail()">活动详情页内容管理</a>
					</td>
				</tr>
			</table>
</div>
<div class="easyui-panel" title="关联产品信息"  style="width:80%;height:90px;padding:10px;">
			<table  style="width:100%" class="siteBasicInfo">
				<tr>
					<td>产品简称：${activityProduct.simpleName}</td>
					<td>产品金额(元)：${activityProduct.amount}</td>
					<td>产品期限(天)：${activityProduct.deadline}</td>
					<td>产品利率(%)：${activityProduct.rate}</td>
				</tr>
			</table>
</div>
<div class="easyui-panel" title="参与用户列表"  style="width:80%;height:380px;padding:10px;">
	<div id="investbtn" style="padding:5px;height:750">
		<form id="investform">
	  		中奖号码:<input id="code" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
	  		显示:
	  		<select name="status" style="width: 172px" class="easyui-combobox" id="status">
	  			<option value="">全部用户</option>	
				<option value=2>中奖用户</option>		<!-- 状态2表示已中奖 -->
			</select>
			&nbsp;&nbsp;参与用户(人)：${count.count}&nbsp;&nbsp;投资金额(元)：${count.amount}&nbsp;&nbsp;&nbsp;&nbsp;
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selinvestList()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetinvest()">重置</a>
	    </form>
	</div>
	<!-- ------------------用户列表 --------------------------->
	<table id="investtable" class="easyui-datagrid" style="height:99%;width:99.9%"></table>
</div>
</div>

<!-- -----------------上传中奖信息------------------------------>
	<div id="addInvest" class="easyui-dialog" style="width:800px;height:600px"   
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	        <form id="addInvestForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:100%;">
				<center>
				<h1>上传中奖人信息</h1>
				<table>
				<tr>
					<td>
					中奖人号码:<input class="easyui-textbox" id="prizeMobile" name="prizeMobile">	
					</td>
					<td>
						
					</td>
				</tr>
				<tr>
				<tr>
					<td>
					中奖人介绍:
					</td>
					<td></td>
				</tr>
				<tr>
				<td colspan="2">
					<input class="easyui-textbox" id="prizeContent" name="prizeContent" data-options="multiline:true" style="width:550px;height:100px">		
					<input id="id" name="id" type="text" hidden/>
				</td>
				</tr>
				<tr>
					<td>
					中奖人头像:	<input type="file" name="headFile" id="headFile" onchange="PreviewHeadImage(this)" />
					</td>
					<td>
					 <font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="" id="showHeadPic"  height="156" width="156" /> 
					</td>
				</tr>
				<tr>
					<td>
					中奖人照片:	<input type="file" name="acceptPicFile" id="acceptPicFile" onchange="PreviewImage(this)" />
					</td>
					<td>
					 <font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="" id="showAcceptPicAddPic"  height="376" width="664" /> 
					</td>
				</tr>
				<tr>
					<td>
					添加视频链接:<input class="easyui-textbox" style="width:300px" id="prizeVideoLink" name="prizeVideoLink">	
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
					添加视频地址:<input class="easyui-textbox" style="width:300px" id="prizeVideoUrl" name="prizeVideoUrl">
					</td>
					<td>
					</td>
				</tr>
				</table>
				</center>
			</div>  
		</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addInvest').dialog('close')">取消</a>
			</div>
		</div>
		
		<!-- 移动端短标题编辑窗口 -->
		<div id="editAppTitle" class="easyui-dialog" style="width:300px;height:120px;overflow:hidden"   
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true">
	        <div style="width:90%;height:75%;margin-top:15px;margin-bottom:15px;margin-left:10px">
		       	 移动端短标题：<input class="easyui-textbox" style="width:150px" id="appTitle" name="appTitle">
		       	<div style="text-align:center;margin-top:5px">  
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAppTitle()">确定</a> 
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editAppTitle').dialog('close')">取消</a>
				</div>
			</div>
	    </div>
	    <!-- 上传活动标-活动详情图窗口 -->
	    <div id="uploadDetailImg" class="easyui-dialog" style="width:600px;height:600px;" data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	    	<form id="detailImgForm" enctype="multipart/form-data">
	    	<input type="hidden" name="productId" value="${activityProduct.id}"/>
	    	活动标活动详情图:<input type="file" name="detailFile" id="detailFile" onchange="PreviewDetailImage(this)" />
	    	<font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
	    	<img src="" id="showDetailPic"  height="800" width="600" /> 
	    	</form>
	    	<div style="text-align:center;margin-top:5px">  
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveDetailImg()">确定</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#uploadDetailImg').dialog('close')">取消</a>
			</div>
	    </div>
	    <!-- 活动详情页内容管理窗口 -->
		<div id="activityDetail" class="easyui-dialog" style="width:620px;height:550px;" data-options="iconCls:'icon-mini-f2',modal:true,closed:true,top:0">
	    	<form id="activityDetailForm" enctype="multipart/form-data">
	    	<table>
	    		<tr>
	    			<td>
	    				<input type="hidden" name="productId" value="${activityProduct.id}"/>
	    				<c:choose>
	    					<c:when test="${activityProduct.pcBannerUrl == null}">
	    						<input type="hidden" id="pcBannerImg" value="${activityPCBannerImg}"/>
	    					</c:when>
	    					<c:otherwise>
			    				<input type="hidden" id="pcBannerImg" value="${activityProduct.pcBannerUrl}"/>
	    					</c:otherwise>
	    				</c:choose>
				    	PC顶部banner:<input type="file" name="pcBannerFile" id="pcBannerFile" onchange="PreviewPcBannerImage(this)" />
				    	<font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
				    </td>
	    		</tr>
	    		<tr>
	    			<td><img src="" id="showPcBannerPic"  height="200" width="600" /></td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<c:choose>
	    					<c:when test="${activityProduct.pcBannerUrl == null}">
	    						<input type="hidden" id="h5BannerImg" value="${activityH5BannerImg}"/>
	    					</c:when>
	    					<c:otherwise>
			    				<input type="hidden" id="h5BannerImg" value="${activityProduct.h5BannerUrl}"/>
	    					</c:otherwise>
	    				</c:choose>
		    			H5顶部banner:<input type="file" name="h5BannerFile" id="h5BannerFile" onchange="PreviewH5BannerImage(this)" />
		    			<font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				<img src="" id="showH5BannerPic"  height="200" width="600" />
	    			</td>
	    		</tr>
	    	</table>
	    	</form>
	    	<div style="text-align:center;margin-top:5px">  
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveActivityDetail()">确定</a> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#activityDetail').dialog('close')">取消</a>
			</div>
	    </div>
<script type="text/javascript"> 
		//参与用户列表
		var investtable = $('#investtable');
		var id=${id};
		$(function() {
			investtable.datagrid({
				url : "../activityTemplate/selProductInvestById.do?id="+id,
				title : '活动列表',
				fitColumns : true,
				pagination : true,
				pageSize:20,
				singleSelect:true,
				pageList:[20,50],
				autoRowHeight : false,
				toolbar:"#investbtn",
				fit:true,
				columns : [ [ 
					{
					field : 'id',
					title : 'id',
					width : '15%',
					align : "center",
					hidden:true
					},{
					field : 'isUplod',
					title : 'isUplod',
					width : '15%',
					align : "center",
					hidden:true
					},{
					field : 'realname',
					title : '用户姓名',
					width : '5%',
					align : "center"
					},{
					field : 'mobilePhone',
					title : '手机号',
					width : '10%',
					align : "center"
					},{
					field : 'recommCodes',
					title : '推荐码',
					width : '10%',
					align : "center"
					},{
					field : 'amount',
					title : '投资金额',
					width : '10%',
					align : "center"
					},{
					field : 'investTime',
					title : '投资时间',
					width : '10%',
					align : "center"
					},{
					field : 'luckCodes',
					title : '幸运码',
					width : '20%',
					align : "center"
					},{
					field : 'prizeStatus',
					title : '是否中奖',
					width : '5%',
					align : "center",
					formatter:function(value,row,index){
						if(row.prizeStatus=="2"){//已中奖
							return '已中奖'
						}else if(row.prizeStatus=="1"){
							return '未中奖';
						}else{
							return '待开奖';
						}
                       }
					},{
					field : ' ',
					title : '操作',
					width : '15%',
					align : "center",
					formatter:function(value,row,index){
						if(row.prizeStatus=="2"){//已中奖
							if(row.isUplod=="1"){//已上传
								//return '已上传';
								return "<a href='#' class='easyui-linkbutton' onclick=\"modify(\'"+index+"')\">修改编辑</a>";
							}else{
								return "<a href='#' class='easyui-linkbutton' onclick=\"update(\'"+row.id+"')\">上传中奖信息</a>";
							} 
						}else{
							return '--';
						}
                       }
					},{
						field : 'prizeMobile',
						hidden : true
					},{
						field : 'prizeImgUrl',
						hidden : true
					},{
						field : 'prizeContent',
						hidden : true
					},{
						field : 'prizeVideoLink',
						hidden : true
					},{
						field : 'prizeHeadPhoto',
						hidden : true
					}
				] ]
			});
		}); 
		
		//查询按钮
		function selinvestList(){
	 		investtable.datagrid({
				queryParams: {
					code:$('#code').textbox('getValue'),
					status:$('#status').combobox('getValue')
					}
			})
		}
		
		//重置
		function resetinvest(){
			$("#investform").form("reset");
			investtable.datagrid("load", {});
		}
		
		//打开上传中奖信息页面
		function update(id){
			$('#addInvest').dialog('open').dialog('setTitle', '上传中奖信息');
			$('#id').val(id);
		}
		
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
		
		function PreviewHeadImage(headFile){
			if (headFile.value == "") {  
				$.messager.alert("提示信息","请上传图片");
		        return false;  
		    }  
	     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(headFile.value)) {  
	        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
	            headFile.value = "";  
	            return false;  
	        }
		    if(headFile){  
				if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
					headFile.select();
					var imgSrc = document.selection.createRange().text;
					var localImagId = document.getElementById("showArticleAddPic");
					localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
					document.selection.empty();
	      		}else{ 
	                if(headFile.files)  
	                {  
	                	$("#showHeadPic").attr("src",window.URL.createObjectURL(headFile.files[0]));  
	                }  
	             }
	         }  
			return true;
		}
	
	//提交
	function save(){
		if($("#prizeMobile").textbox('getValue')==null || $("#prizeMobile").textbox('getValue')==""){
			$.messager.alert("系统提示","请填写中奖人号码");
	        return false;  
		}
		if($("#prizeContent").textbox('getValue')==null || $("#prizeContent").textbox('getValue')==""){
			$.messager.alert("系统提示","请填写中奖人介绍");
	        return false;  
		}
		if($("#prizeContent").textbox('getValue').length >500){
			$.messager.alert("系统提示","中奖人介绍超出指定长度");
	        return false;  
		}
		/* if(($("#acceptPicFile").val()==null || $("#acceptPicFile").val()=="")&&($("#showAcceptPicAddPic").attr("src")==null||$("#showAcceptPicAddPic").attr("src")=='')){
			$.messager.alert("系统提示","请上传中奖图片");
	        return false;  
		} */
		if(($("#headFile").val()==null || $("#headFile").val()=="")&&($("#showHeadPic").attr("src")==null||$("#showHeadPic").attr("src")=='')){
			$.messager.alert("系统提示","请上传中奖人头像");
	        return false;  
		}
		if($("#prizeVideoUrl").textbox('getValue')==null || $("#prizeVideoUrl").textbox('getValue')==""){
			$.messager.alert("系统提示","请填写视频地址");
	        return false;  
		}
		$("#addInvestForm").ajaxSubmit({
				url:"../activityTemplate/addInvest.do",
				type:"POST",
				data:$("#addInvestForm").serialize(),
	      		success:function(data){
	      		    var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if(resultJson.success){
						$("#addInvestForm").form("reset");
						$('#addInvest').dialog('close');
						selinvestList();
						$.messager.alert("提示信息","操作成功");
						
					} else{
						$.messager.alert("提示信息","操作失败");
					}  	
				}
	        });
	}
	
	 //导出幸运码
	function exportluckCodes(){
		window.location.href = '../activityTemplate/exportInvest.do?id='+id;							
	}
	 
	function modify(index){
		var rows = investtable.datagrid('getRows');
		if(rows.length>0){
			var row = rows[index];
			$('#prizeMobile').textbox('setValue',row.prizeMobile);
			$('#prizeContent').textbox('setValue',row.prizeContent);
			if(row.prizeImgUrl!=null&&row.prizeImgUrl!=''){
				$("#showAcceptPicAddPic").attr("src",row.prizeImgUrl);
			}
			if(row.prizeHeadPhoto!=null&&row.prizeHeadPhoto!=''){
				$("#showHeadPic").attr("src",row.prizeHeadPhoto);
			}
			$('#prizeVideoLink').textbox('setValue',row.prizeVideoLink);
			$('#prizeVideoUrl').textbox('setValue',row.prizeVideoUrl);
			$('#addInvest').dialog('open').dialog('setTitle', '修改编辑中奖信息');
			$('#id').val(row.id);
		}
	}
	function editAppTitle(){
		var appTitle = $('#refreshAppTitle').html();
		$('#appTitle').textbox('setValue',appTitle);
		$('#editAppTitle').dialog('open').dialog('setTitle', '编辑信息');
	}
	function uploadDetailImg(){
		var pcDetailImg = $('#pcDetailImg').val();
		if(pcDetailImg!=null&&pcDetailImg!=''){
			$("#showDetailPic").attr("src",pcDetailImg);
		}
		$('#uploadDetailImg').dialog('open').dialog('setTitle', '上传图片');
	}
	function saveAppTitle(){
		var appTitle = $('#appTitle').textbox('getValue');
		var productId = '${activityProduct.id}';
		if(appTitle==null||appTitle==''){
			$.messager.alert("系统提示","请填写移动端短标题");
	        return false;  
		}
		$.ajax({
          	url: "../activityTemplate/updateAppTitle.do",
            type: 'POST',
            data:{
            	appTitle : appTitle,
            	productId : productId
            },  
            success:function(result){
            	if(result=="success"){
					$.messager.alert("提示信息","操作成功");
					$('#editAppTitle').dialog('close');
					$('#refreshAppTitle').html(appTitle);
				}else{
					$.messager.alert("提示信息","操作失败");
				}
			}	
        });
	}
	function PreviewDetailImage(detailFile){
		if (detailFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(detailFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            detailFile.value = "";  
            return false;  
        }
	    if(detailFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
				detailFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(detailFile.files)  
                {  
                	$("#showDetailPic").attr("src",window.URL.createObjectURL(detailFile.files[0]));  
                }  
             }
         }  
		return true;
	}
	function saveDetailImg(){
		/* if(($("#detailFile").val()==null || $("#detailFile").val()=="")){
			$.messager.alert("系统提示","请上传PC活动详情图片");
	        return false;  
		} */
		$("#detailImgForm").ajaxSubmit({
			url:"../activityTemplate/updateDetailImg.do",
			type:"POST",
			data:$("#detailImgForm").serialize(),
      		success:function(data){
				var resultJson = jQuery.parseJSON(data);
				var resultJson = eval(resultJson);
				if(resultJson.success){
					$.messager.alert("提示信息","操作成功");
					var totalUrl = resultJson.msg;
					$('#pcDetailImg').val(totalUrl);
					var imageName = totalUrl.substr(totalUrl.lastIndexOf("/")+1,totalUrl.length);
					$('#uploadDetailImg').dialog('close');
					$('#refreshDetailImg').html(imageName);
				}else{
					$.messager.alert("提示信息","操作失败");
				}
			}
        });
	}
	function editActivityDetail(){
		var pcBannerImg = $('#pcBannerImg').val();
		if(pcBannerImg!=null&&pcBannerImg!=''){
			$("#showPcBannerPic").attr("src",pcBannerImg);
		}
		var h5BannerImg = $('#h5BannerImg').val();
		if(h5BannerImg!=null&&h5BannerImg!=''){
			$("#showH5BannerPic").attr("src",h5BannerImg);
		}
		$('#activityDetail').dialog('open').dialog('setTitle', '活动详情页内容管理');
	}
	function PreviewPcBannerImage(pcBannerFile){
	/* 	if (pcBannerFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    } */  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(pcBannerFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            pcBannerFile.value = "";  
            return false;  
        }
	    if(pcBannerFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
				pcBannerFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(pcBannerFile.files)  
                {  
                	$("#showPcBannerPic").attr("src",window.URL.createObjectURL(pcBannerFile.files[0]));  
                }  
             }
         }  
		return true;
	}
	function PreviewH5BannerImage(h5BannerFile){
		/* if (h5BannerFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  */ 
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(h5BannerFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
            h5BannerFile.value = "";  
            return false;  
        }
	    if(h5BannerFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
				h5BannerFile.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("showArticleAddPic");
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(h5BannerFile.files)  
                {  
                	$("#showH5BannerPic").attr("src",window.URL.createObjectURL(h5BannerFile.files[0]));  
                }  
             }
         }  
		return true;
	}
	function saveActivityDetail(){
		/* if(($("#pcBannerFile").val()==null || $("#pcBannerFile").val()=="")&&($('#h5BannerFile').val()==null||$('#h5BannerFile').val()=="")){
			$.messager.alert("系统提示","请至少上传一个图片");
	        return false;  
		} */
		$("#activityDetailForm").ajaxSubmit({
			url:"../activityTemplate/updateActivityDetailBanner.do",
			type:"POST",
			data:$("#activityDetailForm").serialize(),
      		success:function(data){
				var resultJson = jQuery.parseJSON(data);
				var resultJson = eval(resultJson);
				if(resultJson.success){
					$.messager.alert("提示信息","操作成功");
					var resultMap = resultJson.map;
					var pcBannerUrl = resultMap['pcBannerUrl'];
					var h5BannerUrl = resultMap['h5BannerUrl'];
					$('#pcBannerImg').val(pcBannerUrl);
					$('#h5BannerImg').val(h5BannerUrl);
					$('#activityDetail').dialog('close');
				}else{
					$.messager.alert("提示信息","操作失败");
				}
			}
        });
	}
	</script>
</body>
</html>

