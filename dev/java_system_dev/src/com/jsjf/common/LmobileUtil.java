package com.jsjf.common;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *  备用短信平台微网通联  Lmobile 乐信通
 */
public class LmobileUtil {
    private static Logger log = Logger.getLogger(LmobileUtil.class);
    //正式环境配置，后台访问地址f.lmobile.cn
    private static final String summitUrl = "http://cf.51welink.com/submitdata/Service.asmx/g_Submit";

    private static final String sname = "dlhxcf00";//账号

    private static final String spwd = "Huixin2018";//密码

    private static final String sprid = "1012818";//产品编号

    private static final String enc = "UTF-8";//产品编号
    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    public static int newSendMsg(String mobile,String content) throws UnsupportedEncodingException {
        String sms="";
        int res=-99;
        try {
            String smsg = URLEncoder.encode(content, enc);
            String PostData = "sname="+sname+"&spwd="+spwd+"&scorpid=&sprdid="+sprid+"&sdst="+mobile+"&smsg="+smsg;
            sms = SMS(PostData, summitUrl);
            res = Integer.parseInt(DomXMLEncodeUtils.readStringXml(sms,"State"));
            if (0==res){
                log.info("发送成功");
                res = 99;
            }else {
                res = -99;
                log.error("发送失败!:手机号:"+mobile+",短信内容:"+content+"失败CODE为:"+res);
            }
        }catch (Exception e){
            log.error(sms);
        }
        return res;
    }
    public static void main(String[] args) throws UnsupportedEncodingException, DocumentException {
        String msg=" 您的验证码是：888888，请勿告诉他人。【慧信财富】";
        String PostData = "sname="+sname+"&spwd="+spwd+"&scorpid=&sprdid="+sprid+"&sdst=15021054564&smsg="+ URLEncoder.encode(msg,enc);
        String sms = SMS(PostData, summitUrl);
        System.out.println(sms);
        String s = DomXMLEncodeUtils.readStringXml(sms,"State");
        if (s.equals("0")){
            System.out.println("发送成功");
        }else {
            System.out.println("失败CODE为:"+s);
        }
    }
}
