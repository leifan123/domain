package com.domain.dao;

import com.domain.pojo.DomainAuthentication;

import java.util.List;

import java.util.Map;

public interface DomainAuthenticationDao {

	public void insert(DomainAuthentication domainauthentication);

	public List<DomainAuthentication> select(DomainAuthentication domainauthentication);

	public void update(DomainAuthentication domainauthentication);

	public void delete(DomainAuthentication domainauthentication);

	public List<DomainAuthentication> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

