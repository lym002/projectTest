<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
</head>
<body>
	<div id="customerbtn" style="padding:5px;height:750">
		<form id="selcutomer">
	  		<!-- 客户姓名:<input id="customerName" name="customerName" class="easyui-textbox"  size="15" style="width:100px"/> -->
	  		推荐码:<input id="recommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
	  		客户号码:<input id="mobilePhone" name="mobilePhone" class="easyui-numberbox"  size="15" style="width:100px"/>
	  		电销姓名:<input id="userName" name="userName" class="easyui-textbox"  size="15" style="width:100px"/>
	  		分配状态: <select  id="status" name="status" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="1">已分配</option>
				 	<option value="2">未分配</option>
	           	 </select>
	       	 绑卡状态:<select  id="isbank" name="isbank" style="width:100px;" class="easyui-combobox">
				 	<option value=''>全部</option>
				 	<option value="0">否</option>
				 	<option value="1">是</option>
	           	 </select>
	        累计投资金额:<input id="startMoney" name="startMoney" class="easyui-textbox"  size="15" style="width:100px"/>至
	        <input id="endMoney" name="endMoney" class="easyui-textbox"  size="15" style="width:100px"/>
	 <!--        最后沟通时间:<input class="easyui-datebox" id="lastcall" name="lastcall" style="width:100px"/> -->
	       *天未跟进:<input id="notFollowNumAgo" name="notFollowNumAgo"   class="easyui-numberbox" style="width:100px"/>
	  		-<input id="notFollowNumAfter" name="notFollowNumAfter"  class="easyui-numberbox" style="width:100px"/></br> 
	        
  		<!--  -->
	  		渠道名称:<select class="easyui-combogrid" id="channelCode" name="channelCode" style="width:240px;padding-bottom: 3px;" data-options="
							panelWidth: 240,
							multiple: true,
							multiline:true,
							editable:false,
							idField: 'id',
							textField: 'name',
							url: '../channel/drAllChannelInfoList.do?orders=0',
							method: 'get',
							columns: [[
								{field:'id',checkbox:true},
								{field:'code',title:'渠道号',width:80},
								{field:'name',title:'渠道名称',width:80},
							]],
							fitColumns: true
						">
				</select>
			 最近的登录时间:<input class="easyui-datebox" id="startLoginTime" style="width:100px"/>至<input class="easyui-datebox" id="endLoginTime" style="width:100px"/> 	
         	<br>	
	  		渠道类型:<select name="channelType" style="width: 172px" class="easyui-combobox" id="channelType">
	  			<option value="">全部</option>	
				<option value=1>CPS</option>	
				<option value=0>非CPS</option>	
			</select>
  			<!--  客户类型:<input id="customerType" name="customerType" class="easyui-combobox" 
							data-options="valueField:'code',textField:'cnvalue',url:'../membercall/seltype.do'"/>  -->
	  		沟通次数:<input id="callNumAgo" name="callNumAgo"   size="15" style="width:100px"/>
	  				-<input id="callNumAfter" name="callNumAfter"  size="15" style="width:100px"/>
	  		注册时间:<input  id="regDateStart" name="regDateStart" class="easyui-datebox" style="width:100px"/>
	  				-<input  id="regDateEnd" name="regDateEnd" class="easyui-datebox" style="width:100px"/>
			操作人:<input id="opUserKy" name="opUserKy" class="easyui-combobox" 
				data-options="valueField:'userKy',textField:'name',url:'../user/selectOperater.do?status=1&rName=电销经理'"/> 
			电销部门:<select name="deptId" style="width: 150px" class="easyui-combobox" id="deptId">
				<c:choose>
					<c:when  test="${roleName eq '系统管理员' || roleName eq '电销总监' }">
						<option value="" selected="selected">全部</option>
			  			<option value="0" >未分配</option>
			  			<option value="1" >电销一部</option>
			  			<option value="2" >电销二部</option>	  			
					</c:when>
					<c:otherwise>
						<option value=""  selected="selected">全部</option>	
						<c:if test="${deptId eq 1}">
				  			<option value="1" >电销一部</option>			  		  			
						</c:if>				
						<c:if test="${deptId eq 2}">
				  			<option value="2" >电销二部</option>			  		  			
						</c:if>
					</c:otherwise>				
				</c:choose>
			</select>
	        <br>
			<c:forEach var="map" items="${wincallType}">
				<label>
				<input type="checkbox" name="customerType" value="${map.key}" id="${map.key}" />${map.value}
				</label>
		    </c:forEach>
		    <br/>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selcutomer()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetcustomer()">重置</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">分配</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteCustomer()">取消分配</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="update()">转分配</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="freezeCustomer()">冻结</a>
	    	<a id="exportCustomer" href="javascript:exportCustomer();" class="easyui-linkbutton" iconCls="icon-redo">导出</a>
	    </form>
	</div>
	<table id="customer"  style="height:99%;width:99.9%"></table>

	<!-- -----------------分配用户------------------------------->
	<div id="addCustomer" class="easyui-dialog" style="height:95%;width:50%" closed="true"	buttons="addCustomerBtn"  data-options="resizable:true,modal:true,closed:true">
		<div id="userbtn" style="padding:5px;height:750">
			<form id="selUser">
		  		用户姓名:<input id="name" name="name" class="easyui-textbox"  size="15" style="width:100px"/>
		  		手机号:<input id="mobile" name="mobile" class="easyui-textbox"  size="15" style="width:100px"/>
		  		人事状态:<select  id="voStatus" name="voStatus" style="width:100px;" class="easyui-combobox">
					 	<option value=''>全部</option>
					 	<option value="1">在职</option>
					 	<option value="0">离职</option>
		           	 </select>
	           	 电销部门:<select name="deptId" style="width: 150px" class="easyui-combobox" id="deptIdc">
		          	 	<c:choose>
							<c:when  test="${roleName eq '系统管理员' || roleName eq '电销总监' }">
								<option value="" selected="selected">全部</option>
					  			<option value="1" >电销一部</option>
					  			<option value="2" >电销二部</option>	  			
							</c:when>
							<c:otherwise>
								<c:if test="${deptId eq 1}">
						  			<option value="1" selected="selected">电销一部</option>			  		  			
								</c:if>				
								<c:if test="${deptId eq 2}">
						  			<option value="2"  selected="selected">电销二部</option>			  		  			
								</c:if>
							</c:otherwise>				
						</c:choose>
						</select>
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selUser()">查询</a>
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetUser()">重置</a>
		    </form>
		</div>
		<table id="userList"  style="height:90%;width:99.9%"></table>
		<center>
		<div id="addCustomerBtn" style="padding:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">确定</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addCustomer').dialog('close')">取消</a>
		</div>
		</center>
	</div>

	

<script type="text/javascript">
		var type="";
		//客户列表
		var customer = $('#customer');
		
			customer.datagrid({
				url : "../member/queryCustomer.do",
				title : '会员分配表',
				fitColumns : true,
				pagination : true,
				checkbox:true,
				pageSize:25,
				pageList:[25,50,100],
				autoRowHeight : false,
				toolbar:"#customerbtn",
				fit:true,
				columns : [ [ 
					{
					field:'ck',
					checkbox:true
					},{
					field : 'uid',
					title : 'uid',
					align : "center",
					hidden:true
					},{
					field : 'id',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'realname',
					title : '客户姓名',
					width : '4%',
					align : "center"
					},{
					field : 'mobilePhone',
					title : '客户手机号',
					width : '6%',
					align : "center"
					},{
					field : 'recommCodes',
					title : '推荐码',
					width : '5%',
					align : "center"
					},{
					field : 'isbank',
					title : '是否绑卡',
					width : '4%',
					align : "center"
					},{
					field : 'isRecharge',
					title : '是否充值',
					width : '4%',
					align : "center"
					},{
					field : 'isInvestment',
					title : '是否投资',
					width : '4%',
					align : "center"
					},{
					field : 'moneysum',
					title : '累计投资金额',
					formatter: function(value, row, index){
									if(value>0){
       									return '<font color="red">' + fmoney(value,2) + '</font>';
										}
									}, 
					width : '8%',
					align : "center"
					},{
					field : 'regfrom',
					title : '注册来源',
					width : '4%',
					align : "center"
					},{
					field : 'regdate',
					title : '注册时间',
					width : '5%',
					align : "center"
					},{
					field : 'channelType',
					title : '渠道类型',
					width : '4%',
					disabled:true,
					align : "center"
					},{
					field : 'channelName',
					title : '渠道名称',
					disabled:true,
					width : '5%',
					align : "center"
					},{
					field : 'meets',
					title : '沟通次数',
					width : '4%',
					align : "center" <%--新增沟通次数字段 --%>
					},{
					field : 'lastlogintime',
					title : '最近登陆时间',
					width : '8%',
					align : "center"
					},{
					field : 'lastCallTime',
					title : '最后沟通时间',
					width : '7%',
					align : "center"
					},{
					field : 'notFollowNum',
					title : '*天未跟进',
					disabled:true,
					width : '4%',
					align : "center"
					},{
					field : 'customerType',
					title : '客户类型',
					disabled:true,
					width : '6%',
					align : "center"
					},{
					field : 'username',
					title : '电销人员',
					width : '6%',
					align : "center"
					},{
						field : 'deptId',
						title : '部门',
						width : '5%',
						align : "center",
						formatter:function(value,rows){
							if(value=="1"){
								return "电销一部";
							}
							else if(value=="2"){
								return "电销二部";
							}
							
					}
					},{
					field : 'createUsername',
					title : '操作人',
					width : '6%',
					align : "center"
					},{
					field : 'createDate',
					title : '操作时间',
					width : '5%',
					align : "center"
					}
				] ]
			});

		
		//电销用户列表
		var userList = $('#userList');
		$(function() {
			userList.datagrid({
				url : "../member/queryUser.do",
				title : '电销用户表',
				fitColumns : true,
				singleSelect : true,
				toolbar : "#userbtn",
				columns : [ [ 
					{
					field : 'userKy',
					title : 'id',
					align : "center",
					hidden:true
					},{
					field : 'name',
					title : '用户姓名',
					width : '15%',
					align : "center"
					},{
					field : 'mobile',
					title : '手机号',
					width : '20%',
					align : "center"
					},{
					field : 'phone',
					title : '电话',
					width : '20%',
					align : "center"
					},{
					field : 'status',
					title : '人事状态',
					width : '15%',
					align : "center",
					formatter: function(value, row, index){
							if(value==1){
								return '在职';
							}else{
								return '离职';
							}
						}
					},{
					field : 'deptId',
					title : '部门',
					width : '10%',
					align : "center",
					formatter: function(value, row, index){
							if(value==1){
								return '电销一部';
							}else{
								return '电销二部';
							}
						}
					}
				] ]
			});
		}); 
		
		
		//分配电销
		function add(){
			type="1";
			var customerData = customer.datagrid('getChecked');
			if(customerData!=null && customerData!=""){
				var str=JSON.stringify(customerData);
				var obj = eval(str);
				for (var i = 0; i < obj.length; i++)
				 {   
				 	if(obj[i].deptId == 0){
				 		$.messager.alert('系统提示','存在未分配部门的客户，请先取消！');
				 		return false;
				 	}else if(obj[i].username){
				 		$.messager.alert('系统提示','存在已分配的客户，请先取消已经分配过的客户！');
				 		return false;
				 	};
				 }
				$('#addCustomer').dialog('open').dialog('setTitle', '客户分配');
	         }else{
	         		$.messager.alert('系统提示','请选择客户！');  
	         		return false;
	         }
		}	
		
		//查询按钮
		function selUser(){
	 		$('#userList').datagrid({
				queryParams: {
					name:$('#name').val(),
					mobile:$('#mobile').val(),
					phone: $('#phone').val(),
					status: $('#voStatus').combobox("getValue"),
					deptId: $('#deptIdc').combobox("getValue")
					}
			})
		}
		
		//重置按钮
		function resetUser(){
			$("#selUser").form("reset");
			$("#userList").datagrid("load", {});
		};
		
		//查询按钮
		function selcutomer(){
	 		 customer.datagrid('reload', {
	                /* customerName: $('#customerName').val(), */
	                userName:$('#userName').val(),
	                recommCodes:$('#recommCodes').val(), 
	                status: $('#status').combobox("getValue"),
	                isbank: $('#isbank').combobox("getValue"),
	                startMoney:$('#startMoney').val(),
	                endMoney:$('#endMoney').val(),
	             /*    lastcall:$('#lastcall').datetimebox("getValue"), */
	                mobilePhone:$('#mobilePhone').val(),
	                regDateStart:$('#regDateStart').datetimebox("getValue"),
	                regDateEnd:$('#regDateEnd').datetimebox("getValue"),
	                opUserKy:$('#opUserKy').combobox("getValue"),
	                callNumAfter:$('#callNumAfter').val(),
	                callNumAgo:$('#callNumAgo').val(),
	                notFollowNumAfter:$('#notFollowNumAfter').val(),
	                notFollowNumAgo:$('#notFollowNumAgo').val(),
	                customerType:customerTypeAll(),
	                startLoginTime:$('#startLoginTime').datebox("getValue"),
		            endLoginTime:$('#endLoginTime').datebox("getValue"),
	                channelType:$('#channelType').combobox("getValue"),
	                channelName:$('#channelCode').combogrid('getText')+"",
	                deptId:$('#deptId').combobox("getValue")
	           	});
		}
		
		function customerTypeAll(){//输出选中的值
			var checks="";  
			$('input[name="customerType"]:checked').each(function(){
	        	checks +=$(this).val()+",";
			});  
			return checks;  
		} 
		
		//重置按钮
		function resetcustomer(){
			$("#selcutomer").form("reset");
			$("#customer").datagrid("load",{});
		};
		
		//保存
		function save(){
			var row = userList.datagrid('getSelected');
			var customerData = customer.datagrid('getChecked');
			if(row!=null && row!=""){
				if(type=="1"){//分配
					$.ajax({
						type: 'post',
						url : "../member/saveCustomerAllot.do",
						cache : false,
						data : {
							userKy : row.userKy,
							conData : JSON.stringify(customerData)
						},
						cache : false,
						async : false,
						success : function(message) {
							if (message=='success') { 	
								$.messager.alert('操作提示','操作成功');
								$('#addCustomer').dialog('close');
								selcutomer();
							}else if(message=='0'){
									$.messager.alert('操作提示','存在未分部门配客户');
									return false; 
							}else if(message=='1'){
								$.messager.alert('操作提示','客户已分配，请刷新页面');
								return false; 
						}      
							else {  
								$.messager.alert('操作提示','操作失败');
								return false;  
							} 
					    },
					    error : function(message) {
								 $.messager.alert('操作提示','操作失败');
								 return false; 
							}
					 });
				}
				if(type=="2"){//转分配
					$.messager.confirm('操作提示', '确定转分配吗？', function (data) {		
					 	if(data){
					 		$.ajax({
								type: 'post',
								url : "../member/deleteCustomerAllot.do",
								cache : false,
								data : {
									conData : JSON.stringify(customerData)
								},
								cache : false,
								async : false,
								success : function(message) {
									if (message=='success') { 	
										$.ajax({
											type: 'post',
											url : "../member/saveCustomerAllot.do",
											cache : false,
											data : {
												userKy : row.userKy,
												conData : JSON.stringify(customerData)
											},
											cache : false,
											async : false,
											success : function(message) {
												if (message=='success') { 	
													$.messager.alert('操作提示','操作成功');
													$('#addCustomer').dialog('close');
													selcutomer();
												}  
												else {  
													$.messager.alert('操作提示','操作失败');
													return false;  
												} 
										    },
										    error : function(message) {
													 $.messager.alert('操作提示','操作失败');
													 return false; 
												}
										 });
									}else if(message=='0'){
										$.messager.alert('操作提示','存在未分配部门客户');
										return false; 
									}  
									else {  
										$.messager.alert('操作提示','操作失败');
										return false;  
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
			}else{
				$.messager.alert('系统提示','请选择电销用户！');
			}
			
		}
		
		
		//删除
		function deleteCustomer(){
			var customerData = customer.datagrid('getChecked');
				
			
			if(customerData!=null && customerData!=""){
				 $.messager.confirm('操作提示', '确定取消分配吗？', function (data) {		
				 	if(data){
				 		$.ajax({
							type: 'post',
							url : "../member/deleteCustomerAllot.do",
							cache : false,
							data : {
								conData : JSON.stringify(customerData)
							},
							cache : false,
							async : false,
							success : function(message) {
								if (message=='success') { 	
									$.messager.alert('操作提示','操作成功');
									$('#addCustomer').dialog('close');
									 selcutomer();
								} else if(message=='0'){
									$.messager.alert('操作提示','存在未分配部门客户');
									return false; 
								} 
								else {  
									$.messager.alert('操作提示','操作失败');
									return false;  
								} 
						    },  
						     error : function(message) {
							 $.messager.alert('操作提示','操作失败');
							 return false; 
						}
						 });
				 	}
				 })
			}else{
				$.messager.alert('系统提示','请选择客户！');
			}
			
		}
		
		//转分配
		function update(){
			 type="2";
			 var customerData = customer.datagrid('getChecked');
			 if(customerData!=null && customerData!="" ){
					var str=JSON.stringify(customerData);
					var obj = eval(str);
					for (var i = 0; i < obj.length; i++)
					 {  
						 if(obj[i].deptId == 0){
					 		$.messager.alert('系统提示','存在未分配部门的客户,请取消');
					 		return false;
					 	}else if(!obj[i].username){
					 		$.messager.alert('系统提示','存在未分配的客户，请直接分配！');
					 		return false;
					 	};
					 }
				$('#addCustomer').dialog('open').dialog('setTitle', '客户分配');
	         }else{
	         		$.messager.alert('系统提示','请选择客户！');  
	         };
		};
		//冻结
		function freezeCustomer(){
			 var customerData = customer.datagrid('getChecked');
			 if(customerData!=null && customerData!="" ){
					var str=JSON.stringify(customerData);
					var obj = eval(str);
					/* for (var i = 0; i < obj.length; i++){
					 	if(obj[i].deptId == 0){
					 		$.messager.alert('系统提示','存在未分配部门的客户,请取消');
					 		return false;
					 	}else if(!obj[i].name){
					 		$.messager.alert('系统提示','存在未分配的客户');
					 		return false;
					 	};
					 } */
				  	$.messager.confirm('操作提示', '确定冻结吗？', function (data) {
					 	if(data){
					 		$.ajax({
								type: 'post',
								url : "../member/freezeCustomerByUid.do",
								cache : false,
								data : {
									conData : JSON.stringify(customerData)
								},
								cache : false,
								async : false,
								success : function(message) {
									if (message=='success') { 	
										$.messager.alert('操作提示','操作成功');
										selcutomer();
									}  
									else {  
										$.messager.alert('操作提示','操作失败');
										return false;  
									} 
							    },
							    error : function(message) {
										 $.messager.alert('操作提示','操作失败');
										 return false; 
									}
							 });
					 	}
					 });				
	         }else{
	         		$.messager.alert('系统提示','请选择客户！');  
	         }
		};
			//导出
		function exportCustomer(){
			var map = {
					/* customerName: $('#customerName').val(), */
	                userName:$('#userName').val(),
	                status: $('#status').combobox("getValue"),
	                isbank: $('#isbank').combobox("getValue"),
	                startMoney:$('#startMoney').val(),
	                endMoney:$('#endMoney').val(),
	             /* lastcall:$('#lastcall').datetimebox("getValue"), */
	                mobilePhone:$('#mobilePhone').val(),
	                regDateStart:$('#regDateStart').datetimebox("getValue"),
	                regDateEnd:$('#regDateEnd').datetimebox("getValue"),
	                opUserKy:$('#opUserKy').combobox("getValue"),
	                callNumAfter:$('#callNumAfter').val(),
	                callNumAgo:$('#callNumAgo').val(),
	                notFollowNumAfter:$('#notFollowNumAfter').val(),
	                notFollowNumAgo:$('#notFollowNumAgo').val(),
	                customerType:customerTypeAll(),
	                recommCodes:$('#recommCodes').val(), 
	                startLoginTime:$('#startLoginTime').datebox("getValue"),
		            endLoginTime:$('#endLoginTime').datebox("getValue"),
	                channelType:$('#channelType').combobox("getValue"),
	                channelName:$('#channelCode').combogrid('getText')+"",
	                deptId:$('#deptId').combobox("getValue")
			};
			window.location.href="../member/exportQueryCustomer.do?"+$.param(map);
		}
</script>
</body>
</html>

