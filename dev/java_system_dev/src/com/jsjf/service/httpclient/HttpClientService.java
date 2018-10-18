package com.jsjf.service.httpclient;

public interface HttpClientService {
	
	 /**
     * 
     * 功能描述：发送序列化对象
     * @param url
     * @param inputObj
     * @return
     */
    public Object postSendHttp(String url, Object inputObj);

	public String postSendHttp(String url, String body);
	
}
