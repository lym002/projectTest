$.ajaxSetup({ 
	beforeSend:function(XMLHttpRequest){
	    $.messager.progress({ 
			title:"请稍后", 
			msg:"页面加载中..."
		});
	},
	error: function (XMLHttpRequest, textStatus, errorThrown){
		if(XMLHttpRequest.status==403){
			$.messager.alert('我的消息', '您没有权限访问此资源或进行此操作！', 'success');
			return false;
		}
	}, 
	complete:function(XMLHttpRequest,textStatus){
		$.messager.progress("close");
		var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus， 
		if(sessionstatus=='timeout'){ 
			//如果超时就处理 ，指定要跳转的页面 
			var top = getTopWinow(); //获取当前页面的顶层窗口对象
			$.messager.alert('我的消息', '登录超时-请重新登录！', 'info');
			top.location.href = "http://"+window.location.host+"/user/toLogin.do"; //跳转到登陆页面
		} 
	} 
});
/** 
* 在页面中任何嵌套层次的窗口中获取顶层窗口 
* @return 当前页面的顶层窗口对象 
*/
function getTopWinow(){ 
	var p = window; 
	while(p != p.parent){ 
	p = p.parent; 
	} 
	return p; 
}

function fmoney(s, n) {
	if(parseFloat(s + "")){
		n = n > 0 && n <= 20 ? n : 2;
	    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	    var l = s.split(".")[0].split("").reverse(),
	        r = s.split(".")[1];
	    t = "";
	    for (var i = 0; i < l.length; i++) {
	        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	    }
	    return t.split("").reverse().join("") + "." + r;
	}else {
		if(s=="0"){
			return 0.00;
		}
		return s;
	}
};

/**将数字转换成大写人民币**/
function cmycurd(num){  //转成人民币大写金额形式
  var str1 = '零壹贰叁肆伍陆柒捌玖';  //0-9所对应的汉字
  var str2 = '万仟佰拾亿仟佰拾万仟佰拾元角分'; //数字位所对应的汉字
  var str3;    //从原num值中取出的值
  var str4;    //数字的字符串形式
  var str5 = '';  //人民币大写金额形式
  var i;    //循环变量
  var j;    //num的值乘以100的字符串长度
  var ch1;    //数字的汉语读法
  var ch2;    //数字位的汉字读法
  var nzero = 0;  //用来计算连续的零值是几个
 
  num = Math.abs(num).toFixed(2);  //将num取绝对值并四舍五入取2位小数
  str4 = (num * 100).toFixed(0).toString();  //将num乘100并转换成字符串形式
  j = str4.length;      //找出最高位
  if (j > 15){return '溢出';}
  str2 = str2.substr(15-j);    //取出对应位数的str2的值。如：200.55,j为5所以str2=佰拾元角分
 
  //循环取出每一位需要转换的值
  for(i=0;i<j;i++){
    str3 = str4.substr(i,1);   //取出需转换的某一位的值
    if (i != (j-3) && i != (j-7) && i != (j-11) && i != (j-15)){    //当所取位数不为元、万、亿、万亿上的数字时
   if (str3 == '0'){
     ch1 = '';
     ch2 = '';
  nzero = nzero + 1;
   }
   else{
     if(str3 != '0' && nzero != 0){
       ch1 = '零' + str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
  }
  else{
    ch1 = str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
        }
   }
}
else{ //该位是万亿，亿，万，元位等关键位
      if (str3 != '0' && nzero != 0){
        ch1 = "零" + str1.substr(str3*1,1);
        ch2 = str2.substr(i,1);
        nzero = 0;
      }
      else{
     if (str3 != '0' && nzero == 0){
          ch1 = str1.substr(str3*1,1);
          ch2 = str2.substr(i,1);
          nzero = 0;
  }
        else{
    if (str3 == '0' && nzero >= 3){
            ch1 = '';
            ch2 = '';
            nzero = nzero + 1;
       }
       else{
      if (j >= 11){
              ch1 = '';
              nzero = nzero + 1;
   }
   else{
     ch1 = '';
     ch2 = str2.substr(i,1);
     nzero = nzero + 1;
   }
          }
  }
   }
}
    if (i == (j-11) || i == (j-3)){  //如果该位是亿位或元位，则必须写上
        ch2 = str2.substr(i,1);
    }
    str5 = str5 + ch1 + ch2;
   
    if (i == j-1 && str3 == '0' ){   //最后一位（分）为0时，加上“整”
      str5 = str5 + '整';
    }
  }
  if (num == 0){
    str5 = '零元整';
  }
  return str5;
} 

function searchForm(queryForm,datagridId){
	var o = {};
	 $.each($("#queryForm").form().serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
	 $(datagridId).datagrid('load', o);
}

// 两个浮点数求和
function accAdd(num1,num2){
   var r1,r2,m;
   try{
       r1 = num1.toString().split('.')[1].length;
   }catch(e){
       r1 = 0;
   }
   try{
       r2=num2.toString().split(".")[1].length;
   }catch(e){
       r2=0;
   }
   m=Math.pow(10,Math.max(r1,r2));
   // return (num1*m+num2*m)/m;
   return Math.round(num1*m+num2*m)/m;
}

// 两个浮点数相减
function accSub(num1,num2){
   var r1,r2,m;
   try{
       r1 = num1.toString().split('.')[1].length;
   }catch(e){
       r1 = 0;
   }
   try{
       r2=num2.toString().split(".")[1].length;
   }catch(e){
       r2=0;
   }
   m=Math.pow(10,Math.max(r1,r2));
   n=(r1>=r2)?r1:r2;
   return (Math.round(num1*m-num2*m)/m).toFixed(n);
}
// 两数相除
function accDiv(num1,num2){
   var t1,t2,r1,r2;
   try{
       t1 = num1.toString().split('.')[1].length;
   }catch(e){
       t1 = 0;
   }
   try{
       t2=num2.toString().split(".")[1].length;
   }catch(e){
       t2=0;
   }
   r1=Number(num1.toString().replace(".",""));
   r2=Number(num2.toString().replace(".",""));
   return (r1/r2)*Math.pow(10,t2-t1);
}
//计算散标收益
function calShouyi(repayment,rate,deadline,amount){
	if(repayment == 0){
		var monthRate = parseFloat(rate.toString())/100/12;
		var cf = Math.pow((1 + monthRate), deadline);
		var monthAmount = amount * monthRate * cf / (cf - 1);//月还本息
		var allAmount = monthAmount*deadline;
		return fmoney(allAmount - amount,2);
	}else{
		return fmoney(amount*parseFloat(rate)/100/12*deadline,2);
	}
}

//格式金额，标红
function styleColor(value,row,index){return 'color:red';}
function formatAmount(value,rows){
	return fmoney(value,2);
}

/*easyUi 显示时间*/  
function formatDateBoxFull(value) {  
    if (value == null || value == '') {  
        return '';  
    }  
    var dt = parseToDate(value);  
    return dt.format("yyyy-MM-dd hh:mm:ss");  
}  
function parseToDate(value) {  
    if (value == null || value == '') {  
        return undefined;   
    }  
  
    var dt;  
    if (value instanceof Date) {  
        dt = value;  
    }  
    else {  
        if (!isNaN(value)) {  
            dt = new Date(value);  
        }  
        else if (value.indexOf('/Date') > -1) {  
            value = value.replace(/\/Date(−?\d+)\//, '$1');  
            dt = new Date();  
            dt.setTime(value);  
        } else if (value.indexOf('/') > -1) {  
            dt = new Date(Date.parse(value.replace(/-/g, '/')));  
        } else {  
            dt = new Date(value);  
        }  
    }  
    return dt;  
}  
//为Date类型拓展一个format方法，用于格式化日期  
Date.prototype.format = function (format) //author: meizz   
{  
    var o = {  
        "M+": this.getMonth() + 1, //month   
        "d+": this.getDate(),    //day   
        "h+": this.getHours(),   //hour   
        "m+": this.getMinutes(), //minute   
        "s+": this.getSeconds(), //second   
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter   
        "S": this.getMilliseconds() //millisecond   
    };  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1,  
                (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1,  
                    RegExp.$1.length == 1 ? o[k] :  
                        ("00" + o[k]).substr(("" + o[k]).length));  
    return format;  
};  



//让IE支持placeholder属性  
jQuery.fn.placeholder = function(){
	var i = document.createElement('input'),
		placeholdersupport = 'placeholder' in i;
	if(!placeholdersupport){
		var inputs = jQuery(this);
		inputs.each(function(){
			var input = jQuery(this),
				text = input.attr('placeholder'),
				pdl = 0,
				height = input.outerHeight(),
				width = input.outerWidth()-10,
				placeholder = jQuery('<span class="phTips">'+text+'</span>');
			try{
				pdl = input.css('padding-left').match(/\d*/i)[0] * 1;
			}catch(e){
				pdl = 5;
			}
			placeholder.css({'margin-left': -(width-pdl),'height':height,'line-height':height+"px",'position':'absolute', 'color': "#4D4D4D", 'font-size' : "12px"});
			placeholder.click(function(){
				input.focus();
			});
			if(input.val() != ""){
				placeholder.css({display:'none'});
			}else{
				placeholder.css({display:'inline'});
			}
			placeholder.insertAfter(input);
			input.keyup(function(e){
				if(jQuery(this).val() != ""){
					placeholder.css({display:'none'});
				}else{
					placeholder.css({display:'inline'});
				}
			});
		});
	}
	return this;
};
