<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<link rel="stylesheet" type="text/css" href="${apppath }/js/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${apppath }/js/easyUI/themes/icon.css">
<script type="text/javascript" src="${apppath }/js/easyUI/jquery.min.js"></script>
<script type="text/javascript" src="${apppath }/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${apppath }/js/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${apppath }/js/easyUI/validatebox.js"></script>
</head>
<body>
	<table id="articleList" title="活动列表 " 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '${apppath }/activity/getAllActivity.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#articleTools',onLoadSuccess:articleBtn">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" width="10%" hidden="true">ID</th>
	    	<th data-options="field:'title'" width="10%">活动标题</th>
	        <th data-options="field:'pcUrl'" width="20%">pc活动链接</th>
	        <th data-options="field:'appUrl'" width="20%">app活动链接</th>
	        <th data-options="field:'activityDate'" width="10%">活动时间</th>
	       	<th data-options="field:'isTop'" width="3%" formatter="formatIsTop">置顶</th>
			<th data-options="field:'status'" width="4%" formatter="formatStatus">状态</th>
			<th data-options="field:'terminalTypePC'" width="3%" formatter="terminalType">PC</th>
			<th data-options="field:'terminalTypeH5'" width="3%" formatter="terminalType">H5</th>
			<th data-options="field:'terminalTypeIOS'" width="3%" formatter="terminalType">IOS</th>
			<th data-options="field:'terminalTypeAndroid'" width="3%" formatter="terminalType">安卓</th>
			<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="articleTools" style="padding:5px;height:750">
	  	<form id="articleForm">
	  		活动标题:<input id="searchActivityTitle" name="title" class="easyui-textbox"  size="15" style="width:200px"/>
	  		状态:<select id="searchStatus" name="status" style="width:150px" class="easyui-combobox">
					<option value='0' selected="selected">全部</option>
					<option value='1'>进行中</option>
					<option value='2'>已结束</option>
					<option value='3'>活动暂定</option>
					
			</select>
	    	<a id="searchArticleBtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id=resetArticleBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addArticleBtn()">新增活动</a>
	    </form>
	</div>
<script type="text/javascript">
	function articleBtn (){
			$('.articleBtn').linkbutton();
	}
	//时间格式
	function formatDate(value,row,index){ 
		if(value == null){
			return "";
		}
		var unixTimestamp = new Date(value);  
		return unixTimestamp.toLocaleString();  
	} 
	//重置按钮
	$('#resetArticleBtn').click(function(){
		$("#articleForm").form("reset");
		$("#articleList").datagrid("load", {});
	});
	//查询按钮
	$('#searchArticleBtn').click(function(){
 		$('#articleList').datagrid({
			queryParams: {
				title: $('#searchActivityTitle').val(),
				status:$('#searchStatus').combobox('getValue')
			}
		}); 
	});
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="articleBtn" onclick="toArticleUpdate('+index+')">修改</a>'+' ';
		/* if(row.status == 1){
			articleOper +='<a href="#" class="articleBtn" onclick="toArticleUpdate('+index+')">修改</a>'+' ';
			articleOper +='<a href="#" class="articleBtn" onclick="deleteArticle('+index+')">删除</a>'+' ';
		}
		if(row.status == 0){
			articleOper +='<a href="#" class="articleBtn" onclick="toArticleUpdate('+index+')">修改</a>'+' ';
			articleOper +='<a href="#" class="articleBtn" onclick="refuseAtricle('+index+')">拒绝</a>'+' ';
			articleOper +='<a href="#" class="articleBtn" onclick="recoverAtricle('+index+')">审核</a>'+' ';
		}
		if(row.status == 2){
			articleOper +='<a href="#" class="articleBtn" onclick="toArticleUpdate('+index+')">修改</a>'+' ';
			articleOper +='<a href="#" class="articleBtn" onclick="recoverAtricle('+index+')">恢复</a>'+' ';
		}
		if(row.isrecommend == 0 && row.status == 1){
			articleOper +='<a href="#" class="articleBtn" onclick="isrecommendArticle('+index+')">推荐</a>'+' ';
		} */
		return articleOper;
	} 	
	
	//跳转文章修改页面
 	function toArticleUpdate(index){
		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "修改活动聚合页",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activity/toUpdateAggregationPage.do?id="+row.id+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	} 
	
	//跳转文章显示页面
 	function showArticle(index){
		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "查看文章",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/article/showArticle.do?artiId="+row.artiId+"' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	} 
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="1"){
			return '进行中';
		}else if(row.status=="2"){
			return '已结束';
		}else if(row.status=="3"){
			return '活动暂定';
		}
	}
	
	//修改显示是否头条信息
	function formatIsTop(value,row,index){
		if(row.isTop=="0"){
			return '否';
		}else if(row.isTop=="1"){
			return '是';
		}
	}
	
	function terminalType(value){
		if(value == "0"){
			return "不显示";
		}else if(value == "1"){
			return "显示";
		}
	}
	
	//跳转文章添加页面
	function addArticleBtn(){  
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "添加活动聚合页",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/activity/toAddAggregationPage.do' ></iframe>",
				closable : true
		};
		var tab = mainTab.tabs("getSelected");
		var index = mainTab.tabs('getTabIndex',tab);
		/* if(index != 0){
			mainTab.tabs('close',index);
		} */
		mainTab.tabs("add",detailTab);
	}
	
	function deleteArticle(index) {
		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
        $.messager.confirm('操作提示', '确定要删除吗?', function(r){
        	if(r){
	            $.ajax({
	            	url: "${apppath}/article/deleteArticle.do?artiId="+row.artiId,
	                type: 'POST',
	               success:function(result){
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							$("#articleList").datagrid("reload");
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
	            });
            }
        });
 	}
 	
 	function isrecommendArticle(index) {
 		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/article/isrecommendArticle.do?artiId="+row.artiId,
	        type: 'POST',
	        success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#articleList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
       });
 	}
 	
 	function recoverAtricle(index) {
 		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/article/recoverAtricle.do?artiId="+row.artiId,
	        type: 'POST',
	        success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#articleList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
       });
 	}
 	
 	function refuseAtricle(index) {
 		$('#articleList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#articleList').datagrid('getSelected'); 
		$.ajax({
	       	url: "${apppath}/article/refuseAtricle.do?artiId="+row.artiId,
	        type: 'POST',
	        success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#articleList").datagrid("reload");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
       });
 	}
	
</script>
</body>
</html>

