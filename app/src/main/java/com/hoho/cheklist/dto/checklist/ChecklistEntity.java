package com.hoho.cheklist.dto.checklist;

public class ChecklistEntity {
    long id;
    String title;
    String inspectedAt;
    String department;
    String systemName;
    String inspector;
    int stage;
    String createdAt;
    String updatedAt;

    private ChecklistEntity() {}

    public static ChecklistEntity create(
            long id, String title, String inspectedAt,
            String department, String systemName, String inspector,
            int stage, String createdAt, String updatedAt) {
        ChecklistEntity request = new ChecklistEntity();

        request.id = id;
        request.title = title;
        request.inspectedAt = inspectedAt;
        request.department = department;
        request.systemName = systemName;
        request.inspector = inspector;
        request.stage = stage;
        request.createdAt = createdAt;
        request.updatedAt = updatedAt;

        return request;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
