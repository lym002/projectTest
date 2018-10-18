<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="${apppath}/js/common/jquery.form.js"></script>
<style>
	#selcutomer{
		margin:0;
	}
	#selcutomer .l-btn-icon{
		display: inline-block;
	    width: 16px;
	    height: 16px;
	    line-height: 16px;
	    position: absolute;
	    top: 50%;
	    margin-top: -8px;
	    font-size: 1px;
	}
	#selcutomer .l-btn-icon.icon-search{
	background: url('/js/easyUI/themes/icons/search.png') no-repeat center center;
	}
	#selcutomer .l-btn-icon.icon-reload{
	background: url('/js/easyUI/themes/icons/reload.png') no-repeat center center;}
	#ztth_load{ padding-bottom:20px;}
</style>
</head>
<body>
	<div id="customerbtn" style="padding:5px;">
		<form id="selcutomer">
	  		<!-- 客户姓名:<input id="customerName" name="customerName" class="easyui-textbox"  size="15" style="width:100px"/> -->
	  		推荐码:<input id="recommCodes" name="recommCodes" class="easyui-textbox"  size="15" style="width:100px"/>
	  		手机号:<input id="mobilePhone" name="mobilePhone" class="easyui-numberbox"  size="15" style="width:100px"/>
	  		客户状态:<select name="status" style="width: 172px" class="easyui-combobox" id="status">
	  			<option value="">全部</option>	
				<option value="01111">首投(新手标)</option>	
				<option value="10111">首投(非新手标)</option>	
				<option value="00001">绑卡未充值</option>
				<option value="00011">充值未投资</option>		
				<option value="00000">注册未绑卡</option>					
			</select>			
			电销部门:<select name="deptId" style="width: 150px" class="easyui-combobox" id="deptId">
	  				<c:choose>
					<c:when  test="${roleName eq '系统管理员' || roleName eq '电销总监' }">
						<option value="" selected="selected">全部</option>
			  			<option value="0" >未分配</option>
			  			<option value="1" >电销一部</option>
			  			<option value="2" >电销二部</option>	  			
					</c:when>
					<c:otherwise>
						<c:if test="${roleName eq '电销经理' || roleName eq '电销主管'  }">
							<option value="" selected="selected">全部</option>	
						</c:if>
						<c:if test="${deptId eq 1}">
				  			<option value="1" >电销一部</option>			  		  			
						</c:if>				
						<c:if test="${deptId eq 2}">
				  			<option value="2" >电销二部</option>			  		  			
						</c:if>
					</c:otherwise>				
				</c:choose>
			</select>
			可用余额:<input id="balanceFirst" name="balanceFirst" class="easyui-numberbox" style="width:140px"/>
	    	至<input id="balanceEnd" name="balanceEnd" class="easyui-numberbox" style="width:140px"/></br>
			 <!-- 客户类型:<input id="type" class="easyui-combobox" 
								data-options="valueField:'code',textField:'cnvalue',url:'../membercall/seltype.do',"/>  -->    
			渠道名称:<input id="channelName" name="channelName" class="easyui-textbox"  size="15" style="width:100px"/>
			渠道类型:<select name="channelType" style="width: 172px" class="easyui-combobox" id="channelType">
	  			<option value="">全部</option>	
				<option value=1>CPS</option>	
				<option value=0>非CPS</option>	
			</select>
			投资笔数:
			<input id="investCountFirst" name="investCountFirst" class="easyui-numberbox" style="width:140px"/>
	    	至<input id="investCountEnd" name="investCountEnd" class="easyui-numberbox" style="width:140px"/>
	    	沟通次数:<input id="callNumAgo" name="callNumAgo"   class="easyui-numberbox" style="width:100px"/>
	  		-<input id="callNumAfter" name="callNumAfter"  class="easyui-numberbox" style="width:100px"/></br>
	  		 最近的登录时间:<input class="easyui-datebox" id="startLoginTime" style="width:100px"/>至<input class="easyui-datebox" id="endLoginTime" style="width:100px"/> 
			投资时间:<input class="easyui-datebox" id="startTime" style="width:100px"/>至<input class="easyui-datebox" id="endTime" style="width:100px"/>
			回款时间:<input class="easyui-datebox" id="startshouldTime" style="width:100px"/>至<input class="easyui-datebox" id="endshouldTime" style="width:100px"/>
	    	预约时间:<input id="startAppointDate" name="startAppointDate" class="easyui-datetimebox" style="width:140px"/>
	    	至<input id="endAppointDate" name="endAppointDate" class="easyui-datetimebox" style="width:140px"/> </br>
	    	<!-- 最近通话时间:<input id="startCallDate" name="startCallDate" class="easyui-datetimebox" style="width:140px"/> -->
	    	 *天未跟进:<input id="notFollowNumAgo" name="notFollowNumAgo"   class="easyui-numberbox" style="width:100px"/>
	  		 -<input id="notFollowNumAfter" name="notFollowNumAfter"  class="easyui-numberbox" style="width:100px"/> 
	    <!-- 	至<input id="endCallDate" name="endCallDate" class="easyui-datetimebox" style="width:140px"/> -->
	    	注册时间:<input id="startRegDate" name="startRegDate" class="easyui-datebox" style="width:140px"/>
	    	至<input id="endRegDate" name="endRegDate" class="easyui-datebox" style="width:140px"/>
	    	投资总额:<input id="investAmountSumFirst" name="investAmountSumFirst" class="easyui-numberbox" style="width:140px"/>
	    	至<input id="investAmountSumEnd" name="investAmountSumEnd" class="easyui-numberbox" style="width:140px"/>
	    	</br>
	    	<c:forEach var="map" items="${wincallType}">
				<input type="checkbox" name="customerType" value="${map.key}" id="${map.key}" />${map.value}
		    </c:forEach>
		    </br>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selcutomer()">查询</a>
	    	<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetcustomer()">重置</a>
	    	
	    </form>
	</div>
	<div style="height:100%;width:100%;box-sizing:border-box; padding-top:90px;position: relative;">
		<div style="height:90px;width:100%;position: absolute;top:0;left:0;">
			<div id="ztth_load"></div>
		</div>
		<table id="customer"  style="height:99%;width:99.9%"></table>
	</div>
<script type="text/javascript">
var ag_num_value ='${num}'; //动态设置变量ag_num_value(坐席工号);
var ag_phone_value ='${phone}'; //动态设置变量ag_phone_value(分机号码)
	    
	
	    $('#ztth_load').load('../phoneSevice/index.html');
	    
		//客户列表
		var customer = $('#customer');
		$(function() {
			customer.datagrid({
				url : "../member/selCustomerManagement.do",
				title : '会员列表',
				fitColumns : true,
				pagination : true,
				checkbox:true,
				pageSize:25,
				pageList:[25,50],
				autoRowHeight : true,
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
					width : '5%',
					align : "center"	
					},{
					field : 'mobilePhone',
					title : '客户手机号',
					width : '6%',
					align : "center",
					formatter:function(value,row,index){
						return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.uid+"')\">"+value+"</a>";	
                        }
					},{
						field : '  ',
						title : '操作',
						width : '10%',
						align : "center",
						width : '10%',
						formatter:function(value,row,index){
							return "<a href='#' class='easyui-linkbutton' onclick=\"call(\'"+row.uid+"')\">拨打</a>  <a href='#' class='easyui-linkbutton' onclick=\"addcall(\'"+row.uid+"')\">加0拨打</a>";	
	                        },
						},{
					field : 'recommCodes',
					title : '推荐码',
					width : '5%',
					align : "center"
					},{
					field : 'isRecharge',
					title : '是否充值',
					width : '4%',
					align : "center"
					},{
					field : 'channelName',
					title : '渠道名称',
					width : '4%',
					align : "center"
					},{
					field : 'channelType',
					title : '渠道类型',
					width : '4%',
					align : "center"
					},{
					field : 'investAmountSum',
					title : '投资总额',
					width : '5%',
					align : "center"
					},{
					field : 'investCount',
					title : '投资次数',
					width : '4%',
					align : "center"
					},{
					field : 'meets',
					title : '沟通次数',
					width : '4%',
					align : "center" <%--新增沟通次数字段 --%>
					},{
					field : 'regdate',
					title : '注册时间',
					width : '8%',
					align : "center"
					},{
					field : 'investTime',
					title : '最近投资时间',
					width : '8%',
					align : "center"
					},{
					field : 'lastPaymentTime',
					title : '最近回款时间',
					width : '8%',
					align : "center"
					},{
					field : 'lastlogintime',
					title : '最近登陆时间',
					width : '8%',
					align : "center"
					},{
					field : 'lastCallTime',
					title : '最近通话时间',
					width : '8%',
					align : "center"
					},{
					field : 'notFollowNum',
					title : '*天未跟进',
					disabled:true,
					width : '6%',
					align : "center"
					},{
					field : 'appointDate',
					title : '预约下次通话时间',
					width : '8%',
					align : "center"
					},{
					field : 'balance',
					title : '账户余额',
					width : '4%',
					align : "center"
					},{
					field : 'fuiouBalance',
					title : '存管账户余额',
					width : '4%',
					align : "center"
					},{
					field : 'isFuiou',
					title : '是否开通存管',
					width : '4%',
					align : "center"
					},{
					field : 'customerType',
					title : '客户类型',
					width : '6%',
					align : "center",
					formatter:function(value,row,index){
						if(value==null || value==""){
							return "无通话记录";
						}else{
							return value;
                        }
					}
					},{
					field : 'username',
					title : '所属人',
					width : '6%',
					align : "center"
					},{
					field : 'deptId',
					title : '部门',
					width : '5%',
					align : "center",
					formatter:function(value,row,index){
							if(value==1){
								return "电销一部";
							}else if(value==2){
								return "电销二部";
	                        }else if(value==100){
	                        	return "冻结";
	                        }else if(value==0){
	                        	return "未分配";
	                        }
						}
					} /* ,{
					field : 'createDate',
					title : '操作时间',
					width : '10%',
					align : "center"
					} */
				] ]
			});
		}); 
		
		function selectData(uid){
			$.ajax({
				type: 'post',
				url : "../member/selectByPrimaryKey.do?uid="+uid,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
					window.open("../member/toCustomerService.do?caller="+str);
			    },
			 });
		}
		//查询按钮
		function selcutomer(){
	 		$('#customer').datagrid({
				queryParams: {
					mobilePhone:$('#mobilePhone').val(),
					/* customerName:$('#customerName').val(), */
					recommCodes:$('#recommCodes').val(), 
					status:$('#status').combobox('getValue'),
					startTime: $('#startTime').datebox("getValue"),
		            endTime: $('#endTime').datebox("getValue"),
		            startAppointDate:$('#startAppointDate').datetimebox("getValue"),  
	                endAppointDate:$('#endAppointDate').datetimebox("getValue"),
	              /*   startCallDate:$('#startCallDate').datetimebox("getValue"),
	                endCallDate:$('#endCallDate').datetimebox("getValue"), */
	                startRegDate:$('#startRegDate').datebox("getValue"),
	                endRegDate:$('#endRegDate').datebox("getValue"),
		            startshouldTime: $('#startshouldTime').datebox("getValue"),
		            endshouldTime: $('#endshouldTime').datebox("getValue"),
		            investAmountSumFirst: $('#investAmountSumFirst').numberbox("getValue"),
		            investAmountSumEnd: $('#investAmountSumEnd').numberbox("getValue"),
		            balanceFirst: $('#balanceFirst').numberbox("getValue"),
		            balanceEnd: $('#balanceEnd').numberbox("getValue"),
		            channelType:$('#channelType').combobox('getValue'),
					investCountFirst:$('#investCountFirst').numberbox("getValue"),
					investCountEnd:$('#investCountEnd').numberbox("getValue"),
					channelName:$('#channelName').val(),
					callNumAfter:$('#callNumAfter').val(),
		            callNumAgo:$('#callNumAgo').val(),
		            notFollowNumAfter:$('#notFollowNumAfter').val(),
	                notFollowNumAgo:$('#notFollowNumAgo').val(),
		            customerType:customerTypeAll(),
		            startLoginTime:$('#startLoginTime').datebox("getValue"),
		            endLoginTime:$('#endLoginTime').datebox("getValue"),
					deptId:$('#deptId').combobox("getValue"),
					}
			})
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
			$("#customer").datagrid("load", {});
		};
		
		function call(uid){
			var phone=null;
			$.ajax({
				type: 'post',
				url : "../member/selectByUid.do?uid="+uid,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
					phone=str;
			    },
			 });
			dial(1,'',phone);
		}
		
		function addcall(uid){
			var phone=null;
			$.ajax({
				type: 'post',
				url : "../member/selectByUid.do?uid="+uid,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
					phone="0"+str;
			    },
			 });
			dial(0, '',phone);
		}
		
</script>
</body>
</html>

