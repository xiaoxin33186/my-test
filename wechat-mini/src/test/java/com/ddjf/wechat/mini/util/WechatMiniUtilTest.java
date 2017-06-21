package com.ddjf.wechat.mini.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ddjf.BaseTestCase;
import com.ddjf.wechat.mini.vo.MiniTemplateData;
import com.ddjf.wechat.mini.vo.MiniTemplateMessage;

public class WechatMiniUtilTest extends BaseTestCase {
	
	@Test
	public void Test(){
		WechatMiniUtil.getAccessToken();
	}

	@Test
	public void sendTemplateMessageTest(){
		Map<String, MiniTemplateData> data = new HashMap<>();
		data.put("keyword1", new MiniTemplateData("查询征信报告"));
		data.put("keyword2", new MiniTemplateData("征信报告已生成，等待输入身份验证码"));
		data.put("keyword3", new MiniTemplateData("请点击本消息，输入短信中的身份验证码"));
		MiniTemplateMessage templateMessage = new MiniTemplateMessage();
		templateMessage.setFormId("1497442697759");
		templateMessage.setPage("pages/credit/accounts/accounts");
		templateMessage.setToUser("o7k8L0bX-Y1x8-c6_UeBLbu_RAhw");
		templateMessage.setTemplateId("2Cm2FSy1yphYD86TCk320rsglSwM00dngw4zlNTFT0o");
		templateMessage.setData(data);
		System.out.println(WechatMiniUtil.sendTemplateMessage(templateMessage));;
	}
	
}
