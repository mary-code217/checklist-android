package com.hoho.cheklist.dto.detail;

public class P1PhotoDto {
    private long id;
    private long p1ItemId;
    private String photoPath;

    private P1PhotoDto() {}

    public static P1PhotoDto create(long id, long p1ItemId, String path) {
        P1PhotoDto request = new P1PhotoDto();

        request.id = id;
        request.p1ItemId = p1ItemId;
        request.photoPath = path;

        return request;
    }

    public long getId() {
        return id;
    }

    public long getP1ItemId() {
        return p1ItemId;
    }

    public String getPhotoPath() {
        return photoPath;
    }
}