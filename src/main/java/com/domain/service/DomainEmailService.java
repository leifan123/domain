package com.domain.service;

import com.domain.pojo.DomainEmail;

import java.util.List;

public interface DomainEmailService {

	public void insert(DomainEmail domainemail);

	public List<DomainEmail> select(DomainEmail domainemail);

	public void update(DomainEmail domainemail);

	public void delete(DomainEmail domainemail);

	public List<DomainEmail> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

