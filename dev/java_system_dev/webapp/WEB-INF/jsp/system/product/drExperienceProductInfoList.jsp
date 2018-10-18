<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
</head>
<body>
	<table id="drExperienceProductInfoList" title="体验标管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../product/drexperienceProductInfoList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drProductInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'isSid'" hidden="true"></th>	    	
	    	<th data-options="field:'renewal'" hidden="true">判断是否续发</th>	
	    	<th data-options="field:'bespoke'" hidden="true">取消预约</th>		    	
	    	<th data-options="field:'code'" width="10%">产品编号</th>
	        <th data-options="field:'fullName'" width="15%">产品名称</th>
	       	<th data-options="field:'rate'" width="5%">产品利率</th>
	       	<th data-options="field:'alreadyRaiseAmount'" width="10%" styler="styleColor" formatter="formatAmount">已募集金额(元)</th>
	       	<th data-options="field:'experienceName'" width="5%">产品状态</th>
	        <th data-options="field:'status'" hidden="true">状态</th>
	       	<th data-options="field:'startDate'" width="5%" formatter="formatDateBoxFull">上架日期</th>
			<th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drProductInfoTools" style="padding:5px;height:750">
	  	<form id="drProductInfoForm">
	  		产品编号:<input id="searchDrProductInfoCode" name="code" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品简称:<input id="searchDrProductInfoSimpleName" name="simpleName" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品状态: <select  id="searchDrProductInfoStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value=''>全部</option>
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 5 || map.key == 6}">
							<c:choose>
									<c:when test="${map.key ==5}">
        					  			<option value='${map.key }'>开启</option>
    					 		  	</c:when>
									<c:when test="${map.key ==6}">
        								<option value='${map.key }'>停用</option>
      								</c:when>
							</c:choose>
						</c:if>
					</c:forEach>
	           		</select>
	    	<a id="searchDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrProductInfo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoBtn()">体验标新增</a>	
	    	<a id="exportDrProductInfo" href="javascript:exportDrProductInfo();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	<div id="updateDrProductInfoDialog" class="easyui-dialog" title="体验标修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#updateDrProductInfoBtn'" style="width:600px;height:400px;padding:5px;">
		<form id="updateDrProductInfoForm">
			<input type="hidden" id="drProductId"  name="id"/>	
			<table align="center" id = "updateTab">
				<tr>
					<td align="left">产品编号：</td>
					<td colspan="2">
						<input id ="drProductCode" name="code"type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">产品全称：</td>
					<td colspan="2">
						<input id="drProductFullName" name="fullName" type="text" class="easyui-textbox" disabled="disabled" data-options="validType:'maxLength[50]'"/>
					</td>
					<td align="left">产品简称：</td>
					<td colspan="2">
						<input id="drProductSimpleName" name="simpleName" type="text" class="easyui-textbox" disabled="disabled" data-options="validType:'maxLength[50]'"/>
					</td>
				</tr>
				<tr>
					<td align="left">年化利率：</td>
					<td colspan="2">
						<input id="productRate" name="rate" type="text" class="easyui-numberbox" disabled="disabled" data-options="required:true,min:0,max:20,precision:2"/>%
					</td>
					<td align="left">活动利率：</td>
					<td colspan="2">
						<input id="productActivityRate" name="activityRate" type="text" class="easyui-numberbox" disabled="disabled" data-options="required:true,min:0,max:10,precision:2"/>%
					</td>
				</tr>
				<tr>
					<td align="left">还款方式：</td>
					<td colspan="2">
						<select id = "productRepayType" name ="repayType" style="width: 172px" class="selector" disabled="disabled">
							<c:forEach items="${repayType}" var="map">
								<c:if test="${map.key == 1 || map.key == 2}">
								<option value='${map.key }'>${map.value }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
						
					<td align="left">产品期限：</td>
					<td colspan="2">
						<input id="productDeadline" name="deadline" type="text" class="easyui-numberbox" disabled="disabled" data-options="required:true,min:1,max:1000" />天
					</td>
				</tr>
				<tr>
					<td align="left">是否开启：</td>
					<!-- <td colspan="2">
						<input id="productStatus" name="status" value = "5" type="radio" />开启
						<input id="productStatus2" name="status" value = "6" type="radio" />停用
					</td> -->
					<td colspan="2">
					<select  id="drProductInfoStatus" name="status"  style="width:100px;" class="drProductInfoStatus">
						<c:forEach items="${status }" var="map">
							<c:if test="${map.key == 5 || map.key == 6}">
							<c:choose>
									<c:when test="${map.key ==5}">
        					  			<option value='${map.key }'>开启</option>
    					 		  	</c:when>
									<c:when test="${map.key ==6}">
        								<option value='${map.key }'>停用</option>
      								</c:when>
							</c:choose>
						</c:if>
					</c:forEach>
	           		</select>
	           		</td>
				</tr>
			</table>
		</form>
		<div id="updateDrProductInfoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateDrProductInfoForElse()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()">取消</a>
		</div>
	</div>
	
	<div id="addDrProductInfoStartDateDialog" class="easyui-dialog" title="上架提示"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductStartDateDialogBtn'" style="width:300px;height:200px;padding:5px;">
		<form id="addDrProductInfoStartDateForm">
			<input type="hidden" id="drProductStartDateId"  name="id"/>
			<table align="center">
				<tr>
					<td align="left">上架时间：</td>
					<td>
						<input id="addStartDate" name="startDate" type="text" class="easyui-datetimebox" data-options="required:true"/>
					</td>
				</tr>

			</table>
		</form>
		<div id="addDrProductStartDateDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addDrProductStartDate()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDrProductStartDa	teDialog()">取消</a>
		</div>
	</div>
<script type="text/javascript">

	//重置按钮
	$('#resetDrProductInfo').click(function(){
		$("#drProductInfoForm").form("reset");
		$("#drProductInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchDrProductInfo').click(function(){
 		$('#drExperienceProductInfoList').datagrid({
			queryParams: {
				code: $('#searchDrProductInfoCode').val(),
				simpleName: $('#searchDrProductInfoSimpleName').val(),
				status: $('#searchDrProductInfoStatus').combobox('getValue'),
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		/* if(row.status=="1" || row.status=="3"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoBtn('+index+')">修改</a>';
		}else if(row.status=="2"){
			return	'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoBtn('+index+')">修改</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductStartDateBtn('+index+')">上架</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductDeleteBtn('+index+')">作废</a>';
		}else{
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoForElseBtn('+index+')">修改</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'; */
					
// 			if(0<row.surplusDay && row.surplusDay<=5 && row.isSid == 1 && row.status=="8"){
// 				if(null == row.renewal){
// 					oper +="   "+'<a href="#" class="btn l-btn l-btn-small" onclick="addProductInfoRenewalBtn('+index+')">续发</a>';
// 				}
// 			}
//			if(row.bespoke == 1 && row.status=="5"){
//				oper +="   "+'<a href="#" class="btn l-btn l-btn-small" onclick="productCancelBespokeBtn('+index+')">取消预约</a>';
//			}
			var oper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateDrProductInfoForElseBtn('+index+')">修改</a>'+"   "+
			'<a href="#" class="btn l-btn l-btn-small" onclick="toShowProductInfoBtn('+index+')">查看</a>'; 
			return oper;
	//	}
	} 
	
	//跳转到产品添加页面
	function addDrProductInfoBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "体验标新增",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toAddExperienceDrProductInfo.do' ></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//跳转到产品修改页面

	function updateDrProductInfoBtn(index) {
		$('#drExperienceProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drExperienceProductInfoList').datagrid('getSelected');
		var mainTab = parent.$('#main-center');
		var detailTab = {
			title : "体验标修改",
			content : "<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toUpdateExperienceProductInfo.do?id="
					+ row.id + "'></iframe>",
			closable : true
		};
		mainTab.tabs("add", detailTab);
	}

	//跳转到产品续发页面
	function addProductInfoRenewalBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$
				.ajax({
					url : "${apppath}/product/validatorAddDrProductInfoRenewal.do?id="
							+ row.id,
					dataType : "json",
					success : function(result) {
						if (result.success) {
							var mainTab = parent.$('#main-center');
							var detailTab = {
								title : "产品续发",
								content : "<iframe width='100%' height='100%' frameborder='0' src='${apppath}/product/toAddProductInfoRenewal.do?id="
										+ row.id + "'></iframe>",
								closable : true
							};
							mainTab.tabs("add", detailTab);
						} else {
							$.messager.alert("提示信息", result.errorMsg,
									function() {
										$('#drProductInfoList').datagrid(
												'reload');
									});
						}
					}
				});
	}

	//上架操作
	function updateDrProductStartDateBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$('#drProductStartDateId').val(row.id);
		$("#addStartDate").datetimebox("setValue", getNowFormatDate());
		$("#addDrProductInfoStartDateDialog").dialog("open");
	}

	//上架操作
	function addDrProductStartDate(index) {
		var validate = $("#addDrProductInfoStartDateForm").form("validate");
		if (!validate) {
			return false;
		}
		var url = "${apppath}/product/updateDrProductStatus.do?id="
				+ $('#drProductStartDateId').val() + "&startDate="
				+ $('#addStartDate').datetimebox('getValue');
		$.ajax({
			url : url,
			dataType : "json",
			success : function(result) {
				if (result.success) {
					$.messager.alert("操作提示", result.msg);
					$('#drProductInfoList').datagrid('reload');
					$("#addDrProductInfoStartDateDialog").dialog("close");
				} else {
					$.messager.alert("提示信息", result.errorMsg);
					$('#drProductInfoList').datagrid('reload');
					$("#addDrProductInfoStartDateDialog").dialog("close");
				}
			}
		});
	}

	//取消预约操作
	function productCancelBespokeBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$.messager
				.confirm(
						"取消预约提示",
						"您取消上架时间为 " + formatDateBoxFull(row.startDate),
						function(ensure) {
							if (ensure) {
								var url = "${apppath}/product/updateDrProductCancelBespoke.do?id="
										+ row.id;
								$.ajax({
									url : url,
									dataType : "json",
									success : function(result) {
										if (result.success) {
											$('#drProductInfoList').datagrid(
													'reload');
											$.messager
													.alert("操作提示", result.msg);
										} else {
											$('#drProductInfoList').datagrid(
													'reload');
											$.messager.alert("提示信息",
													result.errorMsg);
										}
									}
								});
							}
						});
	}

	//作废操作
	function updateDrProductDeleteBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$.messager.confirm("操作提示", "是否作废这个产品？", function(ensure) {
			if (ensure) {
				var url = "${apppath}/product/updateDrProductDelete.do?id="
						+ row.id;
				$.ajax({
					url : url,
					dataType : "json",
					success : function(result) {
						if (result.success) {
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						} else {
							$('#drProductInfoList').datagrid('reload');
							$.messager.alert("提示信息", result.errorMsg);
						}
					}
				});
			}
		});
	}

	//跳转到产品显示页面
	function toShowProductInfoBtn(index) {
		$('#drExperienceProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drExperienceProductInfoList').datagrid('getSelected');
		var mainTab = parent.$('#main-center');
		var detailTab = {
			title : "产品显示",
			content : "<iframe scrolling='yes' width='100%' height='100%' frameborder='0' src='${apppath}/product/toShowDrExperienceProductInfo.do?id="
					+ row.id + "'></iframe>",
			closable : true
		};
		mainTab.tabs("add", detailTab);
	}

	//更改是否显示
	function updateDrProductIsShowBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$.messager.confirm('操作提示', '你确定更改吗？', function(r) {
			if (r) {
				var url = "../product/updateDrProductIsShow.do?id=" + row.id
						+ "&isShow=" + row.isShow;
				$.ajax({
					url : url,
					dataType : "json",
					success : function() {
						$('#drProductInfoList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
				});
			}
		});
	}
	//更改是否热推
	function updateDrProductIsHotBtn(index) {
		$('#drProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drProductInfoList').datagrid('getSelected');
		$.messager.confirm('操作提示', '你确定更改吗？', function(r) {
			if (r) {
				var url = "../product/updateDrProductIsHot.do?id=" + row.id
						+ "&isHot=" + row.isHot;
				$.ajax({
					url : url,
					dataType : "json",
					success : function() {
						$('#drProductInfoList').datagrid('reload');
						$.messager.alert("操作提示", "操作成功");
					}
				});
			}
		});
	}

	//打开dialog
	function updateDrProductInfoForElseBtn(index) {
		$('#drExperienceProductInfoList').datagrid('selectRow', index);// 关键在这里 
		var row = $('#drExperienceProductInfoList').datagrid('getSelected');
		var url = "${apppath}/product/queryDrProductInfo.do?id=" + row.id;
		$.ajax({
			url : url,
			dataType : "json",
			success : function(result) {
				if (result.success) {
					$("#drProductId").val(result.map.drProductInfo.id);
					$("#drProductCode").textbox('setValue',
							result.map.drProductInfo.code);
					$("#drProductType").textbox('setValue',
							result.map.drProductInfo.type);
					$("#drProductFullName").textbox('setValue',
							result.map.drProductInfo.fullName);
					$("#drProductSimpleName").textbox('setValue',
							result.map.drProductInfo.simpleName);
					$("#productRate").numberbox('setValue',
							result.map.drProductInfo.rate);
					$("#productActivityRate").numberbox('setValue',
							result.map.drProductInfo.activityRate);
					/* if(result.map.drProductInfo.status == 5){
						$("#productStatus").attr("checked","checked");
					}else if(result.map.drProductInfo.status == 6){
						$("#productStatus2").attr("checked","checked");
					} */
					$(".drProductInfoStatus").val(result.map.drProductInfo.status);
					$(".selector").val(result.map.drProductInfo.repayType);
					$("#productDeadline").textbox('setValue',
							result.map.drProductInfo.deadline);

					if (result.map.drProductInfo.status == 5
							&& result.map.drProductInfo.type != 1) {
						$("#raiseDeadline").textbox({
							'editable' : true
						});
					} else {
						$("#raiseDeadline").textbox({
							'editable' : false
						});
					}

					$("#updateDrProductInfoDialog").dialog("open");
				} else {
					$.messager.alert("提示信息", result.errorMsg);
				}
			}
		});
	}

	function updateDrProductInfoForElse() {
		var validate = $("#updateDrProductInfoForm").form("validate");
		if (!validate) {
			return false;
		}
		//var productStatus = $('input:radio:checked').val();
		$.ajax({
			url : "${apppath}/product/updateDrExperienceProductInfo.do",
			type : 'POST',
			data :  { 
						id: $("#drProductId").val(), 
						code: $("#drProductCode").val(), 
						fullName :$("#drProductFullName").val(),
						simpleName :$("#drProductSimpleName").val(),
						rate :$("#productRate").val(),
						activityRate :$("#productActivityRate").val(),
						repayType :$("#productRepayType").val(),
						deadline :$("#productDeadline").val(),
						status :$("#drProductInfoStatus").val()
					},
			success : function(result) {
				if (result.success) {
					$.messager.alert("提示信息", result.msg);
					$("#drExperienceProductInfoList").datagrid("reload");
					$("#updateDrProductInfoDialog").dialog("close");
				} else {
					$.messager.alert("提示信息", result.errorMsg);
				}
			}
		});
	}

	//添加状态链接
	function formatOperIsShow(value, row, index) {
		if (row.isShow == "1") {
			return '<a href="#" onclick="updateDrProductIsShowBtn(' + index
					+ ')">是</a>';
		} else {
			return '<a href="#" onclick="updateDrProductIsShowBtn(' + index
					+ ')">否</a>';
		}
	}
	//添加热推状态
	function formaterOperisHot(value, row, index) {
		if (row.isHot == "1") {
			return '<a href="#" onclick="updateDrProductIsHotBtn(' + index
					+ ')">是</a>';
		} else {
			return '<a href="#" onclick="updateDrProductIsHotBtn(' + index
					+ ')">否</a>';
		}
	}

	//是否是续发产品
	function formaterOperisSecondaryProducts(value, row, index) {
		if (row.fid == null || row.fid == 'undefined') {
			return '否';
		} else {
			return '是';
		}
	}

	//关闭Dialog
	function closeDialog() {
		$("#drExperienceProductInfoList").datagrid("reload");
		$("#updateDrProductInfoDialog").dialog("close");
	}

	//关闭Dialog
	function closeDrProductStartDateDialog() {
		$("#drExperienceProductInfoList").datagrid("reload");
		$("#addDrProductInfoStartDateDialog").dialog("close");
	}

	//导出
	function exportDrProductInfo() {
		window.location.href = "../product/exportDrExperienceProductInfo.do?code="
				+ $('#searchDrProductInfoCode').val()
				+ "&simpleName="
				+ encodeURIComponent(encodeURIComponent($(
						'#searchDrProductInfoSimpleName').val())) + "&status="
				+ $('#searchDrProductInfoStatus').combobox('getValue');
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
	function formatRemainDays(value, row, index) {
		if (row.status == '5') {
			if (value == '1') {
				return value + '<img src="../images/bell.png"/>';
			} else
				return value;
		} else
			return '';
	}
</script>
</body>
</html>

