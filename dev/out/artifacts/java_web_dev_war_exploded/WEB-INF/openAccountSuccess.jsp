﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
</head>
<body>
<input id="success" value="${br.success}"  type="hidden"/>
<input id="errorCode" value="${br.errorCode}"   type="hidden"/>
<script type="text/javascript">
	location.href="${br.msg}?success="+'${br.success}'
						+"&errorCode="+'${br.errorCode}'+
						"&errorMsg="+'${br.errorMsg}'+
						"&amount="+'${br.map.amount}'+
						"&investId="+'${br.map.investId}'+
						"&investTime="+'${br.map.investTime}'+
						"&isDoubleEgg="+'${br.map.isDoubleEgg}'+
						"&luckCodes="+'${br.map.luckCodes}'+
						"&luckCodeCount="+'${br.map.luckCodeCount}'+
						"&isRepeats="+'${br.map.isRepeats}';
</script>
</body>
</html>
