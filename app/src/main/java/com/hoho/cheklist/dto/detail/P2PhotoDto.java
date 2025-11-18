package com.hoho.cheklist.dto.detail;

public class P2PhotoDto {
    private long id;
    private long checklistId;
    private String photoPath;

    private P2PhotoDto() {}

    public static P2PhotoDto create(long id, long checklistId, String photoPath) {
        P2PhotoDto request = new P2PhotoDto();

        request.id = id;
        request.checklistId = checklistId;
        request.photoPath = photoPath;

        return request;
    }

    public long getId() {
        return id;
    }

    public long getChecklistId() {
        return checklistId;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}
