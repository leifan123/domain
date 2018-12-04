package com.domain.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.domain.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.domain.pojo.DomainAuthentication;
import com.domain.pojo.DomainEmail;
import com.domain.pojo.DomainTemplate;
import com.domain.pojo.RspData;
import com.domain.pojo.YrComper;
import com.domain.service.DomainAuthenticationService;
import com.domain.service.DomainEmailService;
import com.domain.service.DomainTemplateService;
import com.domain.service.YrComperService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(MappingConfigura.TEMPLATE)
public class TemplateController {

	private Logger log = Logger.getLogger(TemplateController.class);

	@Autowired
	private DomainTemplateService domainTemplateService;

	@Autowired
	private DomainEmailService domainEmailService;

	@Autowired
	private DomainAuthenticationService domainAuthenticationService;

	@Autowired
	private YrComperService yrComperService;

	private static String domainPath= Prop.getInstance().getPropertiesByPro("cloudStack.properties", "domain.path");

	/**
	 * 添加模板（联系人）
	 *
//	 * @param username
//	 *            美橙用户名
//	 * @param otime
//	 * @param checksum
//	 *            美橙密码
//	 * @param companyEn
//	 *            域名所有者（英文）
//	 * @param lastnameEn
//	 *            姓（英文）
//	 * @param firstnameEn
//	 *            名（英文）
//	 * @param countryEn
//	 *            国家代码（英文）
//	 * @param stateEn
//	 *            省份（英文）
//	 * @param cityEn
//	 *            城市（英文）
//	 * @param addressEn
//	 *            地址（英文）
//	 * @param companyCn
//	 *            域名所有者（中文，非国内域名可以输入英文）
//	 * @param lastnameCn
//	 *            姓（中文，非国内域名可以输入英文）
//	 * @param firstnameCn
//	 *            名（中文，非国内域名可以输入英文）
//	 * @param countryCn
//	 *            国家代码（中文，非国内域名可以输入英文）
//	 * @param stateCn
//	 *            省份（中文，非国内域名可以输入英文）
//	 * @param cityCn
//	 *            城市（中文，非国内域名可以输入英文）
//	 * @param addressCn
//	 *            联系地址（中文，非国内域名可以输入英文）
//	 * @param zipcode
//	 *            邮编
//	 * @param phone
//	 *            电话
//	 * @param fax
//	 *            传真
//	 * @param email
//	 *            电子邮件
//	 * @param usertype
//	 *            针对注册域名类型（O — 企业； I –– 个人）
//	 * @param idtype
//	 *            证件类型
//	 * @param idnumber
//	 *            证件号码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createTemple", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createTemple(HttpServletRequest request) throws Exception {

		Map<String, Object> maps = ApiTool.getBodyObject(request);

		String companyEn = (String) maps.get("companyEn"); // 域名持有者 英文
		String companyCn = (String) maps.get("companyCn"); // 域名持有者 中文
		String extensionNumber = (String) maps.get("extensionNumber");
		String extensionFax = (String) maps.get("extensionFax");

		String[] companyEns = companyEn.split(" ");

		String lastnameCn = "";
		String firstnameCn = "";
		String lastnameEn = "";
		String firstnameEn = "";
		if (companyCn.length() < 4) {
			lastnameCn = companyCn.substring(0, 1);
			firstnameCn = companyCn.substring(1);
			lastnameEn = companyEns[0];
			for (int i = 1; i < companyEns.length; i++) {
				firstnameEn += companyEns[i];
			}
		} else {
			lastnameCn = companyCn.substring(0, 2);
			firstnameCn = companyCn.substring(2);
			lastnameEn = companyEns[0] + companyEns[1];
			for (int i = 1; i < companyEns.length; i++) {
				firstnameEn += companyEns[i];
			}
		}
		String countryEn = (String) maps.get("countryEn");
		String stateEn = (String) maps.get("stateEn");
		String cityEn = (String) maps.get("cityEn");
		String addressEn = (String) maps.get("addressEn");

		String countryCn = (String) maps.get("countryCn");
		String stateCn = (String) maps.get("stateCn");
		String cityCn = (String) maps.get("cityCn");
		String addressCn = (String) maps.get("addressCn");
		String zipcode = (String) maps.get("zipcode");
		String phone = (String) maps.get("phone");
		String fax = (String) maps.get("fax");
		String email = (String) maps.get("email");
		String usertype = (String) maps.get("usertype");
		// String idtype = maps.get("idtype");
		// String idnumber = maps.get("idnumber");
		String companyId = DomainUtil.getCompanyId(request);
		String dateStr = TimeUtil.getTime();

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyEn, countryEn, stateEn, cityEn, addressEn, companyCn, countryCn, stateCn, cityCn,
				addressCn, zipcode, phone, fax, email, usertype, companyId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> param = DomainUtil.getAuthMap(dateStr);
		param.put("company-en", companyEn);
		param.put("lastname-en", lastnameEn.trim());
		param.put("firstname-en", firstnameEn);
		param.put("country-en", countryEn);
		param.put("state-en", stateEn);
		param.put("city-en", cityEn);
		param.put("address-en", addressEn);
		param.put("company-cn", companyCn);
		param.put("lastname-cn", lastnameCn);
		param.put("firstname-cn", firstnameCn);

		param.put("country-cn", countryCn);
		param.put("state-cn", stateCn);
		param.put("city-cn", cityCn);
		param.put("address-cn", addressCn);
		param.put("zipcode", zipcode);
		param.put("phone", phone);
		param.put("fax", fax);
		param.put("email", email);
		param.put("usertype", usertype);
		// param.put("idtype", idtype);
		// param.put("idnumber", idnumber);

		String sendGet = HttpSendUtil.doGet("http://api.cndns.com/domains/CreateContact.aspx", param);

		JSONObject jsonObject = JSONObject.fromObject(sendGet);

		if (jsonObject.get("status").equals("failed")) {
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg((String) jsonObject.get("message"));
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/template/createTemple.do", //操作链接
						"添加模版", //操作类型
						companyId, //操作者companyid
						Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
						(String) jsonObject.get("message"), //操作详细描述
						rd.getMsg());//
			} catch (Exception e) {
				log.error("添加模版添加日志出错");
			}
			return JSONUtils.createObjectJson(rd);
		}

		String userId = (String) jsonObject.get("message");

		DomainTemplate template = new DomainTemplate();
		template.setCompanyid(companyId);
		template.setUserid(userId);
		template.setCompanyCn(companyCn);
		template.setCompanyEn(companyEn);
		template.setLastnameCn(lastnameCn);
		template.setFirstnameCn(firstnameCn);
		template.setExtensionFax(extensionFax);
		template.setCountryCn(countryCn);
		template.setStateCn(stateCn);
		template.setCityCn(cityCn);
		template.setAddressCn(addressCn);
		template.setLastnameEn(lastnameEn);
		template.setFirstnameEn(firstnameEn);
		template.setCityEn(cityEn);
		template.setCountyEn(countryEn);
		template.setStateEn(stateEn);
		template.setCityEn(cityEn);
		template.setAddressEn(addressEn);
		template.setZipcode(zipcode);
		template.setPhone(phone);
		template.setFax(fax);
		template.setEmail(email);
		template.setExtensionNumber(extensionNumber);
		if (usertype.equals("I")) {
			template.setUsertype(1);
		} else {
			template.setUsertype(0);
		}
		// template.setIdtype(idtype);
		// template.setIdnumber(idnumber);
		template.setIschecked(2); // 1为默认 2为不是
		template.setIsdefault(0);
		template.setIsforbidden(1);
		Date date = new Date();
		template.setRegtime(df.parse(df.format(date)));
		template.setCreattime(df.parse(df.format(date)));
		template.setTemplateType(1);

		domainTemplateService.insert(template);

		template.setTemplateType(2);

		domainTemplateService.insert(template);

		template.setTemplateType(3);

		domainTemplateService.insert(template);

		template.setTemplateType(4);

		domainTemplateService.insert(template);

		List<DomainEmail> domainEmailList = domainEmailService.select(new DomainEmail().setEmail(email));
		if (domainEmailList.size() == 0) {
			DomainEmail domainEmail = new DomainEmail();
			domainEmail.setEmail(email);
			domainEmail.setStatus(2); // 2 未认证 1 已认证 3 认证中
			domainEmail.setCreatetime(df.parse(df.format(date)));

			domainEmailService.insert(domainEmail);
		}

		DomainAuthentication domainAuthentication = new DomainAuthentication();
		domainAuthentication.setCompanyId(companyId);
		domainAuthentication.setUsername(companyCn);

		List<DomainAuthentication> domainAuthenticationList = domainAuthenticationService.select(domainAuthentication);
		if (domainAuthenticationList.size() == 0) {
			DomainAuthentication authentication = new DomainAuthentication();
			authentication.setCompanyId(companyId);
			authentication.setUsername(companyCn);
			authentication.setUserid(Integer.parseInt(userId));
			// authentication.setAuthenicationtype(idtype);
			if (usertype.equals("I")) {
				authentication.setCardtype(1);
			} else {
				authentication.setCardtype(0);
			}

			// authentication.setCardnum(Long.parseLong(idnumber));
			authentication.setStatus(2); // 2 未认证 1 已认证 3 认证中
			authentication.setCreatetime(df.parse(df.format(date)));

			domainAuthenticationService.insert(authentication);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/template/createTemple.do", //操作链接
					"添加模版", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("添加模版添加日志出错");
		}
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 修改模板（联系人）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateTemple", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateTemple(HttpServletRequest request) throws Exception {
		Map<String, String> maps = ApiTool.getBodyString(request);

		String companyEn = maps.get("companyEn"); // 域名持有者 英文
		String companyCn = maps.get("companyCn"); // 域名持有者 中文
		String extensionNumber = maps.get("extensionNumber");
		String extensionFax = maps.get("extensionFax");

		String lastnameCn = "";
		String firstnameCn = "";
		String lastnameEn = "";
		String firstnameEn = "";

		String[] companyEns = companyEn.split(" ");
		if (companyEn.contains(" ")) {
			if (companyEns.length < 4) {
				firstnameEn = companyEns[0];
				for (int i = 0; i < companyEns.length; i++) {
					if (i != 0) {
						lastnameEn += companyEns[i];
					}
				}
			} else {
				firstnameEn = companyEns[0] + companyEns[1];
				for (int i = 0; i < companyEns.length; i++) {
					if (i != 0 && i != 1) {
						lastnameEn += companyEns[i];
					}
				}
			}
		} else {
			lastnameEn = companyEn;
			firstnameEn = companyEn;
		}

		if (companyCn.length() < 4) {
			lastnameCn = companyCn.substring(0, 1);
			firstnameCn = companyCn.substring(1);
		} else {
			lastnameCn = companyCn.substring(0, 2);
			firstnameCn = companyCn.substring(2);
		}
		String countryEn = maps.get("countryEn");
		String stateEn = maps.get("stateEn");
		String cityEn = maps.get("cityEn");
		String addressEn = maps.get("addressEn");
		String countryCn = maps.get("countryCn");
		String stateCn = maps.get("stateCn");
		String cityCn = maps.get("cityCn");
		String addressCn = maps.get("addressCn");
		String zipcode = maps.get("zipcode");
		String phone = maps.get("phone");
		String fax = maps.get("fax");
		String email = maps.get("email");
		String usertype = maps.get("usertype");
		String templateType = maps.get("templateType");
		String userid = maps.get("userid");
		String companyId = DomainUtil.getCompanyId(request);
		String dateStr = TimeUtil.getTime();

		RspData rd = new RspData();

		// 参数完整性判断
		if (!ParamIsNull.isNull(companyEn, lastnameEn, firstnameEn, countryEn, stateEn, cityEn, addressEn, companyCn,
				lastnameCn, firstnameCn, countryCn, stateCn, cityCn, addressCn, zipcode, phone, fax, email, usertype,
				templateType, userid)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		List<DomainTemplate> afterTemplate = domainTemplateService.selectByParam(null,
				"userid='" + userid + "' and templateType=1");
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("1".equals(templateType)) {
			Map<String, String> param = DomainUtil.getAuthMap(dateStr);
			param.put("company-en", companyEn);
			param.put("lastname-en", lastnameEn);
			param.put("firstname-en", firstnameEn);
			param.put("country-en", countryEn);
			param.put("state-en", stateEn);
			param.put("city-en", cityEn);
			param.put("address-en", addressEn);
			param.put("company-cn", companyCn);
			param.put("lastname-cn", lastnameCn);
			param.put("firstname-cn", firstnameCn);
			param.put("country-cn", countryCn);
			param.put("state-cn", stateCn);
			param.put("city-cn", cityCn);
			param.put("address-cn", addressCn);
			param.put("zipcode", zipcode);
			param.put("phone", phone);
			param.put("fax", fax);
			param.put("email", email);
			param.put("usertype", usertype);
			param.put("userid", userid);

			String sendGet = HttpSendUtil.doGet("http://api.cndns.com/domains/ModifyContact.aspx?", param);

			JSONObject jsonObject = JSONObject.fromObject(sendGet);

			if (jsonObject.get("status").equals("failed")) {
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg((String) jsonObject.get("message"));
				return JSONUtils.createObjectJson(rd);
			}

			String message = (String) jsonObject.get("message");
			log.debug("message --- > " + message);

			DomainAuthentication domainAuthentication = new DomainAuthentication();
			domainAuthentication.setCompanyId(companyId);
			domainAuthentication.setUserid(Integer.parseInt(userid));
			if (usertype.equals("I")) {
				domainAuthentication.setCardtype(1);
			} else {
				domainAuthentication.setCardtype(0);
			}

			domainAuthentication.setUsername(companyCn);

			List<DomainAuthentication> domainAuthenticationList = domainAuthenticationService
					.select(domainAuthentication);
			if (domainAuthenticationList.size() == 0) {
				DomainAuthentication authentication = new DomainAuthentication();
				authentication.setCompanyId(companyId);
				authentication.setUsername(companyCn);
				authentication.setUserid(Integer.parseInt(userid));
				// authentication.setAuthenicationtype(idtype);
				if (usertype.equals("I")) {
					authentication.setCardtype(1);
				} else {
					authentication.setCardtype(0);
				}

				// authentication.setCardnum(Long.parseLong(idnumber));
				authentication.setStatus(2); // 2 未认证 1 已认证 3 认证中
				authentication.setCreatetime(df.parse(df.format(date)));

				domainAuthenticationService.insert(authentication);
			} else {
				domainAuthenticationService.updateByParam("userName='" + companyCn + "' where companyId='" + companyId
						+ "' and userName='" + afterTemplate.get(0).getCompanyCn() + "'");
			}

			List<DomainEmail> domainEmailList = domainEmailService.select(new DomainEmail().setEmail(email));
			if (domainEmailList.size() != 0) {
				domainEmailService
						.updateByParam("email='" + email + "' where email='" + afterTemplate.get(0).getEmail() + "'");
			} else {
				DomainEmail domainEmail = new DomainEmail();
				domainEmail.setEmail(email);
				domainEmail.setStatus(2); // 2 未认证 1 已认证 3 认证中
				domainEmail.setCreatetime(df.parse(df.format(date)));

				domainEmailService.insert(domainEmail);
			}
		}

		DomainTemplate template = new DomainTemplate();
		template.setCompanyCn(companyCn);
		template.setCompanyEn(companyEn);
		template.setLastnameCn(lastnameCn);
		template.setFirstnameCn(firstnameCn);
		template.setCountryCn(countryCn);
		template.setExtensionFax(extensionFax);
		template.setStateCn(stateCn);
		template.setCityCn(cityCn);
		template.setAddressCn(addressCn);
		template.setLastnameEn(lastnameEn);
		template.setFirstnameEn(firstnameEn);
		template.setCityEn(cityEn);
		template.setCountyEn(countryEn);
		template.setStateEn(stateEn);
		template.setCityEn(cityEn);
		template.setAddressEn(addressEn);
		template.setZipcode(zipcode);
		template.setPhone(phone);
		template.setExtensionNumber(extensionNumber);
		template.setFax(fax);
		template.setEmail(email);
		template.setUserid(userid);
		if (usertype.equals("I")) {
			template.setUsertype(1);
		} else {
			template.setUsertype(0);
		}
		template.setIschecked(1); // 1为默认 2为不是
		template.setIschecked(0);
		template.setIsforbidden(1);
		template.setRegtime(df.parse(df.format(date)));
		template.setCreattime(df.parse(df.format(date)));
		template.setTemplateType(Integer.parseInt(templateType));

		domainTemplateService.updateTemplate(template);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());

		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/template/updateTemple.do", //操作链接
					"修改模版", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("修改模版添加日志出错");
		}
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 删除模板（联系人）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteTemple", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteTemple(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);
		String userid = maps.get("userid");
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String companyId = DomainUtil.getCompanyId(request);

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(userid)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/DeleteContact.aspx",
				authStr + "&userid=" + userid + "");

		JSONObject jsonObject = JSONObject.fromObject(sendGet);
		
		if (jsonObject.get("status").equals("failed")) {
			String str = (String)jsonObject.get("message");
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(str.substring(str.lastIndexOf(" ")));
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/template/deleteTemple.do", //操作链接
						"删除模版", //操作类型
						companyId, //操作者companyid
						Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
						str.substring(str.lastIndexOf(" ")), //操作详细描述
						rd.getMsg());//
			} catch (Exception e) {
				log.error("删除模版添加日志出错");
			}
			return JSONUtils.createObjectJson(rd);
		}

		domainTemplateService.delete(new DomainTemplate().setUserid(userid));

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/template/updateTemple.do", //操作链接
					"修改模版", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("修改模版添加日志出错");
		}
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 查询模板（联系人）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectTemple", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectTemple(HttpServletRequest request) throws Exception {
		Map<String, String> maps = ApiTool.getBodyString(request);
		String userid = maps.get("userid");
		String companyId = DomainUtil.getCompanyId(request);

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(userid)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		List<DomainTemplate> domainTemplateList = null;
		if (!"".equals(userid) && userid != null) {
			domainTemplateList = domainTemplateService.select(new DomainTemplate().setUserid(userid));
		} else {
			domainTemplateList = domainTemplateService.select(null);
		}

		map.put("domainTemplateList", domainTemplateList);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		//日志写入 118.187.7.248
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/template/selectTemple.do", //操作链接
					"查询模板", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("查询模版添加日志出错");
		}
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "emailVerify", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String emailVerify(HttpServletRequest request) throws Exception {

		request.setCharacterEncoding("utf-8");
		Map<String, String> maps = ApiTool.getBodyString(request);
		String email = maps.get("email");

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(email)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		String companyid = DomainUtil.getCompanyId(request);

		Map<String, String> param_Map = null;
		if (param_Map == null || param_Map.isEmpty()) {
			param_Map = new HashMap<String, String>();
			param_Map.put("email", email);

			String url2 = domainPath + "emailSuccess?email=" + email;
			String content = "亲爱的用户，您好！<br><br>欢迎使用新睿云。请进行邮箱验证激活您的信息模板。 <a href='" + url2 + "'>请点击此链接进行认证：链接内容</a>. <br><br>本邮件由系统自动发送，请勿直接回复！<br>感谢您的访问，祝您使用愉快";
			param_Map.put("content", content); // 发送内容
			param_Map.put("companyId", companyid);
			param_Map.put("subject", "新睿云邮箱认证"); // 发送标题
		}
		RspData postIbMsg = HttpReq.sendMessage("smsMailService", "sendEmail", param_Map);

		DomainEmail domainEmail = new DomainEmail();
		domainEmail.setEmail(email);
		domainEmail.setStatus(3);

		domainEmailService.update(domainEmail);

		param_Map.clear();

		if (postIbMsg.getMsg().equals("邮件发送失败")) {
			rd.setStatus("2");
		} else {
			rd.setStatus("1");
		}
		rd.setMsg(postIbMsg.getMsg());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 邮箱验证成功
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "emailVerifySuccess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String emailVerifySuccess(HttpServletRequest request) throws Exception {
		Map<String, String> maps = ApiTool.getBodyString(request);
		String email = maps.get("email");

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(email)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DomainEmail domainEmail = new DomainEmail();
		domainEmail.setEmail(email);
		domainEmail.setStatus(1);
		Date date = new Date();
		domainEmail.setCompletetime(df.parse(df.format(date)));

		domainEmailService.update(domainEmail);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {

		RspData rd = new RspData();

		String companyId = DomainUtil.getCompanyId(request);
		// 将文件放在项目内
		String route = request.getSession().getServletContext()
				.getRealPath("uploadIdCard/" + TimeUtil.getYearToDay() + "/");
		// 远程ip
		String domain = GlobalAttr.getInstance().getUploadImage();
		// 远程存储路径
		String http_route = "/uploadIdCard/resource/" + TimeUtil.getYearToDay();
		String fileName = companyId + "_" + new Date().getTime();
		int last = file.getOriginalFilename().lastIndexOf(".");
		String endString = file.getOriginalFilename().substring(last, file.getOriginalFilename().length());
		// 文件名
		fileName = fileName + endString;

		FileUtil.uploadFile(file.getBytes(), route + "/", fileName);
		String filePath = route + "/" + fileName;
		SFTPUtil.remoteTransportFile(http_route, filePath);
		http_route = domain + http_route.substring(13);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imageUrl", http_route + '/' + fileName);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 获得真实姓名
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "getRealName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getRealName(HttpServletRequest request) throws Exception {

		RspData rd = new RspData();

		String companyId = DomainUtil.getCompanyId(request);

		List<YrComper> yrComper = yrComperService.select(new YrComper().setCompanyid(companyId));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("realName", yrComper.get(0).getRealname());
		map.put("companyId", companyId);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 实名认证
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "authenticationVerify", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String authenticationVerify(HttpServletRequest request) throws Exception {

		Map<String, String> maps = ApiTool.getBodyString(request);
		String userid = maps.get("userid"); // 联系人ID
		String userName = maps.get("userName");
		String ownertype = maps.get("ownertype"); // 注册人类型 E—组织 I—个人
		String orgtype = maps.get("orgtype"); // 组织类型 PR—个体 PU—工商
		String ownerpictype = maps.get("ownerpictype"); // 注册人证件类型
		String ownernumber = maps.get("ownernumber"); // 注册人证件号码
														// （如果注册人类型选择组织，则此项必填）
		String contactidcard = maps.get("contactidcard"); // 联系人身份证号码（个人必填,组织选填）
		String imageUrl = maps.get("imageUrl");

		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(userName, userid, ownertype, ownerpictype, imageUrl)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		String companyId = DomainUtil.getCompanyId(request);

		String dateStr = TimeUtil.getTime();
		Map<String, String> param = DomainUtil.getAuthMap(dateStr);
		param.put("userid", userid);
		param.put("ownertype", ownertype);
		param.put("orgtype", orgtype);
		param.put("ownerpictype", ownerpictype);
		String sendGet = null;
		if (ownertype.equals("E")) {
			param.put("ownernumber", ownernumber);
			param.put("ownerpiclink", imageUrl.substring(7)); // 注册人证件下载地址（如果注册人类型选择组织，则此项必填）
		} else {
			param.put("contactidcard", contactidcard);
			param.put("contactidcardpiclink", imageUrl.substring(7)); // 联系人身份证下载地址（个人必填,组织选填）
		}

		sendGet = HttpSendUtil.doGet("http://api.cndns.com/domains/DomainAudit.aspx",
				param);

		JSONObject jsonObject = JSONObject.fromObject(sendGet);
		if (jsonObject.get("status").equals("failed")) {
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg((String) jsonObject.get("message"));
			return JSONUtils.createObjectJson(rd);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DomainAuthentication authentication = new DomainAuthentication();
		authentication.setUserid(Integer.parseInt(userid));
		authentication.setCompanyId(companyId);
		authentication.setUsername(userName);
		authentication.setAuthenicationtype(ownertype);
		if (ownertype.equals("E")) {
			authentication.setOrganizationType(orgtype);
			authentication.setCardnum(ownernumber);
		} else {
			authentication.setCardnum(contactidcard);
		}
		authentication.setCardtype(Integer.parseInt(ownerpictype));
		authentication.setCardurl(imageUrl);
		authentication.setCompanyId(companyId);
		authentication.setStatus(5);

		domainAuthenticationService.update(authentication);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/template/updateTemple.do", //操作链接
					"修改模版", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("修改模版添加日志出错");
		}
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 实名认证成功
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "authenticationVerifySuccess", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String authenticationVerifySuccess(HttpServletRequest request) throws Exception {
		RspData rd = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String username = maps.get("username");

		DomainAuthentication domainauthentication = new DomainAuthentication();
		domainauthentication.setUsername(username);
		domainauthentication.setCompanyId(ApiTool.getUserMsg(request).getCompanyid());
		domainauthentication.setStatus(1);

		domainAuthenticationService.update(domainauthentication);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 设为默认模板
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "setDefaultTemplate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String setDefaultTemplate(HttpServletRequest request) throws Exception {
		RspData rd = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String userid = maps.get("userid");
		String companyId = DomainUtil.getCompanyId(request);

		// 参数完整性判断
		if (!ParamIsNull.isNull(userid)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		domainTemplateService.updateByParam("isdefault=1 where userid='" + userid + "'");
		List<DomainTemplate> templateList = domainTemplateService.select(new DomainTemplate().setCompanyid(companyId));
		for (DomainTemplate domainTemplate : templateList) {
			domainTemplateService.updateByParam("isdefault=0 where userid!='" + userid + "'");
		}

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 获取当前用户的信息模板 --持有者
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectTemplates", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectTemplates(HttpServletRequest request) throws Exception {

		RspData rd = new RspData();
		String companyId = DomainUtil.getCompanyId(request);

		DomainTemplate domainTemplate = new DomainTemplate();
		domainTemplate.setCompanyid(companyId);
		domainTemplate.setTemplateType(1);

		List<DomainTemplate> templates = domainTemplateService.select(domainTemplate);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		for (DomainTemplate template : templates) {
			try {
				List<DomainEmail> email = domainEmailService.select(new DomainEmail().setEmail(template.getEmail()));
				template.setEmailStatus(email.get(0).getStatus());
				List<DomainAuthentication> authentication = domainAuthenticationService.selectByParam(null,
						"companyId='" + companyId + "' and userName='" + template.getCompanyCn() + "'");
				template.setUserStatus(authentication.get(0).getStatus());
			} catch (Exception e) {
				try {
					List<DomainEmail> domainEmailList = domainEmailService
							.select(new DomainEmail().setEmail(template.getEmail()));
					if (domainEmailList.size() == 0) {
						DomainEmail domainEmail = new DomainEmail();
						domainEmail.setEmail(template.getEmail());
						domainEmail.setStatus(2); // 2 未认证 1 已认证 3 认证中
						domainEmail.setCreatetime(df.parse(df.format(date)));

						domainEmailService.insert(domainEmail);
					}

					DomainAuthentication domainAuthentication = new DomainAuthentication();
					domainAuthentication.setCompanyId(companyId);
					domainAuthentication.setUsername(template.getCompanyCn());

					List<DomainAuthentication> domainAuthenticationList = domainAuthenticationService
							.select(domainAuthentication);
					if (domainAuthenticationList.size() == 0) {
						DomainAuthentication authentication = new DomainAuthentication();
						authentication.setCompanyId(companyId);
						authentication.setUsername(template.getCompanyCn());
						authentication.setUserid(Integer.parseInt(template.getUserid()));
						// authentication.setAuthenicationtype(idtype);
						if (template.getUsertype().equals("I")) {
							authentication.setCardtype(1);
						} else {
							authentication.setCardtype(0);
						}

						// authentication.setCardnum(Long.parseLong(idnumber));
						authentication.setStatus(2); // 2 未认证 1 已认证 3 认证中
						authentication.setCreatetime(df.parse(df.format(date)));

						domainAuthenticationService.insert(authentication);
					}
				} catch (Exception e1) {

				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templates", templates);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}

	/**
	 * 根据userid 获取当前用户的信息模板 --持有者
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectTemplatesByUserid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectTemplatesByUserid(HttpServletRequest request) throws Exception {

		RspData rd = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String companyId = DomainUtil.getCompanyId(request);
		String userId = maps.get("userId");
		// 参数完整性判断
		if (!ParamIsNull.isNull(userId)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}

		DomainTemplate domainTemplate = new DomainTemplate();
		domainTemplate.setUserid(userId);
		domainTemplate.setTemplateType(1);

		List<DomainTemplate> templates = domainTemplateService.select(domainTemplate);

		for (DomainTemplate template : templates) {
			List<DomainEmail> email = domainEmailService.select(new DomainEmail().setEmail(template.getEmail()));
			template.setEmailStatus(email.get(0).getStatus());
			List<DomainAuthentication> authentication = domainAuthenticationService.selectByParam(null,
					"companyId='" + companyId + "' and userName='" + template.getCompanyCn() + "'");
			template.setUserStatus(authentication.get(0).getStatus());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templates", templates);

		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rd);
	}
}
