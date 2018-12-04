package com.domain.dao;

import com.domain.pojo.DomainName;

import java.util.List;

import java.util.Map;

public interface DomainNameDao {

	public void insert(DomainName domainname);

	public List<DomainName> select(DomainName domainname);

	public void update(DomainName domainname);

	public void delete(DomainName domainname);

	public List<DomainName> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

