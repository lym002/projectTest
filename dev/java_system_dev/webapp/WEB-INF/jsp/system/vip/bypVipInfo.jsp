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
	<table id="drProductPrizeList" title="vip等级基本信息" 
	class="easyui-datagrid" style="height:99%;width:99.9%"
	data-options="singleSelect:true,
	    fitColumns:true,url: '../vipInfo/vipInfoList.do',
	    fit:true,
	    method:'post',rownumbers:true, showFooter: true,
	    pagination:true,toolbar:'#prizeManageTools',nowrap: false">
		<thead>
	    <tr>
	    	<th data-options="field:'id'" hidden="true">ID</th>
	        <th data-options="field:'vipLevel'" width="6%" >级别</th>
	        <th data-options="field:'vipName'" width="10%"  >级别头衔</th>
	        <th data-options="field:'growthSection'"  width="10%">成长值（年化）</th>
	        <th data-options="field:'levelUpdateRedPacket'" width="10%"  >红包</th>
	        <th data-options="field:'integralMultiply'" width="6%"  >优币翻倍系数</th>
	        <th data-options="field:'rightsAndInterestsId'" width="20%"  >权益</th>
	        <th data-options="field:'freeWithdrawDeposit'" width="6%"  >免费提现次数</th>
	        <th data-options="field:'createdTime'" width="10%" formatter="formatDateBoxFull">添加时间</th>
	        <th data-options="field:'updateTime'" width="10%" formatter="formatDateBoxFull">修改时间</th>
	       <th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="5%">操作</th> 
	    </tr>
	    </thead>
	</table>
	<div id="prizeManageTools" style="padding:5px;height:750">
	  	<form id="drProductPrizeForm" target="_blank" method="post">
	  		<a id="searchJsProductPrize" onclick="searchJsProductPrize()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	    	<a id="resetJsProductPrize" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addDrProductInfoDialog()">新增</a> 
	    </form>
	</div>
	<%--修改 --%>
	<div id="updateActivityWindow" class="easyui-dialog" title="修改"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:650px;height:300px;padding:20px;">
		<form id="updateSysProgramForm">
			<table id="jptable" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<input id="id" name="id" readonly="readonly" type="hidden" />
				<tr>
					<td align="left">VIP级别：<input id="'vipLevel" name="vipLevel" type="text" class="easyui-textbox" data-options="required:true"/>
					<td align="left">级别头衔：<input id="vipName" name="vipName" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">红包：
					<select id="levelUpdateRedPacket" name="levelUpdateRedPacket" class="easyui-combobox" style="width:150px">
							<option value=''>请选择</option>
							<c:forEach var="map" items="${hb}">
								<option value='${map.hbCode}'>${map.hbName}</option>
					        </c:forEach>
	  					</select>
					<td align="left">最小成长值：<input id="growthValueMin" name="growthValueMin" type="text" class="easyui-numberbox" />
					</td>
				</tr>
				<tr>
					<td align="left">最大成长值：<input id="growthValueMax" name="growthValueMax" type="text" class="easyui-numberbox" />
					<td align="left">优币翻倍系数：<input id="integralMultiply" name="integralMultiply" type="text" class="easyui-textbox" />
				</tr>
				<tr>
					<td align="left">权益：
					<select id="rightsAndInterestsId" name="rightsAndInterestsId" class="easyui-combobox" style="width:150px" data-options="multiple:true">
							<option value=''>请选择</option>
							<c:forEach var="map" items="${qy}">
								<option value='${map.id}'>${map.equitiesName}</option>
					        </c:forEach>
	  					</select>
					<td align="left">免费提现次数：<input id="freeWithdrawDeposit" name="freeWithdrawDeposit" type="text" class="easyui-numberbox" />
				</tr>
				
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="updatePrize()">修改</a>
		</div>
	</div>
	
	<div id="addDrProductInfoDialog" class="easyui-dialog" title="新增"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addDrProductInfoBtn'"
		style="width:650px;height:300px;padding:20px;">
		<form id="addJsProductPrizeForm">
			<table id="productPrize" align="center" cellspacing="1" cellpadding="1" style="width:auto;">
				<tr>
					<td align="left">VIP级别：<input id="'vipLevel" name="vipLevel" type="text" class="easyui-textbox" data-options="required:true"/>
					<td align="left">级别头衔：<input id="vipName" name="vipName" type="text" class="easyui-textbox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td align="left">红包：
					<select id="levelUpdateRedPacket" name="levelUpdateRedPacket" class="easyui-combobox" style="width:150px">
							<option value=''>请选择</option>
							<c:forEach var="map" items="${hb}">
								<option value='${map.hbCode}'>${map.hbName}</option>
					        </c:forEach>
	  					</select>
					<td align="left">最小成长值：<input id="growthValueMin" name="growthValueMin" type="text" class="easyui-numberbox" />
					</td>
				</tr>
				<tr>
					<td align="left">最大成长值：<input id="growthValueMax" name="growthValueMax" type="text" class="easyui-numberbox" />
					<td align="left">优币翻倍系数：<input id="integralMultiply" name="integralMultiply" type="text" class="easyui-textbox" />
				</tr>
				<tr>
					<td align="left">权益：
					<select id="rightsAndInterestsId" name="rightsAndInterestsId" class="easyui-combobox" style="width:150px" data-options="multiple:true">
							<option value=''>请选择</option>
							<c:forEach var="map" items="${qy}">
								<option value='${map.id}'>${map.equitiesName}</option>
					        </c:forEach>
	  					</select>
					<td align="left">免费提现次数：<input id="freeWithdrawDeposit" name="freeWithdrawDeposit" type="text" class="easyui-numberbox" />
				</tr>
			</table>
		</form>
		<div id="#addRedEnvelopeBtn" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addRedEnvelope()">添加</a>
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
			
		});
	};
	

	//跳转到修改页面
	function updateActivityWindow(index){
		$('#drProductPrizeList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drProductPrizeList').datagrid('getSelected'); 
		$("#updateSysProgramForm").form("load",row);
		$("#updateActivityWindow").dialog("open");
	}

	//跳转到添加页面
	function addDrProductInfoDialog(){  
		$("#addJsProductPrizeForm").form("reset");
		$("#addDrProductInfoDialog").dialog("open");
		$("#rightsAndInterestsId").combobox("clear");
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){ 
		var articleOper = '<a href="#" class="btn l-btn l-btn-small" onclick="updateActivityWindow('+index+')">编辑</a>    ';
		return articleOper;
	} 	

	
	function updatePrize(){
		var validate = $("#updateSysProgramForm").form("validate");
		if(!validate){
			return false;
		}
		$.ajax({
          	url: "${apppath}/vipInfo/updateVipInfo.do",
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

	
	function addRedEnvelope(){
		var validate = $("#addJsProductPrizeForm").form("validate");
		if(!validate){
			return false;
		}
	
		$.ajax({
         	url: "${apppath}/vipInfo/addVipinfo.do",
           	type: 'POST',
           	data:$("#addJsProductPrizeForm").serialize(),  
           	success:function(result){
				if(result.success){
					$.messager.alert("提示信息",result.msg);
					$("#drProductPrizeList").datagrid("reload");
					$("#addDrProductInfoDialog").dialog("close");
				}else{
					$.messager.alert("提示信息",result.msg);
				}
			}	
       	});
        return false; // 阻止表单自动提交事件
	}
	
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

