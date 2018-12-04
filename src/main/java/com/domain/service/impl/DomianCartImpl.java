package com.domain.service.impl;

import com.domain.pojo.DomianCart;
import com.domain.dao.DomianCartDao;
import com.domain.service.DomianCartService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomianCartImpl implements DomianCartService {

	private DomianCartDao domianCartDao;

	public void setDomianCartDao(DomianCartDao domianCartDao){
		 this.domianCartDao = domianCartDao;
	}

	public void insert(DomianCart domiancart){
		domianCartDao.insert(domiancart);
	}

	public List<DomianCart> select(DomianCart domiancart){
		return domianCartDao.select(domiancart);
	}

	public void update(DomianCart domiancart){
		domianCartDao.update(domiancart);
	}

	public void delete(DomianCart domiancart){
		domianCartDao.delete(domiancart);
	}

	public List<DomianCart> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return domianCartDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domianCartDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  domianCartDao.deleteByParam(map);
	}

}

