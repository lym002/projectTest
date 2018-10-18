/*配置文件*/
var ag_num = "";
var agentPhone = '';
if (typeof (ag_num_value) == 'undefined') {
    var str = window.location.hash; //通过url 获取；
    str = str.substr(1,str.length);
    var arr = str.split('&');
    eval(arr[0]); // ag_num;
    eval(arr[1]); // agentPhone;
} else {
    ag_num = ag_num_value;
    agentPhone = ag_phone_value+'';
}
//eval(arr[2]); // agentPhone;
var power_phone_view = 1;//是否显示号码
var ip = '192.168.1.250'; //服务器 登陆IP
var tel_port = '5091'; //服务器 登陆IP
var vcc_code = 'js'; //企业代码
var url = "http://192.168.1.250/manage/web/wintelapi/api";//通过jsonp获取坐席相关信息
//var url = "http://192.168.1.36:82/wintelapi/web";//通过jsonp获取坐席相关信息
var is_load = 1; //是否自动加载；1是自动；其他非自动
var agentID = '';//这个得通过ajax获取
var agentNum = '';
var vccID = '';
var agentName = '';
var agentPhoneID = '';
var agentPhoneType = '';
var queueID = '';
var queueState = '1';
var outPage = 'http://www.baidu.com';//外呼弹屏地址
var inpop_page = 'http://www.baidu.com';//来电弹屏地址
var cc_phone = '';
var agentPass = '';
var connect_type = 'swf'; //通道连接方式 swf：flash  amq：消息队列（网络条件不好的时候用）
var amq_uri_receive = url+'/jsonp/consumer'; //接收接口
var amq_uri_send = url+'/jsonp/producer'; //发送接口
var last_call = {callnum:'',callid:''};
var que_lists = [];

/*开关*/
var BUTTON_OFF = 2;
var BUTTON_ON =  1;
var error_timeout = null;

//定义常量
var MOUSE_ON = 3;
var OLMODEL_NONE = 0;//无长接通
var OLMODEL_AUTO = 1;//长接通
var OLMODEL_AUTOANSWER = 2;//长接通并自动接听
var QUESTATE_LOGON = 1;//队列已登录/空闲
var user_ol_model = 0;
var default_caller	= ''; //默认主叫
var default_que	= ''; //默认队列
var user_login_state = '';
var user_channel = 0; //外呼通道；
var user_phone_type = "telephone";//话机类型
var local_code = "021";  //本地区号；
