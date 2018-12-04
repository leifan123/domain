package com.domain.service.impl;

import com.domain.pojo.DomainTemplate;
import com.domain.dao.DomainTemplateDao;
import com.domain.service.DomainTemplateService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainTemplateImpl implements DomainTemplateService {

	private DomainTemplateDao domainTemplateDao;

	public void setDomainTemplateDao(DomainTemplateDao domainTemplateDao){
		 this.domainTemplateDao = domainTemplateDao;
	}

	public void insert(DomainTemplate domaintemplate){
		domainTemplateDao.insert(domaintemplate);
	}

	public List<DomainTemplate> select(DomainTemplate domaintemplate){
		return domainTemplateDao.select(domaintemplate);
	}

	public void update(DomainTemplate domaintemplate){
		domainTemplateDao.update(domaintemplate);
	}

	public void delete(DomainTemplate domaintemplate){
		domainTemplateDao.delete(domaintemplate);
	}

	public List<DomainTemplate> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainTemplateDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainTemplateDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainTemplateDao.deleteByParam(map);
	}

	public void updateByType(DomainTemplate domaintemplate) {
		domainTemplateDao.updateByType(domaintemplate);
	}

	public void updateTemplate(DomainTemplate domaintemplate) {
		domainTemplateDao.updateTemplate(domaintemplate);
	}

}

