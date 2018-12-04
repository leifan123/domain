package com.domain.service.impl;

import com.domain.pojo.DomainSyslog;
import com.domain.dao.DomainSyslogDao;
import com.domain.service.DomainSyslogService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainSyslogImpl implements DomainSyslogService {

	private DomainSyslogDao domainSyslogDao;

	public void setDomainSyslogDao(DomainSyslogDao domainSyslogDao){
		 this.domainSyslogDao = domainSyslogDao;
	}

	public void insert(DomainSyslog domainsyslog){
		domainSyslogDao.insert(domainsyslog);
	}

	public List<DomainSyslog> select(DomainSyslog domainsyslog){
		return domainSyslogDao.select(domainsyslog);
	}

	public void update(DomainSyslog domainsyslog){
		domainSyslogDao.update(domainsyslog);
	}

	public void delete(DomainSyslog domainsyslog){
		domainSyslogDao.delete(domainsyslog);
	}

	public List<DomainSyslog> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainSyslogDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSyslogDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSyslogDao.deleteByParam(map);
	}

}

