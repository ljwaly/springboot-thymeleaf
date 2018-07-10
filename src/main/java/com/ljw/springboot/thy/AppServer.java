package com.ljw.springboot.thy;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class AppServer {

	private static final Logger log = LoggerFactory.getLogger(AppServer.class);
	
	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRepository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) throws UnknownHostException {

		SpringApplication app = new SpringApplication(AppServer.class);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), 
				protocol, 
				env.getProperty("server.port"), 
				protocol,
				InetAddress.getLocalHost().getHostAddress(), 
				env.getProperty("server.port"), 
				env.getActiveProfiles());
	}

}
