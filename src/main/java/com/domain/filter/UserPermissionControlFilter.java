package com.domain.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.domain.util.ApiTool;
import com.domain.util.BodyReaderHttpServletRequestWrapper;
import com.domain.util.GetResult;
import com.domain.util.JSONUtils;
import com.domain.util.WebUtils;
import com.yunrui.pojo.YrComper;

@Component
public class UserPermissionControlFilter implements Filter {

	private Logger log = Logger.getLogger(UserPermissionControlFilter.class);

//	@Autowired
//	private UserCoreAccessService userCoreAccessService;
	
	
	public void init(FilterConfig arg0) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,

			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String servletPath = httpServletRequest.getServletPath();
		String urlStr;
		String queryString = httpServletRequest.getQueryString();
	 
		LinkedHashMap<String, String> paramMap = null;
		HashMap<String, Object> result = null;
		// MAC 校验
		// 放行
		if (1==1 || servletPath.startsWith("/user/getKaptchaImage.do") || servletPath.startsWith("/object/uploadObject.do")
				|| servletPath.startsWith("/template/uploadImage.do") || servletPath.startsWith("/template/updateTemple.do")){
			
		}else {
			if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {

				HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);

				paramMap = JSONUtils.getLinkedHashMapByJson(ApiTool.getBodyStringtoStr(requestWrapper));

				result = ApiTool.mac(paramMap);

				if (!"1".equals(result.get("status").toString())) {
					WebUtils.outputJson(JSONUtils.createObjectJson(result), httpServletRequest, httpServletResponse);
					return;
				}

				// 必须加上
				request = requestWrapper;

			} else if ("GET".equalsIgnoreCase(httpServletRequest.getMethod())) {

				if (queryString == null) {
				} else {

					paramMap = new LinkedHashMap<String, String>();
					Enumeration<String> paramNames = request.getParameterNames();
					while (paramNames.hasMoreElements()) {
						String paramName = (String) paramNames.nextElement();
						String[] paramValues = request.getParameterValues(paramName);
						if (paramValues.length == 1) {
							String paramValue = paramValues[0];
							if (paramValue.length() != 0) {
								paramMap.put(paramName, paramValue);
								// System.out.println(paramName+"="+paramValue);
							}
						}
					}
					String[] queryList = queryString.split("&");
					paramMap = new LinkedHashMap<String, String>();
					for (String str : queryList) {
						// 将 参数名=xxx 拆分
						String[] paramL = str.split("=");
						if (paramL.length == 1) {
							if (!paramL[0].isEmpty()) {
								paramMap.put(paramL[0], "");
							}
						} else {
							if (!paramL[0].isEmpty()) {
								paramMap.put(paramL[0], paramL[1]);
							}
						}

					}

					result = ApiTool.mac(paramMap);

					if (!"1".equals(result.get("status").toString())) {
						WebUtils.outputJson(JSONUtils.createObjectJson(result), httpServletRequest,
								httpServletResponse);
						return;
					}

				}

			}
		}

		// 防sql注入
		if (queryString != null && !queryString.isEmpty()) {

			queryString = queryString.replace("'", "”").replace(";", "；").replace("\"", "”");
			urlStr = servletPath + "?" + queryString;
		} else {
			urlStr = servletPath;
		}

		httpServletResponse.setContentType("textml;charset=UTF-8");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		httpServletResponse.setHeader("Access-Control-Max-Age", "0");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Content-Disposition");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setHeader("XDomainRequestAllowed", "1");
		//session 校验
		try {
			//放行
			if (urlStr.startsWith("/index.html")) {

				chain.doFilter(request, response);
				System.out.println("urlStr.startsWith /index.html");
				return;

			} else {
		  		
// 					HttpSession session = httpServletRequest.getSession(false);
 					 HttpSession session = httpServletRequest.getSession(true);
 					if(session == null) {
 						log.error("session 为空！");
 						result = new HashMap<String, Object>();
						result.put("status", GetResult.NOT_LOGIN);
						result.put("message", "尚未登录");

						WebUtils.outputJson(JSONUtils.createObjectJson(result), httpServletRequest, httpServletResponse);
						return;
 					}
//					YrComper yr =  new YrComper().setCompanyid((String)session.getAttribute("username"));
//					String username = yr.getCompanyid();
					
				   // HttpSession session = httpServletRequest.getSession(true);
				String username = "150898735031";
				session.setAttribute("username", username);
					if (username == null) {
						log.error("username 为空！");
						result = new HashMap<String, Object>();
						result.put("status", GetResult.NOT_LOGIN);
						result.put("message", "尚未登录");

						WebUtils.outputJson(JSONUtils.createObjectJson(result), httpServletRequest, httpServletResponse);
						return;
						 
					} else {
						
						chain.doFilter(request, response);
						return;
						
					}
			  
			}

		}catch (NullPointerException e) {
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return;

		}
	}

	public void destroy() {

	}

}
