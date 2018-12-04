package com.domain.service;

import com.domain.pojo.UserCoreAccess;

import java.util.List;

public interface UserCoreAccessService {

	public void insert(UserCoreAccess usercoreaccess);

	public List<UserCoreAccess> select(UserCoreAccess usercoreaccess);

	public void update(UserCoreAccess usercoreaccess);

	public void delete(UserCoreAccess usercoreaccess);

	public List<UserCoreAccess> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

