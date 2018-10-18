<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp"%>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="customerService" style="width:100%;height:100%;overflow-y:auto">
	<div id="drMemberTable" title="用户基础信息" class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="drMemberList" class="easyui-datagrid"
			style="height:120px;width:99%"
			data-options="singleSelect:true,
		    fitColumns:true, url: '../member/qeuryMemberList.do',
		    method:'post',rownumbers:false, 
		    pagination:false,toolbar:'#drMemberTools'">
			<thead>
				<tr>
					<th data-options="field:'uid'" hidden="hidden"></th>
					<th data-options="field:'realName'" width="4%">用户姓名</th>
					<th data-options="field:'recommCodes'" width="4%">推荐码</th>
					<th data-options="field:'sex'" width="3%" formatter="formateSex">性别</th>
					<th data-options="field:'age'" width="2%">年龄</th>
					<th data-options="field:'mobilephone'" width="6%">联系方式</th>
					<th data-options="field:'bankName'" width="6%">绑卡银行</th>
					<th data-options="field:'bidAmountCount'"  styler="styleColor" width="6%">累计投资金额</th>
					<th data-options="field:'balance'"  styler="styleColor" width="6%">平台可用余额</th>
					<th data-options="field:'fuiou_balance'"  styler="styleColor" width="6%">存管可用余额</th>
					<th data-options="field:'toFrom'" width="5%">注册渠道</th>
					<th data-options="field:'toFromType'" width="4%">渠道类型</th>
					<th data-options="field:'regDate'" formatter="formatDateBoxFull" width="8%">注册时间</th>
					<th data-options="field:'isFuiou'" formatter="formatIsFuiou" width="4%">存管开通</th>
					<th data-options="field:'lastLoginTime'" formatter="formatDateBoxFull" width="8%">最后登录时间</th>
					<th data-options="field:'firstInvest'" formatter="formatDateBoxFull" width="8%">首投时间</th>
					<th data-options="field:'lastInvest'" formatter="formatDateBoxFull" width="8%">末投时间</th>
					<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper" width="15%">基本操作</th>
				</tr>
			</thead>   
		</table>
		<div id="drMemberTools" style="padding:5px;height:20px">
			<form id="incrRestForm">
				<input id="searchType" name="type" value="2" hidden="hidden" /> 
				客户电话：<input class="easyui-numberbox"  id="searchMobilephone" name="mobilephone" style="width:150px" data-options="validType:'maxLength[11]'"/>
				推荐码：<input class="easyui-textbox"  id="searchRecommCodes" name="recommCodes" style="width:150px" data-options="validType:'maxLength[6]'"/>
				<a id="searchMemberBtn" href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMemberBtn()">查询</a>
			</form>
		</div>
	</div>
	
	<div id="investTable" title="用户投资产品" class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom: 5px" data-options="collapsible:true">
		<table id="productInvestList" class="easyui-datagrid"
			style="height:250px;width:99%"
			data-options="singleSelect:true,fitColumns:true, url: '',
		    method:'post',rownumbers:true, pagination:true">
			<thead>
				<tr>
					<th data-options="field:'investTime'" formatter="formatDateBoxFull" width="10%">投资时间</th>
					<th data-options="field:'amount'"  styler="styleColor" width="8%">投资金额</th>
					<th data-options="field:'factInterest'" width="8%">预计利息</th>
					<th data-options="field:'fullName'" width="10%">产品名称</th>
					<th data-options="field:'rate'" width="5%">基础利率(%)</th>
					<th data-options="field:'activityRate'" width="5%">活动利率(%)</th>
					<th data-options="field:'specialRate'" width="5%">其它利率(%)</th>
					<th data-options="field:'deadline'" width="6%">产品周期(天)</th>
					<th data-options="field:'repayTypeName'" width="6%">还款方式</th>
					<th data-options="field:'repayType'" width="6%" formatter="getReimbursementDetail">回款明细</th>
					<th data-options="field:'productAmount'" width="10%">产品总额</th>
					<th data-options="field:'expireDate'" formatter="formatDateBoxFull" width="10%">到期日期</th>
					<th data-options="field:'fid'" width="6%" formatter="formatFavourable">使用优惠</th>
					<th data-options="field:'remark'" width="8%">投资说明</th>
					<th data-options="field:'joinTypeName'" width="10%">加入方式</th>
					<th data-options="field:'productStatus'" width="10%" formatter="iformatter" styler="istyler">状态</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="favourableTable" title="用户优惠券" class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="favourableList" class="easyui-datagrid"
			style="height:250px;width:99%"
			data-options="singleSelect:true,
		    fitColumns:true, url: '',
		    method:'post',rownumbers:true, 
		    pagination:true"> 
			<thead>
				<tr>
					<th data-options="field:'name'" width="10%">优惠券名称</th>
					<th data-options="field:'type'" formatter="formatType" width="5%">优惠券类型</th>
					<th data-options="field:'num'" styler="styleColor" formatter="formatAmount" width="6%">数值</th>
					<th data-options="field:'addtime'" formatter="formatDateBoxFull" width="10%">发放时间</th>
					<th data-options="field:'expireDate'" formatter="formatDate" width="8%">过期日期</th>
					<th data-options="field:'iSSplit'" formatter="formatISSplit" styler="styleColor" width="8%">是否可拆</th>
					<th data-options="field:'status'" formatter=formatStatus width="6%">状态</th>
					<th data-options="field:'enableAmount'" formatter="formatCondition" width="20%">使用条件</th>
					<th data-options="field:'remark'"  width="8%">优惠券详解</th>
					<th data-options="field:'userName'"  width="8%">发放人</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="friendsStatTable"  class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="beInvited" title="用户好友情况 <span style='color: #0015FF;'>推荐人手机：</span><span id='referrerMemberPhone' style='color: blue;'></span><span style='color: #0015FF;'>推荐人姓名：</span><span id='referrerMemberName' style='color: blue;'></span>" class="easyui-datagrid"
			style="height:100px;width:99%"
			data-options="singleSelect:true,
		    fitColumns:true, url: '',
		    method:'post',rownumbers:true, 
		    pagination:false">
		    <thead>
				<tr>
					<th data-options="field:'mobilephone'" width="10%">被邀请人手机</th>
					<th data-options="field:'realName'"   width="10%">被邀请人姓名</th>
					<th data-options="field:'isBackNow'"  width="10%">已返现金额</th>
					<th data-options="field:'noBackNow'"  width="10%">未返现金额</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="memberFundsLogTable" title="用户资金记录" class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="memberFundsRecordList" class="easyui-datagrid"
			style="height:250px;width:99%"
			data-options="singleSelect:true,
		    fitColumns:true, url: '',
		    method:'post',rownumbers:true, 
		    pagination:true">
			<thead>
				<tr>
					<th data-options="field:'id'" width="10%">记录号</th>
					<th data-options="field:'typeName'" width="10%">收支类别</th>
					<th data-options="field:'tradeTypeName'" width="10%">交易类别</th>
					<th data-options="field:'amount'"  styler="styleColor" width="15%">涉及金额</th>
					<th data-options="field:'balance'"  styler="styleColor" width="10%">操作后资金余额</th>
					<th data-options="field:'addTime'" formatter="formatDateBoxFull" width="15%">操作时间</th>
					<th data-options="field:'statusName'"  width="10%">操作状态</th>
				</tr>
			</thead>
		</table>
	</div>
	<div title="用户通话记录（请核对信息再保存，不允许修改与删除）" class="easyui-panel" style="width:99%;height:auto;padding:5px;margin-bottom:5px" data-options="collapsible:true">
			<table id="addWinCall" style="width:100%;height:250px" class="easyui-datagrid" striped="true" >
			</table>
	</div>
	<div id="toolbarBank" style="padding:5px;">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton"  iconCls="icon-add" plain="true"  onclick="addBank()">新增</a>
				<a href="#" class="easyui-linkbutton"  iconCls="icon-remove" plain="true"  onclick="delBank()">删除</a>
				<a href="#" class="easyui-linkbutton"  iconCls="icon-ok" plain="true"onclick="save()">保存</a>
			</div>
	</div>
	<div id="updateActivityWindow" class="easyui-window"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:false,maximizable:false"
		style="width:850px;height:400px;padding:10px;">
	</div> 
	   
</div>
  <div id="dlg" class="easyui-dialog" style="width: 600px; height: auto"  
         data-options="closed:true,buttons:'#dlg-buttons'" title="沟通内容">  
       <table id="datagrid" class="easyui-datagrid">  
                    <input class="easyui-textbox" id="remerk" name="remerk" data-options="multiline:true" style="width:599px;height:200px">	  
        </table>  
    </div>  
    <div id="dlg-buttons">  
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#dlg').dialog('close')">关闭</a>  
    </div> 
    
	<div id="showFavourableDialog" class="easyui-dialog" title="查看优惠券"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:50%;height:40%;padding:5px;">
		<table id="drFavourableList" class="easyui-datagrid" style="height:99%;width:99.9%"
			data-options="singleSelect:true,fitColumns:true,method:'post',rownumbers:true,pagination:false">
				<thead>
			    <tr>
			    	<th data-options="field:'id'" hidden="true">序号</th>
			    	<th data-options="field:'name'" width="15%">名称</th>
			        <th data-options="field:'type'" formatter="formatType" width="15%">类型</th>
			        <th data-options="field:'num'"  width="15%">数值</th>
			    </tr>
			    </thead>
			</table>
	</div>
	
	<div id="showReimbursementDetailDialog" class="easyui-dialog" title="回款明细"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false" style="width:40%;height:40%;padding:5px;">
		<table id="investRepayinfo" class="easyui-datagrid" style="height:80%;width:99.9%"
			data-options="singleSelect:true,fitColumns:true,method:'post',rownumbers:false,pagination:false">
				<thead>
			    <tr>
			    	<th data-options="field:'num'" width="20%" formatter="formatNum">名称</th>
			    	<th data-options="field:'shouldPrincipal'" width="20%">本金</th>
			        <th data-options="field:'shouldInterest'"  width="20%">利息</th>
			        <th data-options="field:'shouldTime'"  width="20%">回款时间</th>
			        <th data-options="field:'status'"  width="20%">状态</th>
			    </tr>
			    </thead>
			</table>
			<span style='color: #0015FF;'>已回款本金</span>：<span id='factPrincipal' style='color: red;'></span>
			<span style='color: #0015FF;'>已回款利息</span>：<span id='factInterest' style='color: red;'></span>
			<br/>
			<span style='color: #0015FF;'>剩余回款本金</span>：<span id='residueprincipal' style='color: red;'></span>
			<span style='color: #0015FF;'>剩余回款利息</span>：<span id='residueinterest' style='color: red;'></span>
	</div>
<script type="text/javascript">
	var recommCodes = '${recommCodes}';
	var mobilephone = '${mobilephone}';
	var type = '${type}';
	
	$(function() {
		addWinCall.datagrid({
			fitColumns : true,
			pagination : true,
			singleSelect:true,
			checkbox:true,
			pageSize:5,
			pageList:[5,10],
			autoRowHeight : false,
			url:'../membercall/selWincallLog.do',
			toolbar:"#toolbarBank",
			fit:true,
			columns : [ [ 
				{
				field : 'id',
				title : 'id',
				align : "center",
				hidden:true
				},{
				field : 'moblie',
				title : '电话号码',
				width : '8%',
				align : "center"
				},{
				field : 'calldate',
				title : '沟通时间',
				width : '12%',
				editor:{type:'datetimebox'},
				align : "center"
				},{
				field : 'name',
				title : '客户姓名',
				width : '8%',
				editor:{type:'textbox'},
				align : "center"
				},{
				field : 'remerk',
				title : '沟通内容',
				editor:{type:'textbox'},
				width : '12%',
				align : "center",
				},{
				field : 'type',
				title : '类型',
				formatter:function(value,row){
					return row.cnvalue;
				},
				editor:{
					type:'combobox',
					options:{
						valueField:'code',
						textField:'cnvalue',
						method:'post',
						url:'../membercall/seltype.do'
					}
				},
				width : '8%',
				align : "center"
				},{
				field : 'appointDate',
				title : '预约时间',
				editor:{type:'datetimebox'},
				width : '12%',
				align : "center"
				},{
				field : 'cancelDate',
				title : '取消预约时间',
				width : '12%',
				align : "center"
				},{
				field : 'cancelUserName',
				title : '取消预约人',
				width : '8%',
				align : "center"
				},{
				field : 'username',
				title : '沟通人员',
				width : '8%',
				align : "center"
				},{  
	                field: 'opt',
	                title: '操作',
	                width: 100, 
	                align: 'center',  
	                formatter: function (value, row,index) { //参数row表示当前行, 参数index表示当前行的索引值  
	  				
	                        //row.Id表示这个button按钮所在的那一行的Id这个字段的值  
	                        var str="<a href='#' class='easyui-linkbutton' onclick=\"seeRemerk(\'"+row.remerk+"')\">查看</a>"
	                        if(row.appointDate!=null && row.cancelDate==null){
	                        	var dB = new Date(row.appointDate.replace(/-/g, "/"));
	                        	if (new Date() < Date.parse(dB)) {
		                        	str=str+"  <a href='#' class='easyui-linkbutton' onclick=\"update(\'"+row.id+"')\">取消预约</a>"

	                        	} 
	                        }
	                         return str;   
	                    } 
				}
			] ]
		});
		 
		if(recommCodes!=null && recommCodes!=""){
			if(recommCodes=="该手机号未注册"){
				 $.messager.alert('操作提示',recommCodes);
			}else{
				$('#searchRecommCodes').textbox('setValue',recommCodes);
				searchMemberBtn();	
			}
		}
		if(mobilephone!=null && mobilephone!=""){
			$('#searchMobilephone').textbox('setValue',mobilephone);
			searchMemberBtn();	
		}
		
	});
	//弹出沟通内容
	 function seeRemerk(row) {  
	   		$('#remerk').textbox("setValue",row);	
	        $('#dlg').window('open');  //弹出这个dialog框  
	    }; 
	    
	//取消预约
	function update(id){
		 $.messager.confirm('操作提示', '确定取消预约吗！', function (data) {		
			 	if(data){
			 		$.ajax({
						type: 'post',
						url : "../membercall/updatewincalllog.do",
						cache : false,
						data : {
							id:id
						},
						cache : false,
						async : false,
						success : function(message) {
							if (message=='success') { 	
								$.messager.alert('操作提示','取消预约成功');
								 $('#addWinCall').datagrid({
										url:'../membercall/selWincallLog.do?moblie='+ $('#searchMobilephone').val()
									}); 
							}  
					    },  
					     error : function(message) {
						 $.messager.alert('操作提示','取消预约失败');
						 return false; 
						}
					 });
			 		}
			 	})
	}
	//查询按钮
	function searchMemberBtn(){
		var mobilephone = $('#searchMobilephone').val();
		var recommCodes = $('#searchRecommCodes').val();
		if(!(mobilephone || recommCodes )){
			$.messager.alert("提示信息","查询条件必填一个");
			return false;
		}else{
			if(mobilephone!=null && mobilephone !=''){
				var length = mobilephone.length;
				if(length!=11){
					$.messager.alert("提示信息","请输入精确手机号码");
					return false;
				}
			}
			if(recommCodes!=null && recommCodes !=''){
				var length = recommCodes.length;
				if(length!=6){
					$.messager.alert("提示信息","请输入精确推荐码");
					return false;
				}
			}
		}
		$('#drMemberList').datagrid("load",{
			mobilephone: mobilephone,
			recommCodes:recommCodes
		});
		$('#productInvestList').datagrid({
			url:'../member/productInvestList.do?mobilephone='+mobilephone+'&recommCodes='+recommCodes
		});
		
		$('#favourableList').datagrid({
			url:'../member/favourableList.do?mobilephone='+mobilephone+'&recommCodes='+recommCodes
		});
		
		$.ajax({
			type: 'post',
			url : "../member/recommendInfoList.do",
			cache : false,
			data : {
				mobilephone : mobilephone,
				recommCodes:recommCodes
			},
			success : function(message) {
				if(message.member != null && message.member != ""){
					if(message.member.mobilephone != null){
						$('#referrerMemberPhone').text(message.member.mobilephone);
					}
					if(message.member.realName != null){
						$('#referrerMemberName').text(message.member.realName);
					}
				}
		    },
		 });
		
		$('#beInvited').datagrid({
			url:'../member/beInvited.do?mobilephone='+mobilephone+'&recommCodes='+recommCodes
		});
		
		$('#memberFundsRecordList').datagrid({
			url:'../member/memberFundsRecordList.do?mobilephone='+mobilephone+'&recommCodes='+recommCodes
		});
		
		 $('#addWinCall').datagrid({
			url:'../membercall/selWincallLog.do?moblie='+mobilephone+'&recommCodes='+recommCodes
		}); 
	}
	
	function ref(){
		$('#favourableList').datagrid({
				url:'../member/favourableList.do?mobilephone='+mobilephone+'&recommCodes='+recommCodes
			});
	}
	
	function formatDate(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt = parseToDate(value);  
    return dt.format("yyyy-MM-dd");  
} 
	
	function validata(str){
		var checkboxes = document.getElementsByName(str);
		for(var i=0;i<checkboxes.length;i++){
			if(checkboxes[i].checked){
     			return true;
     		}
		}
		return false;
	}
	
	function formatIsFuiou(value,row,index){
		if(row.isFuiou==1){
			return '已开通';
		}else {
			return '未开通';
		}
		
	}
	function formateSex(value,row,index){
		if(value==1){
			return '先生';
		}else if(value==2){
			return '女士';
		}else{
			return '';
		}
		
	}
	
	function formatType(value,row,index){
		if(value==1){
			return '红包';
		}else if(value==2){
			return '加息券';
		}else if(value==3){
			return '体验金';
		}else if(value ==4){
			return '翻倍券';
		}else{
			return '';
		}
	}
	
	function formatNum(value,row,index){
		return "第"+value+"期";
	}
	
	function formatCondition(value,row,index){
		return '投资额'+value+'元以上且产品期限大于等于'+row.productDeadline+'天可用';
	}
	
	function formatISSplit(value,row,index){
		if(value == 1){
			return '是';
		}else {
			return '否';
		}
	}
	
	//添加基本操作链接
	function formatOper(val,row,index){
			if('${isdirector}'!='1') return;
	    	return '<a href="#" class="btn l-btn l-btn-small" onclick="giveOut('+index+',1)">发放红包</a>'+"  "+
	    	'<a href="#" class="btn l-btn l-btn-small" onclick="giveOut('+index+',2)">发放加息券</a>'+"  "+
	    	'<a href="#" class="btn l-btn l-btn-small" onclick="giveOut('+index+',4)">发放翻倍券</a>'+"  "+
	    	'<a href="#" class="btn l-btn l-btn-small" onclick="giveOut('+index+',3)">发放体验金</a>';  
	} 
	//跳转到发放页面
	function giveOut(index,type){
		$('#drMemberList').datagrid('selectRow',index);// 关键在这里 
	    var row = $('#drMemberList').datagrid('getSelected'); 
		$("#updateActivityWindow").window({
			title:"发放页面",
			href:"../member/giveOut.do?uid="+row.uid+"&type="+type
		});
		$("#updateActivityWindow").window("open");
	}
	
	
	
	//修改显示状态信息
	function formatStatus(value,row,index){
		if(row.status=="0"){
			return '生效中';
		}else if(row.status=="1"){
			return '使用中';
		}else{
			return '已失效';
		}
	}
	
	function formatStatus(value,rec,index){
		if(value==0){
			return '有效';
		}else if(value==1){
			return '已使用';
		}else if(value==2){
			return '已过期';
		}
	}
	
	function formatAmount(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
	}
	
	function formatFavourable(val,rec,index){
		if(val==null){
			return '';
		}else{
			return '<a href="#" onclick="showFavourable('+index+')">查看优惠</a>';
		}
	}
	
	//查看优惠券favourableList.do?mobilephone='+mobilephone
	function showFavourable(index){
		$("#showFavourableDialog").dialog("open");
		$('#productInvestList').datagrid('selectRow',index);
		var row = $('#productInvestList').datagrid('getSelected');
		$('#drFavourableList').datagrid({
		    url: "${apppath}/member/favourableList.do?fid="+row.fid
		});
	}
	
	function getReimbursementDetail(val,rec,index){
		if(val==null){
			return '';
		}else{
			return '<a href="#" onclick="showReimbursementDetail('+index+')">查看</a>';
		}
	}
	
	//查看回款明细
	function showReimbursementDetail(index){
		$("#showReimbursementDetailDialog").dialog("open");
		$('#productInvestList').datagrid('selectRow',index);
		var row = $('#productInvestList').datagrid('getSelected');
		$('#investRepayinfo').datagrid({
		    url: "${apppath}/member/getInvestRepayinfo.do?id="+row.id
		});
		
		$.ajax({
			url:"${apppath}/member/getInvestRepayinfoResidueSum.do",
			type:"POST",
			data:{
				id: row.id
			}, 
			success:function(result){
				$("#residueprincipal").text(result.residueprincipal);
				$("#residueinterest").text(result.residueinterest);
				$("#factPrincipal").text(result.factPrincipal);
				$("#factInterest").text(result.factInterest);
				}
			});
	}
	
	function formatRaisedRates(value,rec,index){
		if(value==0||value==null)
			return '0.00';
		else
			return (value + '%');
	}
	function formatRedEnvelopeType(value,row,index){
		if(row.redEnvelopeType =="0"){
			return '抵用券';
		}else if(row.redEnvelopeType=="1"){
			return '返现券';
		}else{
			return '其他';
		}
	}
	
	var addWinCall = $('#addWinCall');
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
			if (addWinCall.datagrid('validateRow', editIndex)){
				//获取沟通内容
				var ed = addWinCall.datagrid('getEditor', {index:editIndex,field:'remerk'});
				if(ed==null){
					return editIndex =undefined;
				}
				var remerk = $(ed.target).textbox('getValue');
				
			
				var ed3 = addWinCall.datagrid('getEditor', {index:editIndex,field:'calldate'});
				var calldate = $(ed3.target).textbox('getValue');
				
				var ed4 = addWinCall.datagrid('getEditor', {index:editIndex,field:'name'});
				var name = $(ed4.target).textbox('getValue');
				
				var ed5 = addWinCall.datagrid('getEditor', {index:editIndex,field:'type'});
				var type = $(ed5.target).combobox('getValue');
				if(remerk=="" ||  calldate=="" || type=="" || type==undefined || type=="0" || type=="10"){
					$.messager.alert('操作提示','请把信息填写完整','error');
					return false;
				}else{
					return true;
				}
				
			} else {
				return false;
			}
		} 
					
	//判断当前有无编辑行
	function endEditingByHj(){
	if (editIndex == undefined){return true}
		 if (addWinCall.datagrid('validateRow', editIndex)){
			 $.messager.confirm('操作提示', '请点击保存按钮')
			 return false;
		} else {
			return false;
		}
	} 
	//获取当前时间
 	function getdate(){
		var date = new Date(); 
	    var seperator1 = "-"; 
	    var seperator2 = ":"; 
	    var month = date.getMonth() + 1; 
	    var strDate = date.getDate(); 
	    if (month >= 1 && month <= 9) { 
	        month = "0" + month; 
	    } 
	    if (strDate >= 0 && strDate <= 9) { 
	        strDate = "0" + strDate; 
	    } 
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate 
	            + " " + date.getHours() + seperator2 + date.getMinutes() 
	            + seperator2 + date.getSeconds(); 
	    return currentdate; 

	} 
	function addBank(){
			var mobilephone = $('#searchMobilephone').val();
			var recommCodes = $('#searchRecommCodes').val();
			if((mobilephone==null || mobilephone=="") && (recommCodes==null || recommCodes=="")){
				$.messager.alert('操作提示', '请先查询条件！')
				return false;
			}
			if (endEditing()){
				if(!endEditingByHj()){
					return false;  
				}
			if($('#searchMobilephone').val()==null || $('#searchMobilephone').val()==""){
				addWinCall.datagrid('appendRow',{moblie:recommCodes,calldate:getdate()});
			}else{
				addWinCall.datagrid('appendRow',{moblie:mobilephone,calldate:getdate()});
			}
			editIndex = addWinCall.datagrid('getRows').length-1;
			addWinCall.datagrid('selectRow', editIndex)
			.datagrid('beginEdit', editIndex);
			} 
	} 
	
	 function save(){
			if (endEditing()){
				var ed = addWinCall.datagrid('getEditor', {index:editIndex,field:'remerk'});
				var remerk = $(ed.target).textbox('getValue');
				
				var ed3 = addWinCall.datagrid('getEditor', {index:editIndex,field:'calldate'});
				var calldate = $(ed3.target).textbox('getValue');
				
				var ed4 = addWinCall.datagrid('getEditor', {index:editIndex,field:'name'});
				var name = $(ed4.target).textbox('getValue');
				var mobilephone=null;				
				if($('#searchRecommCodes').val()!=null && $('#searchRecommCodes').val()!=""){
					mobilephone=$('#searchRecommCodes').val();
				}else{
					mobilephone=$('#searchMobilephone').val();
				}
				
				var ed5 = addWinCall.datagrid('getEditor', {index:editIndex,field:'type'});
				var type = $(ed5.target).combobox('getValue');
				
				var ed6 = addWinCall.datagrid('getEditor', {index:editIndex,field:'appointDate'});
				var appointDate=null;
				if(ed6){
					appointDate= $(ed6.target).datetimebox('getValue').replace(/(^\s*)|(\s*$)/g, '');
				}
				
			$.ajax({
					type: 'post',
					url : "../membercall/savewincalllog.do",
					cache : false,
					data : {
						remerk : remerk,
						moblie : mobilephone,
						calldate : calldate,
						name : name,
						type:type,
						appointDate:appointDate
						
					},
					cache : false,
					async : false,
					success : function(message) {
						if (message=="success") {
							 var mobilephone = $('#searchMobilephone').val();
							 var recommCodes=$('#searchRecommCodes').val();
							 $('#addWinCall').datagrid({
								url:'../membercall/selWincallLog.do?moblie='+mobilephone+'&recommCodes='+recommCodes
							}); 
							addWinCall.datagrid('endEdit', editIndex);
							editIndex = undefined;
						}  
						else {  
							
							return false;  
						} 
				    },
				 });
				
				
				}
	} 
	//这里的参数顺序似乎是不能乱的
	//用户投资产品中的状态
	  function iformatter(value,row,index){
		   if(value=="5"){
			   return '募集中';
		   }else if(value=='6'){
		   		return '待计息';
		   }else if(value=="8"){
			   return '待还款';
		   }else if(value=="9"){
			  return '已回款'; 
		   }else if(value=='6'){
		   		return '待计息';
		   }
	   } 
	//给状态改变背景色
		function istyler(value,row,index){
			if(value=="9"){
				return 'background-color:#D28EFF;';
			}
		}
		//删除
	 	function delBank(){
			var row = addWinCall.datagrid('getSelected');
			if(row != null) {
				if(row.id!="" && row.id!=undefined){
					$.messager.alert('操作提示','已保存的数据不允许删除!'); 
					return;
				}else {  
					if (editIndex == undefined){return}
					addWinCall.datagrid('cancelEdit', editIndex)
						.datagrid('deleteRow', editIndex);
					}  
			}else{
				$.messager.alert('操作提示','请选择要删除的数据!'); 
					return false;  	
				}
				editIndex = undefined;
			}
		
	 
</script>
</body>
</html>