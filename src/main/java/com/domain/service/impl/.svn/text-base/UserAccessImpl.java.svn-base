package com.domain.service.impl;

import com.domain.pojo.UserAccess;
import com.domain.dao.UserAccessDao;
import com.domain.service.UserAccessService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccessImpl implements UserAccessService {

	private UserAccessDao userAccessDao;

	public void setUserAccessDao(UserAccessDao userAccessDao){
		 this.userAccessDao = userAccessDao;
	}

	public void insert(UserAccess useraccess){
		userAccessDao.insert(useraccess);
	}

	public List<UserAccess> select(UserAccess useraccess){
		return userAccessDao.select(useraccess);
	}

	public void update(UserAccess useraccess){
		userAccessDao.update(useraccess);
	}

	public void delete(UserAccess useraccess){
		userAccessDao.delete(useraccess);
	}

	public List<UserAccess> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userAccessDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userAccessDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userAccessDao.deleteByParam(map);
	}

}

