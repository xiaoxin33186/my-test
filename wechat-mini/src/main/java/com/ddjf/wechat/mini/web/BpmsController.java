package com.ddjf.wechat.mini.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ddjf.wechat.mini.util.WechatMiniUtil;
import com.ddjf.wechat.modules.bpms.entity.OrderDetail;
import com.ddjf.wechat.modules.bpms.entity.OrderList;
import com.ddjf.wechat.modules.bpms.entity.OrderParam;
import com.ddjf.wechat.modules.bpms.entity.Page;
import com.ddjf.wechat.modules.bpms.manager.BpmsManager;
import com.ddjf.wechat.modules.mini.entity.UserInfo;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "/mini/bpms")
public class BpmsController extends BaseController {

	@Autowired
	BpmsManager bpmsManager;

	/**
	 * 得到订单列表
	 * 
	 * @param OrderParam
	 * @return
	 */
	@RequestMapping(value = "getOrderList", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderList(HttpServletRequest request, OrderParam orderParam) {
		try{
			UserInfo userInfo = WechatMiniUtil.getUserInfo(request);
			// 绑定类型01渠道信息,02业主信息，03买家信息
			if ("01".equals(userInfo.getBindType())) {
				String mobile = userInfo.getBindValue();
				// 核心系统channel（中介），own（业主），buy（买家）
				String userType = "channel";
				orderParam.setUserType(userType);
				orderParam.setMobile(mobile);
			} else if ("02".equals(userInfo.getBindType())) {
				String json = userInfo.getBindValue();
				OrderParam order = JSON.parseObject(json, OrderParam.class);// 业主和买家是json格式的信息
				String idType = order.getIdType();
				String idCode = order.getIdCode();
				String idName = order.getIdName();
				String userType = "own";
				orderParam.setIdType(idType);
				orderParam.setIdCode(idCode);
				orderParam.setIdName(idName);
				orderParam.setUserType(userType);
			} else if ("03".equals(userInfo.getBindType())){
				String json = userInfo.getBindValue();
				OrderParam order = JSON.parseObject(json, OrderParam.class);// 业主和买家是json格式的信息
				String idType = order.getIdType();
				String idCode = order.getIdCode();
				String idName = order.getIdName();
				String userType = "buy";
				orderParam.setIdType(idType);
				orderParam.setIdCode(idCode);
				orderParam.setIdName(idName);
				orderParam.setUserType(userType);
			} else {
				return JSON.toJSONString(new Page<OrderList>());
			}
			Page<OrderList> result = bpmsManager.getOrderList(orderParam);
			return JSON.toJSONString(result);
		} catch(Exception e){
			e.printStackTrace();
		}
		return JSON.toJSONString(new Page<OrderList>());
	}

	/**
	 * 订单详情
	 * 
	 * @param OrderParam
	 * @return
	 */
	@RequestMapping(value = "getOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public OrderDetail getOrderDetail(OrderParam orderParam) {
		return bpmsManager.getOrderDetail(orderParam);
	}

	/**
	 * 状态列表
	 * 
	 * @param applyNo
	 * @return
	 */
	@RequestMapping(value = "getOrderStatusList", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderStatusList(String applyNo, String productType, String applyStatus) {
		OrderParam orderParam = new OrderParam();
		orderParam.setApplyNo(applyNo);
		orderParam.setProductType(productType);
		orderParam.setStatusType(applyStatus);
		return JSON.toJSONString(bpmsManager.orderTuikeStatus(orderParam));
	}

}
