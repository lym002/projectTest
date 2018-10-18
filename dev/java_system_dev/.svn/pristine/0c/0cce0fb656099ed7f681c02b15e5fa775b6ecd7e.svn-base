package com.jsjf.common;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.itextpdf.awt.geom.QuadCurve2D.Float;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.jsjf.model.claims.DrClaimsBill;
import com.jsjf.model.claims.DrClaimsLoan;
import com.jsjf.model.member.DrMember;
import com.jsjf.model.product.DrProductInfo;
import com.jsjf.model.product.DrProductInvest;

public class ItextPdfUtil {
	
	/**
	 * 投资协议
	 * @param m
	 * @param info
	 * @param invest
	 * @param fileaddress
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void investAgreement(DrMember m, DrProductInfo info, DrProductInvest invest,String fileaddress) throws DocumentException, IOException{
		Document document = new Document(PageSize.A4, 80, 79, 20, 45); // A4纸大小 // 左、右、上、下
		
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // 中文处理
		Font FontChinese = new Font(bfChinese, 8, Font.NORMAL); // 其他所有文字字体
		Font BoldChinese = new Font(bfChinese, 14, Font.BOLD); // 粗体
		Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体
		Font moneyFontChinese = new Font(bfChinese, 8, Font.NORMAL); // 币种和租金金额的小一号字体
		Font subBoldFontChinese = new Font(bfChinese, 8, Font.BOLD); // 币种和租金金额的小一号字体
		
		PdfWriter.getInstance(document,new FileOutputStream(fileaddress));//"D:/QMDownload/行业及时通接口调用说明1.2.pdf"
		document.open(); // 打开文档
		// ------------开始写数据-------------------
		Paragraph title = new Paragraph("权益转让及受让协议", titleChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距//设置上面空白宽度
		document.add(title);
		// 编号
		title = new Paragraph("编号："+invest.getAgreementNo(), BoldChinese);
		title.setSpacingBefore(25f);// 设置上面空白宽度
		title.setAlignment(Element.ALIGN_RIGHT);
		document.add(title);
		
		StringBuffer bufStr = new StringBuffer();
		bufStr.append("本权益转让及受让协议（下称“本协议”）由以下各方于"+Utils.format(invest.getInvestTime(), "yyyy年MM月dd日")+"签署：");
		bufStr.append("\n");
		bufStr.append("甲方（转让人）：刘神");
		bufStr.append("\n");
		bufStr.append("身份证号：352202198708092513");
		bufStr.append("\n");
		bufStr.append("乙方（受让人）："+m.getRealName());
		
		title = new Paragraph(bufStr.toString(),FontChinese);
		title.setLeading(18f);// 设置行间距
		document.add(title);
		
		float[] widths = { 10f, 25f, 30f };// 设置表格的列宽和列数
		
		PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
		table.setSpacingBefore(20f);// 设置表格上面空白宽度
		table.setTotalWidth(500);// 设置表格的宽度
		table.setWidthPercentage(100);// 设置表格宽度为%100
		// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
		
		String[] tempValue = { m.getRealName(), m.getIdCards(), m.getMobilephone()}; 
		int rowCount = 1; // 行计数器
		PdfPCell cell = null;
		// ---表头
		cell = new PdfPCell(new Paragraph("乙方（受让人）", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("身份证号", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("币优铺理财用户名", subBoldFontChinese));// 描述
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		cell.setFixedHeight(20);
		table.addCell(cell);
		
		for (int j = 0; j < tempValue.length; j++) {
			cell = new PdfPCell(new Paragraph(tempValue[j], moneyFontChinese));// 描述
			cell.setFixedHeight(20);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
		}
		document.add(table);
		
		bufStr = new StringBuffer();
		bufStr.append("丙方（居间人）：慧信财富管理有限公司");
		bufStr.append("\n");
		bufStr.append("甲、乙、丙三方就甲方通过丙方在币优铺理财网站www.byp.cn " +
		"（下称“币优铺理财平台”，指慧信财富管理有限公司及币优铺理财网站的统称）的居间服务，向乙方转让债权权益事宜，经协商一致，达成如下协议:");
		title = new Paragraph(bufStr.toString(), FontChinese);
		title.setLeading(22f);// 设置行间距
		document.add(title);
		
		title = new Paragraph("第一条 转让标的与流程", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		//1.1
		bufStr = new StringBuffer();
		bufStr.append("1.1 权益转让标的");
		bufStr.append("\n");
		bufStr.append("       甲方同意将其拥有的个人债权所形成的本息权益（详见下表，下称“权益标的”）转让给乙方，乙方同意受让该权益。");
		bufStr.append("\n");
		bufStr.append("       待转让权益标的信息：");
		title = new Paragraph(bufStr.toString(), FontChinese);
		title.setLeading(22f);// 设置行间距
		document.add(title);
		
		widths = new float[]{50f,50f};
		table = new PdfPTable(widths);// 建立一个pdf表格
		table.setSpacingBefore(20f);// 设置表格上面空白宽度
		table.setTotalWidth(500);// 设置表格的宽度
		table.setWidthPercentage(100);// 设置表格宽度为%100
		// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
		
		// ---转让信息
		cell = new PdfPCell(new Paragraph("转让人姓名", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("刘神", moneyFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("转让人身份证号", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("352202198708092513", FontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("权益对应本金金额", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph(invest.getAmount().toString(), FontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("收益率", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph(info.getRate()+"%", FontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("产品类型", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph(info.getFullName()+"（"+info.getDeadline()+"天）", FontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		String date = null;
		if(info.getType() == 1 || info.getType() == 4){
			date = Utils.getDayNumOfAppointDate(invest.getInvestTime(), -1, "yyyy年MM月dd日");
		}else{
			date = Utils.getDayNumOfAppointDate(info.getExpireDate(), info.getDeadline(), "yyyy年MM月dd日");
		}
		cell = new PdfPCell(new Paragraph("权益转让生效日", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph(date, FontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		document.add(table);
		
		bufStr = new StringBuffer();
		bufStr.append("1.2 转让流程\n");
		bufStr.append("1.2.1 甲乙双方同意并确认，双方通过自行或授权有关方根据币优铺理财平台相关的规则和说明，在币优铺理财平台进行权益标的转让和受让购买操作等方式来确认签署本协议。\n");
		bufStr.append("1.2.2 甲乙双方同意，本协议通过币优铺理财平台审核通过时，本协议成立，在乙方转让价款支付完成时生效。协议成立的同时，" +
		"甲方不可撤销地授权币优铺理财平台委托第三方支付平台将权益标的转让价款在扣除甲方应支付币优铺理财平台的相关款项后（包括但不限于转让管理费）划转给甲方，" +
		"上述权益标的转让价款划转完成即视为本协议生效且权益标的转让成功。\n");
		bufStr.append("1.2.3 本协议生效且权益标的转让成功后，甲乙双方特此委托丙方通过币优铺理财平台以其名义将权益标的转让事项及有关信息通过站内短信、电话、书面等形式通知权益标的对应的借款人。\n");
		bufStr.append("1.2.4 自权益标的转让成功之日起，乙方成为权益标的权利人，享有该权益所对应的本金和全部收益。\n");
		title = new Paragraph(bufStr.toString(), FontChinese);
		title.setLeading(22f);// 设置行间距
		document.add(title);
		
		title = new Paragraph("第二条 保证与承诺", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		bufStr = new StringBuffer();
		bufStr.append("2.1 甲方保证其转让的权益标的系合法、有效的权利，不存在转让的限制。甲方同意并承诺，按有关协议及币优铺理财平台的相关规则向币优铺理财平台支付权益转让管理费。\n");
		bufStr.append("2.2 乙方保证其所用于受让权益标的资金来源合法，乙方是该资金的合法所有人。如果第三方对资金归属、合法性问题发生争议，乙方应自行负责解决并承担相关责任。\n");
		title = new Paragraph(bufStr.toString(),FontChinese);
		title.setLeading(18f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		title = new Paragraph("第三条 违约责任", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		bufStr = new StringBuffer();
		bufStr.append("3.1 甲乙双方同意，如果一方违反其在本协议中所作的保证、承诺或任何其他义务，致使其他方遭受或发生损害、损失等责任，违约方须向守约方赔偿守约方因此遭受的一切经济损失。\n");
		bufStr.append("3.2 甲乙双方均有过错的，应根据双方实际过错程度，分别承担各自的违约责任。\n");
		title = new Paragraph(bufStr.toString(),FontChinese);
		title.setLeading(18f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		title = new Paragraph("第四条 适用法律和争议解决", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		bufStr = new StringBuffer();
		bufStr.append("4.1 本协议的订立、效力、解释、履行、修改和终止以及争议的解决适用中国的法律。\n");
		bufStr.append("4.2 本协议在履行过程中，如发生任何争执或纠纷，各方应友好协商解决；若协商不成，任何一方均有权向有管辖权的人民法院提起诉讼。\n");
		title = new Paragraph(bufStr.toString(),FontChinese);
		title.setLeading(18f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		title = new Paragraph("第五条 其他", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		bufStr = new StringBuffer();
		bufStr.append("5.1 本协议采用电子文本形式制成，甲方和乙方在币优铺理财注册且通过币优铺理财平台权益转让即视为同意本协议全部内容。" +
		"甲、乙方同意，本协议通过币优铺理财平台审核通过时，本协议成立,在权益标的转让价款支付完成时生效。各方均认可该形式的协议效力及本协议内容。" +
		"各方委托币优铺理财平台保管所有与本协议有关的书面文件和电子信息。各方确认币优铺理财平台提供的与本协议有关的书面文件和电子信息在无与本协议明显冲突及错误情况下，" +
		"应作为本协议有关事项的终局证明。\n");
		bufStr.append("5.2 甲、乙方均同意并确认，甲、乙方通过币优铺理财平台账户和其银行账户的行为均通过第三方支付平台或银行进行，所产生的法律效果及法律责任归属于甲、乙方。" +
		"同时，甲、乙方委托币优铺理财平台根据本协议采取的全部行动和措施的法律后果均归属于甲、乙方，币优铺理财平台不因此承担责任。\n");
		bufStr.append("5.3 如果本协议中的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。\n");
		bufStr.append("5.4 本协议项下的附件和补充协议构成本协议不可分割的一部分，本协议项下无约定的事项以币优铺理财平台公布的相关规则为准。\n");
		title = new Paragraph(bufStr.toString(),FontChinese);
		title.setLeading(18f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		title = new Paragraph("乙方声明：对于本协议条款，币优铺理财平台已应乙方要求进行充分解释及说明，乙方对本协议内容及相应风险已完全知悉并理解。", BoldChinese);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(25f);// 设置行间距//设置上面空白宽度
		document.add(title);
		
		widths = new float[]{ 10f, 25f, 30f };// 设置表格的列宽和列数 默认是4列
		
		table = new PdfPTable(widths);// 建立一个pdf表格
		table.setSpacingBefore(20f);// 设置表格上面空白宽度
		table.setTotalWidth(500);// 设置表格的宽度
		table.setWidthPercentage(100);// 设置表格宽度为%100
		// table.getDefaultCell().setBorder(0);//设置表格默认为无边框
		
		tempValue = new String[]{ "刘神", m.getRealName(), "慧信财富管理有限公司"}; // 租金期次列表
		// ---表头
		cell = new PdfPCell(new Paragraph("甲方", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("乙方", subBoldFontChinese));// 描述
		cell.setFixedHeight(20);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("丙方", subBoldFontChinese));// 描述
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		cell.setFixedHeight(20);
		table.addCell(cell);
		
		for (int j = 0; j < tempValue.length; j++) {
			cell = new PdfPCell(new Paragraph(tempValue[j], moneyFontChinese));// 描述
			cell.setFixedHeight(20);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
		}
		document.add(table);
		document.close();
	}
	
	/**
	 * PDF模板数据填充
	 * @param tempFile 模板文件
	 * @param contentFile 填充完成的文件
	 * @param map 需要填充的数据
	 * @return
	 */
	public static void fromPDFTempletToPdfWithValue(String tempFile, String contentFile,Map<String, String> map){
		try {
			PdfReader reader = new PdfReader(tempFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PdfStamper ps = new PdfStamper(reader, bos);
			/**
			 * 使用中文字体 
			 */
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			AcroFields s = ps.getAcroFields();
			//设置表单域内容
			//解决中文不显示
			Set<String> keys = s.getFields().keySet();
			for (String name : keys) {
		          if (name == null || name.length() <= 0)
		            continue;
//	            String value = s.getField(name);
	            s.setFieldProperty(name, "textfont", bf, null);
	            s.setFieldProperty(name, "textsize", 8, null);
	            s.setField(name, map.get(name));
		    }

			ps.setFormFlattening(true);
			ps.close();
			FileOutputStream fos = new FileOutputStream(contentFile);
			fos.write(bos.toByteArray());
			fos.close();
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成债权转让协议附件表格
	 * @param file 
	 * @param list 债权列表数据
	 * @throws Exception
	 */
	public static void creatPDFTable(String file,List<Map<String, String>> list) throws Exception{
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bf, 8); // 其他所有文字字体
		Document document = new Document(PageSize.A4, 80, 79, 20, 45);
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		
		Paragraph title = new Paragraph("附：债权出让人列表及相应份额", FontChinese);
		title.setLeading(30f);// 设置行间距
		document.add(title);
		//添加表格
		float[] widths = { 20f, 30f, 20f };// 设置表格的列宽和列数
		PdfPTable table = new PdfPTable(widths);
		table.setSpacingBefore(20);
		table.setWidthPercentage(100);// 设置表格宽度为%100
		
		// ---表头
		PdfPCell cell = null;
		cell = new PdfPCell(new Paragraph("债权出让人", FontChinese));// 描述
		cell.setFixedHeight(18);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("身份证号", FontChinese));// 描述
		cell.setFixedHeight(18);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("出让债权额", FontChinese));// 描述
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
		cell.setFixedHeight(18);
		table.addCell(cell);
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			cell = new PdfPCell(new Paragraph(map.get("realName"), FontChinese));
			cell.setFixedHeight(18);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(map.get("idCards"), FontChinese));
			cell.setFixedHeight(18);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(map.get("amount"), FontChinese));
			cell.setFixedHeight(18);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
		}
		document.add(table);
		document.close();
	}
	
	/**
	 * 合并多个PDF
	 * @param files 需要合并的PDF文件
	 * @param savePDF 合并后生成新的PDF文件
	 * @throws Exception
	 */
	public static void  mergePDF(String[] files,String savePDF) throws Exception{
		PdfReader r = new PdfReader(files[0]);
		Document doc = new Document(r.getPageSize(1));
		PdfCopy pdfCopy = new PdfCopy(doc, new FileOutputStream(savePDF));
		doc.open(); // 打开文档
		 for(int i=0; i<files.length; i++)  
         {  
             PdfReader read = new PdfReader(files[i]);  
            
            int n = read.getNumberOfPages();  
             for(int j=1; j<=n; j++)  
             {  
            	 doc.newPage();  
                 PdfImportedPage page = pdfCopy.getImportedPage(read, j);  
                 pdfCopy.addPage(page);  
             }  
             read.close();
         }  
		 r.close();
		doc.close();
	}
	
	/**
	 * pdf添加table
	 * @param file
	 * @param map {"widths":float[],"headers":String[],"footer":String[],"list":List<String[]>}
	 * @throws Exception
	 */
	public static void pdfTable(String file,Map<String,Object> map) throws Exception{
		
		if(!(map.containsKey("body") && map.containsKey("headers") && map.containsKey("widths"))){
			throw new  Exception("参数错误");
		}
		float[] widths = (float[]) map.get("widths");//{ 20f, 25f, 25f, 25f, 25f, 25f};// 设置表格的列宽和列数
		String[] headers = (String[]) map.get("headers");// {"期数", "还款日", "应收本金（元）", "应收利息（元）", "应收总额（元）", "剩余本金（元）" };//表头
		String[] footer = (String[]) map.get("footer");//{"总数", "", "1",  "2",  "3",  ""}
		
		List<String[]> list = (List<String[]>) map.get("body");
		
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bf, 8); // 其他所有文字字体
		Document document = new Document(PageSize.A4, 80, 79, 20, 45);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
    	
		Paragraph title = new Paragraph("附一：回款详情", FontChinese);
		title.setLeading(30f);// 设置行间距
		document.add(title);
		
		//添加表格
		
		PdfPTable table = new PdfPTable(widths);
		table.setSpacingBefore(20);
		table.setWidthPercentage(100);// 设置表格宽度为%100
		//添加表头
		
		PdfPCell cell = null;
		for (String header : headers) {
			cell = new PdfPCell(new Paragraph(header, FontChinese));// 描述
			cell.setFixedHeight(18);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
			table.addCell(cell);
		}
		
		//添加body
		for (String[] tr : list) {
			for (String td : tr) {
				cell = new PdfPCell(new Paragraph(td, FontChinese));
				cell.setFixedHeight(18);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
				table.addCell(cell);
			}
		}
		
		//添加 footer
		if(footer !=null && footer.length >0){
			for (String td : footer) {
				cell = new PdfPCell(new Paragraph(td, FontChinese));
				cell.setFixedHeight(18);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中
				table.addCell(cell);
			}
		}
						  
		document.add(table);
		document.close();
		writer.close();
         
	}
	
	public static void main(String[] args) throws Exception {
		String file1 = "E:/CELL.pdf";//设置模板路径  
		String file2 = "E:/CELL1.pdf";//设置模板路径  
		String file3 = "E:/CELL2.pdf";//设置模板路径
		
		Map<String,Object> map  = new HashMap<String, Object>();
		List<String[]> list = new ArrayList<String[]>();
		float[] widths = { 20f, 25f, 25f, 25f, 25f, 25f};
		String[] headers = {"期数", "还款日", "应收本金（元）", "应收利息（元）", "应收总额（元）", "剩余本金（元）" };
		String[] footer = {"总计", "", "600",  "11.28",  "611.28",  ""};
		
		list.add(new String[]{"1","2017-07-15","100","1.88","101.88","100"});
		list.add(new String[]{"2","2017-07-16","100","1.88","101.88","100"});
		list.add(new String[]{"3","2017-07-17","100","1.88","101.88","100"});
		list.add(new String[]{"4","2017-07-18","100","1.88","101.88","100"});
		list.add(new String[]{"5","2017-07-19","100","1.88","101.88","100"});
		list.add(new String[]{"6","2017-07-10","100","1.88","101.88","100"});
		
		map.put("body", list);
		map.put("widths", widths);
		map.put("headers", headers);
		map.put("footer", footer);
		
		pdfTable(file2, map);
		
		String[] files = new String[]{"file1","file2"};
		
		mergePDF(files, file3);
	}
	
}
