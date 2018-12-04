package com.domain.util;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.domain.pojo.DomainOperatelog;
import com.domain.service.DomainOperatelogService;

@Component
public class AddOprateloUtil {
	private static DomainOperatelogService domainOperatelogService;
	
	@Autowired
	public AddOprateloUtil(DomainOperatelogService domainOperatelogService){
		this.domainOperatelogService = domainOperatelogService;
	}
    //日志添加
	public static void domainResoveLog(String ip ,String operatetarget,
			String operatetype,String companyId, int operateStatus,
			String operateDes,String errorMessage
			) {
		DomainOperatelog  domainOperatelog = new DomainOperatelog();
		domainOperatelog.setCompanyid(companyId);
		domainOperatelog.setOperatedes(operateDes);
	
		domainOperatelog.setOperatestatus(operateStatus);

		domainOperatelog.setOperatetarget(operatetarget);
		domainOperatelog.setOperatetype(operatetype);
		Date date1 = new Date();
		domainOperatelog.setOperatortime(date1);
		domainOperatelog.setOperatorip(ip);
		
		domainOperatelogService.insert(domainOperatelog);
	}


}
