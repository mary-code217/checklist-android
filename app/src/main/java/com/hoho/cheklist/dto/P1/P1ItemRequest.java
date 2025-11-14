package com.hoho.cheklist.dto.P1;

import org.json.JSONException;
import org.json.JSONObject;

public class P1ItemRequest {
    private long id;
    private long sectionId;
    private int itemNo;

    private String evidence;
    private String detailDesc;
    private String goodCase;
    private String weakCase;

    private int useYn;
    private int sortOrder;

    private P1ItemRequest() {}

    public static P1ItemRequest create(
            long id, long sectionId, int itemNo,
            String evidence, String detailDesc, String goodCase,
            String weakCase, int useYn, int sortOrder) {
        P1ItemRequest request = new P1ItemRequest();

        request.id = id;
        request.sectionId = sectionId;
        request.itemNo = itemNo;
        request.evidence = evidence;
        request.detailDesc = detailDesc;
        request.goodCase = goodCase;
        request.weakCase = weakCase;
        request.useYn = useYn;
        request.sortOrder = sortOrder;

        return request;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();

        o.put("id", this.id);
        o.put("sectionId", this.sectionId);
        o.put("itemNo", this.itemNo);
        o.put("evidence", this.evidence);
        o.put("detailDesc", this.detailDesc);
        o.put("goodCase", this.goodCase);
        o.put("weakCase", this.weakCase);
        o.put("useYn", this.useYn);
        o.put("sortOrder", this.sortOrder);

        return o;
    }

    public long getId() {
        return id;
    }

    public long getSectionId() {
        return sectionId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public String getGoodCase() {
        return goodCase;
    }

    public String getWeakCase() {
        return weakCase;
    }

    public int getUseYn() {
        return useYn;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
