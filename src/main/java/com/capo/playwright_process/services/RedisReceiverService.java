package com.capo.playwright_process.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.capo.redis_object.ImageRedisRequest;
import com.capo.redis_object.ImageRedisResponse;

@Service
public class RedisReceiverService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final ChannelTopic responseTopic;
	private final ExecutingProcessRenderHtml exexutingProcessRender;
	
	public RedisReceiverService(RedisTemplate<String, Object> redisTemplate,
			@Qualifier("responseTopic") ChannelTopic responseTopic, ExecutingProcessRenderHtml exexutingProcessRender) {
		this.redisTemplate= redisTemplate;
		this.responseTopic= responseTopic;
		this.exexutingProcessRender= exexutingProcessRender;
	}
	
	public void receiveMessage(Map<String,String> message) {
		String requestId = message.get("id");
		try {
			ImageRedisRequest information = (ImageRedisRequest) redisTemplate.opsForValue().get(requestId);
			if (information == null) {
				sendErrorNotification(requestId);
				return;
			}
			String imageBase64 = exexutingProcessRender.rederingHtmlImage(information);
			String uuid = getUUID();
			saveImageTemplate(uuid, imageBase64);
			sendIdResultNotification(uuid, requestId);
		} catch (Exception e) {
			e.printStackTrace();
			sendErrorNotification(requestId);
		}
	}
	
	private void sendIdResultNotification(String id, String sessionId) {
		Map<String,String> payload= new HashMap<>();
		payload.put("id", id);
		payload.put("sessionId", sessionId);
		payload.put("status", "COMPLETED");
		redisTemplate.convertAndSend(responseTopic.getTopic(), payload);
	}

	private void sendErrorNotification(String requestId) {
		Map<String,String> payload = new HashMap<>();
		payload.put("id", requestId != null ? requestId : "");
		payload.put("status", "ERROR");
		redisTemplate.convertAndSend(responseTopic.getTopic(), payload);
	}
	
	private void saveImageTemplate(String id, String imageBase64 ) {
		ImageRedisResponse response= new ImageRedisResponse();
		response.setImage(imageBase64);
		redisTemplate.opsForValue().set(id, response, 5, TimeUnit.MINUTES);
	}
	
	private String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
