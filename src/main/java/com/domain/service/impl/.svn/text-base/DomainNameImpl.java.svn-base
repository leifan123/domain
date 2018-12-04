package com.domain.service.impl;

import com.domain.pojo.DomainName;
import com.domain.dao.DomainNameDao;
import com.domain.service.DomainNameService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainNameImpl implements DomainNameService {

	private DomainNameDao domainNameDao;

	public void setDomainNameDao(DomainNameDao domainNameDao){
		 this.domainNameDao = domainNameDao;
	}

	public void insert(DomainName domainname){
		domainNameDao.insert(domainname);
	}

	public List<DomainName> select(DomainName domainname){
		return domainNameDao.select(domainname);
	}

	public void update(DomainName domainname){
		domainNameDao.update(domainname);
	}

	public void delete(DomainName domainname){
		domainNameDao.delete(domainname);
	}

	public List<DomainName> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainNameDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainNameDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainNameDao.deleteByParam(map);
	}

}

