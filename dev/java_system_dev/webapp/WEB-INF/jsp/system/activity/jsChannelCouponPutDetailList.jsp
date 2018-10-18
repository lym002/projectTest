<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<c:if test="${isAudit !=1}"><sapn>备注:${couponPut.remark}</span></c:if>
	<div style="width:100%;height:90%;padding:10px;">
		<table id="couponPutDetailList" title=''
		class="easyui-datagrid" style="height:99%;width:99.9%"
		data-options="singleSelect:true,
		    fitColumns:true, url: '../activity/getCouponPutDetailList.do?id=${couponPut.id}',
		    method:'post',rownumbers:true, 
		    pagination:true,toolbar:'#auditCouponPutTools'">
			<thead>
		    <tr>
		    	<th data-options="field:'id'"  hidden="true">ID</th>
		    	<th data-options="field:'mobile'" width="50px">名称</th>
		    	<th data-options="field:'recommCodes'" width="50px">推荐码</th>
		    	<th data-options="field:'channelType',formatter:formatOper" width="60px">渠道类型</th>
		        <th data-options="field:'amount'" width="80px" >数值</th>
		        <th data-options="field:'code'" width="100px" >编号</th>
		        <th data-options="field:'status'" width="50px" formatter="formatStatus">校验结果</th>
		    </tr>
		    </thead>
		</table>
		<div id="auditCouponPutTools" style="padding:5px;height:750">
<!-- 		  	<c:if test="${isAudit == 1}"> -->
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="confirmCouponPut()">确认审核</a>
<!-- 			</c:if> -->
		</div>
	</div>
	<!-- 新增-->
	<div id="auditCouponPutDialog" class="easyui-dialog" title="审核"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#auditCouponPutBtn'"
		style="width:400px;height:200px;padding:20px;">
		<form id="auditCouponPutForm" enctype="multipart/form-data">
			<table align="center">
				<tr>
					<td >备注:</td>
					<td>
						<input id="remark" name="remark" style="width:200px;height:80px"  class="easyui-textbox" data-options="multiline:true">
					</td>
				</tr>			
			</table>
		</form>
		<div id="auditCouponPutBtn"  style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="auditCouponPut(1)">通过</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="auditCouponPut(0)">不通过</a>
		</div>
	</div>
	
	<div id="updateCouponPutWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:800px;height:600px;padding:10px;">
	</div>
<script type="text/javascript">
	//添加基本操作链接
	function formatOper(val,row,index){
		var couponPutOper = ''
		if(row.channelType == 0){
			couponPutOper = '非cps';
		}
		else if(row.channelType == 1){
			couponPutOper = 'cps';
		}
		return couponPutOper;
	}

	//添加基本操作链接
	function formatOper(val,row,index){
		var couponPutOper = ''
		if(row.channelType == 0){
			couponPutOper = '非cps';
		}
		else if(row.channelType == 1){
			couponPutOper = 'cps';
		}
		return couponPutOper;
	}

	$(function(){
		var title = "名称：${couponPut.name}   导入时间："+formatDate('${couponPut.addtime}')+"  奖励类型："+formatType('${couponPut.type}')+" 状态："+formatPutStatus('${couponPut.status}');
		$("#couponPutDetailList").attr("title",title);	
	});
	//时间格式
	function formatDate(value,row,index){ 
		var options = {year: 'numeric',
			    month: '2-digit',
			    day: '2-digit'};
		if(value == null){
			return "";
		}
		options.timeZone = "UTC";
		options.timeZoneName = "short";
		var unixTimestamp = new Date(value);  
		var local = unixTimestamp.toLocaleString("en-ZH", options);
		return local;  
	}
		//修改显示状态信息
	function formatType(value,row,index){
		if(value == 1){
			return '红包';
		}else if(value == 2){
			return '加息券';
		}else if(value == 3){
			return '体验金';
		}else if(value == 4){
			return '翻倍券';
		}else if(value == 5){
			return '比例红包';
		}
	}
	function formatPutStatus(value){//0:待校验1:校验失败2:待审核3:成功4:失败
		if(value == 0){
			return '待校验';
		}else if(value == 1){
			return '校验失败';
		}else if(value == 2){
			return '待审核';
		}else if(value == 3){
			return '审核成功';
		}else if(value == 4){
			return '审核不通过';
		}
		return "未校验";
	}
	function formatStatus(value,row,index){//1校验通过,2错误信息备注3编号不存在4数值错误5手机号码不存在
		if(value == 1){
			return '校验通过';
		}else if(value == 2){
			return '错误信息备注';
		}else if(value == 3){
			return '编号不存在';
		}else if(value == 4){
			return '数值错误';
		}else if(value == 5){
			return '用户不存在';
		}else if(value == 6){
			return '渠道类型错误';
		}
		return "未校验";
	}
	//确认审核
	function confirmCouponPut(){
	   	$("#auditCouponPutForm").form("reset");
		$("#auditCouponPutDialog").dialog("open");
	}
	
	//审核
	function auditCouponPut(ispass){
		var id = '${couponPut.id}';
		var remark = $("#remark").textbox("getValue");
		if(remark){
			remark = remark.replace(/(^\s*)|(\s*$)/g, "");
			if(remark.length > 50){
				$.messager.alert("提示信息","备注类容过长");
				return false;
			}
		}
		var cf = "不通过";
		if(ispass == 1){
			cf = "通过";
		}
		 $.messager.confirm("操作提示", "您确定要执行 ["+cf+"] 操作吗？", function (data) {  
            if (data) {  
		      	$.ajax({
		          	url: "${apppath}/activity/toAuditCouponPut.do",
		            type: 'POST',
		            data:{"id":id,"ispass":ispass,"remark":remark},  
		            success:function(result){
						if(result.success){
						   $(function () {  
						        $.messager.alert("操作提示", result.msg, "info", function () {  
									var mainTab = parent.$('#main-center');

									var currTab = parent.$('#main-center').tabs('getTab',"渠道优惠券发放审核");
									if (currTab != null) {
										var content = '<iframe scrolling="no" frameborder="0"  src="../activity/toChannelCouponPut.do?isAudit=1" style="width:100%;height:100%;"></iframe>';
										mainTab.tabs
												('update',{tab : currTab,
													options : {
														content : content
													// 新内容的URL
													}
												});
									}
									mainTab.tabs("close",mainTab.tabs('getTabIndex',mainTab.tabs("getSelected")));//
						        });  
						    });  
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
		        });
            } 
        });  
	}
	
</script>
</body>
</html>

