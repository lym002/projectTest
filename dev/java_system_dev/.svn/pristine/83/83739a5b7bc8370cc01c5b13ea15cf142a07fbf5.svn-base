
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
	<table id="drProductPrizeList" title="双旦活动奖品管理" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../activityreward/queryActivityRewardList.do',
	    fit:true,
	    method:'post',rownumbers:true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	   		 <th data-options="field:'id'" hidden="hidden"></th>
	   		 <th data-options="field:'couponid'" hidden="hidden">优惠类型</th>
	    	<th data-options="field:'type'" width="10%" formatter="formaterProductPrizeStatus">奖品类型</th>
	    	<th data-options="field:'atid'" width="10%" formatter="formaterAtid">活动类型</th>
	    	<th data-options="field:'dpaName'" width="5%" >优惠类型</th>
	    	<th data-options="field:'name'"  width="5%">名字</th>
	    	<th data-options="field:'amount'"  width="5%">奖品金额</th>
	        <th data-options="field:'num'" width="8%" >数量</th>
	        <th data-options="field:'status'" width="27%" formatter="formaterStatus">状态</th>
	          <th data-options="field:'classes'" width="8%" >奖品种类</th>
	        <th data-options="field:'probability'" width="8%" >概率</th>
	        <th data-options="field:'orders'"  width="8%" >序列或等级</th>
	        <th data-options="field:'addTime'" formatter="formatDateBoxFull" width="10%">添加时间</th>
	        <th data-options="field:'updateTime'" formatter="formatDateBoxFull" width="10%">修改时间</th>
	        <th data-options="field:'remark'" width="10%">活动对应奖品编号</th>
	        <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="5%">操作</th>
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	  		名字:<input id="name" name="name" class="easyui-textbox" style="width:100px"/>
	  		奖品类型:<select id="type" name="type" class="easyui-combobox" style="width:100px">
	  					<option value=''>全部</option>
	  					<option value='1'>红包</option>
	  					<option value='2'>加息券</option>
	  					<option value='3'>体验金</option>
  				</select>
	  		活动类型:<select id="atid" name="atid" class="easyui-combobox" style="width:100px">
	  					<option value=''>全部</option>
	  					<option value='4'>积分兑换</option>
	  					<option value='5'>捕鱼活动</option>
  				</select>
	  		优惠类型:<select id="couponid" name="couponid" class="easyui-combobox" style="width:200px">
	  					<option value=''>全部</option>
						<c:forEach var="map" items="${coupon}">
							<option value='${map.couponid}'>${map.dpaName}</option>
				        </c:forEach>
  				</select>
	                   状态:<select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value=''>全部</option>
		  					<option value='0' >不可用</option>
		  					<option value='1' >可用</option>
	  				</select>
  			奖品种类：<select id="classes" name="classes" class="easyui-combobox" style="width:100px">
	  					<option value=''>全部</option>
	  					<option value='1'>虚拟货物</option>
	  					<option value='2'>实体货物</option>
  				</select>
  				</br>
  				</br>
  				<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    		<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    		<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addRewardBtn()">新增</a>
	    </form>
	</div>
	
	
	<%--新增 --%>
	<div id="addRewardDialog" class="easyui-dialog" title="新增奖品"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addTasteMoneyBtn'"
		style="width:600px;height:400px;padding:20px;">
		<form id="addRewardForm">
			<table align="center">
				<tr>
					<td>名字:</td>
					<td>
						<input id="name" name="name" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
					<td>奖品类型:</td>
					<td>
						<select id="type" name="type"  class="easyui-combobox" style="width:100px">
	  					<option value='1'  selected ="selected">红包</option>
	  					<option value='2'>加息券</option>
	  					<option value='3'>体验金</option>
  				</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>优惠类型:</td>
					<td>
						<select id="couponid" name="couponid" class="easyui-combobox" style="width:150px">
							<c:forEach var="map" items="${coupon}">
								<option value='${map.couponid}'>${map.dpaName}</option>
					        </c:forEach>
	  					</select>
					</td>
				<td>状态:</td>
					<td>
						<select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value='0' >不可用</option>
		  					<option value='1'  selected ="selected">可用</option>
	  				</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>奖品种类:</td>
					<td style="width:120px">
						<select id="classes" name="classes" class="easyui-combobox" style="width:100px">
	  					<option value='1'>虚拟货物</option>
	  					<option value='2'>实体货物</option>
  				</select>
					</td>
					<td>活动类型:</td>
					<td>
						<select id="atid" name="atid" class="easyui-combobox" style="width:100px">
		  					<option value='4'>积分兑换</option>
		  					<option value='5'>捕鱼活动</option>
  						</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>金额：</td>
					<td colspan="15">
						<input id="amount" name="amount" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>数量：</td>
					<td colspan="15">
						<input id="num" name="num" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr> 
				<tr>
					<td>概率：</td>
					<td colspan="15">
						<input id="probability" name="probability" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>序列：</td>
					<td colspan="15">
						<input id="orders" name="orders" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr> 
				<tr>
					<td>活动对应奖品编号：</td>
					<td colspan="15">
						<input id="remark" name="remark" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div id="#addRewardBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="submitActivityAdd()">添加</a>
		</div>
	</div>
	
	<%--修改页面--%>
	<div id="updateActivityWindow" class="easyui-dialog" title="修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:550px;height:300px;padding:20px;">
		<form id="updateSysProgramForm">
			<table align="center">
				<input type="text" name="id" hidden="hidden" value="3" readonly="readonly"/>
				<tr>
					<td>名字:</td>
					<td>
						<input id="name" name="name" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
					<td>奖品类型:</td>
					<td>
						<select id="type" name="type" class="easyui-combobox" style="width:100px">
	  					<option value='1'>红包</option>
	  					<option value='2'>加息券</option>
	  					<option value='3'>体验金</option>
  				</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>优惠类型:</td>
					<td>
						<select id="couponid" name="couponid" class="easyui-combobox" style="width:150px">
							<c:forEach var="map" items="${coupon}">
								<option value='${map.couponid}'>${map.dpaName}</option>
					        </c:forEach>
	  					</select>
					</td>
				<td>状态:</td>
					<td>
						<select id="status" name="status" class="easyui-combobox" style="width:100px">
		  					<option value='0' >不可用</option>
		  					<option value='1' >可用</option>
	  				</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>奖品种类:</td>
					<td style="width:120px">
						<select id="classes" name="classes" class="easyui-combobox" style="width:100px">
	  					<option value='1'>虚拟货物</option>
	  					<option value='2'>实体货物</option>
  				</select>
					</td>
					<td>活动类型:</td>
					<td>
						<select id="atid" name="atid" class="easyui-combobox" style="width:100px">
		  					<option value='4'>积分兑换</option>
		  					<option value='5'>捕鱼活动</option>
  						</select>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>金额：</td>
					<td colspan="15">
						<input id="amount" name="amount" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>数量：</td>
					<td colspan="15">
						<input id="num" name="num" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr> 
				<tr>
					<td>概率：</td>
					<td colspan="15">
						<input id="probability" name="probability" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>序列：</td>
					<td colspan="15">
						<input id="orders" name="orders" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr> 
				<tr>
					<td>活动对应奖品编号：</td>
					<td colspan="15">
						<input id="remark" name="remark" class="easyui-textbox" style="width:100px" data-options="required:true"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updatePrize()">提交</a>
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
				name:$('#name').val(),
				type:$('#type').datebox('getValue'),
				atid:$('#atid').datebox('getValue'),
				couponid:$('#couponid').datebox('getValue'),
				status:$('#status').combobox('getValue'),
				classes:$('#classes').combobox('getValue')	
			}
		});
	};
	
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">修改</a>';
		articleOper +='<a href="#" class="btn l-btn l-btn-small" onclick="deleteReward('+row.id+')">删除</a>'+' ';
		return articleOper;
	} 	
	
	//修改
	function updatePrize(){
		var validate = $("#updateSysProgramForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/activityreward/updateActivityReward.do",
            type: 'POST',
            data:$("#updateSysProgramForm").serialize(),  
            success:function(result){
			if(result.success){
				$.messager.alert("提示信息",result.msg);
				$("#drProductPrizeList").datagrid("reload");
				$("#updateActivityWindow").dialog("close");
			}else{
				$.messager.alert("提示信息",result.msg);
			}
			}
        });
		 return false;
	}

	//跳转到修改页面
	function updateActivityWindow(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$("#updateSysProgramForm").form("load",row);
		$("#updateActivityWindow").dialog("open");
	}
	
	//删除
	function deleteReward(index){
		$.ajax({
         	url: "${apppath}/activityreward/deleteActivityReward.do?id="+index,
           	type: 'POST',
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeList").datagrid("reload");
					$("#addRewardDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.msg);
				}
			}	
       	});
	}
	
	//新增页面
	function addRewardBtn(){
		$("#addRewardForm").form("reset");
		$("#addRewardDialog").dialog("open");
	}
	
	//新增
	function submitActivityAdd(){
		var validate = $("#addRewardForm").form("validate");
		if(!validate){
			return false;
		}
		
		$.ajax({
         	url: "${apppath}/activityreward/addActivityReward.do",
           	type: 'POST',
           	data:$("#addRewardForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeList").datagrid("reload");
					$("#addRewardDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.msg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}
	
	//奖品状态	
	function formaterProductPrizeStatus(value,row,index){
		if(value == 1){
			return '红包';
		}else if(value == 2){
			return '加息券';
		}else if(value == 3){
			return '体验金';
		}
	}

	function formaterStatus(value,row,index){
		if(value == 1){
			return '可用';
		}
		if(value == 0){
			return '不可用';
		}
	}
	
	function formaterAtid(value,row,index){
		if(value == 4){
			return '积分兑换';
		}else if(value == 5){
			return '捕鱼活动';
		}
	}

	
	$(document).ready(function () {
		$('#status').combobox('getValue');
		/* $('#drProductPrizeList').datagrid({ 
		    onBeforeLoad: function (d) {
			var url= "../festivaIactivity/queryFestivaIActivityList.do"; 
				$.ajax({
					url:url,
					type:'post',
					data:$("#drProductPrizeForm").serialize(), 
					success:function(data){
					}
				});
			} 
    	}); */
	});
</script>
</body>
</html>

