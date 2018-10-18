package com.jsjf.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jsjf.model.activity.BypCommodityDetailBean;
import com.jsjf.model.store.CommodityExchange;

public class ReadExcel {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
 
    //构造方法
    public ReadExcel(){}
  
    /**
     * 读EXCEL文件，获取信息集合
     * @param fielName
     * @return
     * @throws Exception 
     */
    public <E> List<E> getExcelInfo(String fileName,MultipartFile Mfile,BypCommodityDetailBean bean) throws Exception{
	    	 String filePath = this.getClass().getResource("/").getPath()+"/resources/template/";
	         //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
	         CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
	         File file = new  File(filePath);
	         //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	         if (!file.exists()) file.mkdirs();
	         //新建一个文件
	         File file1 = new File(filePath + new Date().getTime() + ".xlsx"); 
	         //将上传的文件写入新建的文件中
	         cf.getFileItem().write(file1); 
	         //初始化客户信息的集合    
	         List<E> list=new ArrayList<E>();
	         //初始化输入流
	         InputStream is = null;
	    try{
	         //根据新建的文件实例化输入流
	         is = new FileInputStream(file1);
	         //根据excel里面的内容读取信息
	         list = getExcelInfo(is,bean); 
        }finally{
           FileUtil.closeQuietly(is);
           if(file1.exists()){
        	   file1.delete();
           }
        }
        return list;
    }
    
    public <E> List<E> getCommodityExcelInfo(String fileName,MultipartFile Mfile,CommodityExchange bean) throws Exception{
   	 String filePath = this.getClass().getResource("/").getPath()+"/resources/template/";
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
        File file = new  File(filePath);
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) file.mkdirs();
        //新建一个文件
        File file1 = new File(filePath + new Date().getTime() + ".xlsx"); 
        //将上传的文件写入新建的文件中
        cf.getFileItem().write(file1); 
        //初始化客户信息的集合    
        List<E> list=new ArrayList<E>();
        //初始化输入流
        InputStream is = null;
   try{
        //根据新建的文件实例化输入流
        is = new FileInputStream(file1);
        //根据excel里面的内容读取信息
        list = getCommodityExcelInfo(is,bean); 
   }finally{
      FileUtil.closeQuietly(is);
      if(file1.exists()){
   	   file1.delete();
      }
   }
   return list;
}
    
    /**
     * 根据excel里面的内容读取信息
     * @param is 输入流
     * @return
     * @throws IOException
     */
    public <E> List<E> getExcelInfo(InputStream is,BypCommodityDetailBean bean) throws IOException{
         List<E> list=null;
         /** 根据版本选择创建Workbook的方式 */
         Workbook wb = null;
        
         wb = new XSSFWorkbook(is); 
         //读取Excel里面客户的信息
         list=readExcelValue(wb,bean);
        
         return list;
    }
    
    public <E> List<E> getCommodityExcelInfo(InputStream is,CommodityExchange bean) throws IOException{
        List<E> list=null;
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb = null;
       
        wb = new XSSFWorkbook(is); 
        //读取Excel里面客户的信息
        list=readCommodityExcelValue(wb,bean);
       
        return list;
   }
    
    /**
     * 读取Excel里面客户的信息
     * @param wb
     * @return
     */
    private <E> List<E> readExcelValue(Workbook wb,BypCommodityDetailBean bean){ 
        //得到第一个shell  
         Sheet sheet=wb.getSheetAt(0);
         
        //得到Excel的行数
         this.totalRows=sheet.getPhysicalNumberOfRows();
         
        //得到Excel的列数(前提是有行数)
         if(totalRows>=1 && sheet.getRow(0) != null){
              this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
         }
         
         List<E> beanList=new ArrayList<E>();
        //循环Excel行数,从第二行开始。标题不入库
         for(int r=1;r<totalRows;r++){
             Row row = sheet.getRow(r);
             if (row == null) continue;
             bean = new BypCommodityDetailBean();
             DataFormatter formatter = new DataFormatter();
             bean = new BypCommodityDetailBean();
             //循环Excel的列
             for(int c = 0; c <this.totalCells; c++){
                 Cell cell = row.getCell(c);
                 if (null != cell){
                	 if(c == 0 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                		 bean.setId(Integer.parseInt(formatter.formatCellValue(cell)));//id
                	 }else if(c==1 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setUid(Integer.parseInt(formatter.formatCellValue(cell)));//用户id
                     }else if(c==2 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setMobilePhone(formatter.formatCellValue(cell));//手机号
                     }else if(c==5 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setCode(formatter.formatCellValue(cell));//兑换码
                     }else if(c==6 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setAddress(formatter.formatCellValue(cell));//地址
                     }else if(c==7 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setRealName(formatter.formatCellValue(cell));//姓名
                     }
                 }
             }
           //添加客户
             beanList.add((E)bean);
         }
         return beanList;
    }
    
    private <E> List<E> readCommodityExcelValue(Workbook wb,CommodityExchange bean){ 
        //得到第一个shell  
         Sheet sheet=wb.getSheetAt(0);
         
        //得到Excel的行数
         this.totalRows=sheet.getPhysicalNumberOfRows();
         
        //得到Excel的列数(前提是有行数)
         if(totalRows>=1 && sheet.getRow(0) != null){
              this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
         }
         
         List<E> beanList=new ArrayList<E>();
        //循环Excel行数,从第二行开始。标题不入库
         for(int r=1;r<totalRows;r++){
             Row row = sheet.getRow(r);
             if (row == null) continue;
             bean = new CommodityExchange();
             DataFormatter formatter = new DataFormatter();
             bean = new CommodityExchange();
             //循环Excel的列
             for(int c = 0; c <this.totalCells; c++){
                 Cell cell = row.getCell(c);
                 if (null != cell){
                	 if(c == 0 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                		 bean.setCode(formatter.formatCellValue(cell));//卡密
                	 }else if(c==1 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setExchangeId(Integer.parseInt(formatter.formatCellValue(cell)));//商品ID
                     }else if(c==2 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setExpirationTime(formatter.formatCellValue(cell));//时间
                     }else if(c==3 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setCommodityStatus(Integer.parseInt(formatter.formatCellValue(cell)));//发放状态
                     }else if(c==4 && Utils.isObjectNotEmpty(formatter.formatCellValue(cell))){
                    	 bean.setRemark(formatter.formatCellValue(cell));//备注
                     }
                	 bean.setAddDate(new Date());
                	 if(Utils.isObjectEmpty(bean.getExpirationTime())){
        				 Calendar c1 = Calendar.getInstance();
        		         c1.add(Calendar.YEAR, 3);
        				bean.setExpirationTime(c1.getTime().toString());
        			}
                 }
             }
             beanList.add((E)bean);
         }
         return beanList;
    }
    
}