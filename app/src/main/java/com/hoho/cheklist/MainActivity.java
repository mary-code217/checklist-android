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

import com.hoho.cheklist.bridge.AuthBridge;
import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private AppDBHelper dbHelper;
    private UserRepository userRepository;
    private ExecutorService io;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 시스템 바 Insets 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1) DB & Repository 초기화
        dbHelper = new AppDBHelper(this);
        userRepository = new UserRepository(dbHelper);
        io = Executors.newSingleThreadExecutor();

        // 2) WebView 초기화 + 브릿지 등록
        initWebView();

        // 3) 첫 화면: 로그인 페이지
        loadLoginPage();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = findViewById(R.id.webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient()); // 외부 브라우저로 안튀게
        webView.setWebChromeClient(new WebChromeClient()); // alert/console 등

        // JS 브릿지 연결(모듈 별로 분리)
        webView.addJavascriptInterface(new AuthBridge(webView, userRepository, io), "Auth");
    }

    private void loadLoginPage() {
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (io != null && !io.isShutdown()) {
            io.shutdown();
        }
    }
}