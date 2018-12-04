package com.domain.dao;

import com.domain.pojo.DomainConfigure;

import java.util.List;

import java.util.Map;

public interface DomainConfigureDao {

	public void insert(DomainConfigure domainconfigure);

	public List<DomainConfigure> select(DomainConfigure domainconfigure);

	public void update(DomainConfigure domainconfigure);

	public void delete(DomainConfigure domainconfigure);

	public List<DomainConfigure> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

