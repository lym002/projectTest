<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="drChannelInfoList" title="渠道管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../channel/drChannelInfoList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drChannelInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'type'" hidden="true">渠道类型code</th>
	    	<th data-options="field:'code'" width="10%">渠道号</th>
	        <th data-options="field:'name'" width="10%">渠道名称</th>
	        <!-- <th data-options="field:'isParticipation'" width="10%" formatter="formatIsParticipation">是否参与活动</th> -->
	  		<th data-options="field:'typeName'" width="6%">渠道类型</th>
	        <th data-options="field:'status'" width="5%" formatter="formatStatus">渠道状态</th>
	       	<th data-options="field:'addUserName'" width="10%">添加人</th>
	        <th data-options="field:'addDate'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'updUserName'" width="10%">修改人</th>
	        <th data-options="field:'updDate'" width="10%" formatter="formatDateBoxFull">修改时间</th>
			<th data-options="field:'_operate',align:'center'" width="20%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drChannelInfoTools" style="padding:5px;height:750">
	  	<form id="drChannelInfoForm">
	  		渠道编号:<input id="searchDrChannelInfoCode" name="code" class="easyui-textbox"  size="15" style="width:100px"/>
	  		渠道名称:<input id="searchDrChannelInfoName" name="name" class="easyui-textbox"  size="15" style="width:100px"/>
	  		渠道状态:<select  id="searchDrChannelInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
					<option value=''>全部</option>
					<option value='0'>已失效</option>
					<option value='1'>已生效</option>
         		  </select>
	    	<a id="searchDrChannelInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrChannelInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrChannelInfoBtn()">渠道新增</a>	
	    </form>
	</div>
	<div id="addDrChannelInfoDialog" class="easyui-dialog" title="渠道添加"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrChannelInfoBtn'" style="width:400px;height:200px;padding:5px;">
		<form id="addDrChannelInfoForm">
			<table align="center">
				<tr>
					<td>
						渠道号：
					</td>
					<td>
						<input type="text" id="adddrChannelInfoCode" name="code" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						渠道名称：
					</td>
					<td>		
						<input class="easyui-textbox" id="adddrChannelInfoName"name="name" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>是否参与推荐首投返利：</td>
					<td>
						<select id="addIsParticipation" name="isParticipation" style="width:150px;" class="easyui-combobox">
							<option value='0'>参与</option>
							<option value='1'>不参与</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>渠道类型：</td>
					<td>
						<select  id="addtype" name="type" style="width:150px;" class="easyui-combobox">
						<c:forEach var="map" items="${chooseOptions}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
		           		</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="addDrChannelInfoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addDrChannelInfo()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="updateDrChannelInfoDialog" class="easyui-dialog" title="渠道修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateDrChannelInfoBtn'" style="width:400px;height:200px;padding:5px;">
		<form id="updateDrChannelInfoForm">
			<input type="hidden" id="drChannelInfoId"  name="id"/>
			<table align="center">
				<tr>
					<td>
						渠道号：
					</td>
					<td>
						<input id="drChannelInfoCode" type="text" name="code" class="easyui-textbox" data-options="required:true" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td>
						渠道名称：
					</td>
					<td>		
						<input id="drChannelInfoName" class="easyui-textbox" name="name" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>是否参与推荐首投返利：</td>
					<td>
						<select id="updateIsParticipation" name="isParticipation" style="width:150px;" class="easyui-combobox">
							<option  value='0'>参与</option>
							<option  value='1'>不参与</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>渠道类型：</td>
					<td>
						<select  id="updatetype" name="type" style="width:150px;" class="easyui-combobox">
						<c:forEach var="map" items="${chooseOptions}">
							<option value='${map.key}'>${map.value}</option>
				        </c:forEach>
		           		</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="updateDrChannelInfoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateDrChannelInfo()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	<div id="uploadFileDialog" class="easyui-dialog" title="导入关键字"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#uploadFileDialogBtn'" style="width:400px;height:160px;padding:5px;">
		<form id="uploadFileDialogForm" enctype="multipart/form-data">
			<input type="hidden" id="uploadFileDialogId"  name="id"/>
			<table align="center">
				<tr>
					<td>
						<input id="uploadFile" type="file" name="file" onchange="verifyFile(this)"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div id="uploadFileDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="uploadChannelKeyWord()">上传</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	<div id="drChannelKeyWordsTools" style="padding:5px;height:750">
	  	<form id="drChannelKeyWordsForm">
	  		<input type="hidden" id="drChannelKeyWordCode"  name="code"/>
	  		关键字:<input id="searchKeyWord" name="keyword" class="easyui-textbox"  size="15" style="width:100px"/>
	    	<a id="searchDrChannelKeyWord" href="javascript:searcheKeyWordsListBtn()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    </form>
	</div>
	<div id="keyWordListDialog" class="easyui-dialog" title="查看关键字"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:80%;height:70%;padding:5px;">
		<table id="drChannelKeyWordsList"
			class="easyui-datagrid" style="height:99%;width:99.9%"
			data-options="singleSelect:true,
			    fitColumns:true,
			    method:'post',rownumbers:true, 
			    pagination:true,toolbar:'#drChannelKeyWordsTools'">
				<thead>
			    <tr>
			    	<th data-options="field:'id'" hidden="true">序号</th>
			    	<th data-options="field:'code'" width="15%">渠道号</th>
			        <th data-options="field:'keyword'" width="15%">关键字</th>
			        <th data-options="field:'kCode'" width="15%">关键字号</th>
			        <th data-options="field:'status'" width="5%" formatter="formatStatus">状态</th>
			       	<th data-options="field:'name'" width="10%">添加人</th>
			        <th data-options="field:'addTime'" width="12%" formatter="formatDateBoxFull">添加时间</th>
			    </tr>
			    </thead>
			</table>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrChannelInfo').click(function(){
		$("#drChannelInfoForm").form("reset");
		$("#drChannelInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrChannelInfo').click(function(){
 		$('#drChannelInfoList').datagrid({
			queryParams: {
				code: $('#searchDrChannelInfoCode').val(),
				name: $('#searchDrChannelInfoName').val(),
				status: $('#searchDrChannelInfoStatus').combobox('getValue'),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		return	'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrChannelInfoBtn('+index+')">修改</a>&nbsp;&nbsp;&nbsp;'+
				'<a href="#" class="btn l-btn l-btn-small" onclick="uploadFile('+index+')">导入关键字</a>&nbsp;&nbsp;&nbsp;'+
				'<a href="#" class="btn l-btn l-btn-small" onclick="keyWordsListBtn('+index+')">查看关键字</a>';
	} 
	
	//跳转添加页面
	function addDrChannelInfoBtn(){  
		$("#addDrChannelInfoForm").form("reset");
		$("#addDrChannelInfoDialog").dialog("open");
	}
	
	//添加渠道
	function addDrChannelInfo(){  
		var validate = $("#addDrChannelInfoForm").form("validate");
		if(!validate){
			return false;
		}
		if($('#adddrChannelInfoCode').textbox("getValue").trim()=="" || $('#adddrChannelInfoName').textbox("getValue").trim()==""){
			$.messager.alert("提示信息","渠道信息不能为空");
			return false;
		}
   		 $.ajax({
          	url: "${apppath}/channel/addDrChannelInfo.do",
            type: 'POST',
            data:decodeURIComponent($("#addDrChannelInfoForm").serialize().replace(/\+/g,"")),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drChannelInfoList").datagrid("reload");
					$("#addDrChannelInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });   
	}
	
	//关闭添加渠道窗口
	function closeDialog(){  
		$("#drChannelInfoList").datagrid("reload");
		$("#updateDrChannelInfoDialog").dialog("close");
		$("#uploadFileDialog").dialog("close");
		$("#addDrChannelInfoDialog").dialog("close");
	}
	
	//打开dialog
	function updateDrChannelInfoBtn(index){  
		$('#drChannelInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drChannelInfoList').datagrid('getSelected'); 
		var url = "${apppath}/channel/queryDrChannelInfo.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#drChannelInfoId").val(result.map.drChannelInfo.id);
					$("#drChannelInfoCode").textbox('setValue',result.map.drChannelInfo.code);
					$("#drChannelInfoName").textbox('setValue',result.map.drChannelInfo.name);
					$("#updateIsParticipation").combobox('setValue',result.map.drChannelInfo.isParticipation);
					$("#updatetype").combobox('setValue',result.map.drChannelInfo.type);
					$("#updateDrChannelInfoDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	//修改渠道
	function updateDrChannelInfo(){  
		var validate = $("#updateDrChannelInfoForm").form("validate");
		if(!validate){
			return false;
		}
		if($('#drChannelInfoCode').textbox("getValue").trim()=="" || $('#drChannelInfoName').textbox("getValue").trim()==""){
			$.messager.alert("提示信息","渠道信息不能为空");
			return false;
		}
   		$.ajax({
          	url: "${apppath}/channel/updateDrChannelInfo.do",
            type: 'POST',
            data:decodeURIComponent($("#updateDrChannelInfoForm").serialize().replace(/\+/g,"")),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drChannelInfoList").datagrid("reload");
					$("#updateDrChannelInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });
	}
	
	 //更新是否参与推荐首投返利活动
	function updateDrChannelInfoIsParticipation(index){
		$('#drChannelInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drChannelInfoList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../channel/updateDrChannelInfoIsParticipation.do?id="+row.id+"&isParticipation="+row.isParticipation;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(){
						$('#drChannelInfoList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
			  	});
			}
		});
	} 
	
	//文件上传dialog
	function uploadFile(index){
		$("#uploadFileDialogForm").form("reset");
		$('#drChannelInfoList').datagrid('selectRow',index);
		var row = $('#drChannelInfoList').datagrid('getSelected');
		$("#uploadFileDialogId").val(row.id);
		$("#uploadFileDialog").dialog("open");
	}
	//验证文件
	function verifyFile(obj) {
		if (obj.value == "") {  
			$.messager.alert("提示信息","请上传文件");
	        return false;  
	    } 
        if (! /\.(xls)$/.test(obj.value)) {  
        	$.messager.alert("提示信息","文件类型必须是.xls");
            obj.value = "";  
            return false;  
        }
    	return true;  
	}
	//导入关键字
	function uploadChannelKeyWord(){
		$("#uploadFileDialogForm").ajaxSubmit({
			url:"${apppath}/channel/uploadChannelKeyWord.do",
			type:"POST",
			data:$("#uploadFileDialogForm").serialize(),
			success: function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#uploadFileDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
	}
	
	//关键字查询
	function keyWordsListBtn(index){
		$("#drChannelKeyWordsForm").form("reset");
		$("#keyWordListDialog").dialog("open");
		$('#drChannelInfoList').datagrid('selectRow',index);
		var row = $('#drChannelInfoList').datagrid('getSelected');
		$("#drChannelKeyWordCode").val(row.code);
		$('#drChannelKeyWordsList').datagrid({
		    url: "${apppath}/channel/queryChannelKeyword.do",
		    queryParams:{
		        code:$("#drChannelKeyWordCode").val()
		    }
		});
	}
	function searcheKeyWordsListBtn(){
		$("#drChannelKeyWordsList").datagrid({
		    url: "${apppath}/channel/queryChannelKeyword.do",
		    queryParams:{
		        code:$("#drChannelKeyWordCode").val(),
		        keyword:$("#searchKeyWord").val()
		    }
		});
	}
	
	//更改渠道状态
	function updateDrChannelInfoStatus(index){
		$('#drChannelInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drChannelInfoList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../channel/updateDrChannelInfoStatus.do?id="+row.id+"&status="+row.status;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						$.messager.alert("操作提示", result.msg);
						$('#drChannelInfoList').datagrid('reload');
					}
			  	});
			}
		});
	}
	//更改关键字状态
	function updateDrChannelKeyWordStatus(id,status){
		$.messager.confirm('操作提示', '你确定更改吗？', function(r){
			if(r){
				var url = "../channel/updateKeyWordStatus.do?id="+id+"&status="+status;
				$.ajax({
					url: url,
					type:"post",
					dataType:"json",
					success:function(result){	
						$.messager.alert("操作提示", result.msg);
						$("#drChannelKeyWordsList").datagrid({
						    url: "${apppath}/channel/queryChannelKeyword.do",
						    queryParams:{
						        code:$("#drChannelKeyWordCode").val(),
						        keyword:$("#searchKeyWord").val()
						    }
						});
					}
			  	});
			}
		});
	}
		//添加状态链接
	function formatStatus(value,row,index){
		if(row.status == "1"){
			if(row.keyword==null||row.keyword==""||typeof(row.keyword)=="undefind"){
				return '<a href="#" onclick="updateDrChannelInfoStatus('+index+')">已生效</a>';
			}else{
				return '<a href="#" onclick="updateDrChannelKeyWordStatus('+row.id+',0)">有效</a>';
			}
		}else{
			if(row.keyword==null||row.keyword==""||typeof(row.keyword)=="undefind"){
				return '<a href="#" onclick="updateDrChannelInfoStatus('+index+')">已失效</a>';
			}else{
				return '<a href="#" onclick="updateDrChannelKeyWordStatus('+row.id+',1)">已失效</a>';
			}
		}
	}
	
	function formatIsParticipation(value,row,index){
		if(value == "1"){
			return '<a href="#" onclick="updateDrChannelInfoIsParticipation('+index+')">不参与</a>';
		}else{
			return '<a href="#" onclick="updateDrChannelInfoIsParticipation('+index+')">参与</a>';
		}
	} 
</script>
</body>
</html>
