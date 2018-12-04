package com.domain.service.impl;

import com.domain.pojo.DomainTransfer;
import com.domain.dao.DomainTransferDao;
import com.domain.service.DomainTransferService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainTransferImpl implements DomainTransferService {

	private DomainTransferDao domainTransferDao;

	public void setDomainTransferDao(DomainTransferDao domainTransferDao){
		 this.domainTransferDao = domainTransferDao;
	}

	public void insert(DomainTransfer domaintransfer){
		domainTransferDao.insert(domaintransfer);
	}

	public List<DomainTransfer> select(DomainTransfer domaintransfer){
		return domainTransferDao.select(domaintransfer);
	}

	public void update(DomainTransfer domaintransfer){
		domainTransferDao.update(domaintransfer);
	}

	public void delete(DomainTransfer domaintransfer){
		domainTransferDao.delete(domaintransfer);
	}

	public List<DomainTransfer> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", param);
		 return domainTransferDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainTransferDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  domainTransferDao.deleteByParam(map);
	}

}

