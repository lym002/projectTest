package com.jsjf.common;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 对上传图片做压缩处理
 * @author 何修水
 *
 */
public class ImageUtils {
	private File file = null; // 文件对象
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private InputStream inputStream;
	
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

	public ImageUtils() { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}
	
	public String getInputDir() {
		return inputDir;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}
	
	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * 返回图片大小
	 * @param path 图片路径
	 * @return
	 */
	public  long getPicSize(String path){
		file = new File(path);
		return file.length();
	}
	
	/**
	 * 重新生成文件名
	 * @return
	 */
	public static String getServerFileName() {
		String serverFileName = new SimpleDateFormat("yyyyMMdd")
				.format(new Date()) + UUID.randomUUID() ;
		return serverFileName;
	}
	/**
	 * 获取等比缩放后的图片流
	 * @param inputStream 要缩放的图片
	 * @param outputDir 
	 * @param outputFileName
	 * @param width
	 * @param height
	 * @param gp
	 * @return
	 */
	public BufferedImage getBufferImage(InputStream inputStream,int width,int height,boolean gp){
		this.inputStream = inputStream;
		this.outputWidth = width;
		this.outputHeight = height;
		this.proportion = gp;
		try {
			BufferedImage img ;
			if(inputStream==null){
				//获取源文件
				file = new File(this.inputDir+this.inputFileName);
				if(!file.exists()){
					throw new Exception("文件不存在");
				}
				
				img= ImageIO.read(file);
			}else{
//				file = new File(this.outputDir+this.outputFileName);
//				if (file != null && !file.exists()) {
//					file.getParentFile().mkdirs();
//	            }
				img = ImageIO.read(this.inputStream);
				
			}
			
			
			if(img.getWidth(null)==-1){
				throw new Exception("图片异常");
			}else{
				int newWidth;int newHeight;
				if(this.proportion){
					// 为等比缩放计算输出的图片宽度及高度   
					double rate1 = ((double)img.getWidth(null)/(double)this.outputWidth)+0.1;
					double rate2 = ((double)img.getHeight(null)/(double)this.outputHeight)+0.1;
					double rate = rate1>rate2 ? rate1:rate2;
					newWidth = (int)((double)img.getWidth(null)/rate);
					newHeight = (int)((double)img.getHeight(null)/rate);
				}else{
					newHeight=outputHeight;
					newWidth=outputWidth;
				}
				BufferedImage bufferImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				bufferImage.createGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH),0,0,null);
				bufferImage.createGraphics().dispose();
				return bufferImage;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private String compressPic(){
		try {
			Image img ;
			if(inputStream==null){
				//获取源文件
				file = new File(this.inputDir+this.inputFileName);
				if(!file.exists()){
					return "";
				}
				
				img= ImageIO.read(file);
			}else{
				file = new File(this.outputDir+this.outputFileName);
				if (file != null && !file.exists()) {
					file.getParentFile().mkdirs();
	            }
				img = ImageIO.read(this.inputStream);
				
			}
			
			
			if(img.getWidth(null)==-1){
				return "NO";
			}else{
				int newWidth;int newHeight;
				if(this.proportion){
					// 为等比缩放计算输出的图片宽度及高度   
					double rate1 = ((double)img.getWidth(null)/(double)this.outputWidth)+0.1;
					double rate2 = ((double)img.getHeight(null)/(double)this.outputHeight)+0.1;
					double rate = rate1>rate2 ? rate1:rate2;
					newWidth = (int)((double)img.getWidth(null)/rate);
					newHeight = (int)((double)img.getHeight(null)/rate);
				}else{
					newHeight=outputHeight;
					newWidth=outputWidth;
				}
				BufferedImage bufferImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				bufferImage.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH),0,0,null);
				FileOutputStream out = new FileOutputStream(outputDir+outputFileName);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(bufferImage);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
	
	public String compressPic(String inputDir,String inputFileName,String outputDir,String outputFileName){
		this.inputDir = inputDir;
		this.inputFileName = inputFileName;
		this.outputDir = outputDir;
		this.outputFileName = outputFileName;
		return compressPic();
		
	}
	
	public String compressPic(String inputDir,String inputFileName,String outputDir,String outputFileName,int width,int height,boolean gp){
		this.inputDir = inputDir;
		this.inputFileName = inputFileName;
		this.outputDir = outputDir;
		this.outputFileName = outputFileName;
		this.outputWidth = width;
		this.outputHeight = height;
		this.proportion = gp;
		return compressPic();
	}
	public String compressPic(InputStream inputStream,String outputDir,String outputFileName,int width,int height,boolean gp){
		this.inputStream = inputStream;
		this.outputDir = outputDir;
		this.outputFileName = outputFileName;
		this.outputWidth = width;
		this.outputHeight = height;
		this.proportion = gp;
		return compressPic();
	}
	
	public static void main(String[] args) {
		ImageUtils imageUtils = new ImageUtils();
		int start = (int) System.currentTimeMillis();   // 开始时间 
		File file=new File("D:/photo/cm/");
		File[] files = null;
		if(file.isDirectory()){
			files = file.listFiles();
			for(File f:files){
				if(f.length()>100000){
					String fileName = f.getName();
					imageUtils.compressPic("D:/photo/cm/", fileName, "D:/photo1/cm/", fileName, 1024, 768, true);
				}
			}
		}
		//imageUtils.compressPic("D:\\Test\\", "S025519_JK0000000006126.jpg", "D:\\Test\\", "1.jpg", 1024, 768, true);
		int end = (int) System.currentTimeMillis();   // 开始时间 
		System.out.println(end-start);
	}


}
