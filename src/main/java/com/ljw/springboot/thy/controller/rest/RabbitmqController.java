package com.ljw.springboot.thy.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ljw.springboot.thy.controller.mq.RabbitmqProducer;

@RestController
@RequestMapping("/rbmq")
public class RabbitmqController {

	@Autowired
	private RabbitmqProducer producer;
	
	
	@RequestMapping("/test")
	public Map<String, Object> testRabbitmq(@RequestParam (required = false)String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (msg == null || "".equals(msg)) {
			map.put("resultCode", "fail");
			map.put("resultDesc", "msg is null");
		}
		boolean result = producer.sendMessage(msg);
		map.put("resultCode", "success");
		map.put("resultDesc", result);
		return map;
	}
}
