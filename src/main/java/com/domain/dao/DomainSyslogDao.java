package com.domain.dao;

import com.domain.pojo.DomainSyslog;

import java.util.List;

import java.util.Map;

public interface DomainSyslogDao {

	public void insert(DomainSyslog domainsyslog);

	public List<DomainSyslog> select(DomainSyslog domainsyslog);

	public void update(DomainSyslog domainsyslog);

	public void delete(DomainSyslog domainsyslog);

	public List<DomainSyslog> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

