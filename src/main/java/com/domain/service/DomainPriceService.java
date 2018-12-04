package com.domain.service;

import com.domain.pojo.DomainPrice;

import java.util.List;

public interface DomainPriceService {

	public void insert(DomainPrice domainprice);

	public List<DomainPrice> select(DomainPrice domainprice);

	public void update(DomainPrice domainprice);

	public void delete(DomainPrice domainprice);

	public List<DomainPrice> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

	public void insertDomain(List<String> list);
}

