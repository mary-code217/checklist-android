package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hoho.cheklist.service.user.AuthService;

import java.util.concurrent.ExecutorService;

/**
 * 인증/로그인 관련 브릿지
 * JS 네임스페이스: Auth
 * 사용 예)
 *  - JS: Auth.login('admin', '1q2w3e4r');
 */
public class AuthBridge {
    private final WebView webView;
    private final AuthService authService;
    private final ExecutorService io;

    public AuthBridge(WebView webView, AuthService authService, ExecutorService io) {
        this.webView = webView;
        this.authService = authService;
        this.io = io;
    }

    @JavascriptInterface
    public void login(String username, String password) {
        io.execute(() -> {
            boolean result = authService.login(username, password);

            String script = "window.onLoginResult && window.onLoginResult(" + result + ")";
            webView.post(() -> {
                webView.evaluateJavascript(script, null);
            });
        });
    }
}
