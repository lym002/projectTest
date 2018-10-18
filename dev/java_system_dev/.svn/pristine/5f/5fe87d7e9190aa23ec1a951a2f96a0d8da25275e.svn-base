package com.jzh.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

@SuppressWarnings("all")
public class JzhSFtpUtil {
    private Logger logger = Logger.getLogger(JzhSFtpUtil.class);
	//测试环境
	private String IP;//IP
    private int PORT;//端口
    private String USERNAME;//用户名
    private String PASSWORD;//密码
    
    //路径
    private String PATH = "";
    private Session session = null;
    private ChannelSftp channel = null;
    
    public JzhSFtpUtil(){
    	IP = ConfigReader.getConfig("ip");
    	PORT = new Integer(ConfigReader.getConfig("port"));
    	USERNAME = ConfigReader.getConfig("username");
    	PASSWORD = ConfigReader.getConfig("password");
    }
    
    public JzhSFtpUtil(String ip,String username,String password){
    	this.IP = ip;
    	this.USERNAME = username;
    	this.PASSWORD = password;
    }
    
    public JzhSFtpUtil(String ip, int port,String username,String password){
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

	        channel = (ChannelSftp) session.openChannel("sftp"); // 打开SFTP通道
	        channel.connect(); // 建立SFTP通道的连接
			// 用2进制上传、下载
			logger.info("已登录到\"" +this.IP +"\"目录");
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
    		logger.info("图片上传成功");
		} catch (Exception e) {
			this.closeServer();
			logger.error("图片上传出错！",e);
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
				logger.info(mikdir+splitStr+docName[i+1]+" 目录创建成功!");
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
    		logger.info("图片上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.closeServer();
			logger.error("图片上传出错！",e);
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
    /**
     * 切换工作目录
     * <p>
     * 使用示例，SFTP服务器上的目录结构如下：/testA/testA_B/
     * <table border="1">
     * <tr><td>当前目录</td><td>方法</td><td>参数(绝对路径/相对路径)</td><td>切换后的目录</td></tr>
     * <tr><td>/</td><td>changeDir("testA")</td><td>相对路径</td><td>/testA/</td></tr>
     * <tr><td>/</td><td>changeDir("testA/testA_B")</td><td>相对路径</td><td>/testA/testA_B/</td></tr>
     * <tr><td>/</td><td>changeDir("/testA")</td><td>绝对路径</td><td>/testA/</td></tr>
     * <tr><td>/testA/testA_B/</td><td>changeDir("/testA")</td><td>绝对路径</td><td>/testA/</td></tr>
     * </table>
     * </p>
     * @param pathName 路径
     * @return boolean
     */
    public boolean changeDir(String pathName){
        if(pathName == null || pathName.trim().equals("")){
            logger.debug("invalid pathName");
            return false;
        }
        
        try {
            channel.cd(pathName.replaceAll("\\\\", "/"));
            logger.debug("directory successfully changed,current dir=" + channel.pwd());
            return true;
        } catch (SftpException e) {
            logger.error("failed to change directory",e);
            return false;
        }
    }
    
    /**
     * 切换到上一级目录
     * <p>
     * 使用示例，SFTP服务器上的目录结构如下：/testA/testA_B/
     * <table border="1">
     * <tr><td>当前目录</td><td>方法</td><td>切换后的目录</td></tr>
     * <tr><td>/testA/</td><td>changeToParentDir()</td><td>/</td></tr>
     * <tr><td>/testA/testA_B/</td><td>changeToParentDir()</td><td>/testA/</td></tr>
     * </table>
     * </p>
     * @return boolean
     */
    public boolean changeToParentDir(){
        return changeDir("..");
    }
    
    /**
     * 切换到根目录
     * @return boolean
     */
    public boolean changeToHomeDir(){
        String homeDir = null;
        try {
            homeDir = channel.getHome();
        } catch (SftpException e) {
            logger.error("can not get home directory",e);
            return false;
        }
        return changeDir(homeDir);
    }
    
    /**
     * 创建目录
     * <p>
     * 使用示例，SFTP服务器上的目录结构如下：/testA/testA_B/
     * <table border="1">
     * <tr><td>当前目录</td><td>方法</td><td>参数(绝对路径/相对路径)</td><td>创建成功后的目录</td></tr>
     * <tr><td>/testA/testA_B/</td><td>makeDir("testA_B_C")</td><td>相对路径</td><td>/testA/testA_B/testA_B_C/</td></tr>
     * <tr><td>/</td><td>makeDir("/testA/testA_B/testA_B_D")</td><td>绝对路径</td><td>/testA/testA_B/testA_B_D/</td></tr>
     * </table>
     * <br/>
     * <b>注意</b>，当<b>中间目录不存在</b>的情况下，不能够使用绝对路径的方式期望创建中间目录及目标目录。
     * 例如makeDir("/testNOEXIST1/testNOEXIST2/testNOEXIST3")，这是错误的。
     * </p>
     * @param dirName 目录
     * @return boolean
     */
    public boolean makeDir(String dirName){
        try {
            channel.mkdir(dirName);
            logger.debug("directory successfully created,dir=" + dirName);
            return true;
        } catch (SftpException e) {
            logger.error("failed to create directory", e);
            return false;
        }
    }
    
    /**
     * 删除文件夹
     * @param dirName
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public boolean delDir(String dirName){
        if(!changeDir(dirName)){
            return false;
        }
        
        Vector<LsEntry> list = null;
        try {
            list = channel.ls(channel.pwd());
        } catch (SftpException e) {
            logger.error("can not list directory",e);
            return false;
        }
        
        for(LsEntry entry : list){
            String fileName = entry.getFilename();
            if(!fileName.equals(".") && !fileName.equals("..")){
                if(entry.getAttrs().isDir()){
                    delDir(fileName);
                } else {
                    delFile(fileName);
                }
            }
        }
        
        if(!changeToParentDir()){
            return false;
        }
        
        try {
            channel.rmdir(dirName);
            logger.debug("directory " + dirName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            logger.error("failed to delete directory " + dirName,e);
            return false;
        }
    }
    
    /**
     * 删除文件
     * @param fileName 文件名
     * @return boolean
     */
    public boolean delFile(String fileName){
        if(fileName == null || fileName.trim().equals("")){
            logger.debug("invalid filename");
            return false;
        }
        
        try {
            channel.rm(fileName);
            logger.debug("file " + fileName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            logger.error("failed to delete file " + fileName,e);
            return false;
        }
    }
    
    /**
     * 当前目录下文件及文件夹名称列表
     * @return String[]
     */
    public String[] ls(){
        return list(Filter.ALL);
    }
    
    /**
     * 指定目录下文件及文件夹名称列表
     * @return String[]
     */
    public String[] ls(String pathName){
        String currentDir = currentDir();
        if(!changeDir(pathName)){
            return new String[0];
        };
        String[] result = list(Filter.ALL);
        if(!changeDir(currentDir)){
            return new String[0];
        }
        return result;
    }
    
    /**
     * 当前目录下文件名称列表
     * @return String[]
     */
    public String[] lsFiles(){
        return list(Filter.FILE);
    }
    
    /**
     * 指定目录下文件名称列表
     * @return String[]
     */
    public String[] lsFiles(String pathName){
        String currentDir = currentDir();
        if(!changeDir(pathName)){
            return new String[0];
        };
        String[] result = list(Filter.FILE);
        if(!changeDir(currentDir)){
            return new String[0];
        }
        return result;
    }
    
    /**
     * 当前目录下文件夹名称列表
     * @return String[]
     */
    public String[] lsDirs(){
        return list(Filter.DIR);
    }
    
    /**
     * 指定目录下文件夹名称列表
     * @return String[]
     */
    public String[] lsDirs(String pathName){
        String currentDir = currentDir();
        if(!changeDir(pathName)){
            return new String[0];
        };
        String[] result = list(Filter.DIR);
        if(!changeDir(currentDir)){
            return new String[0];
        }
        return result;
    }
    
    /**
     * 当前目录是否存在文件或文件夹
     * @param name 名称
     * @return boolean
     */
    public boolean exist(String name){
        return exist(ls(), name);
    }
    
    /**
     * 指定目录下，是否存在文件或文件夹
     * @param path 目录
     * @param name 名称
     * @return boolean
     */
    public boolean exist(String path,String name){
        return exist(ls(path),name);
    }
    
    /**
     * 当前目录是否存在文件
     * @param name 文件名
     * @return boolean
     */
    public boolean existFile(String name){
        return exist(lsFiles(),name);
    }
    
    /**
     * 指定目录下，是否存在文件
     * @param path 目录
     * @param name 文件名
     * @return boolean
     */
    public boolean existFile(String path,String name){
        return exist(lsFiles(path), name);
    }
    
    /**
     * 当前目录是否存在文件夹
     * @param name 文件夹名称
     * @return boolean
     */
    public boolean existDir(String name){
        return exist(lsDirs(), name);
    }
    
    /**
     * 指定目录下，是否存在文件夹
     * @param path 目录
     * @param name 文家夹名称
     * @return boolean
     */
    public boolean existDir(String path,String name){
        return exist(lsDirs(path), name);
    }
    
    /**
     * 当前工作目录
     * @return String
     */
    public String currentDir(){
        try {
            return channel.pwd();
        } catch (SftpException e) {
            logger.error("failed to get current dir",e);
            return homeDir();
        }
    }
    
    /**
     * 登出
     */
    public void logout(){
        if(channel != null){
            channel.quit();
            channel.disconnect();
        }
        if(session != null){
            session.disconnect();
        }
        logger.debug("logout successfully");
    }
    
    
    //------private method ------
    
    /** 枚举，用于过滤文件和文件夹  */
    private enum Filter {/** 文件及文件夹 */ ALL ,/** 文件 */ FILE ,/** 文件夹 */ DIR };
    
    /**
     * 列出当前目录下的文件及文件夹
     * @param filter 过滤参数
     * @return String[] 
     */
    @SuppressWarnings("unchecked")
    private String[] list(Filter filter){
        Vector<LsEntry> list = null;
        try {
            //ls方法会返回两个特殊的目录，当前目录(.)和父目录(..)
            list = channel.ls(channel.pwd());
        } catch (SftpException e) {
            logger.error("can not list directory",e);
            return new String[0];
        }
        
        List<String> resultList = new ArrayList<String>();
        for(LsEntry entry : list){
            if(filter(entry, filter)){
                resultList.add(entry.getFilename());
            }
        }
        return resultList.toArray(new String[0]);
    }
    
    /**
     * 判断是否是否过滤条件
     * @param entry LsEntry
     * @param f 过滤参数
     * @return boolean
     */
    private boolean filter(LsEntry entry,Filter f){
        if(f.equals(Filter.ALL)){
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("..");
        } else if(f.equals(Filter.FILE)){
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && !entry.getAttrs().isDir();
        } else if(f.equals(Filter.DIR)){
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getAttrs().isDir();
        }
        return false;
    }
    
    /**
     * 根目录
     * @return String
     */
    private String homeDir(){
        try {
            return channel.getHome();
        } catch (SftpException e) {
            return "/";
        }
    }
    
    /**
     * 判断字符串是否存在于数组中
     * @param strArr 字符串数组
     * @param str 字符串
     * @return boolean
     */
    private boolean exist(String[] strArr,String str){
        if(strArr == null || strArr.length == 0){
            return false;
        }
        if(str == null || str.trim().equals("")){
            return false;
        }
        for(String s : strArr){
            if(s.equalsIgnoreCase(str)){
                return true;
            }
        }
        return false;
    }
	
	public static void main(String[] args)throws Exception {
		JzhSFtpUtil sftpUtil = new JzhSFtpUtil("192.168.1.245", 22, "root", "yzxx1234!!");
		boolean isconnect = sftpUtil.connectServer();
		System.out.println(isconnect ? "连接成功！" : "连接失败！");
//		File file = new File("D:\\photo\\cm\\S112768_JK0000000081425.jpg");
//		InputStream in = new FileInputStream(file);
//		sftpUtil.put(in, "/photo/cm11/cm/","aa.jpg");
		
	}
}
