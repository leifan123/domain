package com.domain.service.impl;

import com.domain.pojo.DomainAuthentication;
import com.domain.dao.DomainAuthenticationDao;
import com.domain.service.DomainAuthenticationService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainAuthenticationImpl implements DomainAuthenticationService {

	private DomainAuthenticationDao domainAuthenticationDao;

	public void setDomainAuthenticationDao(DomainAuthenticationDao domainAuthenticationDao){
		 this.domainAuthenticationDao = domainAuthenticationDao;
	}

	public void insert(DomainAuthentication domainauthentication){
		domainAuthenticationDao.insert(domainauthentication);
	}

	public List<DomainAuthentication> select(DomainAuthentication domainauthentication){
		return domainAuthenticationDao.select(domainauthentication);
	}

	public void update(DomainAuthentication domainauthentication){
		domainAuthenticationDao.update(domainauthentication);
	}

	public void delete(DomainAuthentication domainauthentication){
		domainAuthenticationDao.delete(domainauthentication);
	}

	public List<DomainAuthentication> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainAuthenticationDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainAuthenticationDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainAuthenticationDao.deleteByParam(map);
	}

}

