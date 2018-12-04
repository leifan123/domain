package com.domain.service;

import com.domain.pojo.DomainConfigure;

import java.util.List;

public interface DomainConfigureService {

	public void insert(DomainConfigure domainconfigure);

	public List<DomainConfigure> select(DomainConfigure domainconfigure);

	public void update(DomainConfigure domainconfigure);

	public void delete(DomainConfigure domainconfigure);

	public List<DomainConfigure> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

