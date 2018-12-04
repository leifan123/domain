package com.domain.dao;

import com.domain.pojo.DomainRole;

import java.util.List;

import java.util.Map;

public interface DomainRoleDao {

	public void insert(DomainRole domainrole);

	public List<DomainRole> select(DomainRole domainrole);

	public void update(DomainRole domainrole);

	public void delete(DomainRole domainrole);

	public List<DomainRole> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

