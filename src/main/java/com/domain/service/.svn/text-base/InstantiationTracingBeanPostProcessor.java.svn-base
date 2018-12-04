/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.domain.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.domain.util.GlobalAttr;
import com.domain.util.Prop;



/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: asda.java, v 0.1 2018年5月15日 上午11:30:48 yunrui006 Exp $
 */
@Service
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	private Logger log = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);

	
	/**
	 * @param event
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() == null) {

			Map<String, String> userTokenMap = GlobalAttr.getInstance().getUserTokenMap();
			String username = Prop.getInstance().getPropertiesByPro("domain.properties", "meicheng.username");
			String apiPassword = Prop.getInstance().getPropertiesByPro("domain.properties", "meicheng.apiPassword");
			String email = Prop.getInstance().getPropertiesByPro("domain.properties", "meicheng.email");
			userTokenMap.put("username", username);
			userTokenMap.put("apiPassword", apiPassword);
			userTokenMap.put("email", email);

		}

	}

}
