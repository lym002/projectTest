<%@include file="/WEB-INF/jsp/common/include/Taglibs.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>币优铺理财 - 慧信财富管理有限公司</title>
<%@ include file="../../common/include/util.jsp" %>
<script src="../js/layer/layer.min.js"></script>
</head>
<body>
<div id="${tabid }">
	<div title="<center>企业客户基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">企业名称：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.companyName" value="${drClaimsCustomer.companyName}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">企业名称协议显示：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.companyNameProtocolShow" value="${drClaimsCustomer.companyNameProtocolShow}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">法人姓名：</td>
				<td>
					<input name="drClaimsCustomer.name" value="${drClaimsCustomer.name}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsCustomer.sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsCustomer.sex== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsCustomer.phone" value="${drClaimsCustomer.phone}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsCustomer.certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsCustomer.certificateType== map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.certificateNo" value="${drClaimsCustomer.certificateNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">组织机构代码证号码：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.mechanismNo" value="${drClaimsCustomer.mechanismNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">行业类别：</td>
				<td>
					<input name="drClaimsCustomer.industryType" value="${drClaimsCustomer.industryType}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">营业执照号码：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.businessNo" value="${drClaimsCustomer.businessNo}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">企业联系号码：</td>
				<td>
					<input name="drClaimsCustomer.companyPhone" value="${drClaimsCustomer.companyPhone}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">企业邮箱：</td>
				<td colspan="3">
					<input name="drClaimsCustomer.companyMail" value="${drClaimsCustomer.companyMail}" style="width: 410px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">通讯地址：</td>
				<td colspan="5">
					<input name="drClaimsCustomer.address" value="${drClaimsCustomer.address}" style="width: 655px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="shareholderTable" title="<center>股东情况</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
	data-options="collapsible:true">
		<c:if test="${drClaimsShareholder.size() == 0}">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr>
				<td align="left">法人姓名：</td>
				<td>
					<input name="drClaimsShareholder[0].name" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsShareholder[0].sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsShareholder[0].phone" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">股东类别：</td>
				<td>
					<select name="drClaimsShareholder[0].type" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${shareholderType}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsShareholder[0].certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td>
					<input name="drClaimsShareholder[0].certificateNo" type="text" class="easyui-textbox" />
				</td>
			</tr>
			<tr>
				<td align="left">出资额度：</td>
				<td>
					<input name="drClaimsShareholder[0].contributionLines" type="text" class="easyui-textbox" />
				</td>
				<td align="left">出资方式：</td>
				<td>
					<input name="drClaimsShareholder[0].contributionType" type="text" class="easyui-textbox" />
				</td>
				<td align="left">投资比例：</td>
				<td>
					<input name="drClaimsShareholder[0].investRate" type="text" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
			</tr>
		</table>
		</c:if>
		<c:forEach items="${drClaimsShareholder}" var="v" varStatus="i">
		<c:if test="${i.index != 0}">
			<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>
		</c:if>
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr>
				<td align="left">法人姓名：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].name" value="${v.name}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsShareholder[${i.index}].sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${v.sex == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].phone" value="${v.phone}" type="text" class="easyui-textbox" />
				</td>
			</tr>
			<tr>
				<td align="left">股东类别：</td>
				<td>
					<select name="drClaimsShareholder[${i.index}].type" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${shareholderType}" var="map">
							<option value='${map.key }' <c:if test="${v.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsShareholder[${i.index}].certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }' <c:if test="${v.certificateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].certificateNo" value="${v.certificateNo}" type="text" class="easyui-textbox" />
				</td>
			</tr>
			<tr>
				<td align="left">出资额度：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].contributionLines" value="${v.contributionLines}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">出资方式：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].contributionType" value="${v.contributionType}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">投资比例：</td>
				<td>
					<input name="drClaimsShareholder[${i.index}].investRate" value="${v.investRate}" type="text" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
			</tr>
		</table>
		</c:forEach>
	</div>
	
	<div title="<center>贷款项目基本信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">产品名称：</td>
				<td>
					<input name="name" value="${drClaimsLoan.name}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">借款合同编号：</td>
				<td>
					<input name="no" value="${drClaimsLoan.no}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">贷款用途：</td>
				<td>
					<input name="loanUse" value="${drClaimsLoan.loanUse}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">贷款金额(万元)：</td>
				<td>
					<input name="loanAmount" value="${drClaimsLoan.loanAmount}" class="easyui-textbox"/>
				</td>
				<td align="left">币种：</td>
				<td>
					<input name="currency" value="${drClaimsLoan.currency}" class="easyui-textbox"/>
				</td>
				<td align="left">贷款期限：</td>
				<td>
					<input name="loanDeadline" value="${drClaimsLoan.loanDeadline}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">年利率(%)：</td>
				<td>
					<input name="rate" value="${drClaimsLoan.rate}" class="easyui-textbox" data-options="min:0,max:20,precision:2"/>
				</td>
				<td align="left">还款方式：</td>
				<td>
					<select name="repayType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${repayType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsLoan.repayType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">还款周期：</td>
				<td>
					<select name="repayDeadline" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${repayDeadline}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsLoan.repayDeadline == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left">应收利息：</td>
				<td>
					<input name="receiveInterest" value="${drClaimsLoan.receiveInterest}" class="easyui-numberbox" data-options="min:0,max:100000000,precision:2"/>
				</td>
				<td align="left">放款日期：</td>
				<td>
					<input name="startDate" class="easyui-datebox" value="${LoanStartDate}"/>
				</td>
				<td align="left">还款日期：</td>
				<td>
					<input name="endDate" class="easyui-datebox" value="${LoanEndDate}"/>
				</td>
			</tr>
			<tr>
				<td align="left">服务费：</td>
				<td>
					<input name="serviceCharge" value="${drClaimsLoan.serviceCharge}" class="easyui-numberbox" data-options="min:0,max:100000000,precision:2"/>
				</td>
				<td align="left">打款类型：</td>
				<td>
						<select name="cardFlag" style="width: 175px" class="easyui-combobox">
							<c:forEach items="${cardFlag}" var="map">
								<option value='${map.key }' <c:if test="${drClaimsLoan.cardFlag == map.key}"> selected="selected" </c:if>>${map.value }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
			<tr>
				<td align="left">日期模式：</td>
				<td>
					<select name="dateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${dateType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsLoan.dateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">贷款户名：</td>
				<td>
					<input name="loanName" value="${drClaimsLoan.loanName}" class="easyui-textbox"/>
				</td>
				<td align="left">银行名称：</td>
				<td>
					<input name="bankName" value="${drClaimsLoan.bankName}" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">银行账号：</td>
				<td colspan="3">
					<input name="bankNo" style="width: 500px" value="${drClaimsLoan.bankNo}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">开户行地址：</td>
				<td>
					<input name="bankAddress" value="${drClaimsLoan.bankAddress}" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">管理咨询费率(%)：</td>
				<td>
					<input name="advisoryRate" value="${drClaimsLoan.advisoryRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
				<td align="left">服务费率(%)：</td>
				<td>
					<input name="serviceRate" value="${drClaimsLoan.serviceRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
				<td align="left">违约金比例(%)：</td>
				<td>
					<input name="defaultersRate" value="${drClaimsLoan.defaultersRate}" type="text" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
			</tr>
			<tr>
				<td align="left">逾期利率(%)：</td>
				<td>
					<input name="overdueRate" value="${drClaimsLoan.overdueRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
				<td align="left">逾期罚息利率(%)：</td>
				<td>
					<input name="overduePenaltyRate" value="${drClaimsLoan.overduePenaltyRate}" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
				<td align="left">风险保证金比例(%)：</td>
				<td>
					<input name="riskRate" value="${drClaimsLoan.riskRate}" type="text" class="easyui-textbox" data-options="min:0,max:100,precision:2"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="guaranteeTable" title="<center>担保情况</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" 
	data-options="collapsible:true">
		<c:if test="${drClaimsGuarantee.size() == 0}">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr>
				<td align="left">是否有抵/质押物：</td>
				<td>
					<select name="drClaimsGuarantee[0].isPawn" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">抵/质押物类别：</td>
				<td>
					<select name="drClaimsGuarantee[0].pawnType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${pawnType}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">评估价值（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[0].assessAmount" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有承兑保证：</td>
				<td>
					<select name="drClaimsGuarantee[0].isAcceptance" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">保证人名称：</td>
				<td>
					<input name="drClaimsGuarantee[0].guarantor" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有个人担保：</td>
				<td>
					<select name="drClaimsGuarantee[0].isGuarantee" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保类别：</td>
				<td>
					<select name="drClaimsGuarantee[0].guaranteeType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${guaranteeType}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">个人担保人名称：</td>
				<td>
					<input name="drClaimsGuarantee[0].guarantee" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有融资性担保：</td>
				<td>
					<select name="drClaimsGuarantee[0].isFinanc" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保类别：</td>
				<td>
					<select name="drClaimsGuarantee[0].financType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${guaranteeType}" var="map">
							<option value='${map.key }'>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保金额：</td>
				<td>
					<input name="drClaimsGuarantee[0].guaranteeAmount" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
		</c:if>
		<c:forEach items="${drClaimsGuarantee}" var="v" varStatus="i">
		<c:if test="${i.index != 0}">
			<div style='border-bottom: 1px dashed #444444;height: 10px;margin-bottom: 10px;width: 100%;'></div>
		</c:if>
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;margin-bottom: 10px">
			<tr>
				<td align="left">是否有抵/质押物：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].isPawn" value="${v.isPawn}" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isPawn == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">抵/质押物类别：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].pawnType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${pawnType}" var="map">
							<option value='${map.key }' <c:if test="${v.pawnType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">评估价值（万元）：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index}].assessAmount" value="${v.assessAmount}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有承兑保证：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].isAcceptance" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isAcceptance == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">保证人名称：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index}].guarantor" value="${v.guarantor}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有个人担保：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].isGuarantee" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isGuarantee == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保类别：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].guaranteeType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${guaranteeType}" var="map">
							<option value='${map.key }' <c:if test="${v.guaranteeType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">个人担保人名称：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index}].guarantee" value="${v.guarantee}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">是否有融资性担保：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].isFinanc" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${whether}" var="map">
							<option value='${map.key }' <c:if test="${v.isFinanc == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保类别：</td>
				<td>
					<select name="drClaimsGuarantee[${i.index}].financType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${guaranteeType}" var="map">
							<option value='${map.key }' <c:if test="${v.financType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">担保金额：</td>
				<td>
					<input name="drClaimsGuarantee[${i.index}].guaranteeAmount" value="${v.guaranteeAmount}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
		</c:forEach>
	</div>
	
	<div title="<center>票据信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true"> 
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">类别：</td>
				<td>
					<select name="drClaimsBill.type" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${billType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsBill.type == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">开票人：</td>
				<td>
					<input name="drClaimsBill.drawer" value="${drClaimsBill.drawer}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">收款人：</td>
				<td>
					<input name="drClaimsBill.payee" value="${drClaimsBill.payee}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">承兑人：</td>
				<td>
					<input name="drClaimsBill.acceptor" value="${drClaimsBill.acceptor}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">票据金额（万元）：</td>
				<td>
					<input name="drClaimsBill.amount" value="${drClaimsBill.amount}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">票据号码：</td>
				<td>
					<input name="drClaimsBill.number" value="${drClaimsBill.number}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">被背书人：</td>
				<td colspan="5">
					<input name="drClaimsBill.endorsee" value="${drClaimsBill.endorsee}" style="width: 700px" type="text" class="easyui-textbox" 
					data-options="validType:'maxLength[255]'" placeholder="示例:a/b/c/d"/>
				</td>
			</tr>
			<tr>
				<td align="left">开票日期：</td>
				<td>
					<input name="drClaimsBill.startDate" class="easyui-datebox" value="${billStartDate}"/>
				</td>
				<td align="left">票据到期日：</td>
				<td>
					<input name="drClaimsBill.endDate" class="easyui-datebox" value="${billEndDate}"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div title="<center>融资性担保企业信息</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true">
		<table align="center" cellspacing="1" cellpadding="1" style="width:auto;">
			<tr>
				<td align="left">企业名称：</td>
				<td colspan="5">
					<input name="drClaimsFinanc.companyName" value="${drClaimsFinanc.companyName}" style="width: 700px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">法人姓名：</td>
				<td>
					<input name="drClaimsFinanc.name" value="${drClaimsFinanc.name}" type="text" class="easyui-textbox" />
				</td>
				<td align="left">性别：</td>
				<td>
					<select name="drClaimsFinanc.sex" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${sex}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsFinanc.sex == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">手机号码：</td>
				<td>
					<input name="drClaimsFinanc.phone" value="${drClaimsFinanc.phone}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">证件类型：</td>
				<td>
					<select name="drClaimsFinanc.certificateType" style="width: 175px" class="easyui-combobox">
						<c:forEach items="${certificateType}" var="map">
							<option value='${map.key }' <c:if test="${drClaimsFinanc.certificateType == map.key}"> selected="selected" </c:if>>${map.value }</option>
						</c:forEach>
					</select>
				</td>
				<td align="left">证件号码：</td>
				<td colspan="3">
					<input name="drClaimsFinanc.certificateNo" value="${drClaimsFinanc.certificateNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">组织机构代码：</td>
				<td colspan="3">
					<input name="drClaimsFinanc.mechanismNo" value="${drClaimsFinanc.mechanismNo}" style="width: 450px" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">行业类别：</td>
				<td>
					<input name="drClaimsFinanc.industryType" value="${drClaimsFinanc.industryType}" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">营业执照号码：</td>
				<td>
					<input name="drClaimsFinanc.businessNo" value="${drClaimsFinanc.businessNo}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">税务登记证号码：</td>
				<td colspan="3">
					<input name="drClaimsFinanc.taxNo" value="${drClaimsFinanc.taxNo}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">企业联系号码：</td>
				<td>
					<input name="drClaimsFinanc.companyPhone" value="${drClaimsFinanc.companyPhone}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">企业邮箱：</td>
				<td colspan="3">
					<input name="drClaimsFinanc.companyMail" value="${drClaimsFinanc.companyMail}" style="width: 415px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
			<tr>
				<td align="left">注册资金：</td>
				<td>
					<input name="drClaimsFinanc.registerFund" value="${drClaimsFinanc.registerFund}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">注册资金币种：</td>
				<td>
					<input name="drClaimsFinanc.registerCurrency" value="${drClaimsFinanc.registerCurrency}" type="text" class="easyui-textbox"/>
				</td>
				<td align="left">成立日期：</td>
				<td>
					<input name="drClaimsFinanc.establishDate" value="${establishDate}" class="easyui-datebox" />
				</td>
			</tr>
			<tr>
				<td align="left">通讯地址：</td>
				<td colspan="5">
					<input name="drClaimsFinanc.address" value="${drClaimsFinanc.address}" style="width: 700px" type="text" class="easyui-textbox"/>
				</td>
			</tr>
		</table>
	</div>
	<div title="<center>审核项目</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px" data-options="collapsible:true,region:'center'">
		<table align="center" cellspacing="1" cellpadding="1" style="width: auto;">
           	<c:forEach items="${drClaimsProject}" var="v">
           	<tr>
           		<td>
           			审核内容：<input type='text' value="${v.name}" class='easyui-textbox' disabled="disabled"/>
           		</td>
           	</tr>
			</c:forEach>
		</table>
	</div>
	
	<div title="<center>债权图片</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
		<table id="picTable" style="width:auto;" align="center" >
			<tr>
				<td>
	                <div id="imgs" class="imgs" style="overflow-x: auto;width: ${drClaimsPic.size()*210}px;">
	            		<c:forEach items="${drClaimsPic}" var="v">
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
	
	<div title="<center>债权审核</center>" class="easyui-panel" style="width:100%;height:auto;padding:10px;margin-bottom: 10px"data-options="collapsible:true,region:'center'">
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
<script type="text/javascript">
	layer.use('extend/layer.ext.js', function(){
	    //初始加载即调用，所以需放在ext回调里
	    layer.ext = function(){
	        layer.photosPage({
	            id: 100, //相册id，可选
	            parent:'#imgs'
	        });
	    };
	});
	$(document).ready(function(){
 		$("input").attr("disabled",true);
 		$(".easyui-combobox").combobox('disable');
 		$(".easyui-datebox").datebox('disable');
 	});
</script>
</body>
</html>
