package com.domain.dao;

import com.domain.pojo.DomainResolveList;

import java.util.List;

import java.util.Map;

public interface DomainResolveListDao {

	public void insert(DomainResolveList domainresolvelist);

	public List<DomainResolveList> select(DomainResolveList domainresolvelist);

	public void update(DomainResolveList domainresolvelist);

	public void delete(DomainResolveList domainresolvelist);

	public List<DomainResolveList> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

