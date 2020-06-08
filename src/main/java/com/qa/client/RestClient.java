package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {

	
// 1.GET Method without Headers in request
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpClient = HttpClients.createDefault();  //one client connection, returns closeable httpclient 
	    HttpGet httpget = new HttpGet(url); // a connection is created; http get request
	    CloseableHttpResponse closeablehttpresponse = httpClient.execute(httpget);  //hit the GET URL, and get response
	    
	    return closeablehttpresponse;
	    
	}
	
	// 2.GET Method with Headers in request e.g. usr/passwd  ; overloading the same method
		public CloseableHttpResponse get(String url, HashMap<String, String> headerMap ) throws ClientProtocolException, IOException {
		    CloseableHttpClient httpClient = HttpClients.createDefault();  //one client connection, returns closeable httpclient 
		    HttpGet httpget = new HttpGet(url); // a connection is created; http get request
		    
		    for(Map.Entry<String, String> entry : headerMap.entrySet()) {
		    	httpget.addHeader(entry.getKey(), entry.getValue());
		    }
   		    CloseableHttpResponse closeablehttpresponse = httpClient.execute(httpget);  //hit the GET URL, and get response
		    return closeablehttpresponse;
	   }	
	
	// 3.POST Method
		public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		    CloseableHttpClient httpClient = HttpClients.createDefault();  //one client connection, returns closeable httpclient 
		    HttpPost httppost = new HttpPost(url);  //http POST request
		    httppost.setEntity(new StringEntity(entityString));  //define request payload
			
			//for headers
		    for(Map.Entry<String, String> entry : headerMap.entrySet()) {
		    	httppost.addHeader(entry.getKey(), entry.getValue());
		    }
			
		    CloseableHttpResponse closeablehttpresponse = httpClient.execute(httppost);
		    return closeablehttpresponse;
		}
		
		
		
	
}
