package com.ddjf.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.ddjf.handler.AbstractAsyncHandler;
import com.ddjf.wechat.common.util.DateUtil;
import com.ddjf.wechat.common.util.MsgSendUtil;
import com.ddjf.wechat.modules.mini.entity.CreditInfo;
import com.ddjf.wechat.modules.mini.manager.CreditInfoManager;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;



/**
 * 业务相关的作业调度
 * 
           字段               允许值                           允许的特殊字符
	秒	 	0-59	 	, - * /
	分	 	0-59	 	, - * /
	小时	 	0-23	 	, - * /
	日期	 	1-31	 	, - * ? / L W C
	月份	 	1-12 或者 JAN-DEC	 	, - * /
	星期	 	1-7 或者 SUN-SAT	 	, - * ? / L C #
	年（可选）	 	留空, 1970-2099	 	, - * /
	
	*  字符代表所有可能的值
	/  字符用来指定数值的增量
	L  字符仅被用于天（月）和天（星期）两个子表达式，表示一个月的最后一天或者一个星期的最后一天
	6L 可以表示倒数第６天
	
 * @Frez
 *
 */
@Service
@EnableScheduling
public class MessageSendTask {
		
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(cron = "0 0 10 * * ? ")
	public void sendTemplateMessage(){
		String time = DateUtil.formatDate(new Date());
		try{
			CreditInfoManager creditInfoManager = SpringContextHolder.getBean("creditInfoManager", CreditInfoManager.class);
			ThreadPoolTaskExecutor threadPoolExecutor = SpringContextHolder.getBean("threadPoolExecutor", ThreadPoolTaskExecutor.class);
			CreditInfo creditInfo = new CreditInfo();
			creditInfo.setSendFlag("01");
			List<CreditInfo> creditInfoList = creditInfoManager.findCreditInfoList(creditInfo);
			logger.warn("creditInfoList.size()={}", creditInfoList.size());
			for(CreditInfo entity : creditInfoList){
				AbstractAsyncHandler handler = SpringContextHolder.getBean("messageHandler", AbstractAsyncHandler.class);
				handler.setObj(entity);
				threadPoolExecutor.execute(handler);
			}
			MsgSendUtil.sendSMSMsg("18681534651", "【大道金服】小程序模板消息处理成功，共有"+creditInfoList.size()+"条模板消息, "+time);
		} catch(Exception e){
			MsgSendUtil.sendSMSMsg("18681534651", "【大道金服】小程序模板消息处理失败，"+time );
			e.printStackTrace();
		}
	}
}
