package com.domain.service.impl;

import com.domain.pojo.DomainSsl;
import com.domain.dao.DomainSslDao;
import com.domain.service.DomainSslService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainSslImpl implements DomainSslService {

	private DomainSslDao domainSslDao;

	public void setDomainSslDao(DomainSslDao domainSslDao){
		 this.domainSslDao = domainSslDao;
	}

	public void insert(DomainSsl domainssl){
		domainSslDao.insert(domainssl);
	}

	public List<DomainSsl> select(DomainSsl domainssl){
		return domainSslDao.select(domainssl);
	}

	public void update(DomainSsl domainssl){
		domainSslDao.update(domainssl);
	}

	public void delete(DomainSsl domainssl){
		domainSslDao.delete(domainssl);
	}

	public List<DomainSsl> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainSslDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSslDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSslDao.deleteByParam(map);
	}

}

