package com.jsjf.model.system;

import java.io.Serializable;

@SuppressWarnings("serial")  
public class Mail implements Serializable {  
  
    public static final String ENCODEING = "UTF-8";  
  
    private String receiver; // 收件人的邮箱  
    private String subject; // 主题  
    private String message; // 信息(支持HTML)  
  
    public String getReceiver() {  
        return receiver;  
    }  
  
    public void setReceiver(String receiver) {  
        this.receiver = receiver;  
    }  
  
    public String getSubject() {  
        return subject;  
    }  
  
    public void setSubject(String subject) {  
        this.subject = subject;  
    }  
  
    public String getMessage() {  
        return message;  
    }  
  
    public void setMessage(String message) {  
        this.message = message;  
    }
}