package com.domain.service.impl;

import com.domain.pojo.UserCoreAccess;
import com.domain.dao.UserCoreAccessDao;
import com.domain.service.UserCoreAccessService;
import com.domain.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCoreAccessImpl implements UserCoreAccessService {

	private UserCoreAccessDao userCoreAccessDao;

	public void setUserCoreAccessDao(UserCoreAccessDao userCoreAccessDao){
		 this.userCoreAccessDao = userCoreAccessDao;
	}

	public void insert(UserCoreAccess usercoreaccess){
		userCoreAccessDao.insert(usercoreaccess);
	}

	public List<UserCoreAccess> select(UserCoreAccess usercoreaccess){
		return userCoreAccessDao.select(usercoreaccess);
	}

	public void update(UserCoreAccess usercoreaccess){
		userCoreAccessDao.update(usercoreaccess);
	}

	public void delete(UserCoreAccess usercoreaccess){
		userCoreAccessDao.delete(usercoreaccess);
	}

	public List<UserCoreAccess> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return userCoreAccessDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userCoreAccessDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  userCoreAccessDao.deleteByParam(map);
	}

}

