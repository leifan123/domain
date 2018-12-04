 package com.domain.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.DomainTransfer;
import com.domain.pojo.RspData;
import com.domain.service.DomainPriceService;
import com.domain.service.DomainTransferService;
import com.domain.service.OrderTableService;
import com.domain.service.YrComperService;
import com.domain.util.AddOprateloUtil;
import com.domain.util.ApiTool;
import com.domain.util.Config;
import com.domain.util.DomainUtil;
import com.domain.util.ExptNum;
import com.domain.util.FcUtil;
import com.domain.util.GetRemoteIp;
import com.domain.util.GetResult;
import com.domain.util.HttpReq;
import com.domain.util.JSONUtils;
import com.domain.util.ParamIsNull;
import com.domain.util.TimeUtil;
import com.yunrui.pojo.YrComper;


@Controller
@RequestMapping("/domaintransfer")
public class DomainTransferController {
	@Autowired
	private DomainTransferService domainTransferService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	String cloudPath= com.domain.util.Prop.getInstance().getPropertiesByPro("cloudStack.properties", "yrcloud.path");
	String iBServerpath= com.domain.util.Prop.getInstance().getPropertiesByPro("cloudStack.properties", "ibserver.path");

	Logger log = Logger.getLogger(getClass());
	//域名转入列表
	@RequestMapping(value = "/domaintransferlist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTransfer(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		RspData rspData = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String companyid = DomainUtil.getCompanyId(request);
		String daomainName  = maps.get("daomainName");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		DomainTransfer daomaintransfer = new DomainTransfer();
		daomaintransfer.setDomainname(daomainName);
		daomaintransfer.setCompanyid(companyid);
		List<DomainTransfer> daomaintransferList = domainTransferService.select(daomaintransfer);
		Map<String,Object> daomaintransferListMap = new HashMap<String,Object>();
		daomaintransferListMap.put("daomaintransferList", daomaintransferList);
		rspData.setData(daomaintransferListMap);
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domaintransfer/domaintransferlist.do", //操作链接
					"获取域名转入列表", //操作类型
					companyid, //操作者companyid
					1, //操作状态，1 ：成功 0： 失败
					"获取转入列表", //操作详细描述
					" ");//
		} catch (Exception e) {
			log.error("添加日志出错");
		}
		 
		return JSONUtils.createObjectJson(rspData);	
	}
	
	//域名转入
	@RequestMapping(value = "/domaintransfer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTransferList(HttpServletRequest request , HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		String discountedOrders  = maps.get("discountedOrders");	
		String Suserid = maps.get("userid");
		String domaintransfers = maps.get("domaintransfers");
		String companyid = DomainUtil.getCompanyId(request);
		Map<String, Object> mapparam = new HashMap<String, Object>();
		mapparam.put("discountedOrders", discountedOrders);
		mapparam.put("companyid", companyid);
		mapparam.put("userid", Suserid);
		mapparam.put("domaintransfers", domaintransfers);
		
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
		mapparam.put("token",map_token.get("token"));
		String increaseFlux = HttpReq.sendPost(cloudPath + "domain/domaintransfer.json", FcUtil.map2ParamV2Object(mapparam));
		
		
		//日志写入
		try {
			Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(increaseFlux);
			if("success".equals(map_cloud.get("status"))){
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domaintransfer/domaintransfer.do", //操作链接
						"域名转入", //操作类型
						companyid, //操作者companyid
						1, //操作状态，1 ：成功 0： 失败
						"域名转入", //操作详细描述
						" ");//
			}else{
				String ip = GetRemoteIp.getIpAddress(request);
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domaintransfer/domaintransfer.do", //操作链接
						"域名转入", //操作类型
						companyid, //操作者companyid
						0, //操作状态，1 ：成功 0： 失败
						"域名转入", //操作详细描述
						increaseFlux);//
			}	
		} catch (Exception e) {
			log.error("域名转入添加日志出错");
		}
		
		return increaseFlux;
	}
	
	//域名转入重新转入
	@RequestMapping(value = "/domainTransferAgain", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTransferAgain(HttpServletRequest request , HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);	
		String Suserid = maps.get("userid");
		String domaintransfers = maps.get("domaintransfers");
		String companyid = DomainUtil.getCompanyId(request);
		Map<String, Object> mapparam = new HashMap<String, Object>();
		mapparam.put("companyid", companyid);
		mapparam.put("userid", Suserid);
		mapparam.put("domaintransfers", domaintransfers);	
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
		mapparam.put("token",map_token.get("token"));
		String domainTransferAgainInfo = HttpReq.sendPost(cloudPath + "domain/domainTransferAgain.json", FcUtil.map2ParamV2Object(mapparam));
		
	    //日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(domainTransferAgainInfo);
			if("success".equals(map_cloud.get("status"))){
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domaintransfer/domainTransferAgain.do", //操作链接
						"重新转入域名", //操作类型
						companyid, //操作者companyid
						1, //操作状态，1 ：成功 0： 失败
						"重新转入域名", //操作详细描述
						"");//
			}else{
				
				AddOprateloUtil.domainResoveLog(ip, //操作ip
						"/domaintransfer/domainTransferAgain.do", //操作链接
						"重新转入域名", //操作类型
						companyid, //操作者companyid
						0, //操作状态，1 ：成功 0： 失败
						"重新转入域名", //操作详细描述
						domainTransferAgainInfo);//
			}	
		} catch (Exception e) {
			log.error("重新转入域名添加日志出错");
		}
	
		return domainTransferAgainInfo;
		
	}
	
	//查询状态
	@RequestMapping(value = "/getdomainstatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getdomainstatus(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		RspData rspData = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String companyid = DomainUtil.getCompanyId(request);
		String daomainName  = maps.get("daomainName");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		DomainTransfer daomaintransfer = new DomainTransfer();
		daomaintransfer.setDomainname(daomainName);
		daomaintransfer.setCompanyid(companyid);
		List<DomainTransfer> daomaintransferList = domainTransferService.select(daomaintransfer);
		Map<String,Object> daomaintransferListMap = new HashMap<String,Object>();
		if(!daomaintransferList.isEmpty()){
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			daomaintransferListMap.put("daomaintransferList", daomaintransferList.get(0));
			rspData.setData(daomaintransferListMap);
		}else{
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg("没有该数据信息");
		}	
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domaintransfer/getdomainstatus.do", //操作链接
					"域名转入查询状态", //操作类型
					companyid, //操作者companyid
					Integer.parseInt(rspData.getStatus()), //操作状态，1 ：成功 0： 失败
					"域名转入查询状态", //操作详细描述
					rspData.getMsg());//	
		} catch (Exception e) {
			log.error("域名转入查询状态添加日志出错");
		}
		
		return JSONUtils.createObjectJson(rspData);	
	}
	//取消转入
	@RequestMapping(value = "/domaintransfercancel", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTransferCancel (HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		RspData rspData = new RspData();
		String companyid = DomainUtil.getCompanyId(request);
		String domainname = maps.get("domainname");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid,domainname)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		DomainTransfer domainTransfer = new DomainTransfer();
		domainTransfer.setCompanyid(companyid);
		domainTransfer.setDomainname(domainname);
		List<DomainTransfer> domainTransfers = domainTransferService.select(domainTransfer);
		//只有状态为 5，6,7,13,14才能取消转入
		//0未支付 1 支付成功（转入处理） 2支付失败 3 转入处理成功（人工审核） 4 转入处理失败（人工审核） 5 转移密码错误 6待发送确认邮件
		//7等待客户确认 8等待查询状态 9转移中 10转入成功 11 转入失败 12 取消转入13 命名审核中 14 实名审核中
		if(!domainTransfers.isEmpty()){
			if(domainTransfers.get(0).getStatus()==5 ||
					domainTransfers.get(0).getStatus()==6 ||
					domainTransfers.get(0).getStatus()==7 || 
					domainTransfers.get(0).getStatus()==13 || 
					domainTransfers.get(0).getStatus()==14 
					){
				String dateStr = TimeUtil.getTime();
				String authStr = DomainUtil.getAuthStr(dateStr);
				String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/cancelzhuanru.aspx", "domainname="+domainname +"&"+authStr);
				try {
					Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
					if("success".equals(map_cloud.get("status"))){
						DomainTransfer domainTransferupdate = new DomainTransfer();
						domainTransferupdate.setId(domainTransfers.get(0).getId());
						domainTransferupdate.setStatus(11);//状态设置为转入失败	
						rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
						rspData.setMsg("您已成功取消转入，约一小时后生效 ");	
					}else{
						rspData.setStatus(ExptNum.FAIL.getCode()+"");
						rspData.setMsg(map_cloud.get("message")+"");	
					}
					
				} catch (Exception e) {
					rspData.setStatus(ExptNum.FAIL.getCode()+"");
					rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
				}		     	
			}else {
				rspData.setStatus(ExptNum.FAIL.getCode()+"");  	
				rspData.setMsg("该域名不在可取消转入状态，无需取消转入");
			}
			
		}else{
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg("没有查询到该转入订单");
		}		
		//日志写入
		try {
			String ip = GetRemoteIp.getIpAddress(request);
			AddOprateloUtil.domainResoveLog(ip, //操作ip
					"/domaintransfer/domaintransfercancel.do", //操作链接
					"域名转入取消转入", //操作类型
					companyid, //操作者companyid
					Integer.parseInt(rspData.getStatus()), //操作状态，1 ：成功 0： 失败
					"域名转入取消转入", //操作详细描述
					rspData.getMsg());//	
		} catch (Exception e) {
			log.error("域名转入取消转入添加日志出错");
		}
		
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	//取消转入测试
	@RequestMapping(value = "/domaintransfercanceltest", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domaintransfercanceltest(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		RspData rspData = new RspData();
		String companyid = DomainUtil.getCompanyId(request);
		String domainname = maps.get("domainname");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid,domainname)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}	
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/cancelzhuanru.aspx", "domainname="+domainname +"&"+authStr);
		try {
			Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
			if("success".equals(map_cloud.get("status"))){			
				rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
				rspData.setMsg(map_cloud+"");

			}else{
				rspData.setStatus(ExptNum.FAIL.getCode()+"");
				rspData.setMsg(map_cloud+"");	
			}
			
		} catch (Exception e) {
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
		}

		return JSONUtils.createObjectJson(rspData);
		
	}
   //转入测试
	@RequestMapping(value = "/domaintransfertest", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domaintransfertest(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		RspData rspData = new RspData();
		String companyid = DomainUtil.getCompanyId(request);
		String domainname = maps.get("domainname");
		String userid = maps.get("userid");
		String authcode = maps.get("authcode");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid,domainname)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}	
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/domainzhuanru.aspx", "domainname="+domainname+"&userid="+userid+"&authcode="+authcode +"&"+authStr);
		try {
			Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
			if("success".equals(map_cloud.get("status"))){
				
				rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
				rspData.setMsg(cloud_reslut);
				
			}else{
				rspData.setStatus(ExptNum.FAIL.getCode()+"");
				rspData.setMsg(cloud_reslut);	
			}
			
		} catch (Exception e) {
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
		}

		return JSONUtils.createObjectJson(rspData);
		
	}

	//查询转入状态测试
	@RequestMapping(value = "/domaintransfercancelselecttest", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domaintransfercancelselecttest(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		Map<String, String> maps = ApiTool.getBodyString(request);
		RspData rspData = new RspData();
		String companyid = DomainUtil.getCompanyId(request);
		String domainname = maps.get("domainname");
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyid,domainname)){
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}	
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/domainzhuanrustate.aspx", "domainname="+domainname +"&"+authStr);
		try {
			Map<String, Object> map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
			if("success".equals(map_cloud.get("status"))){			
				rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
				rspData.setMsg(map_cloud+"");

			}else{
				rspData.setStatus(ExptNum.FAIL.getCode()+"");
				rspData.setMsg(map_cloud+"");	
			}
			
		} catch (Exception e) {
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
		}

		return JSONUtils.createObjectJson(rspData);
		
	}
}
