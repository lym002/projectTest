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
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加配置</a>
	    </form>
	</div>
	<!-- ------------------邀请返现列表 --------------------------->
	<table id="activitiestable" class="easyui-datagrid" style="height:99%;width:99.9%"></table>
	
	<!-- -----------------新增奖励配置------------------------------->
	<div id="addActivity" class="easyui-dialog" style="height:70%;width:40%" closed="true"	buttons="addBtn"  data-options="resizable:true,modal:true,closed:true">
		<form id="addActivityForm" enctype="multipart/form-data" method="post">
		<div style="padding:5px">
			奖励名称:<input id="addName" name="name" class="easyui-textbox"  size="15" style="width:150px" />
			奖励条件:<select id="addConditionType" name="conditionType" editable="false" class="easyui-combobox"  size="15" style="width:150px">
				<option value='1' selected="selected">用户投资返现</option>
			</select>
		</div>
		<div style="padding:5px">
			奖励周期:<input id="addStartDate" name="startDate" editable="false" style="width: 150px" class="easyui-datetimebox"/>-<input id="addEndDate" name="endDate" editable="false" style="width: 150px" class="easyui-datetimebox"/>
		</div>
		<div style="padding:5px;">
			期数:<input id="addPeriods" name="periods" style="width: 150px" class="easyui-numberbox"/>
		</div>
		<div style="padding:5px">
			<table id="activityPrize" class="easyui-datagrid" style="height:350px;width:99.9%"></table>
		</div>
		<div id="toolActivity" style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true"  onclick="addPrize()">新增</a>
				<a href="#" class="easyui-linkbutton"  iconCls="icon-remove" plain="true"  onclick="delPrize()">删除</a>
		</div>
		<input type="checkbox" value="1" name="isPut" id="isPut" checked="checked" onclick="changePushDivDisplay()"/>推送至“我的邀请”（超过奖励周期推送实效）
		<div id="pushDiv" style="display: block;">
		<table>
		<tr><td>上传PC推送图片（建议尺寸850x160）：</td></tr>
		<tr><td><input type="file" name="pcPutImg" id="pcPutImg" onchange="PreviewPcImage(this)" /></td></tr>
		<tr><td><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></td></tr>
		<tr><td><img src="" id="showPcPic"  height="160" width="600" /></td></tr>
		<tr><td>跳转地址:</td></tr>
		<tr><td><input id="pcPutUrl" name="pcPutUrl" class="easyui-textbox"  size="15" style="width:300px" /></td></tr>
		<tr><td><font color="#FF3030">请以http://格式输入如:http://www.baidu.com</font></td></tr>
		<tr><td>上传移动推送图片（建议尺寸1080x270）：</td></tr>
		<tr><td><input type="file" name="appPutImg" id="appPutImg" onchange="PreviewAppImage(this)" /></td></tr>
		<tr><td><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></td></tr>
		<tr><td><img src="" id="showAppPic"  height="160" width="600" /></td></tr>
		<tr><td>跳转地址:</td></tr>
		<tr><td><input id="appPutUrl" name="appPutUrl" class="easyui-textbox"  size="15" style="width:300px" /></td></tr>
		<tr><td><font color="#FF3030">请以http://格式输入如:http://www.baidu.com</font></td></tr>
		<tr><td>推送文案:</td></tr>
		<tr><td><input class="easyui-textbox" id="putContent" name="putContent" data-options="multiline:true,prompt:'最多50个字'" style="width:550px;height:100px"></td></tr>
		</table>
		</div>
		</form>
		<center>
		<div id="addBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActivity').dialog('close')">取消</a>
		</div>
		</center>
	</div>
	
	<!-- -----------------添加条件------------------------------->
	<div id="addCondition" class="easyui-dialog" style="height:30%;width:30%" closed="true"	buttons="addBtn"  data-options="resizable:true,modal:true,closed:true">
		<div style="padding:5px;height:700">
			产品期限:<input id="addDays" name="days" class="easyui-numberbox"  size="15" style="width:150px"/><font color="#FF3030">(天)</font>
			配置奖励:<select id="addAwardType" name="awardType"  class="easyui-combobox" editable="false" size="15" style="width:100px">
				<option value='1' >固定比例(%)</option>
				<option value='2' >固定面值(元)</option>
			</select>
		</div>
		<div style="padding:5px;height:700">
			非APP奖励：<input id="addAmount" name="amount" class="easyui-numberbox" precision="2" size="15" style="width:50px"/>
			APP奖励：<input id="addAppReward" name="appReward" class="easyui-numberbox" precision="2" size="15" style="width:50px"/>
		</div>
		<div style="padding:5px;height:750">
			投资限制:<input id="firstInvest" name="investLimit" type="radio" value="0" checked="true"/>首投返现 
					<input id="invested" name=investLimit type="radio" value="1" />投资返现
		</div>
		<center>
		<div id="addBtn2" style="padding:15px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveCondition()">保存</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addCondition').dialog('close')">取消</a>
		</div>
		</center>
	</div>
	<!-- 展示 -->
	<div id="viewActivity" class="easyui-dialog" style="height:70%;width:40%" closed="true"	buttons="addBtn"  data-options="resizable:true,modal:true,closed:true">
		<div style="padding:5px">
			奖励名称:<input id="viewName" name="name" class="easyui-textbox" disabled="true" size="15" style="width:150px" />
			奖励条件:<select id="viewConditionType" name="conditionType" disabled="true" editable="false" class="easyui-combobox"  size="15" style="width:150px">
				<option value='1' selected="selected">用户投资返现</option>
			</select>
		</div>
		<div style="padding:5px">
			奖励周期:<input id="viewStartDate" name="startDate" disabled="true" style="width: 150px" class="easyui-datetimebox"/>-<input id="viewEndDate" name="endDate" disabled="true" style="width: 150px" class="easyui-datetimebox"/>
		</div>
		<div style="padding:5px;">
			期数:<input id="viewPeriods" name="periods" disabled="true" style="width: 150px" class="easyui-numberbox"/>
		</div>
		<div style="padding:5px">
			<table id="viewActivityPrize" class="easyui-datagrid" style="height:350px;width:99.9%"></table>
		</div>
		<input type="checkbox" value="1" name="isPut" disabled="disabled" id="viewisPut"/>推送至“我的邀请”（超过奖励周期推送实效）
		<div id="viewpushDiv" style="display: none;">
		<table>
		<tr><td>上传PC推送图片（建议尺寸850x160）：</td></tr>
		<tr><td><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></td></tr>
		<tr><td><img src="" id="viewshowPcPic"  height="160" width="600" /></td></tr>
		<tr><td>跳转地址:</td></tr>
		<tr><td><input id="viewpcPutUrl" name="pcPutUrl" readonly="true" class="easyui-textbox"  size="15" style="width:300px" /></td></tr>
		<tr><td><font color="#FF3030">请以http://格式输入如:http://www.baidu.com</font></td></tr>
		<tr><td>上传移动推送图片（建议尺寸1080x270）：</td></tr>
		<tr><td><font color="#FF3030">图片文件最大5MB，支持.jpg.jpeg的图片格式。</font></td></tr>
		<tr><td><img src="" id="viewshowAppPic"  height="160" width="600" /></td></tr>
		<tr><td>跳转地址:</td></tr>
		<tr><td><input id="viewappPutUrl" name="appPutUrl" readonly="true" class="easyui-textbox"  size="15" style="width:300px" /></td></tr>
		<tr><td><font color="#FF3030">请以http://格式输入如:http://www.baidu.com</font></td></tr>
		<tr><td>推送文案:</td></tr>
		<tr><td><input class="easyui-textbox" id="viewputContent" readonly="true" name="putContent" data-options="multiline:true,prompt:'最多50个字'" style="width:550px;height:100px"></td></tr>
		</table>
		</div>
		<center>
		<div id="viewBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#viewActivity').dialog('close')">确定</a>
		</div>
		</center>
	</div>
</body>
<script type="text/javascript"> 
		//奖励列表
		var activities = $('#activitiestable');
		$(function() {
			activities.datagrid({
				url : "../activityFriend/findInviteCashback.do",
				title : '邀请返现管理',
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
						field : 'name',
						title : '奖励名称',
						width : '15%',
						align : 'center'
					},{
						field : 'period',
						title : '奖励周期',
						width : '20%',
						align : 'center',
						formatter : function(value,row,index){
							if (row.startDate == null || row.startDate == '') {  
						        return '';  
						    }  
						    var start = parseToDate(row.startDate);
						    if(row.endDate == null || row.endDate == ''){
						    	return '';
						    }
						    var end = parseToDate(row.endDate);
							return start.format("yyyy-MM-dd hh:mm:ss")+'至'+end.format("yyyy-MM-dd hh:mm:ss");
						}
					},{
						field : 'conditionType',
						title : '奖励条件',
						width : '10%',
						align : 'center',
						formatter : function(value,row,index){
							if(value=='1')
								return '用户投资返现';
						}
					},{
						field : 'status',
						title : '奖励状态',
						width : '10%',
						align : 'center',
						formatter : function(value,row,index){
							if(value=='1')
								return '进行中';
							else if(value=='2')
								return '已结束';
						}
					},{
						field : 'periods',
						title : '期数',
						width : '10%',
						align : 'center'
					},{
						field : 'addDate',
						title : '创建时间',
						width : '15%',
						align : 'center',
						formatter : formatDateBoxFull
					},{
						field : 'id',
						title : '操作',
						width : '15%',
						align : 'center',
						formatter : function(value,row,index){
							return '<a href="#" class="btn l-btn l-btn-small" onclick="view('+index+')">查看配置</a>';
						}
					},{
						field:'isPut',
						hidden:true
					},{
						field:'pcPutImg',
						hidden:true
					},{
						field:'pcPutUrl',
						hidden:true
					},{
						field:'appPutImg',
						hidden:true
					},{
						field:'appPutUrl',
						hidden:true
					},{
						field:'putContent',
						hidden:true
					}
				] ]
			});
		}); 
		
		//弹出新增奖励配置框
		var id="";
		function add(){
			$('#addActivity').dialog('open').dialog('setTitle', '新增奖励配置');
			$('#addName').textbox('setValue','');
			$('#addStartDate').datetimebox('setValue','');
			$('#addEndDate').datetimebox('setValue','');
			$('#addPeriods').numberbox('setValue','');
			$('#addActivityForm').attr('enctype','multipart/form-data');
			$('#pushDiv').attr("style", "display:block;");
			$('#isPut').prop('checked','true');
			$('#showPcPic').removeAttr('src');
			$('#showAppPic').removeAttr('src');
			loadActivityPrize();//加载条件
		}	
		
		//加载条件表
		function loadActivityPrize(){
			$('#activityPrize').datagrid({
				url : "../activityFriend/findInviteCashbackDetail.do?fid=00000000000000",
				title : '条件列表',
				fitColumns : true,
				autoRowHeight : false,
				singleSelect:true,
				toolbar:"#toolActivity",
				fit:true,
				columns : [ [ 
					{
						field : 'days',
						title : '产品期限',
						width : '25%',
						align : "center",
						formatter : function(value,row,index){
							return value+'天';
						}
					},{
						field : 'awardTypeString',
						title : '非app奖励',
						width : '25%',
						align : "center",
						formatter : function(value,row,index){
							if(row.awardType=='1')
								return '固定比例(%),'+row.amount;
							else if(row.awardType=='2')
								return '固定面值(元),'+row.amount;
						}
					},{
						field : 'appRewardString',
						title : 'app奖励',
						width : '25%',
						align : "center",
						formatter : function(value,row,index){
							if(row.awardType=='1')
								return '固定比例(%),'+row.appReward;
							else if(row.awardType=='2')
								return '固定面值(元),'+row.appReward;
						}
					},{
						field : 'investLimit',
						title : '投资限制',
						width : '25%',
						align : "center",
						formatter : function(value,row,index){
							if(value=='0')
								return '首投返现';
							else if(value=='1')
								return '投资返现';
						}
					},{
						field : 'awardType',
						hidden : true,
					},{
						field : 'amount',
						hidden : true,
					},{
						field : 'appReward',
						hidden : true,
					}
				] ]
			});
		}
		
		function addPrize(){
			$('#addCondition').dialog('open').dialog('setTitle', '添加条件');
			$('#addDays').textbox('setValue','');
			$('#addAwardType').combobox('setValue','');
			$('#addAmount').numberbox('setValue','');
			$('#addAppReward').numberbox('setValue','');
		}
		
		function delPrize(){
			var row = $('#activityPrize').datagrid('getSelected');
			if(row != null) {
				var rowIndex = $('#activityPrize').datagrid('getRowIndex', row);
				$('#activityPrize').datagrid('deleteRow', rowIndex);
			}else{
				$.messager.alert('操作提示','请选择要删除的数据!'); 
				return false;  	
			}
		}
		
		function saveCondition(){
			var days = $('#addDays').textbox('getValue');
			var awardType = $('#addAwardType').combobox('getValue');
			var amount = $('#addAmount').numberbox('getValue');
			var appReward=$('#addAppReward').numberbox('getValue');
			var investLimit = $('input[name="investLimit"]:checked').val();
			if(days==null||days==''||awardType==null||awardType==''||amount==null||amount==''||investLimit==null||investLimit==''||appReward==null||appReward==''){
				$.messager.alert('系统提示','请把条件信息填写完整！');
				return false;
			}
			
			$('#activityPrize').datagrid('appendRow',{days:days,awardType:awardType,
				amount:amount,investLimit:investLimit,appReward:appReward
			});
			
			$('#addCondition').dialog('close');
		}
		
		//保存
		function save(){
			var name = $("#addName").textbox('getValue');
			var conditionType = $('#addConditionType').combobox('getValue');
			var startDate = $('#addStartDate').datetimebox('getValue');
			var endDate = $('#addEndDate').datetimebox('getValue');
			var periods = $('#addPeriods').numberbox('getValue');
			if(name==null||name==''||conditionType==null||conditionType==''||startDate==null||startDate==''||endDate==null||endDate==''||periods==null||periods==''){
				$.messager.alert('系统提示','请把信息填写完整！');
				return false;
			}
			var rows  = $('#activityPrize').datagrid("getRows");
			if(rows.length<1){
				$.messager.alert('系统提示','请添加奖励条件！');
				return false;
			}
			if(endDate<=startDate){
				$.messager.alert('系统提示','奖励结束时间不能早于开始时间！');
				return false;
			}
			if(id==null || id==""){//新增奖励
				$.messager.progress({ 
			        title: '请等待', 
			        msg: '提交中...', 
			        text: 'PROCESSING.......' 
			    });
				$('#addActivityForm').form('submit',{
					url:'../activityFriend/addInviteCashback.do?conData='+JSON.stringify(rows),
					dataType:'json',
					success:function(data){
						$.messager.progress('close'); 
						var d = $.parseJSON(data);
						if(d.success){
							$.messager.alert('操作提示','操作成功');
							$('#addActivity').dialog('close');
							activities.datagrid('reload');
						}else{
							if(d.msg=="exists")
								$.messager.alert('系统提示','该奖励周期内已有配置');
							else if(d.msg=="exists2")
								$.messager.alert('系统提示','上一期活动未结束，无法创建新活动');
							else
								$.messager.alert('系统提示',d.errorMsg);
						}
					}
				});
				/* $.ajax({
					url : "../activityFriend/addInviteCashback.do",
					type: "post",
					data : $("#addActivityForm").serialize(),
					success : function(result) {
						if(result=="success"){
							$.messager.alert('操作提示','操作成功');
							$('#addActivity').dialog('close');
							activities.datagrid('reload');
						}else if(result=="exists"){
							$.messager.alert('系统提示','该奖励周期内已有配置');
						}else if(result=="exists2"){
							$.messager.alert('系统提示','上一期活动未结束，无法创建新活动');
						}
					}
				}); */
			}
		}
		
		var id = null;
		function view(index){
			var rows = activities.datagrid('getRows');
			if(rows.length>0){
				var row = rows[index];
				if(row != null) { 
					$('#viewActivity').dialog('open').dialog('setTitle', '查看配置');
					$('#viewName').textbox('setValue',row.name);
					$('#viewConditionType').combobox('setValue',row.conditionType);
					$('#viewStartDate').datetimebox('setValue',row.startDate);
					$('#viewEndDate').datetimebox('setValue',row.endDate);
					$('#viewPeriods').numberbox('setValue',row.periods);
					if(row.pcPutImg!=null&&row.pcPutImg!=''){
						$("#viewshowPcPic").attr("src",row.pcPutImg);
					}else {
						$("#viewshowPcPic").attr("src",null);
					}
					if(row.appPutImg!=null&&row.appPutImg!=''){
						$("#viewshowAppPic").attr("src",row.appPutImg);
					}else {
						$("#viewshowAppPic").attr("src",null);
					}
					if(row.isPut==1){
						$('#viewisPut').attr("checked",'checked');
						$('#viewpushDiv').attr("style", "display:block;");
					}
					$('#viewpcPutUrl').textbox('setValue',row.pcPutUrl);
					$('#viewappPutUrl').textbox('setValue',row.appPutUrl);
					$('#viewputContent').textbox('setValue',row.putContent);
					id=row.id;
					//加载产品表
					$('#viewActivityPrize').datagrid({
						url : "../activityFriend/findInviteCashbackDetail.do?fid="+id,
						title : '条件列表',
						fitColumns : true,
						autoRowHeight : false,
						singleSelect:true,
						fit:true,
						columns : [ [ 
							{
								field : 'days',
								title : '产品期限',
								width : '25%',
								align : "center",
								formatter : function(value,row,index){
									return value+'天';
								}
							},{
								field : 'awardTypeString',
								title : '非APP奖励',
								width : '25%',
								align : "center",
								formatter : function(value,row,index){
									if(row.awardType=='1')
										return '固定比例(%),'+row.amount;
									else if(row.awardType=='2')
										return '固定面值(元),'+row.amount;
								}
							},{
								field : 'appRewardString',
								title : 'APP奖励',
								width : '25%',
								align : "center",
								formatter : function(value,row,index){
									if(row.awardType=='1')
										return '固定比例(%),'+row.appReward;
									else if(row.awardType=='2')
										return '固定面值(元),'+row.appReward;
								}
							},{
								field : 'investLimit',
								title : '投资限制',
								width : '25%',
								align : "center",
								formatter : function(value,row,index){
									if(value=='0')
										return '首投返现';
									else if(value=='1')
										return '投资返现';
								}
							},{
								field : 'awardType',
								hidden : true,
							},{
								field : 'amount',
								hidden : true,
							},{
								field : 'appReward',
								hidden : true,
							}
						] ]
					});	          
				}
			}
		}
		
		function PreviewPcImage(pcPutImg){
			if (pcPutImg.value == "") {  
				$.messager.alert("提示信息","请上传图片");
		        return false;  
		    }  
	     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(pcPutImg.value)) {  
	        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
	            pcPutImg.value = "";  
	            return false;  
	        }
		    if(pcPutImg){  
				if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
					pcPutImg.select();
					var imgSrc = document.selection.createRange().text;
					var localImagId = document.getElementById("showArticleAddPic");
					localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
					document.selection.empty();
	      		}else{ 
	                if(pcPutImg.files)  
	                {  
	                	$("#showPcPic").attr("src",window.URL.createObjectURL(pcPutImg.files[0]));  
	                }  
	             }
	         }  
			return true;
		}
		
		function PreviewAppImage(appPutImg){
			if (appPutImg.value == "") {  
				$.messager.alert("提示信息","请上传图片");
		        return false;  
		    }  
	     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(appPutImg.value)) {  
	        	$.messager.alert("提示信息","图片类型必须是.gif,jpeg,jpg,png中的一种");
	            appPutImg.value = "";  
	            return false;  
	        }
		    if(appPutImg){  
				if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {    
					appPutImg.select();
					var imgSrc = document.selection.createRange().text;
					var localImagId = document.getElementById("showArticleAddPic");
					localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
					document.selection.empty();
	      		}else{ 
	                if(appPutImg.files)  
	                {  
	                	$("#showAppPic").attr("src",window.URL.createObjectURL(appPutImg.files[0]));  
	                }  
	             }
	         }  
			return true;
		}
		function changePushDivDisplay(){
			if($('#isPut').is(':checked')) {
				$('#addActivityForm').attr('enctype','multipart/form-data');
				$('#pushDiv').attr("style", "display:block;");
			}else{
				$("addActivityForm").removeAttr("enctype");
				$('#pushDiv').attr("style", "display:none;");
			}
		}
</script>
</html>

