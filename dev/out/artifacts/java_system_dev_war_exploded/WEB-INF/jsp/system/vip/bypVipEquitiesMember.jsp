<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="drProductPrizeList" title="vip权益领取记录" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../vipEquitiesMember/vipEquitiesMemberList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">ID</th>
	        <th data-options="field:'mobilePhone'" width="10%" >手机号</th>
	        <th data-options="field:'vipName'" width="5%"  >会员等级</th>
	        <th data-options="field:'addTime'"  width="20%" formatter="formatDateBoxFull">领取时间</th>
	        <th data-options="field:'equitiesName'" width="10%" >权益记录</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	  	   手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-textbox"  size="15" style="width:200px"/>
	  	   获取时间:<input id="startAddTime" name="startAddTime" class="easyui-datebox" style="width:100px" />
	  	 至:<input id="endAddTime" name="endAddTime" class="easyui-datebox" style="width:100px"/>
	     	会员等级:<select id="vipLevel" name="vipLevel" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<c:forEach var="map" items="${level}">
								<option value='${map.vipLevel}'>${map.vipName}</option>
					        </c:forEach>
	  				</select>
			权益:<select id="vipEquitiesId" name="vipEquitiesId" class="easyui-combobox" style="width:100px">
  					<option value=''>全部</option>
  					<c:forEach var="map" items="${qy}">
						<option value='${map.id}'>${map.equitiesName}</option>
			        </c:forEach>
 				</select>
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    </form>
	</div>
	
	
<script type="text/javascript">

	//重置按钮
	$('#resetJsProductPrize').click(function(){
		$("#drProductPrizeForm").form("reset");
		$("#drProductPrizeList").datagrid("load", {});
	});
	//查询按钮
	function searchJsProductPrize(){
 		$('#drProductPrizeList').datagrid({
 			queryParams: {
	 			mobilePhone:$('#mobilePhone').val(),
	 			startAddTime:$('#startAddTime').datebox('getValue'),
	 			endAddTime:$('#endAddTime').datebox('getValue'),
	 			vipLevel:$('#vipLevel').combobox('getValue'),
	 			vipEquitiesId:$('#vipEquitiesId').combobox('getValue'),
 			}
		});
	};

	
	$(document).ready(function () {
/* 		$('#drProductPrizeList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../prizemanage/prizeLogCount.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeLogForm").serialize(), 
					success:function(data){
						$('#productPrizeCount').text(data.count);
					}
				});
			} 
    	}); */
	});
</script>
</body>
</html>

