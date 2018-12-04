package com.domain.dao;

import com.domain.pojo.YrComper;

import java.util.List;

import java.util.Map;

public interface YrComperDao {

	public void insert(YrComper yrcomper);

	public List<YrComper> select(YrComper yrcomper);

	public void update(YrComper yrcomper);

	public void delete(YrComper yrcomper);

	public List<YrComper> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

