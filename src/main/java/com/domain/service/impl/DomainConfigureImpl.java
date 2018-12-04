package com.domain.service.impl;

import com.domain.pojo.DomainConfigure;
import com.domain.dao.DomainConfigureDao;
import com.domain.service.DomainConfigureService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainConfigureImpl implements DomainConfigureService {

	private DomainConfigureDao domainConfigureDao;

	public void setDomainConfigureDao(DomainConfigureDao domainConfigureDao){
		 this.domainConfigureDao = domainConfigureDao;
	}

	public void insert(DomainConfigure domainconfigure){
		domainConfigureDao.insert(domainconfigure);
	}

	public List<DomainConfigure> select(DomainConfigure domainconfigure){
		return domainConfigureDao.select(domainconfigure);
	}

	public void update(DomainConfigure domainconfigure){
		domainConfigureDao.update(domainconfigure);
	}

	public void delete(DomainConfigure domainconfigure){
		domainConfigureDao.delete(domainconfigure);
	}

	public List<DomainConfigure> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return domainConfigureDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainConfigureDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainConfigureDao.deleteByParam(map);
	}

}

