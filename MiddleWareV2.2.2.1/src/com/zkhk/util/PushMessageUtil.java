package com.zkhk.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkhk.constants.Constants;
import com.zkhk.entity.MessageData;
import com.zkhk.services.MeasureServiceImpl;

public class PushMessageUtil {
	private static Logger logger = Logger.getLogger(PushMessageUtil.class);

	/**
	 * 发送推送消息
	 * 
	 * @param message
	 * @throws Exception
	 */
	// ?data=#& roleIds=#& deviceType=#& platform=#&sign=#
	public static void sendMessage(MessageData data, String notifier)
			throws Exception {
		if (notifier != null && !"".equals(notifier)) {
			String[] notifiers = notifier.split(",");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", JSON.toJSONString(data));
			map.put("sender", data.getMemberId());
			map.put("memberIds", JSON.toJSONString(notifiers));
			map.put("deviceType", 0);
			map.put("platfrom", "baidu");
			map.put("sign", getSign(map));
			// 重复发送3
			for (int i = 0; i < Constants.PUSH_COUNT; i++) {
				try {
					String reslut = HttpTookit.doPost(Constants.PUSH_URL, map,
							Constants.PUSH_CHARSET);
					JSONObject json = JSONObject.parseObject(reslut);
					if (json.getIntValue("code") == 0) {
						logger.info("消息推送成功");
						break;
					}
				} catch (Exception e) {
				}
			}
		}
	}

	// sign=MD5(Base64(http://10.2.2.130:8081/push/MessengerSend)+
	// userId+Base64(123456)+ roleId+ Base64(1111)+ channeled+ Base64(0001)+
	// platform+Base64(baidu)+data+Base64(sendTxt))

	// MD5后 sign的值为：9D6CD7690A4762A903857FCF1A6CE68E
	/**
	 * 获取数字签名
	 * 
	 * @param paramMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSign(Map<String, Object> paramMap) throws Exception {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(PushUtils.encode(Constants.PUSH_URL
				.getBytes(Constants.PUSH_CHARSET)));
		// 循环请求参数
		for (Object key : paramMap.keySet()) {
			buffer.append(key);
			String value = (String) (paramMap.get(key) + "");
			// System.out.println(value);
			buffer.append(PushUtils.encode(value
					.getBytes(Constants.PUSH_CHARSET)));

		}
		// System.out.println(PushUtils.MD5(buffer.toString().getBytes(Constants.PUSH_CHARSET)));
		return PushUtils
				.MD5(buffer.toString().getBytes(Constants.PUSH_CHARSET));
	}

	public static void main(String[] args) throws Exception {
		PushMessageUtil.sendMessage(new MessageData(), "123,234,245");
	}
}