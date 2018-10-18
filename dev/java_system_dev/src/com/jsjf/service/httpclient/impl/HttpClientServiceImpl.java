package com.jsjf.service.httpclient.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jsjf.service.httpclient.HttpClientService;

@Service
public class HttpClientServiceImpl implements HttpClientService {
	private static Logger log = Logger.getLogger(HttpClientServiceImpl.class);

	/**
     * 
     * 功能描述：发送序列化对象
     * @param url
     * @param inputObj
     * @return
     */
	@Override
    public Object postSendHttp(String url, Object inputObj)
    {
        long start = System.currentTimeMillis();
        if (url == null || "".equals(url))
        {
        	log.info("request url is empty.");
            return null;
        }
//        HttpClient httpClient = CustomHttpClient.GetHttpClient();
        HttpClient httpClient = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/octet-stream");
        java.io.ByteArrayOutputStream bOut = new java.io.ByteArrayOutputStream(
                1024);
        java.io.InputStream bInput = null;
        java.io.ObjectOutputStream out = null;
        Serializable returnObj = null;
        try
        {
            out = new java.io.ObjectOutputStream(bOut);
            out.writeObject(inputObj);
            out.flush();
            out.close();
            out = null;
            bInput = new java.io.ByteArrayInputStream(bOut.toByteArray());
            InputStreamEntity inputStreamEntity = new InputStreamEntity(bInput,
                    bOut.size(), null);
            inputStreamEntity.setContentEncoding(new BasicHeader(
                    HTTP.CONTENT_ENCODING, "UTF-8"));
            // 设置请求主体
            post.setEntity(inputStreamEntity);
            // 发起交易
            HttpResponse resp = httpClient.execute(post);
            //log.info("请求[" + url + "] " + resp.getStatusLine());
            int ret = resp.getStatusLine().getStatusCode();
            if (ret == HttpStatus.SC_OK)
            {
                // // // 响应分析
                // HttpEntity entity = resp.getEntity();
                // returnObj = (Serializable) SerializationUtils.deserialize(entity.getContent());
                // return returnObj;

                // 响应分析
                HttpEntity entity = resp.getEntity();

                java.io.InputStream in = entity.getContent();
                java.io.ObjectInputStream oInput = new java.io.ObjectInputStream(
                        in);
                returnObj = (Serializable) oInput.readObject();
                oInput.close();
                oInput = null;
                long end = System.currentTimeMillis();
//                log.info("请求[" + url + "]消耗时间 " + (end - start)
//                        + "毫秒");
                return returnObj;
            }
            return null;
        } catch (ConnectTimeoutException cte)
        {
        	log.info(cte.getMessage());
            return null;
        } catch (SocketTimeoutException cte)
        {
        	log.info(cte.getMessage());
            return null;
        } catch (Exception e)
        {
        	log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public String postSendHttp(String url, String body)
    {
        long start = System.currentTimeMillis();
        if (url == null || "".equals(url))
        {
        	log.info("request url is empty.");
            return null;
        }
//        HttpClient httpClient = CustomHttpClient.GetHttpClient();
        HttpClient httpClient = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "text/html;charset=UTF-8");
        try
        {
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setContentEncoding(new BasicHeader(
                    HTTP.CONTENT_ENCODING, "UTF-8"));
            // 设置请求主体
            post.setEntity(stringEntity);
            // 发起交易
            HttpResponse resp = httpClient.execute(post);
            int ret = resp.getStatusLine().getStatusCode();
            if (ret == HttpStatus.SC_OK)
            {
                // 响应分析
                HttpEntity entity = resp.getEntity();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        entity.getContent(), "UTF-8"));
                StringBuffer responseString = new StringBuffer();
                String result = br.readLine();
                while (result != null)
                {
                    responseString.append(result);
                    result = br.readLine();
                }
                long end = System.currentTimeMillis();
//                log.info("请求[" + url + "]消耗时间 " + (end - start)
//                        + "毫秒");
                return responseString.toString();
            }
            return null;
        } catch (ConnectTimeoutException cte)
        {
        	log.info(cte.getMessage());
            return null;
        } catch (SocketTimeoutException cte)
        {
        	log.info(cte.getMessage());
            return null;
        } catch (Exception e)
        {
        	log.info(e.getMessage());
            return null;
        }
    }

}
