package com.qa.util;

import org.json.JSONObject;
import org.json.JSONArray;


//to parse the return json string, then to test for parsed key:value pairs
public class TestUtil {

	public static JSONObject responsejson;
	
	public static String getValueByJPath(JSONObject responsejson, String jpath) {
		Object obj = responsejson;
		for(String s: jpath.split("/"))
			if(!s.isEmpty())
				if(!(s.contains("[") || s.contains("]")))  //this is for single value attribute e.g. per_page
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))  //this is for an array attribute e.g. data. Its array composite attributes are within [ and ]
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]","")));
			return obj.toString();
	}
	
	
}
