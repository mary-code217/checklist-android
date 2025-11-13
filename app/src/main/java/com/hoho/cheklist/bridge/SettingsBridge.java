package com.hoho.cheklist.bridge;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hoho.cheklist.dto.P1ItemRequest;
import com.hoho.cheklist.dto.P1SectionWithItems;
import com.hoho.cheklist.service.MasterService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SettingsBridge {
    private final WebView webView;
    private final MasterService masterService;
    private final ExecutorService io;

    public SettingsBridge(WebView webView, MasterService masterService, ExecutorService io) {
        this.webView = webView;
        this.masterService = masterService;
        this.io = io;
    }

    @JavascriptInterface
    public void loadSections() {
        io.execute(() -> {
            JSONArray arr = masterService.getP1Section();
            String json = arr.toString();
            String quoted = JSONObject.quote(json);
            String script = "(() => { const __d = " + quoted + "; window.setSections(JSON.parse(__d)); })()";
            webView.post(() -> webView.evaluateJavascript(script, null));
        });
    }

    @JavascriptInterface
    public void loadP1Structure() {
        io.execute(() -> {
            try {
                // 1) 서비스에서 전체 구조 조회 (대항목 + 하위항목)
                List<P1SectionWithItems> structure = masterService.loadP1Structure();

                // 2) JSON 변환
                String json = toJsonArray(structure).toString();

                // JS 문자열로 안전하게 이스케이프
                String quoted = JSONObject.quote(json);

                // 3) JS 코드 생성: window.setP1Structure([...]);
                String script =
                        "(() => { " +
                                "const __d = " + quoted + ";" +
                                "if (window.setP1Structure) {" +
                                "window.setP1Structure(JSON.parse(__d));" +
                                "} else {" +
                                "console.error('setP1Structure is not defined');" +
                                "}" +
                                "})()";

                // 4) 메인 스레드에서 실행
                webView.post(() -> webView.evaluateJavascript(script, null));
            } catch (Throwable t) {
                // ❗여기서 모든 예외를 먹고 JS 콘솔로만 보냄 (앱 안 죽게)
                Log.e(TAG, "loadP1Structure failed", t);

                String stack = Log.getStackTraceString(t);
                String quotedStack = JSONObject.quote(stack);

                String script =
                        "console.error('Android loadP1Structure error: ' + " + quotedStack + ");";

                webView.post(() -> webView.evaluateJavascript(script, null));
            }
        });
    }

    private JSONArray toJsonArray(List<P1SectionWithItems> structure) throws JSONException {
        JSONArray arr = new JSONArray();

        for (P1SectionWithItems entry : structure) {
            JSONObject obj = new JSONObject();

            // 대항목
            JSONObject sec = entry.getSection().toJson();
            // 하위항목들
            JSONArray array = new JSONArray();
            for (P1ItemRequest item : entry.getItems()) {
                array.put(item.toJson());
            }

            obj.put("section", sec);
            obj.put("items", array);

            arr.put(obj);
        }

        return arr;
    }
}
