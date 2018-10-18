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
	<div id="activitiesbtn" style="padding:5px;height:750">
		<form id="activitiesform">
	  		活动名称:<input id="name" name="name" class="easyui-textbox"  size="15" style="width:100px"/>
	  		产品名称:<input id="productname" name="productname" class="easyui-textbox"  size="15" style="width:100px"/>
	  		活动状态:
	  		<select name="status" style="width: 172px" class="easyui-combobox" id="status">
	  			<option value="">全部</option>	
				<option value=1>进行中</option>	
				<option value=2>待开奖</option>	
				<option value=3>已开奖</option>		
			</select>
	  		活动产品时间:<input class="easyui-datebox" id="startTime" name="startTime" style="width:100px"/> 至 <input class="easyui-datebox" id="endTime" name="endTime" style="width:100px"/>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selactivitiesList()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetactivities()">重置</a>
	    </form>
	</div>
	<!-- ------------------活动模板列表 --------------------------->
	<table id="activitiestable" class="easyui-datagrid" style="height:99%;width:99.9%"></table>
	
	<!-- -----------------发布中奖结果------------------------------->
	<div id="addActivity" class="easyui-dialog" style="width:750px;height:450px"   
	        data-options="iconCls:'icon-mini-f2',modal:true,closed:true">
	        <form id="addDrProductInfoForm" enctype="multipart/form-data">
			<div style="margin-bottom:20px;width:100%;height:80%;">
				<center>
				<h1>发布中奖号码</h1>
				<table>
				<tr>
					<td>
					中奖号码:<input id="prizeCode" name="prizeCode" class="easyui-textbox"  size="15" style="width:100px"/>
					<input id="id" name="id" type="text" hidden/>
					</td>
					<td>
					 <font color="#D0D0D0">多个号码请用【,】隔开</font>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
					视频地址:<input id="prizeUrl" name="prizeUrl" class="easyui-textbox"  size="15" style="width:500px"/>
					</td>
				</tr>
				<tr>
					<td>
					抽奖照片:	<input type="file" name="acceptPicFile" id="acceptPicFile" onchange="PreviewImage(this)" />
					</td>
					<td>
					 <font color="#D0D0D0">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="" id="showAcceptPicAddPic"  height="200" width="600" /> 
					</td>
				</tr>
				</table>
				</center>
			</div>  
		</form>
			<div style="text-align:center;">  
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActivity').dialog('close')">取消</a>
			</div>
		</div>
</body>
<script type="text/javascript"> 
		//活动模板列表
		var activities = $('#activitiestable');
		$(function() {
			activities.datagrid({
				url : "../activityTemplate/selActivityProductAll.do",
				title : '活动列表',
				fitColumns : true,
				pagination : true,
				pageSize:20,
				singleSelect:true,
				pageList:[20,50],
				autoRowHeight : false,
				toolbar:"#activitiesbtn",
				fit:true,
				columns : [ [ 
					{
					field : 'id',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'name',
					title : '活动产品名称',
					width : '15%',
					formatter:function(value,row,index){
						return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.id+"')\">"+value+"</a>";	
                       },
					align : "center"
					},{
					field : 'startDate',
					title : '上架时间',
					width : '15%',
					align : "center"
					},{
					field : 'status',
					title : '活动状态',
					width : '15%',
					formatter:function(value,row,index){
						if(value=="1"){
							return '进行中';
						}
						if(value=="2"){
							return '待开奖';
						}
						if(value=="3"){
							return '已开奖';
						}	
                       },
					align : "center"
					},{
					field : 'cnvalue',
					title : '产品状态',
					width : '15%',
					align : "center"
					},{
					field : 'prizeCode',
					title : '开奖号码',
					width : '6%',
					formatter:function(value,row,index){
						if(value==null || value==""){
							return '--';
						}else{
							return value;
						}
                       },
					align : "center"
					},{
					field : 'prizeUrl',
					title : '视频地址',
					width : '15%',
					formatter:function(value,row,index){
						if(value==null || value==""){
							return '--';
						}else{
							return value;
						}
	                   },
					align : "center"
					},{
					field : ' ',
					title : '操作',
					width : '6%',
					formatter:function(value,row,index){
						if(row.status==2){
							return "<a href='#' class='easyui-linkbutton' onclick=\"update(\'"+row.id+"')\">发布中奖结果</a>";
						}else{
							return '--';
						}
                       },
					align : "center"
					}
				] ]
			});
		}); 
		
		//查询按钮
		function selactivitiesList(){
	 		activities.datagrid({
				queryParams: {
					name:$('#name').textbox('getValue'),
					productname:$('#productname').textbox('getValue'),
					status:$('#status').combobox('getValue'),
					startTime:$('#startTime').datebox('getValue'),
					endTime:$('#endTime').datebox('getValue')
					}
			})
		}
		
		//重置
		function resetactivities(){
			$("#activitiesform").form("reset");
			activities.datagrid("load", {});
		};
		
		//发布中奖结果
		function update(id){
			$('#addActivity').dialog('open').dialog('setTitle', '发布开奖结果');
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
	
	function save(){
		if($("#prizeCode").textbox('getValue')==null || $("#prizeCode").textbox('getValue')==""){
			$.messager.alert("系统提示","请填写中奖号码");
	        return false;  
		}
		/* if($("#acceptPicFile").val()==null || $("#acceptPicFile").val()==""){
			$.messager.alert("系统提示","请上传中奖图片");
	        return false;  
		} */
		if($("#prizeUrl").textbox('getValue')==null || $("#prizeUrl").textbox('getValue')==""){
			$.messager.alert("系统提示","请填写视频地址");
	        return false;  
		}
		$("#addDrProductInfoForm").ajaxSubmit({
				url:"../activityTemplate/addActivityProduct.do",
				type:"POST",
				data:$("#addDrProductInfoForm").serialize(),
	      		success:function(data){
	      		    var resultJson = jQuery.parseJSON(data);
					var resultJson = eval(resultJson);
					if(resultJson.success){
						$("#addDrProductInfoForm").form("reset");
						$('#addActivity').dialog('close');
						selactivitiesList();
						$.messager.alert("提示信息",resultJson.msg);
						
					} else{
						$.messager.alert("提示信息",resultJson.msg);
						return false;
					}  	
				}
	        });
	}
	
	function selectData(id){//跳转活动详情页面
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "活动详情",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activityTemplate/toActivityDetail.do?id="+id+"'></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab); 
		mainTab.tabs("add",detailTab);
	}
</script>
</html>

