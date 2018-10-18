﻿<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script src="../js/layer/layer.min.js"></script>
<script src="../js/layer/extend/layer.ext.js"></script>
</head>
<body>
<div id="${tabid }">
	<div title="产品信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">产品编号：</td>
				<td colspan="2">
					<input name="code" value="${drProductInfo.code}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
				<c:if test="${drProductInfo.fid != null}">
					<td align="left">续发编号：</td>
					<td colspan="2">
						<input name="code" value="${fcode }" type="text" class="easyui-textbox" disabled="disabled"/>
					</td>
				</c:if>
			</tr>
			<tr>
				<td align="left">产品全称：</td>
				<td colspan="2">
					<input name="fullName" value="${drProductInfo.fullName}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
				<td align="left">产品简称：</td>
				<td colspan="2">
					<input name="simpleName" value="${drProductInfo.simpleName}" type="text" class="easyui-textbox" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td align="left">产品类型：</td>
				<td colspan="2">
					<select name="type" style="width: 172px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${type}" var="map">
							<option value='${map.key }' <c:if test="${drProductInfo.type== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<!--td align="left">居间人：</td>
				<td colspan="2">
					<select name="intermediary" style="width: 172px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${intermediary}" var="map">
							<option value='${map.key }' <c:if test="${drProductInfo.intermediary== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td-->
			</tr>
			<tr <c:if test="${drProductInfo.type == 1 || drProductInfo.isSid == 0}"> style="display:none" </c:if>>
				<td></td>
				<td align="left" colspan="2">
					<input name="isSid" value="1" type="radio" checked="checked" disabled="disabled"/>是否需要关联标的
				</td>
				<td align="left">关联标的：</td>
				<td>
					<select id="sidUpdate" name="sid" style="width: 190px" class="easyui-combobox" disabled="disabled">
						<option selected="selected" value="${drSubjectInfo.id}">${drSubjectInfo.name }</option>
					</select>
				</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="queryDrSubjectInfoBtn();">查看详情</a>
				</td>
			</tr>
			<tr>
				<td align="left">产品金额：</td>
				<td colspan="2">
					<input name="amount" value="${drProductInfo.amount}" type="text" class="easyui-numberbox" disabled="disabled"/>元
				</td>
				<!--td align="left">成立时间：</td>
				<td colspan="2">
					<input name="establish" value="${establish}" type="text" class="easyui-datebox" data-options="editable:false" disabled="disabled"/>预估
				</td-->
			</tr>
			<tr>
				<td align="left">年化利率：</td>
				<td colspan="2">
					<input name="rate" value="${drProductInfo.rate}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0,max:20,precision:2"/>%
				</td>
				<td align="left">活动利率：</td>
				<td colspan="2">
					<input name="activityRate" value="${drProductInfo.activityRate}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0,max:20,precision:2"/>%
				</td>
			</tr>
			<tr>
				<td align="left">还款方式：</td>
				<td colspan="2">
					<select name="repayType" style="width: 172px" class="easyui-combobox" disabled="disabled">
						<c:forEach items="${repayType}" var="map">
							<c:if test="${map.key == drProductInfo.repayType}">
							<option value='${map.key }' <c:if test="${drProductInfo.repayType== map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td align="left">产品期限：</td>
				<td colspan="2">
					<input name="deadline" value="${drProductInfo.deadline}" type="text" class="easyui-numberbox" disabled="disabled" data-options="min:0"/>天
				</td>
			</tr>
			<tr>
				<td align="left">起投金额：</td>
				<td colspan="2">
					<input name="leastaAmount" value="${drProductInfo.leastaAmount}" type="text" class="easyui-numberbox" disabled="disabled"/>元
				</td>
				<td align="left">募集期限：</td>
				<td colspan="2">
					<input name="raiseDeadline" value="${drProductInfo.raiseDeadline}" type="text" class="easyui-numberbox" disabled="disabled"/>天
				</td>
			</tr>
			<tr>
				<td align="left">递增金额：</td>
				<td colspan="2">
					<input name="increasAmount" value="${drProductInfo.increasAmount}" type="text" class="easyui-numberbox" disabled="disabled"/>元
				</td>
				<td align="left">最大金额：</td>
				<td colspan="2">
					<input name="maxAmount" value="${drProductInfo.maxAmount}" type="text" class="easyui-numberbox" disabled="disabled"/>元
				</td>
			</tr>
			<tr>
				<td align="left">承兑机构：</td>
				<td colspan="2">
					<input name="accept" value="${drProductInfo.accept}" type="text" class="easyui-textbox" style="height:40px;" data-options="multiline:true" disabled="disabled"/>
				</td>
				<td align="left">承兑图片：</td>
				<td colspan="2">
					<img src="${drProductInfo.acceptPic}" height="30" width="170" /> 
				</td>
			</tr>
			<tr>
				<td align="left">	
						绑定活动 :
					</td>
					<td align="left">
					<select name="atid" style="width: 172px" class="easyui-combobox" id="atid" disabled="disabled" >
							<option value="">选择活动</option>
							<c:forEach items="${activitymap}" var="map">
								<option value='${map.id }' <c:if test="${drProductInfo.atid== map.id}"> selected="selected" </c:if>>${map.name }</option>
							</c:forEach>						
						</select>  
					</td>
				<td align="left">
					<input name="isShow" type="checkbox" value="1" <c:if test="${drProductInfo.isShow == 1}"> checked="checked" </c:if> disabled="disabled"/>是否显示
				</td>
				<td align="left">
					<input name="isHot" type="checkbox" value="1" <c:if test="${drProductInfo.isHot == 1}"> checked="checked" </c:if> disabled="disabled"/>是否热推
				</td>
				<!--td align="left">
					<input name="isDeductible" type="checkbox" value="1" <c:if test="${drProductInfo.isDeductible == 1}"> checked="checked" </c:if> disabled="disabled"/>可否抵扣
				</td-->
				<td align="left">
					<input name="isInterest" type="checkbox" value="1" <c:if test="${drProductInfo.isInterest == 1}"> checked="checked" </c:if> disabled="disabled"/>可否加息
				</td>
				<td align="left">
					<input name="isCash" type="checkbox" value="1" <c:if test="${drProductInfo.isCash == 1}"> checked="checked" </c:if> disabled="disabled"/>可否返现
				</td>
				<td align="left">
					<input name="isDouble" type="checkbox" value="1" <c:if test="${drProductInfo.isDouble == 1}"> checked="checked" </c:if> disabled="disabled"/>可否加倍
				</td>
			</tr>
				<tr>
				
				<c:choose>
					<c:when test="${productPrize !=null}">
							<td align="left">关联奖品:</td>
							<td align="left"><select name="prizeId" style="width: 172px"
								class="easyui-combobox" id="prizeId" disabled="disabled">
									<option value="">${productPrize.name}</option>
							</select></td>
						</c:when>
						<c:otherwise>
							<td align="left">关联奖品:</td>
							<td align="left"><select name="prizeId" style="width: 172px"
								class="easyui-combobox" id="prizeId" disabled="disabled">
									<option value="">选择奖品</option>
							</select></td>
						</c:otherwise>
				</c:choose>
			</tr>
			<tr >
					<td align="left">
						产品原理图:
					</td>
					<td align="left"  >
						<select name="principleId" style="width: 172px" class="easyui-combobox" disabled="disabled">
							<c:forEach items="${principleType}" var="map">
								<c:choose>
									 <c:when test="${map.key == drProductInfo.principleId}">
										 <option value='${map.key }'>${map.value }</option>  
									  </c:when>
										<c:otherwise>   
									   	<option value="">选择原理图</option>
									  </c:otherwise> 
								</c:choose>
							</c:forEach>
						</select>  
					</td>
					<td align="right" class="principle"  >
						pc:<input id="principlePC"  placeholder="pcUrl" value="${drProductInfo.principlePC }" class='easyui-textbox' style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;' disabled="disabled"/>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('principlePC')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" class="principle" hidden="hidden">
						<a href="javascript:;" class="file">选择文件
							<input  type="file" name='principlePcFile'  id="" onchange="addVerifyPrincipleImage(this,'principlePC')"  disabled="disabled"/>
						</a>
					</td>
					<td align="right" class="principle" >
						app:<input  id="principleH5" value="${drProductInfo.principleH5 }" type='text' placeholder="appUrl" class='easyui-textbox' style='width:100px; white-space:nowrap;overflow:hidden;text-overflow:ellipsis;' disabled="disabled" />
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openPic('principleH5')" iconCls="icon-search">查看</a>
					</td>
					<td align="left" class="principle"  hidden="hidden">
							<a href="javascript:;" class="file">选择文件
								<input  type="file"   name='principleAppFile' onchange="addVerifyPrincipleImage(this,'principleApp')" disabled="disabled"/>
							</a>
					</td>
				</tr>
			<tr>
				<td align="left">项目介绍：</td>
				<td colspan="4">
					<input name="introduce" value="${drProductInfo.introduce}" class="easyui-textbox" style="height:123px;width: 450px;" data-options="multiline:true" disabled="disabled"/>
				</td>
			</tr>
			<tr>	
				<td align="left">借款用途：</td>
				<td colspan="4">
					<input name="borrower" value="${drProductInfo.borrower}" class="easyui-textbox" style="height:123px;width: 450px;" data-options="multiline:true" disabled="disabled"/>
				</td>
			</tr>
			<tr>		
				<td align="left">还款来源：</td>
				<td colspan="4">
					<input name="repaySource" value="${drProductInfo.repaySource}" class="easyui-textbox" style="height:123px;width: 450px;" data-options="multiline:true" disabled="disabled"/>
				</td>
			</tr>
			<tr>		
				<td align="left">还款保障：</td>
				<td colspan="4">
					<input name="windMeasure" value="${drProductInfo.windMeasure}" class="easyui-textbox" style="height:123px;width: 450px;" data-options="multiline:true" disabled="disabled"/>
				</td>
			</tr>
			<tr id="isShowPicUpdate" data-value="${drClaimsPic}" <c:if test="${drClaimsPic == null}"> style="display:none" </c:if>>
				<th></th>
				<th>图片名称(点击查看)</th>
				<th></th>
				<th>是否显示</th>
				<th>显示顺序(先显示数字小的)</th>
			</tr>
			<c:forEach items="${drClaimsPic}" var="v" varStatus="i">
	   	   	<tr>
		   	   	<td></td>
		   	   	<td>
					<a href='javascript:void(0)' data-value="${v.bigUrl}" onclick="showPic(this);">${v.name}</a>
				</td>
				<td></td>
				<td>
					<input type="checkbox" name="drClaimsPic[${i.index}].isShow" value="1" <c:if test="${v.isShow == 1}"> checked="checked" </c:if> disabled="disabled"/>
				</td>
	   	   		<td>
	   	   			<input name="drClaimsPic[${i.index}].showSort" value="${v.showSort}" type='text' class='easyui-numberbox' disabled="disabled"/> 
	   	   		</td>
   	   		</tr>
			</c:forEach>
		</table>
	</div>
	<div title="产品扩展信息" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table id="addProductExtendTable" align="center" cellspacing="1" cellpadding="1" style="width: auto;">
           	<c:forEach items="${drProductExtend}" var="v">
           	<tr>
           		<td>
           			标题：<input type='text' value="${v.title}" class='easyui-textbox' disabled="disabled"/>
           		</td>
           		<td>
           			内容：<input class='easyui-textbox' value="${v.content}" style="height:60px;width: 450px;" disabled="disabled"/>
           		</td>
           	</tr>
			</c:forEach>
		</table>
	</div>
	<div title="产品图片" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
		<table id="picTable" style="width:auto;" align="center" >
			<tr>
				<td>
	                <div id="imgs" class="imgs" style="overflow-x: auto;width: ${drProductPic.size()*210}px;">
	            		<c:forEach items="${drProductPic}" var="v">
				        	<div style="text-align: center;display: inline-block;">
					        	<img src="${v.bigUrl}" width="200px" height="200px">
					        	<p>图片名称：${v.name}</p>
					        </div>
	  					</c:forEach>
				    </div>
				</td>
			</tr>
		</table>
	</div>
	<div title="<center>产品审核</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table style="width:auto;" align="center" >
			<c:forEach items="${drAuditInfo}" var="v" varStatus="i">
			<tr>
				<td align="left">审核人员：</td>
				<td>
					${v.name}
				</td>
				<td align="left">审核时间：</td>
				<td>
					<fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核结果：</td>
				<td colspan="3">
					<c:forEach items="${auditType}" var="map">
						<c:if test="${v.status == map.key}">${map.value }</c:if>
					</c:forEach>
				</td>
	   	   	</tr>
	   	   	<tr>
				<td align="left">审核意见：</td>
				<td colspan="3">
					<input class="easyui-textbox" data-options="multiline:true" value="${v.opinion}" style="height:123px;width: 500px;" disabled="disabled"/>
				</td>
	   	   	</tr>
	   	   	<c:if test="${drAuditInfo.size() != i.index+1}">
	   	   		<tr><td colspan="4"><div style="border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;"></div></td></tr>
	   	   	</c:if>
			</c:forEach>
		</table>
	</div>
</div>	
<div id="queryDrSubjectInfoWindow" class="easyui-window"
	data-options="closed:true,modal:true,minimizable:false,maximizable:false" style="padding:10px;">
</div>
<div id="picShow" class="easyui-dialog" title="查看图片" style="height:auto;width:550px;padding:10px;top:20%" 
		data-options="closed:true,modal:true,minimizable:false,resizable:true,maximizable:false">
			<div id="imgbig">
				<img id="imglooks" src="" width="500px" height="auto" />
			</div>
			<!-- 显示图片 -->
	</div>
<script type="text/javascript">
	//跳转标的详情页面
	function queryDrSubjectInfoBtn(index){
		$("#queryDrSubjectInfoWindow").window({
			title:"标的详情",
			width:$(this).width()*0.5,
			height:$(this).height()*0.6,
			href:"../product/showDrSubjectInfo.do?id="+$('#sidUpdate').combobox('getValue')
		});
		$("#queryDrSubjectInfoWindow").window("open").window("center");
	}
	
	function showPic(ths){
		var src = $(ths).data('value');
        layer.photos({
            json:  { //您服务端接口需严格按照下述格式返回
		        "status": 1,    //请求的状态，1表示成功，其它表示失败
		        "msg": "",  //状态提示语
		        "title": "",    //相册标题
		        "id": "",    //相册id
		        "start": 0, //初始显示的图片序号，默认0
		        "data": [   //相册包含的图片，数组格式
		            {
		                "name": "", //图片名
		                "pid": "", //图片id
		                "src": src, //原图地址
		                "thumb": "", //缩略图地址
		                "area": [638, 851] //原图宽高
		            }
		        ]
		    },
        });
	}
	
	layer.photosPage({
        id: 100, //相册id，可选
        parent:'#imgs'
    });
    
    	//放大图片
	function openPic(ths){
		
		var src = $('#'+ths).val();
		
		if(src ==null || src == ''){
			return ;		
		}
		$("#imglooks").attr("src",src);
		$('#picShow').dialog('open');
		
  }
</script>
</body>
</html>
