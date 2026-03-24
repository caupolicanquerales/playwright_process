package com.capo.playwright_process.services;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.ScreenshotType;

@Service
public class RenderHtmlService {
	
	private final Browser browser; 
	
	public RenderHtmlService(Browser browser) {
		this.browser= browser;
	}
	
	public String renderHtmlToImage(String htmlContent) {
        try (BrowserContext context = browser.newContext();
            Page page = context.newPage()) {
            page.setContent(htmlContent);
            page.waitForLoadState(LoadState.NETWORKIDLE, new Page.WaitForLoadStateOptions().setTimeout(15000));
            byte[] imageData= page.screenshot(new Page.ScreenshotOptions()
                    .setType(ScreenshotType.PNG)
                    .setFullPage(true));
            return Base64.getEncoder().encodeToString(imageData);
        }
    }
}

