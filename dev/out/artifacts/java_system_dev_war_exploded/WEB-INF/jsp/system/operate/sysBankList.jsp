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
	<table id="sysBankList" title="银行信息"
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../bank/sysBankList.do',
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#sysBankTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true"></th>
	        <th data-options="field:'bankName'" width="10%">银行名称</th>
	        <th data-options="field:'singleQuotaJYT'" width="8%" styler="styleColor" formatter="formatAmount">单笔限额(金运通)</th>	     
	       	<th data-options="field:'dayQuotaJYT'" width="8%" styler="styleColor" formatter="formatAmount">单日限额(金运通)</th>
	       	<th data-options="field:'singleQuotaSFT'" width="8%" styler="styleColor" formatter="formatAmount">单笔限额(盛付通)</th>	      	
	        <th data-options="field:'dayQuotaSFT'" width="8%" styler="styleColor" formatter="formatAmount">单日限额(盛付通)</th>
	       	<th data-options="field:'isUserJYTWY'" width="5%" formatter="isUser">网银(金运通)</th>
	        <th data-options="field:'isUserJYTRZ'" width="5%" formatter="isUser">认证(金运通)</th>
	        <th data-options="field:'isUserSFTRZ'" width="5%" formatter="isUser">认证(盛付通)</th>	        
			<th data-options="field:'channel'"  width="5%" formatter="channel">正在使用</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="sysBankTools" style="padding:5px;height:750">
	  	<form id="sysBankForm">
	  		银行名称：<input id="searchSysBankName" name="bankName" class="easyui-textbox"  size="15" style="width:100px"/>
	    	<a id="searchSysBankBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetSysBankBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	<div id="querySysBankDialog" class="easyui-dialog" title="银行设置(<span style='color: red;'>谨慎操作</span>)"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#querySysBankBtn'" style="width:400px;height:300px;padding:5px;">
		<form id="updateSysBankForm">
			<input type="hidden" id="sysBankId"  name="id"/>
			<input type="hidden" id="bankName"  name="bankName"/>
			<table align="center">
				<tr>
					<td>
						单笔限额(金运通)：
					</td>
					<td>
						<input id="singleQuotaJYT" name="singleQuotaJYT" class="easyui-numberbox" data-options="required:true,min:0,max:100000000"/>
					</td>
				</tr>
				<tr>
					<td>
						单日限额(金运通)：
					</td>
					<td>		
						<input id="dayQuotaJYT" name="dayQuotaJYT" class="easyui-numberbox" data-options="required:true,min:0,max:100000000"/>
					</td>
				</tr>
				<tr>
					<td>
						单笔限额(盛付通)：
					</td>
					<td>		
						<input id="singleQuotaSFT" name="singleQuotaSFT" class="easyui-numberbox" data-options="required:true,min:0,max:100000000"/>
					</td>
				</tr>
				<tr>
					<td>
						单日限额(盛付通)：
					</td>
					<td>		
						<input id="dayQuotaSFT" name="dayQuotaSFT" class="easyui-numberbox" data-options="required:true,min:0,max:100000000"/>
					</td>
				</tr>
			
				<tr>
					<td>
						网银(金运通)：
					</td>
					<td>
						<select id="isUserJYTWY" name="isUserJYTWY" class="easyui-combobox" style="width:100px">
		  					<option value='0'>维护</option>
		  					<option value='1'>可用</option>
		  					<option value='2'>不支持</option>
	  					</select>
					</td>
				</tr>
				<tr>
					<td>
						认证(金运通)：
					</td>
					<td>		
						<select id="isUserJYTRZ" name="isUserJYTRZ" class="easyui-combobox" style="width:100px">
		  					<option value='0'>维护</option>
		  					<option value='1'>可用</option>
		  					<option value='2'>不支持</option>
	  					</select>
					</td>
				</tr>
				<tr>
					<td>
						认证(盛付通)：
					</td>
					<td>		
						<select id="isUserSFTRZ" name="isUserSFTRZ" class="easyui-combobox" style="width:100px">
		  					<option value='0'>维护</option>
		  					<option value='1'>可用</option>
		  					<option value='2'>不支持</option>
	  					</select>
					</td>
				</tr>
				<tr>
					<td>
						正在使用：
					</td>
					<td>		
						<select id="channel" name="channel" class="easyui-combobox" style="width:100px">
		  					<option value='1'>金运通</option>
		  				<!-- 	<option value='2'>盛付通</option> -->
		  					<option value='3'>融宝</option>
		  					<option value='9'>不支持</option>
	  					</select>
					</td>
				</tr>
			</table>
		</form>
		<div id="querySysBankBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateSysBank()">确认修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeSysBankBtn()">取消</a>
		</div>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetSysBankBtn').click(function(){
		$("#sysBankForm").form("reset");
		$("#sysBankList").datagrid("load", {});
	});
	//查询按钮
	$('#searchSysBankBtn').click(function(){
 		$('#sysBankList').datagrid({
			queryParams: {
				bankName: $('#searchSysBankName').val(),
			}
		}); 
	});
	
	function isUser(val,row,index){ 
		if(val == 0){
			return "维护";
		}
		if(val == 1){
			return "可用";
		}
		if(val == 2){
			return "不支持";
		}
	}
	
	function channel(val,row,index){ 
		if(val == 1){
			return "金运通";
		}
		if(val == 2){
			return "盛付通";
		}
		if(val == 3){
			return "融宝";
		}
		if(val == 9){
			return "不支持";
		}
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){  
		return '<a href="#" class="btn l-btn l-btn-small" onclick="updSysBankBtn('+index+')">修改</a>';
	} 
		
	//打开银行设置dialog
	function updSysBankBtn(index){  
		$('#sysBankList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysBankList').datagrid('getSelected'); 
		var url = "${apppath}/bank/querySysBank.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#sysBankId").val(result.map.sysBank.id);
					$("#bankName").val(result.map.sysBank.bankName);
					$("#singleQuotaJYT").numberbox('setValue',result.map.sysBank.singleQuotaJYT);
					$("#dayQuotaJYT").numberbox('setValue',result.map.sysBank.dayQuotaJYT);
					$("#singleQuotaSFT").numberbox('setValue',result.map.sysBank.singleQuotaSFT);
					$("#dayQuotaSFT").numberbox('setValue',result.map.sysBank.dayQuotaSFT);
					$("#isUserJYTWY").combobox('setValue',result.map.sysBank.isUserJYTWY);
					$("#isUserJYTRZ").combobox('setValue',result.map.sysBank.isUserJYTRZ);
					$("#isUserSFTRZ").combobox('setValue',result.map.sysBank.isUserSFTRZ);
					$("#channel").combobox('setValue',result.map.sysBank.channel);
					$("#querySysBankDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	//修改银行设置
	function updateSysBank(){  
		var validate = $("#updateSysBankForm").form("validate");
		if(!validate){
			return false;
		}
		if($("#channel").combobox('getValue')==3 && $("#bankName").val()=="招商银行"){
			$.messager.alert("提示信息","招商银行不能使用融宝通道(需提供用户银行卡密码)");
			return false;
		}
   		$.ajax({
          	url: "${apppath}/bank/updateSysBank.do",
            type: 'POST',
            data:$("#updateSysBankForm").serialize(),  
            success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#sysBankList").datagrid("reload");
					$("#querySysBankDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
        });
	}
	
	//关闭窗口
	function closeSysBankBtn(){  
		$("#sysBankList").datagrid("reload");
		$("#querySysBankDialog").dialog("close");
	}
</script>
</body>
</html>

