package com.jsjf.common;

import java.util.Set;

import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.HtmlEmail;  
import org.apache.log4j.Logger;

import com.jsjf.model.system.Mail;  
  
/**  
 * 邮件发送工具实现类  
 * @author DELL
 *
 */
public class MailUtil {  
  
    protected static final Logger logger = Logger.getLogger(MailUtil.class); 
    public static String host = "smtp.mxhichina.com";// 设置邮件服务器,如果不用163的,自己找找看相关的
    public static int smtpPort = 25;//端口号
    public static String sender = "system@huixinwealth.com";//发件人
    public static String name = "system";//昵称
    public static String username = "system@huixinwealth.com";// 登录账号,一般都是和邮箱名一样吧  
    public static String password = "Huixin2017";// 发件人邮箱的登录密码
 
    public static boolean send(Mail mail) {  
        // 发送email  
        HtmlEmail email = new HtmlEmail();  
        try {  
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName(host); 
            //设置端口
            email.setSmtpPort(smtpPort);
            // 字符编码集的设置  
            email.setCharset(Mail.ENCODEING);  
            // 收件人的邮箱  
            email.addTo(mail.getReceiver());  
            // 发送人的邮箱  
            email.setFrom(sender, name);  
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(username, password);  
            // 要发送的邮件主题  
            email.setSubject(mail.getSubject());  
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(mail.getMessage());  
            // 发送  
            email.send();  
            if (logger.isDebugEnabled()) {  
                logger.debug(sender + " 发送邮件到 " + mail.getReceiver());  
            }  
            return true;  
        } catch (EmailException e) {  
            e.printStackTrace();  
            logger.info(sender + " 发送邮件到 " + mail.getReceiver()  
                    + " 失败");  
            return false;  
        }  
    }  
    public static boolean sendMail(String message){
		//如果短信发送失败，发送邮件
        Mail mail = new Mail(); 
        mail.setReceiver("tec@byp.cn"); // 接收人 
        mail.setSubject("H5短信发送失败！");  
        mail.setMessage(message);  
        MailUtil.send(mail); 
        mail.setReceiver("wei.feng@js-fax.com");
    	return send(mail);
    }
    
    public static boolean sendMails(Mail mail,Set<String> urls){
    	if(Utils.isObjectEmpty(urls)){
    		return false;
    	}
    	for (String url : urls) {
    		mail.setReceiver(url);
    		return MailUtil.send(mail);
    	}
		return true;
    }
    
    public static void main(String[] args) {  
        Mail mail = new Mail(); 
        mail.setReceiver("wuyunmin@js-fax.com"); // 接收人 
        
        mail.setSubject("系统异常");  
        mail.setMessage("短信发送失败！");  
        
        boolean result = MailUtil.send(mail);  
        System.out.println("mail==="+result);
    }  
  
}