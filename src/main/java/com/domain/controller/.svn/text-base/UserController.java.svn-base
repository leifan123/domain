/**
 * @author YuLong.Dai
 * @time 2017年3月31日 上午9:59:15
 * TODO
 */
package com.domain.controller;



import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.exception.ExcepEnum;
import com.domain.pojo.UserAccess;
import com.domain.pojo.UserCoreAccess;
import com.domain.service.UserAccessService;
import com.domain.service.UserCoreAccessService;
import com.domain.util.DesUtils;
import com.domain.util.ExptNum;
import com.domain.util.FcUtil;
import com.domain.util.GlobalAttr;
import com.domain.util.JSONUtils;


/**
 * @author YuLong.Dai
 * @time 2017年3月31日 上午9:59:15
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserCoreAccessService userCoreAccessService;
	@Autowired
	private UserAccessService userAccessSerivce;
	
	public Boolean judgeApiSerect(String companyId, String secret){
		List<UserCoreAccess> userCoreAccessList = userCoreAccessService.selectByParam(null, "apiaccesskeysecret = '"+secret+"'");
		if(userCoreAccessList.isEmpty()){
			return false;
		} else {
			UserCoreAccess userCoreAccess = userCoreAccessList.get(0);
			if(companyId.equals(userCoreAccess.getCompanyid())){
				return true;
			}
		}
		List<UserAccess> userAccessList = userAccessSerivce.selectByParam(null, "accesskeysecret = '" + secret + "'");
		if(userAccessList.isEmpty()){
			return false;
		}
		for(UserAccess userAccess : userAccessList){
			if(companyId.equals(userAccess.getCompanyid())){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @Title: GetUserInfo @Description: 获取用户信息 @author YaNan.Guan @param
	 *         request @param response @return @return String @throws
	 */
	/*@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String GetUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result_map = new HashMap<String, Object>();
		YrComper comper = ApiTool.getUserMsg(request);
		
		List<Getfreevminformation> list_getfree = this.getfreevminformationService.selectBySql(
				"select  a.id,case when b.companyType is null then 0 else b.companyType  end as companyType from activity as a LEFT JOIN getfreevminformation as b on a.id=b.activityNum and companyId='"
						+ comper.getCompanyid() + "'");

		YrComper comPerinfo = new YrComper();
		comPerinfo.setRealname(comper.getRealname());
		comPerinfo.setActivityInfo(list_getfree);
		String telephone = comper.getPhone();

		if (telephone == null || telephone.isEmpty()) {
		} else {
			comPerinfo.setPhone(telephone.substring(0, 3) + "****" + telephone.substring(7, 11));
		}
		comPerinfo.setLoginname(comper.getLoginname());
		comPerinfo.setAvatar(comper.getAvatar());
		comPerinfo.setCountry(comper.getCountry());
		comPerinfo.setProvince(comper.getProvince());
		comPerinfo.setCity(comper.getCity());
		comPerinfo.setAddress(comper.getAddress());
		comPerinfo.setIsdirector(comper.getIsdirector());
		comPerinfo.setPersonalauth(comper.getPersonalauth());
		comPerinfo.setCompanyauth(comper.getCompanyauth());
		comPerinfo.setEmailauth(comper.getEmailauth());
		comPerinfo.setDefaultzoneid(comper.getDefaultzoneid());
		comPerinfo.setDefaultzonename(comper.getDefaultzonename());
		comPerinfo.setCompanyid(comper.getCompanyid());
		result_map.put("status", GetResult.SUCCESS_STATUS);
		result_map.put("message", Config.REQUEST_SUCCESS);
		result_map.put("result", comPerinfo);

		List<YrAuthlog> yrAuthList = yrAuthlogService.select(new YrAuthlog().setCompanyid(comper.getCompanyid()));

		YrAuthlog yrAuth = null;

		try {

			if (!yrAuthList.isEmpty()) {
				yrAuth = yrAuthList.get(0);
				String ps = yrAuth.getPersonalnumber();
				String phone = comper.getPhone();
				if (phone != null && !phone.isEmpty() && phone.length() >= 11) {
					yrAuth.setPhone(phone.substring(0, 3) + "****" + phone.substring(7, 11));
				}
				if (ps != null && !ps.isEmpty()) {
					yrAuth.setPersonalnumber(ps.substring(0, 6) + "********" + ps.substring(14, ps.length()));
				}
			}

		} catch (Exception e) {
			log.error(e);
			// TODO: handle exception
		}

		result_map.put("authInfo", yrAuth);

		return JSONUtils.createObjectJson(result_map);
	}*/
	/**
	 * 获取用户token
	 * 
	 * @param request
	 * @param response
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getToken", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getToken(HttpServletRequest request, HttpServletResponse response, String companyId, String secret)
			throws Exception {

		// String companyId = ApiTool.getYrComer(request);
		
		UserCoreAccess queryParam = new UserCoreAccess();
		queryParam.setApiaccesskeysecret(secret);
		List<UserCoreAccess> userAccessList = userCoreAccessService.select(queryParam);
		// 判空
		if (FcUtil.checkIsNull(companyId) || FcUtil.checkIsNull(secret)) {

			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_SY_ISNOTNULL.VAL);
			return JSONUtils.createObjectJson(result_map);
		}
/*
		// 用户不存在
		if (userAccessList.size() == 0) {
			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_NOUSER.VAL);

			return JSONUtils.createObjectJson(result_map);

		}*/
		// 密钥错误
		if (!judgeApiSerect(companyId, secret)) {

			Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.ERROR_CM_MYSB.VAL);

			return JSONUtils.createObjectJson(result_map);
		}
		String token_md = (new Date().getTime() / 1000) + "#" + companyId;
		String token = DesUtils.getInstance().encrypt(token_md);

		Map<String, String> userTokenMap = GlobalAttr.getInstance().getUserTokenMap();

		userTokenMap.put(companyId, token);

		Map<String, Object> result_map = FcUtil.getResult(ExcepEnum.SUCCESS.VAL);

		result_map.put("token", token);

		return JSONUtils.createObjectJson(result_map);

	}

	
	/**
	 * 退出登录 
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("status", ExptNum.SUCCESS.getCode());
		result.put("msg", ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(result);
	}
	
}
