package com.ddjf.wechat.mini.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ddjf.wechat.common.util.AESUtil;
import com.ddjf.wechat.common.util.MsgSendUtil;
import com.ddjf.wechat.common.util.RedisUtil;
import com.ddjf.wechat.common.util.ResultUtil;
import com.ddjf.wechat.common.util.StringUtil;
import com.ddjf.wechat.common.util.VerifyCodeUtil;
import com.ddjf.wechat.mini.util.WechatMiniUtil;
import com.ddjf.wechat.modules.mini.entity.UserInfo;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;

@Controller
@RequestMapping(value = "/mini/login")
public class MiniLoginController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "getVerifyCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getVerifyCode(@RequestParam("mobile") String mobile){
		this.logger.warn("mobile={}", mobile);
		if(StringUtils.isNotEmpty(mobile)){
			String vcode = VerifyCodeUtil.getVcode(mobile);
			MsgSendUtil.sendVerificationCode(mobile, vcode);
			return ResultUtil.success();
		} else {
			return ResultUtil.error("手机号码不能为空！");
		}
	}
	
	@RequestMapping(value = "loginForm")
	@ResponseBody
	public Map<String, Object> loginForm(HttpServletRequest request, @RequestBody Map<String, String> params){
		String type = params.get("type");
		String code = params.get("code");
		String mobile = params.get("mobile");
		String idType = params.get("idType");
		String idCode = params.get("idCode");
		String idName = params.get("idName");
		this.logger.warn("type={}, mobile={}, code={}, idType={}, idCode={}, idName={}", type, mobile, code, idType, idCode, idName);
		UserInfo userInfo = WechatMiniUtil.getUserInfo(request);
		if(userInfo == null) {
			return ResultUtil.error("获取用户信息出错，请稍后重试！"); 
		}
		if("01".equals(type)){
			if(StringUtils.isEmpty(mobile)){
				return ResultUtil.error("手机号码不能为空！");
			}
			if(StringUtils.isEmpty(code)){
				return ResultUtil.error("验证码不能为空！");
			}
			String vcode = VerifyCodeUtil.getVcode(mobile);
			if(vcode.equals(code)){
				WechatMiniUtil.updateUserInfo(userInfo.getOpenId(), type, mobile);
				return ResultUtil.success();
			} else {
				return ResultUtil.error("3", "验证码错误!");
			}
		} else {
			if(StringUtils.isEmpty(idName)){
				return ResultUtil.error("姓名不能为空！");
			}
			if(StringUtils.isEmpty(idType)){
				return ResultUtil.error("证件类型不能为空！");
			}
			if(StringUtils.isEmpty(idCode)){
				return ResultUtil.error("证件号不能为空！");
			}
			Map<String, String> map = new HashMap<>();
			map.put("idType", idType);
			map.put("idCode", idCode);
			map.put("idName", idName);
			WechatMiniUtil.updateUserInfo(userInfo.getOpenId(), type, JSON.toJSONString(map));
			return ResultUtil.success();
		}
	}
	
	@RequestMapping(value = "logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request){
		UserInfo userInfo = WechatMiniUtil.getUserInfo(request);
		if(userInfo != null){
			WechatMiniUtil.updateUserInfo(userInfo.getOpenId(), "", "");
		}
		return ResultUtil.success();
	}
	
	@RequestMapping(value = "getSessionKey")
	@ResponseBody
	public Map<String, Object> getSessionKey(@RequestBody Map<String, String> params){
		String jsCode = params.get("jsCode");
		String iv = params.get("iv");
		String encryptedData = params.get("encryptedData");
		Map<String, Object> map = ResultUtil.success();
		try{
			Map<String, String> session = WechatMiniUtil.getSessionInfo(jsCode);
			String sessionKey = session.get("session_key");
			if(StringUtil.isNotEmpty(sessionKey)){
				String sessionId = IdGen.uuid();
				System.out.println(sessionId);
				map.put("sessionKey", sessionId);
				RedisUtil.set(sessionId, session);
				String json = AESUtil.decrypt(encryptedData, sessionKey, iv);
				UserInfo userInfo = WechatMiniUtil.saveUserInfo(json);
				map.put("bindFlag", WechatMiniUtil.checkBindStatus(userInfo.getOpenId()));
			} else {
				map = ResultUtil.error("获取jscode2session出错！");
			}
		} catch (Exception e){
			map = ResultUtil.error("系统错误：" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "getUserInfo")
	@ResponseBody
	public Map<String, String> getUserInfo(@RequestParam("sessionKey") String sessionKey){
		System.out.println(RedisUtil.getMapValue(sessionKey));
		return RedisUtil.getMapValue(sessionKey);
	}
	
	@RequestMapping(value = "getSession")
	@ResponseBody
	public Map<String, String> getSession(@RequestParam("sessionKey") String sessionKey){
		System.out.println(RedisUtil.getMapValue(sessionKey));
		return RedisUtil.getMapValue(sessionKey);
	}
	
	@RequestMapping(value = "simulateLogin")
	@ResponseBody
	public String simulateLogin(@RequestParam("openId") String openId) {
		Map<String, String> session = new HashMap<>();
		String sessionId = "123456";
		System.out.println(sessionId);
		session.put("openid", openId);
		RedisUtil.setObject(sessionId, session, 12*60*60);
		return "Success";
	}
	
	@RequestMapping(value = "set")
	@ResponseBody
	public String set(@RequestParam("key") String key, @RequestParam("value") String value){
		RedisUtil.set(key, value);
		return "Success";
	}
	
	@RequestMapping(value = "get")
	@ResponseBody
	public String get(HttpServletRequest request, @RequestParam("key") String key){
		UserInfo userInfo = WechatMiniUtil.getUserInfo(request);
		System.out.println(userInfo.getNickName());
		String value = RedisUtil.get(key);
		System.out.println(key + "===>" + value);
		return RedisUtil.get(key);
	}
}
