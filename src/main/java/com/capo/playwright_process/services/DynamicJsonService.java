package com.capo.playwright_process.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DynamicJsonService {
	
	private final ObjectMapper mapper = new ObjectMapper();

	public Map<String, Object> extractAllData(String rawHtml) {
        try {
            String cleanText = Jsoup.parse(rawHtml).text();
            int start = -1;
            int end = -1;
            int depth = 0;
            for (int i = 0; i < cleanText.length(); i++) {
                char c = cleanText.charAt(i);
                if (c == '{') {
                    if (depth == 0) start = i;
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && start != -1) {
                        end = i;
                        break;
                    }
                }
            }
            if (start == -1 || end == -1) {
                return Collections.emptyMap();
            }
            String cleanJson = cleanText.substring(start, end + 1);
            JsonNode rootNode = mapper.readTree(cleanJson);
            Object result = processNode(rootNode);
            
            if (result instanceof Map) {
                return (Map<String, Object>) result;
            } else {
                return Map.of("items", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private Object processNode(JsonNode node) {
        if (node.isObject()) {
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, JsonNode> entry : node.properties()) {
                map.put(entry.getKey(), processNode(entry.getValue()));
            }
            return map;
        } else if (node.isArray()) {
            List<Object> list = new ArrayList<>();
            for (JsonNode element : node) {
                list.add(processNode(element));
            }
            return list;
        } else {
            return getValue(node);
        }
    }

    private Object getValue(JsonNode node) {
        if (node.isNumber()) return node.numberValue();
        if (node.isBoolean()) return node.booleanValue();
        return node.asText();
    }
}
