package com.hoho.cheklist.dto.checklist;

public class ChecklistView {
    long id;
    String no;
    String title;
    String statusLabel;
    String createdDateLabel;
    int stageValue;

    private ChecklistView() {}

    public static ChecklistView create(long id, String no, String title,
                                       String statusLabel, String createdDateLabel, int stageValue) {
        ChecklistView request = new ChecklistView();

        request.id = id;
        request.no = no;
        request.title = title;
        request.statusLabel = statusLabel;
        request.createdDateLabel = createdDateLabel;
        request.stageValue = stageValue;

        return request;
    }

    public long getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public String getCreatedDateLabel() {
        return createdDateLabel;
    }

    public int getStageValue() {
        return stageValue;
    }
}
