package com.hoho.cheklist.bridge;

import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hoho.cheklist.db.repository.MasterRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;

public class SettingsBridge {
    private final WebView webView;
    private final MasterRepository masterRepository;
    private final ExecutorService io;
    private final Handler ui = new Handler(Looper.getMainLooper());

    public SettingsBridge(WebView webView, MasterRepository masterRepository, ExecutorService io) {
        this.webView = webView;
        this.masterRepository = masterRepository;
        this.io = io;
    }

    @JavascriptInterface
    public void loadSections() {
        JSONArray arr = masterRepository.findP1Section();

        String json = arr.toString();
        String quoted = JSONObject.quote(json);

        String script = "(() => { const __d = " + quoted + "; window.setSections(JSON.parse(__d)); })()";
        webView.post(() -> webView.evaluateJavascript(script, null));
    }
}
