package com.ddjf.wechat.mini.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ddjf.wechat.common.util.RedisUtil;
import com.ddjf.wechat.common.util.StringUtil;
import com.thinkgem.jeesite.common.service.BaseService;


/**
 * 小程序视图拦截器
 * @author Frez
 *
 */
public class MiniInterceptor extends BaseService implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath(); 
		this.logger.warn("path={}", path);
		List<String> anonUrl = new ArrayList<>();
		anonUrl.add("/mini/login/getSessionKey");
		anonUrl.add("/mini/login/simulateLogin");
		//无需权限验证放行  
		if(anonUrl.contains(path)) {   
			return true;  
		} 
		String sessionKey = request.getHeader("sessionKey");
		this.logger.warn("sessionKey={}", sessionKey);
		if(StringUtil.isEmpty(sessionKey)){
			response.setStatus(401);
			return false;
		}
		Map<String, String> map = RedisUtil.getMapValue(sessionKey);
		if(map == null){
			response.setStatus(403);
			return false;
		}
		String openId = map.get("openid");
		this.logger.warn("openId={}", openId);
		request.setAttribute("openId", openId);
		request.setAttribute("sessionId", sessionKey);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		 
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}
