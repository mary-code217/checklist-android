package com.hoho.cheklist.dto.checklist;

import java.util.List;

public class ChecklistPageView {
    List<ChecklistView> items;
    int page;
    int pageSize;
    int totalPages;
    long totalElements;
    boolean hasPrev;
    boolean hasNext;

    private ChecklistPageView() {}

    public static ChecklistPageView create(
            List<ChecklistView> items, int page, int pageSize,
            int totalPages, long totalElements, boolean hasPrev, boolean hasNext) {
        ChecklistPageView request = new ChecklistPageView();

        request.items = items;
        request.page = page;
        request.pageSize = pageSize;
        request.totalPages = totalPages;
        request.totalElements = totalElements;
        request.hasPrev = hasPrev;
        request.hasNext = hasNext;

        return request;
    }

    public List<ChecklistView> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
