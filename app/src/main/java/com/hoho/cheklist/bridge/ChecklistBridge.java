package com.hoho.cheklist.bridge;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.hoho.cheklist.dto.checklist.ChecklistPageView;
import com.hoho.cheklist.service.main.ChecklistModifyService;
import com.hoho.cheklist.service.main.ChecklistQueryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ChecklistBridge {
    private final WebView webView;
    private final ChecklistQueryService queryService;
    private final ChecklistModifyService modifyService;
    private final ExecutorService io;

    private final Gson gson = new Gson();

    public ChecklistBridge(WebView webView, ChecklistQueryService queryService,
                           ChecklistModifyService modifyService, ExecutorService io) {
        this.webView = webView;
        this.queryService = queryService;
        this.modifyService = modifyService;
        this.io = io;
    }

    @JavascriptInterface
    public void findChecklist() {
        // JS에서 page 안 넘기면 1페이지
        findChecklistInternal(1);
    }

    @JavascriptInterface
    public void findChecklist(int page) {
        // JS에서 Android.findChecklist(2); 이런 식으로 호출할 때
        findChecklistInternal(page);
    }

    // listView에서 제목 클릭시 해당 ID 체크리스트 디테일로 이동
    @JavascriptInterface
    public void openChecklistDetail(long id) {
        String url = "file:///android_asset/detail.html?id=" + id;
        webView.post(() -> webView.loadUrl(url));
    }

    // 실제 로직은 여기로 모음 (점검결과 리스트 조회 + 페이징 처리)
    private void findChecklistInternal(int page) {
        final int safePage = page <= 0 ? 1 : page;

        io.execute(() -> {
            ChecklistPageView view = queryService.getChecklistPage(safePage);

            final String jsonString = gson.toJson(view);

            webView.post(() -> {
                String script =
                        "if (window.checklist && typeof window.checklist.onFindChecklist === 'function') {" +
                                "  window.checklist.onFindChecklist(" + jsonString + ");" +
                                "}";

                webView.evaluateJavascript(script, null);
            });
        });
    }

    /**
     * 체크리스트 삭제 (단일/다수 공통)
     * JS에서: checklist.deleteChecklist(JSON.stringify([11,10,9]));
     */
    @JavascriptInterface
    public void deleteChecklist(String idsJson) {
        io.execute(() -> {
            JSONObject resultJson = new JSONObject();

            try {
                // 1) JSON 문자열 → List<Long>
                List<Long> ids = parseIdsJson(idsJson);

                if (ids.isEmpty()) {
                    // 빈 배열이면 바로 실패 응답
                    resultJson.put("success", false);
                    resultJson.put("deletedCount", 0);
                    resultJson.put("message", "삭제할 항목이 없습니다.");

                } else {
                    // 2) 서비스 호출
                    int deletedCount = modifyService.deleteChecklists(ids);

                    resultJson.put("success", true);
                    resultJson.put("deletedCount", deletedCount);
                    resultJson.put("message", "총 " + deletedCount + "건이 삭제되었습니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    resultJson.put("success", false);
                    resultJson.put("deletedCount", 0);
                    resultJson.put("message", "요청 데이터를 파싱하는 중 오류가 발생했습니다.");
                } catch (JSONException ex) {
                    // 무시
                }
            }

            final String jsonString = resultJson.toString();

            // 3) JS 콜백 호출 (항상 메인쓰레드에서)
            webView.post(() -> {
                String script =
                        "if (window.checklist && typeof window.checklist.onDeleted === 'function') {" +
                                "  window.checklist.onDeleted(" + jsonString + ");" +
                                "}";

                webView.evaluateJavascript(script, null);
            });
        });
    }

    // 삭제용 idsJson("[11,10,9]") → List<Long>
    private List<Long> parseIdsJson(String idsJson) throws JSONException {
        List<Long> ids = new ArrayList<>();

        if (idsJson == null || idsJson.trim().isEmpty()) {
            return ids;
        }

        JSONArray array = new JSONArray(idsJson);

        for (int i = 0; i < array.length(); i++) {
            // 숫자/문자 둘 다 받을 수 있도록 getLong 사용
            long id = array.getLong(i);
            ids.add(id);
        }

        return ids;
    }
}
