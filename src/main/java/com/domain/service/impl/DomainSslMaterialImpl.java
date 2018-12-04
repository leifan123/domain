package com.domain.service.impl;

import com.domain.pojo.DomainSslMaterial;
import com.domain.dao.DomainSslMaterialDao;
import com.domain.service.DomainSslMaterialService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainSslMaterialImpl implements DomainSslMaterialService {

	private DomainSslMaterialDao domainSslMaterialDao;

	public void setDomainSslMaterialDao(DomainSslMaterialDao domainSslMaterialDao){
		 this.domainSslMaterialDao = domainSslMaterialDao;
	}

	public void insert(DomainSslMaterial domainsslmaterial){
		domainSslMaterialDao.insert(domainsslmaterial);
	}

	public List<DomainSslMaterial> select(DomainSslMaterial domainsslmaterial){
		return domainSslMaterialDao.select(domainsslmaterial);
	}

	public void update(DomainSslMaterial domainsslmaterial){
		domainSslMaterialDao.update(domainsslmaterial);
	}

	public void delete(DomainSslMaterial domainsslmaterial){
		domainSslMaterialDao.delete(domainsslmaterial);
	}

	public List<DomainSslMaterial> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainSslMaterialDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSslMaterialDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainSslMaterialDao.deleteByParam(map);
	}

}

