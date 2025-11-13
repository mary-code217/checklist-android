package com.hoho.cheklist.bridge;

import android.webkit.WebView;

import com.hoho.cheklist.service.ChecklistService;

import java.util.concurrent.ExecutorService;

public class ChecklistBridge {
    private final WebView webView;
    private final ChecklistService checklistService;
    private final ExecutorService io;

    public ChecklistBridge(WebView webView, ChecklistService checklistService, ExecutorService io) {
        this.webView = webView;
        this.checklistService = checklistService;
        this.io = io;
    }


}
