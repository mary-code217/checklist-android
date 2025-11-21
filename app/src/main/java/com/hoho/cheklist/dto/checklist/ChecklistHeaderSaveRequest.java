package com.hoho.cheklist.dto.checklist;

public class ChecklistHeaderSaveRequest {
    public String title;
    public String inspectedAt;
    public String department;
    public String systemName;
    public String inspector;

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
}
