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
	<table id="drMemberFourElementsLogList" title="银行四要素认证 " 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:false, url: '../fourElements/drMemberFourElementsLogList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drMemberFourElementsLogTools'">
		<thead >
	    <tr >
	    	<th data-options="field:'uid'" hidden="true" width="120"></th>
	    	<th data-options="field:'isAuth'" hidden="true" width="120"></th>
	       	<th data-options="field:'phone'" width="10%">客户手机号</th>
	       	<th data-options="field:'count'" width="10%">认证次数</th>
	       	<th data-options="field:'acctName'" width="10%">真实姓名</th>
	        <th data-options="field:'idCards'" width="10%" >身份证号</th>
	        <th data-options="field:'bankNum'" width="10%" >银行卡号</th>
	        <th data-options="field:'mobilePhone'" width="10%" >预留手机</th>	        
	       	<th data-options="field:'_operate',align:'center',formatter:formatOper" width="10%">操作</th>	     
	    </tr>
	    </thead>
	</table>
	<div id="drMemberFourElementsLogTools" style="padding:5px;">
	  	<form id="drMemberFourElementsLogForm">
	                    客户手机号:<input id="drMemberFourElementsLogMobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:100px"/>   		
	    	<a id="searchDrMemberFourElementsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrMemberFourElementsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	<div id="updateDrMemberFourElementsLogWindow" class="easyui-dialog" title="开始认证" 
		data-options="closed:true,modal:true,width:350,height:260,buttons:'#AuthenticationButtons'" style="padding:10px;">
		<form class="easyui-form" id="updateDrMemberFourElementsLogForm" >
			<input type="hidden" name="uid">
			<table>
				<tr>
					<td>银行名称：
						<select name="bankName" style="width: 240px" class="easyui-combobox" editable="false" data-options="required:true">
							<c:forEach items="${sysBank}" var="map">
								<option value='${map.bankName }'>${map.bankName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						银行卡号：
						<input type="text" name="bankNum" size="30" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						预留手机：
						<input type="text" name="mobilePhone" size="30" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						真实姓名：
						<input type="text" name="realName" size="30" class="easyui-textbox" data-options="required:true,validType:'chinese'" />
					</td>
				</tr>
				<tr>
					<td>
						身份证号：
						<input type="text" name="idCards" size="30" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
			</table>	
		</form>
	</div>
	<div id="AuthenticationButtons">
		<a href="#" class="easyui-linkbutton"  iconCls="icon-ok" onclick="updateDrMemberFourElementsLog();"  >开始认证</a>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrMemberFourElementsLogBtn').click(function(){
		$("#drMemberFourElementsLogForm").form("reset");
		$("#drMemberFourElementsLogList").datagrid("load", {});
	});
	//查询按钮
	$('#searchDrMemberFourElementsLogBtn').click(function(){
 		$('#drMemberFourElementsLogList').datagrid({
			queryParams: {
				mobilePhone: $('#drMemberFourElementsLogMobilePhone').val(),
			}
		}); 
	});

	//添加基本操作链接
	function formatOper(val,row,index){
		if(row.count >2 && row.isAuth == 0){
		    return '<a href="#" class="btn l-btn l-btn-small" onclick="updateDrMemberFourElementsLogBtn('+index+')">开始认证</a>' +' '+ 
    		'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrMemberFourElementsLogAgainBtn('+index+')">重新认证</a>';
		}else{
			return '<a href="#" class="btn l-btn l-btn-small" onclick="updateDrMemberFourElementsLogBtn('+index+')">开始认证</a>';
		}
	} 
	
	//跳转分配页面
 	function updateDrMemberFourElementsLogBtn(index){
		$('#drMemberFourElementsLogList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drMemberFourElementsLogList').datagrid('getSelected'); 
	    $("#updateDrMemberFourElementsLogForm").form("load",row);
		$('#updateDrMemberFourElementsLogWindow').dialog('open');  
	} 
	
	//分配修改
 	function updateDrMemberFourElementsLog(index){
		var validate = $("#updateDrMemberFourElementsLogForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
			url:"${apppath}/fourElements/updateDrMemberFourElementsLog.do",
			type:"POST",
			data:$("#updateDrMemberFourElementsLogForm").serialize(),  
			success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#updateDrMemberFourElementsLogWindow").window("close");
					$("#updateDrMemberFourElementsLogForm").form("clear");
					$("#drMemberFourElementsLogList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
		});
	} 
	
		//分配修改
 	function updateDrMemberFourElementsLogAgainBtn(index){
 		$('#drMemberFourElementsLogList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drMemberFourElementsLogList').datagrid('getSelected'); 
 		$.messager.confirm("重新认证", "是否允许手机号"+row.phone+"进行重新认证？", function(ensure){
			if(ensure){
				var url = "${apppath}/fourElements/updateDrMemberFourElementsLogAgain.do?uid="+row.uid;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#drMemberFourElementsLogList").datagrid("reload");
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

