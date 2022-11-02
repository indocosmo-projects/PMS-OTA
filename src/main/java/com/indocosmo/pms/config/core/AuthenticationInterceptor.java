package com.indocosmo.pms.config.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.indocosmo.pms.web.login.model.UserSession;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		// if (!uri.endsWith("pms/") &&!uri.endsWith("login") &&
		// !uri.endsWith("logout")) {
		String rootPath = request.getContextPath();
		if (!uri.endsWith(rootPath + "/") && !uri.endsWith("login") && !uri.endsWith("logout")) {

			UserSession userSessionData = (UserSession) request.getSession().getAttribute("userSession");
			if (userSessionData == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			}
		} else if (uri.endsWith(rootPath + "/")) {

			UserSession userSessionData = (UserSession) request.getSession().getAttribute("userSession");
			if (userSessionData != null) {
				response.sendRedirect(request.getContextPath() + "/dashboard");
				return false;
			}
		}

		return true;
	}
			

}