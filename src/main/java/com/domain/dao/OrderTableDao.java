package com.domain.dao;

import com.domain.pojo.OrderTable;

import java.util.List;

import java.util.Map;

public interface OrderTableDao {

	public void insert(OrderTable ordertable);

	public List<OrderTable> select(OrderTable ordertable);

	public void update(OrderTable ordertable);

	public void delete(OrderTable ordertable);

	public List<OrderTable> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

