package com.domain.service.impl;

import com.domain.pojo.DomainPrice;
import com.domain.dao.DomainPriceDao;
import com.domain.service.DomainPriceService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainPriceImpl implements DomainPriceService {

	private DomainPriceDao domainPriceDao;

	public void setDomainPriceDao(DomainPriceDao domainPriceDao){
		 this.domainPriceDao = domainPriceDao;
	}

	public void insert(DomainPrice domainprice){
		domainPriceDao.insert(domainprice);
	}

	public List<DomainPrice> select(DomainPrice domainprice){
		return domainPriceDao.select(domainprice);
	}

	public void update(DomainPrice domainprice){
		domainPriceDao.update(domainprice);
	}

	public void delete(DomainPrice domainprice){
		domainPriceDao.delete(domainprice);
	}

	public List<DomainPrice> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainPriceDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainPriceDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainPriceDao.deleteByParam(map);
	}

	public void insertDomain(List<String> list) {
		domainPriceDao.insertDomain(list);
	}

}

