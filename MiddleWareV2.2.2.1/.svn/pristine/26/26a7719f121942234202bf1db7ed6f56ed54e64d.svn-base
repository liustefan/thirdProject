package com.zkhk.task;

import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zkhk.dao.PushMessageDao;


/**
 * 定时删除表中的数据
 * @author bit
 *
 */
public class DeletePushMessage  extends TimerTask {
	private Logger logger=Logger.getLogger(DeletePushMessage.class);
	WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	PushMessageDao messageDao =  (PushMessageDao) wac.getBean("pushMessageDao");
	private String tagTime;
	
	public DeletePushMessage(String tagTime) {
		this.tagTime = tagTime;
	}

	@Override
	public void run() {
		try {
			messageDao.deletePushMessageByTagTime(tagTime);
			logger.info("定时删除数据成功");
		} catch (Exception e) {
			logger.error("定时删除数据异常");
		}
		
	}
	
	

}
