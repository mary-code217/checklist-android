package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;

import com.hoho.cheklist.MainActivity;

public class SaveBridge {
    private final MainActivity activity;

    public SaveBridge(MainActivity activity) {
        this.activity = activity;
    }

    // JS에서 호출할 메서드
    @JavascriptInterface
    public void pickImage() {
        // JS에서 박스를 눌렀을 때 여기로 들어옴
        System.out.println("★ JS에서 pickImage() 호출됨");

        activity.runOnUiThread(activity::showImagePickDialog);
    }
}
