package com.domain.util;

import java.util.Map;

/**
 * 返回报文结构
 * @author YuLong.Dai
 * @time Sep 27, 2016 5:26:45 PM
 */
public class RspData {
	

	private String status;
	
	private String msg;
	
	private String message;
	
	private Map<String,Object> data;
  
	public String getStatus() {
		return status;
	}

	
 

	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public String getMsg() {
		return msg;
	}

	public RspData setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public RspData setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}




	public void setStatus(String status) {
		this.status = status;
	}
	
	 
}

