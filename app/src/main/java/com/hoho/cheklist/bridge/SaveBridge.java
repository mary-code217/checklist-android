package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.hoho.cheklist.dto.checklist.ChecklistHeaderSaveRequest;
import com.hoho.cheklist.service.save.HeaderSaveService;

import java.util.concurrent.ExecutorService;

public class SaveBridge {
    private final WebView webView;
    private final HeaderSaveService headerSaveService;
    private final ExecutorService io;
    private final Gson gson = new Gson();

    public SaveBridge(WebView webView, HeaderSaveService headerSaveService, ExecutorService io) {
        this.webView = webView;
        this.headerSaveService = headerSaveService;
        this.io = io;
    }

    /**
     * JS에서 넘어온 JSON 문자열을 파싱해서
     * 헤더 저장 후, 생성된 checklist id를 JS로 다시 보내준다.
     */
    @JavascriptInterface
    public void saveChecklistHeader(String json) {
        io.execute(() -> {
            try {
                // 1) JSON → 요청 DTO
                ChecklistHeaderSaveRequest req =
                        gson.fromJson(json, ChecklistHeaderSaveRequest.class);

                // 2) 서비스 호출해서 DB 저장 (id 리턴받는다고 가정)
                long newId = headerSaveService.createChecklistHeader(req);

                // 3) UI 스레드에서 JS 콜백 호출
                webView.post(() -> {
                    // JS 쪽에 정의해둘 콜백: window.onChecklistHeaderSaved(id)
                    String script = "window.onChecklistHeaderSaved(" + newId + ");";
                    webView.evaluateJavascript(script, null);
                });

            } catch (Exception e) {
                e.printStackTrace();

                webView.post(() -> {
                    // 실패 시 -1을 넘겨서 JS 쪽에서 에러 처리
                    String script = "window.onChecklistHeaderSaved(-1);";
                    webView.evaluateJavascript(script, null);
                });
            }
        });
    }
}
