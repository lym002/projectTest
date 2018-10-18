
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
	<table id="drProductPrizeList" title="礼品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../productPrize/jsProductPrizeList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#jsProductPrizeTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">商品编号</th>
	    	<th data-options="field:'name'" width="10%">商品名称</th>
	        <th data-options="field:'simpleName'" width="10%">商品简称</th>
	       	<th data-options="field:'price'" width="5%" styler="styleColor" formatter="formatAmount">商品原价</th>
	        <th data-options="field:'amount'" width="6%" styler="styleColor" formatter="formatAmount">投资金额(元)</th>
	        <th data-options="field:'status'" formatter="formaterProductPrizeStatus" width="4%">状态</th>
	        <th data-options="field:'type'" formatter="formaterProductPrizeType" width="4%">分类</th>
	        <th data-options="field:'category'" formatter="formaterProductPrizeCategory" width="4%">类别</th>
	    <!--    	<th data-options="field:'code'" width="11%" >标的编号</th> -->
	      <!--  	<th data-options="field:'productName'" width="8%" >标的名字</th>
	       	<th data-options="field:'productStatus'" width="4%" formatter="formaterProductInfoStatus">标的状态</th> -->
	       	<th data-options="field:'sort'" width="3%" >权重</th>
	       	<th data-options="field:'addTime'" formatter="iFormatDateTimeBoxFull" width="10%" >创建时间</th>
	       	<th data-options="field:'remark'" width="10%" >备注</th>
	        <th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="jsProductPrizeTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	  		商品名称:<input id="searchName" name="name" class="easyui-textbox"  size="15" style="width:200px"/>
	                   状态:<select id="searchStatus" name="status" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<!-- <option value='0'>未上架</option> -->
		  					<option value='1'>已上架</option>
		  					<option value='2'>已下架</option>
	  				</select>
	  				类别：<select id="searchCategory" name="category" class="easyui-combobox" style="width:100px">
		  					<option value='0'>全部</option>
		  					<option value='1'>夏日专享</option>
		  					<option value='2'>品质生活</option>
		  					<option value='3'>数码优选</option>
	  				</select>
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoBtn()">新增</a>
	    </form>
	</div>
	
	<!-- 预约列表 -->
	<div id="prizeLogList" class="easyui-dialog" style="height:50%;width:30%" closed="true"	buttons="addBtn"  data-options="resizable:true,modal:true,closed:true">
	
		<table id="drProductPrizeLogList" class="easyui-datagrid" style="height:99%;width:99.9%">
		</table>
		<div id="jsProductPrizeLogTools" style="padding:5px;height:750">
			<form id="drProductPrizeLogForm" target="_blank" method="post">
						<input type="hidden" id="ppid" name="ppid"/>
				录入日期:<input id="addTimeStart" name="addTimeStart" class="easyui-datebox" style="width:100px"/>到
	  				<input id="addTimeEnd" name="addTimeEnd" class="easyui-datebox" style="width:100px"/>
					<a id="searchDrProductPrizeLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a> 
					<a id="resetDrProductPrizeLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a> 
					<a id="exDrProductPrizeLog" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
			<span style='color: #0015FF;'>共</span><span id='productPrizeCount' style='color: red;'></span><span style='color: #0015FF;'>笔</span>
			</form>
		</div>
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
				name:$('#searchName').val(),
				status:$('#searchStatus').combobox('getValue'),
				category:$('#searchCategory').combobox('getValue')	
			}
		});
	};
	
	//重置按钮
	$('#resetDrProductPrizeLog').click(function(){
		$("#drProductPrizeLogForm").form("reset");
		$("#drProductPrizeLogList").datagrid("load", {});
	});
	//查询按钮
	$('#searchDrProductPrizeLog').click(function(){
 		$('#drProductPrizeLogList').datagrid({
			queryParams: {
				addTimeStart:$('#addTimeStart').datebox('getValue'),
				addTimeEnd:$('#addTimeEnd').datebox('getValue')
			}
		});
	});
	
	
	
	//导出预约表表
	$('#exDrProductPrizeLog').click(function(){
		var ppid = $("#ppid").val();
		var url= "../productPrizeLog/exportJsProductPrizeLog.do?ppid="+ppid; 
		$('#drProductPrizeLogForm').form('submit',{
			url:url,
			success:function(data){
				var d = $.parseJSON(data);
				if(!d.success){
					alert(d.msg);
				}
			}
		});
	});
	
	//操作链接
	function formatOper(val,row,index){  
				if(row.status==1){
					return	'<a href="#" class="btn l-btn l-btn-small" onclick="openPrizeLog('+index+')">查看预约</a>'+"   "+
							'<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">下架</a>'+"   ";
				}else{
					return	'<a href="#" class="btn l-btn l-btn-small" onclick="openPrizeLog('+index+')">查看预约</a>';
				}
				
				
						
					/* '<a href="#" class="btn l-btn l-btn-small" onclick="update('+index+')">编辑</a>'+"   "+
	                    '<a href="#" class="btn l-btn l-btn-small" onclick="openPrizeLog('+index+')">查看预约</a>'+"   "+
	                    '<a href="#" class="btn l-btn l-btn-small" onclick="copyPrizeLog('+index+')">复制并新建</a>'+"   "; */
    }
    
    	//跳转到产品添加页面
	function addDrProductInfoBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "商品新增",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/productPrize/toAddDrProductPrize.do'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
/* 	//跳转到产品修改页面
	function update(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
					var mainTab = parent.$('#main-center');
					var detailTab = {
							title : "商品修改",
							content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/productPrize/toUpdateDrProductPrize.do?id="+row.id+"'></iframe>",
							closable : true
					};
					mainTab.tabs("add",detailTab);
	} */
	
	//下架
	function update(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
	    $.ajax({
			url:"../productPrize/getProductPrize.do",
			type:'post',
			data:{
				id:row.id
			},
			success:function(data){
				if(data.success){//
					 $.messager.confirm('操作提示', '确定下架该商品吗？', function (data) {		
						 	if(data){//确定
						 		 $.ajax({
						 			url:"../productPrize/deleteProductPrize.do",
						 			type:'post',
						 			data:{
						 				id:row.id
						 			},
						 			success:function(data){
						 				if(data.success){//
						 					$.messager.alert("提示信息","操作成功");
						 					searchJsProductPrize();
						 				}else{
						 					$.messager.alert("提示信息","操作失败");
						 					return false;
						 				}
						 			}
						 		});
						 	}
					 })
				}else{
					$.messager.alert("提示信息","当前商品已关联已审核或募集中的产品，不可下架");
					return false;
				}
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
	
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd hh:mm:ss");  
	} 
	//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function openPrizeLog(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
		var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$('#ppid').val(row.id);
			$('#prizeLogList').dialog('open').dialog('setTitle', '预约列表');
			var drProductPrizeLogList = $('#drProductPrizeLogList');
			drProductPrizeLogList.datagrid({
				url : "../productPrizeLog/jsProductPrizeLogList.do?ppid="+row.id,
				fitColumns : true,
				pagination : true,
				checkbox:true,
				pageSize:10,
				pageList:[10,20,25,50],
				autoRowHeight : true,
				toolbar:"#jsProductPrizeLogTools",
				fit:true,
				columns : [ [ 
					{
					field : 'realname',
					title : '用户姓名',
					width : '25%',
					align : "center"
					},{
					field : 'mobilePhone',
					title : '手机号',
					width : '25%',
					align : "center"
					},{
					field : 'recommCodes',
					title : '推荐码',
					width : '15%',
					align : "center"
					},{
					field : 'addTime',
					title : '预约时间',
					formatter:iFormatDateTimeBoxFull,
					width : '35%',
					align : "center"	
					} ]]
			});
			
		}
		//复制并新增
		function copyPrizeLog(index){
			$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
		    var row = $('#drProductPrizeList').datagrid('getSelected'); 
			$.messager.confirm('操作提示', '复制并新建商品：'+row.name, function (data) {		
			 	if(data){
			 		$.ajax({
						type: 'post',
						url : "../productPrize/copyAddPrizeLog.do",
						cache : false,
						data : {
							id : row.id
						},
						cache : false,
						async : false,
						success : function(message) {
							var resultJson = jQuery.parseJSON(message);
							var resultJson = eval(resultJson);
							if(resultJson.success){
								$.messager.alert("提示信息","复制并新增成功！");
								$("#drProductPrizeList").datagrid("reload");
							}else{
								$.messager.alert("提示信息","复制并新增失败！");
							}
					    },  
					    error : function(message) {
						 $.messager.alert('操作提示','操作失败');
						 return false; 
					}
					 });
			 	}
			 })
		}
		
		//奖品状态	
	function formaterProductPrizeStatus(value,row,index){
		if(row.status == 0){
			return '未上架';
		}else if(row.status == 1){
			return '已上架';
		}else if( row.status == 2 ){
			return '已下架';
		}
	}
		//奖品状态	
	function formaterProductPrizeType(value,row,index){
		if(row.type == 0){
			return '非实物商品';
		}else if(row.type == 1){
			return '实物商品';
		}
	}
		
	function formaterProductPrizeCategory(value,row,index){
		if(value == 0){
			return '请选择';
		}
		if(value == 1){
			return '夏日专享';
		}
		if(value == 2){
			return '品质生活';
		} 
		if(value == 3){
			return '数码优选';
		}
	}
	
	function formaterProductInfoStatus(value,row,index){
		if(row.productStatus == 1){
			return '待审核';
		}else if(row.productStatus == 2){
			return '已审核';
		}else if( row.productStatus == 3 ){
			return '已驳回';
		}else if( row.productStatus == 4 ){
			return '已作废';
		}else if( row.productStatus == 5 ){
			return '募集中';
		}else if( row.productStatus == 6 ){
			return '募集成功';
		}else if( row.productStatus == 7 ){
			return '募集失败';
		}else if( row.productStatus == 8 ){
			return '待还款';
		}else if( row.productStatus == 9 ){
			return '已还款 ';
		}
	}
	
	
	$(document).ready(function () {
		$('#drProductPrizeLogList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../productPrizeLog/prizeLogCount.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeLogForm").serialize(), 
					success:function(data){
						$('#productPrizeCount').text(data.count);
					}
				});
			} 
    	});
	});
</script>
</body>
</html>

