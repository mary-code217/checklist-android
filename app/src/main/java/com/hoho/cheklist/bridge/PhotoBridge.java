package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hoho.cheklist.MainActivity;
import com.hoho.cheklist.dto.detail.P1PhotoDto;
import com.hoho.cheklist.dto.detail.P2PhotoDto;
import com.hoho.cheklist.service.save.P1PhotoService;
import com.hoho.cheklist.service.save.P2PhotoService;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PhotoBridge {
    private enum Mode {
        NONE,
        P1,
        P2
    }

    private final MainActivity activity;
    private final WebView webView;
    private final P1PhotoService p1PhotoService;
    private final P2PhotoService p2PhotoService;
    private final ExecutorService io;

    public PhotoBridge(MainActivity activity, WebView webView,
                       P1PhotoService p1PhotoService, P2PhotoService p2PhotoService,
                       ExecutorService io) {
        this.activity = activity;
        this.webView = webView;
        this.p1PhotoService = p1PhotoService;
        this.p2PhotoService = p2PhotoService;
        this.io = io;
    }

    private Mode currentMode = Mode.NONE;
    private long currentP1ItemId = -1L;
    private long currentChecklistId = -1L;
    private int currentSlot = -1;

    // JS → Android
    @JavascriptInterface
    public void pickP1Photo(long p1ItemId, int slotIndex) {
        currentMode = Mode.P1;
        currentP1ItemId = p1ItemId;
        currentChecklistId = -1L;
        currentSlot = slotIndex;

        activity.runOnUiThread(activity::showImagePickDialog);
    }

    // JS → Android
    @JavascriptInterface
    public void pickP2Photo(long checklistId, int slotIndex) {
        currentMode = Mode.P2;
        currentChecklistId = checklistId;
        currentP1ItemId = -1L;
        currentSlot = slotIndex;

        activity.runOnUiThread(activity::showImagePickDialog);
    }

    // =========================
    // Activity → Bridge
    // =========================

    /** 앨범에서 선택한 이미지의 최종 저장 경로 */
    public void onImageSelectedFromGallery(String savedPath) {
        if (currentMode == Mode.NONE) {
            return;
        }

        try {
            if (currentMode == Mode.P1) {
                P1PhotoDto dto = p1PhotoService.addPhoto(currentP1ItemId, savedPath);
                notifyJsP1(dto, currentSlot);
            } else if (currentMode == Mode.P2) {
                P2PhotoDto dto = p2PhotoService.addPhoto(currentChecklistId, savedPath);
                notifyJsP2(dto, currentSlot);
            }
        } finally {
            resetState();
        }
    }

    /** 카메라로 찍은 이미지의 최종 저장 경로 */
    public void onImageCapturedFromCamera(String savedPath) {
        if (currentMode == Mode.NONE) {
            return;
        }

        try {
            if (currentMode == Mode.P1) {
                P1PhotoDto dto = p1PhotoService.addPhoto(currentP1ItemId, savedPath);
                notifyJsP1(dto, currentSlot);
            } else if (currentMode == Mode.P2) {
                P2PhotoDto dto = p2PhotoService.addPhoto(currentChecklistId, savedPath);
                notifyJsP2(dto, currentSlot);
            }
        } finally {
            resetState();
        }
    }

    // =========================
    // JS 콜백 (썸네일 갱신)
    // =========================

    private void notifyJsP1(P1PhotoDto dto, int slotIndex) {
        if (webView == null) return;

        String path = dto.getPhotoPath();
        if (path == null) path = "";

        // file:// prefix 추가
        String url = path.startsWith("file:") ? path : "file://" + path;
        String safePath = url.replace("'", "\\'");

        String js = "window.setP1PhotoSlot("
                + slotIndex + ", '"
                + safePath + "', "
                + dto.getId()
                + ");";

        webView.post(() -> webView.evaluateJavascript(js, null));
    }

    private void notifyJsP2(P2PhotoDto dto, int slotIndex) {
        if (webView == null) return;

        String path = dto.getPhotoPath();
        if (path == null) path = "";

        String url = path.startsWith("file:") ? path : "file://" + path;
        String safePath = url.replace("'", "\\'");

        String js = "window.setP2PhotoSlot("
                + slotIndex + ", '"
                + safePath + "', "
                + dto.getId()
                + ");";

        webView.post(() -> webView.evaluateJavascript(js, null));
    }

    private void resetState() {
        currentMode = Mode.NONE;
        currentP1ItemId = -1L;
        currentChecklistId = -1L;
        currentSlot = -1;
    }

    /**
     * JS → 안드로이드
     * 현재 p1 항목(p1ItemId)에 대한 사진 목록 로딩 요청
     *  - JS: window.photo.loadP1Photos(p1ItemId)
     *  - 동작:
     *      1) DB에서 해당 항목의 사진 목록 조회
     *      2) 0~3번 슬롯에 순서대로 꽂아서
     *         window.setP1PhotoSlot(slotIndex, imagePath, photoId) 호출
     */
    @JavascriptInterface
    public void loadP1Photos(long p1ItemId) {
        // IO 스레드에서 DB 작업
        io.execute(() -> {
            List<P1PhotoDto> photos = p1PhotoService.findByP1ItemId(p1ItemId);

            // 정렬 기준: id 오름차순 (INSERT 순서)
            // (서비스나 레포지토리에서 이미 정렬했다면 생략 가능)
            // Collections.sort(photos, Comparator.comparingLong(P1PhotoDto::getId));

            // 최대 4장까지만 슬롯에 꽂기
            for (int i = 0; i < photos.size() && i < 4; i++) {
                P1PhotoDto dto = photos.get(i);

                // file:// prefix 붙이기 (이미 했다면 생략)
                String path = dto.getPhotoPath();
                if (path == null) path = "";
                String url = path.startsWith("file:") ? path : "file://" + path;
                String safePath = url.replace("'", "\\'");

                int slotIndex = i; // 0~3 슬롯

                String js = "window.setP1PhotoSlot("
                        + slotIndex + ", '"
                        + safePath + "', "
                        + dto.getId()
                        + ");";

                webView.post(() -> webView.evaluateJavascript(js, null));
            }
        });
    }
}
