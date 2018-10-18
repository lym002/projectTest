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
<div style="width:100%;height:100%;overflow-y:auto">
	<div title="错误列表" class="easyui-panel" style="width:99%;height:40%;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="errorList" class="easyui-datagrid" style="height:220px;width:99%"></table>
		<div id="errorListbtn" >
			<form id="selerrorList">
		  		编号:<input id="code" name="code" class="easyui-numberbox" size="15" style="width:100px"/>
			    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selerrorList()">查询</a>
		    </form>
		</div>
	</div>
	
	<div  title="回款计算器" class="easyui-panel" style="width:99%;height:40%;padding:5px;margin-bottom:5px" data-options="collapsible:true">
		<table id="calculator" class="easyui-datagrid" style="height:220px;width:99%"></table>
		<div id="calculatorbtn" >
			<form id="selcalculator">
		  		投资类型:<input name="type" type="radio" value="1" checked="checked"/>到期还本付息
					   <input name="type" type="radio" value="2" />等本等息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				还款方式:<input name="manner" type="radio" value="1"/>按月
					   <input name="manner" type="radio" value="2" />按周&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				投资金额:<input class="easyui-numberbox" data-options="min:0,precision:2" name="amount" id="amount" style="width:100px"/>元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				年化收益:<input class="easyui-numberbox" data-options="min:0,precision:1" name="rate" id="rate" style="width:100px"/>%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				产品天数:<input class="easyui-numberbox" data-options="min:0,precision:0" name="deadline" id="deadline" style="width:100px"/>天
			    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selcalculator()">立即计算</a>
		    </form>
		</div>
	</div>
	
	<div  title="备注" class="easyui-panel" style="width:99%;height:20%;padding:5px;margin-bottom:5px" data-options="collapsible:true">
			1.提现失败处理时间工作日上午9:00至下午17:00，节假日遇到问题客服记录，工作日时汇总提交给技术处理；<br/>
			2.技术对接人员——数据修改【李钢齐&朱幼林】，电销操作及缺陷【刘青】，客服短信退订解除&生产第三方对接【黄旭东&刘妮】；<br/>
			3.问题反馈需提供技术：H5\APP\PC，手机品牌及型号，手机操作系统，下载应用版本号，操作描述，用户手机号；浏览器，浏览器版本，操作描述，用户手机号；<br/>
			4.反馈内容：类型区分①缺陷②处理③建议优化		
	</div>
	
</div>
  
<script type="text/javascript">
	var errorList = $('#errorList');
	var calculator = $('#calculator');
	
	
	errorList.datagrid({
		url : "../member/getJsError.do",
		fitColumns : true,
		autoRowHeight : false,
		toolbar:"#errorListbtn",
		fit:true,
		columns : [ [ 
			{
			field : 'type',
			title : '类型',
			width : '18%',
			align : "center"
			},{
			field : 'code',
			title : '编号',
			width : '20%',
			align : "center"
			},{
			field : 'reason',
			title : '原因',
			width : '30%',
			align : "center"
			},{
			field : 'proposal',
			title : '技术建议',
			width : '30%',
			align : "center"
			}
			] ]
	});
	
	calculator.datagrid({
		url : "../member/getcalculator.do",
		fitColumns : true,
		autoRowHeight : false,
		toolbar:"#calculatorbtn",
		fit:true,
		columns : [ [ 
			{
			field : 'periods',
			title : '期数',
			width : '18%',
			align : "center"
			},{
			field : 'principal',
			title : '本金',
			width : '20%',
			align : "center"
			},{
			field : 'interest',
			title : '利息',
			width : '30%',
			align : "center"
			},{
			field : 'sum',
			title : '当期本息总和',
			width : '30%',
			align : "center"
			}
			] ]
	});
	
	//查询按钮
	function selerrorList(){
		errorList.datagrid('reload', {
	            code: $('#code').numberbox("getValue"),
	       	});
	}
	
	//立即计算
	function selcalculator(){
		var type = $("input[name='type']:checked").val();
		if(type==null || type=='' || $("#amount").numberbox('getValue')==null ||  $("#amount").numberbox('getValue')=='' || $("#rate").numberbox('getValue')==null || $("#rate").numberbox('getValue')=='' ||$("#deadline").numberbox('getValue')==null || $("#deadline").numberbox('getValue')==''){
			$.messager.alert("提示信息","请把信息填写完整");
			return false;
		}
		if(type=='2'){//等本等息
			var manner = $("input[name='manner']:checked").val();
			if(manner==null || manner=='' ){
				$.messager.alert("提示信息","请选择还款方式");
				return false;
			}
		}
		
		calculator.datagrid('reload', {
			type: type,
			manner:manner,
			amount:$("#amount").numberbox('getValue'),
			rate:$("#rate").numberbox('getValue'),
			deadline:$("#deadline").numberbox('getValue')
       	});
	}

</script>
</body>
</html>