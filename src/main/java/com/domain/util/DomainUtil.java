package com.domain.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

public class DomainUtil {
	public static Map<String, String> userTokenMap = GlobalAttr.getInstance().getUserTokenMap();
	
	/**
	 * 获取授权字符串
	 * @param dateStr
	 * @return
	 */
	public static String getAuthStr(String dateStr) {
		String username = userTokenMap.get("username");
		String apiPassword = userTokenMap.get("apiPassword");
		String email = userTokenMap.get("email");
		String otime = dateStr;
		String checkNum = MD5.MD5(username + MD5.MD5(apiPassword).toLowerCase() + otime + email).toLowerCase();
		String authStr = "username="+username + "&otime=" + otime + "&checksum=" + checkNum; 
		return authStr; 
	}
	
	/**
	 * 获取授权字符串
	 * @param map
	 * @return
	 */
	public static Map<String, String> getAuthMap(String dateStr) {
		String username = userTokenMap.get("username");
		String apiPassword = userTokenMap.get("apiPassword");
		String email = userTokenMap.get("email");
		String otime = dateStr;
		String checkNum = MD5.MD5(username + MD5.MD5(apiPassword).toLowerCase() + otime + email).toLowerCase();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("username", username);
		map.put("otime", otime);
		map.put("checksum", checkNum);
		return map; 
	}
	
	/**
	 * 获取真实的IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String unknown = "unknown";
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		System.out.println("ip = " + ip);
		return ip;
	}
	
	/**
	 * 获取SSL token
	 * @return
	 */
	public static String getSSLToken(){
		return GlobalAttr.getInstance().getSslToken();
	}
	
	public static String getDate(int num){
		Calendar calendar1 = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date today1 = null;
		
		for (int i = 0; i < num; i++) {
			if (i != 0) {
				calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) + 1);
			}
			today1 = calendar1.getTime();
		}
		return format.format(today1);
	}
	
	/**
	 * 获取用户ID
	 * @param request
	 * @return
	 */
	public static String getCompanyId(HttpServletRequest request){
		return "150898735031";
	}
	
}