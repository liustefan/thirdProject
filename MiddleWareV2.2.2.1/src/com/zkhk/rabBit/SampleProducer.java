package com.zkhk.rabBit;

import java.io.IOException;



import com.rabbitmq.client.Channel;

import com.rabbitmq.client.Connection;

import com.rabbitmq.client.ConnectionFactory;


public class SampleProducer implements Runnable {
	private final static String QUEUE_NAME = "aaaa";
	private final static String HOST = "192.169.10.27";
	private final static String PASSWORD = "guest";
	private final static String USERNAME = "guest";
	private final static int PORT = 55672;
	private final static String VIRTUAL_HOST= "/debug";
	  private final String message;

	    private static final String EXCHANGE_NAME = "test";

	    private static final String ROUTING_KEY = "test";

	    public SampleProducer(final String message) {

	       // TODO Auto-generated constructor stub

	       this.message = message;

	    }

	   
	    public void run() {

	       ConnectionFactory factory = new ConnectionFactory();

	       factory.setUsername(SampleProducer.USERNAME);

	       factory.setPassword(SampleProducer.PASSWORD);

	       factory.setHost(SampleProducer.HOST);

	       factory.setPort(SampleProducer.PORT);
		   
	       factory.setConnectionTimeout(5000);

	       Connection conn;

	       try{

	           conn = factory.newConnection();

	           Channel channel = conn.createChannel();

	           channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);//EXCHANGE 定义交换机

	           String queueName = channel.queueDeclare().getQueue();//message-queue得到消息队列

	           channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);//route-bind 定义类似路由器的东西路由交换机VS队列

	           System.out.println("生产者:"  +  message + " in thread:" + Thread.currentThread().getName());

	           //publish / sub   生产者的作用就是将消息推送到消息队列里面去 实现类似于publish的功能

	           channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());//将消息丢到队列里面。只是内容。这个消息的格式是否可以自行定义？比如定义一个JSON包或XML的包？比如一条发布的内容加一个代号？后台消费者会依据这个代号调用相应的号码进行处理掉？

	           channel.close();

	           conn.close();

	       } catch (IOException e) {
	       } 

	    }
	    
	    
	   public static void main(String[] args) {
		Thread thread=new Thread(new SampleProducer("test"));
		thread.start();
	}

	}
