$(document).ready(function(){
    $.ajax({
        type: "GET",
        url: url+'/jsonp/agent',
        dataType:"jsonp",
        async: false,
        data: "vcc_code="+vcc_code+"&ag_num="+ag_num+"&callback=agentCall",
        jsonp:"jsonpcallback",
        success:function(data){
        }
    });
});
function agentCall(data)
{
    if(data.code == 200){
        data = data.data;
        /*坐席相关信息*/
        agentID = data.id;
        agentNum = data.ag_num;
        vccID = data.vcc_id;
        agentName = data.ag_name;
        agentPhoneID = data.pho_id;
        agentPhoneType = data.pho_type;
        queueID = data.que_id;
        agentPass = data.ag_password;
        /*system*/
        outPage = data.outpop_page;
        inpop_page = data.inpop_page;
        /*cc_phone*/
        cc_phone = data.cc_phone;
        var que = data.que;
        for (var i = 0; i < que.length; i++) {
            que_lists[que[i].id] = que[i].que_name;
        }
        //返回成功后 调用初始化
        wincall = $.wincall({
            phonetype:3,
            tel_server:ip,
            tel_server_port:tel_port,
            sip_prefix:true,
            dealnumber_before_call:true,
            connect_type:connect_type
        });
        initPhone();
    } else {
        alert(data.message);
    }
}

/*
 返回状态
 */
function getStatus(value)
{
    switch(value){
        case '1':
            return '空闲';
            break;
        case '2':
            return '忙碌';
            break;
        case '4':
            return '占用';
            break;
        case '5':
            return '事后处理';
            break;
        default:
            return value;
            break;
    }
}

/*初始化电话按钮*/

function initPhone()
{
	//签入签出
	$('#ztth_login').click(function(){
		agentCheck();
		
	});
	//		置闲
	$('#ztth_unbusy').click(function (){
		if(trim($('#ztth_unbusy').text()) == '置闲')
		{
			busy_btn_checked = false;
			//btn_state_change('ztth_unbusy',BUTTON_ON);
			//btn_state_change('ztth_busy',BUTTON_OFF);
			wincall.fn_unbusy();
		}
	});
	// 置忙
	$('#ztth_busy').click(function (){
		if(trim($('#ztth_busy').text()) == '置忙')
		{
			busy_btn_checked = true;
			//btn_state_change('ztth_unbusy',BUTTON_OFF);
			//btn_state_change('ztth_busy',BUTTON_ON);
			wincall.fn_busy();
		}
	});

	//保持 恢复
	$('#ztth_hold').click(function (){
		if(trim($('#ztth_hold_value').text()) == '保持')
		{
			wincall.fn_hold();
		}
		else if(trim($('#ztth_hold_value').text()) == '恢复')
		{
			wincall.fn_unhold();
		}
	});

	//内呼 弹框；
	$('#ztth_callinner').click(function (){
		callinner();
	});

	//外呼
	$('#ztth_callouter').click(function (){
		dialouter_panel('','');
	});

	//挂断
	$('#ztth_hangup').click(function (){
		btn_state_change('ztth_hangup',BUTTON_OFF);
		wincall.fn_hangup();
	});
	//评价
	$('#ztth_evaluation').click(function (){
		btn_state_change('ztth_evaluation',BUTTON_OFF);
		wincall.fn_evaluate();
	});

	//咨询坐席
	$('#ztth_consultinner').click(function (){
		callinner(0,1); //和内呼一样
	});

	//咨询外线
	$('#ztth_consultouter').click(function (){
		jsonp_consultouter();
	});

	//咨询接回
	$('#ztth_consultback').click(function (){
		wincall.fn_consultback();
	});

	//转接
	$('#ztth_transfer').click(function (){
		wincall.fn_transfer();
	});

	//三方
	$('#ztth_threeway').click(function (){
		wincall.fn_3way();
	});
	//三方接回
	$('#ztth_threewayback').click(function (){
		wincall.fn_3wayback();
	});

	//监听
	$('#ztth_chanspy').click(function (){
		jsonp_chanspy();
	});
	//拦截
	$('#ztth_intercept').click(function (){
		wincall.fn_intercept();
	});
	//强插
	$('#ztth_breakin').click(function (){
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

//    if (name == 'ztth_busy') {
//        $('#')
//    } else if(name == 'ztth_unbusy') {
//
//    }
}
var que_list = [];
//调出外呼呼叫页面 type:1：工具栏外呼 type=''业务受理外呼
function dialouter_panel(phone_num,caller)
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
				//var que_html = "<select id='callouter_queid' class='form-control'>";
				var select_phone = "<select id='callouter_caller' class='form-control'>";
				
				for (items in data.que) {
				
					que_list[data.que[items].id] = data.que[items].que_name;
					//que_html+="<option value='"+data.que[item].id+"'>"+data.que[item].que_name+"</option>";
				}
				for (items in data.num) {
					select_phone+="<option value='"+data.num[items]+"'>"+data.num[items]+"</option>";
				}
				select_phone +="</select>";
				//alert(select_phone);
				//que_html +="</select>";
				$('#ztth_callouter_num').val(data.number);
				$('#ztth_out_que_num').html(select_phone);
				//$('#out_que_list').html(que_html);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callout_content').html();
	$('#ztth_modal_content').html(content);
	$('#ztth_myModalLabel').html('外呼');
	$('#ztth_myModal').modal('show');
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
function showimport(responce)
{
    //alert(responce);
	var content = '';
	var show = true;
	var dialog  = false;
	switch(responce)
	{
		case '002' : show = false;content = '系统置忙';break;
		case '003' : show = false;content = '系统置闲';break;
		case '004' : show = false;content = '系统占用';break;
		case '005' : show = false;content = '事后处理';break;
		case '007' : content = '常接通电话离线';break;
		case '011' : show = false;content = '上线空闲';break;
		case '012' : show = false;content = '上线示忙';break;
		case '024' : show = false;content = '强制签出';break;
		case '096' : content = '系统重签入';break;
		case '097' :  content = '连接失败';break;
		case '098' : content = 'flash未加载';break;
		case '099' : show = false;content = '断开socket';break;
		case '0000' : show = false;dialog = true;content = '通话完成';break;
		case '0400' : show = false;content = '外呼中';break;
		case '0500' : show = false;dialog = true;content = '被呼叫来电';break;
		case '0501' : show = false;dialog = true;content = '队列分配来电';break;
		case '0502' : show = false;content = '外呼来电';break;
		case '0503' : show = false;content = '监听来电';break;
		case '0504' : show = false;content = '被咨询';break;
		case '0505' : show = false;dialog = true;content = '自动外呼来电';break;
		case '0507' : show = false;content = '常接通模式来电';break;
		case '0508' : show = false;content = '外线转坐席';break;
		case '0600' : show = false;content = '通话中';break;
		case '0800' : show = false;content = '常接通通话中';break;
		case '0900' : dialog = true;content = '常接通模式电话断开';break;
		case '1000' : dialog = true;show = false;content = '己方接通';break;
		case '2100' : content = '咨询外呼中';break;
		case '2200' : content = '咨询接通';break;
		case '2300' : content = '咨询接回';break;
		case '2400' : content = '咨询未呼通';break;
		case '2500' : content = '三方接通';break;
		case '2600' : content = '三方接回';break;
		case '3100' : content = '监听成功';
					btn_state_change('ztth_chanspy',BUTTON_OFF);
					btn_state_change('ztth_breakin',BUTTON_ON);
				    btn_state_change('ztth_intercept',BUTTON_ON);
			break;
		case '3200' : content = '强插成功';
					btn_state_change('ztth_breakin',BUTTON_OFF);
			break;
		case '3300' : content = '转接成功';break;
		case '3400' : content = '拦截成功';
				btn_state_change('ztth_evaluation',BUTTON_ON);
				btn_state_change('ztth_breakin',BUTTON_OFF);
				btn_state_change('',BUTTON_OFF);
				btn_state_change('ztth_hangup',BUTTON_ON);
				btn_state_change('ztth_consultinner',BUTTON_ON);
				btn_state_change('ztth_consultouter',BUTTON_ON);
				btn_state_change('ztth_hold',BUTTON_ON);
		break;
		case '3500' : content = '通话已转接';break;//被转接的坐席收到此信息
		case '4000' : content = '常接通模式未接通';break;
		case '5500' : content = '对方振铃';break;
		case '20001' : content = '软电话注册成功';break;
		case '20002' : content = '软电话重新注册';break;
		case '20003' : content = '软电话注册失败';break;
		case '10010' : 
			content = '连接成功';
			if(is_load == 1){
				clearInterval(flashTime);
			}
			break;
		case '10011' : content = '与服务器断开连接';break;
		case '10012' : content = '无法连接到服务器';break;
		case '10013' : content = '安全错误';break;
		case '10014' : show = false;content = ' 发送数据错误，服务器还没准备好';break;
		case '10015' : content = '网络异常';break;
		case '10016' : content = '网络异常，中间件断开';break;
		case '001000' : content = '初始化成功';break;
		case '001001' : content = '坐席已被初始化';break;
		case '001002' : content = '本socket已被初始化';break;
		case '001003' : content = '分机号不存在';break;
		case '001004' : content = '分机已有坐席使用';break;
		case '001005' : content = '队列不存在';break;
		case '001006' : content = '已达登录上限';break;
		case '001007' : content = '分机类型错误';break;
		case '001020' : content = '企业错误';break;
		case '001801' : content = '连接数据库错误';break;
		case '001804' : content = '帐号错误';break;
		case '001805' : content = '找到多个坐席';break;
		case '001806' : content = '密码错误';break;
		case '001807' : content = '分机查询错误';break;
		case '001808' : content = '分机不存在';break;
		case '001809' : content = '找到多个分机';break;
		case '001999' : content = '其它初始化错误';break;
		case '002000' : content = '签出成功';break;//反初始化成功
		case '002001' : content = '坐席尚未初始化';break;
		case '002999' : content = '其它反初始化错误';break;
		case '003000' : content = '签入成功';break;
		case '003001' : content = '坐席尚未初始化';break;
		case '003999' : content = '其它签入错误';break;
		case '004000' : show = false;content = '签出成功';break;
		case '004001' : content = '坐席尚未初始化';break;
		case '004002' : content = '坐席尚未登录';break;
		case '004999' : content = '其它签出错误';break;
		case '005000' : show = false;content = '置忙成功';break;
		case '005001' : content = '坐席尚未初始化';break;
		case '005002' : content = '坐席尚未签入';break;
		case '005003' : content = '已处于置忙';break;
		case '005004' : content = '不允许置忙';break;
		case '005999' : content = '其它置忙错误';break;
		case '006000' : show = false;content = '置闲成功';break;
		case '006001' : content = '坐席尚未初始化';break;
		case '006002' : content = '坐席尚未登录';break;
		case '006003' : content = '已处于置闲';break;
		case '006999' : content = '其它置闲错误';break;
		case '007000' : content = '未登录';break;
		case '007010' : content = '尚未初始化';break;
		case '009000' : show = false;content = '进入自动外呼成功';break;
		case '009001' : content = '坐席尚未初始化';break;
		case '009002' : content = '坐席尚未登录';break;
		case '009003' : content = '项目错误';break;
		case '009004' : content = '已进入自动外呼';break;
		case '009999' : content = '其它进入自动外呼错误';break;
		case '010000' : show = false;content = '退出自动外呼成功';break;
		case '010001' : content = '坐席尚未初始化';break;
		case '010002' : content = '坐席尚未登录';break;
		case '010003' : content = '项目错误';break;
		case '010004' : content = '已退出自动外呼';break;
		case '010999' : content = '其它退出自动外呼错误';break;
		case '021000' : show = false;content = '呼叫坐席成功';break;
		case '021001' : content = '主叫坐席错误';break;
		case '021002' : content = '被叫坐席错误';break;
		case '021003' : content = '队列错误';break;
		case '021004' : content = '队列不允许呼叫';break;
		case '021005' : content = '主叫状态不对';break;
		case '021006' : content = '被叫状态不空闲';break;
		case '021007' : content = '被叫坐席不属于本企业';break;
		case '021008' : content = '主叫号码不对';break;
		case '021020' : content = '企业错误(停用或过期等)';break;
		case '021030' : content = '常接通模式不能呼叫坐席';break;
		case '021451' : content = '消息格式错误';break;
		case '021501' : content = '企业未启用';break;
		case '021502' : content = '企业已过期或未开通';break;
		case '021503' : content = '余额不足';break;
		case '021999' : content = '其它呼叫坐席错误';break;
		case '022000' : show = false;content = '呼叫外线成功';break;
		case '022001' : content = '呼叫坐席错误';break;
		case '022002' : content = '呼叫错误';break;
		case '022003' : content = '队列错误';break;
		case '022004' : content = '队列不允许呼叫';break;
		case '022005' : content = '主叫状态不对';break;
		case '022007' : content = '企业不匹配';break;
		case '022008' : content = '主叫号码错误';break;
		case '022009' : content = '常接通模式未接通';break;
		case '022010' : content = '通道错误';break;
		case '022020' : content = '企业错误(停用或过期等)';break;
		case '022451' : content = '消息格式错误';break;
		case '022501' : content = '企业未启用';break;
		case '022502' : content = '企业已过期或未开通';break;
		case '022503' : content = '余额不足';break;
		case '022999' : content = '其它呼叫外线错误';break;
		case '023000' : show = false;content = '挂机成功';break;
		case '023001' : content = '坐席错误';break;
		case '023002' : content = '不处于通话中';break;
		case '023999' : content = '其它挂机错误';break;
		case '024000' : show = false;content = '咨询成功';break;
		case '024001' : content = '主叫坐席错误';break;
		case '024002' : content = '咨询坐席错误';break;
		case '024004' : content = '咨询坐席不空闲';break;
		case '024005' : content = '不在通话中';break;
		case '024006' : content = '通话类型不对';break;
		case '024007' : content = '通话异常';break;
		case '024009' : content = '已处于咨询或三方';break;
		case '024451' : content = '消息格式错误';break;
		case '024501' : content = '企业未启用';break;
		case '024502' : content = '企业已过期或未开通';break;
		case '024503' : content = '余额不足';break;
		case '024999' : content = '其它咨询坐席错误';break;
		case '025000' : show = false;content = '咨询成功';break;
		case '025001' : content = '主叫坐席错误';break;
		case '025002' : content = '咨询坐席错误';break;
		case '025004' : content = '咨询坐席不空闲';break;
		case '025005' : content = '不在通话中';break;
		case '025006' : content = '通话类型不对';break;
		case '025007' : content = '通话异常';break;
		case '025009' : content = '于咨询或三方';break;
		case '025020' : content = '企业错误';break;
		case '025451' : content = '消息格式错误';break;
		case '025501' : content = '企业未启用';break;
		case '025502' : content = '企业已过期或未开通';break;
		case '025503' : content = '余额不足';break;
		case '025999' : content = '其它咨询外线错误';break;
		case '026000' : show = false;content = '咨询接回成功';break;
		case '026001' : content = '坐席错误';break;
		case '026002' : content = '没有通话';break;
		case '026003' : content = '通话异常';break;
		case '026004' : content = '不在咨询三方中';break;
		case '026005' : content = '咨询状态不对';break;
		case '026999' : content = '其它咨询接回错误';break;
		case '027000' : show = false;content = '三方成功';break;
		case '027001' : content = '坐席错误';break;
		case '027002' : content = '没有通话';break;
		case '027003' : content = '通话异常';break;
		case '027004' : content = '不在咨询中';break;
		case '027005' : content = '咨询状态异常';break;
		case '027999' : content = '其它三方错误';break;
		case '028000' : show = false;content = '三方接回成功';break;
		case '028001' : content = '坐席错误';break;
		case '028002' : content = '没有通话';break;
		case '028003' : content = '通话异常';break;
		case '028004' : content = '不在咨询中';break;
		case '028005' : content = '咨询状态异常';break;
		case '028999' : content = '其它三方接回错误';break;
		case '029000' : show = false;content = '转接成功';break;
		case '029001' : content = '坐席错误';break;
		case '029002' : content = '没有通话';break;
		case '029003' : content = '通话异常';break;
		case '029004' : content = '不在咨询中';break;
		case '029005' : content = '询状态异常';break;
		case '029006' : content = '不允许转接外线';break;
		case '029999' : content = '其它转接错误';break;
		case '030000' : show = false;content = '监听成功';break;
		case '030001' : content = '监听坐席错误';break;
		case '030002' : content = '被监听坐席错误';break;
		case '030004' : content = '监听坐席状态不对';break;
		case '030005' : content = '不在通话中';break;
		case '030006' : content = '通话类型不对';break;
		case '030007' : content = '通话异常';break;
		case '030009' : content = '已被监听或强插';break;
		case '030020' : content = '企业错误';break;
		case '030999' : content = '其它监听错误';break;
		case '031000' : show = false;content = '拦截成功';break;
		case '031001' : content = '坐席错误';break;
		case '031002' : content = '不在监听中';break;
		case '031999' : content = '其它拦截错误';break;
		case '032000' : show = false;content = '强插成功';break;
		case '032001' : content = '坐席错误';break;
		case '032003' : content = '不在监听或强插中';break;
		case '032999' : content = '其它强插错误';break;
		case '033000' : show = false;content = '保持成功';break;
		case '033001' : content = '坐席错误';break;
		case '033002' : content = '坐席不在通话中';break;
		case '003003' : content = '尚未接通';break;
		case '033004' : content = '已保持';break;
		case '033005' : content = '没有权限';break;
		case '033999' : content = '其他保持错误';break;
		case '034000' : show = false;content = '恢复成功';break;
		case '034999' : content = '其它恢复错误';break;
		case '041000' : content = '满意度成功';break;
		case '041001' : content = '坐席错误';break;
		case '041002' : content = '坐席不在通话中';break;
		case '041003' : content = '坐席不在通话中';break;
		case '041004' : content = '通话异常';break;
		case '041005' : content = '通话类型错误';break;
		case '041006' : content = '通话状态不对';break;
		case '041007' : content = '通道错误';break;
		case '041999' : content = '其它满意度错误';break;
		case '042000' : show = false;content = '握手信号';break;
		case '051000' : show = false;content = '常接通模式接通';break;
		case '051001' : content = '坐席错误';break;
		case '051002' : content = '不处于通话中';break;
		case '051003' : content = '非振铃状态';break;
		case '051020' : content = '企业错误';break;
		case '051030' : content = '常接通模式不允许';break;
		case '051999' : content = '其它应答错误';break;
		default: content='事件'+responce;break;
	}
	//alert(content + '' + show);
	if(content!='' && show)
	{
		clearTimeout(error_timeout);
		$('#ztth_phone_error').text(content);
		$('#ztth_phone_error').attr('class','label label-important');
		error_timeout = setTimeout(function ()	{
			$('#ztth_phone_error').attr('class','label label-important hide');
		},3000);
	}
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
		$('#ztth_seat_state').text(minute+':'+second);
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
	if(responce=='' || !responce)
	return;
	showimport(responce);
	switch(responce)
	{
		case '002' : //系統置忙
		case '012' : //上线置忙
		case '005000' : //置忙成功
		callQueueState('忙碌');
		btn_state_change('ztth_busy',BUTTON_OFF);
		//btn_state_change('')
		/*
		$.each(busy, function(index, id) {
			btn_state_change('ztth_busy'+id,BUTTON_OFF);
		});*/
		btn_state_change('ztth_unbusy',BUTTON_ON);
		btn_state_change('ztth_callinner',BUTTON_ON);
		btn_state_change('ztth_hangup',BUTTON_OFF);
		btn_state_change('autocall',BUTTON_ON);
		break;
		case '003' :  //系統置閑
		case '011' :  //上线空闲
		case '006000' :  //置闲成功
		callQueueState('空闲');
		btn_state_change('ztth_busy',BUTTON_ON);
		/*
		$.each(busy, function(index, id) {
			btn_state_change('ztth_busy'+id,BUTTON_ON);
		});*/
		btn_state_change('ztth_unbusy',BUTTON_OFF);
		btn_state_change('ztth_callinner',BUTTON_ON);
		btn_state_change('ztth_hangup',BUTTON_OFF);
		btn_state_change('autocall',BUTTON_ON);
		break;
		case '004' :  //系统占用
		callQueueState('系统占用');
		btn_state_change('ztth_hangup',BUTTON_ON);
		btn_state_change('ztth_busy',BUTTON_OFF);
		/*
		$.each(busy, function(index, id) {
			btn_state_change('ztth_busy'+id,BUTTON_OFF);
		});*/
		btn_state_change('ztth_unbusy',BUTTON_OFF);
		btn_state_change('ztth_callinner',BUTTON_OFF);
		break;
		case '005' :  //事后处理
		_stop_music();
		_stop_clock();
		_clear_number_loc();
		callQueueState('事后处理');
		if(busy_btn_checked) //忙碌状态时候处理直接忙碌
		{
			wincall.fn_busy();
		}
		else
		{
			btn_state_change('ztth_busy',BUTTON_ON);
			/*
			$.each(busy, function(index, id) {
				btn_state_change('ztth_busy'+id,BUTTON_ON);
			});*/
			btn_state_change('ztth_unbusy',BUTTON_ON);
		}
		break;
		case '007' :  //电话离线状态
		callQueueState('电话离线');
		$("#toolbar button").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		btn_state_change('ztth_login',BUTTON_ON);
		break;
		case '024':
		$.messager.alert('警告','您已被管理员强制签出！','error');
		break;
		case '096' : //系统重签入
		$("#toolbar button").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		break;
		case '098'://flash未加载
		btn_state_change('ztth_login',BUTTON_ON);
		break;
		case '099' :  //断开连接
		case '004000' :  //签出成功
		_stop_music();
		_stop_clock();
		_stop_change_title();
		_clear_number_loc();
		//alert('xyxy');
		$('#autocall_stat').css('display','none');
		callSeatState('未登录');
		callQueueState('未登录');
		$('#ztth_login_value').text('签入');
		$('#ztth_hold_value').text('保持');
		$("#toolbar button").each(function (i,item){
			btn_state_change(item.id,BUTTON_OFF);
		});
		btn_state_change('ztth_login',BUTTON_ON);
		break;
		case '0000' : //通话完成
		_stop_music();
		_stop_change_title();
		_stop_clock();
		_clear_number_loc();
		callSeatState('通话完成');
		$('#ztth_hold_value').text('保持');
		_close_ring_panel();
		btn_state_change('ztth_consultinner',BUTTON_OFF);
		btn_state_change('ztth_consultouter',BUTTON_OFF);
		btn_state_change('ztth_hangup',BUTTON_OFF);
		btn_state_change('ztth_evaluation',BUTTON_OFF);
		btn_state_change('ztth_transfer',BUTTON_OFF);
		btn_state_change('ztth_hold',BUTTON_OFF);
		btn_state_change('ztth_consultback',BUTTON_OFF);
		btn_state_change('ztth_threeway',BUTTON_OFF);
		btn_state_change('ztth_threewayback',BUTTON_OFF);
		btn_state_change('ztth_chanspy',BUTTON_ON);
		btn_state_change('ztth_breakin',BUTTON_OFF);
		btn_state_change('ztth_intercept',BUTTON_OFF);
		break;
		case '0400' : //呼叫中
		callSeatState('呼叫中');
		btn_state_change('ztth_hangup',BUTTON_ON);
		var _called = wincall.fn_getParam('Called');
		last_call = {callnum:_called,callid:wincall.fn_getParam('CallId')};
		//_set_number_loc(_called)
		if (outPage) {
			console.log(outPage);
			if(_called.substring(0,1)=="0"){
				_called=_called.substring(1,_called.length);
			}
			$.ajax({
				type: 'post',
				url : "../member/selectByMobilephone.do?mobilephone="+_called,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
					_called=str;
			    },
			 });
			window.open(outPage+_called);
		}
		//外呼弹屏： 1是  2否
		if(user_outcall_popup == 1)
		{
			var title_phone = _called;
			if(!power_phone_view)
			{
				title_phone     = hidden_part_number(title_phone);
			}
			if(pop_address != '')
			{
				var _pop_address = eval('"'+pop_address+'"');
				addTab('业务受理'+title_phone,_pop_address,'menu_icon');
			}
			else
			{
				addTab('业务受理'+title_phone+'','index.php?c=client&m=search_client&phone='+_called,'menu_icon');
			}
		}
		break;
		case '0500' : // 来电  被叫 (呼叫坐席)
            callSeatState('来电');
            btn_state_change('ztth_hangup',BUTTON_ON);
            last_call = {callnum:wincall.fn_getParam('Caller'),callid:wincall.fn_getParam('CallId')};
            if(user_ol_model != OLMODEL_AUTOANSWER)
            {
                _start_music();
            }
            var _OAgId = wincall.fn_getParam('OAgId');//来电坐席ag_i
            _user_callin(_OAgId,'');
            break;
		case '0501':  //队列来电  队列分配
            _start_music();
            _start_change_title();
            btn_state_change('ztth_hangup',BUTTON_ON);
            callSeatState('队列来电');
            last_call = {callnum:wincall.fn_getParam('Caller'),callid:wincall.fn_getParam('CallId')};
            var _caller = wincall.fn_getParam('Caller');
            var _queid = wincall.fn_getParam('QueId');//队列
            var _quename = que_lists[_queid];
            var _servnum = wincall.fn_getParam('ServNum');//服务号码
            var _title_str = '【'+_quename+'-'+_servnum+'】';  //   队列：_quename ，  服务号码：_servnum
            //来电弹屏
            if (inpop_page) {
                window.open(eval("'"+inpop_page+_caller+"'"));
            }
            var title_phone = _caller;
            if(!power_phone_view)
            {
                title_phone = hidden_part_number(title_phone);
            }
            _set_number_loc(_caller);
            $('#callin_number').html(title_phone);
            _ring_panel_dialog('新的来电'+_title_str);
            break;
		break;
		case '0502': //外呼来电  外呼时呼叫自己
            btn_state_change('ztth_hangup',BUTTON_ON);
            callSeatState('外呼来电');
            if(user_phone_type == 'softphone')
            {
                wincall.fn_answer();
            }
		break;
		case '0503':  // 监听来电 监听时呼叫自己
		btn_state_change('ztth_hangup',BUTTON_ON);
		callSeatState('监听来电');
		if(user_phone_type == 'softphone')
		{
			wincall.fn_answer();
		}
		break;
		case '0504':  //咨询来电  被咨询
		_start_music();
		btn_state_change('ztth_hangup',BUTTON_ON);
		callSeatState('咨询来电');
		var _OAgId = wincall.fn_getParam('OAgId');//来电坐席ag_id
		var coustom_number = wincall.fn_getParam('Others');//客户电话
		_user_callin(_OAgId,coustom_number);
		break;
		case '0505'://自动外呼来电
		_start_change_title();
		var _caller = wincall.fn_getParam('Caller');
		//_set_number_loc(_caller);
		last_call = {callnum:_caller,callid:wincall.fn_getParam('CallId')};
		var acPro = wincall.fn_getParam('AcPro');//项目ID
		var acTask = wincall.fn_getParam('AcTask');//任务ID;
		if(user_ol_model != OLMODEL_AUTOANSWER)
		{
			_start_music();
			setTimeout(function(){
				wincall.fn_answer();
			},1000);
			break;
		}
		case '0507'://常接通模式来电
		setTimeout(function(){
			wincall.fn_answer(true);
		},2000);
		break;
		case '0508'://外线直接转坐席
            _start_music();
            _start_change_title();
            btn_state_change('ztth_hangup',BUTTON_ON);
            callSeatState('客户来电');
            last_call = {callnum:wincall.fn_getParam('Caller'),callid:wincall.fn_getParam('CallId')};
            var _caller = wincall.fn_getParam('Caller');
            //来电弹屏
            var title_phone = _caller;
            if(!power_phone_view)
            {
                title_phone = hidden_part_number(title_phone);
            }
            if (inpop_page) {
                window.open(eval("'"+inpop_page+"'"));
            }
            _set_number_loc(_caller);
            $('#callin_number').html(title_phone);
            _ring_panel_dialog('新的来电');
		break;
		case '0600':  //通话中
		case '0800'://常接通模设计接通
		_stop_music();
		_stop_change_title();
		callSeatState('通话中');
		btn_state_change('ztth_hangup',BUTTON_ON);
		btn_state_change('ztth_evaluation',BUTTON_ON);
		btn_state_change('ztth_consultinner',BUTTON_ON);
		btn_state_change('ztth_consultouter',BUTTON_ON);
		btn_state_change('ztth_hold',BUTTON_ON);
		_start_clock();
		_close_ring_panel();
		break;
		case '024000' : //咨询成功
		case '025000' :
		btn_state_change('ztth_consultback',BUTTON_ON);
		btn_state_change('ztth_consultinner',BUTTON_OFF);
		btn_state_change('ztth_consultouter',BUTTON_OFF);
		break;
		case '2200':   //咨询接通
		btn_state_change('ztth_threeway',BUTTON_ON);
		btn_state_change('ztth_transfer',BUTTON_ON);
		btn_state_change('ztth_consultinner',BUTTON_OFF);
		btn_state_change('ztth_consultouter',BUTTON_OFF);
		break;
		case '2300':
		case '026000':
		case '2400':  //咨询未呼通
		btn_state_change('ztth_consultback',BUTTON_OFF);
		btn_state_change('ztth_transfer',BUTTON_OFF);
		btn_state_change('ztth_threeway',BUTTON_OFF);
		btn_state_change('ztth_consultinner',BUTTON_ON);
		btn_state_change('ztth_consultouter',BUTTON_ON);
		break;
		case '2500': //三方中
		case '027000': //三方成功
		btn_state_change('ztth_threewayback',BUTTON_ON);
		btn_state_change('ztth_consultback',BUTTON_OFF);
		btn_state_change('ztth_transfer',BUTTON_OFF);
		btn_state_change('ztth_threeway',BUTTON_OFF);
		break;
		case '2600': //三方接回
		case '028000': //三方接回成功
		btn_state_change('ztth_threewayback',BUTTON_OFF);
		btn_state_change('ztth_consultinner',BUTTON_ON);
		btn_state_change('ztth_consultouter',BUTTON_ON);
		break;
		case '003000' :  //签入成功
		callSeatState('签入');
		clearTimeout(flashTime);
		_set_default_caller_que();
		if(user_ol_model == OLMODEL_NONE)
		{

			if(user_login_state == QUESTATE_LOGON)
			{
				callQueueState('空闲');	
				btn_state_change('ztth_busy',BUTTON_ON);
				btn_state_change('ztth_busy',BUTTON_ON);
			}
			else
			{
				callQueueState('忙碌');
				btn_state_change('ztth_unbusy',BUTTON_ON);
			}
		}
		else
		{
			callQueueState('常接通离线');
		}
		$('#ztth_login_value').text('签出');
		//$('#autocall').text('进入AC');
		//btn_state_change('autocall',BUTTON_ON);
		btn_state_change('ztth_login',BUTTON_ON);
		btn_state_change('ztth_callinner',BUTTON_ON);
		btn_state_change('ztth_callouter',BUTTON_ON);
		btn_state_change('ztth_chanspy',BUTTON_ON);
		break;
		case '009000'://进入自动外呼成功
		case '009004':
		//$('#autocall').val('退出AC');
		//$('#autocall_stat').css('display','block');
		//btn_state_change('autocall',BUTTON_ON);
		break;
		case '010000'://退出自动外呼成功
		case '010004':
		//$('#autocall').val('进入AC');
		//$('#autocall_stat').css('display','none');
		//btn_state_change('autocall',BUTTON_ON);
		break;
		case '033000' : //保持成功
		$('#ztth_hold_value').text('恢复');
		btn_state_change('ztth_hold',BUTTON_ON);
		break;
		case '034000': //恢复成功
		$('#ztth_hold_value').text('保持');
		btn_state_change('ztth_hold',BUTTON_ON);
		break;
	}
}

//内呼jsonp方法 咨询坐席；
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
	$('#ztth_myModalLabel').text(title);
	//jsonp 获取内呼信息;
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
				//var que_html = '';
				var select_phone = '<select id="out_que_number">';
				flag = flag ? flag : '';
				/*
				for (item in data.que) {
					que_html+="<a onclick=\"callinner("+data.que[item].id+",'"+flag+"')\">"+data.que[item].que_name+"</a>&nbsp;&nbsp;";
				}
				*/
				//等于1时是咨询坐席；
				//if(flag != 1){
					for (items in data.num) {
						select_phone+="<option value='"+data.num[items]+"'>"+data.num[items]+"</option>";
					}
				//}
				select_phone+="</select>";
				/*table列表*/
				var table = "<tr><td>坐席号</td><td>姓名</td><td>技能组</td><td>状态</td></tr>";
				for (items in data.agent_info) {
					table+="<tr><td>"+data.agent_info[items].ag_num+"</td><td>"
					+data.agent_info[items].ag_name+"</td><td>"
					+data.agent_info[items].ag_queue+"</td><td>"+getStatus(data.agent_info[items].ag_sta)+"</td></tr>"
				}
				
				if (flag == 1) {
					//咨询坐席；
					$('#ztth_inner_agent_flag').val(1);
				}
				$('#ztth_innerTable').html(table);
				$('#ztth_num_list').html(select_phone);
				//$('#que_list').html(que_html);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callinner_content').html();
	$('#ztth_modal_content').html(content);
	$('#ztth_myModal').modal('show');	
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

function pressOutNum(event){
	var code = event.keyCode;
	var flag = $('#ztth_out_inner_flag').val();
	if(code== 13)
	{
		var number = $.trim($('#ztth_callouter_num').val());
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
		
		$('#ztth_myModal').modal('hide');	
	}
	else if((code >= 48 && code <= 57 )  || (code == 35)|| (code == 42))	return true;
	else return false;
}
*/
/*点击呼叫按钮*/
var _pc_skill_group = '';
var CallNumber = '';
function clickOutNum(){
	CallNumber = $.trim($('#ztth_callouter_num').val());
	var callouter_caller = $('#callouter_caller').val();
	var flag = $('#ztth_out_inner_flag').val();
	if(isNaN(CallNumber)){
		alert('号码错误');
		return;
	}
	/*
	var callouter_queid = Number($('#callouter_queid').val());
	if(!callouter_queid  && flag == 1)
	{
		alert('请选择外呼技能组');
		return;
	}*/
	if(CallNumber == 0 || CallNumber == '')
	{
		return;
	}
    if (CallNumber.substr(0, 1) != 0)
    {
         dealnum(CallNumber);
    }
	if (flag == 1) {
		var _skill_groups =  wincall.fn_get_que();//得到当前坐席所属队列  
		for(var i=0;i < _skill_groups.length;i++)
		{
			if( que_lists[_skill_groups[i]] != 'undefined')
			{
				_pc_skill_group = _skill_groups[i];
				continue;
			}
		}
		var _skill_group	= Number(_pc_skill_group);
		wincall.fn_dialouter(CallNumber,callouter_caller,_skill_group,user_channel);
	} else if(flag == 2) {
		wincall.fn_consultouter(CallNumber,callouter_caller);
	}
	$('#ztth_myModal').modal('hide');	
}

function dealnum(number)
{
    $.ajax({
        type: "GET",
        url: url+'/jsonp/dealnumber',
        dataType:"jsonp",
        async: false,
        data: "number="+number+"&local_code="+local_code+"&jsonpcallback=localNumber",
        jsonp:"jsonpcallback",
        success:function(data){
        }
    });
}

function localNumber(res)
{
    if (res.code == 200) {
        CallNumber = res.data.deal_number;
    }
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
				for (items in data.num) {
					select_phone+="<option value='"+data.num[items]+"'>"+data.num[items]+"</option>";
				}
				select_phone +="</select>";
				$('#ztth_callouter_num').val(data.number);
				$('#ztth_out_que_num').html(select_phone);
				$('#que_list_label').hide();
				$('#ztth_number_label').text('咨询号码:');
				$('#ztth_callouter_btn').text('咨询');
				$('#ztth_out_inner_flag').val(2);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#callout_content').html();
	$('#ztth_modal_content').html(content);
	$('#ztth_myModalLabel').html('咨询外线');
	$('#ztth_myModal').modal('show');
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
				for (items in data.agent_info) {
					table+="<tr onclick='click_chanspy("+data.agent_info[items].ag_id+")'><td>"+data.agent_info[items].ag_id+"</td><td>"
					+data.agent_info[items].ag_num+"</td><td>"
					+data.agent_info[items].ag_name+"</td><td>"
					+data.agent_info[items].pho_num+"</td><td>"+data.agent_info[items].ag_time+"</td></tr>"
				}
				$('#ztth_chanspyTable').html(table);
			} else {
				alert(data.message);
			}
		 }
	});
	var content = $('#ztth_chanspy_content').html();
	$('#ztth_modal_content').html(content);
	$('#ztth_myModalLabel').html('监听');
	$('#ztth_myModal').modal('show');
}

/*
监听
*/
function click_chanspy(ag_id){
	wincall.fn_chanspy(Number(ag_id));
	$('#ztth_myModal').modal('hide');
}

//座席状态
function callSeatState(responce)
{
	$('#ztth_seat_state').text(responce);
}

//队列状态
function callQueueState(responce)
{
	$('#ztth_queue_state').text(responce);
}

//设置默认的主叫和队列
function _set_default_caller_que()
{
	var _callers =  wincall.fn_get_caller();
	if(_callers.length != 0)
	default_caller= _callers[0];

	var _ques =  wincall.fn_get_que();
	default_que = _ques[0];
}

function _stop_music(){
	if(user_phone_type == 'softphone')
	{
		var asound = getFlashObject("asound");
		asound.SetVariable("f",'not-exist.mp3');
		asound.GotoFrame(1);
	}
	else
	return true;
}

function set_caller_cookie()
{
	var cookie_caller = $('#pc_caller').val();
	set_cookie('est_caller',cookie_caller);
}

function getFlashObject(movieName)
{
	if (window.document[movieName])
	{
		return window.document[movieName];
	}
	if (navigator.appName.indexOf("Microsoft Internet")==-1)
	{
		if (document.embeds && document.embeds[movieName])
		return document.embeds[movieName];
	}
	else
	{
		return document.getElementById(movieName);
	}
}

var callclock = null;
var time_tmp = 0;
var minute;
var second;
//开始通话计时
function _start_clock()
{
	_stop_clock();
	callclock = setInterval(function (){
		time_tmp++;
		second = time_tmp%60;
		minute = Math.floor(time_tmp/60);
		if(minute < 10){minute = '0'+minute;}
		if(second < 10){second = '0'+second;}
		$('#ztth_seat_state').text(minute+':'+second);
	},1000);
}
//停止通话计时
function _stop_clock()
{
	if(callclock)
	{
		clearInterval(callclock);
		time_tmp = 0;
	}
}

// 控制来电的时候标题改变
var initial_title        = "";   //系统初始标题
var time_interval_handel = "";  //来电时，动态改变页面标题 HANDEL
var change_title_number  = 0;  //来电，动态改变标题的次数

/* 客户来电 - 窗口获取焦点、改变tittle - 显示在最前面*/
function _start_change_title()
{
	window.setTimeout(function(){  this.blur(); this.focus();},0);//页面获取焦点前置
	initial_title = document.title;//系统初始标题
	time_interval_handel = setInterval(function(){
		document.title = change_title_number % 2==0 ? "【　　　】- "+initial_title : "【新的来电】- "+initial_title;
		change_title_number ++;
	},500);
}

/*停止标题改变 - 还原标题*/
function _stop_change_title()
{
	if( !time_interval_handel )
	{
		return true;
	}

	clearInterval(time_interval_handel);//停止周期
	document.title = initial_title;//还原标题
}

function _clear_number_loc(number)
{
	$('#caller_loc').text('')
}

/*内呼  咨询坐席*/
function innerCall()
{
	var user_num = $('#ztth_pc_user_num').val();
	if(user_num.length!=0)
	{
		//_callinner_by_user_num(user_num);
		$.ajax({
		 type: "GET",
		 url: url+'/jsonp/getUserId',
		 dataType:"jsonp",
		 async: false,
		 data: "vcc_code=" + vcc_code+"&user_num="+user_num,
		 jsonp:"jsonpcallback",
		 success:function(data){
			if (data.code == 200) {
				data = data.data;
				var _caller = $('#out_que_number').val(); //主叫号码
				//获得标识；
				var flag = $('#ztth_inner_agent_flag').val();
				if (flag == 1) {
					//咨询坐席
					wincall.fn_consultinner(data.user_id,_caller);
				} else {
					//内呼；
					wincall.fn_dialinner(data.user_id,_caller);
				}	
			} else {
				alert(data.message);
			}
			$('#ztth_myModal').modal('hide');	
		 }
	});
	}
	else
	{
		alert('请填写坐席工号');
	}
}
function _close_ring_panel()
{
	try
	{
		$('#ztth_myModal').modal('hide');
	}
	catch(e){}
}
//签入签出；
function agentCheck()
{

	if(trim($('#ztth_login_value').text()) == '签入')
	{
		user_login_state = queueState
		$('#ztth_login').attr('class', 'btn btn-default');
		$('#ztth_login').attr('disabled', true);
		//wincall.fn_login(ip,vccID,agentID,agentNum,agentName,agentPhoneID,agentPhone,agentPhoneType,queueID,queueState);(ip,vccCode,agentNum,agentPass,agentphone,queState,olMode){
		//alert(ip+"\n"+vcc_code + "\n" + agentNum + "\n"+agentPass+' '+agentPhone+'\n'+queueState+' '+user_ol_model);
		wincall.fn_login(ip,vcc_code,agentNum,agentPass,agentPhone,queueState,user_ol_model);
	}
	else if(trim($('#ztth_login_value').text()) == '签出')
	{
		$(this).attr('class', 'btn disabled');
		$(this).attr('disabled', 'disabled');
		wincall.fn_logout();
	}
}

/**
 * 隐藏部分号码 用星号代替
 *
 **/
function hidden_part_number(str)
{
    if(str.length > 7)
    {
        //str = substr_replace(str, "****",7, 4); //隐藏后4位
        str = substr_replace(str, "****",3, 4); //隐藏中间4位
    }
    return str;
}

/*来电归属地*/
var locNum = '';
function _set_number_loc(number)
{
    locNum = number;
    $('#caller_loc').text('');
    $('#calling_loc').text('');
    $.ajax({
        type:'GET',
        url: url+'/jsonp/nbattribute',
        data: {'num':number,'jsonpcallback':'numlocCallback'},
        dataType: 'jsonp',
        success: function(){
        }
    });
}

/*号码归属地回调*/
function numlocCallback(responce)
{
    if(responce.code == 200)
    {
        if(wincall.fn_getParam('CallId'))
        {
            var city = responce.data.city ? responce.data.city : '';
            if(power_phone_view)
                $('#caller_loc').text(locNum+' '+city);
            else
                $('#caller_loc').text(hidden_part_number(locNum)+' '+city);

            $('#calling_loc').text(city);
        }
    }
}

if(user_phone_type == 'softphone' ||  user_ol_model == OLMODEL_AUTO)
{
    var _ring_panel_dialog = function(title)
    {
        $('#ztth_btn_hidden').hide();
        var content = $('#incoming_dialog').html();
        customerAlert(title, content);
    }
}
else
{
    var _ring_panel_dialog = function(title)
    {
        //这个只有拒接；
        $('#ztth_btn_hidden').hide();
        $('#ztth_btn_jieting').hide();
        var content = $('#incoming_dialog').html();
        customerAlert(title, content);
    }
}

function customerAlert(title, content)
{
    $('#ztth_modal_content').html(content);
    $('#ztth_myModalLabel').html(title);
    $('#ztth_myModal').modal('show');
}

function _start_music(){
    if(user_phone_type == 'softphone')
    {
        var asound = getFlashObject("asound");
        asound.SetVariable("f",'./media/start.mp3');
        asound.GotoFrame(1);
    }
    else
    {
        return true;
    }
}

function _stop_music(){
    if(user_phone_type == 'softphone')
    {
        var asound = getFlashObject("asound");
        asound.SetVariable("f",'not-exist.mp3');
        asound.GotoFrame(1);
    }
    else
        return true;
}

//接听
function ztthAnswer()
{
    _close_ring_panel();
    wincall.fn_answer();
}

//拒接
function ztthReject()
{
    _close_ring_panel();
    wincall.fn_hangup();
}

var flashTime = '';
if (is_load == 1) {
	flashTime = setInterval("agentCheck()", 2000);
}

function _dial(CallNumber){
	if(isNaN(CallNumber)){
		alert('号码错误');
		return;
	}
	if(CallNumber == 0 || CallNumber == '')
	{
		return;
	}
    if (CallNumber.substr(0, 1) != 0)
    {
         dealnum(CallNumber);
    }
	var callers = wincall.fn_get_caller();
	for(var i=0;i < callers.length;i++)
	{
		if( callers[i] != 'undefined')
		{
			callouter_caller = callers[i];
			break;
		}
	}
	var _skill_groups =  wincall.fn_get_que();//得到当前坐席所属队列  
	for(var i=0;i < _skill_groups.length;i++)
	{
		if( que_lists[_skill_groups[i]] != 'undefined')
		{
			_pc_skill_group = _skill_groups[i];
			continue;
		}
	}
	var _skill_group	= Number(_pc_skill_group);
	wincall.fn_dialouter(CallNumber,callouter_caller,_skill_group,user_channel);
}


/**
 * 呼出默认号码
 * @param CallNumber
 */
function _dialCaller(CallNumber){
	/*if(isNaN(CallNumber)){
		alert('号码错误');
		return;
	}*/
	if(CallNumber == 0 || CallNumber == '')
	{
		return;
	}
    if (CallNumber.substr(0, 1) != 0)
    {
         dealnum(CallNumber);
    }
	var callers = wincall.fn_get_caller();
	for(var i=0;i < callers.length;i++)
	{
		if( callers[i] != 'undefined')
		{
			callouter_caller = callers[i];
			break;
		}
	}
	var _skill_groups =  wincall.fn_get_que();//得到当前坐席所属队列  
	for(var i=0;i < _skill_groups.length;i++)
	{
		if( que_lists[_skill_groups[i]] != 'undefined')
		{
			_pc_skill_group = _skill_groups[i];
			continue;
		}
	}
	var _skill_group	= Number(_pc_skill_group);
	wincall.fn_dialouter(CallNumber,"80281705",_skill_group,user_channel);
}




