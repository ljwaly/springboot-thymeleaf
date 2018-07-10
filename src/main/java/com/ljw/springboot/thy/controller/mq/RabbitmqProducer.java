package com.ljw.springboot.thy.controller.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * Rabbitmq消息生产者
 * 
 * @author PC
 *
 */
@Component
public class RabbitmqProducer {

	private final static String QUEUE_NAME = "hello2";// 队列名不能重复 之前已有就会失败

	private final static String Routing_Key  = "p.event_hello2";// 发送者和接受者要一致
	
	private final static String Exchange  = "ljw123";// rabbitmq管理器中必须有
	
	private Channel channel;

	@PostConstruct
	public void init() {

		/* 使用工厂类建立Connection和Channel，并且设置参数 */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");// MQ的IP
		// factory.setPort(5672);// MQ端口
		factory.setUsername("ljwly");// MQ用户名
		factory.setPassword("1988ljw");// MQ密码

		try {
			Connection connection = factory.newConnection();
			channel = connection.createChannel();

			/* 创建消息队列，并且发送消息 */
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			sendMessage("启动测试：发送一个massage!");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		/* 关闭连接 */
		// channel.close();
		// connection.close();
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 * @return
	 */
	public boolean sendMessage(String msg) {
		try {
			channel.basicPublish(Exchange, Routing_Key, null, msg.getBytes());
			System.out.println("生产了个'" + msg + "'");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}