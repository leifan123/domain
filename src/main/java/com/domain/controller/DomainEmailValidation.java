package com.domain.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.RspData;
import com.domain.util.ApiTool;
import com.domain.util.DomainUtil;
import com.domain.util.ExptNum;
import com.domain.util.HttpReq;
import com.domain.util.JSONUtils;
import com.domain.util.MappingConfigura;

@Controller
@RequestMapping(MappingConfigura.VALIDATION)
public class DomainEmailValidation {
	
	/**
	 * 请求邮箱验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ReqEmailVCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ReqEmailVCode(HttpServletRequest request) throws Exception{	
		Map<String, String> maps = ApiTool.getBodyString(request);	
		String orderCode = maps.get("orderCode");
		String sslToken = DomainUtil.getSSLToken();
		
		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=ReqEmailVCode&connect_token=" + sslToken + 
				"&OrderID=" + orderCode);
		
		System.out.println(sendPost);
		
		RspData rd = new RspData();
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(sendPost);
		return  JSONUtils.createObjectJson(rd);
		
	}
	/**
	 * 验证邮箱验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ValEmailVCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ValEmailVCode(HttpServletRequest request) throws Exception{	
		Map<String, String> maps = ApiTool.getBodyString(request);	
		String orderCode = maps.get("orderCode");
		String validateCode = maps.get("validateCode");
		String sslToken = DomainUtil.getSSLToken();

		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=ReqEmailVCode&connect_token=" + sslToken + 
				"&OrderID=" + orderCode+"&validateCode="+validateCode);
		System.out.println(sendPost);
		RspData rd = new RspData();
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(sendPost);
		return  JSONUtils.createObjectJson(rd);
		
	}
	
	/**
	 * 域名网站验证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ValidateWebSiteHTML", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ValidateWebSiteHTML(HttpServletRequest request) throws Exception{	
		Map<String, String> maps = ApiTool.getBodyString(request);	
		String orderCode = maps.get("orderCode");
		String topdomain = maps.get("topdomain");
		String sslToken = DomainUtil.getSSLToken();

		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=ReqEmailVCode&connect_token=" + sslToken + 
				"&OrderID=" + orderCode+"&topdomain="+topdomain);
		System.out.println(sendPost);
		RspData rd = new RspData();
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(sendPost);
		return  JSONUtils.createObjectJson(rd);
		
	}

}
