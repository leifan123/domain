package com.domain.dao;

import com.domain.pojo.DomainOperatelog;

import java.util.List;

import java.util.Map;

public interface DomainOperatelogDao {

	public void insert(DomainOperatelog domainoperatelog);

	public List<DomainOperatelog> select(DomainOperatelog domainoperatelog);

	public void update(DomainOperatelog domainoperatelog);

	public void delete(DomainOperatelog domainoperatelog);

	public List<DomainOperatelog> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);
	
	public int selectCount(Map<String, String> param);
	
	

}

