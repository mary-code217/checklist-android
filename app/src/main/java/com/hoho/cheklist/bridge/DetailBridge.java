package com.hoho.cheklist.bridge;

import android.webkit.WebView;

import com.google.gson.Gson;
import com.hoho.cheklist.service.detail.DetailService;

import java.util.concurrent.ExecutorService;

public class DetailBridge {
    private final WebView webView;
    private final DetailService detailService;
    private final ExecutorService io;
    private final Gson gson = new Gson();

    public DetailBridge(WebView webView, DetailService detailService, ExecutorService io) {
        this.webView = webView;
        this.detailService = detailService;
        this.io = io;
    }
}
