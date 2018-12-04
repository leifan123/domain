package com.domain.service;

import com.domain.pojo.YrComper;

import java.util.List;

public interface YrComperService {

	public void insert(YrComper yrcomper);

	public List<YrComper> select(YrComper yrcomper);

	public void update(YrComper yrcomper);

	public void delete(YrComper yrcomper);

	public List<YrComper> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

