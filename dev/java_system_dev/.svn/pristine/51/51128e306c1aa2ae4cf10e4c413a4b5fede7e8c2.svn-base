<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
</head>
<body>
<div style="width:auto;height:auto;padding:10px;">
	<form id="updateActivityForm" >
	    	<table align="center">
				<input id="updateActivityId" name="id" value="${drActivityParameter.id}" readonly="readonly" type="hidden"/>
				<tr>
					<td style="width:100px">红包编号:</td>
					<td colspan="2"><input id="updateCode" type="text" name="code" class="easyui-textbox" value="${drActivityParameter.code}" readonly="readonly"/></td>
				</tr>
				<tr>
					<td style="width:100px">红包名称:</td>
					<td><input type="text" id="updateName" name="name" class="easyui-textbox" value="${drActivityParameter.name}" data-options="required:true" readonly="readonly"/></td>
					<c:if test="${drActivityParameter.type == 1}">
						<td style="width:100px">红包金额:</td>
						<td><input type="text" id="updateAmount" name="amount" class="easyui-numberbox" value="${drActivityParameter.amount}" data-options="precision:2,groupSeparator:',',required:true" readonly="readonly"/>
						</td>
					</c:if>
					<c:if test="${drActivityParameter.type == 5}">
						<td style="width:100px">红包比率:</td>
						<td><input type="text" id="updateRaisedRates" name="raisedRates" class="easyui-numberbox" value="${drActivityParameter.raisedRates}" data-options="precision:2,groupSeparator:',',required:true" readonly="readonly"/>
						</td>
						<td><span>%</span></td>
					</c:if>
					
				</tr>
				<tr>
					<td>启用金额:</td>
					<td><input type="text" id="updateEnableAmount" name="enableAmount" class="easyui-numberbox" value="${drActivityParameter.enableAmount}" data-options="groupSeparator:',',required:true" readonly="readonly"/></td>
					<td style="width:100px">发放数量:</td>
					<td><input type="text" id="updateGrantQty" name="grantQty" class="easyui-numberbox" value="${drActivityParameter.grantQty}" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>适用场景：</td>
					<td colspan="3">
						<input id="telephoneSales" name="applicableScenarios" type="radio" value="0" <c:if test="${drActivityParameter.applicableScenarios.contains('0')}"> checked="checked" </c:if>  disabled="disabled"/>电销使用 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="activity" name="applicableScenarios" type="radio"  value="1"  <c:if test="${drActivityParameter.applicableScenarios.contains('1')}"> checked="checked" </c:if> disabled="disabled"/>活动使用&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="operational" name="applicableScenarios" type="radio"  value="2"  <c:if test="${drActivityParameter.applicableScenarios.contains('2')}"> checked="checked" </c:if> disabled="disabled"/>运营使用
					</td>
				</tr>
				<%-- <tr>
					<td>适用产品：</td>
					<td colspan="3">
						<input id="optimization" disabled="disabled" name="applicableProducts" type="checkbox" value="2" <c:if test="${drActivityParameter.applicableProducts.contains('2')}"> checked="checked" </c:if>/>票据优选 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="security" disabled="disabled" name="applicableProducts" type="checkbox" value="3" <c:if test="${drActivityParameter.applicableProducts.contains('3')}"> checked="checked" </c:if>/>票据安选
					</td>
				</tr> --%>
				<tr>
					<td>适用用户：</td>
					<td colspan="3">
						<input id="all" name="applicableUser" disabled="disabled" type="radio" value="0" <c:if test="${drActivityParameter.applicableUser==0}"> checked="checked" </c:if> />所有用户 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="general" name=applicableUser disabled="disabled" type="radio" value="1" <c:if test="${drActivityParameter.applicableUser==1}"> checked="checked" </c:if> />普通用户 &nbsp;&nbsp;&nbsp;&nbsp;
						<input id="woolParty" name="applicableUser" disabled="disabled" type="radio" value="2" <c:if test="${drActivityParameter.applicableUser==2}"> checked="checked" </c:if> />羊毛党
					</td>
				</tr>
				<tr>
					<td>有效期限：</td>
					<td style="width:120px">
						<input readonly="readonly" style="width:110px" id="updateDeadline" name="deadline" type="text" value="${drActivityParameter.deadline}" class="easyui-numberbox" data-options="required:true" > (天) 
					</td>
					<td>产品期限≥：</td>
					<td>
						<input id="updateProductDeadline" style="width:110px" disabled="disabled" name="productDeadline" class="easyui-textbox" value="${drActivityParameter.productDeadline}"  data-options="required:true" />天
					</td>
				</tr>
				<tr>
					<td>红包状态：</td>
					<td>
						<select id="searchActivityStatus" name="status" style="width:100px;"class="easyui-combobox" disabled="disabled">
							<option value='0' <c:if test="${drActivityParameter.status==0}"> selected="selected" </c:if> >生效中</option>
							<option value='1' <c:if test="${drActivityParameter.status==1}"> selected="selected" </c:if>>使用中</option>
							<option value='2' <c:if test="${drActivityParameter.status==2}"> selected="selected" </c:if>>已失效</option>
						</select> 
					</td>
					<td>剩余数量：</td>
					<td><input id="updateSurplusQty" name="surplusQty" readonly="readonly" type="text" value="${drActivityParameter.surplusQty}"/></td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:20px;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitActivityUpdate()">确认修改</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateStatus()">关闭红包</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeActivityUpdateWindow()">取消</a>
	    </div>
</div>
<script type="text/javascript">
	
	//关闭窗口
	function closeActivityUpdateWindow(){
		$("#updateActivityWindow").window("close");
	}
	
	function updateStatus(){
		$.ajax({
			url:"${apppath}/activity/updateStatus.do",
			type:"POST",
			data:$("#updateActivityForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateActivityWindow").window("close");
					$("#updateActivityForm").form("clear");
					$("#redEnvelopeList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
	}
	
	//提交
	function submitActivityUpdate(){
		var validate = $("#updateActivityForm").form("validate");
		if(!validate){
			return false;
		}
		if(!validata("applicableScenarios")){
			$.messager.alert("提示信息","选择适用场景");
			return false;
		}
		/* if(!validata("applicableProducts")){
			$.messager.alert("提示信息","选择适用产品");
			return false;
		} */
		if(!validata("applicableUser")){
			$.messager.alert("提示信息","选择适用用户群");
			return false;
		}
		$.ajax({
			url:"${apppath}/activity/modifyActivity.do",
			type:"POST",
			data:$("#updateActivityForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateActivityWindow").window("close");
					$("#updateActivityForm").form("clear");
					$("#redEnvelopeList").datagrid("reload");
					/* var currTab =parent.$('main-center').tabs('getTab',"红包管理");
					var content = '<iframe scrolling="no" frameborder="0"  src="../activity/redEnvelopeManager.do" style="width:100%;height:100%;"></iframe>';  
					parent.$('#main-center').tabs('update', {
						tab: currTab,
						options: {
							content: content  // 新内容的URL
						}
					}); */
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
		
	}
	
	function validata(str){
		var checkboxes = document.getElementsByName(str);
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
     			return true;
     		}
		}
		return false;
	}
	
	/* var varify;//用于查询验证,验证开始时间是否小于结束时间
	$.extend($.fn.validatebox.defaults.rules, {//验证开始时间小于结束时间
		md: {
			validator: function(value, param){
				startTime2 =$(param[0]).datetimebox('getValue');
				var d1 = $.fn.datebox.defaults.parser(startTime2);
				var d2 = $.fn.datebox.defaults.parser(value);
				varify= d2>=d1;
				return varify;
			},
			message: '结束时间要大于开始时间！'
		}
	})
	
	 $.fn.datebox.defaults.formatter = function(date){//对于时间格式的转换
		return date.format("yyyy-MM-dd");
	};
	
	//格式化日期
	Date.prototype.format = function (format) {  
	    var o = {  
	        "M+": this.getMonth() + 1, // month  
	        "d+": this.getDate(), // day  
	        "h+": this.getHours(), // hour  
	        "m+": this.getMinutes(), // minute  
	        "s+": this.getSeconds(), // second  
	        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
	        "S": this.getMilliseconds()  
	        // millisecond  
	    }  
	    if (/(y+)/.test(format))  
	        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
	            .substr(4 - RegExp.$1.length));  
	    for (var k in o)  
	        if (new RegExp("(" + k + ")").test(format))  
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
	    return format;  
	}  */
</script>
</body>
</html>