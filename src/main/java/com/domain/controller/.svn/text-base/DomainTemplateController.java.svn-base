package com.domain.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.DomainTemplate;
import com.domain.pojo.RspData;
import com.domain.service.DomainTemplateService;
import com.domain.util.ExptNum;
import com.domain.util.JSONUtils;

@Controller
@RequestMapping("/domaintemplate")
public class DomainTemplateController {
	@Autowired
	private DomainTemplateService domainTemplateService;
	
	Logger log = Logger.getLogger(getClass());
	
	
	//新建模板
	@RequestMapping(value = "/domaintemplateadd", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTemplateAdd(DomainTemplate domainTemplate ){
		RspData  rspData = new RspData();
		
		try {
			domainTemplateService.insert(domainTemplate);
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg(ExptNum.FAIL.getDesc());
		}
		
		
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	//列出模板
	@RequestMapping(value = "/domaintemplatelist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTemplateList(DomainTemplate domainTemplate ){//传companyId
		RspData  rspData = new RspData();
		
		try {
			domainTemplateService.select(domainTemplate);
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg(ExptNum.FAIL.getDesc());
		}
		
		
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	//删除模板
	@RequestMapping(value = "/domaintemplatedelete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTemplateDelete(DomainTemplate domainTemplate ){//传companyId
		RspData  rspData = new RspData();
		
		try {
			domainTemplateService.delete(domainTemplate);;
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg(ExptNum.FAIL.getDesc());
		}
		
		
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	//修改模板
	@RequestMapping(value = "/domaintemplateupdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainTemplateUpdate(DomainTemplate domainTemplate ){
		RspData  rspData = new RspData();
		
		try {
			domainTemplateService.update(domainTemplate);;
			rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
			rspData.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rspData.setStatus(ExptNum.FAIL.getCode()+"");
			rspData.setMsg(ExptNum.FAIL.getDesc());
		}
		
		
		return JSONUtils.createObjectJson(rspData);
		
	}
	

}
