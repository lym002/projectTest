<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
</head>
<body>
	<!-- 新增APP更新 -->
	<!-- 新增-->
<div style="width:auto;height:auto;padding:10px;">
		<form id="updateAppPushForm">
			<table align="center">
				<tr>

					<td >推送渠道:</td>
					<td>
						<select id="channel" name="channel" class="easyui-combobox"
							data-options="    
						        onSelect: function(rec){
						        	channleRecs(rec);
						        },editable:false" 
						
						>							
							<option value="0" <c:if test="${result.channel eq 0 }">selected="selected"</c:if>>极光</option>
							<option value="1" <c:if test="${result.channel eq 1 }">selected="selected"</c:if>>友盟</option>
						</select>
					</td>
				</tr>
				<tr>
						<td >推送平台:</td>
					<td>
						<select id="platform" name="platform" class="easyui-combobox">							
							<option value="" >全部</option>
							<option value="1" <c:if test="${result.platform eq 1 }">selected="selected"</c:if>>IOS</option>
							<option value="2" <c:if test="${result.platform eq 2 }">selected="selected"</c:if>>Android</option>
						</select>
					</td>
				</tr>
				<tr>
					<td >标题:</td>
					<td>
						<input id="id" name="id" value="${result.id}" type="hidden">
						<input id="title" name="title"  value="${result.title}" style="width:150px" class="easyui-textbox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td >发送内容:</td>
					<td>
						<input id="content" name="content" value="${result.content}" class="easyui-textbox" data-options="multiline:true,required:true" style="width:240px;height:60px;">
						<c:if test="${result.status == 1 }">!已启用</c:if>
						<c:if test="${result.status == 3 }">!已删除</c:if>
					</td>
				</tr>	
				<tr>
					<td >url:</td>
					<td>
						<input id="url" name="url" value="${result.url}" class="easyui-textbox" style="width:240px">
					</td>
				</tr>					
					<tr style="height:50px;">
						<td colspan="2">请锁定用户群体:</td>
					</tr>
					<tr>
						<td>所在城市:</td>
						<td>
							<select id="city" name="city" class="easyui-combobox">
								<option value="" <c:if test="${result.city eq null }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${result.city eq 0 }">selected="selected"</c:if>>上海</option>
								<option value="1" <c:if test="${result.city eq 1 }">selected="selected"</c:if>>苏州</option>
								<option value="2" <c:if test="${result.city eq 2 }">selected="selected"</c:if>>杭州</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>推送方式:</td>
						<td>
							<select id="type" name="type" class="easyui-combobox"
							data-options="    
						        onSelect: function(rec){
						        	recs(rec);
						        },editable:false" 
							
							
							>
								<option value="0" <c:if test="${result.type eq 0 }">selected="selected"</c:if>>立即</option>
								<option value="1" <c:if test="${result.type eq 1 }">selected="selected"</c:if>>定时</option>
								<option value="2" <c:if test="${result.type eq 2 }">selected="selected"</c:if>>定期</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>投资情况:</td>
						 <td style="text-align:left">
				            <span class="radioSpan">
				                <input type="radio" name="isInvest"  value=""  <c:if test="${result.isInvest eq null }">checked="checked"</c:if> >全部</input>
				                <input type="radio" name="isInvest" value="0" <c:if test="${result.isInvest eq 0 }">checked="checked"</c:if> >未投资</input>
				                <input type="radio" name="isInvest" value="1" <c:if test="${result.isInvest eq 1 }">checked="checked"</c:if> >已投资（新手标，体验标除外）</input>
				            </span>
				        </td>
					</tr>
					<tr>
						<td>账户福利:</td>
						 <td style="text-align:left">
				            <span class="acc">
				                <input type="radio" name="weal"  value="" <c:if test="${result.weal eq null }">checked="checked"</c:if> >全部</input>
				                <input type="radio" name="weal" value="0" <c:if test="${result.weal eq 0 }">checked="checked"</c:if> >无福利</input>
				                <input type="radio" name="weal" value="1" <c:if test="${result.weal eq 1 }">checked="checked"</c:if> >有福利</input>
				            </span>
				        </td>
					</tr>
					<tr class ="fruit">
						<td><input type="checkbox" class ="checkbox"  value="" <c:if test="${checkbox.invest eq 1 }">checked="checked"</c:if>  />投资峰值:</td>
						 <td>
						 	<input id="investMin" name="investMin" value="${result.investMin }" style="width:80px" class="easyui-numberbox" placeholder="请输入金额">-
				          	<input id="investMax" name="investMax" value="${result.investMax }" style="width:80px" class="easyui-numberbox" placeholder="请输入金额">按照日投资峰值进行筛选				          	
				        </td>
					</tr>
					<tr class ="fruit">
						<td><input  type="checkbox" class ="checkbox" value=""  <c:if test="${checkbox.balance eq 1 }">checked="checked"</c:if>/>账号余额:</td>
						 <td>
				          	<input id="balanceMin" name="balanceMin" value="${result.balanceMin }" style="width:80px" class="easyui-numberbox" placeholder="请输入金额">-		          	
				          	<input id="balanceMax" name="balanceMax" value="${result.balanceMax }" style="width:80px" class="easyui-numberbox" placeholder="请输入金额">
				        </td>
					</tr>
					<tr class ="fruit">
						<td><input type="checkbox" class ="checkbox" value="" <c:if test="${checkbox.payment eq 1 }">checked="checked"</c:if> />回款情况:</td>
						 <td>
				          	近<input id="payment" name="payment" value="${result.payment }" style="width:50px" class="easyui-numberbox" >天内有回款				        	          	
				        </td>
					</tr>
					<tr class ="fruit">
						<td><input  type="checkbox" class ="checkbox"  value=""  <c:if test="${checkbox.liveness eq 1 }">checked="checked"</c:if> />活跃情况:</td>
						 <td>
				          	近<input id="liveness" name="liveness" value="${result.liveness }" style="width:150px" class="easyui-numberbox" >天
				          	<select id="livenessType" name="livenessType" class="easyui-combobox">
								<option value="1"  <c:if test="${result.livenessType eq 1 }">checked="checked"</c:if> >打开过app</option>
								<option value="0"  <c:if test="${result.livenessType eq 0 }">checked="checked"</c:if> >未开过app</option>
							</select>	        	          	
				        </td>				       
					</tr>
				
				<tr class="isShow" <c:if test="${result.type == 0 || (result.channel == 1 && result.type == 2  ) }">hidden="true"</c:if>> 
					<td>发送时间:</td>
					<td colspan="3" id="dt">
						<c:if test="${result.type != 0 }">
							<input id="sendStartDate" name="sendStartDate" value="${result.sendStartDate }" class="easyui-datebox" style="width:100px" data-options="required:true"/>-
						</c:if>
						<c:if test="${result.channel == 0 && result.type == 2}">
							<input id="sendEndDate" name="sendEndDate" value="${result.sendEndDate }" class="easyui-datebox" style="width:100px" data-options="required:true"/>
						</c:if>
						<c:if test="${result.type != 0 }">
							<input id="sendTime" name="sendTime"  value="${result.sendTime }" class="easyui-timespinner" data-options="showSeconds:true,required:true" style="width:100px"/>
						</c:if>
					</td>
				</tr>				
			</table>
		</form>
		<br>
			<div id="addappPushBtn"  style="text-align:center">
				<c:if test="${isEdit == 1 }">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateAppPush()">修改</a>
				</c:if>
				<c:if test="${isEdit eq null}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addAppPush()">添加</a>
				</c:if>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">关闭</a>
			</div>
	</div>

<script type="text/javascript">
	
	$(".fruit").on('click',function(){
		if(!$(this).find(".checkbox").prop('checked')){
			$(this).find("td").eq(1).find('input').prop('disabled',true);
		}else{
			$(this).find("td").eq(1).find('input').prop('disabled',false);
		}
	});
	function cancel(){
		$("#updateAppPushWindow").window("close");
	}
	function updateAppPush(){
		var validate = $("#updateAppPushForm").form("validate");
		if(!validate){
			return false;
		}
	
		$.ajax({
          	url: "${apppath}/app/addAppPush.do?isEdit=1",
          	type: 'POST',
            data:$("#updateAppPushForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateAppPushWindow").window("close");
					$("#updateAppPushForm").form("clear");
					$("#appPushList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}	
        });
        return false; // 阻止表单自动提交事件
	}
  function recs (rec){
  			$('#dt').text('');
  			if(rec.value == 0 ){
	    		$('.isShow').hide();
	     		
	     	}else if(rec.value == 1 ){
	    			$('.isShow').show();
	    			$('#dt').append("<input id='sendStartDate' name='sendStartDate' class='easyui-datebox' style='width:100px' data-options='required:true'/>-"+
					"<input id='sendTime' name='sendTime' class='easyui-timespinner' data-options='showSeconds:true,required:true' style='width:100px'/>");
     				$('#sendStartDate').datebox({"required":true});
			 		$('#sendTime').timespinner({"showSeconds":true,"required":true});			        		
	     	}else if(rec.value == 2 ){
	     		if($('#channel').combobox('getValue')==1){
	     			$('.isShow').hide();
	     		
	     		}else{
	     			$('.isShow').show();	
	     			$('#dt').append("<input id='sendStartDate' name='sendStartDate' class='easyui-datebox' style='width:100px' data-options='required:true'/>-"+
					"<input id='sendEndDate' name='sendEndDate' class='easyui-datebox' style='width:100px' data-options='required:true'/>"+
					"<input id='sendTime' name='sendTime' class='easyui-timespinner' data-options='showSeconds:true,required:true' style='width:100px'/>");
     				$('#sendStartDate').datebox({"required":true});
					$('#sendEndDate').datebox({"required":true});
			 		$('#sendTime').timespinner({"showSeconds":true,"required":true});
     			}
	     		
	 		}
  }
  
  function channleRecs(rec){
        	if($('#type').combobox('getValue')==2){
        		$('#dt').text('');
        		if(rec.value==1){
        			$('.isShow').hide();
        		}else{
	       			$('.isShow').show();	
	     			$('#dt').append("<input id='sendStartDate' name='sendStartDate' class='easyui-datebox' style='width:100px' data-options='required:true'/>-"+
					"<input id='sendEndDate' name='sendEndDate' class='easyui-datebox' style='width:100px' data-options='required:true'/>"+
					"<input id='sendTime' name='sendTime' class='easyui-timespinner' data-options='showSeconds:true,required:true' style='width:100px'/>");
					$('#sendStartDate').datebox({"required":true});
					$('#sendEndDate').datebox({"required":true});
			 		$('#sendTime').timespinner({"showSeconds":true,"required":true});
        		}
        	}else if($('#type').combobox('getValue')==0){
       			$('.isShow').hide();
       			$('#dt').text('');
        	}
  }
  
  //添加
	function addAppPush(){
		var validate = $("#updateAppPushForm").form("validate");
		if(!validate){
			$.messager.alert("提示信息","数据未填完整");
			return false;
		}
		if($("#url").textbox('getValue')!="" && $("#url").textbox('getValue').trim()==""){
			$.messager.alert("提示信息","url不能为空格");			
			return false;
		}
	 	$.ajax({
          	url: "${apppath}/app/addAppPush.do",
            type: 'POST',
            data:$("#updateAppPushForm").serialize(),   
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#appPushList").datagrid("reload");
					$("#updateAppPushForm").form("clear");
					$("#updateAppPushWindow").window("close");
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