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
	<table id="memberCarryList" title="提现记录 <span style='color: #0015FF;'>提现金额合计</span>：
	<span id='memberCarrySum' style='color: red;'></span>
	<span style='color: #0015FF;'>用户余额总额</span>：<span id='memberBalanceSum' style='color: red;'></span>
	<span style='color: #0015FF;'>存管余额总额</span>：<span id='memberBalanceSumFuiou' style='color: red;'></span>
	<span style='color: #0015FF;'>明日产品到期总额</span>：<span id='memberExpireSum' style='color: red;'></span>" 
	style="height:99%;width:99.9%">
		<thead>
	    <tr>
	    	<th data-options="field:'uid'" hidden="true">uid</th>
	    	<th data-options="field:'id'" hidden="true">id</th>
	    	<th data-options="checkbox:true,field:'ck'" width="3%"></th>
	    	<th data-options="field:'paymentNum'" width="12%">商户订单号</th>
	        <th data-options="field:'realName'" width="6%">用户姓名</th>
	        <th data-options="field:'phone'" width="6%" >用户手机号</th><!-- formatter='formatPhone' -->
	        <th data-options="field:'recommCodes'" width="6%" formatter='formatRecommCodes'>推荐码</th>
	       	<th data-options="field:'earningSum'" width="7%" formatter="formatAmount">收入总额</th>
	       	<th data-options="field:'paySum'" width="7%" formatter="formatAmount">支出总额</th>
	       	<th data-options="field:'balance'" width="7%" formatter="formatAmount">余额</th>
	       	<th data-options="field:'amount'" width="7%" styler="styleColor" formatter="formatAmount">提现金额</th>
	       	<th data-options="field:'poundage'" width="5%">提现手续费</th>	     
	       	<th data-options="field:'bankName'" width="8%">银行卡名称</th>
	       	<th data-options="field:'bankNum'" width="7%">银行账号</th>	      	
	        <th data-options="field:'status'" hidden="true">提现状态</th>
	       	<th data-options="field:'statusName'" width="4%">提现状态</th>
			<th data-options="field:'addTime'" width="8%" formatter="formatDateBoxFull">提现时间</th>
			<th data-options="field:'channel'" width="8%" formatter="withdrawalQD">提现渠道</th>
			<th data-options="field:'reason'" width="20%">失败原因</th>
			<th data-options="field:'type'" width="8%" formatter="withdrawalTD">提现通道</th>
			<th data-options="field:'_operate',align:'center'" width="10%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="memberCarryTools" style="padding:5px;height:750">
	  	<form id="memberCarryForm">
	  		商户订单号:<input id="searchMemberCarryPaymentNum" name="paymentNum" class="easyui-textbox"  size="100px" style="width:200px"/>
	  		提现时间:<input id="searchMemberCarryStartDate" name="startDate" class="easyui-datetimebox" style="width:140px"/>
	  		至<input id="searchMemberCarryEndDate" name="endDate" class="easyui-datetimebox" style="width:140px"/>
	  		审核时间:<input id="searchMemberCarryAudStartDate" name="audStartDate" class="easyui-datebox" style="width:100px"/>
	  		至<input id="searchMemberCarryAudEndDate" name="audEndDate" class="easyui-datebox" style="width:100px"/>
<!-- 	  		用户姓名:<input id="searchMemberCarryRealName" name="realName" class="easyui-textbox"  size="15" style="width:100px"/> -->
			推荐码：<input id="searchRecommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
	  		用户手机号:<input id="searchMemberCarryPhone" name="phone" class="easyui-textbox"  size="15" style="width:100px"/>
	  		提现金额:<input id="startamount" name="startamount" class="easyui-numberbox" style="width:100px"/>
	                    至<input id="endamount" name="endamount" class="easyui-numberbox" style="width:100px"/>
	  		提现状态: <select  id="searchMemberCarryStatus" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<c:forEach items="${status }" var="map">
						<option value='${map.key }'>${map.value }</option>
					</c:forEach>
	           </select>
	         <br>
	    	 提现通道: <select  id="type" name="type" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
					<option value='1'>连连</option>
					<option value='2'>金运通</option>
					<option value='3'>存管</option>
	           </select>
	         <br>
	    	<a id="searchMemberCarryBtn" href="#" class="easyui-linkbutton" onclick="searchMemberCarryBtn()" iconCls="icon-search">查询</a>
	    	<a id=resetMemberCarryBtn href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="exportMemberCarryBtn()">导出</a>	    	
	    	<a id="searchBatchCarryBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="batchCarryAudit()">批量审核</a>
	    </form>
	</div>
<script type="text/javascript">
	 $(document).ready(function () {
		 $('#searchMemberCarryStartDate').datetimebox('setValue',getstartdate());
		 $('#searchMemberCarryEndDate').datetimebox('setValue',getenddate());
		 searchMemberCarryBtn();
	}); 
	
	function loadsuccess(data){
		$('#memberCarryList').datagrid('clearSelections');
		 if (data.rows.length > 0) {
	            //循环判断操作为新增的不能选择
	            for (var i = 0; i < data.rows.length; i++) {
	                //根据isFinanceExamine让某些行不可选
	                if (data.rows[i].type ==3) {
	                    $("input[type='checkbox']")[i + 1].disabled = true;
	                }
	            }
	        }
	}
	
	function clickrow(rowIndex, rowData){
		  $("input[type='checkbox']").each(function(index, el){
	            //如果当前的复选框不可选，则不让其选中
	            if (el.disabled == true) {
	                $('#memberCarryList').datagrid('unselectRow', index - 1);
	            }
	        })
	}
	 
	//重置按钮
	$('#resetMemberCarryBtn').click(function(){
		$("#memberCarryForm").form("reset");
		$("#memberCarryList").datagrid("load", {});
	});
	//查询按钮
	function searchMemberCarryBtn(){
 		$('#memberCarryList').datagrid({
 			url:"../carry/memberCarryList.do",
 			fitColumns : true,
 			showFooter:true,
 			pagination : true,
 			rownumbers:true,
 			pageSize:25,
 			pageList:[25,50,100],
 			autoRowHeight : false,
 			toolbar:"#memberCarryTools", 
 			fit:true,
 			onLoadSuccess:function(data){
 		    	 loadsuccess(data);
 			}, 
 			onClickRow : function(rowIndex, rowData) {
 				clickrow(rowIndex, rowData);
 			},
 			rowStyler:function(index,row){
 		        if(row.type=='3'){
 		            return 'background-color:#FFEEDD;font-weight:bold;';
 		        }
 	   		},
			queryParams: {
				paymentNum: $('#searchMemberCarryPaymentNum').textbox('getValue'),
				startDate: $('#searchMemberCarryStartDate').datetimebox('getValue'),
				endDate: $('#searchMemberCarryEndDate').datetimebox('getValue'),
				audStartDate: $('#searchMemberCarryAudStartDate').datebox('getValue'),
				audEndDate: $('#searchMemberCarryAudEndDate').datebox('getValue'),
// 				realName: $('#searchMemberCarryRealName').val(),
				recommCodes: $('#searchRecommCodes').val(),
				phone: $('#searchMemberCarryPhone').val(),
				status: $('#searchMemberCarryStatus').combobox('getValue'),
				startamount:$('#startamount').numberbox('getValue'),
				endamount:$('#endamount').numberbox('getValue'),
				type: $('#type').combobox('getValue'),
			},
			onBeforeLoad: function (d) {
				    $.ajax({
					url:"${apppath}/carry/memberCarrySum.do",
					type:"POST",
					data:$("#memberCarryForm").serialize(),  
					success:function(result){
						$("#memberCarrySum").text(fmoney(result.memberCarrySum,2));
						/* $("#JYTCarryBalance").text(fmoney(result.JYTCarryBalance,2)); */
						$("#memberBalanceSum").text(fmoney(result.memberBalanceSum,2));
						$("#memberBalanceSumFuiou").text(fmoney(result.memberBalanceSumFuiou,2));
						$("#memberExpireSum").text(fmoney(result.memberExpireSum,2));
					}
					});
				} 
		}); 
	};
		//添加基本操作链接
	function formatOper(val,row,index){  
		if(row.status=="0" && row.type!="3"){
		return '<a href="#" class="btn l-btn l-btn-small" onclick="memberCarryAudit('+index+')">审核</a>'+"   "+
		'<a href="#" class="btn l-btn l-btn-small" onclick="memberCarryRefuse('+index+')">拒绝</a>';
		}
	} 
	 
		//拒绝操作
	function memberCarryRefuse(index){  
		$('#memberCarryList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#memberCarryList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定拒绝吗？', function(ensure){
			if(ensure){
				var url = "${apppath}/carry/memberCarryRefuse.do";
				$.ajax({
					url: url,
					type:'POST',
					data:{id:row.id},
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#memberCarryList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	
	//审核操作
	function memberCarryAudit(index){  
		$('#memberCarryList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#memberCarryList').datagrid('getSelected'); 
		$.messager.confirm('操作提示', '你确定审核通过吗？', function(ensure){
			if(ensure){
				var url = "${apppath}/carry/memberCarryAudit.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#memberCarryList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	//批量审核
	function batchCarryAudit(){
		var carryListData = $("#memberCarryList").datagrid('getChecked');
		var listData = new Array();
		var data ;
		if(carryListData!=null && carryListData!=""){			
			for (var i = 0; i < carryListData.length; i++){   
			 	if(0 != carryListData[i].status){
			 		$.messager.alert('系统提示','存在已审核的记录，请先取消已审核的记录');
			 		return false;
			 	}
			 	if(3 == carryListData[i].type){
			 		$.messager.alert('系统提示','存在勾选存管数据，请先取消勾选');
			 		return false;
			 	}
			 	data = new Object();
			 	data["id"]=carryListData[i].id;
			 	data["paymentNum"]=carryListData[i].paymentNum;
			 	listData[i]=data;			 	
			 }
	 		$.ajax({
				type: 'post',
				url : "../carry/batchCarryAudit.do",
				cache : false,
				data : {					
					carryListData : JSON.stringify(carryListData)
				},
				cache : false,
				async : false,
				success : function(result) {
					if (result.success) { 
						var msg = "";
						if(result.errorMsg){
							var error = result.errorMsg.split(",");
							for(var i=0;i<error.length-1;i++){
								msg+=error[i]+"<br>";
								
							}
						}
						$.messager.alert('操作提示','操作成功,'+result.msg+'<br>'+msg);				
// 						$("#memberCarryList").datagrid("load", {});
						$('#searchMemberCarryBtn').click();
					}  
					else {  
						$.messager.alert('操作提示','操作失败'+result.errorMsg);
						return false;  
					} 
			    },
			    error : function(message) {
						 $.messager.alert('操作提示','操作失败'+result.errorMsg);
						 return false; 
					}
			 });			 
         }else{
         		$.messager.alert('系统提示','请选择客户！');  
         		return false;
         }
	}

	function exportMemberCarryBtn(){
		window.location.href="../carry/exportMemberCarry.do?startDate="+$('#searchMemberCarryStartDate').datetimebox('getValue')+
						"&endDate="+$('#searchMemberCarryEndDate').datetimebox('getValue')+
						"&audStartDate="+$('#searchMemberCarryAudStartDate').datebox('getValue')+
						"&audEndDate="+$('#searchMemberCarryAudEndDate').datebox('getValue')+
						/* "&realName="+encodeURIComponent(encodeURIComponent($('#searchMemberCarryRealName').val()))+ */
						"&phone="+$('#searchMemberCarryPhone').val()+
						"&status="+$('#searchMemberCarryStatus').combobox('getValue')+
						"&startamount="+$('#startamount').numberbox('getValue')+
						"&type="+$('#type').combobox('getValue')+
						"&endamount="+$('#endamount').numberbox('getValue');
	}
	
	
	function formatRecommCodes(val,row,index){
		return "<a href='#' class='easyui-linkbutton' onclick=\"selectDataByRecommCodes(\'"+val+"')\">"+val+"</a>";
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
	function selectDataByRecommCodes(recommCodes){
			addTab("用户收支","../memberFundsLog/toMemberFundsLogList.do?recommCodes="+recommCodes);
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
</script>
</body>
</html>

