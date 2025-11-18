package com.hoho.cheklist.dto.detail;

import java.util.ArrayList;
import java.util.List;

public class P1ItemDto {
    private long id;
    private long checklistId;
    private int sectionNo;
    private int itemNo;

    private String secCategory;
    private String secMainQuestion;
    private String secTarget;
    private String secDescription;
    private String secReference;

    private String evidence;
    private String detailDesc;
    private String goodCase;
    private String weakCase;

    private String result;
    private String remark;

    private List<P1PhotoDto> photos = new ArrayList<>();

    private P1ItemDto() {}

    public static P1ItemDto create(long id, long checklistId, int sectionNo,
                                   int itemNo, String secCategory, String secMainQuestion,
                                   String secTarget, String secDescription, String secReference,
                                   String evidence, String detailDesc, String goodCase, String weakCase,
                                   String result, String remark) {
        P1ItemDto request = new P1ItemDto();

        request.id = id;
        request.checklistId = checklistId;
        request.sectionNo = sectionNo;
        request.itemNo = itemNo;
        request.secCategory = secCategory;
        request.secMainQuestion = secMainQuestion;
        request.secTarget = secTarget;
        request.secDescription = secDescription;
        request.secReference = secReference;
        request.evidence = evidence;
        request.detailDesc = detailDesc;
        request.goodCase = goodCase;
        request.weakCase = weakCase;
        request.result = result;
        request.remark = remark;

        return request;
    }

    public void addPhotos(List<P1PhotoDto> photos) {
        this.photos = (photos != null) ? photos : new ArrayList<>();
    }

    public void addPhoto(P1PhotoDto photo) {
        if (photo == null) return;
        this.photos.add(photo);
    }

    public long getId() {
        return id;
    }

    public long getChecklistId() {
        return checklistId;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public int getItemNo() {
        return itemNo;
    }

    public String getSecCategory() {
        return secCategory;
    }

    public String getSecMainQuestion() {
        return secMainQuestion;
    }

    public String getSecTarget() {
        return secTarget;
    }

    public String getSecDescription() {
        return secDescription;
    }

    public String getSecReference() {
        return secReference;
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

    public String getResult() {
        return result;
    }

    public String getRemark() {
        return remark;
    }

    public List<P1PhotoDto> getPhotos() {
        return photos;
    }
}
