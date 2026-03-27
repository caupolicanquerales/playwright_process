package com.capo.playwright_process.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.capo.redis_object.ImageRedisRequest;

@Service
public class ExexutingProcessRenderHtml {
	
	private final DynamicJsonService dynamicJsonService;
	private final RenderHtmlService renderHtmlService;
	private final TemplateEngine templateEngine;
	
	public ExexutingProcessRenderHtml(DynamicJsonService dynamicJsonService,
			RenderHtmlService renderHtmlService, TemplateEngine templateEngine) {
		this.dynamicJsonService= dynamicJsonService;
		this.renderHtmlService= renderHtmlService;
		this.templateEngine= templateEngine;
	}
	
	public String rederingHtmlImage(ImageRedisRequest imageRedis) {
		Map<String, Object> mapVariables= dynamicJsonService.extractAllData(Optional.ofNullable(imageRedis.getData()).orElse(""));
		Map<String, Object> mapVariablesImages= dynamicJsonService.extractAllData(Optional.ofNullable(imageRedis.getImages()).orElse(""));
		Map<String, Object> merged = new HashMap<>(mapVariables);
		merged.putAll(mapVariablesImages); 
		Context context = new Context();
		context.setVariables(merged);
		String htmlTemplate= templateEngine.process(imageRedis.getHtml(),context);
		return renderHtmlService.renderHtmlToImage(htmlTemplate);	
	}
	
	
}
