package com.domain.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.DomainSsl;
import com.domain.pojo.RspData;
import com.domain.service.DomainSslMaterialService;
import com.domain.service.DomainSslService;
import com.domain.util.ApiTool;
import com.domain.util.Config;
import com.domain.util.DomainUtil;
import com.domain.util.ExptNum;
import com.domain.util.GetResult;
import com.domain.util.HttpReq;
import com.domain.util.JSONUtils;
import com.domain.util.MappingConfigura;
import com.domain.util.ParamIsNull;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(MappingConfigura.SSL)
public class DomainSSLController {

	@Autowired
	private DomainSslService domainSslService;
	
	@Autowired
	private DomainSslMaterialService domainSslMaterialService;
	
	/**
	 * 创建SSL 证书
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createDomainSsl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createDomainSsl(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		//订单信息
		String sslName = maps.get("sslName");  //证书名称 --- 使用接口名称 DVKuaiSSL/OVZhenSSLPro
		String certencryptType = "SHA2"; // 证书加密算法
		String certExpTime = maps.get("certExpTime"); //证书使用年限以月为单位，最大支持 24 个月
		String certallDomain = maps.get("certallDomain"); //证书绑定的域名，系统默认第一个为证书绑定的主域，多 个域名，请使用英文“,”分隔
		String cerepayType = maps.get("cerepayType"); //是否收费，可不传  例如：收费：“ DVSSL” 
		//OVZhenSSLPro
		String certValidateType = maps.get("certValidateType"); //验证方式(电话验证：2 或小额验证：4)
		String freessl30 = maps.get("freessl30 "); //是否免费试用 30 天，1：是，0：否
		
		//申请人信息/被授权人信息 --
		String ownUserName = maps.get("ownUserName"); //申请人/被授权人姓名-代表单位申请证书的个人 
		String ownUserEmail = maps.get("ownUserEmail"); //申请人/被授权人邮箱
		String ownUserPhone = maps.get("ownUserPhone"); //申请人/被授权人电话；标准格式(国家码-区号-号码) 例如：+86-755-86008688 
		String ownUserIdCardNumber = maps.get("ownUserIdCardNumber"); //被授权人身份证号码
		
		//单位信息 
		String orgName = maps.get("orgName"); //证书使用者的单位名称
		String orgEmail = maps.get("orgEmail"); //证书使用者的单位邮箱
		String orgType = maps.get("orgType"); // 证书使用者的单位所属类型，默认为 企业：PrivateOrganization
		// 个体：BusinessEntity , 企业：PrivateOrganization , 政府事业单位：GovernmentEntity ,非商业机构/协会：Non-CommercialEntity 
		String orgPhone = maps.get("orgPhone"); //单 位 电话 ； 标准 格式 ( 国家 码 - 区号- 号码) 例如：+86-755-86008688 
		
		String sslToken = DomainUtil.getSSLToken();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sendPost = "";
		
		DomainSsl ssl = new DomainSsl();
		ssl.setStatus(2);
		ssl.setCreatetime(sdf.parse(sdf.format(new Date())));
		ssl.setEndtime(sdf.parse(DomainUtil.getDate(Integer.parseInt(certExpTime))));
		ssl.setCertalldomain(certallDomain);
		ssl.setCertencrypttype(certencryptType);
		ssl.setOwnuserName(ownUserName);
		ssl.setOwnuserEmail(ownUserEmail);
		ssl.setOwnuserPhone(ownUserPhone);
		
		RspData rd = new RspData();
		// 超快SSL --- DVKuaiSSL
		if(sslName.equals("DVKuaiSSL")){
			if (!ParamIsNull.isNull(certencryptType, certExpTime, certallDomain, cerepayType, ownUserName, ownUserEmail, ownUserPhone)) {
				rd.setStatus(GetResult.ERROR_STATUS + "");
				rd.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rd);
			}
			
			sendPost = HttpReq.sendPost("https://api.wosign.com", 
					"actionType=MakeOrder&certName=DVKuaiSSL&connect_token=" + sslToken + "&certencryptType=" + certencryptType + 
					"&certExpTime=" + certExpTime + "&certallDomain =" + certallDomain + 
					"&cerepayType=" + cerepayType + "&ownUser_name=" + ownUserName + 
					"&ownUser_email=" + ownUserEmail + "&ownUser_phone=" + ownUserPhone);
			
			
			String[]  str = sendPost.split(";");
			String data = null;
			if(str[0].equals("[1]")){
				data = str[2].split("=")[2];
				data = data.substring(0, data.length() - 2);
				
				System.out.println("orderCode ---- > " + data);
				ssl.setType(1);
				ssl.setCerepayType(cerepayType);
				domainSslService.insert(ssl);
				
				domainSslService.updateByParam("orderCode='" + data + "' where id=" + ssl.getId());
				
				rd.setStatus(ExptNum.SUCCESS.getCode() + "");
				rd.setMsg(ExptNum.SUCCESS.getDesc());
				return  JSONUtils.createObjectJson(rd);
			}else{
				data = str[2].split("=")[3];
				data = data.substring(0, data.length() - 1);
				
				System.out.println("errorMsg ---- > " + data);
				
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(data);
				return  JSONUtils.createObjectJson(rd);
			}
			
		//超真 SSL Pro --- OVZhenSSLPro
		}else{
			if (!ParamIsNull.isNull(certExpTime, certallDomain, cerepayType, ownUserName, ownUserEmail, ownUserPhone, 
					certValidateType, ownUserIdCardNumber, orgName, orgEmail, orgType, orgPhone)) {
				rd.setStatus(GetResult.ERROR_STATUS + "");
				rd.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rd);
			}
			
			sendPost = HttpReq.sendPost("https://api.wosign.com", 
					"actionType=MakeOrder&certName=DVKuaiSSL&connect_token=" + sslToken + "&certencryptType=" + certencryptType + 
					"&certExpTime=" + certExpTime + "&freessl30=" + freessl30 + "&certallDomain =" + certallDomain + 
					"&certValidateType=" + certValidateType + "&org_name=" + orgName + "&org_email=" + orgEmail +
					"&org_Type=" + orgType + "&org_phone=" + orgPhone + "&ownUser_name=" + ownUserName + 
					"&ownUser_email=" + ownUserEmail + "&ownUser_phone=" + ownUserPhone + 
					"&ownUser_idCardNumber=" + ownUserIdCardNumber);
			
			String[]  str = sendPost.split(";");
			String data = null;
			if(str[0].equals("[1]")){
				data = str[2].split("=")[2];
				data = data.substring(0, data.length() - 2);
				
				System.out.println("orderCode ---- > " + data);
				ssl.setType(2);
				ssl.setOwnuserIdcardnumber(ownUserIdCardNumber);
				ssl.setCertvalidatetype(Integer.parseInt(certValidateType));
				ssl.setFreessl30(Integer.parseInt(freessl30));
				ssl.setOrgEmail(orgEmail);
				ssl.setOrgName(orgName);
				ssl.setOrgPhone(orgPhone);
				ssl.setOrgType(orgType);
				
				domainSslService.insert(ssl);
				
				domainSslService.updateByParam("orderCode='" + data + "' where id=" + ssl.getId());
				
				rd.setStatus(ExptNum.SUCCESS.getCode() + "");
				rd.setMsg(ExptNum.SUCCESS.getDesc());
				return  JSONUtils.createObjectJson(rd);
			}else{
				data = str[2].split("=")[3];
				data = data.substring(0, data.length() - 1);
				
				System.out.println("errorMsg ---- > " + data);
				
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(data);
				return  JSONUtils.createObjectJson(rd);
			}
			
		}
		
	}
	
	/**
	 * 取消SSL 证书订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cancelDomainSsl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String cancelDomainSsl(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String orderCode = maps.get("orderCode");
		String sslToken = DomainUtil.getSSLToken();
		
		RspData rd = new RspData();
		
		if (!ParamIsNull.isNull(orderCode)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=CancelOrder&connect_token=" + sslToken + 
				"&OrderID=" + orderCode);
		
		String[]  str = sendPost.split(";");
		String data = null;
		if(str[0].equals("[1]")){
			domainSslService.updateByParam("status=3 where orderCode='" + orderCode + "'");
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}else{
			data = str[2].split("=")[3];
			data = data.substring(0, data.length() - 1);
			
			System.out.println("errorMsg ---- > " + data);
			
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(data);
			return  JSONUtils.createObjectJson(rd);
		}
		
	}
	
	/**
	 * 吊销SSL 证书订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "revokeDomainSsl", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String revokeDomainSsl(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String orderCode = maps.get("orderCode");
		String reason = maps.get("reason");
		String sslToken = DomainUtil.getSSLToken();
		
		RspData rd = new RspData();
		
		if (!ParamIsNull.isNull(orderCode)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=ReStartOrder&connect_token=" + sslToken + 
				"&OrderID=" + orderCode + "&reason=" + reason);
		
		String[]  str = sendPost.split(";");
		String data = null;
		if(str[0].equals("[1]")){
			domainSslService.updateByParam("status=4 where orderCode='" + orderCode + "'");
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}else{
			data = str[2].split("=")[3];
			data = data.substring(0, data.length() - 1);
			
			System.out.println("errorMsg ---- > " + data);
			
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(data);
			return  JSONUtils.createObjectJson(rd);
		}
	}
	
	/**
	 * 查询订单状态  -- 后转 定时任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQueryOrderStatusCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getQueryOrderStatusCode(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String orderCode = maps.get("orderCode");
		String sslToken = DomainUtil.getSSLToken();
		
		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=QueryOrderStatusCode&connect_token=" + sslToken + 
				"&OrderID=" + orderCode);
		
		System.out.println(sendPost);
		
		RspData rd = new RspData();
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 查询订单所有域名的根域名以及验证状态  -- 后转 定时任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQueryDomainValStatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getQueryDomainValStatus(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String orderCode = maps.get("orderCode");
		String sslToken = DomainUtil.getSSLToken();
		
		String sendPost = HttpReq.sendPost("https://api.wosign.com", "actionType=QueryDomainValStatus&connect_token=" + sslToken + 
				"&OrderID=" + orderCode);
		
		System.out.println(sendPost);
		
		RspData rd = new RspData();
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
		
	}
}
