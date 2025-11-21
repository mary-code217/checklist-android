package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.hoho.cheklist.dto.P1.P1SectionWithItems;
import com.hoho.cheklist.service.template.P1TemplateService;

import java.util.concurrent.ExecutorService;

public class P1TemplateBridge {
    private final WebView webView;
    private final P1TemplateService p1TemplateService;
    private final ExecutorService io;
    private final Gson gson = new Gson();

    public P1TemplateBridge(WebView webView, P1TemplateService p1TemplateService, ExecutorService io) {
        this.webView = webView;
        this.p1TemplateService = p1TemplateService;
        this.io = io;
    }

    @JavascriptInterface
    public void loadTemplate(int sectionNo) {
        io.execute(() -> {
            P1SectionWithItems data = p1TemplateService.loadP1Section(sectionNo);

            // JSON 직렬화
            final String json = gson.toJson(data);

            // UI 스레드에서 JS 함수 호출
            webView.post(() -> {
                String script = "window.onP1TemplateLoaded(" + json + ");";
                webView.evaluateJavascript(script, null);
            });
        });
    }

    /**
     * p1 템플릿 한 섹션 저장
     * JS 예:
     * const page = { section: {...}, items: [...] };
     * P1Template.saveTemplate(JSON.stringify(page));
     * <p>
     * 콜백:
     * window.onP1TemplateSaved();
     * // 실패시: window.onP1TemplateSaveFailed(msg);
     */
    @JavascriptInterface
    public void saveTemplate(final String json) {
        io.execute(() -> {
            try {
                // 1) JSON -> DTO 변환
                P1SectionWithItems page = gson.fromJson(json, P1SectionWithItems.class);
                if (page == null || page.getSection() == null) {
                    // 잘못된 데이터일 때 실패 콜백
                    webView.post(() -> webView.evaluateJavascript(
                            "window.onP1TemplateSaveFailed && window.onP1TemplateSaveFailed('invalid payload');",
                            null
                    ));
                    return;
                }

                // 2) 서비스 통해 DB 업데이트 (섹션 + 하위항목)
                p1TemplateService.UpdateP1Section(page);

                // 3) 성공 콜백
                webView.post(() -> webView.evaluateJavascript(
                        "window.onP1TemplateSaved && window.onP1TemplateSaved();",
                        null
                ));
            } catch (Exception e) {
                e.printStackTrace();
                // 4) 실패 콜백
                String msg = "'" + e.getMessage() + "'";
                webView.post(() -> webView.evaluateJavascript(
                        "window.onP1TemplateSaveFailed && window.onP1TemplateSaveFailed(" + msg + ");",
                        null
                ));
            }
        });
    }
}