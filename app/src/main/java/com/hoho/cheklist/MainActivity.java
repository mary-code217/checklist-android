package com.hoho.cheklist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private WebView web;
    private AppDBHelper dbHelper;
    private UserRepository userRepository;
    private final ExecutorService io = Executors.newSingleThreadExecutor();


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DB 세팅
        dbHelper = new AppDBHelper(this);
        userRepository = new UserRepository(dbHelper);


        // WebView 세팅
        web = findViewById(R.id.webView);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);

        web.setWebViewClient(new WebViewClient()); // 외부 브라우저로 안튀게
        web.setWebChromeClient(new WebChromeClient()); // alert/console 등

        // JS 브릿지 연결
        web.addJavascriptInterface(new Bridge(), "Android");

        // 로그인 화면 로드
        web.loadUrl("file:///android_asset/index.html");
    }

    private class Bridge {
        private final Handler ui = new Handler(Looper.getMainLooper());

        @JavascriptInterface
        public void login(String username, String password) {
            io.execute(() -> {
                boolean ok = userRepository.checkLogin(username, password);
                ui.post(() -> {
                    // (선택) JS 콜백: 실패 메시지 표시용
                    web.evaluateJavascript(
                            "window.onLoginResult && window.onLoginResult(" + ok + ")", null);

                    // 성공 시 메인 화면으로 이동
                    if (ok) {
                        web.loadUrl("file:///android_asset/main.html");
                    }
                });
            });
        }
    }
}