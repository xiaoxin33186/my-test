package com.ddjf.wechat.mini.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ddjf.BaseTestCase;
import com.ddjf.wechat.mini.vo.CreditUserVO;

public class SimpleCreditServiceTest extends BaseTestCase{
	
	@Autowired
	SimpleCreditService simpleCreditService;
	
	@Test
	public void checkCertInfoTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setName("冯平平");
		creditUserVO.setCerType("CER");
		creditUserVO.setCerNo("420621199411296613");
		creditUserVO.setSessionId("123456");
		simpleCreditService.checkCertInfo(creditUserVO);
	}
	
	@Test
	public void checkLoginNameTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setName("冯平平");
		creditUserVO.setCerType("CER");
		creditUserVO.setCerNo("420621199411296613");
		creditUserVO.setSessionId("123456");
		creditUserVO.setLoginName("15997206541");
		simpleCreditService.checkLoginName(creditUserVO);
	}
	
	@Test
	public void getMobileCodeTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setName("冯平平");
		creditUserVO.setCerType("CER");
		creditUserVO.setCerNo("420621199411296613");
		creditUserVO.setSessionId("123456");
		creditUserVO.setLoginName("15997206541");
		creditUserVO.setMobileTel("15997206541");
		creditUserVO.setPassword("a123456");
		simpleCreditService.getMobileCode(creditUserVO);
	}
	
	@Test
	public void userSignupTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setName("冯平平");
		creditUserVO.setCerType("CER");
		creditUserVO.setCerNo("420621199411296613");
		creditUserVO.setSessionId("123456");
		creditUserVO.setLoginName("15997206541");
		creditUserVO.setMobileTel("15997206541");
		creditUserVO.setPassword("a123456");
		creditUserVO.setSmsCode("esdafh");
		simpleCreditService.userSignup(creditUserVO);
	}
	
	@Test
	public void userLoginTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setSessionId("123456");
		/*
		creditUserVO.setLoginName("15997206541");
		creditUserVO.setPassword("a123456");
		*/
		
		creditUserVO.setLoginName("frez890");
		creditUserVO.setPassword("a18681534651");
		
		/*
		creditUserVO.setLoginName("zhaohuitest03");
		creditUserVO.setPassword("tan123456");
		*/
		simpleCreditService.userLogin(creditUserVO);
	}
	
	@Test
	public void questionListTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setSessionId("123456");
		/*
		creditUserVO.setLoginName("15997206541");
		creditUserVO.setPassword("a123456");
		*/
		/*
		creditUserVO.setLoginName("frez890");
		creditUserVO.setPassword("a18681534651");
		*/
		creditUserVO.setLoginName("zhaohuitest03");
		creditUserVO.setPassword("tan123456");
		
		simpleCreditService.questionList(creditUserVO);
	}
	
	@Test
	public void answerQuestionTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setSessionId("123456");
		
		simpleCreditService.answerQuestion(creditUserVO);
	}
	
	@Test
	public void getCreditInfoTest(){
		CreditUserVO creditUserVO = new CreditUserVO();
		creditUserVO.setSessionId("123456");
		
		simpleCreditService.getCreditInfo(creditUserVO);
	}
	
	
	
}
