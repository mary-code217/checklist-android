package com.hoho.cheklist;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.hoho.cheklist.bridge.SettingsBridge;
import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.MasterRepository;
import com.hoho.cheklist.db.repository.UserRepository;
import com.hoho.cheklist.service.AuthService;
import com.hoho.cheklist.service.MasterService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ExecutorService io = Executors.newSingleThreadExecutor();

    private AuthService authService;
    private MasterService masterService;

    private UserRepository userRepository;
    private MasterRepository masterRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().deleteDatabase("app.db");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 시스템 바 Insets 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets ime  = insets.getInsets(WindowInsetsCompat.Type.ime());
            int bottom = Math.max(bars.bottom, ime.bottom); // 키보드가 올라오면 그 높이를 우선
            v.setPadding(bars.left, bars.top, bars.right, bottom);
            return insets;
        });

        // DB & Repository 초기화
        AppDBHelper dbHelper = new AppDBHelper(this);
        userRepository = new UserRepository(dbHelper);
        masterRepository = new MasterRepository(dbHelper);

        // Service 초기화
        authService = new AuthService(userRepository);
        masterService = new MasterService(masterRepository);

        // WebView 초기화 + 브릿지 등록
        initWebView();

        // 첫 화면: 로그인 페이지
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
        webView.addJavascriptInterface(new AuthBridge(webView, authService, io), "Auth");
        webView.addJavascriptInterface(new SettingsBridge(webView, masterRepository, io), "Setting");
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