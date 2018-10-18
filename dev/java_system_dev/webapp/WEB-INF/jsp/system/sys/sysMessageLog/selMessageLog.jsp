<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../../common/include/util.jsp" %>

<style type="text/css">
.onediv{width:350px; height:300px;float:left;margin:0 0 0 5px;  }
.twodiv{width:200px; height:300px;float:left;margin:0 0 0 5px;  }
</style>

</head>
<body>
	<table id="logList" title="注册码管理" class="easyui-datagrid" style="width:99.9%;height:99%;"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../sysmessagelog/logList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#logTools'">
		<thead>
	    <tr>
			<th data-options="field:'phone'" width="10%">手机号</th>
	        <th data-options="field:'message'" width="50%">验证码</th>
	        <th data-options="field:'typeName'" width="20%">类型描述</th>
	        <th data-options="field:'sendTime'" width="20%" formatter="formatDateBoxFull">发送时间</th>
	    </tr>
	    </thead>
	</table>
	<div id="logTools" style="padding:5px;height:750">
	  	<form id="logForm">
	    	手机号：<input class="easyui-textbox" id="phone" name="phone" size="15" style="width:100px"/>
	    	类型描述: <select  id="type" name="type" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach var="map" items="${status}">
						<option value='${map.key}'>${map.value}</option>
			        </c:forEach>
	           </select>
	    	发送时间：<input class="easyui-datebox" id="sendTime" name="sendTime" style="width:100px"/>
	    	<a id="searchRoleBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetRoleBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
<script type="text/javascript">
	
	//重置按钮
	$('#resetRoleBtn').click(function(){
		$("#logForm").form("reset");
		$("#logList").datagrid("load", {});
	});
	//查询按钮
	$('#searchRoleBtn').click(function(){
 		$('#logList').datagrid({
			queryParams: {
				sendTime:$('#sendTime').datebox('getValue'),
				type:$('#type').combobox('getValue'),
				phone: $('#phone').val()
				
			}
		}); 
	});
	
//重置按钮
	$('#resetRoleBtn').click(function(){
		$("#logForm").form("reset");
		$("#logList").datagrid("load", {});
	});



</script>
</body>
</html>

