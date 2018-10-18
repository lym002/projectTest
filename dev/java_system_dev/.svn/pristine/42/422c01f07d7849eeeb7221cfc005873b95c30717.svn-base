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
    <div style="width:auto;height:420px;padding:1px;">
    	<form id="updateSettingsForm">
    	<input id="updateDetailList" name="details" type="hidden">
    	<input id="updateSettingsId" name="id" value=${settings.id} type="hidden">
    		<div style="width:auto;height:110px">
	    		<table>
	    			<tr>
	    				<td style="width:80px">奖励标准:</td>
	    				<td>
	    					<select id="updateNorm" name="norm" style="width: 150px" class="easyui-combobox" data-options="onSelect:function(rec){
                                        var peo = $('#updateNorm').combobox('getValue');
                                        if(peo==1){
                                                $('#updatePeriod').numberbox({
                                                        disabled:Rtrue
                                                });
                                        }else{
                                                $('#updatePeriod').numberbox({
                                                        disabled:false
                                                });
                                        }
                                }">
								<c:forEach items="${norm}" var="map">
									<option value='${map.key }' <c:if test="${settings.norm== map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:forEach>
							</select>
	    				</td>
	    				<td style="width:80px">累计周期:</td>
	    				<td >
	    					<input id="updatePeriod" name="period" class="easyui-numberbox" style="width: 130px" value='${settings.period}' <c:if test="${settings.norm== 1}">disabled="disabled"</c:if>/>天
	    				</td>
	    			</tr>
	    	
	    			<tr>
	    				<td>奖励形式 :</td>
	    				<td>
	    					<select id="updateModality" name="modality" style="width: 150px" class="easyui-combobox" data-options="onSelect:function(rec){
                                        var peo = $('#updateModality').combobox('getValue');
                                        if(peo==1){
                                                $('#updateStandard').combobox({
                                                        disabled:true
                                                });
                                                 $('#updateStandard').combobox('clear');
                                        }else{
                                                $('#updateStandard').combobox({
                                                        disabled:false
                                                });
                                                $('#updateStandard').combobox('clear');
                                        }
                                }">
								<c:forEach items="${modality}" var="map">
									<option value='${map.key }' <c:if test="${settings.modality== map.key}"> selected="selected" </c:if>>${map.value }</option>
								</c:forEach>
							</select>
	    				</td>
	    				<td>奖励基准 :</td>
	    				<td>
	    					<select id="updateStandard" name="standard" style="width: 150px" class="easyui-combobox" <c:if test="${settings.modality==1}">disabled="disabled"</c:if>>
								<c:forEach items="${standard}" var="map">
									<option value="${map.key}" <c:if test="${settings.standard== map.key}"> selected="selected" </c:if>>${map.value}</option>
								</c:forEach>
							</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>适用产品</td>
	    				<td colspan="3">
	    					<c:forEach items="${productType}" var="map">
	    						<input value="${map.key}" name="products" type="checkbox" <c:if test="${settings.products.contains(map.key)}">checked="checked"</c:if>/>${map.value}
	    					</c:forEach>
	    				<td>
	    			</tr>
	    			<tr>
	    				<td>开始时间:</td>
	    				<td>
	    					<input id="updateStartTime" name="startTime" style="width: 150px" class="easyui-datebox" value="<fmt:formatDate value="${settings.startTime}" pattern="yyyy-MM-dd" />"/>
	    				</td>
	    				<td>结束时间:</td>
	    				<td>
	    					<input id="updateEndTime" name="endTime" style="width: 150px" class="easyui-datebox" value="<fmt:formatDate value="${settings.endTime}" pattern="yyyy-MM-dd" />">
	    				</td>
	    			</tr>
	    			<tr hidden="hidden">
	    				<td>
		    			</td>
	                   	<td>

                   		</td>
                   		<td></td>
                   		<td></td>
	    			</tr>
	    		</table>
    		</div>
    		<div style="width:auto;height:250px">
    		    <table id="detailListdg" title="好友推荐设置详情" class="easyui-datagrid"
					style="height:99%;width:99%"
					data-options="singleSelect:true,
				    fitColumns:true, url: '../recommendedSettings/settingsDetailsList.do?rid=${settings.id}',
				    method:'post',rownumbers:true,pagination:false,toolbar:'#tb',onClickRow: onClickRow">
					<thead>
						<tr>
							<th data-options="field:'id'" hidden="hidden">ID</th>
							<th data-options="field:'rid'" hidden="hidden">rid</th>
							<th data-options="field:'amount',editor:'numberbox',required:true" formatter="formatAmount" width="45%" align="center">金额</th>
							<th data-options="field:'reward',editor:{type:'numberbox',options:{precision:2}},required:true" width="45%" formatter="formatAmount" align="center">奖励</th>
						</tr>
					</thead>
				</table>   
				<div id="tb" style="height:auto">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">暂存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
				</div>
    		</div>
    	</form>
    	<div style="text-align:center;padding:10px;">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitSettingsUpdate()">确认修改</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeSettingsWindow()">取消</a>
	    </div>
    </div>
    <script type="text/javascript">
    	function closeSettingsWindow(){
    		$("#showDetailsWindow").window("close");
    	}
    	
    	function submitSettingsUpdate(){
    		if(!period()){
    			$.messager.alert("提示信息","累计投资额，累计周期必填");
    			return false;
    		}
    		if(!products()){
    			$.messager.alert("提示信息","请选择适用产品");
				return false;
    		}
    		var inserted = $('#detailListdg').datagrid('getRows');
    		$("#updateDetailList").val(JSON.stringify(inserted));
    		$.ajax({
			url:"${apppath}/recommendedSettings/modifySettings.do",
			type:"POST",
			data:$("#updateSettingsForm").serialize(), 
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#showDetailsWindow").window("close");
					$("#updateSettingsForm").form("clear");
					$("#recommendedSettingsList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
    	}
    	
    	function products(){
			var checkboxes = document.getElementsByName("products");
			for(var i=0;i<checkboxes.length;i++){
				if(checkboxes[i].checked){
	     			return true;
	     		}
			}
			return false;
		}
		
		function period(){
			if($('#updateNorm').combobox('getValue')==2){
				var period = $('#updatePeriod').val();
				if(null ==period || period=="undefined" || period<=0)
					return false;
			}
			return true;
		}
    	
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){
				return true;
			}
			if ($('#detailListdg').datagrid('validateRow', editIndex)){
				$('#detailListdg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#detailListdg').datagrid('selectRow', index).datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#detailListdg').datagrid('selectRow', editIndex);
				}
			}
		}
		 function append(){
			if (endEditing()){
				editIndex = $('#detailListdg').datagrid('getRows').length;
				$('#detailListdg').datagrid('appendRow',{
					index:editIndex-1,
					row:{}
				});
				$('#detailListdg').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			}
		} 

		function removeit(){
			if (editIndex == undefined){return}
			$('#detailListdg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		function accept(){
			if (endEditing()){
				$('#detailListdg').datagrid('acceptChanges');
			}
		}
		function reject(){
			$('#detailListdg').datagrid('rejectChanges');
			editIndex = undefined;
		}
	</script>
</body>
</html>
