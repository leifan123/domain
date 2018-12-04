package com.domain.service;

import com.domain.pojo.OrderTable;

import java.util.List;

public interface OrderTableService {

	public void insert(OrderTable ordertable);

	public List<OrderTable> select(OrderTable ordertable);

	public void update(OrderTable ordertable);

	public void delete(OrderTable ordertable);

	public List<OrderTable> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

