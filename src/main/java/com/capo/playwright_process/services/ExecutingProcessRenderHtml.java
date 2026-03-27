package com.capo.playwright_process.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
	
	public String rederingHtmlImage(String rawHtml, String rawData) {
		Map<String, Object> mapVariables= dynamicJsonService.extractAllData(rawData);
		Context context = new Context();
		context.setVariables(mapVariables);
		String htmlTemplate= templateEngine.process(rawHtml,context);
		return renderHtmlService.renderHtmlToImage(htmlTemplate);	
	}
}
