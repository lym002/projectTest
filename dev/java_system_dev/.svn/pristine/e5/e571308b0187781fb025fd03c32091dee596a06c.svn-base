<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
	<!-- 修改系统发放优惠券规则 -->
	<div style="width:auto;height:auto;padding:10px;">
		<form id="updateIssuedRulesForm">
			<input id="updateId" name="id" value="${drCouponsIssuedRules.id}" hidden="hidden">
			<input id="updateStatus" name="status" value="${drCouponsIssuedRules.status}" hidden="hidden">
			<table align="center">
				<tr>
					<td>赠送类型:</td>
					<td>
						<select id="updateType" name="type" style="width:100px;" class="easyui-combobox" data-options="required:true">
							<option value='0' <c:if test="${drCouponsIssuedRules.type==0}"> selected="selected" </c:if>>注册赠送</option>
							<option value='1' <c:if test="${drCouponsIssuedRules.type==1}"> selected="selected" </c:if>>投资后赠送</option>
							<option value='2' <c:if test="${drCouponsIssuedRules.type==2}"> selected="selected" </c:if>>手动发送</option>
							<option value='3' <c:if test="${drCouponsIssuedRules.type==3}"> selected="selected" </c:if>>绑卡赠送</option>
							<option value='4' <c:if test="${drCouponsIssuedRules.type==4}"> selected="selected" </c:if>>邀请好友三重好礼</option>
							<option value='5' <c:if test="${drCouponsIssuedRules.type==5}"> selected="selected" </c:if>>感恩活动</option>
							<option value='6' <c:if test="${drCouponsIssuedRules.type==6}"> selected="selected" </c:if>>老客户活动</option>
						</select>
					</td>
					<td>活动名称:</td>
					<td>
						<input id="updateName" name="name" style="width:150px" value="${drCouponsIssuedRules.name }" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>开始时间≥:</td>
    				<td>
    					<input id="updateStartTime" name="startTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" value="<fmt:formatDate value="${drCouponsIssuedRules.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
    				</td>
    				<td>结束时间〈:</td>
    				<td>
    					<input id="updateEndTime" name="endTime" style="width: 150px" class="easyui-datetimebox" data-options="required:true" value="<fmt:formatDate value="${drCouponsIssuedRules.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
    				</td>
				</tr>
				<tr>
					<td>使用场景:</td>
					<td>
						<select id="updateScene" name="scene" style="width:150px;" class="easyui-combobox" 
						data-options="onLoadSuccess:function(record){
							var type = '${drCouponsIssuedRules.couponType}';
				           var scene = $('#updateScene').combobox('getValue');
				           $('#updateCoupons').combogrid({
				           	 	panelWidth:380,
								multiple: true,
								multiline:true,
								editable:false,
								idField: 'id',
								textField: 'name',
								url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?'1,2,3,4,5':type),
								method: 'get',
								columns: [[
									{field:'coupons',checkbox:true},
									{field:'name',title:'券名称',width:80},
									{field:'enableAmount',title:'启用金额',width:50},
									{field:'productDeadline',title:'产品期限',width:50},
								]],
								fitColumns: true ,
								onLoadSuccess:function(data){
									var oldType = '${drCouponsIssuedRules.couponType}';
							        var oldScene = '${drCouponsIssuedRules.scene}';
							        if(type == oldType && scene == oldScene){
							          	var coupons = '${drCouponsIssuedRules.coupons}';
										var obj = coupons.split(',');
										$('#updateCoupons').combogrid('setValues', obj);
							        }
								}
				           });
						},
						onSelect:function(record){
				           var type = $('#updateCouponType').combobox('getValue');
				           var scene = $('#updateScene').combobox('getValue');
				           $('#updateCoupons').combogrid('clear');
				           $('#updateCoupons').combogrid({
				           	 	panelWidth:380,
								multiple: true,
								multiline:true,
								editable:false,
								idField: 'id',
								textField: 'name',
								url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?'1,2,3,4,5':type),
								method: 'get',
								columns: [[
									{field:'coupons',checkbox:true},
									{field:'name',title:'券名称',width:80},
									{field:'enableAmount',title:'启用金额',width:50},
									{field:'productDeadline',title:'产品期限',width:50},
								]],
								fitColumns: true ,
								onLoadSuccess:function(){
									var oldType = '${drCouponsIssuedRules.couponType}';
							        var oldScene = '${drCouponsIssuedRules.scene}';
							        if(type == oldType && scene == oldScene){
							          	var coupons = '${drCouponsIssuedRules.coupons}';
										var obj = coupons.split(',');
										$('#updateCoupons').combogrid('setValues', obj);
							        }
								}
				           });
				          
				        },editable:false">
							<option value=''>--全部--</option>
							<option value='0' <c:if test="${drCouponsIssuedRules.scene ==0}"> selected="selected" </c:if>>电销使用</option>
							<option value='1' <c:if test="${drCouponsIssuedRules.scene ==1}"> selected="selected" </c:if>>活动使用</option>
							<option value='2' <c:if test="${drCouponsIssuedRules.scene ==2}"> selected="selected" </c:if>>运营使用</option>
						</select>
					</td>
					<td>优惠券类型:</td>
					<td>
						<select id="updateCouponType" name="couponType" style="width:150px;" class="easyui-combobox" data-options="
						onLoadSuccess:function(record){
							var type = $('#updateCouponType').combobox('getValue');
			           		var scene = $('#updateScene').combobox('getValue');
				 		 	$('#updateCoupons').combogrid({
				           	 	panelWidth:380,
								multiple: true,
								multiline:true,
								editable:false,
								idField: 'id',
								textField: 'name',
								url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?'1,2,3,4,5':type),
								method: 'get',
								columns: [[
									{field:'coupons',checkbox:true},
									{field:'name',title:'券名称',width:80},
									{field:'enableAmount',title:'启用金额',width:50},
									{field:'productDeadline',title:'产品期限',width:50},
								]],
								fitColumns: true ,
								onLoadSuccess:function(){
									var oldType = '${drCouponsIssuedRules.couponType}';
							        var oldScene = '${drCouponsIssuedRules.scene}';
							        if(type == oldType && scene == oldScene){
							          	var coupons = '${drCouponsIssuedRules.coupons}';
										var obj = coupons.split(',');
										$('#updateCoupons').combogrid('setValues', obj);
							        }
								}
				           });
				           var coupons = '${drCouponsIssuedRules.coupons}';
				           var obj = coupons.split(',');
				           $('#updateCoupons').combogrid('setValues', obj)
					},
					onSelect:function(record){
			           var type = $('#updateCouponType').combobox('getValue');
			           var scene = $('#updateScene').combobox('getValue');
			           $('#updateCoupons').combogrid('clear');
			           $('#updateCoupons').combogrid('setValue','');
			           $('#updateCoupons').combogrid({
			           	 	panelWidth:380,
							multiple: true,
							multiline:true,
							editable:false,
							idField: 'id',
							textField: 'name',
							url: '../activity/drActivityByCouponTypeList.do?scene='+scene+'&types='+(type==0?'1,2,3,4,5':type),
							method: 'get',
							columns: [[
								{field:'coupons',checkbox:true},
								{field:'name',title:'券名称',width:80},
								{field:'enableAmount',title:'启用金额',width:50},
								{field:'productDeadline',title:'产品期限',width:50},
							]],
							fitColumns: true ,
							onLoadSuccess:function(){
									var oldType = '${drCouponsIssuedRules.couponType}';
							        var oldScene = '${drCouponsIssuedRules.scene}';
							        if(type == oldType && scene == oldScene){
							          	var coupons = '${drCouponsIssuedRules.coupons}';
										var obj = coupons.split(',');
										$('#updateCoupons').combogrid('setValues', obj);
							        }
								}
			           });
			        },editable:false">
			        		<option value=''>--全部--</option>
							<c:forEach items="${couponTypes}" var="map">
								<option value='${map.key }' <c:if test="${drCouponsIssuedRules.couponType ==map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>cps使用:</td>
					<td>
						<select id="isCps" name="isCps" style="width:150px;" class="easyui-combobox" data-options="required:true">
							<option value='2' >--全部--</option>
							<option value='0' <c:if test="${drCouponsIssuedRules.isCps==0}"> selected="selected"</c:if>>非cps</option>
							<option value='1' <c:if test="${drCouponsIssuedRules.isCps==1}"> selected="selected"</c:if>>是cps</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>优惠券:</td>
					<td colspan="3">
						<input class="easyui-combogrid"  style="width:400px;" id="updateCoupons" name="coupons"  data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>站内信:</td>
					<td colspan="3">
						<input class="easyui-textbox" name="message" style="height:100px;width:400px;" data-options="multiline:true,validType:'maxLength[2000]'" value="${drCouponsIssuedRules.message}"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="#updateIssuedRulesBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updateIssuedRules()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeIssuedRulesWindow()">取消</a>
		</div>
	</div>
	
<script type="text/javascript">
	//关闭窗口
	function closeIssuedRulesWindow(){
		$("#updateIssuedRulesWindow").window("close");
	} 
	
	//修改
	function updateIssuedRules(){
		var validate = $("#updateIssuedRulesForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/activity/updateIssuedRules.do?sid=0",
            type: 'POST',
            data:$("#updateIssuedRulesForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateIssuedRulesWindow").window("close");
					$("#updateIssuedRulesForm").form("clear");
					$("#couponsIssuedRulesList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
	
</script>
</body>
</html>