package com.domain.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.pojo.DomainOperatelog;
import com.domain.pojo.DomainTemplate;
import com.domain.pojo.RspData;
import com.domain.service.DomainOperatelogService;
import com.domain.util.ApiTool;
import com.domain.util.Config;
import com.domain.util.DomainUtil;
import com.domain.util.ExptNum;
import com.domain.util.GetResult;
import com.domain.util.JSONUtils;
import com.domain.util.MappingConfigura;
import com.domain.util.ParamIsNull;

@Controller
@RequestMapping(MappingConfigura.OPRATELOG)
public class DomainOpratelogController {
	@Autowired
	private DomainOperatelogService domainOpratelogService;
	Logger log = Logger.getLogger(getClass());
	//新建模板
	@RequestMapping(value = "/domainOprateloglist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String domainOprateloglist(HttpServletRequest request , HttpServletResponse response) throws Exception{
		RspData rspData = new RspData();
		 Map<String, String> maps = ApiTool.getBodyString(request);
		String companyId = DomainUtil.getCompanyId(request);
		 String pageString = maps.get("page");
		 String pagenumString = maps.get("pagenum");
		if(!ParamIsNull.isNull(pageString,pagenumString,companyId)){
			rspData.setStatus(GetResult.ERROR_STATUS + "");
			rspData.setMsg(Config.REQUEST_Param_IS_NULL);
			return JSONUtils.createObjectJson(rspData);
		} 
		 int page = Integer.parseInt(pageString);//第几页
		 int pagenum = Integer.parseInt(pagenumString);//每页数量
		 List<DomainOperatelog> oprateLog = 
				 domainOpratelogService.selectByParam("",
                   "companyId = '"+companyId+"'"+ " ORDER BY id DESC LIMIT "+ page+","+pagenum);
		 int allnum = domainOpratelogService.selectCount("(CASE  WHEN count(*)  IS NULL THEN 0 ELSE count(*) END ) AS sum1",
                 "companyId = '"+companyId+"'");
		 int allpage = 0;//定义总页数1
		 if( allnum % pagenum == 0){
			allpage = allnum / pagenum ;
		 }else{
			allpage = allnum / pagenum + 1 ;
		 }
		 Map<String,Object> pageInfo = new HashMap<String,Object>();
		 pageInfo.put("oprateLog", oprateLog);
		 pageInfo.put("allpage", allpage);
		 pageInfo.put("allnum", allnum);
		 rspData.setData(pageInfo);
		 
		rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		rspData.setData(pageInfo);
		return JSONUtils.createObjectJson(rspData);
	}

}
