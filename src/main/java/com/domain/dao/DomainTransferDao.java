package com.domain.dao;

import com.domain.pojo.DomainTransfer;

import java.util.List;

import java.util.Map;

public interface DomainTransferDao {

	public void insert(DomainTransfer domaintransfer);

	public List<DomainTransfer> select(DomainTransfer domaintransfer);

	public void update(DomainTransfer domaintransfer);

	public void delete(DomainTransfer domaintransfer);

	public List<DomainTransfer> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

