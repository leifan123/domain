package com.domain.service.impl;

import com.domain.pojo.DomainResolveList;
import com.domain.dao.DomainResolveListDao;
import com.domain.service.DomainResolveListService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainResolveListImpl implements DomainResolveListService {

	private DomainResolveListDao domainResolveListDao;

	public void setDomainResolveListDao(DomainResolveListDao domainResolveListDao){
		 this.domainResolveListDao = domainResolveListDao;
	}

	public void insert(DomainResolveList domainresolvelist){
		domainResolveListDao.insert(domainresolvelist);
	}

	public List<DomainResolveList> select(DomainResolveList domainresolvelist){
		return domainResolveListDao.select(domainresolvelist);
	}

	public void update(DomainResolveList domainresolvelist){
		domainResolveListDao.update(domainresolvelist);
	}

	public void delete(DomainResolveList domainresolvelist){
		domainResolveListDao.delete(domainresolvelist);
	}

	public List<DomainResolveList> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return domainResolveListDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainResolveListDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainResolveListDao.deleteByParam(map);
	}

}

