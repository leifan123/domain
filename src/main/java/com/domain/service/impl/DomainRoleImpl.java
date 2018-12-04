package com.domain.service.impl;

import com.domain.pojo.DomainRole;
import com.domain.dao.DomainRoleDao;
import com.domain.service.DomainRoleService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainRoleImpl implements DomainRoleService {

	private DomainRoleDao domainRoleDao;

	public void setDomainRoleDao(DomainRoleDao domainRoleDao){
		 this.domainRoleDao = domainRoleDao;
	}

	public void insert(DomainRole domainrole){
		domainRoleDao.insert(domainrole);
	}

	public List<DomainRole> select(DomainRole domainrole){
		return domainRoleDao.select(domainrole);
	}

	public void update(DomainRole domainrole){
		domainRoleDao.update(domainrole);
	}

	public void delete(DomainRole domainrole){
		domainRoleDao.delete(domainrole);
	}

	public List<DomainRole> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return domainRoleDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainRoleDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domainRoleDao.deleteByParam(map);
	}

}

