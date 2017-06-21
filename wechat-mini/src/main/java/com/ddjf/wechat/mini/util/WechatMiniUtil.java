package com.ddjf.wechat.mini.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ddjf.wechat.common.util.HttpUtil;
import com.ddjf.wechat.common.util.JsonUtil;
import com.ddjf.wechat.common.util.PropertyUtil;
import com.ddjf.wechat.common.util.RedisUtil;
import com.ddjf.wechat.common.util.StringUtil;
import com.ddjf.wechat.mini.vo.MiniTemplateMessage;
import com.ddjf.wechat.modules.mini.entity.UserInfo;
import com.ddjf.wechat.modules.mini.manager.UserInfoManager;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

public class WechatMiniUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WechatMiniUtil.class);

	//private static UserInfoManager userInfoManager = SpringContextHolder.getBean("userInfoManager", UserInfoManager.class);

	/**
	 * 根据jsCode获取用户权限信息
	 * @param jsCode
	 * @return
	 */
	public static Map<String, String> getSessionInfo(String jsCode) {
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		String appId = PropertyUtil.get("app.id");
		String appSecret = PropertyUtil.get("app.secret");
		// String appId = "wxa3074cf979003b88";
		// String appSecret = "a62c0f870c8ade9674fd4c0c03f7a2ea";
		// String appId = "wx011b15bd0edbc44b";
		// String appSecret = "1affb7d14449523517862ddf3ffe7130";
		// String jsCode = "041mrSnN0J9Ab729ErlN0VWMnN0mrSnt";
		String grantType = "authorization_code";
		Map<String, String> params = new HashMap<>();
		params.put("appid", appId);
		params.put("secret", appSecret);
		params.put("js_code", jsCode);
		params.put("grant_type", grantType);
		String json = HttpUtil.httpGet(url, params);
		logger.warn("json={}", json);
		return JsonUtil.jsonToMap(json);
	}
	
	/**
	 * 获取请求的AccessToken
	 * @return
	 */
	public static String getAccessToken(){
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String appId = PropertyUtil.get("app.id");
		String appSecret = PropertyUtil.get("app.secret");
		String key = "appId_" + appId; 
		String accessToken = RedisUtil.get(key);
		if(StringUtil.isEmpty(accessToken)){
			String grantType = "client_credential";
			Map<String, String> params = new HashMap<>();
			params.put("appid", appId);
			params.put("secret", appSecret);
			params.put("grant_type", grantType);
			String json = HttpUtil.httpGet(url, params);
			logger.warn("getAccessToken.json={}", json);
			JSONObject jsonObj = JSONObject.parseObject(json);
			accessToken = jsonObj.getString("access_token");
			if(StringUtil.isNotEmpty(accessToken)){
				RedisUtil.set(key, accessToken, 2*55*60);
			}
		}
		logger.warn("accessToken={}", accessToken);
		return accessToken;
	}
	
	/**
	 * 发送模板消息
	 * @param templateMessage
	 * @return
	 */
	public static boolean sendTemplateMessage(MiniTemplateMessage templateMessage){
		String accessToken = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
		String json = HttpUtil.sendHttpPostJson(url, JSONObject.toJSONString(templateMessage));
		logger.warn("sendTemplateMessage.json={}", json);
		JSONObject jsonObj = JSONObject.parseObject(json);
		int errCode = jsonObj.getIntValue("errcode");
		if(errCode == 0){
			return true;
		}
		return false;
	}
	

	/**
	 * 保存用户信息
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static UserInfo saveUserInfo(String json) throws Exception {
		UserInfoManager userInfoManager = SpringContextHolder.getBean("userInfoManager", UserInfoManager.class);
		UserInfo userInfo = JSONObject.parseObject(json, UserInfo.class);
		String nickName = userInfo.getNickName();
		if (StringUtil.isNotEmpty(nickName)) {
			nickName = new String(Base64.encodeBase64(nickName.getBytes("UTF-8")), "UTF-8");
			userInfo.setNickName(nickName);
		}
		userInfoManager.save(userInfo);
		return userInfo;
	}

	/**
	 * 更新用户信息
	 * @param openId
	 * @param bindType
	 * @param bindValue
	 * @return
	 */
	public static UserInfo updateUserInfo(String openId, String bindType, String bindValue) {
		UserInfoManager userInfoManager = SpringContextHolder.getBean("userInfoManager", UserInfoManager.class);
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenId(openId);
		userInfo.setBindType(bindType);
		userInfo.setBindValue(bindValue);
		userInfoManager.update(userInfo);
		return userInfo;
	}

	/**
	 * 根据openId获取用户信息
	 * @param openId
	 * @return
	 */
	public static UserInfo getUserInfo(String openId) {
		UserInfoManager userInfoManager = SpringContextHolder.getBean("userInfoManager", UserInfoManager.class);
		UserInfo entity = new UserInfo();
		entity.setOpenId(openId);
		return userInfoManager.get(entity);
	}

	/**
	 * 根据当前Session获取用户信息
	 * @param request
	 * @return
	 */
	public static UserInfo getUserInfo(HttpServletRequest request) {
		String openId = (String) request.getAttribute("openId");
		return getUserInfo(openId);
	}
	
	/**
	 * 根据当前session获取openId
	 * @param request
	 * @return
	 */
	public static String getOpenId(HttpServletRequest request) {
		return (String) request.getAttribute("openId");
	}
	
	/**
	 * 根据当前session获取sessionId
	 * @param request
	 * @return
	 */
	public static String getSeesionId(HttpServletRequest request){
		return (String) request.getAttribute("sessionId");
	}

	/**
	 * 查询当前用户的绑定状态
	 * @param openId
	 * @return
	 */
	public static boolean checkBindStatus(String openId) {
		UserInfo userInfo = getUserInfo(openId);
		if (StringUtil.isNotEmpty(userInfo.getBindType()) && StringUtil.isNotEmpty(userInfo.getBindValue())) {
			return true;
		}
		return false;
	}
	
}
