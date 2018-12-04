package com.domain.service.impl;

import com.domain.pojo.OrderTable;
import com.domain.dao.OrderTableDao;
import com.domain.service.OrderTableService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTableImpl implements OrderTableService {

	private OrderTableDao orderTableDao;

	public void setOrderTableDao(OrderTableDao orderTableDao){
		 this.orderTableDao = orderTableDao;
	}

	public void insert(OrderTable ordertable){
		orderTableDao.insert(ordertable);
	}

	public List<OrderTable> select(OrderTable ordertable){
		return orderTableDao.select(ordertable);
	}

	public void update(OrderTable ordertable){
		orderTableDao.update(ordertable);
	}

	public void delete(OrderTable ordertable){
		orderTableDao.delete(ordertable);
	}

	public List<OrderTable> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return orderTableDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  orderTableDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  orderTableDao.deleteByParam(map);
	}

}

