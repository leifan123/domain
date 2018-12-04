package com.domain.service;

import com.domain.pojo.DomainName;

import java.util.List;

public interface DomainNameService {

	public void insert(DomainName domainname);

	public List<DomainName> select(DomainName domainname);

	public void update(DomainName domainname);

	public void delete(DomainName domainname);

	public List<DomainName> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

