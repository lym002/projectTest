<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
</head>
<body>
</body>
	
<script type="text/javascript">
	//恒丰企业开户返回状态码
	var resp_code = '${resp_code}';
	var resp_desc = '${resp_desc}';
	$(function(){
		if(resp_code != null && resp_code != ''){
			alert(resp_desc);
			window.close();
		}
		
	});
	
</script>
</body>
</html>

