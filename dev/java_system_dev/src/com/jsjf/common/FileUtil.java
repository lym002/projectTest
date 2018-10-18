package com.jsjf.common;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	/**
	* 下载文件
	* @param file
	* @param response
	*/
	public static void download(File file, HttpServletResponse response) {  
		try {  
		    // 取得文件名。  
		    String filename = file.getName();
		    // 以流的形式下载文件。  
		    InputStream fis = new BufferedInputStream(new FileInputStream(file));  
		    byte[] buffer = new byte[fis.available()];  
		    fis.read(buffer);  
		    if(fis!=null){
		    	fis.close();  
		    } 
		    // 清空response  
		    response.reset();  
		    // 设置response的Header  
		    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));  
		    response.addHeader("Content-Length", "" + file.length());  
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
		    response.setContentType("application/vnd.ms-excel;charset=gb2312");  
		    toClient.write(buffer);  
		    toClient.flush();  
		    if(fis!=null){
		    	toClient.close(); 
		    } 
		    
		} catch (IOException ex) {  
		    ex.printStackTrace();  
		}
	}
	
	/**
	* 下载文件
	* @param file
	* @param response
	*/
	public static void download(File file, HttpServletRequest request, HttpServletResponse response,String filename) {  
		try {  
		    if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
		    	filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
		    } 
		    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
		    	filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器
		    }
		    // 以流的形式下载文件。  
		    InputStream fis = new BufferedInputStream(new FileInputStream(file));  
		    byte[] buffer = new byte[fis.available()];  
		    fis.read(buffer);  
		    if(fis!=null){
		    	fis.close();  
		    } 
		    // 清空response  
		    response.reset();  
		    // 设置response的Header  
		    if((request.getHeader("User-Agent").toLowerCase().indexOf("firefox")>0)||request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>0){
		    	response.addHeader("Content-Disposition", "attachment;filename=" + filename);  
		    }else{
		    	//response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes())); 
		    	response.addHeader("Content-Disposition", "attachment;filename=\""+ encodeFilename(request,filename)+"\"");
		    }
		    response.addHeader("Content-Length", "" + file.length());  
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
//		    response.setContentType("application;charset=gb2312");  
		    toClient.write(buffer);  
		    toClient.flush();  
		    if(fis!=null){
		    	toClient.close(); 
		    } 
		    
		} catch (IOException ex) {  
		    ex.printStackTrace();  
		}
	}
	
	/**
	 * 下载文件（支持中文文件名）
	 * @param file
	 * @param request
	 * @param response
	 */
	public static void download(File file, HttpServletRequest request, HttpServletResponse response) {  
		try {  
		    // 取得文件名。  
		    String filename = file.getName();
		    if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
		    	filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
		    } 
		    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
		    	filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器
		    }
		    // 以流的形式下载文件。  
		    InputStream fis = new BufferedInputStream(new FileInputStream(file));  
		    byte[] buffer = new byte[fis.available()];  
		    fis.read(buffer);  
		    if(fis!=null){
		    	fis.close();  
		    } 
		    // 清空response  
		    response.reset();  
		    // 设置response的Header  
		    if((request.getHeader("User-Agent").toLowerCase().indexOf("firefox")>0)||request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>0){
		    	response.addHeader("Content-Disposition", "attachment;filename=" + filename);  
		    }else{
		    	//response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes())); 
		    	response.addHeader("Content-Disposition", "attachment;filename=\""+ encodeFilename(request,filename)+"\"");
		    }
		    response.addHeader("Content-Length", "" + file.length());  
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
		    response.setContentType("application/vnd.ms-excel;charset=gb2312");  
		    toClient.write(buffer);  
		    toClient.flush();  
		    if(fis!=null){
		    	toClient.close(); 
		    } 
		    
		} catch (IOException ex) {  
		    ex.printStackTrace();  
		}
	}
	
	/**
	 * 下载时编译文件名
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String encodeFilename(HttpServletRequest request,String fileName) throws UnsupportedEncodingException  {
		String agent =request.getHeader("USER-AGENT");
		try {
			// IE
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
				// Firefox
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				fileName =  new String( fileName.getBytes("UTF-8"), "ISO-8859-1" );
			}
		} catch (UnsupportedEncodingException e) {
			try {
				fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return fileName; 
	} 
	
	/**
	 * 压缩文件列表中的文件
	 * @param files
	 * @param outputStream
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException,ServletException{
		try{
			int size = files.size();
			//压缩列表中的文件
			for(int i = 0; i < size; i++){
				File file = files.get(i);
				zipFile(file, outputStream);
			}
		}catch(IOException e){
			throw e;
		}
	}
	
	/**
	 * 压缩
	 * @param srcPathName
	 * @param zipFile
	 */
	public static void compress(String srcPathName,File zipFile) {  
        File file = new File(srcPathName);  
        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
        try {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
            compress(file, out, basedir);  
            out.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
	
	private static void compress(File file, ZipOutputStream out, String basedir) {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {  
            compressDirectory(file, out, basedir);  
        } else {  
            compressFile(file, out, basedir);  
        }  
    }  
  
    /** 压缩一个目录 */  
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {  
        if (!dir.exists())  
            return;  
  
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  
  
    /** 压缩一个文件 */  
    private static void compressFile(File file, ZipOutputStream out, String basedir) {  
        if (!file.exists()) {  
            return;  
        }  
        try {  
            BufferedInputStream bis = new BufferedInputStream(  
                    new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(basedir + file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[8192];  
            while ((count = bis.read(data, 0, 8192)) != -1) {  
                out.write(data, 0, count);  
            }  
            bis.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
	
		
	/**
	 * 将文件写入到zip文件中
	 * @param inputFile
	 * @param outputstream
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException,ServletException{
		try{
			if(inputFile.exists()){
				if(inputFile.isFile()){
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);
					final int MAX_BYTE = 10 * 1024 *1024; //最大的流为10M
					long streamTotal = 0; //接受流的容量
					int streamNum = 0; //流需要分开的数量
					int leaveByte = 0; //文件剩下的字符数
					byte[] inOutbyte; //byte数组接受文件的数据
                  
					streamTotal = bInStream.available(); //通过available方法取得流的最大字符数
					streamNum = (int)Math.floor(streamTotal / MAX_BYTE); //取得流文件需要分开的数量
					leaveByte = (int)streamTotal % MAX_BYTE; //分开文件之后,剩余的数量
					if (streamNum > 0){
						for(int j = 0; j < streamNum; ++j){
							inOutbyte = new byte[MAX_BYTE];
							//读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE);  //写出流
						}
					}
					//写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry();     //Closes the current ZIP entry and positions the stream for writing the next entry
					bInStream.close();    //关闭
					inStream.close();
				}
			}else{
				throw new ServletException("文件不存在！");
			}
		}catch(IOException e){
			throw e;
		}
	}
	
	/**
	 * 创建临时文件
	 * @param file
	 * @param fileTemp
	 * @throws FileNotFoundException 
	 */
	public static void createTemp(File file , File fileTemp) throws FileNotFoundException,IOException{
		//获取源文件和目标文件的输入输出流  
		FileInputStream fin = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(fileTemp);
		 //获取输入输出通道  
        FileChannel fcin = fin.getChannel();  
        FileChannel fcout = fos.getChannel(); 
        ByteBuffer buffer =  ByteBuffer.allocate(1024);  
		try {
	        while(true){  
	            //重设此缓冲区，使它可以接受读入的数据。Buffer对象中的limit=capacity;  
	            buffer.clear();  
	            //从输入通道中将数据读入到缓冲区  
	            int temp = fcin.read(buffer);  
	            if(temp == -1){  
	                break;  
	            }  
	            //让缓冲区将新入读的输入写入另外一个通道.Buffer对象中的limit=position  
	            buffer.flip();  
	            //从输出通道中将数据写入缓冲区  
	            fcout.write(buffer);  
	        }
		}finally {  
            closeQuietly(fin);
            closeQuietly(fos);
            closeQuietly(fcin);
            closeQuietly(fcout);
        }    
	}
	
	public static void closeQuietly(Closeable closeable) throws IOException {     
        if (closeable != null) {             
            closeable.close();         
        }     
	}
	
}
