package com.hoho.cheklist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hoho.cheklist.bridge.AuthBridge;
import com.hoho.cheklist.bridge.ChecklistBridge;
import com.hoho.cheklist.bridge.DetailBridge;
import com.hoho.cheklist.bridge.P1TemplateBridge;
import com.hoho.cheklist.bridge.PhotoBridge;
import com.hoho.cheklist.bridge.SettingsBridge;
import com.hoho.cheklist.db.AppDBHelper;
import com.hoho.cheklist.service.detail.DetailService;
import com.hoho.cheklist.service.main.ChecklistModifyService;
import com.hoho.cheklist.service.main.ChecklistQueryService;
import com.hoho.cheklist.service.master.MasterService;
import com.hoho.cheklist.service.save.P1PhotoService;
import com.hoho.cheklist.service.save.P2PhotoService;
import com.hoho.cheklist.service.template.P1TemplateService;
import com.hoho.cheklist.service.user.AuthService;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_GALLERY = 1001;
    private static final int REQ_CAMERA  = 1002;
    private static final int REQ_CAMERA_PERMISSION = 2001;

    private WebView webView;
    private ExecutorService io = Executors.newSingleThreadExecutor();

    private AuthService authService;
    private MasterService masterService;
    private ChecklistQueryService checklistQueryService;
    private ChecklistModifyService checklistModifyService;
    private P1TemplateService p1TemplateService;
    private DetailService detailService;
    private P1PhotoService p1PhotoService;
    private P2PhotoService p2PhotoService;

    private PhotoBridge photoBridge;
    private Uri cameraImageUri;
    private String cameraImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().deleteDatabase("app.db");
        deleteAllAppPictures();

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

        // DB 초기화
        AppDBHelper dbHelper = new AppDBHelper(this);

        // Service 초기화
        authService = new AuthService(dbHelper);
        masterService = new MasterService(dbHelper);
        checklistQueryService = new ChecklistQueryService(dbHelper);
        checklistModifyService = new ChecklistModifyService(dbHelper);
        p1TemplateService = new P1TemplateService(dbHelper);
        detailService = new DetailService(dbHelper);
        p1PhotoService = new P1PhotoService(dbHelper);
        p2PhotoService = new P2PhotoService(dbHelper);

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

        // 로컬 파일 접근 허용 (기본값이 true인 경우가 많지만 명시해두면 좋음)
        s.setAllowFileAccess(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient()); // 외부 브라우저로 안튀게
        webView.setWebChromeClient(new WebChromeClient()); // alert/console 등

        photoBridge = new PhotoBridge(this, webView, p1PhotoService, p2PhotoService);

        // JS 브릿지 연결(모듈 별로 분리)
        webView.addJavascriptInterface(new AuthBridge(webView, authService, io), "Auth");
        webView.addJavascriptInterface(new SettingsBridge(webView, masterService, io), "Setting");
        webView.addJavascriptInterface(new ChecklistBridge(webView, checklistQueryService, checklistModifyService, io), "Android");
        webView.addJavascriptInterface(new P1TemplateBridge(webView, p1TemplateService, io), "P1Template");
        webView.addJavascriptInterface(new DetailBridge(webView, detailService, io), "detail");
//        webView.addJavascriptInterface(new SaveBridge(this), "photo");
        webView.addJavascriptInterface(photoBridge, "photo");
    }

    private void loadLoginPage() {
        webView.loadUrl("file:///android_asset/index.html");
    }

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
        // 1) CAMERA 권한 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // 권한 없으면 요청하고, 여기서 바로 종료
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    REQ_CAMERA_PERMISSION
            );
            return;
        }

        // 2) 권한이 이미 허용된 상태면 실제 카메라 열기
        try {
            File photoFile = createImageFile();
            cameraImagePath = photoFile.getAbsolutePath();
            cameraImageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    photoFile
            );

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, REQ_CAMERA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String fileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    private String copyImageFromUri(Uri uri) throws IOException {
        // 카메라에서 쓰던 것과 같은 위치에 새 파일 생성
        File dst = createImageFile();

        try (InputStream in = getContentResolver().openInputStream(uri);
             OutputStream out = new FileOutputStream(dst)) {

            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        }

        // 이 경로를 DB에 저장하고, PhotoBridge/JS로 넘긴다
        return dst.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQ_GALLERY) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();

                try {
                    // 1) 갤러리 원본을 우리 앱 전용 폴더로 복사
                    String savedPath = copyImageFromUri(uri);

                    // 2) 복사된 파일 경로를 PhotoBridge로 전달 → DB 저장 + JS 썸네일 콜백
                    if (photoBridge != null) {
                        photoBridge.onImageSelectedFromGallery(savedPath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQ_CAMERA) {
            if (photoBridge != null && cameraImagePath != null) {
                photoBridge.onImageCapturedFromCamera(cameraImagePath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용 → 다시 카메라 오픈
                openCamera();
            } else {
                // 권한 거절 → 필요하면 안내
                // 예: Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteAllAppPictures() {
        File dir = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        if (dir == null || !dir.exists()) {
            return;
        }

        // 하위 파일/폴더 모두 삭제
        deleteRecursively(dir);
    }

    /**
     * 파일/폴더 재귀 삭제
     */
    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (io != null && !io.isShutdown()) {
            io.shutdown();
        }
    }
}