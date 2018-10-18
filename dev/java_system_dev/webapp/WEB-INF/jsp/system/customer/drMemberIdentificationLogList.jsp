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
	<table id="drMemberIdentificationLogList" title="银行认证日志" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:false, url: '../fourElements/drMemberIdentificationLogList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drMemberFourElementsLogTools'">
		<thead >
	   <tr >
	    	<th data-options="field:'uid'" hidden="true" width="120"></th>
	       	<th data-options="field:'realName'" width="10%">真实姓名</th>
	        <th data-options="field:'idCards'" width="10%" >身份证号</th>
	        <th data-options="field:'bankNum'" width="10%" >银行卡号</th>
	        <th data-options="field:'bankName'" width="10%" >银行名称</th>
	        <th data-options="field:'mobilePhone'" width="10%" >预留手机</th>	 
	        <th data-options="field:'reason'" width="10%" >认证结果</th>
	        <th data-options="field:'sendTime'" width="10%" formatter="iFormatDateBoxFull">认证时间</th>       
	    </tr>
	    </thead>
	</table>
	<div id="drMemberFourElementsLogTools" style="padding:5px;">
	  	<form id="drMemberFourElementsLogForm">
	                    客户手机号:<input id="drMemberFourElementsLogMobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:100px" required="required"/>   		
	    	<a id="searchDrMemberFourElementsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetDrMemberFourElementsLogBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="#" class="easyui-linkbutton"  iconCls="icon-ok" onclick="updateDrMemberFourElementsLogAgainBtn();">重新认证</a>
	    </form>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetDrMemberFourElementsLogBtn').click(function(){
		$("#drMemberFourElementsLogForm").form("reset");
		$("#drMemberIdentificationLogList").datagrid("load", {});
	});
	//查询按钮
	$('#searchDrMemberFourElementsLogBtn').click(function(){
 		$('#drMemberIdentificationLogList').datagrid({
			queryParams: {
				mobilePhone: $('#drMemberFourElementsLogMobilePhone').val(),
			}
		}); 
	});

		//分配修改
 	function updateDrMemberFourElementsLogAgainBtn(index){
 		$.messager.confirm("重新认证", "是否允许此手机进行重新认证？", function(ensure){
			if(ensure){
				var url = "${apppath}/fourElements/updateDrMemberIdentificationLogAgain.do?mobilePhone="+$("#drMemberFourElementsLogMobilePhone").val();
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#drMemberIdentificationLogList").datagrid("reload");
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
		
 	//修改时间的显示样式，只显示到年月日
	function iFormatDateBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
</script>
</body>
</html>

