package com.ddjf.wechat.mini.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ddjf.wechat.common.util.ResultUtil;
import com.ddjf.wechat.common.util.StringUtil;
import com.ddjf.wechat.mini.util.Constant;
import com.ddjf.wechat.mini.util.DataConvert;
import com.ddjf.wechat.mini.util.SimpleCreditUtil;
import com.ddjf.wechat.mini.vo.CreditUserVO;
import com.ddjf.wechat.modules.mini.entity.CreditInfo;
import com.ddjf.wechat.modules.mini.manager.CreditInfoManager;

/**
 * @author Frez
 * 简版征信处理Service
 *
 */
@Service
public class SimpleCreditService {
	
	@Autowired
	CreditInfoManager creditInfoManager;
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleCreditService.class);
	
	/**
	 * 用户列表
	 * @param openId
	 * @return
	 */
	public List<CreditUserVO> getUserInfoList(String openId){
		CreditInfo creditInfo = new CreditInfo();
		creditInfo.setOpenId(openId);
		List<CreditInfo> data = creditInfoManager.findList(creditInfo);
		return DataConvert.convertCreditInfo(data);
	}
	
	/**
	 * 删除用户
	 * @param creditUserVO
	 * @return
	 */
	public int deleteUserInfo(CreditUserVO creditUserVO){
		Long id = creditUserVO.getId();
		return creditInfoManager.delete(id);
	}
	
	/**
	 * 身份信息校验
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> checkCertInfo(CreditUserVO creditUserVO){
		return SimpleCreditUtil.sendPost(Constant.PBCCRC_CERNOVERIFY, JSONObject.toJSONString(creditUserVO));
	}
	
	/**
	 * 用户名校验
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> checkLoginName(CreditUserVO creditUserVO) {
		return SimpleCreditUtil.sendPost(Constant.PBCCRC_LOGINNAMEVERIFY, JSONObject.toJSONString(creditUserVO));
	}
	
	/**
	 * 获取手机验证码
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> getMobileCode(CreditUserVO creditUserVO){
		return SimpleCreditUtil.sendPost(Constant.PBCCRC_SMSCODE, JSONObject.toJSONString(creditUserVO));
	}
	
	/**
	 * 用户注册
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> userSignup(CreditUserVO creditUserVO){
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_REGISTER, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
			creditInfoManager.save(creditInfo);
		}
		return map;
	}
	
	/**
	 * 用户登录
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> userLogin(CreditUserVO creditUserVO){
		CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
		CreditInfo entity = creditInfoManager.get(creditInfo);
		if(entity != null && Constant.CODE_DELETE.equals(entity.getDelFlag())){
			return ResultUtil.error("用户"+creditUserVO.getLoginName()+"已经登录");
		} 
		return this.login(creditUserVO);
	}
	
	/**
	 * 身份信息绑定
	 * @param creditUserVO
	 * @return
	 */
	public int bindCertInfo(CreditUserVO creditUserVO){
		CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
		this.formatCreditUserVO(creditUserVO);
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_APPLYVERIFY, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			return creditInfoManager.update(creditInfo);
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取问题列表
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> questionList(CreditUserVO creditUserVO){
		this.formatCreditUserVO(creditUserVO);
		return SimpleCreditUtil.sendPost(Constant.PBCCRC_QUESITONS, JSONObject.toJSONString(creditUserVO));
	}
	
	/**
	 * 回答问题
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> answerQuestion(CreditUserVO creditUserVO){
		this.formatCreditUserVO(creditUserVO);
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_ANSWERS, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
			creditInfo.setSearchTime(new Date());
			creditInfo.setUserStatus(Constant.STATUS_APPLYING);
			creditInfo.setSearchFlag(Constant.FLAG_FALSE);
			creditInfo.setCreditFlag(Constant.FLAG_FALSE);
			creditInfo.setSendFlag("01");
			creditInfoManager.update(creditInfo);
		}
		return map;
	}
	
	/**
	 * 获取征信报告
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> getCreditInfo(CreditUserVO creditUserVO){
		logger.error("id={}, authCode={}", creditUserVO.getId(), creditUserVO.getAuthCode());
		String authCode = creditUserVO.getAuthCode();
		int count = 10;
		if(StringUtil.isNotEmpty(authCode)){
			count = SimpleCreditUtil.getQueryCount(creditUserVO.getOpenId(), creditUserVO.getId());
		}
		logger.warn("count={}", count);
		if(count <= 0){
			JSONObject data = new JSONObject();
			data.put(Constant.COUNT, count);
			return ResultUtil.error(data);
		}
		this.formatCreditUserVO(creditUserVO);
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_REPORTDETAIL, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
			creditInfo.setCreditTime(new Date());
			creditInfo.setSearchFlag(Constant.FLAG_TRUE);
			creditInfo.setCreditFlag(Constant.FLAG_TRUE);
			creditInfoManager.update(creditInfo);
		} else {
			JSONObject data = new JSONObject();
			if(map.get(Constant.DATA) != null){
				data = JSONObject.parseObject(map.get(Constant.DATA).toString());
			}
			data.put(Constant.COUNT, count);
			map.put(Constant.DATA, data);
		}
		return map;
	}
	
	/**
	 * 获取用户当前状态
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> getUserStatus(CreditUserVO creditUserVO){
		this.formatCreditUserVO(creditUserVO);
		return this.login(creditUserVO);
	}
	
	/**
	 * 获取生成征信报告手机验证码
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> getApplyMobileCode(CreditUserVO creditUserVO){
		this.formatCreditUserVO(creditUserVO);
		return SimpleCreditUtil.sendPost(Constant.PBCCRC_APPLYCODE, JSONObject.toJSONString(creditUserVO));
	}
	
	/**
	 * 提交生成征信报告手机验证码
	 * @param creditUserVO
	 * @return
	 */
	public Map<String, Object> submitApplyMobileCode(CreditUserVO creditUserVO){
		this.formatCreditUserVO(creditUserVO);
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_APPLY, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
			creditInfo.setSearchTime(new Date());
			creditInfo.setUserStatus(Constant.STATUS_APPLYING);
			creditInfo.setSearchFlag(Constant.FLAG_FALSE);
			creditInfo.setCreditFlag(Constant.FLAG_FALSE);
			creditInfo.setSendFlag("01");
			creditInfoManager.update(creditInfo);
		}
		return map;
	}
	
	/**
	 * 对象转换
	 * @param creditUserVO
	 */
	private void formatCreditUserVO(CreditUserVO creditUserVO){
		CreditInfo creditInfo = new CreditInfo();
		creditInfo.setId(creditUserVO.getId());
		CreditInfo entity = creditInfoManager.get(creditInfo);
		creditUserVO.setLoginName(entity.getLoginName());
		creditUserVO.setPassword(entity.getPassword());
	}
	
	public Map<String, Object> login(CreditUserVO creditUserVO){
		Map<String, Object> map = SimpleCreditUtil.sendPost(Constant.PBCCRC_LOGIN, JSONObject.toJSONString(creditUserVO));
		String retCode = (String)map.get(Constant.RET_CODE);
		if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
			CreditInfo creditInfo = DataConvert.convertCreditUserVO(creditUserVO);
			if(map.get(Constant.DATA) != null){
				JSONObject data = JSONObject.parseObject(map.get(Constant.DATA).toString());
				String userStatus = (String)data.get(Constant.STATUS);
				if(StringUtil.isNotEmpty(userStatus) && (Constant.STATUS_SUCCESS.equals(userStatus)||Constant.STATUS_FAILED.equals(userStatus))){
					creditInfo.setSearchFlag(Constant.FLAG_TRUE);
					creditInfo.setUserStatus(userStatus);
				} 
				map = ResultUtil.success(userStatus);
			}
			creditInfoManager.save(creditInfo);
		}
		return map;
	}
	
}
