package com.domain.util;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author daire 2015-7-27
 */
public class GlobalAttr {

	private Logger log = Logger.getLogger(GlobalAttr.class);

	private static GlobalAttr globalAttr;
  
	private static Map<String, String> userTokenMap;
	
	private static String uploadImage;
	
	private static String sslToken;
  
	public static GlobalAttr getInstance() {
		if (globalAttr == null) {
			globalAttr = new GlobalAttr();
		}
		return globalAttr;
	}

	private GlobalAttr() {
		
		userTokenMap = new HashMap<String, String>();
		uploadImage = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.res.path");
		
		sslToken = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "ssl.token");
	}
	public Map<String, String> getUserTokenMap() {
		return userTokenMap;
	}
	
	public static String getSslToken() {
		return sslToken;
	}


	public static String getUploadImage() {
		return uploadImage;
	}

}
