package com.ddjf.wechat.mini.util;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ddjf.wechat.common.util.DateUtil;
import com.ddjf.wechat.common.util.HttpUtil;
import com.ddjf.wechat.common.util.JsonUtil;
import com.ddjf.wechat.common.util.PropertyUtil;
import com.ddjf.wechat.common.util.RedisUtil;
import com.ddjf.wechat.common.util.StringUtil;

public class SimpleCreditUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleCreditUtil.class);
	
	/**
	 * 发送获取爬虫服务请求
	 * @param suffix
	 * @param json
	 * @return
	 */
	public static Map<String, Object> sendPost(String suffix, String json) {
		String url = PropertyUtil.get("simplecredit.url") + suffix;
		//String url = "http://10.1.108.124:9090" + suffix;
		String result = HttpUtil.sendHttpPostJson(url, json);
		logger.warn("url={}, json={}, result={}", url, json, result);
		return JsonUtil.jsonToMapObject(result);
	}
	
	/**
	 * 获取查询次数
	 * @param openId
	 * @param id
	 * @return
	 */
	public static int getQueryCount(String openId, Long id){
		int count;
		String key = "query_" + openId + "_" + id;
		String value = RedisUtil.get(key);
		if(StringUtil.isNotEmpty(value)){
			count = Integer.parseInt(value);
		} else {
			count = Integer.parseInt(PropertyUtil.get("query.count"));
		}
		count--;
		RedisUtil.set(key, count+"", getSeconds());
		logger.warn("key={}, count={}", key, count);
		return count;
	}
	
	/**
	 * 计算秒数
	 * @return
	 */
	public static long getSeconds(){
		Date date1 = new Date();
		String dateStr = DateUtil.formatDate(date1, "yyyy-MM-dd") + " 23:59:59";
		Date date2 = DateUtil.parseDate(dateStr);
		long time = (date2.getTime() - date1.getTime())/1000;
		return time;
	}

}
