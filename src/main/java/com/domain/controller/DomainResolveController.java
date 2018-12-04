package com.domain.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.domain.pojo.DomainName;
import com.domain.pojo.DomainOperatelog;
import com.domain.pojo.DomainResolveList;
import com.domain.pojo.RspData;
import com.domain.service.DomainNameService;
import com.domain.service.DomainOperatelogService;
import com.domain.service.DomainResolveListService;
import com.domain.util.ApiTool;
import com.domain.util.Config;
import com.domain.util.DomainUtil;
import com.domain.util.ExcelUtil;
import com.domain.util.ExptNum;
import com.domain.util.FcUtil;
import com.domain.util.GetRemoteIp;
import com.domain.util.GetResult;
import com.domain.util.HttpReq;
import com.domain.util.JSONUtils;
import com.domain.util.ParamIsNull;
import com.domain.util.TimeUtil;


@Controller
@RequestMapping("/domainresolve")
public class DomainResolveController {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式

	@Autowired
	private DomainResolveListService domainResolveListService;

	@Autowired
	private DomainNameService domainNameService;
	
	@Autowired
	private DomainOperatelogService domainOperatelogService;
	


	Logger log = Logger.getLogger(getClass());


	
	// 列出域名解析列表
	@RequestMapping(value = "/domainresolvelist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainNameList(HttpServletRequest request ,HttpServletResponse response) throws Exception {
		//domainName.get
		RspData rspData = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String doMainName = maps.get("domainName");
		String companyId = DomainUtil.getCompanyId(request);
		log.error("参数："+doMainName+companyId);
		// 参数完整性判断
		if (!ParamIsNull.isNull(doMainName,companyId)) {
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}	
		DomainResolveList domainResolveList = new DomainResolveList();
		domainResolveList.setCompanyid(companyId);
		domainResolveList.setDomainName(doMainName);
			try {
				List<DomainResolveList> domainResolveLists = domainResolveListService.select(domainResolveList);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("domainresolvelistList", domainResolveLists);
				rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
				rspData.setMsg(ExptNum.SUCCESS.getDesc());
				rspData.setData(data);
			} catch (Exception e) {
				log.error(e);
				rspData.setStatus(ExptNum.SYSTEM_BUSY.getCode() + "");
				rspData.setMsg(ExptNum.SYSTEM_BUSY.getDesc());
			}
		return JSONUtils.createObjectJson(rspData);

	}
	
	// 列出解析列表
	@RequestMapping(value = "/domainNameList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainResolveList(HttpServletRequest request ,HttpServletResponse response) throws Exception {
		//domainName.get
		RspData rspData = new RspData();
		//Map<String, String> maps = ApiTool.getBodyString(request);
		//String doMainName = maps.get("domainName");
		String companyId = DomainUtil.getCompanyId(request);
		//log.error("参数："+doMainName+companyId);
		// 参数完整性判断
		if (!ParamIsNull.isNull(companyId)) {
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		
		DomainName domainName = new DomainName();
		domainName.setCompanyid(companyId);
		//domainName.setDomainname(doMainName);
			try {
				List<DomainName> domainNameList = domainNameService.select(domainName);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("domainNameList", domainNameList);
				rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
				rspData.setMsg(ExptNum.SUCCESS.getDesc());
				rspData.setData(data);
			} catch (Exception e) {
				log.error(e);
				rspData.setStatus(ExptNum.SYSTEM_BUSY.getCode() + "");
				rspData.setMsg(ExptNum.SYSTEM_BUSY.getDesc());
			}
		

		return JSONUtils.createObjectJson(rspData);

	}
	//修改
	@RequestMapping(value = "/domainNameupdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainResolveupdate(HttpServletRequest request ,HttpServletResponse response) throws Exception {
		//domainName.get
		RspData rspData = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainname = maps.get("domainname");
		String rec_id = maps.get("rec_id");
		String rec_value = maps.get("rec_value");
		String rec_ttl = maps.get("rec_ttl");
		String id = maps.get("id");
		DomainOperatelog  domainOperatelog = new DomainOperatelog();
		String companyId = DomainUtil.getCompanyId(request);
		//log.error("参数："+doMainName+companyId);
		// 参数完整性判断
		if (!ParamIsNull.isNull(domainname,id,rec_ttl,rec_value,rec_id,companyId)) {
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		
		DomainResolveList domainResolveList = new DomainResolveList();
		domainResolveList.setDomainName(domainname);
		domainResolveList.setRecValue(rec_value);
		domainResolveList.setRecTtl(Integer.parseInt(rec_ttl));
		domainResolveList.setId(Integer.parseInt(id));
		domainResolveList.setResolveCode(1);
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		Map<String, String> map = new HashMap<String, String>();
		map.put("domainname", domainname);
		map.put("rec_id", rec_id);
		map.put("rec_value", rec_value);
		map.put("rec_ttl", rec_ttl);
		Map<String, Object> map_cloud = new HashMap<String, Object>();
		String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/ModifyRec.aspx", FcUtil.map2Param(map)+"&"+authStr);
			try {
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				
				if(map_cloud.get("status").equals("success")){
					List<DomainResolveList> doname = domainResolveListService.select(new DomainResolveList().setId(Integer.parseInt(id)));
					
					//日志写入
					 String ip = GetRemoteIp.getIpAddress(request);
					domainOperatelog.setCompanyid(companyId);

					Map<String, String> log = new HashMap<String,String>();
					log.put("domainname", domainname);
					log.put(doname.get(0).getRecValue()+"改为：", rec_value);
					log.put(doname.get(0).getRecTtl()+"改为：", rec_ttl);
					domainOperatelog.setOperatedes(log.toString());
					if("success".equals(map_cloud.get("status"))){
						domainOperatelog.setOperatestatus(1);
					}else{
						domainOperatelog.setOperatestatus(0);
						domainOperatelog.setErrormessage(cloud_reslut);
					}
					domainOperatelog.setOperatetarget("/domainresolve/domainNameupdate.do");
					domainOperatelog.setOperatetype("修改解析");
					domainOperatelog.setOperatorip(ip);
					//domainOperatelog.setOperator(operator);
					Date date1 = new Date();
					//String str = dateFormat.format(date1);
					domainOperatelog.setOperatortime(date1);
					domainOperatelogService.insert(domainOperatelog);
					
					domainResolveListService.update(domainResolveList);
					
					rspData.setStatus(ExptNum.SUCCESS.getCode() + "");
					rspData.setMsg(ExptNum.SUCCESS.getDesc());
					rspData.setData(map_cloud);
					
				}else{
					domainResolveList.setRecId(rec_id);
					//Map<String, Object> map_cloud = daomainAdd(domainResolveList, rspData);
					//http://api.cndns.com/domains/ModifyRec.aspx?use&otime=2012921&checksum=e2eadc4254f19ea01net&recid=10&recvalue=7.7.7.7&recttl=288
					rspData.setStatus(ExptNum.FAIL.getCode() + "");
					rspData.setMsg("请确认解析状态是开启状态");
					rspData.setData(map_cloud);
				}

			} catch (Exception e) {
				log.error(e);
				rspData.setStatus(ExptNum.SYSTEM_BUSY.getCode() + "");
				rspData.setMsg(ExptNum.SYSTEM_BUSY.getDesc());
			}
			

			
			
		return JSONUtils.createObjectJson(rspData);

	}
	// 新增解析（开启）
	@RequestMapping(value = "/domainresolveadd", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainResolveAddlog(HttpServletRequest request ,HttpServletResponse response) throws Exception {	
		RspData rspData = new RspData();
		
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainname = maps.get("domainname");
		String rec_item = maps.get("rec_item");
		String rec_type = maps.get("rec_type");
		String rec_level = maps.get("rec_level");
		String rec_value = maps.get("rec_value");
		String rec_ttl = maps.get("rec_ttl");
		String companyId = DomainUtil.getCompanyId(request);
		String userId = maps.get("userid");
		String resolveCode = maps.get("resolvecode");
		String id = maps.get("id");	
		
		
		//参数完整性判断		
		if (!ParamIsNull.isNull(domainname, rec_item, rec_type,rec_value, rec_ttl,companyId,userId)) {
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		if(rec_type.equals("MX")){
			if(!ParamIsNull.isNull(rec_level)){
				rspData.setStatus(GetResult.ERROR_STATUS + "");
				rspData.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rspData);
			}
		}
		
		//检测是否实名认证
		DomainName domainNames = new DomainName();
		domainNames.setCompanyid(companyId);
		domainNames.setDomainname(domainname);
		List<DomainName> domainNameCheck = domainNameService.select(domainNames);
		if(domainNameCheck.isEmpty()){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.NOTHISDOMAINNAME);
			return JSONUtils.createObjectJson(rspData);
		}else if(domainNameCheck.get(0).getIsAutonym()== null || domainNameCheck.get(0).getIsAutonym()!=1){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.NOAUTOYM);
			return JSONUtils.createObjectJson(rspData);
		}
		
	    DomainResolveList domainresolvelist = new DomainResolveList();
	    domainresolvelist.setDomainName(domainname);
	    domainresolvelist.setRecItem(rec_item);
	    domainresolvelist.setRecType(rec_type);
	    domainresolvelist.setRecLevel(Integer.parseInt(rec_level));
	    domainresolvelist.setRecValue(rec_value);
	    domainresolvelist.setRecTtl(Integer.parseInt(rec_ttl));
	    domainresolvelist.setCompanyid(companyId);
	    domainresolvelist.setUserId(userId);

	    if(resolveCode!=null && !("").equals(resolveCode)){
	    	domainresolvelist.setResolveCode(Integer.parseInt(resolveCode));
	    }
	    if(id!=null && !("").equals(id)){
	    	domainresolvelist.setId(Integer.parseInt(id));
	    }
		Map<String, Object> map_cloud = daomainAdd(request,domainresolvelist, rspData);

		rspData.setData(map_cloud);
/*		//日志添加
		Date date1 = new Date();
		domainResoveLog("/domainresolve/domainNameupdate.do","新增解析",companyId, domainresolvelist, map_cloud, date1);*/
		return JSONUtils.createObjectJson(rspData);

	}
    //日志添加
	public void domainResoveLog(String ip ,String operatetarget,String operatetype,String companyId, DomainResolveList domainresolvelist, Map<String, Object> map_cloud,
			Date date1) {
		DomainOperatelog  domainOperatelog = new DomainOperatelog();
		domainOperatelog.setCompanyid(companyId);
		domainOperatelog.setOperatedes(domainresolvelist.toString());
		if("success".equals(map_cloud.get("status"))){
			domainOperatelog.setOperatestatus(1);
		}else{
			domainOperatelog.setOperatestatus(0);
			domainOperatelog.setErrormessage(map_cloud.get("message")+"");
		}
		domainOperatelog.setOperatetarget(operatetarget);
		domainOperatelog.setOperatetype(operatetype);
		//domainOperatelog.setOperator(operator);
		
		//String str = dateFormat.format(date1);
		domainOperatelog.setOperatortime(date1);
		domainOperatelog.setOperatorip(ip);
		domainOperatelogService.insert(domainOperatelog);
	}
	// 删除解析（暂停）
	@RequestMapping(value = "/domainresolvedelete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainResolveDelete(HttpServletRequest request ,HttpServletResponse response) throws Exception {
		RspData rspData = new RspData();
		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainname = maps.get("domainname");
		String rec_id = maps.get("rec_id");
		String resolve_code = maps.get("resolve_code");
		
		String companyId = DomainUtil.getCompanyId(request);
		System.err.println(" 删除解析（暂停）"+domainname+rec_id);
		if(!ParamIsNull.isNull(domainname,rec_id)){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		
		DomainResolveList domainresolvelist = new DomainResolveList();
		domainresolvelist.setDomainName(domainname);
		
		domainresolvelist.setRecId(rec_id);
		if(resolve_code!=null ){
			domainresolvelist.setResolveCode(Integer.parseInt(resolve_code));
		}
		
		
		Map<String, Object> map_cloud = daomainDelete(request,domainresolvelist, rspData);

		rspData.setData(map_cloud);
		return JSONUtils.createObjectJson(rspData);

	}

	  //批量删除（暂停）	  
	 @RequestMapping(value = "/domainresolvebatchesdelete", method = RequestMethod.POST,produces = "application/json; charset=utf-8") 
	 @ResponseBody 
	 public String domainResolvebatchesdelete(HttpServletRequest request, HttpServletResponse response ) throws Exception { 
		  RspData rspData = new RspData();
		  System.err.println("进入");
		  Map<String, String> maps = ApiTool.getBodyString(request);
		  String resolve_code = maps.get("resolve_code");
		  String idss = maps.get("ids");
		  String [] ids = idss.replaceAll("", "").split(",");

			// 参数完整性判断
			if (ids==null) {
				rspData.setStatus(GetResult.ERROR_STATUS + "");
				rspData.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rspData);
			}
		Map<String,Object> resolveinfo = new LinkedHashMap<String,Object>();
		 Map<String, Object> map_cloud = null;
		 for(Integer i = 0;i < ids.length;i++){
			 List<DomainResolveList> domainresolvelists =  domainResolveListService.selectByParam("", "id="+Integer.parseInt(ids[i]));
			 if(domainresolvelists.isEmpty()){
				map_cloud.put("status", "failed");
				map_cloud.put("message", "该数据不存在");
				
			 }else{
				 if(!(resolve_code == null || resolve_code.equals("")) && resolve_code.equals("0")){
					 domainresolvelists.get(0).setResolveCode(0);
				 }else{
					 domainresolvelists.get(0).setResolveCode(null);
				 }
				 map_cloud = daomainDelete(request,domainresolvelists.get(0), rspData);
				  resolveinfo.put("info"+i, map_cloud);


				  
			 } 			  
		 }
		 rspData.setData(resolveinfo);
		 return JSONUtils.createObjectJson(rspData);
		 
	  
	  }
	
	 //批量开启
	 @RequestMapping(value = "/domainResolvebatchesadd", method = RequestMethod.POST,produces = "application/json; charset=utf-8") 
	 @ResponseBody 
	 public String domainResolvebatchesadd(HttpServletRequest request, HttpServletResponse response ) throws Exception { 
		  RspData rspData = new RspData();
		  Map<String, String> maps = ApiTool.getBodyString(request);
		  //String resolve_code = maps.get("resolve_code");
		  String idss = maps.get("ids");
		  
			// 参数完整性判断
			if (idss==null) {
				rspData.setStatus(GetResult.ERROR_STATUS + "");
				rspData.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rspData);
			}
			String [] ids = idss.split(",");
			Map<String,Object> resolveinfo = new LinkedHashMap<String,Object>();
			Map<String, Object> map_cloud = null;
			for(int i = 0 ;i<ids.length;i++){	
				 List<DomainResolveList> domainresolvelists =  domainResolveListService.selectByParam("", "id="+Integer.parseInt(ids[i]));
				 if(domainresolvelists.isEmpty()){
					map_cloud.put("status", "failed");
					map_cloud.put("message", "请输入id");
					resolveinfo.put("info"+i, map_cloud);
					 //rspData.setMsg("该条数据不存在");
				 }else{				 
					  domainresolvelists.get(0).setResolveCode(1);
					  map_cloud = daomainAdd(request,domainresolvelists.get(0), rspData); 

					  resolveinfo.put("info"+i,map_cloud);	  
					  
					  //rspData.setData(map_cloud);
				 }
			}
			rspData.setData(resolveinfo);
			return JSONUtils.createObjectJson(rspData);
	 }	
	 
	// 导出
	@RequestMapping(value = "/exporttoexcel", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String exportToExcel(HttpServletRequest request, HttpServletResponse response,String domainName) throws Exception {// 传companyId
		RspData rspData = new RspData();
		/*Map<String, String> maps = ApiTool.getBodyString(request);
		System.err.println("maps:"+maps.toString());
		String domainName = maps.get("domainName");*/
		String companyId = DomainUtil.getCompanyId(request);
		if(!ParamIsNull.isNull(domainName)){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		
		DomainResolveList domainresolvelist = new DomainResolveList();
		domainresolvelist.setDomainName(domainName);
		domainresolvelist.setCompanyid(companyId);
		//System.out.println(domainresolvelist.getDomainName());
		List<DomainResolveList> domainresolvelists = domainResolveListService.select(domainresolvelist);
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet(domainresolvelists.get(0).getDomainName());
		// 创建表头
		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("记录类型");
		row0.createCell(1).setCellValue("主机记录");
		row0.createCell(2).setCellValue("记录值");
		row0.createCell(3).setCellValue("TTL");
		row0.createCell(4).setCellValue("MX优先级别");
		// HSSFRow row1 = sheet.createRow(1);

		for (int i = 0; i < domainresolvelists.size(); i++) {
			HSSFRow rowName = sheet.createRow(i + 1);
			rowName.createCell(0).setCellValue(domainresolvelists.get(i).getRecType());
			rowName.createCell(1).setCellValue(domainresolvelists.get(i).getRecItem());
			rowName.createCell(2).setCellValue(domainresolvelists.get(i).getRecValue());
			rowName.createCell(3).setCellValue(domainresolvelists.get(i).getRecTtl());
			rowName.createCell(4).setCellValue(domainresolvelists.get(i).getRecLevel());
		}

		// 输出Excel文件
		OutputStream output = null;
		try {
			output = response.getOutputStream();
/*			 response.reset();
			 response.addHeader("Access-Control-Allow-Origin", "*");
	          
	         response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
	         response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");*/
	         //HttpContext.Current.Response.AddHeader("Access-Control-Max-Age", "1728000");
			response.setHeader("Content-disposition",
					"attachment; filename=" + domainresolvelist.getDomainName() + ".xls");
			response.setContentType("application/msexcel");
			wb.write(output);
			output.close();
			wb.close();
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (IOException e){
			output.close();
			wb.close();
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg(ExptNum.FAIL.getDesc());

		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String,Object> map_cloud = new HashMap<String,Object>();
		map_cloud.put("status", "success");
		Date date1 = new Date();
		
		 String ip = GetRemoteIp.getIpAddress(request);//获取ip
		domainResoveLog(ip,"/domainresolve/exporttoexcel.do","导出解析",companyId,domainresolvelist, map_cloud, date1);
		return JSONUtils.createObjectJson(rspData);

	}

	// 导入
	@RequestMapping(value = "/importtoexcel", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String importToExcel(HttpServletRequest request, HttpServletResponse response,MultipartFile file,String domainName ) throws Exception {
		System.out.println("表格解析");
		RspData rspData = new RspData();
		 String companyId = DomainUtil.getCompanyId(request);
		
/*		Map<String, String> maps = ApiTool.getBodyString(request);
		String domainName = maps.get("domainName");
		String companyId = maps.get("companyId");*/
		
		if(!ParamIsNull.isNull(domainName,companyId)){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		}
		
		//检测是否实名认证
		DomainName domainNames = new DomainName();
		domainNames.setCompanyid(companyId);
		domainNames.setDomainname(domainName);
		List<DomainName> domainNameCheck = domainNameService.select(domainNames);
		if(domainNameCheck.isEmpty()){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.NOTHISDOMAINNAME);
			return JSONUtils.createObjectJson(rspData);
		}else if(domainNameCheck.get(0).getIsAutonym()== null || domainNameCheck.get(0).getIsAutonym()!=1){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.NOAUTOYM);
			return JSONUtils.createObjectJson(rspData);
		}
		
		
		POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet hssfSheet = wb.getSheetAt(0); // 获取第一个Sheet页
		Map<String,Object> errorMsg = new HashMap<String,Object>(); 

		if (hssfSheet != null) {
			
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
/*				if (hssfRow == null) {
					errorMsg.put("errorInfo","该解析已存在");
					continue;
				}*/
				for(int i = 0;i<4;i++){
					System.err.println(ExcelUtil.formatCell(hssfRow.getCell(i)));
				}
				
				
				DomainResolveList domainresolvelist  = new DomainResolveList();
				domainresolvelist.setRecType(ExcelUtil.formatCell(hssfRow.getCell(0)));
				domainresolvelist.setRecItem(ExcelUtil.formatCell(hssfRow.getCell(1)));
				domainresolvelist.setRecValue(ExcelUtil.formatCell(hssfRow.getCell(2)));	
				try {
					domainresolvelist.setRecTtl(Integer.parseInt(ExcelUtil.formatCell(hssfRow.getCell(3)).substring(0,
							ExcelUtil.formatCell(hssfRow.getCell(3)).indexOf("."))));
					domainresolvelist.setRecLevel(Integer.parseInt(ExcelUtil.formatCell(hssfRow.getCell(4)).substring(0,
							ExcelUtil.formatCell(hssfRow.getCell(4)).indexOf("."))));
				} catch (Exception e) {
					errorMsg.put("errorInfo"+rowNum, domainresolvelist.toString()+"请检查参数RecTtl、RecLevel是否正确");
					continue;
				}
				domainresolvelist.setResolveCode(0);
				domainresolvelist.setCompanyid(companyId);
				domainresolvelist.setDomainName(domainName);
				
				DomainResolveList domainresolvelistrepy = new DomainResolveList();
				domainresolvelistrepy.setCompanyid(domainresolvelist.getCompanyid());
				domainresolvelistrepy.setDomainName(domainresolvelist.getDomainName());
				domainresolvelistrepy.setRecItem(domainresolvelist.getRecItem());
				if(!domainResolveListService.select(domainresolvelistrepy).isEmpty()){
					errorMsg.put("errorInfo"+rowNum, domainresolvelist.toString()+"该解析已存在");
					continue;
				}
				Map<String, Object> map_cloud = daomainAdd(request,domainresolvelist, rspData);
				
				if(!"success".equals(map_cloud.get("status"))){
					//errorMsg.put(domainresolvelist.toString(), map_cloud);
					errorMsg.put("errorInfo"+rowNum,domainresolvelist.toString()+":"+map_cloud);
					//返回失败信息
				}else{
					errorMsg.put("errorInfo"+rowNum, "导入成功");
				}
				rspData.setData(map_cloud);
				System.out.println("获取的数据："+domainresolvelist.toString());
				
			}
		}
		wb.close();
		
		//domainResoveLog("/domainresolve/domainresolveadd.do","导入解析",domainresolvelist.getCompanyid(),domainresolvelist, map_cloud, date1);
		rspData.setData(errorMsg);
		return JSONUtils.createObjectJson(rspData);

	}

	// 删除方法体
	public Map<String, Object> daomainDelete(HttpServletRequest request,DomainResolveList domainresolvelist, RspData rspData) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("domainname", domainresolvelist.getDomainName());
		map.put("rec_id", domainresolvelist.getRecId());
		System.err.println(domainresolvelist.getDomainName());
		String ip = GetRemoteIp.getIpAddress(request);//获取ip
		

		Map<String, Object> map_cloud = new HashMap<String, Object>();
		// 转化成map失败则返回失败
		System.out.println("code : "+domainresolvelist.getResolveCode());
		if (domainresolvelist.getResolveCode() != null && domainresolvelist.getResolveCode() == 0) {
			System.out.println("暂停");
			String dateStr = TimeUtil.getTime();
			String authStr = DomainUtil.getAuthStr(dateStr);
			String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/DeleteRec.aspx", FcUtil.map2Param(map)+"&"+authStr);
			try {		
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				if(map_cloud.get("status").equals("success")){
					DomainResolveList domainresolvelistupdate = new DomainResolveList();
					domainresolvelistupdate.setDomainName(domainresolvelist.getDomainName());
					domainresolvelistupdate.setRecId(domainresolvelist.getRecId());
					int id = domainResolveListService.select(domainresolvelistupdate).get(0).getId();
					domainresolvelist.setId(id);
					domainResolveListService.update(domainresolvelist);
					map_cloud.put("status", "success");
					map_cloud.put("message", "暂停解析成功");
					/*rspData.setStatus(GetResult.SUCCESS_STATUS+"");
					rspData.setMsg(Config.REQUEST_SUCCESS);*/
				}else if(map_cloud.get("message").toString().contains("11318")){
					map_cloud.put("status", "failed");
					map_cloud.put("message", "暂停解析失败，请确保该解析信息是开启状态");
				}	
			} catch (Exception e) {
				log.error(e);
				rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
			}
		    Date date1 = new Date();
		    
		    if(!domainResolveListService.select(domainresolvelist).isEmpty()){
		    	domainResoveLog(ip,"/domainresolve/domainresolvedelete.do","暂停解析",domainResolveListService.select(domainresolvelist).get(0).getCompanyid(), domainResolveListService.select(domainresolvelist).get(0), map_cloud, date1);
		    }
		} else {
			System.out.println("删除");
			
			domainresolvelist.setResolveCode(null);
			List<DomainResolveList> dls = domainResolveListService.select(domainresolvelist);
			
			if(!dls.isEmpty() && dls.get(0).getResolveCode()==0){
				log.debug("dls："+dls.size());
				domainResolveListService.delete(domainresolvelist);

				map_cloud.put("status", "success");
				map_cloud.put("message", "删除解析成功");
				//日志写入
			    Date date1 = new Date();
			    if(!domainResolveListService.select(domainresolvelist).isEmpty()){
			    	domainResoveLog(ip,"/domainresolve/domainresolvedelete.do","删除解析",domainResolveListService.select(domainresolvelist).get(0).getCompanyid(), domainResolveListService.select(domainresolvelist).get(0), map_cloud, date1);
			    }
			}else{
				String dateStr = TimeUtil.getTime();
				String authStr = DomainUtil.getAuthStr(dateStr);
				String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/DeleteRec.aspx", FcUtil.map2Param(map)+"&"+authStr);
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				if(map_cloud.get("status").equals("success")){
					domainResolveListService.delete(domainresolvelist);
					DomainName domainName = new DomainName();
					domainName.setDomainname(domainresolvelist.getDomainName());
					domainName.setCompanyid(domainresolvelist.getCompanyid());
					List<DomainName> domainNamelist = domainNameService.select(domainName);
					if(domainNamelist.isEmpty()){
						domainNameService.updateByParam("isResolveList=0 where id ="+domainNamelist.get(0).getId());
					}
					map_cloud.put("status", "success");
					map_cloud.put("message", "删除解析成功");

				}else{
					if(map_cloud.get("message").toString().contains("11318")){
						domainResolveListService.delete(domainresolvelist);
						map_cloud.put("status", "success");
						map_cloud.put("message", "删除成功");
					}/*else{
						rspData.setData(map_cloud);
					}	*/
					
				}
				
				//日志写入
			    Date date1 = new Date();
			    if(!domainResolveListService.select(domainresolvelist).isEmpty()){
			    	domainResoveLog(ip,"/domainresolve/domainresolvedelete.do","删除解析",domainResolveListService.select(domainresolvelist).get(0).getCompanyid(), domainResolveListService.select(domainresolvelist).get(0), map_cloud, date1);
			    }
			}
				
		}
		
		
		
		return map_cloud;
	}

	// 新增方法体
	public Map<String, Object> daomainAdd(HttpServletRequest request,DomainResolveList domainresolvelist, RspData rspData) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("domainname", domainresolvelist.getDomainName());
		map.put("rec_item", domainresolvelist.getRecItem());
		map.put("rec_type", domainresolvelist.getRecType());
		map.put("rec_level", domainresolvelist.getRecLevel() + "");
		map.put("rec_value", domainresolvelist.getRecValue());
		map.put("rec_ttl", domainresolvelist.getRecTtl() + "");
		String ip = GetRemoteIp.getIpAddress(request);//获取ip
		
		Map<String, Object> map_cloud = new HashMap<String, Object>();
		String cloud_reslut = null;
		// 转化成map失败则返回失败
		String dateStr = TimeUtil.getTime();
		String authStr = DomainUtil.getAuthStr(dateStr);
		if (domainresolvelist.getResolveCode() != null && domainresolvelist.getResolveCode() == 1) {
			if(domainresolvelist.getId()==null || domainresolvelist.getId().equals("")){
				rspData.setStatus(ExptNum.FAIL.getCode()+"");
				rspData.setMsg("请输入id");
				map_cloud.put("status", "failed");
				map_cloud.put("message", "请输入id");
				
				return map_cloud;
			}
			cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/AddRec.aspx", FcUtil.map2Param(map)+"&"+authStr);
			try {
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				if(map_cloud.get("status").equals("success")){
					domainresolvelist.setRecId(map_cloud.get("message") + "");
					
					domainResolveListService.update(domainresolvelist);
	/*				rspData.setStatus(GetResult.SUCCESS_STATUS+"");
					rspData.setMsg(Config.REQUEST_SUCCESS);*/
					map_cloud.put("status", "success");
					map_cloud.put("message", "开启解析成功");
					//rspData.setData(map_cloud);
				}else if(map_cloud.get("message").toString().contains("11319")){
					map_cloud.put("status", "failed");
					map_cloud.put("message", "开启解析失败，请确保该解析信息是暂停状态");
				}	
			} catch (Exception e) {
				rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
			}
			//日志写入
		    Date date1 = new Date();
		    
		    domainResoveLog(ip,"/domainresolve/domainresolveadd.do","开启解析",domainresolvelist.getCompanyid(),domainresolvelist, map_cloud, date1);
		    

		} else {
			System.err.println(domainresolvelist.toString());
			DomainResolveList domainresolvelistrepy = new DomainResolveList();
			domainresolvelistrepy.setCompanyid(domainresolvelist.getCompanyid());
			domainresolvelistrepy.setDomainName(domainresolvelist.getDomainName());
			domainresolvelistrepy.setRecItem(domainresolvelist.getRecItem());
			if(!domainResolveListService.select(domainresolvelistrepy).isEmpty()){
				System.err.println("信息已存在");
				map_cloud.put("status", "failed");
				map_cloud.put("message", "信息已存在");
				/*rspData.setStatus(ExptNum.RESOLVE_INFO_EXIT.getCode()+"");
				rspData.setMsg(ExptNum.RESOLVE_INFO_EXIT.getDesc());*/
			}else{
				cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/AddRec.aspx", FcUtil.map2Param(map)+"&"+authStr);
				try {
					map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);				
					if(map_cloud.get("status").equals("success")){
						domainresolvelist.setRecId(map_cloud.get("message") + "");
						domainresolvelist.setResolveCode(1);
						domainResolveListService.insert(domainresolvelist);
						DomainName domainName = new DomainName();
						domainName.setDomainname(domainresolvelist.getDomainName());
						domainName.setCompanyid(domainresolvelist.getCompanyid());
						List<DomainName> domainNamelist = domainNameService.select(domainName);
						domainNameService.updateByParam("isResolveList=1 where id ="+domainNamelist.get(0).getId());		
/*						rspData.setStatus(GetResult.SUCCESS_STATUS+"");
						rspData.setMsg(Config.REQUEST_SUCCESS);*/
						map_cloud.put("status", "success");
						map_cloud.put("message", "新增解析成功");
						
					}
				} catch (Exception e) {
					map_cloud.put("status", "failed");
					map_cloud.put("message", "失败，请联系客服");
				}
			}
			
			Date date1 = new Date();
			domainResoveLog(ip,"/domainresolve/domainresolveadd.do","新增解析",domainresolvelist.getCompanyid(),domainresolvelist, map_cloud, date1);
		}
			
		return map_cloud;
	}
	//搜素解析
	 @RequestMapping(value = "/domainResolveselect", method = RequestMethod.POST,produces = "application/json; charset=utf-8") 
	 @ResponseBody 
	 public String domainResolveselect(HttpServletRequest request, HttpServletResponse response ) throws Exception { 
		 Map<String, String> maps = ApiTool.getBodyString(request);
		 String domainName = maps.get("domainName");
		  RspData rspData = new RspData();
		  Map<String, String> map = new HashMap<String, String>();
		  map.put("domainname", domainName);
		  if(!ParamIsNull.isNull(domainName)){
				rspData.setStatus(GetResult.ERROR_STATUS + "");
				rspData.setMsg(Config.REQUEST_Param_IS_NULL);
				return JSONUtils.createObjectJson(rspData);
		  }
		  
		  String dateStr = TimeUtil.getTime();
		  String authStr = DomainUtil.getAuthStr(dateStr);
		  String cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/DomainDetail.aspx", FcUtil.map2Param(map)+"&"+authStr);
		  System.err.println( FcUtil.map2Param(map)+"&"+authStr);
		  Map<String, Object> map_cloud = new HashMap<String, Object>();
		  try {
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				if(map_cloud.get("status").equals("success")){
					rspData.setStatus(GetResult.SUCCESS_STATUS+"");
					rspData.setMsg(Config.REQUEST_SUCCESS);
					rspData.setData(map_cloud);
					
				}else{
					rspData.setData(map_cloud);
				}					
			} catch (Exception e) {
				log.error(e);
				rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
			}
		  return JSONUtils.createObjectJson(rspData);
	}
	 
	 //解析日志
	 @RequestMapping(value = "/domainResolvelog", method = RequestMethod.POST,produces = "application/json; charset=utf-8") 
	 @ResponseBody 
	 public String domainResolvelog(HttpServletRequest request, HttpServletResponse response ) throws Exception { 
		 Map<String, String> maps = ApiTool.getBodyString(request);
		 String domainName = maps.get("domainName");
		 String companyId = DomainUtil.getCompanyId(request);
		 RspData rspData = new RspData();
		 String pageString = maps.get("page");
		 String pagenumString = maps.get("pagenum");
		if(!ParamIsNull.isNull(domainName,pageString,pagenumString,companyId)){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		} 
		 int page = Integer.parseInt(pageString);//第几页
		 int pagenum = Integer.parseInt(pagenumString);//每页数量
 
		 List<DomainOperatelog> domainoperatelogs = 
				 domainOperatelogService.selectByParam("",
                   "companyId = '"+companyId+"'"+ " AND operateDes LIKE '%" +domainName+"%'"+ " ORDER BY id DESC LIMIT "+ page+","+pagenum);
		 int allnum = domainOperatelogService.selectCount("(CASE  WHEN count(*)  IS NULL THEN 0 ELSE count(*) END ) AS sum1",
                 "companyId = '"+companyId+"'"+ " AND operateDes LIKE '%" +domainName+"%'");
		 int allpage = 0;//定义总页数
		 if( allnum % pagenum == 0){
			allpage = allnum / pagenum ;
		 }else{
			allpage = allnum / pagenum + 1 ;
		 }
		 Map<String,Object> pageInfo = new HashMap<String,Object>();
		 pageInfo.put("domainoperatelogs", domainoperatelogs);
		 pageInfo.put("allpage", allpage);
		 pageInfo.put("allnum", allnum);
		 rspData.setData(pageInfo);
		 rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
		 rspData.setMsg(ExptNum.SUCCESS.getDesc());
		 return JSONUtils.createObjectJson(rspData);
		 
	 }

	// 列出域名解析列表
		@RequestMapping(value = "/domainresolvelistMC", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
		@ResponseBody
		public String domainNameListMC(HttpServletRequest request ,HttpServletResponse response) throws Exception {
			//domainName.get
			RspData rspData = new RspData();
			Map<String, String> maps = ApiTool.getBodyString(request);
			String doMainName = maps.get("domainName");
			String companyId = DomainUtil.getCompanyId(request);
			log.error("参数："+doMainName+companyId);
			///domains/RecList.aspx
			Map<String, Object> map_cloud = new HashMap<String, Object>();
			String cloud_reslut = null;
			String dateStr = TimeUtil.getTime();
			String authStr = DomainUtil.getAuthStr(dateStr);
			Map<String, String> map = new HashMap<String, String>();
			map.put("domainname", doMainName);
			cloud_reslut = HttpReq.sendGet("http://api.cndns.com/domains/RecList.aspx", FcUtil.map2Param(map)+"&"+authStr);
			try {
				map_cloud = JSONUtils.getMapObjectByJson(cloud_reslut);
				
			} catch (Exception e) {
				rspData.setMsg("云平台返回数据格式化错误:" + cloud_reslut);
			}
			System.err.println("map_cloud.toString():"+map_cloud.toString());
			return null;

			


		}
}
