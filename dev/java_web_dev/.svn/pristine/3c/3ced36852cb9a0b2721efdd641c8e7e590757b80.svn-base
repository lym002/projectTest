package com.jsjf.common;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import sun.net.ftp.FtpClient;

@SuppressWarnings("all")
public class SFtpUtil {
	//生产环境172.16.194.199
	private String IP = "192.168.1.13";//IP
	
    private int PORT = 22;//端口
    
  private String USERNAME = "sftp";//用户名
    
    private String PASSWORD = "sftp";//密码
    

    
    //路径
    private String PATH = "";
    private Session session = null;
    private Channel channel = null;
    
    public SFtpUtil(){
    	
    }
    
    public SFtpUtil(String ip,String username,String password){
    	this.IP = ip;
    	this.USERNAME = username;
    	this.PASSWORD = password;
    }
    
    public SFtpUtil(String ip, int port,String username,String password){
    	this.IP = ip;
    	this.PORT = port;
    	this.USERNAME = username;
    	this.PASSWORD = password;
    }
    
    /*** 连接ftp服务器* * @throws IOException */
	public boolean connectServer() throws Exception{
		JSch jsch= new JSch();
		try {
			session = jsch.getSession(this.USERNAME, this.IP, this.PORT);
			session.setPassword(this.PASSWORD);
		
			Properties config = new Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config); // 为Session对象设置properties
	        session.setTimeout(Integer.MAX_VALUE); // 设置timeout时间
	        session.connect(); // 通过Session建立链接

	        channel = session.openChannel("sftp"); // 打开SFTP通道
	        channel.connect(); // 建立SFTP通道的连接
			// 用2进制上传、下载
			System.out.println("已登录到\"" +this.IP +"\"目录");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 关闭sftp连接
	 * @throws Exception
	 */
    public void closeServer() throws Exception{
    	try {
    		if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        
    }
    
    /**
     * 上传文件
     * @param in 要上传的文件
     * @param newDir 新的文件存放路径
     * @param newFileName 新的文件名
     */
    public void put(InputStream in,String newDir,String newFileName) throws Exception{
    	ChannelSftp chsftp = (ChannelSftp)channel;
    	try {
    		createPath(newDir, chsftp);
    		chsftp.put(in, newDir+newFileName);
    		chsftp.quit();
    		this.closeServer();
    		System.out.println("图片上传成功");
		} catch (Exception e) {
			this.closeServer();
			System.out.println("图片上传出错！");
			throw e;
		}
    	
    }
    
    /**
     * 目录创建
     * @param newDir
     * @param chsftp
     * @throws SftpException
     */
	private void createPath(String newDir, ChannelSftp chsftp)
			throws SftpException {
		String splitStr = newDir.indexOf("\\")==-1?"/":"\\";
		String[] docName = newDir.substring(0, newDir.lastIndexOf(splitStr)).split(splitStr);
		String mikdir="";
		for(int i=1;i<docName.length-1;i++){
			mikdir = mikdir+splitStr+docName[i];
			Vector<LsEntry> fileNames = chsftp.ls(mikdir);
			boolean exists = true;
			for(LsEntry obj : fileNames){
				SftpATTRS t = obj.getAttrs();
				if(t.isDir()&&obj.getFilename().equals(docName[i+1])){//不存在文件夹，创建文件夹
						exists=false;
				}
			}
			if(exists){
				chsftp.cd(mikdir);
				chsftp.mkdir(mikdir+splitStr+docName[i+1]);
				System.out.println(mikdir+splitStr+docName[i+1]+" 目录创建成功!");
			}
		}
	}
    
    /**
     * 上传文件 
     * @param in 要上传的文件
     * @param newDir 新的文件存放路径
     * @param newFileName 新的文件名（全名）
     */
    public void put(BufferedImage img,String newDir,String newFileName)throws Exception{
    	ChannelSftp chsftp = (ChannelSftp)channel;
    	try {
    		this.createPath(newDir, chsftp);
    		InputStream in = null;
    		ImageOutputStream ios = null;
    		
    		ByteArrayOutputStream os = new ByteArrayOutputStream();
    		ios = ImageIO.createImageOutputStream(os);
    		ImageIO.write(img, "jpg", ios);
    		in = new ByteArrayInputStream(os.toByteArray());
    		chsftp.put(in, newDir+newFileName);
    		chsftp.quit();
    		this.closeServer();
    		System.out.println("图片上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.closeServer();
			System.out.println("图片上传出错！");
		}
    	
    }
    
    /**
    * 下载文件 
    * @param directory 下载目录
    * @param downloadFile 下载的文件
    * @param saveFile 存在本地的路径
    * @param sftp
    */
    public void download(String directory, String downloadFile,String saveFile) {
	    try {
	    	ChannelSftp chsftp = (ChannelSftp)channel;
	    	chsftp.cd(directory);
		    File file=new File(saveFile);
		    chsftp.get(downloadFile, new FileOutputStream(file));
		    this.closeServer();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
    /**
     * 下载文件 
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param sftp
     */
     public InputStream download(String directory, String downloadFile) throws Exception{
 	    try {
 	    	if(!channel.isConnected()){
 	    		this.connectServer();
 	    	}
 	    	ChannelSftp chsftp = (ChannelSftp)channel;
 	    	chsftp.cd(directory);
 	    	InputStream in = chsftp.get(downloadFile);
 	    	return in;
 	    	
 	    } catch (Exception e) {
 	    	this.closeServer();
 	    	e.printStackTrace();
 	    	return null;
 	    }
     }

    /**
    * 删除文件
    * @param directory 要删除文件所在目录
    * @param deleteFile 要删除的文件
    * @param sftp
    */
    public void delete(String directory, String deleteFile) {
	    try {
	    	ChannelSftp sftp = (ChannelSftp)channel;
		    sftp.cd(directory);
		    sftp.rm(deleteFile);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
	
	public static void main(String[] args)throws Exception {
		SFtpUtil sftpUtil = new SFtpUtil("192.168.1.245", 22, "root", "yzxx1234!!");
		boolean isconnect = sftpUtil.connectServer();
		System.out.println(isconnect ? "连接成功！" : "连接失败！");
//		File file = new File("D:\\photo\\cm\\S112768_JK0000000081425.jpg");
//		InputStream in = new FileInputStream(file);
//		sftpUtil.put(in, "/photo/cm11/cm/","aa.jpg");
		
	}
}
