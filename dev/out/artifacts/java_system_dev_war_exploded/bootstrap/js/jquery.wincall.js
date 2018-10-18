/**
* EasyTone 呼叫中心中间件交流类
* ============================================================================
* 版权所有 2008-2009 北京华夏成讯科技有限公司，并保留所有权利。
* 网站地址: http://www.chinawintel.com；
* ============================================================================
* $Id    : jquery.wincall.js
* $Author: ZT
* $time  : Thu Oct 15 16:24:50 CST 2009
* @version 1.2
*/

(function($) {
	var wincall = function(opts) {
		$.extend(this, {
			isReady: function() {
				return isReady();
			},
			set_swf_ready: function() {
				return set_swf_ready();
			},
			logState: function()
			{
				return logState();
			},
			msgResponce: function(message){
				return msgResponce(message);
			},
			isCalling: function()
			{
				return isCalling;
			},
			fn_getParam: function (name){
				return fn_getParam(name);
			},
			fn_login: function(ip,vccCode,agentNum,agentPass,agentphone,queState,olMode){
				queState = parseInt(queState);
				olMode = parseInt(olMode);
				if(!queState)
				queState = 1;
				if(!olMode)
				olMode = 0;
				return login(ip,vccCode,agentNum,agentPass,agentphone,queState,olMode);
			},
			fn_logout: function(){
				return logout();
			},
			fn_dialinner: function(agentID,callerID){
				agentID = parseInt(agentID);
				return dialinner(agentID,callerID);
			},
			fn_dialouter: function(called,callerID,groupID,outChan){
				var proID = arguments[4] || 0;
				var taskID = arguments[5] || 0;
				groupID = parseInt(groupID);
				proID = parseInt(proID);
				taskID = parseInt(taskID);
                outChan = parseInt(outChan);
				called = called.replace(/[^0-9]/g,'');
				return dialouter(called,callerID,groupID,outChan,proID,taskID);
			},
			fn_answer:function(){
				if(arguments[0])
				var ol_first_connect = true;
				else
				var ol_first_connect = false;
				return answer(ol_first_connect);
			},
			fn_hangup: function(){
				return hangup();
			},
			fn_consultinner: function(agentID,callerID){
				agentID = parseInt(agentID);
				return consultinner(agentID,callerID);
			},
			fn_consultouter: function(called,caller){
				called = called.replace(/[^0-9]/g,'');
				return consultouter(called,caller);
			},
			fn_consultback: function(){
				return consultback();
			},
			fn_busy: function(){
				if(arguments[0])
				{
					var reason = arguments[0];
				}
				else
				{
					var reason = 1;
				}
				return busy(reason);
			},
			fn_unbusy: function(){
				if(arguments[0])
				{
					var reason = arguments[0];
				}
				else
				{
					var reason = 1;
				}
				return unbusy(reason);
			},
			fn_agentQuery: function ()
			{
				return agentQuery();
			},
			fn_hold: function(){
				return hold();
			},
			fn_unhold: function(){
				return unhold();
			},
			fn_dtmf: function(num){
				return dtmf(num);
			},
			fn_transfer: function(){
				return transfer();
			},
			fn_3way: function(){
				return three_way();
			},
			fn_3wayback: function(){
				return three_wayback();
			},
			fn_chanspy: function(agentID){
				agentID = parseInt(agentID);
				return chanspy(agentID);
			},
			fn_intercept: function(){
				return intercept();
			},
			fn_breakin: function(){
				return breakin();
			},
			fn_evaluate:function(){
				return evaluate();
			},
			fn_autocallin:function(proID){
				proID = parseInt(proID);
				return autocallin(proID);
			},
			fn_autocallout:function(){
				return autocallout();
			},
			fn_get_que:function(){
				return get_queue();
			},
			fn_get_caller:function(){
				return get_caller();
			},
			fn_get_callrecord:function(){
				return get_callrecord();
			}
		});

		/**系统常量**/
		var AGMT_INIT		= 100		//坐席初始化
		var AGMT_UNINIT		= 1		//坐席反初始化
		var AGMT_LOGIN		= 2		//签入
		var AGMT_LOGOUT		= 3		//签出
		var AGMT_BUSY		= 4		//置忙
		var AGMT_UNBUSY		= 5		//置闲
		var AGMT_QUERY		= 6		//状态查询
		var AGMT_KEEP		= 7		//保持连接
		var AGMT_ATIN		= 9		//进入自动外呼
		var AGMT_ATOUT		= 10	//退出自动外呼
		var AGMT_DIALAG		= 21	//呼叫坐席
		var AGMT_DIALOUT	= 22	//呼叫外线
		var AGMT_HANGUP		= 23	//挂机
		var AGMT_CONSULTAG	= 24	//咨询内线
		var AGMT_CONSULTOUT = 25	//咨询外线
		var AGMT_CONSULTBACK =26	//咨询接回
		var AGMT_3WAY 		= 27	//三方
		var AGMT_3WAYBACK	= 28	//三方
		var AGMT_TRANSFER	= 29	//转接
		var AGMT_CHANSPY	= 30	//监听
		var AGMT_INTERCEPT	= 31	//拦截
		var AGMT_BREAKIN	= 32	//强插
		var AGMT_HOLD		= 33	//保持
		var AGMT_UNHOLD		= 34	//恢复
		var AGMT_EVALUATE	= 41	//转评价
		var AGMT_DTMF		= 42	//DTMF
		var AGMT_OLANSWER	= 51	//长接通应答

		var RETURN_FALSH	= 0		//flash消息返回
		var RETURN_ACTION	= 1		//操作消息返回
		var RETURN_CONNECT	= 2		//保持连接消息返回
		var RETURN_PHONE	= 3		//电话状态消息返回 如呼叫中，振铃，挂机
		var RETURN_AGENT	= 4		//坐席状态消息返回 如系统置忙，系统置闲，占用等。
		var RETURN_QUEUE	= 5		//系统排队状态
		var RETURN_ERROR	= 10	//返回和警告信息

		var PHRN_HANGUP		= 0		//挂机
		var PHRN_CALLOUT	= 4		//外呼中
		var PHRN_RING		= 5		//来电振铃
		var PHRN_CONNECT	= 6		//接通
		var PHRN_OLRING		= 7		//常接通来电
		var PHRN_OLCONNECT	= 8		//常接通接通
		var PHRN_OLHANGUP	= 9		//常接通断开
		var PHRN_SELFCONNECT	= 10	//己方接通
		var PHRN_CONSULTOUT	= 21	//咨询外呼中
		var PHRN_CONSULTCONNECT	= 22	//咨询接通
		var PHRN_CONSULTBACK= 23	//咨询接回
		var PHRN_CONSULTFAIL= 24	//咨询未呼通
		var PHRN_3WAYSUCCESS= 25	//三方接通
		var PHRN_3WAYBACK	= 26	//三方接回
		var PHRN_CHANSPYSUCCESS	= 31	//监听成功
		var PHRN_BREAKINSUCCESS	= 32	//强插成功
		var PHRN_TRANSFERSUCCESS	= 33	//转接成功
		var PHRN_INTERCEPTSUCCESS= 34	//拦截成功
		var PHRN_TRANSFER = 35	//电话转接 被转接的坐席收到此消息
		var PHRN_OLDISCONNECT	= 40	//常接通模式未接通
		var PHRN_OTHERRING	= 55	//对方振铃

		var AGSTRN_SYSBUSY	= 2		//系统示忙
		var AGSTRN_SYSUNBUSY	= 3		//系统示闲
		var AGSTRN_SYSOCCUPY	= 4		//系统占用
		var AGSTRN_SYSAFTDEAL	= 5		//事后处理
		var AGSTRN_OFFLINE	= 7		//电话离线
		var AGSTRN_OLUNBUSY	= 11		//上线空闲
		var AGSTRN_OLBUSY	= 12		//上线忙碌

		var QUESTATE_LOGOFF	= 0;	//未登录
		var QUESTATE_LOGON	= 1;	//队列已登录/空闲
		var QUESTATE_BUSY	= 2;	//示忙
		var QUESTATE_UNBUSY	= 3;	//忙碌
		var QUESTATE_INUSE	= 4;	//占用中
		var QUESTATE_WAIT	= 5;	//事后处理
		var QUESTATE_OFFLINE	= 7;	//离线(常接通模式未接通)

		var ERROR_LOGOFF	= 4;//强制退出

		/******长接通模式*******************************/
		var OLMODEL_NONE = 0;//无长接通
		var OLMODEL_AUTO = 1;//长接通
		var OLMODEL_AUTOANSWER = 2//长接通并自动接听

		/***********************************/
		var SYS_RECHECHIN	= 35; //系统重签入
		var CONNECT_FAILURE	= 36; //连接失败
		var FLASH_NTLOADED	= 37; //flash未加载

		/***********************************/
		var CALLRECORD_NAME = "_wincall_call_record_cookie";  //来电、去电号码记录

		/**系统变量**/
		var WshShell	= null;
		var self		= this;
		var jsReady		= false;
		var swfReady	= false;
		var isConnect	= false;
		var isLogin		= false;
		var isCalling	= false;//是否在通话中
		var btn_logout	= false;//是否点击签出按钮
		var CTIIP		= null;
		var	VCCCODE		= null;
		var	AGENTNUM	= null;
		var	AGENTPASS	= null;
		var	AGENTPHONE	= null;
		var	QUEUESTATE	= QUESTATE_LOGOFF;
		var OLMODE		= OLMODEL_NONE;//常接通模式，默认非0 1常接通 2常接通自动应答
		var swfobj		= null;
		var obj_phone	= {};
		var actid		= 1;//初始的actionid
		var reconnect_time	= 0; //重连次数
		var ag_ques		= []; //坐席的所有队列
		var ag_callers	= []; //坐席的所有主叫号码
		var pho_pwd 	= '';//坐席登陆电话密码 软电话使用
		var linkid      = 0; //连接ID amq使用
		var amq			= {};
		var last_alive_time = 0; // 最近一次接收心跳时间
		var alive_interval = null;

		function init(){
			if(opts.phonetype == 'telephone')
			{
				opts.chassis_ringing = false;
				opts.sip_prefix = '';
			}
			if(opts.connect_type == 'amq')
			{
				$.getScript('./bootstrap/js/jquery.activemq.js',function(){
					amq = org.activemq.Amq;
				});
			}
			jsReady = true;
		}
		init();

		//启动心跳
		function start_alive_monitor()
		{
			if(alive_interval != null)
			{
				clearInterval(alive_interval);
			}
			alive_interval = setInterval(function (){
				if(last_alive_time !=0 && (get_now_time() - last_alive_time > 60) && !btn_logout)
				{
					login();
				}
			},20000)

		}

		//停止心跳
		function stop_alive_monitor()
		{
			clearInterval(alive_interval);
			alive_interval = null;
		}

		//得到当前时间戳
		function get_now_time()
		{
			var _time = Date.parse(new Date());
			return _time/1000;
		}


		//通过swf向后台发送数据
		function sendData(obj)
		{
			if(obj.Action)
			{
				if(obj.Action == AGMT_KEEP)
				{
					obj.Actid = '0';
				}
				else
				{
					obj.Actid = ''+actid;
					actid++;
				}
			}

			if(opts.connect_type == 'swf')
			{
				var commond = JSON.stringify(obj);
				swfobj.sendData(commond);
			}
			else if(opts.connect_type == 'amq')
			{
				amq.sendMessage(opts.amq_uri_send, obj);
			}
		}
		//返回swf状态
		function isReady(){
			return jsReady;
		}

		//		返回当前登录状态
		function logState()
		{
			return isLogin;
		}

		//设置flash状态为ok
		function set_swf_ready()
		{
			swfReady = true;
			var Browser = new Object();
			Browser.isIE = window.ActiveXObject ? true : false;
			if(Browser.isIE)
			swfobj = document.getElementById('wincall');
			else
			swfobj = document.getElementById('wincall_emded');
		}

		/*连接服务器*/
		function connect()
		{
			if(isConnect)
			{
				agentInit();
			}
			if(opts.connect_type == 'swf')
			{
				if (swfReady)
				{
					swfobj.connect(CTIIP);
					return true;
				}
				else
				{
					responce_message('098'); //flash未加载
					return false;
				}
			}
			else if(opts.connect_type == 'amq')
			{

				//监听消息
				amq.init({
					uri: opts.amq_uri_receive,
					logging: false,//输出日志
					timeout: 20,
					linkid: linkid,
					ctiip:CTIIP,//cti服务器ip
					//初始化成功返回
					sessionInitializedCallback: function(response){
						if(typeof response.id != 'undefined'){
							linkid = response.id;
						}
						agentInit();
					},
					//初始化失败返回消息
					sessionInitalizedError: function(){
						responce_message('10012');
					},
					//正常消息返回
					messageCallback:function(response){
						if(response.length == 0){
							return false;
						}
						msgResponce(response);
					},
					//断线重连处理
					reinit:function(){
						linkid = 0;
						connect();
					},
					//网络连接问题
					connectStatusHandler:function(netStatus, wincallStatus){
						//网络异常但是中间件还没有断开
						if(!netStatus && wincallStatus){
							responce_message('10015');
						}
						//网络异常且中间件断开
						else if(!netStatus && !wincallStatus){
							responce_message('10016');
						}
					}
				});
				//没有linkid则先申请
				if(linkid == 0)
				{
					sendData({});
				}
				else//否则直接初始化
				{
					agentInit();
				}
			}
		}

		/*		断开连接*/
		function disconnect()
		{
			isConnect	= false;
			btn_logout = true;
			isLogin		= false;
			if(opts.connect_type == 'swf')
			{
				//console.log(swfobj);
				swfobj.closesoc();
			}
			if(opts.phonetype == 'softphone')
			{
				close_softphone();
			}
			stop_alive_monitor();
			responce_message('099');//断开socket
		}
		/*启动软电话*/
		function start_softphone()
		{
			try
			{
				WshShell =new ActiveXObject('WScript.Shell');
				WshShell.Run('winkill linphoned.exe',0,true);
				WshShell.Run('linphonecsh init -c NUL -d 10 -l c:/ll.log',0,true);
				WshShell.Run('beepplay.exe -stop',0,true);
			}
			catch(e)
			{
				location.href='wincall.exe';
				return false;
			}
			if(opts.tel_server == '')
			{
				opts.tel_server = CTIIP;
			}
			setTimeout(function (){
				WshShell.Run('linphonecsh register --host '+opts.tel_server+':'+opts.tel_server_port+' --username '+opts.sip_prefix+''+AGENTPHONE+' --password '+pho_pwd,0,true);
			},1500);
			var register_info = setInterval(function (){
				var register_status = WshShell.Run('linphonecsh status register',0,true);
				if(register_status == 1)
				{
					WshShell.Run('linphonecsh generic "ec on"',0,true);
					clearInterval(register_info);
					clearTimeout(register_info2);
					responce_message('20001');//软电话注册成功
					sendData({Action:AGMT_LOGIN,State:QUEUESTATE,OnLine:OLMODE});
				}
			},500);

			var register_info2 = setTimeout(function (){
				var register_status = WshShell.Run("linphonecsh status register",0,true);
				if(register_status != 1)
				{
					clearInterval(register_info);
					if(opts.reconnect && !btn_logout && reconnect_time < opts.reconnect_limit)
					{
						reconnect_time ++;
						clearInterval(register_info);
						start_softphone();
						responce_message("20002");//软电话重新注册
						return;
					}
					responce_message('20003');//软电话注册失败
					agentUnInit();
				}
			},10000);
		}

		/*关闭软电话*/
		function close_softphone()
		{
			try{
				WshShell.Run("winkill linphoned.exe",0,true);
				WshShell.Run("beepplay.exe -stop",0,true);
			}
			catch(e){}
		}

		/*初始化*/
		function agentInit()
		{
			sendData({Action:AGMT_INIT,VccCode:VCCCODE,AgNum:AGENTNUM,AgPass:AGENTPASS,AgPhoneNum:AGENTPHONE});
		}

		/*反初始化*/
		function agentUnInit()
		{
			sendData({Action:AGMT_UNINIT});
			disconnect();
		}

		/*签入*/
		function agentLogin()
		{
			if(opts.phonetype=='softphone')
			{
				start_softphone();
			}
			else
			{
				sendData({Action:AGMT_LOGIN,State:QUEUESTATE,OnLine:OLMODE});
			}
		}

		/*签出*/
		function agentLogout()
		{
			sendData({Action:AGMT_LOGOUT});
		}

		/*保持连接 握手信号*/
		function agentKeepAlive()
		{
			sendData({Action:AGMT_KEEP});
		}

		/*设置坐席的主教和队列*/
		function set_ag_quecaller(json_str)
		{
			var json_obj =  eval("("+json_str+")");
			ag_ques = json_obj[0];
			ag_callers = json_obj[1];
			pho_pwd = json_obj[2];
		}

		/*******************************************************按钮事件 ***********************************************/
		/*登陆*/
		function login(_ctiIp,_vccCode,_agentNum,_agentPass,_agentPhone,_queState,_olMode)
		{
			CTIIP			= _ctiIp ? _ctiIp : CTIIP;
			VCCCODE		= _vccCode ? _vccCode : VCCCODE;
			AGENTNUM	= _agentNum ? _agentNum : AGENTNUM;
			AGENTPASS	= _agentPass ? _agentPass : AGENTPASS;
			AGENTPHONE	= _agentPhone ? _agentPhone : AGENTPHONE;
			QUEUESTATE	= _queState ? _queState : QUEUESTATE;
			OLMODE		= _olMode ? _olMode : OLMODE;
			connect();//连接服务器
		}

		/*签出*/
		function logout()
		{
			if(!isLogin)
			{
				return false;
			}

			btn_logout = true;
			agentLogout();
			agentUnInit();
		}

		/*查询状态*/
		function agentQuery()
		{
			if(!isLogin)
			{
				return false;
			}

			sendData({Action:AGMT_QUERY});
		}

		/*取得phone对象中的参数*/
		function fn_getParam(name)
		{
			if(!isLogin)
			{
				return false;
			}

			if(!name)
			{
				return obj_phone;
			}
			else
			{
				return obj_phone[name];
			}
		}

		/*呼叫坐席*/
		function dialinner(agentID,callerID)
		{
			if(!isLogin)
			{
				return false;
			}
			if(isCalling)
			{
				return false;
			}

			//如果 当前有通话状态 返回错误
			if(QUEUESTATE != QUESTATE_BUSY && QUEUESTATE != QUESTATE_UNBUSY && QUEUESTATE != QUESTATE_LOGON)
			{
				if(QUEUESTATE == QUESTATE_WAIT)
				{
					unbusy();
				}
				else
				{
					return false;
				}
			}

			sendData({Action:AGMT_DIALAG,AgID:agentID,CallerID:callerID});
		}

		/*呼叫外线*/
		function dialouter(called,callerID,groupID,outChan,proID,taskID)
		{
			if(!isLogin)
			{
				return false;
			}
			if(isCalling)
			{
				return false;
			}

			//如果 当前有通话状态 返回错误
			if(QUEUESTATE != QUESTATE_BUSY && QUEUESTATE != QUESTATE_UNBUSY && QUEUESTATE != QUESTATE_LOGON)
			{
				if(QUEUESTATE == QUESTATE_WAIT)
				{
					unbusy();
				}
				else
				{
					return false;
				}
			}
			called = called.replace(/[^0-9]/g,'');
			if(opts.dealnumber_before_call)
			{
					$.ajax({
					type:'GET',
					dataType:"jsonp",
					jsonp:"jsonpcallback",
					url: url+'/jsonp/dealnumber',
					data: 'number='+called+'&local_code='+local_code,
					success: function(responce){
						if(responce.code === 200)
						{
							called = responce.data.deal_number;
							called = called.replace(/[^0-9]/g,'');
							set_callrecord(called);
							sendData({Action:AGMT_DIALOUT,Called:called,CallerID:callerID,GroupID:groupID,OutChan:outChan,ProID:proID,TaskID:taskID});
						}
						else
						{
							called = called.replace(/[^0-9]/g,'');
							set_callrecord(called);
							sendData({Action:AGMT_DIALOUT,Called:called,CallerID:callerID,GroupID:groupID,OutChan:outChan,ProID:proID,TaskID:taskID});
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						set_callrecord(called);
						sendData({Action:AGMT_DIALOUT,Called:called,CallerID:callerID,GroupID:groupID,OutChan:outChan,ProID:proID,TaskID:taskID});
					}
				});
				return ;
			}
			set_callrecord(called);
			sendData({Action:AGMT_DIALOUT,Called:called,CallerID:callerID,GroupID:groupID,OutChan:outChan,ProID:proID,TaskID:taskID});
		}

		/*挂断*/
		function hangup()
		{
			if(!isLogin)
			{
				return false;
			}
			sendData({Action:AGMT_HANGUP});
		}

		/*接听*/
		function answer(ol_first_connect)
		{
			if(!isLogin)
			{
				return false;
			}

			if(OLMODE == OLMODEL_NONE)
			{
				if(opts.phonetype == 'softphone')
				{
					WshShell.Run('linphonecsh generic "answer"',0,true);
				}
			}
			else
			{
				if(opts.phonetype == 'softphone' && ol_first_connect)
				{
					WshShell.Run('linphonecsh generic "answer"',0,true);
				}
				else if(OLMODE == OLMODEL_AUTO)
				{
					sendData({Action:AGMT_OLANSWER});
				}
			}
		}

		/*咨询内线*/
		function consultinner(agentID,callerID)
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_CONSULTAG,AgID:agentID,CallerID:callerID});
		}

		/*咨询外线*/
		function consultouter(called,caller)
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}
			called = called.replace(/[^0-9]/g,'');
			if(opts.dealnumber_before_call)
			{
				$.ajax({
					type:'GET',
					dataType:"jsonp",
					jsonp:"jsonpcallback",
					url: url+'/jsonp/dealnumber',
					data: 'number='+called+'&local_code='+local_code,
					success: function(responce){
						if(responce.code === 200)
						{
							called = responce.data.deal_number;
							set_callrecord(called);
							sendData({Action:AGMT_CONSULTOUT,Called:called,Caller:caller});
						}
						else
						{
							set_callrecord(called);
							sendData({Action:AGMT_CONSULTOUT,Called:called,Caller:caller});
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						set_callrecord(called);
						sendData({Action:AGMT_CONSULTOUT,Called:called,Caller:caller});
					}
				});
				return ;
			}
			set_callrecord(called);
			sendData({Action:AGMT_CONSULTOUT,Called:called,Caller:caller});
		}

		/*咨询接回*/
		function consultback()
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_CONSULTBACK});
		}

		/*置忙*/
		function busy(reason)
		{
			if(!isLogin)
			{
				return false;
			}

			if(isCalling)
			{
				return false;
			}
            reason = parseInt(reason);
			sendData({Action:AGMT_BUSY,Reason:reason});
		}

		/*置闲*/
		function unbusy(reason)
		{
			if(!isLogin)
			{
				return false;
			}

			if(isCalling)
			{
				return false;
			}
			sendData({Action:AGMT_UNBUSY,Reason:reason});
		}

		/*保持*/
		function hold()
		{
			if( ! isLogin)
			{
				return false;
			}

			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_HOLD});
		}

		/*恢复*/
		function unhold()
		{
			if(!isLogin)
			{
				return false;
			}

			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_UNHOLD});
		}

		/*DTMF码*/
		function dtmf(num)
		{
			if(!isLogin)
			{
				return false;
			}
			//如果 当前不为有通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_DTMF,Dtmf:num});
		}

		/*转接*/
		function transfer()
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为有通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_TRANSFER});
		}

		/*三方*/
		function three_way()
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为有通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}
			sendData({Action:AGMT_3WAY});
		}

		/*三方接回*/
		function three_wayback()
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为有通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}

			sendData({Action:AGMT_3WAYBACK});
		}

		/*监听*/
		function chanspy(agentID)
		{
			if(!isLogin)
			{
				return false;
			}
			sendData({Action:AGMT_CHANSPY,AgID:agentID});
		}

		/*拦截*/
		function intercept()
		{
			if(!isLogin)
			{
				return false;
			}

			sendData({Action:AGMT_INTERCEPT});
		}

		/*强插*/
		function breakin()
		{
			if(!isLogin)
			{
				return false;
			}

			sendData({Action:AGMT_BREAKIN});
		}

		/*评价*/
		function evaluate()
		{
			if(!isLogin)
			{
				return false;
			}

			//如果 当前不为有通话状态 返回错误
			if( ! isCalling)
			{
				return false;
			}
			sendData({Action:AGMT_EVALUATE});
		}

		/*		进入自动外呼*/
		function autocallin(proID)
		{
			if(!isLogin)
			{
				return false;
			}

			sendData({Action:AGMT_ATIN,ProID:proID});
		}

		/*		退出自动外呼*/
		function autocallout()
		{
			if(!isLogin)
			{
				return false;
			}

			sendData({Action:AGMT_ATOUT});
		}

		/*		返回坐席所处队列的id*/
		function get_queue()
		{
			return ag_ques;
		}

		/*返回主叫号码*/
		function get_caller()
		{
			return ag_callers;
		}

		/*返回通话记录 - 最近拨打的10个号码  */
		function get_callrecord()
		{
			var callrecord = wincall_get_cookie(CALLRECORD_NAME);

			if( callrecord )
			{
				callrecord = decodeURIComponent(callrecord);
				callrecord = callrecord.split(",");
			}

			return callrecord;
		}

		/*记录通话记录 - 记录最近通话的号码(最近10个)  value:新增值 */
		function set_callrecord(value)
		{
			if( !value )
			{
				return true;
			}

			var call_record = get_callrecord();
			if( call_record )
			{
				var phone_length = call_record.length;
				call_record = call_record.join(",");

				//子串自一次出现的位置，不存在则返回 -1
				var stand_substring = call_record.indexOf(value);
				if( stand_substring > 0 )
				{
					call_record = call_record.replace(","+value,"");
					phone_length--;
				}

				if( stand_substring != 0   )
				{
					//原数据超过9个，则需要去除末尾一个
					if( phone_length > 9 )
					{
						call_record = call_record.substring(0,call_record.lastIndexOf(","));
					}

					call_record = value+","+call_record;
				}
			}
			else
			{
				call_record = value;
			}

			//更新cookie
			if( call_record )
			{
				call_record = encodeURIComponent(call_record);
				wincall_set_cookie(CALLRECORD_NAME,call_record,3);
			}

			return true;
		}

		/**
		* 写cookies函数
		**/
		function wincall_set_cookie(name,value)
		{
			var cookie_path = opts.cookie_path;
			var Days = arguments[2];
			if(Days>0)
			{
				var exp  = new Date();
				exp.setTime(exp.getTime() + Days*24*60*60*1000);
				document.cookie = name + "="+ escape (value) + ";path="+cookie_path+";expires=" + exp.toGMTString();
			}
			else
			{
				document.cookie = name + "="+ escape (value) + ";path="+cookie_path;
			}
		}

		/**
		* 取cookies函数
		**/
		function wincall_get_cookie(name)
		{
			var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
			if(arr != null) return unescape(arr[2]);
			return null;
		}

		/*******************************************************flash 结果返回 ***********************************************/
		function msgResponce(message){


			if(opts.connect_type == 'amq')
			{
				if(message.MT == 3)
				{
					$.each(message, function(property,value) {
						obj_phone[property] = value;
					});
				}
				var _responce = responce(message);
				responce_message(_responce);
			}
			else if(opts.connect_type == 'swf')
			{
				message = $.trim(message);
				var temp = message.split('\r\n');

				$.each(temp,function(n,objstr){
					var  obj_responce = eval("("+objstr+")");
					//电话状态缓存起来
					if(obj_responce.MT == 3)
					{
						$.each(obj_responce, function(property,value) {
							obj_phone[property] = value;
						});
					}
					var _responce = responce(obj_responce);
					responce_message(_responce);
				});
			}
		};

		/* swf 状态 事件 返回*/
		function responce(obj_responce){
			switch(obj_responce.MT)
			{
				case RETURN_FALSH :
				switch(obj_responce.Ret)
				{
					case 10010 : isConnect = true; agentInit(); btn_logout = false; return '10010';  //连接成功
					case 10011 : isConnect = false; isLogin = false;
					if(opts.reconnect && !btn_logout && reconnect_time < opts.reconnect_limit)
					{
						reconnect_time ++;
						if(reconnect_time > 1)
						{
							setTimeout(function (){
								login();
							},5000);
						}
						else
						{
							login();
						}
						return '096'; //系统重签入
					}
					disconnect();
					if(!btn_logout)
					{
						return '10011'; //与服务器断开
					}

					case 10012 : isConnect = false; isLogin = false;
					if(opts.reconnect && !btn_logout && reconnect_time < opts.reconnect_limit)
					{
						reconnect_time ++;

						if(reconnect_time > 1)
						{
							setTimeout(function (){
								login();
							},5000);
						}
						else
						{
							login();
						}
						return '096'; //系统重签入
					}
					return '10012'; //无法连接到服务器

					case 10013 : isConnect = false; isLogin = false;
					if(opts.reconnect && !btn_logout && reconnect_time < opts.reconnect_limit)
					{
						reconnect_time ++;
						if(reconnect_time > 1)
						{
							setTimeout(function (){
								login();
							},5000);
						}
						else
						{
							login();
						}
						return "096"; //系统重签入
					}
					return "097"; // 连接失败
					case 10014 : isConnect = false; isLogin = false; return "10014"; //发送数据错误
					default: return "FLASH_ERROR"+obj_responce.Ret;  //flash错误
				}
				break;
				case RETURN_ACTION :
				switch(obj_responce.Retact)
				{
					case AGMT_INIT ://初始化
					switch(obj_responce.Ret)
					{
						case 0 : set_ag_quecaller(obj_responce.Retmsg);
						setTimeout(function (){
							agentLogin();
						},500);
						return '001000';  //成功
						case 1 : disconnect();return '001001';   //   坐席已被初始化
						case 2 : disconnect();return '001002';   //   本socket已被初始化
						case 3 : disconnect();return '001003';   //   分机号不存在
						case 4 : disconnect();return '001004';   //   分机已有坐席使用
						case 5 : disconnect();return '001005';   //   有队列不存在
						case 6 : disconnect();return '001006';   //   已达登录上限
						case 7 : disconnect();return '001007';   //   分机类型错误
						case 20 : disconnect();return '001020';   //  企业错误
						case 801 : disconnect();return '001801';   //  连接数据库错误
						case 802 : disconnect();return '001802';   //  数据库名错误
						case 803 : disconnect();return '001803';   //  查询语句错误
						case 804 : disconnect();return '001804';   //  帐号错误
						case 805 : disconnect();return '001805';   //  找到多个坐席
						case 806 : disconnect();return '001806';   //  密码错误
						case 807 : disconnect();return '001807';   //  分机查询错误
						case 808 : disconnect();return '001808';   //  分机不存在
						case 809 : disconnect();return '001809';   //  找到多个分机
						default: disconnect();return '001'+obj_responce.Ret;   //其它初始化错误
					}
					break;
					case AGMT_UNINIT ://反初始化
					switch(obj_responce.Ret)
					{
						case 0 : return '002000';   // 成功
						case 1 : return '002001';   // 坐席尚未初始化
						default: return '002999';   //其它反初始化错误
					}
					break;
					case AGMT_LOGIN ://签入
					switch(obj_responce.Ret)
					{
						case 0 :
						case 2 : isLogin=true; reconnect_time =0; start_alive_monitor();return '003000';  //成功
						case 1 : agentUnInit();return '003001';  //坐席尚未初始化
						default: agentUnInit();return '003999';   //其它签入错误
					}
					break;
					case AGMT_LOGOUT ://签出
					switch(obj_responce.Ret)
					{
						case 0 : QUEUESTATE=QUESTATE_LOGOFF;agentUnInit();return '004000';  //成功
						case 1 : return '004001';  //坐席尚未初始化
						case 2 : return '004002';  //坐席尚未登录
						default: return '004999';  //其它签出错误
					}
					break;
					case AGMT_BUSY ://置忙
					switch(obj_responce.Ret)
					{
						case 3 : //已处于示忙
						case 0 : QUEUESTATE=QUESTATE_BUSY; return '005000'; //成功
						case 1 : return '005001';   //坐席尚未初始化
						case 2 : return '005002';   //坐席尚未签入
						case 4 : return '005004';   //不允许示忙
						default: return '005999';  //其它置忙错误
					}
					break;
					case AGMT_UNBUSY ://置闲
					switch(obj_responce.Ret)
					{
						case 3 : //已处于示闲
						case 0 : QUEUESTATE=QUESTATE_UNBUSY; return '006000'; //成功
						case 1 : return '006001';   //坐席尚未初始化
						case 2 : return '006002';   //坐席尚未登录
						default: return '006999';  //其它置闲错误
					}
					break;
					case AGMT_QUERY ://状态查询
					switch(obj_responce.Ret)
					{
						case 0 : return '007000';    //未登录
						case 1 : return '007001';   //已登录（空闲）
						case 2 : return '007002';   //示忙
						case 3 : return '007002';   //示忙
						case 4 : return '007004';   //占用
						case 5 : return '007005';   //事后处理
						case 10 :return '007010';  //尚未初始化
						default: return '007999';  //其它坐席状态查询错误
					}
					break;
					case AGMT_KEEP ://保持连接
					break;
					case AGMT_ATIN://进入自动外呼
					switch(obj_responce.Ret)
					{
						case 0 : return '009000';//成功
						case 1 : return '009001';//坐席尚未初始化
						case 2 : return '009002';//坐席尚未登录
						case 3 : return '009003';//项目错误
						case 4 : return '009004';//已进入
						default: return '009999';  //其它进入自动外呼错误
					}
					break;
					case AGMT_ATOUT://退出自动外呼
					switch(obj_responce.Ret)
					{
						case 0 : return '010000';//成功
						case 1 : return '010001';//坐席尚未初始化
						case 2 : return '010002';//坐席尚未登录
						case 3 : return '010003';//项目错误
						case 4 : return '010004';//已退出
						default: return '010999';  //其它退出自动外呼错误
					}
					break;
					case AGMT_DIALAG ://呼叫坐席
					switch(obj_responce.Ret)
					{
						case 0: return '021000';//呼叫成功
						case 1 : return '021001';   //主叫坐席错误
						case 2 : return '021002';   //被叫坐席错误
						case 3 : return '021003';   //队列错误
						case 4 : return '021004';   //队列不允许呼叫
						case 5 : return '021005';   //主叫状态不对
						case 6 : return '021006';   //被叫状态不空闲
						case 7 : return '021007';   //被叫坐席不属于本企业
						case 8 : return '021008';   //主叫号码不对
						case 20 : return '021020';   //企业错误(停用或过期等)
						case 30 : return '021030';   //常接通模式不能呼叫坐席
						case 451 :
						case 452 : return '021451';   //消息格式错误
						case 501 : return '021501';   //企业未启用
						case 502 : return '021502';   //企业已过期或未开通
						case 503 : return '021503';   //余额不足
						default: return '021999';  //其它呼叫坐席错误
					}
					break;
					case AGMT_DIALOUT ://呼叫外线
					switch(obj_responce.Ret)
					{
						case 0: return '022000';//呼叫成功
						case 1 : return '022001';  //主叫坐席错误
						case 2 : return '022002';  //呼叫错误
						case 3 : return '022003';   //队列错误
						case 4 : return '022004';   //队列不允许呼叫
						case 5 : return '022005';   //主叫状态不对
						case 6 : return '022006';   //无
						case 7 : return '022007';   //企业不匹配
						case 8 : return '022008';   //主叫号码错误
						case 9 : return '022009';   //常接通模式未接通
						case 10 : return '022010';   //通道错误
						case 20 : return '022020';   //企业错误(停用或过期等)
						case 451 :
						case 452 : return '022451';   //消息格式错误
						case 501 : return '022501';   //企业未启用
						case 502 : return '022502';   //企业已过期或未开通
						case 503 : return '022503';   //余额不足
						default: return '022999';  //其它呼叫外线错误
					}
					break;
					case AGMT_HANGUP ://挂机
					switch(obj_responce.Ret)
					{
						case 0 : return '023000';//挂机成功
						case 1 : return '023001';  //坐席错误
						case 2 : return '023002';  //不处于通话中
						default: return '023999';  //其它挂机错误
					}
					break;
					case AGMT_CONSULTAG ://咨询坐席
					switch(obj_responce.Ret)
					{
						case 0: return '024000';//咨询成功
						case 1 :return '024001';//主叫坐席错误
						case 2 :
						case 3 : return '024002';  //咨询坐席错误
						case 4 : return '024004';  //咨询坐席不空闲
						case 5 : return '024005';  //不在通话中
						case 6 : return '024006';  // 通话类型不对
						case 7 :
						case 8 : return '024007';  //通话异常
						case 9 : return '024009';  //已处于咨询或三方
						case 451 :
						case 452 : return '024451';   //消息格式错误
						case 501 : return '024501';   //企业未启用
						case 502 : return '024502';   //企业已过期或未开通
						case 503 : return '024503';   //余额不足
						default: return '024999';  //其它咨询坐席错误
					}
					break;
					case AGMT_CONSULTOUT ://咨询外线
					switch(obj_responce.Ret)
					{
						case 0: return '025000';//咨询成功
						case 1 : return '025001';  //主叫坐席错误
						case 2 :
						case 3 : return '025002';  //咨询坐席错误
						case 4 : return '025004';  //咨询坐席不空闲
						case 5 : return '025005';  //不在通话中
						case 6 : return '025006';  //通话类型不对
						case 7 :
						case 8 : return '025007';  //通话异常
						case 9 : return '025009';  //于咨询或三方
						case 20 : return '025020';  //  企业错误
						case 451 :
						case 452 : return '025451';   //消息格式错误
						case 501 : return '025501';   //企业未启用
						case 502 : return '025502';   //企业已过期或未开通
						case 503 : return '025503';   //余额不足
						default: return '025999'; //其它咨询外线错误
					}
					break;
					case AGMT_CONSULTBACK ://咨询接回
					switch(obj_responce.Ret)
					{
						case 0: return '026000';  //操作成功
						case 1 : return '026001';  //坐席错误
						case 2 : return '026002';  //没有通话
						case 3 : return '026003';  //通话异常
						case 4 : return '026004';  //不在咨询三方中
						case 5 : return '026005';  //咨询状态不对
						default: return '026999';  //其它咨询接回错误
					}
					break;
					case AGMT_3WAY ://三方
					switch(obj_responce.Ret)
					{
						case 0 : return '027000';//三方成功
						case 1 : return '027001';  //坐席错误
						case 2 : return '027002';  //没有通话
						case 3 : return '027003';  //通话异常
						case 4 : return '027004';  //不在咨询中
						case 5 : return '027005';  //咨询状态异常
						default: return '027999';  //其它三方错误
					}
					break;
					case AGMT_3WAYBACK ://三方接回
					switch(obj_responce.Ret)
					{
						case 0 : return '028000';  //操作成功
						case 1 : return '028001';  //坐席错误
						case 2 : return '028002';  //没有通话
						case 3 : return '028003';  //通话异常
						case 4 : return '028004';  //不在咨询中
						case 5 : return '028005';  //咨询状态异常
						default: return '028999';  //其它三方接回错误
					}
					break;
					case AGMT_TRANSFER ://转接
					switch(obj_responce.Ret)
					{
						case 0 : return '029000';//转接成功
						case 1 : return '029001';  //坐席错误
						case 2 : return '029002';  //没有通话
						case 3 : return '029003';  //通话异常
						case 4 : return '029004';  //不在咨询中
						case 5 : return '029005';  //咨询状态异常
						case 6 : return '029006';  //不允许转接外线
						default: return '029999';  //其它转接错误
					}
					break;
					case AGMT_CHANSPY ://监听
					switch(obj_responce.Ret)
					{
						case 0 : return '030000';//监听成功
						case 1 :
						case 3 : return '030001';  //监听坐席错误
						case 2 : return '030002';  //被监听坐席错误
						case 4 : return '030004';  //监听坐席状态不对
						case 5 : return '030005';  //不在通话中
						case 6 : return '030006';  //通话类型不对
						case 7 :
						case 8 : return '030007';  //通话异常
						case 9 : return '030009';  //已被监听或强插
						case 20 : return '030020';  //企业错误
						default: return '030999'; //其它监听错误
					}
					break;
					case AGMT_INTERCEPT ://拦截
					switch(obj_responce.Ret)
					{
						case 0 : return '031000';//拦截成功
						case 1 : return '031001';  //其它坐席错误
						case 2 :
						case 3 :
						case 4 :
						case 5 : return '031002';  //不在监听中
						default: return '031999';  //其它拦截错误
					}
					break;
					case AGMT_BREAKIN ://强插
					switch(obj_responce.Ret)
					{
						case 0 : return '032000';//强插成功
						case 1 :
						case 3 : return '032001';  //坐席错误
						case 4 :
						case 5 :
						case 6 : return '032003';  //不在监听或强插中
						default: return '032999';  //其它强插错误
					}
					break;
					case AGMT_HOLD ://保持
					switch(obj_responce.Ret)
					{
						case 0 : return '033000'; //成功
						case 1 : return '033001';  //坐席错误
						case 2 : return '033002';  //坐席不在通话中
						case 3 : return '003003';  //尚未接通
						case 4 : return '033004';  //已保持
						case 5 : return '033005';  //没有权限
						default: return '033999';  //其它保持错误
					}
					break;
					case AGMT_UNHOLD ://恢复
					switch(obj_responce.Ret)
					{
						case 0 : return "034000";  //恢复成功
						default: return "034999";   //其它恢复错误
					}
					break;
					case AGMT_EVALUATE ://转评价
					switch(obj_responce.Ret)
					{
						case 0 : return '041000';  //转评价成功
						case 1 : return '041001';  //坐席错误
						case 2 : return '041002';  //坐席不在通话中
						case 3 : return '041003';  //坐席不在通话中
						case 4 : return '041004';  //通话异常
						case 5 : return '041005';  //通话类型错误
						case 6 : return '041006';  //通话状态不对
						case 7 : return '041007';  //通道错误
						default: return '041999';  //其它转评价错误
					}
					break;
					case AGMT_OLANSWER://常接通模式应答
					switch(obj_responce.Ret)
					{
						case 0 : return '051000';//应答成功
						case 1 : return '051001';  //坐席错误
						case 2 : return '051002';  //不处于通话中
						case 3 : return '051003';  //非振铃状态
						case 20 : return '051020';  //企业错误
						case 30 : return '051030';  //常接通模式不允许
						default: return '051999';  //其它挂机错误
					}
					break;
					default: return "UNKNOWN_FUNCTION"+obj_responce.Retact;
				}
				break;
				case RETURN_CONNECT : //保持连接 握手型号
				agentKeepAlive();
				return '042000';
				case RETURN_PHONE :
				switch(obj_responce.PhoSta)
				{
					case PHRN_RING ://来电振铃
					case PHRN_OLRING ://常接通模式来电
					switch(obj_responce.RingType)
					{
						case 0://被叫
						if(opts.chassis_ringing)
						WshShell.Run("beepplay.exe -play",0,true);
						if(obj_responce.OAgId == 0)//外线直接转坐席
						{
							return '0508';
						}
						else//坐席内线来电
						{
							return '0500';
						}
						case 1:
						if(opts.chassis_ringing)
						WshShell.Run("beepplay.exe -play",0,true);
						set_callrecord(obj_phone.Caller);
						return '0501'; //队列分配
						case 2: return '0502';  //外呼时呼叫自己
						case 3: return '0503'; //监听时呼叫自己
						case 4:
						if(opts.chassis_ringing)
						WshShell.Run("beepplay.exe -play",0,true);
						return '0504';  //被咨询
						case 5:
						set_callrecord(obj_phone.Caller);
						return '0505';//自动外呼来电
						case 7:
						return '0507';//常接通模式呼叫自己
						default: return 'INCOMING_CALL'+obj_responce.RingType;  //来电事件
					}
					case PHRN_OLCONNECT:
					isCalling = true;
					return '0800';
					case PHRN_OLHANGUP:
					isCalling = false;
					return '0900';
					case PHRN_OLDISCONNECT:
					isCalling = false;
					return '4000';
					case PHRN_CONNECT ://接通
					isCalling = true;
					if(opts.chassis_ringing)
					WshShell.Run("beepplay.exe -stop",0,true);
					return '0600';
					case PHRN_CALLOUT ://外呼中
					return '0400';
					case PHRN_OTHERRING://对方振铃
					return '5500';
					case PHRN_HANGUP ://通话完成
					isCalling = false;
					if(opts.chassis_ringing)
					WshShell.Run("beepplay.exe -stop",0,true);
					obj_phone  = {};
					return '0000';
					case PHRN_SELFCONNECT: // 己方接通
					return '1000';
					case PHRN_CONSULTOUT: //咨询外呼中
					return '2100';
					case PHRN_CONSULTCONNECT://咨询接通
					return '2200';
					case PHRN_CONSULTBACK: //咨询接回
					return '2300';
					case PHRN_CONSULTFAIL://咨询未呼通
					return '2400';
					case PHRN_3WAYSUCCESS://三方接通
					return '2500';
					case PHRN_3WAYBACK://三方接回
					return '2600';
					case PHRN_CHANSPYSUCCESS://监听成功
					return '3100';
					case PHRN_BREAKINSUCCESS://强插成功
					return '3200';
					case PHRN_TRANSFERSUCCESS://转接成功
					return '3300';
					case PHRN_INTERCEPTSUCCESS://拦截成功
					return '3400';
					case PHRN_TRANSFER://电话转接 被转电话的坐席有此消息 转接成功
					return '3500';
					default: return 'UNKNOWN_PHONE_FUNCTION'+obj_responce.PhoSta; //其他电话事件
				}
				break;
				case RETURN_AGENT :
				switch(obj_responce.AgSta)
				{
					case AGSTRN_OLUNBUSY: //上线空闲--常接通模式下电话接通恢复到之前的状态
					QUEUESTATE=QUESTATE_UNBUSY;
					return '011';
					case AGSTRN_OLBUSY:	  //上线示忙--常接通模式下电话接通恢复到之前的状态
					QUEUESTATE=QUESTATE_BUSY;
					return '012';
					case AGSTRN_OFFLINE: //电话离线，常接通模式下电话未接通
					QUEUESTATE=QUESTATE_OFFLINE;
					return '007';
					case AGSTRN_SYSBUSY ://系统置忙
					QUEUESTATE=QUESTATE_BUSY;
					return '002';
					case AGSTRN_SYSUNBUSY ://系统置闲
					QUEUESTATE=QUESTATE_UNBUSY;
					return '003';
					case AGSTRN_SYSOCCUPY :	//系统占用
					QUEUESTATE=QUESTATE_INUSE;
					return '004';
					case AGSTRN_SYSAFTDEAL ://事后处理
					QUEUESTATE=QUESTATE_WAIT;
					return '005';
					default: 	//其他队列事件
					return 'UNKNOWN_QUE_FUNCTION'+obj_responce.AgSta;
				}
				break;
				case RETURN_QUEUE : //排队消息
				break;
				case RETURN_ERROR : //系统错误告警
				switch(obj_responce.Code)
				{
					case ERROR_LOGOFF://强制签出
					logout();
					return '024';
					break;

				}
				break;
				default : break;
			}
		};

		/*返回执行状态信息*/
		function responce_message(responce)
		{
			if(typeof callProcess == 'function')
			{
				callProcess(responce);
			}
		}
	};

	$.wincall = function(settings) {
		var options = {
			reconnect : true,
			reconnect_limit : 3,//重连次数限制
			chassis_ringing :false,//机箱振铃
			tel_server:'',//通讯服务器IP
			tel_server_port:'5060',//通讯服务器端口
			sip_prefix:'',//软电话号码前缀
			dealnumber_before_call:true,//呼叫前处理号码
			phonetype : 'telephone',//teltphone,softphone
			connect_type: connect_type,//通道连接方式 swf：flash  amq：消息队列（网络条件不好的时候用）
			amq_uri_receive: amq_uri_receive,
			amq_uri_send: amq_uri_send
		};
		$.extend(options, settings);
		var obj = new wincall(options);
		return obj;
	};

})(jQuery);

//实例对象
var wincall = null;

/****************对flash接口***************/
function isReady() {
	return wincall.isReady();
}

function setSWFIsReady() {
	wincall.set_swf_ready();
}

function remessage(responce){
	wincall.msgResponce(responce);
}