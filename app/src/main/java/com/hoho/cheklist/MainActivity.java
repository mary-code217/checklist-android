package com.hoho.cheklist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.hoho.cheklist.bridge.ChecklistBridge;
import com.hoho.cheklist.bridge.DetailBridge;
import com.hoho.cheklist.bridge.P1TemplateBridge;
import com.hoho.cheklist.bridge.SaveBridge;
import com.hoho.cheklist.bridge.SettingsBridge;
import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.db.repository.main.ChecklistRepository;
import com.hoho.cheklist.db.repository.master.MasterRepository;
import com.hoho.cheklist.db.repository.template.P1TemplateRepository;
import com.hoho.cheklist.db.repository.template.P2TemplateRepository;
import com.hoho.cheklist.db.repository.user.UserRepository;
import com.hoho.cheklist.service.detail.DetailService;
import com.hoho.cheklist.service.main.ChecklistModifyService;
import com.hoho.cheklist.service.main.ChecklistQueryService;
import com.hoho.cheklist.service.master.MasterService;
import com.hoho.cheklist.service.template.P1TemplateService;
import com.hoho.cheklist.service.user.AuthService;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_GALLERY = 1001;  // 앨범 요청 코드
    private static final int REQ_CAMERA  = 1002;  // 카메라 요청 코드

    private WebView webView;
    private ExecutorService io = Executors.newSingleThreadExecutor();

    private AuthService authService;
    private MasterService masterService;
    private ChecklistQueryService checklistQueryService;
    private ChecklistModifyService checklistModifyService;
    private P1TemplateService p1TemplateService;
    private DetailService detailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().deleteDatabase("app.db");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 시스템 바 Insets 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets ime = insets.getInsets(WindowInsetsCompat.Type.ime());
            int bottom = Math.max(bars.bottom, ime.bottom); // 키보드가 올라오면 그 높이를 우선
            v.setPadding(bars.left, bars.top, bars.right, bottom);
            return insets;
        });

        // DB & Repository 초기화
        AppDBHelper dbHelper = new AppDBHelper(this);
        UserRepository userRepository = new UserRepository(dbHelper);
        MasterRepository masterRepository = new MasterRepository(dbHelper);
        ChecklistRepository checklistRepository = new ChecklistRepository(dbHelper);
        P1TemplateRepository p1TemplateRepository = new P1TemplateRepository(dbHelper);
        P2TemplateRepository p2TemplateRepository = new P2TemplateRepository(dbHelper);

        // Service 초기화
        authService = new AuthService(userRepository);
        masterService = new MasterService(masterRepository);
        checklistQueryService = new ChecklistQueryService(checklistRepository);
        checklistModifyService = new ChecklistModifyService(checklistRepository);
        p1TemplateService = new P1TemplateService(dbHelper, p1TemplateRepository);
        detailService = new DetailService(dbHelper);

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
        webView.addJavascriptInterface(new SettingsBridge(webView, masterService, io), "Setting");
        webView.addJavascriptInterface(new ChecklistBridge(webView, checklistQueryService, checklistModifyService, io), "Android");
        webView.addJavascriptInterface(new P1TemplateBridge(webView, p1TemplateService, io), "P1Template");
        webView.addJavascriptInterface(new DetailBridge(webView, detailService, io), "detail");
        webView.addJavascriptInterface(new SaveBridge(this), "photo");
    }

    private void loadLoginPage() {
        webView.loadUrl("file:///android_asset/index.html");
    }

    // 🔹 브릿지에서 호출하는 메서드 (카메라/앨범 선택 다이얼로그)
    public void showImagePickDialog() {
        new AlertDialog.Builder(this)
                .setTitle("사진 선택")
                .setItems(new CharSequence[]{"카메라로 촬영", "앨범에서 선택"}, (dialog, which) -> {
                    if (which == 0) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(intent, REQ_GALLERY);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQ_GALLERY) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                System.out.println("★ GALLERY 선택 이미지 URI = " + uri);
                Log.d("MainActivity", "GALLERY URI = " + uri);
            }
        } else if (requestCode == REQ_CAMERA) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                System.out.println("★ CAMERA 선택 이미지 URI(data) = " + uri);
                Log.d("MainActivity", "CAMERA URI(data) = " + uri);
            } else {
                // 일부 기기에서는 data.getData()가 null이고, 썸네일이 data.getExtras()에 있을 수 있음
                System.out.println("★ CAMERA: data=null 또는 URI 없음 (썸네일만 올 수도 있음)");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (io != null && !io.isShutdown()) {
            io.shutdown();
        }
    }
}