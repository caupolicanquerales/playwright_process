package com.capo.playwright_process.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.capo.playwright_process.request.ImageRedisRequest;
import com.capo.playwright_process.response.ImageRedisResponse;

@Service
public class RedisReceiverService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	private final ChannelTopic responseTopic;
	private final ExexutingProcessRenderHtml exexutingProcessRender;
	
	public RedisReceiverService(RedisTemplate<String, Object> redisTemplate,
			ChannelTopic responseTopic, ExexutingProcessRenderHtml exexutingProcessRender) {
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
			String imageBase64 = exexutingProcessRender.rederingHtmlImage(information.getHtml(), information.getData());
			String uuid = getUUID();
			saveImageTemplate(uuid, imageBase64);
			sendIdResultNotification(uuid);
		} catch (Exception e) {
			e.printStackTrace();
			sendErrorNotification(requestId);
		}
	}
	
	private void sendIdResultNotification(String id) {
		Map<String,String> payload= new HashMap<>();
		payload.put("id", id);
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
