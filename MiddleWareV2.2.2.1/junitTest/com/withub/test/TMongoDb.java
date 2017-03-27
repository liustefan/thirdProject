package com.withub.test;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.gridfs.GridFSDBFile;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.services.MemService;


public class TMongoDb {
	
	private  ApplicationContext ctx = null;  
	MongoEntityDao med = null;
	MemService uis = null;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("spring-content.xml");
		med = (MongoEntityDao)ctx.getBean("mongoEntityDao");
		//uis = (MemService)ctx.getBean("userInfoService");
		
	}
	
	@Test
	public void save() throws Exception{		
		//UserInfo u = new UserInfo();
	//	u.setObjectId(UUID.randomUUID().toString());
		
	//	u.setUserName("qqqq");
		
		//med.saveOrUpdate(u);
		
//		ChannelInfo c = new ChannelInfo();
//		c.setObjectId(UUID.randomUUID().toString());
//		c.setChannelName("");	
//		c.setStatus(1);
//		c.setChannelType("top");
//		c.setComment(true);
//		med.saveOrUpdate(c);
		
		//uis.addUserInfo(u);
	}
	
	@Test
	public void find() throws Exception{
//		UserInfo u = med.getObject(UserInfo.class,"50a4d7df-3f74-4d22-994d-afdb4f94812f");
//		System.out.println(u.getId()+"===="+u.getUserName()+"======");
	}
	
	@Test
	public void findAll() throws Exception{
//	//	List<UserInfo> uList = med.findAll(UserInfo.class);
//		for(UserInfo u : uList){
//			System.out.println(u.getId()+"===="+u.getUserName()+"======");
//		}
	}
	
	@Test
	public void delete() throws Exception{
//		UserInfo u = med.getObject(UserInfo.class,"ff67d99a-6eb5-40c9-bd5d-36845ae877a9");
//		med.delete(u);
	}
	
	@Test
	public void getObjectByProperty() throws Exception{
//		UserInfo u = med.getObjectByProperty(UserInfo.class, "objectId", "51f4af304429de70c1ac01f6");
//		System.out.println(u.getId()+"===="+u.getUserName()+"======");
	}
	
	@Test
	public void listByProperty() throws Exception{
//		List<UserInfo> uList = med.listByProperty(UserInfo.class, "userName", "asdasda");
//		for(UserInfo u : uList){
//			System.out.println(u.getId()+"===="+u.getUserName()+"======");
//		}
	}
	
	
	@Test
	public void getFile() throws Exception{
		ObjectId id=new ObjectId("552634112dbf553e3924f8bc");
		GridFSDBFile file=	med.retrieveFileOne("fs",id );
		System.out.println(file.getFilename());
		
	}
	
	
	

}
