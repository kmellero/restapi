package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class PostAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeablehttpresponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");  //from config.properties via TestBase class extension
		apiURL = prop.getProperty("serviceURL");
		// concatenate two strings into http://reqres.in/api/users
		url = serviceUrl + apiURL;
		
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json"); // the same for other hash maps. Content-Type

		//jackson API to convert java Users into json called marshalling or vice versa
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus","leader");  //expected users object
		
		//java object to json file
		mapper.writeValue(new File("C:\\Users\\kajetanmellerowicz\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\user.json"), users);
		
		//object to json in String
		String userJsonString = mapper.writeValueAsString(users);
		System.out.println(userJsonString);
		
		//call the API via POST request
		closeablehttpresponse = restClient.post(url,userJsonString, headerMap);  
		
		//VALIDATE the response from API
		//1. status code
		int statusCode = closeablehttpresponse.getStatusLine().getStatusCode();
	    System.out.println("Status Code--->"+statusCode);
		
	    Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status code is not 201");
		
		//2. JsonString correct or not? 
	    String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");  //entire response into string with headers, 
	    System.out.println("responseString--->"+responseString);  //to check the responseString first
	    
	    JSONObject responseJson = new JSONObject(responseString);  //to check the json string.  In this case, responseJson=responseString in different order
	    System.out.println("response JSON from API--->"+responseJson);
	    
	    //json to java object (?unmarshalling)  -actual!  see above for expected!
	    Users userRespObj = mapper.readValue(responseString, Users.class);  //actual users object
	    System.out.println("userRespObj--->"+userRespObj);
	    
		//Assertions, two objects Users (users->API request; and userRespObj from<-API with copies of Name and Job; comparing them below in sysout /or Assertions 
//	    System.out.println(users.getName().equals(userRespObj.getName()));
	    Assert.assertTrue(users.getName().equals(userRespObj.getName()));
//		System.out.println(users.getJob().equals(userRespObj.getJob()));
	    Assert.assertTrue(users.getJob().equals(userRespObj.getJob()));
	    
	    System.out.println(userRespObj.getCreatedAt());
	    System.out.println(userRespObj.getId());
	    
	    
	}
	
	
	
}
