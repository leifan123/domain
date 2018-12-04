package com.domain.service.impl;

import com.domain.pojo.DomainEmail;
import com.domain.dao.DomainEmailDao;
import com.domain.service.DomainEmailService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainEmailImpl implements DomainEmailService {

	private DomainEmailDao domainEmailDao;

	public void setDomainEmailDao(DomainEmailDao domainEmailDao){
		 this.domainEmailDao = domainEmailDao;
	}

	public void insert(DomainEmail domainemail){
		domainEmailDao.insert(domainemail);
	}

	public List<DomainEmail> select(DomainEmail domainemail){
		return domainEmailDao.select(domainemail);
	}

	public void update(DomainEmail domainemail){
		domainEmailDao.update(domainemail);
	}

	public void delete(DomainEmail domainemail){
		domainEmailDao.delete(domainemail);
	}

	public List<DomainEmail> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainEmailDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainEmailDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainEmailDao.deleteByParam(map);
	}

}

