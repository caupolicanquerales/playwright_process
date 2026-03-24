package com.capo.playwright_process.services;

import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DynamicJsonServiceTest {
	
	@InjectMocks
	private DynamicJsonService dynamicJsonService;
	
	@Test
	public void extractAllDataTest() {
		String data= rawData.trim();
		Map<String, Object> result= dynamicJsonService.extractAllData(data);
		assert(Objects.nonNull(result));
	} 
	
	private String rawData= """
			'<br><span style="color: rgb(0, 0, 0) !important; font-weight: bold;"><span><p><br></p><pre><code class="language-json">    {\n        "invoiceId": "INV-2025-001",</code></pre><pre><code class="language-json">        "invoiceTitle": "Factura Prueba",\n        "businessName": "Tech Solutions Inc.",\n        "businessAddress": "1234 Innovation Drive, Tech City, TX 75001",\n        "billing_date": "2023-10-15",\n        "invoiceDate": "2023-10-30",\n        "line_items": [\n            {\n                "description": "Premium Tier Subscription - October",\n                "unit_price": 150,\n                "quantity": 3,\n                "tax_rate": 7.5\n            },\n            {\n                "description": "5 hours of Senior Consultant time",\n                "unit_price": 80,\n                "quantity": 5,\n                "tax_rate": 7.5\n            }\n        ],\n        "paymentStatus": "Paid"\n    }<br></code></pre>\n</span></span>'
			"""; 
			
}
