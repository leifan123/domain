package com.domain.dao;

import com.domain.pojo.DomainSsl;

import java.util.List;

import java.util.Map;

public interface DomainSslDao {

	public void insert(DomainSsl domainssl);

	public List<DomainSsl> select(DomainSsl domainssl);

	public void update(DomainSsl domainssl);

	public void delete(DomainSsl domainssl);

	public List<DomainSsl> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

