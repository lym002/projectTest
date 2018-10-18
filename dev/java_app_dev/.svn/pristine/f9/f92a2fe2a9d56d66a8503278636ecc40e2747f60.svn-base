package com.jsjf.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient 基础工具类
 * 
 */
public class HttpClientUtils {
    
    public static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    
    private static String DEFAULT_ACCOUNT = "bhadmin";
    
    private static String DEFAULT_PASSWORD = "bw@2015";

    /**
     * POST请求数据
     * 
     * @param url 请求路径
     * @param params 请求数据
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, params, DEFAULT_ACCOUNT, DEFAULT_PASSWORD);
    }

    /**
     * GET请求数据
     * 
     * @param url 请求路径
     * @return
     */
    public static String get(String url) {
        return get(url, DEFAULT_ACCOUNT, DEFAULT_PASSWORD);
    }

    /**
     * POST请求数据
     * 
     * @param url 请求路径
     * @param params 请求数据
     * @return
     */
    public static String post(String url, Map<String, String> params, String account, String password) {
        HttpClient client = new DefaultHttpClient();

        HttpPost httpPost = packHttpPost(url, params);
        String body = null;
        body = doRequest(client, httpPost, account, password);
        client.getConnectionManager().shutdown();
        return body.toString();
    }
    
    public static String postNoPassword(String url, Map<String, String> params) {
        HttpClient client = new DefaultHttpClient();

        HttpPost httpPost = packHttpPost(url, params);
        String body = null;
        body = doRequest(client, httpPost, null, null);
        client.getConnectionManager().shutdown();
        return body.toString();
    }

    /**
     * GET请求数据
     * 
     * @param url 请求路径
     * @return
     */
    public static String get(String url, String account, String password) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpGet httpGet = new HttpGet(url);
        body = doRequest(httpclient, httpGet, account, password);
        httpclient.getConnectionManager().shutdown();

        return body;
    }

    /**
     * 包装请求数据
     * 
     * @param url
     * @param params
     * @return
     */
    private static HttpPost packHttpPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            pairs.add(new BasicNameValuePair(key, params.get(key)));
        }
        logger.info("set charset utf-8 ");
        HttpEntity reqEntity = null;

        try {
            reqEntity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(reqEntity);
        return httpPost;
    }

    /**
     * 处理请求响应
     * 
     * @param client
     * @param httpPost
     * @return
     */
    private static String getResult(HttpClient client, HttpUriRequest httpRequest) {
        return doRequest(client, httpRequest, DEFAULT_ACCOUNT, DEFAULT_PASSWORD);
    }

    /**
     * 处理请求响应
     * 
     * @param client
     * @param httpPost
     * @return
     */
    private static String doRequest(HttpClient client, HttpUriRequest httpRequest, String account, String password) {
        HttpResponse response = null;
        try {
            HttpClientContext context = HttpClientContext.create();
        	if(account == null || password == null){
	            CredentialsProvider credsProvider = new BasicCredentialsProvider();
	            credsProvider.setCredentials(new AuthScope(null, AuthScope.ANY_PORT), new UsernamePasswordCredentials(account, password));
	            context.setCredentialsProvider(credsProvider);
        	}
            // ------------------------------------------------------------------------------------------------------------------------------------
            response = client.execute(httpRequest, context);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity=null;
        if(Utils.isObjectNotEmpty(response)){
            entity = response.getEntity();
        }
        String body = null;
        try {
            body = EntityUtils.toString(entity);
            body = new String(body.getBytes("ISO-8859-1"), "UTF-8");// 解决返回值中文乱码问题
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

}
