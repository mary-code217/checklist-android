package com.hoho.cheklist.dto.detail;

import java.util.ArrayList;
import java.util.List;

public class ChecklistDetailDto {
    private long id;
    private String title;
    private String inspectedAt;
    private String department;
    private String systemName;
    private String inspector;
    private int stage;
    private String stageLabel;
    private String createdAt;
    private String updatedAt;

    private List<P1ItemDto> p1Items = new ArrayList<>();

    private List<P2ItemDto> p2Items = new ArrayList<>();
    private List<P2PhotoDto> p2Photos = new ArrayList<>();

    private ChecklistDetailDto() {}

    public static ChecklistDetailDto create(long id, String title, String inspectedAt,
                                            String department, String systemName, String inspector,
                                            int stage, String createdAt, String updatedAt) {
        ChecklistDetailDto request = new ChecklistDetailDto();

        request.id = id;
        request.title = title;
        request.inspectedAt = inspectedAt;
        request.department = department;
        request.systemName = systemName;
        request.inspector = inspector;
        request.stage = stage;
        request.createdAt = createdAt;
        request.updatedAt = updatedAt;

        request.stageLabel = request.stageLabel(stage);

        return request;
    }

    private String stageLabel(int stage) {
        switch (stage) {
            case 0:
                return "진행중";
            case 1:
                return "1단계 완료";
            case 2:
                return "2단계 완료";
            case 3:
                return "최종 완료";
            default:
                return "알 수 없음";
        }
    }

    public void addP1Items(List<P1ItemDto> p1Items) {
        this.p1Items = (p1Items != null) ? p1Items : new ArrayList<>();
    }

    public void addP2Items(List<P2ItemDto> p2Items) {
        this.p2Items = (p2Items != null) ? p2Items : new ArrayList<>();
    }

    public void addP2Photos(List<P2PhotoDto> p2Photos) {
        this.p2Photos = (p2Photos != null) ? p2Photos : new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInspectedAt() {
        return inspectedAt;
    }

    public String getDepartment() {
        return department;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getInspector() {
        return inspector;
    }

    public int getStage() {
        return stage;
    }

    public String getStageLabel() {
        return stageLabel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<P1ItemDto> getP1Items() {
        return p1Items;
    }

    public List<P2ItemDto> getP2Items() {
        return p2Items;
    }

    public List<P2PhotoDto> getP2Photos() {
        return p2Photos;
    }
}
