package com.domain.controller;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.domain.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.DomainAuthentication;
import com.domain.pojo.DomainEmail;
import com.domain.pojo.DomainName;
import com.domain.pojo.DomainPrice;
import com.domain.pojo.DomainTemplate;
import com.domain.pojo.RspData;
import com.domain.service.DomainAuthenticationService;
import com.domain.service.DomainEmailService;
import com.domain.service.DomainNameService;
import com.domain.service.DomainPriceService;
import com.domain.service.DomainTemplateService;
import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(MappingConfigura.DOMAIN)
public class DomainController {

	private Logger log = Logger.getLogger(DomainController.class);
	
	@Autowired
	private DomainPriceService domainPriceService;
	
	@Autowired
	private DomainNameService domainNameService;
	
	@Autowired
	private DomainTemplateService domainTemplateService;
	
	@Autowired
	private DomainEmailService domainEmailService;
	
	@Autowired
	private DomainAuthenticationService domainAuthenticationService;
	
	
	/**
	 * 搜索域名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "domainFound", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainFound(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String domainName = maps.get("domainName");
		String tids = maps.get("tids");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String[] tid = null;
		try{
			if(!tids.equals("")){
				tid = tids.split(",");
			}else{
				List<DomainPrice> domainNameList = domainPriceService.selectByParam(null, "type=1");
				List<String> list = new LinkedList<String>();
				for(DomainPrice domainPrice : domainNameList){
					list.add(domainPrice.getDomainType());
				}
				tid = list.toString().replace("[", "").replace("]", "").split(",");
			}
		} catch (Exception e) {
			List<DomainPrice> domainNameList = domainPriceService.selectByParam(null, "type=1");
			List<String> list = new LinkedList<String>();
			for(DomainPrice domainPrice : domainNameList){
				list.add(domainPrice.getDomainType());
			}
			tid = list.toString().replace("[", "").replace("]", "").split(",");
		}
		 
		String sendGet = "";
		JSONObject jsonObject = null;
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		Map<String, Object>[] list = new Map[tid.length + 1];
		List<Map<String, Object>> data = new LinkedList<Map<String,Object>>();
		
		int i = 0;
		for(String str : tid){
			str = str.trim();
			List<DomainName> domainNameList = domainNameService.selectByParam(null, "domainname='" + (domainName.toLowerCase() + str) + "'");
			list[i] = new LinkedHashMap<String, Object>();
			
			sendGet = HttpReq.sendGet("http://dmcheck.cndns.com/check.aspx","domain-name="+ domainName +"&tld="+ str +"&" + authStr);
			System.out.println(sendGet);
			
			try{
				List<DomainPrice> priceList = domainPriceService.selectByParam(null, " domain_type='" + str + "'");
				jsonObject = JSONObject.fromObject(sendGet);
				JSONArray cndns = (JSONArray) jsonObject.get("message");
				JSONObject  object = (JSONObject) cndns.get(0);
				list[i].put("name" ,domainName + str);
				list[i].put("isRes", object.getString(domainName.toLowerCase() + str));
				list[i].put("price", priceList.get(0).getAdjustpurchase() + "");
				if(object.getString(domainName.toLowerCase() + str).equals("unavailable") && domainNameList.size() != 0){
					list[i].put("status", "1");
				}else{
					list[i].put("status", "0");
				}
				
				data.add(list[i]);
			}	catch (Exception e) {
			}
			
			i++;
		}
		map.put("results", data);
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 查询域名信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDomainName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDomainName(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String domainName = maps.get("domainName");
		String status = maps.get("status");
		
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName, status)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(status.equals("1")){
			
			List<DomainName> userid = domainNameService.selectByParam(null, "domainname='" + domainName + "'");
			List<DomainTemplate> companyCn = domainTemplateService.selectByParam(null, "userid=" + userid.get(0).getUserid() + " and templateType=1");
			
			System.out.println("--- > http://api.cndns.com/domains/DomainDetail.aspx" + authStr +"&domainname="+ domainName +"");
			String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/DomainDetail.aspx", 
					authStr +"&domainname="+ domainName +"");
			
			JSONObject jsonObject = null;
			jsonObject = JSONObject.fromObject(sendGet);
			if("failed".equals(jsonObject.get("status"))){
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(ExptNum.FAIL.getDesc());
				return  JSONUtils.createObjectJson(rd);
			}
			jsonObject = JSONObject.fromObject(sendGet);
			JSONArray cndns = (JSONArray) jsonObject.get("message");
			map.put("getDomain", cndns);
			map.put("companyCn", companyCn.get(0).getCompanyCn());
		}else{
			String sendGet = HttpReq.sendGet("http://dmcheck.cndns.com/whois.aspx", 
					"domain="+ domainName + "&ip=" + DomainUtil.getIpAddress(request));
			
			if("".equals(sendGet) || sendGet == null){
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(ExptNum.FAIL.getDesc());
				return  JSONUtils.createObjectJson(rd);
			}
			
			JSONObject jsonObject = null;
			jsonObject = JSONObject.fromObject(sendGet);
			String detailInfo = (String) jsonObject.get("detailInfo");
			List<String> list = new LinkedList<String>();
			for(String str : detailInfo.split("<br/>")){
				list.add(str);
			}
			map.put("getDomain", jsonObject);
			map.put("detailInfo", list);
		}
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 查询域名信息 -- modificationDNS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDomain", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDomain(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainName = maps.get("domainName");
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String sendGet = HttpReq.sendGet("http://dmcheck.cndns.com/whois.aspx", 
				"domain="+ domainName + "&ip=" + DomainUtil.getIpAddress(request));
		
		if("".equals(sendGet) || sendGet == null){
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}
		JSONObject jsonObject = null;
		jsonObject = JSONObject.fromObject(sendGet);
		String detailInfo = (String) jsonObject.get("detailInfo");
		List<String> list = new LinkedList<String>();
		for(String str : detailInfo.split("<br/>")){
			list.add(str);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("getDomain", jsonObject);
		map.put("detailInfo", list);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		rd.setData(map);
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 创建域名  -- 暂停使用
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createDomainName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createDomainName(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainName = maps.get("domainName"); //联系人ID
		String userid = maps.get("userid"); //域名
		String years = maps.get("years"); //注册年限
		String isName = maps.get("isName"); 
		String signature = maps.get("signature");
		String companyId = DomainUtil.getCompanyId(request);
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName, userid, years)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		if(signature == null || signature.equals("")){
			signature = "";
		}
		
		if(domainName.contains(",")){
			for(String str : domainName.split(",")){
				
				StringBuilder msg=new StringBuilder();//域名密码
				Random random=new Random();
				for(int i = 0;i < 8; i++){
					msg.append(random.nextInt(10));
				}
				
				String resultBytes = DesUtils.getInstance().encrypt(msg.toString());
				
				String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/CreateDom.aspx", 
						authStr  +"&userid="+ userid +"&domainname=" + str + "&years="+ years +"&domainpass="+ msg.toString() +"&isName=" + isName +"&signature=" + signature +"");
				
				JSONObject jsonObject = null;
				jsonObject = JSONObject.fromObject(sendGet);
				if("failed".equals(jsonObject.get("status"))){
					rd.setStatus(ExptNum.FAIL.getCode() + "");
					rd.setMsg(ExptNum.FAIL.getDesc());
					//日志写入
					try {
						String ip = GetRemoteIp.getIpAddress(request);
						AddOprateloUtil.domainResoveLog(ip, //操作ip
								"/domain/createDomainName.do", //操作链接
								"查询域名信息whois", //操作类型
								companyId, //操作者companyid
								Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
								ExptNum.FAIL.getDesc(), //操作详细描述
								rd.getMsg());//
					} catch (Exception e) {
						log.error("查询域名信息whois添加日志出错");
					}
					return  JSONUtils.createObjectJson(rd);
				}
				
				DomainName name = new DomainName();
				name.setCompanyid(companyId);
				name.setUserid(userid);
				name.setDomainname(domainName);
				name.setYears(Integer.parseInt(years));
				name.setDomainpass(resultBytes);
				name.setCreatetime(new Date());
				
				Calendar calendar1 = Calendar.getInstance();
				calendar1.set(Calendar.YEAR, calendar1.get(Calendar.YEAR) + Integer.parseInt(years));
				Date today1 = calendar1.getTime();
				name.setEndtime(today1);
				
				name.setStatus(1);
				if(isName != null && !"".equals(isName)){
					name.setIsname(0);
				}else{
					name.setIsname(Integer.parseInt(isName));
				}
				name.setSignature(signature);
				
				domainNameService.insert(name);
			}
		}else{
			StringBuilder msg=new StringBuilder();//域名密码
			Random random=new Random();
			for(int i = 0;i < 8; i++){
				msg.append(random.nextInt(10));
			}
			
			String resultBytes = DesUtils.getInstance().encrypt(msg.toString());
			
			String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/CreateDom.aspx", 
					authStr  +"&userid="+ userid +"&domainname=" + domainName + "&years="+ years +"&domainpass="+ msg.toString() +"&isName=" + isName +"&signature=" + signature +"");
			
			JSONObject jsonObject = null;
			jsonObject = JSONObject.fromObject(sendGet);
			if("failed".equals(jsonObject.get("status"))){
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(ExptNum.FAIL.getDesc());
				//日志写入
				try {
					String ip = GetRemoteIp.getIpAddress(request);
					AddOprateloUtil.domainResoveLog(ip, //操作ip
							"/domain/createDomainName.do", //操作链接
							"查询域名信息whois", //操作类型
							companyId, //操作者companyid
							Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
							ExptNum.FAIL.getDesc(), //操作详细描述
							rd.getMsg());//
				} catch (Exception e) {
					log.error("查询域名信息whois添加日志出错");
				}
				return  JSONUtils.createObjectJson(rd);
			}
			
			DomainName name = new DomainName();
			name.setCompanyid(companyId);
			name.setUserid(userid);
			name.setDomainname(domainName);
			name.setYears(Integer.parseInt(years));
			name.setDomainpass(resultBytes);
			name.setCreatetime(new Date());
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, calendar1.get(Calendar.YEAR) + Integer.parseInt(years));
			Date today1 = calendar1.getTime();
			name.setEndtime(today1);
			
			name.setStatus(1);
			if(isName != null && !"".equals(isName)){
				name.setIsname(0);
			}else{
				name.setIsname(Integer.parseInt(isName));
			}
			name.setSignature(signature);
			
			domainNameService.insert(name);
		}
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	
	
	/**
	 * 获取域名价格
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getPrice", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPrice(HttpServletRequest request) throws Exception{
			
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String type = maps.get("type"); //获取产品价格类型 1：域名 2：橙云 3：虚拟主机 4：建站  5：DDOS
		String years = maps.get("years"); //年限 格式错误就默认1年时间 
		String tag = maps.get("tag"); //域名类型 中文域名:dmc 英文域名:dme (必填,固定值) 
		String suffix = maps.get("suffix"); //域名后缀 如：.com后缀 或 .com.cn后缀 
		String prd = maps.get("prd"); //域名prd  
		String priceType = maps.get("priceType"); //价格类型 购买价格:0  续费价格:1 (选填,默认为0)
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String companyId = DomainUtil.getCompanyId(request);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(type, years, tag, suffix, priceType)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String sendGet = "";
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = null;
		
		if(prd != null && !"".equals(prd)){
			sendGet = HttpReq.sendGet("http://api.cndns.com/product/getProductPrice.aspx", 
					authStr  +"&type="+ type +"&years=" + years + "&tag="+ tag +"&prd="+ prd + "&priceType=" + priceType +"");
			
			jsonObject = JSONObject.fromObject(sendGet);
			if("failed".equals(jsonObject.get("status"))){
				rd.setStatus(ExptNum.FAIL.getCode() + "");
				rd.setMsg(ExptNum.FAIL.getDesc());
				return  JSONUtils.createObjectJson(rd);
			}
			
			String price = (String) jsonObject.get("message");
			
			map.put("tag", tag +prd);
			map.put("price", price);
				
		}else{
			List<String> tagList = new LinkedList<String>();
			List<String> priceList = new LinkedList<String>();
			for(String str : suffix.split(",")){
				sendGet = HttpReq.sendGet("http://api.cndns.com/product/getProductPrice.aspx", 
						authStr  +"&type="+ type +"&years=" + years + "&tag="+ tag +"&suffix="+ str + "&priceType=" + priceType +"");
				
				jsonObject = JSONObject.fromObject(sendGet);
				if("failed".equals(jsonObject.get("status"))){
					rd.setStatus(ExptNum.FAIL.getCode() + "");
					rd.setMsg(ExptNum.FAIL.getDesc());
					return  JSONUtils.createObjectJson(rd);
				}
				
				tagList.add(tag + str);
				priceList.add((String) jsonObject.get("message"));
				
			}
			map.put("tag", tagList);
			map.put("price", priceList);
		}
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取域名续费价格
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRenewPrice", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getRenewPrice(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String years = maps.get("years");
		String pdtnme = maps.get("pdtnme");  //域名
//		String tmetpe = maps.get("tmetpe"); 
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(pdtnme, years)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		String sendGet = HttpReq.sendGet("http://api.cndns.com/product/getRenewPrice.aspx",
				authStr + "&type=1&years=" + years + "&pdtnme=" + pdtnme + "&tmetpe=5");
		
		JSONObject jsonObject = null;
		jsonObject = JSONObject.fromObject(sendGet);
		if("failed".equals(jsonObject.get("status"))){
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}
		
		String price = jsonObject.getString("message");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("price", price);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}

	String cloudPath= com.domain.util.Prop.getInstance().getPropertiesByPro("cloudStack.properties", "yrcloud.path");

	public DomainController() {
		super();
	}

	/**
	 * 续费域名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "renewDomain", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String renewDomain(HttpServletRequest request, @RequestBody Map<String, Object> maps) throws Exception{

		String companyid = DomainUtil.getCompanyId(request);
		maps.put("companyId", companyid);
		String companyId = DomainUtil.getCompanyId(request);

		//获取用户信息
		Map<String, Object> param_map = new HashMap<String, Object>();
		param_map.put("companyId", companyid);
		RspData resd = HttpReq.getIbMsg("comperService", "getComperInfo", param_map);
		//String cloudBasePath = "http://192.168.3.29:8081/ruicloudApi";
		//获取token加密串
		Map<String,Object> code=(Map<String,Object>)resd.getData().get("content");
		String token_reslut=HttpReq.sendGet(cloudPath+ "user/getToken.json","secret="+code.get("code")+"&companyId="+companyid);
		Map<String, Object> map_token=new HashMap<String, Object>();
		System.err.println("map_token:"+token_reslut);
		try {
			map_token=JSONUtils.getMapObjectByJson(token_reslut);
		} catch (Exception e) {
			return "云平台返回token格式化错误:"+token_reslut;
		}
		if(!new Double("1.0").equals(map_token.get("status"))){
			return "获取token失败:"+map_token.get("message");
		}
		maps.put("token",map_token.get("token"));

		String increaseFlux = HttpReq.sendPost(cloudPath + "domain/createOrder.json", FcUtil.map2ParamV2Object(maps));
		RspData rd = new RspData();
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domain/renewDomain.do", //操作链接
					"续费域名", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.FAIL.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("续费域名添加日志出错");
		}
		return increaseFlux;
	}
	
	/**
	 * 获取后缀
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getSuffix", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSuffix(HttpServletRequest request) throws Exception{
		
		RspData rd = new RspData();
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> en = new ArrayList<String>();
		List<String> cn = new ArrayList<String>();
		List<String> xz = new ArrayList<String>();
		List<DomainPrice> suffixs = domainPriceService.select(null);
		for(DomainPrice domainPrice : suffixs){
			if(domainPrice.getType() == 1){
				en.add(domainPrice.getDomainType());
			}else if(domainPrice.getType() == 2){
				cn.add(domainPrice.getDomainType());
			}else{
				xz.add(domainPrice.getDomainType());
			}
		}
		map.put("en", en);
		map.put("cn", cn);
		map.put("xz", xz);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取当前用户的域名列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectDomain", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectDomain(HttpServletRequest request) throws Exception{
		
		RspData rd = new RspData();
	
		String companyId = DomainUtil.getCompanyId(request);
		 
		List<DomainName> domainNames = domainNameService.select(new DomainName().setCompanyid(companyId));
		
		for(DomainName domainName : domainNames){
			try{
				List<DomainTemplate> domainTemplate = domainTemplateService.selectByParam(null, "userid='"+ domainName.getUserid() + "' and templateType=1");
				List<DomainEmail> domainEmail = domainEmailService.select(new DomainEmail().setEmail(domainTemplate.get(0).getEmail()));

				domainName.setEmailType(domainEmail.get(0).getStatus());
				domainName.setUserType(domainName.getIsAutonym());
			}	catch (Exception e){
				log.warn("信息有误！");
			}

		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("domainNames", domainNames);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取当前用户的域名列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setdomainTemplates", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String setdomainTemplates(HttpServletRequest request) throws Exception{
		
		RspData rd = new RspData();
		
		String companyId = DomainUtil.getCompanyId(request);
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		List<DomainName> domainNames = domainNameService.select(null);
		
		for(DomainName domainName : domainNames){
			List<DomainTemplate> domainTemplate = domainTemplateService.selectByParam(null, "userid =  '"+ domainName.getUserid() + "'");
			if(domainTemplate.size() == 0){
				String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/ContactList.aspx", 
						authStr +"&userid="+ domainName.getUserid() +"");
				
				JSONObject jsonObject = JSONObject.fromObject(sendGet);
				
				JSONArray message = (JSONArray) jsonObject.get("message");
				
				JSONObject messageJson = JSONObject.fromObject(message.get(0));
				
				DomainTemplate template = new DomainTemplate();
				template.setCompanyid(companyId);
				template.setUserid((String) messageJson.get("userid"));
				template.setCompanyCn((String) messageJson.get("ownername_cn"));
				template.setCompanyEn((String) messageJson.get("ownername"));
				template.setLastnameCn((String) messageJson.get("lastname_cn"));
				template.setFirstnameCn((String) messageJson.get("firstname_cn"));
				template.setCountryCn((String) messageJson.get("country_cn"));
				template.setStateCn((String) messageJson.get("province_cn"));
				template.setCityCn((String) messageJson.get("city_cn"));
				template.setAddressCn((String) messageJson.get("address_cn"));
				template.setLastnameEn((String) messageJson.get("lastname"));
				template.setFirstnameEn((String) messageJson.get("firstname"));
				template.setCountyEn((String) messageJson.get("country"));
				template.setStateEn((String) messageJson.get("province"));
				template.setCityEn((String) messageJson.get("city"));
				template.setAddressEn((String) messageJson.get("address"));
				template.setZipcode((String) messageJson.get("postcode"));
				template.setPhone((String) messageJson.get("phone"));
				template.setFax((String) messageJson.get("fax"));
				template.setEmail((String) messageJson.get("email"));
//				template.setIdtype(idtype);
//				template.setIdnumber(idnumber);
				template.setIschecked(Integer.parseInt((String) messageJson.get("ischecked"))); //1为默认 2为不是
				template.setIsdefault(0);
				template.setIsforbidden(Integer.parseInt((String)messageJson.get("isforbidden")));
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				template.setRegtime(df.parse((String) messageJson.get("regtime")));
				template.setCreattime(df.parse((String) messageJson.get("regtime")));
				template.setTemplateType(1);
				
				domainTemplateService.insert(template);
				
				template.setTemplateType(2);
				
				domainTemplateService.insert(template);
				
				template.setTemplateType(3);
				
				domainTemplateService.insert(template);
				
				template.setTemplateType(4);
				
				domainTemplateService.insert(template);
				
				List<DomainEmail> domainEmailList = domainEmailService.select(new DomainEmail().setEmail((String) messageJson.get("email")));
				if(domainEmailList.size() == 0){
					DomainEmail domainEmail = new DomainEmail();
					domainEmail.setEmail((String) messageJson.get("email"));
					domainEmail.setStatus(2); //2 未认证 1 已认证 3 认证中
					domainEmail.setCreatetime(df.parse(df.format(date)));
					
					domainEmailService.insert(domainEmail);
				}
				
				DomainAuthentication domainAuthentication = new DomainAuthentication();
				domainAuthentication.setCompanyId(companyId);
				domainAuthentication.setUsername((String) messageJson.get("ownername_cn"));
				
				List<DomainAuthentication> domainAuthenticationList = domainAuthenticationService.select(domainAuthentication);
				if(domainAuthenticationList.size() == 0){
					DomainAuthentication authentication = new DomainAuthentication();
					authentication.setCompanyId(companyId);
					authentication.setUsername((String) messageJson.get("ownername_cn"));
//					authentication.setAuthenicationtype(idtype);
					authentication.setCardtype(1);
					
//					authentication.setCardnum(Long.parseLong(idnumber));
					authentication.setStatus(2); //2 未认证 1 已认证 3 认证中
					authentication.setCreatetime(df.parse(df.format(date)));
					
					domainAuthenticationService.insert(authentication);
				}
			}
		}
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 邮箱是否验证
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "isEmailTrue", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String isEmailTrue(HttpServletRequest request) throws Exception{
	
		Map<String, String> maps = ApiTool.getBodyString(request);
		String email = maps.get("email"); 
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(email)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<DomainEmail> domainEmail = domainEmailService.select(new DomainEmail().setEmail(email));
		Map<String, Object> map  = new HashMap<String, Object>();
		
		if(domainEmail.size() != 0){
			if(domainEmail.get(0).getStatus() == 1){
				map.put("status", "true");
			}else{
				map.put("status", "false");
			}
		}else{
			map.put("status", "false");
		}
			
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 获取某个域名的 模板信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectDomainTemplate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectDomainTemplate(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainName = maps.get("domainName"); 
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<DomainName> domain = domainNameService.select(new DomainName().setDomainname(domainName));
		
		if(domain.size() == 0){
			rd.setStatus(ExptNum.NO_DOMAIN.getCode() + "");
			rd.setMsg(ExptNum.NO_DOMAIN.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}
		
		//模板信息
		List<DomainTemplate> template = domainTemplateService.select(new DomainTemplate().setUserid(domain.get(0).getUserid()));
		
		List<DomainTemplate> domainTemplate = domainTemplateService.selectByParam(null, "userid='"+ domain.get(0).getUserid() + "' and templateType=1");
		
		//用户认证
		List<DomainAuthentication> domainAuthentication = domainAuthenticationService.selectByParam(null, "userid=" + template.get(0).getUserid()+ " and userName='" + domainTemplate.get(0).getCompanyCn() + "'");
		for(DomainAuthentication authen :  domainAuthentication){
			try{
				if(authen.getCardnum().equals(0)){
					authen.setCardnum(null);
				}
			}catch(Exception e){
				
			}
		}
		
		//邮箱认证
		List<DomainEmail> domainEmail = domainEmailService.select(new DomainEmail().setEmail(template.get(0).getEmail()));
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("template", template);
		map.put("domainAuthentication", domainAuthentication);
		map.put("domainEmail", domainEmail);
		map.put("password", DesUtils.getInstance().decrypt(domain.get(0).getDomainpass()));
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 根据模板类型查询信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectDomainTemplateByType", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectDomainTemplateByType(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainName = maps.get("domainName");
		String templateType = maps.get("templateType");
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainName, templateType)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<DomainName> domain = domainNameService.select(new DomainName().setDomainname(domainName));
		
		if(domain.size() == 0){
			rd.setStatus(ExptNum.NO_DOMAIN.getCode() + "");
			rd.setMsg(ExptNum.NO_DOMAIN.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}
		
		DomainTemplate domainTemplate = new DomainTemplate();
		domainTemplate.setUserid(domain.get(0).getUserid());
		domainTemplate.setTemplateType(Integer.parseInt(templateType));
		
		List<DomainTemplate> templates = domainTemplateService.select(domainTemplate);
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("template", templates);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 根据模板ID查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectDomainTemplateById", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String selectDomainTemplateById(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String id = maps.get("id");
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(id)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		List<DomainTemplate> templates = domainTemplateService.select(new DomainTemplate().setId(Integer.parseInt(id)));
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("template", templates);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 根据域名获取DNS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDNS", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDNS(HttpServletRequest request) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String domainname  = maps.get("domainname");
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainname)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		System.out.println("http://api.cndns.com/domains/DNSList.aspx" + "domainname="+ domainname +"&" + authStr);
		String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/DNSList.aspx","domainname="+ domainname +"&" + authStr);
		
		JSONObject jsonObject = null;
		
		jsonObject = JSONObject.fromObject(sendGet);
		if("failed".equals(jsonObject.get("status"))){
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			return  JSONUtils.createObjectJson(rd);
		}
		
		JSONObject cndns = (JSONObject) jsonObject.get("message");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dns", cndns);
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 根据域名修改DNS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateDNS", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateDNS(HttpServletRequest request) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String domainname  = maps.get("domainname");
		String dns_hst1 = maps.get("dns_hst1");
		String dns_hst2 = maps.get("dns_hst2");
		String dns_hst3 = maps.get("dns_hst3");
		String dns_hst4 = maps.get("dns_hst4");
		String dns_hst5 = maps.get("dns_hst5");
		String dns_hst6 = maps.get("dns_hst6");
		String companyId = DomainUtil.getCompanyId(request);
		
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainname, dns_hst1, dns_hst2)) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);

			return JSONUtils.createObjectJson(rd);
		}
		
		if(dns_hst3 != null && !"".equals(dns_hst3)){
			
		}
		String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/ModifyDNS.aspx",authStr + "&" + "domainname=" + domainname + "&dns_hst1=" + dns_hst1 + "&dns_hst2=" + dns_hst2 + "&dns_hst3=" + dns_hst3 + "&dns_hst4=" + dns_hst4 + "&dns_hst5=" + dns_hst5 + "&dns_hst6="+ dns_hst6);
		
		JSONObject jsonObject = null;
		
		jsonObject = JSONObject.fromObject(sendGet);
		if("failed".equals(jsonObject.get("status"))){
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domain/updateDNS.do", //操作链接
						"修改DNS", //操作类型
						companyId, //操作者companyid
						Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
						ExptNum.FAIL.getDesc(), //操作详细描述
						rd.getMsg());//
			} catch (Exception e) {
				log.error("修改DNS添加日志出错");
			}
			return  JSONUtils.createObjectJson(rd);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dns", jsonObject.get("message"));
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domain/updateDNS.do", //操作链接
					"修改DNS", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("修改DNS添加日志出错");
		}
		return  JSONUtils.createObjectJson(rd);
	}
	
	/**
	 * 根据域名还原美橙DNS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "restoreDNS", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String restoreDNS(HttpServletRequest request) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		
		String domainname  = maps.get("domainname");
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String companyId = DomainUtil.getCompanyId(request);
		
		RspData rd = new RspData();
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainname )) {
			rd.setStatus(GetResult.ERROR_STATUS + "");
			rd.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rd);
		}
		
		String sendGet = HttpReq.sendGet("api.cndns.com/domains/ResetDNS.aspx","domainname="+ domainname +"&" + authStr);
		
		JSONObject jsonObject = null;
		
		jsonObject = JSONObject.fromObject(sendGet);
		if("failed".equals(jsonObject.get("status"))){
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domain/restoreDNS.do", //操作链接
						"还原DNS", //操作类型
						companyId, //操作者companyid
						Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
						ExptNum.SUCCESS.getDesc(), //操作详细描述
						rd.getMsg());//
			} catch (Exception e) {
				log.error("还原DNS添加日志出错");
			}
			return  JSONUtils.createObjectJson(rd);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dns", jsonObject.get("message"));
		
		rd.setStatus(ExptNum.SUCCESS.getCode() + "");
		rd.setData(map);
		rd.setMsg(ExptNum.SUCCESS.getDesc());
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domain/restoreDNS.do", //操作链接
					"还原DNS", //操作类型
					companyId, //操作者companyid
					Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					rd.getMsg());//
		} catch (Exception e) {
			log.error("还原DNS添加日志出错");
		}
		return  JSONUtils.createObjectJson(rd);
	}


	/**
	 * 购买域名 付费
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "feeDeductionBuy", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String feeDeductionBuy(HttpServletRequest request) throws Exception{

		Map<String, String> maps = ApiTool.getBodyString(request);
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String domainName = (String)maps.get("domainName");
		String signature = (String)maps.get("signature");
		String companyid = (String)maps.get("companyid");
		String isName = (String)maps.get("isName");
		String years = (String)maps.get("years");
		String userid = (String)maps.get("userid");
		String door = (String)maps.get("door");
		
		System.out.println("door ---- > " + door);
		System.out.println("domainName ---- > " + domainName);
		
		if(null == signature || "null".equals(signature)){
			signature="";
		}
		
		String message = "";
		StringBuilder msg=new StringBuilder();//域名密码
		java.util.Random  random=new java.util.Random();
		for(int j = 0;j < 8; j++) {
			msg.append(random.nextInt(10));
		}

		String resultBytes = DesUtils.getInstance().encrypt(msg.toString());
		if("open".equals(door)){
			String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/CreateDom.aspx",
					authStr  +"&userid="+ maps.get("userid") +"&domainname=" + domainName + "&years="+ years +"&domainpass="+ msg +"&isName=" + isName +"&signature=" + signature +"");
			
			JSONObject jsonObject = null;
			jsonObject = JSONObject.fromObject(sendGet);
			if("failed".equals(jsonObject.get("status"))){
				String str = (String) jsonObject.get("message");
				message += "fail," + str.substring(str.lastIndexOf(" "));
				//日志写入
				try {
					String ip = GetRemoteIp.getIpAddress(request);
					AddOprateloUtil.domainResoveLog(ip, //操作ip
							"/domain/feeDeductionBuy.do", //操作链接
							"购买域名失败", //操作类型
							companyid, //操作者companyid
							Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
							str.substring(str.lastIndexOf(" ")), //操作详细描述
							str.substring(str.lastIndexOf(" ")));//
				} catch (Exception e) {
					log.error("还原DNS添加日志出错");
				}
			}else{
				message += "success,";
				//日志写入
				try {
					String ip = GetRemoteIp.getIpAddress(request);
					AddOprateloUtil.domainResoveLog(ip, //操作ip
							"/domain/feeDeductionBuy.do", //操作链接
							"购买域名成功", //操作类型
							companyid, //操作者companyid
							Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
						ExptNum.SUCCESS.getDesc(), //操作详细描述
						ExptNum.SUCCESS.getDesc());//
				} catch (Exception e) {
					log.error("还原DNS添加日志出错");
				}
			}
		}
		
		List<DomainTemplate> template = domainTemplateService.selectByParam(null, " userid='" + userid + "' and templateType=1");
		
		List<DomainAuthentication> authen = domainAuthenticationService.selectByParam(null, " userid='" + userid + "' and companyId='" + companyid + "' and userName='" + template.get(0).getCompanyCn() +"'");
		
		DomainName name = new DomainName();
		name.setCompanyid(companyid);
		name.setUserid(userid);
		name.setDomainname(domainName);
		name.setYears(Integer.parseInt(years));
		name.setDomainpass(resultBytes);
		name.setCreatetime(new Date());
		name.setIsresolvelist(0);
		if(authen.get(0).getStatus() == 1){
			name.setIsAutonym(3);
		}else{
			name.setIsAutonym(2);
		}

		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, calendar1.get(Calendar.YEAR) + Integer.parseInt(years));
		Date today1 = calendar1.getTime();
		name.setEndtime(today1);

		name.setStatus(1);
		if(isName != null && !"".equals(isName)){
			name.setIsname(0);
		}else{
			name.setIsname(Integer.parseInt(isName));
		}
		name.setSignature(signature);

		domainNameService.insert(name);
	
		return message;
	}

	/**
	 * 续费域名 续费
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "feeDeductionRenevw", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String feeDeductionRenevw(HttpServletRequest request) throws Exception{
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String companyid = (String)maps.get("companyid");
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		
		String sendGet = HttpReq.sendGet("http://api.cndns.com/domains/DomainRenew.aspx",
				authStr + "&domainname=" + maps.get("domainName") + "&exptme=" + maps.get("exptme") + "&signature=" + maps.get("signature"));
		String message = "";
		JSONObject jsonObject = null;
		jsonObject = JSONObject.fromObject(sendGet);
		
		if("failed".equals(jsonObject.get("status"))){
			String str = (String) jsonObject.get("message");
			message += "fail," + str.substring(str.lastIndexOf(" "));
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domain/feeDeductionRenevw.do", //操作链接
						"续费域名失败", //操作类型
						companyid, //操作者companyid
						Integer.parseInt(ExptNum.FAIL.getCode() + ""), //操作状态，1 ：成功 0： 失败
						str.substring(str.lastIndexOf(" ")), //操作详细描述
						str.substring(str.lastIndexOf(" ")));//
			} catch (Exception e) {
				log.error("续费域名添加日志出错");
			}
		}else{
			message += "success,";
			//日志写入
			try {
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domain/feeDeductionRenevw.do", //操作链接
						"续费域名成功", //操作类型
						companyid, //操作者companyid
						Integer.parseInt(ExptNum.SUCCESS.getCode() + ""), //操作状态，1 ：成功 0： 失败
					ExptNum.SUCCESS.getDesc(), //操作详细描述
					ExptNum.SUCCESS.getDesc());//
			} catch (Exception e) {
				log.error("续费域名添加日志出错");
			}
		}
		
		return message;
	}
}
