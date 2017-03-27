package com.zkhk.rabBit;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class ClientSender {
	private static String QUEUE_NAME ;
	private static String HOST ;
	private static String PASSWORD ;
	private static String USERNAME ;
	private static int PORT ;
	private static String VIRTUAL_HOST;
//	private final static String HOST = "121.201.32.169";
//	private final static String PASSWORD = "guest";
//	private final static String USERNAME = "zkhkguest";
	
//	private final static String HOST = "121.201.32.169";
//	private final static String PASSWORD = "test";
//	private final static String USERNAME = "test";
	   static {   
		         Properties prop = new Properties();   
		        InputStream in = ClientSender.class.getResourceAsStream("/rabbit.properties");   
		         try {   
		             prop.load(in);   
		               QUEUE_NAME = prop.getProperty("rabbit.queue").trim();   
		               HOST=prop.getProperty("rabbit.host").trim();
		               PASSWORD=prop.getProperty("rabbit.password").trim();
		               USERNAME=prop.getProperty("rabbit.username").trim();
		               PORT=Integer.parseInt(prop.getProperty("rabbit.port").trim());
		               VIRTUAL_HOST=prop.getProperty("rabbit.virtualHost").trim();
		             //  param2 = prop.getProperty("initYears2").trim();   
		           } catch (IOException e) {   
		           }   
		       }   
		    

  
	private static Logger logger=Logger.getLogger(ClientSender.class);

	public static void sender(String message) throws IOException {	
		
		ConnectionFactory factory = new ConnectionFactory();
	
		factory.setHost(HOST);
		factory.setPort(PORT);
		factory.setPassword(PASSWORD);
		factory.setUsername(USERNAME);
		factory.setVirtualHost(VIRTUAL_HOST);
		factory.setConnectionTimeout(5000);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		channel.close();
		connection.close();
		logger.info("发送数据成功"+message);
	}
	
	public static void main(String[] args) throws IOException {
	  sender("123");
	}
	
	
}
