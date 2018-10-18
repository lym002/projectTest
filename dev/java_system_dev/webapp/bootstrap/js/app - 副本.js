/*初始化电话按钮*/

function initPhone()
{

	//签入签出
	$('#login').click(function(){
		if(trim($('#login').text()) == '签入')
		{
			//alert(ip);
			//var ip			= ip;
			//var agentID		= agentID;
			//var agentNum	= agentNum;
			//var vccID		= vccID;
			//var agentName 	= agentName;
			//var agentPhoneID = agentPhoneID;
			//var agentPhone	= agentPhone;
			//var agentPhoneType	= agentPhoneType;
			//var queueID		= queueID;
			//var queueState	= queueState;
			$('#login').attr('class', 'btn btn-default');
			$('#login').attr('disabled', true);
			//wincall.fn_login(ip,vccID,agentID,agentNum,agentName,agentPhoneID,agentPhone,agentPhoneType,queueID,queueState);(ip,vccCode,agentNum,agentPass,agentphone,queState,olMode){
			wincall.fn_login(ip,vcc_code,agentNum,agentPass,agentPhone,queueState,0);
		}
		else if(trim($('#login').text()) == '签出')
		{
			$(this).attr('class', 'btn disabled');
			$(this).attr('disabled', 'disabled');
			wincall.fn_logout();
		}
	});
	//		置闲
	$('#unbusy').click(function (){
		if(trim($('#unbusy').text()) == '置闲')
		{
			btn_state_change('unbusy',BUTTON_ON);
			btn_state_change('busy',BUTTON_OFF);
			wincall.fn_unbusy();
		}
	});
	// 置忙
	$('#busy').click(function (){
		if(trim($('#busy').text()) == '置忙')
		{
			btn_state_change('unbusy',BUTTON_OFF);
			btn_state_change('busy',BUTTON_ON);
			wincall.fn_busy();
		}
	});

	//保持 恢复
	$('#hold').click(function (){
		if(trim($('#hold').text()) == '保持')
		{
			wincall.fn_hold();
		}
		else if(trim($('#hold').text()) == '恢复')
		{
			wincall.fn_unhold();
		}
	});

	//内呼 弹框；
	$('#callinner').click(function (){
		callinner();
		/*
		$('#dail_panel').window({
			href:"phone_control.php?act=callinner",
			width:450,
			height:365,
			title:"内呼",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizable:false,
			cache:false
		});*/
	});

	//外呼
	$('#callouter').click(function (){
		dialouter_panel('',1);
	});

	//挂断
	$('#hangup').click(function (){
		btn_state_change('hangup',BUTTON_OFF);
		wincall.fn_hangup();
	});
	//评价
	$('#evaluation').click(function (){
		btn_state_change('evaluation',BUTTON_OFF);
		wincall.fn_evaluate();
	});

	//咨询坐席
	$('#consultinner').click(function (){
		callinner(0,1); //和内呼一样
		/*
		$('#dail_panel').window({
			href:"phone_control.php?act=consultinner",
			width:450,
			height:350,
			title:"咨询坐席",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizable:false,
			cache:false
		});*/
	});

	//咨询外线
	$('#consultouter').click(function (){
		jsonp_consultouter();
		/*
		$('#dail_panel').window({
			href:"phone_control.php?act=consultouter",
			width:256,
			height:170,
			title:"咨询外线",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizable:false,
			cache:false
		});*/
	});

	//咨询接回
	$('#consultback').click(function (){
		wincall.fn_consultback();
	});

	//转接
	$('#transfer').click(function (){
		wincall.fn_transfer();
	});

	//三方
	$('#threeway').click(function (){
		wincall.fn_3way();
	});
	//三方接回
	$('#threewayback').click(function (){
		wincall.fn_3wayback();
	});

	//监听
	$('#chanspy').click(function (){
		jsonp_chanspy();
		/*
		$('#dail_panel').window({
			href:"phone_control.php?act=chanspy",
			width:450,
			height:350,
			title:"通话中的坐席",
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizable:false,
			cache:false
		});*/
	});
	//拦截
	$('#intercept').click(function (){
		wincall.fn_intercept();
	});
	//强插
	$('#breakin').click(function (){
		wincall.fn_breakin();
	});
}
//按钮状态修改 1启用，2禁用 3激活
function btn_state_change(name,type)
{
	var img = name;
	if(type == 1)
	{
		$('#'+name).attr('class','btn btn-info');
		$('#'+name).attr('disabled', false);
	}
	else if(type == 2)
	{
		$('#'+name).attr('class','btn disabled');
		$('#'+name).attr('disabled', true);
	}
	else if(type == 3)
	{
		$('#'+name).attr('class','btn btn-info');
	}
}

//调出外呼呼叫页面 type:1：工具栏外呼 type=''业务受理外呼
function dialouter_panel(num,type)
{
	/*
	if(type != 1)
	{
		var num=$.trim(num);

		if(num.length==0){
			$.messager.alert('错误','您呼叫的号码为空或不正确！','error');
			return;
		}
	}*/
	//外呼查询
	$.ajax({
		 type: "GET",
		 url: url+'/jsonp/callout',
		 dataType:"jsonp",
		 async: false,
		 data: "vcc_code=" + vcc_code + "&ag_id=" + agentID,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				data = data.data;
				var que_html = "<select id='callouter_queid' class='form-control'>";
				var select_phone = "<select id='callouter_caller' class='form-control'>";
				for (item in data.que) {
					que_html+="<option value='"+data.que[item].id+"'>"+data.que[item].que_name+"</option>";
				}
				for (item in data.num) {
					select_phone+="<option value='"+data.num[item]+"'>"+data.num[item]+"</option>";
				}
				select_phone +="</select>";
				que_html +="</select>";
				$('#callouter_num').val(data.number);
				$('#out_que_num').html(select_phone);
				$('#out_que_list').html(que_html);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callout_content').html();
	$('#modal_content').html(content);
	$('#myModalLabel').html('外呼 (非0316地区，手机请加拨0)');
	$('#myModal').modal('show');
	/*
	$('#dail_panel').window({
		href:"phone_control.php?act=callouter&number="+num+"&type="+type,
		width:380,
		height:180,
		title:"外呼 (非0316地区，手机请加拨0)",
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:false,
		cache:false
	});*/
}
//用红字显示

function showimport(content)
{
	clearTimeout(error_timeout);
	$('#phone_error').attr('innerHTML',content);
	$('#phone_error').css('display','');
	error_timeout = setTimeout(function ()	{
		$('#phone_error').css('display','none');
	},3000);
}

var callclock = null;
var time_tmp = 0;
var minute;
var second;

//开始通话计时
function startclock()
{
	stopclock();
	callclock = setInterval(function (){
		time_tmp++;
		second = time_tmp%60;
		minute = Math.floor(time_tmp/60);
		if(minute < 10){minute = "0"+minute;}
		if(second < 10){second = "0"+second;}
		$('#seat_state').text(minute+':'+second);
	},1000);
}

//停止通话计时
function stopclock()
{
	if(callclock)
	{
		clearInterval(callclock);
		time_tmp = 0;
	}
}

//程序消息返回
function callProcess(responce){
alert(responce);
	if(responce=="" || !responce)	return;

	switch(responce)
	{
		case '签入成功' :
		callSeatState('签入');
		if(queueState == 1)
		{
			callQueueState('空闲');
			btn_state_change('busy',BUTTON_ON);
		}
		else
		{
			callQueueState('忙碌');
			btn_state_change('unbusy',BUTTON_ON);
		}
		$('#login_value').text('签出');
		btn_state_change('login',BUTTON_ON);
		btn_state_change('callinner',BUTTON_ON);
		btn_state_change('callouter',BUTTON_ON);
		btn_state_change('chanspy',BUTTON_ON);
		showimport(responce);
		break;
		case '签出成功' :
		callSeatState('未签入');
		callQueueState('未签入');
		stopclock();
		$('#login_value').text('签入');
		$("#toolbar a").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		btn_state_change('login',BUTTON_ON);
		break;
		case '置闲成功' :
		case '系统置闲':
		callQueueState('空闲');
		btn_state_change('busy',BUTTON_ON);
		btn_state_change('unbusy',BUTTON_OFF);
		btn_state_change('chanspy',BUTTON_ON);
		btn_state_change('callinner',BUTTON_ON);
		btn_state_change('callouter',BUTTON_ON);
		break;
		case '置忙成功' :
		case '系统置忙' :
		callQueueState('忙碌');
		btn_state_change('busy',BUTTON_OFF);
		btn_state_change('unbusy',BUTTON_ON);
		btn_state_change('chanspy',BUTTON_ON);
		//stopclock();
		break;
		case '保持成功' :
		$('#hold_value').text('恢复');
		btn_state_change('hold',BUTTON_ON);
		break;
		case '恢复成功':
		$('#hold_value').text('保持');
		btn_state_change('hold',BUTTON_ON);
		break;
		case '呼叫中' :
		callSeatState('呼叫中');
		var _called = wincall.fn_getParam('Called');
        var callid = wincall.fn_getParam('CallId');
		if (outPage) {
			window.open(outPage);
		}
		btn_state_change('hangup',BUTTON_ON);
		break;
		case '咨询外呼中' :
		btn_state_change('consultback',BUTTON_ON);
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('consultinner',BUTTON_OFF);
		btn_state_change('consultouter',BUTTON_OFF);
		showimport(responce);
		break;
		case '来电' :
		callSeatState('来电');
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('evaluation',BUTTON_ON);
		var _caller = wincall.fn_getParam('Caller');
		$('#myModalLabel').text('新的来电');
		$('#btn_hidden').text('拒接');
		$('#myModal').modal('show');
		$('#myModal').on('hidden.bs.modal', function (e) {
			wincall.fn_hangup();
		})
		/* 弹框处理
		$('#incoming_phone').html(_caller);
		$('#incoming_dialog').dialog({
			title:"新的来电",
			width:300,
			height:150,
			buttons:[
			{
				iconCls:'icon-hangup',
				text:'拒接',
				handler:function(){$('#incoming_dialog').dialog('close');wincall.fn_hangup();}
			}
			]
		});*/
		break;
		case '队列来电':
		callSeatState('队列来电');
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('evaluation',BUTTON_ON);
		var _caller = wincall.fn_getParam('Caller');
		var _servnum = wincall.fn_getParam('ServNum');
        var callid = wincall.fn_getParam('CallId');
		var  phone400='';
		for(i in cc_phone){
			if(_servnum == i){
			     phone400 = cc_phone[i];
			}
		}
		
		if (inpop_page) {
			window.open(inpop_page);
		}
		$('#myModalLabel').text('新的来电');
		$('#btn_hidden').text('拒接');
		$('#myModal').on('hidden.bs.modal', function (e) {
			wincall.fn_hangup();
		})
		//发送jsonp
		$.ajax({
		 type: "GET",
		 url: url+'/jsonp/nbattribute',
		 dataType:"jsonp",
		 async: false,
		 data: "num=" + _caller,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				$('#modal_content').html(data.data.city); //写入归属地；
			} else {
				alert(data.message);
			}
		 }
		});
		$('#myModal').modal('show');
		/* 弹框处理；
		$('#incoming_phone').html(_caller);
		$('#incoming_dialog').dialog({
			title:"新的来电",
			width:300,
			height:150,
			buttons:[
			{
				iconCls:'icon-hangup',
				text:'拒接',
				handler:function(){$('#incoming_dialog').dialog('close');wincall.fn_hangup();}
			}
			]
		});
		$.ajax({
			url:      "phone_control.php?act=nbattribute",
			type:     "post",
			data:     {'num':_caller},
			dataType: "json",
			success: function(responce){
				if(responce["error"] == 0)
				{
					$('#incoming_address').html(responce["content"]);
				}
			}
		});*/
		break;
		case '外呼来电':
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('evaluation',BUTTON_ON);
		callSeatState('外呼来电');
		break;
		case '监听来电':
		btn_state_change('hangup',BUTTON_ON);
		callSeatState('监听来电');
		break;
		case '咨询来电':
		btn_state_change('hangup',BUTTON_ON);
		callSeatState('咨询来电');
		var _caller = wincall.fn_getParam('Caller');
		$('#myModalLabel').text('来电咨询');
		$('#btn_hidden').text('拒接');
		$('#myModal').on('hidden.bs.modal', function (e) {
			wincall.fn_hangup();
		})
		$('#myModal').modal('show');
		/* 弹框处理
		$('#incoming_phone').html(_caller+" 来电咨询");
		$('#incoming_dialog').dialog({
			title:"咨询来电",
			width:300,
			height:150,
			buttons:[
			{
				iconCls:'icon-hangup',
				text:'拒接',
				handler:function(){$('#incoming_dialog').dialog('close');wincall.fn_hangup();}
			}
			]
		});*/
		break;
		case '通话中'   :
		callSeatState('通话中');
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('consultinner',BUTTON_ON);
		btn_state_change('consultouter',BUTTON_ON);
		btn_state_change('hold',BUTTON_ON);
		startclock();
		try
		{
			$('#myModal').modal('hide');
			//$('#incoming_dialog').dialog('close');
		}catch(e){}
		break;
		case '咨询接通'   :
		btn_state_change('threeway',BUTTON_ON);
		btn_state_change('transfer',BUTTON_ON);
		btn_state_change('consultinner',BUTTON_OFF);
		btn_state_change('consultouter',BUTTON_OFF);
		showimport(responce);
		break;
		case '咨询接回':
		case '咨询未呼通' :
		btn_state_change('consultback',BUTTON_OFF);
		btn_state_change('transfer',BUTTON_OFF);
		btn_state_change('threeway',BUTTON_OFF);
		btn_state_change('consultinner',BUTTON_ON);
		btn_state_change('consultouter',BUTTON_ON);
		showimport(responce);
		break;
		case '三方中':
		btn_state_change('threewayback',BUTTON_ON);
		btn_state_change('consultback',BUTTON_OFF);
		btn_state_change('transfer',BUTTON_OFF);
		btn_state_change('threeway',BUTTON_OFF);
		showimport(responce);
		break;
		case '三方接回':
		case '三方接回成功':
		btn_state_change('threewayback',BUTTON_OFF);
		btn_state_change('consultinner',BUTTON_ON);
		btn_state_change('consultouter',BUTTON_ON);
		showimport(responce);
		break;
		case '监听中':
		btn_state_change('chanspy',BUTTON_OFF);
		btn_state_change('breakin',BUTTON_ON);
		btn_state_change('intercept',BUTTON_ON);
		startclock();
		showimport(responce);
		break;
		case '拦截中':
		btn_state_change('evaluation',BUTTON_ON);
		btn_state_change('breakin',BUTTON_OFF);
		btn_state_change('intercept',BUTTON_OFF);
		btn_state_change('hangup',BUTTON_ON);
		btn_state_change('consultinner',BUTTON_ON);
		btn_state_change('consultouter',BUTTON_ON);
		btn_state_change('hold',BUTTON_ON);
		showimport(responce);
		break;
		case '强插中':
		btn_state_change('breakin',BUTTON_OFF);
		showimport(responce);
		break;
		case '被转接':
		btn_state_change('evaluation',BUTTON_ON);
		break;
		case '通话完成' :
		callSeatState('通话完成');
		$('#hold_value').text('保持');
		btn_state_change('hangup',BUTTON_OFF);
		btn_state_change('evaluation',BUTTON_OFF);
		btn_state_change('transfer',BUTTON_OFF);
		btn_state_change('hold',BUTTON_OFF);
		btn_state_change('consultinner',BUTTON_OFF);
		btn_state_change('consultouter',BUTTON_OFF);
		btn_state_change('consultback',BUTTON_OFF);
		btn_state_change('threeway',BUTTON_OFF);
		btn_state_change('threewayback',BUTTON_OFF);
		btn_state_change('chanspy',BUTTON_OFF);
		btn_state_change('breakin',BUTTON_OFF);
		btn_state_change('intercept',BUTTON_OFF);
		btn_state_change('callinner',BUTTON_ON);
		btn_state_change('callouter',BUTTON_ON);
		stopclock();
		try
		{
			$('#myModal').dialog('hide');
		}catch(e){}
		break;
		case '转评价成功':
		btn_state_change('evaluation',BUTTON_OFF);
		callSeatState('转评价成功');
		break;
		case '事后处理' :
		callQueueState('事后处理');
		btn_state_change('busy',BUTTON_ON);
		btn_state_change('unbusy',BUTTON_ON);
		btn_state_change('chanspy',BUTTON_OFF);
		stopclock();
		break;
		case '报工号' :
		callSeatState('报工号');
		break;
		case '系统占用' :
		callQueueState('系统占用');
		btn_state_change('busy',BUTTON_OFF);
		btn_state_change('unbusy',BUTTON_OFF);
		btn_state_change('chanspy',BUTTON_OFF);
		btn_state_change('callinner',BUTTON_OFF);
		btn_state_change('callouter',BUTTON_OFF);
		break;
		case '电话控制问题' :
		var str = '控件问题:<br/>可能是以下原因导致的：<br>1、没有安装linphone控件，<a href="linphone.exe">点击安装</a>。<br>2、系统没有权限控制电话，请把地址加入到可信任站点，并且调整可信任站点的权限为最低';
		$('#modal_content').html(str);
		$('#myModal').modal('show');
		case 'flash未加载' :
		case '电话注册失败' :
		case '与服务器断开' :
		case '无法连接到服务器':
		case '连接失败' :
		case '本Socket已初始化' :
		case '分机号不存在' :
		case '分机已有使用' :
		case '坐席已初始化' :
		case '队列不存在' :
		case '尚未初始化' :
		case '尚未签入' :
		case '已达登陆上限' :
		callQueueState('未签入');
		callSeatState('未签入');
		stopclock();
		$('#login_value').text('签入');
		$("#toolbar button").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		btn_state_change('login',BUTTON_ON);
		showimport(responce);
		break;
		case '系统重签入' :
		stopclock();
		alert('xx');
		$("#toolbar button").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		showimport(responce);
		break;
		default:
		showimport(responce);
		break;
	}
}

//内呼jsonp方法
function callinner(que_id, flag)
{		
	var str = title = '';
	if (que_id) {
		str = '&que_id=' + que_id;
	}
	if (flag == 1) {
		title = "咨询坐席";
	} else {
		title = "内呼";
	}
	$('#myModalLabel').text(title);
	//jsonp 获取内呼信息；
	$.ajax({
		 type: "GET",
		 url: url+'/jsonp/callinner',
		 dataType:"jsonp",
		 async: false,
		 data: "vcc_code=" + vcc_code + "&ag_id=" + agentID + str,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				data = data.data;
				var que_html = select_phone = '';
				flag = flag ? flag : '';
				for (item in data.que) {
					que_html+="<a onclick=\"callinner("+data.que[item].id+",'"+flag+"')\">"+data.que[item].que_name+"</a>&nbsp;&nbsp;";
				}
				//等于1时是咨询坐席；
				if(flag != 1){
					for (item in data.num) {
						select_phone+="<option value='"+data.num[item]+"'>"+data.num[item]+"</option>";
					}
				}
				/*table列表*/
				var table = "<tr><td>坐席号</td><td>姓名</td><td>技能组</td><td>状态</td></tr>";
				for (item in data.agent_info) {
					table+="<tr><td>"+data.agent_info[item].ag_num+"</td><td>"
					+data.agent_info[item].ag_name+"</td><td>"
					+data.agent_info[item].ag_queue+"</td><td>"+getStatus(data.agent_info[item].ag_sta)+"</td></tr>"
				}
				if (flag == 1) {
					$('#inner_number').hide();
				}
				$('#innerTable').html(table);
				$('#num_list').html(select_phone);
				$('#que_list').html(que_html);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callinner_content').html();
	$('#modal_content').html(content);
	$('#myModal').modal('show');	
}

/*去掉前后空格*/
function trim(text)
{
  if (typeof(text) == "string")
  {
    return text.replace(/^\s*|\s*$/g, "");
  }
  else
  {
    return text;
  }
}

/*
外呼号码框keypress事件
*/
function pressOutNum(event){
	var code = event.keyCode;
	var flag = $('#out_inner_flag').val();
	if(code== 13)
	{
		var number = $.trim($('#callouter_num').val());
		var callouter_caller = $('#callouter_caller').val();
		if(isNaN(number)){
			alert('号码错误');
			return;
		}
		var callouter_queid = Number($('#callouter_queid').val());
		if(!callouter_queid && flag == 1)
		{
			alert('请选择外呼技能组');
			return;
		}
		if(number == 0 || number == '')
		{
			return;
		}
		var callouter_caller = $('#callouter_caller').val();
		if (flag == 1) {
			wincall.fn_dialouter(number,callouter_caller,callouter_queid);
		} else if(flag == 2) {
			wincall.fn_consultouter(number,callouter_caller);
		}
		
		$('#myModal').modal('hide');	
	}
	else if((code >= 48 && code <= 57 )  || (code == 35)|| (code == 42))	return true;
	else return false;
}

/*点击呼叫按钮*/
function clickOutNum(){
	var number = $.trim($('#callouter_num').val());
	var callouter_caller = $('#callouter_caller').val();
	var flag = $('#out_inner_flag').val();
	if(isNaN(number)){
		alert('号码错误');
		return;
	}
	var callouter_queid = Number($('#callouter_queid').val());
	if(!callouter_queid  && flag == 1)
	{
		alert('请选择外呼技能组');
		return;
	}
	if(number == 0 || number == '')
	{
		return;
	}
	if (flag == 1) {
		wincall.fn_dialouter(number,callouter_caller,callouter_queid);
	} else if(flag == 2) {
		wincall.fn_consultouter(number,callouter_caller);
	}
	$('#myModal').modal('hide');	
}

//咨询外线
function jsonp_consultouter()
{
	//外呼查询
	$.ajax({
		 type: "GET",
		 url: url+'/jsonp/consultouter',
		 dataType:"jsonp",
		 async: false,
		 data: "vcc_code=" + vcc_code,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				data = data.data;
				var select_phone = "<select id='callouter_caller' class='form-control'>";
				for (item in data.num) {
					select_phone+="<option value='"+data.num[item]+"'>"+data.num[item]+"</option>";
				}
				select_phone +="</select>";
				$('#callouter_num').val(data.number);
				$('#out_que_num').html(select_phone);
				$('#que_list_label').hide();
				$('#number_label').text('咨询号码:');
				$('#callouter_btn').text('咨询');
				$('#out_inner_flag').val(2);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callout_content').html();
	$('#modal_content').html(content);
	$('#myModalLabel').html('咨询外线');
	$('#myModal').modal('show');
}

//监听弹屏
function jsonp_chanspy() 
{
	//监听弹屏
	$.ajax({
		 type: "GET",
		 url: url+'/jsonp/chanspy',
		 dataType:"jsonp",
		 async: false,
		 data: "vcc_code=" + vcc_code,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				data = data.data;
				/*table列表*/
				var table = "<tr><td>ag_id</td><td>坐席号</td><td>姓名</td><td>分机号</td><td>通话时长</td></tr>";
				for (item in data.agent_info) {
					table+="<tr><td>"+data.agent_info[item].ag_id+"</td><td>"
					+data.agent_info[item].ag_num+"</td><td>"
					+data.agent_info[item].ag_name+"</td><td>"
					+data.agent_info[item].pho_num+"</td><td>"+data.agent_info[item].ag_time+"</td></tr>"
				}
				$('#chanspyTable').html(table);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#chanspy_content').html();
	$('#modal_content').html(content);
	$('#myModalLabel').html('监听');
	$('#myModal').modal('show');
}

//座席状态
function callSeatState(responce)
{
	$('#seat_state').attr('innerHTML',responce);
}

//队列状态
function callQueueState(responce)
{
	$('#queue_state').attr('innerHTML',responce);
}