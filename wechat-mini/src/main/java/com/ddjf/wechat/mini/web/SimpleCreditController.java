package com.ddjf.wechat.mini.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddjf.wechat.common.util.ResultUtil;
import com.ddjf.wechat.mini.service.SimpleCreditService;
import com.ddjf.wechat.mini.util.WechatMiniUtil;
import com.ddjf.wechat.mini.vo.CreditUserVO;

@Controller
@RequestMapping(value = "/mini/simplecredit")
public class SimpleCreditController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SimpleCreditService simpleCreditService;
	
	/**
	 * 用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getuserinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserInfoList(HttpServletRequest request){
		try{
			String openId = WechatMiniUtil.getOpenId(request);
			List<CreditUserVO> data = simpleCreditService.getUserInfoList(openId);
			return ResultUtil.success(data);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "deleteuserinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserInfo(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			int count = simpleCreditService.deleteUserInfo(creditUserVO);
			if(count>0){
				return ResultUtil.success("删除成功");
			} else {
				return ResultUtil.error("删除失败");
			}
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 身份信息校验
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "checkcertinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkCertInfo(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.checkCertInfo(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 登录名校验
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "checkloginname", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLoginName(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.checkLoginName(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 获取短信验证码
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "getmobilecode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMobileCode(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.getMobileCode(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "usersignup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userSignup(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			String openId = WechatMiniUtil.getOpenId(request);
			creditUserVO.setOpenId(openId);
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.userSignup(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "userlogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin(HttpServletRequest request,@RequestBody CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			String openId = WechatMiniUtil.getOpenId(request);
			creditUserVO.setOpenId(openId);
			return simpleCreditService.userLogin(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 身份信息绑定
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "bindcertinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bindCertInfo(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			String openId = WechatMiniUtil.getOpenId(request);
			this.logger.warn("openId={}", openId);
			int count = simpleCreditService.bindCertInfo(creditUserVO);
			if(count>0){
				return ResultUtil.success("绑定成功！");
			} else {
				return ResultUtil.error("绑定失败！");
			}
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 问题列表
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "questionlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> questionList(HttpServletRequest request, CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.questionList(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 回答问题
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "answerquestion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> answerQuestion(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			logger.info("formId={}", creditUserVO.getFormId());
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			return simpleCreditService.answerQuestion(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 征信报告获取
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "getcreditinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCreditInfo(HttpServletRequest request, CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			String openId = WechatMiniUtil.getOpenId(request);
			creditUserVO.setOpenId(openId);
			return simpleCreditService.getCreditInfo(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 获取用户当前状态
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "getuserstatus", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserStatus(HttpServletRequest request, CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			this.logger.warn("creditUserVO.getId={}", creditUserVO.getId());
			return simpleCreditService.getUserStatus(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	/**
	 * 征信报告获取短信验证码
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "getapplymobilecode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getApplyMobileCode(HttpServletRequest request, CreditUserVO creditUserVO){
		try{
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			this.logger.warn("creditUserVO.getId={}", creditUserVO.getId());
			return simpleCreditService.getApplyMobileCode(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	/**
	 * 征信报告提交短信验证码
	 * @param request
	 * @param creditUserVO
	 * @return
	 */
	@RequestMapping(value = "submitapplymobilecode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitApplyMobileCode(HttpServletRequest request, @RequestBody CreditUserVO creditUserVO){
		try{
			logger.info("formId={}", creditUserVO.getFormId());
			String sessionId = WechatMiniUtil.getSeesionId(request);
			creditUserVO.setSessionId(sessionId);
			this.logger.warn("creditUserVO.getId={}", creditUserVO.getId());
			return simpleCreditService.submitApplyMobileCode(creditUserVO);
		} catch (Exception e){
			logger.error("error={}" + e.getMessage());
			return ResultUtil.error("后台服务错误，请稍后重试");
		}
	}
	
	
}
