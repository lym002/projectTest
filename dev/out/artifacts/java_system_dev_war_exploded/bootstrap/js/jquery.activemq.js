// AMQ Ajax Adapter for jQuery
var org = org || {};
org.activemq = org.activemq || {};

org.activemq.AmqAdapter = {

	init: function(options) {
		this.options = options;
	},

	ajax: function(uri, options) {
		request = {
			url: uri,
			data: options.data,
			success: options.success || function(){},
			error: options.error || function(){}
		}
		var headers = {};
		if( options.headers ) {
			headers = options.headers;
		}

		if (options.method == 'post') {
			request.type = 'POST';
			headers[ 'Connection' ] = 'close';
		} else {
			request.type = 'GET';
			request.dataType = options.dataType || 'json';
		}

		if( headers ) {
			request.beforeSend = function(xhr) {
				for( h in headers ) {
					xhr.setRequestHeader( h, headers[ h ] );
				}
			}
		}
		//console.log(request);
		jQuery.ajax( request );
	},

	log: function(message, exception) {
		if (typeof console != 'undefined' && console.log) console.log(message);
	}
};

// AMQ Ajax handler
// This class provides the main API for using the Ajax features of AMQ. It
// allows JMS messages to be sent and received from javascript when used
// with the org.apache.activemq.web.MessageListenerServlet.
//
// This version of the file provides an adapter interface for the jquery library
// and a namespace for the Javascript file, private/public variables and
// methods, and other scripting niceties. -- jim cook 2007/08/28
org.activemq.Amq = function() {
	var connectStatusHandler;

	// Just a shortcut to eliminate some redundant typing.
	var adapter = org.activemq.AmqAdapter;

	if (typeof adapter == 'undefined') {
		throw 'An org.activemq.AmqAdapter must be declared before the amq.js script file.'
	}

	// The URI of the AjaxServlet.
	var uri;

	// The number of seconds that the long-polling socket will stay connected.
	// Best to keep this to a value less than one minute.
	var timeout;

	// A session should not be considered initialized until the JSESSIONID is returned
	// from the initial GET request.  Otherwise subscription POSTS may register the
	// subscription with the wrong session.
	var sessionInitialized = false;

	// This callback will be called after the first GET request returns.
	var sessionInitializedCallback;

	//This callback will be called after the first GET request didnot return anything.
	var sessionInitalizedError;

	// This callback will be called after the every GET request returns.
	var messageCallback;

	// Poll delay. if set to positive integer, this is the time to wait in ms
	// before sending the next poll after the last completes.
	var pollDelay;

	// Inidicates whether logging is active or not. Not by default.
	var logging = false;

	// 5 second delay if an error occurs during poll. This could be due to
	// server capacity problems or a timeout condition.
	var pollErrorDelay = 5000;

	// Map of handlers that will respond to message receipts. The id used during
	// addListener(id, destination, handler) is used to key the callback
	// handler.
	var messageHandlers = {};

	// Indicates whether an AJAX post call is in progress.
	var batchInProgress = false;

	// A collection of pending messages that accumulate when an AJAX call is in
	// progress. These messages will be delivered as soon as the current call
	// completes. The array contains objects in the format { destination,
	// message, messageType }.
	var messageQueue = [];

	// String to distinguish this client from others sharing the same session.
	// This can occur when multiple browser windows or tabs using amq.js simultaneously.
	// All windows share the same JESSIONID, but need to consume messages independently.
	var linkid = null;
	//重连操作的回掉函数
	var reinit = null;
	var ctiip = '';//cti服务器ip
	//定时器
	var executeTimeid = 0;
	//网络连接失败次数
	var netErrorTimes = 0;

	var errorHandler = function(xhr, status, ex) {
		connectStatusHandler(false);
		if (logging) adapter.log('Error occurred in ajax call. HTTP result: ' +
		xhr.status + ', status: ' + status);
	}

	var pollErrorHandler = function(xhr, status, ex) {
		netErrorTimes++;
		if(netErrorTimes < 11)//网络异常，但是后台还没有识别到断开
		connectStatusHandler(false, true);
		else//网络异常，后台也断开
		connectStatusHandler(false, false);
		clearTimeout(executeTimeid);
		executeTimeid = 0;

		if (status === 'error' && xhr.status === 0) {
			if (logging) adapter.log('Server connection dropped.');
			setTimeout(function() { sendPoll(); }, pollErrorDelay);
			return;
		}
		if (logging) adapter.log('Error occurred in poll. HTTP result: ' +
		xhr.status + ', status: ' + status);
		setTimeout(function() { sendPoll(); }, pollErrorDelay);
	}

	var pollHandler = function(data) {
		var res = data.body || {}
		//重置失败次数
		netErrorTimes = 0;
		clearTimeout(executeTimeid);
		executeTimeid = 0;
		try {
			messageCallback(res);
		} catch(e) {
			if (logging) adapter.log('Exception in the poll handler: ' + res, e);
			throw(e);
		} finally {
			if( typeof data.linkstate != 'undefined' && data.linkstate == 0){
				setTimeout(sendPoll, pollDelay);
			}
			//网络断开后服务器识别客户端已下线，当网络恢复后重新连接
			else if( typeof data.linkstate != 'undefined' && data.linkstate == 1){
				//从新初始化
				linkid = 0;
				sessionInitialized = false;
				reinit();
			}
			//返回为空,则认为掉线
			else if( typeof data.linkstate != 'undefined' && data.linkstate == 999){
				connectStatusHandler(false, false);
			}
		}
	};

	var initHandler = function(data) {
		data = data.body;
		//重置失败次数
		netErrorTimes = 0;
		//返回linkid
		if(data && typeof data.Ret != 'undefined'){
			linkid = data.id;
			sessionInitialized = true;
			if(sessionInitializedCallback) {
				sessionInitializedCallback(data);
			}
			sendPoll();
		}
		else{
			if(sessionInitalizedError){
				sessionInitalizedError();
			}
		}
	}

	var sendPoll = function() {
		// Workaround IE6 bug where it caches the response
		// Generate a unique query string with date and random
		var now = new Date();
		var timeoutArg = sessionInitialized ? timeout : 5;
		var data = {};
		data.timeout = timeoutArg;
		var successCallback = sessionInitialized ? pollHandler : initHandler;

		var options = { method: 'get',
		data: addLinkid( data ),
		success: successCallback,
		error: pollErrorHandler,
		timeout:20000,
		dataType:'jsonp',
		jsonp:'callback',
		jsonpCallback:'jsonpcallback'};
		adapter.ajax(uri, options);
		//处理在网络掉线的情况下无法返回，则自己设置一个定时器来处理
		if(executeTimeid == 0){
			executeTimeid = setTimeout(function(){sendPoll();}, 30000);
		}
	};

	var sendJmsMessage = function(uri, message, type, headers) {
		var msg     = {};
		msg.content = JSON.stringify(message);
		msg.type    = type;
		// Add message to outbound queue
		if (batchInProgress) {
			messageQueue[messageQueue.length] = {message:msg, headers:headers};
		} else {
			org.activemq.Amq.startBatch();
			adapter.ajax(uri, { method: 'get',
			data: addLinkid( msg ),
			dataType:'jsonp',
			jsonp:'callback',
			jsonpCallback:'jsonpcallback',
			error: errorHandler,
			headers: headers,
			success: org.activemq.Amq.endBatch});
		}
	};

	// add linkid to data if it exists, before passing data to ajax connection adapter.
	var addLinkid = function( data ) {
		data.linkid = linkid;
		data.ctiip = ctiip;
		return data;
	}

	return {
		// optional clientId can be supplied to allow multiple clients (browser windows) within the same session.
		init : function(options) {
			connectStatusHandler = options.connectStatusHandler || function(connected){
				if(!connected){
					if (logging) adapter.log('网络断开连接.');
				}
			};
			uri = options.uri;
			pollDelay = typeof options.pollDelay == 'number' ? options.pollDelay : 0;
			timeout = typeof options.timeout == 'number' ? options.timeout : 25;
			logging = options.logging;
			sessionInitializedCallback = options.sessionInitializedCallback;
			sessionInitalizedError = options.sessionInitalizedError;
			messageCallback = options.messageCallback;
			reinit = options.reinit || function(){};
			linkid = options.linkid;
			ctiip = encodeURIComponent(options.ctiip);
			adapter.init(options);
			sendPoll();
		},

		startBatch : function() {
			batchInProgress = true;
		},

		endBatch : function(data) {
			batchInProgress = false;
		},

		// Send a JMS message to a destination (eg topic://MY.TOPIC).  Message
		// should be xml or encoded xml content.
		sendMessage : function(uri, message, messageType) {
			sendJmsMessage(uri, message, messageType);
		},

		// Listen on a channel or topic.
		// handler must be a function taking a message argument
		//
		// Supported options:
		//  selector: If supplied, it should be a SQL92 string like "property-name='value'"
		//            http://activemq.apache.org/selectors.html
		//
		// Example: addListener( 'handler', 'topic://test-topic', function(msg) { return msg; }, { selector: "property-name='property-value'" } )
		addListener : function(id, destination, handler, options) {
			messageHandlers[id] = handler;
			var headers = options && options.selector ? {selector:options.selector} : null;
			sendJmsMessage(destination, id, 'listen', headers);
		},

		// remove Listener from channel or topic.
		removeListener : function(id, destination) {
			messageHandlers[id] = null;
			sendJmsMessage(destination, id, 'unlisten');
		},

		// for unit testing
		getMessageQueue: function() {
			return messageQueue;
		},
		testPollHandler: function( data ) {
			return pollHandler( data );
		}
	};
}();