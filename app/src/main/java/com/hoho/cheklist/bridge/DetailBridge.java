package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.hoho.cheklist.dto.detail.ChecklistDetailDto;
import com.hoho.cheklist.service.detail.DetailService;

import java.util.concurrent.ExecutorService;

public class DetailBridge {
    private final WebView webView;
    private final DetailService detailService;
    private final ExecutorService io;
    private final Gson gson = new Gson();

    public DetailBridge(WebView webView, DetailService detailService, ExecutorService io) {
        this.webView = webView;
        this.detailService = detailService;
        this.io = io;
    }

    /**
     * detail.html 에서 호출:
     *   detail.loadChecklistDetail(10);
     */
    @JavascriptInterface
    public void loadChecklistDetail(long checklistId) {
        io.execute(() -> {
            try {
                ChecklistDetailDto dto = detailService.getChecklistDetail(checklistId);
                String json = gson.toJson(dto);

                String script =
                        "(() => {"
                                + "  if (window.setChecklistDetail) {"
                                + "    window.setChecklistDetail(" + json + ");"
                                + "  } else {"
                                + "    console.error('setChecklistDetail is not defined');"
                                + "  }"
                                + "})()";

                webView.post(() -> webView.evaluateJavascript(script, null));

            } catch (Exception e) {
                e.printStackTrace();
                String errorScript =
                        "(() => {"
                                + "  console.error('loadChecklistDetail failed: " + checklistId + "');"
                                + "  if (window.onChecklistDetailError) {"
                                + "    window.onChecklistDetailError('ERROR');"
                                + "  }"
                                + "})()";
                webView.post(() -> webView.evaluateJavascript(errorScript, null));
            }
        });
    }

}
