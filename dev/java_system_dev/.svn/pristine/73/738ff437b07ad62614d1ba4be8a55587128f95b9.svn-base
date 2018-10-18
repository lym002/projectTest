﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script type="text/javascript" src="../js/common/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		searchForm();
	});
	
	$(function(){
		$("#${tabid} #memberList").datagrid({
			title:"会员列表",
			width:"100%",
			singleSelect:true,
			fitColumns:true,
			
			columns:[[
				{title:"uid",field:"uid",hidden:true},
				{title:"注册时间",field:"regDate",width:$(this).width() * 0.1,align:"left",
					formatter:formatDateBoxFull
 				},
				{title:"手机号码",field:"mobilephone",width:$(this).width() * 0.1,align:"left",
					formatter:function(value,row,index){
						return "<a href='#' class='easyui-linkbutton' onclick=\"selectData(\'"+row.uid+"')\">"+value+"</a>";	
                    }
				},
				{title:"号码",field:"phone",hidden:true},
				{title:"真实姓名",field:"realName",width:$(this).width() * 0.05,align:"left"},
				{title:"推荐码",field:"recommCodes",width:$(this).width() * 0.05,align:"left"},
				{title:"身份证号",field:"idCards",width:$(this).width() * 0.1,align:"left"},

				{title:"开户银行",field:"bankName",width:$(this).width() * 0.1,align:"left"},
				{title:"银行卡号",field:"bankNum",width:$(this).width() * 0.1,align:"left"},
				{title:"存管开通",field:"isFuiou",width:$(this).width() * 0.1,align:"center",
					formatter:function(value,rows){
						return rows.isFuiou==1?"已开通":"未开通";
					}
				},
				/* {title:"平台余额",field:"balance",width:$(this).width() * 0.1,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.balance,2);
					}
				},
				{title:"存管余额",field:"fuiou_balance",width:$(this).width() * 0.1,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.fuiou_balance,2);
					}
				},
				{title:"平台冻结",field:"freeze",width:$(this).width() * 0.05,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.freeze,2);
					}
				},
				{title:"存管冻结",field:"fuiou_freeze",width:$(this).width() * 0.05,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.freeze,2);
					}
				},
				{title:"充值总额",field:"crushCount",width:$(this).width() * 0.1,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.crushCount,2);
					}
				},
				{title:"提现总额",field:"carryCount",width:$(this).width() * 0.1,align:"left",
					styler:function(value,row,index){return 'color:red';},
					formatter:function(value,rows){
						return fmoney(rows.carryCount,2);
					}
				}, */
				{title:"推荐人",field:"referrer",width:$(this).width() * 0.1,align:"left"},	
				{title:"更改银行卡状态",field:"updateStatus",width:$(this).width() * 0.1,align:"left"},	
				{title:"更改银行卡备注",field:"remark",width:$(this).width() * 0.3,align:"left"},	
				{	title:"操作",
					field:"_operate",width:$(this).width() * 0.1,align:"center",
					formatter:function(value,row,index){
						var operates = "";
						if(row.isFuiou ==0){
							operates += "<a href='#' class='btn l-btn l-btn-small' onclick=\"selectFuiouOperate(\'"+row.uid+"')\">存管开户数据同步</a>  ";
						}
						if(row.isFuiou ==1 && row.updateStatus !="待审核"){
							operates += "<a href='#' class='btn l-btn l-btn-small' onclick=\"updateCardNo(\'"+row.phone+"',\'"+row.uid+"')\">修改银行卡号</a>  ";
						}
						if(row.updateStatus =="待审核"){
							operates += "<a href='#' class='btn l-btn l-btn-small' onclick=\"selectCardNo(\'"+index+"')\">查看审核进度</a>  ";
						}
						return operates;	
                    }
				}
			]],
			pagination:true,
			rownumbers:true,
			toolbar:"#tb"
		});
	});
	
	function searchForm(){
		var o = {};
		var flag = true;
		 $.each($("#queryForm").form().serializeArray(), function(index) {
				if (o[this['name']]) {
					o[this['name']] = o[this['name']] + "," + this['value'];
				} else {
					o[this['name']] = this['value'];
					if(this['value']){
						flag = false;
					}
				}
			});
			if(flag){
				$.messager.alert("提示信息","查询条件至少一个");
				return false;
			}
		 $("#${tabid} #memberList").datagrid({
		 	url:'../member/memberList.do',
		 	queryParams:o
		 });
	}
	
	function selectData(uid){
			$.ajax({
				type: 'post',
				url : "../member/selectByPrimaryKey.do?uid="+uid,
				cache : false,
				data : {},
				cache : false,
				async : false,
				success : function(str) {
					window.open("../member/toCustomerService.do?caller="+str);
			    },
			 });
		}
	function selectFuiouOperate(uid){
		$.ajax({
				type: 'post',
				url : "../member/selectFuiouByPrimaryKey.do?uid="+uid,
				data : {},
				dataType:"json",
				success : function(result) {
						if(result.success){
							$.messager.alert("提示信息",result.msg);
							searchForm();
						}else{
							$.messager.alert("提示信息",result.errorMsg);
						}
			    },
		 });
	
	}
	

	function updateCardNo(mobilephone,uid){
		$("#addCardNoForm").form("reset");
		$("#mobilephone").val(mobilephone);
		$("#uid").val(uid);
		$("#addCardNoDiaLog").dialog("open");
	}
	
	//图片预览
	function PreviewImage1(acceptPicAddFile) {
		if (acceptPicAddFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(acceptPicAddFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.jpg中的一种");
            acceptPicAddFile.value = "";  
            return false;  
        }
	    if(acceptPicAddFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {   
				acceptPicAddFile.select();
				var imgSrc = document.selection.createRange().text;
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(acceptPicAddFile.files)  
                {  
                	$("#showfile1").attr("src",window.URL.createObjectURL(acceptPicAddFile.files[0]));  
                }  
             }
         }  
		return true;
	}
	
	//图片预览
	function PreviewImage2(acceptPicAddFile) {
		if (acceptPicAddFile.value == "") {  
			$.messager.alert("提示信息","请上传图片");
	        return false;  
	    }  
     	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(acceptPicAddFile.value)) {  
        	$.messager.alert("提示信息","图片类型必须是.jpg中的一种");
            acceptPicAddFile.value = "";  
            return false;  
        }
	    if(acceptPicAddFile){  
			if (window.navigator.userAgent.indexOf("MSIE")>=1 && !(navigator.userAgent.indexOf("MSIE 10.0") > 0))    {   
				acceptPicAddFile.select();
				var imgSrc = document.selection.createRange().text;
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				document.selection.empty();
      		}else{ 
                if(acceptPicAddFile.files)  
                {  
                	$("#showfile2").attr("src",window.URL.createObjectURL(acceptPicAddFile.files[0]));  
                }  
             }
         }  
		return true;
	}
	
	//关闭添加更改银行卡窗口
	function closeAddCardNoDiaLog() {
		$("#memberList").datagrid("reload");
		$("#addCardNoDiaLog").dialog("close");
	}
	
	//提交
	function addCardNoCommit(){
		if($("#file1").val()==null || $("#file2").val()==null){
			$.messager.alert("提示信息","请上传图片");
	        return false;  
		}
	    var flag = $("#addCardNoForm").form('enableValidation').form('validate');
		if(flag){
			$("#addCardNoForm").ajaxSubmit({
				url:"${apppath}/member/addCardNo.do",
				type:"POST",
				data:$("#addCardNoForm").serialize(),
	      		success:function(data){
	      			$.messager.alert("提示信息",data);  
	      			$("#memberList").datagrid("reload");
	      			$("#addCardNoDiaLog").dialog("close");
				}
	        });
	        return false; // 阻止表单自动提交事件
		} 
	}
	
	//查看审核进度
	function selectCardNo(index){
		$("#memberList").datagrid('selectRow',index);// 关键在这里 
	    var row = $("#memberList").datagrid('getSelected'); 
		$.ajax({
			type: 'post',
			url : "../member/selectCardNo.do",
			data : {
				bank_no_mchnt_txn_ssn:row.bank_no_mchnt_txn_ssn,
				mobilephone:row.phone
			},
			success : function(result) {
				$.messager.alert("提示信息",result);	
		    },
	 });
	}

</script>

</head>
<body style="width:100%;height:100%">
<div id="${tabid }" style="width:99.9%;height:99%;">
	<table id="memberList" style="width:99.9%;height:99%;"></table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<form id="queryForm">
			手机号码: <input class="easyui-textbox" name="mobilephone" style="width:150px">
<!-- 			真实姓名: <input class="easyui-textbox" name="realName" style="width:150px"> -->
			推荐码: <input class="easyui-textbox" name="recommCodes" style="width:100px">
<!-- 			身份证号码: <input class="easyui-textbox" name="idCards" style="width:150px"> -->
			推荐人: <input class="easyui-textbox" name="referrer" style="width:150px">
			银行卡修改状态: <select class="easyui-combobox" name="updateStatus" style="width:150px">
							<option value=''>全部</option>
							<option value='1'>发起申请</option>
							<option value='2' selected="selected">待审核</option>
							<option value='3'>审核通过</option>
							<option value='4'>审核失败</option>
						</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchForm()">查询</a>
			</form>
		</div>
	</div>
</div>

<div id="addCardNoDiaLog" class="easyui-dialog" title="更改银行卡"
		data-options="iconCls:'icon-edit',closed:true,modal:true,minimizable:false,resizable:true,maximizable:false,buttons:'#addCardNoBtn'" style="width:700px;height:430px;padding:5px;">
		<form id="addCardNoForm" enctype="multipart/form-data">
			<table align="center">
				<tr>
					<td align="left">银行卡号：</td>
					<td>
						<input id="mobilephone" name="mobilephone" type="hidden"/>
						<input id="uid" name="uid"  type="hidden"/>
						<input id="card_no" name="card_no" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="left">银行卡预留手机号：</td>
					<td>
						<input id="mobile" name="mobile" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="left">更改银行卡原因：</td>
					<td>
						<input id="remark" name="remark" type="text" data-options="required:true" class="easyui-textbox" />
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="left">开户行行别：</td>
					<td>
						<input class="easyui-combobox" id = "bank_cd" name="bank_cd" data-options="
							required:true,
							valueField:'bankCode',
					        textField:'bank',
					        url:'${apppath}/claims/getBankCode.do'
						">
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="left">开户行省市：</td>
					    <td><input class="easyui-combobox" id ="province" name="province" data-options=" 
					    	required:true, 
					        valueField:'cityCode',
					        textField:'city',
					        url:'${apppath}/claims/getCityList.do',  
					        onSelect:function(rec){  
					            $('[comboname=city_id]').combobox('clear');  
					            $('[comboname=city_id]').combobox('reload', '${apppath}/claims/getFuiouAreaByCity.do?cityCode=' + rec.cityCode);  
					        },
						    onLoadSuccess:function(){  
					            var value = $('#province').combobox('getValue'); 
					            if(value != ''){  
					            $('[comboname=city_id]').combobox('reload', '${apppath}/claims/getFuiouAreaByCity.do?cityCode=' + value);  
					                }  
					        }
					        ">  
					    </td>  
					    <td>区县:</td>  
					    <td><input class="easyui-combobox" id = "city_id" name="city_id" data-options="
					    valueField:'areaCode',
					    textField:'area',
					    required:true,
					    prompt:'请先填入省市名称'">  
					    </td>
				</tr>
				<tr>
					<td align="left">图片1：</td>
					<td>
						<input type="file" name="file1" id="file1" onchange="PreviewImage1(this)" />
						<!-- <div style="color:red;">人物，身份证，新银行卡合照图片。上传图片不能大于 3M,图片格式.jpg</div> -->
					</td>
					<td></td>
					<td>
						<img src="" id="showfile1"  height="30" width="120" /> 
					</td>
					
				</tr>
				<tr>
					<td align="left">图片2：</td>
					<td>
						<input type="file" name="file2" id="file2" onchange="PreviewImage2(this)" />
						<!-- <div style="color:red;">人物，身份证，新银行卡合照图片。上传图片不能大于 3M,图片格式.jpg</div> -->
					</td>
					<td></td>
					<td>
						<img src="" id="showfile2"  height="30" width="120" /> 
					</td>
					
				</tr>
			</table>
		</form>
		<div id="addCardNoBtn">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addCardNoCommit()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeAddCardNoDiaLog()">取消</a>
		</div>
	</div>
</body>
</html>
