package com.domain.service.impl;

import com.domain.pojo.DomainOperatelog;
import com.domain.dao.DomainOperatelogDao;
import com.domain.service.DomainOperatelogService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainOperatelogImpl implements DomainOperatelogService {

	private DomainOperatelogDao domainOperatelogDao;

	public void setDomainOperatelogDao(DomainOperatelogDao domainOperatelogDao){
		 this.domainOperatelogDao = domainOperatelogDao;
	}

	public void insert(DomainOperatelog domainoperatelog){
		domainOperatelogDao.insert(domainoperatelog);
	}

	public List<DomainOperatelog> select(DomainOperatelog domainoperatelog){
		return domainOperatelogDao.select(domainoperatelog);
	}
	

	public void update(DomainOperatelog domainoperatelog){
		domainOperatelogDao.update(domainoperatelog);
	}

	public void delete(DomainOperatelog domainoperatelog){
		domainOperatelogDao.delete(domainoperatelog);
	}

	public List<DomainOperatelog> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainOperatelogDao.selectByParam(map);
	}
	
	public int selectCount(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		return domainOperatelogDao.selectCount(map);
	}
	

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainOperatelogDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainOperatelogDao.deleteByParam(map);
	}

}

