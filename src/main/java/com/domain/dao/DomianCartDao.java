package com.domain.dao;

import com.domain.pojo.DomianCart;

import java.util.List;

import java.util.Map;

public interface DomianCartDao {

	public void insert(DomianCart domiancart);

	public List<DomianCart> select(DomianCart domiancart);

	public void update(DomianCart domiancart);

	public void delete(DomianCart domiancart);

	public List<DomianCart> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

