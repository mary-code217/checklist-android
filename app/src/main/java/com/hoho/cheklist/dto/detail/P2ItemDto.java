package com.hoho.cheklist.dto.detail;

public class P2ItemDto {
    private long id;
    private long checklistId;
    private int itemNo;
    private String label;
    private String result;
    private String remark;

    private P2ItemDto() {}

    public static P2ItemDto create(long id, long checklistId, int itemNo,
                                   String label, String result, String remark) {
        P2ItemDto request = new P2ItemDto();

        request.id = id;
        request.checklistId = checklistId;
        request.itemNo = itemNo;
        request.label = label;
        request.result = result;
        request.remark = remark;

        return request;
    }

    public long getId() {
        return id;
    }

    public long getChecklistId() {
        return checklistId;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getLabel() {
        return label;
    }

    public String getResult() {
        return result;
    }

    public String getRemark() {
        return remark;
    }
}
