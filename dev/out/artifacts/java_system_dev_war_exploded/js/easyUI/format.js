function isDepository(value){
	if(value != null){
		return "是";
	}
	if(value == null || value == ''){
		return "否";
	}
}

//提现通道
function withdrawalTD(value){
	if(value == 1){
		return "连连";
	}
	if(value == 2){
		return "金运通";
	}
	if(value == 3){
		return "存管";
	}
}
//提现渠道
function withdrawalQD(value){
	if(value == 0){
		return "PC";
	}
	if(value == 1){
		return "苹果";
	}
	if(value == 2){
		return "安卓";
	}
	if(value == 3){
		return "H5";
	}
	if(value == 4){
		return "后台";
	}
	if(value == 5){
		return "微信";
	}
}

//获取当前时间
function getdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate ;
	return currentdate; 
} 

//获取当前时间前一天
function getDateBefore(num){
	console.log(num);
	var date ; 
	if(num){
		date = new Date(new Date().getTime() - num*60*60*24*1000);
	}else{
		date = new Date(); 
	}
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate ;
	return currentdate; 
}

//获取当天最早时间
function getstartdate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+" 00:00:00";
	return currentdate; 
} 

//获取当天最晚时间
function getenddate(){
	var date = new Date(); 
	var seperator1 = "-"; 
	var month = date.getMonth() + 1; 
	var strDate = date.getDate(); 
	if (month >= 1 && month <= 9) { 
	    month = "0" + month; 
	} 
	if (strDate >= 0 && strDate <= 9) { 
	    strDate = "0" + strDate; 
	} 
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+" 23:59:59" ;
	return currentdate; 
}

//促复投红包是否可拆
function iSSplit(value){
	if(value == 1){
		return "是";
	}
	if(value == 0){
		return "否";
	}
}
