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
	<table id="drClaimsInfoList" title="债权记录" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true, url: '../claims/drClaimsLoanList.do',
	    method:'post',rownumbers:true, 
	    pagination:true,toolbar:'#drClaimsInfoTools'">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">序号</th>
	    	<th data-options="field:'customerId'" hidden="true">序号</th>
	    	<th data-options="field:'status'" hidden="true">状态</th>  	
	    	<th data-options="field:'user_id'" hidden="true">状态</th>  	
	    	<th data-options="field:'no'" width="10%">借款合同编号</th>
	        <th data-options="field:'name'" width="10%">产品名称</th>
	       	<th data-options="field:'companyName'" width="15%">企业名称</th>
	       	<th data-options="field:'companyNameProtocolShow'" width="15%">企业名称协议显示</th>
	       	<th data-options="field:'bankName'" width="15%">开户行</th>
	       	<th data-options="field:'bankNo'" width="15%">银行卡号</th>
	       	<th data-options="field:'isFuiou'" width="5%" formatter="formatIsFuiou">存管开通</th>
	        <th data-options="field:'loanAmount'" width="10%" styler="styleColor" formatter="formatAmount">借款金额(元)</th>
	        <th data-options="field:'receiveInterest'" width="10%" styler="styleColor" formatter="formatAmount">应收利息(元)</th>
	        <th data-options="field:'serviceCharge'" width="10%" styler="styleColor" formatter="formatAmount">服务费(元)</th>	        
	        <th data-options="field:'addDate'" width="10%" formatter="formatDateBoxFull">新增日期</th>
	        <th data-options="field:'endDate'" width="10%" formatter="iFormatDateTimeBoxFull">截止日期</th>
	        <th data-options="field:'statusName'" width="5%">当前状态</th>
	        <th data-options="field:'showStatus'"  hidden="true">显示关闭状态</th>
			<th data-options="field:'_operate',align:'center'" width="20%" formatter="formatOper">基本操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="drClaimsInfoTools" style="padding:5px;height:750">
	  	<form id="drClaimsInfoForm">
	  		合同编号:<input id="searchClaimsLoanNo" name="no" class="easyui-textbox"  size="15" style="width:200px"/>
	  		产品名称:<input id="searchClaimsLoanName" name="name" class="easyui-textbox"  size="15" style="width:150px"/>
	  		企业名称:<input id="searchClaimsLoanCompanyName" name="companyName" class="easyui-textbox"  size="15" style="width:150px"/>
	  		录入日期:<input id="searchClaimsLoanStartDate" name="startDate" class="easyui-datebox" style="width:100px"/>到
	  				<input id="searchClaimsLoanEndDate" name="endDate" class="easyui-datebox" style="width:100px"/>
	  		当前状态: <select  id="searchClaimsLoanStatus" name="status" style="width:100px;" class="easyui-combobox">
						<option value=''>全部</option>
						<c:forEach items="${status }" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
	           		</select>
	    	<a id="searchClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetClaimsLoan" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrClaimsInfoBtn()">债权新增</a>
	    	<a id="exportClaimsLoanList" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo">导出</a>	
	    </form>
	</div>
	
	<div id="addDrClaimsInfoAdvanceDialog" class="easyui-dialog" title="放款提示"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrClaimsInfoAdvanceDialogBtn'" style="width:300px;height:200px;padding:5px;">
		<form id="addDrClaimsInfoAdvanceForm">
			<input type="hidden" id="addDrClaimsInfoAdvanceId"  name="id"/>
			<table align="center">
				<tr>
					<td align="left">放款日期：</td>
					<td>
						<input id="addStartDate" name="startDate" type="text" class="easyui-datebox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">服务费：</td>
					<td>
						<input id="addServiceCharge" name="serviceCharge" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:100000000,precision:2"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="addDrClaimsInfoAdvanceDialogBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addDrClaimsInfoAdvance()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeAddDrClaimsInfoAdvanceDialog()">取消</a>
			
		</div>
	</div>
	
	<div class="easyui-dialog" data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
		<form id="forwardForm" name="forwardForm" action="" method="POST" target="_blank">			
		  </form>
	</div>
	<!-- 查看存管信息弹框 -->
	<div id="addEnterpriseAccountAlertDiaLog" class="easyui-dialog" title="存管企业信息"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addEnterpriseAccountAlertBtn'" style="width:400px;height:350px;padding:5px;">
		<form id="addEnterpriseAccountAlertForm">
			<table align="center">
				<input id="customerId" name ="customerId" type="hidden" />
				<tr>
					<td align="left">开户支行：</td>
					<td>
						<input id="fuiou_bank_nm" name="fuiou_bank_nm" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">银行卡号：</td>
					<td>
						<input id="fuiou_capAcntNo" name="fuiou_capAcntNo" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">身份证号：</td>
					<td>
						<input id="fuiou_certif_id" name="fuiou_certif_id" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">公司名称：</td>
					<td>
						<input id="fuiou_cust_nm" name="fuiou_cust_nm" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">Email：</td>
					<td>
						<input id="fuiou_email" name="fuiou_email" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">登录账号：</td>
					<td>
						<input id="fuiou_login_id" name="fuiou_login_id" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">手机号：</td>
					<td>
						<input id="fuiou_mobile_no" name="fuiou_mobile_no" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td align="left">用户状态：</td>
					<td>
						<input id="fuiou_user_st" name="fuiou_user_st" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="addEnterpriseAccountAlertBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeAddEnterpriseAccountAlertDiaLog()">确定</a>
		</div>
	</div>
	<!-- 存管企业开户弹框 -->
	<div id="addEnterpriseAccountByHttpDiaLog" class="easyui-dialog" title="存管企业开户"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addEnterpriseAccountHttpAlertBtn'" style="width:700px;height:430px;padding:5px;">
		<form id="addEnterpriseAccountHttpAlertForm">
			<table align="center">
				<input id="customerId2" name ="customerId" type="hidden" />
				<tr>
					<td align="left">企业名称：</td>
					<td>
						<input id="cust_nm" name="cust_nm" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">法人姓名：</td>
					<td>
						<input id="artif_nm" name="artif_nm" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">身份证号码：</td>
					<td>
						<input id="certif_id" name="certif_id" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">手机号码：</td>
					<td>
						<input id="mobile_no" name="mobile_no" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">Email：</td>
					<td>
						<input id="email" name="email" type="text" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">开户行行别：</td>
					<td>
						<input class="easyui-combobox" id = "parent_bank_id" name="parent_bank_id" data-options="
							required:true,
							valueField:'bankCode',
					        textField:'bank',
					        url:'${apppath}/claims/getBankCode.do'
						">
					</td>
				</tr>
				<tr>
					<td align="left">开户行省市：</td>
					    <td><input class="easyui-combobox" id ="province" name="province" data-options=" 
					    	required:true, 
					        valueField:'cityCode',
					        textField:'city',
					        url:'${apppath}/claims/getCityList.do',  
					        onSelect:function(rec){  
					            $('[comboname=city_id]').combobox('clear');  
					            $('[comboname=city_id]').combobox('reload', '${apppath}/claims/getFuiouAreaByCity.do?cityCode=' + rec.cityCode);  
					        },
						    onLoadSuccess:function(){  
					            var value = $('#province').combobox('getValue'); 
					            if(value != ''){  
					            $('[comboname=city_id]').combobox('reload', '${apppath}/claims/getFuiouAreaByCity.do?cityCode=' + value);  
					                }  
					        }
					        ">  
					    </td>  
					    <td>区县:</td>  
					    <td><input class="easyui-combobox" id = "city_id" name="city_id" data-options="
					    valueField:'areaCode',
					    textField:'area',
					    required:true,
					    prompt:'请先填入省市名称'">  
					    </td>
				</tr>
				<tr>
					<td align="left">开户行支行名称：</td>
					<td>
						<input id="bank_nm" name="bank_nm" type="text" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">帐号：</td>
					<td>
						<input id="capAcntNo" name="capAcntNo" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">提现密码：</td>
					<td>
						<input id="password" name="password" type="text" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">登录密码：</td>
					<td>
						<input id="lpassword" name="lpassword" type="text" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="left">备注：</td>
					<td>
						<input id="rem" name="rem" type="text" class="easyui-textbox" />
					</td>
				</tr>
			</table>
		</form>
		<div id="addEnterpriseAccountHttpAlertBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addEnterpriseAccountByCommit()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeAddEnterpriseAccountHttpDiaLog()">取消</a>
		</div>
	</div>
<script type="text/javascript">
	//重置按钮
	$('#resetClaimsLoan').click(function(){
		$("#drClaimsInfoForm").form("reset");
		$("#drClaimsInfoList").datagrid("load", {});
	});
	
	//查询按钮
	$('#searchClaimsLoan').click(function(){
 		$('#drClaimsInfoList').datagrid({
			queryParams: {
				no: $('#searchClaimsLoanNo').val(),
				name: $('#searchClaimsLoanName').val(),
				companyName: $('#searchClaimsLoanCompanyName').val(),
				startDate: $('#searchClaimsLoanStartDate').datebox('getValue'),
				endDate: $('#searchClaimsLoanEndDate').datebox('getValue'),
				status: $('#searchClaimsLoanStatus').combobox('getValue'),
			}
		}); 
	});
	//添加基本操作链接
	function formatOper(val,row,index){
		var fo = " ";
		if(row.status=="1" || row.status=="3" || row.status=="10"){
			fo = fo+'<a href="#" class="btn l-btn l-btn-small" onclick="toShowClaimsInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="updateDrClaimsInfoBtn('+index+')">修改</a>';
		}else if((row.status=="4" || row.status=="5") && !row.receiveInterest){
			fo = fo+'<a href="#" class="btn l-btn l-btn-small" onclick="toShowClaimsInfoBtn('+index+')">查看</a>'+"   "+
					'<a href="#" class="btn l-btn l-btn-small" onclick="addDrClaimsInfoAdvanceBtn('+index+')">放款</a>';
			if(!row.user_id){
				//页面
				fo = fo+" "+'<a href="#" class="btn l-btn l-btn-small" onclick="addEnterpriseAccountAlert('+index+')">存管企业开户</a>';
				//直连
			//	fo = fo+" "+'<a href="#" class="btn l-btn l-btn-small" onclick="addEnterpriseAccountByHttp('+index+')">存管企业开户</a>';
			}
			else{
				fo = fo+" "+'<a href="#" class="btn l-btn l-btn-small" onclick="getFuiouEnterpriseInfo('+index+')">查看存管信息</a>';
			}
					
		}else{
			fo = fo+'<a href="#" class="btn l-btn l-btn-small" onclick="toShowClaimsInfoBtn('+index+')">查看</a>';	
		}
		//如果前面的状态成立，拼接满足下面状态的按钮
		if(row.showStatus=="1"){
			fo = fo+" "+'<a href="#" class="btn l-btn l-btn-small" onclick="updateShowOffStatusBtn('+index+')">关闭</a>';
		}else if(row.showStatus=="0"){
			fo = fo+" "+'<a href="#" class="btn l-btn l-btn-small" onclick="updateShowOnStatusBtn('+index+')">开启</a>';
		}
		return fo;      
	} 
	
	//跳转到债权添加页面
	function addDrClaimsInfoBtn(){
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权新增",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toAddClaimsInfo.do' ></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//跳转到债权修改页面
	function updateDrClaimsInfoBtn(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
/* 	    $.ajax({
			url: "${apppath}/claims/isOperate.do?id="+row.id+"&operate=update",
			dataType:"json",
			success:function(result){
				if(result.success){
				}else{
					$.messager.alert("提示信息",result.errorMsg,function(){
						$('#drClaimsInfoList').datagrid('reload');
					});
				}
			}
 		}); */
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权修改",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toUpdateClaimsInfo.do?lid="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//跳转到债权显示页面
	function toShowClaimsInfoBtn(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
		var mainTab = parent.$('#main-center');
		var detailTab = {
				title : "债权显示",
				content:"<iframe width='100%' height='100%' frameborder='0' src='${apppath}/claims/toShowClaimsInfo.do?lid="+row.id+"'></iframe>",
				closable : true
		};
		mainTab.tabs("add",detailTab);
	}
	
	//放款操作
	function addDrClaimsInfoAdvanceBtn(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
	    $('#addDrClaimsInfoAdvanceId').val(row.id);
		$("#addDrClaimsInfoAdvanceDialog").dialog("open");
	}
	
	//存管开户查询弹框信息
	function addEnterpriseAccountByHttp(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
	    var customerId = row.customerId;
		var lid = row.id;
	    var url = "${apppath}/claims/addEnterpriseAccountByHttp.do";
		$.ajax({
			url: url,
			dataType:"json",
			data:{
					"id": customerId,
					"lid": lid
				},
			success:function(result){
				if(result.success){
					$('#cust_nm').textbox('setValue', result.map.customer.companyName); 
					$('#artif_nm').textbox('setValue', result.map.customer.name); 
					$('#certif_id').textbox('setValue', result.map.customer.certificateNo); 
					$('#mobile_no').textbox('setValue', result.map.customer.phone); 
					$('#email').textbox('setValue', result.map.customer.companyMail); 
					$('#capAcntNo').textbox('setValue', result.map.customer.bankNo); 
					$('#capAcntNo').textbox('setValue', result.map.customer.bankNo); 
					$('#customerId2').val(customerId); 
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					$('#drClaimsInfoList').datagrid('reload');
					$("#addEnterpriseAccountByHttpDiaLog").dialog("close");
				}
			}
	  	});
	    $("#addEnterpriseAccountByHttpDiaLog").dialog("open");
	}
	//存管企业开户直连
	function addEnterpriseAccountByCommit(){
		var validate = $("#addEnterpriseAccountHttpAlertForm").form("validate");
		if(!validate){
			return false;
		}
		var url = "${apppath}/claims/addEnterpriseAccountByAjax.do";
		$.ajax({
			url: url,
			dataType:"json",
			data:$("#addEnterpriseAccountHttpAlertForm").serialize(),
			success:function(result){
				if(result.success){
					$('#drClaimsInfoList').datagrid('reload');
					$("#addEnterpriseAccountByHttpDiaLog").dialog("close");
					$.messager.alert("提示信息",result.msg);
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					$('#drClaimsInfoList').datagrid('reload');
				}
			}
	  	});
	}
	
	//恒丰开户（页面）
	function addEnterpriseAccountAlert(index){
		 $('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
	   /*$('#customerId').val();
		$("#addEnterpriseAccountAlertDiaLog").dialog("open");
		}
	//恒丰企业开户
	function addEnterpriseAccount(){ */
		var customerId = row.customerId;
		var lid = row.id;
	    var url = "${apppath}/claims/addEnterpriseAccount.do";
		$.ajax({
			url: url,
			dataType:"json",
			data:{
					"id": customerId,
					"lid": lid
				},
			success:function(result){
				if(result.success){
					//$.messager.alert("操作提示", result.msg);
					$('#drClaimsInfoList').datagrid('reload');
					//window.location.href=result.map.fuiouUrl;  
					//window.location.href=result.map.signature;  
					//window.open(result.map.fuiouUrl)
					//$("#message").val(result.map.signature);
					var json = result.map.signature ;
					json = eval("("+json+")");
					var arr=[];
					for(var key in json.message){		
						arr.push({'name':key,'value':json.message[key]});
						if(key !="toJSONString" && key !="signature"  )
							$("#forwardForm").prepend(key+ ':<input type="text" name="'+key+'" value="'+json.message[key]+'" /><br/>')
					}  
					for(var key in json){		
						arr.push({'name':key,'value':json[key]});
						if(key =="signature"  )
							$("#forwardForm").prepend(key+ ':<input type="text" name="'+key+'" value="'+json[key]+'" /><br/>')
					}
					$("#forwardForm").attr("action",result.map.fuiouUrl).submit();
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					$('#drClaimsInfoList').datagrid('reload');
				}
			}
	  	});
	}
	
	//放款操作
	function addDrClaimsInfoAdvance(index){
		var validate = $("#addDrClaimsInfoAdvanceForm").form("validate");
		if(!validate){
			return false;
		}
		var url = "${apppath}/claims/addDrClaimsInfoAdvance.do";
		$.ajax({
			url: url,
			dataType:"json",
			data:$("#addDrClaimsInfoAdvanceForm").serialize(),
			success:function(result){
				if(result.success){
					$.messager.alert("操作提示", result.msg);
					$('#drClaimsInfoList').datagrid('reload');
					$("#addDrClaimsInfoAdvanceDialog").dialog("close");
					$('#addDrClaimsInfoAdvanceForm').form('clear');
				}else{
					$.messager.alert("提示信息",result.errorMsg);
					$('#drClaimsInfoList').datagrid('reload');
					$("#addDrClaimsInfoAdvanceDialog").dialog("close");
				}
			}
	  	});
	}
	//修改资产为关闭状态
	function updateShowOffStatusBtn(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定关闭!", function(ensure){
			if(ensure){
				var url = "../claims/updateShowOffStatusBtn.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drClaimsInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$('#drClaimsInfoList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	}
 	//修改 资产为开启状态
	function updateShowOnStatusBtn(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
		$.messager.confirm("操作提示", "确定开启!", function(ensure){
			if(ensure){
				var url = "../claims/updateShowOnStatusBtn.do?id="+row.id;
				$.ajax({
					url: url,
					dataType:"json",
					success:function(result){
						if(result.success){
							$('#drClaimsInfoList').datagrid('reload');
							$.messager.alert("操作提示", result.msg);
						}else{
							$('#drClaimsInfoList').datagrid('reload');
							$.messager.alert("提示信息",result.errorMsg);
						}
					}
			  	});
			}
		});
	} 
	//导出操作
	$('#exportClaimsLoanList').click(function(){
		window.location.href="../claims/exportClaimsLoanList.do?name="+encodeURIComponent(encodeURIComponent($('#searchClaimsLoanName').val()))+
		"&companyName="+encodeURIComponent(encodeURIComponent($('#searchClaimsLoanCompanyName').val()))+
		"&no="+$('#searchClaimsLoanNo').val()+
		"&startDate="+$('#searchClaimsLoanStartDate').datebox('getValue')+
		"&endDate="+$('#searchClaimsLoanEndDate').datebox('getValue')+
		"&status="+$('#searchClaimsLoanStatus').combobox('getValue');
	});
	//修改时间的显示样式，只显示到年月日时分秒
	function iFormatDateTimeBoxFull(value) {  
	    if (value == null || value == '') {  
	        return '';  
	    }  
	    var dt = parseToDate(value);  
	    return dt.format("yyyy-MM-dd");  
	} 
	//
	function formatIsFuiou(val,row,index) {  
		console.log(row.user_id);
	    if (row.user_id ) {  
	        return '已开通';  
	    }else{
	  		return '未开通';
	    }  
	} 
	//关闭Dialog
	function closeAddDrClaimsInfoAdvanceDialog(){  
		$("#drClaimsInfoList").datagrid("reload");
		$("#addDrClaimsInfoAdvanceDialog").dialog("close");
	}
	//关闭Dialog
	function closeAddEnterpriseAccountAlertDiaLog(){  
		$("#drClaimsInfoList").datagrid("reload");
		$("#addEnterpriseAccountAlertDiaLog").dialog("close");
	}
	//关闭Dialog
	function closeAddEnterpriseAccountHttpDiaLog(){  
		$("#drClaimsInfoList").datagrid("reload");
		$("#addEnterpriseAccountByHttpDiaLog").dialog("close");
	}
	//查询存管企业信息
	function getFuiouEnterpriseInfo(index){
		$('#drClaimsInfoList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drClaimsInfoList').datagrid('getSelected'); 
		var user_id = row.user_id;
	    var url = "${apppath}/claims/getFuiouEnterpriseInfo.do";
		$.ajax({
			url: url,
			dataType:"json",
			data:{
					"user_id": user_id
				},
			success:function(result){
				if(result.success){
					$('#fuiou_bank_nm').textbox('setText', result.map.bank_nm);  
					$('#fuiou_capAcntNo').textbox('setText', result.map.capAcntNo);  
					$('#fuiou_certif_id').textbox('setText', result.map.certif_id);  
					$('#fuiou_cust_nm').textbox('setText', result.map.cust_nm);  
					$('#fuiou_email').textbox('setText', result.map.email);  
					$('#fuiou_login_id').textbox('setText', result.map.login_id);  
					$('#fuiou_mobile_no').textbox('setText', result.map.mobile_no);  
					var user_st;
					if(result.map.user_st == 1){
						user_st = '正常';
					}
					else if(result.map.user_st == 2){
						user_st = '已注销';
					}
					else if(result.map.user_st == 3){
						user_st = '申请注销';
					}
					$('#fuiou_user_st').textbox('setText', user_st);  
					$("#addEnterpriseAccountAlertDiaLog").dialog("open");
				}else{
					$.messager.alert("提示信息",result.errorMsg);
				}
			}
	  	});
	}
</script>
</body>
</html>

