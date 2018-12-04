package com.domain.service;

import com.domain.pojo.DomainTemplate;

import java.util.List;

public interface DomainTemplateService {

	public void insert(DomainTemplate domaintemplate);

	public List<DomainTemplate> select(DomainTemplate domaintemplate);

	public void update(DomainTemplate domaintemplate);

	public void delete(DomainTemplate domaintemplate);

	public List<DomainTemplate> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

	public void updateByType(DomainTemplate domaintemplate);
	
	public void updateTemplate(DomainTemplate domaintemplate);
}

