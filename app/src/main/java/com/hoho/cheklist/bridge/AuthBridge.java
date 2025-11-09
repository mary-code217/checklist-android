package com.hoho.cheklist.bridge;

import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hoho.cheklist.db.repository.UserRepository;

import java.util.concurrent.ExecutorService;

/**
 * 인증/로그인 관련 브릿지
 * JS 네임스페이스: Auth
 * 사용 예)
 *  - JS: Auth.login('admin', '1234');
 */
public class AuthBridge {

    private final WebView webView;
    private final UserRepository userRepository;
    private final ExecutorService io;
    private final Handler ui = new Handler(Looper.getMainLooper());

    public AuthBridge(WebView webView,
                      UserRepository userRepository,
                      ExecutorService io) {
        this.webView = webView;
        this.userRepository = userRepository;
        this.io = io;
    }

    @JavascriptInterface
    public void login(String username, String password) {
        io.execute(() -> {
            boolean ok = userRepository.checkLogin(username, password);

            ui.post(() -> {
                // JS 콜백: 로그인 결과 전달
                webView.evaluateJavascript(
                        "window.onLoginResult && window.onLoginResult(" + ok + ")",
                        null
                );

                if (ok) {
                    // 로그인 성공 시: 페이지 선택 화면으로 이동 (점검하기 / 설정)
                    webView.loadUrl("file:///android_asset/main.html");
                }
            });
        });
    }
}
