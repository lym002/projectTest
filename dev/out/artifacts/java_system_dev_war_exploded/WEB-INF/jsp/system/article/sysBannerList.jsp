<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
	<table id="sysBannerList" title="广告列表 " 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../banner/sysBannerList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#sysBannerTools',onLoadSuccess:sysBannerBtn">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" width="10%" hidden="true">ID</th>
	        <th data-options="field:'title'" width="10%">广告标题</th>
	       	<th data-options="field:'remark'" width="15%">广告说明</th>	     
	       	<th data-options="field:'codeName'" width="6%" >广告位置</th>	      	
	       	<th data-options="field:'sort'" width="4%">排序等级</th>
			<th data-options="field:'location'" width="30%">跳转地址</th>
			<th data-options="field:'minVersion'" width="10%">最低版本号</th>
			<th data-options="field:'statusName'" width="3%">状态</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="sysBannerTools" style="padding:5px;height:750">
		<form id="sysBannerForm">
	  		广告标题:<input id="searchSysBannerTitle" name="title" class="easyui-textbox"  size="15" style="width:200px"/>
	  		广告状态: <select  id="searchSysBannerStatus" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach var="map" items="${bannerstatus}">
						<option value='${map.key}'>${map.value}</option>
			        </c:forEach>
	           		</select>
			广告位置: <select  id="searchSysBannerCode" name="code" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach var="map" items="${bannercode}">
						<option value='${map.key}'>${map.value}</option>
			        </c:forEach>
	           		</select>
	    	<a id="searchSysBannerBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetSysBannerBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSysBannerWindow()">新增广告</a>
	    </form>
	</div>
	<div id="addSysBannerWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:500px;height:560px;padding:10px;">
	</div>
	<div id="updateSysBannerWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false" style="width:500px;height:560px;padding:10px;">
	</div>
<script type="text/javascript">
	function sysBannerBtn (){
			$('.sysBannerBtn').linkbutton();
	}
	//重置按钮
	$('#resetSysBannerBtn').click(function(){
		$("#sysBannerForm").form("reset");
		$("#sysBannerList").datagrid("load", {});
	});
	//查询按钮
	$('#searchSysBannerBtn').click(function(){
 		$('#sysBannerList').datagrid({
			queryParams: {
				title: $('#searchSysBannerTitle').val(),
				status: $('#searchSysBannerStatus').combobox("getValue"),
				code: $('#searchSysBannerCode').combobox("getValue"),
			
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper="";
		if(row.status==1){
			articleOper += '<a href="#" class="sysBannerBtn" onclick="updateSysBannerWindow('+index+')">修改</a>'+' ';
			articleOper += '<a href="#" class="sysBannerBtn" onclick="deleteSysBanner('+index+')">删除</a>'+' ';
		}
		if(row.status==0){
			articleOper += '<a href="#" class="sysBannerBtn" onclick="recoverSysBanner('+index+')">恢复</a>'+' ';
		}
		return articleOper;
	} 	
	//跳转广告添加页面
	function addSysBannerWindow(){  
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "添加广告",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/banner/toSysBannerAdd.do' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	}
	//跳转广告修改页面
	function updateSysBannerWindow(index){  
		$('#sysBannerList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysBannerList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "修改广告",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/banner/toSysBannerUpdate.do?id="+row.id+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	}
	
	function deleteSysBanner(index) {
		$('#sysBannerList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysBannerList').datagrid('getSelected'); 
        $.messager.confirm('操作提示', '确定要删除吗?', function(r){
        	if(r){
	            $.ajax({
	            	url: "${apppath}/banner/deleteSysBanner.do?id="+row.id,
	                type: 'POST',
	               success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#sysBannerList").datagrid("reload");
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
	            });
            }
        });
 	}
 	
 	function recoverSysBanner(index) {
 		$('#sysBannerList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#sysBannerList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/banner/recoverSysBanner.do?id="+row.id,
	        type: 'POST',
	        success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#sysBannerList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
       });
 	}
	
</script>
</body>
</html>

