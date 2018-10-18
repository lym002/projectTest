package com.jzh.http;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientHelper{
    
    public static String doHttp(String urlStr,String charSet,Object parameters, String timeOut) throws Exception{
    	String responseString="";
    	PostMethod xmlpost;
 	    int statusCode = 0;
 	    HttpClient httpclient = new HttpClient();
 	    httpclient.setConnectionTimeout(new Integer(timeOut).intValue());
 	    xmlpost = new PostMethod(urlStr);
 	    httpclient.getParams().setParameter(
 	    		HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
        try{
        	//组合请求参数
        	List<NameValuePair> list=new ArrayList<NameValuePair>();
			Method[] ms=parameters.getClass().getMethods();
			for(int i=0;i<ms.length;i++){
				Method m=ms[i];
				String name=m.getName();
				if(name.startsWith("get")){
					String param=name.substring(3,name.length());
					param=param.substring(0,1).toLowerCase()+param.substring(1,param.length());
					if(param.equals("class")){
						continue;
					}
					Object value="";
					try {
						value = m.invoke(parameters,null);
						NameValuePair nvp=new NameValuePair(param,value !=null ?value.toString() : "");
						list.add(nvp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw e;
					}
				}
			}
			NameValuePair[] nvps=new NameValuePair[list.size()];
        	xmlpost.setRequestBody(list.toArray(nvps)); 
        	statusCode = httpclient.executeMethod(xmlpost);
	    	responseString = xmlpost.getResponseBodyAsString();
            if(statusCode<HttpURLConnection.HTTP_OK || statusCode>=HttpURLConnection.HTTP_MULT_CHOICE){
                System.err.println("失败返回码[" + statusCode + "]");
                throw new Exception("请求接口失败，失败码[ "+ statusCode +" ]");
            }
        }catch(IOException e){
            e.printStackTrace();
            System.err.println(e.toString());
            throw e;
        }
        return responseString;
    }

}
