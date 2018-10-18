
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
	<table id="drProductPrizeList" title="奖品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../prizemanage/queryPrizeManageList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'prId'" hidden="true">奖品ID</th>
	    	<th data-options="field:'prizeName'" width="10%">奖品名称</th>
	        <th data-options="field:'price'"  width="10%">金额</th>
	        <th data-options="field:'activityId'"  width="5%">活动ID</th>
	    	<th data-options="field:'hbName'"  width="10%">红包名称</th>
	    	<th data-options="field:'repertory'"  width="5%">库存</th>
	        <th data-options="field:'status'" width="6%"  formatter="formaterProductPrizeStatus">奖品状态</th>
	        <th data-options="field:'type'" formatter="formaterProductPrizeCategory" width="6%">奖品类别</th>
	        <th data-options="field:'needPoints'" width="5%" >奖品所需积分</th>
	        <th data-options="field:'details'" width="55%" >奖品详情</th>
	        <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	  		奖品名称:<input id="prizeName" name="prizeName" class="easyui-textbox"  size="15" style="width:200px"/>
	                   状态:<select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0'>不可用</option>
		  					<option value='1'>可用</option>
	  				</select>
	  				类别：<select id="type" name="type" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='1'>京东卡</option>
		  					<option value='2'>电子产品</option>
		  					<option value='3'>虚拟货物</option>
	  				</select>
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoDialog()">新增</a>
	    	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="importImgDialog()">奖品图片上传</a> -->
	    </form>
	</div>
	
	<%--奖品新增 --%>>
	<div id="addDrProductInfoDialog" class="easyui-dialog" title="新增奖品"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="addJsProductPrizeForm">
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">奖品名称：<input id="prizeName" name="prizeName" type="text" class="easyui-textbox" data-options="required:true"/>
					金额：<input id="price" name="price" type="text" class="easyui-numberbox" data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">奖品状态：
						<select name="status" style="width: 172px" class="easyui-combobox" id="status">
							<option value='0' selected="selected">不可用</option>
							<option value='1' selected="selected">可用</option>
						</select>
						类型：<select name="type" style="width: 172px" class="easyui-combobox" id="type">
						<option value='1'>京东卡</option>
						<option value='2'>电子产品</option>
						<option value='3'>虚拟货物</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">活动ID：<input id="activityId" name="activityId" type="text" class="easyui-textbox" />
					红包编号：<select id="hbCode" name="hbCode" class="easyui-combobox" style="width:150px">
							<option value=''>请选择</option>
							<c:forEach var="map" items="${hb}">
								<option value='${map.hbCode}'>${map.hbName}</option>
					        </c:forEach>
	  					</select>
					</td>
					
				</tr>
				<tr>
					<td align="left">所需积分：<input id="needPoints" name="needPoints" type="text" class="easyui-textbox" />
					库存：<input id="repertory" name="repertory" type="text" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">奖品详情：<textarea rows="5" cols="50" id="details" name="details" style="resize: none"></textarea>
				</tr>
				
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addRedEnvelope()">添加</a>
		</div>
	</div>
	
	<%--修改 --%>
	<div id="updateActivityWindow" class="easyui-dialog" title="修改奖品"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="updateSysProgramForm">
			<table id="jptable" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<input id="prId" name="prId" readonly="readonly" type="hidden" />
				<tr>
					<td align="left">奖品名称：<input id="prizeName" name="prizeName" type="text" class="easyui-textbox" data-options="required:true"/>
					金额：<input id="price" name="price" type="text" class="easyui-numberbox" data-options="validType:'maxLength[100]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">奖品状态：
						<select name="status" style="width: 172px" class="easyui-combobox" id="status">
							<option value='0' selected="selected">不可用</option>
							<option value='1' selected="selected">可用</option>
						</select>
						类型：<select name="type" style="width: 172px" class="easyui-combobox" id="type">
						<option value='1'>京东卡</option>
						<option value='2'>电子产品</option>
						<option value='3'>虚拟货物</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left">活动ID：<input id="activityId" name="activityId" type="text" class="easyui-textbox" />
					红包编号：<select id="hbCode" name="hbCode" class="easyui-combobox" style="width:150px">
						<option value=''>请选择</option>
							<c:forEach var="map" items="${hb}">
								<option value='${map.hbCode}' <c:if test="${settings.hb== map.hbCode}"> selected="selected" </c:if>>${map.hbName}</option>
					        </c:forEach>
	  					</select>
					</td>
					
				</tr>
				<tr>
					<td align="left">所需积分：<input id="needPoints" name="needPoints" type="text" class="easyui-textbox" />
					库存：<input id="repertory" name="repertory" type="text" class="easyui-textbox" />
					</td>
				</tr>
				
				<tr>
					<td align="left">奖品详情：<textarea rows="5" cols="50" id="details" name="details" style="resize: none"></textarea>
				</tr>
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updatePrize()">修改</a>
		</div>
	</div>
	
	<div id="uploadFileDialog" class="easyui-dialog" title="上传奖品图片"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#uploadFileDialogBtn'" 
		style="width:420px;height:200px;padding:15px;">
		<form id="uploadFileDialogForm" name="uploadFileDialogForm" enctype="multipart/form-data">
			<div class="easyui-panel"  style="border:0">
			<input id="prId" name="prId" readonly="readonly" type="hidden" />
			<%-- <div style="margin-bottom:20px">
					<div>奖品名称:</div>
					<select id="prid" name="prid" class="easyui-combobox" style="width:200px">
	  					<option value=''>请选择</option>
						<c:forEach var="map" items="${pridList}">
							<option value='${map.prId}'>${map.prizeName}</option>
				        </c:forEach>
  				</select>
				</div> --%>
				<div style="margin-bottom:20px">
					<div>请选择要上传的图片:</div>
					<input class="easyui-filebox" name="filename" data-options="prompt:'请选择图片...'" style="width:100%">
				</div>
			</div>
			<div id="uploadFileDialogBtn">
				<a id="uploadMemberInfo" href="javascript:void(0)" class="easyui-linkbutton" onclick="importImg()">上传</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
			</div>
		</form>
	</div>
	
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeForm").form("reset");
		$("#drProductPrizeList").datagrid("load", {});
	});
	//查询按钮
	function searchJsProductPrize(){
 		$('#drProductPrizeList').datagrid({
			queryParams: {
				name:$('#prizeName').val(),
				status:$('#status').combobox('getValue'),
				type:$('#type').combobox('getValue'),
			}
		});
	};
	

	
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改</a>    ';
		articleOper += '<a href="#" class="btn l-btn l-btn-small" onclick="importImgDialog('+index+')">上传奖品图片</a>';
		return articleOper;
	} 	

    	
	//跳转到添加页面
	function addDrProductInfoDialog(){  
		$("#addJsProductPrizeForm").form("reset");
		$("#addDrProductInfoDialog").dialog("open");
	}
	
	//
	
	//添加新奖品
	function addRedEnvelope(){
		var validate = $("#addJsProductPrizeForm").form("validate");
		if(!validate){
			return false;
		}
	
		$.ajax({
         	url: "${apppath}/prizemanage/addDrPrizeManage.do",
           	type: 'POST',
           	data:$("#addJsProductPrizeForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeList").datagrid("reload");
					$("#addDrProductInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.msg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}

	
	function updatePrize(){
		var validate = $("#updateSysProgramForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/prizemanage/updateDrPrizeManage.do",
            type: 'POST',
            data:$("#updateSysProgramForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#drProductPrizeList").datagrid("reload");
				$("#updateActivityWindow").dialog("close");
			}else{
				$.messager.alert("提示信息",result.msg);
			}
			}
        });
		 return false;
	}

	//跳转到修改页面
	function updateActivityWindow(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$("#updateSysProgramForm").form("load",row);
		$("#updateActivityWindow").dialog("open");
	}
	
	//活动图片上传窗口
	function importImgDialog(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$("#uploadFileDialogForm").form("reset");
		$("#uploadFileDialogForm").form("load",row);
		$("#uploadFileDialog").dialog("open");
	}
	
	//上传图片
	function importImg(){
		$("#uploadFileDialogForm").ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: '${apppath}/prizemanage/importImg.do', // 需要提交的 url
            success:function(result){
             	var resultJson = jQuery.parseJSON(result);
				if(resultJson.success){
					$.messager.alert("提示信息",resultJson.msg);
					$("#uploadFileDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",resultJson.msg);
				}
			}
        });
	}

	/* //修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	}  */
	/* //修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
 */
		
		//奖品状态	
	function formaterProductPrizeStatus(value,row,index){
		if(row.status == 0){
			return '不可用';
		}else if(row.status == 1){
			return '可用';
		}
	}

	function formaterProductPrizeCategory(value,row,index){
		if(value == " "){
			return '请选择';
		}
		if(value == 1){
			return '京东卡';
		}
		if(value == 2){
			return '电子产品';
		} 
		if(value == 3){
			return '虚拟货物';
		}
		
	}
	

	
	$(document).ready(function () {
/* 		$('#drProductPrizeList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../prizemanage/prizeLogCount.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeLogForm").serialize(), 
					success:function(data){
						$('#productPrizeCount').text(data.count);
					}
				});
			} 
    	}); */
	});
</script>
</body>
</html>

