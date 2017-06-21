package com.ddjf.wechat.mini.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.ddjf.wechat.mini.vo.CreditUserVO;
import com.ddjf.wechat.modules.mini.entity.CreditInfo;

public class DataConvert {
	
	public static List<CreditUserVO> convertCreditInfo(List<CreditInfo> ciList){
		List<CreditUserVO> cuvList = new ArrayList<>();
		CreditUserVO cuv;
		try {
			for(CreditInfo ci : ciList){
				cuv = new CreditUserVO();
				BeanUtilsBean.getInstance().copyProperties(cuv, ci);
				cuv.setPassword(null);
				cuvList.add(cuv);
			} 
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return cuvList;
	}
	
	public static CreditInfo convertCreditUserVO(CreditUserVO creditUserVO){
		CreditInfo creditInfo = new CreditInfo();
		try {
			BeanUtilsBean.getInstance().copyProperties(creditInfo, creditUserVO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return creditInfo;
		
	}
}
