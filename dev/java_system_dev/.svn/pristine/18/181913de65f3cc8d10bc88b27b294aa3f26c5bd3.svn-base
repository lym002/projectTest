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
	<table id="memberCrushRecordList" title="充值记录  <span style='color: #0015FF;'>充值金额合计</span>：<span id='recordSum' style='color: red;'></span>" style="height:99%;width:99.9%">
		<thead>
	    <tr>
	        <th data-options="field:'id'" hidden="true"></th>
	       	<th data-options="field:'payNum'" width="12%">订单号</th>
	        <th data-options="field:'realName'" width="5%" >用户姓名</th>
			<th data-options="field:'recommCodes'" width="5%" formatter='formatRecommCodes' >推荐码</th>
	        <th data-options="field:'phone'" width="5%" >用户手机号</th><!-- formatter='formatPhone' -->
	       	<th data-options="field:'amount'" width="6%" styler="styleColor" formatter="formatAmount">充值金额</th>
			<th data-options="field:'addTime'" width="8%" formatter="formatDateBoxFull">充值时间</th>
			<th data-options="field:'bankName'" width="7%" >充值银行</th>
			<th data-options="field:'type'" hidden="true"></th>
			<th data-options="field:'typeName'" width="5%">充值类型</th>
	       	<th data-options="field:'channelName'" width="4%">充值渠道</th>
	       	<th data-options="field:'channel'" hidden="true"></th>
	        <th data-options="field:'statusName'" width="4%">充值状态</th>
	        <th data-options="field:'remark'" width="20%">备注</th>
	       	<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="10%">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="memberCrushRecordTools" style="padding:5px;height:750">
	  	<form id="memberCrushRecordForm">
	    	订单号：<input id="searchMemberCrushPayNum" name="payNum" class="easyui-textbox"   style="width:200px"/>
	  		充值时间：<input id="searchMemberCrushRecordStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchMemberCrushRecordEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
<!-- 	  		用户姓名：<input id="realNameID" name="realName"  class="easyui-textbox"  size="15" style="width:100px" hidden="true"/> -->
	  		推荐码：<input id="searchRecommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
	  		用户手机号：<input id="searchMemberCrushRecordPhone" name="phone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		充值类型：<select id="searchMemberCrushType" name="type" class="easyui-combobox"   style="width:100px">
	  					<option value=''>全部</option>
						<c:forEach var="map" items="${type}">
						<option value='${map.key}'>${map.value}</option>
			            </c:forEach>
	  				</select>
	  		充值渠道：<select id="searchMemberCrushChannel" name="channel" class="easyui-combobox"   style="width:80px">
	  					<option value=''>全部</option>
						<c:forEach var="map" items="${channel}">
						<option value='${map.key}'>${map.value}</option>
			            </c:forEach>
	  				</select>
	  		状态： <select id="searchMemberCrushRecordStatus" name="status" style="width:80px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach var="map" items="${status}">
					<option value='${map.key}'>${map.value}</option>
		            </c:forEach>
	           </select>
	    	<a id="searchMemberCrushRecordBtn" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMemberCrushRecordBtn()">查询</a>
	    	<a id="resetMemberCrushRecordBtn" href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<!--a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addMemberCrushRecordBtn()">后台充值</a-->
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="exportMemberCrushRecordBtn()">导出</a>
	    </form>
	</div>
	<div id="addMemberCrushRecordDialog" class="easyui-dialog" title="后台充值"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addMemberCrushRecord'" style="width:300px;height:240px;padding:5px;">
		<form id="addMemberCrushRecordForm">
			<table align="center">
				<tr>
					<td>
						用户真实姓名:
					</td>
					<td>	
						<input type="text" name="realName" class="easyui-textbox" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td>
						手机号:
					</td>
					<td>		
						<input class="easyui-textbox" name="phone" data-options="required:true,validType:'mobile'"/>
					</td>
				</tr>
				<tr>
					<td>
						身份证号:
					</td>
					<td>		
						<input class="easyui-textbox" name="idCards" maxlength="10" data-options="required:true,validType:'idcard'"/>
					</td>
				</tr>
				<tr>
					<td>
						充值金额:
					</td>
					<td>		
						<input class="easyui-numberbox" name="amount" data-options="required:true,min:0,precision:2,validType:'intOrFloat'"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="addMemberCrushRecord">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="memberCrushAdd()">添加</a>
		</div>
	</div>
	<div id="queryPayResultDialog" class="easyui-dialog" title="查询充值结果"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#queryPayResultBtn'" style="width:300px;height:240px;padding:5px;">
			<table align="center">
				<tr>
					<td>
						创建时间:
					</td>
					<td>	
						<span id="payDtorder"></span>
					</td>
				</tr>
				<tr>
					<td>
						订单号:
					</td>
					<td>		
						<span id="payNoorder"></span>
					</td>
				</tr>
				<tr>
					<td>
						金额:
					</td>
					<td>		
						<span id="payMoneyorder"></span>
					</td>
				</tr>
				<tr>
					<td>
						交易状态:
					</td>
					<td>		
						<span id="payResultpay"></span>
					</td>
				</tr>
				<tr>
					<td>
						支付方式:
					</td>
					<td>		
						<span id="payType"></span>
					</td>
				</tr>
			</table>
		<div id="queryPayResultBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closePayResult()">关闭</a>
		</div>
	</div>
<script type="text/javascript">	
	 $(document).ready(function () {
		$('#searchMemberCrushRecordStartDate').datebox('setValue',getdate());
		$('#searchMemberCrushRecordEndDate').datebox('setValue',getdate());
		searchMemberCrushRecordBtn();
	}); 
	 
	//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.type != 3){
			if(row.channel != 4){
				return '<a href="#" class="btn l-btn l-btn-small" onclick="queryPayResult('+index+')">查询充值状态</a>';
			}
		}
	} 
	//重置按钮
	$('#resetMemberCrushRecordBtn').click(function(){
		$("#memberCrushRecordForm").form("reset");
		$("#memberCrushRecordList").datagrid("load", {});
	});
	//查询按钮
	function searchMemberCrushRecordBtn(){		
 		$('#memberCrushRecordList').datagrid({
 			url: '../crush/memberCrushRecordList.do',
 			singleSelect:true,
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:25,
 			pageList:[25,50,100],
 			autoRowHeight : false,
 			toolbar:"#memberCrushRecordTools", 
 			fit:true,
 			queryParams: {
				payNum: $('#searchMemberCrushPayNum').val(),
				startDate: $('#searchMemberCrushRecordStartDate').datebox('getValue'),
				endDate: $('#searchMemberCrushRecordEndDate').datebox('getValue'),
// 				realName:$('#realNameID').val(),
				phone: $('#searchMemberCrushRecordPhone').val(),
				channel: $('#searchMemberCrushChannel').combobox("getValue"),
				status: $('#searchMemberCrushRecordStatus').combobox("getValue"),
				recommCodes: $('#searchRecommCodes').textbox('getValue'),
				type: $('#searchMemberCrushType').combobox("getValue")
			},
			onBeforeLoad: function (d) {
				 $.ajax({
						url:"${apppath}/crush/memberCrushRecordSum.do",
						type:"POST",
						data:$("#memberCrushRecordForm").serialize(),  
						success:function(result){
							$("#recordSum").text(fmoney(result.recordSum,2));
							/* $("#JYTCrushBalance").text(fmoney(result.JYTCrushBalance,2)); */
						}
						});
			}
		}); 
	};
	
	//跳转充实添加页面
	function addMemberCrushRecordBtn(){  
		$("#addMemberCrushRecordForm").form("reset");
		$("#addMemberCrushRecordDialog").dialog("open");
	}
	
	//查询充值结果
	function queryPayResult(index){  
		$('#memberCrushRecordList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#memberCrushRecordList').datagrid('getSelected'); 
		var url = "${apppath}/crush/queryPayResult.do?id="+row.id;
		$.ajax({
			url: url,
			dataType:"json",
			success:function(result){
				if(result.success){
					$("#payDtorder").html(result.map.dtorder);
					$("#payNoorder").html(result.map.noorder);
					$("#payMoneyorder").html(result.map.moneyorder);
					$("#payResultpay").html(result.map.resultpay);
					$("#payType").html(result.map.paytype);
					$("#queryPayResultDialog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
	
	//关闭查询充值结果窗口
	function closePayResult(){  
		$("#memberCrushRecordList").datagrid("reload");
		$("#queryPayResultDialog").dialog("close");
	}
	
	//后台充值
	function memberCrushAdd(){  
		var validate = $("#addMemberCrushRecordForm").form("validate");
		if(!validate){
			return false;
		}
   		$.ajax({
          	url: "${apppath}/crush/memberCrushAdd.do",
            type: 'POST',
            data:$("#addMemberCrushRecordForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#memberCrushRecordList").datagrid("reload");
				$("#addMemberCrushRecordDialog").dialog("close");
			}else{
				$.messager.alert("提示信息",result.errorMsg);
			}
			}
        });
	}

	function exportMemberCrushRecordBtn(){
		window.location.href="../crush/exportMemberCrushRecord.do?payNum="+$('#searchMemberCrushPayNum').val()+
// 						"&realName="+encodeURIComponent(encodeURIComponent($('#realNameID').val()))+
						"&phone="+$('#searchMemberCrushRecordPhone').val()+
						"&startDate="+$('#searchMemberCrushRecordStartDate').combobox('getValue')+
						"&endDate="+$('#searchMemberCrushRecordEndDate').combobox('getValue')+
						"&channel="+$('#searchMemberCrushChannel').combobox('getValue')+
						"&recommCodes"+ $('#searchRecommCodes').textbox('getValue')+
						"&status="+$('#searchMemberCrushRecordStatus').combobox('getValue')+
						"&type="+$('#searchMemberCrushType').combobox('getValue');
	}
	
	function formatPhone(val,row,index){
		return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.uid+"')\">"+val+"</a>";
	}
	function selectData(uid){
			$.ajax({
				type: 'post',
				url : "../member/selectByPrimaryKey.do?uid="+uid,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
				
					addTab("用户收支","../memberFundsLog/toMemberFundsLogList.do?mobilePhone="+str);
// 					window.open("../memberFundsLog/toMemberFundsLogList.do?mobilePhone="+str);
			    },
			 });
		}
		
	function addTab(title,href){
		var mainTab = parent.$('#main-center');
		if(mainTab.tabs("exists",title)){
			mainTab.tabs("select",title); 
			//再次点击时，页面内容重新加载
		    var currTab = mainTab.tabs('getTab', title);
			var iframe = $(currTab.panel('options').content);
			var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';  
			var src = iframe.attr('src');
			mainTab.tabs('update', { tab: currTab, options: { content: content }});
		}else{
			var content;
			if(href){
		    	content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';   
			}else{
				content='建设中';
			}
			mainTab.tabs('add',{   
			    title:title,   
				closable:true,   
				content:content   
			});
		}
    }
	function formatRecommCodes(val,row,index){
		return "<a href='#' class='easyui-linkbutton' onclick=\"selectDataByRecommCodes(\'"+val+"')\">"+val+"</a>";
	}
	function selectDataByRecommCodes(recommCodes){
			addTab("用户收支","../memberFundsLog/toMemberFundsLogList.do?recommCodes="+recommCodes);
	}
</script>
</body>
</html>

