package com.ddjf.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ddjf.wechat.common.util.DesEncryptUtil;
import com.ddjf.wechat.common.util.StringUtil;
import com.ddjf.wechat.mini.service.SimpleCreditService;
import com.ddjf.wechat.mini.util.Constant;
import com.ddjf.wechat.mini.util.WechatMiniUtil;
import com.ddjf.wechat.mini.vo.CreditUserVO;
import com.ddjf.wechat.mini.vo.MiniTemplateData;
import com.ddjf.wechat.mini.vo.MiniTemplateMessage;
import com.ddjf.wechat.modules.mini.entity.CreditInfo;
import com.ddjf.wechat.modules.mini.manager.CreditInfoManager;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

@Component("messageHandler")
@Scope("prototype")
public class MessageAsyncHandler extends AbstractAsyncHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void handle(Object obj) {
		try {
			logger.warn("thread.id="+Thread.currentThread().getId() + ";obj=" + obj);
			CreditInfo creditInfo = (CreditInfo)obj;
			Long id = creditInfo.getId();
			String formId = creditInfo.getFormId();
			String toUser = creditInfo.getOpenId();
			String page = "pages/credit/accounts/accounts";
			String templateId = "2Cm2FSy1yphYD86TCk320rsglSwM00dngw4zlNTFT0o";
			Map<String, MiniTemplateData> data = new HashMap<>();
			for(int i=0; i<5; i++){
				SimpleCreditService simpleCreditService = SpringContextHolder.getBean("simpleCreditService", SimpleCreditService.class);
				Map<String, Object> map = simpleCreditService.login(this.getCreditUserVO(creditInfo));
				String retCode = (String)map.get(Constant.RET_CODE);
				if(StringUtil.isNotEmpty(retCode) && Constant.CODE_SUCCESS.equals(retCode)){
					String userStatus = "";
					if(map.get(Constant.DATA) != null){
						userStatus = (String)map.get(Constant.DATA);
					}
					if(Constant.STATUS_SUCCESS.equals(userStatus)){
						data.put("keyword1", new MiniTemplateData("查询征信报告"));
						data.put("keyword2", new MiniTemplateData("征信报告已生成，等待输入身份验证码"));
						data.put("keyword3", new MiniTemplateData("请点击本消息，输入短信中的身份验证码"));
						this.sendTemplateMessage(toUser, formId, page, templateId, data, id);
					} else if(Constant.STATUS_FAILED.equals(userStatus)){
						data.put("keyword1", new MiniTemplateData("查询征信报告"));
						data.put("keyword2", new MiniTemplateData("身份验证失败"));
						data.put("keyword3", new MiniTemplateData("您可以点击本消息重新申请查询"));
						this.sendTemplateMessage(toUser, formId, page, templateId, data, id);
					} else {
						logger.warn("id={}, userStatus={}", id, userStatus);
					}
					break;
				}else {
					Thread.sleep(3000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private CreditUserVO getCreditUserVO(CreditInfo creditInfo) {
		String sessionId = System.currentTimeMillis() + "";
 		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setLoginName(creditInfo.getLoginName());
		try {
			creditUserVO.setOpenId(creditInfo.getOpenId());
			creditUserVO.setPassword(DesEncryptUtil.decrypt(creditInfo.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		creditUserVO.setSessionId(sessionId);
		return creditUserVO;
	}
	
	private void sendTemplateMessage(String toUser, String formId, String page, String templateId, Map<String, MiniTemplateData> data, Long id){
		MiniTemplateMessage templateMessage = new MiniTemplateMessage();
		templateMessage.setToUser(toUser);
		templateMessage.setFormId(formId);
		templateMessage.setPage(page);
		templateMessage.setTemplateId(templateId);
		templateMessage.setData(data);
		boolean flag = WechatMiniUtil.sendTemplateMessage(templateMessage);
		CreditInfo entity = new CreditInfo();
		entity.setId(id);
		if(flag){
			entity.setSendFlag("02");
		} else {
			entity.setSendFlag("03");
		}
		CreditInfoManager creditInfoManager = SpringContextHolder.getBean("creditInfoManager", CreditInfoManager.class);
		creditInfoManager.update(entity);
	}

}
