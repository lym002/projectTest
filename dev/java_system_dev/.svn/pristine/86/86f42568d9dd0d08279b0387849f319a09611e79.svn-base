package com.esign.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/***
 * 
* @Description: 文件辅助类
* @Team: 公有云技术支持小组
* @Author: 天云小生
* @Date: 2017年11月19日
 */
public class FileHelper {
    private static Logger LOG = LoggerFactory.getLogger(FileHelper.class);
    /***
     * 获取文件基本信息
     * @param filePath
     * @return
     */
    public static Map<String, String> getFileInfo(String filePath) {
        Map<String, String> fileInfo = new LinkedHashMap<String, String>();
        File file = new File(filePath);
        fileInfo.put("FileName", file.getName());
        fileInfo.put("FileLength", String.valueOf(file.length()));
        return fileInfo;
    }
    /***
     * 获取文件的Bytes
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getBytes(String filePath) {
        File file = new File(filePath);
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
        }
        catch (FileNotFoundException e) {
            LOG.error("获取文件二进制字节流异常：" + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e) {
            LOG.error("获取文件二进制字节流异常：" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                fis.close();
            }
            catch (IOException e) {
        	 LOG.error("文件二进制字节流关闭时发生异常：" + e.getMessage());
                e.printStackTrace();
            }
        }
        return buffer;
    }
    
	/**
	 * 根据byte数组生产文件
	 * 
	 * @param filename
	 *            String 文件路径
	 * @throws IOException
	 * @return byte[] 文件内容
	 */
	public static Map<String, String> saveFileByStream(byte[] bytes, String folder, String fileName) {
		Map<String, String> fileResult = new HashMap<String, String>();
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(folder);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(folder + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
			fileResult.put("errCode", "0");
			fileResult.put("msg", "保存签署后文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("保存签署后文件失败：" + e.getMessage());
			fileResult.put("errCode", "0");
			fileResult.put("msg", "保存签署后文件成功");
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					LOG.info("保存签署后文件失败：" + e1.getMessage());
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					LOG.info("保存签署后文件失败：" + e1.getMessage());
				}
			}
		}
		return fileResult;
	}
	
	/***
	 * 根据图片路径将图片转成Base64数据
	 * 
	 * @return Base64数据
	 */
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			LOG.info("上传的印章图片转sealData错误：" + e.getMessage());
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		byte[] en = Base64.encodeBase64(data);
		return new String(en);// 返回Base64编码过的字节数组字符串
	}
}