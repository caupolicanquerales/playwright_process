package com.capo.playwright_process.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RenderHtmlServiceTest {
	
	@Autowired
	RenderHtmlService renderHtmlService;
	
	@Test
	public void renderHtmlToImageTest() {
		String data= rawHtml.trim();
		String result= renderHtmlService.renderHtmlToImage(data);
		System.out.println("Inicio :" +result);
		assertNotNull(result);
	}
	
	private String rawHtml= """
			'<style>.document-page {\n    background: white;\n    padding: 40px;\n    font-family: Arial, sans-serif;\n}\n\n.top-section-header {\n    display: flex;\n    justify-content: space-between;\n    border-bottom: 2px solid black;\n    height: 15%; \n}\n\n.logo-placeholder {\n    width: 100px;\n    height: 100px;\n    border: 2px dashed gray;\n}\n\n.content-split-layout {\n    display: flex;\n    flex-direction: row;\n    gap: 20px;\n    height: 70%;\n    margin-top: 20px;\n}\n\n.marketing-sidebar-left-30 {\n    flex: 3;\n    min-width: 30%;\n    background-color: #997777;\n}\n\n.invoice-details-right-70 {\n    flex: 7;\n    width: 70%;\n    padding-left: 20px;\n}\n\n.data-table {\n    width: 100%;\n    border-collapse: collapse;\n}\n\n.bottom-security-section {\n    display: flex;\n    flex-direction: column;\n    gap: 10px;\n    position: absolute;\n    bottom: 20px;\n    left: 40px;\n    right: 40px;\n}\n\n.qr-code-section {\n    display: flex;\n    flex-direction: row; \n    align-items: center; \n    gap: 15px; \n    border: 1px solid #eee;\n    padding: 5px;\n}\n\n.qr-code-zone {\n    width: 80px;\n    height: 80px;\n    background: #f0f0f0; \n    flex-shrink: 0;  \n}\n\n.qr-code-publicity {\n    min-width: 150px;   \n    height: 80px;\n    display: flex;\n    flex-direction: column;\n    justify-content: center;\n    font-size: 10px;\n    line-height: 1.2;\n}\n\n.barcode-zone {\n    width: 80px;\n    height: 80px;\n    background: white;\n    border: none;\n}\n\n.scannable-wrapper {\n    display: flex;\n    justify-content: space-between;\n    align-items: flex-end;\n    width: 100%;\n}\n\n.publicity-footer-banner {\n    width: 100%;\n    border-top: 1pt solid #000;\n    padding-top: 5px;\n    text-align: center;\n    font-size: 8pt;\n}\n</style>\n\n    \n        <meta charset="UTF-8">\n        <meta name="viewport" content="width=device-width, initial-scale=1.0">\n        <title>My Plantilla</title>\n    \n\n        <div class="document-page letter-size">\n            <header class="top-section-header">\n                <div class="logo-placeholder">\n                    <img th:src="\'data:image/png;base64,\' + ${logoBase64}" style="width: 100%; height: 100%; object-fit: contain;" alt="Company Logo">\n                </div>\n                <div class="invoice-title-middle">\n                    <h1 th:text="${invoiceTitle}">INVOICE</h1>\n                    <p>ID: <span th:text="${invoiceId}">#12345</span></p>\n                    <p>DATE: <span th:text="${invoiceDate}">2026-01-05</span></p>\n                </div>\n                <div class="business-info-right">\n                    <p th:text="${businessName}">Business Name LLC</p>\n                    <p th:text="${businessAddress}">123 Street, City</p>\n                </div>\n            </header>\n\n            <main class="content-split-layout">\n                <aside class="marketing-sidebar-right-30">\n                    <div class="advertisement-box">\n                        <img th:src="\'data:image/png;base64,\' + ${promoImageBase64}" style="width: 100%; height: 100%; object-fit: cover; display: block;" alt="Promotional Banner">\n                    </div>\n                </aside>\n                <section class="invoice-details-left-70">\n                    <table class="data-table">\n                        <thead>\n                            <tr><th>Description</th><th>Qty</th><th>Price</th></tr>\n                        </thead>\n                        <tbody>\n                            <tr><td>Product A</td><td>1</td><td>$50</td></tr>\n                        </tbody>\n                    </table>\n                    <div th:text="${paymentStatus}" class="payment-status-footer">STATUS: PAID</div>\n                </section>\n            </main>\n\n            <footer class="bottom-security-section">\n                <div class="scannable-wrapper">\n                    <div class="qr-code-section">\n                        <div class="qr-code-zone" id="qr_anchor"></div> \n                        <div class="qr-code-publicity" id="publicity_anchor">\n                            <span class="marketing-text"></span>\n                        </div>\n                    </div>\n                    <div class="barcode-zone"></div>\n                </div>\n                <div class="publicity-footer-banner">\n                    <span class="secondary-promo-text"></span>\n                </div>\n            </footer>\n        </div>\n    \n\n'
			
			"""; 
	
}
