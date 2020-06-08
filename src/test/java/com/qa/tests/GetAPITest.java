package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{
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
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeablehttpresponse = restClient.get(url);  //returns response object, which in turn will have its content tested as follows:
		
      // Status Code
	    int statusCode = closeablehttpresponse.getStatusLine().getStatusCode();
	    System.out.println("Status Code--->"+statusCode);
	
	    Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
	    
	    
	    //Json String    - response as a string first then get it into json format
	    String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");  //entire response into string with headers, 
	    JSONObject responseJson = new JSONObject(responseString);
	    System.out.println("response JSON from API--->"+responseJson);
	    
	    //JSON object: single value attributes
	    //Json object: per_page 
	    String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
	    System.out.println("Per_page is --->"+perPageValue);
	    Assert.assertEquals(perPageValue, "6");  //6 is from response json page, hardcoded!!!
	    //or
	    Assert.assertEquals(Integer.parseInt(perPageValue), 6);
	    
	    //Json object: total
	    String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
	    System.out.println("total is --->"+totalValue);
	    Assert.assertEquals(totalValue, "12");  //12 is from response json page, hardcoded!!!
	    //or
	    Assert.assertEquals(Integer.parseInt(totalValue), 12);
	    
	    //JSON Array attributes data[0,1...n]
	    String lastName = TestUtil.getValueByJPath(responseJson, "/data[1]/last_name");
	    String id = TestUtil.getValueByJPath(responseJson, "/data[1]/id");
	    String avatar = TestUtil.getValueByJPath(responseJson, "/data[1]/avatar");
	    String firstName = TestUtil.getValueByJPath(responseJson, "/data[1]/first_name");
	    System.out.println("last_name is --->"+lastName);
	    System.out.println("id is --->"+id+"; avatar is --->"+avatar+"; first_name is --->"+ firstName);
	    Assert.assertEquals(lastName,"Weaver" );  //hardcoded from Json response page
	    Assert.assertEquals(id, "2");
	    Assert.assertEquals(avatar,"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg");
	    Assert.assertEquals(firstName, "Janet");
	    
	    
	    //All headers
	    Header[] headerArray = closeablehttpresponse.getAllHeaders(); //hash map: key value
	    HashMap<String, String> allHeaders = new HashMap<String,String>();
	    for(Header header : headerArray) {
	    	allHeaders.put(header.getName(), header.getValue());
	    }
	    System.out.println("Headers Array--->"+allHeaders);
		System.out.println("Server is --->"+allHeaders.get("Server"));  // a value for a KEY=Server in one of the headers
	}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		//must first create hashMap for request headers
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json"); // the same for other hash maps. Content-Type
		
		closeablehttpresponse = restClient.get(url, headerMap );  //passing url and request headers.  It returns response object with headers, which in turn will have its content tested as follows:
		
      // Status Code
	    int statusCode = closeablehttpresponse.getStatusLine().getStatusCode();
	    System.out.println("Status Code--->"+statusCode);
	
	    Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
	    
	    
	    //Json String    - response as a string first then get it into json format
	    String responseString = EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");  //entire response into string with headers, 
	    JSONObject responseJson = new JSONObject(responseString);
	    System.out.println("response JSON from API--->"+responseJson);
	    
	    //JSON object: single value attributes
	    //Json object: per_page 
	    String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
	    System.out.println("Per_page is --->"+perPageValue);
	    Assert.assertEquals(perPageValue, "6");  //6 is from response json page, hardcoded!!!
	    //or
	    Assert.assertEquals(Integer.parseInt(perPageValue), 6);
	    
	    //Json object: total
	    String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
	    System.out.println("total is --->"+totalValue);
	    Assert.assertEquals(totalValue, "12");  //12 is from response json page, hardcoded!!!
	    //or
	    Assert.assertEquals(Integer.parseInt(totalValue), 12);
	    
	    //JSON Array attributes data[0,1...n]
	    String lastName = TestUtil.getValueByJPath(responseJson, "/data[2]/last_name");
	    String id = TestUtil.getValueByJPath(responseJson, "/data[2]/id");
	    String avatar = TestUtil.getValueByJPath(responseJson, "/data[2]/avatar");
	    String firstName = TestUtil.getValueByJPath(responseJson, "/data[2]/first_name");
	    System.out.println("last_name is --->"+lastName);
	    System.out.println("id is --->"+id+"; avatar is --->"+avatar+"; first_name is --->"+ firstName);
	    Assert.assertEquals(lastName,"Wong" );  //hardcoded from Json response page
	    Assert.assertEquals(id, "3");
	    Assert.assertEquals(avatar,"https://s3.amazonaws.com/uifaces/faces/twitter/olegpogodaev/128.jpg");
	    Assert.assertEquals(firstName, "Emma");
	    
	    
	    //All headers
	    Header[] headerArray = closeablehttpresponse.getAllHeaders(); //hash map: key value
	    HashMap<String, String> allHeaders = new HashMap<String,String>();
	    for(Header header : headerArray) {
	    	allHeaders.put(header.getName(), header.getValue());
	    }
	    System.out.println("Headers Array--->"+allHeaders);
		System.out.println("Date is --->"+allHeaders.get("Date"));  // a value for a KEY=Date in one of the headers
	}
}
