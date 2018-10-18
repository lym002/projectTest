var wsUri = "";//"ws://192.168.27.10:5061/";
var output;
var docheckin = 0;
var doCalling = 0;
var reConNum = 0;
var lincmdsn;
var cc = 0;
var str1 = '{"cmdsn":"200","seatno":"1001","caller":"","para":"","cmd":"0"}';
var stateValue = new Array();
var heartbeat_timer,igetline_timer;
var stateName = [
{
    "0": "签出",
    "1": "空闲",
    "2": "振铃",
    "3": "通话中",
    "4": "话后",
    "5": "分机忙",
    "11": "小休",
    "12": "会议",
    "13": "培训",
    "14": "洗手",
    "15": "就餐",
    "16": "话后",
    "98": "分机忙",
    "99":"话后"
}
];

stateValue["-1"] = "需要重新连接";
stateValue["-2"] = "状态错误";
stateValue["-3"] = "坐席工号错误";
stateValue["-4"] = "登录密码错误";
stateValue["-5"] = "未签入上班";
stateValue["-6"] = "被叫号码非法";
stateValue["-7"] = "呼叫没有空闲线路异常";
stateValue["-8"] = "数据库错误";
stateValue["-9"] = "其他未知错误";
stateValue["-10"] = "坐席已在其他浏览器签入";
stateValue["-11"] = "坐席分机号码在忙或者没注册";
stateValue["-12"] = "呼叫用户号码失败";
stateValue["-13"] = "呼叫缓存区超出100个";
stateValue["-14"] = "坐席没分配队列不能签入上班";
stateValue["-15"] = "被转接的坐席不是空闲,无法转接";
function init() {
    if ($("#txtwsUri").val() == "") {
        outms("未设置通话服务器");
    }
    else {
        wsUri = $("#txtwsUri").val();
        InitSocket();    
    }
    cc = 0;

}
function InitSocket() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function (evt) { onOpen(evt) };
    websocket.onclose = function (evt) { onClose(evt) };
    websocket.onmessage = function (evt) { onMessage(evt) };
    websocket.onerror = function (evt) { onError(evt) };


}
function onOpen(evt) {
    outms("连接成功");
    heartbeat_timer = setInterval(function () { keepalive(websocket) }, 5000);
    //igetline_timer = setInterval(function () { getlineup() }, 3000);
    if (docheckin == 1) {
        checkIn();
    }
}
function onClose(evt) {
    cc = 6;
    outms("连接关闭");
    if (docheckin == 0) {
        clearInterval(heartbeat_timer);
    }
    goClose("离线");
}
function onError(evt) {
    cc = 6;
    //alert("连接已关闭,请刷新页面重新连接,如刷新后仍未连接请联系管理员检查服务器状态.");
    outms("连接已关闭");
    //this.outms('<span style="color: red;">ERROR:</span> ' + evt.data);
    goClose("离线");

}
function doSend(message) { websocket.send(message); }
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}


function OnConnect() {
    wsUri = document.getElementById("url").value;
    init();
}
function DisConnect() {
    if (websocket != null) {
        websocket.close();
        websocket = null;
    }
}
function keepalive(ws) {
    cc++;
    if (docheckin == 1 && cc > 6) {
        cc = 3;
        DisConnect();
        InitSocket();
        return;
    }
    ws.send('{}');
}

window.onbeforeunload = function () {
    DisConnect();
}

function outms(str) {
    $("#showstate").html(str);
}
function newphone(t, s) {
    var sl = s.split(";");
    var slr = "";
    if (sl.length >= 2) {
        slr = sl[2].split("=")[1];
    }
    $("#txtNtel").val(t);
    //$("#call_in").html("当前来电:" + t + "," + slr);
}

//示忙示闲设置
//para:0示闲1示忙
function setbusyState(v) {
    str1 = '{"cmdsn":"123","seatno":"' + $("#txtseatno").val() + '","caller":"1001","para":"' + v + '","cmd":"5"}';
    websocket.send(str1);
}
function setbusyState1(v, seatno) {
    str1 = '{"cmdsn":"123","seatno":"' + seatno + '","caller":"1001","para":"' + v + '","cmd":"5"}';
    websocket.send(str1);
}
function onMessage(evt) {
    cc = 0;
    console.info(evt.data);
    if (evt.data.length > 4) {

        var callercalledinfo = '';
        var json = JSON.parse(evt.data);

        /*   回调弹屏说明:
             {"cmdsn":"123456789","seatno":"1004","caller":"13606060253","para":"05925961526","state":"0"}
             para :被叫号码
            state=0表示 操作成功
            =1 用户拨入弹屏
            =2 外拨用户弹屏
            =3 用户回电,坐席在忙的弹屏
        
            state<0 表示错误：
            -1：socket 断开，需要重新连接
            -2：状态错误，比如发起一个呼叫未结束又发起一个呼叫
            -3：坐席工号错误
            -4：登录密码错误
            -5：未签入上班，无法进行其他操作
            -6: 被叫号码非法
            -7: 呼叫没有空闲线路异常
            -10: 坐席已在其他浏览器签入,不能从当前浏览器迁出
            -11: 坐席分机号码在忙或者没注册
            -12: 呼叫用户号码失败,可能是没注册上落地网关或者落地网关返回错误.
            -13:被操作的坐席状态不对,不在服务状态,导致班长控制操作失败.
            -14: 坐席没分配队列不能签入上班
            -15:被转接的坐席不是空闲,无法转接.
            -8：数据库错误
            -9：其他未知错误
            
        业务--websocket--YC中间件--esl--freeswitch===voip网关==E1==运营商
                                         |
                                        IP话机  
            */
        if (json.state == 0) //ok
        {
            paraV = json.para;
            stateNow = json.agstate;
            paraVr = paraV.split(",");
            paraNum = paraV.split(".");
            $("#state").text(stateName[0][stateNow])
            if (paraNum.length > 1) {
                alert(paraV);
            }
            if (paraVr.length > 3)
            {
                if (paraVr[0].split("=")[1] == "0") {
                    $("#txtLinupVal").val("");
                }
                cmdsn = json.cmdsn;
                if (lincmdsn == cmdsn) {
                    $("#txtLinupVal").val($("#txtLinupVal").val() + paraV + "|");
                }
                else {
                    lincmdsn = cmdsn;
                    $("#txtLinupVal").val(paraV + "|");
                }
            }
            if (paraVr.length > 1) {
                $("#paiduinum").html(paraVr[0].split("=")[1]);
            }
            else {
                if (docheckin == 1) {
                    goStart();
                    outms('<span style="color: green;">提示:</span> 坐席=' + json.seatno + ',签入成功!');
                    if (doCalling == 1) {
                        setButtonState(1, 1, doCalling);
                    }
                    else {
                        setButtonState(1, 0, doCalling);
                    }
                }
                else {
                    outms('<span style="color: green;">提示:</span> 坐席=' + json.seatno + ',签出成功!');
                    goClose("签出");
                    setButtonState(0, 0, 0);
                }
            }
        }
        if (json.state < 0) //fail
        {
            outms('<span style="color: green;">提示:</span> 出错了:' + stateValue[json.state]);
        }
        if (json.state == 1) //user callin 
        {
            //alert(json.para+"-"+json.caller+"-"+json.seatno);
            //$("#targetframe").attr({"src":"customer.do?ismine=1&tel="+json.caller});
            //var ex = json.ex;
            //var exar = ex.split(";");
            //if (exar.length >= 3) {
            doCalling = 1;
            newphone(json.caller, json.ex);
            //}
            newcall(json.caller, json.ex);
            //this.outms('有新来电');
        }
        if (json.state == 2) //station callout
        {
            doCalling = 1;
            callercalledinfo = '<span style="color: red;">callou:</span>cmdsn=' + json.cmdsn + ', state=' + json.state + ',seatno=' + json.seatno + ',caller=' + json.caller + ',called=' + json.para;
            outms(callercalledinfo);
            newOutcall(json.para, json.ex);
        }
        if (json.state == 3) {
            s60Stimer();
        }
        if (json.state == 4) {
            doCalling = 0;
            s60Etimer();
        }
    }

}

//----------------------------------------------------------------------------------------------------------

function loginServer(url) {
    wsUri = url;
    init();
}
function checkIn() {
    if (checkLink()) {
        str1 = '{"cmdsn":"100","seatno":"' + $("#txtseatno").val() + '","caller":"' + $("#txtstation").val() + '","para":"' + $("#txtpassowrd").val() + '","cmd":"1"}';
        websocket.send(str1);
        docheckin = 1;
    }
}
function checkOut() {
    str1 = '{"cmdsn":"101","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"2"}';
    docheckin = 0;
    websocket.send(str1);
}
function makeCall(tel, externtion) {
    doCalling = 1;
    str1 = '{"cmdsn":"102","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + tel + '","cmd":"3"}';
    websocket.send(str1);
}
function dropCall() {
    doCalling = 0;
    str1 = '{"cmdsn":"103","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"4"}';
    websocket.send(str1);
}
function SetSeatState(seatno, seatstate) {
    str1 = '{"cmdsn":"105","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + seatstate + '","cmd":"5"}';
    websocket.send(str1);
}

function GetGWLines(seatno) {
    str1 = '{"cmdsn":"106","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"6"}';
    websocket.send(str1);
}

function GetUserqLines(seatno) {
    str1 = '{"cmdsn":"108","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"8"}';
    websocket.send(str1);
}

function checkLink() {
    var result = true;
    if (websocket == null) {
        result = false;
    }
    else {
        if (websocket.readyState == null) {
            result = false;
        }
        else {
            if (websocket.readyState != 1) {
                result = false;
            }
        }
    }
    if (!result) {
        InitSocket();
    }
    return result;
}
//获取队列数
function getlineupList() {
    //alert(this.checkLink());
    if (checkLink()) {
        var r = new Date().getTime();
        str1 = '{"cmdsn":"122'+r+'","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"8"}';
        websocket.send(str1);
    }
}

//获取分组队列
function getlineup() {
    //alert(this.checkLink());
    if (checkLink()) {
        str1 = '{"cmdsn":"123","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"","cmd":"28"}';
        websocket.send(str1);
    }
}

function Keep(callertel, p) {
    str1 = '{"cmdsn":"113","seatno":"' + $("#txtseatno").val() + '","caller":"' + callertel + '","para":"' + p + '","cmd":"13"}';
    websocket.send(str1);
}

function onCallinCallouRing(callercalledinfo) {
    outms(callercalledinfo);
}

//by ctt-转技能组
function gotoGroup(groupid) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + groupid + '","cmd":"9"}';
    websocket.send(str1);
}
//转评价
function Callback(callnum) {
    doCalling = 0;
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + callnum + '","cmd":"10"}';
    websocket.send(str1);
}
function Callauther(callnum) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + callnum + '","cmd":"11"}';
    websocket.send(str1);
}
//转座席
function gotoStation(s) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + s + '","cmd":"12"}';
    websocket.send(str1);
}

function quittreetalk(callno) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + callno + '","cmd":"14"}';
    websocket.send(str1);
}

function getSysNum() {
    str1 = '{"cmdsn":"12345678","seatno":"1001","caller":"","para":"","cmd":"27"}';
    websocket.send(str1);
}
function checkLink() {
    var result = true;
    if (websocket == null) {
        result = false;
    }
    else {
        if (websocket.readyState == null) {
            result = false;
        }
        else {
            if (websocket.readyState != 1) {
                result = false;
            }
        }
    }
   
    return result;
}

//班长台功能:
function checkInTelno(seatno, telno) {
    str1 = '{"cmdsn":"100","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + telno + '","cmd":"21"}';
    websocket.send(str1);

}
function checkOutTelno(seatno, telno) {
    str1 = '{"cmdsn":"101","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + telno + '","cmd":"22"}';
    websocket.send(str1);
}
//监听
function MonitorTalk(opseatno) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + opseatno + '","cmd":"23"}';
    websocket.send(str1);
}
//强插
function ThreeTalk(seatno) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + seatno + '","cmd":"24"}';
    websocket.send(str1);
}
function KillTalk(seatno) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + seatno + '","cmd":"25"}';
    websocket.send(str1);
}
function RobTalk(seatno) {
    str1 = '{"cmdsn":"109","seatno":"' + $("#txtseatno").val() + '","caller":"","para":"' + seatno + '","cmd":"26"}';
    websocket.send(str1);
}